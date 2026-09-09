package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class HiImageView extends android.widget.ImageView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.graphics.Paint f711a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f712b;

    public HiImageView(android.content.Context context) {
        super(context);
        a(context, null, 0);
    }

    public HiImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet, 0);
    }

    public HiImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet, i);
    }

    private android.graphics.Bitmap a(android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
        canvas.translate(0.0f, getPaddingTop());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private android.graphics.Bitmap a(android.graphics.drawable.Drawable drawable, boolean z) {
        if (drawable == null) {
            return null;
        }
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
        if (z) {
            canvas.drawColor(-1);
        }
        drawable.draw(canvas);
        return android.graphics.Bitmap.createScaledBitmap(bitmapCreateBitmap, getWidth(), getHeight(), true);
    }

    private void a(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this.f711a = new android.graphics.Paint(5);
        android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, me.hisn.mygesture.g.HiImageView, i, 0);
        this.f712b = typedArrayObtainStyledAttributes.hasValue(0) ? typedArrayObtainStyledAttributes.getInt(0, 0) : 0;
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        android.graphics.Bitmap bitmapA;
        android.content.res.Resources resources;
        int i;
        float dimensionPixelSize;
        android.graphics.drawable.Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            return;
        }
        int i2 = this.f712b;
        if (i2 == 0) {
            super.onDraw(canvas);
            return;
        }
        if (i2 == 2 || i2 == 3) {
            bitmapA = a(drawable);
        } else {
            bitmapA = i2 == 4 ? a(drawable, false) : a(drawable, true);
        }
        android.graphics.Paint paint = this.f711a;
        android.graphics.Shader.TileMode tileMode = android.graphics.Shader.TileMode.CLAMP;
        paint.setShader(new android.graphics.BitmapShader(bitmapA, tileMode, tileMode));
        int i3 = this.f712b;
        if (i3 == 1 || i3 == 4) {
            int i4 = this.f712b;
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() / 2) - 5, this.f711a);
            return;
        }
        if (i3 == 2 || i3 == 3 || i3 == 5) {
            int i5 = this.f712b;
            if (i5 == 2) {
                dimensionPixelSize = getResources().getDimensionPixelSize(me.hisn.mygesture.R.dimen.round_radius) + 2.0f;
            } else {
                if (i5 == 5) {
                    resources = getResources();
                    i = me.hisn.mygesture.R.dimen.capture_phone_radius;
                } else {
                    resources = getResources();
                    i = me.hisn.mygesture.R.dimen.phone_radius;
                }
                dimensionPixelSize = resources.getDimensionPixelSize(i);
            }
            float f = dimensionPixelSize;
            canvas.drawRoundRect(0.0f, 0.0f, getWidth(), getHeight(), f, f, this.f711a);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public void setType(int i) {
        this.f712b = i;
    }
}
