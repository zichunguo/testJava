package com.guo.test.thread;

public class TestSync {
	
	public static int i = 0;
	
	public static synchronized void test(String val) {
		System.out.println("hahaha : " + val);
		i++;
		if (i > 10) {
			i = 0;
			return;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		test(val + i);
	}
	
	public static void main(String[] args) {
//		TestSync.test("ooo");
		new Thread() {
			@Override
			public void run() {
				TestSync.test("aaa");
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				TestSync.test("bbb");
			}
		}.start();
	}

}
