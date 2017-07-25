package com.heshun.firstkotlin.dagger.Car.bean;

import javax.inject.Inject;

/**
 * author：Jics
 * 2017/7/25 20:37
 */
public class ChildA {
	@Inject
	public ChildA() {
		System.out.println("ChildA类");
	}

}
