package com.xj.anchortask

import android.app.Application
import android.util.Log
import com.xj.anchortask.anchorTask.TestTaskUtils
import com.xj.anchortask.asyncInflate.page.AsyncUtils
import com.xj.anchortask.library.OnProjectExecuteListener
import com.xj.anchortask.library.monitor.OnGetMonitorRecordCallback

/**
 * Created by jun xu on 2/1/21.
 */
class MyApplication : Application() {


    companion object {
        const val TAG = "MyApplication"

        lateinit var myApplication: MyApplication
            private set

        @JvmStatic
        fun getInstance(): Application {
            return myApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
        AsyncUtils.asyncInflate(applicationContext)
        TestTaskUtils.executeTask(this, projectExecuteListener = object : OnProjectExecuteListener {
            override fun onProjectFinish() {

            }

            override fun onProjectStart() {

            }

            override fun onTaskFinish(taskName: String) {

            }

        }, onGetMonitorRecordCallback = object : OnGetMonitorRecordCallback {
            override fun onGetProjectExecuteTime(costTime: Long) {
                Log.i(TAG, "onGetProjectExecuteTime: costTime is $costTime")

            }

            override fun onGetTaskExecuteRecord(result: Map<String?, Long?>?) {
                Log.i(TAG, "onGetTaskExecuteRecord: result is $result")
            }

        })

    }


}