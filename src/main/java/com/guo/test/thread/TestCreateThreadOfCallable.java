package com.guo.test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestCreateThreadOfCallable {
	public static void main(String[] args) {
		test();
	}

	private static void test() {
		// 实现 Callable 接口
		ThreadThree c = new ThreadThree();
		FutureTask<Object> ft = new FutureTask<Object>(c);
		Thread t = new Thread(ft);
		t.setName("第三种方法线程。");
		t.start();
		try {
			Object object = ft.get();
			System.out.println(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}

/**
 * 实现多线程的第三种方式：
 * 实现 Callable 接口
 */
class ThreadThree implements Callable<Object> {

	@Override
	public Object call() throws Exception {
		long time = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+ "3、实现 Callable 接口 ：" + time);
		return "callll";
	}

}