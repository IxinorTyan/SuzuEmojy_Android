package com.suzu.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.resource.ResourceImportService
import kotlinx.coroutines.runBlocking
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
        val assetManager = context.assets

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
}
