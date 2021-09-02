package com.whh.mykotlin.langbase

/**
 * 1、类的定义
 *   class 默认为 public final 类型，不能被继承，如果需要被继承，加 open 关键字
 *   当主构造被定义并设定了需传参数，则在定义次构造时必须将主构造参数作为该构造的参数
 * 2、接口的定义
 *    接口默认 open
 * 3、abstract有open特征
 */
open class Person(id: Int) {  //定义了主构造方法

    var id = 0
    var name: String? = null
    var age = 0

    //次构造1
    constructor() : this(1) {
        println("peson id is $id")
    }

    //次构造2
    constructor(id: Int, name: String) : this(id) {
        this.id = id
        this.name = name
        println("peson id is ${this.id}, name is ${this.name}")
    }

    //次构造3
    constructor(id: Int, age: Int) : this(id) {
        this.id = id
        this.age = age
        println("peson id is ${this.id}, age is ${this.age}")
    }
}

class Student(id: Int) : Person(id) {
    //派生自Person类
    var studenId = 0

    constructor(id: Int, studenId: Int) : this(id) {
        this.id = id
        this.studenId = studenId
        println("student id is ${this.id}, studenId is ${this.studenId}")
    }
}

/**
 * 接口
 */
interface Callback1 {
    fun callback1(info: String): String
}

interface Callback2 {
    fun callback2(result: Int): Int
}

/**
 * abstract有open特征
 */
class ImlCalback : Callback1, Callback2 {
    override fun callback1(info: String): String {
        return info
    }

    override fun callback2(result: Int): Int {
        return result
    }
}

fun main() {
    Person(123) //主构造
    Person() //次构造1
    Person(464, "whh") // 次构造2
    Person(464, 12) // 次构造3
    Student(123, 456)

    println("callbackString:${ImlCalback().callback1("whhh")}")
    println("callbackInt:${ImlCalback().callback2(555)}")
}

