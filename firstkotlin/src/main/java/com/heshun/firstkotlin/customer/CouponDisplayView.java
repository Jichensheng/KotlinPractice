package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.heshun.firstkotlin.R;

/**
 * author：Jics
 * 2017/6/14 13:09
 */
public class CouponDisplayView extends LinearLayout {
	private static final String TAG = "mDebug";
	private Paint mCirclePaint;
	private Paint mBackPaint;
	private Bitmap bitmap;
	//圆半径
	private float radius = 10;
	//圆间距
	private float gap = 8;
	//圆数量
	private int circleNum;
	//圆球排完是否有剩余
	private float remain;


	public CouponDisplayView(Context context) {
		this(context, null);
		Log.d(TAG, "CouponDisplayView: context");
	}

	public CouponDisplayView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		Log.d(TAG, "CouponDisplayView: contex,attrs");
	}

	public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setWillNotDraw(false);//强制画
		Log.d(TAG, "CouponDisplayView: context,attrs,defStyleAttr");
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CouponDisplayView, defStyleAttr, 0);
		for (int i = 0; i < a.getIndexCount(); i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.CouponDisplayView_radius:
					radius = a.getDimensionPixelSize(R.styleable.CouponDisplayView_radius, 10);
					break;
				case R.styleable.CouponDisplayView_gap:
					gap = a.getDimensionPixelSize(R.styleable.CouponDisplayView_gap, 8);
					break;
			}
		}
		a.recycle();

		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setDither(true);
		mCirclePaint.setColor(Color.WHITE);
		mCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
		mCirclePaint.setStyle(Paint.Style.FILL);

		mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBackPaint.setDither(true);
		mBackPaint.setColor(Color.BLUE);
		mBackPaint.setStyle(Paint.Style.FILL);

		bitmap = Bitmap.createBitmap(500, 300, Bitmap.Config.ARGB_8888);
		bitmap.eraseColor(Color.parseColor("#FF0000"));//填充颜色

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.d(TAG, "onSizeChanged: w=" + w + ",h=" + h + ",oldw=" + oldw + ",oldh=" + oldh);
		if (remain == 0) {
			remain = (int) (w - gap) % (2 * radius + gap);
		}
		circleNum = (int) ((w - gap) / (2 * radius + gap));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw");
		canvas.drawRect(0,0,getWidth(),getHeight(), mBackPaint);
		for (int i = 0; i < circleNum; i++) {
			//x坐标
			float x = gap + radius + remain / 2 + ((gap + radius * 2) * i);
			canvas.drawCircle(x, 0, radius, mCirclePaint);
			canvas.drawCircle(x, getHeight(), radius, mCirclePaint);
		}
	}
}
