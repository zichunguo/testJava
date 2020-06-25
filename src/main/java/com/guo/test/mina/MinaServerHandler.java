package com.guo.test.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 服务器端消息处理器
 */
public class MinaServerHandler extends IoHandlerAdapter {
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("****** server message received ******");
		String str = message.toString();
        System.out.println("server message received ：" + str);
        if (str.endsWith("quit")) {
            session.closeNow();
            return;
        }
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("****** server session created ******");
		super.sessionCreated(session);
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("****** server session opened ******");
		super.sessionOpened(session);
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("****** server session closed ******");
		super.sessionClosed(session);
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("****** server session idle ******");
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		/**
         * 自定义异常处理， 要不然异常会被“吃掉”；
         */
        cause.printStackTrace();
	}
}
