package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class d extends androidx.recyclerview.widget.RecyclerView.n implements androidx.recyclerview.widget.RecyclerView.s {
    private static final int[] D = {android.R.attr.state_pressed};
    private static final int[] E = new int[0];

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final int f290a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final int f291b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final android.graphics.drawable.StateListDrawable f292c;
    final android.graphics.drawable.Drawable d;
    private final int e;
    private final int f;
    private final android.graphics.drawable.StateListDrawable g;
    private final android.graphics.drawable.Drawable h;
    private final int i;
    private final int j;
    int k;
    int l;
    float m;
    int n;
    int o;
    float p;
    private androidx.recyclerview.widget.RecyclerView s;
    private int q = 0;
    private int r = 0;
    private boolean t = false;
    private boolean u = false;
    private int v = 0;
    private int w = 0;
    private final int[] x = new int[2];
    private final int[] y = new int[2];
    final android.animation.ValueAnimator z = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
    int A = 0;
    private final java.lang.Runnable B = new androidx.recyclerview.widget.d.a();
    private final androidx.recyclerview.widget.RecyclerView.t C = new androidx.recyclerview.widget.d.b();

    class a implements java.lang.Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            androidx.recyclerview.widget.d.this.a(500);
        }
    }

    class b extends androidx.recyclerview.widget.RecyclerView.t {
        b() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.t
        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
            androidx.recyclerview.widget.d.this.a(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
        }
    }

    private class c extends android.animation.AnimatorListenerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private boolean f295a = false;

        c() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            this.f295a = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (this.f295a) {
                this.f295a = false;
                return;
            }
            if (((java.lang.Float) androidx.recyclerview.widget.d.this.z.getAnimatedValue()).floatValue() == 0.0f) {
                androidx.recyclerview.widget.d dVar = androidx.recyclerview.widget.d.this;
                dVar.A = 0;
                dVar.b(0);
            } else {
                androidx.recyclerview.widget.d dVar2 = androidx.recyclerview.widget.d.this;
                dVar2.A = 2;
                dVar2.a();
            }
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.d$d, reason: collision with other inner class name */
    private class C0014d implements android.animation.ValueAnimator.AnimatorUpdateListener {
        C0014d() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            int iFloatValue = (int) (((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            androidx.recyclerview.widget.d.this.f292c.setAlpha(iFloatValue);
            androidx.recyclerview.widget.d.this.d.setAlpha(iFloatValue);
            androidx.recyclerview.widget.d.this.a();
        }
    }

    d(androidx.recyclerview.widget.RecyclerView recyclerView, android.graphics.drawable.StateListDrawable stateListDrawable, android.graphics.drawable.Drawable drawable, android.graphics.drawable.StateListDrawable stateListDrawable2, android.graphics.drawable.Drawable drawable2, int i, int i2, int i3) {
        this.f292c = stateListDrawable;
        this.d = drawable;
        this.g = stateListDrawable2;
        this.h = drawable2;
        this.e = java.lang.Math.max(i, stateListDrawable.getIntrinsicWidth());
        this.f = java.lang.Math.max(i, drawable.getIntrinsicWidth());
        this.i = java.lang.Math.max(i, stateListDrawable2.getIntrinsicWidth());
        this.j = java.lang.Math.max(i, drawable2.getIntrinsicWidth());
        this.f290a = i2;
        this.f291b = i3;
        this.f292c.setAlpha(255);
        this.d.setAlpha(255);
        this.z.addListener(new androidx.recyclerview.widget.d.c());
        this.z.addUpdateListener(new androidx.recyclerview.widget.d.C0014d());
        a(recyclerView);
    }

    private int a(float f, float f2, int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[1] - iArr[0];
        if (i4 == 0) {
            return 0;
        }
        int i5 = i - i3;
        int i6 = (int) (((f2 - f) / i4) * i5);
        int i7 = i2 + i6;
        if (i7 >= i5 || i7 < 0) {
            return 0;
        }
        return i6;
    }

    private void a(float f) {
        int[] iArrE = e();
        float fMax = java.lang.Math.max(iArrE[0], java.lang.Math.min(iArrE[1], f));
        if (java.lang.Math.abs(this.o - fMax) < 2.0f) {
            return;
        }
        int iA = a(this.p, fMax, iArrE, this.s.computeHorizontalScrollRange(), this.s.computeHorizontalScrollOffset(), this.q);
        if (iA != 0) {
            this.s.scrollBy(iA, 0);
        }
        this.p = fMax;
    }

    private void a(android.graphics.Canvas canvas) {
        int i = this.r;
        int i2 = this.i;
        int i3 = i - i2;
        int i4 = this.o;
        int i5 = this.n;
        int i6 = i4 - (i5 / 2);
        this.g.setBounds(0, 0, i5, i2);
        this.h.setBounds(0, 0, this.q, this.j);
        canvas.translate(0.0f, i3);
        this.h.draw(canvas);
        canvas.translate(i6, 0.0f);
        this.g.draw(canvas);
        canvas.translate(-i6, -i3);
    }

    private void b(float f) {
        int[] iArrF = f();
        float fMax = java.lang.Math.max(iArrF[0], java.lang.Math.min(iArrF[1], f));
        if (java.lang.Math.abs(this.l - fMax) < 2.0f) {
            return;
        }
        int iA = a(this.m, fMax, iArrF, this.s.computeVerticalScrollRange(), this.s.computeVerticalScrollOffset(), this.r);
        if (iA != 0) {
            this.s.scrollBy(0, iA);
        }
        this.m = fMax;
    }

    private void b(android.graphics.Canvas canvas) {
        int i = this.q;
        int i2 = this.e;
        int i3 = i - i2;
        int i4 = this.l;
        int i5 = this.k;
        int i6 = i4 - (i5 / 2);
        this.f292c.setBounds(0, 0, i2, i5);
        this.d.setBounds(0, 0, this.f, this.r);
        if (g()) {
            this.d.draw(canvas);
            canvas.translate(this.e, i6);
            canvas.scale(-1.0f, 1.0f);
            this.f292c.draw(canvas);
            canvas.scale(1.0f, 1.0f);
            i3 = this.e;
        } else {
            canvas.translate(i3, 0.0f);
            this.d.draw(canvas);
            canvas.translate(0.0f, i6);
            this.f292c.draw(canvas);
        }
        canvas.translate(-i3, -i6);
    }

    private void c() {
        this.s.removeCallbacks(this.B);
    }

    private void c(int i) {
        c();
        this.s.postDelayed(this.B, i);
    }

    private void d() {
        this.s.b((androidx.recyclerview.widget.RecyclerView.n) this);
        this.s.b((androidx.recyclerview.widget.RecyclerView.s) this);
        this.s.b(this.C);
        c();
    }

    private int[] e() {
        int[] iArr = this.y;
        int i = this.f291b;
        iArr[0] = i;
        iArr[1] = this.q - i;
        return iArr;
    }

    private int[] f() {
        int[] iArr = this.x;
        int i = this.f291b;
        iArr[0] = i;
        iArr[1] = this.r - i;
        return iArr;
    }

    private boolean g() {
        return a.c.e.m.i(this.s) == 1;
    }

    private void h() {
        this.s.a((androidx.recyclerview.widget.RecyclerView.n) this);
        this.s.a((androidx.recyclerview.widget.RecyclerView.s) this);
        this.s.a(this.C);
    }

    void a() {
        this.s.invalidate();
    }

    void a(int i) {
        int i2 = this.A;
        if (i2 == 1) {
            this.z.cancel();
        } else if (i2 != 2) {
            return;
        }
        this.A = 3;
        android.animation.ValueAnimator valueAnimator = this.z;
        valueAnimator.setFloatValues(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
        this.z.setDuration(i);
        this.z.start();
    }

    void a(int i, int i2) {
        int iComputeVerticalScrollRange = this.s.computeVerticalScrollRange();
        int i3 = this.r;
        this.t = iComputeVerticalScrollRange - i3 > 0 && i3 >= this.f290a;
        int iComputeHorizontalScrollRange = this.s.computeHorizontalScrollRange();
        int i4 = this.q;
        boolean z = iComputeHorizontalScrollRange - i4 > 0 && i4 >= this.f290a;
        this.u = z;
        if (!this.t && !z) {
            if (this.v != 0) {
                b(0);
                return;
            }
            return;
        }
        if (this.t) {
            float f = i3;
            this.l = (int) ((f * (i2 + (f / 2.0f))) / iComputeVerticalScrollRange);
            this.k = java.lang.Math.min(i3, (i3 * i3) / iComputeVerticalScrollRange);
        }
        if (this.u) {
            float f2 = i4;
            this.o = (int) ((f2 * (i + (f2 / 2.0f))) / iComputeHorizontalScrollRange);
            this.n = java.lang.Math.min(i4, (i4 * i4) / iComputeHorizontalScrollRange);
        }
        int i5 = this.v;
        if (i5 == 0 || i5 == 1) {
            b(1);
        }
    }

    public void a(androidx.recyclerview.widget.RecyclerView recyclerView) {
        androidx.recyclerview.widget.RecyclerView recyclerView2 = this.s;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            d();
        }
        this.s = recyclerView;
        if (recyclerView != null) {
            h();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.s
    public void a(boolean z) {
    }

    boolean a(float f, float f2) {
        if (f2 >= this.r - this.i) {
            int i = this.o;
            int i2 = this.n;
            if (f >= i - (i2 / 2) && f <= i + (i2 / 2)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.s
    public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
        int i = this.v;
        if (i == 1) {
            boolean zB = b(motionEvent.getX(), motionEvent.getY());
            boolean zA = a(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!zB && !zA) {
                return false;
            }
            if (zA) {
                this.w = 1;
                this.p = (int) motionEvent.getX();
            } else if (zB) {
                this.w = 2;
                this.m = (int) motionEvent.getY();
            }
            b(2);
        } else if (i != 2) {
            return false;
        }
        return true;
    }

    public void b() {
        int i = this.A;
        if (i != 0) {
            if (i != 3) {
                return;
            } else {
                this.z.cancel();
            }
        }
        this.A = 1;
        android.animation.ValueAnimator valueAnimator = this.z;
        valueAnimator.setFloatValues(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        this.z.setDuration(500L);
        this.z.setStartDelay(0L);
        this.z.start();
    }

    void b(int i) {
        int i2;
        if (i == 2 && this.v != 2) {
            this.f292c.setState(D);
            c();
        }
        if (i == 0) {
            a();
        } else {
            b();
        }
        if (this.v != 2 || i == 2) {
            i2 = i == 1 ? 1500 : 1200;
            this.v = i;
        }
        this.f292c.setState(E);
        c(i2);
        this.v = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.n
    public void b(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (this.q != this.s.getWidth() || this.r != this.s.getHeight()) {
            this.q = this.s.getWidth();
            this.r = this.s.getHeight();
            b(0);
        } else if (this.A != 0) {
            if (this.t) {
                b(canvas);
            }
            if (this.u) {
                a(canvas);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.s
    public void b(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
        if (this.v == 0) {
            return;
        }
        if (motionEvent.getAction() == 0) {
            boolean zB = b(motionEvent.getX(), motionEvent.getY());
            boolean zA = a(motionEvent.getX(), motionEvent.getY());
            if (zB || zA) {
                if (zA) {
                    this.w = 1;
                    this.p = (int) motionEvent.getX();
                } else if (zB) {
                    this.w = 2;
                    this.m = (int) motionEvent.getY();
                }
                b(2);
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 1 && this.v == 2) {
            this.m = 0.0f;
            this.p = 0.0f;
            b(1);
            this.w = 0;
            return;
        }
        if (motionEvent.getAction() == 2 && this.v == 2) {
            b();
            if (this.w == 1) {
                a(motionEvent.getX());
            }
            if (this.w == 2) {
                b(motionEvent.getY());
            }
        }
    }

    boolean b(float f, float f2) {
        if (!g() ? f >= this.q - this.e : f <= this.e / 2) {
            int i = this.l;
            int i2 = this.k;
            if (f2 >= i - (i2 / 2) && f2 <= i + (i2 / 2)) {
                return true;
            }
        }
        return false;
    }
}
