package com.heshun.kotlinpractice

/**
 * author：Jics
 * 2017/6/12 09:22
 */
 class Derived(p:Int):Base(p),MyInterface{
    override fun bar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var name:String
    get() = "Jcs get"
    set(value) {
        name=value
    }
    override val x: String
        get() = super.x+"  998"
    override fun v(){
        println("Derived $x ")
    }

}