package com.xj.anchortask

import android.app.Application
import com.xj.anchortask.library.AnchorTaskDispatcher
import com.xj.anchortask.library.IAnchorCallBack
import com.xj.anchortask.library.log.LogUtils

/**
 * Created by jun xu on 2/1/21.
 */
class MyApplication : Application() {

    companion object {
        const val TAG = "MyApplication"
    }

    override fun onCreate() {
        super.onCreate()
        val anchorTask = AnchorTaskTwo()
        anchorTask.addCallback(object : IAnchorCallBack {
            override fun onAdd() {
                com.xj.anchortask.LogUtils.i(TAG, "onAdd: $anchorTask")
            }

            override fun onRemove() {
                com.xj.anchortask.LogUtils.i(TAG, "onRemove: $anchorTask")
            }

            override fun onStart() {
                com.xj.anchortask.LogUtils.i(TAG, "onStart:$anchorTask ")
            }

            override fun onFinish() {
                com.xj.anchortask.LogUtils.i(TAG, "onFinish:$anchorTask ")
            }

        })
        AnchorTaskDispatcher.instance.setContext(this).setLogLevel(LogUtils.LogLevel.DEBUG)
            .addTask(AnchorTaskZero())
            .addTask(AnchorTaskOne())
            .addTask(anchorTask)
            .addTask(AnchorTaskThree())
            .addTask(AnchorTaskFour())
            .addTask(AnchorTaskFive())
            .start()
            .await()

    }
}