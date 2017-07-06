package com.heshun.canvasdemo.customerView.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * author：Jics
 * 2017/7/6 13:06
 */
public class Wave extends View {
	private Paint mPaint;
	private Paint textPaint;
	private Canvas mCanvas;//我们自己的画布
	private Bitmap mBitmap;
	private int mWidth = 500;
	private int mHeight = 500;
	private Path path;
	private float currentPersent;
private int currentX=-200;
	public Wave(Context context) {
		this(context, null);
	}

	public Wave(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Wave(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Color.rgb(41, 163, 254));
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(80);

		path = new Path();

		mBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888); //生成一个bitmap
		mCanvas = new Canvas(mBitmap);//讲bitmp放在我们自己的画布上，实际上mCanvas.draw的时候 改变的是这个bit
		ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
		animator.setDuration(1000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.RESTART);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				currentPersent=animation.getAnimatedFraction();
				postInvalidate();
			}
		});
		animator.start();
	}


	@Override
	protected void onDraw(Canvas canvas) {
		textPaint.setColor(Color.rgb(41, 163, 254));
		canvas.drawText("季", 60, 130, textPaint);
		textPaint.setColor(Color.WHITE);
		//裁剪成圆形
		Path o = new Path();
		o.addCircle(100, 100, 100, Path.Direction.CCW);
		mCanvas.clipPath(o);
		//重新生成路径
		path=getActionPath(currentPersent);
		//清除上次内容
		mBitmap.eraseColor(Color.parseColor("#00000000"));
		mCanvas.drawPath(path, mPaint);
		//裁剪文字
		mCanvas.save(Canvas.CLIP_SAVE_FLAG);
		mCanvas.clipPath(path);
		mCanvas.drawText("季", 60, 130, textPaint);
		mCanvas.restore();

		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (widthMode == MeasureSpec.EXACTLY) {
			mWidth = widthSize;
		}
		if (heightMode == MeasureSpec.EXACTLY) {
			mHeight = heightSize;
		}
		setMeasuredDimension(mWidth, mHeight);

	}
	private Path getActionPath(float persent) {
		Path path = new Path();
		currentX = (int) (-200+ persent * 200);
		currentX = currentX == -1 ? -200 : currentX;
		path.moveTo(currentX, 100);
		System.out.println(currentX);
		path.rQuadTo(50, 30, 100, 0);
		path.rQuadTo(50, -30, 100, 0);
		path.rQuadTo(50, 30, 100, 0);
		path.rQuadTo(50, -30, 100, 0);
		path.lineTo(currentX+400,200);
		path.lineTo(currentX,200);
		path.close();
		return path;
	}
}
