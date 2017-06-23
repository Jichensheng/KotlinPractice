package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.heshun.firstkotlin.R;

/**
 * author：Jics
 * 2017/6/23 13:56
 */
public class MokeFliker extends View {


	private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
	private Bitmap pgBitmap;//空内容的bitmap，用于生成canvas
	private Bitmap flikerBitmap;//高光素材图
	private Canvas pgCanvas;
	BitmapShader bitmapShader;
	private Paint pgPaint,textPaint;
	private RectF bgRectf;



	public MokeFliker(Context context) {
		this(context, null);
	}

	public MokeFliker(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MokeFliker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(36);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pgPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(Color.BLACK);
		canvas.drawText("社会主义核心价值观",400,dp2px(22),textPaint);
		//用bitmap作为构造参数创建的canvas，所有的操作都是画在那个bitmap上的
		pgCanvas.save(Canvas.CLIP_SAVE_FLAG);
		pgCanvas.clipRect(0, 0, 600, getMeasuredHeight());
		pgCanvas.drawColor(new Color().rgb(10, 159, 228));
		textPaint.setColor(Color.WHITE);
		pgCanvas.drawText("社会主义核心价值观",400,dp2px(22),textPaint);
		pgCanvas.restore();

		pgPaint.setXfermode(xfermode);
		pgCanvas.drawBitmap(flikerBitmap, 50, 0, pgPaint);
		pgPaint.setXfermode(null);
		//以上代码目的只有一个，在bitmap上绘图

		//绘制矩形的
//		canvas.drawBitmap(pgBitmap, 0, 0, pgPaint);
		//以下代码的作用是绘制一个具有圆角效果的图形
		bitmapShader = new BitmapShader(pgBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);//clamp了为什么没按照边缘颜色扩充
		pgPaint.setShader(bitmapShader);
		bgRectf=new RectF(0,0,getMeasuredWidth(),getMeasuredHeight());
		canvas.drawRoundRect(bgRectf, 10, 10, pgPaint);


	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		int height = 0;
		switch (heightSpecMode) {
			case MeasureSpec.AT_MOST:
				height = dp2px(36);
				break;
			case MeasureSpec.EXACTLY:
			case MeasureSpec.UNSPECIFIED:
				height = heightSpecSize;
				break;
		}
		setMeasuredDimension(widthSpecSize, height);

		pgBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		flikerBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flicker);
		pgCanvas = new Canvas(pgBitmap);

	}


	private int dp2px(int dp) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dp * density);
	}
}
