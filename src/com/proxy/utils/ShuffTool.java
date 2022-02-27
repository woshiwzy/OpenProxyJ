package com.proxy.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;
import java.util.TimeZone;

import com.proxy.common.DeqMap;

/**
 * 生成密码本的程序
 */
public class ShuffTool {

	private static int getSeed() {
		Calendar ca1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long currentMiles = ca1.getTimeInMillis();
		int after = Integer.parseInt(String.valueOf(currentMiles).substring(0, 6));
		return after;
	}

	public static DeqMap<Byte, Byte> createPassWordBook() {

		ArrayList<Byte> bytes1 = new ArrayList<>();
		ArrayList<Byte> bytes2 = new ArrayList<>();

		for (byte b = Byte.MIN_VALUE; b <= Byte.MAX_VALUE; b++) {
			byte enc = 0;
			enc = (byte) -b;
			bytes1.add(b);
			bytes2.add(enc);
			if (b == Byte.MAX_VALUE) {
				break;
			}
		}

		Random rand = new Random(getSeed());

		Collections.shuffle(bytes1, rand);
		Collections.shuffle(bytes2, rand);

		DeqMap<Byte, Byte> deqMap = new DeqMap<>();

		for (int i = 0, isize = bytes1.size(); i < isize; i++) {
			deqMap.put(bytes1.get(i), bytes2.get(i));
		}

//		System.out.println(bytes1);
//		System.out.println(bytes1.size());

//		System.out.println(bytes2);
//		System.out.println(bytes2.size());

		return deqMap;
	}

//	public static void main(String[] args) {
//
//		DeqMap deqMap = createPassWordBook();
//		System.out.println("----------");
//		System.out.println(deqMap);
//
//		byte bk = 10;
//		System.out.println(bk + "==>" + deqMap.getVByK(bk));
//		byte bv = (byte) deqMap.getVByK(bk);
//		System.out.println(bv + "==>" + deqMap.getKByV(bv));
//
//	}

}
