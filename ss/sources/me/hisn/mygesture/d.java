package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class d extends me.hisn.mygesture.e {
    private static final int j;
    private static final int k;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.widget.ImageView f569c;
    private android.view.WindowManager.LayoutParams d;
    private boolean e;
    private boolean f;
    private int g;
    private int h;
    private int i;

    class a implements android.animation.ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f570a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f571b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ float f572c;
        final /* synthetic */ float d;

        a(int i, int i2, float f, float f2) {
            this.f570a = i;
            this.f571b = i2;
            this.f572c = f;
            this.d = f2;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            float fFloatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
            me.hisn.mygesture.d.this.d.x = (int) (me.hisn.mygesture.d.this.g + (this.f570a * fFloatValue));
            me.hisn.mygesture.d.this.d.y = (int) (me.hisn.mygesture.d.this.h + (this.f571b * fFloatValue));
            me.hisn.mygesture.d.this.f569c.setAlpha(fFloatValue);
            try {
                me.hisn.mygesture.d.this.f576b.updateViewLayout(me.hisn.mygesture.d.this.f569c, me.hisn.mygesture.d.this.d);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            if (me.hisn.mygesture.d.this.f) {
                me.hisn.mygesture.d.this.f569c.setRotation(this.f572c - (this.d * (1.0f - fFloatValue)));
            }
            if (fFloatValue == 0.0f) {
                try {
                    me.hisn.mygesture.d.this.f569c.setVisibility(8);
                    me.hisn.mygesture.d.this.f576b.removeView(me.hisn.mygesture.d.this.f569c);
                } catch (java.lang.Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.graphics.Bitmap f573a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.content.Context f574b;

        b(android.graphics.Bitmap bitmap, android.content.Context context) {
            this.f573a = bitmap;
            this.f574b = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.mypanel.c().a(this.f573a, me.hisn.mygesture.d.b(this.f574b), me.hisn.mygesture.d.j, true);
        }
    }

    static {
        int iMin = java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) / 6;
        j = iMin;
        k = iMin / 2;
    }

    public d(android.content.Context context, android.view.WindowManager windowManager) {
        super(context, windowManager);
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        d();
    }

    public static android.graphics.drawable.Drawable a(android.content.Context context) {
        java.io.File file = new java.io.File(b(context));
        android.graphics.drawable.Drawable drawableCreateFromPath = file.exists() ? android.graphics.drawable.Drawable.createFromPath(file.getPath()) : null;
        return drawableCreateFromPath == null ? context.getDrawable(me.hisn.mygesture.R.mipmap.mg_logo_192_r) : drawableCreateFromPath;
    }

    public static void a(android.content.Context context, android.graphics.Bitmap bitmap) {
        new java.lang.Thread(new me.hisn.mygesture.d.b(bitmap, context)).start();
    }

    public static java.lang.String b(android.content.Context context) {
        return context.getExternalFilesDir(null) + "/31421.png";
    }

    private void d() {
        this.f = me.hisn.mygesture.P.s.getBoolean("31422", true);
        android.widget.ImageView imageView = new android.widget.ImageView(this.f575a);
        this.f569c = imageView;
        imageView.setImageDrawable(a(this.f575a));
        me.hisn.utils.v vVar = new me.hisn.utils.v();
        int i = j;
        this.d = vVar.a(false, 8388659, i, i, this.g, this.h, 0, false);
    }

    @Override // me.hisn.mygesture.e
    public void a() {
        if (this.e) {
            int i = this.i;
            if (i > 0) {
                android.view.WindowManager.LayoutParams layoutParams = this.d;
                int i2 = layoutParams.x - this.g;
                int i3 = layoutParams.y - this.h;
                float f = (float) ((((double) ((i == 1 ? i2 : i3) * 360.0f)) / 3.141592653589793d) / ((double) j));
                this.i = 0;
                float rotation = this.f569c.getRotation();
                android.animation.ValueAnimator valueAnimatorOfFloat = android.animation.ValueAnimator.ofFloat(1.0f, 0.0f);
                valueAnimatorOfFloat.addUpdateListener(new me.hisn.mygesture.d.a(i2, i3, rotation, f));
                valueAnimatorOfFloat.setDuration(300L);
                valueAnimatorOfFloat.start();
            } else {
                try {
                    this.f576b.removeView(this.f569c);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
            this.e = false;
        }
    }

    @Override // me.hisn.mygesture.e
    public void a(int i, int i2, int i3) {
        int i4;
        try {
            if (i3 != me.hisn.mygesture.P.L) {
                if (i3 == me.hisn.mygesture.P.R) {
                    i4 = me.hisn.mygesture.P.k0 - (j / 2);
                } else if (i3 == me.hisn.mygesture.P.B) {
                    this.g = (i - (j / 2)) - me.hisn.mygesture.MAS.h();
                    this.h = (me.hisn.mygesture.P.l0 - (j / 2)) - me.hisn.mygesture.MAS.j();
                }
                android.view.WindowManager.LayoutParams layoutParams = this.d;
                layoutParams.x = this.g;
                layoutParams.y = this.h;
                this.f576b.addView(this.f569c, layoutParams);
                this.f569c.setAlpha(1.0f);
                this.f569c.setVisibility(0);
                this.e = true;
                return;
            }
            i4 = 0 - (j / 2);
            this.f576b.addView(this.f569c, layoutParams);
            this.f569c.setAlpha(1.0f);
            this.f569c.setVisibility(0);
            this.e = true;
            return;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return;
        }
        this.g = i4 - me.hisn.mygesture.MAS.h();
        this.h = (i2 - (j / 2)) - me.hisn.mygesture.MAS.j();
        android.view.WindowManager.LayoutParams layoutParams2 = this.d;
        layoutParams2.x = this.g;
        layoutParams2.y = this.h;
    }

    @Override // me.hisn.mygesture.e
    public void a(int i, int i2, int i3, int i4) {
        int i5;
        if (this.e) {
            if (me.hisn.mygesture.P.D) {
                if (this.i == 0 && i < me.hisn.mygesture.P.n0) {
                    return;
                }
                if (this.i == 0) {
                    this.i = java.lang.Math.abs(i2) > java.lang.Math.abs(i3) ? 1 : 2;
                }
            } else if (i4 == 0) {
                return;
            } else {
                this.i = i4;
            }
            int i6 = i2 / 4;
            int i7 = i3 / 4;
            int i8 = this.i;
            if (i8 == 1) {
                i5 = i6 >= 0 ? 1 : -1;
                int iAbs = java.lang.Math.abs(i6);
                int i9 = k;
                if (iAbs > i9) {
                    i6 = i5 * i9;
                }
                this.d.x = this.g + i6;
            } else if (i8 == 2) {
                i5 = i7 >= 0 ? 1 : -1;
                int iAbs2 = java.lang.Math.abs(i7);
                int i10 = k;
                if (iAbs2 > i10) {
                    i7 = i5 * i10;
                }
                this.d.y = this.h + i7;
            }
            if (this.f) {
                if (this.i != 1) {
                    i2 = i3;
                }
                this.f569c.setRotation((float) ((((double) (i2 * 90.0f)) / 3.141592653589793d) / ((double) j)));
            }
            try {
                this.f576b.updateViewLayout(this.f569c, this.d);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            if (me.hisn.mygesture.P.C == null) {
                me.hisn.mygesture.m.q = new me.hisn.mypanel.c().a();
            }
        }
    }

    @Override // me.hisn.mygesture.e
    public void b() {
        if (this.e) {
            try {
                this.f569c.setVisibility(8);
                this.f576b.removeView(this.f569c);
                this.e = false;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }
}
