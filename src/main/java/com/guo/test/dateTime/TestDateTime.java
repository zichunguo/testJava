package com.guo.test.dateTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;

public class TestDateTime {
	
	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		Year year = Year.now();
		System.out.println(year);
		System.out.println(year.atMonth(10));
		System.out.println(year.atDay(10));
		
		System.out.println("================");
		MonthDay md = MonthDay.now();
		System.out.println(md);
		
		System.out.println("================");
		LocalDate ld = LocalDate.now();
		System.out.println(ld);
		
		System.out.println("================");
		LocalTime lt = LocalTime.now();
		System.out.println(lt);
	}

}
