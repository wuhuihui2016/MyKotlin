package com.whh.mykotlin.langbase

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
    }
}