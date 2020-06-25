package com.guo.test.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	
	// 服务器IP
	private String hostname;
	
	// 服务器端口号
	private int port;
	
	MinaClientHandler handler = new MinaClientHandler();
	
	public MinaClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		start();
	}

	/**
	 * 创建客户端连接
	 */
	public void start() {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000);
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		connector.setHandler(handler);
		connector.connect(new InetSocketAddress(hostname, port));
	}
	
	/**
	 * 发送消息
	 * @param msg
	 */
	public void sent(String msg) {
		handler.sent(msg);
	}
	
	public static void main(String[] args) {
		String msg = "nihao server";
		MinaClient minaClient = new MinaClient("localhost", 10010);
		try {
			Thread.sleep(3000);
			minaClient.sent(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
