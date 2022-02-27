package com.proxy.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.proxy.utils.Log;

public class Shuff {
	
	
	public static Shuff create() {
		return new Shuff();
	}

	private ArrayList<Byte> encList;
	private ArrayList<Byte> decList;

	private Shuff() {
		encList = new ArrayList<>();
		decList = new ArrayList<>();
		for (byte b = Byte.MIN_VALUE; b <= Byte.MAX_VALUE; b++) {
			byte enc = 0;
			enc = (byte) -b;
			encList.add(b);
			decList.add(enc);

			if (b == Byte.MAX_VALUE) {
				break;
			}
		}

		Random rand = new Random((int) System.currentTimeMillis());
		Collections.shuffle(encList, rand);
		Collections.shuffle(decList, rand);
	}

	public String getEncDecString() {
		StringBuffer sbf = new StringBuffer();
		String connectChar = "=";
		for (int i = 0, isize = encList.size(); i < isize; i++) {
			if (i != isize - 1) {
				sbf.append(encList.get(i) + connectChar + decList.get(i) + ",");
			} else {
				sbf.append(encList.get(i) + connectChar + decList.get(i));
			}
		}
		return sbf.toString();
	}

	public void printShuffs() {
		Log.redPrint(encList.toString());
		Log.redPrint(decList.toString());
	}

	public static void main(String[] args) {

		Shuff shuff = Shuff.create();
		shuff.printShuffs();
		System.out.println(shuff.getEncDecString());

	}

}
