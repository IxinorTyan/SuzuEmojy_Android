package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class i0 {
    public void a(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
        android.content.ComponentName componentName = new android.content.ComponentName("com.eg.android.AlipayGphone", "com.eg.android.AlipayGphone.FastStartActivity");
        intent.setComponent(componentName);
        intent.addFlags(32768);
        new me.hisn.utils.s().a(context.getApplicationContext(), intent, null, componentName.getPackageName());
    }

    public void b(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse("alipays://platformapi/startapp?saId=10000007&sourceId=shortcut"));
        intent.addFlags(32768);
        new me.hisn.utils.s().a(context.getApplicationContext(), intent, null, null);
    }

    public void c(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
        android.content.ComponentName componentName = new android.content.ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setComponent(componentName);
        intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
        intent.addFlags(32768);
        new me.hisn.utils.s().a(context.getApplicationContext(), intent, null, componentName.getPackageName());
    }
}
