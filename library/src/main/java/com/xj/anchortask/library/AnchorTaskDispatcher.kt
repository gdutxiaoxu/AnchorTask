package com.xj.anchortask.library

import android.content.Context
import android.os.Looper
import com.xj.anchortask.library.log.LogUtils
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by jun xu on 2/1/21.
 */
class AnchorTaskDispatcher private constructor(
) {

    init {
        LogUtils.init("AnchorTaskLibrary")
    }

    private var threadPoolExecutor: ThreadPoolExecutor? = null

    private lateinit var context: Context
    private var logLevel: LogUtils.LogLevel = LogUtils.logLevel
    private var timeOutMillion: Long = -1

    // 存储所有的任务
    private val list: MutableList<AnchorTask> = ArrayList()

    // 存储所有的任务,key 是 Class<out AnchorTask>，value 是 AnchorTask
    private val taskMap: MutableMap<Class<out AnchorTask>, AnchorTask> = HashMap()

    // 储存当前任务的子任务， key 是当前任务的 class，value 是 AnchorTask 的 list
    private val taskChildMap: MutableMap<Class<out AnchorTask>, ArrayList<Class<out AnchorTask>>?> =
        HashMap()

    // 拓扑排序之后的主线程任务
    private val mainList: MutableList<AnchorTask> = ArrayList()

    // 拓扑排序之后的子线程任务
    private val threadList: MutableList<AnchorTask> = ArrayList()

    //需要等待的任务总数，用于阻塞
    private lateinit var countDownLatch: CountDownLatch

    //需要等待的任务总数，用于CountDownLatch
    private val needWaitCount: AtomicInteger = AtomicInteger()

    private var startTime = -1L

    companion object {

        private const val TAG = "AnchorTaskDispatcher"

        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AnchorTaskDispatcher()
        }

    }

    fun setContext(context: Context): AnchorTaskDispatcher {
        this.context = context
        return this
    }

    fun setLogLevel(logLevel: LogUtils.LogLevel): AnchorTaskDispatcher {
        this.logLevel = logLevel
        LogUtils.logLevel = logLevel
        return this
    }

    fun setThreadPoolExecutor(threadPoolExecutor: ThreadPoolExecutor?): AnchorTaskDispatcher {
        this.threadPoolExecutor = threadPoolExecutor
        return this
    }

    fun setTimeOutMillion(timeOutMillion: Long): AnchorTaskDispatcher {
        this.timeOutMillion = timeOutMillion
        return this
    }

    fun addTask(anchorTask: AnchorTask): AnchorTaskDispatcher {
        list.add(anchorTask)
        anchorTask.onAdd()
        if (anchorTask.needWait()) {
            needWaitCount.incrementAndGet()
        }
        return this
    }

    fun removeTask(anchorTask: AnchorTask): AnchorTaskDispatcher {
        list.remove(anchorTask)
        anchorTask.onRemove()
        if (anchorTask.needWait()) {
            needWaitCount.decrementAndGet()
        }
        return this
    }

    fun start(): AnchorTaskDispatcher {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw AnchorTaskException("start method should be call on main thread")
        }
        startTime = System.currentTimeMillis()

        val sortResult = AnchorTaskUtils.getSortResult(list, taskMap, taskChildMap)
        LogUtils.i(TAG, "start: sortResult is $sortResult")
        sortResult.forEach {
            if (it.isRunOnMainThread()) {
                mainList.add(it)
            } else {
                threadList.add(it)
            }
        }

        countDownLatch = CountDownLatch(needWaitCount.get())

        val threadPoolExecutor =
            this.threadPoolExecutor ?: TaskExecutorManager.instance.cpuThreadPoolExecutor

        threadList.forEach {
            threadPoolExecutor.execute(AnchorTaskRunnable(this, anchorTask = it))
        }

        mainList.forEach {
            AnchorTaskRunnable(this, anchorTask = it).run()
        }

        return this
    }

    fun await() {
        if (!this::countDownLatch.isInitialized) {
            throw AnchorTaskException("await method should be could after method start")
        }

        if (timeOutMillion > 0) {
            countDownLatch.await(timeOutMillion, TimeUnit.MILLISECONDS)
        } else {
            countDownLatch.await()
        }
        val timeInstance = System.currentTimeMillis() - startTime
        LogUtils.i(TAG, "await finish, timeInstance is $timeInstance")
    }

    /**
     *  通知 child countdown,当前的阻塞任务书也需要 countdown
     */
    fun setNotifyChildren(anchorTask: AnchorTask) {
        taskChildMap[anchorTask::class.java]?.forEach {
            taskMap[it]?.countdown()
        }
        if (anchorTask.needWait()) {
            countDownLatch.countDown()
        }
    }

}


