package com.heshun.firstkotlin.dagger.Car;

import com.heshun.firstkotlin.dagger.Car.bean.AUser;
import com.heshun.firstkotlin.dagger.Car.bean.BranchUser;

/**
 * author：Jics
 * 2017/7/25 13:55
 */
public class TestCar {
	public static void main(String[] args){
		TestCar testCar=new TestCar();
//		new Car();
		new BranchUser();
		new AUser();
	}
}
