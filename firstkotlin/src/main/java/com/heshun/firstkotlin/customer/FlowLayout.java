package com.heshun.firstkotlin.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/6/20 15:22
 */
public class FlowLayout extends ViewGroup {
	private int x=0;
	private int y=0;
	private static final String TAG = "jcs_flowLayout";

	public FlowLayout(Context context) {
		super(context);
		Log.e(TAG, "FlowLayout: 一个" );
		setWillNotDraw(false);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e(TAG, "FlowLayout: 两个" );
		setWillNotDraw(false);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		Log.e(TAG, "FlowLayout: 三个" );
		setWillNotDraw(false);
	}

	//按行记录所有view
	private List<List<View>> mAllViews = new ArrayList<>();
	//记录每一行最大行高
	private List<Integer> mLineHeight = new ArrayList<>();

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.e(TAG, "onDraw:************* " );
		Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStrokeWidth(2);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(x,y,10,paint);
	}

	/**
	 * 遍历该ViewGroup中所有孩子，并确定孩子位置，通过孩子的layout方法设置位置
	 *
	 * @param changed
	 * @param l 此group的左上角
	 * @param t
	 * @param r 此group的右下角
	 * @param b
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		x = r;
		y = b;
		Log.e(TAG, String.format("onLayout: ********l=%d***t=%d**r=%d***b=%d*", l, t, r, b));
		mAllViews.clear();
		mLineHeight.clear();
		int width = getWidth();

		int lineWidth = 0;
		int lineHeight = 0;
		//每行所有的childView
		List<View> lineViews = new ArrayList<>();
		int cCount = getChildCount();
		//遍历所有孩子
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();
			//当需要换行时
			if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
				//记录此行所有view及最大高度
				mLineHeight.add(lineHeight);
				mAllViews.add(lineViews);
				lineWidth = 0;
				lineViews = new ArrayList<>();
			}
			/**
			 * 不需要换行时
			 */
			lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
			lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
			lineViews.add(child);
		}
		mLineHeight.add(lineHeight);
		mAllViews.add(lineViews);
		int left = 0;
		int top = 0;
		//总行数
		int lineNums = mAllViews.size();
		for (int i = 0; i < lineNums; i++) {
			//每行所有的views
			lineViews = mAllViews.get(i);
			//当前最大高度
			lineHeight = mLineHeight.get(i);
			Log.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
			Log.e(TAG, "第" + i + "行， ：" + lineHeight);

			//遍历当前行所有view
			for (int j = 0; j < lineViews.size(); j++) {
				View child = lineViews.get(j);
				if (child.getVisibility() == View.GONE) {
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

				int lc = left + lp.leftMargin;
				int tc = top + lp.topMargin;
				int rc = lc + child.getMeasuredWidth();
				int bc = tc + child.getMeasuredHeight();
				Log.e(TAG, child + " , l = " + lc + " , t = " + t + " , r ="
						+ rc + " , b = " + bc);
				//Layout是ViewGroup用来确定子元素的位置
				//ViewGroup会遍历所有子元素并调用各个子元素的layout方法，
				child.layout(lc, tc, rc, bc);
				left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;

			}
			left = 0;
			top += lineHeight;

		}
	}

	/**
	 * 测量整个Group的大小
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.e(TAG, "onMeasure: *************************");
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
		Log.e("jcs", "onMeasure: " + sizeWidth + "," + sizeHeight);

		//如果是wrap_content
		int width = 0;
		int height = 0;
		/**
		 * 记录每一行的宽度，width不断取最大宽度
		 */
		int lineWidth = 0;
		/**
		 * 记录所有行高累加至此
		 */
		int lineHeight = 0;

		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
			int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

			/**
			 * 如果加入当前的child,则超出最大宽度，得到的最大宽度给width，累加height 然后开新行
			 */
			if (lineWidth + childWidth > sizeWidth) {
				width = Math.max(lineWidth, childWidth);
				lineWidth = childWidth;//重新开一行
				height += lineHeight;
				lineHeight = childHeight;
			} else {
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}
			if (i == cCount - 1) {
				width = Math.max(width, lineWidth);
				height += lineHeight;
			}
		}
		//AT_MOST 自适应大小
		//判断是不是精确大小EXACTLY(具体值、match_parent、fill_parent)不是的话就用计算出来的width和height
		setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
}
