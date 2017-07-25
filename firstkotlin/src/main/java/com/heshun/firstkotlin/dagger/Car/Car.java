package com.heshun.firstkotlin.dagger.Car;

import com.heshun.firstkotlin.dagger.Car.bean.Config;
import com.heshun.firstkotlin.dagger.Car.bean.Engine;
import com.heshun.firstkotlin.dagger.Car.bean.Seat;
import com.heshun.firstkotlin.dagger.Car.bean.Wheel;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * author：Jics
 * 2017/7/25 13:48
 */
public class Car {
	@Inject
	@Named("hasName1")
	Engine engine;//相当于实例化了一个Engine
	@Inject
	Seat seat;
	@Inject
	Wheel wheel;
	public Car(){
		DaggerCarComponent
				.builder()
				.carModule(new CarModule())
				.build()
				.inject(this);
		System.out.println(Config.TAG+ "new Car()");
	}
}
