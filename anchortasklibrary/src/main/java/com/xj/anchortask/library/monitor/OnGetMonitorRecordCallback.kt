/*
 * Copyright 2018 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xj.anchortask.library.monitor

/**
 *
 * 获取`Project`执行性能记录的回调
 *
 */
interface OnGetMonitorRecordCallback {
    /**
     * 获取`task`执行的耗时。
     * @param result `task`执行的耗时。`key`是`task`名称，`value`是`task`执行耗时，单位是毫秒。
     */
    fun onGetTaskExecuteRecord(result: Map<String?, Long?>?)

    /**
     * 获取整个`Project`执行耗时。
     * @param costTime 整个`Project`执行耗时。
     */
    fun onGetProjectExecuteTime(costTime: Long)
}