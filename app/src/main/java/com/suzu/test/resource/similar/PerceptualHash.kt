package com.suzu.test.resource.similar

import kotlin.math.cos
import kotlin.math.PI

/** Independent visual fingerprint. Never use this as a resource identity or sync key. */
object PerceptualHash {
    const val SIZE = 32
    private val basis = Array(8) { frequency ->
        DoubleArray(SIZE) { position -> cos((2 * position + 1) * frequency * PI / (2 * SIZE)) }
    }

    /** 63 AC coefficients plus one fixed bit; DC is excluded to tolerate brightness changes. */
    fun compute(luminance: DoubleArray): Long {
        require(luminance.size == SIZE * SIZE)
        val horizontal = Array(SIZE) { y ->
            DoubleArray(8) { u -> (0 until SIZE).sumOf { x -> luminance[y * SIZE + x] * basis[u][x] } }
        }
        val coefficients = DoubleArray(63)
        var index = 0
        for (v in 0..7) for (u in 0..7) {
            if (u == 0 && v == 0) continue
            coefficients[index++] = (0 until SIZE).sumOf { y -> horizontal[y][u] * basis[v][y] }
        }
        val median = coefficients.sorted()[31]
        var hash = 0L
        coefficients.forEachIndexed { bit, value ->
            // Suppress roundoff noise in flat images.
            if (value > median + 1e-7) hash = hash or (1L shl bit)
        }
        return hash
    }

    fun distance(a: Long, b: Long): Int = java.lang.Long.bitCount(a xor b)
}

/** BK tree bounds comparisons by Hamming distance instead of storing all image pairs. */
class SimilarityIndex(private val hashes: List<Long>) {
    private class Node(val hash: Long, val indices: MutableList<Int>) {
        val children = mutableMapOf<Int, Node>()
    }
    private var root: Node? = null

    fun add(index: Int) {
        val hash = hashes[index]
        var node = root ?: run { root = Node(hash, mutableListOf(index)); return }
        while (true) {
            val distance = PerceptualHash.distance(hash, node.hash)
            if (distance == 0) { node.indices.add(index); return }
            val next = node.children[distance]
            if (next == null) { node.children[distance] = Node(hash, mutableListOf(index)); return }
            node = next
        }
    }

    fun find(hash: Long, threshold: Int, checkCancelled: () -> Unit = {}): List<Int> {
        val result = mutableListOf<Int>()
        val pending = java.util.ArrayDeque<Node>()
        root?.let(pending::add)
        while (pending.isNotEmpty()) {
            checkCancelled()
            val node = pending.removeLast()
            val distance = PerceptualHash.distance(hash, node.hash)
            if (distance <= threshold) result.addAll(node.indices)
            node.children.forEach { (edge, child) ->
                if (edge in (distance - threshold)..(distance + threshold)) pending.add(child)
            }
        }
        return result
    }

    companion object {
        /** Stable, disjoint groups. Every member must match its group's first item directly. */
        fun group(hashes: List<Long>, threshold: Int, checkCancelled: () -> Unit = {}): List<List<Int>> {
            require(threshold in 0..63)
            val tree = SimilarityIndex(hashes)
            hashes.indices.forEach { checkCancelled(); tree.add(it) }
            val assigned = BooleanArray(hashes.size)
            val groups = mutableListOf<List<Int>>()
            hashes.indices.forEach { anchor ->
                checkCancelled()
                if (!assigned[anchor]) {
                    val members = tree.find(hashes[anchor], threshold, checkCancelled)
                        .filter { !assigned[it] }.sorted()
                    if (members.size > 1) {
                        members.forEach { assigned[it] = true }
                        groups.add(members)
                    }
                }
            }
            return groups
        }
    }
}
