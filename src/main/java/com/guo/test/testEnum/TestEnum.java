package com.guo.test.testEnum;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class TestEnum {
	
	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		Code[] values = Code.values();
//		Code.OK.setCode(100);
		for (Code e : values) {
			System.out.println(e + "---" + e.getCode() + "---" + e.ordinal() + "---" + e.getMsg());
		}
		// EnumSet的使用
		System.out.println("***  EnumSet展示  ***");
		EnumSet<Code> errSet = EnumSet.allOf(Code.class);
		for (Code e : errSet) {
		    System.out.println(e.name() + " : " + e.ordinal());
		}
		
		// EnumMap的使用
		System.out.println("***  EnumMap展示  ***");
		EnumMap<Code, String> errMap = new EnumMap<Code, String>(Code.class);
		errMap.put(Code.OK, "成功");
		errMap.put(Code.ERROR, "失败");
		Iterator<Entry<Code, String>> iter = errMap.entrySet().iterator();
		while (iter.hasNext()) {
		    Entry<Code, String> next = iter.next();
		    System.out.println(next.getKey().name() + " : " + next.getValue());
		}
	}

}
