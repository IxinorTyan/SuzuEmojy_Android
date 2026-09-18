package com.suzu.test.floating

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
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
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.suzu.test.R
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.ime.TestImageIME
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
    private var edgeGestureController: EdgeGestureController? = null
    private var searchBarController: FloatingSearchBarController? = null
    private var springReturnAnimator: ValueAnimator? = null
    private var positionOrientation = context.resources.configuration.orientation
    private var finishTouchGesture: (() -> Unit)? = null

    private fun cancelSpringReturn() {
        // cancel() 也会触发 onAnimationEnd，先移除监听，避免旧动画覆盖新坐标。
        springReturnAnimator?.removeAllListeners()
        springReturnAnimator?.removeAllUpdateListeners()
        springReturnAnimator?.cancel()
        springReturnAnimator = null
    }

    private val mainHandler = Handler(Looper.getMainLooper())
    private var imeVisible: Boolean = true
    private var imeSwitchGuardUntil: Long = 0L
    private var isAttached: Boolean = false

    /**
     * 切换输入法期间可能会暂时忽略隐藏请求，但不能永久丢失该请求。
     * 保护锁到期后重新读取当前 imeVisible 并补做一次可见性评估。
     */
    private val imeGuardRecheck = object : Runnable {
        override fun run() {
            val remaining = imeSwitchGuardUntil - System.currentTimeMillis()
            if (remaining > 0L) {
                mainHandler.postDelayed(this, remaining)
            } else {
                evaluateVisibility()
            }
        }
    }

    // 命中检测位图（仅无边框模式且有贴图时启用，GIF取首帧，内存控制在数百KB内）
    // 说明：由于跨窗口点击穿透受 Android 窗口分发机制限制，此位图用于「防止透明区域误触发悬浮球」
    @Volatile
    private var hitTestBitmap: Bitmap? = null

    // 无延迟直接评估隐藏

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
        // 旧版行为：挂载时默认 IME 可见，交由后续真实信号修正，避免初始化期误隐藏
        imeVisible = true
        edgeGestureController = EdgeGestureController(context) { side, direction ->
            val action = FloatingBallConfig.getEdgeGestureAction(context, side, direction)
            when (action) {
                FloatingBallConfig.EdgeGestureAction.SWITCH_KEYBOARD -> {
                    onFloatingBallActionTriggered()
                }
                FloatingBallConfig.EdgeGestureAction.OPEN_SEARCH -> {
                    TestLog.i(MODULE, "边缘手势触发唤起搜索框: side=$side, direction=$direction")
                    imeSwitchGuardUntil = System.currentTimeMillis() + 1200L
                    searchBarController?.show()
                }
                FloatingBallConfig.EdgeGestureAction.TOGGLE_FAVORITES -> {
                    TestImageIME.instance?.toggleFavoritesIfShowing()
                }
                FloatingBallConfig.EdgeGestureAction.NONE -> {
                    TestLog.i(MODULE, "边缘手势配置为关闭: side=$side, direction=$direction")
                }
            }
        }.also {
            it.attach()
            it.onForegroundAppChanged(lastForegroundPackage)
            it.onImeVisibilityChanged(imeVisible)
        }
        searchBarController = FloatingSearchBarController(context).also {
            it.attach()
        }
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
        finishTouchGesture?.invoke()
        finishTouchGesture = null
        isAttached = false
        controllerScope?.cancel()
        controllerScope = null
        mainHandler.removeCallbacksAndMessages(null)
        cancelSpringReturn()
        edgeGestureController?.detach()
        edgeGestureController = null
        searchBarController?.detach()
        searchBarController = null

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

    fun showSearchBar() {
        searchBarController?.show()
    }

    private fun releaseHitTestBitmap() {
        hitTestBitmap?.recycle()
        hitTestBitmap = null
    }

    /**
     * 根据悬浮球当前可见状态控制 GIF 动画。
     *
     * GifDrawable.stop() 会停止帧调度并保留当前帧；重新 start() 时从暂停位置继续，
     * 从而避免悬浮球隐藏期间仍持续消耗动画解码与定时任务资源。
     */
    private fun updateGifPlayback() {
        val gifDrawable = ballBinding?.ivSkinIcon?.drawable as? GifDrawable ?: return
        if (isBallVisible) {
            if (!gifDrawable.isRunning) {
                gifDrawable.start()
            }
        } else {
            if (gifDrawable.isRunning) {
                gifDrawable.stop()
            }
        }
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
                FloatingBallConfig.KEY_SEARCH_BAR_TOP_MARGIN_DP -> {
                    searchBarController?.updatePosition()
                }
                FloatingBallConfig.KEY_BALL_SHAPE -> {
                    // 形状变更触发完整样式与贴图刷新
                    applyBallImage()
                }
                FloatingBallConfig.KEY_IMAGE_RESOURCE_ID -> {
                    applyBallImage()
                }
                FloatingBallConfig.KEY_FLOATING_MASTER_ENABLED,
                FloatingBallConfig.KEY_BALL_ENABLED,
                BallAppWhitelist.KEY_ENABLED,
                BallAppWhitelist.KEY_PACKAGES,
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
            edgeGestureController?.onForegroundAppChanged(packageName)
            evaluateVisibility()
        }
    }

    fun onVerifiedForegroundChanged() {
        // Accessibility callbacks and preference notifications run on the main thread.
        // Do not enqueue a stale allowed result behind a newer app switch.
        evaluateVisibility()
    }

    private fun isAppAllowed(): Boolean = BallAppAccessPolicy.allows(
        BallAppWhitelist.isEnabled(context),
        BallAppWhitelist.packages(context),
        TestAccessibilityService.instance?.verifiedBallForeground
    )

    fun onImeVisibilityChanged(visible: Boolean) {
        mainHandler.post {
            imeVisible = visible
            edgeGestureController?.onImeVisibilityChanged(visible)
            searchBarController?.onImeVisibilityChanged(visible)
            evaluateVisibility()
        }
    }

    fun onImeBoundsChanged(topPx: Int?) {
        mainHandler.post {
            edgeGestureController?.onImeBoundsChanged(topPx)
            searchBarController?.onImeBoundsChanged(topPx)
        }
    }

    fun onScreenConfigurationChanged() {
        val fView = floatingView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return
        // 手势仍属于旋转前的方向；必须先结束，再切换读取的存储槽。
        finishTouchGesture?.invoke()
        cancelSpringReturn()
        positionOrientation = context.resources.configuration.orientation
        val (savedX, savedY) = FloatingBallConfig.getBallPosition(context, positionOrientation)
        params.x = savedX
        params.y = savedY
        clampPosition(params)
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
        val appDenied = !isAppAllowed()
        if (appDenied) {
            mainHandler.removeCallbacks(imeGuardRecheck)
            if (!isBallVisible && floatingView?.visibility == View.GONE) return
            floatingView?.animate()?.cancel()
            cancelSpringReturn()
            finishTouchGesture?.invoke()
            isBallVisible = false
            updateGifPlayback()
            floatingView?.alpha = 0f
            floatingView?.visibility = View.GONE
            layoutParams?.let { params ->
                params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                floatingView?.let { view ->
                    try {
                        windowManager?.updateViewLayout(view, params)
                    } catch (e: Exception) {
                        TestLog.w(MODULE, "白名单隐藏窗口失败: ${e.message}")
                    }
                }
            }
            return
        }
        if (!shouldShow && System.currentTimeMillis() < imeSwitchGuardUntil) {
            // 不立即隐藏以避免输入法切换过程中的闪烁，但保护期结束后必须补做评估。
            mainHandler.removeCallbacks(imeGuardRecheck)
            mainHandler.postAtTime(imeGuardRecheck, imeSwitchGuardUntil)
            TestLog.i(MODULE, "IME 切换保护锁生效中，延迟到保护期结束后重新评估隐藏")
            return
        }

        if (shouldShow == isBallVisible) {
            updateGifPlayback()
            return
        }

        if (!shouldShow) finishTouchGesture?.invoke()
        isBallVisible = shouldShow
        updateGifPlayback()

        val fView = floatingView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return
        if (shouldShow) fView.visibility = View.VISIBLE

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
                // 当需要隐藏时，立即加上 FLAG_NOT_TOUCHABLE，避免透明淡出过程中仍拦截点击
                if (!shouldShow) {
                    params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    try {
                        wm.updateViewLayout(fView, params)
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "updateViewLayout 异常: ${e.message}", e)
                    }
                }
                fView.animate()
                    .alpha(targetAlpha)
                    .setDuration(animDuration.toLong())
                    .withEndAction {
                        finalizeState(targetAlpha)
                    }
                    .start()

                if (shouldShow) {
                    params.flags = params.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE.inv()
                    try {
                        wm.updateViewLayout(fView, params)
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "updateViewLayout 异常: ${e.message}", e)
                    }
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
        if (!isAppAllowed()) return false
        if (!FloatingBallConfig.isBallEnabled(context)) return false
        // 白名单准入和自家应用常显必须使用同一宿主，不能把自家 IME 的事件包名
        // 当成打开了自家应用。白名单关闭时保留原有前台判断。
        val foregroundPackage = if (BallAppWhitelist.isEnabled(context)) {
            TestAccessibilityService.instance?.verifiedBallForeground
        } else {
            lastForegroundPackage
        }
        if (foregroundPackage == context.packageName) return true
        if (!FloatingBallConfig.isShowOnlyWithImeEnabled(context)) return true
        // 检测不可用时 fail-open：宁可显示也不误隐藏（旧版精髓）
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
            finishTouchGesture?.invoke()
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

                // GIF 由 Glide 异步解码；加载完成后按当前可见状态同步播放状态。
                // 这样可以覆盖“悬浮球已隐藏，但 GIF 才刚加载完成”的竞态。
                binding.ivSkinIcon.post {
                    if (binding.ivSkinIcon.drawable is GifDrawable) {
                        updateGifPlayback()
                    }
                }

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
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

        val density = context.resources.displayMetrics.density
        val sizeDp = FloatingBallConfig.getSizeDp(context)
        val sizePx = (sizeDp * density).toInt()

        positionOrientation = context.resources.configuration.orientation
        val (savedX, savedY) = FloatingBallConfig.getBallPosition(context, positionOrientation)
        val params = WindowManager.LayoutParams(
            sizePx,
            sizePx,
            layoutType,
            flags,
            PixelFormat.TRANSLUCENT
        ).apply {
            // 与 rawX/rawY 统一使用屏幕左上角，避免 RTL、系统栏、IME 改变坐标原点。
            gravity = Gravity.TOP or Gravity.LEFT
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setFitInsetsTypes(0)
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
            x = savedX
            y = savedY
        }
        layoutParams = params

        val alphaPct = FloatingBallConfig.getAlphaPercent(context)
        binding.root.alpha = alphaPct / 100f

        setupTouchListener(binding.root)

        try {
            clampPosition(params)
            // 临时屏幕尺寸只影响显示，不覆盖用户保存的位置。
            // 初始状态下根据是否仅在键盘弹出时显示，决定初始 alpha 与 touchable flag
            val initialShow = determineShouldShow()
            isBallVisible = initialShow
            if (!initialShow) {
                binding.root.alpha = 0f
                params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            }
            windowManager?.addView(floatingView, params)
            TestLog.i(MODULE, "悬浮球常驻挂载到 WindowManager 成功 (x=${params.x}, y=${params.y}, show=$initialShow)")
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

    private fun animateSpringBack(params: WindowManager.LayoutParams, targetX: Int, targetY: Int) {
        cancelSpringReturn()
        val fView = floatingView ?: return
        val wm = windowManager ?: return

        val startX = params.x
        val startY = params.y
        if (startX == targetX && startY == targetY) return

        springReturnAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 150L
            interpolator = DecelerateInterpolator(2.0f)
            addUpdateListener { animator ->
                val fraction = animator.animatedValue as Float
                params.x = (startX + (targetX - startX) * fraction).toInt()
                params.y = (startY + (targetY - startY) * fraction).toInt()
                clampPosition(params)
                try {
                    wm.updateViewLayout(fView, params)
                } catch (e: Exception) {
                    TestLog.w(MODULE, "animateSpringBack updateViewLayout 异常: ${e.message}")
                }
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    params.x = targetX
                    params.y = targetY
                    clampPosition(params)
                    try {
                        wm.updateViewLayout(fView, params)
                    } catch (_: Exception) {}
                    springReturnAnimator = null
                }
            })
        }
        springReturnAnimator?.start()
    }

    private fun setupTouchListener(view: View) {
        val density = context.resources.displayMetrics.density
        val clickThreshold = 10 * density
        val swipeThreshold = 25 * density
        val maxDisplacementPx = 16f * density
        val travelLimitPx = 80f * density
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var lastTouchX = 0f
        var lastTouchY = 0f
        var isClick = true
        var isDownConsumed = false
        var isLongPressed = false
        var isSwiped = false
        var isSwipedDown = false
        var isSwipedUp = false

        val longPressRunnable = Runnable {
            if (isDownConsumed && !isSwiped && !isSwipedDown && !isSwipedUp && !isLongPressed) {
                isLongPressed = true
                isClick = false

                // 长按触发：视觉“浮起/激活”反馈
                view.animate().cancel()
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .alpha(1.0f)
                    .setDuration(160L)
                    .setInterpolator(OvershootInterpolator(2.0f))
                    .start()

                // 平滑衔接拖拽坐标，消除手指微移产生的突跳
                val p = layoutParams
                if (p != null) {
                    initialX = p.x
                    initialY = p.y
                    initialTouchX = lastTouchX
                    initialTouchY = lastTouchY
                }
                TestLog.i(MODULE, "悬浮球长按 300ms 触发，进入移动模式")
            }
        }

        finishTouchGesture = {
            mainHandler.removeCallbacks(longPressRunnable)
            val p = layoutParams
            if (p != null) {
                if (isDownConsumed && isLongPressed) {
                    FloatingBallConfig.saveBallPosition(context, p.x, p.y, positionOrientation)
                } else if (isDownConsumed || springReturnAnimator != null) {
                    p.x = initialX
                    p.y = initialY
                }
            }
            cancelSpringReturn()
            isDownConsumed = false
            isLongPressed = false
            isSwiped = false
            isSwipedDown = false
            isSwipedUp = false
            isClick = false
            view.animate().cancel()
            view.scaleX = 1f
            view.scaleY = 1f
            view.alpha = if (isBallVisible) FloatingBallConfig.getAlphaPercent(context) / 100f else 0f
            if (p != null && view.isAttachedToWindow) {
                try {
                    windowManager?.updateViewLayout(view, p)
                } catch (e: Exception) {
                    TestLog.w(MODULE, "结束悬浮球手势失败: ${e.message}")
                }
            }
        }

        view.setOnTouchListener { _, event ->
            if (!isAppAllowed()) {
                evaluateVisibility()
                return@setOnTouchListener false
            }
            val params = layoutParams ?: return@setOnTouchListener false
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    val ballShape = FloatingBallConfig.getBallShape(context)
                    if (ballShape == FloatingBallConfig.SHAPE_BORDERLESS && FloatingBallConfig.getImageResourceId(context) != null) {
                        if (!isHitOpaqueRegion(event.x, event.y)) {
                            // 透明区域防误触拦截：ACTION_DOWN 直接返回 false，不武装拖拽与点击状态机
                            isDownConsumed = false
                            return@setOnTouchListener false
                        }
                    }

                    // 从当前可见位置接管回弹，不能跳回上次手势的起点。
                    if (springReturnAnimator?.isRunning == true) {
                        cancelSpringReturn()
                    }
                    view.animate().cancel()

                    isDownConsumed = true
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    lastTouchX = event.rawX
                    lastTouchY = event.rawY
                    isClick = true
                    isLongPressed = false
                    isSwiped = false
                    isSwipedDown = false
                    isSwipedUp = false

                    // 按下即时动效：微缩下陷，透明度略增亮提供物理按压反馈
                    val baseAlpha = FloatingBallConfig.getAlphaPercent(context) / 100f
                    val pressedAlpha = (baseAlpha + 0.2f).coerceAtMost(1.0f)
                    view.animate()
                        .scaleX(0.90f)
                        .scaleY(0.90f)
                        .alpha(pressedAlpha)
                        .setDuration(120L)
                        .setInterpolator(DecelerateInterpolator())
                        .start()

                    // 启动 300ms 长按检测定时器
                    mainHandler.removeCallbacks(longPressRunnable)
                    mainHandler.postDelayed(longPressRunnable, 300L)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!isDownConsumed) return@setOnTouchListener false
                    lastTouchX = event.rawX
                    lastTouchY = event.rawY

                    val dx = event.rawX - initialTouchX
                    val dy = event.rawY - initialTouchY
                    val absDx = abs(dx)
                    val absDy = abs(dy)

                    if (isLongPressed) {
                        // 拖拽移动模式：1:1 流畅跟手
                        params.x = initialX + dx.toInt()
                        params.y = initialY + dy.toInt()
                        clampPosition(params)
                        windowManager?.updateViewLayout(floatingView, params)
                    } else {
                        val isHorizontalMotion = absDx > swipeThreshold && absDx > absDy * 1.3f
                        val isPullingHorizontal = absDx > clickThreshold && absDx > absDy * 1.2f

                        val isDownwardMotion = dy > swipeThreshold && absDy > absDx * 1.3f
                        val isUpwardMotion = dy < -swipeThreshold && absDy > absDx * 1.3f
                        val isPullingVertical = absDy > clickThreshold && absDy > absDx * 1.2f

                        if (!isSwiped && !isSwipedUp && isDownwardMotion) {
                            isSwipedDown = true
                            isClick = false
                            mainHandler.removeCallbacks(longPressRunnable)
                        } else if (isSwipedDown && dy < swipeThreshold * 0.5f) {
                            isSwipedDown = false
                        }

                        if (!isSwiped && !isSwipedDown && isUpwardMotion) {
                            isSwipedUp = true
                            isClick = false
                            mainHandler.removeCallbacks(longPressRunnable)
                        } else if (isSwipedUp && dy > -swipeThreshold * 0.5f) {
                            isSwipedUp = false
                        }

                        if (isHorizontalMotion && !isSwipedDown && !isSwipedUp) {
                            if (!isSwiped) {
                                isSwiped = true
                                isClick = false
                                mainHandler.removeCallbacks(longPressRunnable)
                            }
                        } else if (isSwiped && absDx < swipeThreshold * 0.5f) {
                            // 往回滑回起点附近，撤销横向滑动意图
                            isSwiped = false
                        }

                        // 实时计算受限的阻尼位移（限制位移最大值不超过 16dp）
                        val dampDx = if (isPullingHorizontal) {
                            val ratio = (absDx / travelLimitPx).coerceIn(0f, 1f)
                            val easedOffset = maxDisplacementPx * (ratio * (2f - ratio))
                            val sign = if (dx > 0f) 1f else -1f
                            (sign * easedOffset).toInt()
                        } else {
                            0
                        }

                        val dampDy = if (isPullingVertical) {
                            val ratio = (absDy / travelLimitPx).coerceIn(0f, 1f)
                            val easedOffset = maxDisplacementPx * (ratio * (2f - ratio))
                            val sign = if (dy > 0f) 1f else -1f
                            (sign * easedOffset).toInt()
                        } else {
                            0
                        }

                        val targetX = initialX + dampDx
                        val targetY = initialY + dampDy
                        if (params.x != targetX || params.y != targetY) {
                            params.x = targetX
                            params.y = targetY
                            clampPosition(params)
                            windowManager?.updateViewLayout(floatingView, params)
                        }

                        // 伴随微弱的压扁拉伸形变（限制在 5% 以内）
                        if (isPullingHorizontal) {
                            val stretchFactor = (abs(dampDx) / maxDisplacementPx) * 0.05f
                            view.scaleX = 0.90f + stretchFactor
                            view.scaleY = 0.90f - stretchFactor
                        } else if (isPullingVertical) {
                            val stretchFactor = (abs(dampDy) / maxDisplacementPx) * 0.05f
                            view.scaleX = 0.90f - stretchFactor
                            view.scaleY = 0.90f + stretchFactor
                        } else {
                            view.scaleX = 0.90f
                            view.scaleY = 0.90f
                        }

                        if (absDx > clickThreshold || absDy > clickThreshold) {
                            isClick = false
                        }
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (!isDownConsumed) return@setOnTouchListener false
                    mainHandler.removeCallbacks(longPressRunnable)
                    isDownConsumed = false

                    val baseAlpha = FloatingBallConfig.getAlphaPercent(context) / 100f

                    if (isLongPressed) {
                        // UP 可能带有最后一段位移，不能只保存最后一次 MOVE 的位置。
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        clampPosition(params)
                        windowManager?.updateViewLayout(floatingView, params)
                        // 拖拽完成：着陆反馈
                        view.animate().cancel()
                        view.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .alpha(baseAlpha)
                            .setDuration(180L)
                            .setInterpolator(DecelerateInterpolator())
                            .start()

                        FloatingBallConfig.saveBallPosition(context, params.x, params.y, positionOrientation)
                        TestLog.i(MODULE, "长按拖拽结束已持久化坐标: (${params.x}, ${params.y})")
                    } else if (isSwiped || isSwipedDown || isSwipedUp) {
                        // 滑动松手：回弹并执行对应动作。
                        animateSpringBack(params, initialX, initialY)

                        view.animate().cancel()
                        view.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .alpha(baseAlpha)
                            .setDuration(160L)
                            .setInterpolator(DecelerateInterpolator())
                            .start()

                        if (isSwipedDown) {
                            val dx = event.rawX - initialTouchX
                            val dy = event.rawY - initialTouchY
                            if (dy > swipeThreshold && abs(dy) > abs(dx) * 1.3f) {
                                TestLog.i(MODULE, "下滑悬浮球触发，切换常用/收藏夹")
                                TestImageIME.instance?.toggleFavoritesIfShowing()
                            }
                        } else if (isSwipedUp) {
                            val dx = event.rawX - initialTouchX
                            val dy = event.rawY - initialTouchY
                            if (dy < -swipeThreshold && abs(dy) > abs(dx) * 1.3f) {
                                TestLog.i(MODULE, "上滑悬浮球触发，切换常用/收藏夹")
                                TestImageIME.instance?.toggleFavoritesIfShowing()
                            }
                        } else {
                            TestLog.i(MODULE, "横向滑动悬浮球触发，启用顶部搜索框")
                            imeSwitchGuardUntil = System.currentTimeMillis() + 1200L
                            searchBarController?.show()
                        }
                    } else if (isClick) {
                        // 点击：弹性冲量回弹 + 动作执行
                        view.animate().cancel()
                        view.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .alpha(baseAlpha)
                            .setDuration(200L)
                            .setInterpolator(OvershootInterpolator(2.5f))
                            .start()

                        onFloatingBallActionTriggered()
                    } else {
                        // 未完成手势（中途松手）：平滑归位
                        if (params.x != initialX || params.y != initialY) {
                            animateSpringBack(params, initialX, initialY)
                        }
                        view.animate().cancel()
                        view.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .alpha(baseAlpha)
                            .setDuration(160L)
                            .setInterpolator(DecelerateInterpolator())
                            .start()
                    }
                    true
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_POINTER_DOWN -> {
                    // 第二根手指加入时结束本次手势，避免 pointer 索引切换造成坐标跳变。
                    val consumed = isDownConsumed
                    finishTouchGesture?.invoke()
                    consumed
                }
                else -> false
            }
        }
    }

    /**
     * 单击悬浮球 (纯静默切换):
     */
    fun onFloatingBallActionTriggered() {
        TestLog.i(MODULE, "========== 悬浮球动作触发 (静默切换) ==========")

        val accessibility = TestAccessibilityService.instance
        if (accessibility == null) {
            TestLog.w(MODULE, "无障碍辅助服务未就绪，打开无障碍设置")
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            return
        }

        // 切换保护锁：覆盖旧窗口销毁 → 新窗口就绪的间隙，期间忽略一切隐藏信号（旧版精髓）
        imeSwitchGuardUntil = System.currentTimeMillis() + 100L

        val currentIme = Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        val isCurrentOwnIme = accessibility.isSelfIme(currentIme)

        TestLog.i(MODULE, "当前默认 IME = $currentIme, isCurrentOwnIme = $isCurrentOwnIme")

        if (isCurrentOwnIme) {
            TestLog.i(MODULE, "当前已处于 SuzuEmojy，直接复用 IME 收起/退出恢复逻辑...")
            val ime = TestImageIME.instance
            if (ime != null) {
                ime.exitAndRestoreIme()
            } else {
                accessibility.restorePreviousIme()
            }
        } else {
            TestLog.i(MODULE, "当前非 SuzuEmojy，静默切换到 SuzuEmojy...")
            accessibility.cancelImeSwitch("用户主动切换键盘")
            accessibility.switchToTestIme()
        }
    }
}
