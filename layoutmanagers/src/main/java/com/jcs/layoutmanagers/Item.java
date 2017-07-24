package com.jcs.layoutmanagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * author：Jics
 * 2017/7/21 13:53
 */
public class Item extends LinearLayout {
	private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
	//	private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
	private Paint paint;
	private Canvas bitCanvas;
	private Bitmap bitmap;
	private Paint dashPaint;

	public Item(Context context) {
		this(context, null);
	}

	public Item(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Item(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL);

		dashPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dashPaint.setColor(Color.RED);
		dashPaint.setStyle(Paint.Style.STROKE);
		dashPaint.setStrokeWidth(2);
		Path path = new Path();
		path.addCircle(0, 0, 2, Path.Direction.CCW);
//		dashPaint.setPathEffect(new PathDashPathEffect(path, 15, 0, PathDashPathEffect.Style.ROTATE));
		//dashPaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));
//		dashPaint.setPathEffect(new DiscretePathEffect(15,5));
		dashPaint.setStrokeCap(Paint.Cap.ROUND);
		dashPaint.setXfermode(xfermode);

		setWillNotDraw(false);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		bitmap = makeHoleBitmap(w, h);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		bitmap = makeHoleBitmap(getMeasuredWidth(), getMeasuredHeight());
	}

	/**
	 * 镂空bitmap
	 *
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap makeHoleBitmap(int width, int height) {
		PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);

		//---虚线画笔
		Paint dashPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dashPaint.setColor(Color.TRANSPARENT);
		dashPaint.setStyle(Paint.Style.STROKE);
		dashPaint.setStrokeWidth(2);
		Path path = new Path();
		path.addCircle(0, 0, 4, Path.Direction.CCW);
		//圆点 间距 偏移量
//		dashPaint.setPathEffect(new PathDashPathEffect(path, 30, 0, PathDashPathEffect.Style.ROTATE));
		//线
//		dashPaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));
//		dashPaint.setStrokeCap(Paint.Cap.ROUND);
        //毛刺
        dashPaint.setPathEffect(new DiscretePathEffect(1,11));
        //组合毛刺
//        dashPaint.setPathEffect(new ComposePathEffect(new DiscretePathEffect(1,11),new DiscretePathEffect(11,21)));
        //不懂
//        dashPaint.setPathEffect(new CornerPathEffect(30));
		dashPaint.setXfermode(xfermode);

		//背景画笔
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL);
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas bitCanvas = new Canvas(bitmap);
		bitCanvas.drawColor(Color.rgb(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255)));//先画背景色
		paint.setXfermode(xfermode);
		paint.setColor(Color.TRANSPARENT);

		Path dashPath = new Path();
		dashPath.moveTo(0, bitCanvas.getHeight() / 2);
        dashPath.lineTo(bitCanvas.getWidth() / 3, bitCanvas.getHeight()*0.8f);
		dashPath.quadTo(bitCanvas.getWidth() / 3*2, bitCanvas.getHeight()*1.32F, bitCanvas.getWidth(), bitCanvas.getHeight() / 2);
		bitCanvas.drawPath(dashPath, dashPaint);
		bitCanvas.drawCircle(bitCanvas.getWidth() / 3, bitCanvas.getHeight()*0.32F,50,dashPaint);
		bitCanvas.drawCircle(bitCanvas.getWidth() / 3*2, bitCanvas.getHeight()*0.32F,50,dashPaint);

		paint.setTextSize(40);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextAlign(Paint.Align.CENTER);
		dashPath.reset();
		dashPath.moveTo(0, bitCanvas.getHeight() / 2);
        dashPath.quadTo(bitCanvas.getWidth() / 2, -bitCanvas.getHeight()*0.32F, bitCanvas.getWidth(), bitCanvas.getHeight() / 2);
		bitCanvas.drawTextOnPath("践行社会主义核心价值观",dashPath,0,11,paint);
		for (int i = 0; i < 30; i++) {
			bitCanvas.drawCircle(40 * i, 0, 20, paint);
			bitCanvas.drawCircle(40 * i, bitCanvas.getHeight(), 20, paint);
			bitCanvas.drawCircle(0, 40 * (i + 1), 20, paint);
			bitCanvas.drawCircle(bitCanvas.getWidth(), 40 * (i + 1), 20, paint);
		}
		return bitmap;
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		setLayerType(LAYER_TYPE_SOFTWARE, null);//drawline关闭硬件加速能画虚线
		int save = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
//		paint.setColor(Color.GREEN);
		/*	paint.setXfermode(null);
			paint.setColor(Color.LTGRAY);
			bitCanvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
			canvas.drawBitmap(bitmap, 0, 0, paint);*/
//			canvas.drawColor(Color.LTGRAY);
//		paint.setXfermode(xfermode);
//			paint.setColor(Color.TRANSPARENT);
			/*Path path=new Path();
			path.moveTo(0,canvas.getHeight()/2);
			path.quadTo(canvas.getWidth()/2,canvas.getHeight(),canvas.getWidth(),canvas.getHeight()/2);
			canvas.drawPath(path,dashPaint);*/
		canvas.drawBitmap(bitmap, 0, 0, paint);
		/*	for (int i = 0; i < 30; i++) {
				canvas.drawCircle(40*i, 0, 20, paint);
				canvas.drawCircle(40*i, canvas.getHeight(), 20, paint);
				canvas.drawCircle(0, 40*(i+1), 20, paint);
				canvas.drawCircle(canvas.getWidth(), 40*(i+1), 20, paint);
			}*/
		canvas.restoreToCount(save);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();
                /* Raise view on ACTION_DOWN and lower it on ACTION_UP. */
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				setTranslationZ(120);
				break;
			case MotionEvent.ACTION_UP:
				setTranslationZ(0);
				break;
			default:
				return false;
		}
		return super.onTouchEvent(event);
	}
}
