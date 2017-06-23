package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：Jics
 * 2017/6/20 11:09
 */
public class CustomViewGroup extends ViewGroup {
	public CustomViewGroup(Context context) {
		super(context);
	}

	public CustomViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cCount = getChildCount();
		int cWidth = 0;
		int cHeight = 0;
		MarginLayoutParams cParams = null;
		/**
		 * 遍历所有的childView根据其宽高，以及margin进行布局
		 */
		for (int i = 0; i < cCount; i++) {
			View childView = getChildAt(i);
			cWidth = childView.getMeasuredWidth();
			cHeight = childView.getMeasuredHeight();
			cParams = (MarginLayoutParams) childView.getLayoutParams();
			int cl = 0, ct = 0, cr = 0, cb = 0;
			switch (i) {
				case 0:
					cl=cParams.leftMargin;
					ct=cParams.topMargin;
					break;
				case 1:
					cl=getWidth()-cWidth-cParams.leftMargin-cParams.rightMargin;
					ct=cParams.topMargin;
					break;
				case 2:
					cl=cParams.leftMargin;
					ct=getHeight()-cHeight-cParams.bottomMargin;
					break;
				case 3:
					cl=getWidth()-cWidth-cParams.leftMargin-cParams.rightMargin;
					ct=getHeight()-cHeight-cParams.bottomMargin;
					break;
			}
			cr=cl+cWidth;
			cb=cHeight+ct;
			childView.layout(cl,ct,cr,cb);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 获得此ViewGroup上级容器为其推荐的宽高，和计算模式
		 * 获取该ViewGroup父容器为其设置的计算模式和尺寸
		 * 大多情况下，只要不是wrap_content，父容器都能正确的计算其尺寸
		 * 所以我们自己需要计算如果设置为wrap_content时的宽和高
		 * 如何计算呢？那就是通过其childView的宽和高来进行计算。
		 */
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		//计算出所有的childView的宽高
		//通过ViewGroup的measureChildren方法为其所有的孩子设置宽和高
		// 此行执行完成后，childView的宽和高都已经正确的计算过了
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		/**
		 * 记录如果是wrap_content是设置的宽和高
		 */
		int width = 0;
		int height = 0;

		int cCount = getChildCount();

		int cWidth = 0;
		int cHeight = 0;
		MarginLayoutParams cParams = null;

		//左右两view高度
		int lHeight = 0;
		int rHeight = 0;

		//用于计算上边两个childView的宽度，最终取大的
		int tWidth = 0;
		//计算下边两个childView的宽度，最终取大的
		int bWidth = 0;

		for (int i = 0; i < cCount; i++) {
			View childView = getChildAt(i);
			cWidth = childView.getMeasuredWidth();
			cHeight = childView.getMeasuredHeight();
			cParams = (MarginLayoutParams) childView.getLayoutParams();

			//上边两个childView
			if (i == 0 || i == 1) {
				tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
			}
			if (i == 2 || i == 3) {
				bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
			}
			if (i == 0 || i == 2) {
				lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
			}
			if (i == 1 || i == 3) {
				rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
			}
		}
		width = Math.max(tWidth, bWidth);
		height = Math.max(lHeight, rHeight);
		/**
		 * 如果wrap_content设置为我们计算的值
		 * 否则直接设置为父容器计算的值
		 */
		setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);
	}

	/**
	 * 返回MarginLayoutParams的实例
	 * 这样就为我们的ViewGroup指定了其LayoutParams为MarginLayoutParams
	 *
	 * @param attrs
	 * @return
	 */
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
}
