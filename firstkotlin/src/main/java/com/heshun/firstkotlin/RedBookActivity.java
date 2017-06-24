package com.heshun.firstkotlin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.heshun.firstkotlin.customer.IndicatorView;
import com.heshun.firstkotlin.fragment.CardFragment;
import com.heshun.firstkotlin.fragment.IndicatorFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/6/24 15:57
 */
public class RedBookActivity extends AppCompatActivity {
	private ViewPager viewPager;
	private IndicatorView indicatorView;
	@Override

	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_red_book);
		initView();
	}

	private void initView() {
		List<Fragment> fragments=new ArrayList<>();
		fragments.add(new CardFragment(1));
		fragments.add(new CardFragment(2));
		fragments.add(new CardFragment(3));
		indicatorView= (IndicatorView) findViewById(R.id.idv_indicator);
		viewPager= (ViewPager) findViewById(R.id.vp_container);
		viewPager.setAdapter(new IndicatorFragmentAdapter(getSupportFragmentManager(),fragments));
		indicatorView.setViewPager(viewPager);
	}
}
