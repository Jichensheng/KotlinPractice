package com.heshun.canvasdemo.customerView.drawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;

public class CustomDrawable extends Drawable implements Animatable {

    private static final float DEFAULT_WIDTH = 50.0f;

    private static final float DEFAULT_HEIGHT = 50.0f;

    private static final float DEFAULT_LINE_WIDTH = 2.0f;

    private static final float DEFAULT_BALL_RADIUS = 10.0f;

    private static final float DEFAULT_POINT_RADIUS = 3.0f;

    private static final float DEFAULT_MAX_ANGLE = (float) (2 * Math.PI / 24.0f);

    private static final int DEFAULT_DURATION = 2000;

    private static final int DEFAULT_COLOR = Color.RED;

    private float mWidth;

    private float mHeight;

    private float mMaxAngle;

    private float mLineWidth;

    private float mBallRadius;

    private float mFixedPointRadius;

    private float mLineLength;

    private float mCurrentX;

    private float mCurrentY;

    private int mDuration;

    private Paint mPaint;

    private ValueAnimator mAnimator;

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;

    public CustomDrawable(Context context) {
        // 设置长宽
        mWidth = dp2px(context, DEFAULT_WIDTH);
        mHeight = dp2px(context, DEFAULT_HEIGHT);
        // 线的粗细
        mLineWidth = dp2px(context, DEFAULT_LINE_WIDTH);
        // 球的半径
        mBallRadius = dp2px(context, DEFAULT_BALL_RADIUS);
        // 固定点的半径
        mFixedPointRadius = dp2px(context, DEFAULT_POINT_RADIUS);
        // 线的长度
        mLineLength = dp2px(context, DEFAULT_WIDTH * 0.75f);
        // 设置最大角度
        mMaxAngle = DEFAULT_MAX_ANGLE;
        // 设置周期
        mDuration = DEFAULT_DURATION;
        // 设置属性动画参数
        mAnimator = new ValueAnimator();
        mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(DEFAULT_DURATION);
        mAnimator.setDuration(mDuration);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setInterpolator(new LinearInterpolator());
        // 设置画笔参数
        mPaint = new Paint();
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setAntiAlias(true);
        // 设置动画的回调
        mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                calculate((float) animation.getAnimatedValue());
                invalidateSelf();
            }
        };
    }

    @Override
    public void start() {
        mAnimator.addUpdateListener(mAnimatorUpdateListener);
        mAnimator.start();
    }

    @Override
    public void stop() {
        mAnimator.removeUpdateListener(mAnimatorUpdateListener);
        mAnimator.end();
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int saveCount = canvas.save();
        float centerX = mWidth / 2.0f;
        float centerY = 0;
        // 画固定点
        mPaint.setStrokeWidth(mFixedPointRadius * 2);
        RectF pointRectF = new RectF(centerX - mFixedPointRadius, centerY - mFixedPointRadius, centerX + mFixedPointRadius, centerY + mFixedPointRadius);
        canvas.drawArc(pointRectF, 0, 360, true, mPaint);
        // 画线
        mPaint.setStrokeWidth(mLineWidth);
        canvas.drawLine(mWidth / 2.0f, 0, mCurrentX, mCurrentY, mPaint);
        // 画小球
        RectF ballRectF = new RectF(mCurrentX - mBallRadius, mCurrentY - mBallRadius, mCurrentX + mBallRadius, mCurrentY + mBallRadius);
        mPaint.setStrokeWidth(mBallRadius * 2);
        canvas.drawArc(ballRectF, 0, 360, true, mPaint);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    // 获取默认的高度
    @Override
    public int getIntrinsicHeight() {
        return (int) mHeight;
    }

    // 获取默认的宽度
    @Override
    public int getIntrinsicWidth() {
        return (int) mWidth;
    }

    public float dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    private void calculate(float animatedValue) {
        float currentAngle = (float) (-Math.cos(animatedValue * Math.PI * 2) * mMaxAngle);
        mCurrentX = (float) (mWidth / 2.0f + mLineLength * Math.sin(currentAngle));
        mCurrentY = (float) (mLineLength * Math.cos(currentAngle));
    }
}