package com.guo.test.rabbitMQ.topic;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 测试第五种消息模型：订阅模型-Topic（Topics）-- 消费者2
 * 效果：动态路由，根据路由key（可以使用通配符“*”、“#”），动态匹配，exchange 将消息发到对应的消息队列
 * 说明：本类和 Consumer 类基本相同，只是测试多个消费者在 topic 模式下的效果
 */
public class Consumer2 {

	// 交换机名称
	private String exchangeName = "text_exchange_logs_topic";
	// 消息队列名称
	private String queueName = "test_topic_queue_2";
	// 路由key，使用通配符形式，“#”表示匹配0个或多个单词，如“log.info.user”、“log”
	private String routingKey = "log.#";
	
	/**
	 * 消费消息，使用 topic 模式
	 * @throws IOException
	 */
	public void receive() throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		// 声明交换机
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
		// 声明对象
		channel.queueDeclare(queueName, false, false, false, null);
		// 绑定交换机和队列，routingKey使用通配符的形式，动态匹配
		channel.queueBind(queueName, exchangeName, routingKey);
		// 消费消息
		channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				System.out.println("消费者1：" + new String(body));
			}
		});
	}
	
	public static void main(String[] args) throws IOException {
		Consumer2 consumer = new Consumer2();
		consumer.receive();
	}
	
}
