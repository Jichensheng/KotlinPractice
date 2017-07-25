package com.heshun.firstkotlin.dagger.Car;

import com.heshun.firstkotlin.dagger.Car.bean.AUser;

import dagger.Component;

/**
 * author：Jics
 * 2017/7/25 20:35
 */
@Component
public interface AComponent {
	void inject(AUser user);
}
