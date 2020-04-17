package server;

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
}
