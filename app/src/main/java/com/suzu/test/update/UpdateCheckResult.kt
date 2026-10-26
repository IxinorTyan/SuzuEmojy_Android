package com.suzu.test.update

data class GitHubRelease(val tag: String, val notes: String)

sealed class UpdateCheckResult {
    data class NewVersion(val release: GitHubRelease) : UpdateCheckResult()
    data object UpToDate : UpdateCheckResult()
    data object NoRelease : UpdateCheckResult()
    data object Skipped : UpdateCheckResult()
    data class Failed(val reason: FailureReason) : UpdateCheckResult()
}

enum class FailureReason { NETWORK, RATE_LIMIT, SERVER, INVALID_RESPONSE, UNKNOWN_VERSION }

internal object UpdateCheckPolicy {
    const val SUCCESS_INTERVAL_MS = 24 * 60 * 60 * 1000L
    const val RETRY_INTERVAL_MS = 60 * 60 * 1000L

    fun shouldCheck(now: Long, lastAttempt: Long, lastSuccess: Long): Boolean {
        fun withinInterval(timestamp: Long, interval: Long): Boolean =
            timestamp > 0 && now >= timestamp && now - timestamp < interval
        return !withinInterval(lastAttempt, RETRY_INTERVAL_MS) &&
            !withinInterval(lastSuccess, SUCCESS_INTERVAL_MS)
    }

    fun compare(current: String, release: GitHubRelease): UpdateCheckResult {
        val installed = ReleaseVersion.parse(current)
            ?: return UpdateCheckResult.Failed(FailureReason.UNKNOWN_VERSION)
        val latest = ReleaseVersion.parse(release.tag)
            ?: return UpdateCheckResult.Failed(FailureReason.UNKNOWN_VERSION)
        return if (latest > installed) UpdateCheckResult.NewVersion(release) else UpdateCheckResult.UpToDate
    }
}
