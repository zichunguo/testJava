package com.guo.test.thread;

public abstract class TestJoin {
	
	// 测试 join() 方法
	public static void testJoin() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("线程1：" + i);
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("线程2：" + i);
				}
			}
		});
		
		try {
			t1.start();
			t1.join();
			t2.start();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testJoin();
	}

}
