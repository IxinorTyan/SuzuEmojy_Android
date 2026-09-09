package androidx.core.graphics.drawable;

/* JADX INFO: loaded from: classes.dex */
class a extends androidx.core.graphics.drawable.b {
    protected a(android.content.res.Resources resources, android.graphics.Bitmap bitmap) {
        super(resources, bitmap);
    }

    @Override // androidx.core.graphics.drawable.b
    void a(int i, int i2, int i3, android.graphics.Rect rect, android.graphics.Rect rect2) {
        android.view.Gravity.apply(i, i2, i3, rect, rect2, 0);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        b();
        outline.setRoundRect(this.h, a());
    }
}
