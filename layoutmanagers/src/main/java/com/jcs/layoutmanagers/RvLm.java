package com.jcs.layoutmanagers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：Jics
 * 2017/7/20 18:07
 */
public class RvLm extends RecyclerView.LayoutManager {
	private static final String TAG = "+-+-";


	@Override
	public RecyclerView.LayoutParams generateDefaultLayoutParams() {
		return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

/*	*//**
	 * 支持滚动
	 * @return
	 *//*
	@Override
	public boolean canScrollVertically() {
		return true;
	}

	*//**
	 * 实现滚动
	 * @param recycler
	 * @param state
	 * @return
	 *//*
	@Override
	public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
		offsetChildrenVertical(-dy);
		return dy;
	}*/
	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
		detachAndScrapAttachedViews(recycler);
		Log.e(TAG, String.format("onLayoutChildren:childCount=: %s  itemCount=： %s ",getChildCount(),getItemCount() ));
		int itemCount = getItemCount();
		if (itemCount < 1) {
			return;
		}
		for (int i = 0; i <itemCount; i++) {
			View view = recycler.getViewForPosition(i);
			//后add的在上边
			addView(view);
			measureChildWithMargins(view, 0, 0);
			int widSpace = getWidth() - getDecoratedMeasuredWidth(view);
			int heiSpace = getHeight() - getDecoratedMeasuredHeight(view);
			layoutDecoratedWithMargins(view, widSpace / 2, heiSpace / 2,
					widSpace / 2 + getDecoratedMeasuredWidth(view), heiSpace / 2 + getDecoratedMeasuredHeight(view));
			view.setTranslationY((itemCount-i)*100);
			view.setScaleX(i*0.01f);
			view.setScaleY(i*0.01f);
		}

	}
}
