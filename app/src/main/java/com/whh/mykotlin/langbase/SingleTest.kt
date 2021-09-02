package com.whh.mykotlin.langbase

/**
 * 1、单例写法
 * 2、data 类相当于 java bean，data 类的 copy：获的类参数，_则不接收
 */
fun main() {
    val user = User(99, "whh", 'M')
    val (myId, myName, mySex) = user.copy();
    println("myID:$myId, myName:$myName, mySex:$mySex")

    // _ 代表不接收
    val (_, myName2, _) = user.copy()
    println("myName:$myName2")

    // TODO　对象被 new 了3次，并没有真正实现单例！！！
    SingleDemo.method()
    SingleDemo.method()
    SingleDemo.method()

    //TODO 真正实现了单例！！！
    //单例模式1：
    val singleDemo1 = SingleDemo1.getInstance()
    singleDemo1.show("kt singleDemo1")
    //单例模式2(需判空处理)：
    val singleDemo2 = SingleDemo2.getInstance()
    singleDemo2?.show("kt singleDemo2")

}

/**
 * 单例实现类，并没有真正实现单例
 */
object SingleDemo {
    fun method() {
        println("running in SingleTest.method()")
    }
}

/**
 * 单例模式1:
 */
class SingleDemo1 {
    object Hloder {
        val instance = SingleDemo1();
    }

    companion object {
        fun getInstance(): SingleDemo1 = Hloder.instance
    }

    fun show(name: String) {
        println("show:$name")
    }
}

/**
 * 单例模式2:
 */
class SingleDemo2 {

    companion object {
        private var instance: SingleDemo2? = null
        fun getInstance(): SingleDemo2? {
            if (instance == null) {
                instance = SingleDemo2()
            }
            return instance
        }
    }

    fun show(name: String) {
        println("show:$name")
    }
}

/**
 * data 类，相当于 java bean
 */
data class User(val id: Int, val name: String, val sex: Char)