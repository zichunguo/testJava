package com.guo.test.thread;

public class TestSync3 {
	
	public static int i = 0;
	
	public void increase() {
		i++;
	}
	
	public synchronized void increase1() {
		i++;
	}
	
	public synchronized static void increase2() {
		i++;
	}
	
	public static void increase3() {
		synchronized (TestSync3.class) {
			i++;
		}
	}
	
	public static void main(String[] args) {
		test2();
	}
	
	public static void test2() {
		TestSync3 test = new TestSync3();
		TestSync3 test2 = new TestSync3();
		Thread t1 = new Thread(){
            public void run() {
                for(int j=0;j<10000;j++)
                    test.increase1();
            };
        };
        Thread t2 = new Thread(){
            public void run() {
                for(int j=0;j<10000;j++)
                    test.increase1();
            };
        };
        t1.start();
        t2.start();
        
        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(TestSync3.i);
	}
	
	public static void test1() {
		TestSync3 test = new TestSync3();
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int i=0; i<1000; i++)
                        test.increase();
                };
            }.start();
        }

        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(TestSync3.i);
	}

}
