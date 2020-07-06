package com.guo.test.sort;

interface ii {
	int i = 11;
	default void test() {
		System.out.println("tttt");
	}
}
public class TestSort {
	
	public static void main(String[] args) {
//		test();
		int[] arr = {2, 33, 5, 88, 3};
		printArr(arr);
//		printArr(sortOfBubble(arr));
//		printArr(sortOfSelect(arr));
//		printArr(sortOfInsert(arr));
//		printArr(sortOfQuick(arr, 0 , arr.length - 1));
	}
	
	// 快速排序
	public static int[] sortOfQuick(int[] arr, int start, int end) {
		if (start < end) {
			int i = start;
			int j = end;
			int base = arr[start];// arr[i]
			while (i < j) {
				while (i < j && arr[j] > base) {
					j--;
				}
				arr[i] = arr[j];
				while (i < j && arr[i] <= base) {
					i++;
				}
				arr[j] = arr[i];
			}
			arr[i] = base;
			sortOfQuick(arr, start, i - 1);
			sortOfQuick(arr, i + 1, end);
		}
		return arr;
	}
	
	// 插入排序
	public static int[] sortOfInsert(int[] arr) {
		int temp;
		int preIndex;
		for (int i = 1; i < arr.length; i++) {
			temp = arr[i];
			preIndex = i - 1;
			while (preIndex >= 0 && arr[preIndex] > temp) {
				arr[preIndex + 1] = arr[preIndex];
				preIndex--;
			}
			arr[preIndex + 1] = temp;
		}
		return arr;
	}
	
	// 选择排序
	public static int[] sortOfSelect(int[] arr) {
		int temp;
		int minIndex;
		for (int i = 0; i < arr.length - 1; i++) {
			minIndex = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			temp = arr[i];
			arr[i] = arr[minIndex];
			arr[minIndex] = temp;
		}
		return arr;
	}
	
	// 冒泡排序
	public static int[] sortOfBubble(int[] arr) {
		int temp;
		for (int i = 0; i < arr.length - 1; i++) {
			System.out.println("i : " + i);
			boolean flag = true;// 标志位，当初始数组已经是拍好序的，则不会进入if中，则第一次循环后直接退出
			for (int j = 0; j < arr.length - i - 1; j++) {
				System.out.println("j : " + j);
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					flag = false;
				}
			}
			if (flag) {
				break;
			}
		}
		return arr;
	}
	
	

	// 给定一个数组，返回数组中元素拼接后最大的值
	public static void test() {
		int[] arr = {32, 321, 9, 10};
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i+1; j < arr.length; j++) {
				if (sort(arr[i], arr[j]) > 0) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		
		for (int i = arr.length - 1; i >= 0; i--) {
			sb.append(arr[i]);
		}
		System.out.println("拼接最大值：" + sb.toString());
	}
	
	public static int sort(int a, int b) {
		int oa = Integer.parseInt(a + "" + b);
		int ob = Integer.parseInt(b + "" + a);
		return oa - ob;
	}

	// 输出数组
	public static void printArr(int[] arr) {
		System.out.print("[");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
			if (i < arr.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
	}
}
