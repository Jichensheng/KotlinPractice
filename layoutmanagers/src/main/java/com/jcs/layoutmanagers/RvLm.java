package com.jcs.layoutmanagers;

import android.support.v7.widget.RecyclerView;
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

	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
		detachAndScrapAttachedViews(recycler);
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
			view.setScaleX(i*0.1f);
			view.setScaleY(i*0.1f);
		}

	}
}
