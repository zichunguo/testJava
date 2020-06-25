package com.guo.test.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestNIOOfFile {
	
	/**
	 * 测试 NIO 读写文件
	 */
	public static void testNIOOfFile() {
		try {
			FileInputStream fis = new FileInputStream("/Users/chun/Downloads/tt.txt");
			FileOutputStream fos = new FileOutputStream("/Users/chun/Downloads/tt2.txt");
			FileChannel ifc = fis.getChannel();
			FileChannel ofc = fos.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (ifc.read(buffer) != -1) {
				buffer.flip();
				ofc.write(buffer);
				buffer.clear();
			}
			ofc.close();
			ifc.close();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testNIOOfFile();
	}
	
}
