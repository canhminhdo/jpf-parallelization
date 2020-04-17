package server.factory;

import config.AppConfig;
import server.instances.RabbitMQ;
import server.instances.Redis;

/**
 * Factory Method
 * 
 * @author OgataLab
 *
 */
public class ServerFactory {

	AppConfig config;

	public ServerFactory() {
		config = AppConfig.getInstance();
	}

	public Redis createRedis() {
		return new Redis(config.getRedisHost(), config.getRedisPort());
	}

	public RabbitMQ createRabbitMQ() {
		return new RabbitMQ(config.getRabbitmqHost(), config.getRabbitmqUsername(), config.getRabbitmqPassword(),
				config.getAppName());
	}

	public Boolean isRemote() {
		return config.isRemote();
	}
}
