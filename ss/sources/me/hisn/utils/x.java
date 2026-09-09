package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class x {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.graphics.Paint f878a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.graphics.PorterDuffXfermode f879b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.graphics.Bitmap f880c;
    private int d;
    private int e;

    public x() {
        android.graphics.Paint paint = new android.graphics.Paint();
        this.f878a = paint;
        paint.setAntiAlias(true);
        this.f879b = new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    private android.graphics.Bitmap a(android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public android.graphics.Bitmap a(android.graphics.Bitmap bitmap, android.graphics.Bitmap bitmap2, int i, int i2, int i3) {
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmap2 == null ? this.f880c : android.graphics.Bitmap.createScaledBitmap(bitmap2, i, i2, true), 0.0f, 0.0f, this.f878a);
        this.f878a.setXfermode(this.f879b);
        int i4 = i3 * 2;
        android.graphics.Bitmap bitmapCreateScaledBitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, i + i4, i2 + i4, true);
        float f = i3 * (-1);
        canvas.drawBitmap(bitmapCreateScaledBitmap, f, f, this.f878a);
        this.f878a.setXfermode(null);
        return bitmapCreateBitmap;
    }

    public android.graphics.drawable.Drawable a(android.content.Context context) {
        return context.getDrawable(me.hisn.mygesture.R.drawable.circle_mask);
    }

    public android.graphics.drawable.Drawable a(android.content.Context context, android.graphics.drawable.Drawable drawable, int i) {
        if (this.f880c == null || drawable == null) {
            return drawable;
        }
        if (i < 0) {
            i = this.d / 10;
        }
        return new android.graphics.drawable.BitmapDrawable(context.getResources(), a(a(drawable), null, this.d, this.e, i));
    }

    public boolean a(android.content.Context context, int i, int i2, int i3) {
        android.graphics.drawable.Drawable drawableA = a(context);
        if (drawableA == null) {
            this.f880c = null;
            return false;
        }
        android.graphics.Bitmap bitmapA = a(drawableA);
        this.f880c = bitmapA;
        this.f880c = android.graphics.Bitmap.createScaledBitmap(bitmapA, i2, i3, true);
        this.d = i2;
        this.e = i3;
        return true;
    }
}
