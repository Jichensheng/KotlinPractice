package com.heshun.firstkotlin.dagger.wine;

import com.heshun.firstkotlin.dagger.wine.entity.Brandy;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Junerver on 2016/12/23.
 * Feature:
 * Updated:
 */
public class TestDagger {
    /**
     * BrandyComponent中并没有定义没有别名的Brandy,此处使用的是Inject方法
     */
    @Inject
    Brandy mBrandy;

    @Inject
    @Named("CabernetSauvignon")
    Brandy mCSBrandy;

    public TestDagger() {
        //没用到Module时初始化注入的方法
//        DaggerBrandyComponent.create().inject(this);
        DaggerBrandyComponent.builder()
                .baseComponent(Singleton.getInstance().getBaseComponent())
                .build()
                .inject(this);
    }

    @Override
    public String toString() {
//        return mBrandy.toString();
        return mBrandy.toString()+"\n"+mCSBrandy.toString();
    }
}