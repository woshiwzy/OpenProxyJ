package com.proxy.utils;

public class Log {
	
	

	public static void print(String log) {
		sayout(log);
	}
	
	public static void println(String log) {
		sayout(log);
	}

	public static void redPrint(String log) {
		sayout(log);
	}

	private static void sayout(String log){
		System.err.println(log+"|");
	}
}
