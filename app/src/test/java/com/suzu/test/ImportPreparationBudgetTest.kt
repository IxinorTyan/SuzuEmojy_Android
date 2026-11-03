package com.suzu.test

import com.suzu.test.ui.import.ImportPreparationBudget
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ImportPreparationBudgetTest {
    @Test fun permitsSmallImagesTogetherButWaitsForEnoughMemoryForALargeImage() = runBlocking {
        withTimeout(5_000) {
            val budget = ImportPreparationBudget(10)
            val release = CompletableDeferred<Unit>()
            val first = async(start = CoroutineStart.UNDISPATCHED) { budget.withReservation(4) { release.await() } }
            val second = async(start = CoroutineStart.UNDISPATCHED) { budget.withReservation(4) { release.await() } }
            var entered = false
            val large = async(start = CoroutineStart.UNDISPATCHED) { budget.withReservation(7) { entered = true } }
            assertFalse(entered)
            release.complete(Unit)
            first.await()
            second.await()
            large.await()
            assertTrue(entered)
        }
    }

    @Test fun oversizedImageRunsExclusivelyAndDoesNotDeadlock() = runBlocking {
        withTimeout(5_000) {
            val budget = ImportPreparationBudget(10)
            val release = CompletableDeferred<Unit>()
            val large = async(start = CoroutineStart.UNDISPATCHED) { budget.withReservation(100) { release.await() } }
            var entered = false
            val small = async(start = CoroutineStart.UNDISPATCHED) { budget.withReservation(1) { entered = true } }
            assertFalse(entered)
            release.complete(Unit)
            large.await()
            small.await()
            assertTrue(entered)
        }
    }

    @Test fun cancelingActiveAndWaitingWorkersDoesNotLeakReservations() = runBlocking {
        withTimeout(5_000) {
            val budget = ImportPreparationBudget(10)
            val active = launch(start = CoroutineStart.UNDISPATCHED) { budget.withReservation(10) { awaitCancellation() } }
            val waiting = launch(start = CoroutineStart.UNDISPATCHED) { budget.withReservation(10) { error("Should be canceled while waiting") } }
            waiting.cancelAndJoin()
            active.cancelAndJoin()
            assertEquals("next image", budget.withReservation(10) { "next image" })
        }
    }

    @Test fun failedDecodeReleasesItsMemoryForTheNextImage() = runBlocking {
        val budget = ImportPreparationBudget(10)
        try {
            budget.withReservation(10) { throw IllegalArgumentException("bad image") }
        } catch (e: IllegalArgumentException) {
            assertEquals("bad image", e.message)
        }
        withTimeout(5_000) { assertEquals(42, budget.withReservation(10) { 42 }) }
    }
}
