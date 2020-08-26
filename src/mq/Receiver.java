package mq;

import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import config.AppConfig;
import jpf.TraceMessage;
import server.Application;
import server.ApplicationConfigurator;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master Start internally JPF program to generate state sequences
 * 
 * @author OgataLab
 *
 */
public class Receiver {

	/**
	 * Starting a RabbitMQ client.
	 * 
	 * @param argv Unsed.
	 * @throws Exception
	 */
	public static void main(String[] argv) throws Exception {
		// Initialize application with configuration
		Application app = ApplicationConfigurator.getInstance().getApplication();

		// rabbitMQ connection
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(app.getRabbitMQ().getHost());
		if (app.getServerFactory().isRemote()) {
			factory.setUsername(app.getRabbitMQ().getUserName());
			factory.setPassword(app.getRabbitMQ().getPassword());
		}
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// setting prefetch count: how many messages are being sent to the consumer at the same time.
		channel.basicQos(1);
		
		channel.queueDeclare(app.getRabbitMQ().getQueueName(), false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			
			TraceMessage traceMsg = SerializationUtils.deserialize(delivery.getBody());
			int length = traceMsg.getLength();
			
			assert traceMsg.getAppName() == AppConfig.getInstance().getAppName() : "Not message from our experiment";
			
			System.out.println(traceMsg);
			System.out.println("Depth " + length);
			System.out.println(" [x] Received '" + traceMsg);
			
			if (length >= AppConfig.getInstance().getBmcDepth()) {
				// Reach to maximum depth. Do not check anymore
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				return;
			}
			
			try {
				RunJPF runner = new RunJPF(traceMsg);
				runner.start();
				runner.join();
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		boolean autoAck = false;
		channel.basicConsume(app.getRabbitMQ().getQueueName(), autoAck, deliverCallback, (consumerTag) -> {
			System.out.println("Receiver consumer cancelling " + consumerTag);
		});
	}

}
