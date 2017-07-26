package com.heshun.firstkotlin.dagger.Car.bean;

import javax.inject.Inject;

/**
 * author：Jics
 * 2017/7/25 19:06
 */
public class Branch {

	@Inject
	public Branch(BranchName name) {
		System.out.println("大桥牌——用moudle"+name.toString());
	}
}