package com.suzu.test.resource

import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.floating.FloatingBallConfig

enum class MatchMode {
    SUBSTRING,
    PREFIX
}

object KeywordUtils {

    private val whitespace = Regex("\\s+")

    val KEYWORD_MATCH_MODE: MatchMode = MatchMode.SUBSTRING

    /**
     * 按空白拆分 -> trim -> 去空 token -> 保序去重
     */
    fun parse(raw: String): List<String> {
        if (raw.isBlank()) return emptyList()
        return raw.split(whitespace)
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
     * 匹配范围：按 searchScope 决定匹配 tag(keywords)、分类夹(categoryNames)、或二者皆匹配
     * 严格不匹配 filename
     */
    fun matches(
        resource: ResourceEntity,
        query: String,
        mode: MatchMode = KEYWORD_MATCH_MODE,
        categoryNames: List<String>? = null,
        searchScope: Int = FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY
    ): Boolean {
        val terms = parse(query.lowercase())
        return matchesTerms(resource, terms, mode, categoryNames, searchScope)
    }

    private fun searchableFields(
        resource: ResourceEntity,
        categoryNames: List<String>?,
        searchScope: Int
    ): List<String> = buildList {
        if (searchScope != FloatingBallConfig.SEARCH_SCOPE_CATEGORY_ONLY) add(resource.keywords.lowercase())
        if (searchScope != FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY) {
            categoryNames?.forEach { add(it.lowercase()) }
        }
    }

    private fun matchesTerms(
        resource: ResourceEntity,
        terms: List<String>,
        mode: MatchMode,
        categoryNames: List<String>?,
        searchScope: Int
    ): Boolean {
        if (terms.isEmpty()) return true
        val fields = searchableFields(resource, categoryNames, searchScope)
        return when (mode) {
            MatchMode.SUBSTRING -> terms.all { term -> fields.any { it.contains(term) } }
            MatchMode.PREFIX -> {
                val tokens = fields.flatMap { parse(it) }
                terms.all { term -> tokens.any { it.startsWith(term) } }
            }
        }
    }

    /**
     * 判断是否为前缀命中（用于排序加权：前缀命中排在中间命中之前）
     */
    fun isPrefixMatch(
        resource: ResourceEntity,
        query: String,
        categoryNames: List<String>? = null,
        searchScope: Int = FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY
    ): Boolean {
        val terms = parse(query.lowercase())
        return terms.isNotEmpty() && matchesTerms(resource, terms, MatchMode.PREFIX, categoryNames, searchScope)
    }

    /**
     * 过滤并对结果排序：前缀命中优先于中间命中，其余维持 sortOrder ASC, id ASC
     */
    fun filterAndSort(
        resources: List<ResourceEntity>,
        query: String,
        mode: MatchMode = KEYWORD_MATCH_MODE,
        categoryMap: Map<Long, List<String>>? = null,
        searchScope: Int = FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY
    ): List<ResourceEntity> {
        if (query.isBlank()) {
            return resources.sortedWith(
                compareBy<ResourceEntity> { it.sortOrder }
                    .thenBy { it.id }
            )
        }

        // Parse once per search; compute ranking once per resource, not on every comparison.
        val terms = parse(query.lowercase())
        return resources.asSequence()
            .filter { matchesTerms(it, terms, mode, categoryMap?.get(it.id), searchScope) }
            .map { it to matchesTerms(it, terms, MatchMode.PREFIX, categoryMap?.get(it.id), searchScope) }
            .sortedWith(compareByDescending<Pair<ResourceEntity, Boolean>> { it.second }
                .thenBy { it.first.sortOrder }.thenBy { it.first.id })
            .map { it.first }
            .toList()
    }
}
