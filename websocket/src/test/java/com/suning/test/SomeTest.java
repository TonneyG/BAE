package com.suning.test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class SomeTest {
	public static void main(String[] args) throws ParseException {
		AtomicInteger i = new AtomicInteger();
		i.getAndIncrement();
		System.out.println(i);
		
		Calendar calendar = Calendar.getInstance();
	      // 指定一个日期  
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(calendar.get(Calendar.MONTH)+1);
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
	}
}
