package com.wechat.cache;

import java.util.Calendar;

public class CacheManager {
	public static void main(String[] args) {
		Calendar c =  Calendar.getInstance();
		System.out.println(String.format("Duke's Birthday: %1$tm %1$te %1$tY", c));
	}
}
