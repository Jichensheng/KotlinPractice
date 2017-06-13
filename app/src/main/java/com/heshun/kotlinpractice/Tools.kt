package com.heshun.kotlinpractice

/**
 * author：Jics
 * 2017/6/9 17:24
 */
object Tools {
    fun int2byte(res: Int): ByteArray {
        val targets = ByteArray(4)
        targets[0] = (res and 0xff).toByte()// 最低位
        targets[1] = (res shr 8 and 0xff).toByte()// 次低位
        targets[2] = (res shr 16 and 0xff).toByte()// 次高位
        targets[3] = res.ushr(24).toByte()// 最高位,无符号右移。
        return targets
    }
}
