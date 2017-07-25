package com.heshun.firstkotlin.dagger.Car.bean;

import javax.inject.Inject;

/**
 * author：Jics
 * 2017/7/25 20:07
 */
public class BranchName {
	@Inject
	public BranchName() {
		System.out.println("我是Jcs");
	}

	@Override
	public String toString() {
		return "BranchName{}";
	}
}
