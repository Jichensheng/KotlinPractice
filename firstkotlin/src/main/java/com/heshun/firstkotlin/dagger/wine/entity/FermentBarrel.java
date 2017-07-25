package com.heshun.firstkotlin.dagger.wine.entity;

import javax.inject.Inject;

/**
 * Created by Junerver on 2016/12/21.
 * Feature: 发酵桶
 * Updated:
 */
public class FermentBarrel {

    @Inject
    public FermentBarrel() {
    }

    @Override
    public String toString() {
        return "这是一个发酵桶";
    }
}