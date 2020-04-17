package jpf;

import static org.junit.Assert.assertFalse;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.VM;
import server.Application;
import server.ApplicationConfigurator;

public class ChoiceSelector extends ListenerAdapter {
	// set if we replay a trace
	ExChoicePoint trace;

	// start the search when reaching the end of the stored trace. If not set,
	// the listener will just randomly select single choices once the trace
	// got processed
	boolean searchAfterTrace;
	
	// application information
	Application app;

	public ChoiceSelector (Config config, JPF jpf) {
	    
		VM vm = jpf.getVM();

		// Read configuration
		app = ApplicationConfigurator.getInstance().getApplication();
		
		trace = app.getTraceMsg().getEx();
	    searchAfterTrace = config.getBoolean("choice.search_after_trace", true);
	    vm.setTraceReplay(trace != null);
	  }

	@Override
	public void choiceGeneratorAdvanced(VM vm, ChoiceGenerator<?> currentCG) {
		if (trace != null) { 
			// this is a replay
			// <2do> maybe that should just be a warning, and then a single choice
			assert currentCG.getClass().getName()
					.equals(trace.getCgClassName()) : "wrong choice generator class, expecting: "
							+ trace.getCgClassName() + ", read: " + currentCG.getClass().getName();

			int choiceIndex = trace.getChoiceIndex();
			currentCG.select(choiceIndex);
		}
	}

	@Override
	public void stateAdvanced(Search search) {
		if (trace != null) {
			// there is no backtracking or restoring as long as we replay
//			System.out.println("Replaying at depth = " + search.getDepth() + ", stateID = " + search.getStateId());
			trace = trace.getNext();

			if (trace == null) {
				search.getVM().setTraceReplay(false);
				if (!searchAfterTrace) {
					search.terminate();
				}
			}
		} else {
//			System.out.println("Searching at depth = " + search.getDepth() + ", stateID = " + search.getStateId());
		}
	}
	
	@Override
	public void stateBacktracked(Search search) {
		assertFalse(search.getVM().isTraceReplay());
//		System.out.println("Backtracking at depth = " + search.getDepth() + ", stateID = " + search.getStateId());
	}
}
