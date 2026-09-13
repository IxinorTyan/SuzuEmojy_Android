package com.suzu.test

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceEntity
import kotlinx.coroutines.flow.first
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
class BatchOperationsTest {

    private lateinit var db: SuzuDatabase
    private lateinit var testFilesDir: File

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java).build()
        testFilesDir = File(context.cacheDir, "test_batch_resources").apply {
            if (!exists()) mkdirs()
        }
    }

    @After
    fun tearDown() {
        db.close()
        testFilesDir.deleteRecursively()
    }

    @Test
    fun testBatchKeywordsAppendAndRemoveInSingleTransaction() = runBlocking {
        val id1 = db.resourceDao().insert(
            ResourceEntity(filename = "f1.png", format = "PNG", isAnimated = false, syncKey = "k1", pixelMd5 = "m1", fileMd5 = "m1", width = 100, height = 100, byteSize = 100, keywords = "可爱 猫猫")
        )
        val id2 = db.resourceDao().insert(
            ResourceEntity(filename = "f2.png", format = "PNG", isAnimated = false, syncKey = "k2", pixelMd5 = "m2", fileMd5 = "m2", width = 100, height = 100, byteSize = 100, keywords = "可爱 狗狗")
        )

        // 批量追加 "开心 猫猫"
        val affectedAppend = db.resourceDao().batchAppendKeywords(listOf(id1, id2), "开心 猫猫")
        assertEquals(2, affectedAppend)

        val res1AfterAppend = db.resourceDao().getById(id1)
        val res2AfterAppend = db.resourceDao().getById(id2)
        assertEquals("可爱 猫猫 开心", res1AfterAppend?.keywords)
        assertEquals("可爱 狗狗 开心 猫猫", res2AfterAppend?.keywords)

        // 批量移除 "可爱 开心" (大小写不敏感)
        val affectedRemove = db.resourceDao().batchRemoveKeywords(listOf(id1, id2), "可爱 开心")
        assertEquals(2, affectedRemove)

        val res1AfterRemove = db.resourceDao().getById(id1)
        val res2AfterRemove = db.resourceDao().getById(id2)
        assertEquals("猫猫", res1AfterRemove?.keywords)
        assertEquals("狗狗 猫猫", res2AfterRemove?.keywords)
    }

    @Test
    fun testBatchCategoryAddToFrontAndRemove() = runBlocking {
        val catId = db.categoryDao().insert(CategoryEntity(name = "猫组", sortOrder = 0))

        val id1 = db.resourceDao().insert(
            ResourceEntity(filename = "f1.png", format = "PNG", isAnimated = false, syncKey = "k1", pixelMd5 = "m1", fileMd5 = "m1", width = 100, height = 100, byteSize = 100)
        )
        val id2 = db.resourceDao().insert(
            ResourceEntity(filename = "f2.png", format = "PNG", isAnimated = false, syncKey = "k2", pixelMd5 = "m2", fileMd5 = "m2", width = 100, height = 100, byteSize = 100)
        )

        // 批量加入分类
        val addedCount = db.resourceCategoryDao().addResourcesToCategoryBatch(listOf(id1, id2), catId)
        assertEquals(2, addedCount)

        val catResources = db.resourceCategoryDao().getResourcesForCategory(catId).first()
        assertEquals(2, catResources.size)

        // 批量移出
        val removedCount = db.resourceCategoryDao().removeResourcesFromCategoryBatch(catId, listOf(id1))
        assertEquals(1, removedCount)

        val remainingCatResources = db.resourceCategoryDao().getResourcesForCategory(catId).first()
        assertEquals(1, remainingCatResources.size)
        assertEquals(id2, remainingCatResources[0].id)
    }

    @Test
    fun testBatchDeleteLeavesNoOrphanFiles() = runBlocking {
        val id1 = db.resourceDao().insert(
            ResourceEntity(filename = "f1.png", format = "PNG", isAnimated = false, syncKey = "k1", pixelMd5 = "m1", fileMd5 = "m1", width = 100, height = 100, byteSize = 100)
        )
        val id2 = db.resourceDao().insert(
            ResourceEntity(filename = "f2.png", format = "PNG", isAnimated = false, syncKey = "k2", pixelMd5 = "m2", fileMd5 = "m2", width = 100, height = 100, byteSize = 100)
        )

        // 创建本地物理文件
        val file1 = File(testFilesDir, "f1.png").apply { writeText("dummy1") }
        val file2 = File(testFilesDir, "f2.png").apply { writeText("dummy2") }
        assertTrue(file1.exists())
        assertTrue(file2.exists())

        val ids = listOf(id1, id2)
        // 1. 读出文件名
        val targets = db.resourceDao().getResourcesByIds(ids)
        // 2. DAO 事务删库
        val deletedCount = db.resourceDao().deleteByIds(ids)
        assertEquals(2, deletedCount)

        // 3. 事务成功后删物理文件
        targets.forEach { res ->
            val f = File(testFilesDir, res.filename)
            if (f.exists()) f.delete()
        }

        assertFalse(file1.exists())
        assertFalse(file2.exists())

        // 检查数据库记录数与文件数对齐（均为 0）
        val dbCount = db.resourceDao().getAllResources().size
        val fileCount = testFilesDir.listFiles()?.size ?: 0
        assertEquals(0, dbCount)
        assertEquals(0, fileCount)
    }

    @Test
    fun testMoveResourcesToFront() = runBlocking {
        val id1 = db.resourceDao().insert(
            ResourceEntity(filename = "f1.png", format = "PNG", isAnimated = false, syncKey = "k1", pixelMd5 = "m1", fileMd5 = "m1", width = 100, height = 100, byteSize = 100, sortOrder = 0)
        )
        val id2 = db.resourceDao().insert(
            ResourceEntity(filename = "f2.png", format = "PNG", isAnimated = false, syncKey = "k2", pixelMd5 = "m2", fileMd5 = "m2", width = 100, height = 100, byteSize = 100, sortOrder = 1)
        )
        val id3 = db.resourceDao().insert(
            ResourceEntity(filename = "f3.png", format = "PNG", isAnimated = false, syncKey = "k3", pixelMd5 = "m3", fileMd5 = "m3", width = 100, height = 100, byteSize = 100, sortOrder = 2)
        )

        // 将 id3 和 id2 移动到最前 (保持 [id3, id2] 相对顺序)
        val count = db.resourceDao().moveResourcesToFront(listOf(id3, id2))
        assertEquals(2, count)

        val orderedList = db.resourceDao().getAllResourcesOrdered().first()
        assertEquals(listOf(id3, id2, id1), orderedList.map { it.id })
    }

    @Test
    fun testMoveResourcesToFrontInCategory() = runBlocking {
        val catId = db.categoryDao().insert(CategoryEntity(name = "测试组", sortOrder = 0))

        val id1 = db.resourceDao().insert(
            ResourceEntity(filename = "f1.png", format = "PNG", isAnimated = false, syncKey = "k1", pixelMd5 = "m1", fileMd5 = "m1", width = 100, height = 100, byteSize = 100)
        )
        val id2 = db.resourceDao().insert(
            ResourceEntity(filename = "f2.png", format = "PNG", isAnimated = false, syncKey = "k2", pixelMd5 = "m2", fileMd5 = "m2", width = 100, height = 100, byteSize = 100)
        )
        val id3 = db.resourceDao().insert(
            ResourceEntity(filename = "f3.png", format = "PNG", isAnimated = false, syncKey = "k3", pixelMd5 = "m3", fileMd5 = "m3", width = 100, height = 100, byteSize = 100)
        )

        db.resourceCategoryDao().addResourcesToCategoryBatch(listOf(id1, id2, id3), catId)

        // 将 id3 和 id2 移动到该分类最前
        val count = db.resourceCategoryDao().moveResourcesToFrontInCategory(catId, listOf(id3, id2))
        assertEquals(2, count)

        val catList = db.resourceCategoryDao().getResourcesForCategory(catId).first()
        assertEquals(listOf(id3, id2, id1), catList.map { it.id })

        // 验证全局主库资源不受分类内置顶影响
        val globalList = db.resourceDao().getAllResourcesOrdered().first()
        assertEquals(3, globalList.size)
    }
}
