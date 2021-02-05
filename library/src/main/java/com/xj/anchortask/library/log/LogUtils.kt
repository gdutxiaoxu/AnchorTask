package com.xj.anchortask.library.log

import android.util.Log

/**
 * Created by jun xu on 2/2/21.
 */
object LogUtils {

    enum class LogLevel {
        NONE {
            override val value: Int
                get() = -1
        },

        ERROR {
            override val value: Int
                get() = 0
        },
        WARN {
            override val value: Int
                get() = 1
        },
        INFO {
            override val value: Int
                get() = 2
        },
        DEBUG {
            override val value: Int
                get() = 3
        };


        abstract val value: Int
    }

    private var TAG = "SAF_L"

    var logLevel = LogLevel.INFO // 日志的等级，可以进行配置，最好在Application中进行全局的配置

    @JvmStatic
    fun init(clazz: Class<*>) {
        TAG = clazz.simpleName
    }

    /**
     * 支持用户自己传tag，可扩展性更好
     * @param tag
     */
    @JvmStatic
    fun init(tag: String) {
        TAG = tag
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        if (LogLevel.ERROR.value <= logLevel.value) {
            if (msg.isNotBlank()) {
                Log.e("$TAG$tag", msg)
            }
        }
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        if (LogLevel.WARN.value <= logLevel.value) {
            if (msg.isNotBlank()) {
                Log.e("$TAG$tag", msg)
            }
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (LogLevel.INFO.value <= logLevel.value) {
            if (msg.isNotBlank()) {
                Log.i("$TAG$tag", msg)
            }

        }
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        if (LogLevel.DEBUG.value <= logLevel.value) {
            if (msg.isNotBlank()) {
                Log.d("$TAG$tag", msg)
            }
        }
    }


}