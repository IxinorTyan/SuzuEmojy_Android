package a.c.f;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static void a(android.widget.EdgeEffect edgeEffect, float f, float f2) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            edgeEffect.onPull(f, f2);
        } else {
            edgeEffect.onPull(f);
        }
    }
}
