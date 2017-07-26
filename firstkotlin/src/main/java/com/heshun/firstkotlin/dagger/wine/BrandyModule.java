package com.heshun.firstkotlin.dagger.wine;

import com.heshun.firstkotlin.dagger.wine.entity.Brandy;
import com.heshun.firstkotlin.dagger.wine.entity.Distiller;
import com.heshun.firstkotlin.dagger.wine.entity.FermentBarrel;
import com.heshun.firstkotlin.dagger.wine.entity.Grape;
import com.heshun.firstkotlin.dagger.wine.entity.Wine;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 Module 可以理解为一个生产实例的工厂，
 他掌握各个需要注入的类的实例化方法，
 当 Dagger 需要为某个类注入实例时，
 会到 @Module 注解的类中，查找这个类的实例化方法。
 */
@Module
public class BrandyModule {
    @Provides
    public Grape provideGrape() {
        return new Grape("解百纳");
    }

    @Provides
    @Named("CabernetSauvignon")
    public Grape provideOtherGrape() {
        return new Grape("赤霞珠");
    }
    @Provides
    @Named("CabernetSauvignon")
    public Wine provideOtherWine(@Named("CabernetSauvignon") Grape grape, FermentBarrel fermentBarrel) {
        return new Wine(grape, fermentBarrel);
    }

    @Provides
    @Named("CabernetSauvignon")
    public Brandy provideOtherBrandy(@Named("CabernetSauvignon") Wine wine, Distiller distiller) {
        return new Brandy(distiller, wine);
    }
}