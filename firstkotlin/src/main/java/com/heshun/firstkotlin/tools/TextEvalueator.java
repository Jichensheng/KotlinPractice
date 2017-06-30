package com.heshun.firstkotlin.tools;

import android.animation.TypeEvaluator;

/**
 * author：Jics
 * 2017/6/30 16:39
 */
public class TextEvalueator implements TypeEvaluator<Integer> {
	@Override
	public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
		int total=endValue-startValue;

		return (int)(fraction*100)%total+ 'A';
	}
}
