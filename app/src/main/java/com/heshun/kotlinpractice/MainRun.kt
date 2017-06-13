package com.heshun.kotlinpractice

import java.util.concurrent.locks.Lock

/**
 * author：Jics
 * 2017/6/9 09:23
 */
fun main(args: Array<String>) {

    displaySimpleFunction()
    lambdaPractice()
    baseType()//基本数据类型
}

fun displaySimpleFunction() {
    //常量
    val text: String = "Jcs"
    //字符串模板
    print("Hello,World!$text${sum(1, 2)}")
    printSum(3, maxOf(3, 8))
    print(parseInt("5"))

    //for
    println("演示for循环")
    val items = listOf<String>("apple", "banana", "kiwi")
    for (item in items) {
        println(item)
    }
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    //while
    println("演示while循环")
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }

    //when
    println("演示when表达式")
    println(describe(1))
    println(describe(23L))
    println(describe("nihao"))

    /**
     * 区间演示in
     */

    var x = 10;
    var y = 9;
    if (x in 1..y + 1) {
        println("fits in range")
    }
    val list = listOf<String>("a", "b", "c")
    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range too")
    }
    //区间迭代
    println("区间迭代")
    for (x in 1..5) {
        print("$x ")
    }
    println("\n有步长的区间迭代")
    for (x in 1..10 step 2) {
        print("$x ")
    }
    println("\n有步长downTo的区间迭代")
    for (x in 9 downTo 0 step 3) {
        print("$x ")
    }
    println("\n用in运算符判断集合内是否有某元素")
    when {
        "a" in list -> println("a 在list里")
        "apple" in items -> println("apple 在items里")
    }
    //lambda表达式
    println("lambda表达式")
    items.filter { it.length > 1 }//过滤
            .map { it.toUpperCase() }//转换
            .forEach { print("$it ") }//遍历

    //函数调用
    println("\n" + Cunsomer("Jcs", "530049276@qq.com").email)

    //map相关
    val map = mapOf<String, Int>("a" to 1, "b" to 2, "c" to 3)
    for ((k, v) in map) {
        print("$k -> $v ")
    }
    println("map的遍历")
    val tempMap = map["a"]
    println("在$map 中访问key为'a'的值为$tempMap")

    //null 相关
    /**
     * A变量？ 用来判断A变量是不是为空
     * string?.length 即变量string如果不为空就返回其长度
     * ?:表达式 前边是否为空，为空就打印冒号后边的，不为空就返回问号前边的(类型自己转换)
     */
    val lengthS: String? = null
    val lengthSs: String? = "JCS"
    println("$lengthS if not null 求长度的结果 ${lengthS?.length}")
    println("$lengthSs if not null 求长度的结果 ${lengthSs?.length}")

    println("$lengthS if not null and else 求长度的结果 ${lengthS?.length ?: "empty"}")
    println("$lengthSs if not null and else 求长度的结果 ${lengthSs?.length ?: "empty"}")
    lengthS?.let { println("$lengthS if not null 执行了此代码块（let的用法）") }
    lengthSs?.let { println("判断$lengthSs if not null 后执行了此代码块") }
    lengthS ?: println("lengthS if null 执行一个语句的结果")

    /**
     * when-else try/catch if-else 可作赋给变量
     */
    val whenVal = when ("a") {
        "a" -> 0
        else -> 1
    }
    val tryVal = try {

    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }
    val ifVal = if (true) {
        "one"
    } else {
        "two"
    }

    /**
     * 使用with块 调用一个对象的多个方法
     */
    val myTurtle = Turtle()
    with(myTurtle) {
        penDown()
        for (i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
        println()
    }
//创建数组
    val asc = Array(5, { i -> (i * i).toString() })
    print("通过Array(size,函数字面量)创建的数组  ")
    for (s: String in asc) {
        print("$s ")
    }
    println()
    //数组在内存中连续  list可以直接打印
    val ascc = arrayOf("3", "323")
    val ascclist = listOf<String>("3", "323")
    ascc[1]
    ascclist.get(1)
    println("$ascc $ascclist")
    //字符串

    //继承
    val derived = Derived(3)
    with(derived) {
        v()
        println(name)
    }
    derived.tos()

    //数据类的copy 及结构
    var jack = User("Jcs", 25)
    val olderJack = jack.copy(age = 22)
    println(jack.toString() + olderJack.toString())
    val (name, age) = olderJack
    println("解构$name  $age")

//泛型类
    val box1: Box<String> = Box<String>("Jcs")
    val value = box1.value
    println("泛型 $value")

    println("tailrec ${findFixPoint(2.3)}")

    //高阶函数
    val mapp = mapOf<String, Int>("一" to 1, "二" to 2, "三" to 3)
    for((key,value) in mapp){
        print("最好的遍历map的方法 $key  $value ")
    }
    mapp.mapValues { (key,value)->"key$key value$value  " }
    println()

    /**
     * list sets maps是不可变集合
     * 需要借助Mutable来进行变化
     * number和readOnlyView绑定
     */
    val numbers:MutableList<Int> = mutableListOf(1,2,3)
    val readOnlyView:List<Int> =numbers
    numbers.reverse()//反转
    println(numbers)
    numbers.add(5)
    println("可变List $readOnlyView ")
    numbers.clear()
    print(numbers+"\n可变的map")

    val mapppper:MutableMap<String,Int> = mutableMapOf("一" to 1, "二" to 2, "三" to 3)
    mapppper.put("五",5)
    val mapppppp:Map<String,Int> = mapppper
    for ((key,value) in mapppppp){
        print("$key $value  ")
    }
    println()

    val settt= setOf<String>("key1","key3","key5","key7")
    val mutaSettt:MutableSet<String> = mutableSetOf("key9")
    mutaSettt.add("key11")
    val readWriteMap= hashMapOf("foo" to 11, "bat" to 22)
    readWriteMap.put("avr",23)
    println("可变长的hashMap $readWriteMap")



}

fun Derived.tos() {
    println("扩展另一个类")
}

tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))

fun baseType() {
    val b: Byte = 0x0f;
    val i: Int = b.toInt()//显示类型转换
    println(255.toByte())
    //移位运算
}

//when 表达式作为函数的返回值
fun describe(obj: Any): String =
        when (obj) {
            1 -> "one"
            "Hello" -> "Greeting"
            is Long -> "Long"
            !is String -> "Not a string"
            else -> "Unknown"
        }

fun lambdaPractice() {
    println("")
    val list = listOf<String>("dd", "ddddd", "aaaaaaaaaaaaa")
    //可直接打印出list "$list" 等价于java的Arrays.toString(list)
    println("$list 中较长的是 ${max(list, { a, b -> a.length < b.length })}")

    val numlist = listOf<Int>(1, 2, 3, 4, 5, 6);
    println("$numlist 中较大的是 ${max(numlist, { a, b -> a < b })}")


}

fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}


/**
 * 传入一个Collection<T>型的集合，和一个函数字面量，输出一个
 * 参数less是(T,T)->Boolean类型的函数，返回值是布尔型的,此参数是调用的时候才传入的未声明函数
 * 相当于 fun compare(a:String,b:String):Boolean =a.length<b.length
 */
fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? {
    var max: T? = null
    for (it in collection)
    //在此执行less函数即比较max和it的大小max大就返回false
        if (max == null || less(max, it))
            max = it
    return max

}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun printSum(a: Int, b: Int): Unit {//可以省略无意义的值
    println("sum of $a and $b is ${a + b}")
}

fun maxOf(a: Int, b: Int) = if (a > b) a else b
//当一个个变量值可为空的时候必须在声明处的类型后边加？
fun parseInt(str: String): Int? {
    val temp = Integer.parseInt(str)
    if (temp > 5) {
        return temp
    } else return null
}

//可返回空的实例
fun getStringLength(obj: Any): Int? {
    if (obj is String) {
        return obj.length
    }
    return null
}

fun getStringLenght2(obj: Any): Int? {
    //&&运算符之前的式子成立就能确认obj的类型了
    if (obj is String && obj.length > 0) {
        return obj.length
    }
    return null
}
