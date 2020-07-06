package com.guo.test.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务执行的类
 *	注意：job 类要定义为 public 否则会报错，在通过反射创建 job 类时出错
 */
public class TestQuartzJob implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("test quartz : " + sf.format(date));
	}
	
}
