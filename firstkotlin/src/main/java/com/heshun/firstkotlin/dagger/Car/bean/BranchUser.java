package com.heshun.firstkotlin.dagger.Car.bean;

import com.heshun.firstkotlin.dagger.Car.BranchModule;
import com.heshun.firstkotlin.dagger.Car.DaggerBranchComponent;

import javax.inject.Inject;

/**
 * author：Jics
 * 2017/7/25 19:07
 */
public class BranchUser {
	@Inject
	Branch branch ;
/*	@Inject
	@Named("nameddddd")
	Branch branch ;*/


	public BranchUser() {
		DaggerBranchComponent.builder()
				.branchModule(new BranchModule())
				.build()
				.inject(this);

	}
}
