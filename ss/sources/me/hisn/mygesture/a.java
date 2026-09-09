package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.view.View f546a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.view.View f547b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.view.View f548c;
    private android.view.View d;
    private final android.view.WindowManager f;
    private boolean g;
    public me.hisn.mygesture.e h;
    private int i;
    private long k;
    private final java.util.List<android.view.View> e = new java.util.ArrayList();
    private java.lang.Runnable j = null;
    private me.hisn.mygesture.a.h l = null;

    /* JADX INFO: renamed from: me.hisn.mygesture.a$a, reason: collision with other inner class name */
    class ViewOnLongClickListenerC0023a implements android.view.View.OnLongClickListener {
        ViewOnLongClickListenerC0023a(me.hisn.mygesture.a aVar) {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            return true;
        }
    }

    class b implements java.lang.Runnable {
        b(me.hisn.mygesture.a aVar) {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.MAS.a((java.lang.String) null, 0);
        }
    }

    class c implements android.animation.ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f549a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f550b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f551c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;

        c(me.hisn.mygesture.a aVar, android.view.View view, int i, int i2, int i3, int i4) {
            this.f549a = view;
            this.f550b = i;
            this.f551c = i2;
            this.d = i3;
            this.e = i4;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            int iIntValue = ((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue();
            this.f549a.setPadding(this.f550b - iIntValue, this.f551c, this.d - iIntValue, this.e);
        }
    }

    class d implements android.animation.ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f552a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f553b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f554c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;
        final /* synthetic */ int f;

        d(me.hisn.mygesture.a aVar, android.view.View view, int i, int i2, int i3, int i4, int i5) {
            this.f552a = view;
            this.f553b = i;
            this.f554c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            float fFloatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
            android.view.View view = this.f552a;
            int i = this.f553b;
            float f = this.f554c;
            int i2 = this.d;
            view.setPadding(i, (int) (f - (i2 * fFloatValue)), this.e, (int) (this.f + (i2 * fFloatValue)));
        }
    }

    class e implements android.animation.ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f555a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f556b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f557c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;
        final /* synthetic */ int f;

        e(me.hisn.mygesture.a aVar, android.view.View view, int i, int i2, int i3, int i4, int i5) {
            this.f555a = view;
            this.f556b = i;
            this.f557c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            float fFloatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
            android.view.View view = this.f555a;
            float f = this.f556b;
            int i = this.f557c;
            view.setPadding((int) (f - (i * fFloatValue)), this.d, (int) (this.e + (i * fFloatValue)), this.f);
        }
    }

    class f implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f558a;

        f(me.hisn.mygesture.a aVar, android.view.View view) {
            this.f558a = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0) {
                this.f558a.setAlpha(0.0f);
            }
        }
    }

    private class g implements android.view.View.OnTouchListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f559a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f560b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f561c;
        int d;
        int e;

        /* JADX INFO: renamed from: me.hisn.mygesture.a$g$a, reason: collision with other inner class name */
        class RunnableC0024a implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.View f562a;

            RunnableC0024a(android.view.View view) {
                this.f562a = view;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (me.hisn.mygesture.a.this.g) {
                    return;
                }
                me.hisn.mygesture.P.r0 = true;
                me.hisn.mygesture.MAS.a(this.f562a, me.hisn.mygesture.P.f);
                if (this.f562a == me.hisn.mygesture.a.this.d) {
                    if (me.hisn.utils.k0.a("f41439", false)) {
                        me.hisn.utils.o.a(me.hisn.mygesture.a.this.d);
                    }
                } else if (me.hisn.mygesture.P.E && me.hisn.mygesture.P.D) {
                    me.hisn.mygesture.a.g gVar = me.hisn.mygesture.a.g.this;
                    me.hisn.mygesture.a.this.a(this.f562a, gVar.f559a, gVar.f560b, gVar.f561c, gVar.d, false);
                }
            }
        }

        class b implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.View f564a;

            b(android.view.View view) {
                this.f564a = view;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    me.hisn.mygesture.a.this.f.addView(this.f564a, this.f564a.getLayoutParams());
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private g() {
            this.e = 0;
        }

        /* synthetic */ g(me.hisn.mygesture.a aVar, me.hisn.mygesture.a.ViewOnLongClickListenerC0023a viewOnLongClickListenerC0023a) {
            this();
        }

        @Override // android.view.View.OnTouchListener
        @android.annotation.SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            me.hisn.mygesture.e fVar;
            int iMax;
            me.hisn.mygesture.e eVar;
            int action = motionEvent.getAction();
            int i = 0;
            if (action == 0) {
                me.hisn.mygesture.a.this.g = false;
                me.hisn.mygesture.P.r0 = false;
                this.f559a = (int) motionEvent.getRawX();
                this.f560b = (int) motionEvent.getRawY();
                this.f561c = 0;
                this.d = 0;
                if (me.hisn.mygesture.a.this.l == null) {
                    if (me.hisn.mygesture.P.f536c != ((java.lang.Integer) view.getTag()).intValue()) {
                        if (!me.hisn.mygesture.P.j || me.hisn.mygesture.P.B != ((java.lang.Integer) view.getTag()).intValue()) {
                            int i2 = me.hisn.mygesture.P.w;
                            if (i2 > 0) {
                                me.hisn.mygesture.a aVar = me.hisn.mygesture.a.this;
                                if (aVar.h == null) {
                                    switch (i2) {
                                        case 1:
                                        case 2:
                                            fVar = new me.hisn.mygesture.f(view.getContext().getApplicationContext(), me.hisn.mygesture.a.this.f);
                                            break;
                                        case 3:
                                        case 4:
                                        case 5:
                                        case 6:
                                        case 7:
                                        case 8:
                                        case 9:
                                            fVar = new me.hisn.mygesture.l(view.getContext().getApplicationContext(), me.hisn.mygesture.a.this.f);
                                            break;
                                        case 10:
                                            fVar = new me.hisn.mygesture.d(view.getContext().getApplicationContext(), me.hisn.mygesture.a.this.f);
                                            break;
                                    }
                                    aVar.h = fVar;
                                }
                                me.hisn.mygesture.e eVar2 = me.hisn.mygesture.a.this.h;
                                if (eVar2 != null) {
                                    eVar2.a(this.f559a, this.f560b, ((java.lang.Integer) view.getTag()).intValue());
                                }
                            }
                        } else if (me.hisn.mygesture.P.k) {
                            view.setAlpha(1.0f);
                        }
                    }
                    if (me.hisn.mygesture.P.r > 0) {
                        me.hisn.mygesture.a.this.j = new me.hisn.mygesture.a.g.RunnableC0024a(view);
                        view.postDelayed(me.hisn.mygesture.a.this.j, me.hisn.mygesture.P.r * 50);
                    }
                }
            } else if (action == 1) {
                if (me.hisn.mygesture.a.this.l != null) {
                    me.hisn.mygesture.a.this.a(view, motionEvent.getRawX(), motionEvent.getRawY(), this.f559a, this.f560b, 2);
                } else if (java.lang.Math.abs(this.f561c) >= 20 || java.lang.Math.abs(this.d) >= 20) {
                    if (me.hisn.mygesture.P.D && !me.hisn.mygesture.a.this.g) {
                        me.hisn.mygesture.a.this.a(view, this.f559a, this.f560b, this.f561c, this.d, true);
                    }
                } else if (me.hisn.mygesture.P.o0 && (view == me.hisn.mygesture.a.this.f546a || view == me.hisn.mygesture.a.this.f547b)) {
                    if (me.hisn.mygesture.f.b(view.getContext())) {
                        me.hisn.mygesture.a.this.a(view, true);
                        return true;
                    }
                    new me.hisn.utils.s().a(view.getContext(), me.hisn.mygesture.P.p0, new me.hisn.utils.z0().a(view));
                    me.hisn.mygesture.P.o0 = false;
                    me.hisn.mygesture.P.p0 = null;
                } else if (!me.hisn.mygesture.P.j || me.hisn.mygesture.P.B != ((java.lang.Integer) view.getTag()).intValue() ? me.hisn.mygesture.P.f536c != ((java.lang.Integer) view.getTag()).intValue() || !me.hisn.mygesture.a.this.a(view, this.f559a, this.f560b) : !me.hisn.mygesture.a.this.b(view, this.f559a, this.f560b)) {
                    if (!me.hisn.mygesture.P.r0 && me.hisn.mygesture.P.y && (me.hisn.mygesture.P.B != ((java.lang.Integer) view.getTag()).intValue() || me.hisn.mygesture.MAS.g() <= 0)) {
                        me.hisn.utils.d dVar = new me.hisn.utils.d();
                        boolean z = dVar.c(view.getContext().getApplicationContext()) >= 7;
                        me.hisn.mygesture.a.this.f.removeView(view);
                        view.postDelayed(new me.hisn.mygesture.a.g.b(view), z ? 1500L : 1000L);
                        if (z) {
                            int i3 = this.f559a;
                            int i4 = this.f560b;
                            dVar.a(view.getContext().getApplicationContext(), new int[]{i3, i4, i3, i4, 100});
                        }
                    }
                }
                me.hisn.mygesture.a.this.a(view, true);
            } else if (action != 2) {
                me.hisn.mygesture.a.this.a(view, motionEvent.getRawX(), motionEvent.getRawY(), this.f559a, this.f560b, 3);
                me.hisn.mygesture.a.this.a(view, false);
            } else {
                this.f561c = ((int) motionEvent.getRawX()) - this.f559a;
                this.d = ((int) motionEvent.getRawY()) - this.f560b;
                if (me.hisn.mygesture.a.this.l != null) {
                    me.hisn.mygesture.a.this.a(view, motionEvent.getRawX(), motionEvent.getRawY(), this.f559a, this.f560b, 1);
                } else {
                    if (me.hisn.mygesture.P.f536c == ((java.lang.Integer) view.getTag()).intValue()) {
                        if (view.getScaleX() > 0.75f) {
                            float fMax = 1.0f - (java.lang.Math.max(java.lang.Math.abs(this.f561c), java.lang.Math.abs(this.d)) / me.hisn.mygesture.P.k0);
                            view.setScaleY(fMax);
                            view.setScaleX(fMax);
                        }
                    } else if (me.hisn.mygesture.P.j && me.hisn.mygesture.P.B == ((java.lang.Integer) view.getTag()).intValue()) {
                        if (!me.hisn.mygesture.a.this.g) {
                            if (java.lang.Math.abs(this.f561c) > java.lang.Math.abs(this.d)) {
                                int i5 = this.f561c / 4;
                                view.setPadding(((view.getWidth() - (me.hisn.mygesture.P.l * me.hisn.mygesture.P.m)) / 2) + i5, (me.hisn.mygesture.a.this.i - me.hisn.mygesture.P.l) / 2, ((view.getWidth() - (me.hisn.mygesture.P.l * me.hisn.mygesture.P.m)) / 2) - i5, (me.hisn.mygesture.a.this.i - me.hisn.mygesture.P.l) / 2);
                            } else {
                                int i6 = ((me.hisn.mygesture.a.this.i - me.hisn.mygesture.P.l) / 2) + (this.d / 10);
                                if (i6 >= 0) {
                                    i = i6 > me.hisn.mygesture.a.this.i - me.hisn.mygesture.P.l ? me.hisn.mygesture.a.this.i - me.hisn.mygesture.P.l : i6;
                                }
                                view.setPadding((view.getWidth() - (me.hisn.mygesture.P.l * me.hisn.mygesture.P.m)) / 2, i, (view.getWidth() - (me.hisn.mygesture.P.l * me.hisn.mygesture.P.m)) / 2, (me.hisn.mygesture.a.this.i - me.hisn.mygesture.P.l) - i);
                            }
                            view.invalidate();
                        }
                    } else if (me.hisn.mygesture.P.w > 0 && (iMax = java.lang.Math.max(java.lang.Math.abs(this.f561c), java.lang.Math.abs(this.d))) > 20 && (eVar = me.hisn.mygesture.a.this.h) != null) {
                        eVar.a(iMax, this.f561c, this.d, this.e);
                    }
                    if (!me.hisn.mygesture.a.this.g && (!me.hisn.mygesture.P.D || (me.hisn.mygesture.P.r0 && me.hisn.mygesture.P.E))) {
                        this.e = me.hisn.mygesture.a.this.a(view, this.f559a, this.f560b, this.f561c, this.d, true);
                    }
                }
            }
            return true;
        }
    }

    public interface h {
        void a(android.view.View view);

        void a(android.view.View view, int i, int i2, int i3, int i4);

        void b(android.view.View view, int i, int i2, int i3, int i4);
    }

    a(android.view.WindowManager windowManager) {
        this.f = windowManager;
    }

    public static int a(int i) {
        return (i * java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0)) / 500;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:54:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:55:0x00ed  */
    public int a(android.view.View view, int i, int i2, int i3, int i4, boolean z) {
        java.lang.String str;
        int[] iArr = {i, i2};
        if (java.lang.Math.abs(i3) * 1.2f <= java.lang.Math.abs(i4)) {
            int i5 = me.hisn.mygesture.P.n0;
            if (i4 > i5) {
                if (view == this.f546a) {
                    a("left_edge_swipe_down_key", "slow_left_edge_swipe_down_key", view, 4, z, iArr);
                } else if (view == this.f547b) {
                    a("right_edge_swipe_down_key", "slow_right_edge_swipe_down_key", view, 4, z, iArr);
                } else if (view == this.d) {
                    a("f41428", "f41431", view, 4, z, iArr);
                }
                return 2;
            }
            if (i4 < i5 * (-1)) {
                if (view != this.f548c) {
                    if (view == this.f546a) {
                        a("left_edge_swipe_up_key", "slow_left_edge_swipe_up_key", view, 3, z, iArr);
                        return 2;
                    }
                    if (view == this.f547b) {
                        a("right_edge_swipe_up_key", "slow_right_edge_swipe_up_key", view, 3, z, iArr);
                        return 2;
                    }
                    if (view != this.d) {
                        return 2;
                    }
                    a("f41427", "f41432", view, 3, z, iArr);
                    return 2;
                }
                int i6 = me.hisn.mygesture.P.f0 + 1;
                java.lang.String str2 = "left_of_bottom_edge_swipe_up_key";
                if (i6 == 1) {
                    str = "slow_left_of_bottom_edge_swipe_up_key";
                } else if (i6 != 2) {
                    if (i6 != 3) {
                        str = "slow_left_of_bottom_edge_swipe_up_key";
                    } else {
                        int i7 = me.hisn.mygesture.P.k0;
                        if (i < i7 / 3) {
                            str = "slow_left_of_bottom_edge_swipe_up_key";
                        } else if (i > (i7 * 2) / 3) {
                            str2 = "right_of_bottom_edge_swipe_up_key";
                            str = "slow_right_of_bottom_edge_swipe_up_key";
                        } else {
                            str = "slow_center_of_bottom_edge_swipe_up_key";
                            str2 = "center_of_bottom_edge_swipe_up_key";
                        }
                    }
                } else if (i < me.hisn.mygesture.P.k0 / 2) {
                    str = "slow_left_of_bottom_edge_swipe_up_key";
                } else {
                    str = "slow_center_of_bottom_edge_swipe_up_key";
                    str2 = "center_of_bottom_edge_swipe_up_key";
                }
                a(str2, str, view, 3, z, iArr);
                return 2;
            }
        } else if (java.lang.Math.abs(i3) > me.hisn.mygesture.P.n0) {
            if (view == this.f546a) {
                a("left_edge_swipe_right_key", "slow_left_edge_swipe_right_key", view, 1, z, iArr);
            } else if (view == this.f547b) {
                a("right_edge_swipe_left_key", "slow_right_edge_swipe_left_key", view, 2, z, iArr);
            } else if (view == this.f548c) {
                if (i3 > 0) {
                    a("b31416", "b31417", view, 1, z, iArr);
                } else {
                    a("b31415", "b31418", view, 2, z, iArr);
                }
            } else if (view == this.d) {
                if (i3 > 0) {
                    a("f41426", "f41430", view, 1, z, iArr);
                } else {
                    a("f41425", "f41429", view, 2, z, iArr);
                }
            }
            return 1;
        }
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0092  */
    /* JADX WARN: Code duplicated, block: B:22:0x0095  */
    /* JADX WARN: Code duplicated, block: B:26:? A[RETURN, SYNTHETIC] */
    private void a(android.view.View view) {
        android.animation.ValueAnimator valueAnimatorOfFloat;
        android.animation.ValueAnimator.AnimatorUpdateListener eVar;
        if (((java.lang.Integer) view.getTag()).intValue() == me.hisn.mygesture.P.f536c) {
            view.animate().scaleY(1.0f).scaleX(1.0f).setDuration(200L).start();
            return;
        }
        if (!me.hisn.mygesture.P.j || me.hisn.mygesture.P.B != ((java.lang.Integer) view.getTag()).intValue()) {
            a(true);
            return;
        }
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        if (paddingLeft == paddingRight) {
            if (paddingTop != paddingBottom) {
                valueAnimatorOfFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.setDuration(200L);
                eVar = new me.hisn.mygesture.a.d(this, view, paddingLeft, paddingTop, (paddingTop - paddingBottom) / 2, paddingRight, paddingBottom);
            }
            if (!me.hisn.mygesture.P.k && me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0) {
                view.postDelayed(new me.hisn.mygesture.a.f(this, view), 1000L);
            }
        }
        valueAnimatorOfFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(200L);
        eVar = new me.hisn.mygesture.a.e(this, view, paddingLeft, (paddingLeft - paddingRight) / 2, paddingTop, paddingRight, paddingBottom);
        valueAnimatorOfFloat.addUpdateListener(eVar);
        valueAnimatorOfFloat.start();
        if (!me.hisn.mygesture.P.k && me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0) {
            view.postDelayed(new me.hisn.mygesture.a.f(this, view), 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.view.View view, float f2, float f3, int i, int i2, int i3) {
        me.hisn.mygesture.a.h hVar = this.l;
        if (hVar != null) {
            if (i3 == 1) {
                hVar.a(view, (int) f2, (int) f3, i, i2);
            } else if (i3 == 2) {
                hVar.b(view, (int) f2, (int) f3, i, i2);
            } else if (i3 == 3) {
                hVar.a(view);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.view.View view, boolean z) {
        java.lang.Runnable runnable = this.j;
        if (runnable != null) {
            view.removeCallbacks(runnable);
            this.j = null;
        }
        a(view);
        me.hisn.mygesture.P.s0++;
        me.hisn.mygesture.P.t0 = java.lang.System.currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(android.view.View view, int i, int i2) {
        if (!a("f41437", "f41438", view, 3, false, new int[]{i, i2})) {
            return false;
        }
        me.hisn.mygesture.MAS.a(view, me.hisn.mygesture.P.i);
        return true;
    }

    private boolean a(java.lang.String str, java.lang.String str2, android.view.View view, int i, boolean z, int[] iArr) {
        int i2;
        this.g = true;
        if (me.hisn.mygesture.P.r0) {
            i2 = me.hisn.mygesture.P.s.getInt(str2 + "_k", 0);
        } else {
            i2 = 0;
        }
        if (i2 == 0) {
            android.content.SharedPreferences sharedPreferences = me.hisn.mygesture.P.s;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            str2 = str;
            sb.append(str);
            sb.append("_k");
            i2 = sharedPreferences.getInt(sb.toString(), 0);
        }
        java.lang.String str3 = str2;
        if (i2 == 0) {
            return false;
        }
        if (me.hisn.mygesture.P.x == 1 && me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0) {
            long jCurrentTimeMillis = java.lang.System.currentTimeMillis() - this.k;
            this.k = java.lang.System.currentTimeMillis();
            if (jCurrentTimeMillis > 1000) {
                return true;
            }
        }
        android.os.Bundle bundleA = new me.hisn.utils.z0().a(i2, i, view);
        if (z) {
            me.hisn.mygesture.MAS.a(view, me.hisn.mygesture.P.e);
        }
        try {
            me.hisn.utils.s0.a(view.getContext(), str3, view, bundleA, me.hisn.mygesture.P.s, i, iArr);
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
        return true;
    }

    public static int b(int i) {
        return (me.hisn.mygesture.P.k0 * java.lang.Math.min(i, 1000)) / 1000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(android.view.View view, int i, int i2) {
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int width = (view.getWidth() - paddingLeft) - paddingRight;
        if (i <= paddingLeft || i >= view.getWidth() - paddingRight || !a("touch_bar_click", "touch_bar_long_click", view, 3, false, new int[]{i, i2})) {
            return false;
        }
        me.hisn.mygesture.MAS.a(view, me.hisn.mygesture.P.i);
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        android.animation.ValueAnimator valueAnimatorOfInt = android.animation.ValueAnimator.ofInt(0, width / 6, 0);
        valueAnimatorOfInt.setDuration(150L);
        valueAnimatorOfInt.addUpdateListener(new me.hisn.mygesture.a.c(this, view, paddingLeft, paddingTop, paddingRight, paddingBottom));
        valueAnimatorOfInt.start();
        return true;
    }

    public static int c(int i) {
        return (me.hisn.mygesture.P.l0 * java.lang.Math.min(i, 1000)) / 1000;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v9 */
    java.util.List<android.view.View> a(android.content.Context context) {
        boolean z;
        ?? r4;
        me.hisn.mygesture.a.g gVar = new me.hisn.mygesture.a.g(this, null);
        int i = (me.hisn.mygesture.P.l0 * me.hisn.mygesture.P.I) / 10;
        int iMin = (me.hisn.mygesture.P.J * java.lang.Math.min(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0)) / 100;
        int i2 = (me.hisn.mygesture.P.l0 * me.hisn.mygesture.P.M) / 10;
        int iMin2 = (me.hisn.mygesture.P.O * java.lang.Math.min(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0)) / 100;
        int iMin3 = (me.hisn.mygesture.P.S * java.lang.Math.min(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0)) / 100;
        this.i = iMin3;
        if (me.hisn.mygesture.P.l > iMin3 / 2) {
            me.hisn.mygesture.P.l = iMin3 / 2;
        }
        this.e.clear();
        me.hisn.mygesture.a.ViewOnLongClickListenerC0023a viewOnLongClickListenerC0023a = new me.hisn.mygesture.a.ViewOnLongClickListenerC0023a(this);
        if (me.hisn.mygesture.P.t) {
            if (this.f546a == null) {
                android.view.View view = new android.view.View(context);
                this.f546a = view;
                view.setOnTouchListener(gVar);
                this.f546a.setTag(java.lang.Integer.valueOf(me.hisn.mygesture.P.L));
                this.f546a.setOnLongClickListener(viewOnLongClickListenerC0023a);
                this.f546a.setLongClickable(false);
            }
            z = false;
            this.f546a.setLayoutParams(new me.hisn.utils.v().a(true, 8388691, iMin, i, 0 - me.hisn.mygesture.MAS.h(), (me.hisn.mygesture.P.l0 * me.hisn.mygesture.P.K) / 10, 0, me.hisn.mygesture.P.z));
            this.e.add(this.f546a);
        } else {
            z = false;
        }
        if (me.hisn.mygesture.P.u) {
            if (this.f547b == null) {
                android.view.View view2 = new android.view.View(context);
                this.f547b = view2;
                view2.setOnTouchListener(gVar);
                this.f547b.setTag(java.lang.Integer.valueOf(me.hisn.mygesture.P.R));
                this.f547b.setOnLongClickListener(viewOnLongClickListenerC0023a);
                this.f547b.setLongClickable(z);
            }
            r4 = 0;
            this.f547b.setLayoutParams(new me.hisn.utils.v().a(true, 8388693, iMin2, i2, 0 - me.hisn.mygesture.MAS.i(), (me.hisn.mygesture.P.l0 * me.hisn.mygesture.P.Q) / 10, 0, me.hisn.mygesture.P.z));
            this.e.add(this.f547b);
        } else {
            r4 = 0;
        }
        if (me.hisn.mygesture.P.v) {
            if (this.f548c == null) {
                android.widget.ImageView imageView = new android.widget.ImageView(context);
                this.f548c = imageView;
                imageView.setOnTouchListener(gVar);
                this.f548c.setTag(java.lang.Integer.valueOf(me.hisn.mygesture.P.B));
                this.f548c.setOnLongClickListener(viewOnLongClickListenerC0023a);
                this.f548c.setLongClickable(r4);
            }
            if (me.hisn.mygesture.P.j) {
                android.graphics.drawable.Drawable drawable = context.getDrawable(me.hisn.mygesture.R.drawable.touch_bar);
                if (drawable != null) {
                    drawable.setColorFilter(me.hisn.mygesture.P.q ? context.getResources().getColor(me.hisn.mygesture.R.color.green) : me.hisn.mygesture.P.n, android.graphics.PorterDuff.Mode.SRC);
                    ((android.widget.ImageView) this.f548c).setImageDrawable(drawable);
                    android.view.View view3 = this.f548c;
                    int i3 = (me.hisn.mygesture.P.k0 - (me.hisn.mygesture.P.l * me.hisn.mygesture.P.m)) / 2;
                    int i4 = this.i;
                    view3.setPadding(i3, (i4 - me.hisn.mygesture.P.l) / 2, (me.hisn.mygesture.P.k0 - (me.hisn.mygesture.P.l * me.hisn.mygesture.P.m)) / 2, (i4 - me.hisn.mygesture.P.l) / 2);
                    if (me.hisn.mygesture.P.q) {
                        new java.lang.Thread(new me.hisn.mygesture.a.b(this)).start();
                    }
                }
                this.f548c.setAlpha(me.hisn.mygesture.P.k && me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0 ? 0.0f : 1.0f);
            } else {
                ((android.widget.ImageView) this.f548c).setImageDrawable(null);
            }
            this.f548c.setLayoutParams(new me.hisn.utils.v().a(true, 8388691, me.hisn.mygesture.P.k0, this.i, 0 - me.hisn.mygesture.MAS.h(), 0 - me.hisn.mygesture.MAS.g(), 0, me.hisn.mygesture.P.z));
            this.e.add(this.f548c);
        }
        if (me.hisn.mygesture.P.s.getBoolean("f41433", r4)) {
            int iA = a(me.hisn.mygesture.P.s.getInt("f41436", 50));
            if (this.d == null) {
                android.widget.ImageView imageView2 = new android.widget.ImageView(context);
                this.d = imageView2;
                imageView2.setImageDrawable(me.hisn.utils.o.b(context));
                this.d.setOnTouchListener(gVar);
                this.d.setOnLongClickListener(viewOnLongClickListenerC0023a);
                this.d.setLongClickable(r4);
                this.d.setTag(java.lang.Integer.valueOf(me.hisn.mygesture.P.f536c));
                int i5 = iA / 20;
                this.d.setPadding(i5, i5, i5, i5);
            }
            this.d.setAlpha(me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0 ? 0.1f : 0.3f);
            this.d.setLayoutParams(new me.hisn.utils.v().a(true, 8388659, iA, iA, b(me.hisn.mygesture.P.s.getInt("f41434", r4)) - me.hisn.mygesture.MAS.h(), c(me.hisn.mygesture.P.s.getInt("f41435", me.hisn.mygesture.P.l0 / 2)) - me.hisn.mygesture.MAS.j(), 0, me.hisn.mygesture.P.z));
            this.e.add(this.d);
        }
        return this.e;
    }

    public void a(me.hisn.mygesture.a.h hVar) {
        this.l = hVar;
    }

    public void a(boolean z) {
        me.hisn.mygesture.e eVar;
        if (me.hisn.mygesture.P.w <= 0 || (eVar = this.h) == null) {
            return;
        }
        if (z) {
            eVar.a();
        } else {
            eVar.b();
        }
    }
}
