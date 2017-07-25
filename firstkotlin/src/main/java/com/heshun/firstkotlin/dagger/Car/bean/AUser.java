package com.heshun.firstkotlin.dagger.Car.bean;

import com.heshun.firstkotlin.dagger.Car.DaggerAComponent;

import javax.inject.Inject;

/**
 * author：Jics
 * 2017/7/25 20:34
 */
public class AUser {
	@Inject
	A a;

	public AUser() {
		DaggerAComponent.create().inject(this);
	}
}
