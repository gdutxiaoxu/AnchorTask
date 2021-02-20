package com.xj.anchortask.library

import android.os.Handler
import android.os.Looper

object ThreadUtils {

    fun runOnUiThread(r: Runnable) {
        if (isMainThread) {
            r.run()
        } else {
            LazyHolder.sUiThreadHandler.post(r)
        }
    }

    fun runOnUiThreadAtFront(r: Runnable) {
        if (isMainThread) {
            r.run()
        } else {
            LazyHolder.sUiThreadHandler.postAtFrontOfQueue(r)
        }
    }

    fun runOnUiThread(r: Runnable?, delay: Long) {
        LazyHolder.sUiThreadHandler.postDelayed(r!!, delay)
    }

    fun removeCallbacks(r: Runnable?) {
        LazyHolder.sUiThreadHandler.removeCallbacks(r!!)
    }

    val isMainThread: Boolean
        get() = Looper.getMainLooper() == Looper.myLooper()

    fun checkAtMainThread() {
        val e = RuntimeException("not main thread")
        LazyHolder.sUiThreadHandler.postDelayed({ throw e }, 500)
    }

    private object LazyHolder {
        val sUiThreadHandler =
            Handler(Looper.getMainLooper())
    }
}