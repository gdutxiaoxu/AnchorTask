package com.xj.anchortask

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by jun xu on 2/2/21.
 */
object LogUtils {

    enum class LogLevel {
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

    private var TAG = "AnchorTask"

    var logLevel = LogLevel.DEBUG // 日志的等级，可以进行配置，最好在Application中进行全局的配置

    var printMsgStack = false

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
                val s = getMethodNames()
                Log.e("${TAG}_${tag}", String.format(s, msg))
            }
        }
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        if (LogLevel.WARN.value <= logLevel.value) {
            if (msg.isNotBlank()) {
                val s = getMethodNames()
                Log.e("${TAG}_${tag}", String.format(s, msg))
            }
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (LogLevel.INFO.value <= logLevel.value) {
            if (msg.isNotBlank()) {
                val s = getMethodNames()
                Log.i("${TAG}_${tag}", String.format(s, msg))
            }

        }
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        if (LogLevel.DEBUG.value <= logLevel.value) {
            if (msg.isNotBlank()) {
                val s = getMethodNames()
                Log.d("${TAG}_${tag}", String.format(s, msg))
            }
        }
    }

    @JvmStatic
    fun json(tag: String, json: String) {
        var json = json
        if (json.isBlank()) {
            d(tag, "Empty/Null json content")
            return
        }

        try {
            json = json.trim { it <= ' ' }
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
                return
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
                return
            }
            e(tag, "Invalid Json")
        } catch (e: JSONException) {
            e(tag, "Invalid Json")
        }

    }

    private fun getMethodNames(): String {
        if (!printMsgStack) {
            return "%s"
        }
        val sElements = Thread.currentThread().stackTrace

        var stackOffset = LoggerPrinter.getStackOffset(sElements)

        stackOffset++
        val builder = StringBuilder()
        builder.append(LoggerPrinter.TOP_BORDER).append("\r\n")
            // 添加当前线程名
            .append("║ " + "Thread: " + Thread.currentThread().name).append("\r\n")
            .append(LoggerPrinter.MIDDLE_BORDER).append("\r\n")
            // 添加类名、方法名、行数
            .append("║ ")
            .append(sElements[stackOffset].className)
            .append(".")
            .append(sElements[stackOffset].methodName)
            .append(" ")
            .append(" (")
            .append(sElements[stackOffset].fileName)
            .append(":")
            .append(sElements[stackOffset].lineNumber)
            .append(")")
            .append("\r\n")
            .append(LoggerPrinter.MIDDLE_BORDER).append("\r\n")
            // 添加打印的日志信息
            .append("║ ").append("%s").append("\r\n")
            .append(LoggerPrinter.BOTTOM_BORDER).append("\r\n")
        return builder.toString()
    }

}