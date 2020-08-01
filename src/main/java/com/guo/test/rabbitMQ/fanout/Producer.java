package com.guo.test.rabbitMQ.fanout;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 测试第三种消息模型：订阅模型-Fanout（Publish/Subscribe）-- 生产者
 */
public class Producer {
	
	// 交换机名称
	private String exchangeName = "text_exchange_logs_fanout";
	
	/**
	 * 发送消息，使用 fanout 方式
	 * @param message 消息内容
	 * @throws IOException
	 */
	public void send(String message) throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		
		// 将通道声明指定交换机
		// 参数1：exchange，交换机名称
		// 参数2：type，交换机类型，fanout：广播类型
		channel.exchangeDeclare(exchangeName, "fanout");
		
		// 发送消息。此模式下指定交换机，即生产者将消息发送给交换机，由交换机分发到对应的消息队列。而 fanout 广播类型时，第二个参数 routingKey 可以不设置
		channel.basicPublish(exchangeName, "", null, message.getBytes());
		
		// 关闭资源
		RabbitMQUtil.closeChannelAndConnection(channel, connection);
	}
	
	public static void main(String[] args) throws IOException {
		Producer producer = new Producer();
		producer.send("你好");
	}

}
