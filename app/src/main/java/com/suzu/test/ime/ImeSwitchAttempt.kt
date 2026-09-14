package com.suzu.test.ime

/** Pure timing policy; Android window and editor checks are supplied by the caller. */
internal class ImeSwitchAttempt(val startedAt: Long) {
    private var visibleSince: Long? = null
    private var clicks = 0
    private var lastClickAt: Long? = null
    var cancelled = false
        private set

    fun cancel() { cancelled = true }
    fun expired(now: Long): Boolean = now - startedAt >= 1000L
    fun stable(now: Long, targetVisible: Boolean): Boolean {
        if (cancelled) return false
        if (!targetVisible) { visibleSince = null; return false }
        val since = visibleSince ?: now.also { visibleSince = it }
        return now - since >= 100L
    }
    fun takeClick(now: Long, targetReady: Boolean, targetVisible: Boolean): Boolean {
        if (cancelled || expired(now) || !targetReady || targetVisible || clicks >= 2) return false
        val due = if (clicks == 0) 120L else 350L
        if (now - startedAt < due || lastClickAt?.let { now - it < 150L } == true) return false
        clicks++
        lastClickAt = now
        return true
    }
}
