package com.xj.anchortask.asyncInflate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xj.anchortask.R

class AsyncActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val asyncInflateItem =
            AsyncInflateItem(
                LaunchInflateKey.LAUNCH_ACTIVITY,
                R.layout.activity_async,
                null,
                null
            )
        AsyncInflateManager.instance.asyncInflate(this, asyncInflateItem)
        Thread.sleep(2)
        val inflatedView = AsyncInflateManager.instance.getInflatedView(
            this,
            R.layout.activity_async,
            null,
            LaunchInflateKey.LAUNCH_ACTIVITY,
            layoutInflater
        )
        setContentView(inflatedView)
    }

    override fun onDestroy() {
        super.onDestroy()
        AsyncInflateManager.instance.remove(LaunchInflateKey.LAUNCH_ACTIVITY)
    }
}