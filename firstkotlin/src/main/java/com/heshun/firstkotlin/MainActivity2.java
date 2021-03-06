package com.heshun.firstkotlin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.heshun.firstkotlin.customer.IndicatorView;
import com.heshun.firstkotlin.fragment.IndicatorFragmentAdapter;
import com.heshun.firstkotlin.fragment.OtherFragment;
import com.heshun.firstkotlin.fragment.PrograssFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {
	private ViewPager viewPager;
	private List<Fragment> fragments;
	private IndicatorView indicatorView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2_main);
		initView();
	}

	private void initView() {
		indicatorView= (IndicatorView) findViewById(R.id.idv_indicator);
		fragments=new ArrayList<>();
		fragments.add(new OtherFragment());
		fragments.add(new PrograssFragment());
		viewPager= (ViewPager) findViewById(R.id.vp_container);
		viewPager.setAdapter(new IndicatorFragmentAdapter(getSupportFragmentManager(),fragments));
		indicatorView.setViewPager(viewPager);
	}

}