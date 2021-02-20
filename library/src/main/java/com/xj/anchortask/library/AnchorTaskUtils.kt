package com.xj.anchortask.library


import com.xj.anchortask.library.log.LogUtils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 * Created by jun xu on 2/1/21.
 */
object AnchorTaskUtils {

    @JvmStatic
    fun getSortResult(
        list: MutableList<AnchorTask>, taskMap: MutableMap<String, AnchorTask>,
        taskChildMap: MutableMap<String, ArrayList<AnchorTask>?>
    ): MutableList<AnchorTask> {
        val result = ArrayList<AnchorTask>()
        // 入度为 0 的队列
        val queue = ArrayDeque<AnchorTask>()
        val taskIntegerHashMap = HashMap<String, Int>()

        // 建立每个 task 的入度关系
        list.forEach { anchorTask: AnchorTask ->
            val taskName = anchorTask.getTaskName()
            if (taskIntegerHashMap.containsKey(taskName)) {
                throw AnchorTaskException("anchorTask is repeat, anchorTask is $anchorTask, list is $list")
            }

            val size = anchorTask.getDependsTaskList()?.size ?: 0
            taskIntegerHashMap[taskName] = size
            taskMap[taskName] = anchorTask
            if (size == 0) {
                queue.offer(anchorTask)
            }
        }

        // 建立每个 task 的 children 关系
        list.forEach { anchorTask: AnchorTask ->
            anchorTask.getDependsTaskList()?.forEach { taskName: String ->
                var list = taskChildMap[taskName]
                if (list == null) {
                    list = ArrayList<AnchorTask>()
                }
                list.add(anchorTask)
                taskChildMap[taskName] = list
            }
        }

        taskChildMap.entries.iterator().forEach {
            LogUtils.d("TAG","key is ${it.key}, value is ${it.value}")
        }


        // 使用 BFS 方法获得有向无环图的拓扑排序
        while (!queue.isEmpty()) {
            val anchorTask = queue.pop()
            result.add(anchorTask)
            val taskName = anchorTask.getTaskName()
            taskChildMap[taskName]?.forEach { // 遍历所有依赖这个顶点的顶点，移除该顶点之后，如果入度为 0，加入到改队列当中
                val key = it.getTaskName()
                var result = taskIntegerHashMap[key] ?: 0
                result--
                if (result == 0) {
                    queue.offer(it)
                }
                taskIntegerHashMap[key] = result
            }
        }

        // size 不相等，证明有环
        if (list.size != result.size) {
            throw AnchorTaskException("Ring appeared，Please check.list is $list, result is $result")
        }

        return result

    }
}