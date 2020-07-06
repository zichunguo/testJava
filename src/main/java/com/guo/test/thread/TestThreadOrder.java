package com.guo.test.thread;

public class TestThreadOrder {

	public static void main(String[] args) throws Exception {
		testOrder2();
	}
	
	public static void testOrder2() throws Exception {
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
		ThreadOrderSync t1 = new ThreadOrderSync("a", c, a);
		ThreadOrderSync t2 = new ThreadOrderSync("b", a, b);
		ThreadOrderSync t3 = new ThreadOrderSync("c", b, c);
		t1.start();
		Thread.sleep(10);
		t2.start();
		Thread.sleep(10);
		t3.start();
		Thread.sleep(10);
	}
	
	public static void testOrder1() {
		ThreadOrder t1 = new ThreadOrder("X");
		ThreadOrder t2 = new ThreadOrder("Y");
		ThreadOrder t3 = new ThreadOrder("Z");
		ThreadOrder.nowVal = "X";
		t1.start();
		t2.start();
		t3.start();
	}
}

class ThreadOrderSync extends Thread {
	private String name;
	private Object prev;
	private Object self;
	
	public ThreadOrderSync(String name, Object prev, Object self) {
		this.name = name;
		this.prev = prev;
		this.self = self;
	}
	
	@Override
	public void run() {
		int count = 10;
		while (count > 0) {
			synchronized (prev) {
				synchronized (self) {
					 System.out.print(name);// 打印
                     count--;
                     self.notifyAll();
				}
				// 此时执行完self的同步块，这时self锁才释放。
                try {
                    if (count == 0) {// 如果count==0,表示这是最后一次打印操作，通过notifyAll操作释放对象锁。
                        prev.notifyAll();
                    } else {
                        prev.wait(); // 立即释放 prev锁，当前线程休眠，等待唤醒
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}
	}
	
}

class ThreadOrder extends Thread {
	public static String nowVal;
	private String val;
	
	public ThreadOrder(String val) {
		this.val = val;
	}
	@Override
	public void run() {
		int count = 10;
		while (count > 0) {
			if (nowVal.equals(val)) {
				System.out.print(val);
				if ("X".equals(nowVal)) {
					nowVal = "Y";
				} else if("Y".equals(nowVal)) {
					nowVal = "Z";
				} else if("Z".equals(nowVal)) {
					System.out.println();
					nowVal = "X";
				}
				count--;
			}
		}
	}
}
