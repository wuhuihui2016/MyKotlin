package com.whh.mykotlin.langbase

import com.whh.mykotlin.langbase.TestJvmStatic.Companion.printlnMsg
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

suspend fun main() {

    //创建线程方法一
    var myThread = MyThread()
    myThread.start()

    //创建线程方法二
    Thread {
        printlnMsg("running from Thread2")
    }.start()

    //创建线程方法三
    thread(start = true) {
        printlnMsg("running from thread3")
    }

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
    printlnMsg("GlobalScope.launch: 111")
    var job = GlobalScope.launch {
        printlnMsg("GlobalScope.launch: 222") //currentThread=DefaultDispatcher-worker-1
        var context = Job() + Dispatchers.IO + CoroutineName("Hello")
        printlnMsg("GlobalScope.launch: 222  $context, ${context[CoroutineName]}")  //[JobImpl{Active}@3e838002, CoroutineName(Hello), Dispatchers.IO], CoroutineName(Hello)
        context = context.minusKey(Job)
        printlnMsg("GlobalScope.launch: 222  $context") //[CoroutineName(Hello), Dispatchers.IO]
    }
    printlnMsg("GlobalScope.launch: 333")
//    job.join()
    printlnMsg("GlobalScope.launch: 444")

    printlnMsg("===========================")
    printlnMsg("===========================")
    //[https://www.jianshu.com/p/29a9503187cb]
    // 通过 launch 启动了一个协程，为它指定了我们自己的拦截器作为上下文，紧接着在其中用 async 启动了一个协程，
    //async 与 launch 从功能上是同等类型的函数，它们都被称作协程的 Builder 函数，
    //不同之处在于 async 启动的 Job 也就是实际上的 Deferred 可以有返回结果，可以通过 await 方法获取。
    //所有协程启动的时候，都会有一次 Continuation.resumeWith 的操作
    GlobalScope.launch(MyContinuationInterceptor()) {
        printlnMsg(1)
        val job = async {
            printlnMsg(2)
            delay(1000)
            printlnMsg(3)
            "Hello"
        }
        printlnMsg(4)
        val result = job.await()
        printlnMsg("5. $result")
    }.join()
    printlnMsg(6)

    yieldTest()
    delayTest()
    mutexTest()
}

/**
 * yield()的作用是挂起当前协程，然后将协程分发到 Dispatcher 的队列，这样可以让该协程所在线程或线程池可以运行其他协程逻辑，
 * 然后在 Dispatcher 空闲的时候继续执行原来协程。简单的来说就是让出自己的执行权，给其他协程使用，当其他协程执行完成或也让出执行权时，
 * 一开始的协程可以恢复继续运行。
 *
 * 实现 job1 和 job2 两个协程交替运行
 */
fun yieldTest() {
    printlnMsg("yieldTest...")
    GlobalScope.launch {
        repeat(3) {
            println("job1 repeat $it times")
            yield()
        }
    }
    GlobalScope.launch {
        repeat(3) {
            println("job2 repeat $it times")
            yield()
        }
    }
}

/**
 * delay 使用suspendCancellableCoroutine挂起协程，而协程恢复的一般情况下是关键在DefaultExecutor.scheduleResumeAfterDelay()，
 * 其中实现是schedule(DelayedResumeTask(timeMillis, continuation))，其中的关键逻辑是将 DelayedResumeTask 放到 DefaultExecutor 的队列最后，
 * 在延迟的时间到达就会执行 DelayedResumeTask
 */
fun delayTest() {
    printlnMsg("relayTest...")
    GlobalScope.launch {
        repeat(3) {
            println("job1 repeat $it times")
            delay(200)
        }
    }
    GlobalScope.launch {
        repeat(3) {
            println("job2 repeat $it times")
            delay(200)
        }
    }
}

/**
 * 协程互斥锁
 */
fun mutexTest() = runBlocking<Unit> {
    printlnMsg("mutexTest...")
    val mutex = Mutex()
    var counter = 0
    repeat(100) {
        GlobalScope.launch {
            mutex.withLock {
                counter++
            }
        }
    }
    println("The final count is $counter") //每次运行结果不一致
}

/**
 * 自定义拦截器
 */
class MyContinuationInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>) =
        MyContinuation(continuation)
}

class MyContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {
    override val context = continuation.context
    override fun resumeWith(result: Result<T>) {
        printlnMsg("<MyContinuation> $result")
        continuation.resumeWith(result)
    }
}

/**
 * 创建线程方法一
 */
class MyThread : Thread() {
    override fun run() {
        printlnMsg("running from Thread1")
    }
}


