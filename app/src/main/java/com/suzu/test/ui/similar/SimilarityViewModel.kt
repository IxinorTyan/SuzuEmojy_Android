package com.suzu.test.ui.similar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.resource.similar.HashedResource
import com.suzu.test.resource.similar.SimilarityIndex
import com.suzu.test.resource.similar.SimilarityScanner
import com.suzu.test.resource.similar.AnimationSimilarity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class SimilarityMedia(val label: String) {
    ALL("全部图片（静态图与动图分别比较）"), STATIC("仅静态图"), ANIMATED("仅动图（GIF）");
    fun accepts(resource: ResourceEntity) = this == ALL || resource.isAnimated == (this == ANIMATED)
}

data class SimilarityState(
    val categories: List<CategoryEntity> = emptyList(),
    val categoryId: Long = 0,
    val media: SimilarityMedia = SimilarityMedia.ALL,
    val threshold: Int = 6,
    val busy: Boolean = false,
    val deleting: Boolean = false,
    val scanned: Boolean = false,
    val message: String = "静态图与动图分别比较。结果为疑似相似项，请自行比较后选择删除。",
    val groups: List<List<ResourceEntity>> = emptyList(),
    val selected: Set<Long> = emptySet(),
    val durations: Map<Long, Long> = emptyMap()
)

class SimilarityViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseProvider.getDatabase(application)
    private val preferences = application.getSharedPreferences("similarity_settings", 0)
    private val mutableState = MutableStateFlow(SimilarityState(threshold = preferences.getInt("threshold", 6).coerceIn(0, 16)))
    val state = mutableState.asStateFlow()
    private var fingerprints: List<HashedResource> = emptyList()
    private var job: Job? = null
    private var skipped = 0
    private var failed = 0

    init {
        viewModelScope.launch {
            try {
                val categories = database.categoryDao().getAllCategories()
                mutableState.value = mutableState.value.copy(categories = categories)
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                mutableState.value = mutableState.value.copy(message = "分类读取失败，仍可尝试扫描全部资源。")
            }
        }
    }

    fun chooseCategory(id: Long) {
        if (state.value.busy || state.value.deleting || state.value.categoryId == id) return
        fingerprints = emptyList()
        mutableState.value = state.value.copy(categoryId = id, scanned = false, groups = emptyList(),
            selected = emptySet(), message = "扫描范围已更改，请点击开始扫描。")
    }

    fun chooseMedia(media: SimilarityMedia) {
        if (state.value.busy || state.value.deleting || state.value.media == media) return
        fingerprints = emptyList()
        mutableState.value = state.value.copy(media = media, scanned = false, groups = emptyList(),
            selected = emptySet(), message = "图片类型已更改，请点击开始扫描。")
    }

    fun scan() {
        if (state.value.busy || state.value.deleting) return
        fingerprints = emptyList()
        mutableState.value = state.value.copy(busy = true, scanned = false, groups = emptyList(),
            selected = emptySet(), message = "正在读取资源…")
        job = viewModelScope.launch {
            try {
                val id = state.value.categoryId
                val resources = if (id == 0L) database.resourceDao().getAllResourcesOrderedList()
                    else database.resourceCategoryDao().getResourcesForCategoryList(id)
                val candidates = resources.filter { state.value.media.accepts(it) }.sortedBy { it.id }
                skipped = resources.size - candidates.size
                val result = withContext(Dispatchers.IO) {
                    SimilarityScanner.scan(getApplication(), candidates) { done ->
                        mutableState.value = mutableState.value.copy(message = "已扫描 $done/${candidates.size} 张图片" +
                            if (candidates.getOrNull(done)?.isAnimated == true) " · 正在解析动图帧…" else "…")
                    }
                }
                fingerprints = result.items
                failed = result.failed
                mutableState.value = state.value.copy(scanned = true,
                    durations = fingerprints.filter { it.resource.isAnimated }.associate { it.resource.id to it.durationMs })
                match()
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                mutableState.value = state.value.copy(message = "扫描失败，请重试。", scanned = false)
            } finally {
                mutableState.value = state.value.copy(busy = false)
            }
        }
    }

    fun setThreshold(value: Int) {
        val threshold = value.coerceIn(0, 16)
        if (state.value.busy || state.value.deleting || threshold == state.value.threshold) return
        preferences.edit().putInt("threshold", threshold).apply()
        mutableState.value = state.value.copy(threshold = threshold, selected = emptySet())
        if (state.value.scanned) rematch()
    }

    private fun rematch() {
        mutableState.value = state.value.copy(busy = true, groups = emptyList(), selected = emptySet(), message = "正在重新匹配…")
        job = viewModelScope.launch {
            try { match()
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                mutableState.value = state.value.copy(message = "匹配失败，请重新扫描。")
            } finally { mutableState.value = state.value.copy(busy = false) }
        }
    }

    private suspend fun match() {
        mutableState.value = state.value.copy(message = "正在匹配疑似相似图片…")
        val threshold = state.value.threshold
        val groups = withContext(Dispatchers.Default) {
            val coroutine = currentCoroutineContext()
            val still = fingerprints.filter { !it.resource.isAnimated }
            val animated = fingerprints.filter { it.resource.isAnimated }
            val stillGroups = SimilarityIndex.group(still.map { it.hash }, threshold) { coroutine.ensureActive() }
                .map { group -> group.map { still[it].resource } }
            val animatedGroups = AnimationSimilarity.group(animated.map { it.frameHashes }, threshold) { coroutine.ensureActive() }
                .map { group -> group.map { animated[it].resource } }
            (stillGroups + animatedGroups).sortedBy { it.first().id }
        }
        val count = groups.sumOf { it.size }
        val summary = if (groups.isEmpty()) "当前范围内未找到疑似相似图片" else "找到 ${groups.size} 组疑似相似图片，共 $count 张"
        mutableState.value = state.value.copy(groups = groups, message =
            "$summary\n静态图 ${fingerprints.count { !it.resource.isAnimated }} 张 · 动图 ${fingerprints.count { it.resource.isAnimated }} 张 · 范围外 $skipped 张 · 失败 $failed 张")
    }

    fun cancel() {
        job?.cancel()
        mutableState.value = state.value.copy(groups = emptyList(), selected = emptySet(),
            message = "已取消，已计算的特征会供下次扫描复用。")
    }

    fun toggle(id: Long) {
        if (state.value.busy || state.value.deleting) return
        val selected = state.value.selected.toMutableSet()
        if (!selected.add(id)) selected.remove(id)
        mutableState.value = state.value.copy(selected = selected)
    }

    /** Reconcile after preview/deletion without automatically scanning newly imported resources. */
    fun refreshExisting() {
        if (state.value.busy || state.value.deleting || !state.value.scanned) return
        mutableState.value = state.value.copy(busy = true)
        job = viewModelScope.launch {
            try {
                val current = database.resourceDao().getAllResourcesOrderedList().associateBy { it.id }
                val refreshed = fingerprints.mapNotNull { item ->
                    current[item.resource.id]?.takeIf {
                        it.syncKey == item.resource.syncKey && it.fileMd5 == item.resource.fileMd5 &&
                            it.isAnimated == item.resource.isAnimated && state.value.media.accepts(it)
                    }?.let { item.copy(resource = it) }
                }
                if (refreshed != fingerprints) {
                    fingerprints = refreshed
                    mutableState.value = state.value.copy(selected = emptySet())
                    match()
                }
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                fingerprints = emptyList()
                mutableState.value = state.value.copy(groups = emptyList(), selected = emptySet(), scanned = false, message = "资源刷新失败，请重新扫描。")
            } finally { mutableState.value = state.value.copy(busy = false) }
        }
    }

    fun beginDeletion(): Boolean {
        if (state.value.busy || state.value.deleting) return false
        mutableState.value = state.value.copy(deleting = true)
        return true
    }

    fun finishDeletion() {
        mutableState.value = state.value.copy(deleting = false, selected = emptySet())
        refreshExisting()
    }
}
