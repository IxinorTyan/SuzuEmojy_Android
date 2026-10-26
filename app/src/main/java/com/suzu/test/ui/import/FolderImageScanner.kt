package com.suzu.test.ui.import

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import java.util.ArrayDeque

internal data class FolderDocument(
    val id: String,
    val name: String?,
    val mimeType: String?
)

internal data class ScannedFolderImage(val documentId: String, val categoryName: String?)

internal data class FolderScanResult(
    val images: List<ScannedFolderImage>,
    val unreadableDirectories: List<String>
)

/** The selected root and each immediate child own their images, regardless of deeper nesting. */
internal class FolderImageScanner(
    private val readChildren: suspend (String) -> List<FolderDocument>
) {
    private data class Directory(
        val id: String,
        val categoryName: String?,
        val path: String,
        val isRoot: Boolean = false
    )

    suspend fun scan(
        rootId: String,
        rootName: String?,
        onProgress: (directories: Int, images: Int) -> Unit = { _, _ -> }
    ): FolderScanResult {
        val rootCategory = normalizeFolderCategoryName(rootName)
        val pending = ArrayDeque<Directory>()
        val visited = mutableSetOf(rootId)
        val seenImages = mutableSetOf<Pair<String, String?>>()
        val images = mutableListOf<ScannedFolderImage>()
        val unreadable = mutableListOf<String>()
        var scannedDirectories = 0
        pending.addLast(Directory(rootId, rootCategory, rootName ?: "所选文件夹", isRoot = true))

        while (pending.isNotEmpty()) {
            currentCoroutineContext().ensureActive()
            val directory = pending.removeFirst()
            val entries = try {
                readChildren(directory.id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Failure to read the selected root must never look like an empty, successful scan.
                if (directory.isRoot) throw e
                unreadable.add(directory.path)
                continue
            }
            for (entry in entries) {
                currentCoroutineContext().ensureActive()
                if (entry.mimeType?.equals(DIRECTORY_MIME_TYPE, ignoreCase = true) == true) {
                    if (!visited.add(entry.id)) continue
                    val rawName = entry.name?.trim()?.takeIf { it.isNotEmpty() }
                    val name = rawName ?: "未命名文件夹"
                    val category = if (directory.isRoot) {
                        normalizeFolderCategoryName(rawName) ?: rootCategory
                    } else directory.categoryName
                    pending.addLast(Directory(entry.id, category, "${directory.path}/$name"))
                } else if (isImage(entry) && seenImages.add(entry.id to directory.categoryName)) {
                    images.add(ScannedFolderImage(entry.id, directory.categoryName))
                }
            }
            scannedDirectories++
            onProgress(scannedDirectories, images.size)
        }
        currentCoroutineContext().ensureActive()
        return FolderScanResult(images, unreadable)
    }

    private fun isImage(entry: FolderDocument): Boolean {
        val extension = entry.name?.substringAfterLast('.', "")?.lowercase()
        return entry.mimeType?.startsWith("image/", ignoreCase = true) == true ||
            extension in SUPPORTED_IMAGE_EXTENSIONS
    }

    companion object {
        const val DIRECTORY_MIME_TYPE = "vnd.android.document/directory"
        private val SUPPORTED_IMAGE_EXTENSIONS = setOf("png", "jpg", "jpeg", "gif", "webp", "bmp")
    }
}
