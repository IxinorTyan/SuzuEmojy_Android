package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public class GridLayoutManager extends androidx.recyclerview.widget.LinearLayoutManager {
    boolean I;
    int J;
    int[] K;
    android.view.View[] L;
    final android.util.SparseIntArray M;
    final android.util.SparseIntArray N;
    androidx.recyclerview.widget.GridLayoutManager.c O;
    final android.graphics.Rect P;
    private boolean Q;

    public static final class a extends androidx.recyclerview.widget.GridLayoutManager.c {
        @Override // androidx.recyclerview.widget.GridLayoutManager.c
        public int a(int i) {
            return 1;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.c
        public int d(int i, int i2) {
            return i % i2;
        }
    }

    public static class b extends androidx.recyclerview.widget.RecyclerView.p {
        int e;
        int f;

        public b(int i, int i2) {
            super(i, i2);
            this.e = -1;
            this.f = 0;
        }

        public b(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.e = -1;
            this.f = 0;
        }

        public b(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.e = -1;
            this.f = 0;
        }

        public b(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.e = -1;
            this.f = 0;
        }

        public int e() {
            return this.e;
        }

        public int f() {
            return this.f;
        }
    }

    public static abstract class c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final android.util.SparseIntArray f176a = new android.util.SparseIntArray();

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final android.util.SparseIntArray f177b = new android.util.SparseIntArray();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private boolean f178c = false;
        private boolean d = false;

        static int a(android.util.SparseIntArray sparseIntArray, int i) {
            int size = sparseIntArray.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (sparseIntArray.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            int i4 = i2 - 1;
            if (i4 < 0 || i4 >= sparseIntArray.size()) {
                return -1;
            }
            return sparseIntArray.keyAt(i4);
        }

        public abstract int a(int i);

        int a(int i, int i2) {
            if (!this.d) {
                return c(i, i2);
            }
            int i3 = this.f177b.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int iC = c(i, i2);
            this.f177b.put(i, iC);
            return iC;
        }

        public void a() {
            this.f177b.clear();
        }

        int b(int i, int i2) {
            if (!this.f178c) {
                return d(i, i2);
            }
            int i3 = this.f176a.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int iD = d(i, i2);
            this.f176a.put(i, iD);
            return iD;
        }

        public void b() {
            this.f176a.clear();
        }

        /* JADX WARN: Code duplicated, block: B:13:0x002d  */
        /* JADX WARN: Code duplicated, block: B:15:0x0034  */
        /* JADX WARN: Code duplicated, block: B:16:0x0038 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:17:0x003a  */
        /* JADX WARN: Code duplicated, block: B:21:0x0043  */
        /* JADX WARN: Code duplicated, block: B:26:0x003d A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:27:? A[RETURN, SYNTHETIC] */
        public int c(int i, int i2) {
            int i3;
            int iB;
            int i4;
            int iA;
            int iA2;
            int iA3;
            if (this.d && (iA3 = a(this.f177b, i)) != -1) {
                i4 = this.f177b.get(iA3);
                i3 = iA3 + 1;
                iB = b(iA3, i2) + a(iA3);
                i4 = iB == i2 ? i4 + 1 : 0;
                iA = a(i);
                while (i3 < i) {
                    iA2 = a(i3);
                    iB += iA2;
                    if (iB == i2) {
                        i4++;
                        iB = 0;
                    } else if (iB > i2) {
                        i4++;
                        iB = iA2;
                    }
                    i3++;
                }
                if (iB + iA > i2) {
                    return i4 + 1;
                }
                return i4;
            }
            i3 = 0;
            iB = 0;
            iA = a(i);
            while (i3 < i) {
                iA2 = a(i3);
                iB += iA2;
                if (iB == i2) {
                    i4++;
                    iB = 0;
                } else if (iB > i2) {
                    i4++;
                    iB = iA2;
                }
                i3++;
            }
            if (iB + iA > i2) {
                return i4 + 1;
            }
            return i4;
        }

        public abstract int d(int i, int i2);
    }

    public GridLayoutManager(android.content.Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        this.I = false;
        this.J = -1;
        this.M = new android.util.SparseIntArray();
        this.N = new android.util.SparseIntArray();
        this.O = new androidx.recyclerview.widget.GridLayoutManager.a();
        this.P = new android.graphics.Rect();
        k(i);
    }

    public GridLayoutManager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.I = false;
        this.J = -1;
        this.M = new android.util.SparseIntArray();
        this.N = new android.util.SparseIntArray();
        this.O = new androidx.recyclerview.widget.GridLayoutManager.a();
        this.P = new android.graphics.Rect();
        k(androidx.recyclerview.widget.RecyclerView.o.a(context, attributeSet, i, i2).f222b);
    }

    private void L() {
        int iE = e();
        for (int i = 0; i < iE; i++) {
            androidx.recyclerview.widget.GridLayoutManager.b bVar = (androidx.recyclerview.widget.GridLayoutManager.b) c(i).getLayoutParams();
            int iA = bVar.a();
            this.M.put(iA, bVar.f());
            this.N.put(iA, bVar.e());
        }
    }

    private void M() {
        this.M.clear();
        this.N.clear();
    }

    private void N() {
        android.view.View[] viewArr = this.L;
        if (viewArr == null || viewArr.length != this.J) {
            this.L = new android.view.View[this.J];
        }
    }

    private void O() {
        int iH;
        int iP;
        if (H() == 1) {
            iH = q() - o();
            iP = n();
        } else {
            iH = h() - m();
            iP = p();
        }
        l(iH - iP);
    }

    private int a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i) {
        if (!a0Var.d()) {
            return this.O.a(i, this.J);
        }
        int iA = vVar.a(i);
        if (iA != -1) {
            return this.O.a(iA, this.J);
        }
        android.util.Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
        return 0;
    }

    private void a(float f, int i) {
        l(java.lang.Math.max(java.lang.Math.round(f * this.J), i));
    }

    private void a(android.view.View view, int i, int i2, boolean z) {
        androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
        if (z ? b(view, i, i2, pVar) : a(view, i, i2, pVar)) {
            view.measure(i, i2);
        }
    }

    private void a(android.view.View view, int i, boolean z) {
        int iA;
        int iA2;
        androidx.recyclerview.widget.GridLayoutManager.b bVar = (androidx.recyclerview.widget.GridLayoutManager.b) view.getLayoutParams();
        android.graphics.Rect rect = bVar.f225b;
        int i2 = rect.top + rect.bottom + ((android.view.ViewGroup.MarginLayoutParams) bVar).topMargin + ((android.view.ViewGroup.MarginLayoutParams) bVar).bottomMargin;
        int i3 = rect.left + rect.right + ((android.view.ViewGroup.MarginLayoutParams) bVar).leftMargin + ((android.view.ViewGroup.MarginLayoutParams) bVar).rightMargin;
        int iG = g(bVar.e, bVar.f);
        if (this.s == 1) {
            iA2 = androidx.recyclerview.widget.RecyclerView.o.a(iG, i, i3, ((android.view.ViewGroup.MarginLayoutParams) bVar).width, false);
            iA = androidx.recyclerview.widget.RecyclerView.o.a(this.u.g(), i(), i2, ((android.view.ViewGroup.MarginLayoutParams) bVar).height, true);
        } else {
            int iA3 = androidx.recyclerview.widget.RecyclerView.o.a(iG, i, i2, ((android.view.ViewGroup.MarginLayoutParams) bVar).height, false);
            int iA4 = androidx.recyclerview.widget.RecyclerView.o.a(this.u.g(), r(), i3, ((android.view.ViewGroup.MarginLayoutParams) bVar).width, true);
            iA = iA3;
            iA2 = iA4;
        }
        a(view, iA2, iA, z);
    }

    private void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i, boolean z) {
        int i2;
        int i3;
        int i4 = 0;
        int i5 = -1;
        if (z) {
            i5 = i;
            i2 = 0;
            i3 = 1;
        } else {
            i2 = i - 1;
            i3 = -1;
        }
        while (i2 != i5) {
            android.view.View view = this.L[i2];
            androidx.recyclerview.widget.GridLayoutManager.b bVar = (androidx.recyclerview.widget.GridLayoutManager.b) view.getLayoutParams();
            int iC = c(vVar, a0Var, l(view));
            bVar.f = iC;
            bVar.e = i4;
            i4 += iC;
            i2 += i3;
        }
    }

    static int[] a(int[] iArr, int i, int i2) {
        int i3;
        if (iArr == null || iArr.length != i + 1 || iArr[iArr.length - 1] != i2) {
            iArr = new int[i + 1];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 <= 0 || i - i4 >= i6) {
                i3 = i5;
            } else {
                i3 = i5 + 1;
                i4 -= i;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    private int b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i) {
        if (!a0Var.d()) {
            return this.O.b(i, this.J);
        }
        int i2 = this.N.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int iA = vVar.a(i);
        if (iA != -1) {
            return this.O.b(iA, this.J);
        }
        android.util.Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 0;
    }

    private void b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.a aVar, int i) {
        boolean z = i == 1;
        int iB = b(vVar, a0Var, aVar.f180b);
        if (z) {
            while (iB > 0) {
                int i2 = aVar.f180b;
                if (i2 <= 0) {
                    return;
                }
                int i3 = i2 - 1;
                aVar.f180b = i3;
                iB = b(vVar, a0Var, i3);
            }
            return;
        }
        int iA = a0Var.a() - 1;
        int i4 = aVar.f180b;
        while (i4 < iA) {
            int i5 = i4 + 1;
            int iB2 = b(vVar, a0Var, i5);
            if (iB2 <= iB) {
                break;
            }
            i4 = i5;
            iB = iB2;
        }
        aVar.f180b = i4;
    }

    private int c(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i) {
        if (!a0Var.d()) {
            return this.O.a(i);
        }
        int i2 = this.M.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int iA = vVar.a(i);
        if (iA != -1) {
            return this.O.a(iA);
        }
        android.util.Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 1;
    }

    private int i(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() != 0 && a0Var.a() != 0) {
            E();
            boolean zJ = J();
            android.view.View viewB = b(!zJ, true);
            android.view.View viewA = a(!zJ, true);
            if (viewB != null && viewA != null) {
                int iA = this.O.a(l(viewB), this.J);
                int iA2 = this.O.a(l(viewA), this.J);
                int iMax = this.x ? java.lang.Math.max(0, ((this.O.a(a0Var.a() - 1, this.J) + 1) - java.lang.Math.max(iA, iA2)) - 1) : java.lang.Math.max(0, java.lang.Math.min(iA, iA2));
                if (zJ) {
                    return java.lang.Math.round((iMax * (java.lang.Math.abs(this.u.a(viewA) - this.u.d(viewB)) / ((this.O.a(l(viewA), this.J) - this.O.a(l(viewB), this.J)) + 1))) + (this.u.f() - this.u.d(viewB)));
                }
                return iMax;
            }
        }
        return 0;
    }

    private int j(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() != 0 && a0Var.a() != 0) {
            E();
            android.view.View viewB = b(!J(), true);
            android.view.View viewA = a(!J(), true);
            if (viewB != null && viewA != null) {
                if (!J()) {
                    return this.O.a(a0Var.a() - 1, this.J) + 1;
                }
                return (int) (((this.u.a(viewA) - this.u.d(viewB)) / ((this.O.a(l(viewA), this.J) - this.O.a(l(viewB), this.J)) + 1)) * (this.O.a(a0Var.a() - 1, this.J) + 1));
            }
        }
        return 0;
    }

    private void l(int i) {
        this.K = a(this.K, this.J, i);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public boolean C() {
        return this.D == null && !this.I;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public int a(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        O();
        N();
        return super.a(i, vVar, a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (this.s == 1) {
            return this.J;
        }
        if (a0Var.a() < 1) {
            return 0;
        }
        return a(vVar, a0Var, a0Var.a() - 1) + 1;
    }

    /* JADX WARN: Code duplicated, block: B:79:0x0107  */
    /* JADX WARN: Code duplicated, block: B:81:0x010d  */
    /* JADX WARN: Code duplicated, block: B:82:0x0123  */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00d6, code lost:
    
        if (r13 == (r2 > r15)) goto L49;
     */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View a(android.view.View r24, int r25, androidx.recyclerview.widget.RecyclerView.v r26, androidx.recyclerview.widget.RecyclerView.a0 r27) {
        /*
            Method dump skipped, instruction units count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.a(android.view.View, int, androidx.recyclerview.widget.RecyclerView$v, androidx.recyclerview.widget.RecyclerView$a0):android.view.View");
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    android.view.View a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i, int i2, int i3) {
        E();
        int iF = this.u.f();
        int iB = this.u.b();
        int i4 = i2 > i ? 1 : -1;
        android.view.View view = null;
        android.view.View view2 = null;
        while (i != i2) {
            android.view.View viewC = c(i);
            int iL = l(viewC);
            if (iL >= 0 && iL < i3 && b(vVar, a0Var, iL) == 0) {
                if (((androidx.recyclerview.widget.RecyclerView.p) viewC.getLayoutParams()).c()) {
                    if (view2 == null) {
                        view2 = viewC;
                    }
                } else {
                    if (this.u.d(viewC) < iB && this.u.a(viewC) >= iF) {
                        return viewC;
                    }
                    if (view == null) {
                        view = viewC;
                    }
                }
            }
            i += i4;
        }
        return view != null ? view : view2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public androidx.recyclerview.widget.RecyclerView.p a(android.content.Context context, android.util.AttributeSet attributeSet) {
        return new androidx.recyclerview.widget.GridLayoutManager.b(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public androidx.recyclerview.widget.RecyclerView.p a(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.view.ViewGroup.MarginLayoutParams ? new androidx.recyclerview.widget.GridLayoutManager.b((android.view.ViewGroup.MarginLayoutParams) layoutParams) : new androidx.recyclerview.widget.GridLayoutManager.b(layoutParams);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(android.graphics.Rect rect, int i, int i2) {
        int iA;
        int iA2;
        if (this.K == null) {
            super.a(rect, i, i2);
        }
        int iN = n() + o();
        int iP = p() + m();
        if (this.s == 1) {
            iA2 = androidx.recyclerview.widget.RecyclerView.o.a(i2, rect.height() + iP, k());
            int[] iArr = this.K;
            iA = androidx.recyclerview.widget.RecyclerView.o.a(i, iArr[iArr.length - 1] + iN, l());
        } else {
            iA = androidx.recyclerview.widget.RecyclerView.o.a(i, rect.width() + iN, l());
            int[] iArr2 = this.K;
            iA2 = androidx.recyclerview.widget.RecyclerView.o.a(i2, iArr2[iArr2.length - 1] + iP, k());
        }
        c(iA, iA2);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    void a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.c cVar, androidx.recyclerview.widget.RecyclerView.o.c cVar2) {
        int iA = this.J;
        for (int i = 0; i < this.J && cVar.a(a0Var) && iA > 0; i++) {
            int i2 = cVar.d;
            cVar2.a(i2, java.lang.Math.max(0, cVar.g));
            iA -= this.O.a(i2);
            cVar.d += cVar.e;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, android.view.View view, a.c.e.q.c cVar) {
        int iF;
        int iE;
        int iF2;
        boolean z;
        boolean z2;
        int iE2;
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof androidx.recyclerview.widget.GridLayoutManager.b)) {
            super.a(view, cVar);
            return;
        }
        androidx.recyclerview.widget.GridLayoutManager.b bVar = (androidx.recyclerview.widget.GridLayoutManager.b) layoutParams;
        int iA = a(vVar, a0Var, bVar.a());
        if (this.s == 0) {
            iE2 = bVar.e();
            iF = bVar.f();
            iF2 = 1;
            z = false;
            z2 = false;
            iE = iA;
        } else {
            iF = 1;
            iE = bVar.e();
            iF2 = bVar.f();
            z = false;
            z2 = false;
            iE2 = iA;
        }
        cVar.b(a.c.e.q.c.C0004c.a(iE2, iF, iE, iF2, z, z2));
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.a aVar, int i) {
        super.a(vVar, a0Var, aVar, i);
        O();
        if (a0Var.a() > 0 && !a0Var.d()) {
            b(vVar, a0Var, aVar, i);
        }
        N();
    }

    /* JADX WARN: Code duplicated, block: B:98:0x0219  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v19 */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.c cVar, androidx.recyclerview.widget.LinearLayoutManager.b bVar) {
        int i;
        int iC;
        int i2;
        int i3;
        int i4;
        int iC2;
        int iC3;
        int i5;
        int iA;
        int iA2;
        android.view.View viewA;
        int iE = this.u.e();
        ?? r5 = 0;
        boolean z = iE != 1073741824;
        int i6 = e() > 0 ? this.K[this.J] : 0;
        if (z) {
            O();
        }
        boolean z2 = cVar.e == 1;
        int iB = this.J;
        if (!z2) {
            iB = b(vVar, a0Var, cVar.d) + c(vVar, a0Var, cVar.d);
        }
        int i7 = 0;
        while (i7 < this.J && cVar.a(a0Var) && iB > 0) {
            int i8 = cVar.d;
            int iC4 = c(vVar, a0Var, i8);
            if (iC4 > this.J) {
                throw new java.lang.IllegalArgumentException("Item at position " + i8 + " requires " + iC4 + " spans but GridLayoutManager has only " + this.J + " spans.");
            }
            iB -= iC4;
            if (iB < 0 || (viewA = cVar.a(vVar)) == null) {
                break;
            }
            this.L[i7] = viewA;
            i7++;
        }
        if (i7 == 0) {
            bVar.f183b = true;
            return;
        }
        float f = 0.0f;
        a(vVar, a0Var, i7, z2);
        int i9 = 0;
        int i10 = 0;
        while (i9 < i7) {
            android.view.View view = this.L[i9];
            if (cVar.l == null) {
                if (z2) {
                    b(view);
                } else {
                    b(view, (int) r5);
                }
            } else if (z2) {
                a(view);
            } else {
                a(view, (int) r5);
            }
            a(view, this.P);
            a(view, iE, (boolean) r5);
            int iB2 = this.u.b(view);
            if (iB2 > i10) {
                i10 = iB2;
            }
            float fC = (this.u.c(view) * 1.0f) / ((androidx.recyclerview.widget.GridLayoutManager.b) view.getLayoutParams()).f;
            if (fC > f) {
                f = fC;
            }
            i9++;
            r5 = 0;
        }
        if (z) {
            a(f, i6);
            i10 = 0;
            for (int i11 = 0; i11 < i7; i11++) {
                android.view.View view2 = this.L[i11];
                a(view2, 1073741824, true);
                int iB3 = this.u.b(view2);
                if (iB3 > i10) {
                    i10 = iB3;
                }
            }
        }
        for (int i12 = 0; i12 < i7; i12++) {
            android.view.View view3 = this.L[i12];
            if (this.u.b(view3) != i10) {
                androidx.recyclerview.widget.GridLayoutManager.b bVar2 = (androidx.recyclerview.widget.GridLayoutManager.b) view3.getLayoutParams();
                android.graphics.Rect rect = bVar2.f225b;
                int i13 = rect.top + rect.bottom + ((android.view.ViewGroup.MarginLayoutParams) bVar2).topMargin + ((android.view.ViewGroup.MarginLayoutParams) bVar2).bottomMargin;
                int i14 = rect.left + rect.right + ((android.view.ViewGroup.MarginLayoutParams) bVar2).leftMargin + ((android.view.ViewGroup.MarginLayoutParams) bVar2).rightMargin;
                int iG = g(bVar2.e, bVar2.f);
                if (this.s == 1) {
                    iA2 = androidx.recyclerview.widget.RecyclerView.o.a(iG, 1073741824, i14, ((android.view.ViewGroup.MarginLayoutParams) bVar2).width, false);
                    iA = android.view.View.MeasureSpec.makeMeasureSpec(i10 - i13, 1073741824);
                } else {
                    int iMakeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i10 - i14, 1073741824);
                    iA = androidx.recyclerview.widget.RecyclerView.o.a(iG, 1073741824, i13, ((android.view.ViewGroup.MarginLayoutParams) bVar2).height, false);
                    iA2 = iMakeMeasureSpec;
                }
                a(view3, iA2, iA, true);
            }
        }
        int i15 = 0;
        bVar.f182a = i10;
        if (this.s == 1) {
            if (cVar.f == -1) {
                i2 = cVar.f186b;
                i3 = i2 - i10;
            } else {
                int i16 = cVar.f186b;
                i3 = i16;
                i2 = i10 + i16;
            }
            i = 0;
            iC = 0;
        } else if (cVar.f == -1) {
            int i17 = cVar.f186b;
            iC = i17 - i10;
            i3 = 0;
            i = i17;
            i2 = 0;
        } else {
            int i18 = cVar.f186b;
            i = i10 + i18;
            iC = i18;
            i2 = 0;
            i3 = 0;
        }
        while (i15 < i7) {
            android.view.View view4 = this.L[i15];
            androidx.recyclerview.widget.GridLayoutManager.b bVar3 = (androidx.recyclerview.widget.GridLayoutManager.b) view4.getLayoutParams();
            if (this.s == 1) {
                if (I()) {
                    int iN = n() + this.K[this.J - bVar3.e];
                    iC = iN - this.u.c(view4);
                    iC3 = i2;
                    iC2 = iN;
                    i4 = i3;
                } else {
                    int iN2 = n() + this.K[bVar3.e];
                    iC3 = i2;
                    i5 = iN2;
                    i4 = i3;
                    iC2 = this.u.c(view4) + iN2;
                }
                a(view4, i5, i4, iC2, iC3);
                if (bVar3.c() || bVar3.b()) {
                    bVar.f184c = true;
                }
                bVar.d |= view4.hasFocusable();
                i15++;
                i2 = iC3;
                i = iC2;
                i3 = i4;
                iC = i5;
            } else {
                int iP = p() + this.K[bVar3.e];
                i4 = iP;
                iC2 = i;
                iC3 = this.u.c(view4) + iP;
            }
            i5 = iC;
            a(view4, i5, i4, iC2, iC3);
            if (bVar3.c()) {
                bVar.f184c = true;
            } else {
                bVar.f184c = true;
            }
            bVar.d |= view4.hasFocusable();
            i15++;
            i2 = iC3;
            i = iC2;
            i3 = i4;
            iC = i5;
        }
        java.util.Arrays.fill(this.L, (java.lang.Object) null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        this.O.b();
        this.O.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2, int i3) {
        this.O.b();
        this.O.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2, java.lang.Object obj) {
        this.O.b();
        this.O.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean a(androidx.recyclerview.widget.RecyclerView.p pVar) {
        return pVar instanceof androidx.recyclerview.widget.GridLayoutManager.b;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public int b(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        O();
        N();
        return super.b(i, vVar, a0Var);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public int b(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.Q ? i(a0Var) : super.b(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (this.s == 0) {
            return this.J;
        }
        if (a0Var.a() < 1) {
            return 0;
        }
        return a(vVar, a0Var, a0Var.a() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void b(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        this.O.b();
        this.O.a();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void b(boolean z) {
        if (z) {
            throw new java.lang.UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.b(false);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public int c(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.Q ? j(a0Var) : super.c(a0Var);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public androidx.recyclerview.widget.RecyclerView.p c() {
        return this.s == 0 ? new androidx.recyclerview.widget.GridLayoutManager.b(-2, -1) : new androidx.recyclerview.widget.GridLayoutManager.b(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void d(androidx.recyclerview.widget.RecyclerView recyclerView) {
        this.O.b();
        this.O.a();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public int e(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.Q ? i(a0Var) : super.e(a0Var);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public void e(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (a0Var.d()) {
            L();
        }
        super.e(vVar, a0Var);
        M();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public int f(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.Q ? j(a0Var) : super.f(a0Var);
    }

    int g(int i, int i2) {
        if (this.s != 1 || !I()) {
            int[] iArr = this.K;
            return iArr[i2 + i] - iArr[i];
        }
        int[] iArr2 = this.K;
        int i3 = this.J;
        return iArr2[i3 - i] - iArr2[(i3 - i) - i2];
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
    public void g(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        super.g(a0Var);
        this.I = false;
    }

    public void k(int i) {
        if (i == this.J) {
            return;
        }
        this.I = true;
        if (i >= 1) {
            this.J = i;
            this.O.b();
            y();
        } else {
            throw new java.lang.IllegalArgumentException("Span count should be at least 1. Provided " + i);
        }
    }
}
