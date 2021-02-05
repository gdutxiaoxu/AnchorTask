package com.xj.anchortask.library

import android.os.Process
import androidx.annotation.CallSuper
import androidx.annotation.IntRange
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch

/**
 * Created by jun xu on 2/1/21.
 */
interface IAnchorTask : IAnchorCallBack {

    /**
     * 是否在主线程执行
     */
    fun isRunOnMainThread(): Boolean

    /**
     * 任务优先级别
     */
    @IntRange(
        from = Process.THREAD_PRIORITY_FOREGROUND.toLong(),
        to = Process.THREAD_PRIORITY_LOWEST.toLong()
    )
    fun priority(): Int

    /**
     * 调用 await 方法，是否需要等待改任务执行完成
     * true 不需要
     * false 需要
     */
    fun needWait(): Boolean

    /**
     * 当前任务的前置任务，可以用来确定顶点的入度
     */
    fun getDependsTaskList(): List<Class<out AnchorTask>>?

    /**
     * 任务被执行的时候回调
     */
    fun run()

}

interface IAnchorCallBack {
    fun onAdd()
    fun onRemove()
    fun onStart()
    fun onFinish()
}

abstract class AnchorTask : IAnchorTask {

    companion object {
        const val TAG = "AnchorTask"
    }

    private val countDownLatch: CountDownLatch = CountDownLatch(getListSize())
    private val copyOnWriteArrayList: CopyOnWriteArrayList<IAnchorCallBack> by lazy {
        CopyOnWriteArrayList<IAnchorCallBack>()
    }


    private fun getListSize() = getDependsTaskList()?.size ?: 0

    override fun priority(): Int {
        return Process.THREAD_PRIORITY_FOREGROUND
    }

    override fun needWait(): Boolean {
        return true
    }

    /**
     * self call,await
     */
    fun await() {
        countDownLatch.await()
    }

    /**
     * parent call, countDown
     */
    fun countdown() {
        countDownLatch.countDown()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun getDependsTaskList(): List<Class<out AnchorTask>>? {
        return null
    }

    @CallSuper
    override fun onAdd() {
        copyOnWriteArrayList.forEach {
            it.onAdd()
        }
    }

    @CallSuper
    override fun onStart() {
        copyOnWriteArrayList.forEach {
            it.onStart()
        }
    }

    override fun onRemove() {
        copyOnWriteArrayList.forEach {
            it.onRemove()
        }
    }

    @CallSuper
    override fun onFinish() {
        copyOnWriteArrayList.forEach {
            it.onFinish()
        }
    }

    fun addCallback(iAnchorCallBack: IAnchorCallBack?) {
        iAnchorCallBack ?: return
        copyOnWriteArrayList.add(iAnchorCallBack)
    }

    fun removeCallback(iAnchorCallBack: IAnchorCallBack?) {
        iAnchorCallBack ?: return
        copyOnWriteArrayList.remove(iAnchorCallBack)
    }

}