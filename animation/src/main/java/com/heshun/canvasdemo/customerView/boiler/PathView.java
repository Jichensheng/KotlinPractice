package com.heshun.canvasdemo.customerView.boiler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * author：Jics
 * 2017/7/3 17:55
 */
public class PathView extends View {
	private Path path;
	private float persent;
	private Paint pathPaint;
	private Paint pointPaint;

	public PathView(Context context) {
		this(context, null);
	}

	public PathView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		path = new Path();
		path.moveTo(100, 100);
		path.quadTo(130, 100, 200, 500);
		path.lineTo(200, 200);
		pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pathPaint.setStyle(Paint.Style.STROKE);
		pathPaint.setStrokeWidth(2);
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setStyle(Paint.Style.FILL);
		pointPaint.setColor(Color.RED);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
						persent += 0.01;
						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		float[] point = AnimatorUtils.getPathPoint(path, (float) ((int) (persent * 100) % 100) / 100);
		canvas.drawPath(path, pathPaint);
		canvas.drawCircle(point[0], point[1], 5, pointPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(600, 600);
	}
}
