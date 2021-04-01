package com.xj.anchortask.viewStub

import ViewStubTaskManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xj.anchortask.R

class ViewStubDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stub_demo)
        val decorView = this.window.decorView
        ViewStubTaskManager.instance(decorView)
            .addTask(ViewStubTaskContent(decorView))
            .addTask(ViewStubTaskTitle(decorView))
            .addTask(ViewStubTaskBottom(decorView))
            .start()
    }


}