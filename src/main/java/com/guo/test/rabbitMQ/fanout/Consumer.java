package com.guo.test.rabbitMQ.fanout;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 测试第三种消息模型：订阅模型-Fanout（Publish/Subscribe）-- 消费者
 * 效果：每个队列都会收到一份消息内容（广播）
 */
public class Consumer {

	// 交换机名称
	private String exchangeName = "text_exchange_logs_fanout";
	// 队列名称
	private String queueName = "test_fanout_queue_1";
	
	/**
	 * 消费消息，使用 fanout 方式
	 * @throws IOException
	 */
	public void receive() throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		
		// 声明交换机
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
		
		// 获取临时队列
//		String queueName = channel.queueDeclare().getQueue();
		// 声明队列，如果使用上面的临时队列可以不用声明
		channel.queueDeclare(queueName, false, false, false, null);
		
		// 绑定交换机和队列
		channel.queueBind(queueName, exchangeName, "");
		
		// 消费消息
		channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				System.out.println("消费者1：" + new String(body));
			}
		});
	}
	
	public static void main(String[] args) throws IOException {
		Consumer consumer = new Consumer();
		consumer.receive();
	}
	
}
