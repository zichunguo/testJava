package com.guo.test.rabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtil {
	// 主机地址
	private static String HOST = "127.0.0.1";
	// 端口号
	private static int PORT = 5672;
	// 虚拟机名称
	private static String VHOST = "/test";
	// 用户名
	private static String USER = "test";
	// 密码
	private static String PASS = "test";
	
	// 连接工厂对象
	private static  ConnectionFactory connectionFactory;
	
	static {
		// 创建连接工厂对象
		connectionFactory = new ConnectionFactory();
		// 设置连接主机地址
		connectionFactory.setHost(HOST);
		// 设置端口号
		connectionFactory.setPort(PORT);
		// 设置连接的虚拟主机，如果不设置默认为 "/"
		connectionFactory.setVirtualHost(VHOST);
		// 设置访问虚拟主机的用户名、密码
		connectionFactory.setUsername(USER);
		connectionFactory.setPassword(PASS);
	}
	
	/**
	 * 通过连接工厂获取连接对象
	 * @return Connection 连接对象
	 */
	public static Connection getConnection() {
			try {
				return connectionFactory.newConnection();
			} catch (IOException | TimeoutException e) {
				e.printStackTrace();
			}
			
		return null;
	}
	
	/**
	 * 关闭通道和连接对象
	 * @param channel 通道对象
	 * @param connection 连接对象
	 */
	public static void closeChannelAndConnection(Channel channel, Connection connection) {
		closeChannel(channel);
		closeConnection(connection);
	}
	
	/**
	 * 关闭通道
	 * @param channel 通道对象
	 */
	public static void closeChannel(Channel channel) {
		try {
			if (channel != null) {
				channel.close();
			}
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭连接对象
	 * @param connection 连接对象
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
