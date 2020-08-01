package com.guo.test.lambda;

import java.util.ArrayList;
import java.util.List;

public class TestLambda {

	public static void main(String[] args) {
		MathOperation add = (a, b) -> a + b;
		System.out.println(add.operation(1, 2));
		new Thread(() -> System.out.println("lambda 测试")).start();
		new Thread(TestLambda::tp).start();
		TestLambda tl = new TestLambda();
		new Thread(tl::tp3).start();
		
		List<String> list = new ArrayList<String>();
		list.add("nihao");
		list.stream().forEach((s) -> System.out.println(s));
		list.stream().forEach(System.out::println);
	}
	
	public static void tp() {
		System.out.println("静态方法引用");
	}
	
	public static int tp2() {
		return 3;
	}
	
	public void tp3() {
		System.out.println("普通方法引用");
	}
	
	public void tp4(TestLambda tl) {
		System.out.println("普通方法引用");
	}
	
	@FunctionalInterface
	interface MathOperation {
        int operation(int a, int b);
    }
}
