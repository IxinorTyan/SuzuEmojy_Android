package com.suzu.test.resource.lansync

import org.json.JSONArray
import org.json.JSONObject

internal val syncId = Regex("[a-f0-9]{32}")
internal val syncKey = Regex("[pf]:[a-f0-9]{32}")
internal val syncIconAsset = Regex("icons/[a-f0-9]{64}\\.(png|gif|jpg|jpeg|webp|bmp)")
internal val reservedSyncNames = setOf("全部表情", "未分类", "新建分类")

internal data class SyncIcon(val type: String, val value: String) {
    fun json() = JSONObject().put("type", type).put("value", value)
}

internal data class SyncCategory(val id: String, val name: String, val icon: SyncIcon?) {
    fun json() = JSONObject().put("id", id).put("name", name).put("icon", icon?.json() ?: JSONObject.NULL)
}

internal data class SyncResource(val key: String, val keywords: List<String>, val categories: List<String>) {
    fun json() = JSONObject().put("key", key).put("keywords", JSONArray(keywords)).put("categories", JSONArray(categories))
}

internal data class SyncLibrary(val categories: List<SyncCategory>, val resources: List<SyncResource>) {
    fun json() = JSONObject().put("categories", JSONArray(categories.map { it.json() }))
        .put("resources", JSONArray(resources.map { it.json() }))

    fun validate(): SyncLibrary {
        val ids = categories.map { it.id }.toSet()
        require(categories.size <= 10_000 && resources.size <= 35_000 &&
            ids.size == categories.size && categories.map { it.name }.toSet().size == categories.size &&
            resources.map { it.key }.toSet().size == resources.size) { "同步清单无效。" }
        require(categories.all { c ->
            syncId.matches(c.id) && c.name.length in 1..256 && c.name.trim() == c.name &&
                c.name !in reservedSyncNames && c.name.none(Char::isISOControl) &&
                when (c.icon?.type) {
                    null -> true
                    "image" -> syncIconAsset.matches(c.icon.value)
                    "text" -> c.icon.value.length in 1..256 && c.icon.value.isNotBlank() && c.icon.value.none(Char::isISOControl)
                    else -> false
                }
        } && resources.all { r ->
            syncKey.matches(r.key) && r.keywords.size <= 1024 && r.keywords.distinct() == r.keywords &&
                r.keywords.all { w -> w.length in 1..1024 && w.none { it.isWhitespace() || it.isISOControl() } } &&
                r.categories.distinct() == r.categories && r.categories.all { it in ids }
        }) { "同步清单无效。" }
        return this
    }

    companion object {
        fun read(json: JSONObject): SyncLibrary {
            val categories = json.getJSONArray("categories")
            val resources = json.getJSONArray("resources")
            require(categories.length() <= 10_000 && resources.length() <= 35_000) { "同步清单过大。" }
            return SyncLibrary((0 until categories.length()).map {
                val item = categories.getJSONObject(it)
                val icon = if (item.isNull("icon")) null else item.getJSONObject("icon").let { i ->
                    SyncIcon(i.getString("type"), i.getString("value"))
                }
                SyncCategory(item.getString("id"), item.getString("name"), icon)
            }, (0 until resources.length()).map {
                val item = resources.getJSONObject(it)
                SyncResource(item.getString("key"), strings(item.getJSONArray("keywords"), 1024),
                    strings(item.getJSONArray("categories"), 10_000))
            }).validate()
        }
    }
}

internal data class SyncAssets(val keys: Set<String>, val icons: Set<String>) {
    val any get() = keys.isNotEmpty() || icons.isNotEmpty()

    fun within(available: SyncLibrary) = keys.all(available.resources.map { it.key }.toHashSet()::contains) &&
        icons.all(iconsIn(available)::contains)

    companion object {
        private fun iconsIn(library: SyncLibrary) = library.categories.mapNotNull { it.icon?.takeIf { i -> i.type == "image" }?.value }.toSet()
        fun needed(have: SyncLibrary, want: SyncLibrary) = SyncAssets(
            want.resources.map { it.key }.toSet() - have.resources.map { it.key }.toSet(), iconsIn(want) - iconsIn(have))

        fun read(json: JSONObject): SyncAssets {
            val keys = strings(json.getJSONArray("keys"), 35_000).toSet()
            val icons = strings(json.getJSONArray("icons"), 10_000).toSet()
            require(keys.all(syncKey::matches) && icons.all(syncIconAsset::matches)) { "同步资源请求无效。" }
            return SyncAssets(keys, icons)
        }
    }
}

private fun strings(array: JSONArray, limit: Int): List<String> {
    require(array.length() <= limit) { "同步清单过大。" }
    return (0 until array.length()).map { array.get(it) as? String ?: error("同步清单的字段无效。") }
}
