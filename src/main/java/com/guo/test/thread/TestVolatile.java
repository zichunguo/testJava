package com.guo.test.thread;

public class TestVolatile {
	
	public static boolean keepRunning = true;
//	public static volatile boolean keepRunning = true;
	
	public static volatile int i = 0;
	
	public volatile int inc = 0;;
	
	public synchronized void increase() {
        inc++;
    }
	
	public static void main(String[] args) {
		test3();
	}
	
	public static void test3() {
		for (int i = 0; i < 10; i++) {
			new Thread() {
				@Override
				public void run() {
					Singleton singleton = Singleton.getSingleton();
					singleton.ss();
				}
			}.start();
		}
	}

	private static void test() {
		new Thread() {
			@Override
			public void run() {
				while (TestVolatile.keepRunning){
//					System.out.println("========");// 使用此行程序会正常结束，是由于打印语句触发了happen—before
//					TestVolatile.i = 2;
//					int j = i;
				}
			}
		}.start();
		
		try {
			Thread.sleep(1000);
			TestVolatile.keepRunning = false;
//			Thread.sleep(1000);
//			TestVolatile.i = 2;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void test2() {
		final TestVolatile test = new TestVolatile();
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase();
                };
            }.start();
        }
         
        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
	}
	
}

class Singleton {
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
	    if (singleton == null) {
	        synchronized (Singleton.class) {
		        if (singleton == null) {
		            singleton = new Singleton();
		        }
	        }
	    }
	    return singleton;
    }
    public void ss() {
    	int i = 0;
    }
}  