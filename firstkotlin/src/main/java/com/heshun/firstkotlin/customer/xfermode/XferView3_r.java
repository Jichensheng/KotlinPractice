package com.heshun.firstkotlin.customer.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * 两个bitmap内容完全被填充填充且对齐
 * author：Jics
 * 2017/7/24 11:06
 */
public class XferView3_r extends View {
	private static final int W = 64;
	private static final int H = 64;
	private static final int ROW_MAX = 4;   // number of samples per row

	private Bitmap mRectB;
	private Bitmap mCircleB;
	private Shader mBG;     // background checker-board pattern

	private static final Xfermode[] sModes = {
			new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
			new PorterDuffXfermode(PorterDuff.Mode.SRC),
			new PorterDuffXfermode(PorterDuff.Mode.DST),
			new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
			new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
			new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
			new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
			new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
			new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
			new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
			new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
			new PorterDuffXfermode(PorterDuff.Mode.XOR),
			new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
			new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
			new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
			new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
	};

	private static final String[] sLabels = {
			"Clear", "Src", "Dst", "SrcOver",
			"DstOver", "SrcIn", "DstIn", "SrcOut",
			"DstOut", "SrcATop", "DstATop", "Xor",
			"Darken", "Lighten", "Multiply", "Screen"
	};


	// create beeline bitmap with beeline circle, used for the "dst" image
	static Bitmap makeCircle(int w, int h) {
		Bitmap bm = Bitmap.createBitmap((int) (w*0.7f), (int) (h*0.7f), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFF04a923);
//		c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
		c.drawCircle(w*0.7f / 2, h*0.7f / 2, w*0.7f / 2, p);
		return bm;
	}

	// create beeline bitmap with beeline rect, used for the "src" image
	static Bitmap makeRect(int w, int h) {
		Bitmap bm = Bitmap.createBitmap((int) (w*0.7f), (int) (h*0.7f), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFFff5b22);
		c.drawRect(0, 0, w*0.7f, h*0.7f, p);
//		c.drawRect(w/3, h/3, w*19/20, h*19/20, p);
		return bm;
	}

	public XferView3_r(Context context) {
		super(context);

		init();
	}

	public XferView3_r(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mRectB = makeRect(W, H);
		mCircleB = makeCircle(W, H);

		// make beeline ckeckerboard pattern
		Bitmap bm = Bitmap.createBitmap(new int[]{0xFFFFFFFF, 0xFFCCCCCC,
						0xFFCCCCCC, 0xFFFFFFFF}, 2, 2,
				Bitmap.Config.RGB_565);
		//BitmapShader的作用是使用特定的图片来作为纹理来使用
		mBG = new BitmapShader(bm,//指的是要作为纹理的图片
				Shader.TileMode.REPEAT,//指的是在ｘ方向纹理的绘制模式
				Shader.TileMode.REPEAT);//指的是Ｙ方向上的绘制模式
		//2*2的方格太小了，在此用matrix扩展成6*6的
		Matrix m = new Matrix();
		m.setScale(6, 6);
		mBG.setLocalMatrix(m);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);

		Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
		labelP.setTextAlign(Paint.Align.CENTER);

		Paint paint = new Paint();
		paint.setFilterBitmap(false);

		canvas.translate(15, 35);

		int x = 0;
		int y = 0;
		for (int i = 0; i < sModes.length; i++) {
			// draw the border
			paint.setStyle(Paint.Style.STROKE);
			paint.setShader(null);//防止上个循环的shader影响到本次
			canvas.drawRect(x - 0.5f, y - 0.5f,
					x + W + 0.5f, y + H + 0.5f, paint);

			// draw the checker-board pattern
			paint.setStyle(Paint.Style.FILL);
			paint.setShader(mBG);
			canvas.drawRect(x, y, x + W, y + H, paint);

			// draw the src/dst example into our offscreen bitmap
			int sc = canvas.saveLayer(x, y, x + W, y + H, null,
					Canvas.MATRIX_SAVE_FLAG |
							Canvas.CLIP_SAVE_FLAG |
							Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
							Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
							Canvas.CLIP_TO_LAYER_SAVE_FLAG);
			canvas.translate(x, y);
			//先画的是目的dst
			canvas.drawBitmap(mCircleB, 0, 0, paint);//圆
			paint.setXfermode(sModes[i]);
			//后画的是源src
			canvas.drawBitmap(mRectB, 0,0, paint);//方
			paint.setXfermode(null);
			canvas.restoreToCount(sc);

			// draw the label
			canvas.drawText(sLabels[i],
					x + W / 2, y - labelP.getTextSize() / 2, labelP);

			x += W + 10;//绘制完成后把x的左边变一变，因为每次restore后都从0开始

			// wrap around when we've drawn enough for one row
			if ((i % ROW_MAX) == ROW_MAX - 1) {
				x = 0;
				y += H + 30;
			}
		}
	}
}
