package com.suzu.test

import com.suzu.test.ui.import.FolderDocument
import com.suzu.test.ui.import.FolderImageScanner
import com.suzu.test.ui.import.ScannedFolderImage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class FolderImageScannerTest {
    private fun folder(id: String, name: String = id) =
        FolderDocument(id, name, FolderImageScanner.DIRECTORY_MIME_TYPE)

    private fun image(id: String, name: String = "$id.png", mime: String? = "image/png") =
        FolderDocument(id, name, mime)

    private val nestedTree = mapOf(
        "root" to listOf(image("root-image"), folder("cats", "猫猫"), folder("dogs", "狗狗"), folder("empty")),
        "cats" to listOf(image("cat-image"), folder("animated", "动图")),
        "animated" to listOf(image("dance", "dance.gif"), folder("funny", "搞笑")),
        "funny" to listOf(image("funny-cat")),
        "dogs" to listOf(folder("dog-funny", "搞笑")),
        "dog-funny" to listOf(image("dog-image")),
        "empty" to emptyList()
    )

    @Test fun flattensThreeNestedLevelsIntoTheirFirstLevelCategories() = runBlocking {
        val result = FolderImageScanner { nestedTree.getValue(it) }.scan("root", "Stickers")
        assertEquals(mapOf(
            "root-image" to "Stickers",
            "cat-image" to "猫猫",
            "dance" to "猫猫",
            "funny-cat" to "猫猫",
            "dog-image" to "狗狗"
        ), result.images.associate { it.documentId to it.categoryName })
        assertTrue(result.unreadableDirectories.isEmpty())
    }

    @Test fun selectingADeeperRootChangesTheCategoryBoundary() = runBlocking {
        val result = FolderImageScanner { nestedTree.getValue(it) }.scan("cats", "猫猫")
        assertEquals(mapOf("cat-image" to "猫猫", "dance" to "动图", "funny-cat" to "动图"),
            result.images.associate { it.documentId to it.categoryName })
    }

    @Test fun containerRootsAndEmptyFoldersDoNotProduceCategories() = runBlocking {
        val tree = mapOf(
            "root" to listOf(folder("cats", "猫猫"), folder("empty")),
            "cats" to listOf(image("cat")),
            "empty" to emptyList()
        )
        val result = FolderImageScanner { tree.getValue(it) }.scan("root", "Stickers")
        assertEquals(listOf(ScannedFolderImage("cat", "猫猫")), result.images)
        val empty = FolderImageScanner { emptyList() }.scan("root", "Stickers")
        assertTrue(empty.images.isEmpty())
    }

    @Test fun supportsRootOnlyImagesAndMimeOrExtensionDetection() = runBlocking {
        val result = FolderImageScanner { listOf(
            image("upper", "PHOTO.JPG", "application/octet-stream"),
            image("gif", "dance.GIF", null),
            image("webp", "sticker.WEBP", null),
            image("mime", "unknown", "IMAGE/PNG"),
            FolderDocument("text", "notes.txt", "text/plain")
        ) }.scan("root", "Stickers")
        assertEquals(setOf("upper", "gif", "webp", "mime"), result.images.map { it.documentId }.toSet())
        assertEquals(setOf("Stickers"), result.images.map { it.categoryName }.toSet())
    }

    @Test fun unreadableSubtreeIsReportedWhileOtherBranchesContinue() = runBlocking {
        val tree = mapOf(
            "root" to listOf(folder("cats", "猫猫"), folder("dogs", "狗狗")),
            "cats" to listOf(image("cat"), folder("locked", "动图")),
            "dogs" to listOf(image("dog"))
        )
        val result = FolderImageScanner {
            if (it == "locked") throw SecurityException("Permission denied")
            tree.getValue(it)
        }.scan("root", "Stickers")
        assertEquals(setOf("cat", "dog"), result.images.map { it.documentId }.toSet())
        assertEquals(listOf("Stickers/猫猫/动图"), result.unreadableDirectories)
    }

    @Test fun unreadableRootFailsRatherThanReportingAnEmptyFolder() {
        assertThrows(IOException::class.java) {
            runBlocking { FolderImageScanner { throw IOException("Cannot query") }.scan("root", "Stickers") }
        }
    }

    @Test fun handlesRepeatedDirectoryIdsCyclesAndImagesWithoutLosingCategoryMemberships() = runBlocking {
        val reads = mutableListOf<String>()
        val tree = mapOf(
            "root" to listOf(folder("cats", "猫猫"), folder("cats", "猫猫"), folder("dogs", "狗狗")),
            "cats" to listOf(image("shared"), image("shared"), folder("root")),
            "dogs" to listOf(image("shared"))
        )
        val result = FolderImageScanner { reads.add(it); tree.getValue(it) }.scan("root", "Stickers")
        assertEquals(listOf("root", "cats", "dogs"), reads)
        assertEquals(listOf(ScannedFolderImage("shared", "猫猫"), ScannedFolderImage("shared", "狗狗")), result.images)
    }

    @Test fun deeplyNestedDirectoriesDoNotUseTheCallStack() = runBlocking {
        val depth = 2000
        val result = FolderImageScanner { id ->
            val level = id.toInt()
            if (level < depth) listOf(folder((level + 1).toString(), if (level == 0) "猫猫" else "子目录"))
            else listOf(image("bottom"))
        }.scan("0", "Stickers")
        assertEquals(listOf(ScannedFolderImage("bottom", "猫猫")), result.images)
    }

    @Test fun providerCancellationIsNotReportedAsAnUnreadableSubfolder() {
        assertThrows(CancellationException::class.java) {
            runBlocking {
                FolderImageScanner {
                    if (it == "root") listOf(folder("cats")) else throw CancellationException("Cancelled")
                }.scan("root", "Stickers")
            }
        }
    }

    @Test fun cancellationStopsBeforeScanningTheNextDirectory() {
        val reads = mutableListOf<String>()
        assertThrows(CancellationException::class.java) {
            runBlocking {
                val job = currentCoroutineContext()[kotlinx.coroutines.Job]!!
                FolderImageScanner { reads.add(it); listOf(folder("next")) }
                    .scan("root", "Stickers") { _, _ -> job.cancel() }
            }
        }
        assertEquals(listOf("root"), reads)
    }

    @Test fun cancellationAfterTheLastDirectoryDoesNotPublishACompletedScan() {
        var published = false
        assertThrows(CancellationException::class.java) {
            runBlocking {
                val job = currentCoroutineContext()[kotlinx.coroutines.Job]!!
                FolderImageScanner { listOf(image("root-image")) }
                    .scan("root", "Stickers") { _, _ -> job.cancel() }
                published = true
            }
        }
        assertFalse(published)
    }

    @Test fun reservedAndMissingNamesStillHaveUsableCategories() = runBlocking {
        val tree = mapOf(
            "root" to listOf(image("root-image"), folder("recent", "最近"), folder("unnamed", "  ")),
            "recent" to listOf(image("recent-image")),
            "unnamed" to listOf(image("unnamed-image"))
        )
        val result = FolderImageScanner { tree.getValue(it) }.scan("root", "全部")
        assertEquals(mapOf("root-image" to null, "recent-image" to null,
            "unnamed-image" to null), result.images.associate { it.documentId to it.categoryName })
    }
}
