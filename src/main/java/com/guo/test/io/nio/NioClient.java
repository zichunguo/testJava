package com.guo.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
	
	public static final String host = "127.0.0.1";
	
	public static final int port = 8222;
	
	private Selector selector;
	
	public void connect(String host, int port) {
		try {
			SocketChannel sc = SocketChannel.open();
			sc.configureBlocking(false);
			selector = Selector.open();
			sc.connect(new InetSocketAddress(host, port));
			sc.register(selector, SelectionKey.OP_CONNECT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() {
		while(true) {
			try {
				int events = selector.select();
				if (events > 0) {
					Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
					while (selectionKeys.hasNext()) {
						SelectionKey key = selectionKeys.next();
						selectionKeys.remove();
						System.out.println(key.readyOps());
						if (key.isConnectable()) {
							SocketChannel sc = (SocketChannel) key.channel();
							if (sc.isConnectionPending()) {
								sc.finishConnect();
							}
							sc.configureBlocking(false);
							sc.register(selector, SelectionKey.OP_READ);
							sc.write(ByteBuffer.wrap(("Hello this is " + Thread.currentThread().getName()).getBytes()));
						} else if(key.isReadable()) {
							SocketChannel sc = (SocketChannel) key.channel();
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							sc.read(buffer);
							buffer.flip();
							System.out.println("收到服务端的数据："+new String(buffer.array()));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
//		NioClient client = new NioClient();
//		client.connect(host, port);
//		client.listen();
		for (int i=0;i<3;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NioClient client = new NioClient();
                    client.connect(host, port);
                    client.listen();
                }
            }).start();
        }
	}
}
