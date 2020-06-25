package com.guo.test.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NioServerHandler implements Runnable {

	private SelectionKey skey;

	public NioServerHandler(SelectionKey key) {
		this.skey = key;
	}
	
	@Override
	public void run() {
		try {
			if (skey.isReadable()) {
				SocketChannel sc = (SocketChannel) skey.channel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				sc.read(buffer);
				buffer.flip();
				System.out.println("收到客户端"+sc.socket().getInetAddress().getHostName()+"的数据："+new String(buffer.array()));
				ByteBuffer outBuffer = ByteBuffer.wrap(buffer.array());
				sc.write(outBuffer);
				skey.cancel();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
