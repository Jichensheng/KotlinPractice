package com.heshun.firstkotlin.dagger.Car;

import dagger.Component;

/**
 * author：Jics
 * 2017/7/25 13:48
 */
@Component(modules = CarModule.class)//需要在CarModule类中去寻找依赖
public interface CarComponent {
	void inject(Car car);//Car类可以使用这些依赖提供者
	void inject(TestCar car);//TestCar类可以使用这些依赖提供者
}
