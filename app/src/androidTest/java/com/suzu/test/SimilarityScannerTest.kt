package com.suzu.test

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ActivityScenario
import androidx.lifecycle.ViewModelProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.resource.similar.PerceptualHash
import com.suzu.test.resource.similar.SimilarityScanner
import com.suzu.test.ui.similar.SimilarityActivity
import com.suzu.test.ui.similar.SimilarityViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class SimilarityScannerTest {
    private lateinit var directory: File
    private lateinit var context: Context

    @Before fun setup() {
        val base = InstrumentationRegistry.getInstrumentation().targetContext
        directory = File(base.cacheDir, "similarity-test-${UUID.randomUUID()}").apply { mkdirs() }
        context = object : ContextWrapper(base) {
            override fun getFilesDir() = File(directory, "files").apply { mkdirs() }
            override fun getCacheDir() = File(directory, "cache").apply { mkdirs() }
        }
        File(context.filesDir, "resources").mkdirs()
    }

    @After fun cleanup() { directory.deleteRecursively() }

    private fun resource(id: Long, size: Int = 64, transparent: Boolean = false, horizontal: Boolean = false): ResourceEntity {
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (y in 0 until size) for (x in 0 until size) {
            val dark = if (horizontal) y < size / 2 else x < size / 2
            bitmap.setPixel(x, y, if (dark) Color.BLACK else if (transparent) Color.TRANSPARENT else Color.WHITE)
        }
        val file = File(context.filesDir, "resources/$id.png")
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
        return ResourceEntity(id = id, filename = file.name, format = "png", isAnimated = false,
            syncKey = "test:$id:$horizontal", pixelMd5 = null, fileMd5 = "test:$horizontal", width = size,
            height = size, byteSize = file.length())
    }

    @Test fun resizedAndTransparentVersionsMatchAndCacheIsReusable() = runBlocking {
        val resources = listOf(resource(1), resource(2, 128), resource(3, transparent = true))
        val progress = mutableListOf<Int>()
        val first = SimilarityScanner.scan(context, resources, progress::add)
        assertEquals(0, first.failed)
        assertEquals(listOf(1, 2, 3), progress)
        assertTrue(first.items.all { PerceptualHash.distance(first.items.first().hash, it.hash) <= 2 })
        val second = SimilarityScanner.scan(context, resources) {}
        assertEquals(first, second)
        assertTrue(File(context.cacheDir, "similarity-v1.db").isFile)
    }

    @Test fun changedContentInvalidatesCacheAndMissingOrCorruptFilesAreSkipped() = runBlocking {
        val original = resource(1)
        val before = SimilarityScanner.scan(context, listOf(original)) {}.items.single().hash
        val changed = resource(1, horizontal = true)
        val missing = changed.copy(id = 2, filename = "missing.png")
        File(context.filesDir, "resources/corrupt.png").writeText("not an image")
        val corrupt = changed.copy(id = 3, filename = "corrupt.png")
        val after = SimilarityScanner.scan(context, listOf(changed, missing, corrupt)) {}
        assertEquals(2, after.failed)
        assertEquals(1, after.items.size)
        assertNotEquals(before, after.items.single().hash)
    }

    @Test fun openingAndRecreatingPageNeverStartsScanningOrSelectsImages() {
        ActivityScenario.launch(SimilarityActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val state = ViewModelProvider(activity)[SimilarityViewModel::class.java].state.value
                assertFalse(state.busy)
                assertFalse(state.scanned)
                assertTrue(state.selected.isEmpty())
                assertFalse(activity.findViewById<android.widget.Button>(R.id.btnDelete).isEnabled)
            }
            scenario.recreate()
            scenario.onActivity { activity ->
                val state = ViewModelProvider(activity)[SimilarityViewModel::class.java].state.value
                assertFalse(state.scanned)
                assertTrue(state.selected.isEmpty())
            }
        }
    }
}
