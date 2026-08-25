package com.suzu.test.provider

import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.os.ParcelFileDescriptor
import android.os.Process
import android.os.SystemClock
import androidx.core.content.FileProvider
import com.suzu.test.log.TestLog

class DiagnosticFileProvider : FileProvider() {

    companion object {
        private const val MODULE = "DiagnosticFileProvider"
    }

    private fun logAccess(methodName: String, uri: Uri?, extraInfo: String = "") {
        val callingUid = Binder.getCallingUid()
        val isSelf = (callingUid == Process.myUid())
        val sourceTag = if (isSelf) "SELF" else "REMOTE"
        val pm = context?.packageManager
        val packages = try {
            pm?.getPackagesForUid(callingUid)?.joinToString() ?: "null/unknown"
        } catch (e: Exception) {
            "error:${e.message}"
        }

        val wallTime = System.currentTimeMillis()
        val elapsedRealtime = SystemClock.elapsedRealtime()

        TestLog.i(
            MODULE,
            "[$sourceTag] $methodName | uri=$uri | callingUid=$callingUid | packages=[$packages] | wallTime=$wallTime | elapsedRealtime=$elapsedRealtime ms | $extraInfo"
        )
    }

    override fun getType(uri: Uri): String? {
        val result = super.getType(uri)
        logAccess("getType", uri, "returnedType=$result")
        return result
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val projStr = projection?.joinToString() ?: "null"
        logAccess("query", uri, "projection=[$projStr], selection=$selection")
        return super.query(uri, projection, selection, selectionArgs, sortOrder)
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        logAccess("openFile", uri, "mode=$mode")
        try {
            val pfd = super.openFile(uri, mode)
            logAccess("openFile-SUCCESS", uri, "fd=${pfd?.fd}, statSize=${pfd?.statSize}")
            return pfd
        } catch (e: Exception) {
            logAccess("openFile-EXCEPTION", uri, "error=${e.message}")
            throw e
        }
    }

    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor? {
        logAccess("openAssetFile", uri, "mode=$mode")
        try {
            val afd = super.openAssetFile(uri, mode)
            logAccess("openAssetFile-SUCCESS", uri, "length=${afd?.length}")
            return afd
        } catch (e: Exception) {
            logAccess("openAssetFile-EXCEPTION", uri, "error=${e.message}")
            throw e
        }
    }
}
