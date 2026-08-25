package com.suzu.test.ui.import

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.suzu.test.databinding.ActivityImportBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.log.TestLog
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.ui.picker.MediaPickerActivity
import com.suzu.test.ui.picker.PickerResultStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImportActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "ImportActivity"
    }

    private lateinit var binding: ActivityImportBinding
    private lateinit var importService: ResourceImportService

    private val customPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val token = result.data?.getStringExtra(MediaPickerActivity.EXTRA_RESULT_TOKEN)
            val uris = PickerResultStore.consumeResultUris(this, token)
            if (uris.isNotEmpty()) {
                startImportProcess(uris)
            } else {
                binding.tvProgress.text = "未选择任何图片"
            }
        }
    }

    private val systemPhotoPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(50)
    ) { uris ->
        if (uris.isNotEmpty()) {
            startImportProcess(uris)
        } else {
            binding.tvProgress.text = "未选择任何图片"
        }
    }

    private val systemDocumentPickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (!uris.isNullOrEmpty()) {
            startImportProcess(uris)
        } else {
            binding.tvProgress.text = "未选择任何图片"
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        handlePermissionResult()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DatabaseProvider.getDatabase(this)
        importService = ResourceImportService(this, db)

        binding.btnPickImages.setOnClickListener {
            checkAndLaunchPicker()
        }

        binding.btnPickFromFiles.setOnClickListener {
            launchSystemFilePicker()
        }
    }

    private fun launchSystemFilePicker() {
        systemDocumentPickerLauncher.launch(
            arrayOf(
                "image/*",
                "image/png",
                "image/jpeg",
                "image/webp",
                "image/gif"
            )
        )
    }

    private fun checkAndLaunchPicker() {
        if (hasFullMediaPermission()) {
            launchCustomPicker()
        } else {
            requestMediaPermissions()
        }
    }

    private fun hasFullMediaPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestMediaPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionLauncher.launch(permissions)
    }

    private fun handlePermissionResult() {
        if (hasFullMediaPermission()) {
            launchCustomPicker()
        } else {
            fallbackToSystemPicker()
        }
    }

    private fun launchCustomPicker() {
        PickerResultStore.clearResidualFiles(this)
        val intent = Intent(this, MediaPickerActivity::class.java)
        customPickerLauncher.launch(intent)
    }

    private fun fallbackToSystemPicker() {
        Toast.makeText(
            this,
            "已使用系统选择器，授予完整相册权限可使用滑动多选与删除原图功能",
            Toast.LENGTH_LONG
        ).show()

        systemPhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    private fun startImportProcess(uris: List<Uri>) {
        binding.btnPickImages.isEnabled = false
        binding.btnPickFromFiles.isEnabled = false
        binding.tvSummary.text = ""

        lifecycleScope.launch {
            val total = uris.size
            var successCount = 0
            var duplicateCount = 0
            var failCount = 0

            withContext(Dispatchers.IO) {
                uris.forEachIndexed { index, uri ->
                    withContext(Dispatchers.Main) {
                        binding.tvProgress.text = "正在导入 ${index + 1}/$total"
                    }

                    try {
                        val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
                        if (bytes == null || bytes.isEmpty()) {
                            TestLog.e(MODULE, "读取 URI 失败或文件为空: $uri")
                            failCount++
                            return@forEachIndexed
                        }

                        val result = importService.import(bytes)
                        if (result.isDuplicate) {
                            duplicateCount++
                        } else {
                            successCount++
                        }
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "导入异常 (URI=$uri): ${e.message}", e)
                        failCount++
                    }
                }
            }

            binding.tvProgress.text = "导入流程已完成"
            binding.tvSummary.text = "成功导入 $successCount 张，重复跳过 $duplicateCount 张，失败 $failCount 张"
            binding.btnPickImages.isEnabled = true
            binding.btnPickFromFiles.isEnabled = true
        }
    }
}
