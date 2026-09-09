package com.suzu.test.provider

import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.os.ParcelFileDescriptor
import android.os.Process
import android.os.SystemClock
import androidx.core.content.FileProvider
import com.suzu.test.BuildConfig
import com.suzu.test.ime.diag.DebugSendTestConfig
import com.suzu.test.ime.diag.ImageSendDiagnostics
import com.suzu.test.log.TestLog
import java.io.File

class DiagnosticFileProvider : FileProvider() {

    companion object {
        private const val MODULE = "DiagnosticFileProvider"
    }

    private fun resolveFile(uri: Uri): File? {
        val record = ImageSendDiagnostics.recordForUri(uri)
        return record?.filePath?.let(::File)
    }

    private fun stack(e: Exception): String =
        android.util.Log.getStackTraceString(e)

    private fun logAccess(methodName: String, uri: Uri?, mode: String?, success: String, error: Exception? = null) {
        val callingUid = Binder.getCallingUid()
        val packages = try {
            context?.packageManager?.getPackagesForUid(callingUid)?.joinToString() ?: "null/unknown"
        } catch (e: Exception) {
            "error:${e.message}"
        }
        val record = uri?.let { ImageSendDiagnostics.recordForUri(it) }
        val file = resolveFile(uri ?: Uri.EMPTY)
        val extra = buildString {
            append("timestamp=${System.currentTimeMillis()}")
            append(", uri=$uri, mode=${mode ?: "N/A"}")
            append(", callingUid=$callingUid, packages=[$packages]")
            append(", processPid=${Process.myPid()}, processUid=${Process.myUid()}")
            append(", eventId=${record?.eventId ?: "UNKNOWN"}")
            append(", filePath=${file?.absolutePath ?: record?.filePath ?: "UNKNOWN"}")
            append(", fileExists=${file?.exists() ?: false}, fileLength=${file?.length() ?: -1}")
            append(", result=$success")
            if (error != null) {
                append(", exceptionType=${error::class.java.name}")
                append(", exceptionMessage=${error.message}")
                append(", stack=${stack(error)}")
            }
        }
        TestLog.i(MODULE, "$methodName | $extra")
        if (
            record != null &&
            methodName.startsWith("openFile") &&
            callingUid != Process.myUid() &&
            !(BuildConfig.DEBUG &&
                DebugSendTestConfig.isSkipProviderDiagnosticAndCleanupEnabled(requireNotNull(context)))
        ) {
            record.providerOpen = when {
                success == "SUCCESS" -> "成功"
                success == "EXCEPTION" -> "异常"
                else -> record.providerOpen
            }
            ImageSendDiagnostics.update(record)
        }
    }

    override fun getType(uri: Uri): String? {
        return try {
            val result = super.getType(uri)
            logAccess("getType", uri, null, "SUCCESS")
            result
        } catch (e: Exception) {
            logAccess("getType", uri, null, "EXCEPTION", e)
            throw e
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        return try {
            val result = super.query(uri, projection, selection, selectionArgs, sortOrder)
            logAccess("query", uri, null, "SUCCESS")
            result
        } catch (e: Exception) {
            logAccess("query", uri, null, "EXCEPTION", e)
            throw e
        }
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        return try {
            val result = super.openFile(uri, mode)
            logAccess("openFile", uri, mode, "SUCCESS")
            result
        } catch (e: Exception) {
            logAccess("openFile", uri, mode, "EXCEPTION", e)
            throw e
        }
    }

    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor? {
        return try {
            val result = super.openAssetFile(uri, mode)
            logAccess("openAssetFile", uri, mode, "SUCCESS")
            result
        } catch (e: Exception) {
            logAccess("openAssetFile", uri, mode, "EXCEPTION", e)
            throw e
        }
    }
}
