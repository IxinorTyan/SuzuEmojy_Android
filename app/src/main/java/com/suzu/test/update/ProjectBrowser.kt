package com.suzu.test.update

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast

object ProjectBrowser {
    /** Resolve browsers using a neutral URL so GitHub's verified app links cannot intercept updates. */
    fun open(activity: Activity) {
        val packageManager = activity.packageManager
        val browserProbe = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"))
            .addCategory(Intent.CATEGORY_BROWSABLE)
        val browserCategory = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_BROWSER)
        val browsers = (
            packageManager.queryIntentActivities(browserProbe, PackageManager.MATCH_DEFAULT_ONLY)
                .map { it.activityInfo.packageName } +
                packageManager.queryIntentActivities(browserCategory, 0).map { it.activityInfo.packageName }
            ).distinct()
        val preferred = packageManager.resolveActivity(browserProbe, PackageManager.MATCH_DEFAULT_ONLY)
            ?.activityInfo?.packageName?.takeIf { it in browsers }
            ?: packageManager.resolveActivity(browserCategory, 0)
                ?.activityInfo?.packageName?.takeIf { it in browsers }
        fun target(browser: String) = Intent(Intent.ACTION_VIEW, Uri.parse(GitHubUpdateChecker.PROJECT_URL))
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setPackage(browser)

        if (browsers.isEmpty()) {
            Toast.makeText(activity, "未找到可用浏览器，请先安装或启用浏览器", Toast.LENGTH_LONG).show()
            return
        }
        val intent = if (preferred != null || browsers.size == 1) {
            target(preferred ?: browsers.first())
        } else {
            // A browser chooser is needed only when Android has no default browser.
            Intent.createChooser(target(browsers.first()), "选择浏览器").apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, browsers.drop(1).map { target(it) }.toTypedArray())
            }
        }
        try {
            activity.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(activity, "无法打开浏览器，请检查默认浏览器设置", Toast.LENGTH_LONG).show()
        }
    }
}
