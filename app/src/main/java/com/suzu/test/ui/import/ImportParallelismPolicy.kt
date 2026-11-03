package com.suzu.test.ui.import

internal data class ImportProbeTiming(val parallelism: Int, val durationNanos: Long)

/** Pure policy: all timings must represent the same number of identical image operations. */
internal object ImportParallelismPolicy {
    private const val MIB = 1024L * 1024L

    fun limit(
        processors: Int,
        heapLimitBytes: Long,
        heapHeadroomBytes: Long,
        lowRam: Boolean,
        lowMemory: Boolean,
        thermallyConstrained: Boolean
    ): Int {
        if (lowRam || lowMemory || thermallyConstrained) return 1
        // Leave room for the UI, queued encoded images and a large image. This is a
        // concurrency allowance, not a promise that arbitrarily large images fit.
        val heapSlots = ((heapLimitBytes.coerceAtLeast(0) - 64 * MIB).coerceAtLeast(0) / (48 * MIB)).coerceAtMost(4).toInt()
        val freeSlots = ((heapHeadroomBytes.coerceAtLeast(0) - 32 * MIB).coerceAtLeast(0) / (48 * MIB)).coerceAtMost(4).toInt()
        return minOf(4, processors.coerceAtLeast(1), heapSlots.coerceAtLeast(1), freeSlots.coerceAtLeast(1))
    }

    fun choose(timings: List<ImportProbeTiming>, limit: Int): Int {
        var best = timings.firstOrNull { it.parallelism == 1 && it.durationNanos > 0 } ?: return 1
        for (trial in timings.sortedBy { it.parallelism }) {
            if (trial.parallelism <= best.parallelism || trial.parallelism > limit || trial.durationNanos <= 0) continue
            // At least 15% more throughput to justify the extra memory, CPU and heat.
            if (best.durationNanos.toDouble() / trial.durationNanos >= 1.15) best = trial
        }
        return best.parallelism.coerceIn(1, limit.coerceAtLeast(1))
    }
}
