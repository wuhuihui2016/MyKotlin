package com.whh.mykotlin.langbase


fun main() {

//    //TODO　1、变量修改值
//    var name = "wuhuihui"
//    println(name)
//    name = "whh123"
//    println(name)
//
//    //TODO　2、方法的定义 Unit == Java 中的 void
    val funTest = FunTest()
    println("add1：" + funTest.add1(1, 2))
    println("add2：" + funTest.add2(1, 2))
    println("add3：${funTest.add3(1, 2)}")
    funTest.lenMethod(1, 2, 3, 4, 5)
    //TODO　lambda表达式函数，形式：参数类型序列 -> 返回类型　= {具体实现}
    val addFun: (Int, Int) -> Int = { num1, num2 -> num1 + num2 }
    val r = addFun(9, 9)
    println("addMethod result：$r")
    funTest.StringFun() //String 输出自动换行
//
//    //TODO　3、NULL检查机制
//    testNull()
//    println(testMethod("whh")) //输出555
//    println(testMethod("whh111")) //输出null
//
//    //TODO　4、区间使用
//    testIn()
//
//    //TODO　5、数组
//    arrayTest()
//
//    //TODO　6、条件判断
//    ifTest()

    //TODO　7、循环与标签
//    loopAndTag();

}


/**
 * 嵌套类：变量定义
 *   Var：可变变量，字节码中有set、get方法
 *   Val：不可变变量，类似Java中final，字节码中只有get方法
 *   变量确定类型：可不提前定义，在赋值时自动确认类型，一旦被确定不可更改类型
 */
class VarValTest {

    // 赋值时确定变量类型
    var info1 = "AAAA" // 类型推到  String
    var info2 = 'A' // 类型推到  Char
    var info3: Int = 99 // 类型推到  Int

    // 不可以修改的
    val age: Int = 99

    // 静态语言 编译期 就决定了 String类型
    var info4 = "LISI"  // info4==String类型
}

/**
 * 嵌套类：方法定义
 *   方法定义的几种方法
 */
class FunTest {

    /**
     * 方法返回类型 Int
     */
    fun add1(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    /**
     * 对方法1的简单写法
     */
    fun add2(num1: Int, num2: Int) = num1 + num2

    /**
     * 返回结果类型转换
     */
    fun add3(num1: Int, num2: Int) = "" + (num1 + num2)

    /**
     * 可变长度的参数，方法作用：遍历输出参数
     */
    fun lenMethod(vararg value: Int) {
        for (i in value) {
            println(i)
        }
    }

    /**
     * 不需关心 \n 换行，自动调整换行
     */
    fun StringFun() {

        //前置空格
        val info1 = """
        AAA
        BBB
        CCC
        DDD
        EEE
    """
        println("$info1")

        //没空格
        val info2 = """
        AAA
        BBB
        CCC
        DDD
        EEE
    """.trimIndent()
        println("trimIndent$info2")

        //没空格，trimMargin过滤字符
        val info3 = """
        ?AAA
        ?BBB
        ?CCC
        ?DDD
        ?EEE
    """.trimMargin("?")
        println("trimMargin?$info3")

        // 显示 $99999.99
        val price = """
        ${'$'}99999.99
    """.trimIndent()
        println(price)

        println("String compare")
        //比较值本身(equals、==)
        val name1 = "555"
        val name2 = "555"
        println("equals:${name1.equals(name2)}") //true
        println("==:${name1 == name2}") //true
        //比较对象地址(===)
        val test1: Int? = 10000
        val test2: Int? = 10000
        println("===:${test1 === test2}") //false
        println("String compare end")

        var mm = { number: Int -> println("我想打印: $number")
            number + 100
        }
        println("m15:${mm(88)}")
        /*我想打印: 88
        m15:188*/

        //高阶用法
        loginEngine("whh", "123")

    }
}

/**
 * 方法的高阶用法（方法嵌套方法）
 */
fun loginEngine(userName: String, userPwd: String) : Unit {
    loginService(userName, userPwd) { name, pwd ->
        if (name == "whh" && pwd == "123456") {
            println("恭喜:${name}登录成功")
        } else {
            println("登录失败，请检查用户名或密码!!")
        }
    }
}

// 标准  String String --> Unit
typealias RequestLogin = (String, String) -> Unit

private fun loginService(userName: String, userPwd: String, requestLogin: RequestLogin) : Unit {
    requestLogin(userName, userPwd)
}

/**
 * 空值处理
 */
fun testNull() {
    var info: String? = null
//    println(info!!.length) //不管空值与否，一定要执行，空时出现 KotlinNullPointerException
    println("info?.length:${info?.length}") //补救方法1：如果为空，则不执行.length，输出null
    if (info != null) println(info.length)  // 补救方法2：使用前判空(java同方法)
    println(info?.length ?: "我还不错") //如果为空，输出
}

/**
 * Int? 允许返回null
 */
fun testMethod(name: String): Int? {
    if (name == "whh") {
        return 555;
    }
    return null;
}

/**
 * 区间
 */
fun testIn() {

    println("顺序输出1-5：")
    for (i in 1..5) println(i)

    println("顺序有误，不会输出1-5！")
    for (i in 5..1) println(i)

    println("逆序输出：")
    for (i in 5 downTo 1) println(i)

    val value = 88
    if (value in 1..100) println("88在区间[1-100]内")

    println("步长指定2，在区间1-10内输出：")
    for (i in 1..10 step 2) println(i)

    println("顺序输出1-5，但排除最后元素：")
    for (i in 1 until 5) println(i)
}

/**
 * 数组
 */
fun arrayTest() {
    println("arrayTest start>>>>")
    val numbers = arrayOf(1, 2, 3, 4, 5)
    println("numbers[2]=${numbers[2]}")
    for (n in numbers) println(n) //遍历输出数组

    println("数组遍历输出时+200，value初始值为0")
    val numbers2 = Array(5, { value: Int -> (value + 200) })
    for (n in numbers2) println(n)

    println("arrayTest end>>>>")
}

/**
 * 条件判断
 */
fun ifTest() {
    println("ifTest start>>>>")
    val n1 = 123
    val n2 = 456
    println("max value is ${if (n1 > n2) n1 else n2}")

    val n3 = 2
    when (n3) {
        1 -> println("一")
        2 -> println("二")
        3 -> println("三")
        else -> println("其他")
    }

    val n4 = 45
    when (n4) {
        in 1..100 -> println("1..100")
        in 200..500 -> println("200..500")
        else -> println("其他")
    }

    val n5 = 3
    val result = when (n5) {
        1 -> {
            println("很开心")
            "今天是星期一"
        }
        2 -> {
            println("很开心")
            "今天是星期二"
            88
        }
        3 -> {
            println("很开心")
            "今天是星期三"
            100
        }
        else -> 99
    }
    println("result:$result") //100

    when (8) { //过滤多条件
        1, 2, 3, 4, 5, 6, 7 -> println("满足")
        else -> println("不满足")
    }
    println("ifTest end>>>>")
}

/**
 * 循环与标签
 */
fun loopAndTag() {
    outer@ for (i in 1..3) {
        for (j in 1..3) {
            if (i == 2) {
//                break //中断了j
                break@outer //给外层循环打的标签outer中断
            }
            println("i:$i, j:$j")
        }
    }

    var items = listOf<String>("111", "222", "333")
    //遍历方法1
    for (item in items) {
        println(item)
    }

    //遍历方法2
    items.forEach {
        println(it)
    }

    for (index in items.indices) {
        println("下标：$index,  对应的值：${items[index]}")
    }

    class whhTag {
        val i = "whhhh"

        fun show() {
            println(i);
            println(this.i);
            println(this@whhTag.i) //自带的标签
        }
    }

    whhTag().show()
}
