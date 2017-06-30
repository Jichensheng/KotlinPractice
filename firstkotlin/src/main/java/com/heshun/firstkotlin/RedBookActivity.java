package com.heshun.firstkotlin;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.heshun.firstkotlin.customer.IndicatorView;
import com.heshun.firstkotlin.fragment.CardFragment;
import com.heshun.firstkotlin.fragment.IndicatorFragmentAdapter;
import com.heshun.firstkotlin.interfaces.OnIndicatorScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/6/24 15:57
 */
public class RedBookActivity extends AppCompatActivity implements OnIndicatorScrollListener {
	private ViewPager viewPager;
	private IndicatorView indicatorView;
	private ImageView iv_head;
	private String TAG = "jics****";
	private PointF mStart;
	private Point mCenter;
	private PointF mTCenter;

	private boolean isRentComplite = false;

	@Override

	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_red_book);
		initView();
	}

	private void initView() {
		iv_head = (ImageView) findViewById(R.id.iv_head);
		List<Fragment> fragments = new ArrayList<>();
		fragments.add(new CardFragment(1));
		fragments.add(new CardFragment(2));
		fragments.add(new CardFragment(3));
		indicatorView = (IndicatorView) findViewById(R.id.idv_indicator);
		viewPager = (ViewPager) findViewById(R.id.vp_container);
		viewPager.setAdapter(new IndicatorFragmentAdapter(getSupportFragmentManager(), fragments));
		indicatorView.setViewPager(viewPager);
		indicatorView.setOnIndicatorScroll(this);
		Display display = getWindowManager().getDefaultDisplay();
		mCenter = new Point();
		display.getSize(mCenter);

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			isRentComplite = true;
			mStart = new PointF(iv_head.getX(), iv_head.getY());
		}
	}

	@Override
	public void onScroll(int position, float positionOffset, int positionOffsetPixels) {
		if (isRentComplite) {
			PointF start = new PointF();
			PointF end = new PointF();
			switch (position) {
				case 0:
					start.set(mStart.x, mStart.y);
					end.set(mCenter.x / 2 - iv_head.getMeasuredWidth() * 0.25f, mCenter.y / 2 - iv_head.getMeasuredHeight() * 1.2f);
					if (positionOffset != 0) {
						translateView(iv_head, start, end, positionOffset);
						scalView(iv_head,1f,0.3f,positionOffset);
					}
					break;
				case 1:
					start.set(mCenter.x / 2 - iv_head.getMeasuredWidth() * 0.25f, mCenter.y / 2 - iv_head.getMeasuredHeight() * 1.2f);
//					end.set(mCenter.x / 2 - 200, mStart.y + 20);
					end.set(mStart.x, mStart.y);
					if (positionOffset != 0) {
						translateView(iv_head, start, end, positionOffset);
						scalView(iv_head,0.35f,0.8f,positionOffset);
					}
					break;

			}

		}

	}

	public void translateView(View view, PointF start, PointF end, float persent) {
		float sX = start.x;
		float sY = start.y;
		float eX = end.x;
		float eY = end.y;
		float currentX = sX + (eX - sX) * persent;
		float currentY = sY + (eY - sY) * persent;
		view.setX(currentX);
		view.setY(currentY);
	}

	private void scalView(View view, float start, float end, float persent) {
		float current = start - (start - end) * persent;
		view.setScaleX(current);
		view.setScaleY(current);
	}
}
