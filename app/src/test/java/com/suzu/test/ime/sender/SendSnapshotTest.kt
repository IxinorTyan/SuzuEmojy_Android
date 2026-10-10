package com.suzu.test.ime.sender

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class SendSnapshotTest {
    @get:Rule val temporary = TemporaryFolder()

    @Test fun neverSentSourceAndRepeatedSendsAreIndependent() {
        val source = temporary.newFile("new-image.png").apply { writeBytes(byteArrayOf(1, 2, 3, 4)) }
        val directory = File(temporary.root, "attempts")
        val first = SendSnapshot.create(source, directory)
        val second = SendSnapshot.create(source, directory)
        assertNotEquals(first, second)
        assertNotEquals(source, first)
        assertArrayEquals(source.readBytes(), first.readBytes())
        assertTrue(first.delete()) // Simulate the first request's expiry.
        assertTrue(source.exists())
        assertArrayEquals(source.readBytes(), second.readBytes())
        val third = SendSnapshot.create(source, directory)
        assertArrayEquals(source.readBytes(), third.readBytes())
    }

    @Test fun snapshotSurvivesSourceCacheRemoval() {
        val cache = temporary.newFile("converted.gif").apply { writeText("GIF89a-test-bytes") }
        val result = SendSnapshot.create(cache, File(temporary.root, "attempts"))
        assertTrue(cache.delete())
        assertEquals("GIF89a-test-bytes", result.readText())
    }
}
