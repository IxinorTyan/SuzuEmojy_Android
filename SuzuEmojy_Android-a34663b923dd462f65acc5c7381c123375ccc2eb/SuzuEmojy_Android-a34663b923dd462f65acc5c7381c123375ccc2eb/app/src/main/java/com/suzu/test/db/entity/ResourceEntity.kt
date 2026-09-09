package com.suzu.test.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "resources",
    indices = [
        Index(value = ["filename"], unique = true),
        Index(value = ["sync_key"], unique = true)
    ]
)
data class ResourceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "filename")
    val filename: String,

    @ColumnInfo(name = "format")
    val format: String,

    @ColumnInfo(name = "is_animated")
    val isAnimated: Boolean,

    @ColumnInfo(name = "sync_key")
    val syncKey: String,

    @ColumnInfo(name = "pixel_md5")
    val pixelMd5: String?,

    @ColumnInfo(name = "file_md5")
    val fileMd5: String,

    @ColumnInfo(name = "width")
    val width: Int,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "byte_size")
    val byteSize: Long,

    @ColumnInfo(name = "quality_score")
    val qualityScore: Double = 0.0,

    @ColumnInfo(name = "use_count")
    val useCount: Int = 0,

    @ColumnInfo(name = "last_used_at")
    val lastUsedAt: Long? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "keywords")
    val keywords: String = "",

    @ColumnInfo(name = "sort_order")
    val sortOrder: Int = 0
)
