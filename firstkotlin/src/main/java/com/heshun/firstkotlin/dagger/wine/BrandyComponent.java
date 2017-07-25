package com.heshun.firstkotlin.dagger.wine;

import dagger.Component;

/**
 * Created by Junerver on 2016/12/23.
 * Feature:
 * Updated:
 */

//@Component 用来注解一个接口，在编译的时候会生成 Dagger+文件名 的新Java文件。
// Component可以理解为注射器，它是连接被注入的类与需要被注入的类之间的桥梁。
@Component(modules = BrandyModule.class,dependencies = BaseComponent.class)
@Lalala
public interface BrandyComponent {
    void inject(TestDagger testDagger);
    void inject(OtherTest otherTest);
}