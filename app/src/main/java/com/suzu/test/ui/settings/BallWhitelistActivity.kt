package com.suzu.test.ui.settings

import android.content.Intent
import com.suzu.test.floating.BallAppWhitelist

class BallWhitelistActivity : AppWhitelistActivity() {
    override val pageTitle = "悬浮球白名单"
    override val pageSubtitle = "选择允许悬浮球生效的应用"
    override val explanation = "本应用固定开启，以支持搜索等自家界面。其他应用开启后按原有规则显示悬浮球，修改立即保存；已开启的应用会在下次进入时排在最前面。"
    override val emptyHint = "未发现可选择的应用"
    override val switchDescription = "允许悬浮球生效"
    override val retainUnavailable = true
    override fun isSelectionLocked(packageName: String) = packageName == this.packageName
    override fun queryIntent() = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    override fun selectedPackages() = BallAppWhitelist.packages(this)
    override fun saveSelection(packageName: String, enabled: Boolean) {
        val selected = selectedPackages().toMutableSet()
        if (enabled) selected.add(packageName) else selected.remove(packageName)
        BallAppWhitelist.savePackages(this, selected)
    }
}
