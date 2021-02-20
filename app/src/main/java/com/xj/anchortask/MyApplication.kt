package com.xj.anchortask

import android.app.Application
import com.xj.anchortask.library.AnchorProject

import com.xj.anchortask.library.IAnchorCallBack
import com.xj.anchortask.library.OnProjectExecuteListener
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

    }


}