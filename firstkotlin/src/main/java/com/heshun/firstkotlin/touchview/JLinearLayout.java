package com.heshun.firstkotlin.touchview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * author：Jics
 * 2017/6/28 10:13
 */
public class JLinearLayout extends LinearLayout {
	private static final String TAG = "JLinearLayout~~";

	public JLinearLayout(Context context) {
		super(context);
	}

	public JLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public JLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean b = super.dispatchTouchEvent(ev);
		Log.e(TAG, "dispatchTouchEvent: " + b);
		return b;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean b=super.onInterceptTouchEvent(ev);
		Log.e(TAG, "onInterceptTouchEvent: "+b);
		return b;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean b=super.onTouchEvent(event);
		Log.e(TAG, "onTouchEvent: "+b);
		Log.e(TAG, "onTouchEvent: 我自己处理" );
		return b;
	}
}
