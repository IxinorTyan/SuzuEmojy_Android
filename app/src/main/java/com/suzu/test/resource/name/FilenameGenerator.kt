package com.suzu.test.resource.name

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger

object FilenameGenerator {

    private val counter = AtomicInteger(0)
    private const val MAX_COUNTER = 999999

    fun generate(extension: String = ".png"): String {
        val safeExt = if (extension.startsWith(".")) extension else ".$extension"
        val timestamp = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(Date())
        val seq = counter.updateAndGet { (it + 1) % MAX_COUNTER }
        val suffix = "%06d".format(seq)
        return "$timestamp$suffix$safeExt"
    }
}
