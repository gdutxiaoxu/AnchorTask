package com.xj.anchortask.library

import android.os.Process
import android.os.SystemClock

/**
 * Created by jun xu on 2/2/21.
 *
 */
class AnchorTaskRunnable(
    private val anchorProject: AnchorProject,
    private val anchorTask: AnchorTask
) : Runnable {

    override fun run() {
        Process.setThreadPriority(anchorTask.priority())
        //  前置任务没有执行完毕的话，等待，执行完毕的话，往下走
        anchorTask.await()
        anchorTask.onStart()
        // 执行任务
        val startTime = SystemClock.elapsedRealtime()
        anchorTask.run()
        val executeTime = SystemClock.elapsedRealtime() - startTime
        anchorProject.record(anchorTask.getTaskName(), executeTime)
        anchorTask.onFinish()
        // 通知子任务，当前任务执行完毕了，相应的计数器要减一。
        anchorProject.setNotifyChildren(anchorTask)
    }
}