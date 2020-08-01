package com.guo.test.rabbitMQ.topic;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 测试第五种消息模型：订阅模型-Topic（Topics）-- 生产者
 */
public class Producer {
	
	// 交换机名称
	private String exchangeName = "text_exchange_logs_topic";
	// 路由key
//	private String routingKey = "log.info";
	private String routingKey = "log.info.user";
	
	/**
	 * 发送消息，使用topic模式
	 * @param message 消息内容
	 * @throws IOException
	 */
	public void send(String message) throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		// 声明交换机及类型，topic：表示动态路由模式
		channel.exchangeDeclare(exchangeName, "topic");
		// 发送消息
		channel.basicPublish(exchangeName, routingKey, null, message.getBytes());;
		// 关闭资源
		RabbitMQUtil.closeChannelAndConnection(channel, connection);
	}
	
	public static void main(String[] args) throws IOException {
		Producer producer = new Producer();
		producer.send("topic 消息");
	}
	
}
