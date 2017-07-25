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
    /**
     * @Provides
        在提供实例的方法上注解，用于告诉 Dagger 这是一个用于注入的实例。
        方法名可以随便，Dagger 是通过方法的返回值来将其添加到依赖列表的。
     * @return
	 */
    @Provides
    public Grape provideGrape() {
        return new Grape("解百纳");
    }

    @Provides
    @Named("CabernetSauvignon")
    public Grape provideOtherGrape() {
        return new Grape("赤霞珠");
    }

    /**
     * @Named 注解用于给 @Provides 注解提供别名
     * 在使用的时候也需要加上 @Named 注解，Dagger 就知道我们需要的是具体哪个实例了
     * @param grape
     * @param fermentBarrel
     * @return
	 */
    @Provides
    public Wine provideOtherWine(@Named("CabernetSauvignon") Grape grape, FermentBarrel fermentBarrel) {
        return new Wine(grape, fermentBarrel);
    }

    @Provides
    @Named("CabernetSauvignon")
    public Brandy provideOtherBrandy(Wine wine, Distiller distiller) {
        return new Brandy(distiller, wine);
    }
}