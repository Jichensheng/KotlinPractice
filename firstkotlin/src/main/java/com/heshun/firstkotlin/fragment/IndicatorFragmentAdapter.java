package com.heshun.firstkotlin.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author：Jics
 * 2017/6/24 14:01
 */
public class IndicatorFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;

	public IndicatorFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}


	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

}
