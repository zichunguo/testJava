package com.guo.test.rabbitMQ.workqueues;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 测试第二种消息模型：work 消息模型（Work queues）-- 生产者
 */
public class Producer {
	
	// 消息队列名称
	private String queueName = "test";
	
	/**
	 * 发送消息的方法
	 * @param message
	 * @throws IOException
	 */
	public void send(String message) throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		
		// 通过通道声明队列
		channel.queueDeclare(queueName, false, false, false, null);
		
		// 发送消息
		channel.basicPublish("", queueName, null, message.getBytes());
		
		// 关闭资源
		RabbitMQUtil.closeChannelAndConnection(channel, connection);
	}
	
	public static void main(String[] args) throws IOException {
		Producer producer = new Producer();
		for (int i = 0; i < 10; i++) {
			producer.send("你好，" + (i + 1) + "先生！");
		}
	}

}
