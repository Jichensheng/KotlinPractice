package com.heshun.kotlinpractice

/**
 * author：Jics
 * 2017/6/12 09:21
 */
open class Base(val p:Int){
    open val x:String get(){return "$p "}
    open fun v(){}
    fun nv(){}
}