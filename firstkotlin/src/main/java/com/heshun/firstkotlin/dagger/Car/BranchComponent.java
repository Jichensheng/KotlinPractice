package com.heshun.firstkotlin.dagger.Car;

import com.heshun.firstkotlin.dagger.Car.bean.BranchUser;

import dagger.Component;

/**
 * author：Jics
 * 2017/7/25 19:07
 */
@Component(modules = BranchModule.class)
public interface BranchComponent {
	void inject(BranchUser user);
}
