package com.suzu.test.resource.similar

object AnimationSimilarity {
    const val SAMPLE_COUNT = 8

    /** Sample by elapsed playback time, not frame number. */
    fun sampleFrames(delays: List<Int>): List<Int> {
        require(delays.isNotEmpty() && delays.all { it > 0 })
        val duration = delays.sumOf { it.toLong() }
        var frame = 0
        var end = delays[0].toLong()
        return List(SAMPLE_COUNT) { sample ->
            val time = duration * sample / SAMPLE_COUNT
            while (time >= end && frame < delays.lastIndex) end += delays[++frame]
            frame
        }
    }

    fun matches(a: List<Long>, b: List<Long>, threshold: Int): Boolean {
        require(threshold in 0..63)
        if (a.size != SAMPLE_COUNT || b.size != SAMPLE_COUNT) return false
        // At least 7/8 time positions must match; never decide using just the first frame.
        return a.indices.count { PerceptualHash.distance(a[it], b[it]) <= threshold } >= 7
    }

    fun group(samples: List<List<Long>>, threshold: Int, checkCancelled: () -> Unit = {}): List<List<Int>> {
        val assigned = BooleanArray(samples.size)
        val groups = mutableListOf<List<Int>>()
        for (anchor in samples.indices) {
            checkCancelled()
            if (assigned[anchor]) continue
            val members = mutableListOf(anchor)
            for (candidate in anchor + 1 until samples.size) {
                checkCancelled()
                if (!assigned[candidate] && matches(samples[anchor], samples[candidate], threshold)) members.add(candidate)
            }
            if (members.size > 1) {
                members.forEach { assigned[it] = true }
                groups.add(members)
            }
        }
        return groups
    }
}
