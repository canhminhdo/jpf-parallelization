/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * The Java Pathfinder core (jpf-core) platform is licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package jpf;

import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;

import config.AppConfig;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.util.StringSetMatcher;
import gov.nasa.jpf.vm.VM;
import redis.clients.jedis.Jedis;
import server.Application;
import server.ApplicationConfigurator;

/**
 * tool to save traces upon various conditions like - property violation - call
 * of a certain method - reaching a certain search depth - creating a certain
 * thread
 */
public class TraceStorer extends ListenerAdapter {
	
	int nTrace = 1;

	String traceFileName;

	// do we store to the same file? (i.e. overwrite previously stored traces)
	// if set to 'true', store all traces (in <traceFileName>.n)
	boolean storeMultiple;

	// do we want to terminate after first store, even if it's triggered by a
	// property violation?
	boolean terminateOnStore;

	boolean storeOnConstraintHit;

	// search depth at what we store the trace
	int storeDepth;

	// search depth at what we store the trace
	int storeBound;

	// calls that should trigger a store
	StringSetMatcher storeCalls;

	// thread starts that should trigger a store
	StringSetMatcher storeThreads;

	// do we want verbose output
	boolean verbose;

	Search search;
	VM vm;
	
	// redis for caching
	Jedis jedis = null;
	
	// app configuration
	AppConfig appConfig;
	
	// application information
	Application app;
	
	// sequence set
	HashMap<Integer, HashMap<String, Integer>> seqSet;
		
	// sequence set in this depth
	HashMap<String, Integer> seqDepthSet;
	
	// number of time hit to the cache
	int hitCached = 0;
	
	public TraceStorer() {}
	
	public TraceStorer(Config config, JPF jpf) {

		traceFileName = config.getString("trace.file", "trace");
		storeMultiple = config.getBoolean("trace.multiple", false);
		storeDepth = config.getInt("trace.depth", Integer.MAX_VALUE);
		storeBound = config.getInt("trace.bound", Integer.MAX_VALUE);
		verbose = config.getBoolean("trace.verbose", false);

		terminateOnStore = config.getBoolean("trace.terminate", false);
		storeOnConstraintHit = config.getBoolean("trace.store_constraint", false);
		
		vm = jpf.getVM();
		search = jpf.getSearch();
		
		// Read configuration
		app = ApplicationConfigurator.getInstance().getApplication();
		appConfig = AppConfig.getInstance();
		
		// calculate next depth for JPF
		storeDepth = calculateNextDepth(app.getTraceMsg().getLength(), storeDepth, appConfig.getBmcDepth());
		
		seqSet = app.getSeqSet();
		if (!seqSet.containsKey(storeDepth)) {
			seqSet.put(storeDepth, new HashMap<String, Integer>());
		}
		seqDepthSet = seqSet.get(storeDepth);
	}
	
	int calculateNextDepth(int currentDepth, int storeDepth, int bmcDepth) {
		assert currentDepth <= bmcDepth : "BMC depth must be equal to or greater than the current depth";
		if (storeDepth != Integer.MAX_VALUE) {
			if (currentDepth < bmcDepth - storeDepth) {
				storeDepth += currentDepth;
			} else {
				storeDepth = bmcDepth;
			}
		}
		return storeDepth;
	}
	
	void storeTrace(String reason) {
		String fname = traceFileName;

		if (storeMultiple) {
			fname = fname + '.' + nTrace++;
		}
		
		ExChoicePoint.storeTrace(fname, vm.getSUTName(), reason, vm.getSystemState().getChoiceGenerators(), verbose);
	}

	@Override
	public void propertyViolated(Search search) {
		storeTrace("violated property: " + search.getLastError().getDetails());
		checkSearchTermination();
	}
	
	public void submitJob(ExChoicePoint trace) {
		// check existing in big cached
		String sha256Hex = DigestUtils.sha256Hex(SerializationUtils.serialize(trace));
		if (seqDepthSet.containsKey(sha256Hex)) {
			seqDepthSet.replace(sha256Hex, seqDepthSet.get(sha256Hex) + 1);
			hitCached ++;
			System.out.println("Hit to the cache " + hitCached);
		} else {
			seqDepthSet.put(sha256Hex, 0);
			TraceMessage traceMsg = new TraceMessage(trace);
			mq.Sender.getInstance().sendJob(traceMsg);
		}
	}

	@Override
	public void stateAdvanced(Search search) {
		int id = search.getStateId();
		boolean has_next = search.hasNextState();
		boolean is_new = search.isNewState();
		boolean is_end = search.isEndState();
		int depth = search.getDepth();
		
		if (id < 0)
			return;
		
		// do not need to store trace in case:
		// - end state or do not have a next state (program finish)
		// - not new state (no more instruction will be executed)
		if (depth >= storeDepth) {
			if (is_new && has_next && !is_end) {
				// prepare to submit job
				if (depth > app.getTraceMsg().getLength() && depth < appConfig.getBmcDepth())
					submitJob(ExChoicePoint.buildTrace(search.getVM().getChoiceGenerators()));
//				checkSearchTermination();
			}
			nTrace ++;
			search.requestBacktrack();
		}
		
		
		if (nTrace >= storeBound) {
			search.terminate();
		}
	}

	@Override
	public void searchConstraintHit(Search search) {
		if (storeOnConstraintHit) {
			storeTrace("search constraint hit: " + search.getLastSearchConstraint());
			checkSearchTermination();
		}
	}

	boolean checkSearchTermination() {
		if (terminateOnStore) {
			search.terminate();
			return true;
		}

		return false;
	}

	@Override
	public void searchFinished(Search search) {
		app.printSeqSet();
		System.out.println("Depth is " + storeDepth + ", number of hit to the cache = " + hitCached);
	}
}
