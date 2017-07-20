package com.jcs.layoutmanagers;

/**
 * author：Jics
 * 2017/7/20 19:09
 */
public class Test {
	static String s="100|1,200|2,300|3,400|4";
	public static void main(String[] args){
		String[] ss=s.split("\\|");
		for (String s1 : ss) {
			System.out.println(s1);
		}
	}
}
