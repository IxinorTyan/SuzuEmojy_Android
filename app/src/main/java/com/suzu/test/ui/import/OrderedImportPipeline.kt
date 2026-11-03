package com.suzu.test.ui.import

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import java.util.ArrayDeque

/** Bounds decoded-image memory and preserves source order even when preparation finishes out of order. */
internal suspend fun <T, R> forEachPreparedInOrder(
    items: List<T>,
    parallelism: Int = 2,
    parallelismLimit: () -> Int = { parallelism },
    prepare: suspend (T) -> R,
    consume: suspend (index: Int, item: T, result: Result<R>) -> Unit
) = coroutineScope {
    require(parallelism > 0)
    var next = 0
    fun enqueue(index: Int) = async(Dispatchers.IO) {
        try {
            Result.success(prepare(items[index]))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    val pending = ArrayDeque<Pair<Int, Deferred<Result<R>>>>()
    fun fill() {
        val limit = parallelismLimit().coerceIn(1, parallelism)
        while (pending.size < limit && next < items.size) {
            pending.addLast(next to enqueue(next))
            next++
        }
    }
    fill()
    while (pending.isNotEmpty()) {
        ensureActive()
        val (index, task) = pending.removeFirst()
        consume(index, items[index], task.await())
        // A reduced memory/thermal limit drains existing work before starting more.
        fill()
    }
}
