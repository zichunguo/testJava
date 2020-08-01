package com.guo.test.rabbitMQ.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 测试第一种消息模型：基本消息模型（“Hello World”）-- 生产者
 */
public class BasicProducer {
	
	// 消息队列名称
	private String queueName = "test";
	
	/**
	 * 发送消息的方法
	 * @param message 发送的具体内容
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public void send(String message) throws IOException, TimeoutException {
		// 创建连接工厂对象
		ConnectionFactory connectionFactory = new ConnectionFactory();
		// 设置连接主机地址
		connectionFactory.setHost("127.0.0.1");
		// 设置端口号
		connectionFactory.setPort(5672);
		// 设置连接的虚拟主机，如果不设置默认为 "/"
		connectionFactory.setVirtualHost("/test");
		// 设置访问虚拟主机的用户名、密码
		connectionFactory.setUsername("test");
		connectionFactory.setPassword("test");
		
		// 获取连接对象
		Connection connection = connectionFactory.newConnection();
		
		// 获取连接中通道（信道）
		Channel channel = connection.createChannel();
		
		// 通道绑定消息队列
		// 参数1：queue，消息队列名称，如果队列不存在会自动创建
		// 参数2：durable，用来定义队列特行是否要持久化（是否持久化队列），true：持久化队列；false：不持久化
		// 参数3：exclusive，是否独占队列，true：独占队列；false：不独占
		// 参数4：autoDelete，是否在消费完成后自动删除队列，true：自动删除；false：不自动删除
		// 参数5：arguments，额外附加参数
		channel.queueDeclare(queueName, false, false, false, null);
		
		// 发布消息
		// 参数1：exchange，交换机名称
		// 参数2：routingKey，路由key，队列名称
		// 参数3：props，传递消息额外设置
		// 参数4：body，消息的具体内容
		channel.basicPublish("", queueName, null, message.getBytes());
		// 参数3 中的 MessageProperties.PERSISTENT_TEXT_PLAIN，设置消息持久化
//		channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		System.out.println("生产者已发送消息：" + message);
		
		// 关闭资源，包括通道和连接
		channel.close();
		connection.close();
	}
	
	/**
	 * 使用工具类的发送消息方法
	 * @param message 发送的消息
	 * @throws IOException
	 */
	public void sendOfUtil(String message) throws IOException {
		// 使用工具类获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		
		// 获取连接中通道（信道）
		Channel channel = connection.createChannel();
		
		// 通道绑定消息队列
		channel.queueDeclare(queueName, false, false, false, null);
		
		// 发布消息
		channel.basicPublish("", queueName, null, message.getBytes());
		System.out.println("生产者已发送消息：" + message);
		
		// 关闭资源，包括通道和连接
		RabbitMQUtil.closeChannelAndConnection(channel, connection);
	}
	
	public static void main(String[] args) throws IOException, TimeoutException {
		BasicProducer basicProducer = new BasicProducer();
//		basicProducer.send("你好");
		basicProducer.sendOfUtil("你好");
	}

}
