package com.guo.test.thread;

public class TestCreateThreadOfThread {
	
	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		// 继承 Thread 类
		ThreadOne t = new ThreadOne();
		t.setName("第一种方法线程。");
		t.start();
	}
}

/**
 * 实现多线程的第一种方式：
 * 继承 Thread 类，重写 run 方法
 */
class ThreadOne extends Thread {
	
	@Override
	public void run() {
		long time = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+ "1、继承 Thread 类 ：" + time);
	}
	
}
