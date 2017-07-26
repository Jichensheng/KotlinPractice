package com.heshun.firstkotlin.dagger.Car;

import com.heshun.firstkotlin.dagger.Car.bean.Branch;
import com.heshun.firstkotlin.dagger.Car.bean.BranchName;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * author：Jics
 * 2017/7/25 19:08
 */
@Module
public class BranchModule {
	@Provides
	@Named("nameddddd")
	public Branch provideBranch(@Named("nameddddd")BranchName name) {
		return new Branch(name);
	}
	@Provides
	@Named("nameddddd")
	public BranchName prseBranchName(){
		return new BranchName("J.cs");
	}
	@Provides
	public BranchName provideBranchName3(){
		return new BranchName("默认名");
	}
}
