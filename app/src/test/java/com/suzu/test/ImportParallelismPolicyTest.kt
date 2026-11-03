package com.suzu.test

import com.suzu.test.ui.import.ImportParallelismPolicy
import com.suzu.test.ui.import.ImportProbeTiming
import org.junit.Assert.assertEquals
import org.junit.Test

class ImportParallelismPolicyTest {
    private val mib = 1024L * 1024L

    private fun limit(
        cores: Int = 8, heap: Long = 512 * mib, free: Long = 400 * mib,
        lowRam: Boolean = false, lowMemory: Boolean = false, hot: Boolean = false
    ) = ImportParallelismPolicy.limit(cores, heap, free, lowRam, lowMemory, hot)

    @Test fun boundsParallelismByCpuHeapAndCurrentHeadroom() {
        assertEquals(4, limit())
        assertEquals(2, limit(cores = 2))
        assertEquals(1, limit(cores = 0))
        assertEquals(1, limit(heap = 96 * mib))
        assertEquals(2, limit(heap = 192 * mib))
        assertEquals(2, limit(free = 128 * mib))
        assertEquals(1, limit(free = 40 * mib))
        assertEquals(1, limit(heap = Long.MIN_VALUE, free = 0))
    }

    @Test fun memoryOrThermalPressureAlwaysFallsBackToOne() {
        assertEquals(1, limit(lowRam = true))
        assertEquals(1, limit(lowMemory = true))
        assertEquals(1, limit(hot = true))
    }

    @Test fun selectsMeasuredThroughputInsteadOfAssumingMoreWorkersIsFaster() {
        assertEquals(1, choose(100, 110, 120))
        assertEquals(1, choose(100, 95, 90))
        assertEquals(2, choose(100, 80, 74))
        assertEquals(4, choose(100, 80, 60))
    }

    private fun choose(serial: Long, two: Long, four: Long) = ImportParallelismPolicy.choose(listOf(
        ImportProbeTiming(1, serial), ImportProbeTiming(2, two), ImportProbeTiming(4, four)
    ), 4)

    @Test fun incompleteProbeAndInvalidTimingsCannotRaiseConcurrency() {
        assertEquals(1, ImportParallelismPolicy.choose(emptyList(), 4))
        assertEquals(1, ImportParallelismPolicy.choose(listOf(ImportProbeTiming(2, 10)), 4))
        assertEquals(1, choose(100, 0, -10))
        assertEquals(2, ImportParallelismPolicy.choose(listOf(
            ImportProbeTiming(4, 1), ImportProbeTiming(2, 80), ImportProbeTiming(1, 100)
        ), 2))
    }
}
