package com.suzu.test.floating

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.suzu.test.R
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.databinding.LayoutFloatingBallBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.abs

class FloatingBallController(private val context: Context) {

    companion object {
        private const val MODULE = "FloatingBallController"
        private const val MAX_HIT_BITMAP_EDGE = 256
        private const val ALPHA_THRESHOLD = 24
    }

    private var windowManager: WindowManager? = null
    private var lastForegroundPackage: String? = null
    private var floatingView: View? = null
    private var layoutParams: WindowManager.LayoutParams? = null
    private var spListener: SharedPreferences.OnSharedPreferenceChangeListener? = null
    private var isBallVisible: Boolean = true
    private var ballBinding: LayoutFloatingBallBinding? = null
    private var controllerScope: CoroutineScope? = null

    private val mainHandler = Handler(Looper.getMainLooper())
    private var imeVisible: Boolean = true
    private var imeSwitchGuardUntil: Long = 0L
    private var isAttached: Boolean = false

    // 命中检测位图（仅无边框模式且有贴图时启用，GIF取首帧，内存控制在数百KB内）
    // 说明：由于跨窗口点击穿透受 Android 窗口分发机制限制，此位图用于「防止透明区域误触发悬浮球」
    @Volatile
    private var hitTestBitmap: Bitmap? = null

    private val hideRunnable = Runnable {
        imeVisible = false
        evaluateVisibility()
    }

    fun attach() {
        if (isAttached) return
        if (!Settings.canDrawOverlays(context)) {
            TestLog.w(MODULE, "无悬浮窗权限，无法挂载悬浮球")
            return
        }

        TestLog.i(MODULE, "attach: 开始挂载悬浮球")
        controllerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        isAttached = true
        initFloatingBall()
        observeConfigChanges()

        lastForegroundPackage = TestAccessibilityService.instance?.foregroundAppPackage
        imeVisible = TestAccessibilityService.instance?.isImeVisibleNow() ?: true
        cleanLegacyFilterConfigOnce()
        applyBallImage()
        observeImeVisibilityBus()
        evaluateVisibility()
    }

    private fun observeImeVisibilityBus() {
        controllerScope?.launch {
            ImeVisibilityBus.isImeVisible.collect { isVisible ->
                onImeVisibilityChanged(isVisible)
            }
        }
    }

    fun detach() {
        if (!isAttached) return
        TestLog.i(MODULE, "detach: 移除悬浮球并清理资源")
        isAttached = false
        controllerScope?.cancel()
        controllerScope = null
        mainHandler.removeCallbacksAndMessages(null)

        releaseHitTestBitmap()

        spListener?.let {
            val sp = context.getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
            sp.unregisterOnSharedPreferenceChangeListener(it)
        }
        spListener = null

        floatingView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (e: Exception) {
                TestLog.w(MODULE, "removeView 异常: ${e.message}")
            }
        }
        floatingView = null
        ballBinding = null
        layoutParams = null
        windowManager = null
    }

    private fun releaseHitTestBitmap() {
        hitTestBitmap?.recycle()
        hitTestBitmap = null
    }

    private fun cleanLegacyFilterConfigOnce() {
        val sp = context.getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
        if (!sp.getBoolean("sp_cleanup_v2", false)) {
            sp.edit()
                .remove("floating_ball_app_filter_enabled")
                .remove("floating_ball_allowed_packages")
                .putBoolean("sp_cleanup_v2", true)
                .apply()
        }
    }

    private fun observeConfigChanges() {
        val sp = context.getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
        spListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                FloatingBallConfig.KEY_BALL_SIZE_DP, FloatingBallConfig.KEY_BALL_ALPHA -> {
                    applyConfigToView()
                }
                FloatingBallConfig.KEY_BALL_SHAPE -> {
                    // 形状变更触发完整样式与贴图刷新
                    applyBallImage()
                }
                FloatingBallConfig.KEY_IMAGE_RESOURCE_ID -> {
                    applyBallImage()
                }
                FloatingBallConfig.KEY_SHOW_ONLY_WITH_IME -> {
                    evaluateVisibility()
                }
            }
        }
        sp.registerOnSharedPreferenceChangeListener(spListener)
    }

    fun onForegroundAppChanged(packageName: String?) {
        mainHandler.post {
            lastForegroundPackage = packageName
            evaluateVisibility()
        }
    }

    fun onImeVisibilityChanged(visible: Boolean) {
        mainHandler.post {
            if (visible) {
                mainHandler.removeCallbacks(hideRunnable)
                imeVisible = true
                evaluateVisibility()
            } else {
                mainHandler.removeCallbacks(hideRunnable)
                imeVisible = false
                evaluateVisibility()
            }
        }
    }

    fun onScreenConfigurationChanged() {
        val fView = floatingView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return
        clampPosition(params)
        FloatingBallConfig.saveBallPosition(context, params.x, params.y)
        try {
            wm.updateViewLayout(fView, params)
            TestLog.i(MODULE, "屏幕旋转后已重新限制悬浮球坐标: (${params.x}, ${params.y})")
        } catch (e: Exception) {
            TestLog.e(MODULE, "onScreenConfigurationChanged updateViewLayout 异常: ${e.message}", e)
        }
    }

    private fun getRealScreenSize(): Point {
        val wm = windowManager ?: context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val bounds = windowMetrics.bounds
            point.x = bounds.width()
            point.y = bounds.height()
        } else {
            @Suppress("DEPRECATION")
            wm.defaultDisplay.getRealSize(point)
        }
        return point
    }

    private fun clampPosition(params: WindowManager.LayoutParams) {
        val density = context.resources.displayMetrics.density
        val sizeDp = FloatingBallConfig.getSizeDp(context)
        val sizePx = (sizeDp * density).toInt()

        val screenSize = getRealScreenSize()
        val maxX = (screenSize.x - sizePx).coerceAtLeast(0)
        val maxY = (screenSize.y - sizePx).coerceAtLeast(0)

        params.x = params.x.coerceIn(0, maxX)
        params.y = params.y.coerceIn(0, maxY)
    }

    private fun applyVisibility(shouldShow: Boolean) {
        if (!shouldShow && System.currentTimeMillis() < imeSwitchGuardUntil) {
            TestLog.i(MODULE, "IME 切换保护锁生效中，忽略隐藏操作")
            return
        }

        if (shouldShow == isBallVisible) return
        isBallVisible = shouldShow

        val fView = floatingView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return

        try {
            val targetAlpha = if (shouldShow) {
                FloatingBallConfig.getAlphaPercent(context) / 100f
            } else {
                0f
            }

            val finalizeState: (Float) -> Unit = { alpha ->
                fView.alpha = alpha
                if (shouldShow) {
                    params.flags = params.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE.inv()
                } else {
                    params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                }
                try {
                    wm.updateViewLayout(fView, params)
                } catch (e: Exception) {
                    TestLog.e(MODULE, "updateViewLayout 异常: ${e.message}", e)
                }
            }

            val animDuration = FloatingBallConfig.getAnimDurationMs(context)
            if (animDuration <= 0) {
                fView.animate().cancel()
                finalizeState(targetAlpha)
            } else {
                fView.animate().cancel()
                fView.animate()
                    .alpha(targetAlpha)
                    .setDuration(animDuration.toLong())
                    .withEndAction {
                        finalizeState(targetAlpha)
                    }
                    .start()

                if (shouldShow) {
                    params.flags = params.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE.inv()
                } else {
                    params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                }
                try {
                    wm.updateViewLayout(fView, params)
                } catch (e: Exception) {
                    TestLog.e(MODULE, "updateViewLayout 异常: ${e.message}", e)
                }
            }

            TestLog.i(MODULE, "悬浮球可见性跃迁: ${if (shouldShow) "显示" else "隐藏"} (pkg=$lastForegroundPackage, imeVisible=$imeVisible, alpha=$targetAlpha, duration=${animDuration}ms)")
        } catch (e: Exception) {
            TestLog.e(MODULE, "applyVisibility 异常: ${e.message}", e)
        }
    }

    private fun evaluateVisibility() {
        val shouldShow = determineShouldShow()
        applyVisibility(shouldShow)
    }

    private fun determineShouldShow(): Boolean {
        if (lastForegroundPackage == context.packageName) return true
        if (!FloatingBallConfig.isShowOnlyWithImeEnabled(context)) return true
        val accOk = TestAccessibilityService.instance?.imeDetectionAvailable ?: false
        return if (!accOk) true else imeVisible
    }

    private fun applyConfigToView() {
        val fView = floatingView ?: return
        val params = layoutParams ?: return
        val density = context.resources.displayMetrics.density

        val sizeDp = FloatingBallConfig.getSizeDp(context)
        val targetSizePx = (sizeDp * density).toInt()

        val alphaPct = FloatingBallConfig.getAlphaPercent(context)
        if (isBallVisible) {
            fView.alpha = alphaPct / 100f
        }

        if (params.width != targetSizePx || params.height != targetSizePx) {
            params.width = targetSizePx
            params.height = targetSizePx
            clampPosition(params)
            try {
                windowManager?.updateViewLayout(fView, params)
                TestLog.i(MODULE, "已更新悬浮球尺寸与透明度: size=${sizeDp}dp, alpha=$alphaPct%")
            } catch (e: Exception) {
                TestLog.e(MODULE, "updateViewLayout 异常: ${e.message}", e)
            }
        }
    }

    /**
     * 根据形状配置与资源 ID 应用悬浮球样式与贴图
     */
    private fun applyBallImage() {
        val binding = ballBinding ?: return
        val resourceId = FloatingBallConfig.getImageResourceId(context)
        val ballShape = FloatingBallConfig.getBallShape(context)
        val density = context.resources.displayMetrics.density

        // 1. 配置 ShapeableImageView 的 ShapeAppearance 与 ScaleType
        when (ballShape) {
            FloatingBallConfig.SHAPE_ROUNDED_RECT -> {
                val radiusPx = 12f * density
                binding.ivSkinIcon.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCornerSizes(radiusPx)
                    .build()
                binding.ivSkinIcon.scaleType = ImageView.ScaleType.CENTER_CROP
            }
            FloatingBallConfig.SHAPE_BORDERLESS -> {
                binding.ivSkinIcon.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCornerSizes(0f)
                    .build()
                binding.ivSkinIcon.scaleType = ImageView.ScaleType.FIT_CENTER
            }
            else -> { // SHAPE_CIRCLE
                binding.ivSkinIcon.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCornerSizes(RelativeCornerSize(0.5f))
                    .build()
                binding.ivSkinIcon.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

        // 2. 无贴图时的处理
        if (resourceId == null) {
            releaseHitTestBitmap()
            binding.ivSkinIcon.visibility = View.GONE
            when (ballShape) {
                FloatingBallConfig.SHAPE_ROUNDED_RECT -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball_rounded)
                FloatingBallConfig.SHAPE_BORDERLESS -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball_square)
                else -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball)
            }
            return
        }

        // 3. 有贴图时的异步加载
        controllerScope?.launch {
            val resource = withContext(Dispatchers.IO) {
                try {
                    val db = DatabaseProvider.getDatabase(context)
                    db.resourceDao().getById(resourceId)
                } catch (e: Exception) {
                    null
                }
            }

            val file = if (resource != null) File(context.filesDir, "resources/${resource.filename}") else null
            if (file != null && file.exists()) {
                // 设置根布局背景
                when (ballShape) {
                    FloatingBallConfig.SHAPE_BORDERLESS -> binding.floatingRoot.background = null
                    FloatingBallConfig.SHAPE_ROUNDED_RECT -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball_rounded)
                    else -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball)
                }

                binding.ivSkinIcon.visibility = View.VISIBLE

                // Glide 统一只负责加载，由 ShapeableImageView 接管裁切
                Glide.with(context)
                    .load(file)
                    .into(binding.ivSkinIcon)

                // 无边框模式下异步解码低分辨率位图供防误触判定
                if (ballShape == FloatingBallConfig.SHAPE_BORDERLESS) {
                    val decodedBmp = withContext(Dispatchers.IO) {
                        decodeSampledHitBitmap(file.absolutePath)
                    }
                    releaseHitTestBitmap()
                    hitTestBitmap = decodedBmp
                    TestLog.i(MODULE, "已就绪无边框防误触位图: size=${decodedBmp?.width}x${decodedBmp?.height}")
                } else {
                    releaseHitTestBitmap()
                }

                TestLog.i(MODULE, "已加载悬浮球贴图: ID=$resourceId, shape=$ballShape, file=${file.name}")
            } else {
                TestLog.w(MODULE, "贴图资源不存在或已被删除，自动回退默认纯色球: ID=$resourceId")
                FloatingBallConfig.setImageResourceId(context, null)
                releaseHitTestBitmap()
                binding.ivSkinIcon.visibility = View.GONE
                when (ballShape) {
                    FloatingBallConfig.SHAPE_ROUNDED_RECT -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball_rounded)
                    FloatingBallConfig.SHAPE_BORDERLESS -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball_square)
                    else -> binding.floatingRoot.setBackgroundResource(R.drawable.bg_floating_ball)
                }
            }
        }
    }

    /**
     * 降采样解码用于防误触判定的 ARGB_8888 首帧位图 (最大边 ≤ 256px，常驻内存仅数十KB)
     */
    private fun decodeSampledHitBitmap(filePath: String): Bitmap? {
        return try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeFile(filePath, options)
            val maxEdge = maxOf(options.outWidth, options.outHeight)
            var inSampleSize = 1
            if (maxEdge > MAX_HIT_BITMAP_EDGE) {
                while ((maxEdge / (inSampleSize * 2)) >= MAX_HIT_BITMAP_EDGE) {
                    inSampleSize *= 2
                }
            }
            val decodeOptions = BitmapFactory.Options().apply {
                this.inSampleSize = inSampleSize
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            BitmapFactory.decodeFile(filePath, decodeOptions)
        } catch (e: Exception) {
            TestLog.w(MODULE, "decodeSampledHitBitmap 异常: ${e.message}")
            null
        }
    }

    private fun initFloatingBall() {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val binding = LayoutFloatingBallBinding.inflate(LayoutInflater.from(context))
        ballBinding = binding
        floatingView = binding.root

        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL

        val density = context.resources.displayMetrics.density
        val sizeDp = FloatingBallConfig.getSizeDp(context)
        val sizePx = (sizeDp * density).toInt()

        val (savedX, savedY) = FloatingBallConfig.getBallPosition(context)
        val params = WindowManager.LayoutParams(
            sizePx,
            sizePx,
            layoutType,
            flags,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = savedX
            y = savedY
        }
        layoutParams = params

        val alphaPct = FloatingBallConfig.getAlphaPercent(context)
        binding.root.alpha = alphaPct / 100f

        setupTouchListener(binding.root)

        try {
            clampPosition(params)
            if (params.x != savedX || params.y != savedY) {
                FloatingBallConfig.saveBallPosition(context, params.x, params.y)
            }
            windowManager?.addView(floatingView, params)
            isBallVisible = true
            TestLog.i(MODULE, "悬浮球常驻挂载到 WindowManager 成功 (x=${params.x}, y=${params.y})")
        } catch (e: Exception) {
            TestLog.e(MODULE, "挂载悬浮球到 WindowManager 失败: ${e.message}", e)
        }
    }

    /**
     * 判断当前触摸点在 ImageView 内部是否落在有效不透明区域
     */
    private fun isHitOpaqueRegion(touchX: Float, touchY: Float): Boolean {
        val binding = ballBinding ?: return true
        val bmp = hitTestBitmap ?: return true // 无贴图或解码未就绪，安全守卫返回 true
        val drawable = binding.ivSkinIcon.drawable ?: return true

        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        if (drawableWidth <= 0 || drawableHeight <= 0) return true

        // 利用 ImageView 的逆矩阵将 View 坐标映射为 Drawable 原始坐标
        val inverseMatrix = Matrix()
        if (!binding.ivSkinIcon.imageMatrix.invert(inverseMatrix)) {
            return true
        }

        val pts = floatArrayOf(touchX, touchY)
        inverseMatrix.mapPoints(pts)
        val rawX = pts[0]
        val rawY = pts[1]

        // 检查是否在 Drawable 图像有效边界内
        if (rawX < 0 || rawX >= drawableWidth || rawY < 0 || rawY >= drawableHeight) {
            return false
        }

        // 映射到降采样判定位图的像素坐标
        val bmpX = ((rawX / drawableWidth) * bmp.width).toInt().coerceIn(0, bmp.width - 1)
        val bmpY = ((rawY / drawableHeight) * bmp.height).toInt().coerceIn(0, bmp.height - 1)

        val pixel = bmp.getPixel(bmpX, bmpY)
        val alpha = Color.alpha(pixel)

        // alpha < 24 判定为透明死区，返回 false 拦截触发
        return alpha >= ALPHA_THRESHOLD
    }

    private fun setupTouchListener(view: View) {
        val clickThreshold = 10 * context.resources.displayMetrics.density
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var isClick = true
        var isDownConsumed = false

        view.setOnTouchListener { _, event ->
            val params = layoutParams ?: return@setOnTouchListener false
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val ballShape = FloatingBallConfig.getBallShape(context)
                    if (ballShape == FloatingBallConfig.SHAPE_BORDERLESS && FloatingBallConfig.getImageResourceId(context) != null) {
                        if (!isHitOpaqueRegion(event.x, event.y)) {
                            // 透明区域防误触拦截：ACTION_DOWN 直接返回 false，不武装拖拽与点击状态机
                            isDownConsumed = false
                            return@setOnTouchListener false
                        }
                    }

                    isDownConsumed = true
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isClick = true
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!isDownConsumed) return@setOnTouchListener false
                    val dx = event.rawX - initialTouchX
                    val dy = event.rawY - initialTouchY
                    if (abs(dx) > clickThreshold || abs(dy) > clickThreshold) {
                        isClick = false
                    }
                    params.x = initialX + dx.toInt()
                    params.y = initialY + dy.toInt()
                    clampPosition(params)
                    windowManager?.updateViewLayout(floatingView, params)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (!isDownConsumed) return@setOnTouchListener false
                    isDownConsumed = false
                    if (isClick) {
                        onFloatingBallClicked()
                    } else {
                        FloatingBallConfig.saveBallPosition(context, params.x, params.y)
                        TestLog.i(MODULE, "拖拽结束已持久化坐标: (${params.x}, ${params.y})")
                    }
                    true
                }
                MotionEvent.ACTION_CANCEL -> {
                    isDownConsumed = false
                    false
                }
                else -> false
            }
        }

        view.setOnLongClickListener {
            if (!isDownConsumed) return@setOnLongClickListener false
            TestLog.i(MODULE, "长按悬浮球，隐藏悬浮球并关闭配置开关")
            FloatingBallConfig.setBallEnabled(context, false)
            true
        }
    }

    /**
     * 单击悬浮球 (纯静默切换):
     */
    private fun onFloatingBallClicked() {
        TestLog.i(MODULE, "========== 悬浮球被单击 (静默切换) ==========")

        val accessibility = TestAccessibilityService.instance
        if (accessibility == null || !TestAccessibilityService.isAlive()) {
            TestLog.w(MODULE, "无障碍辅助服务未连接，打开设置")
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            return
        }

        imeSwitchGuardUntil = System.currentTimeMillis() + 2000L

        val currentIme = Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        val testImeId = accessibility.findTestImeId()

        TestLog.i(MODULE, "当前默认 IME = $currentIme, 目标 IME = $testImeId")

        if (testImeId != null && currentIme == testImeId) {
            TestLog.i(MODULE, "当前已处于 SuzuEmojy，静默执行 restorePreviousIme()...")
            accessibility.restorePreviousIme()
        } else {
            TestLog.i(MODULE, "当前非 SuzuEmojy，静默执行 switchToTestIme()...")
            accessibility.switchToTestIme()
        }
    }
}
