package com.community.jetpack.paging.local.db


import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * 在一个专用的后台线程上运行块的实用方法，用于io/数据库工作。
 */
fun ioThread(f : () -> Unit) {
    IO_EXECUTOR.execute(f)
}