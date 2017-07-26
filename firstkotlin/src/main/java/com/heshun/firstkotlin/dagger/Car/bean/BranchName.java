package com.heshun.firstkotlin.dagger.Car.bean;

/**
 * author：Jics
 * 2017/7/25 20:07
 */
public class BranchName {
	public BranchName(String name) {
		System.out.println("我是"+name);
	}

	@Override
	public String toString() {
		return "BranchName{}";
	}
}
