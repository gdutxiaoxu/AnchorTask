

package com.xj.anchortask.library


import java.util.ArrayDeque

/**
 * Created by jun xu on 2/1/21.
 */
object AnchorTaskUtils {

    @JvmStatic
    fun getSortResult(
        list: MutableList<AnchorTask>, taskMap: MutableMap<Class<out AnchorTask>, AnchorTask>,
        taskChildMap: MutableMap<Class<out AnchorTask>, ArrayList<Class<out AnchorTask>>?>
    ): MutableList<AnchorTask> {
        val result = ArrayList<AnchorTask>()
        // 入度为 0 的队列
        val queue = ArrayDeque<AnchorTask>()
        val taskIntegerHashMap = HashMap<Class<out AnchorTask>, Int>()

        // 建立每个 task 的入度关系
        list.forEach { anchorTask: AnchorTask ->
            val clz = anchorTask.javaClass
            if (taskIntegerHashMap.containsKey(clz)) {
                throw AnchorTaskException("anchorTask is repeat, anchorTask is $anchorTask, list is $list")
            }

            val size = anchorTask.getDependsTaskList()?.size ?: 0
            taskIntegerHashMap[clz] = size
            taskMap[clz] = anchorTask
            if (size == 0) {
                queue.offer(anchorTask)
            }
        }

        // 建立每个 task 的 children 关系
        list.forEach { anchorTask: AnchorTask ->
            anchorTask.getDependsTaskList()?.forEach { clz: Class<out AnchorTask> ->
                var list = taskChildMap[clz]
                if (list == null) {
                    list = ArrayList<Class<out AnchorTask>>()
                }
                list.add(anchorTask.javaClass)
                taskChildMap[clz] = list
            }
        }

        // 使用 BFS 方法获得有向无环图的拓扑排序
        while (!queue.isEmpty()) {
            val anchorTask = queue.pop()
            result.add(anchorTask)
            val clz = anchorTask.javaClass
            taskChildMap[clz]?.forEach { // 遍历所有依赖这个顶点的顶点，移除该顶点之后，如果入度为 0，加入到改队列当中
                var result = taskIntegerHashMap[it] ?: 0
                result--
                if (result == 0) {
                    queue.offer(taskMap[it])
                }
                taskIntegerHashMap[it] = result
            }
        }

        // size 不相等，证明有环
        if (list.size != result.size) {
            throw AnchorTaskException("Ring appeared，Please check.list is $list, result is $result")
        }

        return result

    }
}