package androidx.core.content;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final java.lang.Object f168a = new java.lang.Object();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static android.util.TypedValue f169b;

    public static int a(android.content.Context context, java.lang.String str) {
        if (str != null) {
            return context.checkPermission(str, android.os.Process.myPid(), android.os.Process.myUid());
        }
        throw new java.lang.IllegalArgumentException("permission is null");
    }

    public static android.graphics.drawable.Drawable a(android.content.Context context, int i) {
        int i2 = android.os.Build.VERSION.SDK_INT;
        if (i2 >= 21) {
            return context.getDrawable(i);
        }
        if (i2 < 16) {
            synchronized (f168a) {
                if (f169b == null) {
                    f169b = new android.util.TypedValue();
                }
                context.getResources().getValue(i, f169b, true);
                i = f169b.resourceId;
            }
        }
        return context.getResources().getDrawable(i);
    }

    public static java.io.File[] a(android.content.Context context) {
        return android.os.Build.VERSION.SDK_INT >= 19 ? context.getExternalCacheDirs() : new java.io.File[]{context.getExternalCacheDir()};
    }

    public static java.io.File[] b(android.content.Context context, java.lang.String str) {
        return android.os.Build.VERSION.SDK_INT >= 19 ? context.getExternalFilesDirs(str) : new java.io.File[]{context.getExternalFilesDir(str)};
    }
}
