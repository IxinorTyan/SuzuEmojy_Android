package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private android.content.Intent a(int i, java.lang.String str, int[] iArr) {
        android.content.Intent intent = new android.content.Intent();
        android.content.ComponentName componentName = new android.content.ComponentName("me.hisn.slidedown", "me.hisn.slidedown" + str);
        intent.putExtra("action", i);
        intent.setComponent(componentName);
        if (i == 91 && iArr != null) {
            intent.putExtra("91", iArr);
        }
        return intent;
    }

    private void a(android.content.Context context, int i, int[] iArr) {
        c(context);
        android.content.Intent intentA = a(i, ".MTS", iArr);
        try {
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intentA);
            } else {
                context.startService(intentA);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void a(android.content.Context context) {
        a(context, 83, (int[]) null);
    }

    public void a(android.content.Context context, int[] iArr) {
        a(context, 91, iArr);
    }

    public void b(android.content.Context context) {
        a(context, 87, (int[]) null);
    }

    public long c(android.content.Context context) {
        android.content.pm.PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("me.hisn.slidedown", 128);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo != null) {
            return android.os.Build.VERSION.SDK_INT >= 28 ? packageInfo.getLongVersionCode() : packageInfo.versionCode;
        }
        return 0L;
    }

    public void d(android.content.Context context) {
        a(context, 85, (int[]) null);
    }

    public void e(android.content.Context context) {
        a(context, 86, (int[]) null);
    }

    public void f(android.content.Context context) {
        a(context, 90, (int[]) null);
    }
}
