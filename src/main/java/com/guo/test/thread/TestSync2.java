package com.guo.test.thread;

public class TestSync2 {
	
	public static int i = 0;
	
	public static void test() {
		synchronized (TestSync2.class) {
			i++;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Thread() {
			@Override
			public void run() {
				synchronized (TestSync2.class) {
					TestSync2.i += 1;
				}
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				TestSync2.i++;
			}
		}.start();
		
//		TestSync2.i += 1;
		
		Thread.sleep(100);
		while (i == 2) {
			
		}
	}

}
