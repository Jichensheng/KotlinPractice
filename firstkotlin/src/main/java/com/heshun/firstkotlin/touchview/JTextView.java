package com.heshun.firstkotlin.touchview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * author：Jics
 * 2017/6/28 10:14
 */
public class JTextView extends TextView {
	private static final String TAG = "JTextView~~";

	public JTextView(Context context) {
		super(context);
	}

	public JTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public JTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean b=super.dispatchTouchEvent(event);
		Log.e(TAG, "dispatchTouchEvent: "+ b );
		return b;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean b=super.onTouchEvent(event);
		Log.e(TAG, "onTouchEvent: "+ b);
		return b;
	}

}
