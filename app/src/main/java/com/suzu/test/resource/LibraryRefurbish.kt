package com.suzu.test.resource

import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.resource.clean.CleanResult
import com.suzu.test.resource.clean.ResourceCleaner
import com.suzu.test.resource.hash.ResourceHasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

internal class LibraryRefurbish(
    files: File,
    private val availableBytes: () -> Long = { files.usableSpace }
) {
    private val resources = File(files, "resources")
    private val backup = File(files, "library-refurbish-v1")
    private val staging = File(backup, "next")

    private data class Row(val id: Long, val name: String, val key: String)
    private data class Scan(val end: Long, val last: Long) {
        fun state() = "scan:$end:$last"
    }

    // One runner per app process. Decode outside Room; each row and its progress commit together.
    suspend fun run(database: SuzuDatabase, afterCommit: (Long) -> Unit = {}) = withContext(Dispatchers.IO) {
        suspend fun <T> transaction(block: (SupportSQLiteDatabase) -> T): T =
            database.withTransaction { block(database.openHelper.writableDatabase) }

        val initial = transaction { db ->
            val previous = readState(db)
            archivePreviousRun(db, previous)
            if (previous == DONE || previous?.startsWith("[") == true) return@transaction null

            // A stopped process can leave an output file before its SQLite commit.
            resources.listFiles { file -> file.isFile && file.name.matches(STAGED_NAME) }?.forEach { file ->
                if (!db.exists("SELECT 1 FROM resources WHERE filename = ?", arrayOf(file.name)))
                    Files.delete(file.toPath())
            }
            staging.listFiles()?.forEach { Files.delete(it.toPath()) }

            if (previous != null) {
                val (_, end, last) = previous.split(':')
                Scan(end.toLong(), last.toLong())
            } else {
                val end = db.query("SELECT COALESCE(MAX(id), 0) FROM resources").use { it.moveToFirst(); it.getLong(0) }
                if (end == 0L) {
                    writeState(db, RECEIPT, DONE)
                    null
                } else Scan(end, 0).also { writeState(db, RECEIPT, it.state()) }
            }
        } ?: return@withContext

        var scan = initial
        while (true) {
            val batch = transaction { db ->
                db.query("SELECT id, filename, sync_key FROM resources WHERE id > ? AND id <= ? ORDER BY id LIMIT 64",
                    arrayOf<Any>(scan.last, scan.end)).use { cursor ->
                    buildList { while (cursor.moveToNext()) add(Row(cursor.getLong(0), cursor.getString(1), cursor.getString(2))) }
                }
            }
            if (batch.isEmpty()) {
                transaction { writeState(it, RECEIPT, DONE) }
                return@withContext
            }
            for (row in batch) {
                val next = scan.copy(last = row.id)
                val oldFile = File(resources, row.name)
                val clean = if (!oldFile.isFile || oldFile.length() > Runtime.getRuntime().maxMemory() / 8) null else {
                    val bytes = try { oldFile.readBytes() } catch (_: FileNotFoundException) { null }
                    bytes?.let { prepare(it) }
                }
                val key = clean?.let { "p:${it.md5}" }
                val newName = "$PREFIX${row.id}.png"
                val temp = File(staging, newName)
                val replaced = try {
                    val md5 = if (clean != null && key != null && transaction { canReplace(it, row, key) }) {
                        if (availableBytes() < clean.pngBytes.size.toLong() + SPACE_RESERVE)
                            throw IOException("空间不足，保留原图并暂停清洗")
                        check(staging.isDirectory || staging.mkdirs())
                        FileOutputStream(temp).use { it.write(clean.pngBytes); it.fd.sync() }
                        ResourceHasher.fileMd5(clean.pngBytes)
                    } else null
                    transaction { db ->
                        val replace = if (md5 != null && key != null && clean != null && canReplace(db, row, key)) {
                            Files.move(temp.toPath(), File(resources, newName).toPath(), StandardCopyOption.ATOMIC_MOVE)
                            db.execSQL("""UPDATE resources SET filename = ?, format = 'PNG', is_animated = 0, sync_key = ?,
                                pixel_md5 = ?, file_md5 = ?, width = ?, height = ?, byte_size = ? WHERE id = ?""", arrayOf(
                                newName, key, clean.md5, md5, clean.width, clean.height, clean.pngBytes.size.toLong(), row.id))
                            writeState(db, ORIGINAL + row.id, row.name)
                            true
                        } else false
                        writeState(db, RECEIPT, next.state())
                        replace
                    }
                } finally { temp.delete() }
                scan = next
                if (replaced) afterCommit(row.id)
            }
        }
    }

    private fun canReplace(db: SupportSQLiteDatabase, row: Row, key: String): Boolean =
        db.exists("SELECT 1 FROM resources WHERE id = ? AND filename = ? AND sync_key = ?", arrayOf(row.id, row.name, row.key)) &&
            (key == row.key || (!db.exists("SELECT 1 FROM lan_sync_state WHERE `key` LIKE 'revision:%'") &&
                !db.exists("SELECT 1 FROM resources WHERE sync_key = ?", arrayOf(key))))

    private fun prepare(bytes: ByteArray): CleanResult.StaticImage? {
        if ((ResourceHasher.isGif(bytes) && !ResourceCleaner.isSingleFrameGif(bytes)) ||
            ResourceCleaner.isAnimatedWebP(bytes) || ResourceCleaner.isAnimatedPng(bytes)) return null
        return try {
            val clean = ResourceCleaner.decodeStatic(bytes, normalize = true)?.use { it.encode() }
            clean?.takeUnless { it.pngBytes.contentEquals(bytes) }
        } catch (_: IllegalArgumentException) { null }
    }

    private fun archivePreviousRun(db: SupportSQLiteDatabase, previous: String?) {
        val originals = File(backup, "original")
        fun archive(name: String) {
            if (db.exists("SELECT 1 FROM resources WHERE filename = ?", arrayOf(name))) return
            val source = File(resources, name)
            if (!source.isFile) return
            check(originals.isDirectory || originals.mkdirs())
            Files.move(source.toPath(), File(originals, name).toPath(), StandardCopyOption.ATOMIC_MOVE)
        }

        // Readers in the previous process can still hold old filenames after a row switch.
        // Archive on the next process start, when no reader can hold those old snapshots.
        val names = db.query("SELECT `key`, value FROM lan_sync_state WHERE `key` LIKE ?", arrayOf(ORIGINAL + "%")).use { cursor ->
            buildList { while (cursor.moveToNext()) add(cursor.getString(0) to cursor.getString(1)) }
        }
        for ((key, name) in names) {
            archive(name)
            db.execSQL("DELETE FROM lan_sync_state WHERE `key` = ?", arrayOf(key))
        }
        if (previous?.startsWith("[") == true) {
            val legacy = JSONArray(previous)
            for (i in 0 until legacy.length()) archive(legacy.getString(i))
            writeState(db, RECEIPT, DONE)
        }
    }

    private fun readState(db: SupportSQLiteDatabase): String? =
        db.query("SELECT value FROM lan_sync_state WHERE `key` = ?", arrayOf<Any>(RECEIPT)).use {
            if (it.moveToFirst()) it.getString(0) else null
        }

    private fun writeState(db: SupportSQLiteDatabase, key: String, state: String) {
        db.execSQL("INSERT OR REPLACE INTO lan_sync_state (`key`, value) VALUES (?, ?)", arrayOf(key, state))
    }

    private fun SupportSQLiteDatabase.exists(sql: String, args: Array<out Any?> = emptyArray()): Boolean =
        query(sql, args).use { it.moveToFirst() }

    private companion object {
        const val PREFIX = "refurbish-v1-"
        const val RECEIPT = "android.library-refurbish.v1"
        const val ORIGINAL = "$RECEIPT.original:"
        const val DONE = "done"
        const val SPACE_RESERVE = 16L * 1024 * 1024
        val STAGED_NAME = Regex("""refurbish-v1-[0-9]+\.png""")
    }
}
