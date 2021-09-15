package com.xj.anchortask.library

import android.content.Context
import com.xj.anchortask.library.log.LogUtils
import com.xj.anchortask.library.monitor.ExecuteMonitor
import com.xj.anchortask.library.monitor.OnGetMonitorRecordCallback
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by jun xu on 2/1/21.
 */
class AnchorProject private constructor(val builder: Builder) {

    init {
        LogUtils.init("AnchorTaskLibrary")
    }

    private val threadPoolExecutor: ThreadPoolExecutor =
        builder.threadPoolExecutor ?: TaskExecutorManager.instance.cpuThreadPoolExecutor

    // 存储所有的任务,key 是 taskName，value 是 AnchorTask
    private val taskMap: MutableMap<String, AnchorTask> = builder.taskMap

    // 储存当前任务的子任务， key 是当前任务的 taskName，value 是 AnchorTask 的 list
    private val taskChildMap: MutableMap<String, ArrayList<AnchorTask>?> = builder.taskChildMap

    //需要等待的任务总数，用于阻塞
    private val countDownLatch: CountDownLatch = builder.countDownLatch

    // 拓扑排序之后的主线程任务
    private val mainList = builder.mainList

    // 拓扑排序之后的子线程任务
    private val threadList = builder.threadList

    private val totalTaskSize = builder.list.size
    private val finishTask = AtomicInteger(0)

    private val listeners: CopyOnWriteArrayList<OnProjectExecuteListener> =
        CopyOnWriteArrayList()

    private var iAnchorTaskCreator: IAnchorTaskCreator? = null
    private var cacheTask: AnchorTask? = null
    private val executeMonitor: ExecuteMonitor =
        ExecuteMonitor()


    var onGetMonitorRecordCallback: OnGetMonitorRecordCallback? = null

    fun addListener(onProjectExecuteListener: OnProjectExecuteListener) {
        listeners.add(onProjectExecuteListener)
    }

    fun removeListener(onProjectExecuteListener: OnProjectExecuteListener) {
        listeners.remove(onProjectExecuteListener)
    }

    fun record(taskName: String, executeTime: Long) {
        executeMonitor.record(taskName, executeTime)
    }


    /**
     *  通知 child countdown,当前的阻塞任务书也需要 countdown
     */
    fun setNotifyChildren(anchorTask: AnchorTask) {
        taskChildMap[anchorTask.getTaskName()]?.forEach {
            taskMap[it.getTaskName()]?.countdown()
        }
        if (anchorTask.needWait()) {
            countDownLatch.countDown()
        }
        listeners.forEach {
            it.onTaskFinish(anchorTask.getTaskName())
        }
        finishTask.incrementAndGet()

        if (finishTask.get() == totalTaskSize) {
            executeMonitor.recordProjectFinish()
            ThreadUtils.runOnUiThread(Runnable {
                onGetMonitorRecordCallback?.onGetProjectExecuteTime(executeMonitor.projectCostTime)
                onGetMonitorRecordCallback?.onGetTaskExecuteRecord(executeMonitor.executeTimeMap)
            })

            listeners.forEach {
                it.onProjectFinish()
            }
        }
    }

    fun start(): AnchorProject {
        executeMonitor.recordProjectStart()
        this.listeners.forEach {
            it.onProjectStart()
        }

        this.threadList.forEach {
            threadPoolExecutor.execute(AnchorTaskRunnable(this, anchorTask = it))
        }

        this.mainList.forEach {
            AnchorTaskRunnable(this, anchorTask = it).run()
        }
        return this
    }

    fun await(timeOutMillion: Long = -1) {
        if (timeOutMillion > 0) {
            countDownLatch.await(timeOutMillion, TimeUnit.MILLISECONDS)
        } else {
            countDownLatch.await()
        }
    }


    class Builder constructor() {


        companion object {
            private const val TAG = "AnchorTaskDispatcher"
        }


        var threadPoolExecutor: ThreadPoolExecutor? = null
            private set

        private lateinit var context: Context
        private var logLevel: LogUtils.LogLevel = LogUtils.logLevel
        private var timeOutMillion: Long = -1

        // 存储所有的任务
        val list: MutableList<AnchorTask> = ArrayList()

        // 存储所有的任务,key 是 taskName，value 是 AnchorTask
        val taskMap: MutableMap<String, AnchorTask> = HashMap()

        // 储存当前任务的子任务， key 是当前任务的 taskName，value 是 AnchorTask 的 list
        val taskChildMap: MutableMap<String, ArrayList<AnchorTask>?> = HashMap()

        // 拓扑排序之后的主线程任务
        val mainList: MutableList<AnchorTask> = ArrayList()

        // 拓扑排序之后的子线程任务
        val threadList: MutableList<AnchorTask> = ArrayList()

        //需要等待的任务总数，用于阻塞
        lateinit var countDownLatch: CountDownLatch

        //需要等待的任务总数，用于CountDownLatch
        private val needWaitCount: AtomicInteger = AtomicInteger()

        private var startTime = -1L

        private val listeners: CopyOnWriteArrayList<OnProjectExecuteListener> =
            CopyOnWriteArrayList()

        private var iAnchorTaskCreator: IAnchorTaskCreator? = null
        private val anchorTaskWrapper: TaskCreatorWrap = TaskCreatorWrap(iAnchorTaskCreator)

        private var cacheTask: AnchorTask? = null

        fun setAnchorTaskCreator(iAnchorTaskCreator: IAnchorTaskCreator): Builder {
            this.iAnchorTaskCreator = iAnchorTaskCreator
            anchorTaskWrapper.iAnchorTaskCreator = iAnchorTaskCreator
            return this
        }

        fun setContext(context: Context): Builder {
            this.context = context
            return this
        }

        fun setLogLevel(logLevel: LogUtils.LogLevel): Builder {
            this.logLevel = logLevel
            LogUtils.logLevel = logLevel
            return this
        }

        fun setThreadPoolExecutor(threadPoolExecutor: ThreadPoolExecutor?): Builder {
            this.threadPoolExecutor = threadPoolExecutor
            return this
        }

        fun setTimeOutMillion(timeOutMillion: Long): Builder {
            this.timeOutMillion = timeOutMillion
            return this
        }

        fun addTask(taskName: String): Builder {
            val createTask = anchorTaskWrapper.createTask(taskName)
            createTask ?: let {
                throw AnchorTaskException("could not find anchorTask, taskName is $taskName")
            }
            return addTask(anchorTask = createTask)
        }

        fun afterTask(vararg taskNames: String): Builder {
            cacheTask ?: let {
                throw AnchorTaskException("should be call addTask first")
            }

            taskNames.forEach { taskName ->
                val createTask = anchorTaskWrapper.createTask(taskName)
                createTask ?: let {
                    throw AnchorTaskException("could not find anchorTask, taskName is $taskName")
                }

                cacheTask?.afterTask(taskName)
            }

            return this

        }

        fun afterTask(vararg anchorTasks: AnchorTask): Builder {
            cacheTask ?: let {
                throw AnchorTaskException("should be call addTask first")
            }
            anchorTasks.forEach {
                cacheTask?.afterTask(it.getTaskName())
            }

            return this
        }

        fun addTask(anchorTask: AnchorTask): Builder {
            cacheTask = anchorTask
            list.add(anchorTask)
            anchorTask.onAdd()
            if (anchorTask.needWait()) {
                needWaitCount.incrementAndGet()
            }
            return this
        }


        fun build(): AnchorProject {
            val sortResult = AnchorTaskUtils.getSortResult(list, taskMap, taskChildMap)
            LogUtils.d(TAG, "start: sortResult is $sortResult")
            sortResult.forEach {
                if (it.isRunOnMainThread()) {
                    mainList.add(it)
                } else {
                    threadList.add(it)
                }
            }

            countDownLatch = CountDownLatch(needWaitCount.get())
            return AnchorProject(this)
        }


    }
}


