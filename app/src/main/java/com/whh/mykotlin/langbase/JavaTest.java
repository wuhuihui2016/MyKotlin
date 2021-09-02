package com.whh.mykotlin.langbase;

import com.whh.mykotlin.langbase.KTTestKt;
import com.whh.mykotlin.langbase.TestJvmStatic;

/**
 * Java 调用 kotlin
 */
public class JavaTest {

    public static void main(String[] args) {
        KTTestKt.add(1, 2 );
        KTTestKt.show("whh");
        KTTestKt.showTest();

        //kotlin 的静态变量和静态方法的调用
        TestJvmStatic.doOperateA();
        TestJvmStatic.Companion.doOperateB();
        int a = TestJvmStatic.a;
        int e = TestJvmStatic.e;
        int d = TestJvmStatic.d;
        TestJvmStatic.Companion.getB();
        TestJvmStatic.Companion.getC();
    }
}
