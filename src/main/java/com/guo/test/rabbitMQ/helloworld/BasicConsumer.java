package com.guo.test.rabbitMQ.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 测试第一种消息模型：基本消息模型（“Hello World”）-- 消费者
 */
public class BasicConsumer {
	
	// 消息队列名称
	private String queueName = "test";
	
	/**
	 * 消费消息方法
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public void receive() throws IOException, TimeoutException {
		// 创建连接工程
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/test");
		connectionFactory.setUsername("test");
		connectionFactory.setPassword("test");
		
		// 获取连接对象
		Connection connection = connectionFactory.newConnection();
		
		// 获取连接中通道
		Channel channel = connection.createChannel();
		
		// 通道绑定消息队列
		channel.queueDeclare(queueName, false, false, false, null);
		
		// 创建消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			// 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
			public void handleDelivery(String consumerTag, Envelope envelope, 
					AMQP.BasicProperties properties, byte[] body) throws IOException {
				// 最后的参数 body 为消息内容
				String msg = new String(body);
				System.out.println("消费者接收到消息：" + msg);
			};
		};
		
		// 消费消息
		// 参数1：queue，队列名称
		// 参数2：autoAck，消息的自动确认机制（消息是否自动确认）
		// 参数3：callback，消费时的回调接口
		channel.basicConsume(queueName, true, consumer);
		
		// 关闭资源，包括通道和连接
		// 注意此处不应该关闭资源，因为如果关闭后会导致接受消息后影响回调函数的执行
//		channel.close();
//		connection.close();
	}
	
	/**
	 * 使用工具类的消费消息方法
	 * @throws IOException
	 */
	public void receiveOfUtil() throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		
		// 获取连接中通道
		Channel channel = connection.createChannel();
		
		// 通道绑定消息队列
		channel.queueDeclare(queueName, false, false, false, null);
		
		// 创建消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			// 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
			public void handleDelivery(String consumerTag, Envelope envelope, 
					AMQP.BasicProperties properties, byte[] body) throws IOException {
				// 最后的参数 body 为消息内容
				String msg = new String(body);
				System.out.println("消费者接收到消息：" + msg);
			};
		};
		
		// 消费消息
		channel.basicConsume(queueName, true, consumer);
	}
	
	public static void main(String[] args) throws IOException, TimeoutException {
		BasicConsumer basicConsumer = new BasicConsumer();
//		basicConsumer.receive();
		basicConsumer.receiveOfUtil();
	}

}
