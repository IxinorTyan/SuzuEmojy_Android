package com.suzu.test.ime

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal enum class ImeWindowObservation { AWAITING_SNAPSHOT, AVAILABLE, UNAVAILABLE }

internal data class ImeWindowState(
    val visible: Boolean = false,
    val topPx: Int? = null,
    val observation: ImeWindowObservation = ImeWindowObservation.AWAITING_SNAPSHOT,
    val ownImeShowing: Boolean = false,
    val ownLifecycleRevision: Long = 0
) {
    // Only the ball fails open when observation is unavailable. Gestures and search
    // continue to use the last known visibility and geometry.
    val allowsImeOnlyBall: Boolean get() = visible || observation == ImeWindowObservation.UNAVAILABLE

    fun visibilitySignalSince(previous: ImeWindowState?): Boolean =
        previous == null || visible != previous.visible || ownLifecycleRevision != previous.ownLifecycleRevision

    fun ownShowSignalSince(previous: ImeWindowState?): Boolean =
        ownImeShowing && (previous == null || ownLifecycleRevision != previous.ownLifecycleRevision)
}

/** Main thread only. Accessibility snapshots must already have passed the caller's epoch check. */
internal class ImeWindowTracker {
    private companion object {
        const val HIDE_GUARD_MS = 200L
    }

    private val state = MutableStateFlow(ImeWindowState())
    val windowState = state.asStateFlow()
    private var hiddenUntil = 0L

    fun reset(ownImeShowing: Boolean) {
        hiddenUntil = 0L
        state.value = ImeWindowState(
            visible = ownImeShowing,
            ownImeShowing = ownImeShowing,
            ownLifecycleRevision = state.value.ownLifecycleRevision + 1
        )
    }

    fun onOwnImeLifecycle(visible: Boolean, now: Long) {
        hiddenUntil = if (visible) 0L else now + HIDE_GUARD_MS
        val before = state.value
        state.value = before.copy(
            visible = visible,
            topPx = if (visible) before.topPx else null,
            ownImeShowing = visible,
            ownLifecycleRevision = before.ownLifecycleRevision + 1
        )
    }

    fun onAccessibilitySnapshot(available: Boolean, imeTopPx: Int?, now: Long) {
        val before = state.value
        state.value = if (available) {
            val visibleTop = imeTopPx?.takeIf { now >= hiddenUntil }
            before.copy(
                observation = ImeWindowObservation.AVAILABLE,
                visible = visibleTop != null,
                topPx = visibleTop
            )
        } else {
            // A failed/empty query is not evidence that the keyboard was dismissed.
            before.copy(observation = ImeWindowObservation.UNAVAILABLE)
        }
    }
}
