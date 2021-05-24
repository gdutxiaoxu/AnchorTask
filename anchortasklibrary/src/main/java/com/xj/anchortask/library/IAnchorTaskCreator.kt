package com.xj.anchortask.library

/**
 * Created by jun xu on 2/9/21.
 */
open interface IAnchorTaskCreator {
    /**
     * 根据Task名称，创建Task实例。这个接口需要使用者自己实现。创建后的实例会被缓存起来。
     * @param taskName Task名称
     * @return  Task实例
     */
    fun createTask(taskName: String): AnchorTask?
}


open class TaskCreatorWrap(var iAnchorTaskCreator: IAnchorTaskCreator?) : IAnchorTaskCreator {

    private val map: MutableMap<String, AnchorTask?> = HashMap()

    override fun createTask(taskName: String): AnchorTask? {
        val anchorTask = map[taskName]
        anchorTask?.let {
            return it
        }
        return iAnchorTaskCreator?.createTask(taskName)
    }

    fun checkTaskIsExits(taskName: String): Boolean {
        return map.containsKey(taskName)
    }

}
