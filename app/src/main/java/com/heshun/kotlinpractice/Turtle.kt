package com.heshun.kotlinpractice

/**
 * author：Jics
 * 2017/6/9 16:56
 */
class Turtle{
    fun penDown(){
        print("落笔 ")
    }
    fun penUp(){
        print("抬笔 " )
    }
    fun turn(degrees:Double){
        print("转动$degrees 度 ")
    }
    fun forward(pixels:Double){
        print("向前画出$pixels 像素 ")
    }
}