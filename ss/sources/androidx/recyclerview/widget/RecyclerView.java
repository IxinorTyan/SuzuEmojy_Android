package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerView extends android.view.ViewGroup implements a.c.e.e, a.c.e.f {
    static final boolean A0;
    static final boolean B0;
    static final boolean C0;
    private static final boolean D0;
    private static final boolean E0;
    private static final java.lang.Class<?>[] F0;
    static final android.view.animation.Interpolator G0;
    private static final int[] y0 = {android.R.attr.nestedScrollingEnabled};
    static final boolean z0;
    boolean A;
    private final android.view.accessibility.AccessibilityManager B;
    private java.util.List<androidx.recyclerview.widget.RecyclerView.q> C;
    boolean D;
    boolean E;
    private int F;
    private int G;
    private androidx.recyclerview.widget.RecyclerView.k H;
    private android.widget.EdgeEffect I;
    private android.widget.EdgeEffect J;
    private android.widget.EdgeEffect K;
    private android.widget.EdgeEffect L;
    androidx.recyclerview.widget.RecyclerView.l M;
    private int N;
    private int O;
    private android.view.VelocityTracker P;
    private int Q;
    private int R;
    private int S;
    private int T;
    private int U;
    private androidx.recyclerview.widget.RecyclerView.r V;
    private final int W;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final androidx.recyclerview.widget.RecyclerView.x f191a;
    private final int a0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final androidx.recyclerview.widget.RecyclerView.v f192b;
    private float b0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private androidx.recyclerview.widget.RecyclerView.y f193c;
    private float c0;
    androidx.recyclerview.widget.a d;
    private boolean d0;
    androidx.recyclerview.widget.b e;
    final androidx.recyclerview.widget.RecyclerView.c0 e0;
    final androidx.recyclerview.widget.p f;
    androidx.recyclerview.widget.e f0;
    boolean g;
    androidx.recyclerview.widget.e.b g0;
    final java.lang.Runnable h;
    final androidx.recyclerview.widget.RecyclerView.a0 h0;
    final android.graphics.Rect i;
    private androidx.recyclerview.widget.RecyclerView.t i0;
    private final android.graphics.Rect j;
    private java.util.List<androidx.recyclerview.widget.RecyclerView.t> j0;
    final android.graphics.RectF k;
    boolean k0;
    androidx.recyclerview.widget.RecyclerView.g l;
    boolean l0;
    androidx.recyclerview.widget.RecyclerView.o m;
    private androidx.recyclerview.widget.RecyclerView.l.b m0;
    androidx.recyclerview.widget.RecyclerView.w n;
    boolean n0;
    final java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.n> o;
    androidx.recyclerview.widget.l o0;
    private final java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.s> p;
    private androidx.recyclerview.widget.RecyclerView.j p0;
    private androidx.recyclerview.widget.RecyclerView.s q;
    private final int[] q0;
    boolean r;
    private a.c.e.h r0;
    boolean s;
    private final int[] s0;
    boolean t;
    private final int[] t0;
    boolean u;
    final int[] u0;
    private int v;
    final java.util.List<androidx.recyclerview.widget.RecyclerView.d0> v0;
    boolean w;
    private java.lang.Runnable w0;
    boolean x;
    private final androidx.recyclerview.widget.p.b x0;
    private boolean y;
    private int z;

    class a implements java.lang.Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
            if (!recyclerView.u || recyclerView.isLayoutRequested()) {
                return;
            }
            androidx.recyclerview.widget.RecyclerView recyclerView2 = androidx.recyclerview.widget.RecyclerView.this;
            if (!recyclerView2.r) {
                recyclerView2.requestLayout();
            } else if (recyclerView2.x) {
                recyclerView2.w = true;
            } else {
                recyclerView2.b();
            }
        }
    }

    public static class a0 {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private android.util.SparseArray<java.lang.Object> f196b;
        int m;
        long n;
        int o;
        int p;
        int q;

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f195a = -1;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f197c = 0;
        int d = 0;
        int e = 1;
        int f = 0;
        boolean g = false;
        boolean h = false;
        boolean i = false;
        boolean j = false;
        boolean k = false;
        boolean l = false;

        public int a() {
            return this.h ? this.f197c - this.d : this.f;
        }

        void a(int i) {
            if ((this.e & i) != 0) {
                return;
            }
            throw new java.lang.IllegalStateException("Layout state should be one of " + java.lang.Integer.toBinaryString(i) + " but it is " + java.lang.Integer.toBinaryString(this.e));
        }

        void a(androidx.recyclerview.widget.RecyclerView.g gVar) {
            this.e = 1;
            this.f = gVar.a();
            this.h = false;
            this.i = false;
            this.j = false;
        }

        public int b() {
            return this.f195a;
        }

        public boolean c() {
            return this.f195a != -1;
        }

        public boolean d() {
            return this.h;
        }

        public boolean e() {
            return this.l;
        }

        public java.lang.String toString() {
            return "State{mTargetPosition=" + this.f195a + ", mData=" + this.f196b + ", mItemCount=" + this.f + ", mIsMeasuring=" + this.j + ", mPreviousLayoutItemCount=" + this.f197c + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.d + ", mStructureChanged=" + this.g + ", mInPreLayout=" + this.h + ", mRunSimpleAnimations=" + this.k + ", mRunPredictiveAnimations=" + this.l + '}';
        }
    }

    class b implements java.lang.Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            androidx.recyclerview.widget.RecyclerView.l lVar = androidx.recyclerview.widget.RecyclerView.this.M;
            if (lVar != null) {
                lVar.i();
            }
            androidx.recyclerview.widget.RecyclerView.this.n0 = false;
        }
    }

    public static abstract class b0 {
        public abstract android.view.View a(androidx.recyclerview.widget.RecyclerView.v vVar, int i, int i2);
    }

    static class c implements android.view.animation.Interpolator {
        c() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    }

    class c0 implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private int f199a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private int f200b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        android.widget.OverScroller f201c;
        android.view.animation.Interpolator d = androidx.recyclerview.widget.RecyclerView.G0;
        private boolean e = false;
        private boolean f = false;

        c0() {
            this.f201c = new android.widget.OverScroller(androidx.recyclerview.widget.RecyclerView.this.getContext(), androidx.recyclerview.widget.RecyclerView.G0);
        }

        private float a(float f) {
            return (float) java.lang.Math.sin((f - 0.5f) * 0.47123894f);
        }

        private int a(int i, int i2, int i3, int i4) {
            int iRound;
            int iAbs = java.lang.Math.abs(i);
            int iAbs2 = java.lang.Math.abs(i2);
            boolean z = iAbs > iAbs2;
            int iSqrt = (int) java.lang.Math.sqrt((i3 * i3) + (i4 * i4));
            int iSqrt2 = (int) java.lang.Math.sqrt((i * i) + (i2 * i2));
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
            int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
            int i5 = width / 2;
            float f = width;
            float f2 = i5;
            float fA = f2 + (a(java.lang.Math.min(1.0f, (iSqrt2 * 1.0f) / f)) * f2);
            if (iSqrt > 0) {
                iRound = java.lang.Math.round(java.lang.Math.abs(fA / iSqrt) * 1000.0f) * 4;
            } else {
                if (!z) {
                    iAbs = iAbs2;
                }
                iRound = (int) (((iAbs / f) + 1.0f) * 300.0f);
            }
            return java.lang.Math.min(iRound, 2000);
        }

        private void c() {
            androidx.recyclerview.widget.RecyclerView.this.removeCallbacks(this);
            a.c.e.m.a(androidx.recyclerview.widget.RecyclerView.this, this);
        }

        void a() {
            if (this.e) {
                this.f = true;
            } else {
                c();
            }
        }

        public void a(int i, int i2) {
            androidx.recyclerview.widget.RecyclerView.this.setScrollState(2);
            this.f200b = 0;
            this.f199a = 0;
            android.view.animation.Interpolator interpolator = this.d;
            android.view.animation.Interpolator interpolator2 = androidx.recyclerview.widget.RecyclerView.G0;
            if (interpolator != interpolator2) {
                this.d = interpolator2;
                this.f201c = new android.widget.OverScroller(androidx.recyclerview.widget.RecyclerView.this.getContext(), androidx.recyclerview.widget.RecyclerView.G0);
            }
            this.f201c.fling(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            a();
        }

        public void a(int i, int i2, int i3, android.view.animation.Interpolator interpolator) {
            if (i3 == Integer.MIN_VALUE) {
                i3 = a(i, i2, 0, 0);
            }
            int i4 = i3;
            if (interpolator == null) {
                interpolator = androidx.recyclerview.widget.RecyclerView.G0;
            }
            if (this.d != interpolator) {
                this.d = interpolator;
                this.f201c = new android.widget.OverScroller(androidx.recyclerview.widget.RecyclerView.this.getContext(), interpolator);
            }
            this.f200b = 0;
            this.f199a = 0;
            androidx.recyclerview.widget.RecyclerView.this.setScrollState(2);
            this.f201c.startScroll(0, 0, i, i2, i4);
            if (android.os.Build.VERSION.SDK_INT < 23) {
                this.f201c.computeScrollOffset();
            }
            a();
        }

        public void b() {
            androidx.recyclerview.widget.RecyclerView.this.removeCallbacks(this);
            this.f201c.abortAnimation();
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            int i2;
            int i3;
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
            if (recyclerView.m == null) {
                b();
                return;
            }
            this.f = false;
            this.e = true;
            recyclerView.b();
            android.widget.OverScroller overScroller = this.f201c;
            if (overScroller.computeScrollOffset()) {
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int i4 = currX - this.f199a;
                int i5 = currY - this.f200b;
                this.f199a = currX;
                this.f200b = currY;
                androidx.recyclerview.widget.RecyclerView recyclerView2 = androidx.recyclerview.widget.RecyclerView.this;
                int[] iArr = recyclerView2.u0;
                iArr[0] = 0;
                iArr[1] = 0;
                if (recyclerView2.a(i4, i5, iArr, (int[]) null, 1)) {
                    int[] iArr2 = androidx.recyclerview.widget.RecyclerView.this.u0;
                    i4 -= iArr2[0];
                    i5 -= iArr2[1];
                }
                if (androidx.recyclerview.widget.RecyclerView.this.getOverScrollMode() != 2) {
                    androidx.recyclerview.widget.RecyclerView.this.b(i4, i5);
                }
                androidx.recyclerview.widget.RecyclerView recyclerView3 = androidx.recyclerview.widget.RecyclerView.this;
                if (recyclerView3.l != null) {
                    int[] iArr3 = recyclerView3.u0;
                    iArr3[0] = 0;
                    iArr3[1] = 0;
                    recyclerView3.a(i4, i5, iArr3);
                    androidx.recyclerview.widget.RecyclerView recyclerView4 = androidx.recyclerview.widget.RecyclerView.this;
                    int[] iArr4 = recyclerView4.u0;
                    i2 = iArr4[0];
                    i = iArr4[1];
                    i4 -= i2;
                    i5 -= i;
                    androidx.recyclerview.widget.RecyclerView.z zVar = recyclerView4.m.g;
                    if (zVar != null && !zVar.b() && zVar.c()) {
                        int iA = androidx.recyclerview.widget.RecyclerView.this.h0.a();
                        if (iA == 0) {
                            zVar.d();
                        } else {
                            if (zVar.a() >= iA) {
                                zVar.a(iA - 1);
                            }
                            zVar.a(i2, i);
                        }
                    }
                } else {
                    i = 0;
                    i2 = 0;
                }
                if (!androidx.recyclerview.widget.RecyclerView.this.o.isEmpty()) {
                    androidx.recyclerview.widget.RecyclerView.this.invalidate();
                }
                androidx.recyclerview.widget.RecyclerView recyclerView5 = androidx.recyclerview.widget.RecyclerView.this;
                int[] iArr5 = recyclerView5.u0;
                iArr5[0] = 0;
                iArr5[1] = 0;
                recyclerView5.a(i2, i, i4, i5, null, 1, iArr5);
                int[] iArr6 = androidx.recyclerview.widget.RecyclerView.this.u0;
                int i6 = i4 - iArr6[0];
                int i7 = i5 - iArr6[1];
                if (i2 != 0 || i != 0) {
                    androidx.recyclerview.widget.RecyclerView.this.d(i2, i);
                }
                if (!androidx.recyclerview.widget.RecyclerView.this.awakenScrollBars()) {
                    androidx.recyclerview.widget.RecyclerView.this.invalidate();
                }
                boolean z = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i6 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i7 != 0));
                androidx.recyclerview.widget.RecyclerView.z zVar2 = androidx.recyclerview.widget.RecyclerView.this.m.g;
                if ((zVar2 != null && zVar2.b()) || !z) {
                    a();
                    androidx.recyclerview.widget.RecyclerView recyclerView6 = androidx.recyclerview.widget.RecyclerView.this;
                    androidx.recyclerview.widget.e eVar = recyclerView6.f0;
                    if (eVar != null) {
                        eVar.a(recyclerView6, i2, i);
                    }
                } else {
                    if (androidx.recyclerview.widget.RecyclerView.this.getOverScrollMode() != 2) {
                        int currVelocity = (int) overScroller.getCurrVelocity();
                        if (i6 < 0) {
                            i3 = -currVelocity;
                        } else {
                            i3 = i6 > 0 ? currVelocity : 0;
                        }
                        if (i7 < 0) {
                            currVelocity = -currVelocity;
                        } else if (i7 <= 0) {
                            currVelocity = 0;
                        }
                        androidx.recyclerview.widget.RecyclerView.this.a(i3, currVelocity);
                    }
                    if (androidx.recyclerview.widget.RecyclerView.C0) {
                        androidx.recyclerview.widget.RecyclerView.this.g0.a();
                    }
                }
            }
            androidx.recyclerview.widget.RecyclerView.z zVar3 = androidx.recyclerview.widget.RecyclerView.this.m.g;
            if (zVar3 != null && zVar3.b()) {
                zVar3.a(0, 0);
            }
            this.e = false;
            if (this.f) {
                c();
            } else {
                androidx.recyclerview.widget.RecyclerView.this.setScrollState(0);
                androidx.recyclerview.widget.RecyclerView.this.g(1);
            }
        }
    }

    class d implements androidx.recyclerview.widget.p.b {
        d() {
        }

        @Override // androidx.recyclerview.widget.p.b
        public void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
            recyclerView.m.a(d0Var.f203a, recyclerView.f192b);
        }

        @Override // androidx.recyclerview.widget.p.b
        public void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
            androidx.recyclerview.widget.RecyclerView.this.f192b.c(d0Var);
            androidx.recyclerview.widget.RecyclerView.this.b(d0Var, cVar, cVar2);
        }

        @Override // androidx.recyclerview.widget.p.b
        public void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
            d0Var.a(false);
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
            boolean z = recyclerView.D;
            androidx.recyclerview.widget.RecyclerView.l lVar = recyclerView.M;
            if (z) {
                if (!lVar.a(d0Var, d0Var, cVar, cVar2)) {
                    return;
                }
            } else if (!lVar.c(d0Var, cVar, cVar2)) {
                return;
            }
            androidx.recyclerview.widget.RecyclerView.this.s();
        }

        @Override // androidx.recyclerview.widget.p.b
        public void c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
            androidx.recyclerview.widget.RecyclerView.this.a(d0Var, cVar, cVar2);
        }
    }

    public static abstract class d0 {
        private static final java.util.List<java.lang.Object> s = java.util.Collections.emptyList();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final android.view.View f203a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        java.lang.ref.WeakReference<androidx.recyclerview.widget.RecyclerView> f204b;
        int j;
        androidx.recyclerview.widget.RecyclerView r;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f205c = -1;
        int d = -1;
        long e = -1;
        int f = -1;
        int g = -1;
        androidx.recyclerview.widget.RecyclerView.d0 h = null;
        androidx.recyclerview.widget.RecyclerView.d0 i = null;
        java.util.List<java.lang.Object> k = null;
        java.util.List<java.lang.Object> l = null;
        private int m = 0;
        androidx.recyclerview.widget.RecyclerView.v n = null;
        boolean o = false;
        private int p = 0;
        int q = -1;

        public d0(android.view.View view) {
            if (view == null) {
                throw new java.lang.IllegalArgumentException("itemView may not be null");
            }
            this.f203a = view;
        }

        private void B() {
            if (this.k == null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                this.k = arrayList;
                this.l = java.util.Collections.unmodifiableList(arrayList);
            }
        }

        boolean A() {
            return (this.j & 32) != 0;
        }

        void a() {
            this.d = -1;
            this.g = -1;
        }

        void a(int i) {
            this.j = i | this.j;
        }

        void a(int i, int i2) {
            this.j = (i & i2) | (this.j & (~i2));
        }

        void a(int i, int i2, boolean z) {
            a(8);
            a(i2, z);
            this.f205c = i;
        }

        void a(int i, boolean z) {
            if (this.d == -1) {
                this.d = this.f205c;
            }
            if (this.g == -1) {
                this.g = this.f205c;
            }
            if (z) {
                this.g += i;
            }
            this.f205c += i;
            if (this.f203a.getLayoutParams() != null) {
                ((androidx.recyclerview.widget.RecyclerView.p) this.f203a.getLayoutParams()).f226c = true;
            }
        }

        void a(androidx.recyclerview.widget.RecyclerView.v vVar, boolean z) {
            this.n = vVar;
            this.o = z;
        }

        void a(androidx.recyclerview.widget.RecyclerView recyclerView) {
            int iG = this.q;
            if (iG == -1) {
                iG = a.c.e.m.g(this.f203a);
            }
            this.p = iG;
            recyclerView.a(this, 4);
        }

        void a(java.lang.Object obj) {
            if (obj == null) {
                a(1024);
            } else if ((1024 & this.j) == 0) {
                B();
                this.k.add(obj);
            }
        }

        public final void a(boolean z) {
            int i;
            int i2 = this.m;
            int i3 = z ? i2 - 1 : i2 + 1;
            this.m = i3;
            if (i3 < 0) {
                this.m = 0;
                android.util.Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
                return;
            }
            if (!z && i3 == 1) {
                i = this.j | 16;
            } else if (!z || this.m != 0) {
                return;
            } else {
                i = this.j & (-17);
            }
            this.j = i;
        }

        void b() {
            java.util.List<java.lang.Object> list = this.k;
            if (list != null) {
                list.clear();
            }
            this.j &= -1025;
        }

        void b(androidx.recyclerview.widget.RecyclerView recyclerView) {
            recyclerView.a(this, this.p);
            this.p = 0;
        }

        boolean b(int i) {
            return (i & this.j) != 0;
        }

        void c() {
            this.j &= -33;
        }

        void d() {
            this.j &= -257;
        }

        boolean e() {
            return (this.j & 16) == 0 && a.c.e.m.l(this.f203a);
        }

        public final int f() {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.r;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.b(this);
        }

        public final long g() {
            return this.e;
        }

        public final int h() {
            return this.f;
        }

        public final int i() {
            int i = this.g;
            return i == -1 ? this.f205c : i;
        }

        public final int j() {
            return this.d;
        }

        java.util.List<java.lang.Object> k() {
            if ((this.j & 1024) != 0) {
                return s;
            }
            java.util.List<java.lang.Object> list = this.k;
            return (list == null || list.size() == 0) ? s : this.l;
        }

        boolean l() {
            return (this.j & 512) != 0 || o();
        }

        boolean m() {
            return (this.f203a.getParent() == null || this.f203a.getParent() == this.r) ? false : true;
        }

        boolean n() {
            return (this.j & 1) != 0;
        }

        boolean o() {
            return (this.j & 4) != 0;
        }

        public final boolean p() {
            return (this.j & 16) == 0 && !a.c.e.m.l(this.f203a);
        }

        boolean q() {
            return (this.j & 8) != 0;
        }

        boolean r() {
            return this.n != null;
        }

        boolean s() {
            return (this.j & 256) != 0;
        }

        boolean t() {
            return (this.j & 2) != 0;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder((getClass().isAnonymousClass() ? "ViewHolder" : getClass().getSimpleName()) + "{" + java.lang.Integer.toHexString(hashCode()) + " position=" + this.f205c + " id=" + this.e + ", oldPos=" + this.d + ", pLpos:" + this.g);
            if (r()) {
                sb.append(" scrap ");
                sb.append(this.o ? "[changeScrap]" : "[attachedScrap]");
            }
            if (o()) {
                sb.append(" invalid");
            }
            if (!n()) {
                sb.append(" unbound");
            }
            if (u()) {
                sb.append(" update");
            }
            if (q()) {
                sb.append(" removed");
            }
            if (y()) {
                sb.append(" ignored");
            }
            if (s()) {
                sb.append(" tmpDetached");
            }
            if (!p()) {
                sb.append(" not recyclable(" + this.m + ")");
            }
            if (l()) {
                sb.append(" undefined adapter position");
            }
            if (this.f203a.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        boolean u() {
            return (this.j & 2) != 0;
        }

        void v() {
            this.j = 0;
            this.f205c = -1;
            this.d = -1;
            this.e = -1L;
            this.g = -1;
            this.m = 0;
            this.h = null;
            this.i = null;
            b();
            this.p = 0;
            this.q = -1;
            androidx.recyclerview.widget.RecyclerView.e(this);
        }

        void w() {
            if (this.d == -1) {
                this.d = this.f205c;
            }
        }

        boolean x() {
            return (this.j & 16) != 0;
        }

        boolean y() {
            return (this.j & 128) != 0;
        }

        void z() {
            this.n.c(this);
        }
    }

    class e implements androidx.recyclerview.widget.b.InterfaceC0012b {
        e() {
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public int a() {
            return androidx.recyclerview.widget.RecyclerView.this.getChildCount();
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public android.view.View a(int i) {
            return androidx.recyclerview.widget.RecyclerView.this.getChildAt(i);
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public void a(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (d0VarK != null) {
                d0VarK.b(androidx.recyclerview.widget.RecyclerView.this);
            }
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public void a(android.view.View view, int i) {
            androidx.recyclerview.widget.RecyclerView.this.addView(view, i);
            androidx.recyclerview.widget.RecyclerView.this.a(view);
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public void a(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (d0VarK != null) {
                if (!d0VarK.s() && !d0VarK.y()) {
                    throw new java.lang.IllegalArgumentException("Called attach on a child which is not detached: " + d0VarK + androidx.recyclerview.widget.RecyclerView.this.i());
                }
                d0VarK.d();
            }
            androidx.recyclerview.widget.RecyclerView.this.attachViewToParent(view, i, layoutParams);
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public int b(android.view.View view) {
            return androidx.recyclerview.widget.RecyclerView.this.indexOfChild(view);
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public void b() {
            int iA = a();
            for (int i = 0; i < iA; i++) {
                android.view.View viewA = a(i);
                androidx.recyclerview.widget.RecyclerView.this.b(viewA);
                viewA.clearAnimation();
            }
            androidx.recyclerview.widget.RecyclerView.this.removeAllViews();
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public void b(int i) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK;
            android.view.View viewA = a(i);
            if (viewA != null && (d0VarK = androidx.recyclerview.widget.RecyclerView.k(viewA)) != null) {
                if (d0VarK.s() && !d0VarK.y()) {
                    throw new java.lang.IllegalArgumentException("called detach on an already detached child " + d0VarK + androidx.recyclerview.widget.RecyclerView.this.i());
                }
                d0VarK.a(256);
            }
            androidx.recyclerview.widget.RecyclerView.this.detachViewFromParent(i);
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public void c(int i) {
            android.view.View childAt = androidx.recyclerview.widget.RecyclerView.this.getChildAt(i);
            if (childAt != null) {
                androidx.recyclerview.widget.RecyclerView.this.b(childAt);
                childAt.clearAnimation();
            }
            androidx.recyclerview.widget.RecyclerView.this.removeViewAt(i);
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public void c(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (d0VarK != null) {
                d0VarK.a(androidx.recyclerview.widget.RecyclerView.this);
            }
        }

        @Override // androidx.recyclerview.widget.b.InterfaceC0012b
        public androidx.recyclerview.widget.RecyclerView.d0 d(android.view.View view) {
            return androidx.recyclerview.widget.RecyclerView.k(view);
        }
    }

    class f implements androidx.recyclerview.widget.a.InterfaceC0011a {
        f() {
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public androidx.recyclerview.widget.RecyclerView.d0 a(int i) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarA = androidx.recyclerview.widget.RecyclerView.this.a(i, true);
            if (d0VarA == null || androidx.recyclerview.widget.RecyclerView.this.e.c(d0VarA.f203a)) {
                return null;
            }
            return d0VarA;
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public void a(int i, int i2) {
            androidx.recyclerview.widget.RecyclerView.this.f(i, i2);
            androidx.recyclerview.widget.RecyclerView.this.k0 = true;
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public void a(int i, int i2, java.lang.Object obj) {
            androidx.recyclerview.widget.RecyclerView.this.a(i, i2, obj);
            androidx.recyclerview.widget.RecyclerView.this.l0 = true;
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public void a(androidx.recyclerview.widget.a.b bVar) {
            c(bVar);
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public void b(int i, int i2) {
            androidx.recyclerview.widget.RecyclerView.this.g(i, i2);
            androidx.recyclerview.widget.RecyclerView.this.k0 = true;
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public void b(androidx.recyclerview.widget.a.b bVar) {
            c(bVar);
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public void c(int i, int i2) {
            androidx.recyclerview.widget.RecyclerView.this.a(i, i2, true);
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
            recyclerView.k0 = true;
            recyclerView.h0.d += i2;
        }

        void c(androidx.recyclerview.widget.a.b bVar) {
            int i = bVar.f255a;
            if (i == 1) {
                androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
                recyclerView.m.a(recyclerView, bVar.f256b, bVar.d);
                return;
            }
            if (i == 2) {
                androidx.recyclerview.widget.RecyclerView recyclerView2 = androidx.recyclerview.widget.RecyclerView.this;
                recyclerView2.m.b(recyclerView2, bVar.f256b, bVar.d);
            } else if (i == 4) {
                androidx.recyclerview.widget.RecyclerView recyclerView3 = androidx.recyclerview.widget.RecyclerView.this;
                recyclerView3.m.a(recyclerView3, bVar.f256b, bVar.d, bVar.f257c);
            } else {
                if (i != 8) {
                    return;
                }
                androidx.recyclerview.widget.RecyclerView recyclerView4 = androidx.recyclerview.widget.RecyclerView.this;
                recyclerView4.m.a(recyclerView4, bVar.f256b, bVar.d, 1);
            }
        }

        @Override // androidx.recyclerview.widget.a.InterfaceC0011a
        public void d(int i, int i2) {
            androidx.recyclerview.widget.RecyclerView.this.a(i, i2, false);
            androidx.recyclerview.widget.RecyclerView.this.k0 = true;
        }
    }

    public static abstract class g<VH extends androidx.recyclerview.widget.RecyclerView.d0> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final androidx.recyclerview.widget.RecyclerView.h f208a = new androidx.recyclerview.widget.RecyclerView.h();

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private boolean f209b = false;

        public abstract int a();

        public long a(int i) {
            return -1L;
        }

        public final VH a(android.view.ViewGroup viewGroup, int i) {
            try {
                a.c.c.a.a("RV CreateView");
                VH vh = (VH) b(viewGroup, i);
                if (vh.f203a.getParent() != null) {
                    throw new java.lang.IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                }
                vh.f = i;
                a.c.c.a.a();
                return vh;
            } catch (java.lang.Throwable th) {
                a.c.c.a.a();
                throw th;
            }
        }

        public final void a(int i, int i2) {
            this.f208a.a(i, i2);
        }

        public final void a(VH vh, int i) {
            vh.f205c = i;
            if (b()) {
                vh.e = a(i);
            }
            vh.a(1, 519);
            a.c.c.a.a("RV OnBindView");
            a(vh, i, vh.k());
            vh.b();
            android.view.ViewGroup.LayoutParams layoutParams = vh.f203a.getLayoutParams();
            if (layoutParams instanceof androidx.recyclerview.widget.RecyclerView.p) {
                ((androidx.recyclerview.widget.RecyclerView.p) layoutParams).f226c = true;
            }
            a.c.c.a.a();
        }

        public void a(VH vh, int i, java.util.List<java.lang.Object> list) {
            b(vh, i);
        }

        public void a(androidx.recyclerview.widget.RecyclerView.i iVar) {
            this.f208a.registerObserver(iVar);
        }

        public void a(androidx.recyclerview.widget.RecyclerView recyclerView) {
        }

        public boolean a(VH vh) {
            return false;
        }

        public int b(int i) {
            return 0;
        }

        public abstract VH b(android.view.ViewGroup viewGroup, int i);

        public void b(VH vh) {
        }

        public abstract void b(VH vh, int i);

        public void b(androidx.recyclerview.widget.RecyclerView.i iVar) {
            this.f208a.unregisterObserver(iVar);
        }

        public void b(androidx.recyclerview.widget.RecyclerView recyclerView) {
        }

        public final boolean b() {
            return this.f209b;
        }

        public void c(VH vh) {
        }

        public void d(VH vh) {
        }
    }

    static class h extends android.database.Observable<androidx.recyclerview.widget.RecyclerView.i> {
        h() {
        }

        public void a(int i, int i2) {
            for (int size = ((android.database.Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((androidx.recyclerview.widget.RecyclerView.i) ((android.database.Observable) this).mObservers.get(size)).a(i, i2, 1);
            }
        }
    }

    public static abstract class i {
        public void a(int i, int i2, int i3) {
        }
    }

    public interface j {
        int a(int i, int i2);
    }

    public static class k {
        protected android.widget.EdgeEffect a(androidx.recyclerview.widget.RecyclerView recyclerView, int i) {
            return new android.widget.EdgeEffect(recyclerView.getContext());
        }
    }

    public static abstract class l {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private androidx.recyclerview.widget.RecyclerView.l.b f210a = null;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.l.a> f211b = new java.util.ArrayList<>();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private long f212c = 120;
        private long d = 120;
        private long e = 250;
        private long f = 250;

        public interface a {
            void a();
        }

        interface b {
            void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var);
        }

        public static class c {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            public int f213a;

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            public int f214b;

            public androidx.recyclerview.widget.RecyclerView.l.c a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
                a(d0Var, 0);
                return this;
            }

            public androidx.recyclerview.widget.RecyclerView.l.c a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i) {
                android.view.View view = d0Var.f203a;
                this.f213a = view.getLeft();
                this.f214b = view.getTop();
                view.getRight();
                view.getBottom();
                return this;
            }
        }

        static int e(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            int i = d0Var.j & 14;
            if (d0Var.o()) {
                return 4;
            }
            if ((i & 4) != 0) {
                return i;
            }
            int iJ = d0Var.j();
            int iF = d0Var.f();
            return (iJ == -1 || iF == -1 || iJ == iF) ? i : i | 2048;
        }

        public androidx.recyclerview.widget.RecyclerView.l.c a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            androidx.recyclerview.widget.RecyclerView.l.c cVarH = h();
            cVarH.a(d0Var);
            return cVarH;
        }

        public androidx.recyclerview.widget.RecyclerView.l.c a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, java.util.List<java.lang.Object> list) {
            androidx.recyclerview.widget.RecyclerView.l.c cVarH = h();
            cVarH.a(d0Var);
            return cVarH;
        }

        public final void a() {
            int size = this.f211b.size();
            for (int i = 0; i < size; i++) {
                this.f211b.get(i).a();
            }
            this.f211b.clear();
        }

        void a(androidx.recyclerview.widget.RecyclerView.l.b bVar) {
            this.f210a = bVar;
        }

        public abstract boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var);

        public abstract boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2);

        public abstract boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2);

        public boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, java.util.List<java.lang.Object> list) {
            return a(d0Var);
        }

        public final boolean a(androidx.recyclerview.widget.RecyclerView.l.a aVar) {
            boolean zG = g();
            if (aVar != null) {
                if (zG) {
                    this.f211b.add(aVar);
                } else {
                    aVar.a();
                }
            }
            return zG;
        }

        public abstract void b();

        public final void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            d(d0Var);
            androidx.recyclerview.widget.RecyclerView.l.b bVar = this.f210a;
            if (bVar != null) {
                bVar.a(d0Var);
            }
        }

        public abstract boolean b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2);

        public long c() {
            return this.f212c;
        }

        public abstract void c(androidx.recyclerview.widget.RecyclerView.d0 d0Var);

        public abstract boolean c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2);

        public long d() {
            return this.f;
        }

        public void d(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        }

        public long e() {
            return this.e;
        }

        public long f() {
            return this.d;
        }

        public abstract boolean g();

        public androidx.recyclerview.widget.RecyclerView.l.c h() {
            return new androidx.recyclerview.widget.RecyclerView.l.c();
        }

        public abstract void i();
    }

    private class m implements androidx.recyclerview.widget.RecyclerView.l.b {
        m() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.l.b
        public void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            d0Var.a(true);
            if (d0Var.h != null && d0Var.i == null) {
                d0Var.h = null;
            }
            d0Var.i = null;
            if (d0Var.x() || androidx.recyclerview.widget.RecyclerView.this.i(d0Var.f203a) || !d0Var.s()) {
                return;
            }
            androidx.recyclerview.widget.RecyclerView.this.removeDetachedView(d0Var.f203a, false);
        }
    }

    public static abstract class n {
        @java.lang.Deprecated
        public void a(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView) {
        }

        public void a(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            a(canvas, recyclerView);
        }

        @java.lang.Deprecated
        public void a(android.graphics.Rect rect, int i, androidx.recyclerview.widget.RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void a(android.graphics.Rect rect, android.view.View view, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            a(rect, ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).a(), recyclerView);
        }

        @java.lang.Deprecated
        public void b(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView) {
        }

        public void b(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            b(canvas, recyclerView);
        }
    }

    public static abstract class o {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        androidx.recyclerview.widget.b f216a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        androidx.recyclerview.widget.RecyclerView f217b;
        androidx.recyclerview.widget.RecyclerView.z g;
        int m;
        boolean n;
        private int o;
        private int p;
        private int q;
        private int r;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private final androidx.recyclerview.widget.o.b f218c = new androidx.recyclerview.widget.RecyclerView.o.a();
        private final androidx.recyclerview.widget.o.b d = new androidx.recyclerview.widget.RecyclerView.o.b();
        androidx.recyclerview.widget.o e = new androidx.recyclerview.widget.o(this.f218c);
        androidx.recyclerview.widget.o f = new androidx.recyclerview.widget.o(this.d);
        boolean h = false;
        boolean i = false;
        boolean j = false;
        private boolean k = true;
        private boolean l = true;

        class a implements androidx.recyclerview.widget.o.b {
            a() {
            }

            @Override // androidx.recyclerview.widget.o.b
            public int a() {
                return androidx.recyclerview.widget.RecyclerView.o.this.q() - androidx.recyclerview.widget.RecyclerView.o.this.o();
            }

            @Override // androidx.recyclerview.widget.o.b
            public int a(android.view.View view) {
                return androidx.recyclerview.widget.RecyclerView.o.this.f(view) - ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).leftMargin;
            }

            @Override // androidx.recyclerview.widget.o.b
            public android.view.View a(int i) {
                return androidx.recyclerview.widget.RecyclerView.o.this.c(i);
            }

            @Override // androidx.recyclerview.widget.o.b
            public int b() {
                return androidx.recyclerview.widget.RecyclerView.o.this.n();
            }

            @Override // androidx.recyclerview.widget.o.b
            public int b(android.view.View view) {
                return androidx.recyclerview.widget.RecyclerView.o.this.i(view) + ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).rightMargin;
            }
        }

        class b implements androidx.recyclerview.widget.o.b {
            b() {
            }

            @Override // androidx.recyclerview.widget.o.b
            public int a() {
                return androidx.recyclerview.widget.RecyclerView.o.this.h() - androidx.recyclerview.widget.RecyclerView.o.this.m();
            }

            @Override // androidx.recyclerview.widget.o.b
            public int a(android.view.View view) {
                return androidx.recyclerview.widget.RecyclerView.o.this.j(view) - ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).topMargin;
            }

            @Override // androidx.recyclerview.widget.o.b
            public android.view.View a(int i) {
                return androidx.recyclerview.widget.RecyclerView.o.this.c(i);
            }

            @Override // androidx.recyclerview.widget.o.b
            public int b() {
                return androidx.recyclerview.widget.RecyclerView.o.this.p();
            }

            @Override // androidx.recyclerview.widget.o.b
            public int b(android.view.View view) {
                return androidx.recyclerview.widget.RecyclerView.o.this.e(view) + ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).bottomMargin;
            }
        }

        public interface c {
            void a(int i, int i2);
        }

        public static class d {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            public int f221a;

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            public int f222b;

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            public boolean f223c;
            public boolean d;
        }

        public static int a(int i, int i2, int i3) {
            int mode = android.view.View.MeasureSpec.getMode(i);
            int size = android.view.View.MeasureSpec.getSize(i);
            if (mode != Integer.MIN_VALUE) {
                return mode != 1073741824 ? java.lang.Math.max(i2, i3) : size;
            }
            return java.lang.Math.min(size, java.lang.Math.max(i2, i3));
        }

        /* JADX WARN: Code duplicated, block: B:12:0x001c  */
        /* JADX WARN: Code duplicated, block: B:21:0x002f  */
        public static int a(int i, int i2, int i3, int i4, boolean z) {
            int iMax = java.lang.Math.max(0, i - i3);
            if (z) {
                if (i4 >= 0) {
                    i2 = 1073741824;
                } else if (i4 != -1 || (i2 != Integer.MIN_VALUE && (i2 == 0 || i2 != 1073741824))) {
                    i2 = 0;
                    i4 = 0;
                } else {
                    i4 = iMax;
                }
            } else if (i4 >= 0) {
                i2 = 1073741824;
            } else {
                if (i4 != -1) {
                    if (i4 == -2) {
                        i2 = (i2 == Integer.MIN_VALUE || i2 == 1073741824) ? Integer.MIN_VALUE : 0;
                    } else {
                        i2 = 0;
                        i4 = 0;
                    }
                }
                i4 = iMax;
            }
            return android.view.View.MeasureSpec.makeMeasureSpec(i4, i2);
        }

        public static androidx.recyclerview.widget.RecyclerView.o.d a(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
            androidx.recyclerview.widget.RecyclerView.o.d dVar = new androidx.recyclerview.widget.RecyclerView.o.d();
            android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.f.d.RecyclerView, i, i2);
            dVar.f221a = typedArrayObtainStyledAttributes.getInt(a.f.d.RecyclerView_android_orientation, 1);
            dVar.f222b = typedArrayObtainStyledAttributes.getInt(a.f.d.RecyclerView_spanCount, 1);
            dVar.f223c = typedArrayObtainStyledAttributes.getBoolean(a.f.d.RecyclerView_reverseLayout, false);
            dVar.d = typedArrayObtainStyledAttributes.getBoolean(a.f.d.RecyclerView_stackFromEnd, false);
            typedArrayObtainStyledAttributes.recycle();
            return dVar;
        }

        private void a(int i, android.view.View view) {
            this.f216a.a(i);
        }

        private void a(android.view.View view, int i, boolean z) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (z || d0VarK.q()) {
                this.f217b.f.a(d0VarK);
            } else {
                this.f217b.f.g(d0VarK);
            }
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            if (d0VarK.A() || d0VarK.r()) {
                if (d0VarK.r()) {
                    d0VarK.z();
                } else {
                    d0VarK.c();
                }
                this.f216a.a(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.f217b) {
                int iB = this.f216a.b(view);
                if (i == -1) {
                    i = this.f216a.a();
                }
                if (iB == -1) {
                    throw new java.lang.IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.f217b.indexOfChild(view) + this.f217b.i());
                }
                if (iB != i) {
                    this.f217b.m.a(iB, i);
                }
            } else {
                this.f216a.a(view, i, false);
                pVar.f226c = true;
                androidx.recyclerview.widget.RecyclerView.z zVar = this.g;
                if (zVar != null && zVar.c()) {
                    this.g.a(view);
                }
            }
            if (pVar.d) {
                d0VarK.f203a.invalidate();
                pVar.d = false;
            }
        }

        private void a(androidx.recyclerview.widget.RecyclerView.v vVar, int i, android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (d0VarK.y()) {
                return;
            }
            if (d0VarK.o() && !d0VarK.q() && !this.f217b.l.b()) {
                g(i);
                vVar.b(d0VarK);
            } else {
                a(i);
                vVar.c(view);
                this.f217b.f.d(d0VarK);
            }
        }

        private static boolean b(int i, int i2, int i3) {
            int mode = android.view.View.MeasureSpec.getMode(i2);
            int size = android.view.View.MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i;
            }
            return true;
        }

        private int[] c(android.view.View view, android.graphics.Rect rect) {
            int[] iArr = new int[2];
            int iN = n();
            int iP = p();
            int iQ = q() - o();
            int iH = h() - m();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int iWidth = rect.width() + left;
            int iHeight = rect.height() + top;
            int i = left - iN;
            int iMin = java.lang.Math.min(0, i);
            int i2 = top - iP;
            int iMin2 = java.lang.Math.min(0, i2);
            int i3 = iWidth - iQ;
            int iMax = java.lang.Math.max(0, i3);
            int iMax2 = java.lang.Math.max(0, iHeight - iH);
            if (j() != 1) {
                if (iMin == 0) {
                    iMin = java.lang.Math.min(i, iMax);
                }
                iMax = iMin;
            } else if (iMax == 0) {
                iMax = java.lang.Math.max(iMin, i3);
            }
            if (iMin2 == 0) {
                iMin2 = java.lang.Math.min(i2, iMax2);
            }
            iArr[0] = iMax;
            iArr[1] = iMin2;
            return iArr;
        }

        private boolean d(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
            android.view.View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int iN = n();
            int iP = p();
            int iQ = q() - o();
            int iH = h() - m();
            android.graphics.Rect rect = this.f217b.i;
            b(focusedChild, rect);
            return rect.left - i < iQ && rect.right - i > iN && rect.top - i2 < iH && rect.bottom - i2 > iP;
        }

        boolean A() {
            return false;
        }

        void B() {
            androidx.recyclerview.widget.RecyclerView.z zVar = this.g;
            if (zVar != null) {
                zVar.d();
            }
        }

        public boolean C() {
            return false;
        }

        public int a(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public int a(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public int a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView == null || recyclerView.l == null || !a()) {
                return 1;
            }
            return this.f217b.l.a();
        }

        public android.view.View a(android.view.View view, int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return null;
        }

        public androidx.recyclerview.widget.RecyclerView.p a(android.content.Context context, android.util.AttributeSet attributeSet) {
            return new androidx.recyclerview.widget.RecyclerView.p(context, attributeSet);
        }

        public androidx.recyclerview.widget.RecyclerView.p a(android.view.ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof androidx.recyclerview.widget.RecyclerView.p) {
                return new androidx.recyclerview.widget.RecyclerView.p((androidx.recyclerview.widget.RecyclerView.p) layoutParams);
            }
            return layoutParams instanceof android.view.ViewGroup.MarginLayoutParams ? new androidx.recyclerview.widget.RecyclerView.p((android.view.ViewGroup.MarginLayoutParams) layoutParams) : new androidx.recyclerview.widget.RecyclerView.p(layoutParams);
        }

        public void a(int i) {
            a(i, c(i));
        }

        public void a(int i, int i2) {
            android.view.View viewC = c(i);
            if (viewC != null) {
                a(i);
                c(viewC, i2);
            } else {
                throw new java.lang.IllegalArgumentException("Cannot move a child from non-existing index:" + i + this.f217b.toString());
            }
        }

        public void a(int i, int i2, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.RecyclerView.o.c cVar) {
        }

        public void a(int i, androidx.recyclerview.widget.RecyclerView.o.c cVar) {
        }

        public void a(int i, androidx.recyclerview.widget.RecyclerView.v vVar) {
            android.view.View viewC = c(i);
            g(i);
            vVar.b(viewC);
        }

        void a(a.c.e.q.c cVar) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            a(recyclerView.f192b, recyclerView.h0, cVar);
        }

        public void a(android.graphics.Rect rect, int i, int i2) {
            c(a(i, rect.width() + n() + o(), l()), a(i2, rect.height() + p() + m(), k()));
        }

        public void a(android.os.Parcelable parcelable) {
        }

        public void a(android.view.View view) {
            a(view, -1);
        }

        public void a(android.view.View view, int i) {
            a(view, i, true);
        }

        public void a(android.view.View view, int i, int i2) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            android.graphics.Rect rectF = this.f217b.f(view);
            int i3 = i + rectF.left + rectF.right;
            int i4 = i2 + rectF.top + rectF.bottom;
            int iA = a(q(), r(), n() + o() + ((android.view.ViewGroup.MarginLayoutParams) pVar).leftMargin + ((android.view.ViewGroup.MarginLayoutParams) pVar).rightMargin + i3, ((android.view.ViewGroup.MarginLayoutParams) pVar).width, a());
            int iA2 = a(h(), i(), p() + m() + ((android.view.ViewGroup.MarginLayoutParams) pVar).topMargin + ((android.view.ViewGroup.MarginLayoutParams) pVar).bottomMargin + i4, ((android.view.ViewGroup.MarginLayoutParams) pVar).height, b());
            if (a(view, iA, iA2, pVar)) {
                view.measure(iA, iA2);
            }
        }

        public void a(android.view.View view, int i, int i2, int i3, int i4) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            android.graphics.Rect rect = pVar.f225b;
            view.layout(i + rect.left + ((android.view.ViewGroup.MarginLayoutParams) pVar).leftMargin, i2 + rect.top + ((android.view.ViewGroup.MarginLayoutParams) pVar).topMargin, (i3 - rect.right) - ((android.view.ViewGroup.MarginLayoutParams) pVar).rightMargin, (i4 - rect.bottom) - ((android.view.ViewGroup.MarginLayoutParams) pVar).bottomMargin);
        }

        public void a(android.view.View view, int i, androidx.recyclerview.widget.RecyclerView.p pVar) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (d0VarK.q()) {
                this.f217b.f.a(d0VarK);
            } else {
                this.f217b.f.g(d0VarK);
            }
            this.f216a.a(view, i, pVar, d0VarK.q());
        }

        void a(android.view.View view, a.c.e.q.c cVar) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (d0VarK == null || d0VarK.q() || this.f216a.c(d0VarK.f203a)) {
                return;
            }
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            a(recyclerView.f192b, recyclerView.h0, view, cVar);
        }

        public void a(android.view.View view, android.graphics.Rect rect) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.f(view));
            }
        }

        public void a(android.view.View view, androidx.recyclerview.widget.RecyclerView.v vVar) {
            o(view);
            vVar.b(view);
        }

        public void a(android.view.View view, boolean z, android.graphics.Rect rect) {
            android.graphics.Matrix matrix;
            if (z) {
                android.graphics.Rect rect2 = ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f225b;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.f217b != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
                android.graphics.RectF rectF = this.f217b.k;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) java.lang.Math.floor(rectF.left), (int) java.lang.Math.floor(rectF.top), (int) java.lang.Math.ceil(rectF.right), (int) java.lang.Math.ceil(rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public void a(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            a(recyclerView.f192b, recyclerView.h0, accessibilityEvent);
        }

        public void a(androidx.recyclerview.widget.RecyclerView.g gVar, androidx.recyclerview.widget.RecyclerView.g gVar2) {
        }

        public void a(androidx.recyclerview.widget.RecyclerView.v vVar) {
            for (int iE = e() - 1; iE >= 0; iE--) {
                a(vVar, iE, c(iE));
            }
        }

        public void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i, int i2) {
            this.f217b.c(i, i2);
        }

        public void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, a.c.e.q.c cVar) {
            if (this.f217b.canScrollVertically(-1) || this.f217b.canScrollHorizontally(-1)) {
                cVar.a(8192);
                cVar.c(true);
            }
            if (this.f217b.canScrollVertically(1) || this.f217b.canScrollHorizontally(1)) {
                cVar.a(4096);
                cVar.c(true);
            }
            cVar.a(a.c.e.q.c.b.a(b(vVar, a0Var), a(vVar, a0Var), d(vVar, a0Var), c(vVar, a0Var)));
        }

        public void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, android.view.View view, a.c.e.q.c cVar) {
            cVar.b(a.c.e.q.c.C0004c.a(b() ? l(view) : 0, 1, a() ? l(view) : 0, 1, false, false));
        }

        public void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView == null || accessibilityEvent == null) {
                return;
            }
            boolean z = true;
            if (!recyclerView.canScrollVertically(1) && !this.f217b.canScrollVertically(-1) && !this.f217b.canScrollHorizontally(-1) && !this.f217b.canScrollHorizontally(1)) {
                z = false;
            }
            accessibilityEvent.setScrollable(z);
            androidx.recyclerview.widget.RecyclerView.g gVar = this.f217b.l;
            if (gVar != null) {
                accessibilityEvent.setItemCount(gVar.a());
            }
        }

        void a(androidx.recyclerview.widget.RecyclerView recyclerView) {
            this.i = true;
            b(recyclerView);
        }

        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        }

        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2, int i3) {
        }

        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2, java.lang.Object obj) {
            c(recyclerView, i, i2);
        }

        void a(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.v vVar) {
            this.i = false;
            b(recyclerView, vVar);
        }

        public void a(java.lang.String str) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                recyclerView.a(str);
            }
        }

        public boolean a() {
            return false;
        }

        boolean a(int i, android.os.Bundle bundle) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            return a(recyclerView.f192b, recyclerView.h0, i, bundle);
        }

        boolean a(android.view.View view, int i, int i2, androidx.recyclerview.widget.RecyclerView.p pVar) {
            return (!view.isLayoutRequested() && this.k && b(view.getWidth(), i, ((android.view.ViewGroup.MarginLayoutParams) pVar).width) && b(view.getHeight(), i2, ((android.view.ViewGroup.MarginLayoutParams) pVar).height)) ? false : true;
        }

        boolean a(android.view.View view, int i, android.os.Bundle bundle) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            return a(recyclerView.f192b, recyclerView.h0, view, i, bundle);
        }

        public boolean a(android.view.View view, boolean z, boolean z2) {
            boolean z3 = this.e.a(view, 24579) && this.f.a(view, 24579);
            return z ? z3 : !z3;
        }

        public boolean a(androidx.recyclerview.widget.RecyclerView.p pVar) {
            return pVar != null;
        }

        /* JADX WARN: Code duplicated, block: B:25:0x0071 A[PHI: r8
  0x0071: PHI (r8v8 int) = (r8v4 int), (r8v12 int) binds: [B:22:0x005e, B:15:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
        public boolean a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i, android.os.Bundle bundle) {
            int iH;
            int iQ;
            int i2;
            int i3;
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView == null) {
                return false;
            }
            if (i == 4096) {
                iH = recyclerView.canScrollVertically(1) ? (h() - p()) - m() : 0;
                if (this.f217b.canScrollHorizontally(1)) {
                    iQ = (q() - n()) - o();
                    i2 = iH;
                    i3 = iQ;
                } else {
                    i2 = iH;
                    i3 = 0;
                }
            } else if (i != 8192) {
                i3 = 0;
                i2 = 0;
            } else {
                iH = recyclerView.canScrollVertically(-1) ? -((h() - p()) - m()) : 0;
                if (this.f217b.canScrollHorizontally(-1)) {
                    iQ = -((q() - n()) - o());
                    i2 = iH;
                    i3 = iQ;
                } else {
                    i2 = iH;
                    i3 = 0;
                }
            }
            if (i2 == 0 && i3 == 0) {
                return false;
            }
            this.f217b.a(i3, i2, (android.view.animation.Interpolator) null, Integer.MIN_VALUE, true);
            return true;
        }

        public boolean a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, android.view.View view, int i, android.os.Bundle bundle) {
            return false;
        }

        public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.View view, android.graphics.Rect rect, boolean z) {
            return a(recyclerView, view, rect, z, false);
        }

        public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.View view, android.graphics.Rect rect, boolean z, boolean z2) {
            int[] iArrC = c(view, rect);
            int i = iArrC[0];
            int i2 = iArrC[1];
            if ((z2 && !d(recyclerView, i, i2)) || (i == 0 && i2 == 0)) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(i, i2);
            } else {
                recyclerView.i(i, i2);
            }
            return true;
        }

        @java.lang.Deprecated
        public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.View view, android.view.View view2) {
            return w() || recyclerView.n();
        }

        public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.a0 a0Var, android.view.View view, android.view.View view2) {
            return a(recyclerView, view, view2);
        }

        public boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, java.util.ArrayList<android.view.View> arrayList, int i, int i2) {
            return false;
        }

        public boolean a(java.lang.Runnable runnable) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public int b(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public int b(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public int b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView == null || recyclerView.l == null || !b()) {
                return 1;
            }
            return this.f217b.l.a();
        }

        public android.view.View b(int i) {
            int iE = e();
            for (int i2 = 0; i2 < iE; i2++) {
                android.view.View viewC = c(i2);
                androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(viewC);
                if (d0VarK != null && d0VarK.i() == i && !d0VarK.y() && (this.f217b.h0.d() || !d0VarK.q())) {
                    return viewC;
                }
            }
            return null;
        }

        void b(int i, int i2) {
            this.q = android.view.View.MeasureSpec.getSize(i);
            int mode = android.view.View.MeasureSpec.getMode(i);
            this.o = mode;
            if (mode == 0 && !androidx.recyclerview.widget.RecyclerView.A0) {
                this.q = 0;
            }
            this.r = android.view.View.MeasureSpec.getSize(i2);
            int mode2 = android.view.View.MeasureSpec.getMode(i2);
            this.p = mode2;
            if (mode2 != 0 || androidx.recyclerview.widget.RecyclerView.A0) {
                return;
            }
            this.r = 0;
        }

        public void b(android.view.View view) {
            b(view, -1);
        }

        public void b(android.view.View view, int i) {
            a(view, i, false);
        }

        public void b(android.view.View view, android.graphics.Rect rect) {
            androidx.recyclerview.widget.RecyclerView.a(view, rect);
        }

        public void b(androidx.recyclerview.widget.RecyclerView.v vVar) {
            for (int iE = e() - 1; iE >= 0; iE--) {
                if (!androidx.recyclerview.widget.RecyclerView.k(c(iE)).y()) {
                    a(iE, vVar);
                }
            }
        }

        public void b(androidx.recyclerview.widget.RecyclerView recyclerView) {
        }

        public void b(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        }

        public void b(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.v vVar) {
            c(recyclerView);
        }

        public boolean b() {
            return false;
        }

        boolean b(android.view.View view, int i, int i2, androidx.recyclerview.widget.RecyclerView.p pVar) {
            return (this.k && b(view.getMeasuredWidth(), i, ((android.view.ViewGroup.MarginLayoutParams) pVar).width) && b(view.getMeasuredHeight(), i2, ((android.view.ViewGroup.MarginLayoutParams) pVar).height)) ? false : true;
        }

        public int c(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public int c(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public android.view.View c(int i) {
            androidx.recyclerview.widget.b bVar = this.f216a;
            if (bVar != null) {
                return bVar.c(i);
            }
            return null;
        }

        public android.view.View c(android.view.View view) {
            android.view.View viewC;
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView == null || (viewC = recyclerView.c(view)) == null || this.f216a.c(viewC)) {
                return null;
            }
            return viewC;
        }

        public abstract androidx.recyclerview.widget.RecyclerView.p c();

        public void c(int i, int i2) {
            this.f217b.setMeasuredDimension(i, i2);
        }

        public void c(android.view.View view, int i) {
            a(view, i, (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams());
        }

        void c(androidx.recyclerview.widget.RecyclerView.v vVar) {
            int iE = vVar.e();
            for (int i = iE - 1; i >= 0; i--) {
                android.view.View viewC = vVar.c(i);
                androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(viewC);
                if (!d0VarK.y()) {
                    d0VarK.a(false);
                    if (d0VarK.s()) {
                        this.f217b.removeDetachedView(viewC, false);
                    }
                    androidx.recyclerview.widget.RecyclerView.l lVar = this.f217b.M;
                    if (lVar != null) {
                        lVar.c(d0VarK);
                    }
                    d0VarK.a(true);
                    vVar.a(viewC);
                }
            }
            vVar.c();
            if (iE > 0) {
                this.f217b.invalidate();
            }
        }

        @java.lang.Deprecated
        public void c(androidx.recyclerview.widget.RecyclerView recyclerView) {
        }

        public void c(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        }

        public int d() {
            return -1;
        }

        public int d(android.view.View view) {
            return ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f225b.bottom;
        }

        public int d(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public android.view.View d(android.view.View view, int i) {
            return null;
        }

        public void d(int i) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                recyclerView.c(i);
            }
        }

        void d(int i, int i2) {
            int iE = e();
            if (iE == 0) {
                this.f217b.c(i, i2);
                return;
            }
            int i3 = Integer.MIN_VALUE;
            int i4 = Integer.MIN_VALUE;
            int i5 = Integer.MAX_VALUE;
            int i6 = Integer.MAX_VALUE;
            for (int i7 = 0; i7 < iE; i7++) {
                android.view.View viewC = c(i7);
                android.graphics.Rect rect = this.f217b.i;
                b(viewC, rect);
                int i8 = rect.left;
                if (i8 < i5) {
                    i5 = i8;
                }
                int i9 = rect.right;
                if (i9 > i3) {
                    i3 = i9;
                }
                int i10 = rect.top;
                if (i10 < i6) {
                    i6 = i10;
                }
                int i11 = rect.bottom;
                if (i11 > i4) {
                    i4 = i11;
                }
            }
            this.f217b.i.set(i5, i6, i3, i4);
            a(this.f217b.i, i, i2);
        }

        public void d(androidx.recyclerview.widget.RecyclerView recyclerView) {
        }

        public boolean d(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return false;
        }

        public int e() {
            androidx.recyclerview.widget.b bVar = this.f216a;
            if (bVar != null) {
                return bVar.a();
            }
            return 0;
        }

        public int e(android.view.View view) {
            return view.getBottom() + d(view);
        }

        public int e(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public void e(int i) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                recyclerView.d(i);
            }
        }

        public void e(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            android.util.Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        void e(androidx.recyclerview.widget.RecyclerView recyclerView) {
            b(android.view.View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        public int f(android.view.View view) {
            return view.getLeft() - k(view);
        }

        public int f(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            return 0;
        }

        public void f(int i) {
        }

        void f(androidx.recyclerview.widget.RecyclerView recyclerView) {
            int height;
            if (recyclerView == null) {
                this.f217b = null;
                this.f216a = null;
                height = 0;
                this.q = 0;
            } else {
                this.f217b = recyclerView;
                this.f216a = recyclerView.e;
                this.q = recyclerView.getWidth();
                height = recyclerView.getHeight();
            }
            this.r = height;
            this.o = 1073741824;
            this.p = 1073741824;
        }

        public boolean f() {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            return recyclerView != null && recyclerView.g;
        }

        public int g(android.view.View view) {
            android.graphics.Rect rect = ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f225b;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public android.view.View g() {
            android.view.View focusedChild;
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView == null || (focusedChild = recyclerView.getFocusedChild()) == null || this.f216a.c(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public void g(int i) {
            if (c(i) != null) {
                this.f216a.e(i);
            }
        }

        public void g(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        }

        public int h() {
            return this.r;
        }

        public int h(android.view.View view) {
            android.graphics.Rect rect = ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f225b;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public void h(int i) {
        }

        public int i() {
            return this.p;
        }

        public int i(android.view.View view) {
            return view.getRight() + m(view);
        }

        public int j() {
            return a.c.e.m.i(this.f217b);
        }

        public int j(android.view.View view) {
            return view.getTop() - n(view);
        }

        public int k() {
            return a.c.e.m.j(this.f217b);
        }

        public int k(android.view.View view) {
            return ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f225b.left;
        }

        public int l() {
            return a.c.e.m.k(this.f217b);
        }

        public int l(android.view.View view) {
            return ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).a();
        }

        public int m() {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int m(android.view.View view) {
            return ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f225b.right;
        }

        public int n() {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int n(android.view.View view) {
            return ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f225b.top;
        }

        public int o() {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public void o(android.view.View view) {
            this.f216a.d(view);
        }

        public int p() {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int q() {
            return this.q;
        }

        public int r() {
            return this.o;
        }

        boolean s() {
            int iE = e();
            for (int i = 0; i < iE; i++) {
                android.view.ViewGroup.LayoutParams layoutParams = c(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }

        public boolean t() {
            return this.i;
        }

        public boolean u() {
            return this.j;
        }

        public final boolean v() {
            return this.l;
        }

        public boolean w() {
            androidx.recyclerview.widget.RecyclerView.z zVar = this.g;
            return zVar != null && zVar.c();
        }

        public android.os.Parcelable x() {
            return null;
        }

        public void y() {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f217b;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public void z() {
            this.h = true;
        }
    }

    public static class p extends android.view.ViewGroup.MarginLayoutParams {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        androidx.recyclerview.widget.RecyclerView.d0 f224a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final android.graphics.Rect f225b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        boolean f226c;
        boolean d;

        public p(int i, int i2) {
            super(i, i2);
            this.f225b = new android.graphics.Rect();
            this.f226c = true;
            this.d = false;
        }

        public p(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f225b = new android.graphics.Rect();
            this.f226c = true;
            this.d = false;
        }

        public p(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f225b = new android.graphics.Rect();
            this.f226c = true;
            this.d = false;
        }

        public p(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.f225b = new android.graphics.Rect();
            this.f226c = true;
            this.d = false;
        }

        public p(androidx.recyclerview.widget.RecyclerView.p pVar) {
            super((android.view.ViewGroup.LayoutParams) pVar);
            this.f225b = new android.graphics.Rect();
            this.f226c = true;
            this.d = false;
        }

        public int a() {
            return this.f224a.i();
        }

        public boolean b() {
            return this.f224a.t();
        }

        public boolean c() {
            return this.f224a.q();
        }

        public boolean d() {
            return this.f224a.o();
        }
    }

    public interface q {
        void a(android.view.View view);

        void b(android.view.View view);
    }

    public static abstract class r {
        public abstract boolean a(int i, int i2);
    }

    public interface s {
        void a(boolean z);

        boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent);

        void b(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent);
    }

    public static abstract class t {
        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i) {
        }

        public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        }
    }

    public static class u {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        android.util.SparseArray<androidx.recyclerview.widget.RecyclerView.u.a> f227a = new android.util.SparseArray<>();

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private int f228b = 0;

        static class a {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> f229a = new java.util.ArrayList<>();

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            int f230b = 5;

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            long f231c = 0;
            long d = 0;

            a() {
            }
        }

        private androidx.recyclerview.widget.RecyclerView.u.a b(int i) {
            androidx.recyclerview.widget.RecyclerView.u.a aVar = this.f227a.get(i);
            if (aVar != null) {
                return aVar;
            }
            androidx.recyclerview.widget.RecyclerView.u.a aVar2 = new androidx.recyclerview.widget.RecyclerView.u.a();
            this.f227a.put(i, aVar2);
            return aVar2;
        }

        long a(long j, long j2) {
            return j == 0 ? j2 : ((j / 4) * 3) + (j2 / 4);
        }

        public androidx.recyclerview.widget.RecyclerView.d0 a(int i) {
            androidx.recyclerview.widget.RecyclerView.u.a aVar = this.f227a.get(i);
            if (aVar == null || aVar.f229a.isEmpty()) {
                return null;
            }
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList = aVar.f229a;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (!arrayList.get(size).m()) {
                    return arrayList.remove(size);
                }
            }
            return null;
        }

        void a() {
            this.f228b++;
        }

        void a(int i, long j) {
            androidx.recyclerview.widget.RecyclerView.u.a aVarB = b(i);
            aVarB.d = a(aVarB.d, j);
        }

        public void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            int iH = d0Var.h();
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList = b(iH).f229a;
            if (this.f227a.get(iH).f230b <= arrayList.size()) {
                return;
            }
            d0Var.v();
            arrayList.add(d0Var);
        }

        void a(androidx.recyclerview.widget.RecyclerView.g gVar, androidx.recyclerview.widget.RecyclerView.g gVar2, boolean z) {
            if (gVar != null) {
                c();
            }
            if (!z && this.f228b == 0) {
                b();
            }
            if (gVar2 != null) {
                a();
            }
        }

        boolean a(int i, long j, long j2) {
            long j3 = b(i).d;
            return j3 == 0 || j + j3 < j2;
        }

        public void b() {
            for (int i = 0; i < this.f227a.size(); i++) {
                this.f227a.valueAt(i).f229a.clear();
            }
        }

        void b(int i, long j) {
            androidx.recyclerview.widget.RecyclerView.u.a aVarB = b(i);
            aVarB.f231c = a(aVarB.f231c, j);
        }

        boolean b(int i, long j, long j2) {
            long j3 = b(i).f231c;
            return j3 == 0 || j + j3 < j2;
        }

        void c() {
            this.f228b--;
        }
    }

    public final class v {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> f232a = new java.util.ArrayList<>();

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> f233b = null;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> f234c = new java.util.ArrayList<>();
        private final java.util.List<androidx.recyclerview.widget.RecyclerView.d0> d = java.util.Collections.unmodifiableList(this.f232a);
        private int e = 2;
        int f = 2;
        androidx.recyclerview.widget.RecyclerView.u g;
        private androidx.recyclerview.widget.RecyclerView.b0 h;

        public v() {
        }

        private void a(android.view.ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                android.view.View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof android.view.ViewGroup) {
                    a((android.view.ViewGroup) childAt, true);
                }
            }
            if (z) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                } else {
                    int visibility = viewGroup.getVisibility();
                    viewGroup.setVisibility(4);
                    viewGroup.setVisibility(visibility);
                }
            }
        }

        private boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, int i2, long j) {
            d0Var.r = androidx.recyclerview.widget.RecyclerView.this;
            int iH = d0Var.h();
            long nanoTime = androidx.recyclerview.widget.RecyclerView.this.getNanoTime();
            if (j != Long.MAX_VALUE && !this.g.a(iH, nanoTime, j)) {
                return false;
            }
            androidx.recyclerview.widget.RecyclerView.this.l.a(d0Var, i);
            this.g.a(d0Var.h(), androidx.recyclerview.widget.RecyclerView.this.getNanoTime() - nanoTime);
            e(d0Var);
            if (!androidx.recyclerview.widget.RecyclerView.this.h0.d()) {
                return true;
            }
            d0Var.g = i2;
            return true;
        }

        private void e(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            if (androidx.recyclerview.widget.RecyclerView.this.m()) {
                android.view.View view = d0Var.f203a;
                if (a.c.e.m.g(view) == 0) {
                    a.c.e.m.b(view, 1);
                }
                androidx.recyclerview.widget.l lVar = androidx.recyclerview.widget.RecyclerView.this.o0;
                if (lVar == null) {
                    return;
                }
                a.c.e.a aVarB = lVar.b();
                if (aVarB instanceof androidx.recyclerview.widget.l.a) {
                    ((androidx.recyclerview.widget.l.a) aVarB).d(view);
                }
                a.c.e.m.a(view, aVarB);
            }
        }

        private void f(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            android.view.View view = d0Var.f203a;
            if (view instanceof android.view.ViewGroup) {
                a((android.view.ViewGroup) view, false);
            }
        }

        public int a(int i) {
            if (i >= 0 && i < androidx.recyclerview.widget.RecyclerView.this.h0.a()) {
                return !androidx.recyclerview.widget.RecyclerView.this.h0.d() ? i : androidx.recyclerview.widget.RecyclerView.this.d.b(i);
            }
            throw new java.lang.IndexOutOfBoundsException("invalid position " + i + ". State item count is " + androidx.recyclerview.widget.RecyclerView.this.h0.a() + androidx.recyclerview.widget.RecyclerView.this.i());
        }

        androidx.recyclerview.widget.RecyclerView.d0 a(int i, boolean z) {
            android.view.View viewB;
            int size = this.f232a.size();
            for (int i2 = 0; i2 < size; i2++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f232a.get(i2);
                if (!d0Var.A() && d0Var.i() == i && !d0Var.o() && (androidx.recyclerview.widget.RecyclerView.this.h0.h || !d0Var.q())) {
                    d0Var.a(32);
                    return d0Var;
                }
            }
            if (z || (viewB = androidx.recyclerview.widget.RecyclerView.this.e.b(i)) == null) {
                int size2 = this.f234c.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = this.f234c.get(i3);
                    if (!d0Var2.o() && d0Var2.i() == i && !d0Var2.m()) {
                        if (!z) {
                            this.f234c.remove(i3);
                        }
                        return d0Var2;
                    }
                }
                return null;
            }
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(viewB);
            androidx.recyclerview.widget.RecyclerView.this.e.f(viewB);
            int iB = androidx.recyclerview.widget.RecyclerView.this.e.b(viewB);
            if (iB != -1) {
                androidx.recyclerview.widget.RecyclerView.this.e.a(iB);
                c(viewB);
                d0VarK.a(8224);
                return d0VarK;
            }
            throw new java.lang.IllegalStateException("layout index should not be -1 after unhiding a view:" + d0VarK + androidx.recyclerview.widget.RecyclerView.this.i());
        }

        /* JADX WARN: Code duplicated, block: B:101:0x020c  */
        /* JADX WARN: Code duplicated, block: B:103:0x0214  */
        /* JADX WARN: Code duplicated, block: B:104:0x021b  */
        /* JADX WARN: Code duplicated, block: B:107:0x0221 A[ADDED_TO_REGION] */
        /* JADX WARN: Code duplicated, block: B:109:0x0224  */
        /* JADX WARN: Code duplicated, block: B:18:0x0037 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:19:0x0039  */
        /* JADX WARN: Code duplicated, block: B:21:0x0043  */
        /* JADX WARN: Code duplicated, block: B:22:0x004e  */
        /* JADX WARN: Code duplicated, block: B:24:0x0054  */
        /* JADX WARN: Code duplicated, block: B:27:0x005c  */
        /* JADX WARN: Code duplicated, block: B:29:0x005f  */
        /* JADX WARN: Code duplicated, block: B:73:0x0181 A[PHI: r1 r4
  0x0181: PHI (r1v12 androidx.recyclerview.widget.RecyclerView$d0) = (r1v11 androidx.recyclerview.widget.RecyclerView$d0), (r1v31 androidx.recyclerview.widget.RecyclerView$d0) binds: [B:28:0x005d, B:59:0x0102] A[DONT_GENERATE, DONT_INLINE]
  0x0181: PHI (r4v3 boolean) = (r4v2 boolean), (r4v7 boolean) binds: [B:28:0x005d, B:59:0x0102] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:82:0x01a2  */
        /* JADX WARN: Code duplicated, block: B:88:0x01ce  */
        /* JADX WARN: Code duplicated, block: B:90:0x01d4  */
        /* JADX WARN: Code duplicated, block: B:99:0x01fe  */
        androidx.recyclerview.widget.RecyclerView.d0 a(int i, boolean z, long j) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarE;
            boolean z2;
            androidx.recyclerview.widget.RecyclerView.d0 d0Var;
            boolean z3;
            android.view.ViewGroup.LayoutParams layoutParams;
            androidx.recyclerview.widget.RecyclerView.p pVar;
            android.view.ViewGroup.LayoutParams layoutParamsGenerateLayoutParams;
            int iB;
            androidx.recyclerview.widget.RecyclerView recyclerViewJ;
            androidx.recyclerview.widget.RecyclerView.b0 b0Var;
            android.view.View viewA;
            if (i < 0 || i >= androidx.recyclerview.widget.RecyclerView.this.h0.a()) {
                throw new java.lang.IndexOutOfBoundsException("Invalid item position " + i + "(" + i + "). Item count:" + androidx.recyclerview.widget.RecyclerView.this.h0.a() + androidx.recyclerview.widget.RecyclerView.this.i());
            }
            if (androidx.recyclerview.widget.RecyclerView.this.h0.d()) {
                d0VarE = b(i);
                z2 = d0VarE != null;
                if (d0VarE == null && (d0VarE = a(i, z)) != null) {
                    if (d(d0VarE)) {
                        z2 = true;
                    } else {
                        if (!z) {
                            d0VarE.a(4);
                            if (d0VarE.r()) {
                                androidx.recyclerview.widget.RecyclerView.this.removeDetachedView(d0VarE.f203a, false);
                                d0VarE.z();
                            } else if (d0VarE.A()) {
                                d0VarE.c();
                            }
                            b(d0VarE);
                        }
                        d0VarE = null;
                    }
                }
                if (d0VarE == null) {
                    d0Var = d0VarE;
                } else {
                    iB = androidx.recyclerview.widget.RecyclerView.this.d.b(i);
                    if (iB >= 0 || iB >= androidx.recyclerview.widget.RecyclerView.this.l.a()) {
                        throw new java.lang.IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + iB + ").state:" + androidx.recyclerview.widget.RecyclerView.this.h0.a() + androidx.recyclerview.widget.RecyclerView.this.i());
                    }
                    int iB2 = androidx.recyclerview.widget.RecyclerView.this.l.b(iB);
                    if (androidx.recyclerview.widget.RecyclerView.this.l.b() && (d0VarE = a(androidx.recyclerview.widget.RecyclerView.this.l.a(iB), iB2, z)) != null) {
                        d0VarE.f205c = iB;
                        z2 = true;
                    }
                    if (d0VarE == null && (b0Var = this.h) != null && (viewA = b0Var.a(this, i, iB2)) != null) {
                        d0VarE = androidx.recyclerview.widget.RecyclerView.this.e(viewA);
                        if (d0VarE == null) {
                            throw new java.lang.IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder" + androidx.recyclerview.widget.RecyclerView.this.i());
                        }
                        if (d0VarE.y()) {
                            throw new java.lang.IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view." + androidx.recyclerview.widget.RecyclerView.this.i());
                        }
                    }
                    if (d0VarE == null) {
                        androidx.recyclerview.widget.RecyclerView.d0 d0VarA = d().a(iB2);
                        if (d0VarA != null) {
                            d0VarA.v();
                            if (androidx.recyclerview.widget.RecyclerView.z0) {
                                f(d0VarA);
                            }
                        }
                        d0VarE = d0VarA;
                    }
                    if (d0VarE == null) {
                        long nanoTime = androidx.recyclerview.widget.RecyclerView.this.getNanoTime();
                        if (j != Long.MAX_VALUE && !this.g.b(iB2, nanoTime, j)) {
                            return null;
                        }
                        androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
                        androidx.recyclerview.widget.RecyclerView.d0 d0VarA2 = recyclerView.l.a(recyclerView, iB2);
                        if (androidx.recyclerview.widget.RecyclerView.C0 && (recyclerViewJ = androidx.recyclerview.widget.RecyclerView.j(d0VarA2.f203a)) != null) {
                            d0VarA2.f204b = new java.lang.ref.WeakReference<>(recyclerViewJ);
                        }
                        this.g.b(iB2, androidx.recyclerview.widget.RecyclerView.this.getNanoTime() - nanoTime);
                        d0Var = d0VarA2;
                    } else {
                        d0Var = d0VarE;
                    }
                }
                z3 = z2;
                if (z3 && !androidx.recyclerview.widget.RecyclerView.this.h0.d() && d0Var.b(8192)) {
                    d0Var.a(0, 8192);
                    if (androidx.recyclerview.widget.RecyclerView.this.h0.k) {
                        int iE = androidx.recyclerview.widget.RecyclerView.l.e(d0Var) | 4096;
                        androidx.recyclerview.widget.RecyclerView recyclerView2 = androidx.recyclerview.widget.RecyclerView.this;
                        androidx.recyclerview.widget.RecyclerView.this.a(d0Var, recyclerView2.M.a(recyclerView2.h0, d0Var, iE, d0Var.k()));
                    }
                }
                if (androidx.recyclerview.widget.RecyclerView.this.h0.d() || !d0Var.n()) {
                    boolean zA = (d0Var.n() || d0Var.u() || d0Var.o()) ? a(d0Var, androidx.recyclerview.widget.RecyclerView.this.d.b(i), i, j) : false;
                    layoutParams = d0Var.f203a.getLayoutParams();
                    if (layoutParams != null) {
                        if (androidx.recyclerview.widget.RecyclerView.this.checkLayoutParams(layoutParams)) {
                            pVar = (androidx.recyclerview.widget.RecyclerView.p) layoutParams;
                        } else {
                            layoutParamsGenerateLayoutParams = androidx.recyclerview.widget.RecyclerView.this.generateLayoutParams(layoutParams);
                        }
                        pVar.f224a = d0Var;
                        pVar.d = !z3 && zA;
                        return d0Var;
                    }
                    layoutParamsGenerateLayoutParams = androidx.recyclerview.widget.RecyclerView.this.generateDefaultLayoutParams();
                    pVar = (androidx.recyclerview.widget.RecyclerView.p) layoutParamsGenerateLayoutParams;
                    d0Var.f203a.setLayoutParams(pVar);
                    pVar.f224a = d0Var;
                    pVar.d = !z3 && zA;
                    return d0Var;
                }
                d0Var.g = i;
                layoutParams = d0Var.f203a.getLayoutParams();
                if (layoutParams != null) {
                    if (androidx.recyclerview.widget.RecyclerView.this.checkLayoutParams(layoutParams)) {
                        layoutParamsGenerateLayoutParams = androidx.recyclerview.widget.RecyclerView.this.generateLayoutParams(layoutParams);
                    } else {
                        pVar = (androidx.recyclerview.widget.RecyclerView.p) layoutParams;
                    }
                    pVar.f224a = d0Var;
                    pVar.d = !z3 && zA;
                    return d0Var;
                }
                layoutParamsGenerateLayoutParams = androidx.recyclerview.widget.RecyclerView.this.generateDefaultLayoutParams();
                pVar = (androidx.recyclerview.widget.RecyclerView.p) layoutParamsGenerateLayoutParams;
                d0Var.f203a.setLayoutParams(pVar);
                pVar.f224a = d0Var;
                pVar.d = !z3 && zA;
                return d0Var;
            }
            d0VarE = null;
            if (d0VarE == null) {
                if (d(d0VarE)) {
                    if (!z) {
                        d0VarE.a(4);
                        if (d0VarE.r()) {
                            androidx.recyclerview.widget.RecyclerView.this.removeDetachedView(d0VarE.f203a, false);
                            d0VarE.z();
                        } else if (d0VarE.A()) {
                            d0VarE.c();
                        }
                        b(d0VarE);
                    }
                    d0VarE = null;
                } else {
                    z2 = true;
                }
            }
            if (d0VarE == null) {
                iB = androidx.recyclerview.widget.RecyclerView.this.d.b(i);
                if (iB >= 0) {
                }
                throw new java.lang.IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + iB + ").state:" + androidx.recyclerview.widget.RecyclerView.this.h0.a() + androidx.recyclerview.widget.RecyclerView.this.i());
            }
            d0Var = d0VarE;
            z3 = z2;
            if (z3) {
                d0Var.a(0, 8192);
                if (androidx.recyclerview.widget.RecyclerView.this.h0.k) {
                    int iE2 = androidx.recyclerview.widget.RecyclerView.l.e(d0Var) | 4096;
                    androidx.recyclerview.widget.RecyclerView recyclerView3 = androidx.recyclerview.widget.RecyclerView.this;
                    androidx.recyclerview.widget.RecyclerView.this.a(d0Var, recyclerView3.M.a(recyclerView3.h0, d0Var, iE2, d0Var.k()));
                }
            }
            if (androidx.recyclerview.widget.RecyclerView.this.h0.d()) {
                if (d0Var.n()) {
                }
            } else {
                if (d0Var.n()) {
                }
            }
            layoutParams = d0Var.f203a.getLayoutParams();
            if (layoutParams != null) {
                if (androidx.recyclerview.widget.RecyclerView.this.checkLayoutParams(layoutParams)) {
                    layoutParamsGenerateLayoutParams = androidx.recyclerview.widget.RecyclerView.this.generateLayoutParams(layoutParams);
                } else {
                    pVar = (androidx.recyclerview.widget.RecyclerView.p) layoutParams;
                }
                pVar.f224a = d0Var;
                pVar.d = !z3 && zA;
                return d0Var;
            }
            layoutParamsGenerateLayoutParams = androidx.recyclerview.widget.RecyclerView.this.generateDefaultLayoutParams();
            pVar = (androidx.recyclerview.widget.RecyclerView.p) layoutParamsGenerateLayoutParams;
            d0Var.f203a.setLayoutParams(pVar);
            pVar.f224a = d0Var;
            pVar.d = !z3 && zA;
            return d0Var;
        }

        androidx.recyclerview.widget.RecyclerView.d0 a(long j, int i, boolean z) {
            for (int size = this.f232a.size() - 1; size >= 0; size--) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f232a.get(size);
                if (d0Var.g() == j && !d0Var.A()) {
                    if (i == d0Var.h()) {
                        d0Var.a(32);
                        if (d0Var.q() && !androidx.recyclerview.widget.RecyclerView.this.h0.d()) {
                            d0Var.a(2, 14);
                        }
                        return d0Var;
                    }
                    if (!z) {
                        this.f232a.remove(size);
                        androidx.recyclerview.widget.RecyclerView.this.removeDetachedView(d0Var.f203a, false);
                        a(d0Var.f203a);
                    }
                }
            }
            int size2 = this.f234c.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return null;
                }
                androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = this.f234c.get(size2);
                if (d0Var2.g() == j && !d0Var2.m()) {
                    if (i == d0Var2.h()) {
                        if (!z) {
                            this.f234c.remove(size2);
                        }
                        return d0Var2;
                    }
                    if (!z) {
                        e(size2);
                        return null;
                    }
                }
            }
        }

        public void a() {
            this.f232a.clear();
            i();
        }

        void a(int i, int i2) {
            int size = this.f234c.size();
            for (int i3 = 0; i3 < size; i3++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f234c.get(i3);
                if (d0Var != null && d0Var.f205c >= i) {
                    d0Var.a(i2, true);
                }
            }
        }

        void a(int i, int i2, boolean z) {
            int i3 = i + i2;
            for (int size = this.f234c.size() - 1; size >= 0; size--) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f234c.get(size);
                if (d0Var != null) {
                    int i4 = d0Var.f205c;
                    if (i4 >= i3) {
                        d0Var.a(-i2, z);
                    } else if (i4 >= i) {
                        d0Var.a(8);
                        e(size);
                    }
                }
            }
        }

        void a(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            d0VarK.n = null;
            d0VarK.o = false;
            d0VarK.c();
            b(d0VarK);
        }

        void a(androidx.recyclerview.widget.RecyclerView.b0 b0Var) {
            this.h = b0Var;
        }

        void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            androidx.recyclerview.widget.RecyclerView.w wVar = androidx.recyclerview.widget.RecyclerView.this.n;
            if (wVar != null) {
                wVar.a(d0Var);
            }
            androidx.recyclerview.widget.RecyclerView.g gVar = androidx.recyclerview.widget.RecyclerView.this.l;
            if (gVar != null) {
                gVar.d(d0Var);
            }
            androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
            if (recyclerView.h0 != null) {
                recyclerView.f.h(d0Var);
            }
        }

        void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, boolean z) {
            androidx.recyclerview.widget.RecyclerView.e(d0Var);
            android.view.View view = d0Var.f203a;
            androidx.recyclerview.widget.l lVar = androidx.recyclerview.widget.RecyclerView.this.o0;
            if (lVar != null) {
                a.c.e.a aVarB = lVar.b();
                a.c.e.m.a(view, aVarB instanceof androidx.recyclerview.widget.l.a ? ((androidx.recyclerview.widget.l.a) aVarB).c(view) : null);
            }
            if (z) {
                a(d0Var);
            }
            d0Var.r = null;
            d().a(d0Var);
        }

        void a(androidx.recyclerview.widget.RecyclerView.g gVar, androidx.recyclerview.widget.RecyclerView.g gVar2, boolean z) {
            a();
            d().a(gVar, gVar2, z);
        }

        void a(androidx.recyclerview.widget.RecyclerView.u uVar) {
            androidx.recyclerview.widget.RecyclerView.u uVar2 = this.g;
            if (uVar2 != null) {
                uVar2.c();
            }
            this.g = uVar;
            if (uVar == null || androidx.recyclerview.widget.RecyclerView.this.getAdapter() == null) {
                return;
            }
            this.g.a();
        }

        android.view.View b(int i, boolean z) {
            return a(i, z, Long.MAX_VALUE).f203a;
        }

        androidx.recyclerview.widget.RecyclerView.d0 b(int i) {
            int size;
            int iB;
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList = this.f233b;
            if (arrayList != null && (size = arrayList.size()) != 0) {
                for (int i2 = 0; i2 < size; i2++) {
                    androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f233b.get(i2);
                    if (!d0Var.A() && d0Var.i() == i) {
                        d0Var.a(32);
                        return d0Var;
                    }
                }
                if (androidx.recyclerview.widget.RecyclerView.this.l.b() && (iB = androidx.recyclerview.widget.RecyclerView.this.d.b(i)) > 0 && iB < androidx.recyclerview.widget.RecyclerView.this.l.a()) {
                    long jA = androidx.recyclerview.widget.RecyclerView.this.l.a(iB);
                    for (int i3 = 0; i3 < size; i3++) {
                        androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = this.f233b.get(i3);
                        if (!d0Var2.A() && d0Var2.g() == jA) {
                            d0Var2.a(32);
                            return d0Var2;
                        }
                    }
                }
            }
            return null;
        }

        void b() {
            int size = this.f234c.size();
            for (int i = 0; i < size; i++) {
                this.f234c.get(i).a();
            }
            int size2 = this.f232a.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.f232a.get(i2).a();
            }
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList = this.f233b;
            if (arrayList != null) {
                int size3 = arrayList.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    this.f233b.get(i3).a();
                }
            }
        }

        void b(int i, int i2) {
            int i3;
            int i4;
            int i5;
            int i6;
            if (i < i2) {
                i3 = -1;
                i5 = i;
                i4 = i2;
            } else {
                i3 = 1;
                i4 = i;
                i5 = i2;
            }
            int size = this.f234c.size();
            for (int i7 = 0; i7 < size; i7++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f234c.get(i7);
                if (d0Var != null && (i6 = d0Var.f205c) >= i5 && i6 <= i4) {
                    if (i6 == i) {
                        d0Var.a(i2 - i, false);
                    } else {
                        d0Var.a(i3, false);
                    }
                }
            }
        }

        public void b(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (d0VarK.s()) {
                androidx.recyclerview.widget.RecyclerView.this.removeDetachedView(view, false);
            }
            if (d0VarK.r()) {
                d0VarK.z();
            } else if (d0VarK.A()) {
                d0VarK.c();
            }
            b(d0VarK);
            if (androidx.recyclerview.widget.RecyclerView.this.M == null || d0VarK.p()) {
                return;
            }
            androidx.recyclerview.widget.RecyclerView.this.M.c(d0VarK);
        }

        void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            boolean z;
            boolean z2 = false;
            boolean z3 = true;
            if (d0Var.r() || d0Var.f203a.getParent() != null) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Scrapped or attached views may not be recycled. isScrap:");
                sb.append(d0Var.r());
                sb.append(" isAttached:");
                sb.append(d0Var.f203a.getParent() != null);
                sb.append(androidx.recyclerview.widget.RecyclerView.this.i());
                throw new java.lang.IllegalArgumentException(sb.toString());
            }
            if (d0Var.s()) {
                throw new java.lang.IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + d0Var + androidx.recyclerview.widget.RecyclerView.this.i());
            }
            if (d0Var.y()) {
                throw new java.lang.IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + androidx.recyclerview.widget.RecyclerView.this.i());
            }
            boolean zE = d0Var.e();
            androidx.recyclerview.widget.RecyclerView.g gVar = androidx.recyclerview.widget.RecyclerView.this.l;
            if ((gVar != null && zE && gVar.a(d0Var)) || d0Var.p()) {
                if (this.f <= 0 || d0Var.b(526)) {
                    z = false;
                } else {
                    int size = this.f234c.size();
                    if (size >= this.f && size > 0) {
                        e(0);
                        size--;
                    }
                    if (androidx.recyclerview.widget.RecyclerView.C0 && size > 0 && !androidx.recyclerview.widget.RecyclerView.this.g0.a(d0Var.f205c)) {
                        int i = size - 1;
                        while (i >= 0) {
                            if (!androidx.recyclerview.widget.RecyclerView.this.g0.a(this.f234c.get(i).f205c)) {
                                break;
                            } else {
                                i--;
                            }
                        }
                        size = i + 1;
                    }
                    this.f234c.add(size, d0Var);
                    z = true;
                }
                if (z) {
                    z2 = z;
                    z3 = false;
                } else {
                    a(d0Var, true);
                    z2 = z;
                }
            } else {
                z3 = false;
            }
            androidx.recyclerview.widget.RecyclerView.this.f.h(d0Var);
            if (z2 || z3 || !zE) {
                return;
            }
            d0Var.r = null;
        }

        android.view.View c(int i) {
            return this.f232a.get(i).f203a;
        }

        void c() {
            this.f232a.clear();
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList = this.f233b;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        void c(int i, int i2) {
            int i3;
            int i4 = i2 + i;
            for (int size = this.f234c.size() - 1; size >= 0; size--) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f234c.get(size);
                if (d0Var != null && (i3 = d0Var.f205c) >= i && i3 < i4) {
                    d0Var.a(2);
                    e(size);
                }
            }
        }

        void c(android.view.View view) {
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList;
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(view);
            if (!d0VarK.b(12) && d0VarK.t() && !androidx.recyclerview.widget.RecyclerView.this.a(d0VarK)) {
                if (this.f233b == null) {
                    this.f233b = new java.util.ArrayList<>();
                }
                d0VarK.a(this, true);
                arrayList = this.f233b;
            } else {
                if (d0VarK.o() && !d0VarK.q() && !androidx.recyclerview.widget.RecyclerView.this.l.b()) {
                    throw new java.lang.IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + androidx.recyclerview.widget.RecyclerView.this.i());
                }
                d0VarK.a(this, false);
                arrayList = this.f232a;
            }
            arrayList.add(d0VarK);
        }

        void c(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            (d0Var.o ? this.f233b : this.f232a).remove(d0Var);
            d0Var.n = null;
            d0Var.o = false;
            d0Var.c();
        }

        public android.view.View d(int i) {
            return b(i, false);
        }

        androidx.recyclerview.widget.RecyclerView.u d() {
            if (this.g == null) {
                this.g = new androidx.recyclerview.widget.RecyclerView.u();
            }
            return this.g;
        }

        boolean d(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            if (d0Var.q()) {
                return androidx.recyclerview.widget.RecyclerView.this.h0.d();
            }
            int i = d0Var.f205c;
            if (i >= 0 && i < androidx.recyclerview.widget.RecyclerView.this.l.a()) {
                if (androidx.recyclerview.widget.RecyclerView.this.h0.d() || androidx.recyclerview.widget.RecyclerView.this.l.b(d0Var.f205c) == d0Var.h()) {
                    return !androidx.recyclerview.widget.RecyclerView.this.l.b() || d0Var.g() == androidx.recyclerview.widget.RecyclerView.this.l.a(d0Var.f205c);
                }
                return false;
            }
            throw new java.lang.IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + d0Var + androidx.recyclerview.widget.RecyclerView.this.i());
        }

        int e() {
            return this.f232a.size();
        }

        void e(int i) {
            a(this.f234c.get(i), true);
            this.f234c.remove(i);
        }

        public java.util.List<androidx.recyclerview.widget.RecyclerView.d0> f() {
            return this.d;
        }

        public void f(int i) {
            this.e = i;
            j();
        }

        void g() {
            int size = this.f234c.size();
            for (int i = 0; i < size; i++) {
                androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) this.f234c.get(i).f203a.getLayoutParams();
                if (pVar != null) {
                    pVar.f226c = true;
                }
            }
        }

        void h() {
            int size = this.f234c.size();
            for (int i = 0; i < size; i++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.f234c.get(i);
                if (d0Var != null) {
                    d0Var.a(6);
                    d0Var.a((java.lang.Object) null);
                }
            }
            androidx.recyclerview.widget.RecyclerView.g gVar = androidx.recyclerview.widget.RecyclerView.this.l;
            if (gVar == null || !gVar.b()) {
                i();
            }
        }

        void i() {
            for (int size = this.f234c.size() - 1; size >= 0; size--) {
                e(size);
            }
            this.f234c.clear();
            if (androidx.recyclerview.widget.RecyclerView.C0) {
                androidx.recyclerview.widget.RecyclerView.this.g0.a();
            }
        }

        void j() {
            androidx.recyclerview.widget.RecyclerView.o oVar = androidx.recyclerview.widget.RecyclerView.this.m;
            this.f = this.e + (oVar != null ? oVar.m : 0);
            for (int size = this.f234c.size() - 1; size >= 0 && this.f234c.size() > this.f; size--) {
                e(size);
            }
        }
    }

    public interface w {
        void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var);
    }

    private class x extends androidx.recyclerview.widget.RecyclerView.i {
        x() {
        }

        void a() {
            if (androidx.recyclerview.widget.RecyclerView.B0) {
                androidx.recyclerview.widget.RecyclerView recyclerView = androidx.recyclerview.widget.RecyclerView.this;
                if (recyclerView.s && recyclerView.r) {
                    a.c.e.m.a(recyclerView, recyclerView.h);
                    return;
                }
            }
            androidx.recyclerview.widget.RecyclerView recyclerView2 = androidx.recyclerview.widget.RecyclerView.this;
            recyclerView2.A = true;
            recyclerView2.requestLayout();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.i
        public void a(int i, int i2, int i3) {
            androidx.recyclerview.widget.RecyclerView.this.a((java.lang.String) null);
            if (androidx.recyclerview.widget.RecyclerView.this.d.a(i, i2, i3)) {
                a();
            }
        }
    }

    public static class y extends a.d.a.a {
        public static final android.os.Parcelable.Creator<androidx.recyclerview.widget.RecyclerView.y> CREATOR = new androidx.recyclerview.widget.RecyclerView.y.a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        android.os.Parcelable f236c;

        static class a implements android.os.Parcelable.ClassLoaderCreator<androidx.recyclerview.widget.RecyclerView.y> {
            a() {
            }

            @Override // android.os.Parcelable.Creator
            public androidx.recyclerview.widget.RecyclerView.y createFromParcel(android.os.Parcel parcel) {
                return new androidx.recyclerview.widget.RecyclerView.y(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public androidx.recyclerview.widget.RecyclerView.y createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
                return new androidx.recyclerview.widget.RecyclerView.y(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public androidx.recyclerview.widget.RecyclerView.y[] newArray(int i) {
                return new androidx.recyclerview.widget.RecyclerView.y[i];
            }
        }

        y(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f236c = parcel.readParcelable(classLoader == null ? androidx.recyclerview.widget.RecyclerView.o.class.getClassLoader() : classLoader);
        }

        y(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        void a(androidx.recyclerview.widget.RecyclerView.y yVar) {
            this.f236c = yVar.f236c;
        }

        @Override // a.d.a.a, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.f236c, 0);
        }
    }

    public static abstract class z {
        public abstract int a();

        public abstract void a(int i);

        abstract void a(int i, int i2);

        protected abstract void a(android.view.View view);

        public abstract boolean b();

        public abstract boolean c();

        protected final void d() {
            throw null;
        }
    }

    static {
        int i2 = android.os.Build.VERSION.SDK_INT;
        z0 = i2 == 18 || i2 == 19 || i2 == 20;
        A0 = android.os.Build.VERSION.SDK_INT >= 23;
        B0 = android.os.Build.VERSION.SDK_INT >= 16;
        C0 = android.os.Build.VERSION.SDK_INT >= 21;
        D0 = android.os.Build.VERSION.SDK_INT <= 15;
        E0 = android.os.Build.VERSION.SDK_INT <= 15;
        java.lang.Class<?> cls = java.lang.Integer.TYPE;
        F0 = new java.lang.Class[]{android.content.Context.class, android.util.AttributeSet.class, cls, cls};
        G0 = new androidx.recyclerview.widget.RecyclerView.c();
    }

    public RecyclerView(android.content.Context context) {
        this(context, null);
    }

    public RecyclerView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, a.f.a.recyclerViewStyle);
    }

    public RecyclerView(android.content.Context context, android.util.AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f191a = new androidx.recyclerview.widget.RecyclerView.x();
        this.f192b = new androidx.recyclerview.widget.RecyclerView.v();
        this.f = new androidx.recyclerview.widget.p();
        this.h = new androidx.recyclerview.widget.RecyclerView.a();
        this.i = new android.graphics.Rect();
        this.j = new android.graphics.Rect();
        this.k = new android.graphics.RectF();
        this.o = new java.util.ArrayList<>();
        this.p = new java.util.ArrayList<>();
        this.v = 0;
        this.D = false;
        this.E = false;
        this.F = 0;
        this.G = 0;
        this.H = new androidx.recyclerview.widget.RecyclerView.k();
        this.M = new androidx.recyclerview.widget.c();
        this.N = 0;
        this.O = -1;
        this.b0 = Float.MIN_VALUE;
        this.c0 = Float.MIN_VALUE;
        boolean z2 = true;
        this.d0 = true;
        this.e0 = new androidx.recyclerview.widget.RecyclerView.c0();
        this.g0 = C0 ? new androidx.recyclerview.widget.e.b() : null;
        this.h0 = new androidx.recyclerview.widget.RecyclerView.a0();
        this.k0 = false;
        this.l0 = false;
        this.m0 = new androidx.recyclerview.widget.RecyclerView.m();
        this.n0 = false;
        this.q0 = new int[2];
        this.s0 = new int[2];
        this.t0 = new int[2];
        this.u0 = new int[2];
        this.v0 = new java.util.ArrayList();
        this.w0 = new androidx.recyclerview.widget.RecyclerView.b();
        this.x0 = new androidx.recyclerview.widget.RecyclerView.d();
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
        this.U = viewConfiguration.getScaledTouchSlop();
        this.b0 = a.c.e.n.b(viewConfiguration, context);
        this.c0 = a.c.e.n.c(viewConfiguration, context);
        this.W = viewConfiguration.getScaledMinimumFlingVelocity();
        this.a0 = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.M.a(this.m0);
        k();
        G();
        F();
        if (a.c.e.m.g(this) == 0) {
            a.c.e.m.b(this, 1);
        }
        this.B = (android.view.accessibility.AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new androidx.recyclerview.widget.l(this));
        android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.f.d.RecyclerView, i2, 0);
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, a.f.d.RecyclerView, attributeSet, typedArrayObtainStyledAttributes, i2, 0);
        }
        java.lang.String string = typedArrayObtainStyledAttributes.getString(a.f.d.RecyclerView_layoutManager);
        if (typedArrayObtainStyledAttributes.getInt(a.f.d.RecyclerView_android_descendantFocusability, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.g = typedArrayObtainStyledAttributes.getBoolean(a.f.d.RecyclerView_android_clipToPadding, true);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(a.f.d.RecyclerView_fastScrollEnabled, false);
        this.t = z3;
        if (z3) {
            a((android.graphics.drawable.StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(a.f.d.RecyclerView_fastScrollVerticalThumbDrawable), typedArrayObtainStyledAttributes.getDrawable(a.f.d.RecyclerView_fastScrollVerticalTrackDrawable), (android.graphics.drawable.StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(a.f.d.RecyclerView_fastScrollHorizontalThumbDrawable), typedArrayObtainStyledAttributes.getDrawable(a.f.d.RecyclerView_fastScrollHorizontalTrackDrawable));
        }
        typedArrayObtainStyledAttributes.recycle();
        a(context, string, attributeSet, i2, 0);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            android.content.res.TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, y0, i2, 0);
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                saveAttributeDataForStyleable(context, y0, attributeSet, typedArrayObtainStyledAttributes2, i2, 0);
            }
            z2 = typedArrayObtainStyledAttributes2.getBoolean(0, true);
            typedArrayObtainStyledAttributes2.recycle();
        }
        setNestedScrollingEnabled(z2);
    }

    private void A() {
        this.h0.a(1);
        a(this.h0);
        this.h0.j = false;
        w();
        this.f.a();
        q();
        I();
        N();
        androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
        a0Var.i = a0Var.k && this.l0;
        this.l0 = false;
        this.k0 = false;
        androidx.recyclerview.widget.RecyclerView.a0 a0Var2 = this.h0;
        a0Var2.h = a0Var2.l;
        a0Var2.f = this.l.a();
        a(this.q0);
        if (this.h0.k) {
            int iA = this.e.a();
            for (int i2 = 0; i2 < iA; i2++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.c(i2));
                if (!d0VarK.y() && (!d0VarK.o() || this.l.b())) {
                    this.f.c(d0VarK, this.M.a(this.h0, d0VarK, androidx.recyclerview.widget.RecyclerView.l.e(d0VarK), d0VarK.k()));
                    if (this.h0.i && d0VarK.t() && !d0VarK.q() && !d0VarK.y() && !d0VarK.o()) {
                        this.f.a(c(d0VarK), d0VarK);
                    }
                }
            }
        }
        if (this.h0.l) {
            v();
            androidx.recyclerview.widget.RecyclerView.a0 a0Var3 = this.h0;
            boolean z2 = a0Var3.g;
            a0Var3.g = false;
            this.m.e(this.f192b, a0Var3);
            this.h0.g = z2;
            for (int i3 = 0; i3 < this.e.a(); i3++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0VarK2 = k(this.e.c(i3));
                if (!d0VarK2.y() && !this.f.c(d0VarK2)) {
                    int iE = androidx.recyclerview.widget.RecyclerView.l.e(d0VarK2);
                    boolean zB = d0VarK2.b(8192);
                    if (!zB) {
                        iE |= 4096;
                    }
                    androidx.recyclerview.widget.RecyclerView.l.c cVarA = this.M.a(this.h0, d0VarK2, iE, d0VarK2.k());
                    if (zB) {
                        a(d0VarK2, cVarA);
                    } else {
                        this.f.a(d0VarK2, cVarA);
                    }
                }
            }
        }
        a();
        r();
        c(false);
        this.h0.e = 2;
    }

    private void B() {
        w();
        q();
        this.h0.a(6);
        this.d.b();
        this.h0.f = this.l.a();
        androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
        a0Var.d = 0;
        a0Var.h = false;
        this.m.e(this.f192b, a0Var);
        androidx.recyclerview.widget.RecyclerView.a0 a0Var2 = this.h0;
        a0Var2.g = false;
        this.f193c = null;
        a0Var2.k = a0Var2.k && this.M != null;
        this.h0.e = 4;
        r();
        c(false);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0079  */
    private void C() {
        this.h0.a(4);
        w();
        q();
        androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
        a0Var.e = 1;
        if (a0Var.k) {
            for (int iA = this.e.a() - 1; iA >= 0; iA--) {
                androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.c(iA));
                if (!d0VarK.y()) {
                    long jC = c(d0VarK);
                    androidx.recyclerview.widget.RecyclerView.l.c cVarA = this.M.a(this.h0, d0VarK);
                    androidx.recyclerview.widget.RecyclerView.d0 d0VarA = this.f.a(jC);
                    if (d0VarA == null || d0VarA.y()) {
                        this.f.b(d0VarK, cVarA);
                    } else {
                        boolean zB = this.f.b(d0VarA);
                        boolean zB2 = this.f.b(d0VarK);
                        if (zB && d0VarA == d0VarK) {
                            this.f.b(d0VarK, cVarA);
                        } else {
                            androidx.recyclerview.widget.RecyclerView.l.c cVarF = this.f.f(d0VarA);
                            this.f.b(d0VarK, cVarA);
                            androidx.recyclerview.widget.RecyclerView.l.c cVarE = this.f.e(d0VarK);
                            if (cVarF == null) {
                                a(jC, d0VarK, d0VarA);
                            } else {
                                a(d0VarA, d0VarK, cVarF, cVarE, zB, zB2);
                            }
                        }
                    }
                }
            }
            this.f.a(this.x0);
        }
        this.m.c(this.f192b);
        androidx.recyclerview.widget.RecyclerView.a0 a0Var2 = this.h0;
        a0Var2.f197c = a0Var2.f;
        this.D = false;
        this.E = false;
        a0Var2.k = false;
        a0Var2.l = false;
        this.m.h = false;
        java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList = this.f192b.f233b;
        if (arrayList != null) {
            arrayList.clear();
        }
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar.n) {
            oVar.m = 0;
            oVar.n = false;
            this.f192b.j();
        }
        this.m.g(this.h0);
        r();
        c(false);
        this.f.a();
        int[] iArr = this.q0;
        if (k(iArr[0], iArr[1])) {
            d(0, 0);
        }
        J();
        L();
    }

    private android.view.View D() {
        androidx.recyclerview.widget.RecyclerView.d0 d0VarB;
        int i2 = this.h0.m;
        if (i2 == -1) {
            i2 = 0;
        }
        int iA = this.h0.a();
        for (int i3 = i2; i3 < iA; i3++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarB2 = b(i3);
            if (d0VarB2 == null) {
                break;
            }
            if (d0VarB2.f203a.hasFocusable()) {
                return d0VarB2.f203a;
            }
        }
        int iMin = java.lang.Math.min(iA, i2);
        do {
            iMin--;
            if (iMin < 0 || (d0VarB = b(iMin)) == null) {
                return null;
            }
        } while (!d0VarB.f203a.hasFocusable());
        return d0VarB.f203a;
    }

    private boolean E() {
        int iA = this.e.a();
        for (int i2 = 0; i2 < iA; i2++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.c(i2));
            if (d0VarK != null && !d0VarK.y() && d0VarK.t()) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.SuppressLint({"InlinedApi"})
    private void F() {
        if (a.c.e.m.h(this) == 0) {
            a.c.e.m.c(this, 8);
        }
    }

    private void G() {
        this.e = new androidx.recyclerview.widget.b(new androidx.recyclerview.widget.RecyclerView.e());
    }

    private boolean H() {
        return this.M != null && this.m.C();
    }

    private void I() {
        if (this.D) {
            this.d.f();
            if (this.E) {
                this.m.d(this);
            }
        }
        if (H()) {
            this.d.e();
        } else {
            this.d.b();
        }
        boolean z2 = false;
        boolean z3 = this.k0 || this.l0;
        this.h0.k = this.u && this.M != null && (this.D || z3 || this.m.h) && (!this.D || this.l.b());
        androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
        if (a0Var.k && z3 && !this.D && H()) {
            z2 = true;
        }
        a0Var.l = z2;
    }

    private void J() {
        android.view.View viewFindViewById;
        if (!this.d0 || this.l == null || !hasFocus() || getDescendantFocusability() == 393216) {
            return;
        }
        if (getDescendantFocusability() == 131072 && isFocused()) {
            return;
        }
        if (!isFocused()) {
            android.view.View focusedChild = getFocusedChild();
            if (!E0 || (focusedChild.getParent() != null && focusedChild.hasFocus())) {
                if (!this.e.c(focusedChild)) {
                    return;
                }
            } else if (this.e.a() == 0) {
                requestFocus();
                return;
            }
        }
        android.view.View viewD = null;
        androidx.recyclerview.widget.RecyclerView.d0 d0VarA = (this.h0.n == -1 || !this.l.b()) ? null : a(this.h0.n);
        if (d0VarA != null && !this.e.c(d0VarA.f203a) && d0VarA.f203a.hasFocusable()) {
            viewD = d0VarA.f203a;
        } else if (this.e.a() > 0) {
            viewD = D();
        }
        if (viewD != null) {
            int i2 = this.h0.o;
            if (i2 != -1 && (viewFindViewById = viewD.findViewById(i2)) != null && viewFindViewById.isFocusable()) {
                viewD = viewFindViewById;
            }
            viewD.requestFocus();
        }
    }

    private void K() {
        boolean zIsFinished;
        android.widget.EdgeEffect edgeEffect = this.I;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            zIsFinished = this.I.isFinished();
        } else {
            zIsFinished = false;
        }
        android.widget.EdgeEffect edgeEffect2 = this.J;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            zIsFinished |= this.J.isFinished();
        }
        android.widget.EdgeEffect edgeEffect3 = this.K;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            zIsFinished |= this.K.isFinished();
        }
        android.widget.EdgeEffect edgeEffect4 = this.L;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            zIsFinished |= this.L.isFinished();
        }
        if (zIsFinished) {
            a.c.e.m.p(this);
        }
    }

    private void L() {
        androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
        a0Var.n = -1L;
        a0Var.m = -1;
        a0Var.o = -1;
    }

    private void M() {
        android.view.VelocityTracker velocityTracker = this.P;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        g(0);
        K();
    }

    private void N() {
        int iF;
        android.view.View focusedChild = (this.d0 && hasFocus() && this.l != null) ? getFocusedChild() : null;
        androidx.recyclerview.widget.RecyclerView.d0 d0VarD = focusedChild != null ? d(focusedChild) : null;
        if (d0VarD == null) {
            L();
            return;
        }
        this.h0.n = this.l.b() ? d0VarD.g() : -1L;
        androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
        if (this.D) {
            iF = -1;
        } else {
            iF = d0VarD.q() ? d0VarD.d : d0VarD.f();
        }
        a0Var.m = iF;
        this.h0.o = l(d0VarD.f203a);
    }

    private void O() {
        this.e0.b();
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.B();
        }
    }

    private java.lang.String a(android.content.Context context, java.lang.String str) {
        if (str.charAt(0) == '.') {
            return context.getPackageName() + str;
        }
        if (str.contains(".")) {
            return str;
        }
        return androidx.recyclerview.widget.RecyclerView.class.getPackage().getName() + '.' + str;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003d  */
    /* JADX WARN: Code duplicated, block: B:13:0x0053  */
    /* JADX WARN: Code duplicated, block: B:15:0x0057  */
    /* JADX WARN: Code duplicated, block: B:16:0x006e  */
    private void a(float f2, float f3, float f4, float f5) {
        boolean z2;
        android.widget.EdgeEffect edgeEffect;
        float width;
        float height;
        boolean z3 = true;
        if (f3 >= 0.0f) {
            if (f3 > 0.0f) {
                g();
                edgeEffect = this.K;
                width = f3 / getWidth();
                height = f4 / getHeight();
            } else {
                z2 = false;
            }
            if (f5 < 0.0f) {
                h();
                a.c.f.a.a(this.J, (-f5) / getHeight(), f2 / getWidth());
            } else if (f5 > 0.0f) {
                e();
                a.c.f.a.a(this.L, f5 / getHeight(), 1.0f - (f2 / getWidth()));
            } else {
                z3 = z2;
            }
            if (z3 && f3 == 0.0f && f5 == 0.0f) {
                return;
            }
            a.c.e.m.p(this);
        }
        f();
        edgeEffect = this.I;
        width = (-f3) / getWidth();
        height = 1.0f - (f4 / getHeight());
        a.c.f.a.a(edgeEffect, width, height);
        z2 = true;
        if (f5 < 0.0f) {
            h();
            a.c.f.a.a(this.J, (-f5) / getHeight(), f2 / getWidth());
        } else if (f5 > 0.0f) {
            e();
            a.c.f.a.a(this.L, f5 / getHeight(), 1.0f - (f2 / getWidth()));
        } else {
            z3 = z2;
        }
        if (z3) {
        }
        a.c.e.m.p(this);
    }

    private void a(long j2, androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2) {
        int iA = this.e.a();
        for (int i2 = 0; i2 < iA; i2++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.c(i2));
            if (d0VarK != d0Var && c(d0VarK) == j2) {
                androidx.recyclerview.widget.RecyclerView.g gVar = this.l;
                if (gVar == null || !gVar.b()) {
                    throw new java.lang.IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + d0VarK + " \n View Holder 2:" + d0Var + i());
                }
                throw new java.lang.IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + d0VarK + " \n View Holder 2:" + d0Var + i());
            }
        }
        android.util.Log.e("RecyclerView", "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + d0Var2 + " cannot be found but it is necessary for " + d0Var + i());
    }

    private void a(android.content.Context context, java.lang.String str, android.util.AttributeSet attributeSet, int i2, int i3) {
        java.lang.reflect.Constructor constructor;
        if (str != null) {
            java.lang.String strTrim = str.trim();
            if (strTrim.isEmpty()) {
                return;
            }
            java.lang.String strA = a(context, strTrim);
            try {
                java.lang.Class<? extends U> clsAsSubclass = java.lang.Class.forName(strA, false, isInEditMode() ? getClass().getClassLoader() : context.getClassLoader()).asSubclass(androidx.recyclerview.widget.RecyclerView.o.class);
                java.lang.Object[] objArr = null;
                try {
                    constructor = clsAsSubclass.getConstructor(F0);
                    objArr = new java.lang.Object[]{context, attributeSet, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)};
                } catch (java.lang.NoSuchMethodException e2) {
                    try {
                        constructor = clsAsSubclass.getConstructor(new java.lang.Class[0]);
                    } catch (java.lang.NoSuchMethodException e3) {
                        e3.initCause(e2);
                        throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + strA, e3);
                    }
                }
                constructor.setAccessible(true);
                setLayoutManager((androidx.recyclerview.widget.RecyclerView.o) constructor.newInstance(objArr));
            } catch (java.lang.ClassCastException e4) {
                throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + strA, e4);
            } catch (java.lang.ClassNotFoundException e5) {
                throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + strA, e5);
            } catch (java.lang.IllegalAccessException e6) {
                throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + strA, e6);
            } catch (java.lang.InstantiationException e7) {
                throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + strA, e7);
            } catch (java.lang.reflect.InvocationTargetException e8) {
                throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + strA, e8);
            }
        }
    }

    static void a(android.view.View view, android.graphics.Rect rect) {
        androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
        android.graphics.Rect rect2 = pVar.f225b;
        rect.set((view.getLeft() - rect2.left) - ((android.view.ViewGroup.MarginLayoutParams) pVar).leftMargin, (view.getTop() - rect2.top) - ((android.view.ViewGroup.MarginLayoutParams) pVar).topMargin, view.getRight() + rect2.right + ((android.view.ViewGroup.MarginLayoutParams) pVar).rightMargin, view.getBottom() + rect2.bottom + ((android.view.ViewGroup.MarginLayoutParams) pVar).bottomMargin);
    }

    private void a(android.view.View view, android.view.View view2) {
        android.view.View view3 = view2 != null ? view2 : view;
        this.i.set(0, 0, view3.getWidth(), view3.getHeight());
        android.view.ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof androidx.recyclerview.widget.RecyclerView.p) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) layoutParams;
            if (!pVar.f226c) {
                android.graphics.Rect rect = pVar.f225b;
                android.graphics.Rect rect2 = this.i;
                rect2.left -= rect.left;
                rect2.right += rect.right;
                rect2.top -= rect.top;
                rect2.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.i);
            offsetRectIntoDescendantCoords(view, this.i);
        }
        this.m.a(this, view, this.i, !this.u, view2 == null);
    }

    private void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2, boolean z2, boolean z3) {
        d0Var.a(false);
        if (z2) {
            d(d0Var);
        }
        if (d0Var != d0Var2) {
            if (z3) {
                d(d0Var2);
            }
            d0Var.h = d0Var2;
            d(d0Var);
            this.f192b.c(d0Var);
            d0Var2.a(false);
            d0Var2.i = d0Var;
        }
        if (this.M.a(d0Var, d0Var2, cVar, cVar2)) {
            s();
        }
    }

    private void a(androidx.recyclerview.widget.RecyclerView.g gVar, boolean z2, boolean z3) {
        androidx.recyclerview.widget.RecyclerView.g gVar2 = this.l;
        if (gVar2 != null) {
            gVar2.b(this.f191a);
            this.l.b(this);
        }
        if (!z2 || z3) {
            t();
        }
        this.d.f();
        androidx.recyclerview.widget.RecyclerView.g gVar3 = this.l;
        this.l = gVar;
        if (gVar != null) {
            gVar.a(this.f191a);
            gVar.a(this);
        }
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.a(gVar3, this.l);
        }
        this.f192b.a(gVar3, this.l, z2);
        this.h0.g = true;
    }

    private void a(int[] iArr) {
        int iA = this.e.a();
        if (iA == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MIN_VALUE;
        for (int i4 = 0; i4 < iA; i4++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.c(i4));
            if (!d0VarK.y()) {
                int i5 = d0VarK.i();
                if (i5 < i2) {
                    i2 = i5;
                }
                if (i5 > i3) {
                    i3 = i5;
                }
            }
        }
        iArr[0] = i2;
        iArr[1] = i3;
    }

    private boolean a(android.view.MotionEvent motionEvent) {
        androidx.recyclerview.widget.RecyclerView.s sVar = this.q;
        if (sVar == null) {
            if (motionEvent.getAction() == 0) {
                return false;
            }
            return b(motionEvent);
        }
        sVar.b(this, motionEvent);
        int action = motionEvent.getAction();
        if (action == 3 || action == 1) {
            this.q = null;
        }
        return true;
    }

    private boolean a(android.view.View view, android.view.View view2, int i2) {
        int i3;
        if (view2 == null || view2 == this || c(view2) == null) {
            return false;
        }
        if (view == null || c(view) == null) {
            return true;
        }
        this.i.set(0, 0, view.getWidth(), view.getHeight());
        this.j.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.i);
        offsetDescendantRectToMyCoords(view2, this.j);
        byte b2 = -1;
        int i4 = this.m.j() == 1 ? -1 : 1;
        android.graphics.Rect rect = this.i;
        int i5 = rect.left;
        int i6 = this.j.left;
        if ((i5 < i6 || rect.right <= i6) && this.i.right < this.j.right) {
            i3 = 1;
        } else {
            android.graphics.Rect rect2 = this.i;
            int i7 = rect2.right;
            int i8 = this.j.right;
            i3 = ((i7 > i8 || rect2.left >= i8) && this.i.left > this.j.left) ? -1 : 0;
        }
        android.graphics.Rect rect3 = this.i;
        int i9 = rect3.top;
        int i10 = this.j.top;
        if ((i9 < i10 || rect3.bottom <= i10) && this.i.bottom < this.j.bottom) {
            b2 = 1;
        } else {
            android.graphics.Rect rect4 = this.i;
            int i11 = rect4.bottom;
            int i12 = this.j.bottom;
            if ((i11 <= i12 && rect4.top < i12) || this.i.top <= this.j.top) {
                b2 = 0;
            }
        }
        if (i2 == 1) {
            return b2 < 0 || (b2 == 0 && i3 * i4 <= 0);
        }
        if (i2 == 2) {
            return b2 > 0 || (b2 == 0 && i3 * i4 >= 0);
        }
        if (i2 == 17) {
            return i3 < 0;
        }
        if (i2 == 33) {
            return b2 < 0;
        }
        if (i2 == 66) {
            return i3 > 0;
        }
        if (i2 == 130) {
            return b2 > 0;
        }
        throw new java.lang.IllegalArgumentException("Invalid direction: " + i2 + i());
    }

    private boolean b(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int size = this.p.size();
        for (int i2 = 0; i2 < size; i2++) {
            androidx.recyclerview.widget.RecyclerView.s sVar = this.p.get(i2);
            if (sVar.a(this, motionEvent) && action != 3) {
                this.q = sVar;
                return true;
            }
        }
        return false;
    }

    private void c(android.view.MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.O) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.O = motionEvent.getPointerId(i2);
            int x2 = (int) (motionEvent.getX(i2) + 0.5f);
            this.S = x2;
            this.Q = x2;
            int y2 = (int) (motionEvent.getY(i2) + 0.5f);
            this.T = y2;
            this.R = y2;
        }
    }

    private void d(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        android.view.View view = d0Var.f203a;
        boolean z2 = view.getParent() == this;
        this.f192b.c(e(view));
        if (d0Var.s()) {
            this.e.a(view, -1, view.getLayoutParams(), true);
            return;
        }
        androidx.recyclerview.widget.b bVar = this.e;
        if (z2) {
            bVar.a(view);
        } else {
            bVar.a(view, true);
        }
    }

    static void e(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        java.lang.ref.WeakReference<androidx.recyclerview.widget.RecyclerView> weakReference = d0Var.f204b;
        if (weakReference == null) {
            return;
        }
        java.lang.Object parent = weakReference.get();
        while (true) {
            android.view.View view = (android.view.View) parent;
            while (true) {
                if (view == null) {
                    d0Var.f204b = null;
                    return;
                } else {
                    if (view == d0Var.f203a) {
                        return;
                    }
                    parent = view.getParent();
                    if (parent instanceof android.view.View) {
                        break;
                    } else {
                        view = null;
                    }
                }
            }
        }
    }

    private a.c.e.h getScrollingChildHelper() {
        if (this.r0 == null) {
            this.r0 = new a.c.e.h(this);
        }
        return this.r0;
    }

    static androidx.recyclerview.widget.RecyclerView j(android.view.View view) {
        if (!(view instanceof android.view.ViewGroup)) {
            return null;
        }
        if (view instanceof androidx.recyclerview.widget.RecyclerView) {
            return (androidx.recyclerview.widget.RecyclerView) view;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            androidx.recyclerview.widget.RecyclerView recyclerViewJ = j(viewGroup.getChildAt(i2));
            if (recyclerViewJ != null) {
                return recyclerViewJ;
            }
        }
        return null;
    }

    static androidx.recyclerview.widget.RecyclerView.d0 k(android.view.View view) {
        if (view == null) {
            return null;
        }
        return ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams()).f224a;
    }

    private boolean k(int i2, int i3) {
        a(this.q0);
        int[] iArr = this.q0;
        return (iArr[0] == i2 && iArr[1] == i3) ? false : true;
    }

    private int l(android.view.View view) {
        int id;
        loop0: while (true) {
            id = view.getId();
            while (!view.isFocused() && (view instanceof android.view.ViewGroup) && view.hasFocus()) {
                view = ((android.view.ViewGroup) view).getFocusedChild();
                if (view.getId() != -1) {
                }
            }
            break loop0;
        }
        return id;
    }

    private void y() {
        M();
        setScrollState(0);
    }

    private void z() {
        int i2 = this.z;
        this.z = 0;
        if (i2 == 0 || !m()) {
            return;
        }
        android.view.accessibility.AccessibilityEvent accessibilityEventObtain = android.view.accessibility.AccessibilityEvent.obtain();
        accessibilityEventObtain.setEventType(2048);
        a.c.e.q.b.a(accessibilityEventObtain, i2);
        sendAccessibilityEventUnchecked(accessibilityEventObtain);
    }

    public android.view.View a(float f2, float f3) {
        for (int iA = this.e.a() - 1; iA >= 0; iA--) {
            android.view.View viewC = this.e.c(iA);
            float translationX = viewC.getTranslationX();
            float translationY = viewC.getTranslationY();
            if (f2 >= viewC.getLeft() + translationX && f2 <= viewC.getRight() + translationX && f3 >= viewC.getTop() + translationY && f3 <= viewC.getBottom() + translationY) {
                return viewC;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002a  */
    /* JADX WARN: Code duplicated, block: B:17:0x0034  */
    /* JADX WARN: Code duplicated, block: B:22:0x0036 A[SYNTHETIC] */
    androidx.recyclerview.widget.RecyclerView.d0 a(int i2, boolean z2) {
        int iB = this.e.b();
        androidx.recyclerview.widget.RecyclerView.d0 d0Var = null;
        for (int i3 = 0; i3 < iB; i3++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i3));
            if (d0VarK != null && !d0VarK.q()) {
                if (z2) {
                    if (d0VarK.f205c != i2) {
                        continue;
                    } else {
                        if (this.e.c(d0VarK.f203a)) {
                            return d0VarK;
                        }
                        d0Var = d0VarK;
                    }
                } else if (d0VarK.i() != i2) {
                    continue;
                } else {
                    if (this.e.c(d0VarK.f203a)) {
                        return d0VarK;
                    }
                    d0Var = d0VarK;
                }
            }
        }
        return d0Var;
    }

    public androidx.recyclerview.widget.RecyclerView.d0 a(long j2) {
        androidx.recyclerview.widget.RecyclerView.g gVar = this.l;
        androidx.recyclerview.widget.RecyclerView.d0 d0Var = null;
        if (gVar != null && gVar.b()) {
            int iB = this.e.b();
            for (int i2 = 0; i2 < iB; i2++) {
                androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i2));
                if (d0VarK != null && !d0VarK.q() && d0VarK.g() == j2) {
                    if (!this.e.c(d0VarK.f203a)) {
                        return d0VarK;
                    }
                    d0Var = d0VarK;
                }
            }
        }
        return d0Var;
    }

    void a() {
        int iB = this.e.b();
        for (int i2 = 0; i2 < iB; i2++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i2));
            if (!d0VarK.y()) {
                d0VarK.a();
            }
        }
        this.f192b.b();
    }

    void a(int i2) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.f(i2);
        }
        e(i2);
        androidx.recyclerview.widget.RecyclerView.t tVar = this.i0;
        if (tVar != null) {
            tVar.a(this, i2);
        }
        java.util.List<androidx.recyclerview.widget.RecyclerView.t> list = this.j0;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.j0.get(size).a(this, i2);
            }
        }
    }

    void a(int i2, int i3) {
        if (i2 < 0) {
            f();
            if (this.I.isFinished()) {
                this.I.onAbsorb(-i2);
            }
        } else if (i2 > 0) {
            g();
            if (this.K.isFinished()) {
                this.K.onAbsorb(i2);
            }
        }
        if (i3 < 0) {
            h();
            if (this.J.isFinished()) {
                this.J.onAbsorb(-i3);
            }
        } else if (i3 > 0) {
            e();
            if (this.L.isFinished()) {
                this.L.onAbsorb(i3);
            }
        }
        if (i2 == 0 && i3 == 0) {
            return;
        }
        a.c.e.m.p(this);
    }

    public final void a(int i2, int i3, int i4, int i5, int[] iArr, int i6, int[] iArr2) {
        getScrollingChildHelper().a(i2, i3, i4, i5, iArr, i6, iArr2);
    }

    public void a(int i2, int i3, android.view.animation.Interpolator interpolator) {
        a(i2, i3, interpolator, Integer.MIN_VALUE);
    }

    public void a(int i2, int i3, android.view.animation.Interpolator interpolator, int i4) {
        a(i2, i3, interpolator, i4, false);
    }

    void a(int i2, int i3, android.view.animation.Interpolator interpolator, int i4, boolean z2) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null) {
            android.util.Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.x) {
            return;
        }
        if (!oVar.a()) {
            i2 = 0;
        }
        if (!this.m.b()) {
            i3 = 0;
        }
        if (i2 == 0 && i3 == 0) {
            return;
        }
        if (!(i4 == Integer.MIN_VALUE || i4 > 0)) {
            scrollBy(i2, i3);
            return;
        }
        if (z2) {
            int i5 = i2 != 0 ? 1 : 0;
            if (i3 != 0) {
                i5 |= 2;
            }
            j(i5, 1);
        }
        this.e0.a(i2, i3, i4, interpolator);
    }

    void a(int i2, int i3, java.lang.Object obj) {
        int i4;
        int iB = this.e.b();
        int i5 = i2 + i3;
        for (int i6 = 0; i6 < iB; i6++) {
            android.view.View viewD = this.e.d(i6);
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(viewD);
            if (d0VarK != null && !d0VarK.y() && (i4 = d0VarK.f205c) >= i2 && i4 < i5) {
                d0VarK.a(2);
                d0VarK.a(obj);
                ((androidx.recyclerview.widget.RecyclerView.p) viewD.getLayoutParams()).f226c = true;
            }
        }
        this.f192b.c(i2, i3);
    }

    void a(int i2, int i3, boolean z2) {
        int i4 = i2 + i3;
        int iB = this.e.b();
        for (int i5 = 0; i5 < iB; i5++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i5));
            if (d0VarK != null && !d0VarK.y()) {
                int i6 = d0VarK.f205c;
                if (i6 >= i4) {
                    d0VarK.a(-i3, z2);
                } else if (i6 >= i2) {
                    d0VarK.a(i2 - 1, -i3, z2);
                }
                this.h0.g = true;
            }
        }
        this.f192b.a(i2, i3, z2);
        requestLayout();
    }

    void a(int i2, int i3, int[] iArr) {
        w();
        q();
        a.c.c.a.a("RV Scroll");
        a(this.h0);
        int iA = i2 != 0 ? this.m.a(i2, this.f192b, this.h0) : 0;
        int iB = i3 != 0 ? this.m.b(i3, this.f192b, this.h0) : 0;
        a.c.c.a.a();
        u();
        r();
        c(false);
        if (iArr != null) {
            iArr[0] = iA;
            iArr[1] = iB;
        }
    }

    void a(android.graphics.drawable.StateListDrawable stateListDrawable, android.graphics.drawable.Drawable drawable, android.graphics.drawable.StateListDrawable stateListDrawable2, android.graphics.drawable.Drawable drawable2) {
        if (stateListDrawable != null && drawable != null && stateListDrawable2 != null && drawable2 != null) {
            android.content.res.Resources resources = getContext().getResources();
            new androidx.recyclerview.widget.d(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(a.f.b.fastscroll_default_thickness), resources.getDimensionPixelSize(a.f.b.fastscroll_minimum_range), resources.getDimensionPixelOffset(a.f.b.fastscroll_margin));
        } else {
            throw new java.lang.IllegalArgumentException("Trying to set fast scroller without both required drawables." + i());
        }
    }

    void a(android.view.View view) {
        androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(view);
        g(view);
        androidx.recyclerview.widget.RecyclerView.g gVar = this.l;
        if (gVar != null && d0VarK != null) {
            gVar.b(d0VarK);
        }
        java.util.List<androidx.recyclerview.widget.RecyclerView.q> list = this.C;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.C.get(size).b(view);
            }
        }
    }

    final void a(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (getScrollState() != 2) {
            a0Var.p = 0;
            a0Var.q = 0;
        } else {
            android.widget.OverScroller overScroller = this.e0.f201c;
            a0Var.p = overScroller.getFinalX() - overScroller.getCurrX();
            a0Var.q = overScroller.getFinalY() - overScroller.getCurrY();
        }
    }

    void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar) {
        d0Var.a(0, 8192);
        if (this.h0.i && d0Var.t() && !d0Var.q() && !d0Var.y()) {
            this.f.a(c(d0Var), d0Var);
        }
        this.f.c(d0Var, cVar);
    }

    void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
        d0Var.a(false);
        if (this.M.a(d0Var, cVar, cVar2)) {
            s();
        }
    }

    public void a(androidx.recyclerview.widget.RecyclerView.n nVar) {
        a(nVar, -1);
    }

    public void a(androidx.recyclerview.widget.RecyclerView.n nVar, int i2) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.a("Cannot add item decoration during a scroll  or layout");
        }
        if (this.o.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i2 < 0) {
            this.o.add(nVar);
        } else {
            this.o.add(i2, nVar);
        }
        o();
        requestLayout();
    }

    public void a(androidx.recyclerview.widget.RecyclerView.q qVar) {
        if (this.C == null) {
            this.C = new java.util.ArrayList();
        }
        this.C.add(qVar);
    }

    public void a(androidx.recyclerview.widget.RecyclerView.s sVar) {
        this.p.add(sVar);
    }

    public void a(androidx.recyclerview.widget.RecyclerView.t tVar) {
        if (this.j0 == null) {
            this.j0 = new java.util.ArrayList();
        }
        this.j0.add(tVar);
    }

    void a(java.lang.String str) {
        if (n()) {
            if (str != null) {
                throw new java.lang.IllegalStateException(str);
            }
            throw new java.lang.IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + i());
        }
        if (this.G > 0) {
            android.util.Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new java.lang.IllegalStateException("" + i()));
        }
    }

    void a(boolean z2) {
        int i2 = this.F - 1;
        this.F = i2;
        if (i2 < 1) {
            this.F = 0;
            if (z2) {
                z();
                d();
            }
        }
    }

    boolean a(int i2, int i3, android.view.MotionEvent motionEvent) {
        int i4;
        int i5;
        int i6;
        int i7;
        b();
        if (this.l != null) {
            int[] iArr = this.u0;
            iArr[0] = 0;
            iArr[1] = 0;
            a(i2, i3, iArr);
            int[] iArr2 = this.u0;
            int i8 = iArr2[0];
            int i9 = iArr2[1];
            i4 = i9;
            i5 = i8;
            i6 = i2 - i8;
            i7 = i3 - i9;
        } else {
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
        }
        if (!this.o.isEmpty()) {
            invalidate();
        }
        int[] iArr3 = this.u0;
        iArr3[0] = 0;
        iArr3[1] = 0;
        a(i5, i4, i6, i7, this.s0, 0, iArr3);
        int[] iArr4 = this.u0;
        int i10 = i6 - iArr4[0];
        int i11 = i7 - iArr4[1];
        boolean z2 = (iArr4[0] == 0 && iArr4[1] == 0) ? false : true;
        int i12 = this.S;
        int[] iArr5 = this.s0;
        this.S = i12 - iArr5[0];
        this.T -= iArr5[1];
        int[] iArr6 = this.t0;
        iArr6[0] = iArr6[0] + iArr5[0];
        iArr6[1] = iArr6[1] + iArr5[1];
        if (getOverScrollMode() != 2) {
            if (motionEvent != null && !a.c.e.d.a(motionEvent, 8194)) {
                a(motionEvent.getX(), i10, motionEvent.getY(), i11);
            }
            b(i2, i3);
        }
        if (i5 != 0 || i4 != 0) {
            d(i5, i4);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        return (!z2 && i5 == 0 && i4 == 0) ? false : true;
    }

    public boolean a(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2, i4);
    }

    boolean a(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (!n()) {
            return false;
        }
        int iA = accessibilityEvent != null ? a.c.e.q.b.a(accessibilityEvent) : 0;
        this.z |= iA != 0 ? iA : 0;
        return true;
    }

    boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        androidx.recyclerview.widget.RecyclerView.l lVar = this.M;
        return lVar == null || lVar.a(d0Var, d0Var.k());
    }

    boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i2) {
        if (!n()) {
            a.c.e.m.b(d0Var.f203a, i2);
            return true;
        }
        d0Var.q = i2;
        this.v0.add(d0Var);
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(java.util.ArrayList<android.view.View> arrayList, int i2, int i3) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null || !oVar.a(this, arrayList, i2, i3)) {
            super.addFocusables(arrayList, i2, i3);
        }
    }

    int b(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        if (d0Var.b(524) || !d0Var.n()) {
            return -1;
        }
        return this.d.a(d0Var.f205c);
    }

    public androidx.recyclerview.widget.RecyclerView.d0 b(int i2) {
        androidx.recyclerview.widget.RecyclerView.d0 d0Var = null;
        if (this.D) {
            return null;
        }
        int iB = this.e.b();
        for (int i3 = 0; i3 < iB; i3++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i3));
            if (d0VarK != null && !d0VarK.q() && b(d0VarK) == i2) {
                if (!this.e.c(d0VarK.f203a)) {
                    return d0VarK;
                }
                d0Var = d0VarK;
            }
        }
        return d0Var;
    }

    void b() {
        if (!this.u || this.D) {
            a.c.c.a.a("RV FullInvalidate");
            c();
            a.c.c.a.a();
            return;
        }
        if (this.d.c()) {
            if (this.d.c(4) && !this.d.c(11)) {
                a.c.c.a.a("RV PartialInvalidate");
                w();
                q();
                this.d.e();
                if (!this.w) {
                    if (E()) {
                        c();
                    } else {
                        this.d.a();
                    }
                }
                c(true);
                r();
            } else {
                if (!this.d.c()) {
                    return;
                }
                a.c.c.a.a("RV FullInvalidate");
                c();
            }
            a.c.c.a.a();
        }
    }

    void b(int i2, int i3) {
        boolean zIsFinished;
        android.widget.EdgeEffect edgeEffect = this.I;
        if (edgeEffect == null || edgeEffect.isFinished() || i2 <= 0) {
            zIsFinished = false;
        } else {
            this.I.onRelease();
            zIsFinished = this.I.isFinished();
        }
        android.widget.EdgeEffect edgeEffect2 = this.K;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i2 < 0) {
            this.K.onRelease();
            zIsFinished |= this.K.isFinished();
        }
        android.widget.EdgeEffect edgeEffect3 = this.J;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i3 > 0) {
            this.J.onRelease();
            zIsFinished |= this.J.isFinished();
        }
        android.widget.EdgeEffect edgeEffect4 = this.L;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i3 < 0) {
            this.L.onRelease();
            zIsFinished |= this.L.isFinished();
        }
        if (zIsFinished) {
            a.c.e.m.p(this);
        }
    }

    void b(android.view.View view) {
        androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(view);
        h(view);
        androidx.recyclerview.widget.RecyclerView.g gVar = this.l;
        if (gVar != null && d0VarK != null) {
            gVar.c(d0VarK);
        }
        java.util.List<androidx.recyclerview.widget.RecyclerView.q> list = this.C;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.C.get(size).a(view);
            }
        }
    }

    void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
        d(d0Var);
        d0Var.a(false);
        if (this.M.b(d0Var, cVar, cVar2)) {
            s();
        }
    }

    public void b(androidx.recyclerview.widget.RecyclerView.n nVar) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.a("Cannot remove item decoration during a scroll  or layout");
        }
        this.o.remove(nVar);
        if (this.o.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        o();
        requestLayout();
    }

    public void b(androidx.recyclerview.widget.RecyclerView.q qVar) {
        java.util.List<androidx.recyclerview.widget.RecyclerView.q> list = this.C;
        if (list == null) {
            return;
        }
        list.remove(qVar);
    }

    public void b(androidx.recyclerview.widget.RecyclerView.s sVar) {
        this.p.remove(sVar);
        if (this.q == sVar) {
            this.q = null;
        }
    }

    public void b(androidx.recyclerview.widget.RecyclerView.t tVar) {
        java.util.List<androidx.recyclerview.widget.RecyclerView.t> list = this.j0;
        if (list != null) {
            list.remove(tVar);
        }
    }

    void b(boolean z2) {
        this.E = z2 | this.E;
        this.D = true;
        p();
    }

    long c(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        return this.l.b() ? d0Var.g() : d0Var.f205c;
    }

    public android.view.View c(android.view.View view) {
        java.lang.Object parent;
        while (true) {
            parent = view.getParent();
            if (parent == null || parent == this || !(parent instanceof android.view.View)) {
                break;
            }
            view = (android.view.View) parent;
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    void c() {
        java.lang.String str;
        if (this.l == null) {
            str = "No adapter attached; skipping layout";
        } else {
            if (this.m != null) {
                androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
                a0Var.j = false;
                if (a0Var.e != 1) {
                    if (!this.d.d() && this.m.q() == getWidth() && this.m.h() == getHeight()) {
                        this.m.e(this);
                    }
                    C();
                    return;
                }
                A();
                this.m.e(this);
                B();
                C();
                return;
            }
            str = "No layout manager attached; skipping layout";
        }
        android.util.Log.e("RecyclerView", str);
    }

    public void c(int i2) {
        int iA = this.e.a();
        for (int i3 = 0; i3 < iA; i3++) {
            this.e.c(i3).offsetLeftAndRight(i2);
        }
    }

    void c(int i2, int i3) {
        setMeasuredDimension(androidx.recyclerview.widget.RecyclerView.o.a(i2, getPaddingLeft() + getPaddingRight(), a.c.e.m.k(this)), androidx.recyclerview.widget.RecyclerView.o.a(i3, getPaddingTop() + getPaddingBottom(), a.c.e.m.j(this)));
    }

    void c(boolean z2) {
        if (this.v < 1) {
            this.v = 1;
        }
        if (!z2 && !this.x) {
            this.w = false;
        }
        if (this.v == 1) {
            if (z2 && this.w && !this.x && this.m != null && this.l != null) {
                c();
            }
            if (!this.x) {
                this.w = false;
            }
        }
        this.v--;
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof androidx.recyclerview.widget.RecyclerView.p) && this.m.a((androidx.recyclerview.widget.RecyclerView.p) layoutParams);
    }

    @Override // android.view.View
    public int computeHorizontalScrollExtent() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null && oVar.a()) {
            return this.m.a(this.h0);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeHorizontalScrollOffset() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null && oVar.a()) {
            return this.m.b(this.h0);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeHorizontalScrollRange() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null && oVar.a()) {
            return this.m.c(this.h0);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeVerticalScrollExtent() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null && oVar.b()) {
            return this.m.d(this.h0);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeVerticalScrollOffset() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null && oVar.b()) {
            return this.m.e(this.h0);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeVerticalScrollRange() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null && oVar.b()) {
            return this.m.f(this.h0);
        }
        return 0;
    }

    public androidx.recyclerview.widget.RecyclerView.d0 d(android.view.View view) {
        android.view.View viewC = c(view);
        if (viewC == null) {
            return null;
        }
        return e(viewC);
    }

    void d() {
        int i2;
        for (int size = this.v0.size() - 1; size >= 0; size--) {
            androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.v0.get(size);
            if (d0Var.f203a.getParent() == this && !d0Var.y() && (i2 = d0Var.q) != -1) {
                a.c.e.m.b(d0Var.f203a, i2);
                d0Var.q = -1;
            }
        }
        this.v0.clear();
    }

    public void d(int i2) {
        int iA = this.e.a();
        for (int i3 = 0; i3 < iA; i3++) {
            this.e.c(i3).offsetTopAndBottom(i2);
        }
    }

    void d(int i2, int i3) {
        this.G++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - i2, scrollY - i3);
        h(i2, i3);
        androidx.recyclerview.widget.RecyclerView.t tVar = this.i0;
        if (tVar != null) {
            tVar.a(this, i2, i3);
        }
        java.util.List<androidx.recyclerview.widget.RecyclerView.t> list = this.j0;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.j0.get(size).a(this, i2, i3);
            }
        }
        this.G--;
    }

    @Override // android.view.View
    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return getScrollingChildHelper().a(f2, f3, z2);
    }

    @Override // android.view.View
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().a(f2, f3);
    }

    @Override // android.view.View
    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2);
    }

    @Override // android.view.View
    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return getScrollingChildHelper().a(i2, i3, i4, i5, iArr);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSaveInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        boolean z2;
        float paddingRight;
        int paddingBottom;
        super.draw(canvas);
        int size = this.o.size();
        boolean z3 = false;
        for (int i2 = 0; i2 < size; i2++) {
            this.o.get(i2).b(canvas, this, this.h0);
        }
        android.widget.EdgeEffect edgeEffect = this.I;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z2 = false;
        } else {
            int iSave = canvas.save();
            int paddingBottom2 = this.g ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom2, 0.0f);
            android.widget.EdgeEffect edgeEffect2 = this.I;
            z2 = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(iSave);
        }
        android.widget.EdgeEffect edgeEffect3 = this.J;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int iSave2 = canvas.save();
            if (this.g) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            android.widget.EdgeEffect edgeEffect4 = this.J;
            z2 |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(iSave2);
        }
        android.widget.EdgeEffect edgeEffect5 = this.K;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int iSave3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.g ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate(-paddingTop, -width);
            android.widget.EdgeEffect edgeEffect6 = this.K;
            z2 |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(iSave3);
        }
        android.widget.EdgeEffect edgeEffect7 = this.L;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int iSave4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.g) {
                paddingRight = (-getWidth()) + getPaddingRight();
                paddingBottom = (-getHeight()) + getPaddingBottom();
            } else {
                paddingRight = -getWidth();
                paddingBottom = -getHeight();
            }
            canvas.translate(paddingRight, paddingBottom);
            android.widget.EdgeEffect edgeEffect8 = this.L;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z3 = true;
            }
            z2 |= z3;
            canvas.restoreToCount(iSave4);
        }
        if ((z2 || this.M == null || this.o.size() <= 0 || !this.M.g()) ? z2 : true) {
            a.c.e.m.p(this);
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j2) {
        return super.drawChild(canvas, view, j2);
    }

    public androidx.recyclerview.widget.RecyclerView.d0 e(android.view.View view) {
        android.view.ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return k(view);
        }
        throw new java.lang.IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    void e() {
        int measuredWidth;
        int measuredHeight;
        if (this.L != null) {
            return;
        }
        android.widget.EdgeEffect edgeEffectA = this.H.a(this, 3);
        this.L = edgeEffectA;
        if (this.g) {
            measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        } else {
            measuredWidth = getMeasuredWidth();
            measuredHeight = getMeasuredHeight();
        }
        edgeEffectA.setSize(measuredWidth, measuredHeight);
    }

    public void e(int i2) {
    }

    public boolean e(int i2, int i3) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null) {
            android.util.Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        }
        if (this.x) {
            return false;
        }
        boolean zA = oVar.a();
        boolean zB = this.m.b();
        if (!zA || java.lang.Math.abs(i2) < this.W) {
            i2 = 0;
        }
        if (!zB || java.lang.Math.abs(i3) < this.W) {
            i3 = 0;
        }
        if (i2 == 0 && i3 == 0) {
            return false;
        }
        float f2 = i2;
        float f3 = i3;
        if (!dispatchNestedPreFling(f2, f3)) {
            boolean z2 = zA || zB;
            dispatchNestedFling(f2, f3, z2);
            androidx.recyclerview.widget.RecyclerView.r rVar = this.V;
            if (rVar != null && rVar.a(i2, i3)) {
                return true;
            }
            if (z2) {
                int i4 = zA ? 1 : 0;
                if (zB) {
                    i4 |= 2;
                }
                j(i4, 1);
                int i5 = this.a0;
                int iMax = java.lang.Math.max(-i5, java.lang.Math.min(i2, i5));
                int i6 = this.a0;
                this.e0.a(iMax, java.lang.Math.max(-i6, java.lang.Math.min(i3, i6)));
                return true;
            }
        }
        return false;
    }

    android.graphics.Rect f(android.view.View view) {
        androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
        if (!pVar.f226c) {
            return pVar.f225b;
        }
        if (this.h0.d() && (pVar.b() || pVar.d())) {
            return pVar.f225b;
        }
        android.graphics.Rect rect = pVar.f225b;
        rect.set(0, 0, 0, 0);
        int size = this.o.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.i.set(0, 0, 0, 0);
            this.o.get(i2).a(this.i, view, this, this.h0);
            int i3 = rect.left;
            android.graphics.Rect rect2 = this.i;
            rect.left = i3 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        pVar.f226c = false;
        return rect;
    }

    void f() {
        int measuredHeight;
        int measuredWidth;
        if (this.I != null) {
            return;
        }
        android.widget.EdgeEffect edgeEffectA = this.H.a(this, 0);
        this.I = edgeEffectA;
        if (this.g) {
            measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
            measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        } else {
            measuredHeight = getMeasuredHeight();
            measuredWidth = getMeasuredWidth();
        }
        edgeEffectA.setSize(measuredHeight, measuredWidth);
    }

    public void f(int i2) {
        if (this.x) {
            return;
        }
        x();
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null) {
            android.util.Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            oVar.h(i2);
            awakenScrollBars();
        }
    }

    void f(int i2, int i3) {
        int iB = this.e.b();
        for (int i4 = 0; i4 < iB; i4++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i4));
            if (d0VarK != null && !d0VarK.y() && d0VarK.f205c >= i2) {
                d0VarK.a(i3, false);
                this.h0.g = true;
            }
        }
        this.f192b.a(i2, i3);
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public android.view.View focusSearch(android.view.View view, int i2) {
        android.view.View viewA;
        boolean z2;
        android.view.View viewD = this.m.d(view, i2);
        if (viewD != null) {
            return viewD;
        }
        boolean z3 = (this.l == null || this.m == null || n() || this.x) ? false : true;
        android.view.FocusFinder focusFinder = android.view.FocusFinder.getInstance();
        if (z3 && (i2 == 2 || i2 == 1)) {
            if (this.m.b()) {
                int i3 = i2 == 2 ? 130 : 33;
                z2 = focusFinder.findNextFocus(this, view, i3) == null;
                if (D0) {
                    i2 = i3;
                }
            } else {
                z2 = false;
            }
            if (!z2 && this.m.a()) {
                int i4 = (this.m.j() == 1) ^ (i2 == 2) ? 66 : 17;
                boolean z4 = focusFinder.findNextFocus(this, view, i4) == null;
                if (D0) {
                    i2 = i4;
                }
                z2 = z4;
            }
            if (z2) {
                b();
                if (c(view) == null) {
                    return null;
                }
                w();
                this.m.a(view, i2, this.f192b, this.h0);
                c(false);
            }
            viewA = focusFinder.findNextFocus(this, view, i2);
        } else {
            android.view.View viewFindNextFocus = focusFinder.findNextFocus(this, view, i2);
            if (viewFindNextFocus == null && z3) {
                b();
                if (c(view) == null) {
                    return null;
                }
                w();
                viewA = this.m.a(view, i2, this.f192b, this.h0);
                c(false);
            } else {
                viewA = viewFindNextFocus;
            }
        }
        if (viewA == null || viewA.hasFocusable()) {
            return a(view, viewA, i2) ? viewA : super.focusSearch(view, i2);
        }
        if (getFocusedChild() == null) {
            return super.focusSearch(view, i2);
        }
        a(viewA, (android.view.View) null);
        return view;
    }

    void g() {
        int measuredHeight;
        int measuredWidth;
        if (this.K != null) {
            return;
        }
        android.widget.EdgeEffect edgeEffectA = this.H.a(this, 2);
        this.K = edgeEffectA;
        if (this.g) {
            measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
            measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        } else {
            measuredHeight = getMeasuredHeight();
            measuredWidth = getMeasuredWidth();
        }
        edgeEffectA.setSize(measuredHeight, measuredWidth);
    }

    public void g(int i2) {
        getScrollingChildHelper().c(i2);
    }

    void g(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int iB = this.e.b();
        if (i2 < i3) {
            i6 = -1;
            i5 = i2;
            i4 = i3;
        } else {
            i4 = i2;
            i5 = i3;
            i6 = 1;
        }
        for (int i8 = 0; i8 < iB; i8++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i8));
            if (d0VarK != null && (i7 = d0VarK.f205c) >= i5 && i7 <= i4) {
                if (i7 == i2) {
                    d0VarK.a(i3 - i2, false);
                } else {
                    d0VarK.a(i6, false);
                }
                this.h0.g = true;
            }
        }
        this.f192b.b(i2, i3);
        requestLayout();
    }

    public void g(android.view.View view) {
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            return oVar.c();
        }
        throw new java.lang.IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            return oVar.a(getContext(), attributeSet);
        }
        throw new java.lang.IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            return oVar.a(layoutParams);
        }
        throw new java.lang.IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    public androidx.recyclerview.widget.RecyclerView.g getAdapter() {
        return this.l;
    }

    @Override // android.view.View
    public int getBaseline() {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        return oVar != null ? oVar.d() : super.getBaseline();
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i2, int i3) {
        androidx.recyclerview.widget.RecyclerView.j jVar = this.p0;
        return jVar == null ? super.getChildDrawingOrder(i2, i3) : jVar.a(i2, i3);
    }

    @Override // android.view.ViewGroup
    public boolean getClipToPadding() {
        return this.g;
    }

    public androidx.recyclerview.widget.l getCompatAccessibilityDelegate() {
        return this.o0;
    }

    public androidx.recyclerview.widget.RecyclerView.k getEdgeEffectFactory() {
        return this.H;
    }

    public androidx.recyclerview.widget.RecyclerView.l getItemAnimator() {
        return this.M;
    }

    public int getItemDecorationCount() {
        return this.o.size();
    }

    public androidx.recyclerview.widget.RecyclerView.o getLayoutManager() {
        return this.m;
    }

    public int getMaxFlingVelocity() {
        return this.a0;
    }

    public int getMinFlingVelocity() {
        return this.W;
    }

    long getNanoTime() {
        if (C0) {
            return java.lang.System.nanoTime();
        }
        return 0L;
    }

    public androidx.recyclerview.widget.RecyclerView.r getOnFlingListener() {
        return this.V;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.d0;
    }

    public androidx.recyclerview.widget.RecyclerView.u getRecycledViewPool() {
        return this.f192b.d();
    }

    public int getScrollState() {
        return this.N;
    }

    void h() {
        int measuredWidth;
        int measuredHeight;
        if (this.J != null) {
            return;
        }
        android.widget.EdgeEffect edgeEffectA = this.H.a(this, 1);
        this.J = edgeEffectA;
        if (this.g) {
            measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        } else {
            measuredWidth = getMeasuredWidth();
            measuredHeight = getMeasuredHeight();
        }
        edgeEffectA.setSize(measuredWidth, measuredHeight);
    }

    public void h(int i2, int i3) {
    }

    public void h(android.view.View view) {
    }

    @Override // android.view.View
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().a();
    }

    java.lang.String i() {
        return " " + super.toString() + ", adapter:" + this.l + ", layout:" + this.m + ", context:" + getContext();
    }

    public void i(int i2, int i3) {
        a(i2, i3, (android.view.animation.Interpolator) null);
    }

    boolean i(android.view.View view) {
        w();
        boolean zE = this.e.e(view);
        if (zE) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(view);
            this.f192b.c(d0VarK);
            this.f192b.b(d0VarK);
        }
        c(!zE);
        return zE;
    }

    @Override // android.view.View
    public boolean isAttachedToWindow() {
        return this.r;
    }

    @Override // android.view.ViewGroup
    public final boolean isLayoutSuppressed() {
        return this.x;
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().b();
    }

    public boolean j() {
        return !this.u || this.D || this.d.c();
    }

    public boolean j(int i2, int i3) {
        return getScrollingChildHelper().a(i2, i3);
    }

    void k() {
        this.d = new androidx.recyclerview.widget.a(new androidx.recyclerview.widget.RecyclerView.f());
    }

    void l() {
        this.L = null;
        this.J = null;
        this.K = null;
        this.I = null;
    }

    boolean m() {
        android.view.accessibility.AccessibilityManager accessibilityManager = this.B;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    public boolean n() {
        return this.F > 0;
    }

    void o() {
        int iB = this.e.b();
        for (int i2 = 0; i2 < iB; i2++) {
            ((androidx.recyclerview.widget.RecyclerView.p) this.e.d(i2).getLayoutParams()).f226c = true;
        }
        this.f192b.g();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.F = 0;
        this.r = true;
        this.u = this.u && !isLayoutRequested();
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.a(this);
        }
        this.n0 = false;
        if (C0) {
            androidx.recyclerview.widget.e eVar = androidx.recyclerview.widget.e.e.get();
            this.f0 = eVar;
            if (eVar == null) {
                this.f0 = new androidx.recyclerview.widget.e();
                android.view.Display displayE = a.c.e.m.e(this);
                float f2 = 60.0f;
                if (!isInEditMode() && displayE != null) {
                    float refreshRate = displayE.getRefreshRate();
                    if (refreshRate >= 30.0f) {
                        f2 = refreshRate;
                    }
                }
                androidx.recyclerview.widget.e eVar2 = this.f0;
                eVar2.f300c = (long) (1.0E9f / f2);
                androidx.recyclerview.widget.e.e.set(eVar2);
            }
            this.f0.a(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        androidx.recyclerview.widget.e eVar;
        super.onDetachedFromWindow();
        androidx.recyclerview.widget.RecyclerView.l lVar = this.M;
        if (lVar != null) {
            lVar.b();
        }
        x();
        this.r = false;
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.a(this, this.f192b);
        }
        this.v0.clear();
        removeCallbacks(this.w0);
        this.f.b();
        if (!C0 || (eVar = this.f0) == null) {
            return;
        }
        eVar.b(this);
        this.f0 = null;
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        int size = this.o.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.o.get(i2).a(canvas, this, this.h0);
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0060  */
    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        float f2;
        float axisValue;
        if (this.m != null && !this.x && motionEvent.getAction() == 8) {
            if ((motionEvent.getSource() & 2) != 0) {
                f2 = this.m.b() ? -motionEvent.getAxisValue(9) : 0.0f;
                axisValue = this.m.a() ? motionEvent.getAxisValue(10) : 0.0f;
            } else if ((motionEvent.getSource() & 4194304) != 0) {
                float axisValue2 = motionEvent.getAxisValue(26);
                if (this.m.b()) {
                    f2 = -axisValue2;
                } else if (this.m.a()) {
                    axisValue = axisValue2;
                    f2 = 0.0f;
                } else {
                    f2 = 0.0f;
                }
            } else {
                f2 = 0.0f;
            }
            if (f2 != 0.0f || axisValue != 0.0f) {
                a((int) (axisValue * this.b0), (int) (f2 * this.c0), motionEvent);
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z2;
        if (this.x) {
            return false;
        }
        this.q = null;
        if (b(motionEvent)) {
            y();
            return true;
        }
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null) {
            return false;
        }
        boolean zA = oVar.a();
        boolean zB = this.m.b();
        if (this.P == null) {
            this.P = android.view.VelocityTracker.obtain();
        }
        this.P.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.y) {
                this.y = false;
            }
            this.O = motionEvent.getPointerId(0);
            int x2 = (int) (motionEvent.getX() + 0.5f);
            this.S = x2;
            this.Q = x2;
            int y2 = (int) (motionEvent.getY() + 0.5f);
            this.T = y2;
            this.R = y2;
            if (this.N == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
                g(1);
            }
            int[] iArr = this.t0;
            iArr[1] = 0;
            iArr[0] = 0;
            int i2 = zA ? 1 : 0;
            if (zB) {
                i2 |= 2;
            }
            j(i2, 0);
        } else if (actionMasked == 1) {
            this.P.clear();
            g(0);
        } else if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.O);
            if (iFindPointerIndex < 0) {
                android.util.Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.O + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x3 = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
            int y3 = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
            if (this.N != 1) {
                int i3 = x3 - this.Q;
                int i4 = y3 - this.R;
                if (!zA || java.lang.Math.abs(i3) <= this.U) {
                    z2 = false;
                } else {
                    this.S = x3;
                    z2 = true;
                }
                if (zB && java.lang.Math.abs(i4) > this.U) {
                    this.T = y3;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            y();
        } else if (actionMasked == 5) {
            this.O = motionEvent.getPointerId(actionIndex);
            int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.S = x4;
            this.Q = x4;
            int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.T = y4;
            this.R = y4;
        } else if (actionMasked == 6) {
            c(motionEvent);
        }
        return this.N == 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        a.c.c.a.a("RV OnLayout");
        c();
        a.c.c.a.a();
        this.u = true;
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null) {
            c(i2, i3);
            return;
        }
        boolean z2 = false;
        if (oVar.u()) {
            int mode = android.view.View.MeasureSpec.getMode(i2);
            int mode2 = android.view.View.MeasureSpec.getMode(i3);
            this.m.a(this.f192b, this.h0, i2, i3);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z2 = true;
            }
            if (z2 || this.l == null) {
                return;
            }
            if (this.h0.e == 1) {
                A();
            }
            this.m.b(i2, i3);
            this.h0.j = true;
            B();
            this.m.d(i2, i3);
            if (this.m.A()) {
                this.m.b(android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                this.h0.j = true;
                B();
                this.m.d(i2, i3);
                return;
            }
            return;
        }
        if (this.s) {
            this.m.a(this.f192b, this.h0, i2, i3);
            return;
        }
        if (this.A) {
            w();
            q();
            I();
            r();
            androidx.recyclerview.widget.RecyclerView.a0 a0Var = this.h0;
            if (a0Var.l) {
                a0Var.h = true;
            } else {
                this.d.b();
                this.h0.h = false;
            }
            this.A = false;
            c(false);
        } else if (this.h0.l) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            return;
        }
        androidx.recyclerview.widget.RecyclerView.g gVar = this.l;
        if (gVar != null) {
            this.h0.f = gVar.a();
        } else {
            this.h0.f = 0;
        }
        w();
        this.m.a(this.f192b, this.h0, i2, i3);
        c(false);
        this.h0.h = false;
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i2, android.graphics.Rect rect) {
        if (n()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i2, rect);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.os.Parcelable parcelable2;
        if (!(parcelable instanceof androidx.recyclerview.widget.RecyclerView.y)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        androidx.recyclerview.widget.RecyclerView.y yVar = (androidx.recyclerview.widget.RecyclerView.y) parcelable;
        this.f193c = yVar;
        super.onRestoreInstanceState(yVar.a());
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null || (parcelable2 = this.f193c.f236c) == null) {
            return;
        }
        oVar.a(parcelable2);
    }

    @Override // android.view.View
    protected android.os.Parcelable onSaveInstanceState() {
        androidx.recyclerview.widget.RecyclerView.y yVar = new androidx.recyclerview.widget.RecyclerView.y(super.onSaveInstanceState());
        androidx.recyclerview.widget.RecyclerView.y yVar2 = this.f193c;
        if (yVar2 != null) {
            yVar.a(yVar2);
        } else {
            androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
            yVar.f236c = oVar != null ? oVar.x() : null;
        }
        return yVar;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 == i4 && i3 == i5) {
            return;
        }
        l();
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00dd A[PHI: r0
  0x00dd: PHI (r0v42 int) = (r0v31 int), (r0v46 int) binds: [B:41:0x00c8, B:46:0x00d9] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z2;
        boolean z3 = false;
        if (this.x || this.y) {
            return false;
        }
        if (a(motionEvent)) {
            y();
            return true;
        }
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null) {
            return false;
        }
        boolean zA = oVar.a();
        boolean zB = this.m.b();
        if (this.P == null) {
            this.P = android.view.VelocityTracker.obtain();
        }
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            int[] iArr = this.t0;
            iArr[1] = 0;
            iArr[0] = 0;
        }
        android.view.MotionEvent motionEventObtain = android.view.MotionEvent.obtain(motionEvent);
        int[] iArr2 = this.t0;
        motionEventObtain.offsetLocation(iArr2[0], iArr2[1]);
        if (actionMasked == 0) {
            this.O = motionEvent.getPointerId(0);
            int x2 = (int) (motionEvent.getX() + 0.5f);
            this.S = x2;
            this.Q = x2;
            int y2 = (int) (motionEvent.getY() + 0.5f);
            this.T = y2;
            this.R = y2;
            int i2 = zA ? 1 : 0;
            if (zB) {
                i2 |= 2;
            }
            j(i2, 0);
        } else if (actionMasked == 1) {
            this.P.addMovement(motionEventObtain);
            this.P.computeCurrentVelocity(1000, this.a0);
            float f2 = zA ? -this.P.getXVelocity(this.O) : 0.0f;
            float f3 = zB ? -this.P.getYVelocity(this.O) : 0.0f;
            if ((f2 == 0.0f && f3 == 0.0f) || !e((int) f2, (int) f3)) {
                setScrollState(0);
            }
            M();
            z3 = true;
        } else if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.O);
            if (iFindPointerIndex < 0) {
                android.util.Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.O + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x3 = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
            int y3 = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
            int iMax = this.S - x3;
            int iMax2 = this.T - y3;
            if (this.N != 1) {
                if (zA) {
                    int i3 = this.U;
                    iMax = iMax > 0 ? java.lang.Math.max(0, iMax - i3) : java.lang.Math.min(0, iMax + i3);
                    if (iMax != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                } else {
                    z2 = false;
                }
                if (zB) {
                    int i4 = this.U;
                    iMax2 = iMax2 > 0 ? java.lang.Math.max(0, iMax2 - i4) : java.lang.Math.min(0, iMax2 + i4);
                    if (iMax2 != 0) {
                        z2 = true;
                    }
                }
                if (z2) {
                    setScrollState(1);
                }
            }
            int i5 = iMax;
            int i6 = iMax2;
            if (this.N == 1) {
                int[] iArr3 = this.u0;
                iArr3[0] = 0;
                iArr3[1] = 0;
                if (a(zA ? i5 : 0, zB ? i6 : 0, this.u0, this.s0, 0)) {
                    int[] iArr4 = this.u0;
                    i5 -= iArr4[0];
                    i6 -= iArr4[1];
                    int[] iArr5 = this.t0;
                    int i7 = iArr5[0];
                    int[] iArr6 = this.s0;
                    iArr5[0] = i7 + iArr6[0];
                    iArr5[1] = iArr5[1] + iArr6[1];
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                int i8 = i6;
                int[] iArr7 = this.s0;
                this.S = x3 - iArr7[0];
                this.T = y3 - iArr7[1];
                if (a(zA ? i5 : 0, zB ? i8 : 0, motionEvent)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (this.f0 != null && (i5 != 0 || i8 != 0)) {
                    this.f0.a(this, i5, i8);
                }
            }
        } else if (actionMasked == 3) {
            y();
        } else if (actionMasked == 5) {
            this.O = motionEvent.getPointerId(actionIndex);
            int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.S = x4;
            this.Q = x4;
            int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.T = y4;
            this.R = y4;
        } else if (actionMasked == 6) {
            c(motionEvent);
        }
        if (!z3) {
            this.P.addMovement(motionEventObtain);
        }
        motionEventObtain.recycle();
        return true;
    }

    void p() {
        int iB = this.e.b();
        for (int i2 = 0; i2 < iB; i2++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i2));
            if (d0VarK != null && !d0VarK.y()) {
                d0VarK.a(6);
            }
        }
        o();
        this.f192b.h();
    }

    void q() {
        this.F++;
    }

    void r() {
        a(true);
    }

    @Override // android.view.ViewGroup
    protected void removeDetachedView(android.view.View view, boolean z2) {
        androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(view);
        if (d0VarK != null) {
            if (d0VarK.s()) {
                d0VarK.d();
            } else if (!d0VarK.y()) {
                throw new java.lang.IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + d0VarK + i());
            }
        }
        view.clearAnimation();
        b(view);
        super.removeDetachedView(view, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(android.view.View view, android.view.View view2) {
        if (!this.m.a(this, this.h0, view, view2) && view2 != null) {
            a(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z2) {
        return this.m.a(this, view, rect, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z2) {
        int size = this.p.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.p.get(i2).a(z2);
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.v != 0 || this.x) {
            this.w = true;
        } else {
            super.requestLayout();
        }
    }

    void s() {
        if (this.n0 || !this.r) {
            return;
        }
        a.c.e.m.a(this, this.w0);
        this.n0 = true;
    }

    @Override // android.view.View
    public void scrollBy(int i2, int i3) {
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar == null) {
            android.util.Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.x) {
            return;
        }
        boolean zA = oVar.a();
        boolean zB = this.m.b();
        if (zA || zB) {
            if (!zA) {
                i2 = 0;
            }
            if (!zB) {
                i3 = 0;
            }
            a(i2, i3, (android.view.MotionEvent) null);
        }
    }

    @Override // android.view.View
    public void scrollTo(int i2, int i3) {
        android.util.Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (a(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(androidx.recyclerview.widget.l lVar) {
        this.o0 = lVar;
        a.c.e.m.a(this, lVar);
    }

    public void setAdapter(androidx.recyclerview.widget.RecyclerView.g gVar) {
        setLayoutFrozen(false);
        a(gVar, false, true);
        b(false);
        requestLayout();
    }

    public void setChildDrawingOrderCallback(androidx.recyclerview.widget.RecyclerView.j jVar) {
        if (jVar == this.p0) {
            return;
        }
        this.p0 = jVar;
        setChildrenDrawingOrderEnabled(jVar != null);
    }

    @Override // android.view.ViewGroup
    public void setClipToPadding(boolean z2) {
        if (z2 != this.g) {
            l();
        }
        this.g = z2;
        super.setClipToPadding(z2);
        if (this.u) {
            requestLayout();
        }
    }

    public void setEdgeEffectFactory(androidx.recyclerview.widget.RecyclerView.k kVar) {
        a.c.d.c.a(kVar);
        this.H = kVar;
        l();
    }

    public void setHasFixedSize(boolean z2) {
        this.s = z2;
    }

    public void setItemAnimator(androidx.recyclerview.widget.RecyclerView.l lVar) {
        androidx.recyclerview.widget.RecyclerView.l lVar2 = this.M;
        if (lVar2 != null) {
            lVar2.b();
            this.M.a((androidx.recyclerview.widget.RecyclerView.l.b) null);
        }
        this.M = lVar;
        if (lVar != null) {
            lVar.a(this.m0);
        }
    }

    public void setItemViewCacheSize(int i2) {
        this.f192b.f(i2);
    }

    @java.lang.Deprecated
    public void setLayoutFrozen(boolean z2) {
        suppressLayout(z2);
    }

    public void setLayoutManager(androidx.recyclerview.widget.RecyclerView.o oVar) {
        if (oVar == this.m) {
            return;
        }
        x();
        if (this.m != null) {
            androidx.recyclerview.widget.RecyclerView.l lVar = this.M;
            if (lVar != null) {
                lVar.b();
            }
            this.m.b(this.f192b);
            this.m.c(this.f192b);
            this.f192b.a();
            if (this.r) {
                this.m.a(this, this.f192b);
            }
            this.m.f((androidx.recyclerview.widget.RecyclerView) null);
            this.m = null;
        } else {
            this.f192b.a();
        }
        this.e.c();
        this.m = oVar;
        if (oVar != null) {
            if (oVar.f217b != null) {
                throw new java.lang.IllegalArgumentException("LayoutManager " + oVar + " is already attached to a RecyclerView:" + oVar.f217b.i());
            }
            oVar.f(this);
            if (this.r) {
                this.m.a(this);
            }
        }
        this.f192b.j();
        requestLayout();
    }

    @Override // android.view.ViewGroup
    @java.lang.Deprecated
    public void setLayoutTransition(android.animation.LayoutTransition layoutTransition) {
        if (android.os.Build.VERSION.SDK_INT < 18) {
            if (layoutTransition == null) {
                suppressLayout(false);
                return;
            } else if (layoutTransition.getAnimator(0) == null && layoutTransition.getAnimator(1) == null && layoutTransition.getAnimator(2) == null && layoutTransition.getAnimator(3) == null && layoutTransition.getAnimator(4) == null) {
                suppressLayout(true);
                return;
            }
        }
        if (layoutTransition != null) {
            throw new java.lang.IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
        }
        super.setLayoutTransition(null);
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z2) {
        getScrollingChildHelper().a(z2);
    }

    public void setOnFlingListener(androidx.recyclerview.widget.RecyclerView.r rVar) {
        this.V = rVar;
    }

    @java.lang.Deprecated
    public void setOnScrollListener(androidx.recyclerview.widget.RecyclerView.t tVar) {
        this.i0 = tVar;
    }

    public void setPreserveFocusAfterLayout(boolean z2) {
        this.d0 = z2;
    }

    public void setRecycledViewPool(androidx.recyclerview.widget.RecyclerView.u uVar) {
        this.f192b.a(uVar);
    }

    public void setRecyclerListener(androidx.recyclerview.widget.RecyclerView.w wVar) {
        this.n = wVar;
    }

    void setScrollState(int i2) {
        if (i2 == this.N) {
            return;
        }
        this.N = i2;
        if (i2 != 2) {
            O();
        }
        a(i2);
    }

    public void setScrollingTouchSlop(int i2) {
        int scaledTouchSlop;
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(getContext());
        if (i2 == 0) {
            scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        } else if (i2 != 1) {
            android.util.Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i2 + "; using default value");
            scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        } else {
            scaledTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        }
        this.U = scaledTouchSlop;
    }

    public void setViewCacheExtension(androidx.recyclerview.widget.RecyclerView.b0 b0Var) {
        this.f192b.a(b0Var);
    }

    @Override // android.view.View
    public boolean startNestedScroll(int i2) {
        return getScrollingChildHelper().b(i2);
    }

    @Override // android.view.View, a.c.e.g
    public void stopNestedScroll() {
        getScrollingChildHelper().c();
    }

    @Override // android.view.ViewGroup
    public final void suppressLayout(boolean z2) {
        if (z2 != this.x) {
            a("Do not suppressLayout in layout or scroll");
            if (z2) {
                long jUptimeMillis = android.os.SystemClock.uptimeMillis();
                onTouchEvent(android.view.MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0));
                this.x = true;
                this.y = true;
                x();
                return;
            }
            this.x = false;
            if (this.w && this.m != null && this.l != null) {
                requestLayout();
            }
            this.w = false;
        }
    }

    void t() {
        androidx.recyclerview.widget.RecyclerView.l lVar = this.M;
        if (lVar != null) {
            lVar.b();
        }
        androidx.recyclerview.widget.RecyclerView.o oVar = this.m;
        if (oVar != null) {
            oVar.b(this.f192b);
            this.m.c(this.f192b);
        }
        this.f192b.a();
    }

    void u() {
        androidx.recyclerview.widget.RecyclerView.d0 d0Var;
        int iA = this.e.a();
        for (int i2 = 0; i2 < iA; i2++) {
            android.view.View viewC = this.e.c(i2);
            androidx.recyclerview.widget.RecyclerView.d0 d0VarE = e(viewC);
            if (d0VarE != null && (d0Var = d0VarE.i) != null) {
                android.view.View view = d0Var.f203a;
                int left = viewC.getLeft();
                int top = viewC.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    void v() {
        int iB = this.e.b();
        for (int i2 = 0; i2 < iB; i2++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = k(this.e.d(i2));
            if (!d0VarK.y()) {
                d0VarK.w();
            }
        }
    }

    void w() {
        int i2 = this.v + 1;
        this.v = i2;
        if (i2 != 1 || this.x) {
            return;
        }
        this.w = false;
    }

    public void x() {
        setScrollState(0);
        O();
    }
}
