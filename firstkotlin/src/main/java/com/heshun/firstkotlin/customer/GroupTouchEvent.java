package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：Jics
 * 2017/6/27 14:28
 */
public class GroupTouchEvent extends ViewGroup {
	private static String TAG = "Jcs~~~~~";
	public GroupTouchEvent(Context context) {
		this(context, null);
	}

	public GroupTouchEvent(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GroupTouchEvent(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int wMode = MeasureSpec.getMode(widthMeasureSpec);
		int hMode = MeasureSpec.getMode(heightMeasureSpec);
		int wSize = MeasureSpec.getSize(widthMeasureSpec);
		int hSize = MeasureSpec.getSize(heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int childCount = getChildCount();
		MarginLayoutParams layoutParams ;
		int correctW = 0;//最大的宽度
		int correctH = 0;

		int maxMarginR=0;
		int maxMarginB=0;

		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			layoutParams = (MarginLayoutParams) child.getLayoutParams();
			correctW = Math.max(correctW, child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
			correctH += child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

			maxMarginR=Math.max(maxMarginR,layoutParams.rightMargin);
			maxMarginB=Math.max(maxMarginB,layoutParams.bottomMargin);
		}
		setMeasuredDimension(wMode == MeasureSpec.EXACTLY ? wSize : correctW+maxMarginR, hMode == MeasureSpec.EXACTLY ? hSize : correctH+maxMarginB);
		Log.e("~~~~~~~~~~~", "onMeasure: "+getMeasuredWidth()+"      "+getMeasuredHeight() );
	}

	/**
	 * 此处的ltrb是本ViewGroup相对于其父控件的位置
	 * child.layou(l,t,r,b)时是相对Group的
	 * 模拟竖排线性布局
	 *
	 * @param changed
	 * @param l
	 * @param t
	 * @param r
	 * @param b
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//孩子总数
		int childrenCount = getChildCount();
		int cl = 0;
		int ct = 0;
		int cr = 0;
		int cb = 0;

		MarginLayoutParams layoutParams = null;
		for (int i = 0; i < childrenCount; i++) {
			View child = getChildAt(i);
			layoutParams = (MarginLayoutParams) child.getLayoutParams();
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();
			cl = layoutParams.leftMargin;
			ct = cb+ layoutParams.topMargin;
			cr = cl + childWidth + layoutParams.rightMargin;
			cb = ct + childHeight + layoutParams.bottomMargin;
			child.layout(cl, ct, cr, cb);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				Log.e(TAG, "onTouchEvent:ACTION_DOWN " );
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e(TAG, "onTouchEvent:ACTION_MOVE " );
				break;
			case MotionEvent.ACTION_UP:
				Log.e(TAG, "onTouchEvent:ACTION_UP " );
				break;
		}
		return true;
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				Log.e(TAG, "onInterceptTouchEvent:ACTION_DOWN " );
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e(TAG, "onInterceptTouchEvent:ACTION_MOVE " );
				break;
			case MotionEvent.ACTION_UP:
				Log.e(TAG, "onInterceptTouchEvent:ACTION_UP " );
				break;
		}
		return true;
	}


	/**
	 * 返回MarginLayoutParams的实例
	 * 这样就为我们的ViewGroup指定了其LayoutParams为MarginLayoutParams
	 * 不复写次函数，这句layoutParams = (MarginLayoutParams) child.getLayoutParams();会报类型转换错误
	 * @param attrs
	 * @return
	 */
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
}
