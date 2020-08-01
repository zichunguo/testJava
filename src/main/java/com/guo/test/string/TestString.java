package com.guo.test.string;

public class TestString {
	
	public static void main(String[] args) {
		String strab1 = new String("ab");
		String strab2 = new String("ab");
		String strab3 = new String("ab");
		System.out.println(strab1 == strab2);
		System.out.println(strab2 == strab3);
		
		String strabc1 = "abc";
		String strabc2 = "abc";
		String strabc3 = "abc";
		
		System.out.println(strabc1 == strabc2);
		System.out.println(strabc2 == strabc3);
	}

}

