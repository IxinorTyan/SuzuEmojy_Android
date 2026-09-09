package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public android.graphics.drawable.Drawable f690a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public java.lang.String f691b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public java.lang.String f692c;
    public java.lang.String d;
    public int e;
    public int f;

    public static android.graphics.drawable.Drawable a(android.content.Context context, java.lang.String str) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getApplicationInfo(str, 8192).loadIcon(packageManager);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
