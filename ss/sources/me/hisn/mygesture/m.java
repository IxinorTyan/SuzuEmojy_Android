package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class m extends android.view.View {
    static android.graphics.Bitmap q;
    private static final int r;
    private static final int s;
    private static final int t;
    private static final int u;
    private static final int v;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.util.ArrayList<me.hisn.mygesture.n> f618a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f619b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int f620c;
    private android.graphics.Paint d;
    private android.graphics.Paint e;
    private android.graphics.Paint f;
    private android.graphics.Paint g;
    private android.graphics.Paint h;
    private android.graphics.Paint i;
    private android.graphics.Paint j;
    private android.graphics.Path k;
    private int l;
    private float[] m;
    private int n;
    private int[] o;
    private android.graphics.Point p;

    static {
        int iMin = java.lang.Math.min(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0);
        r = iMin;
        s = (int) (iMin / 2.5f);
        t = iMin / 12;
        u = iMin / 14;
        v = (int) ((iMin * 3.0f) / 5.0f);
    }

    public m(android.content.Context context) {
        this(context, null);
    }

    public m(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.l = 0;
        this.o = new int[2];
        this.p = new android.graphics.Point();
        this.f618a = new java.util.ArrayList<>();
    }

    private int a(int i) {
        int i2 = v;
        if (i > i2) {
            return i2;
        }
        return i < i2 * (-1) ? i2 * (-1) : i;
    }

    private android.graphics.Paint a(int i, boolean z) {
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setAntiAlias(true);
        if (z) {
            paint.setStyle(android.graphics.Paint.Style.STROKE);
            paint.setStrokeWidth(3.0f);
        } else {
            paint.setStyle(android.graphics.Paint.Style.FILL);
        }
        if (i != 0) {
            paint.setColor(i);
        }
        return paint;
    }

    private void a(int i, float f) {
        if (i == 0) {
            float[] fArr = this.m;
            int i2 = this.f619b;
            fArr[0] = i2 - 3.0f;
            int i3 = this.f620c;
            int i4 = s;
            fArr[1] = i3 - i4;
            int i5 = this.n;
            fArr[2] = i2 + ((i5 / 400) * f);
            int i6 = t;
            int[] iArr = this.o;
            fArr[3] = (i3 - i6) + iArr[1];
            fArr[4] = i2 + ((i5 / 7) * f);
            int i7 = u;
            fArr[5] = (i3 - i7) + iArr[1];
            fArr[6] = i2 + ((i5 / 7) * f);
            fArr[7] = iArr[1] + i3;
            fArr[8] = i2 + ((i5 / 7) * f);
            fArr[9] = i7 + i3 + iArr[1];
            fArr[10] = i2 + ((i5 / 400) * f);
            fArr[11] = i6 + i3 + iArr[1];
            fArr[12] = i2 - 3.0f;
            fArr[13] = i3 + i4;
        } else if (i == 1) {
            float[] fArr2 = this.m;
            int i8 = this.f619b;
            fArr2[0] = i8 + 3.0f;
            int i9 = this.f620c;
            int i10 = s;
            fArr2[1] = i9 - i10;
            int i11 = this.n;
            fArr2[2] = i8 - ((i11 / 400) * f);
            int i12 = t;
            int[] iArr2 = this.o;
            fArr2[3] = (i9 - i12) + iArr2[1];
            fArr2[4] = i8 - ((i11 / 7) * f);
            int i13 = u;
            fArr2[5] = (i9 - i13) + iArr2[1];
            fArr2[6] = i8 - ((i11 / 7) * f);
            fArr2[7] = iArr2[1] + i9;
            fArr2[8] = i8 - ((i11 / 7) * f);
            fArr2[9] = i13 + i9 + iArr2[1];
            fArr2[10] = i8 - ((i11 / 400) * f);
            fArr2[11] = i12 + i9 + iArr2[1];
            fArr2[12] = i8 + 3.0f;
            fArr2[13] = i9 + i10;
        } else if (i == 2) {
            float[] fArr3 = this.m;
            int i14 = this.f619b;
            int i15 = s;
            fArr3[0] = i14 - i15;
            int i16 = this.f620c;
            fArr3[1] = i16 + 3.0f;
            int i17 = t;
            int[] iArr3 = this.o;
            fArr3[2] = (i14 - i17) + iArr3[0];
            int i18 = this.n;
            fArr3[3] = i16 - ((i18 / 400) * f);
            int i19 = u;
            fArr3[4] = (i14 - i19) + iArr3[0];
            fArr3[5] = i16 - ((i18 / 7) * f);
            fArr3[6] = iArr3[0] + i14;
            fArr3[7] = i16 - ((i18 / 7) * f);
            fArr3[8] = i19 + i14 + iArr3[0];
            fArr3[9] = i16 - ((i18 / 7) * f);
            fArr3[10] = i17 + i14 + iArr3[0];
            fArr3[11] = i16 - ((i18 / 400) * f);
            fArr3[12] = i14 + i15;
            fArr3[13] = i16 + 3.0f;
        }
        this.k.reset();
        android.graphics.Path path = this.k;
        float[] fArr4 = this.m;
        path.moveTo(fArr4[0], fArr4[1]);
        android.graphics.Path path2 = this.k;
        float[] fArr5 = this.m;
        path2.cubicTo(fArr5[2], fArr5[3], fArr5[4], fArr5[5], fArr5[6], fArr5[7]);
        android.graphics.Path path3 = this.k;
        float[] fArr6 = this.m;
        path3.cubicTo(fArr6[8], fArr6[9], fArr6[10], fArr6[11], fArr6[12], fArr6[13]);
    }

    private void a(android.graphics.Canvas canvas) {
        int i = this.n;
        int i2 = v;
        if (i > i2) {
            this.n = i2;
        }
        if (this.k == null) {
            this.k = new android.graphics.Path();
        }
        a(this.l, 1.0f);
        int i3 = me.hisn.mygesture.P.w;
        switch (i3) {
            case 5:
            case 6:
                if (this.d == null) {
                    c();
                    d();
                }
                canvas.drawPath(this.k, this.d);
                canvas.drawPath(this.k, this.e);
                break;
            case 7:
            case 8:
            case 9:
                boolean z = i3 == 8 || i3 == 9;
                if (this.f == null) {
                    if (z) {
                        int[] iArrA = me.hisn.utils.k.a(me.hisn.mygesture.P.T, -16777216);
                        boolean z2 = me.hisn.mygesture.P.w == 8;
                        this.f = a(iArrA[0], z2);
                        this.g = a(iArrA[1], z2);
                        this.h = a(iArrA[2], z2);
                        this.i = a(iArrA[3], z2);
                        this.j = a(iArrA[4], z2);
                    } else {
                        this.f = a(me.hisn.mygesture.P.T, true);
                    }
                }
                canvas.drawPath(this.k, this.f);
                a(this.l, 0.8f);
                canvas.drawPath(this.k, z ? this.g : this.f);
                a(this.l, 0.6f);
                canvas.drawPath(this.k, z ? this.h : this.f);
                a(this.l, 0.4f);
                canvas.drawPath(this.k, z ? this.i : this.f);
                a(this.l, 0.2f);
                canvas.drawPath(this.k, z ? this.j : this.f);
                break;
        }
    }

    private void b(android.graphics.Canvas canvas) {
        int i = this.n;
        if (i > 0) {
            int i2 = (i * 8) / me.hisn.mygesture.P.k0;
            if (i2 > 3) {
                i2 = 3;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                me.hisn.mygesture.n nVar = this.f618a.get(i3);
                int i4 = 5 - i3;
                int i5 = ((i4 * i4) * this.n) / 100;
                nVar.f623c = i5;
                nVar.f621a.setStrokeWidth(i5 / 7);
                canvas.drawCircle(this.f619b, this.f620c, nVar.f623c, nVar.f621a);
            }
        }
    }

    private void c() {
        android.graphics.Paint paint = new android.graphics.Paint();
        this.d = paint;
        paint.setAntiAlias(true);
        this.d.setStyle(android.graphics.Paint.Style.FILL);
        this.d.setColor(me.hisn.mygesture.P.T);
    }

    private void d() {
        android.graphics.Paint paint = new android.graphics.Paint();
        this.e = paint;
        paint.setAntiAlias(true);
        this.e.setStyle(android.graphics.Paint.Style.STROKE);
        this.e.setStrokeWidth(3.0f);
        this.e.setColor(1436129689);
    }

    void a() {
        setSize(0);
        setVisibility(0);
        this.f618a.clear();
        for (int i = 0; i < 3; i++) {
            this.f618a.add(new me.hisn.mygesture.n());
        }
    }

    void a(int i, int i2) {
        switch (me.hisn.mygesture.P.w) {
            case 5:
                int[] iArr = this.o;
                iArr[0] = 0;
                iArr[1] = 0;
                if (java.lang.Math.abs(i) <= java.lang.Math.abs(i2)) {
                    this.o[1] = (int) (a(i2) * 0.25f);
                } else {
                    this.o[0] = (int) (a(i) * 0.25f);
                }
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                this.o[0] = (int) (a(i) * 0.25f);
                this.o[1] = (int) (a(i2) * 0.25f);
                break;
        }
        android.graphics.Point point = this.p;
        point.x = i;
        point.y = i2;
    }

    void a(int i, int i2, int i3) {
        int i4;
        if (i3 != me.hisn.mygesture.P.L) {
            if (i3 == me.hisn.mygesture.P.R) {
                this.l = 1;
                i4 = me.hisn.mygesture.P.k0;
            } else if (i3 == me.hisn.mygesture.P.B) {
                this.l = 2;
                this.f619b = i;
                this.f620c = me.hisn.mygesture.P.l0;
            }
            this.m = new float[14];
            setAlpha(1.0f);
        }
        i4 = 0;
        this.l = 0;
        this.f619b = i4;
        this.f620c = i2;
        this.m = new float[14];
        setAlpha(1.0f);
    }

    void b() {
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.k = null;
    }

    android.graphics.Point getCurrentPoint() {
        return this.p;
    }

    int getSize() {
        return this.n;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        switch (me.hisn.mygesture.P.w) {
            case 3:
            case 4:
                b(canvas);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                a(canvas);
                break;
        }
    }

    void setSize(int i) {
        this.n = i;
    }
}
