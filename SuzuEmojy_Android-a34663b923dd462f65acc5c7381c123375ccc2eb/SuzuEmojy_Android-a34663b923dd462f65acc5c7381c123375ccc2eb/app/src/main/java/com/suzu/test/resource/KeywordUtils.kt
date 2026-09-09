package com.suzu.test.resource

import com.suzu.test.db.entity.ResourceEntity

enum class MatchMode {
    SUBSTRING,
    PREFIX
}

object KeywordUtils {

    val KEYWORD_MATCH_MODE: MatchMode = MatchMode.SUBSTRING

    /**
     * 按空白拆分 -> trim -> 去空 token -> 保序去重
     */
    fun parse(raw: String): List<String> {
        if (raw.isBlank()) return emptyList()
        return raw.split(Regex("\\s+"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
    }

    /**
     * 空格拼接
     */
    fun join(list: List<String>): String {
        return list.joinToString(" ")
    }

    /**
     * 保存前规范化处理，保留原文大小写
     */
    fun normalize(raw: String): String {
        return join(parse(raw))
    }

    /**
     * 合并新旧标签 (保序去重)
     */
    fun mergeTags(existingRaw: String, newRaw: String): String {
        val existingList = parse(existingRaw)
        val newList = parse(newRaw)
        val merged = (existingList + newList).distinct()
        return join(merged)
    }

    /**
     * 移除指定标签 (大小写不敏感剔除，保留未移除标签的原始大小写)
     */
    fun removeTags(existingRaw: String, removeRaw: String): String {
        val existingList = parse(existingRaw)
        val removeSetLower = parse(removeRaw).map { it.lowercase() }.toSet()
        val filtered = existingList.filter { it.lowercase() !in removeSetLower }
        return join(filtered)
    }

    /**
     * 统一匹配函数：全项目单一入口
     * 匹配范围：keywords + filename，均 lowercase 后比较
     */
    fun matches(resource: ResourceEntity, query: String, mode: MatchMode = KEYWORD_MATCH_MODE): Boolean {
        if (query.isBlank()) return true
        val q = query.trim().lowercase()
        val kw = resource.keywords.lowercase()
        val fn = resource.filename.lowercase()

        return when (mode) {
            MatchMode.SUBSTRING -> {
                kw.contains(q) || fn.contains(q)
            }
            MatchMode.PREFIX -> {
                kw.startsWith(q) || kw.contains(" $q") || fn.startsWith(q)
            }
        }
    }

    /**
     * 判断是否为前缀命中（用于排序加权：前缀命中排在中间命中之前）
     */
    fun isPrefixMatch(resource: ResourceEntity, query: String): Boolean {
        if (query.isBlank()) return false
        val q = query.trim().lowercase()
        val fn = resource.filename.lowercase()
        if (fn.startsWith(q)) return true

        val tokens = parse(resource.keywords)
        return tokens.any { it.lowercase().startsWith(q) }
    }

    /**
     * 过滤并对结果排序：前缀命中优先于中间命中，其余维持 sortOrder ASC, id ASC
     */
    fun filterAndSort(
        resources: List<ResourceEntity>,
        query: String,
        mode: MatchMode = KEYWORD_MATCH_MODE
    ): List<ResourceEntity> {
        if (query.isBlank()) {
            return resources.sortedWith(
                compareBy<ResourceEntity> { it.sortOrder }
                    .thenBy { it.id }
            )
        }

        val matched = resources.filter { matches(it, query, mode) }
        return matched.sortedWith(
            compareByDescending<ResourceEntity> { isPrefixMatch(it, query) }
                .thenBy { it.sortOrder }
                .thenBy { it.id }
        )
    }
}
