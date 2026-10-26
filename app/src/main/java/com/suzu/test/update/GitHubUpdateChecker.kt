package com.suzu.test.update

import android.content.Context
import com.suzu.test.BuildConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class GitHubUpdateChecker(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    fun isAutomaticCheckEnabled(): Boolean = preferences.getBoolean(KEY_AUTOMATIC, true)

    fun setAutomaticCheckEnabled(enabled: Boolean) {
        preferences.edit().putBoolean(KEY_AUTOMATIC, enabled).apply()
    }

    suspend fun check(manual: Boolean): UpdateCheckResult = requestMutex.withLock {
        withContext(Dispatchers.IO) {
            val now = System.currentTimeMillis()
            if (!manual && (!isAutomaticCheckEnabled() || !UpdateCheckPolicy.shouldCheck(
                    now, preferences.getLong(KEY_ATTEMPT, 0L), preferences.getLong(KEY_SUCCESS, 0L)
                ))) return@withContext UpdateCheckResult.Skipped

            preferences.edit().putLong(KEY_ATTEMPT, now).apply()
            val result = requestLatestRelease()
            currentCoroutineContext().ensureActive()
            if (result is UpdateCheckResult.NewVersion || result == UpdateCheckResult.UpToDate ||
                result == UpdateCheckResult.NoRelease) {
                preferences.edit().putLong(KEY_SUCCESS, now).apply()
            }
            result
        }
    }

    private suspend fun requestLatestRelease(): UpdateCheckResult {
        val connection = URL(LATEST_RELEASE_API).openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 8_000
            connection.readTimeout = 8_000
            connection.instanceFollowRedirects = false
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("User-Agent", "SuzuEmojy-Android/${BuildConfig.VERSION_NAME}")
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28")
            when (connection.responseCode) {
                HttpURLConnection.HTTP_NOT_FOUND -> return UpdateCheckResult.NoRelease
                HttpURLConnection.HTTP_FORBIDDEN, 429 -> return UpdateCheckResult.Failed(FailureReason.RATE_LIMIT)
                HttpURLConnection.HTTP_OK -> Unit
                else -> return UpdateCheckResult.Failed(FailureReason.SERVER)
            }
            val bytes = connection.inputStream.use { input ->
                val output = java.io.ByteArrayOutputStream()
                val buffer = ByteArray(8192)
                while (true) {
                    currentCoroutineContext().ensureActive()
                    val count = input.read(buffer)
                    if (count < 0) break
                    if (output.size() + count > MAX_RESPONSE_BYTES) throw JSONException("Release response is too large")
                    output.write(buffer, 0, count)
                }
                output.toByteArray()
            }
            val json = JSONObject(String(bytes, Charsets.UTF_8))
            if (json.getBoolean("draft") || json.getBoolean("prerelease")) return UpdateCheckResult.NoRelease
            val tag = json.getString("tag_name").trim()
            val notes = json.optString("body", "").take(1200)
            return UpdateCheckPolicy.compare(BuildConfig.VERSION_NAME, GitHubRelease(tag, notes))
        } catch (e: CancellationException) {
            throw e
        } catch (_: IOException) {
            return UpdateCheckResult.Failed(FailureReason.NETWORK)
        } catch (_: JSONException) {
            return UpdateCheckResult.Failed(FailureReason.INVALID_RESPONSE)
        } finally {
            connection.disconnect()
        }
    }

    companion object {
        const val PROJECT_URL = "https://github.com/IxinorTyan/SuzuEmojy_Android"
        private const val LATEST_RELEASE_API = "https://api.github.com/repos/IxinorTyan/SuzuEmojy_Android/releases/latest"
        private const val PREFERENCES = "app_update"
        private const val KEY_AUTOMATIC = "automatic_enabled"
        private const val KEY_ATTEMPT = "last_attempt"
        private const val KEY_SUCCESS = "last_success"
        private const val MAX_RESPONSE_BYTES = 1024 * 1024
        private val requestMutex = Mutex()
    }
}
