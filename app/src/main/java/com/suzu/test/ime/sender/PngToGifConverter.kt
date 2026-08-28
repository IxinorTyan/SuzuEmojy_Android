package com.suzu.test.ime.sender

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.suzu.test.log.TestLog
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * PngToGifConverter
 * 将静态 PNG 图像转换为 1 帧透明 GIF89a 格式。
 * 遵循严格的色彩安全选择、尺寸防护与异常降级策略。
 */
object PngToGifConverter {

    private const val MODULE = "PngToGifConverter"
    private const val MAX_PIXEL_COUNT = 1_000_000 // 100 万像素上限防护

    // 候选保留色集合：纯品红、纯青、纯黄、纯绿、纯蓝、纯红、纯黑、纯白
    private val CANDIDATE_COLORS = intArrayOf(
        0xFF00FF, // Magenta
        0x00FFFF, // Cyan
        0xFFFF00, // Yellow
        0x00FF00, // Green
        0x0000FF, // Blue
        0xFF0000, // Red
        0x000000, // Black
        0xFFFFFF  // White
    )

    /**
     * 校验文件是否存在、非空且头部是否为标准 GIF8
     */
    fun isValidGif(file: File?): Boolean {
        if (file == null || !file.exists() || file.length() < 6) return false
        return try {
            file.inputStream().use { input ->
                val header = ByteArray(4)
                val read = input.read(header)
                read == 4 &&
                        header[0] == 'G'.code.toByte() &&
                        header[1] == 'I'.code.toByte() &&
                        header[2] == 'F'.code.toByte() &&
                        header[3] == '8'.code.toByte()
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 将输入流中的 PNG 转换为单帧 GIF 并写入 outputFile。
     * @param inputStreamProvider 提供原始文件输入流的 lambda
     * @param outputFile 目标文件
     * @return 转换成功且校验通过返回 true，否则返回 false（调用方可降级发原图）
     */
    fun convertPngToGif(inputStreamProvider: () -> InputStream?, outputFile: File): Boolean {
        try {
            // 1. inJustDecodeBounds 预检尺寸
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            inputStreamProvider()?.use { input ->
                BitmapFactory.decodeStream(input, null, options)
            } ?: return false

            val width = options.outWidth
            val height = options.outHeight
            if (width <= 0 || height <= 0) {
                TestLog.w(MODULE, "图片尺寸解析异常: width=$width, height=$height")
                return false
            }

            val pixelCount = width.toLong() * height.toLong()
            if (pixelCount > MAX_PIXEL_COUNT) {
                TestLog.i(MODULE, "源图像素数 $pixelCount 超过上限 $MAX_PIXEL_COUNT，跳过转换直接降级原图")
                return false
            }

            // 2. 解码为 ARGB_8888 Bitmap
            val decodeOptions = BitmapFactory.Options().apply {
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            val srcBitmap = inputStreamProvider()?.use { input ->
                BitmapFactory.decodeStream(input, null, decodeOptions)
            } ?: return false

            val w = srcBitmap.width
            val h = srcBitmap.height
            val pixels = IntArray(w * h)
            srcBitmap.getPixels(pixels, 0, w, 0, 0, w, h)
            srcBitmap.recycle()

            // 3. 选取最优保留色并执行 Alpha 预处理
            val chosenReserveColor = selectOptimalReserveColor(pixels)
            var hasTransparent = false

            for (i in pixels.indices) {
                val c = pixels[i]
                val a = (c ushr 24) and 0xff
                if (a < 128) {
                    // alpha < 128: 写入保留色，作为透明标记
                    pixels[i] = (0xFF shl 24) or (chosenReserveColor and 0x00FFFFFF)
                    hasTransparent = true
                } else {
                    // alpha >= 128: 强制将 alpha 置为 255
                    pixels[i] = (0xFF shl 24) or (c and 0x00FFFFFF)
                }
            }

            // 4. 执行单帧 GIF 编码输出
            val parent = outputFile.parentFile
            if (parent != null && !parent.exists()) {
                parent.mkdirs()
            }

            val tempOutFile = File(outputFile.parentFile, "${outputFile.name}.tmp_${System.currentTimeMillis()}")
            val encodeSuccess = FileOutputStream(tempOutFile).use { fos ->
                val encoder = SingleFrameGifEncoder()
                if (hasTransparent) {
                    encoder.setTransparent(chosenReserveColor)
                }
                encoder.setDelay(0)
                if (encoder.start(fos, w, h)) {
                    val frameBitmap = Bitmap.createBitmap(pixels, w, h, Bitmap.Config.ARGB_8888)
                    val frameAdded = encoder.addFrame(frameBitmap)
                    frameBitmap.recycle()
                    encoder.finish() && frameAdded
                } else {
                    false
                }
            }

            if (!encodeSuccess) {
                tempOutFile.delete()
                TestLog.w(MODULE, "GIF 编码输出失败")
                return false
            }

            // 5. 产物校验
            if (isValidGif(tempOutFile)) {
                if (outputFile.exists()) {
                    outputFile.delete()
                }
                val renameOk = tempOutFile.renameTo(outputFile)
                if (renameOk && isValidGif(outputFile)) {
                    TestLog.i(MODULE, "PNG 成功转换为单帧 GIF: ${outputFile.name}, size=${outputFile.length()} bytes")
                    return true
                } else {
                    tempOutFile.delete()
                    outputFile.delete()
                    return false
                }
            } else {
                tempOutFile.delete()
                TestLog.w(MODULE, "生成的 GIF 文件校验失败 (空文件或非 GIF8 头部)")
                return false
            }

        } catch (oom: OutOfMemoryError) {
            TestLog.e(MODULE, "PNG 转 GIF 发生 OOM，降级发原图: ${oom.message}", oom)
            outputFile.delete()
            return false
        } catch (e: Exception) {
            TestLog.e(MODULE, "PNG 转 GIF 发生异常，降级发原图: ${e.message}", e)
            outputFile.delete()
            return false
        }
    }

    /**
     * 在候选保留色中，选取“到图像中任意不透明像素颜色欧氏距离的最小值”最大的那个候选色。
     */
    private fun selectOptimalReserveColor(pixels: IntArray): Int {
        var bestCandidate = CANDIDATE_COLORS[0]
        var maxMinDistance = -1.0

        // 提取采样的原图不透明 RGB 颜色以加快计算速度
        val step = (pixels.size / 2000).coerceAtLeast(1)
        val sampledOpaqueColors = ArrayList<Int>(2000)
        for (i in pixels.indices step step) {
            val c = pixels[i]
            val a = (c ushr 24) and 0xff
            if (a >= 128) {
                sampledOpaqueColors.add(c and 0x00FFFFFF)
            }
        }

        if (sampledOpaqueColors.isEmpty()) {
            return bestCandidate
        }

        for (candidate in CANDIDATE_COLORS) {
            val cr = (candidate ushr 16) and 0xFF
            val cg = (candidate ushr 8) and 0xFF
            val cb = candidate and 0xFF

            var minDistance = Double.MAX_VALUE
            for (sc in sampledOpaqueColors) {
                val sr = (sc ushr 16) and 0xFF
                val sg = (sc ushr 8) and 0xFF
                val sb = sc and 0xFF
                val dr = cr - sr
                val dg = cg - sg
                val db = cb - sb
                val dist = (dr * dr + dg * dg + db * db).toDouble()
                if (dist < minDistance) {
                    minDistance = dist
                    if (minDistance == 0.0) break
                }
            }

            if (minDistance > maxMinDistance) {
                maxMinDistance = minDistance
                bestCandidate = candidate
            }
        }

        return bestCandidate
    }

    // --- 单帧轻量 GIF89a 编码器 ---
    private class SingleFrameGifEncoder {
        private var width = 0
        private var height = 0
        private var transparentColor: Int? = null
        private var transIndex = 0
        private var delay = 0
        private var started = false
        private var out: OutputStream? = null
        private var indexedPixels: ByteArray? = null
        private var colorTab: ByteArray? = null
        private val usedEntry = BooleanArray(256)
        private val palSize = 7 // 2^(7+1) = 256 colors
        private val colorDepth = 8

        fun setTransparent(color: Int) {
            transparentColor = color and 0x00FFFFFF
        }

        fun setDelay(ms: Int) {
            delay = Math.round(ms / 10.0f)
        }

        fun start(os: OutputStream, w: Int, h: Int): Boolean {
            out = os
            width = w
            height = h
            return try {
                writeString("GIF89a")
                started = true
                true
            } catch (e: Exception) {
                false
            }
        }

        fun addFrame(im: Bitmap): Boolean {
            if (!started || out == null) return false
            return try {
                val rgb = IntArray(width * height)
                im.getPixels(rgb, 0, width, 0, 0, width, height)
                val rawRgb = ByteArray(rgb.size * 3)
                var count = 0
                for (c in rgb) {
                    rawRgb[count++] = ((c ushr 16) and 0xff).toByte()
                    rawRgb[count++] = ((c ushr 8) and 0xff).toByte()
                    rawRgb[count++] = (c and 0xff).toByte()
                }

                val nPix = rawRgb.size / 3
                val indexed = ByteArray(nPix)
                val nq = NeuQuant(rawRgb, rawRgb.size, 10)
                val tab = nq.process()
                colorTab = tab

                var k = 0
                for (i in 0 until nPix) {
                    val index = nq.map(
                        rawRgb[k++].toInt() and 0xff,
                        rawRgb[k++].toInt() and 0xff,
                        rawRgb[k++].toInt() and 0xff
                    )
                    usedEntry[index] = true
                    indexed[i] = index.toByte()
                }
                indexedPixels = indexed

                if (transparentColor != null) {
                    transIndex = findClosest(tab, transparentColor!!)
                }

                // 写入头部与首帧描述（无 Netscape 扩展）
                writeLSD()
                writePalette()
                writeGraphicCtrlExt()
                writeImageDesc()
                writePixels()
                true
            } catch (e: Exception) {
                false
            }
        }

        fun finish(): Boolean {
            if (!started || out == null) return false
            return try {
                out!!.write(0x3b) // GIF trailer
                out!!.flush()
                started = false
                true
            } catch (e: Exception) {
                false
            }
        }

        private fun findClosest(colorTab: ByteArray, color: Int): Int {
            val r = (color ushr 16) and 0xff
            val g = (color ushr 8) and 0xff
            val b = color and 0xff
            var minpos = 0
            var dmin = 256 * 256 * 256
            val len = colorTab.size
            var i = 0
            while (i < len) {
                val dr = r - (colorTab[i++].toInt() and 0xff)
                val dg = g - (colorTab[i++].toInt() and 0xff)
                val db = b - (colorTab[i++].toInt() and 0xff)
                val d = dr * dr + dg * dg + db * db
                val index = i / 3 - 1
                if (usedEntry[index] && d < dmin) {
                    dmin = d
                    minpos = index
                }
            }
            return minpos
        }

        private fun writeGraphicCtrlExt() {
            val os = out ?: return
            os.write(0x21) // extension
            os.write(0xf9) // GCE
            os.write(4)    // byte size
            val transp = if (transparentColor != null) 1 else 0
            val disp = if (transparentColor != null) 2 else 0 // restore to background
            os.write(0 or (disp shl 2) or 0 or transp)
            writeShort(delay)
            os.write(transIndex)
            os.write(0) // block terminator
        }

        private fun writeImageDesc() {
            val os = out ?: return
            os.write(0x2c) // image separator
            writeShort(0)
            writeShort(0)
            writeShort(width)
            writeShort(height)
            os.write(0)
        }

        private fun writeLSD() {
            val os = out ?: return
            writeShort(width)
            writeShort(height)
            os.write(0x80 or 0x70 or 0x00 or palSize)
            os.write(0)
            os.write(0)
        }

        private fun writePalette() {
            val os = out ?: return
            val tab = colorTab ?: return
            os.write(tab, 0, tab.size)
            val n = 3 * 256 - tab.size
            for (i in 0 until n) {
                os.write(0)
            }
        }

        private fun writePixels() {
            val os = out ?: return
            val pixels = indexedPixels ?: return
            val encoder = LZWEncoder(width, height, pixels, colorDepth)
            encoder.encode(os)
        }

        private fun writeShort(value: Int) {
            val os = out ?: return
            os.write(value and 0xff)
            os.write((value ushr 8) and 0xff)
        }

        private fun writeString(s: String) {
            val os = out ?: return
            for (i in 0 until s.length) {
                os.write(s[i].code.toByte().toInt())
            }
        }
    }

    // --- NeuQuant 神经网络量化类 ---
    private class NeuQuant(
        private val thepicture: ByteArray,
        private val lengthcount: Int,
        sample: Int
    ) {
        companion object {
            private const val netsize = 256
            private const val prime1 = 499
            private const val prime2 = 491
            private const val prime3 = 487
            private const val prime4 = 503
            private const val maxnetpos = netsize - 1
            private const val netbiasshift = 4
            private const val ncycles = 100
            private const val intbiasshift = 16
            private const val intbias = 1 shl intbiasshift
            private const val gammashift = 10
            private const val betashift = 10
            private const val beta = intbias shr betashift
            private const val betagamma = intbias shl (gammashift - betashift)
            private const val initrad = netsize shr 3
            private const val radiusbiasshift = 6
            private const val radiusbias = 1 shl radiusbiasshift
            private const val initradius = initrad * radiusbias
            private const val radiusdec = 30
            private const val alphabiasshift = 10
            private const val initalpha = 1 shl alphabiasshift
            private const val radbiasshift = 8
            private const val radbias = 1 shl radbiasshift
            private const val alpharadbshift = alphabiasshift + radbiasshift
            private const val alpharadbias = 1 shl alpharadbshift
        }

        private val samplefac = sample
        private val network = Array(netsize) { IntArray(4) }
        private val netindex = IntArray(256)
        private val bias = IntArray(netsize)
        private val freq = IntArray(netsize)
        private val radpower = IntArray(initrad)
        private var alphadec = 0

        init {
            for (i in 0 until netsize) {
                val p = network[i]
                p[0] = (i shl (netbiasshift + 8)) / netsize
                p[1] = p[0]
                p[2] = p[0]
                freq[i] = intbias / netsize
                bias[i] = 0
            }
        }

        fun colorMap(): ByteArray {
            val map = ByteArray(3 * netsize)
            val index = IntArray(netsize)
            for (i in 0 until netsize) index[network[i][3]] = i
            var k = 0
            for (i in 0 until netsize) {
                val j = index[i]
                map[k++] = (network[j][0] shr netbiasshift).toByte()
                map[k++] = (network[j][1] shr netbiasshift).toByte()
                map[k++] = (network[j][2] shr netbiasshift).toByte()
            }
            return map
        }

        fun inxbuild() {
            var previouscol = 0
            var startpos = 0
            for (i in 0 until netsize) {
                val p = network[i]
                var smallpos = i
                var smallval = p[1]
                for (j in i + 1 until netsize) {
                    val q = network[j]
                    if (q[1] < smallval) {
                        smallpos = j
                        smallval = q[1]
                    }
                }
                val q = network[smallpos]
                if (i != smallpos) {
                    var j = q[0]; q[0] = p[0]; p[0] = j
                    j = q[1]; q[1] = p[1]; p[1] = j
                    j = q[2]; q[2] = p[2]; p[2] = j
                    j = q[3]; q[3] = p[3]; p[3] = j
                }
                if (smallval != previouscol) {
                    netindex[previouscol] = (startpos + i) shr 1
                    for (j in previouscol + 1 until smallval) netindex[j] = i
                    previouscol = smallval
                    startpos = i
                }
            }
            netindex[previouscol] = (startpos + maxnetpos) shr 1
            for (j in previouscol + 1 until 256) netindex[j] = maxnetpos
        }

        fun learn() {
            alphadec = 30 + ((samplefac - 1) / 3)
            val step = when {
                lengthcount % prime1 != 0 -> 3 * prime1
                lengthcount % prime2 != 0 -> 3 * prime2
                lengthcount % prime3 != 0 -> 3 * prime3
                else -> 3 * prime4
            }

            val samplepixels = lengthcount / (3 * samplefac)
            val delta = (samplepixels / ncycles).coerceAtLeast(1)
            var alpha = initalpha
            var radius = initradius
            var rad = radius shr radiusbiasshift
            if (rad <= 1) rad = 0
            for (idx in 0 until rad) {
                radpower[idx] = alpha * (((rad * rad - idx * idx) * radbias) / (rad * rad))
            }

            var pix = 0
            for (idx in 0 until samplepixels) {
                val b = (thepicture[pix].toInt() and 0xff) shl netbiasshift
                val g = (thepicture[pix + 1].toInt() and 0xff) shl netbiasshift
                val r = (thepicture[pix + 2].toInt() and 0xff) shl netbiasshift
                val j = contest(b, g, r)

                altersingle(alpha, j, b, g, r)
                if (rad != 0) alterneigh(rad, j, b, g, r)

                pix += step
                if (pix >= lengthcount) pix -= lengthcount

                if ((idx + 1) % delta == 0) {
                    alpha -= alpha / alphadec
                    radius -= radius / radiusdec
                    rad = radius shr radiusbiasshift
                    if (rad <= 1) rad = 0
                    for (rIdx in 0 until rad) {
                        radpower[rIdx] = alpha * (((rad * rad - rIdx * rIdx) * radbias) / (rad * rad))
                    }
                }
            }
        }

        fun map(b: Int, g: Int, r: Int): Int {
            var bestd = 1000
            var best = -1
            var i = netindex[g]
            var j = i - 1

            while (i < netsize || j >= 0) {
                if (i < netsize) {
                    val p = network[i]
                    val dist = p[1] - g
                    if (dist >= bestd) {
                        i = netsize
                    } else {
                        i++
                        val absDist = if (dist < 0) -dist else dist
                        var a = p[0] - b
                        if (a < 0) a = -a
                        val total = absDist + a
                        if (total < bestd) {
                            var a2 = p[2] - r
                            if (a2 < 0) a2 = -a2
                            val full = total + a2
                            if (full < bestd) {
                                bestd = full
                                best = p[3]
                            }
                        }
                    }
                }
                if (j >= 0) {
                    val p = network[j]
                    val dist = g - p[1]
                    if (dist >= bestd) {
                        j = -1
                    } else {
                        j--
                        val absDist = if (dist < 0) -dist else dist
                        var a = p[0] - b
                        if (a < 0) a = -a
                        val total = absDist + a
                        if (total < bestd) {
                            var a2 = p[2] - r
                            if (a2 < 0) a2 = -a2
                            val full = total + a2
                            if (full < bestd) {
                                bestd = full
                                best = p[3]
                            }
                        }
                    }
                }
            }
            return best
        }

        fun process(): ByteArray {
            learn()
            unbiasnet()
            inxbuild()
            return colorMap()
        }

        private fun unbiasnet() {
            for (i in 0 until netsize) {
                network[i][0] = network[i][0] shr netbiasshift
                network[i][1] = network[i][1] shr netbiasshift
                network[i][2] = network[i][2] shr netbiasshift
                network[i][3] = i
            }
        }

        private fun alterneigh(rad: Int, i: Int, b: Int, g: Int, r: Int) {
            val lo = (i - rad).coerceAtLeast(-1)
            val hi = (i + rad).coerceAtMost(netsize)
            var j = i + 1
            var k = i - 1
            var m = 1
            while (j < hi || k > lo) {
                val a = radpower[m++]
                if (j < hi) {
                    val p = network[j++]
                    try {
                        p[0] -= (a * (p[0] - b)) / alpharadbias
                        p[1] -= (a * (p[1] - g)) / alpharadbias
                        p[2] -= (a * (p[2] - r)) / alpharadbias
                    } catch (ignored: Exception) {}
                }
                if (k > lo) {
                    val p = network[k--]
                    try {
                        p[0] -= (a * (p[0] - b)) / alpharadbias
                        p[1] -= (a * (p[1] - g)) / alpharadbias
                        p[2] -= (a * (p[2] - r)) / alpharadbias
                    } catch (ignored: Exception) {}
                }
            }
        }

        private fun altersingle(alpha: Int, i: Int, b: Int, g: Int, r: Int) {
            val n = network[i]
            n[0] -= (alpha * (n[0] - b)) / initalpha
            n[1] -= (alpha * (n[1] - g)) / initalpha
            n[2] -= (alpha * (n[2] - r)) / initalpha
        }

        private fun contest(b: Int, g: Int, r: Int): Int {
            var bestd = Int.MAX_VALUE
            var bestbiasd = bestd
            var bestpos = -1
            var bestbiaspos = bestpos

            for (i in 0 until netsize) {
                val n = network[i]
                val dr = n[0] - b; val distR = if (dr < 0) -dr else dr
                val dg = n[1] - g; val distG = if (dg < 0) -dg else dg
                val db = n[2] - r; val distB = if (db < 0) -db else db
                val dist = distR + distG + distB
                if (dist < bestd) {
                    bestd = dist
                    bestpos = i
                }
                val biasdist = dist - (bias[i] shr (intbiasshift - netbiasshift))
                if (biasdist < bestbiasd) {
                    bestbiasd = biasdist
                    bestbiaspos = i
                }
                val betafreq = freq[i] shr betashift
                freq[i] -= betafreq
                bias[i] += betafreq shl gammashift
            }
            freq[bestpos] += beta
            bias[bestpos] -= betagamma
            return bestbiaspos
        }
    }

    // --- LZW 压缩编码类 ---
    private class LZWEncoder(
        private val imgW: Int,
        private val imgH: Int,
        private val pixAry: ByteArray,
        color_depth: Int
    ) {
        companion object {
            private const val EOF = -1
            private const val BITS = 12
            private const val HSIZE = 5003
            private val masks = intArrayOf(
                0x0000, 0x0001, 0x0003, 0x0007, 0x000F,
                0x001F, 0x003F, 0x007F, 0x00FF, 0x01FF,
                0x03FF, 0x07FF, 0x0FFF, 0x1FFF, 0x3FFF,
                0x7FFF, 0xFFFF
            )
        }

        private val initCodeSize = Math.max(2, color_depth)
        private var remaining = 0
        private var curPixel = 0
        private var n_bits = 0
        private val maxbits = BITS
        private var maxcode = 0
        private val maxmaxcode = 1 shl BITS
        private val htab = IntArray(HSIZE)
        private val codetab = IntArray(HSIZE)
        private val hsize = HSIZE
        private var free_ent = 0
        private var clear_flg = false
        private var g_init_bits = 0
        private var ClearCode = 0
        private var EOFCode = 0
        private var cur_accum = 0
        private var cur_bits = 0
        private var a_count = 0
        private val accum = ByteArray(256)

        private fun char_out(c: Byte, outs: OutputStream) {
            accum[a_count++] = c
            if (a_count >= 254) flush_char(outs)
        }

        private fun cl_block(outs: OutputStream) {
            cl_hash(hsize)
            free_ent = ClearCode + 2
            clear_flg = true
            output(ClearCode, outs)
        }

        private fun cl_hash(hsize: Int) {
            for (i in 0 until hsize) htab[i] = -1
        }

        private fun compress(init_bits: Int, outs: OutputStream) {
            g_init_bits = init_bits
            clear_flg = false
            n_bits = g_init_bits
            maxcode = MAXCODE(n_bits)
            ClearCode = 1 shl (init_bits - 1)
            EOFCode = ClearCode + 1
            free_ent = ClearCode + 2
            a_count = 0

            var ent = nextPixel()
            var hshift = 0
            var fcode = hsize
            while (fcode < 65536) {
                ++hshift
                fcode *= 2
            }
            hshift = 8 - hshift
            val hsize_reg = hsize
            cl_hash(hsize_reg)
            output(ClearCode, outs)

            var c: Int
            outer@ while (true) {
                c = nextPixel()
                if (c == EOF) break
                fcode = (c shl maxbits) + ent
                var i = (c shl hshift) xor ent
                if (htab[i] == fcode) {
                    ent = codetab[i]
                    continue
                } else if (htab[i] >= 0) {
                    var disp = hsize_reg - i
                    if (i == 0) disp = 1
                    do {
                        i -= disp
                        if (i < 0) i += hsize_reg
                        if (htab[i] == fcode) {
                            ent = codetab[i]
                            continue@outer
                        }
                    } while (htab[i] >= 0)
                }
                output(ent, outs)
                ent = c
                if (free_ent < maxmaxcode) {
                    codetab[i] = free_ent++
                    htab[i] = fcode
                } else {
                    cl_block(outs)
                }
            }
            output(ent, outs)
            output(EOFCode, outs)
        }

        fun encode(os: OutputStream) {
            os.write(initCodeSize)
            remaining = imgW * imgH
            curPixel = 0
            compress(initCodeSize + 1, os)
            os.write(0)
        }

        private fun flush_char(outs: OutputStream) {
            if (a_count > 0) {
                outs.write(a_count)
                outs.write(accum, 0, a_count)
                a_count = 0
            }
        }

        private fun MAXCODE(n_bits: Int): Int = (1 shl n_bits) - 1

        private fun nextPixel(): Int {
            if (remaining == 0) return EOF
            --remaining
            val pix = pixAry[curPixel++]
            return pix.toInt() and 0xff
        }

        private fun output(code: Int, outs: OutputStream) {
            cur_accum = cur_accum and masks[cur_bits]
            if (cur_bits > 0) cur_accum = cur_accum or (code shl cur_bits)
            else cur_accum = code
            cur_bits += n_bits
            while (cur_bits >= 8) {
                char_out((cur_accum and 0xff).toByte(), outs)
                cur_accum = cur_accum shr 8
                cur_bits -= 8
            }
            if (free_ent > maxcode || clear_flg) {
                if (clear_flg) {
                    n_bits = g_init_bits
                    maxcode = MAXCODE(n_bits)
                    clear_flg = false
                } else {
                    ++n_bits
                    maxcode = if (n_bits == maxbits) maxmaxcode else MAXCODE(n_bits)
                }
            }
            if (code == EOFCode) {
                while (cur_bits > 0) {
                    char_out((cur_accum and 0xff).toByte(), outs)
                    cur_accum = cur_accum shr 8
                    cur_bits -= 8
                }
                flush_char(outs)
            }
        }
    }
}
