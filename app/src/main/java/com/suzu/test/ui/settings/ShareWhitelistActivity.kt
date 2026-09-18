package com.suzu.test.ui.settings

import android.content.Intent
import android.content.pm.PackageManager
import com.suzu.test.ime.config.ShareWhitelistConfig

class ShareWhitelistActivity : AppWhitelistActivity() {
    override val pageTitle = "分享白名单"
    override val pageSubtitle = "配置拉起系统分享选择器的目标应用"
    override val explanation = "开启后，在该应用中发送表情将唤起系统分享面板；未开启的应用继续使用原有直发机制。已开启的应用会在下次进入时排在最前面。"
    override val emptyHint = "未发现支持图片分享的目标应用"
    override val switchDescription = "使用系统分享选择器"
    override val queryFlags = PackageManager.MATCH_DEFAULT_ONLY
    override val excludeSelf = true
    override fun queryIntent() = Intent(Intent.ACTION_SEND).apply { type = "image/*" }
    override fun selectedPackages() = ShareWhitelistConfig.getPackages(this)
    override fun saveSelection(packageName: String, enabled: Boolean) {
        ShareWhitelistConfig.setWhitelisted(this, packageName, enabled)
    }
}
