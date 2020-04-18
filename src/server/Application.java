package server;

import java.util.HashMap;
import java.util.Map;

import jpf.TraceMessage;
import server.factory.ServerFactory;
import server.instances.RabbitMQ;
import server.instances.Redis;

/**
 * Application that contains all important instances
 * 
 * @author OgataLab
 *
 */
public class Application {

	ServerFactory serverFactory = null;
	Redis redis = null;
	RabbitMQ rabbitMQ = null;
	TraceMessage traceMsg = null;
	HashMap<Integer, HashMap<String, Integer>> seqSet = new HashMap<Integer, HashMap<String, Integer>>();

	public Application(ServerFactory serverFactory) {
		this.serverFactory = serverFactory;
		this.createServer();
	}

	private void createServer() {
		this.redis = this.serverFactory.createRedis();
		this.rabbitMQ = this.serverFactory.createRabbitMQ();
	}
	
	/**
	 * Get ServerFactory configuration
	 * 
	 * @return {@link ServerFactory}
	 */
	public ServerFactory getServerFactory() {
		return serverFactory;
	}

	/**
	 * Get Redis configuration
	 * @return
	 */
	public Redis getRedis() {
		return redis;
	}

	/**
	 * Get RabbitMQ configuration
	 * 
	 * @return {@link RabbitMQ}
	 */
	public RabbitMQ getRabbitMQ() {
		return rabbitMQ;
	}

	public TraceMessage getTraceMsg() {
		return traceMsg;
	}

	public void setTraceMsg(TraceMessage traceMsg) {
		this.traceMsg = traceMsg;
	}
	
	public HashMap<Integer, HashMap<String, Integer>> getSeqSet() {
		return this.seqSet;
	}
	
	public void printSeqSet() {
		for (Map.Entry mapElement : seqSet.entrySet()) { 
			Integer depth = (Integer)mapElement.getKey(); 
            HashMap<String, Integer> seqDepthSet = (HashMap<String,Integer>)mapElement.getValue();
            System.out.println("Depth=" + depth + " #Seq=" + seqDepthSet.size());
        } 
	}
}
