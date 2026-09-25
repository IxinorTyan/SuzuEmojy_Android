package com.suzu.test.ime

/** Identity-based recovery after a host drops input focus during a keyboard switch. */
internal object ImeEditorRecovery {
    data class Identity(val viewId: String, val className: String?)
    data class Candidate(
        val viewId: String?, val className: String?,
        val editable: Boolean, val visible: Boolean, val enabled: Boolean
    )

    fun choose(saved: Identity, focused: Candidate?, candidates: List<Candidate>): Int? {
        fun matches(candidate: Candidate): Boolean =
            candidate.viewId == saved.viewId && candidate.className == saved.className
        // A different focused editor is a new user target, not a lost-focus recovery.
        if (focused?.editable == true && !matches(focused)) return null
        return candidates.indices.filter { index ->
            val candidate = candidates[index]
            matches(candidate) && candidate.editable && candidate.visible && candidate.enabled
        }.singleOrNull()
    }
}
