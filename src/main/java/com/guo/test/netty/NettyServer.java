package com.guo.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	
	private int port; // 监听端口号
	
	public NettyServer(int port) {
		this.port = port;
		bind();
	}
	
	private void bind() {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		try {
			bootstrap.group(boss, worker);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024); // 连接数
			bootstrap.option(ChannelOption.TCP_NODELAY, true); // 不延迟，消息立即发送
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); // 长连接
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ChannelPipeline pipeline = socketChannel.pipeline();
					pipeline.addLast(new NettyServerHandler()); // 添加NettyServerHandler，用来处理Server端接收和处理消息的逻辑
				}
			});
			
			ChannelFuture channelFuture = bootstrap.bind(port).sync();
			if (channelFuture.isSuccess()) {
				System.out.println("启动Netty服务成功，端口号：" + this.port);
			}
			// 关闭连接
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			System.out.println("启动Netty服务异常，异常信息：" + e.getMessage());
			e.printStackTrace();
		} finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
	}
	
	public static void main(String[] args) throws InterruptedException {
        // 启动服务
		new NettyServer(10086);
    }

}
