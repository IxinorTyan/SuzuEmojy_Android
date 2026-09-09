package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class DrawPadView extends android.view.View {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.graphics.Paint f706a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.graphics.Canvas f707b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.graphics.Bitmap f708c;
    private android.graphics.Path d;
    private boolean e;
    java.util.List<me.hisn.utils.DrawPadView.a> f;
    private android.graphics.Matrix g;

    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        float f709a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        float f710b;

        public a(me.hisn.utils.DrawPadView drawPadView, float f, float f2) {
            this.f709a = f;
            this.f710b = f2;
        }
    }

    public DrawPadView(android.content.Context context) {
        super(context);
        this.e = false;
        this.f = new java.util.ArrayList();
        a(context);
    }

    public DrawPadView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = false;
        this.f = new java.util.ArrayList();
        a(context);
    }

    private void a(float f, float f2) {
        this.f.add(new me.hisn.utils.DrawPadView.a(this, f, f2));
    }

    private void a(android.content.Context context) {
        android.graphics.Paint paint = new android.graphics.Paint(5);
        this.f706a = paint;
        paint.setStrokeWidth(10.0f);
        this.f706a.setPathEffect(new android.graphics.CornerPathEffect(200.0f));
        this.f706a.setColor(-65536);
        this.f706a.setStyle(android.graphics.Paint.Style.STROKE);
        this.f706a.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        this.g = new android.graphics.Matrix();
    }

    private void c() {
        this.f708c = android.graphics.Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        this.f707b = new android.graphics.Canvas(this.f708c);
    }

    public void a() {
        this.f708c = null;
        this.e = false;
        invalidate();
    }

    public boolean b() {
        return this.e;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        android.graphics.Matrix matrix;
        android.graphics.Bitmap bitmap = this.f708c;
        if (bitmap == null || (matrix = this.g) == null) {
            return;
        }
        canvas.drawBitmap(bitmap, matrix, null);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x006f  */
    /* JADX WARN: Code duplicated, block: B:15:0x0077  */
    /* JADX WARN: Code duplicated, block: B:19:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:21:0x00b1 A[LOOP:0: B:20:0x00af->B:21:0x00b1, LOOP_END] */
    @Override // android.view.View
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int size;
        me.hisn.utils.DrawPadView.a aVar;
        float f;
        float f2;
        int i;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            if (this.d == null) {
                this.d = new android.graphics.Path();
            }
            if (this.f708c == null) {
                c();
            }
            this.d.moveTo(x, y);
            a(x, y);
        } else if (action == 1) {
            size = this.f.size();
            if (size >= 4) {
                me.hisn.utils.DrawPadView.a aVar2 = this.f.get(size - 1);
                aVar = this.f.get(size - 2);
                f = (aVar2.f709a - aVar.f709a) * 1.5f;
                f2 = (aVar2.f710b - aVar.f710b) * 1.5f;
                if (java.lang.Math.abs(f) <= 10.0f || java.lang.Math.abs(f2) > 10.0f) {
                    for (i = 0; i < 10; i++) {
                        float f3 = i / 10;
                        this.f706a.setStrokeWidth((1.0f - f3) * 10.0f);
                        this.d.lineTo(aVar.f709a + (f * f3), aVar.f710b + (f3 * f2));
                        this.f707b.drawPath(this.d, this.f706a);
                    }
                    invalidate();
                    this.f706a.setStrokeWidth(10.0f);
                }
            }
            this.f.clear();
            this.d.reset();
        } else if (action == 2) {
            java.util.List<me.hisn.utils.DrawPadView.a> list = this.f;
            me.hisn.utils.DrawPadView.a aVar3 = list.get(list.size() - 1);
            a((aVar3.f709a + x) / 2.0f, (aVar3.f710b + y) / 2.0f);
            a(x, y);
            int size2 = this.f.size();
            if (size2 >= 4) {
                me.hisn.utils.DrawPadView.a aVar4 = this.f.get(size2 - 2);
                me.hisn.utils.DrawPadView.a aVar5 = this.f.get(size2 - 3);
                this.d.quadTo(aVar5.f709a, aVar5.f710b, aVar4.f709a, aVar4.f710b);
                this.f707b.drawPath(this.d, this.f706a);
                invalidate();
                this.e = true;
            }
        } else if (action == 3) {
            size = this.f.size();
            if (size >= 4) {
                me.hisn.utils.DrawPadView.a aVar6 = this.f.get(size - 1);
                aVar = this.f.get(size - 2);
                f = (aVar6.f709a - aVar.f709a) * 1.5f;
                f2 = (aVar6.f710b - aVar.f710b) * 1.5f;
                if (java.lang.Math.abs(f) <= 10.0f) {
                    while (i < 10) {
                        float f4 = i / 10;
                        this.f706a.setStrokeWidth((1.0f - f4) * 10.0f);
                        this.d.lineTo(aVar.f709a + (f * f4), aVar.f710b + (f4 * f2));
                        this.f707b.drawPath(this.d, this.f706a);
                    }
                    invalidate();
                    this.f706a.setStrokeWidth(10.0f);
                } else {
                    while (i < 10) {
                        float f5 = i / 10;
                        this.f706a.setStrokeWidth((1.0f - f5) * 10.0f);
                        this.d.lineTo(aVar.f709a + (f * f5), aVar.f710b + (f5 * f2));
                        this.f707b.drawPath(this.d, this.f706a);
                    }
                    invalidate();
                    this.f706a.setStrokeWidth(10.0f);
                }
            }
            this.f.clear();
            this.d.reset();
        }
        return true;
    }
}
