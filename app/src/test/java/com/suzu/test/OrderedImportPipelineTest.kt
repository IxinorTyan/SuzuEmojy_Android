package com.suzu.test

import com.suzu.test.ui.import.forEachPreparedInOrder
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue

class OrderedImportPipelineTest {
    @Test fun preparesTwoImagesConcurrentlyButCommitsInSourceOrder() = runBlocking {
        withTimeout(5_000) {
            val secondReady = CompletableDeferred<Unit>()
            val consumed = mutableListOf<Int>()
            forEachPreparedInOrder((0..9).toList(), prepare = { value ->
                if (value == 0) secondReady.await()
                if (value == 1) secondReady.complete(Unit)
                value * 10
            }) { index, item, result ->
                assertEquals(index, item)
                assertEquals(item * 10, result.getOrThrow())
                consumed.add(item)
            }
            assertEquals((0..9).toList(), consumed)
        }
    }

    @Test fun doesNotReadAnEntireFolderAheadOfTheWriter() = runBlocking {
        withTimeout(5_000) {
            val started = ConcurrentLinkedQueue<Int>()
            val twoStarted = CompletableDeferred<Unit>()
            val releaseWriter = CompletableDeferred<Unit>()
            val task = launch {
                forEachPreparedInOrder((0..999).toList(), prepare = { value ->
                    started.add(value)
                    if (started.size == 2) twoStarted.complete(Unit)
                    value
                }) { _, _, _ -> releaseWriter.await() }
            }
            twoStarted.await()
            assertEquals(setOf(0, 1), started.toSet())
            releaseWriter.complete(Unit)
            task.join()
            assertEquals(1_000, started.size)
        }
    }

    @Test fun oneUnreadableImageDoesNotCancelTheRest() = runBlocking {
        val results = mutableListOf<Result<Int>>()
        forEachPreparedInOrder(listOf(0, 1, 2), prepare = {
            if (it == 1) throw IOException("unreadable")
            it
        }) { _, _, result -> results.add(result) }
        assertEquals(0, results[0].getOrThrow())
        assertTrue(results[1].exceptionOrNull() is IOException)
        assertEquals(2, results[2].getOrThrow())
    }

    @Test fun cancellationStopsAndJoinsPrefetchWithoutCommitting() = runBlocking {
        withTimeout(5_000) {
            val entered = CompletableDeferred<Unit>()
            val finished = ConcurrentLinkedQueue<Int>()
            var consumed = false
            val task = launch {
                forEachPreparedInOrder((0..999).toList(), prepare = { value ->
                    try {
                        entered.complete(Unit)
                        awaitCancellation()
                    } finally { finished.add(value) }
                }) { _, _, _ -> consumed = true }
            }
            entered.await()
            task.cancelAndJoin()
            assertFalse(consumed)
            assertTrue(finished.isNotEmpty())
            assertTrue(finished.all { it < 2 })
        }
    }

    @Test fun writerFailureCancelsOtherInFlightPreparation() = runBlocking {
        withTimeout(5_000) {
            val preparingSecond = CompletableDeferred<Unit>()
            val stopped = CompletableDeferred<Unit>()
            try {
                forEachPreparedInOrder(listOf(0, 1, 2), prepare = { value ->
                    if (value == 0) preparingSecond.await()
                    else try {
                        preparingSecond.complete(Unit)
                        awaitCancellation()
                    } finally { stopped.complete(Unit) }
                    value
                }) { _, _, _ -> throw IOException("database failed") }
                throw AssertionError("Writer failure should escape")
            } catch (e: IOException) {
                assertEquals("database failed", e.message)
            }
            assertTrue(stopped.isCompleted)
        }
    }

    @Test fun reducingConcurrencyDrainsPrefetchBeforeStartingMoreImages() = runBlocking {
        withTimeout(5_000) {
            val started = ConcurrentLinkedQueue<Int>()
            val fourStarted = CompletableDeferred<Unit>()
            var limit = 4
            val consumed = mutableListOf<Int>()
            forEachPreparedInOrder((0..11).toList(), parallelism = 4, parallelismLimit = { limit }, prepare = { value ->
                started.add(value)
                if (started.size == 4) fourStarted.complete(Unit)
                value
            }) { index, _, result ->
                if (index == 0) {
                    fourStarted.await()
                    limit = 1
                }
                if (index < 4) assertEquals(setOf(0, 1, 2, 3), started.toSet())
                consumed.add(result.getOrThrow())
            }
            assertEquals((0..11).toList(), consumed)
        }
    }

    @Test fun dynamicLimitCanRecoverWithoutExceedingMeasuredParallelism() = runBlocking {
        withTimeout(5_000) {
            val started = ConcurrentLinkedQueue<Int>()
            val twoStarted = CompletableDeferred<Unit>()
            var limit = 1
            forEachPreparedInOrder((0..5).toList(), parallelism = 2, parallelismLimit = { limit }, prepare = { value ->
                started.add(value)
                if (started.size == 3) twoStarted.complete(Unit)
                value
            }) { index, _, _ ->
                if (index == 0) {
                    assertEquals(setOf(0), started.toSet())
                    limit = 99 // Caller cannot exceed the measured maximum of two.
                }
                if (index == 1) {
                    twoStarted.await()
                    assertEquals(setOf(0, 1, 2), started.toSet())
                }
            }
        }
    }
}
