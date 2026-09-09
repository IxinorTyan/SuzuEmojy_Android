package androidx.core.graphics.drawable;

/* JADX INFO: loaded from: classes.dex */
public final class c {

    private static class a extends androidx.core.graphics.drawable.b {
        a(android.content.res.Resources resources, android.graphics.Bitmap bitmap) {
            super(resources, bitmap);
        }

        @Override // androidx.core.graphics.drawable.b
        void a(int i, int i2, int i3, android.graphics.Rect rect, android.graphics.Rect rect2) {
            a.c.e.c.a(i, i2, i3, rect, rect2, 0);
        }
    }

    public static androidx.core.graphics.drawable.b a(android.content.res.Resources resources, android.graphics.Bitmap bitmap) {
        return android.os.Build.VERSION.SDK_INT >= 21 ? new androidx.core.graphics.drawable.a(resources, bitmap) : new androidx.core.graphics.drawable.c.a(resources, bitmap);
    }
}
