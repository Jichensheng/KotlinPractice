package com.heshun.firstkotlin.dagger.wine.entity;


import javax.inject.Inject;

/**
 * Created by Junerver on 2016/12/21.
 * Feature: 白兰地类
 * Updated:
 */
public class Brandy {
    private Distiller mDistiller;
    private Wine mWine;

    @Inject
    public Brandy(Distiller distiller, Wine wine) {
        mDistiller = distiller;
        mWine = wine;
    }

    @Override
    public String toString() {
        return mWine.toString()+"在"+mDistiller.toString()+"蒸馏的白兰地";
    }
}