package com.heshun.firstkotlin.dagger.wine;


import com.heshun.firstkotlin.dagger.wine.entity.Distiller;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Junerver on 2016/12/23.
 * Feature:
 * Updated:
 */
@Component
@Singleton
public interface BaseComponent {
	Distiller anyName();
}