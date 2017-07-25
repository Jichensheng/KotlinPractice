package com.heshun.firstkotlin.dagger.Car;


import com.heshun.firstkotlin.dagger.Car.bean.Engine;
import com.heshun.firstkotlin.dagger.Car.bean.Seat;
import com.heshun.firstkotlin.dagger.Car.bean.Wheel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * author：Jics
 * 2017/7/25 13:43
 */
//@Moudle 表示该类能够管理并提供依赖；你需要造车，但是车依赖于发动机，轮胎以及车座，那么写一个@Module注解的类来帮你管理这些依赖。
@Module
public class CarModule {

	@Provides
	public Engine provideEngine(){
		return new Engine();
	}
	@Provides
	@Named("hasName1")
	public Engine provideNamedEngine(){
		return new Engine("有名字");
	}
	@Provides
	public Seat provideSeat(){
		return new Seat();
	}
	@Provides
	public Wheel provideWheel(){
		return new Wheel();
	}
}
