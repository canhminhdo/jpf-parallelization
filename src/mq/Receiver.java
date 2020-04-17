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
import utils.DateUtil;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master Start internally JPF program to generate state sequences
 * 
 * @author OgataLab
 *
 */
public class Receiver {

	public static HashMap<Integer,Integer> analyzer;
	
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
		
		// HashTable to analyze the number of state at each depth
		analyzer = new HashMap<Integer,Integer>();
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			
			TraceMessage traceMsg = SerializationUtils.deserialize(delivery.getBody());
			int length = traceMsg.getLength();
			
			assert traceMsg.getAppName() == AppConfig.getInstance().getAppName() : "Not message from our experiment";
			
			System.out.println(traceMsg);
			System.out.println("JPF started at " + DateUtil.getDateTimeString());
			System.out.println(" [x] Received '" + traceMsg);
			
			analyzer(length);

			if (length >= AppConfig.getInstance().getBmcDepth()) {
				// Reach to maximum depth. Do not check anymore
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				return;
			}
			
			try {
				RunJPF runner = new RunJPF(traceMsg);
				runner.start();
				runner.join();
				System.out.println("JPF finished at " + DateUtil.getDateTimeString());
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				System.out.println("Sending ack");
			} catch (InterruptedException e) {
				System.out.println("waiting jpf and ack to rabbitmq " + e.getMessage());
				e.printStackTrace();
			}
		};

		boolean autoAck = false;
		channel.basicConsume(app.getRabbitMQ().getQueueName(), autoAck, deliverCallback, (consumerTag) -> {
			System.out.println("Receiver consumer cancelling " + consumerTag);
		});
	}
	
	public static void analyzer(int length) {
		if (analyzer.containsKey(length)) {
			analyzer.put(length, analyzer.get(length) + 1);
		} else {
			analyzer.put(length, 1);
		}
		
		System.out.println("--> Start Counter");
		for (int key: analyzer.keySet()) {
			System.out.println("Depth " + key + " = " + analyzer.get(key));
		}
		System.out.println("--> End Counter");
	}
}
