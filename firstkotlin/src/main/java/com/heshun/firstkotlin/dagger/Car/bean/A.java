package com.heshun.firstkotlin.dagger.Car.bean;

import javax.inject.Inject;

/**
 * author：Jics
 * 2017/7/25 20:33
 */
public class A {
	@Inject
	ChildA childA;

	@Inject
	public A() {
		System.out.println("A类");
	}
}
