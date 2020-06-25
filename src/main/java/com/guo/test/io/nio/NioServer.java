package com.guo.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioServer {
	
	private int port;
	
	private Selector selector;
	
	private ExecutorService service = Executors.newFixedThreadPool(5);
	
	public NioServer(int port) {
		this.port = port;
	}
	
	public void init() {
		ServerSocketChannel ssChannel = null;
		try {
			ssChannel = ServerSocketChannel.open();
			ssChannel.configureBlocking(false);
			ssChannel.bind(new InetSocketAddress(port));
			selector = Selector.open();
			ssChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("NioServer started ...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		this.init();
		while(true) {
			try {
				int events = selector.select();
				if (events > 0) {
					Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
					while(selectionKeys.hasNext()) {
						SelectionKey skey = selectionKeys.next();
						selectionKeys.remove();
						if(skey.isAcceptable()) {
							accept(skey);
						} else {
							service.submit(new NioServerHandler(skey));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void accept(SelectionKey key) {
		try {
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			SocketChannel sc = ssc.accept();
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
			System.out.println("accept a client : " + sc.socket().getInetAddress().getHostName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new NioServer(8222).start();
	}

}
