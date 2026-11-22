package com.suzu.test.resource.lansync

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.atomic.AtomicInteger

/** Keep result order without launching one coroutine per file. */
internal suspend fun <T, R> List<T>.mapSyncFiles(workers: Int = 4, work: suspend (T) -> R): List<R> {
    val count = minOf(workers, maxOf(1, Runtime.getRuntime().availableProcessors() / 2))
    if (size < 8 || count == 1) return map { work(it) }
    return coroutineScope {
        val next = AtomicInteger()
        val results = arrayOfNulls<Any>(size)
        List(count) {
            async(Dispatchers.IO) {
                while (true) {
                    val i = next.getAndIncrement()
                    if (i >= size) break
                    results[i] = work(this@mapSyncFiles[i])
                }
            }
        }.awaitAll()
        @Suppress("UNCHECKED_CAST")
        results.map { it as R }
    }
}
