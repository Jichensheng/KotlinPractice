package com.heshun.firstkotlin;

import java.util.Arrays;

/**
 * author：Jics
 * 2017/6/15 14:08
 */
public class Test {
	public static void main(String[] args) {
		int[] i = getBlock(6, 6);
		System.out.println(Arrays.toString(i));
	}

	public static int[] getBlock(int width, int height) {
		int totalPiexl = width * height;
		int half = width / 2;
		int[] result = new int[totalPiexl];
		for (int i = 0; i < totalPiexl; i++) {
			if (i%height<half) {
				result[i] = 0xFFFFFFFF;
			} else result[i] = 0xFFCCCCCC;
		}
		return result;
	}
	public static float bytes2IEEE754(byte b1,byte b2 ,byte b3,byte b4){


		return 1f;
	}
}
