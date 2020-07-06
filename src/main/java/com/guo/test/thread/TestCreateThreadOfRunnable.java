package com.guo.test.thread;

public class TestCreateThreadOfRunnable {
	public static void main(String[] args) {
		test();
	}

	private static void test() {
		// 实现 Runnable 接口
		ThreadTwo r = new ThreadTwo();
		Thread t = new Thread(r);
		t.setName("第二种方法线程。");
		t.start();
	}
}


/**
 * 实现多线程的第二种方式：
 * 实现 Runnable 接口，重写 run 方法
 */
class ThreadTwo implements Runnable {

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+ "2、实现 runnable 接口 ：" + time);
	}

}