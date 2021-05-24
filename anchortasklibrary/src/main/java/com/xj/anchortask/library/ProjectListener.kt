package com.xj.anchortask.library

/**
 * Created by jun xu on 2/8/21.
 */
interface OnProjectExecuteListener {
    /**
     * 当`Project`开始执行时，调用该函数。<br></br>
     * **注意：**该回调函数在`Task`所在线程中回调，注意线程安全。
     */
    fun onProjectStart()

    /**
     * 当`Project`其中一个`Task`执行结束时，调用该函数。<br></br>
     * **注意：**该回调函数在`Task`所在线程中回调，注意线程安全。
     *
     * @param taskName 当前结束的`Task`名称
     */
    fun onTaskFinish(taskName: String)

    /**
     * 当`Project`执行结束时，调用该函数。<br></br>
     * **注意：**该回调函数在`Task`所在线程中回调，注意线程安全。
     */
    fun onProjectFinish()
}

