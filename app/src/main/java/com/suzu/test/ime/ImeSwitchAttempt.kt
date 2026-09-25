package com.suzu.test.ime

/** Pure timing policy; Android window and editor checks are supplied by the caller. */
internal class ImeSwitchAttempt(val startedAt: Long) {
    private var visibleSince: Long? = null
    private var clicks = 0
    private var lastClickAt: Long? = null
    private var lastHiddenAt: Long? = null
    private var showRequests = 0
    private var lastShowRequestAt: Long? = null
    private var completed = false
    @Volatile var cancelled = false
        private set

    fun cancel() { cancelled = true }
    fun expired(now: Long): Boolean = now - startedAt >= 2500L
    fun isActive(now: Long): Boolean = !cancelled && !expired(now)
    fun onHidden(now: Long) {
        if (!isActive(now)) return
        visibleSince = null
        lastHiddenAt = now
    }

    // Recover the bound IME before asking accessibility to click the host editor.
    fun takeShowRequest(now: Long, targetBound: Boolean, targetVisible: Boolean): Boolean {
        if (!isActive(now) || !targetBound || targetVisible || showRequests >= 2) return false
        if (now - startedAt < 120L || lastHiddenAt?.let { now - it < 80L } == true ||
            lastShowRequestAt?.let { now - it < 300L } == true) return false
        showRequests++
        lastShowRequestAt = now
        return true
    }

    fun stable(now: Long, targetVisible: Boolean): Boolean {
        if (!isActive(now)) return false
        if (!targetVisible) { visibleSince = null; return false }
        val since = visibleSince ?: now.also { visibleSince = it }
        val stable = now - since >= 300L
        if (stable) completed = true
        return stable
    }

    // A suppressed finish callback will not be delivered again when we time out.
    // Do not reopen a keyboard here: only restore selection for a failed own-IME handoff.
    fun shouldRestoreOnFinish(toOwnIme: Boolean, destinationSelected: Boolean, ownImeVisible: Boolean): Boolean =
        !completed && toOwnIme && destinationSelected && !ownImeVisible
    fun takeClick(now: Long, targetReady: Boolean, targetVisible: Boolean): Boolean {
        if (cancelled || expired(now) || !targetReady || targetVisible || clicks >= 2) return false
        if (now - startedAt < 500L || lastClickAt?.let { now - it < 300L } == true ||
            lastShowRequestAt?.let { now - it < 300L } == true ||
            lastHiddenAt?.let { now - it < 80L } == true) return false
        clicks++
        lastClickAt = now
        return true
    }
}
