package com.guo.test.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
	
	public static void main(String[] args) throws Exception {
		testTimer();
	}
	public static void testTimer() {
		// 1.创建Timer实例
		Timer timer = new Timer();
		// 2.创建任务对象
		TestTimerTask task = new TestTimerTask();
		// 3.通过Timer定时定频率调用TestTimerTask的业务代码
		timer.schedule(task, 3000L, 2000L);
	}
	
}

class TestTimerTask extends TimerTask {

	@Override
	public void run() {
		System.out.println("test timer : " + System.currentTimeMillis());
	}
	
}