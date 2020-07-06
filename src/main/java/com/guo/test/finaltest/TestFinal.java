package com.guo.test.finaltest;

public class TestFinal {
	
	private static final int[] arr= {1,2,3};
	private static final int a = 1;
	
	public static void main(String[] args) {
		print(arr);
		arr[1] = 333;
		print(arr);
	}
	
	private static void print(int[] arr) {
		System.out.print("[");
		for (int i = 0 ; i < arr.length; i++) {
			System.out.print(arr[i]);
			if (i < arr.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
	}

}
