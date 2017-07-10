package com.heshun.canvasdemo.customerView.boiler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.heshun.canvasdemo.R;
import com.heshun.canvasdemo.customerView.interfacePack.OnWaterFullListener;
import com.heshun.canvasdemo.customerView.tools.AnimatorUtils;
import com.heshun.canvasdemo.customerView.tools.DimentionUtils;

/**
 * Created by Jcs on 2017/7/2.
 * rgb(255,206,148) #ffce94
 */

public class BoilerView extends RelativeLayout implements OnWaterFullListener {
	private ImageView body;
	private Paint circlePaint;
	private AnimatorSet coverAnimatorSet;//锅盖抖动动画
	private AnimatorSet throwAnimatorSet;//扔东西动画

	public BoilerView(Context context) {
		this(context, null);
	}

	public BoilerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BoilerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setWillNotDraw(false);
		initView(context);
		System.out.println();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void initView(final Context context) {
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(Color.rgb(255, 206, 148));
		circlePaint.setStyle(Paint.Style.FILL);
		circlePaint.setDither(true);

		View view = LayoutInflater.from(context).inflate(R.layout.boiler_xxdpi, this);
		view.setScaleX(0.7f);
		view.setScaleY(0.7f);
		body = (ImageView) view.findViewById(R.id.iv_body);
		final ImageView cover = (ImageView) view.findViewById(R.id.iv_cover);
		final WaterView iv_water = (WaterView) view.findViewById(R.id.iv_water);
		final FireView fireView = (FireView) view.findViewById(R.id.iv_fire);
		fireView.setScaleX(1.3f);
		fireView.setScaleY(1.3f);
		iv_water.setOnWaterFullListener(this);//设置水满了的监听器
		final ImageView peas1 = (ImageView) view.findViewById(R.id.iv_peas1);
		final ImageView peas2 = (ImageView) view.findViewById(R.id.iv_peas2);
		final ImageView potato = (ImageView) view.findViewById(R.id.iv_potato);
		final ImageView carrot = (ImageView) view.findViewById(R.id.iv_carrot);
		final ImageView chicken = (ImageView) view.findViewById(R.id.iv_chicken);
		hideView(cover,body,iv_water,fireView,peas1,peas2,potato,carrot,chicken);
		//第一阶段动画
		throwAnimatorSet = makeThrowAnimator(cover, body, peas1, peas2, potato, carrot,chicken);
		throwAnimatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				showView(body);
			}
			//第一阶段动画结束显示水和火动画
			@Override
			public void onAnimationEnd(Animator animation) {

				//第二阶段动画
				iv_water.startAnimator();
				showView(iv_water,fireView);
			}
		});
		throwAnimatorSet.start();
		coverAnimatorSet = makeCoverShakeAnimator(cover);

	}

	/**
	 * 显示view
	 * @param views
	 */
	private void hideView(View... views){
		for (View view : views) {
			if (view != null) {
				if (view.getVisibility()==VISIBLE) {
					view.setVisibility(INVISIBLE);
				}
			}
		}
	}
	/**
	 * 显示view
	 * @param views
	 */
	private void showView(View... views){
		for (View view : views) {
			if (view != null) {
				if (view.getVisibility()!=VISIBLE) {
					view.setVisibility(VISIBLE);
				}
			}
		}
	}
	/**
	 * 抛物动画集
	 *
	 * @param body
	 * @param peas1
	 * @param peas2
	 * @param potato
	 * @param carrot
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private AnimatorSet makeThrowAnimator(final ImageView covert, ImageView body, final ImageView peas1, final ImageView peas2, final ImageView potato, final ImageView carrot, final ImageView chicken) {
		AnimatorSet throwAnimator = new AnimatorSet();
		//锅体从上到下逐渐显示
		ObjectAnimator bodyTrans = makeAnimator(0, body, "translationY", 1000, 0, -DimentionUtils.dip2px(getContext(), 34), 0);
		ObjectAnimator bodyAlpha = makeAnimator(0, body, "alpha", 1000, 0, 0.2f, 1);

		//锅盖斜上方下来转正逐渐显示
		ObjectAnimator covertPath = ObjectAnimator.ofFloat(covert, "x", "y", AnimatorUtils.makeBezierJumpPath(getPoint(200),-getPoint(76),0,getPoint(20)));
		covertPath.setStartDelay(1000);
		covertPath.setDuration(1000);
		covertPath.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				showView(covert);
			}
		});
		ObjectAnimator coverAlpha=makeAnimator(0, covert, "alpha", 1000, 1000, 0.2f, 1);
		ObjectAnimator coverRotation=makeAnimator(0,covert,"rotation",1000,1000,-20,0);

		ObjectAnimator peas1Path=ObjectAnimator.ofFloat(peas1, "x", "y", AnimatorUtils.makeBezierJumpPath(getPoint(200),getPoint(20),getPoint(104),getPoint(136)));
		peas1Path.setStartDelay(1000);
		peas1Path.setDuration(1000);
		peas1Path.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				showView(peas1);
			}
		});
		ObjectAnimator peas1Alpha=makeAnimator(0, peas2, "alpha", 1000, 1000, 0.2f, 1);

		ObjectAnimator peas2Path=ObjectAnimator.ofFloat(peas2, "x", "y", AnimatorUtils.makeBezierJumpPath(getPoint(270),getPoint(50),getPoint(125),getPoint(136)));
		peas2Path.setStartDelay(1000);
		peas2Path.setDuration(1000);
		peas2Path.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				showView(peas2);
			}
		});
		ObjectAnimator peas2Alpha=makeAnimator(0, peas2, "alpha", 1000, 1000, 0.2f, 1);

		ObjectAnimator carrotPath=ObjectAnimator.ofFloat(carrot, "x", "y", AnimatorUtils.makeBezierJumpPath(getPoint(250),getPoint(50),getPoint(67),getPoint(118)));
		carrotPath.setStartDelay(1000);
		carrotPath.setDuration(1000);
		carrotPath.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				showView(carrot);
			}
		});
		ObjectAnimator carrotRotation=makeAnimator(0,carrot,"rotation",1000,1000,20,0);
		ObjectAnimator carrotAlpha=makeAnimator(0, carrot, "alpha", 1000, 1000, 0.2f, 1);


		ObjectAnimator chickenPath=ObjectAnimator.ofFloat(chicken, "x", "y", AnimatorUtils.makeBezierJumpPath(getPoint(0),getPoint(20),getPoint(97),getPoint(95)));
		chickenPath.setStartDelay(1000);
		chickenPath.setDuration(1000);
		chickenPath.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				showView(chicken);
			}
		});
		ObjectAnimator chickenRotation=makeAnimator(0,chicken,"rotation",1000,1000,20,0);
		ObjectAnimator chickenAlpha=makeAnimator(0, chicken, "alpha", 1000, 1000, 0.2f, 1);

		ObjectAnimator potatoPath=ObjectAnimator.ofFloat(potato, "x", "y", AnimatorUtils.makeBezierJumpPath(getPoint(0),getPoint(50),getPoint(67),getPoint(123)));
		potatoPath.setStartDelay(1000);
		potatoPath.setDuration(1000);
		potatoPath.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				showView(potato);
			}
		});
		ObjectAnimator potatoRotation=makeAnimator(0,potato,"rotation",1000,1000,-20,0);
		ObjectAnimator potatoAlpha=makeAnimator(0, potato, "alpha", 1000, 1000, 0.2f, 1);
		throwAnimator.playTogether(bodyTrans,bodyAlpha,
				covertPath,coverAlpha,coverRotation,
				peas1Path,peas1Alpha,
				peas2Path,peas2Alpha,
				carrotPath,carrotRotation,carrotAlpha,
				potatoPath,potatoRotation,potatoAlpha,
				chickenPath,chickenRotation,chickenAlpha
				);
		return throwAnimator;
	}

	private int getPoint(float value) {
		return DimentionUtils.dip2px(getContext(), value);
	}

	/**
	 * 获取控件左上角的dp坐标值以及控件的dp尺寸
	 * @param name
	 * @param view
	 */
	private void getViewPointInfo(final String name, final View view) {
		view.post(new Runnable() {
			@Override
			public void run() {
				float a = DimentionUtils.px2dip(getContext(), view.getMeasuredWidth());
				float b = DimentionUtils.px2dip(getContext(), view.getMeasuredHeight());
				float c = DimentionUtils.px2dip(getContext(), view.getX());
				float d = DimentionUtils.px2dip(getContext(), view.getY());
				Log.e("++++", String.format("%s的宽：%s  高：%s  X：%s  Y：%s  ", name, a, b, c, d));
			}
		});
	}

	/**
	 * 锅盖抖动动画集合
	 *周期循环
	 * @param cover
	 * @return
	 */
	private AnimatorSet makeCoverShakeAnimator(ImageView cover) {
		AnimatorSet coverAnimatorSet = new AnimatorSet();
		ObjectAnimator coverAnimatorY = makeAnimator(1, cover, "translationY", 200, 0, 0, -30);
		ObjectAnimator coverAnimatorRT = makeAnimator(1, cover, "rotation", 100, 100, 0, 5);
		ObjectAnimator coverAnimatorY_R = makeAnimator(1, cover, "translationY", 200, 200, 0, -30);
		ObjectAnimator coverAnimatorRT_R = makeAnimator(1, cover, "rotation", 100, 300, 0, -5);
		ObjectAnimator coverAnimatorY_RR = makeAnimator(1, cover, "translationY", 100, 600, 0, -20);
		coverAnimatorSet.playTogether(coverAnimatorY, coverAnimatorRT, coverAnimatorY_R, coverAnimatorRT_R, coverAnimatorY_RR);
		coverAnimatorSet.setStartDelay(1500);
		coverAnimatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				animation.start();
			}
		});
		return coverAnimatorSet;
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
	private ObjectAnimator makeAnimator(int repeat, View view, String type, long duration, long delay, float... values) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, type, values);
		animator.setDuration(duration);
		animator.setStartDelay(delay);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.setRepeatCount(repeat);
		return animator;
	}

	/**
	 * 水满了启动锅盖抖动
	 */
	@Override
	public void waterFull() {
		coverAnimatorSet.start();
	}
}
