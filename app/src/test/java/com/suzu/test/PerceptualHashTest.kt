package com.suzu.test

import com.suzu.test.resource.similar.PerceptualHash
import com.suzu.test.resource.similar.SimilarityIndex
import org.junit.Assert.*
import org.junit.Test
import java.util.Random

class PerceptualHashTest {
    @Test fun brightnessAndContrastChangesPreserveFingerprint() {
        val random = Random(71)
        val image = DoubleArray(1024) { random.nextDouble() * 120 }
        assertEquals(PerceptualHash.compute(image), PerceptualHash.compute(DoubleArray(1024) { image[it] * 1.3 + 30 }))
    }

    @Test fun uniformImagesHaveStableHashRegardlessOfRoundoff() {
        assertEquals(0L, PerceptualHash.compute(DoubleArray(1024) { 255.0 }))
        assertEquals(0L, PerceptualHash.compute(DoubleArray(1024)))
    }

    @Test fun structuralChangesAreDetected() {
        val vertical = DoubleArray(1024) { if (it % 32 < 16) 240.0 else 10.0 }
        val horizontal = DoubleArray(1024) { if (it / 32 < 16) 240.0 else 10.0 }
        assertTrue(PerceptualHash.distance(PerceptualHash.compute(vertical), PerceptualHash.compute(horizontal)) > 0)
    }

    @Test fun treeMatchesBruteForceIncludingDuplicatesAndSignBit() {
        val random = Random(42)
        val hashes = List(200) { random.nextLong() } + listOf(0L, 0L, Long.MIN_VALUE)
        val index = SimilarityIndex(hashes)
        hashes.indices.forEach(index::add)
        for (threshold in listOf(0, 1, 6, 16, 32, 63)) {
            for (query in hashes.take(20) + listOf(0L, Long.MIN_VALUE)) {
                val expected = hashes.indices.filter { PerceptualHash.distance(query, hashes[it]) <= threshold }
                assertEquals(expected, index.find(query, threshold).sorted())
            }
        }
    }

    @Test fun groupingDoesNotChainSimilarityAndNeverRepeatsResources() {
        // A-B and B-C match, but A-C does not. D-E form a separate group.
        val hashes = listOf(0L, 1L, 3L, -1L, -2L)
        val groups = SimilarityIndex.group(hashes, 1)
        assertEquals(listOf(listOf(0, 1), listOf(3, 4)), groups)
        assertEquals(groups.flatten().size, groups.flatten().toSet().size)
        assertTrue(groups.all { group -> group.all { PerceptualHash.distance(hashes[group.first()], hashes[it]) <= 1 } })
    }

    @Test fun emptySingletonAndExactMatchCases() {
        assertTrue(SimilarityIndex.group(emptyList(), 6).isEmpty())
        assertTrue(SimilarityIndex.group(listOf(123L), 6).isEmpty())
        assertEquals(listOf(listOf(0, 2)), SimilarityIndex.group(listOf(12L, 13L, 12L), 0))
    }

    @Test(expected = kotlinx.coroutines.CancellationException::class)
    fun matchingCanBeCancelled() {
        SimilarityIndex.group(List(1000) { it.toLong() }, 6) { throw kotlinx.coroutines.CancellationException() }
    }
}
