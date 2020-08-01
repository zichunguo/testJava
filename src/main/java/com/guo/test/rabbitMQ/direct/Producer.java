package com.guo.test.rabbitMQ.direct;

import java.io.IOException;

import com.guo.test.rabbitMQ.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 测试第四种消息模型：订阅模型-Direct（Routing）-- 生产者
 */
public class Producer {
	
	// 交换机名称
	private String exchangeName = "text_exchange_logs_direct";
	// 路由key
	private String routingKey = ROUTING_KEY_ERROR;
	public static final String ROUTING_KEY_INFO = "info";
	public static final String ROUTING_KEY_ERROR = "error";
	public static final String ROUTING_KEY_DEBUG = "debug";
	
	/**
	 * 发送消息，使用 direct 模式
	 * @param message 消息内容
	 * @throws IOException
	 */
	public void send(String message) throws IOException {
		// 获取连接对象
		Connection connection = RabbitMQUtil.getConnection();
		// 创建通道对象
		Channel channel = connection.createChannel();
		
		// 声明交换机，参数1：exchange，交换机名称；参数2：type，交换机类型，direct 表示路由模式
		channel.exchangeDeclare(exchangeName, "direct");
		
		// 发送消息
		channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		
		// 关闭资源
		RabbitMQUtil.closeChannelAndConnection(channel, connection);
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
	
	public static void main(String[] args) throws IOException {
		Producer producer = new Producer();
		// 设置 RoutingKey
//		String routingKey = ROUTING_KEY_ERROR;
		String routingKey = ROUTING_KEY_INFO;
//		String routingKey = ROUTING_KEY_DEBUG;
		
		producer.setRoutingKey(routingKey);
		producer.send(routingKey + " - 你好");
	}

}
