package com.guo.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	// 服务器端口号
	private int port;
	
	// 服务器IP
	private String host;
	
	public NettyClient(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
        start();
    }
	
	private void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.remoteAddress(host, port);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new NettyClientHandler());
				}
			});
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			if (channelFuture.isSuccess()) {
                System.out.println("连接服务器成功.");
            }
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            group.shutdownGracefully();
        }
	}
	
	public static void main(String[] args) throws InterruptedException {
        new NettyClient(10086, "localhost");
    }
}
