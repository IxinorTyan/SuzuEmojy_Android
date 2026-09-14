package com.suzu.test.floating

import android.content.SharedPreferences
import android.content.res.Configuration
import java.lang.reflect.Proxy
import org.junit.Assert.assertEquals
import org.junit.Test

class FloatingBallPositionTest {
    private val portrait = Configuration.ORIENTATION_PORTRAIT
    private val landscape = Configuration.ORIENTATION_LANDSCAPE

    @Test
    fun positionsSurviveReloadAndRemainIndependent() {
        val values = mutableMapOf<String, Int>()
        val sp = preferences(values)
        FloatingBallConfig.saveBallPosition(sp, 900, 1800, portrait)
        FloatingBallConfig.saveBallPosition(sp, 1700, 400, landscape)
        val reloaded = preferences(values)
        assertEquals(900 to 1800, FloatingBallConfig.getBallPosition(reloaded, portrait))
        assertEquals(1700 to 400, FloatingBallConfig.getBallPosition(reloaded, landscape))
        FloatingBallConfig.saveBallPosition(reloaded, 100, 200, portrait)
        assertEquals(1700 to 400, FloatingBallConfig.getBallPosition(reloaded, landscape))
    }

    @Test
    fun legacyPositionRemainsFallbackForUnconfiguredOrientation() {
        val values = mutableMapOf(
            FloatingBallConfig.KEY_BALL_POS_X to 800,
            FloatingBallConfig.KEY_BALL_POS_Y to 1600
        )
        val sp = preferences(values)
        assertEquals(800 to 1600, FloatingBallConfig.getBallPosition(sp, portrait))
        FloatingBallConfig.saveBallPosition(sp, 600, 300, landscape)
        assertEquals(800 to 1600, FloatingBallConfig.getBallPosition(sp, portrait))
        assertEquals(600 to 300, FloatingBallConfig.getBallPosition(sp, landscape))
        assertEquals(1600, values[FloatingBallConfig.KEY_BALL_POS_Y])
    }

    @Test
    fun freshInstallUsesDefaultsInBothOrientations() {
        val sp = preferences(mutableMapOf())
        val expected = FloatingBallConfig.DEFAULT_BALL_POS_X to FloatingBallConfig.DEFAULT_BALL_POS_Y
        assertEquals(expected, FloatingBallConfig.getBallPosition(sp, portrait))
        assertEquals(expected, FloatingBallConfig.getBallPosition(sp, landscape))
    }

    // Lightweight storage fake: exercise the production key selection and migration without Android runtime.
    private fun preferences(values: MutableMap<String, Int>): SharedPreferences {
        val pending = mutableMapOf<String, Int>()
        val editor = Proxy.newProxyInstance(
            SharedPreferences.Editor::class.java.classLoader,
            arrayOf(SharedPreferences.Editor::class.java)
        ) { proxy, method, args ->
            when (method.name) {
                "putInt" -> { pending[args!![0] as String] = args[1] as Int; proxy }
                "apply" -> { values.putAll(pending); pending.clear(); null }
                else -> error("Unexpected editor method: ${method.name}")
            }
        } as SharedPreferences.Editor
        return Proxy.newProxyInstance(
            SharedPreferences::class.java.classLoader,
            arrayOf(SharedPreferences::class.java)
        ) { _, method, args ->
            when (method.name) {
                "getInt" -> values[args!![0] as String] ?: args[1]
                "edit" -> editor
                else -> error("Unexpected preferences method: ${method.name}")
            }
        } as SharedPreferences
    }
}
