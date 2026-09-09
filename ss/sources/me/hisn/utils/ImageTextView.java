package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class ImageTextView extends android.widget.TextView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.graphics.drawable.Drawable f713a;

    public ImageTextView(android.content.Context context) {
        super(context);
    }

    public ImageTextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public ImageTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(android.content.Context context, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, me.hisn.mygesture.g.ImageTextView);
        this.f713a = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        setCompoundDrawables(this.f713a, null, null, null);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        android.graphics.drawable.Drawable drawable = this.f713a;
        if (drawable != null) {
            drawable.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
        }
    }

    @Override // android.widget.TextView
    public void setTextSize(float f) {
        super.setTextSize(f);
        invalidate();
    }
}
