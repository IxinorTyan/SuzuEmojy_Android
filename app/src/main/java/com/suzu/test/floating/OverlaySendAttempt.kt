package com.suzu.test.floating

/** Main-thread state: serialise preparation, not the later diagnostic observation window. */
internal class OverlaySendAttempt {
    private var sequence = 0L
    private var preparing = false

    fun begin(): Long? {
        if (preparing) return null
        preparing = true
        return ++sequence
    }

    fun submitted(attempt: Long) {
        if (isLatest(attempt)) preparing = false
    }

    fun isLatest(attempt: Long): Boolean = attempt == sequence
}
