package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class n0 {
    public java.lang.String a(android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        try {
            for (android.content.pm.Signature signature : packageManager.getPackageInfo(context.getPackageName(), 64).signatures) {
                sb.append(signature.toCharsString());
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
