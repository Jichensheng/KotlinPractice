package com.heshun.canvasdemo.customerView.boiler;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.heshun.canvasdemo.R;

/**
 * author：Jics
 * 2017/7/3 17:03
 */
public class FireView extends RelativeLayout {
    private static final int ANIMATOR_DURATION = 500;
    private ImageView f11;
    public FireView(Context context) {
        this(context, null);
    }

    public FireView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FireView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_fire, this);
        ImageView   f12, f2, f31, f32;
        f11 = (ImageView) view.findViewById(R.id.iv_fire11);
        f12 = (ImageView) view.findViewById(R.id.iv_fire12);
        f2 = (ImageView) view.findViewById(R.id.iv_fire2);
        f31 = (ImageView) view.findViewById(R.id.iv_fire31);
        f32 = (ImageView) view.findViewById(R.id.iv_fire32);

        ObjectAnimator aF11X = makeAnimator(f11, "scaleX", 0, 0.7f,0.6f,0.7f);
        ObjectAnimator aF11Y = makeAnimator(f11, "scaleY", 0, 0.7f,0.6f,0.7f);
        ObjectAnimator aF11Alpha = makeAnimator(f11, "alpha", 0, 0.7f,0.6f,0.7f);

        ObjectAnimator aF12X = makeAnimator(f12, "scaleX", 200, 0.8f,0.7f,0.8f);
        ObjectAnimator aF12Y = makeAnimator(f12, "scaleY", 200,  0.8f,0.7f,0.8f);
        ObjectAnimator aF12Alpha = makeAnimator(f12, "alpha", 200,0.8f,0.7f,0.8f);

        ObjectAnimator aF2X = makeAnimator(f2, "scaleX", 100, 0.9f,0.8f,0.9f);
        ObjectAnimator aF2Y = makeAnimator(f2, "scaleY", 100,  0.9f,0.8f,0.9f);
        ObjectAnimator aF2Alpha = makeAnimator(f2, "alpha", 100, 1, 0.7f, 1);

        ObjectAnimator aF31X = makeAnimator(f31, "scaleX", 200,0.8f,0.7f,0.8f);
        ObjectAnimator aF31Y = makeAnimator(f31, "scaleY", 200, 0.8f,0.7f,0.8f);
        ObjectAnimator aF31Alpha = makeAnimator(f31, "alpha", 200, 0.8f,0.7f,0.8f);

        ObjectAnimator aF32X = makeAnimator(f32, "scaleX", 0,  0.7f,0.6f,0.7f);
        ObjectAnimator aF32Y = makeAnimator(f32, "scaleY", 0,  0.7f,0.6f,0.7f);
        ObjectAnimator aF32Alpha = makeAnimator(f32, "alpha", 0, 0.7f,0.6f,0.7f);

        AnimatorSet set=new AnimatorSet();
        set.playTogether(aF11X,aF11Y,aF11Alpha,
                         aF12X,aF12Y,aF12Alpha,
                         aF2X,aF2Y,aF2Alpha,
                         aF31X,aF31Y,aF31Alpha,
                         aF32X,aF32Y,aF32Alpha);
        set.start();



    }

    /**
     * 带延迟 带重复的
     * @param view
     * @param type
     * @param delay
     * @param values
     * @return
     */
    private ObjectAnimator makeAnimator(View view, String type, long delay, float... values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, type, values);
        animator.setDuration(ANIMATOR_DURATION);
        animator.setStartDelay(delay);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        return animator;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(f11.getMeasuredWidth()*5,f11.getMeasuredHeight());
    }
}
