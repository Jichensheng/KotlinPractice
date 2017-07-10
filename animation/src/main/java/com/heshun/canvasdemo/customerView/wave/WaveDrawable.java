package com.heshun.canvasdemo.customerView.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.heshun.canvasdemo.customerView.tools.DimentionUtils;

/**
 * 弊端：无法实现
 * Drawable尺寸由包含它的view决定，对于ImageView来说宽高中有wrap_count的话使用自定义Drawable的严格默认大小，否则要结合拉伸模式
 * 设置为View的backgroun则要直接使用的是Drawable的严格默认大小
 * Think：要想手动定义大小需要自己要实现包裹view，比如继承ImageView的话使用此ImageView必须使用wrap_count 自定义大小才能生效
 * author：Jics
 * 2017/7/10 12:50
 */
public class WaveDrawable extends Drawable implements Animatable {
	private static final String TAG = "Jcs===";
	private static final float DEFAULT_WIDTH = 50.0f;
	private static final float DEFAULT_HEIGHT = 50.0f;
	private static final float DEFAULT_TEXT_SIZE = DEFAULT_WIDTH/2;

	private Paint mPaint;
	private Paint textPaint;
	private int mWidth;
	private int mHeight;
	private Path path;
	private float currentPercent;
	private int color;
	private String text;
	private int textSize;
	private ValueAnimator animator;
	private ValueAnimator.AnimatorUpdateListener listener;

	public WaveDrawable(Context context) {
		this(context, Color.rgb(41, 163, 254), "贴");
	}

	public WaveDrawable(Context context, int color) {
		this(context, color, "贴");
	}

	public WaveDrawable(Context context, String text) {
		this(context, Color.rgb(41, 163, 254), text);
	}

	public WaveDrawable(Context context, int color, String text) {
		init(context, color, text);
	}

	@Override
	public boolean setState(int[] stateSet) {
		return super.setState(stateSet);
	}

	private void init(Context context, int color, String text) {
		Log.e(TAG, "init: "+getBounds().toString());
		this.color = color;
		this.text = text;
		mWidth = DimentionUtils.dip2px(context, DEFAULT_WIDTH);
		mHeight = DimentionUtils.dip2px(context, DEFAULT_HEIGHT);
		textSize = DimentionUtils.dip2px(context, DEFAULT_TEXT_SIZE);
		//图形及路径填充画笔
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(color);
		mPaint.setDither(true);
		//文字画笔
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.WHITE);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		textPaint.setTextSize(textSize);
		//闭合波浪路径
		path = new Path();

		animator = ValueAnimator.ofFloat(0, 1);
		animator.setDuration(1000);
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.RESTART);
		listener=new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				currentPercent = animation.getAnimatedFraction();
				invalidateSelf();
			}
		};
		animator.addUpdateListener(listener);
		animator.start();
	}

	@Override
	public void draw(Canvas canvas) {
		Log.e(TAG, "draw: "+canvas.getWidth()+"      "+canvas.getHeight() );
		mWidth=canvas.getWidth();
		mHeight=canvas.getHeight();
		textSize=mWidth/2;
		textPaint.setTextSize(textSize);
		//底部的字
		textPaint.setColor(color);
		drawCenterText(canvas, textPaint, text);
		//上层的字
		textPaint.setColor(Color.WHITE);
//        canvas.save(Canvas.CLIP_SAVE_FLAG);
		//裁剪成圆形
		Path o = new Path();
		o.addCircle(mWidth / 2, mHeight / 2, mWidth / 2, Path.Direction.CCW);
		canvas.clipPath(o);
		//生成闭合波浪路径
		path = getActionPath(currentPercent);
		//画波浪
		canvas.drawPath(path, mPaint);
		//裁剪文字
		canvas.clipPath(path);
		drawCenterText(canvas, textPaint, text);
//        canvas.restore();
	}

	private Path getActionPath(float percent) {
		Path path = new Path();
		int x = -mWidth;
		//当前x点坐标（根据动画进度水平推移，一个动画周期推移的距离为一个mWidth）
		x += percent * mWidth;
		//波形的起点
		path.moveTo(x, mHeight / 2);
		//控制点的相对宽度
		int quadWidth = mWidth / 4;
		//控制点的相对高度
		int quadHeight = mHeight / 20 * 3;
		//第一个周期
		path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
		path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
		//第二个周期
		path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
		path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
		//右侧的直线
		path.lineTo(x + mWidth * 2, mHeight);
		//下边的直线
		path.lineTo(x, mHeight);
		//自动闭合补出左边的直线
		path.close();
		return path;
	}

	private void drawCenterText(Canvas canvas, Paint textPaint, String text) {
		Rect rect = new Rect(0, 0, mWidth, mHeight);
		textPaint.setTextAlign(Paint.Align.CENTER);

		Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
		float top = fontMetrics.top;
		float bottom = fontMetrics.bottom;

		int centerY = (int) (rect.centerY() - top / 2 - bottom / 2);

		canvas.drawText(text, rect.centerX(), centerY, textPaint);
	}


	/**
	 * 设置透明度
	 *
	 * @param alpha
	 */
	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	/**
	 * 设置颜色滤镜
	 *
	 * @param colorFilter
	 */
	@Override
	public void setColorFilter(ColorFilter colorFilter) {
		mPaint.setColorFilter(colorFilter);
	}

	/**
	 * 设置透明模式
	 *
	 * @return
	 */
	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	// Drawable不是控件，可以作为imageView的res或者view的背景，当依附对象大小为wrap_countent时使用这个
	@Override
	public int getIntrinsicHeight() {
		return mHeight;
	}

	// 获取默认的宽度wrap_countent时的默认
	@Override
	public int getIntrinsicWidth() {
		return mWidth;
	}

	@Override
	public void start() {
		animator.addUpdateListener(listener);
		animator.start();
	}

	@Override
	public void stop() {
		animator.removeUpdateListener(listener);
		animator.end();
	}

	@Override
	public boolean isRunning() {
		return animator.isRunning();
	}
}
