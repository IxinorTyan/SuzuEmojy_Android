package com.suzu.test.ui.import

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/** Coordinates native decode/encode memory across workers, including oversized images. */
internal class ImportPreparationBudget(private val capacityBytes: Long) {
    init { require(capacityBytes > 0) }
    private val mutex = Mutex()
    private var reservedBytes = 0L
    private var changed = CompletableDeferred<Unit>()

    suspend fun <T> withReservation(estimatedBytes: Long, action: suspend () -> T): T {
        // An image larger than the entire allowance runs alone; it does not block forever.
        val reservation = estimatedBytes.coerceIn(1, capacityBytes)
        while (true) {
            val waitForChange = mutex.withLock {
                if (reservation <= capacityBytes - reservedBytes) {
                    reservedBytes += reservation
                    null
                } else changed
            }
            if (waitForChange == null) break
            waitForChange.await()
        }
        try {
            return action()
        } finally {
            // Releasing must work after cancellation while another worker holds the mutex.
            kotlinx.coroutines.withContext(kotlinx.coroutines.NonCancellable) {
                mutex.withLock {
                    reservedBytes -= reservation
                    val previous = changed
                    changed = CompletableDeferred()
                    previous.complete(Unit)
                }
            }
        }
    }
}
