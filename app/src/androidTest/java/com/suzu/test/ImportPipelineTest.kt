package com.suzu.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class ImportPipelineTest {

    private lateinit var context: Context
    private lateinit var testDb: SuzuDatabase
    private lateinit var importService: ResourceImportService
    private lateinit var resourcesDir: File

    private data class Baseline(
        val filename: String,
        val expectedSyncKey: String
    )

    companion object {
        private val BASELINES = listOf(
            Baseline("20260702121038188211.png", "p:92b93896345dc57b4b461983afe3ac48"),
            Baseline("20260702121038215660.png", "p:d5499ed1398cc8cc2f346db3cce16da6"),
            Baseline("20260702121038248736.png", "p:426cac601783a517be01ee830c5e940c")
        )
    }

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        // 使用独立测试隔离目录，绝不污染真实 filesDir/resources/
        resourcesDir = File(context.cacheDir, "test_isolated_resources_${System.currentTimeMillis()}")
        if (resourcesDir.exists()) {
            resourcesDir.deleteRecursively()
        }
        resourcesDir.mkdirs()

        testDb = Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        importService = ResourceImportService(context, testDb, targetDir = resourcesDir)
    }

    @After
    fun tearDown() {
        testDb.close()
        if (resourcesDir.exists()) {
            resourcesDir.deleteRecursively()
        }
    }

    @Test
    fun testImportPipelineDeduplicationAndTransaction() = runBlocking {
        val assetManager = InstrumentationRegistry.getInstrumentation().context.assets

        // 1. 第一次导入三张图并记录首次 lastUsedAt
        val initialLastUsedMap = mutableMapOf<String, Long>()
        for (baseline in BASELINES) {
            val bytes = assetManager.open("hashtest/${baseline.filename}").use { it.readBytes() }
            val result = importService.import(bytes)

            assertFalse("第一次导入不能为重复图: ${baseline.filename}", result.isDuplicate)
            assertTrue("返回的资源ID必须有效", result.resourceId > 0)

            val entity = testDb.resourceDao().getById(result.resourceId)
            initialLastUsedMap[baseline.expectedSyncKey] = entity?.lastUsedAt ?: 0L
        }

        // 等待 15ms 确保时间戳发生微小递增
        kotlinx.coroutines.delay(15)

        // 2. 第二次导入相同的三张图 (触发去重与 lastUsedAt 刷新)
        for (baseline in BASELINES) {
            val bytes = assetManager.open("hashtest/${baseline.filename}").use { it.readBytes() }
            val result = importService.import(bytes)

            assertTrue("第二次导入必须判定为重复图: ${baseline.filename}", result.isDuplicate)

            val updatedEntity = testDb.resourceDao().getBySyncKey(baseline.expectedSyncKey)
            val oldLastUsed = initialLastUsedMap[baseline.expectedSyncKey] ?: 0L
            val newLastUsed = updatedEntity?.lastUsedAt ?: 0L

            assertTrue(
                "重复导入时 lastUsedAt 必须被刷新为更大的时间戳: old=$oldLastUsed, new=$newLastUsed",
                newLastUsed > oldLastUsed
            )
        }

        // 3. 校验数据库最终状态
        val allResources = testDb.resourceDao().getAllResources()
        assertEquals("最终 resources 表中恰好只有 3 行记录", 3, allResources.size)

        val syncKeys = allResources.map { it.syncKey }.toSet()
        for (baseline in BASELINES) {
            assertTrue(
                "数据库中的 syncKey 必须与 PC 基准完全一致: ${baseline.expectedSyncKey}",
                syncKeys.contains(baseline.expectedSyncKey)
            )
        }

        // 4. 校验隔离测试目录下的物理文件
        val onDiskFiles = resourcesDir.listFiles()?.filter { it.isFile } ?: emptyList()
        assertEquals("测试隔离目录下必须恰好有 3 个物理文件", 3, onDiskFiles.size)
    }

    private fun sourceBytes(): ByteArray = InstrumentationRegistry.getInstrumentation().context.assets
        .open("hashtest/${BASELINES.first().filename}").use { it.readBytes() }

    @Test fun preparingAnExistingImageSkipsPngEncoding() = runBlocking {
        val bytes = sourceBytes()
        val first = importService.import(bytes)
        val prepared = importService.prepare(bytes)
        assertEquals(null, prepared.content)
        val duplicate = importService.importPrepared(prepared)
        assertTrue(duplicate.isDuplicate)
        assertEquals(first.resourceId, duplicate.resourceId)
        assertEquals(1, resourcesDir.listFiles()!!.size)
    }

    @Test fun deletedDuplicateIsRebuiltFromItsSource() = runBlocking {
        val bytes = sourceBytes()
        val first = importService.import(bytes)
        val prepared = importService.prepare(bytes)
        testDb.resourceDao().deleteById(first.resourceId)
        File(resourcesDir, first.filename).delete()
        val replacement = importService.importPrepared(prepared)
        assertFalse(replacement.isDuplicate)
        assertEquals(BASELINES.first().expectedSyncKey, replacement.syncKey)
        assertEquals(1, resourcesDir.listFiles()!!.size)
    }

    @Test fun preparationAheadOfTheWriterStillDeduplicatesAndRemovesUnusedFiles() = runBlocking {
        val bytes = sourceBytes()
        val firstPrepared = importService.prepare(bytes)
        val secondPrepared = importService.prepare(bytes)
        val first = importService.importPrepared(firstPrepared)
        val second = importService.importPrepared(secondPrepared)
        assertFalse(first.isDuplicate)
        assertTrue(second.isDuplicate)
        assertEquals(first.resourceId, second.resourceId)
        assertEquals(1, testDb.resourceDao().getAllResources().size)
        assertEquals(1, resourcesDir.listFiles()!!.size)
    }

    @Test fun concurrentImportsCommitOnlyOneResource() = runBlocking {
        val bytes = sourceBytes()
        val tasks = List(2) { async(Dispatchers.IO) { importService.import(bytes) } }
        val results = tasks.map { it.await() }
        assertEquals(1, results.count { !it.isDuplicate })
        assertEquals(1, results.map { it.resourceId }.distinct().size)
        assertEquals(1, resourcesDir.listFiles()!!.size)
    }

    @Test fun failedCategoryTransactionRollsBackResourceAndFile() = runBlocking {
        try {
            importService.importPrepared(importService.prepare(sourceBytes())) { imported ->
                val categoryId = testDb.categoryDao().insertCategory(CategoryEntity(name = "rollback"))
                testDb.resourceCategoryDao().addResourceToCategory(ResourceCategoryEntity(imported.resourceId, categoryId))
                throw IllegalStateException("relation failure")
            }
            throw AssertionError("Expected transaction failure")
        } catch (e: IllegalStateException) {
            assertEquals("relation failure", e.message)
        }
        assertTrue(testDb.resourceDao().getAllResources().isEmpty())
        assertTrue(testDb.categoryDao().getAllCategories().isEmpty())
        assertEquals(0, resourcesDir.listFiles()!!.size)
    }

    @Test fun cancellationDuringCommitFinishesTheCurrentCategoryRelation() = runBlocking {
        withTimeout(5_000) {
            val prepared = importService.prepare(sourceBytes())
            val committing = CompletableDeferred<Unit>()
            val finish = CompletableDeferred<Unit>()
            val task = launch(Dispatchers.IO) {
                importService.importPrepared(prepared) { imported ->
                    committing.complete(Unit)
                    finish.await()
                    val categoryId = testDb.categoryDao().insertCategory(CategoryEntity(name = "kept"))
                    testDb.resourceCategoryDao().addResourceToCategory(ResourceCategoryEntity(imported.resourceId, categoryId))
                }
            }
            committing.await()
            task.cancel()
            finish.complete(Unit)
            task.join()
            val resource = testDb.resourceDao().getAllResources().single()
            assertEquals("kept", testDb.resourceCategoryDao().getCategoriesForResource(resource.id).single().name)
            assertEquals(1, resourcesDir.listFiles()!!.size)
        }
    }
}
