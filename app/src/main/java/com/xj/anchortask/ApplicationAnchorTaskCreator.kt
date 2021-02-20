package com.xj.anchortask

import com.xj.anchortask.library.AnchorTask
import com.xj.anchortask.library.IAnchorTaskCreator

/**
 * Created by jun xu on 2/1/21.
 */

const val TASK_NAME_ZERO = "zero"
const val TASK_NAME_ONE = "one"
const val TASK_NAME_TWO = "two"
const val TASK_NAME_THREE = "three"
const val TASK_NAME_FOUR = "four"
const val TASK_NAME_FIVE = "five"

class ApplicationAnchorTaskCreator : IAnchorTaskCreator {
    override fun createTask(taskName: String): AnchorTask? {
        when (taskName) {
            TASK_NAME_ZERO -> {
                return AnchorTaskZero()
            }

            TASK_NAME_ONE -> {
                return AnchorTaskOne()
            }
            TASK_NAME_TWO -> {
                return AnchorTaskTwo()
            }
            TASK_NAME_THREE -> {
                return AnchorTaskThree()
            }
            TASK_NAME_FOUR -> {
                return AnchorTaskFour()
            }
            TASK_NAME_FIVE -> {
                return AnchorTaskFive()
            }
        }
        return null
    }

}

class AnchorTaskZero() : AnchorTask(TASK_NAME_ZERO) {
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

class AnchorTaskOne : AnchorTask(TASK_NAME_ONE) {
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

class AnchorTaskTwo : AnchorTask(TASK_NAME_TWO) {
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

class AnchorTaskThree : AnchorTask(TASK_NAME_THREE) {
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

}


class AnchorTaskFour : AnchorTask(TASK_NAME_FOUR) {
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

}

class AnchorTaskFive : AnchorTask(TASK_NAME_FIVE) {
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


}



