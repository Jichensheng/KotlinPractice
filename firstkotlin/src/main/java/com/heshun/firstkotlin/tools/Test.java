package com.heshun.firstkotlin.tools;

/**
 * author：Jics
 * 2017/6/26 15:52
 */
public class Test {
	public static final int MAX = 1000;
//e^x/(1+e^x)
	public static void main(String[] args) {
		for (double i = 1; i < 1000; i++) {
			double dd=Math.pow(1.01,i);
			System.out.println(dd/(1+dd));
		}
	}
	public static float getS(int current){
		double dd=Math.pow(1.01,current);
		return (float) (dd/(1+dd));
	}
}
