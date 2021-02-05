package com.xj.anchortask

import com.xj.anchortask.library.AnchorTask

/**
 * Created by jun xu on 2/1/21.
 */

class AnchorTaskZero : AnchorTask() {
    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun run() {
        val start = System.currentTimeMillis()
        try {
            Thread.sleep(300)
        } catch (e: Exception) {
        }
        LogUtils.i(
            TAG, "AnchorTaskOne: " + (System.currentTimeMillis() - start)
        )
    }
}

class AnchorTaskOne : AnchorTask() {
    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun run() {
        val start = System.currentTimeMillis()
        try {
            Thread.sleep(300)
        } catch (e: Exception) {
        }
        LogUtils.i(
            TAG, "AnchorTaskOne: " + (System.currentTimeMillis() - start)
        )
    }

}

class AnchorTaskTwo : AnchorTask() {
    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun run() {
        val start = System.currentTimeMillis()
        try {
            Thread.sleep(300)
        } catch (e: Exception) {
        }
        LogUtils.i(
            TAG, "AnchorTaskTwo执行耗时: " + (System.currentTimeMillis() - start)
        )
    }

}

class AnchorTaskThree : AnchorTask() {
    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun run() {
        val start = System.currentTimeMillis()
        try {
            Thread.sleep(300)
        } catch (e: Exception) {
        }
        LogUtils.i(
            TAG, "AnchorTaskThree执行耗时: " + (System.currentTimeMillis() - start)
        )
    }

    override fun getDependsTaskList(): List<Class<out AnchorTask>>? {
        return ArrayList<Class<out AnchorTask>>().apply {
            add(AnchorTaskZero::class.java)
            add(AnchorTaskOne::class.java)
        }
    }
}


class AnchorTaskFour : AnchorTask() {
    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun run() {
        val start = System.currentTimeMillis()
        try {
            Thread.sleep(300)
        } catch (e: Exception) {
        }
        LogUtils.i(
            TAG, "AnchorTaskFour执行耗时: " + (System.currentTimeMillis() - start)
        )
    }

    override fun getDependsTaskList(): List<Class<out AnchorTask>>? {
        return ArrayList<Class<out AnchorTask>>().apply {
            add(AnchorTaskOne::class.java)
            add(AnchorTaskTwo::class.java)
        }
    }
}

class AnchorTaskFive : AnchorTask() {
    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun run() {
        val start = System.currentTimeMillis()
        try {
            Thread.sleep(300)
        } catch (e: Exception) {
        }
        LogUtils.i(
            TAG, "AnchorTaskFive执行耗时: " + (System.currentTimeMillis() - start)
        )
    }

    override fun getDependsTaskList(): List<Class<out AnchorTask>>? {
        return ArrayList<Class<out AnchorTask>>().apply {
            add(AnchorTaskThree::class.java)
            add(AnchorTaskFour::class.java)
        }
    }
}


class AnchorTaskB : AnchorTask() {
    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun run() {
        val start = System.currentTimeMillis()
        try {
            Thread.sleep(300)
        } catch (e: Exception) {
        }
        com.xj.anchortask.library.log.LogUtils.i(
            TAG, "AnchorTaskOne: " + (System.currentTimeMillis() - start)
        )
    }

    override fun getDependsTaskList(): List<Class<out AnchorTask>>? {
        return ArrayList<Class<out AnchorTask>>().apply {
            add(AnchorTaskOne::class.java)
        }
    }

}

