package com.whh.mykotlin.langbase

/**
 * 演示kotlin、java混合调用方法
 */
fun main() {
    `showTest`()
    `4325436465375`("Derry")
}

fun show(name: String) {
    println("name:$name")
}

fun add(n1: Int, n2: Int) {
    println("resut:${n1 + n2}")
}

fun `showTest`() {
    println("showTest")
}

//TODO　严重误人子弟！！！方法名含有空格，编译失败[com.android.tools.r8.internal.q8: Space characters in SimpleName ' ' are not]
//fun `   `(sex: Char) {
//    println("sex:$sex")
//}

fun `4325436465375`(name: String) {

}