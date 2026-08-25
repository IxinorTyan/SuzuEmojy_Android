package com.suzu.test.ime.diag

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.StringBuilderPrinter
import android.view.inputmethod.EditorInfo
import androidx.core.view.inputmethod.EditorInfoCompat
import com.suzu.test.BuildConfig
import com.suzu.test.log.TestLog

object EditorInfoDumper {

    private const val MODULE = "SuzuEmojy"

    fun dump(context: Context, info: EditorInfo?) {
        if (info == null) {
            TestLog.i(MODULE, "EditorInfo 为 null")
            return
        }

        val rawMimeTypes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val raw = info.contentMimeTypes
            when {
                raw == null -> "null"
                raw.isEmpty() -> "[](size=0)"
                else -> "[${raw.joinToString()}] (size=${raw.size})"
            }
        } else {
            "API<25"
        }

        val compatMimes = EditorInfoCompat.getContentMimeTypes(info)
        val compatMimeStr = if (compatMimes.isEmpty()) "[](size=0)" else "[${compatMimes.joinToString()}] (size=${compatMimes.size})"

        val dumpSb = StringBuilder()
        info.dump(StringBuilderPrinter(dumpSb), "")

        val extrasSb = StringBuilder()
        var sogouExpVal = -999
        var supSogouExpVal = -999
        var sogouWebpVal = -999
        val allExtraKeys = mutableListOf<String>()

        info.extras?.let { bundle ->
            for (key in bundle.keySet()) {
                allExtraKeys.add(key)
                val value = bundle.get(key)
                val valType = value?.javaClass?.simpleName ?: "null"
                extrasSb.append("\n    * $key ($valType) = $value")

                if (key == "SOGOU_EXPRESSION") {
                    sogouExpVal = try { bundle.getInt(key, -999) } catch (e: Exception) { -999 }
                }
                if (key == "SUPPORT_SOGOU_EXPRESSION") {
                    supSogouExpVal = try { bundle.getInt(key, -999) } catch (e: Exception) { -999 }
                }
                if (key == "SOGOU_EXPRESSION_WEBP") {
                    sogouWebpVal = try { bundle.getInt(key, -999) } catch (e: Exception) { -999 }
                }
            }
        }

        val targetPkg = info.packageName ?: "未知包名"
        val currentDefaultIme = Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD) ?: "null"
        val fullDumpLog = """
            [EditorInfo 全量 Dump 取证]
            - 当前 AppId: ${BuildConfig.APPLICATION_ID}
            - 当前 FileProvider Authority: ${BuildConfig.FILE_PROVIDER_AUTHORITY}
            - 当前系统 DEFAULT_INPUT_METHOD: $currentDefaultIme
            - 目标应用包名: $targetPkg
            - 原生 info.contentMimeTypes: $rawMimeTypes
            - EditorInfoCompat.getContentMimeTypes: $compatMimeStr
            - ★ SOGOU_EXPRESSION: $sogouExpVal, SUPPORT_SOGOU_EXPRESSION: $supSogouExpVal, SOGOU_EXPRESSION_WEBP: $sogouWebpVal
            - extras 全量键名列表: [${allExtraKeys.joinToString()}]
        """.trimIndent()

        TestLog.i(MODULE, fullDumpLog)
    }
}
