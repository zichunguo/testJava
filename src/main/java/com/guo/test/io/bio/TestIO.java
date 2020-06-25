package com.guo.test.io.bio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class TestIO {
	
	public static void main(String[] args) throws IOException {
		// 测试字节流 InputStream、OutputStream、FileInputStream、FileOutputStream
//		test1();
		
		// 测试字符流 FileReader，FileWriter
//		test2();
		
		// 测试字符流 BufferedReader、BufferedWriter
//		test3();
		
		// 测试字符流 InputStreamReader、OutputStreamWriter
//		test4();
		
		// 测试读取文本文件
//		testReadFile("/Users/chun/Downloads/tt.txt");
		
		// 测试写入文本文件
//		testWriterFile("hello", "/Users/chun/Downloads/tt3.txt");
		
		// 测试读取文件中目标字符串的次数
//		readFileOfStrCount("/Users/chun/Downloads/tt.txt", "ni");
		
		// 测试文件拷贝
//		copyFile("/Users/chun/Downloads/tt.txt", "/Users/chun/Downloads/tt2.txt");
//		copy("/Users/chun/Downloads/temp", "/Users/chun/Downloads/temp2");
	}
	
	/**
	 * 测试字节流
	 * InputStream、OutputStream、FileInputStream、FileOutputStream
	 */
	public static void test1() {
		try {
			InputStream is = new FileInputStream("/Users/chun/Downloads/logo.png");
			OutputStream os = new FileOutputStream("/Users/chun/Downloads/logo2.png");
//			int read = is.read();
//			System.out.println(read);
//			os.write(read);
			byte[] buff = new byte[1024];
			int len;
			while ((len = is.read(buff)) != -1) {
//				System.out.println(new String(buff, 0, len));
//				os.write(buff);
				os.write(buff, 0, len);
			}
			System.out.println("文件读取完。");
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试字符流 FileReader，FileWriter
	 * 字符流适合处理文本文件，不适合处理图片等文件
	 */
	public static void test2() {
		try {
			FileReader fr = new FileReader("/Users/chun/Downloads/tt.txt");
			FileWriter fw = new FileWriter("/Users/chun/Downloads/tt2.txt");
			char[] buff = new char[1024];
			int len;
			while ( (len = fr.read(buff)) != -1) {
				System.out.println(new String(buff, 0 , len));
				fw.write(buff);
			}
			fr.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试字符流 BufferedReader、BufferedWriter
	 * 字符流适合处理文本文件，不适合处理图片等文件
	 */
	public static void test3() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/chun/Downloads/tt.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/chun/Downloads/tt2.txt"));
			String val = "";
			while ((val = br.readLine()) != null) {
				System.out.println(val);
				bw.write(val);
			}
			br.close();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试字符流 InputStreamReader、OutputStreamWriter
	 * 字符流适合处理文本文件，不适合处理图片等文件
	 */
	public static void test4() {
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream("/Users/chun/Downloads/tt.txt"));
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("/Users/chun/Downloads/tt2.txt"));
			char[] buff = new char[1024];
			int len;
			while ( (len = isr.read(buff)) != -1) {
				System.out.println(new String(buff, 0, len));
				osw.write(buff);
			}
			isr.close();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试读取文本文件
	 * @param path 目标文件路径
	 * @return
	 */
	public static String testReadFile(String path) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String val;
			while ((val = br.readLine()) != null) {
				sb.append(val);
			}
			br.close();
			System.out.println("文件中内容：" + sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 测试写入文本文件
	 * @param val 写入内容
	 * @param path 目标文件路径
	 */
	public static void testWriterFile(String val, String path) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(val);
			bw.close();
			System.out.println("写入文件完成. file：" + path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试读取文件中目标字符串的次数
	 * @param path 文件路径
	 * @param targ 目标字符串
	 */
	public static void readFileOfStrCount(String path, String targ) {
		File file = new File(path);
		try {
			if (file.isFile() && file.exists()) {// 判断文件是否存在
				// 获取文件中的内存
				BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				StringBuffer sb = new StringBuffer();
				String lineStr = null;
				while ((lineStr = read.readLine()) != null) {
					sb.append(lineStr);
				}
				System.out.println(sb.toString());
				String str = sb.toString();
				System.out.println(str);
				// 查询内容中包含的目标字符串个数
				int count = 0;
				while (str.indexOf(targ) >=0) {
					str = str.substring(str.indexOf(targ) + targ.length());
					count++;
				}
				System.out.println("文件中共出现目标字符串 " + count + " 次");
				read.close();
			} else {
				System.out.println("未找到指定的文件");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将某一文件或路径拷贝到另一文件或目录
	 * @param path 源文件或目录路径
	 * @param targ 目标文件或目录路径
	 */
	public static void copy(String path, String targ) {
		File file = new File(path);
		if(file.isDirectory()) {
			File tfile = new File(targ);
			if (!tfile.exists()) {
				tfile.mkdirs();
			}
			File[] listFiles = file.listFiles();
			for (File f : listFiles) {
				if (f.getName().equals(".DS_Store")) {
					continue;
				}
				String targFilePath = targ + File.separator +  f.getName();
				String sourceFilePath = path + File.separator +  f.getName();
				copy(sourceFilePath, targFilePath);
			}
		} else {
			copyFile(path, targ);
		}
	}
	
	/**
	 * 将某文件拷贝到另一文件
	 * @param path 源文件路径
	 * @param targ 目标文件路径
	 */
	public static void copyFile(String path, String targ) {
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targ));
			byte[] buffer = new byte[1024];
			while(bis.read(buffer) != -1) {
				bos.write(buffer);
			}
			bis.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
