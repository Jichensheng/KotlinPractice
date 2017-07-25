package com.heshun.firstkotlin.dagger.Car.bean;

/**
 * 发动机
 */
public class Engine {

	public Engine() {
		System.out.println(Config.TAG + "new Engine()");
	}
	public Engine(String name) {
		System.out.println(Config.TAG + "new Engine(" + name + ")");
	}
}