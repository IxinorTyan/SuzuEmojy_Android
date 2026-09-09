package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class t {
    public static android.content.Intent a(android.content.Context context, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 128);
        if (listQueryIntentActivities.size() <= 0) {
            return null;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(str, listQueryIntentActivities.get(0).activityInfo.name);
        intent.setPackage(null);
        intent.setComponent(componentName);
        intent.setFlags(270532608);
        return intent;
    }
}
