package com.heshun.canvasdemo.customerView.boiler;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.heshun.canvasdemo.R;
import com.heshun.canvasdemo.customerView.interfacePack.OnWaterFullListener;
import com.heshun.canvasdemo.customerView.tools.DimentionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 动画控制部分是水是否满了、进度生成线程、以及属性动画
 * author：Jics
 * 2017/7/3 13:55
 */
public class WaterView extends View {
	private static final int MAX_RADIUS = 15;

	private Bitmap bitWater;
	private Rect rectSrc;
	private Paint mPaint;
	private int mHeight = 0;

	private boolean isWaterFull = false;//水满了
	private ObjectAnimator animator;
	private float persent = 0;

	private List<Bubble> bubbles = initBubble(DimentionUtils.dip2px(getContext(),70), DimentionUtils.dip2px(getContext(),190), 20);

	private OnWaterFullListener waterFullListener;

	public WaterView(Context context) {
		this(context, null);
	}

	public WaterView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		bitWater = BitmapFactory.decodeResource(getResources(), R.drawable.boilerwater).copy(Bitmap.Config.ARGB_8888, true);
		rectSrc = new Rect(0, 0, bitWater.getWidth(), bitWater.getHeight());
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setDither(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(80);
						persent += 0.1;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void setOnWaterFullListener(OnWaterFullListener waterFullListener) {
		this.waterFullListener = waterFullListener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		rectSrc.set(0, mHeight, bitWater.getWidth(), bitWater.getHeight());

		if (!isWaterFull) {
			canvas.drawBitmap(bitWater, rectSrc, rectSrc, mPaint);
		} else {
			canvas.drawBitmap(bitWater, 0, 0, mPaint);
		}

		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);
		drawBubble(canvas, rectSrc, bubbles);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(bitWater.getWidth(), bitWater.getHeight());
	}

	public void startAnimator() {
		animator = ObjectAnimator.ofFloat(this, "height", 0f, 1f);
		animator.setDuration(1500).start();
	}

	/**
	 * 负责第一次裁剪水
	 *
	 * @param persent
	 */
	private void setHeight(float persent) {
		mHeight = (int) (bitWater.getHeight() * (1 - persent));
		if (persent > 0.99) {
			if (!isWaterFull) {//第一次水满的时候通知
				waterFullListener.waterFull();
			}
			isWaterFull = true;
		}
		invalidate();
	}

	private List<Bubble> initBubble(int farLeft, int farRight, int bubbleCount) {
		List<Bubble> bubbles = new ArrayList<>();
		//TODO 总宽度和半径大小关系
		for (int i = 0; i < bubbleCount; i++) {
			//随机x坐标
			int x = MAX_RADIUS / 2 + farLeft + new Random().nextInt(farRight - farLeft - MAX_RADIUS / 2);
			//随机偏移量
			float offset = new Random().nextFloat();//进度偏移量
			bubbles.add(new Bubble(x, 0, 0, offset));

		}
		return bubbles;
	}

	private void drawBubble(Canvas canvas, Rect rectSrc, List<Bubble> bubbles) {
		if (isWaterFull) {
			for (Bubble bubble : bubbles) {
				int x = bubble.getX();
				float offset = bubble.getOffset();
				float progress = (float) (((int) ((persent + offset) * 100)) % 100) / 100;
				int y = bitWater.getHeight() - (int) (bitWater.getHeight() * progress) + MAX_RADIUS;
				int radius = (int) (MAX_RADIUS * progress);
				canvas.drawCircle(x, y, radius, mPaint);
			}
			invalidate();
		} else {
			canvas.save(Canvas.CLIP_SAVE_FLAG);
			canvas.clipRect(rectSrc);
			for (Bubble bubble : bubbles) {
				int x = bubble.getX();
				float offset = bubble.getOffset();
				float progress = (float) (((int) ((persent + offset) * 100)) % 100) / 100;
				int y = bitWater.getHeight() - (int) ((bitWater.getHeight()) * progress) - MAX_RADIUS;
				int radius = (int) (MAX_RADIUS * progress);
				canvas.drawCircle(x, y, radius, mPaint);
			}
			canvas.restore();
		}

	}

}
