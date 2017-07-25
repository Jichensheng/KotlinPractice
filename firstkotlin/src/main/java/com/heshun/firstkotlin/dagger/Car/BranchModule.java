package com.heshun.firstkotlin.dagger.Car;

import com.heshun.firstkotlin.dagger.Car.bean.Branch;
import com.heshun.firstkotlin.dagger.Car.bean.BranchName;

import dagger.Module;
import dagger.Provides;

/**
 * author：Jics
 * 2017/7/25 19:08
 */
@Module
public class BranchModule {
	@Provides
	public Branch provideBranch(BranchName name) {
		return new Branch(name);
	}

}
