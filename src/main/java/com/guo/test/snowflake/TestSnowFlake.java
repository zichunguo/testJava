package com.guo.test.snowflake;

import java.util.Calendar;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

public class TestSnowFlake {
	
	public static void main(String[] args) {
//		testHutoolSnowflake();
//		testBinary();
//		testTime();
//		testNetUtil();
		System.out.println(Long.MAX_VALUE);
	}
	
	/**
	 * 测试 hutool 工具中的 snowflake
	 */
	public static void testHutoolSnowflake() {
		// 使用
		Snowflake snowflake = IdUtil.getSnowflake(1, 1);
		long id = snowflake.nextId();
		System.out.println(id);
	}
	
	public static void testNetUtil() {
		String localhostStr = NetUtil.getLocalhostStr();
		long ipv4ToLong = NetUtil.ipv4ToLong(localhostStr);
		System.out.println(localhostStr);
		System.out.println(ipv4ToLong);
	}
	
	public static void testTime() {
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			System.currentTimeMillis();
		}
		long t2 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Calendar.getInstance().getTimeInMillis();
		}
		long t3 = System.currentTimeMillis();
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t3);
		System.out.println(t2 - t1);
		System.out.println(t3 - t2);
	}
	
	public static void testBinary() {
		System.out.println(~(-1L << 5));
		System.out.println(-1L ^ (-1L << 5));
		System.out.println(Long.toBinaryString(-1L));
		System.out.println(Long.toBinaryString((-1L << 41L)));
		System.out.println(Long.toBinaryString(~(-1L << 41L)));
	}

}
