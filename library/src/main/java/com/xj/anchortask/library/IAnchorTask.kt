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
     * 获取任务昵称
     */
    fun getTaskName(): String

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
     * 任务被执行的时候回调
     */
    fun run()

}

interface IAnchorCallBack {
    fun onAdd()
    fun onStart()
    fun onFinish()
}

class SimpleAnchorCallBack : IAnchorCallBack {
    override fun onAdd() {

    }


    override fun onStart() {

    }

    override fun onFinish() {

    }

}

abstract class AnchorTask(private val name: String) : IAnchorTask {

    companion object {
        const val TAG = "AnchorTask"
    }

    private lateinit var countDownLatch: CountDownLatch
    private val copyOnWriteArrayList: CopyOnWriteArrayList<IAnchorCallBack> by lazy {
        CopyOnWriteArrayList<IAnchorCallBack>()
    }

    val dependList: MutableList<String> = ArrayList()


    private fun getListSize() = getDependsTaskList()?.size ?: 0

    override fun getTaskName(): String {
        return name
    }

    override fun priority(): Int {
        return Process.THREAD_PRIORITY_FOREGROUND
    }

    override fun needWait(): Boolean {
        return true
    }

    fun afterTask(taskName: String) {
        dependList.add(taskName)
    }

    /**
     * self call,await
     */
    fun await() {
        tryToInitCountDown()
        countDownLatch.await()
    }

    private fun tryToInitCountDown() {
        if (!this::countDownLatch.isInitialized) {
            countDownLatch = CountDownLatch(dependList.size)
        }
    }

    /**
     * parent call, countDown
     */
    fun countdown() {
        tryToInitCountDown()
        countDownLatch.countDown()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }

    fun getDependsTaskList(): List<String>? {
        return dependList
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

    override fun toString(): String {
        return "AnchorTask(name='$name',dependList is $dependList)"
    }


}