package androidx.core.graphics.drawable;

/* JADX INFO: loaded from: classes.dex */
public abstract class b extends android.graphics.drawable.Drawable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final android.graphics.Bitmap f173a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f174b;
    private final android.graphics.BitmapShader e;
    private float g;
    private boolean k;
    private int l;
    private int m;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int f175c = 119;
    private final android.graphics.Paint d = new android.graphics.Paint(3);
    private final android.graphics.Matrix f = new android.graphics.Matrix();
    final android.graphics.Rect h = new android.graphics.Rect();
    private final android.graphics.RectF i = new android.graphics.RectF();
    private boolean j = true;

    b(android.content.res.Resources resources, android.graphics.Bitmap bitmap) {
        android.graphics.BitmapShader bitmapShader;
        this.f174b = 160;
        if (resources != null) {
            this.f174b = resources.getDisplayMetrics().densityDpi;
        }
        this.f173a = bitmap;
        if (bitmap != null) {
            c();
            android.graphics.Bitmap bitmap2 = this.f173a;
            android.graphics.Shader.TileMode tileMode = android.graphics.Shader.TileMode.CLAMP;
            bitmapShader = new android.graphics.BitmapShader(bitmap2, tileMode, tileMode);
        } else {
            this.m = -1;
            this.l = -1;
            bitmapShader = null;
        }
        this.e = bitmapShader;
    }

    private static boolean b(float f) {
        return f > 0.05f;
    }

    private void c() {
        this.l = this.f173a.getScaledWidth(this.f174b);
        this.m = this.f173a.getScaledHeight(this.f174b);
    }

    private void d() {
        this.g = java.lang.Math.min(this.m, this.l) / 2;
    }

    public float a() {
        return this.g;
    }

    public void a(float f) {
        android.graphics.Paint paint;
        android.graphics.BitmapShader bitmapShader;
        if (this.g == f) {
            return;
        }
        this.k = false;
        if (b(f)) {
            paint = this.d;
            bitmapShader = this.e;
        } else {
            paint = this.d;
            bitmapShader = null;
        }
        paint.setShader(bitmapShader);
        this.g = f;
        invalidateSelf();
    }

    abstract void a(int i, int i2, int i3, android.graphics.Rect rect, android.graphics.Rect rect2);

    void b() {
        if (this.j) {
            if (this.k) {
                int iMin = java.lang.Math.min(this.l, this.m);
                a(this.f175c, iMin, iMin, getBounds(), this.h);
                int iMin2 = java.lang.Math.min(this.h.width(), this.h.height());
                this.h.inset(java.lang.Math.max(0, (this.h.width() - iMin2) / 2), java.lang.Math.max(0, (this.h.height() - iMin2) / 2));
                this.g = iMin2 * 0.5f;
            } else {
                a(this.f175c, this.l, this.m, getBounds(), this.h);
            }
            this.i.set(this.h);
            if (this.e != null) {
                android.graphics.Matrix matrix = this.f;
                android.graphics.RectF rectF = this.i;
                matrix.setTranslate(rectF.left, rectF.top);
                this.f.preScale(this.i.width() / this.f173a.getWidth(), this.i.height() / this.f173a.getHeight());
                this.e.setLocalMatrix(this.f);
                this.d.setShader(this.e);
            }
            this.j = false;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.Bitmap bitmap = this.f173a;
        if (bitmap == null) {
            return;
        }
        b();
        if (this.d.getShader() == null) {
            canvas.drawBitmap(bitmap, (android.graphics.Rect) null, this.h, this.d);
            return;
        }
        android.graphics.RectF rectF = this.i;
        float f = this.g;
        canvas.drawRoundRect(rectF, f, f, this.d);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.d.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.d.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.m;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.l;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        android.graphics.Bitmap bitmap;
        return (this.f175c != 119 || this.k || (bitmap = this.f173a) == null || bitmap.hasAlpha() || this.d.getAlpha() < 255 || b(this.g)) ? -3 : -1;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        super.onBoundsChange(rect);
        if (this.k) {
            d();
        }
        this.j = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.d.getAlpha()) {
            this.d.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.d.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.d.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.d.setFilterBitmap(z);
        invalidateSelf();
    }
}
