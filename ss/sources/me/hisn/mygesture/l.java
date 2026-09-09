package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
class l extends me.hisn.mygesture.e {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private me.hisn.mygesture.m f613c;
    private android.view.WindowManager.LayoutParams d;
    private boolean e;

    class a implements android.animation.ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f614a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.graphics.Point f615b;

        a(int i, android.graphics.Point point) {
            this.f614a = i;
            this.f615b = point;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            int i = me.hisn.mygesture.P.w;
            java.lang.Object animatedValue = valueAnimator.getAnimatedValue();
            if (i < 5) {
                int iIntValue = ((java.lang.Integer) animatedValue).intValue();
                me.hisn.mygesture.l.this.a(iIntValue, false, 0, 0);
                me.hisn.mygesture.l.this.f613c.setAlpha(1.0f - (iIntValue / (this.f614a * 1.6f)));
            } else {
                float fFloatValue = ((java.lang.Float) animatedValue).floatValue();
                me.hisn.mygesture.l lVar = me.hisn.mygesture.l.this;
                int i2 = (int) (this.f614a * fFloatValue);
                android.graphics.Point point = this.f615b;
                lVar.a(i2, false, (int) (point.x * fFloatValue), (int) (point.y * fFloatValue));
            }
        }
    }

    class b implements android.animation.Animator.AnimatorListener {
        b() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            me.hisn.mygesture.l.this.f();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }
    }

    public l(android.content.Context context, android.view.WindowManager windowManager) {
        super(context, windowManager);
        e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, boolean z, int i2, int i3) {
        if (z && me.hisn.mygesture.P.w < 5) {
            this.f613c.setAlpha((i * 2.0f) / me.hisn.mygesture.P.k0);
        }
        this.f613c.setSize(i);
        if (me.hisn.mygesture.P.w >= 5) {
            this.f613c.a(i2, i3);
        }
        this.f613c.invalidate();
    }

    private void a(boolean z) {
        if (this.e) {
            int size = this.f613c.getSize();
            if (size < 20 || !z) {
                f();
                return;
            }
            android.graphics.Point currentPoint = this.f613c.getCurrentPoint();
            android.animation.ValueAnimator valueAnimatorOfInt = me.hisn.mygesture.P.w < 5 ? android.animation.ValueAnimator.ofInt(size, (int) (size * 1.6f)) : android.animation.ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfInt.setDuration(200L);
            valueAnimatorOfInt.addUpdateListener(new me.hisn.mygesture.l.a(size, currentPoint));
            valueAnimatorOfInt.addListener(new me.hisn.mygesture.l.b());
            valueAnimatorOfInt.start();
        }
    }

    private int[] d() {
        int i;
        int i2;
        int i3;
        int iJ;
        if (me.hisn.mygesture.MAS.h() >= 0) {
            i = 8388611;
            i2 = me.hisn.mygesture.MAS.h();
        } else {
            i = 8388613;
            i2 = me.hisn.mygesture.MAS.i();
        }
        int i4 = 0 - i2;
        if (me.hisn.mygesture.MAS.g() >= 0) {
            i3 = 80;
            iJ = me.hisn.mygesture.MAS.g();
        } else {
            i3 = 48;
            iJ = me.hisn.mygesture.MAS.j();
        }
        return new int[]{i | i3, i4, 0 - iJ};
    }

    private void e() {
        this.f613c = new me.hisn.mygesture.m(this.f575a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        try {
            this.f613c.setVisibility(8);
            try {
                this.f576b.removeView(this.f613c);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            this.e = false;
            this.f613c.b();
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // me.hisn.mygesture.e
    public void a() {
        a(true);
    }

    @Override // me.hisn.mygesture.e
    public void a(int i, int i2, int i3) {
        b(i, i2, i3);
    }

    @Override // me.hisn.mygesture.e
    public void a(int i, int i2, int i3, int i4) {
        if (this.e) {
            a(i, true, i2, i3);
        }
    }

    @Override // me.hisn.mygesture.e
    public void b() {
        a(false);
    }

    void b(int i, int i2, int i3) {
        if (this.e) {
            try {
                this.f576b.removeView(this.f613c);
                this.e = false;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        c();
        this.f613c.a(i, i2, i3);
        this.f613c.a();
        try {
            this.f576b.addView(this.f613c, this.d);
            this.e = true;
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
        if (me.hisn.mygesture.P.N == null) {
            me.hisn.mygesture.m.q = new me.hisn.mypanel.c().a();
        }
    }

    void c() {
        int[] iArrD = d();
        this.d = new me.hisn.utils.v().a(false, iArrD[0], me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0, iArrD[1], iArrD[2], 0, false);
    }
}
