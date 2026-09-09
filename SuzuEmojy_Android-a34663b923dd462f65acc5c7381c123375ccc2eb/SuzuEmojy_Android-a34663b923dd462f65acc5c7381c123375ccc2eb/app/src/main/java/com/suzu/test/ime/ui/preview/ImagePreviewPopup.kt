package com.suzu.test.ime.ui.preview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.ime.ImageItem
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.log.TestLog

class ImagePreviewPopup(private val context: Context) {

    companion object {
        private const val TAG = "ImagePreviewPopup"
        private const val AUTO_DISMISS_DELAY_MS = 4000L
    }

    private var popupWindow: PopupWindow? = null
    private var previewImageView: ImageView? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private val autoDismissRunnable = Runnable { dismiss() }

    fun show(anchorView: View, item: ImageItem) {
        dismiss()

        try {
            val inflater = LayoutInflater.from(context)
            val popupView = inflater.inflate(R.layout.popup_ime_image_preview, null)
            val ivPreview = popupView.findViewById<ImageView>(R.id.ivPreviewImage)
            previewImageView = ivPreview

            val sizePx = (220 * context.resources.displayMetrics.density).toInt()

            val theme = KeyboardTheme.current(context)
            val popupBgDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(theme.popupBg)
                cornerRadius = 12 * context.resources.displayMetrics.density
                setStroke((1 * context.resources.displayMetrics.density).toInt(), theme.popupStroke)
            }
            popupView.background = popupBgDrawable

            val popup = PopupWindow(
                popupView,
                sizePx,
                sizePx,
                false
            ).apply {
                isOutsideTouchable = true
                isClippingEnabled = false
                elevation = 16f
            }

            popupView.setOnClickListener { dismiss() }

            loadImage(ivPreview, item)

            val anchorLocation = IntArray(2)
            anchorView.getLocationInWindow(anchorLocation)
            val anchorX = anchorLocation[0]
            val anchorY = anchorLocation[1]

            var popupX = anchorX + (anchorView.width - sizePx) / 2
            val screenWidth = context.resources.displayMetrics.widthPixels
            if (popupX < 16) popupX = 16
            if (popupX + sizePx > screenWidth - 16) {
                popupX = screenWidth - 16 - sizePx
            }

            val popupY = anchorY - sizePx - (8 * context.resources.displayMetrics.density).toInt()

            popupWindow = popup
            popup.showAtLocation(anchorView, Gravity.NO_GRAVITY, popupX, popupY)

            mainHandler.postDelayed(autoDismissRunnable, AUTO_DISMISS_DELAY_MS)
            TestLog.i(TAG, "预览已显示: item=${item.displayName}, x=$popupX, y=$popupY")
        } catch (e: Exception) {
            TestLog.e(TAG, "显示预览异常: ${e.message}", e)
            dismiss()
        }
    }

    private fun loadImage(imageView: ImageView, item: ImageItem) {
        when (item) {
            is ImageItem.SuzuResource -> {
                val isGif = item.format.equals("gif", ignoreCase = true)
                if (isGif) {
                    Glide.with(context)
                        .asDrawable()
                        .load(item.file)
                        .fitCenter()
                        .into(imageView)
                } else {
                    Glide.with(context)
                        .asBitmap()
                        .load(item.file)
                        .fitCenter()
                        .into(imageView)
                }
            }
            is ImageItem.MediaStoreImage -> {
                val isGif = item.mimeType.contains("gif", ignoreCase = true)
                if (isGif) {
                    Glide.with(context)
                        .asDrawable()
                        .load(item.uri)
                        .fitCenter()
                        .into(imageView)
                } else {
                    Glide.with(context)
                        .asBitmap()
                        .load(item.uri)
                        .fitCenter()
                        .into(imageView)
                }
            }
            is ImageItem.AssetSample -> {
                Glide.with(context)
                    .load("file:///android_asset/${item.assetFileName}")
                    .fitCenter()
                    .into(imageView)
            }
        }
    }

    fun dismiss() {
        try {
            mainHandler.removeCallbacksAndMessages(null)
            previewImageView?.let { iv ->
                try {
                    Glide.with(context).clear(iv)
                } catch (e: Exception) {
                    TestLog.w(TAG, "Glide.clear 异常: ${e.message}")
                }
            }
            previewImageView = null

            popupWindow?.let { pop ->
                if (pop.isShowing) {
                    pop.dismiss()
                }
            }
            popupWindow = null
        } catch (e: Exception) {
            TestLog.e(TAG, "dismiss 异常: ${e.message}", e)
        }
    }
}
