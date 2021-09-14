package com.whh.mykotlin.langbase

import java.text.SimpleDateFormat
import java.util.*

class TestJvmStatic {
    companion object {
        const val a = 1
        val b = 2
        var c = 3

        @JvmField
        var d = 4

        @JvmField
        val e = 5

        @JvmStatic
        fun doOperateA() {

        }

        fun doOperateB() {}

        /**
         * 带时间输出信息
         */
        @JvmStatic
        fun printlnMsg(msg: Any?) {
            val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")
            val now = {
                dateFormat.format(Date(System.currentTimeMillis()))
            }
            println("${now()} [${Thread.currentThread().name}] $msg")
        }
    }
}