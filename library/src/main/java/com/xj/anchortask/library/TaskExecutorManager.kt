package com.xj.anchortask.library

import java.util.concurrent.*

class TaskExecutorManager private constructor() {
    //获得cpu密集型线程池,因为占据CPU的时间片过多的话会影响性能，所以这里控制了最大并发，防止主线程的时间片减少
    //CPU 密集型任务的线程池
    val cpuThreadPoolExecutor: ThreadPoolExecutor

    //获得io密集型线程池，有好多任务其实占用的CPU time非常少，所以使用缓存线程池,基本上来着不拒
    // IO 密集型任务的线程池
    val ioThreadPoolExecutor: ExecutorService

    //线程池队列
    private val mPoolWorkQueue: BlockingQueue<Runnable> =
        LinkedBlockingQueue()

    // 这个是为了保障任务超出BlockingQueue的最大值，且线程池中的线程数已经达到MAXIMUM_POOL_SIZE时候，还有任务到来会采取任务拒绝策略，这里定义的策略就是
    //再开一个缓存线程池去执行。当然BlockingQueue默认的最大值是int_max，所以理论上这里是用不到的
    private val mHandler =
        RejectedExecutionHandler { r, executor -> Executors.newCachedThreadPool().execute(r) }

    companion object {
        //CPU 核数
        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

        //线程池线程数
        private val CORE_POOL_SIZE = Math.max(
            2,
            Math.min(CPU_COUNT - 1, 5)
        )

        //线程池线程数的最大值
        private val MAXIMUM_POOL_SIZE = CORE_POOL_SIZE

        //线程空置回收时间
        private const val KEEP_ALIVE_SECONDS = 5

        @JvmStatic
        val instance: TaskExecutorManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TaskExecutorManager()
        }
    }

    //初始化线程池
    init {
        cpuThreadPoolExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS.toLong(),
            TimeUnit.SECONDS,
            mPoolWorkQueue,
            Executors.defaultThreadFactory(),
            mHandler
        ).apply {
            allowCoreThreadTimeOut(true)
        }
        ioThreadPoolExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory())
    }
}