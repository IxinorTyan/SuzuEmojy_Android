package com.suzu.test.ime

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal data class ImeHost(val windowId: Int, val packageName: String)

/**
 * Runs one keyboard handoff at a time. All entry points and state transitions run on the
 * owning service's main scope; the platform adapter performs window queries on the worker.
 */
internal class ImeHandoffCoordinator(
    private val scope: CoroutineScope,
    private val worker: CoroutineDispatcher,
    private val now: () -> Long,
    private val platform: Platform
) {
    interface Platform {
        val ownPackage: String
        fun ownImeId(): String
        fun selectedImeId(): String?
        fun isOwnIme(id: String?): Boolean
        fun selectOwnIme(): Boolean
        fun selectPreviousIme(id: String): Boolean
        fun restoreSelection()
        fun focusedHost(known: ImeHost?): ImeHost?
        fun captureEditor(host: ImeHost): ImeEditorRecovery.Identity?
        fun visibleImeWindow(packageName: String, knownId: Int?): Int?
        fun ownImeShowing(): Boolean
        fun ownImeShowingFor(host: ImeHost): Boolean
        fun canShowOwnImeFor(host: ImeHost): Boolean
        fun showOwnImeFor(host: ImeHost): Boolean
        fun clickEditor(host: ImeHost, editor: ImeEditorRecovery.Identity?, stillAllowed: () -> Boolean)
        fun clearSearch()
        fun releaseSearchGuard()
        fun log(message: String, failure: Throwable? = null)
    }

    private class Request(val attempt: ImeSwitchAttempt, val fromSearch: Boolean) {
        lateinit var work: Job
        lateinit var timeout: Job
    }

    private var active: Request? = null

    fun isPending(): Boolean = active?.attempt?.isActive(now()) == true

    fun onInputChanged() {
        // A hide/restart can happen entirely between two window polls.
        active?.attempt?.onHidden(now())
    }

    fun cancel() {
        val request = active ?: return
        detach(request)
        request.work.cancel()
    }

    fun openFromSearch(host: ImeHost?): Boolean = start(host, fromSearch = true)
    fun openFromEditor(): Boolean = start(null, fromSearch = false)
    fun restoreAndShow(previousImeId: String): Boolean = start(null, fromSearch = false, previousImeId = previousImeId)

    private fun start(savedHost: ImeHost?, fromSearch: Boolean, previousImeId: String? = null): Boolean {
        cancel()
        if (!scope.isActive) return false
        if (fromSearch && savedHost == null) {
            platform.clearSearch()
            platform.log("无法恢复宿主输入焦点：缺少目标窗口")
            return false
        }
        val toOwnIme = previousImeId == null
        val expectedImeId = previousImeId ?: platform.ownImeId()
        fun matchesDestination(id: String?) = if (toOwnIme) platform.isOwnIme(id) else id == expectedImeId

        val request = Request(ImeSwitchAttempt(now()), fromSearch)
        request.work = scope.launch(start = CoroutineStart.LAZY) {
            try {
                runHandoff(request, savedHost, expectedImeId, toOwnIme, ::matchesDestination)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (failure: Exception) {
                platform.log("IME 交接失败", failure)
                if (active === request && fromSearch) platform.clearSearch()
            } finally {
                if (active === request) {
                    finish(request, request.attempt.expired(now()), toOwnIme, ::matchesDestination)
                }
            }
        }
        request.timeout = scope.launch(start = CoroutineStart.LAZY) {
            delay((request.attempt.deadline - now()).coerceAtLeast(0L))
            if (active === request) {
                finish(request, timedOut = true, toOwnIme, ::matchesDestination)
            }
        }
        active = request
        request.timeout.start()
        request.work.start()
        return true
    }

    private fun detach(request: Request) {
        active = null
        request.attempt.cancel()
        request.timeout.cancel()
    }

    private fun finish(
        request: Request,
        timedOut: Boolean,
        toOwnIme: Boolean,
        matchesDestination: (String?) -> Boolean
    ) {
        detach(request)
        if (timedOut) {
            request.work.cancel()
            platform.log("IME 交接超时")
        }
        val restore = request.attempt.shouldRestoreOnFinish(
            toOwnIme, matchesDestination(platform.selectedImeId()), platform.ownImeShowing()
        )
        if (restore || (timedOut && request.fromSearch)) {
            platform.clearSearch()
        } else if (request.fromSearch) {
            platform.releaseSearchGuard()
        }
        if (restore) {
            // A suppressed finish callback will not be replayed. Restore selection only.
            platform.log("IME 交接未显示，恢复原输入法（不请求弹出）")
            platform.restoreSelection()
        }
    }

    private suspend fun runHandoff(
        request: Request,
        savedHost: ImeHost?,
        expectedImeId: String,
        toOwnIme: Boolean,
        matchesDestination: (String?) -> Boolean
    ) {
        val attempt = request.attempt
        val host = savedHost ?: withContext(worker) { platform.focusedHost(null) }
        if (!attempt.isActive(now())) return
        if (host == null) {
            platform.log("取消 IME 切换：当前没有可用的宿主窗口")
            if (!toOwnIme) withContext(worker) { platform.selectPreviousIme(expectedImeId) }
            return
        }
        platform.log("开始 IME 交接: target=$host fromSearch=${request.fromSearch} destination=$expectedImeId")
        // Some editors lose input focus when the outgoing IME disappears.
        val savedEditor = if (toOwnIme) null else withContext(worker) { platform.captureEditor(host) }
        if (!attempt.isActive(now())) return
        val initialIme = platform.selectedImeId()
        val switched = withContext(worker) {
            if (toOwnIme) platform.selectOwnIme() else platform.selectPreviousIme(expectedImeId)
        }
        if (!attempt.isActive(now())) return
        if (!switched) {
            if (request.fromSearch) platform.clearSearch()
            return
        }
        var destinationObserved = false
        var verifiedImeWindowId: Int? = null
        while (attempt.isActive(now())) {
            val current = platform.selectedImeId()
            val selected = matchesDestination(current)
            if (!selected && (destinationObserved || current != initialIme)) {
                if (request.fromSearch) platform.clearSearch()
                return
            }
            if (selected) destinationObserved = true
            val (focused, imeWindowId) = withContext(worker) {
                val focusedHost = platform.focusedHost(host)
                val visibleWindow = if (!toOwnIme && selected && focusedHost == host) {
                    platform.visibleImeWindow(expectedImeId.substringBefore('/'), verifiedImeWindowId)
                } else null
                focusedHost to visibleWindow
            }
            verifiedImeWindowId = imeWindowId
            if (!attempt.isActive(now())) break
            // Search briefly owns focus before returning it to the saved host.
            if (focused != null && focused != host &&
                !(request.fromSearch && focused.packageName == platform.ownPackage)) {
                platform.log("取消 IME 交接：宿主窗口已切换 target=$host focused=$focused")
                if (request.fromSearch) platform.clearSearch()
                return
            }
            val observedAt = now()
            val hostReady = selected && focused == host
            val visible = hostReady && if (toOwnIme) platform.ownImeShowingFor(host) else imeWindowId != null
            if (attempt.stable(observedAt, visible)) {
                platform.log("IME 交接完成: target=$host elapsed=${observedAt - attempt.startedAt}ms")
                return
            }
            val bound = toOwnIme && hostReady && platform.canShowOwnImeFor(host)
            if (attempt.takeShowRequest(observedAt, bound, visible)) {
                val requested = platform.showOwnImeFor(host)
                platform.log("IME 交接主动显示: target=$host requested=$requested elapsed=${observedAt - attempt.startedAt}ms")
            } else if (attempt.takeClick(observedAt, hostReady, visible)) {
                withContext(worker) {
                    if (attempt.isActive(now())) {
                        platform.clickEditor(host, savedEditor) {
                            matchesDestination(platform.selectedImeId()) && attempt.isActive(now())
                        }
                    }
                }
            }
            delay(50L)
        }
    }
}
