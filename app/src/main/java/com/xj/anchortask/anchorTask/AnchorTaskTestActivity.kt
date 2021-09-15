package com.xj.anchortask.anchorTask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xj.anchortask.R
import com.xj.anchortask.library.OnProjectExecuteListener
import com.xj.anchortask.library.monitor.OnGetMonitorRecordCallback
import kotlinx.android.synthetic.main.activity_anchortask_test.*

class AnchorTaskTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anchortask_test)
        initAnchorTask()
    }

    private fun initAnchorTask() {
        val sb2 = StringBuilder()
        text2.text = "正在执行中"
        val projectExecuteListener = object : OnProjectExecuteListener {
            val sb = StringBuffer()
            override fun onProjectStart() {
                if (sb.length > 0) {
                    sb.delete(0, sb.length)
                }

            }

            override fun onTaskFinish(taskName: String) {
                sb.append("task $taskName execute finish \n")
            }

            override fun onProjectFinish() {
                text.post {
                    text.setText(sb.toString())
                }
            }

        }
        val onGetMonitorRecordCallback = object : OnGetMonitorRecordCallback {


            override fun onGetTaskExecuteRecord(result: Map<String?, Long?>?) {
                result?.entries?.iterator()?.forEach {
                    sb2.append(it.key).append("执行耗时").append(it.value).append("毫秒\n")
                }
                text2.text = sb2.toString()
            }

            override fun onGetProjectExecuteTime(costTime: Long) {
                sb2.append("总共执行耗时").append(costTime).append("毫秒\n")
            }

        }
        btn_execute.setOnClickListener {
            sb2.clear()
            text2.text = "正在执行中"
            try {
                TestTaskUtils.executeTask(
                    this, projectExecuteListener, onGetMonitorRecordCallback
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        btn_execute2.setOnClickListener {
            sb2.clear()
            text2.text = "正在执行中2"
            try {
                TestTaskUtils.executeTask(
                    this, projectExecuteListener, onGetMonitorRecordCallback
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}