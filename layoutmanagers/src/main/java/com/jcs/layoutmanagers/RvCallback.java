package com.jcs.layoutmanagers;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.Collections;
import java.util.List;

/**
 * author：Jics
 * 2017/7/20 16:53
 */
public class RvCallback extends ItemTouchHelper.SimpleCallback {
	private static final String TAG = "+-+-";
	private RecyclerView mRv;
	private RecyclerView.Adapter adapter;
	private List<String> list;

	public RvCallback(int dragDirs, int swipeDirs, RecyclerView mRv, RecyclerView.Adapter adapter, List<String> list) {
		super(dragDirs, swipeDirs);
		this.mRv = mRv;
		this.adapter = adapter;
		this.list = list;
	}

	/**
	 * 必须复写
	 *
	 * @param dragDirs
	 * @param swipeDirs
	 */
	public RvCallback(int dragDirs, int swipeDirs) {
		super(dragDirs, swipeDirs);
	}

	/**
	 * dragDirs不设置的话onMove函数不触发
	 */
	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
		Log.e(TAG, "onMove: " + "viewHolder = " + viewHolder.getAdapterPosition() + "     target = " + target.getAdapterPosition());
		Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
		adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
		return false;
	}

	/**
	 * swipeDirs触发删除掉之后触发
	 *
	 * @param viewHolder
	 * @param direction
	 */
	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		Log.e(TAG, "onSwiped: " + viewHolder.getAdapterPosition());
		String remove = list.remove(viewHolder.getAdapterPosition());
		list.add(list.size(), remove);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 不管是拖拽drag还是滑动swipe只要item有位置变动那么dX,dY就会有变化：原位置是0向左向上为负，向右向下为正
	 *
	 * @param c
	 * @param recyclerView
	 * @param viewHolder
	 * @param dX
	 * @param dY
	 * @param actionState
	 * @param isCurrentlyActive
	 */
	@Override
	public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
		Log.e(TAG, String.format("onChildDraw:childCount=: %s  itemCount=： %s ",recyclerView.getChildCount(),recyclerView.getLayoutManager().getItemCount() ));
/*		double swipValue = Math.sqrt(dX * dX + dY * dY);
		double fraction = swipValue / (mRv.getWidth() * 0.5f);
		fraction = fraction > 1 ? 1 : fraction;
		int childCount = recyclerView.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child=recyclerView.getChildAt(i);
			child.setScaleX(1-(float) fraction*((float) i/childCount));
			child.setScaleY(1-(float) fraction*((float) i/childCount));
//			child.setRotationX(360*(1-(float) fraction*((float) i/childCount)));
		}*/
		Log.e(TAG, String.format("位置%s, 坐标X=%s， 坐标Y=%s,  actionState=%s,  isCurrentlyActivity=%s ", viewHolder.getAdapterPosition(), dX, dY, actionState, isCurrentlyActive));
	}
}
