package com.whh.mykotlin.langbase


class Person1(val name: String, val age: Int)
data class Person2(val name: String, val age: Int)

fun main() {
    val a1 = -0
    val a2 = 0
    println(a1 == a2) //true
    println(a1.equals(a2)) //true

    val a3 = -0f
    val a4 = 0f
    println(a3 == a4) //true
    println(a3.equals(a4)) //false

    //-------------

    val p1 = Person1(name = "hi-dhl", age = 10)
    val p2 = Person1(name = "hi-dhl", age = 10)
    println(p1 == p2) //false
    println(p1.equals(p2)) //false
    println(p1 === p2) //false
    println(p1.name === p2.name) //true

    //-------------

    val p3 = Person2(name = "ByteCode", age = 10)
    val p4 = Person2(name = "ByteCode", age = 10)
    println(p3 == p4) //true
    println(p3.equals(p4)) //true
    println(p3 === p4) //false
    println(p3.name === p4.name) //true

}