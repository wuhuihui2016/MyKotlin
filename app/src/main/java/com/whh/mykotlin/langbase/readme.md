kotlin学习博客：https://www.jianshu.com/u/a324daa6fa19

<VarValDemo.kt>
  1、变量修改值;
  2、方法的定义 Unit == Java 中的 void；
     lambda表达式函数，形式：参数类型序列 -> 返回类型　= {具体实现}
     val addFun: (Int, Int) -> Int = { num1, num2 -> num1 + num2 }
  3、NULL检查机制；
  4、区间使用；
  5、数组；
  6、条件判断；
  7、循环与标签
  
<TestClsAndInterface.kt>
   1、类的定义
      class 默认为 public final 类型，不能被继承，如果需要被继承，加 open 关键字
      当主构造被定义并设定了需传参数，则在定义次构造时必须将主构造参数作为该构造的参数
   2、接口的定义 接口默认 open
   3、abstract有open特征
   
<SingleTest.kt>
   data 类，相当于 java bean，data 类的 copy：获的类参数，_则不接收

<TestJvmStatic.kt> kotlin定义静态变量和方法
<KTTest.kt> 演示kotlin、java混合调用方法


1、kotlin 不需 dataBinding!
2、kotlin 不需 Observable.create()....!
3、查看字节码 Tools - Kotlin - Show Kotlin Bytecode
4、== 或者 equals ：比较对象的结构是否相等[https://www.jianshu.com/p/9879ae169e78]
   Kotlin 中， == 等价于 equals 用于比较对象的结构是否相等, 很多情况下使用的是 ==，因为对于浮点类型 Float 和 Double，其实现方法 equals 不遵循 IEEE 754 浮点运算标准。
   ===比较对象的引用是否相等
   Kotlin 中， === 用于比较对象的引用是否指向同一个对象，运行时如果是基本数据类型 === 等价于 ==

   