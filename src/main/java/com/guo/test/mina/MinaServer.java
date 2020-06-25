package com.guo.test.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	
	private int port;
	
	public MinaServer(int port) {
		this.port = port;
		start();
	}

	public void start() {
		try {
			// 创建IoAcceptor
			IoAcceptor acceptor = new NioSocketAcceptor();
			// 加入编码过滤器，这里处理的是String类型
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
			// 设置session对应的I/O processor 读缓冲区大小2048，通常这个参数不需要设置
			acceptor.getSessionConfig().setReadBufferSize(2048);
			// 设置空闲时间，这里的BOTH_IDLE指EADER_IDLE和WRITER_IDLE都为10秒
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			// 设置handler
			acceptor.setHandler(new MinaServerHandler());
			// 绑定监听端口
			acceptor.bind(new InetSocketAddress(port));
			System.out.println("mina 服务启动成功.");
		} catch (Exception e) {
			System.err.println("服务启动失败.");
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {
		new MinaServer(10010);
	}
}
