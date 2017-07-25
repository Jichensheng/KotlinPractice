package com.heshun.firstkotlin.customer.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

/**
 * author：Jics
 * 2017/7/25 10:09
 */
public class CustomerBehavior extends CoordinatorLayout.Behavior<View> {
	private int width;

	public CustomerBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels;
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		//如果dependency是TextView的实例，说明它就是我们所需要的Dependency
		return dependency instanceof TextView;
	}
	//每次dependency位置发生变化都会执行onDependentViewChanged
	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		int top=dependency.getTop();
		int left=dependency.getLeft();
		int x=width-left-child.getWidth();
		int y=top;
		setPosition(child,x,y);
		return true;
	}

	private void setPosition(View child, int x, int y) {
		CoordinatorLayout.MarginLayoutParams layoutParams= (CoordinatorLayout.MarginLayoutParams) child.getLayoutParams();
		layoutParams.leftMargin=x;
		layoutParams.rightMargin=y;
		child.setLayoutParams(layoutParams);
	}
}

























