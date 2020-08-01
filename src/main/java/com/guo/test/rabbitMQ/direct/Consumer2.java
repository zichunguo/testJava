package com.guo.test.rabbitMQ.direct;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 测试第四种消息模型：订阅模型-Direct（Routing）-- 消费者
 * 效果：根据路由key，exchange 将消息发到对应的消息队列
 * 说明：该类和Consumer类基本一致，只是用于测试多个队列中的路由效果
 */
public class Consumer2 {
	
	// 交换机名称
	private String exchangeName = "text_exchange_logs_direct";
	// 消息队列名称
	private String queueName = "test_direct_queue_2";
	// 路由key
	public static final String ROUTING_KEY_INFO = "info";
	public static final String ROUTING_KEY_ERROR = "error";
	public static final String ROUTING_KEY_DEBUG = "debug";

	/**
	 * 消费消息方法，使用 direct 方式
	 * @throws IOException
	 */
	public void receive() throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		// 声明交换机及交换机类型
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
		// 声明消息队列
		channel.queueDeclare(queueName, false, false, false, null);
		// 绑定交换机和对象
		channel.queueBind(queueName, exchangeName, ROUTING_KEY_ERROR);
		// 发送消息
		channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				System.out.println("消费者2：" + new String(body));
			}
		});
	}
	
	public static void main(String[] args) throws IOException {
		Consumer2 consumer = new Consumer2();
		consumer.receive();
	}
	
}
