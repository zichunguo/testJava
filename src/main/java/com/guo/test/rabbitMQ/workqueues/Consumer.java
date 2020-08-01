package com.guo.test.rabbitMQ.workqueues;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 测试第二种消息模型：work 消息模型（Work queues）-- 消费者1
 * 默认情况下，RabbitMQ 会轮询的方式将消息发给每个消费者（平均分配）。
 */
public class Consumer {

	// 消息队列名称
	private String queueName = "test";
	
	public void receive() throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		// 通过通道声明队列
		channel.queueDeclare(queueName, false, false, false, null);
		// 设置每次消费1个消息，默认一次性消费多个
		channel.basicQos(1);
		// 消费消息
		// 参数2：autoAck，消息确认机制，true：消费者自动向 RabbitMQ 确认消息消费；false：不自动确认消息
		channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				System.out.println("消费者-1：" + new String(body));
				// 手动确认，参数1：deliveryTag，手动确认消息标识；参数2：multiple，是否确认一次多个，false 表示一次确认一个
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		});
	}
	
	public static void main(String[] args) throws IOException {
		Consumer consumer = new Consumer();
		consumer.receive();
	}
	
}
