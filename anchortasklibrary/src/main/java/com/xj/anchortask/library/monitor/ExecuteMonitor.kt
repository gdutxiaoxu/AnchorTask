/*
 * Copyright 2018 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xj.anchortask.library.monitor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * 监控`Project`执行性能的类。会记录每一个`Task`执行时间，以及整个`Project`执行时间。
 *
 */
internal class ExecuteMonitor {
    private val mExecuteTimeMap: MutableMap<String?, Long?> = ConcurrentHashMap()
    private var mStartTime: Long = 0

    /**
     * @return `Project`执行时间。
     */
    var projectCostTime: Long = 0
        private set
    private var mHandler: Handler? = null

    /**
     * 记录`task`执行时间。
     *
     * @param taskName    `task`的名称
     * @param executeTime 执行的时间
     */
    fun record(taskName: String, executeTime: Long) {
//        AlphaLog.d(AlphaLog.GLOBAL_TAG, "AlphaTask-->Startup task %s cost time: %s ms, in thread: %s", taskName, executeTime, Thread.currentThread().getName());
//        if (executeTime >= AlphaConfig.getWarmingTime()) {
//            toastToWarn("AlphaTask %s run too long, cost time: %s", taskName, executeTime);
//        }
        mExecuteTimeMap[taskName] = executeTime
    }

    /**
     * @return 已执行完的每个task的执行时间。
     */
    val executeTimeMap: Map<String?, Long?>
        get() = mExecuteTimeMap

    /**
     * 在`Project`开始执行时打点，记录开始时间。
     */
    fun recordProjectStart() {
        mStartTime = System.currentTimeMillis()
    }

    /**
     * 在`Project`结束时打点，记录耗时。
     */
    fun recordProjectFinish() {
        projectCostTime = System.currentTimeMillis() - mStartTime
        //        AlphaLog.d("==ALPHA==", "tm start up cost time: %s ms", mProjectCostTime);
    }

    /**
     * 通过弹出`toast`来告警。
     *
     * @param msg  告警内容
     * @param args format参数
     */
    private fun toastToWarn(msg: String, vararg args: Any) {
        handler.post {
            val formattedMsg: String
            formattedMsg = if (args == null) {
                msg
            } else {
                String.format(msg, *args)
            }

//                    Toast.makeText(AlphaConfig.getContext(), formattedMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private val handler: Handler
        private get() {
            if (mHandler == null) {
                mHandler = Handler(Looper.getMainLooper())
            }
            return mHandler!!
        }
}