package com.suzu.test

import com.suzu.test.resource.hash.ResourceHasher
import org.junit.Assert.assertEquals
import org.junit.Test

class ResourceHashStreamTest {
    @Test fun preservesKnownMd5ValuesAndLeadingZeros() {
        for ((input, expected) in listOf(
            "" to "d41d8cd98f00b204e9800998ecf8427e",
            "abc" to "900150983cd24fb0d6963f7d28e17f72",
            "a" to "0cc175b9c0f1b6a831c399e269772661"
        )) {
            val bytes = input.toByteArray()
            assertEquals(expected, ResourceHasher.fileMd5(bytes))
            assertEquals(expected, bytes.inputStream().use { ResourceHasher.fileMd5(it) })
        }
    }

    @Test fun streamsFilesAcrossMultipleReadBuffers() {
        val bytes = ByteArray(200_003) { ((it * 31) % 256).toByte() }
        assertEquals(ResourceHasher.fileMd5(bytes), bytes.inputStream().use { ResourceHasher.fileMd5(it) })
    }
}
