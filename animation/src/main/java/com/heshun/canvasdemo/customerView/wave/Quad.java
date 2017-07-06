package com.heshun.canvasdemo.customerView.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * author：Jics
 * 2017/7/6 16:13
 */
public class Quad extends View {
	private Path path;
	private Paint paint;
	private float currentX;
	private float persent;

	public Quad(Context context) {
		this(context, null);
	}

	public Quad(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Quad(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		path = new Path();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.rgb(41, 163, 254));
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
		valueAnimator.setDuration(1000);
		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.setRepeatMode(ValueAnimator.RESTART);
		valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				persent = animation.getAnimatedFraction();
				postInvalidate();
			}
		});
		valueAnimator.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		path.reset();
		path = getActionPath(persent);
		canvas.drawPath(path, paint);

	}

	private Path getActionPath(float persent) {
		Path path = new Path();
		int x = -200;
		x += persent * 200;
		x = x == -1 ? 0 : x;
		path.moveTo(x, 200);
		path.rQuadTo(50, 30, 100, 0);
		path.rQuadTo(50, -30, 100, 0);
		path.rQuadTo(50, 30, 100, 0);
		path.rQuadTo(50, -30, 100, 0);
		path.lineTo(x+400,380);
		path.lineTo(x,380);
		path.close();
		return path;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(1000, 400);
	}
}
