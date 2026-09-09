package a.c.c;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static void a() {
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            android.os.Trace.endSection();
        }
    }

    public static void a(java.lang.String str) {
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            android.os.Trace.beginSection(str);
        }
    }
}
