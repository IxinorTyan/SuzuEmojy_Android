package com.suzu.test

import com.suzu.test.resource.similar.AnimationSimilarity
import org.junit.Assert.*
import org.junit.Test

class AnimationSimilarityTest {
    @Test fun samplingUsesPlaybackTimeInsteadOfFrameCount() {
        assertEquals(listOf(0, 1, 1, 1, 1, 1, 1, 1), AnimationSimilarity.sampleFrames(listOf(100, 700)))
        assertEquals(List(8) { 0 }, AnimationSimilarity.sampleFrames(listOf(100)))
    }

    @Test fun equivalentFrameRatesAndPlaybackSpeedsHaveEquivalentSamples() {
        val original = listOf(0L, -1L)
        val repeated = listOf(0L, 0L, -1L, -1L)
        val a = AnimationSimilarity.sampleFrames(listOf(100, 100)).map { original[it] }
        val b = AnimationSimilarity.sampleFrames(listOf(50, 50, 50, 50)).map { repeated[it] }
        val slow = AnimationSimilarity.sampleFrames(listOf(200, 200)).map { original[it] }
        assertTrue(AnimationSimilarity.matches(a, b, 0))
        assertTrue(AnimationSimilarity.matches(a, slow, 0))
    }

    @Test fun matchingFirstFrameDoesNotMakeAnimationsDuplicates() {
        assertFalse(AnimationSimilarity.matches(List(8) { 0L }, listOf(0L) + List(7) { -1L }, 16))
    }

    @Test fun sevenPositionsMustPassTheSelectedThreshold() {
        val a = List(8) { 0L }
        assertTrue(AnimationSimilarity.matches(a, List(7) { 1L } + -1L, 1))
        assertFalse(AnimationSimilarity.matches(a, List(6) { 1L } + listOf(-1L, -1L), 1))
        assertFalse(AnimationSimilarity.matches(a, List(7) { 1L } + -1L, 0))
        assertFalse(AnimationSimilarity.matches(a, listOf(0L), 16))
    }

    @Test fun groupsDoNotChainOrDuplicateItems() {
        assertEquals(listOf(listOf(0, 1)), AnimationSimilarity.group(listOf(List(8) { 0L }, List(8) { 1L }, List(8) { 3L }), 1))
        assertTrue(AnimationSimilarity.group(emptyList(), 6).isEmpty())
    }

    @Test(expected = kotlinx.coroutines.CancellationException::class)
    fun groupingCanBeCancelled() {
        AnimationSimilarity.group(listOf(List(8) { 0L }), 6) { throw kotlinx.coroutines.CancellationException() }
    }
}
