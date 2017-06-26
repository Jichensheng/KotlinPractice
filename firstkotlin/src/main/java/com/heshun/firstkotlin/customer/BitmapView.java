package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.heshun.firstkotlin.R;

/**
 * author：Jics
 * 2017/6/15 13:11
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BitmapView extends View {
	private static final int W = 64;
	private static final int H = 64;
	private static final int ROW_MAX = 4;   // number of samples per row

	private Bitmap mSrcB;
	private Bitmap mDstB;
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


	public BitmapView(Context context) {
		this(context, null);
	}

	public BitmapView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mSrcB = makeSrc(W, H);
		mDstB = makeDst(W, H);
		// 棋盘纹理
		Bitmap bm = Bitmap.createBitmap(getBlock(8, 8), 8, 8,
				Bitmap.Config.RGB_565);
		//BitmapShader的作用是使用特定的图片来作为纹理来使用
		mBG = new BitmapShader(bm,//指的是要作为纹理的图片
				Shader.TileMode.REPEAT,//指的是在ｘ方向纹理的绘制模式
				Shader.TileMode.REPEAT);//指的是Ｙ方向上的绘制模式

		/*Matrix m = new Matrix();
		m.setScale(6, 6);
		mBG.setLocalMatrix(m);*/
	}

	public static int[] getBlock(int width, int height) {
		int totalPiexl = width * height;
		int half = width / 2;
		int[] result = new int[totalPiexl];
		for (int i = 0; i < totalPiexl; i++) {
			if (i % height < half) {
				result[i] = (i / width) % height < half ? 0xFFFFFFFF : 0xFFCCCCCC;
			} else result[i] = (i / width) % height < half ? 0xFFCCCCCC : 0xFFFFFFFF;
		}
		return result;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(800, 500);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);

		Bitmap bitmap=getBitmapFromDrawable(getResources().getDrawable(R.drawable.ssss,null));
		int width=bitmap.getWidth();
		int height=bitmap.getHeight();
		BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

		Matrix matrix=new Matrix();
		matrix.postRotate(60,width/2,height/2);//先相对于自身中点旋转
		matrix.postTranslate(570-width/2,270-height/2);//相对于坐标系原点移动
		shader.setLocalMatrix(matrix);
		Paint shaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		shaderPaint.setShader(shader);
		canvas.drawCircle(570,270,width/2,shaderPaint);
		canvas.drawRect(320,10,320+width,50,shaderPaint);
		canvas.drawArc(400+width,10,400+width*2,width,90,130,true,shaderPaint);
		shaderPaint.setStrokeWidth(50);
		canvas.drawLine(0,0,600,600,shaderPaint);

		Paint border=new Paint(Paint.ANTI_ALIAS_FLAG);
		border.setStrokeWidth(3);
		border.setColor(Color.RED);
		border.setStyle(Paint.Style.STROKE);
		border.setDither(true);
		canvas.drawCircle(570,270,width/2+5,border);



		Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
		labelP.setTextAlign(Paint.Align.CENTER);

		Paint paint = new Paint();
		paint.setFilterBitmap(false);

		canvas.translate(15, 35);

		int x = 0;
		int y = 0;
		for (int i = 0; i < sModes.length; i++) {
			// 画棋盘边界
			paint.setStyle(Paint.Style.STROKE);
			paint.setShader(null);//使用纹理填充
			canvas.drawRect(x - 0.5f, y - 0.5f,
					x + W + 0.5f, y + H + 0.5f, paint);

			// 用shader填充棋盘
			paint.setStyle(Paint.Style.FILL);
			paint.setShader(mBG);//使用纹理填充
			canvas.drawRect(x, y, x + W, y + H, paint);

			// draw the src/dst example into our offscreen bitmap
			int sc = canvas.saveLayer(x, y, x + W, y + H, null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
			canvas.translate(x, y);//平移画布只影响save-restore之内的
			canvas.drawBitmap(mDstB, 0, 0, paint);
			paint.setXfermode(sModes[i]);
			canvas.drawBitmap(mSrcB, 0, 0, paint);
			paint.setXfermode(null);
			canvas.restoreToCount(sc);

			// draw the label
			canvas.drawText(sLabels[i], x + W / 2, y - labelP.getTextSize() / 2, labelP);

			x += W + 10;
			// wrap around when we've drawn enough for one row
			if ((i % ROW_MAX) == ROW_MAX - 1) {
				x = 0;
				y += H + 30;
			}
		}
	}


	// create beeline bitmap with beeline circle, used for the "dst" image
	static Bitmap makeDst(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFFFFCC44);
		c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
		return bm;
	}

	// create beeline bitmap with beeline rect, used for the "src" image
	static Bitmap makeSrc(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFF66AAFF);
		c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
		return bm;
	}

	/**
	 * drawable转bitmap
	 *
	 * @param drawable
	 * @return
	 */
	private Bitmap getBitmapFromDrawable(Drawable drawable) {
		int COLORDRAWABLE_DIMENSION = 1;
		int DEFAULT_BORDER_WIDTH = 0;
		Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
		if (drawable == null) {
			return null;
		}

		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		try {
			Bitmap bitmap;

			if (drawable instanceof ColorDrawable) {
				bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
			} else {
				bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
						BITMAP_CONFIG);
			}

			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}
}
