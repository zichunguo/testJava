package com.guo.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 实现多线程的第四种方式：
 * 通过线程池创建
 */
public class TestCreateThreadOfPool {
	
	public static void main(String[] args) {
		test();
	}

	private static void test() {

//		ExecutorService es = Executors.newScheduledThreadPool(1);
		ExecutorService es = Executors.newSingleThreadExecutor();
//		ExecutorService es = Executors.newCachedThreadPool();
//		ExecutorService es = Executors.newFixedThreadPool(1);
//		ExecutorService es = buildMyThreadPool();
		
		RunnableThread t = new RunnableThread();
		es.execute(t);
		es.submit(t);
		RunnableThread2 t2 = new RunnableThread2();
		es.submit(t2);
		CallableThread t3 = new CallableThread();
		Future<?> f = es.submit(t3);
		
		try {
			Object object = f.get();
			System.out.println(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		es.shutdown();
	}

	public static ExecutorService buildMyThreadPool(){
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 6, 3,
	            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3)
	    );
		return threadPool;
	}
	
}

class CallableThread implements Callable<Object> {

	@Override
	public Object call() throws Exception {
		long time = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+ ", 4、线程池创建 call ：" + time);
		return "calllllll";
	}
	
}

class RunnableThread implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long time = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+ ", 4、线程池创建 ：" + time);
	}
	
}

class RunnableThread2 implements Runnable {

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+ ", 4、线程池创建222222222 ：" + time);
	}
	
}