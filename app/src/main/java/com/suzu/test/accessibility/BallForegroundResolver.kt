package com.suzu.test.accessibility

/** A small immutable snapshot; no accessibility nodes escape the service callback. */
internal data class BallWindowSnapshot(
    val kind: Kind,
    val packageName: String?,
    val focused: Boolean,
    val active: Boolean,
) {
    enum class Kind { APPLICATION, IME, OWN_OVERLAY, OTHER }
}

internal object BallForegroundResolver {
    fun resolve(windows: List<BallWindowSnapshot>, previousHost: String? = null): String? {
        // System surfaces such as the notification shade must not retain the previous app.
        if (windows.any {
                it.kind == BallWindowSnapshot.Kind.OTHER && (it.focused || it.active)
            }) return null
        val apps = windows.filter { it.kind == BallWindowSnapshot.Kind.APPLICATION }
        // Input focus is authoritative in split screen/freeform; accessibility active state
        // can briefly refer to the window last touched, so use it only without a focused app.
        val focused = apps.filter { it.focused }
        if (focused.isNotEmpty()) return uniquePackage(focused)
        val active = apps.filter { it.active }
        if (active.isNotEmpty()) return uniquePackage(active)

        // Only a keyboard/our overlay taking interaction may retain the verified host.
        // Require it to still be present; never reuse history on a generic loss of focus.
        val hostTemporarilyUnfocused = windows.any {
            (it.kind == BallWindowSnapshot.Kind.OWN_OVERLAY ||
                it.kind == BallWindowSnapshot.Kind.IME) && (it.focused || it.active)
        }
        if (!hostTemporarilyUnfocused || apps.any { it.packageName.isNullOrBlank() }) return null
        if (previousHost != null && apps.any { it.packageName == previousHost }) return previousHost
        return uniquePackage(apps)
    }

    private fun uniquePackage(windows: List<BallWindowSnapshot>): String? {
        if (windows.any { it.packageName.isNullOrBlank() }) return null
        return windows.map { it.packageName }.distinct().singleOrNull()
    }
}
