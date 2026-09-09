package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public class f extends androidx.recyclerview.widget.RecyclerView.n implements androidx.recyclerview.widget.RecyclerView.q {
    private androidx.recyclerview.widget.f.g A;
    private android.graphics.Rect C;
    private long D;
    float d;
    float e;
    private float f;
    private float g;
    float h;
    float i;
    private float j;
    private float k;
    androidx.recyclerview.widget.f.AbstractC0015f m;
    int o;
    private int q;
    androidx.recyclerview.widget.RecyclerView r;
    android.view.VelocityTracker t;
    private java.util.List<androidx.recyclerview.widget.RecyclerView.d0> u;
    private java.util.List<java.lang.Integer> v;
    a.c.e.b z;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final java.util.List<android.view.View> f307a = new java.util.ArrayList();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final float[] f308b = new float[2];

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    androidx.recyclerview.widget.RecyclerView.d0 f309c = null;
    int l = -1;
    private int n = 0;
    java.util.List<androidx.recyclerview.widget.f.h> p = new java.util.ArrayList();
    final java.lang.Runnable s = new androidx.recyclerview.widget.f.a();
    private androidx.recyclerview.widget.RecyclerView.j w = null;
    android.view.View x = null;
    int y = -1;
    private final androidx.recyclerview.widget.RecyclerView.s B = new androidx.recyclerview.widget.f.b();

    class a implements java.lang.Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            androidx.recyclerview.widget.f fVar = androidx.recyclerview.widget.f.this;
            if (fVar.f309c == null || !fVar.c()) {
                return;
            }
            androidx.recyclerview.widget.f fVar2 = androidx.recyclerview.widget.f.this;
            androidx.recyclerview.widget.RecyclerView.d0 d0Var = fVar2.f309c;
            if (d0Var != null) {
                fVar2.a(d0Var);
            }
            androidx.recyclerview.widget.f fVar3 = androidx.recyclerview.widget.f.this;
            fVar3.r.removeCallbacks(fVar3.s);
            a.c.e.m.a(androidx.recyclerview.widget.f.this.r, this);
        }
    }

    class b implements androidx.recyclerview.widget.RecyclerView.s {
        b() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.s
        public void a(boolean z) {
            if (z) {
                androidx.recyclerview.widget.f.this.a((androidx.recyclerview.widget.RecyclerView.d0) null, 0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.s
        public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
            int iFindPointerIndex;
            androidx.recyclerview.widget.f.h hVarA;
            androidx.recyclerview.widget.f.this.z.a(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                androidx.recyclerview.widget.f.this.l = motionEvent.getPointerId(0);
                androidx.recyclerview.widget.f.this.d = motionEvent.getX();
                androidx.recyclerview.widget.f.this.e = motionEvent.getY();
                androidx.recyclerview.widget.f.this.b();
                androidx.recyclerview.widget.f fVar = androidx.recyclerview.widget.f.this;
                if (fVar.f309c == null && (hVarA = fVar.a(motionEvent)) != null) {
                    androidx.recyclerview.widget.f fVar2 = androidx.recyclerview.widget.f.this;
                    fVar2.d -= hVarA.i;
                    fVar2.e -= hVarA.j;
                    fVar2.a(hVarA.e, true);
                    if (androidx.recyclerview.widget.f.this.f307a.remove(hVarA.e.f203a)) {
                        androidx.recyclerview.widget.f fVar3 = androidx.recyclerview.widget.f.this;
                        fVar3.m.a(fVar3.r, hVarA.e);
                    }
                    androidx.recyclerview.widget.f.this.a(hVarA.e, hVarA.f);
                    androidx.recyclerview.widget.f fVar4 = androidx.recyclerview.widget.f.this;
                    fVar4.a(motionEvent, fVar4.o, 0);
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                androidx.recyclerview.widget.f fVar5 = androidx.recyclerview.widget.f.this;
                fVar5.l = -1;
                fVar5.a((androidx.recyclerview.widget.RecyclerView.d0) null, 0);
            } else {
                int i = androidx.recyclerview.widget.f.this.l;
                if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) >= 0) {
                    androidx.recyclerview.widget.f.this.a(actionMasked, motionEvent, iFindPointerIndex);
                }
            }
            android.view.VelocityTracker velocityTracker = androidx.recyclerview.widget.f.this.t;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            return androidx.recyclerview.widget.f.this.f309c != null;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.s
        public void b(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
            androidx.recyclerview.widget.f.this.z.a(motionEvent);
            android.view.VelocityTracker velocityTracker = androidx.recyclerview.widget.f.this.t;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            if (androidx.recyclerview.widget.f.this.l == -1) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            int iFindPointerIndex = motionEvent.findPointerIndex(androidx.recyclerview.widget.f.this.l);
            if (iFindPointerIndex >= 0) {
                androidx.recyclerview.widget.f.this.a(actionMasked, motionEvent, iFindPointerIndex);
            }
            androidx.recyclerview.widget.f fVar = androidx.recyclerview.widget.f.this;
            androidx.recyclerview.widget.RecyclerView.d0 d0Var = fVar.f309c;
            if (d0Var == null) {
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (iFindPointerIndex >= 0) {
                        fVar.a(motionEvent, fVar.o, iFindPointerIndex);
                        androidx.recyclerview.widget.f.this.a(d0Var);
                        androidx.recyclerview.widget.f fVar2 = androidx.recyclerview.widget.f.this;
                        fVar2.r.removeCallbacks(fVar2.s);
                        androidx.recyclerview.widget.f.this.s.run();
                        androidx.recyclerview.widget.f.this.r.invalidate();
                        return;
                    }
                    return;
                }
                if (actionMasked != 3) {
                    if (actionMasked != 6) {
                        return;
                    }
                    int actionIndex = motionEvent.getActionIndex();
                    if (motionEvent.getPointerId(actionIndex) == androidx.recyclerview.widget.f.this.l) {
                        androidx.recyclerview.widget.f.this.l = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
                        androidx.recyclerview.widget.f fVar3 = androidx.recyclerview.widget.f.this;
                        fVar3.a(motionEvent, fVar3.o, actionIndex);
                        return;
                    }
                    return;
                }
                android.view.VelocityTracker velocityTracker2 = fVar.t;
                if (velocityTracker2 != null) {
                    velocityTracker2.clear();
                }
            }
            androidx.recyclerview.widget.f.this.a((androidx.recyclerview.widget.RecyclerView.d0) null, 0);
            androidx.recyclerview.widget.f.this.l = -1;
        }
    }

    class c extends androidx.recyclerview.widget.f.h {
        final /* synthetic */ int n;
        final /* synthetic */ androidx.recyclerview.widget.RecyclerView.d0 o;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, int i2, float f, float f2, float f3, float f4, int i3, androidx.recyclerview.widget.RecyclerView.d0 d0Var2) {
            super(d0Var, i, i2, f, f2, f3, f4);
            this.n = i3;
            this.o = d0Var2;
        }

        @Override // androidx.recyclerview.widget.f.h, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            super.onAnimationEnd(animator);
            if (this.k) {
                return;
            }
            if (this.n <= 0) {
                androidx.recyclerview.widget.f fVar = androidx.recyclerview.widget.f.this;
                fVar.m.a(fVar.r, this.o);
            } else {
                androidx.recyclerview.widget.f.this.f307a.add(this.o.f203a);
                this.h = true;
                int i = this.n;
                if (i > 0) {
                    androidx.recyclerview.widget.f.this.a(this, i);
                }
            }
            androidx.recyclerview.widget.f fVar2 = androidx.recyclerview.widget.f.this;
            android.view.View view = fVar2.x;
            android.view.View view2 = this.o.f203a;
            if (view == view2) {
                fVar2.c(view2);
            }
        }
    }

    class d implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.f.h f312a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f313b;

        d(androidx.recyclerview.widget.f.h hVar, int i) {
            this.f312a = hVar;
            this.f313b = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.f.this.r;
            if (recyclerView == null || !recyclerView.isAttachedToWindow()) {
                return;
            }
            androidx.recyclerview.widget.f.h hVar = this.f312a;
            if (hVar.k || hVar.e.f() == -1) {
                return;
            }
            androidx.recyclerview.widget.RecyclerView.l itemAnimator = androidx.recyclerview.widget.f.this.r.getItemAnimator();
            if ((itemAnimator == null || !itemAnimator.a((androidx.recyclerview.widget.RecyclerView.l.a) null)) && !androidx.recyclerview.widget.f.this.a()) {
                androidx.recyclerview.widget.f.this.m.b(this.f312a.e, this.f313b);
            } else {
                androidx.recyclerview.widget.f.this.r.post(this);
            }
        }
    }

    class e implements androidx.recyclerview.widget.RecyclerView.j {
        e() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.j
        public int a(int i, int i2) {
            androidx.recyclerview.widget.f fVar = androidx.recyclerview.widget.f.this;
            android.view.View view = fVar.x;
            if (view == null) {
                return i2;
            }
            int iIndexOfChild = fVar.y;
            if (iIndexOfChild == -1) {
                iIndexOfChild = fVar.r.indexOfChild(view);
                androidx.recyclerview.widget.f.this.y = iIndexOfChild;
            }
            if (i2 == i - 1) {
                return iIndexOfChild;
            }
            return i2 < iIndexOfChild ? i2 : i2 + 1;
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.f$f, reason: collision with other inner class name */
    public static abstract class AbstractC0015f {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private static final android.view.animation.Interpolator f316b = new androidx.recyclerview.widget.f.AbstractC0015f.a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private static final android.view.animation.Interpolator f317c = new androidx.recyclerview.widget.f.AbstractC0015f.b();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private int f318a = -1;

        /* JADX INFO: renamed from: androidx.recyclerview.widget.f$f$a */
        static class a implements android.view.animation.Interpolator {
            a() {
            }

            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        }

        /* JADX INFO: renamed from: androidx.recyclerview.widget.f$f$b */
        static class b implements android.view.animation.Interpolator {
            b() {
            }

            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        }

        private int a(androidx.recyclerview.widget.RecyclerView recyclerView) {
            if (this.f318a == -1) {
                this.f318a = recyclerView.getResources().getDimensionPixelSize(a.f.b.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.f318a;
        }

        public static int b(int i, int i2) {
            int i3;
            int i4 = i & 789516;
            if (i4 == 0) {
                return i;
            }
            int i5 = i & (~i4);
            if (i2 == 0) {
                i3 = i4 << 2;
            } else {
                int i6 = i4 << 1;
                i5 |= (-789517) & i6;
                i3 = (i6 & 789516) << 2;
            }
            return i5 | i3;
        }

        public static int c(int i, int i2) {
            return i2 << (i * 8);
        }

        public static int d(int i, int i2) {
            return c(2, i) | c(1, i2) | c(0, i2 | i);
        }

        public float a(float f) {
            return f;
        }

        public float a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            return 0.5f;
        }

        public int a() {
            return 0;
        }

        public int a(int i, int i2) {
            int i3;
            int i4 = i & 3158064;
            if (i4 == 0) {
                return i;
            }
            int i5 = i & (~i4);
            if (i2 == 0) {
                i3 = i4 >> 2;
            } else {
                int i6 = i4 >> 1;
                i5 |= (-3158065) & i6;
                i3 = (i6 & 3158064) >> 2;
            }
            return i5 | i3;
        }

        public int a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2, int i3, long j) {
            int iSignum = (int) (((int) (((int) java.lang.Math.signum(i2)) * a(recyclerView) * f317c.getInterpolation(java.lang.Math.min(1.0f, (java.lang.Math.abs(i2) * 1.0f) / i)))) * f316b.getInterpolation(j <= 2000 ? j / 2000.0f : 1.0f));
            if (iSignum == 0) {
                return i2 > 0 ? 1 : -1;
            }
            return iSignum;
        }

        public long a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, float f, float f2) {
            androidx.recyclerview.widget.RecyclerView.l itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator == null) {
                return i == 8 ? 200L : 250L;
            }
            return i == 8 ? itemAnimator.e() : itemAnimator.f();
        }

        public androidx.recyclerview.widget.RecyclerView.d0 a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, java.util.List<androidx.recyclerview.widget.RecyclerView.d0> list, int i, int i2) {
            int bottom;
            int iAbs;
            int top;
            int iAbs2;
            int left;
            int iAbs3;
            int right;
            int iAbs4;
            int width = i + d0Var.f203a.getWidth();
            int height = i2 + d0Var.f203a.getHeight();
            int left2 = i - d0Var.f203a.getLeft();
            int top2 = i2 - d0Var.f203a.getTop();
            int size = list.size();
            androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = null;
            int i3 = -1;
            for (int i4 = 0; i4 < size; i4++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var3 = list.get(i4);
                if (left2 > 0 && (right = d0Var3.f203a.getRight() - width) < 0 && d0Var3.f203a.getRight() > d0Var.f203a.getRight() && (iAbs4 = java.lang.Math.abs(right)) > i3) {
                    d0Var2 = d0Var3;
                    i3 = iAbs4;
                }
                if (left2 < 0 && (left = d0Var3.f203a.getLeft() - i) > 0 && d0Var3.f203a.getLeft() < d0Var.f203a.getLeft() && (iAbs3 = java.lang.Math.abs(left)) > i3) {
                    d0Var2 = d0Var3;
                    i3 = iAbs3;
                }
                if (top2 < 0 && (top = d0Var3.f203a.getTop() - i2) > 0 && d0Var3.f203a.getTop() < d0Var.f203a.getTop() && (iAbs2 = java.lang.Math.abs(top)) > i3) {
                    d0Var2 = d0Var3;
                    i3 = iAbs2;
                }
                if (top2 > 0 && (bottom = d0Var3.f203a.getBottom() - height) < 0 && d0Var3.f203a.getBottom() > d0Var.f203a.getBottom() && (iAbs = java.lang.Math.abs(bottom)) > i3) {
                    d0Var2 = d0Var3;
                    i3 = iAbs;
                }
            }
            return d0Var2;
        }

        public void a(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, float f, float f2, int i, boolean z) {
            androidx.recyclerview.widget.h.f325a.b(canvas, recyclerView, d0Var.f203a, f, f2, i, z);
        }

        void a(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, java.util.List<androidx.recyclerview.widget.f.h> list, int i, float f, float f2) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                androidx.recyclerview.widget.f.h hVar = list.get(i2);
                hVar.c();
                int iSave = canvas.save();
                a(canvas, recyclerView, hVar.e, hVar.i, hVar.j, hVar.f, false);
                canvas.restoreToCount(iSave);
            }
            if (d0Var != null) {
                int iSave2 = canvas.save();
                a(canvas, recyclerView, d0Var, f, f2, i, true);
                canvas.restoreToCount(iSave2);
            }
        }

        public void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i) {
            if (d0Var != null) {
                androidx.recyclerview.widget.h.f325a.b(d0Var.f203a);
            }
        }

        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            androidx.recyclerview.widget.h.f325a.a(d0Var.f203a);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, androidx.recyclerview.widget.RecyclerView.d0 d0Var2, int i2, int i3, int i4) {
            androidx.recyclerview.widget.RecyclerView.o layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof androidx.recyclerview.widget.f.i) {
                ((androidx.recyclerview.widget.f.i) layoutManager).a(d0Var.f203a, d0Var2.f203a, i3, i4);
                return;
            }
            if (layoutManager.a()) {
                if (layoutManager.f(d0Var2.f203a) <= recyclerView.getPaddingLeft()) {
                    recyclerView.f(i2);
                }
                if (layoutManager.i(d0Var2.f203a) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.f(i2);
                }
            }
            if (layoutManager.b()) {
                if (layoutManager.j(d0Var2.f203a) <= recyclerView.getPaddingTop()) {
                    recyclerView.f(i2);
                }
                if (layoutManager.e(d0Var2.f203a) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.f(i2);
                }
            }
        }

        public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2) {
            return true;
        }

        public float b(float f) {
            return f;
        }

        public float b(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            return 0.5f;
        }

        final int b(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            return a(c(recyclerView, d0Var), a.c.e.m.i(recyclerView));
        }

        public void b(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, float f, float f2, int i, boolean z) {
            androidx.recyclerview.widget.h.f325a.a(canvas, recyclerView, d0Var.f203a, f, f2, i, z);
        }

        void b(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, java.util.List<androidx.recyclerview.widget.f.h> list, int i, float f, float f2) {
            int size = list.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                androidx.recyclerview.widget.f.h hVar = list.get(i2);
                int iSave = canvas.save();
                b(canvas, recyclerView, hVar.e, hVar.i, hVar.j, hVar.f, false);
                canvas.restoreToCount(iSave);
            }
            if (d0Var != null) {
                int iSave2 = canvas.save();
                b(canvas, recyclerView, d0Var, f, f2, i, true);
                canvas.restoreToCount(iSave2);
            }
            for (int i3 = size - 1; i3 >= 0; i3--) {
                androidx.recyclerview.widget.f.h hVar2 = list.get(i3);
                if (hVar2.l && !hVar2.h) {
                    list.remove(i3);
                } else if (!hVar2.l) {
                    z = true;
                }
            }
            if (z) {
                recyclerView.invalidate();
            }
        }

        public abstract void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i);

        public abstract boolean b();

        public abstract boolean b(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2);

        public abstract int c(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var);

        public boolean c() {
            return true;
        }

        boolean d(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            return (b(recyclerView, d0Var) & 16711680) != 0;
        }
    }

    private class g extends android.view.GestureDetector.SimpleOnGestureListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private boolean f319a = true;

        g() {
        }

        void a() {
            this.f319a = false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(android.view.MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(android.view.MotionEvent motionEvent) {
            android.view.View viewB;
            androidx.recyclerview.widget.RecyclerView.d0 d0VarE;
            if (!this.f319a || (viewB = androidx.recyclerview.widget.f.this.b(motionEvent)) == null || (d0VarE = androidx.recyclerview.widget.f.this.r.e(viewB)) == null) {
                return;
            }
            androidx.recyclerview.widget.f fVar = androidx.recyclerview.widget.f.this;
            if (fVar.m.d(fVar.r, d0VarE)) {
                int pointerId = motionEvent.getPointerId(0);
                int i = androidx.recyclerview.widget.f.this.l;
                if (pointerId == i) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(i);
                    float x = motionEvent.getX(iFindPointerIndex);
                    float y = motionEvent.getY(iFindPointerIndex);
                    androidx.recyclerview.widget.f fVar2 = androidx.recyclerview.widget.f.this;
                    fVar2.d = x;
                    fVar2.e = y;
                    fVar2.i = 0.0f;
                    fVar2.h = 0.0f;
                    if (fVar2.m.c()) {
                        androidx.recyclerview.widget.f.this.a(d0VarE, 2);
                    }
                }
            }
        }
    }

    private static class h implements android.animation.Animator.AnimatorListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final float f321a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final float f322b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final float f323c;
        final float d;
        final androidx.recyclerview.widget.RecyclerView.d0 e;
        final int f;
        private final android.animation.ValueAnimator g;
        boolean h;
        float i;
        float j;
        boolean k = false;
        boolean l = false;
        private float m;

        class a implements android.animation.ValueAnimator.AnimatorUpdateListener {
            a() {
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                androidx.recyclerview.widget.f.h.this.a(valueAnimator.getAnimatedFraction());
            }
        }

        h(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, int i2, float f, float f2, float f3, float f4) {
            this.f = i2;
            this.e = d0Var;
            this.f321a = f;
            this.f322b = f2;
            this.f323c = f3;
            this.d = f4;
            android.animation.ValueAnimator valueAnimatorOfFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
            this.g = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new androidx.recyclerview.widget.f.h.a());
            this.g.setTarget(d0Var.f203a);
            this.g.addListener(this);
            a(0.0f);
        }

        public void a() {
            this.g.cancel();
        }

        public void a(float f) {
            this.m = f;
        }

        public void a(long j) {
            this.g.setDuration(j);
        }

        public void b() {
            this.e.a(false);
            this.g.start();
        }

        public void c() {
            float f = this.f321a;
            float f2 = this.f323c;
            this.i = f == f2 ? this.e.f203a.getTranslationX() : f + (this.m * (f2 - f));
            float f3 = this.f322b;
            float f4 = this.d;
            this.j = f3 == f4 ? this.e.f203a.getTranslationY() : f3 + (this.m * (f4 - f3));
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            a(1.0f);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (!this.l) {
                this.e.a(true);
            }
            this.l = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }
    }

    public interface i {
        void a(android.view.View view, android.view.View view2, int i, int i2);
    }

    public f(androidx.recyclerview.widget.f.AbstractC0015f abstractC0015f) {
        this.m = abstractC0015f;
    }

    private void a(float[] fArr) {
        if ((this.o & 12) != 0) {
            fArr[0] = (this.j + this.h) - this.f309c.f203a.getLeft();
        } else {
            fArr[0] = this.f309c.f203a.getTranslationX();
        }
        if ((this.o & 3) != 0) {
            fArr[1] = (this.k + this.i) - this.f309c.f203a.getTop();
        } else {
            fArr[1] = this.f309c.f203a.getTranslationY();
        }
    }

    private static boolean a(android.view.View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= f3 + ((float) view.getWidth()) && f2 >= f4 && f2 <= f4 + ((float) view.getHeight());
    }

    private int b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i2) {
        if ((i2 & 12) == 0) {
            return 0;
        }
        int i3 = this.h > 0.0f ? 8 : 4;
        android.view.VelocityTracker velocityTracker = this.t;
        if (velocityTracker != null && this.l > -1) {
            androidx.recyclerview.widget.f.AbstractC0015f abstractC0015f = this.m;
            float f = this.g;
            abstractC0015f.b(f);
            velocityTracker.computeCurrentVelocity(1000, f);
            float xVelocity = this.t.getXVelocity(this.l);
            float yVelocity = this.t.getYVelocity(this.l);
            int i4 = xVelocity <= 0.0f ? 4 : 8;
            float fAbs = java.lang.Math.abs(xVelocity);
            if ((i4 & i2) != 0 && i3 == i4) {
                androidx.recyclerview.widget.f.AbstractC0015f abstractC0015f2 = this.m;
                float f2 = this.f;
                abstractC0015f2.a(f2);
                if (fAbs >= f2 && fAbs > java.lang.Math.abs(yVelocity)) {
                    return i4;
                }
            }
        }
        float width = this.r.getWidth() * this.m.b(d0Var);
        if ((i2 & i3) == 0 || java.lang.Math.abs(this.h) <= width) {
            return 0;
        }
        return i3;
    }

    private java.util.List<androidx.recyclerview.widget.RecyclerView.d0> b(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = d0Var;
        java.util.List<androidx.recyclerview.widget.RecyclerView.d0> list = this.u;
        if (list == null) {
            this.u = new java.util.ArrayList();
            this.v = new java.util.ArrayList();
        } else {
            list.clear();
            this.v.clear();
        }
        int iA = this.m.a();
        int iRound = java.lang.Math.round(this.j + this.h) - iA;
        int iRound2 = java.lang.Math.round(this.k + this.i) - iA;
        int i2 = iA * 2;
        int width = d0Var2.f203a.getWidth() + iRound + i2;
        int height = d0Var2.f203a.getHeight() + iRound2 + i2;
        int i3 = (iRound + width) / 2;
        int i4 = (iRound2 + height) / 2;
        androidx.recyclerview.widget.RecyclerView.o layoutManager = this.r.getLayoutManager();
        int iE = layoutManager.e();
        int i5 = 0;
        while (i5 < iE) {
            android.view.View viewC = layoutManager.c(i5);
            if (viewC != d0Var2.f203a && viewC.getBottom() >= iRound2 && viewC.getTop() <= height && viewC.getRight() >= iRound && viewC.getLeft() <= width) {
                androidx.recyclerview.widget.RecyclerView.d0 d0VarE = this.r.e(viewC);
                if (this.m.a(this.r, this.f309c, d0VarE)) {
                    int iAbs = java.lang.Math.abs(i3 - ((viewC.getLeft() + viewC.getRight()) / 2));
                    int iAbs2 = java.lang.Math.abs(i4 - ((viewC.getTop() + viewC.getBottom()) / 2));
                    int i6 = (iAbs * iAbs) + (iAbs2 * iAbs2);
                    int size = this.u.size();
                    int i7 = 0;
                    for (int i8 = 0; i8 < size && i6 > this.v.get(i8).intValue(); i8++) {
                        i7++;
                    }
                    this.u.add(i7, d0VarE);
                    this.v.add(i7, java.lang.Integer.valueOf(i6));
                }
            }
            i5++;
            d0Var2 = d0Var;
        }
        return this.u;
    }

    private int c(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        if (this.n == 2) {
            return 0;
        }
        int iC = this.m.c(this.r, d0Var);
        int iA = (this.m.a(iC, a.c.e.m.i(this.r)) & 65280) >> 8;
        if (iA == 0) {
            return 0;
        }
        int i2 = (iC & 65280) >> 8;
        if (java.lang.Math.abs(this.h) > java.lang.Math.abs(this.i)) {
            int iB = b(d0Var, iA);
            if (iB > 0) {
                return (i2 & iB) == 0 ? androidx.recyclerview.widget.f.AbstractC0015f.b(iB, a.c.e.m.i(this.r)) : iB;
            }
            int iC2 = c(d0Var, iA);
            if (iC2 > 0) {
                return iC2;
            }
        } else {
            int iC3 = c(d0Var, iA);
            if (iC3 > 0) {
                return iC3;
            }
            int iB2 = b(d0Var, iA);
            if (iB2 > 0) {
                return (i2 & iB2) == 0 ? androidx.recyclerview.widget.f.AbstractC0015f.b(iB2, a.c.e.m.i(this.r)) : iB2;
            }
        }
        return 0;
    }

    private int c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i2) {
        if ((i2 & 3) == 0) {
            return 0;
        }
        int i3 = this.i > 0.0f ? 2 : 1;
        android.view.VelocityTracker velocityTracker = this.t;
        if (velocityTracker != null && this.l > -1) {
            androidx.recyclerview.widget.f.AbstractC0015f abstractC0015f = this.m;
            float f = this.g;
            abstractC0015f.b(f);
            velocityTracker.computeCurrentVelocity(1000, f);
            float xVelocity = this.t.getXVelocity(this.l);
            float yVelocity = this.t.getYVelocity(this.l);
            int i4 = yVelocity <= 0.0f ? 1 : 2;
            float fAbs = java.lang.Math.abs(yVelocity);
            if ((i4 & i2) != 0 && i4 == i3) {
                androidx.recyclerview.widget.f.AbstractC0015f abstractC0015f2 = this.m;
                float f2 = this.f;
                abstractC0015f2.a(f2);
                if (fAbs >= f2 && fAbs > java.lang.Math.abs(xVelocity)) {
                    return i4;
                }
            }
        }
        float height = this.r.getHeight() * this.m.b(d0Var);
        if ((i2 & i3) == 0 || java.lang.Math.abs(this.i) <= height) {
            return 0;
        }
        return i3;
    }

    private androidx.recyclerview.widget.RecyclerView.d0 c(android.view.MotionEvent motionEvent) {
        android.view.View viewB;
        androidx.recyclerview.widget.RecyclerView.o layoutManager = this.r.getLayoutManager();
        int i2 = this.l;
        if (i2 == -1) {
            return null;
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(i2);
        float x = motionEvent.getX(iFindPointerIndex) - this.d;
        float y = motionEvent.getY(iFindPointerIndex) - this.e;
        float fAbs = java.lang.Math.abs(x);
        float fAbs2 = java.lang.Math.abs(y);
        int i3 = this.q;
        if (fAbs < i3 && fAbs2 < i3) {
            return null;
        }
        if (fAbs > fAbs2 && layoutManager.a()) {
            return null;
        }
        if ((fAbs2 <= fAbs || !layoutManager.b()) && (viewB = b(motionEvent)) != null) {
            return this.r.e(viewB);
        }
        return null;
    }

    private void d() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            return;
        }
        if (this.w == null) {
            this.w = new androidx.recyclerview.widget.f.e();
        }
        this.r.setChildDrawingOrderCallback(this.w);
    }

    private void e() {
        this.r.b((androidx.recyclerview.widget.RecyclerView.n) this);
        this.r.b(this.B);
        this.r.b((androidx.recyclerview.widget.RecyclerView.q) this);
        for (int size = this.p.size() - 1; size >= 0; size--) {
            this.m.a(this.r, this.p.get(0).e);
        }
        this.p.clear();
        this.x = null;
        this.y = -1;
        f();
        i();
    }

    private void f() {
        android.view.VelocityTracker velocityTracker = this.t;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.t = null;
        }
    }

    private void g() {
        this.q = android.view.ViewConfiguration.get(this.r.getContext()).getScaledTouchSlop();
        this.r.a((androidx.recyclerview.widget.RecyclerView.n) this);
        this.r.a(this.B);
        this.r.a((androidx.recyclerview.widget.RecyclerView.q) this);
        h();
    }

    private void h() {
        this.A = new androidx.recyclerview.widget.f.g();
        this.z = new a.c.e.b(this.r.getContext(), this.A);
    }

    private void i() {
        androidx.recyclerview.widget.f.g gVar = this.A;
        if (gVar != null) {
            gVar.a();
            this.A = null;
        }
        if (this.z != null) {
            this.z = null;
        }
    }

    androidx.recyclerview.widget.f.h a(android.view.MotionEvent motionEvent) {
        if (this.p.isEmpty()) {
            return null;
        }
        android.view.View viewB = b(motionEvent);
        for (int size = this.p.size() - 1; size >= 0; size--) {
            androidx.recyclerview.widget.f.h hVar = this.p.get(size);
            if (hVar.e.f203a == viewB) {
                return hVar;
            }
        }
        return null;
    }

    void a(int i2, android.view.MotionEvent motionEvent, int i3) {
        androidx.recyclerview.widget.RecyclerView.d0 d0VarC;
        int iB;
        if (this.f309c != null || i2 != 2 || this.n == 2 || !this.m.b() || this.r.getScrollState() == 1 || (d0VarC = c(motionEvent)) == null || (iB = (this.m.b(this.r, d0VarC) & 65280) >> 8) == 0) {
            return;
        }
        float x = motionEvent.getX(i3);
        float y = motionEvent.getY(i3);
        float f = x - this.d;
        float f2 = y - this.e;
        float fAbs = java.lang.Math.abs(f);
        float fAbs2 = java.lang.Math.abs(f2);
        int i4 = this.q;
        if (fAbs >= i4 || fAbs2 >= i4) {
            if (fAbs > fAbs2) {
                if (f < 0.0f && (iB & 4) == 0) {
                    return;
                }
                if (f > 0.0f && (iB & 8) == 0) {
                    return;
                }
            } else {
                if (f2 < 0.0f && (iB & 1) == 0) {
                    return;
                }
                if (f2 > 0.0f && (iB & 2) == 0) {
                    return;
                }
            }
            this.i = 0.0f;
            this.h = 0.0f;
            this.l = motionEvent.getPointerId(0);
            a(d0VarC, 1);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.n
    public void a(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        float f;
        float f2;
        this.y = -1;
        if (this.f309c != null) {
            a(this.f308b);
            float[] fArr = this.f308b;
            float f3 = fArr[0];
            f2 = fArr[1];
            f = f3;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        this.m.a(canvas, recyclerView, this.f309c, this.p, this.n, f, f2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.n
    public void a(android.graphics.Rect rect, android.view.View view, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        rect.setEmpty();
    }

    void a(android.view.MotionEvent motionEvent, int i2, int i3) {
        float x = motionEvent.getX(i3);
        float y = motionEvent.getY(i3);
        float f = x - this.d;
        this.h = f;
        this.i = y - this.e;
        if ((i2 & 4) == 0) {
            this.h = java.lang.Math.max(0.0f, f);
        }
        if ((i2 & 8) == 0) {
            this.h = java.lang.Math.min(0.0f, this.h);
        }
        if ((i2 & 1) == 0) {
            this.i = java.lang.Math.max(0.0f, this.i);
        }
        if ((i2 & 2) == 0) {
            this.i = java.lang.Math.min(0.0f, this.i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.q
    public void a(android.view.View view) {
        c(view);
        androidx.recyclerview.widget.RecyclerView.d0 d0VarE = this.r.e(view);
        if (d0VarE == null) {
            return;
        }
        androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f309c;
        if (d0Var != null && d0VarE == d0Var) {
            a((androidx.recyclerview.widget.RecyclerView.d0) null, 0);
            return;
        }
        a(d0VarE, false);
        if (this.f307a.remove(d0VarE.f203a)) {
            this.m.a(this.r, d0VarE);
        }
    }

    void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        if (!this.r.isLayoutRequested() && this.n == 2) {
            float fA = this.m.a(d0Var);
            int i2 = (int) (this.j + this.h);
            int i3 = (int) (this.k + this.i);
            if (java.lang.Math.abs(i3 - d0Var.f203a.getTop()) >= d0Var.f203a.getHeight() * fA || java.lang.Math.abs(i2 - d0Var.f203a.getLeft()) >= d0Var.f203a.getWidth() * fA) {
                java.util.List<androidx.recyclerview.widget.RecyclerView.d0> listB = b(d0Var);
                if (listB.size() == 0) {
                    return;
                }
                androidx.recyclerview.widget.RecyclerView.d0 d0VarA = this.m.a(d0Var, listB, i2, i3);
                if (d0VarA == null) {
                    this.u.clear();
                    this.v.clear();
                    return;
                }
                int iF = d0VarA.f();
                int iF2 = d0Var.f();
                if (this.m.b(this.r, d0Var, d0VarA)) {
                    this.m.a(this.r, d0Var, iF2, d0VarA, iF, i2, i3);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0122  */
    void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i2) {
        boolean z;
        float fSignum;
        float fSignum2;
        int i3;
        if (d0Var == this.f309c && i2 == this.n) {
            return;
        }
        this.D = Long.MIN_VALUE;
        int i4 = this.n;
        a(d0Var, true);
        this.n = i2;
        if (i2 == 2) {
            if (d0Var == null) {
                throw new java.lang.IllegalArgumentException("Must pass a ViewHolder when dragging");
            }
            this.x = d0Var.f203a;
            d();
        }
        int i5 = (1 << ((i2 * 8) + 8)) - 1;
        androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = this.f309c;
        if (d0Var2 != null) {
            if (d0Var2.f203a.getParent() != null) {
                int iC = i4 == 2 ? 0 : c(d0Var2);
                f();
                if (iC == 1 || iC == 2) {
                    fSignum = java.lang.Math.signum(this.i) * this.r.getHeight();
                    fSignum2 = 0.0f;
                } else {
                    fSignum2 = (iC == 4 || iC == 8 || iC == 16 || iC == 32) ? java.lang.Math.signum(this.h) * this.r.getWidth() : 0.0f;
                    fSignum = 0.0f;
                }
                if (i4 == 2) {
                    i3 = 8;
                } else {
                    i3 = iC > 0 ? 2 : 4;
                }
                a(this.f308b);
                float[] fArr = this.f308b;
                float f = fArr[0];
                float f2 = fArr[1];
                androidx.recyclerview.widget.f.c cVar = new androidx.recyclerview.widget.f.c(d0Var2, i3, i4, f, f2, fSignum2, fSignum, iC, d0Var2);
                cVar.a(this.m.a(this.r, i3, fSignum2 - f, fSignum - f2));
                this.p.add(cVar);
                cVar.b();
                z = true;
            } else {
                c(d0Var2.f203a);
                this.m.a(this.r, d0Var2);
                z = false;
            }
            this.f309c = null;
        } else {
            z = false;
        }
        if (d0Var != null) {
            this.o = (this.m.b(this.r, d0Var) & i5) >> (this.n * 8);
            this.j = d0Var.f203a.getLeft();
            this.k = d0Var.f203a.getTop();
            this.f309c = d0Var;
            if (i2 == 2) {
                d0Var.f203a.performHapticFeedback(0);
            }
        }
        android.view.ViewParent parent = this.r.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(this.f309c != null);
        }
        if (!z) {
            this.r.getLayoutManager().z();
        }
        this.m.a(this.f309c, this.n);
        this.r.invalidate();
    }

    void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, boolean z) {
        for (int size = this.p.size() - 1; size >= 0; size--) {
            androidx.recyclerview.widget.f.h hVar = this.p.get(size);
            if (hVar.e == d0Var) {
                hVar.k |= z;
                if (!hVar.l) {
                    hVar.a();
                }
                this.p.remove(size);
                return;
            }
        }
    }

    public void a(androidx.recyclerview.widget.RecyclerView recyclerView) {
        androidx.recyclerview.widget.RecyclerView recyclerView2 = this.r;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            e();
        }
        this.r = recyclerView;
        if (recyclerView != null) {
            android.content.res.Resources resources = recyclerView.getResources();
            this.f = resources.getDimension(a.f.b.item_touch_helper_swipe_escape_velocity);
            this.g = resources.getDimension(a.f.b.item_touch_helper_swipe_escape_max_velocity);
            g();
        }
    }

    void a(androidx.recyclerview.widget.f.h hVar, int i2) {
        this.r.post(new androidx.recyclerview.widget.f.d(hVar, i2));
    }

    boolean a() {
        int size = this.p.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!this.p.get(i2).l) {
                return true;
            }
        }
        return false;
    }

    android.view.View b(android.view.MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f309c;
        if (d0Var != null) {
            android.view.View view = d0Var.f203a;
            if (a(view, x, y, this.j + this.h, this.k + this.i)) {
                return view;
            }
        }
        for (int size = this.p.size() - 1; size >= 0; size--) {
            androidx.recyclerview.widget.f.h hVar = this.p.get(size);
            android.view.View view2 = hVar.e.f203a;
            if (a(view2, x, y, hVar.i, hVar.j)) {
                return view2;
            }
        }
        return this.r.a(x, y);
    }

    void b() {
        android.view.VelocityTracker velocityTracker = this.t;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.t = android.view.VelocityTracker.obtain();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.n
    public void b(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        float f;
        float f2;
        if (this.f309c != null) {
            a(this.f308b);
            float[] fArr = this.f308b;
            float f3 = fArr[0];
            f2 = fArr[1];
            f = f3;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        this.m.b(canvas, recyclerView, this.f309c, this.p, this.n, f, f2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.q
    public void b(android.view.View view) {
    }

    void c(android.view.View view) {
        if (view == this.x) {
            this.x = null;
            if (this.w != null) {
                this.r.setChildDrawingOrderCallback(null);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x007f  */
    /* JADX WARN: Code duplicated, block: B:36:0x00c8  */
    boolean c() {
        int iA;
        int iA2;
        int i2;
        int width;
        if (this.f309c == null) {
            this.D = Long.MIN_VALUE;
            return false;
        }
        long jCurrentTimeMillis = java.lang.System.currentTimeMillis();
        long j = this.D;
        long j2 = j == Long.MIN_VALUE ? 0L : jCurrentTimeMillis - j;
        androidx.recyclerview.widget.RecyclerView.o layoutManager = this.r.getLayoutManager();
        if (this.C == null) {
            this.C = new android.graphics.Rect();
        }
        layoutManager.a(this.f309c.f203a, this.C);
        if (layoutManager.a()) {
            int i3 = (int) (this.j + this.h);
            int paddingLeft = (i3 - this.C.left) - this.r.getPaddingLeft();
            if (this.h < 0.0f && paddingLeft < 0) {
                iA = paddingLeft;
            } else if (this.h <= 0.0f || (width = ((i3 + this.f309c.f203a.getWidth()) + this.C.right) - (this.r.getWidth() - this.r.getPaddingRight())) <= 0) {
                iA = 0;
            } else {
                iA = width;
            }
        } else {
            iA = 0;
        }
        if (layoutManager.b()) {
            int i4 = (int) (this.k + this.i);
            int paddingTop = (i4 - this.C.top) - this.r.getPaddingTop();
            if (this.i < 0.0f && paddingTop < 0) {
                iA2 = paddingTop;
            } else if (this.i <= 0.0f || (iA2 = ((i4 + this.f309c.f203a.getHeight()) + this.C.bottom) - (this.r.getHeight() - this.r.getPaddingBottom())) <= 0) {
                iA2 = 0;
            }
        } else {
            iA2 = 0;
        }
        if (iA != 0) {
            iA = this.m.a(this.r, this.f309c.f203a.getWidth(), iA, this.r.getWidth(), j2);
        }
        int i5 = iA;
        if (iA2 != 0) {
            i2 = i5;
            iA2 = this.m.a(this.r, this.f309c.f203a.getHeight(), iA2, this.r.getHeight(), j2);
        } else {
            i2 = i5;
        }
        if (i2 == 0 && iA2 == 0) {
            this.D = Long.MIN_VALUE;
            return false;
        }
        if (this.D == Long.MIN_VALUE) {
            this.D = jCurrentTimeMillis;
        }
        this.r.scrollBy(i2, iA2);
        return true;
    }
}
