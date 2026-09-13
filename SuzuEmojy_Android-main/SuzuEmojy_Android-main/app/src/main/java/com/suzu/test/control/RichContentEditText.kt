package com.suzu.test.control

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.core.view.inputmethod.InputConnectionCompat
import androidx.core.view.inputmethod.InputContentInfoCompat
import com.suzu.test.log.TestLog

class RichContentEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    companion object {
        private const val MODULE = "ControlRichEditText"
    }

    var onCommitContentListener: ((Uri, String) -> Boolean)? = null

    override fun onCreateInputConnection(editorInfo: EditorInfo): InputConnection? {
        val ic = super.onCreateInputConnection(editorInfo) ?: return null

        // 显式声明支持的图片 MIME 类型
        val supportedMimes = arrayOf("image/png", "image/gif", "image/jpeg", "image/webp")
        EditorInfoCompat.setContentMimeTypes(editorInfo, supportedMimes)
        TestLog.i(MODULE, "对照组 EditText 设置 contentMimeTypes: ${supportedMimes.joinToString()}")

        val callback = InputConnectionCompat.OnCommitContentListener { inputContentInfo: InputContentInfoCompat, flags: Int, _ ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1 &&
                    (flags and InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION != 0)
                ) {
                    inputContentInfo.requestPermission()
                }

                val uri = inputContentInfo.contentUri
                val mime = inputContentInfo.description.getMimeType(0) ?: "image/*"
                TestLog.i(MODULE, "对照组成功接收到 commitContent: uri=$uri, mime=$mime")

                val handled = onCommitContentListener?.invoke(uri, mime) ?: true
                if (handled) {
                    TestLog.i(MODULE, "对照组消费 commitContent 成功，返回 true")
                    true
                } else {
                    TestLog.e(MODULE, "对照组处理回调返回 false")
                    false
                }
            } catch (e: Exception) {
                TestLog.e(MODULE, "对照组处理 commitContent 异常: ${e.message}", e)
                false
            }
        }

        return InputConnectionCompat.createWrapper(ic, editorInfo, callback)
    }
}
