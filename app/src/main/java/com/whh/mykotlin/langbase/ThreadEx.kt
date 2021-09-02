package com.whh.mykotlin.langbase

import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

suspend fun main() {

    //创建线程方法一
    var myThread = MyThread()
    myThread.start()

    //创建线程方法二
    Thread {
        printMsg("running from Thread2")
    }.start()

    //创建线程方法三
    thread(start = true) {
        printMsg("running from thread3")
    }

    printMsg("GlobalScope.launch: 111")
    //协程是一种并发的设计模式。
    //GlobalScope.launch 启动了一个运行在子线程的顶层协程
    //GlobalScope.launch的协程作用域不受限制, 即除非主进程退出, 否则只要该协程不结束就会占用资源;
    //这导致了如果协程的执行体中出现异常协程仍会占用资源而非释放. 最差的情况下有可能反复调用导致设备资源被占满宕机.
    //协程启动模式：
    /* DEFAULT	立即执行协程体
       ATOMIC	立即执行协程体，但在开始运行之前无法取消 ，
           此时调用Job.cancel()相当于Thread.interrupt(),并不会理会，只是将该 job 的状态置为 cancelling
       UNDISPATCHED	立即在当前线程执行协程体，直到第一个 suspend 调用
       LAZY	只有在需要的情况下运行
       常用：DEFAULT 和 LAZY */

    //调用 Job.start，主动触发协程的调度执行
    //调用 Job.join，隐式的触发协程的调度执行，
    //   和Thread.join()不一样，Thread.join()插入式立即执行，Job.join指不定什么时候执行

    //CoroutineName 为协程添加名称
    var job = GlobalScope.launch {
        printMsg("GlobalScope.launch: 222") //currentThread=DefaultDispatcher-worker-1
        var context = Job() + Dispatchers.IO + CoroutineName("Hello")
        printMsg("GlobalScope.launch: 222  $context, ${context[CoroutineName]}")  //[JobImpl{Active}@3e838002, CoroutineName(Hello), Dispatchers.IO], CoroutineName(Hello)
        context = context.minusKey(Job)
        printMsg("GlobalScope.launch: 222  $context") //[CoroutineName(Hello), Dispatchers.IO]
    }
    printMsg("GlobalScope.launch: 333")
    job.join()
    printMsg("GlobalScope.launch: 444")

    printMsg("===========================")
    printMsg("===========================")
    //[https://www.jianshu.com/p/29a9503187cb]
    // 通过 launch 启动了一个协程，为它指定了我们自己的拦截器作为上下文，紧接着在其中用 async 启动了一个协程，
    //async 与 launch 从功能上是同等类型的函数，它们都被称作协程的 Builder 函数，
    //不同之处在于 async 启动的 Job 也就是实际上的 Deferred 可以有返回结果，可以通过 await 方法获取。
    //所有协程启动的时候，都会有一次 Continuation.resumeWith 的操作
    GlobalScope.launch(MyContinuationInterceptor()) {
        printMsg(1)
        val job = async {
            printMsg(2)
            delay(1000)
            printMsg(3)
            "Hello"
        }
        printMsg(4)
        val result = job.await()
        printMsg("5. $result")
    }.join()
    printMsg(6)
}

/**
 * 自定义拦截器
 */
class MyContinuationInterceptor: ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>) = MyContinuation(continuation)
}

class MyContinuation<T>(val continuation: Continuation<T>): Continuation<T> {
    override val context = continuation.context
    override fun resumeWith(result: Result<T>) {
        printMsg("<MyContinuation> $result" )
        continuation.resumeWith(result)
    }
}

/**
 * 创建线程方法一
 */
class MyThread : Thread() {
    override fun run() {
        printMsg("running from Thread1")
    }
}

/**
 * 带时间输出信息
 */
fun printMsg(msg: Any?) {
    val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")
    val now = {
        dateFormat.format(Date(System.currentTimeMillis()))
    }
    println("${now()} [${Thread.currentThread().name}] $msg")
}