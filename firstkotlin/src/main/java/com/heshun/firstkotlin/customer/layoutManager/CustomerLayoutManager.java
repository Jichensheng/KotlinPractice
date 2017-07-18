package com.heshun.firstkotlin.customer.layoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author：Jics
 * 2017/7/18 14:28
 */
public class CustomerLayoutManager extends RecyclerView.LayoutManager {
	@Override
	public RecyclerView.LayoutParams generateDefaultLayoutParams() {
		return null;
	}

	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
		//在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
		detachAndScrapAttachedViews(recycler);

		//定义竖直方向的偏移量
		int offsetY = 0;
		for (int i = 0; i < getItemCount(); i++) {
			//这里就是从缓存里面取出
			View view = recycler.getViewForPosition(i);
			//将View加入到RecyclerView中
			addView(view);
			//对子View进行测量
			measureChildWithMargins(view, 0, 0);
			//把宽高拿到，宽高都是包含ItemDecorate的尺寸
			int width = getDecoratedMeasuredWidth(view);
			int height = getDecoratedMeasuredHeight(view);
			//最后，将View布局
			layoutDecorated(view, 0, offsetY, width, offsetY + height);
			//将竖直方向偏移量增大height
			offsetY += height;
		}
	}
}
