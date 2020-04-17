package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import config.AppConfig;
import jpf.TraceMessage;
import server.Application;
import server.ApplicationConfigurator;

/**
 * Starter program to kick-off environment by sending the initial message to
 * RabbitMQ master
 * 
 * @author OgataLab
 *
 */
public class Starter {

	/**
	 * Connecting to RabbitMQ and send initial message
	 * 
	 * @param argv Unused.
	 */
	public static void main(String[] argv) {
		try {
			// Initialize application with configuration
			Application app = ApplicationConfigurator.getInstance().getApplication();
			ApplicationConfigurator.getInstance().reset();
			cleanUp(app);
			
			// Push a initial job to message queue
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(app.getRabbitMQ().getHost());
			if (app.getServerFactory().isRemote()) {
				factory.setUsername(app.getRabbitMQ().getUserName());
				factory.setPassword(app.getRabbitMQ().getPassword());
			}

			// connection and channel will close automatically after try-with-resources
			try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
				channel.queueDeclare(app.getRabbitMQ().getQueueName(), false, false, false, null);
				
				TraceMessage traceMsg = new TraceMessage(null);
				
				byte[] data = SerializationUtils.serialize(traceMsg);

				channel.basicPublish("", app.getRabbitMQ().getQueueName(), null, data);
				System.out.println(" [x] Sent '" + traceMsg);
			}
		} catch (Exception e) {
			System.out.println("Cannot start up");
			e.printStackTrace();
		}
	}

	/**
	 * Clean up before starting environment
	 * 
	 * @param app
	 */
	public static void cleanUp(Application app) {
		try {
			// Purge queue from RabbitMQ
			if (!app.getServerFactory().isRemote()) {
				Process p1 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ AppConfig.getInstance().getAppName());

				p1.waitFor();
			}

			// Flush all keys and values from Redis server
//			RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection().flushAll();
		} catch (Exception e) {
			System.out.println("Cannot clean up");
			e.printStackTrace();
		}
	}
}
