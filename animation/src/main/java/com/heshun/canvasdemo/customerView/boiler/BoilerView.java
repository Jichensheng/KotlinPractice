package com.heshun.canvasdemo.customerView.boiler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.heshun.canvasdemo.R;

/**
 * Created by Jcs on 2017/7/2.
 * rgb(255,206,148) #ffce94
 */

public class BoilerView extends RelativeLayout {
    private static final int ANIMATOR_DURATION = 300;
    private ImageView body;
    private ImageView cover;
    private FireView fireView;
    private Paint circlePaint;

    private boolean isCoverShake=false;

    public BoilerView(Context context) {
        this(context, null);
    }

    public BoilerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoilerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(final Context context, AttributeSet attrs) {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.rgb(255, 206, 148));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setDither(true);
        View view = LayoutInflater.from(context).inflate(R.layout.boiler_xxdpi, this);
        /*view.setScaleX(0.5f);
        view.setScaleY(0.5f);*/
        body = (ImageView) view.findViewById(R.id.iv_body);
        cover = (ImageView) view.findViewById(R.id.iv_cover);
        fireView = (FireView) view.findViewById(R.id.iv_fire);
        fireView.setScaleX(1.3f);
        fireView.setScaleY(1.3f);

        Path path = new Path();
        path.moveTo(20, 50);
        path.quadTo(800, 50, 800, 50);
        ObjectAnimator coverAnimatorY = makeAnimator(cover, "translationY",200, 0, 0, -30);
        ObjectAnimator coverAnimatorRT = makeAnimator(cover, "rotation",100, 100, 0, 5);
        ObjectAnimator coverAnimatorRB = makeAnimator(cover, "rotation",0, 100, 0, -5);
        final AnimatorSet set=new AnimatorSet();
        set.playTogether(coverAnimatorY,coverAnimatorRT,coverAnimatorRB);
        set.setStartDelay(7000);
        set.start();

       /* ObjectAnimator traslateAnimator = ObjectAnimator.ofFloat(cover, "x", "y", path);
        traslateAnimator.setDuration(2000);
        traslateAnimator.setRepeatMode(ValueAnimator.REVERSE);
        traslateAnimator.setRepeatCount(200);
        traslateAnimator.start();*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(body.getMeasuredWidth() / 2, body.getMeasuredWidth() / 2, body.getMeasuredWidth() / 2, circlePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//此处不可省
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(body.getMeasuredWidth(), body.getMeasuredWidth());
    }

    /**
     * 带延迟 带重复的
     *
     * @param view
     * @param type
     * @param delay
     * @param values
     * @return
     */
    private ObjectAnimator makeAnimator(View view, String type,long duration, long delay, float... values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, type, values);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        return animator;
    }

}
