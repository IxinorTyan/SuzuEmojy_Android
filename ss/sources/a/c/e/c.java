package a.c.e;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public static void a(int i, int i2, int i3, android.graphics.Rect rect, android.graphics.Rect rect2, int i4) {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            android.view.Gravity.apply(i, i2, i3, rect, rect2, i4);
        } else {
            android.view.Gravity.apply(i, i2, i3, rect, rect2);
        }
    }
}
