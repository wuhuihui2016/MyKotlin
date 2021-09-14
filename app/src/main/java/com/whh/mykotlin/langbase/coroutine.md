### kotlin协程
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2"

协程：也叫微线程，是一种新的多任务并发的操作手段。是一种比线程更加轻量级的存在。正如一个进程可以拥有多个线程一样，一个线程也可以拥有多个协程。
特征：协程是运行在单线程中的并发程序
优点：省去了传统 Thread 多线程并发机制中切换线程时带来的线程上下文切换、线程状态切换、Thread 初始化上的性能损耗，能大幅度提高并发性能

[https://www.jianshu.com/p/76d2f47b900d]
线程运行在内核态，协程运行在用户态
线程：拥有独立的栈、局部变量，基于进程的共享内存，因此数据共享比较容易，但是多线程时需要加锁来进行访问控制，不加锁就容易导致数据错误，
     但加锁过多又容易出现死锁。线程之间的调度由内核控制(时间片竞争机制)，程序员无法介入控制。线程之间的切换需要深入到内核级别，因此线程的切换代价比较大
协程：是跑在线程上的优化产物，被称为轻量级 Thread，拥有自己的栈内存和局部变量，共享成员变量。
     传统 Thread 执行的核心是一个while(true) 的函数，本质就是一个耗时函数，Coroutine 可以用来直接标记方法，由程序员自己实现切换，调度，不再采用传统的时间段竞争机制。
     在一个线程上可以同时跑多个协程，同一时间只有一个协程被执行，在单线程上模拟多线程并发，协程何时运行，何时暂停，都是有程序员自己决定的，使用： yield/resume API。

协程优势：
     1、协程切换不涉及线程上下文的切换和线程状态的改变，不存在资源、数据并发，所以不用加锁，只需要判断状态就OK，所以执行效率比多线程高很多；
     2、协程是非阻塞式的(也有阻塞API)，一个协程在进入阻塞后不会阻塞当前线程，当前线程会去执行其他协程任务；
     3、协程的运行状态可以由程序员控制，何时运行，切换，挂起，唤醒等等，而是不是交给系统内核去竞争CPU时间片
     4、协程在线程中是顺序执行的，协程也有阻塞唤醒，且不同于线程，线程的阻塞会消耗CPU时间，而协程的挂起不会阻塞线程

1、suspend 关键字
   被修饰的方法或类可以被协程挂起，且只能在协程中只能与另一个suspend修饰的方法交流，没用suspend修饰的方法不能参与协程任务。

2、创建协程
   launch - 创建协程
   async - 创建带返回值的协程，返回的是 Deferred 类
   withContext - 不创建新的协程，在指定协程上运行代码块
   runBlocking - 不是 GlobalScope 的 API，可以独立使用，区别是 runBlocking 里面的 delay 会阻塞线程，而 launch 创建的不会
   coroutineScope - 一个suspend方法，创建一个新的作用域，并在该作用域内执行指定代码块，它并不启动协程。其存在的目的是进行符合结构化并发的并行分解

   协程调度：
   Dispatchers.Main	        用途：主线程，和UI交互，执行轻量任务	使用场景：1.call suspend functions。2. call UI functions。 3. Update LiveData
   Dispatchers.IO	        用途：用于网络请求和文件访问	        使用场景：1. Database。 2.Reading/writing files。3. Networking
   Dispatchers.Default	    用途：CPU密集型任务	                使用场景：1. Sorting a list。 2.Parsing JSON。 3.DiffUtils
   Dispatchers.Unconfined	用途：不限制任何制定线程	            使用场景：高级调度器，不应该在常规代码里使用

   withContext 函数用来切换线程，控制任意一行代码运行什么线程
   //在子线程启动一个协程
   GlobalScope.launch(Dispatchers.IO) {
            //发起一个网络请求
            var result = HttpUtil.get("https://www.baidu.com")
            Log.e("whh:22", "${Thread.currentThread().name}") //DefaultDispatcher-worker-2
            withContext(Dispatchers.Main) {
                //网络请求成功以后，到主线程更新UI
                Log.e("whh:33", "${Thread.currentThread().name}") //main
            }
            //再次回到子线程的协程
            Log.e("whh:44", "${Thread.currentThread().name}") //DefaultDispatcher-worker-2
        }

   Job - 协程构建函数的返回值，可以把 Job 看成协程对象本身，协程的操作方法都在 Job 身上了
   job.start() - 启动协程，除了 lazy 模式，协程都不需要手动启动
   job.join() - 等待协程执行完毕
   job.cancel() - 取消一个协程
   job.cancelAndJoin() - 等待协程执行完毕然后再取消

3、启动线程
   GlobalScope.launch 返回job
   GlobalScope.async 同上，不同的是返回的是 Deferred 类型，Deferred 继承自 Job 接口，Job有的它都有，增加了一个方法 await ，这个方法接收的是 async 闭包中返回的值，async 的特点是不会阻塞当前线程，但会阻塞所在协程，也就是挂起
   runBlocking 和 launch 区别的地方就是 runBlocking 的 delay 方法是可以阻塞当前的线程的，和Thread.sleep() 一样。runBlocking 通常的用法是用来桥接普通阻塞代码和挂起风格的非阻塞代码，在 runBlocking 闭包里面启动另外的协程，协程里面是可以嵌套启动别的协程的

4、协程的挂起和恢复
   协程是顺序执行，当不涉及挂起时，执行顺序是谁被写在前面谁先执行，后面的等待前面的执行完成再运行。
   涉及到挂起时，前面的协程挂起了，线程不会空闲，则继续下一个协程运行，当前面的协程被恢复时，不会马上执行这个协程，而是等待当前正在执行的协程完成后再去执行。

5、relay、yield 区别
   relay 和 yield 方法是协程内部的操作，可以挂起协程.
   区别：relay(转播；重播) 是挂起协程并经过执行时间恢复协程，当线程空闲时就会运行协程；
        yield(退让) 是挂起协程，让协程放弃本次 cpu 执行机会让给别的协程，当线程空闲时再次运行协程。

6、协程取消(job.cancel )的特质
   父协程手动调用 cancel() 或者异常结束，会立即取消它的所有子协程
   父协程必须等待所有子协程完成（处于完成或者取消状态）才能完成
   子协程抛出未捕获的异常时，默认情况下会取消其父协程

7、协程异常
   7.1 因协程取消，协程内部suspend方法抛出的CancellationException，将会取消当前协程和子协程，不会取消父协程，也不会其他例如打印堆栈信息等的异常处理操作。
   7.2 常规异常，这类异常，有两种异常传播机制
      launch：将异常自动向父协程抛出，将会导致父协程退出
      async: 将异常暴露给用户(通过捕获deffer.await()抛出的异常)
   父Job退出，所有子job会马上退出
   子job抛出除CancellationException(意味着正常取消)意外的异常会导致父Job马上退出
   如果使用了 SupervisorJob 或 supervisorScope，子协程抛出未捕获的非 CancellationException 异常不会取消父协程，异常也会由子协程自己处理。

8、应用
  用带返回值的协程 GlobalScope.async 在 IO 线程中去执行网络请求，然后通过 await 返回请求结果，
  用launch 在主线程中更新UI就行了，注意外面用 runBlocking 包裹

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coroutine.setOnClickListener { click() }
    }

    private fun click() = runBlocking {
        GlobalScope.launch(Dispatchers.Main) {
            coroutine.text = GlobalScope.async(Dispatchers.IO) {
                // 比如进行了网络请求
                // 放回了请求后的结构
                return@async "main"
            }.await()
        }
    }
}

9、协程并发[https://www.jianshu.com/p/3a97d87683d5]
  9.1 协程互斥锁：Mutex，类似于线程的 synchronized
      fun main(args: Array<String>) = runBlocking<Unit> {
         val mutex = Mutex()
         var counter = 0
         repeat(10000) {
            GlobalScope.launch {
               mutex.withLock {
                  counter ++
               }
            }
         }
         println("The final count is $counter")
      }
  9.2 协程局部数据
      线程中可以使用ThreadLocal作为线程局部数据，每个线程中的数据都是独立的。
      协程中可以通过ThreadLocal.asContextElement()扩展函数实现协程局部数据，每次协程切换会恢复之前的值。
fun main(args: Array<String>) = runBlocking<Unit> {
val threadLocal = ThreadLocal<String>().apply { set("Init") }
printlnValue(threadLocal)
val job = GlobalScope.launch(threadLocal.asContextElement("launch")) {
printlnValue(threadLocal)
threadLocal.set("launch changed")
printlnValue(threadLocal)
yield()
printlnValue(threadLocal)
}
job.join()
printlnValue(threadLocal)
}

private fun printlnValue(threadLocal: ThreadLocal<String>) {
println("${Thread.currentThread()} thread local value: ${threadLocal.get()}")
}