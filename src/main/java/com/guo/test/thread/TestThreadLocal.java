package com.guo.test.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class TestThreadLocal {
	
	public static int i = 10;
	public static Map<String, Object> map = new HashMap<String, Object>();
	
	public static void main(String[] args) {
//		test2();
		test4();
	}
	
	public static void test4() {
		ReentrantLock rl = new ReentrantLock();
		if(rl.tryLock()) {
			System.out.println("获取错成功！");
		}
		rl.unlock();
//		rl.unlock();
	}
	
	public static void test3() {
		B b = new B(22);
		b.print();
		B b2 = new B(33);
		b2.print();
	}
	
	public static void test2() {
		map.put("aa", new Object());
		map.put("bb", new Object());
		Object a = map.get("aa");
		Object b = map.get("bb");
		new Thread() {
			@Override
			public void run() {
				synchronized (a) {
					while (true) {
						System.out.println("aaa");
					}
				}
				
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				synchronized (b) {
					while (true) {
						System.out.println("bb");
					}
				}
				
			}
		}.start();
	}
	
	public static void test1() {
		for (int i = 0; i < 10; i++) {
			new Thread() {
				@Override
				public void run() {
					while (true) {
						if (TestThreadLocal.i == 0) {
							System.out.println("iiii 00000");
						}
					}
				}
			}.start();
		}
		
		try {
			Thread.sleep(1000);
			new Thread() {
				@Override
				public void run() {
					synchronized (this) {
						TestThreadLocal.i = 0;
						System.out.println("设置 i = 0");
					}
				}
			}.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}


class A {
	public int i = 1;
	final void print() {
		System.out.println(this.i);
	}
}

class B extends A {
	public B(int i) {
		super.i = i;
	}
}