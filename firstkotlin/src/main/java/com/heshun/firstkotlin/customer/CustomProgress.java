package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * author：Jics
 * 2017/6/29 15:26
 */
public class CustomProgress extends ProgressBar {
	public CustomProgress(Context context) {
		super(context);
	}

	public CustomProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		RectF rectF=new RectF();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
