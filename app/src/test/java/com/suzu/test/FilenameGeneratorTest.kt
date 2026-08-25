package com.suzu.test

import com.suzu.test.resource.name.FilenameGenerator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FilenameGeneratorTest {

    @Test
    fun testGenerateUniqueFilenames() {
        val count = 1000
        val filenames = (1..count).map { FilenameGenerator.generate(".png") }
        val uniqueSet = filenames.toSet()

        assertEquals("连续生成的 1000 个文件名必须全部唯一", count, uniqueSet.size)

        for (filename in filenames) {
            assertTrue("文件名必须以 .png 结尾", filename.endsWith(".png"))
            assertTrue("文件名长度至少应包含时间戳与附加序列位", filename.length >= 27)
        }
    }
}
