package com.heshun.kotlinpractice

/**
 * author：Jics
 * 2017/6/9 16:08
 */
data class Cunsomer(val name:String,val email:String){
    init{
        println("this is init block")
    }

}