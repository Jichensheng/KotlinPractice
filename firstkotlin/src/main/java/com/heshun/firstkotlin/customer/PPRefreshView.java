package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.heshun.firstkotlin.R;
import com.heshun.firstkotlin.tools.DimentionUtils;

/**
 * 手指按下记录起点
 * 手指滑动记录距离，距离达到一定数值开始播放上下箭头旋转的动画，move过程不断requestLayout来让控件自动measure
 * 手指抬起，将headView隐藏，用动画view替换
 */
public class PPRefreshView extends ViewGroup {
	private Context context;
	private RecyclerView mListView;
	private PPView mPPView;
	private View header;
	private TextView title;
	private ImageView mImage;//箭头

	private int listTop = 0;
	private float headerHeight = 10 + 30 + 10;//header的高度，上留白 + 文字（PPVIew）高度 + 下留白
	private float headerpadding = 10;//上留白,下留白
	private int mYDown, mLastY;
	//最短滑动距离
	private int beeline = 20;
	private int maxTop = 300;

	private RotateAnimation mRotateAnimation;
	private int state = 0; //0,正常;1,下拉;2,松开
	private boolean isRefreshing = false;

	public void setPPRefreshViewListener(PPRefreshViewListener mPPRefreshViewListener) {
		this.mPPRefreshViewListener = mPPRefreshViewListener;
	}

	PPRefreshViewListener mPPRefreshViewListener;

	public PPRefreshView(Context context) {
		super(context);
		this.context = context;
	}

	public PPRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		//px转dp
		beeline = DimentionUtils.px2dip(context, ViewConfiguration.get(context).getScaledDoubleTapSlop());
		TypedArray b = context.obtainStyledAttributes(attrs, R.styleable.PPRefreshView_header);
		headerHeight = b.getDimension(R.styleable.PPRefreshView_header_header_height, 100);
		headerpadding = b.getDimension(R.styleable.PPRefreshView_header_header_padding, 10);
		b.recycle();
		initAnima();
	}

	private void initAnima() {
		//箭头旋转
		mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateAnimation.setFillAfter(true);
		mRotateAnimation.setDuration(200);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mPPView != null) {
			mPPView.measure(widthMeasureSpec, (int) (headerHeight - 2 * headerpadding));
		}
		if (header != null) {
			header.measure(widthMeasureSpec, (int) (headerHeight - 2 * headerpadding));
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	public static float getS(int current){
		double dd=Math.pow(1.1,current);
		return (float) (dd/(1+dd));
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (mListView == null && getChildCount() == 1) {
			mListView = (RecyclerView) getChildAt(0);
			mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView, dx, dy);
					if (!recyclerView.canScrollVertically(1)) {
						//添加外部回调
						if (mPPRefreshViewListener != null) {
							mPPRefreshViewListener.LoadMore();
						}
					}
				}
			});

		}
		if (mListView != null) {
			//为列表定尺寸范围，屏幕左上角和右下角
			mListView.layout(l, listTop, getMeasuredWidth(), b);
		}
		if (mPPView != null) {
			//top：文字（PPVIew）高度 + 下留白
			//把header放在了列表上边区域
			mPPView.layout(l, (int) (listTop - headerHeight + headerpadding), r, listTop);
		}

		if (header != null) {
			//top：文字（PPView）高度 + 下留白
			//header和动画view叠放起来了
			header.layout(l, (int) (listTop - headerHeight + headerpadding), r, listTop);
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		//松开手指，list回到顶部
		if (state == 2) {
			listTop = listTop - 25;
			if (listTop < headerHeight) {
				listTop = (int) headerHeight;
			}
			requestLayout();
		}

		//刷新完毕，关闭header
		if (state == 0 && listTop > 0) {
			listTop = listTop - 25;
			if (listTop < 0) {
				listTop = 0;
			}
			requestLayout();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		if (!isRefreshing) {
			switch (action) {
				case MotionEvent.ACTION_DOWN:
					// 按下
					mYDown = (int) event.getRawY();
					break;

				case MotionEvent.ACTION_MOVE:
					// 移动
					mLastY = (int) event.getRawY();

					//如果不能向下拉了&&下大于上&&距离差比最小移动距离大
					if (!mListView.canScrollVertically(-1) && mLastY > mYDown && (mLastY - mYDown) > beeline) {
						state = 1;
						//计算上边距
						listTop = mLastY - mYDown;
						//阻尼效果
//						int temp = (int) (maxTop * (Math.log(listTop) / Math.log(maxTop)));
						int temp = listTop/2;
						listTop = Math.min(listTop,temp);
						if (mPPView != null) {
							removeView(mPPView);
							mPPView = null;
						}
						if (header == null) {
							header = LayoutInflater.from(context).inflate(R.layout.header_layout, null, false);
							title = ((TextView) header.findViewById(R.id.text));
							mImage = ((ImageView) header.findViewById(R.id.icon));
							addView(header);
						}
						if (title != null && (mLastY - mYDown) > beeline * 2f) {
							title.setText("松开刷新");
							if (mImage.getAnimation() == null) {
								mImage.startAnimation(mRotateAnimation);
							}

						}
						if (title != null && (mLastY - mYDown) < beeline * 2f) {
							title.setText("下拉刷新");
							mImage.setImageResource(R.mipmap.down);
						}

						requestLayout();
						//已经判断是下拉刷新，拦截手势
						return false;
					}
					break;

				case MotionEvent.ACTION_UP:
					//松手的时候，把文字标题去掉
					if (header != null) {
						removeView(header);
						header = null;
					}
					//如果之前是下拉状态，就添加PPVIew
					if (mPPView == null && state == 1) {
						//添加外部回调
						if (mPPRefreshViewListener != null) {
							isRefreshing = true;
							mPPRefreshViewListener.onRefresh();
						}
						//动态生成view
						mPPView = new PPView(context);
						addView(mPPView);
						mYDown = 0;
						mLastY = 0;
						state = 2;
						requestLayout();
					}
					break;
				default:
					break;
			}
			return super.dispatchTouchEvent(event);
		} else
			return false;
	}


	/**
	 * 收起下拉刷新的header，刷新结束
	 */
	public void RefreshOver() {
		if (mPPView != null) {
			removeView(mPPView);
			mPPView = null;
		}
		if (header != null) {
			removeView(header);
			header = null;
			title = null;
			mImage = null;
		}
		state = 0;
	}

	public void setRefreshing(boolean b) {
		if (!b) {
			state = 0;
			isRefreshing = false;
			postInvalidate();
		} else {
			state = 2;
			postInvalidate();
		}
	}

	public interface PPRefreshViewListener {
		void onRefresh();

		void LoadMore();
	}

}