package com.suzu.test.floating

internal class SearchLaunchState {
    enum class Phase { IDLE, PREPARING, WAITING_TEXT_IME, ACTIVE }
    var phase = Phase.IDLE
        private set
    var generation = 0L
        private set
    private var deadline = 0L

    fun begin(now: Long): Long {
        generation++
        phase = Phase.PREPARING
        deadline = now + 2500L
        return generation
    }
    fun waiting() { phase = Phase.WAITING_TEXT_IME }
    fun activate() { phase = Phase.ACTIVE }
    fun accepts(token: Long) = generation == token && phase != Phase.IDLE
    fun expired(now: Long) = phase != Phase.ACTIVE && now >= deadline
    fun shouldYieldToOwnIme() = phase == Phase.ACTIVE
    fun close() { generation++; phase = Phase.IDLE }
}
