package com.suzu.test

import com.suzu.test.log.TestLog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class TestLogConcurrencyTest {
    @Test fun concurrentImportWorkersKeepValidTimestampsAndBoundedHistory() {
        TestLog.clear()
        val delivered = AtomicInteger()
        val listener: (String) -> Unit = { delivered.incrementAndGet() }
        val executor = Executors.newFixedThreadPool(4)
        val start = CountDownLatch(1)
        TestLog.addListener(listener)
        try {
            val tasks = (0..3).map { worker ->
                executor.submit {
                    check(start.await(5, TimeUnit.SECONDS))
                    repeat(200) { TestLog.i("ImportTest", "$worker:$it") }
                }
            }
            start.countDown()
            tasks.forEach { it.get(5, TimeUnit.SECONDS) }
            assertEquals(800, delivered.get())
            val lines = TestLog.getAllLogs().lines()
            assertEquals(300, lines.size)
            assertEquals(300, lines.distinct().size)
            assertTrue(lines.all { it.matches(Regex("\\[\\d{2}:\\d{2}:\\d{2}\\.\\d{3}]\\[ImportTest] [0-3]:\\d+")) })
        } finally {
            executor.shutdownNow()
            TestLog.removeListener(listener)
            TestLog.clear()
        }
    }
}
