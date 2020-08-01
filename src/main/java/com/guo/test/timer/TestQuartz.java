package com.guo.test.timer;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TestQuartz {
	
	public static void main(String[] args) throws Exception {
		testQuartz();
	}
	
	public static void testQuartz() throws SchedulerException {
		// 1.创建调度器 Scheduler
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		// 2.创建JobDetail实例(工作对象)，并与定义的Job类绑定(Job执行内容)-任务对象
		JobDetail job = JobBuilder.newJob(TestQuartzJob.class)
				.withDescription("job 描述")
				.withIdentity("job1", "group1")
				.build();
		// 3.构造触发器 Trigger，设置定时时间
		Trigger trigger = TriggerBuilder.newTrigger()
				.withDescription("trigger 描述")
				.withIdentity("trigger1", "group1")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2))//每2秒执行一次
				.build();
		
//		Trigger trigger = TriggerBuilder.newTrigger()
//				.withIdentity("trigger1","group1").
//				withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))//每2秒执行一次
//				.build();
		// 4.设置 job 工作 和 trigger 触发器
		scheduler.scheduleJob(job, trigger);
		// 5.开始执行任务
		scheduler.start();
		// 6.结束任务
//		scheduler.shutdown();
	}

}