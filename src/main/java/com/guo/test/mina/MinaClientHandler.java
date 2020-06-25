package com.guo.test.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends IoHandlerAdapter {
	
	private IoSession session;
	
	/**
	 * 发送消息
	 * @param message 消息内容
	 */
	public void sent(String message) {
		System.out.println("client sent mseeage :" + message);
		session.write(message);
//		messageSent(session, message);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("****** client session closed ******");
		System.out.println("client message received ：" + message.toString());
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("****** client session created ******");
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("****** client session opened ******");
		this.session = session;
		session.write("hi hi hi ");
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("****** client session closed ******");
	}
	
}
