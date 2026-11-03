package com.suzu.test

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.resource.ImportCategoryIcons
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.importpkg.ResourcePackageImportService
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.UUID
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@RunWith(AndroidJUnit4::class)
class ImportCategoryIconsTest {
    private lateinit var root: File
    private lateinit var context: Context
    private lateinit var database: SuzuDatabase
    private lateinit var importer: ResourceImportService
    private val fixtures = listOf("20260702121038188211.png", "20260702121038215660.png")

    @Before fun setUp() {
        val app = ApplicationProvider.getApplicationContext<Context>()
        root = File(app.cacheDir, "import_icon_test_${UUID.randomUUID()}").apply { mkdirs() }
        context = object : ContextWrapper(app) {
            override fun getFilesDir() = File(root, "files").apply { mkdirs() }
            override fun getCacheDir() = File(root, "cache").apply { mkdirs() }
        }
        database = Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java).build()
        importer = ResourceImportService(context, database)
    }

    @After fun tearDown() {
        database.close()
        root.deleteRecursively()
    }

    private fun fixture(index: Int): ByteArray = InstrumentationRegistry.getInstrumentation().context.assets
        .open("hashtest/${fixtures[index]}").use { it.readBytes() }

    private suspend fun addImages(categoryId: Long): List<Long> {
        val ids = fixtures.indices.map { importer.import(fixture(it)).resourceId }
        database.resourceCategoryDao().addResourcesToCategoryBatch(ids, categoryId)
        return ids
    }

    @Test fun folderDefaultUsesFinalCategorySortInsteadOfFirstSourceImage() = runBlocking {
        val category = database.categoryDao().insertCategory(CategoryEntity(name = "folder"))
        val ids = addImages(category)
        val first = database.resourceCategoryDao().getResourcesForCategoryList(category).first().id
        assertNotEquals(ids.first(), first)
        ImportCategoryIcons.assignMissing(context, database, listOf(category))
        assertEquals("res:$first", database.categoryDao().getCategoryById(category)!!.iconPath)
        assertEquals(2, File(context.filesDir, "resources").listFiles()!!.size)
    }

    @Test fun preservesValidTextResourceAndAttachmentIcons() = runBlocking {
        val resource = importer.import(fixture(0)).resourceId
        File(context.filesDir, "category_icons/icon.png").apply { parentFile!!.mkdirs(); writeBytes(fixture(0)) }
        for ((index, icon) in listOf("text:🌸", "res:$resource", "file:category_icons/icon.png").withIndex()) {
            val category = database.categoryDao().insertCategory(CategoryEntity(name = "custom$index", iconPath = icon))
            addImages(category)
            ImportCategoryIcons.assignMissing(context, database, listOf(category))
            assertEquals(icon, database.categoryDao().getCategoryById(category)!!.iconPath)
        }
    }

    @Test fun repairsMissingIconsAndIgnoresEmptyOrDeletedCategories() = runBlocking {
        val invalidIcons = listOf(null, "res:99999999", "file:category_icons/missing.png")
        for ((index, icon) in invalidIcons.withIndex()) {
            val category = database.categoryDao().insertCategory(CategoryEntity(name = "missing$index", iconPath = icon))
            addImages(category)
            val first = database.resourceCategoryDao().getResourcesForCategoryList(category).first().id
            ImportCategoryIcons.assignMissing(context, database, listOf(category))
            assertEquals("res:$first", database.categoryDao().getCategoryById(category)!!.iconPath)
        }
        val empty = database.categoryDao().insertCategory(CategoryEntity(name = "empty"))
        val deleted = database.categoryDao().insertCategory(CategoryEntity(name = "deleted"))
        database.categoryDao().deleteCategoryById(deleted)
        ImportCategoryIcons.assignMissing(context, database, listOf(empty, deleted))
        assertEquals(null, database.categoryDao().getCategoryById(empty)!!.iconPath)
    }

    private suspend fun importPackage(icon: JSONObject? = null, withImages: Boolean = true) {
        val category = JSONObject().put("ref", 1).put("name", "package")
        if (icon != null) category.put("icon", icon)
        val resources = JSONArray()
        if (withImages) for (index in fixtures.indices) {
            resources.put(JSONObject().put("asset_path", "assets/$index.png")
                .put("category_refs", JSONArray().put(1)))
        }
        val zip = File(context.cacheDir, "fixture.zip")
        ZipOutputStream(zip.outputStream()).use { output ->
            fun write(path: String, bytes: ByteArray) {
                output.putNextEntry(ZipEntry(path))
                output.write(bytes)
                output.closeEntry()
            }
            write("manifest.json", JSONObject().put("format_version", 1).toString().toByteArray())
            write("catalog.json", JSONObject().put("categories", JSONArray().put(category))
                .put("resources", resources).toString().toByteArray())
            if (withImages) for (index in fixtures.indices) write("assets/$index.png", fixture(index))
        }
        val result = ResourcePackageImportService(context, database, importer)
            .importFromZip(Uri.fromFile(zip)) { _, _, _ -> }
        assertEquals(0, result.summary.failCount)
    }

    @Test fun legacyPackageWithoutIconUsesItsFinalFirstImage() = runBlocking {
        importPackage()
        val category = database.categoryDao().getCategoryByName("package")!!
        val first = database.resourceCategoryDao().getResourcesForCategoryList(category.id).first()
        assertEquals("res:${first.id}", category.iconPath)
    }

    @Test fun packageCustomIconTakesPriorityAndEmptyPackageCategoryStaysDefault() = runBlocking {
        importPackage(JSONObject().put("type", "text").put("text", "🌸"))
        assertEquals("text:🌸", database.categoryDao().getCategoryByName("package")!!.iconPath)
        database.categoryDao().deleteCategory(database.categoryDao().getCategoryByName("package")!!)
        importPackage(withImages = false)
        val category = database.categoryDao().getCategoryByName("package")!!
        assertEquals(null, category.iconPath)
        assertTrue(database.resourceCategoryDao().getResourcesForCategoryList(category.id).isEmpty())
    }

    @Test fun mergingPackagePreservesLocalCustomIcon() = runBlocking {
        database.categoryDao().insertCategory(CategoryEntity(name = "package", iconPath = "text:local"))
        importPackage(JSONObject().put("type", "text").put("text", "incoming"))
        assertEquals("text:local", database.categoryDao().getCategoryByName("package")!!.iconPath)
    }

    @Test fun damagedPackageIconFallsBackToItsFirstImage() = runBlocking {
        importPackage(JSONObject().put("type", "image").put("asset_path", "icons/missing.png")
            .put("asset_size", 10).put("asset_sha256", "invalid"))
        val category = database.categoryDao().getCategoryByName("package")!!
        val first = database.resourceCategoryDao().getResourcesForCategoryList(category.id).first()
        assertEquals("res:${first.id}", category.iconPath)
    }
}
