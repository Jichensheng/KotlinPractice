package com.heshun.firstkotlin.tools;

/**
 * author：Jics
 * 2017/6/26 15:52
 */
public class Test {
	public static final int MAX = 100;
//e^x/(1+e^x)
	public static void main(String[] args) {
		for (int i = 0; i < MAX; i++) {
			int total='z'-'A';
			System.out.println(Character.valueOf((char) ((i%total)+'A')));
		}
	}

}
