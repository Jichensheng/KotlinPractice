package com.heshun.firstkotlin.customer.behavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.heshun.firstkotlin.R;

/**
 * author：Jics
 * 2017/7/25 10:25
 */
public class BehaviorActivity extends AppCompatActivity {
	private int lastX;
	private int lastY;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_behavior);
		final TextView textView= (TextView) findViewById(R.id.tv_behavior);
		textView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int x = (int) event.getRawX();
				int y = (int) event.getRawY();
				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) textView.getLayoutParams();
						int left = layoutParams.leftMargin + x - lastX;
						int top = layoutParams.topMargin + y - lastY;
						layoutParams.leftMargin = left;
						layoutParams.topMargin = top;
						textView.setLayoutParams(layoutParams);
						textView.requestLayout();
						break;
				}
				lastX = x;
				lastY = y;
				return true;
			}
		});
	}
}
