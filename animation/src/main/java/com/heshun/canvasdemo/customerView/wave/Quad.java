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
	private Paint bgPaint;
	private Paint textPaint;
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
		paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
		paint.setColor(Color.rgb(41, 163, 254));

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(0xecf9ff);

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(70);
        textPaint.setColor(Color.BLACK);
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

        bgPaint.setColor(Color.rgb(255,214,117));
        canvas.drawRect(0,100,200,300,bgPaint);
		canvas.drawPath(path, paint);
        canvas.drawLine(400,100,400,300,paint);
        /*canvas.drawLine(-200,230,200,230,paint);
        canvas.drawLine(-200,170,200,170,paint);


        canvas.drawText(text,600,200,textPaint);
        canvas.drawCircle(600,200,3,paint);
        canvas.translate(600, 200);
        Rect bgRect=new Rect(0,0,1000,400);
        canvas.drawRect(bgRect,bgPaint);

        Rect textBound=new Rect();
        textPaint.getTextBounds(text,0,text.length(),textBound);
        paint.setColor(Color.RED);
        canvas.drawRect(textBound,paint);

        Paint.FontMetrics metrics=textPaint.getFontMetrics();
        paint.setColor(Color.RED);
        // ascent 橙色
        paint.setColor(Color.rgb(255,126,0));
        canvas.drawLine(0, metrics.ascent, 500,metrics.ascent, paint);
        // descent
        paint.setColor(Color.rgb(255,0,234));
        canvas.drawLine(0, metrics.descent, 500, metrics.descent, paint);
        // top
        paint.setColor(Color.DKGRAY);
        canvas.drawLine(0, metrics.top, 500, metrics.top, paint);
        // bottom
        paint.setColor(Color.GREEN);
        canvas.drawLine(0, metrics.bottom, 500, metrics.bottom, paint);*/
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
		return path;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(1000, 400);
	}
}
