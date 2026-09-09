package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutManager extends androidx.recyclerview.widget.RecyclerView.o implements androidx.recyclerview.widget.f.i {
    int A;
    int B;
    private boolean C;
    androidx.recyclerview.widget.LinearLayoutManager.d D;
    final androidx.recyclerview.widget.LinearLayoutManager.a E;
    private final androidx.recyclerview.widget.LinearLayoutManager.b F;
    private int G;
    private int[] H;
    int s;
    private androidx.recyclerview.widget.LinearLayoutManager.c t;
    androidx.recyclerview.widget.k u;
    private boolean v;
    private boolean w;
    boolean x;
    private boolean y;
    private boolean z;

    static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        androidx.recyclerview.widget.k f179a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f180b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f181c;
        boolean d;
        boolean e;

        a() {
            b();
        }

        void a() {
            this.f181c = this.d ? this.f179a.b() : this.f179a.f();
        }

        public void a(android.view.View view, int i) {
            this.f181c = this.d ? this.f179a.a(view) + this.f179a.h() : this.f179a.d(view);
            this.f180b = i;
        }

        boolean a(android.view.View view, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            return !pVar.c() && pVar.a() >= 0 && pVar.a() < a0Var.a();
        }

        void b() {
            this.f180b = -1;
            this.f181c = Integer.MIN_VALUE;
            this.d = false;
            this.e = false;
        }

        public void b(android.view.View view, int i) {
            int iH = this.f179a.h();
            if (iH >= 0) {
                a(view, i);
                return;
            }
            this.f180b = i;
            if (this.d) {
                int iB = (this.f179a.b() - iH) - this.f179a.a(view);
                this.f181c = this.f179a.b() - iB;
                if (iB > 0) {
                    int iB2 = this.f181c - this.f179a.b(view);
                    int iF = this.f179a.f();
                    int iMin = iB2 - (iF + java.lang.Math.min(this.f179a.d(view) - iF, 0));
                    if (iMin < 0) {
                        this.f181c += java.lang.Math.min(iB, -iMin);
                        return;
                    }
                    return;
                }
                return;
            }
            int iD = this.f179a.d(view);
            int iF2 = iD - this.f179a.f();
            this.f181c = iD;
            if (iF2 > 0) {
                int iB3 = (this.f179a.b() - java.lang.Math.min(0, (this.f179a.b() - iH) - this.f179a.a(view))) - (iD + this.f179a.b(view));
                if (iB3 < 0) {
                    this.f181c -= java.lang.Math.min(iF2, -iB3);
                }
            }
        }

        public java.lang.String toString() {
            return "AnchorInfo{mPosition=" + this.f180b + ", mCoordinate=" + this.f181c + ", mLayoutFromEnd=" + this.d + ", mValid=" + this.e + '}';
        }
    }

    protected static class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f182a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public boolean f183b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public boolean f184c;
        public boolean d;

        protected b() {
        }

        void a() {
            this.f182a = 0;
            this.f183b = false;
            this.f184c = false;
            this.d = false;
        }
    }

    static class c {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f186b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f187c;
        int d;
        int e;
        int f;
        int g;
        boolean j;
        int k;
        boolean m;

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        boolean f185a = true;
        int h = 0;
        int i = 0;
        java.util.List<androidx.recyclerview.widget.RecyclerView.d0> l = null;

        c() {
        }

        private android.view.View b() {
            int size = this.l.size();
            for (int i = 0; i < size; i++) {
                android.view.View view = this.l.get(i).f203a;
                androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
                if (!pVar.c() && this.d == pVar.a()) {
                    a(view);
                    return view;
                }
            }
            return null;
        }

        android.view.View a(androidx.recyclerview.widget.RecyclerView.v vVar) {
            if (this.l != null) {
                return b();
            }
            android.view.View viewD = vVar.d(this.d);
            this.d += this.e;
            return viewD;
        }

        public void a() {
            a((android.view.View) null);
        }

        public void a(android.view.View view) {
            android.view.View viewB = b(view);
            this.d = viewB == null ? -1 : ((androidx.recyclerview.widget.RecyclerView.p) viewB.getLayoutParams()).a();
        }

        boolean a(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            int i = this.d;
            return i >= 0 && i < a0Var.a();
        }

        public android.view.View b(android.view.View view) {
            int iA;
            int size = this.l.size();
            android.view.View view2 = null;
            int i = Integer.MAX_VALUE;
            for (int i2 = 0; i2 < size; i2++) {
                android.view.View view3 = this.l.get(i2).f203a;
                androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view3.getLayoutParams();
                if (view3 != view && !pVar.c() && (iA = (pVar.a() - this.d) * this.e) >= 0 && iA < i) {
                    view2 = view3;
                    if (iA == 0) {
                        break;
                    }
                    i = iA;
                }
            }
            return view2;
        }
    }

    @android.annotation.SuppressLint({"BanParcelableUsage"})
    public static class d implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<androidx.recyclerview.widget.LinearLayoutManager.d> CREATOR = new androidx.recyclerview.widget.LinearLayoutManager.d.a();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f188a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f189b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        boolean f190c;

        static class a implements android.os.Parcelable.Creator<androidx.recyclerview.widget.LinearLayoutManager.d> {
            a() {
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public androidx.recyclerview.widget.LinearLayoutManager.d createFromParcel(android.os.Parcel parcel) {
                return new androidx.recyclerview.widget.LinearLayoutManager.d(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public androidx.recyclerview.widget.LinearLayoutManager.d[] newArray(int i) {
                return new androidx.recyclerview.widget.LinearLayoutManager.d[i];
            }
        }

        public d() {
        }

        d(android.os.Parcel parcel) {
            this.f188a = parcel.readInt();
            this.f189b = parcel.readInt();
            this.f190c = parcel.readInt() == 1;
        }

        public d(androidx.recyclerview.widget.LinearLayoutManager.d dVar) {
            this.f188a = dVar.f188a;
            this.f189b = dVar.f189b;
            this.f190c = dVar.f190c;
        }

        boolean a() {
            return this.f188a >= 0;
        }

        void b() {
            this.f188a = -1;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.f188a);
            parcel.writeInt(this.f189b);
            parcel.writeInt(this.f190c ? 1 : 0);
        }
    }

    public LinearLayoutManager(android.content.Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(android.content.Context context, int i, boolean z) {
        this.s = 1;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = true;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.D = null;
        this.E = new androidx.recyclerview.widget.LinearLayoutManager.a();
        this.F = new androidx.recyclerview.widget.LinearLayoutManager.b();
        this.G = 2;
        this.H = new int[2];
        j(i);
        a(z);
    }

    public LinearLayoutManager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        this.s = 1;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = true;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.D = null;
        this.E = new androidx.recyclerview.widget.LinearLayoutManager.a();
        this.F = new androidx.recyclerview.widget.LinearLayoutManager.b();
        this.G = 2;
        this.H = new int[2];
        androidx.recyclerview.widget.RecyclerView.o.d dVarA = androidx.recyclerview.widget.RecyclerView.o.a(context, attributeSet, i, i2);
        j(dVarA.f221a);
        a(dVarA.f223c);
        b(dVarA.d);
    }

    private android.view.View L() {
        return e(0, e());
    }

    private android.view.View M() {
        return e(e() - 1, -1);
    }

    private android.view.View N() {
        return this.x ? L() : M();
    }

    private android.view.View O() {
        return this.x ? M() : L();
    }

    private android.view.View P() {
        return c(this.x ? 0 : e() - 1);
    }

    private android.view.View Q() {
        return c(this.x ? e() - 1 : 0);
    }

    private void R() {
        this.x = (this.s == 1 || !I()) ? this.w : !this.w;
    }

    private int a(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, boolean z) {
        int iB;
        int iB2 = this.u.b() - i;
        if (iB2 <= 0) {
            return 0;
        }
        int i2 = -c(-iB2, vVar, a0Var);
        int i3 = i + i2;
        if (!z || (iB = this.u.b() - i3) <= 0) {
            return i2;
        }
        this.u.a(iB);
        return iB + i2;
    }

    private void a(int i, int i2, boolean z, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        int iF;
        this.t.m = K();
        this.t.f = i;
        int[] iArr = this.H;
        iArr[0] = 0;
        iArr[1] = 0;
        a(a0Var, iArr);
        int iMax = java.lang.Math.max(0, this.H[0]);
        int iMax2 = java.lang.Math.max(0, this.H[1]);
        boolean z2 = i == 1;
        this.t.h = z2 ? iMax2 : iMax;
        androidx.recyclerview.widget.LinearLayoutManager.c cVar = this.t;
        if (!z2) {
            iMax = iMax2;
        }
        cVar.i = iMax;
        if (z2) {
            this.t.h += this.u.c();
            android.view.View viewP = P();
            this.t.e = this.x ? -1 : 1;
            androidx.recyclerview.widget.LinearLayoutManager.c cVar2 = this.t;
            int iL = l(viewP);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar3 = this.t;
            cVar2.d = iL + cVar3.e;
            cVar3.f186b = this.u.a(viewP);
            iF = this.u.a(viewP) - this.u.b();
        } else {
            android.view.View viewQ = Q();
            this.t.h += this.u.f();
            this.t.e = this.x ? 1 : -1;
            androidx.recyclerview.widget.LinearLayoutManager.c cVar4 = this.t;
            int iL2 = l(viewQ);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar5 = this.t;
            cVar4.d = iL2 + cVar5.e;
            cVar5.f186b = this.u.d(viewQ);
            iF = (-this.u.d(viewQ)) + this.u.f();
        }
        androidx.recyclerview.widget.LinearLayoutManager.c cVar6 = this.t;
        cVar6.f187c = i2;
        if (z) {
            cVar6.f187c = i2 - iF;
        }
        this.t.g = iF;
    }

    private void a(androidx.recyclerview.widget.LinearLayoutManager.a aVar) {
        g(aVar.f180b, aVar.f181c);
    }

    private void a(androidx.recyclerview.widget.RecyclerView.v vVar, int i, int i2) {
        if (i == i2) {
            return;
        }
        if (i2 <= i) {
            while (i > i2) {
                a(i, vVar);
                i--;
            }
        } else {
            for (int i3 = i2 - 1; i3 >= i; i3--) {
                a(i3, vVar);
            }
        }
    }

    private void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.LinearLayoutManager.c cVar) {
        if (!cVar.f185a || cVar.m) {
            return;
        }
        int i = cVar.g;
        int i2 = cVar.i;
        if (cVar.f == -1) {
            b(vVar, i, i2);
        } else {
            c(vVar, i, i2);
        }
    }

    private boolean a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.a aVar) {
        int i;
        if (!a0Var.d() && (i = this.A) != -1) {
            if (i >= 0 && i < a0Var.a()) {
                aVar.f180b = this.A;
                androidx.recyclerview.widget.LinearLayoutManager.d dVar = this.D;
                if (dVar != null && dVar.a()) {
                    boolean z = this.D.f190c;
                    aVar.d = z;
                    aVar.f181c = z ? this.u.b() - this.D.f189b : this.u.f() + this.D.f189b;
                    return true;
                }
                if (this.B != Integer.MIN_VALUE) {
                    boolean z2 = this.x;
                    aVar.d = z2;
                    aVar.f181c = z2 ? this.u.b() - this.B : this.u.f() + this.B;
                    return true;
                }
                android.view.View viewB = b(this.A);
                if (viewB == null) {
                    if (e() > 0) {
                        aVar.d = (this.A < l(c(0))) == this.x;
                    }
                    aVar.a();
                } else {
                    if (this.u.b(viewB) > this.u.g()) {
                        aVar.a();
                        return true;
                    }
                    if (this.u.d(viewB) - this.u.f() < 0) {
                        aVar.f181c = this.u.f();
                        aVar.d = false;
                        return true;
                    }
                    if (this.u.b() - this.u.a(viewB) < 0) {
                        aVar.f181c = this.u.b();
                        aVar.d = true;
                        return true;
                    }
                    aVar.f181c = aVar.d ? this.u.a(viewB) + this.u.h() : this.u.d(viewB);
                }
                return true;
            }
            this.A = -1;
            this.B = Integer.MIN_VALUE;
        }
        return false;
    }

    private boolean a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.a aVar) {
        if (e() == 0) {
            return false;
        }
        android.view.View viewG = g();
        if (viewG != null && aVar.a(viewG, a0Var)) {
            aVar.b(viewG, l(viewG));
            return true;
        }
        if (this.v != this.y) {
            return false;
        }
        android.view.View viewH = aVar.d ? h(vVar, a0Var) : i(vVar, a0Var);
        if (viewH == null) {
            return false;
        }
        aVar.a(viewH, l(viewH));
        if (!a0Var.d() && C()) {
            if (this.u.d(viewH) >= this.u.b() || this.u.a(viewH) < this.u.f()) {
                aVar.f181c = aVar.d ? this.u.b() : this.u.f();
            }
        }
        return true;
    }

    private int b(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, boolean z) {
        int iF;
        int iF2 = i - this.u.f();
        if (iF2 <= 0) {
            return 0;
        }
        int i2 = -c(iF2, vVar, a0Var);
        int i3 = i + i2;
        if (!z || (iF = i3 - this.u.f()) <= 0) {
            return i2;
        }
        this.u.a(-iF);
        return i2 - iF;
    }

    private void b(androidx.recyclerview.widget.LinearLayoutManager.a aVar) {
        h(aVar.f180b, aVar.f181c);
    }

    private void b(androidx.recyclerview.widget.RecyclerView.v vVar, int i, int i2) {
        int iE = e();
        if (i < 0) {
            return;
        }
        int iA = (this.u.a() - i) + i2;
        if (this.x) {
            for (int i3 = 0; i3 < iE; i3++) {
                android.view.View viewC = c(i3);
                if (this.u.d(viewC) < iA || this.u.f(viewC) < iA) {
                    a(vVar, 0, i3);
                    return;
                }
            }
            return;
        }
        int i4 = iE - 1;
        for (int i5 = i4; i5 >= 0; i5--) {
            android.view.View viewC2 = c(i5);
            if (this.u.d(viewC2) < iA || this.u.f(viewC2) < iA) {
                a(vVar, i4, i5);
                return;
            }
        }
    }

    private void b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, int i, int i2) {
        if (!a0Var.e() || e() == 0 || a0Var.d() || !C()) {
            return;
        }
        java.util.List<androidx.recyclerview.widget.RecyclerView.d0> listF = vVar.f();
        int size = listF.size();
        int iL = l(c(0));
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0Var = listF.get(i5);
            if (!d0Var.q()) {
                byte b2 = (d0Var.i() < iL) != this.x ? (byte) -1 : (byte) 1;
                int iB = this.u.b(d0Var.f203a);
                if (b2 == -1) {
                    i3 += iB;
                } else {
                    i4 += iB;
                }
            }
        }
        this.t.l = listF;
        if (i3 > 0) {
            h(l(Q()), i);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar = this.t;
            cVar.h = i3;
            cVar.f187c = 0;
            cVar.a();
            a(vVar, this.t, a0Var, false);
        }
        if (i4 > 0) {
            g(l(P()), i2);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar2 = this.t;
            cVar2.h = i4;
            cVar2.f187c = 0;
            cVar2.a();
            a(vVar, this.t, a0Var, false);
        }
        this.t.l = null;
    }

    private void b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.a aVar) {
        if (a(a0Var, aVar) || a(vVar, a0Var, aVar)) {
            return;
        }
        aVar.a();
        aVar.f180b = this.y ? a0Var.a() - 1 : 0;
    }

    private void c(androidx.recyclerview.widget.RecyclerView.v vVar, int i, int i2) {
        if (i < 0) {
            return;
        }
        int i3 = i - i2;
        int iE = e();
        if (!this.x) {
            for (int i4 = 0; i4 < iE; i4++) {
                android.view.View viewC = c(i4);
                if (this.u.a(viewC) > i3 || this.u.e(viewC) > i3) {
                    a(vVar, 0, i4);
                    return;
                }
            }
            return;
        }
        int i5 = iE - 1;
        for (int i6 = i5; i6 >= 0; i6--) {
            android.view.View viewC2 = c(i6);
            if (this.u.a(viewC2) > i3 || this.u.e(viewC2) > i3) {
                a(vVar, i5, i6);
                return;
            }
        }
    }

    private android.view.View f(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return a(vVar, a0Var, 0, e(), a0Var.a());
    }

    private android.view.View g(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return a(vVar, a0Var, e() - 1, -1, a0Var.a());
    }

    private void g(int i, int i2) {
        this.t.f187c = this.u.b() - i2;
        this.t.e = this.x ? -1 : 1;
        androidx.recyclerview.widget.LinearLayoutManager.c cVar = this.t;
        cVar.d = i;
        cVar.f = 1;
        cVar.f186b = i2;
        cVar.g = Integer.MIN_VALUE;
    }

    private android.view.View h(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.x ? f(vVar, a0Var) : g(vVar, a0Var);
    }

    private void h(int i, int i2) {
        this.t.f187c = i2 - this.u.f();
        androidx.recyclerview.widget.LinearLayoutManager.c cVar = this.t;
        cVar.d = i;
        cVar.e = this.x ? 1 : -1;
        androidx.recyclerview.widget.LinearLayoutManager.c cVar2 = this.t;
        cVar2.f = -1;
        cVar2.f186b = i2;
        cVar2.g = Integer.MIN_VALUE;
    }

    private int i(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0) {
            return 0;
        }
        E();
        return androidx.recyclerview.widget.m.a(a0Var, this.u, b(!this.z, true), a(!this.z, true), this, this.z);
    }

    private android.view.View i(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.x ? g(vVar, a0Var) : f(vVar, a0Var);
    }

    private int j(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0) {
            return 0;
        }
        E();
        return androidx.recyclerview.widget.m.a(a0Var, this.u, b(!this.z, true), a(!this.z, true), this, this.z, this.x);
    }

    private int k(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0) {
            return 0;
        }
        E();
        return androidx.recyclerview.widget.m.b(a0Var, this.u, b(!this.z, true), a(!this.z, true), this, this.z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    boolean A() {
        return (i() == 1073741824 || r() == 1073741824 || !s()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean C() {
        return this.D == null && this.v == this.y;
    }

    androidx.recyclerview.widget.LinearLayoutManager.c D() {
        return new androidx.recyclerview.widget.LinearLayoutManager.c();
    }

    void E() {
        if (this.t == null) {
            this.t = D();
        }
    }

    public int F() {
        android.view.View viewA = a(0, e(), false, true);
        if (viewA == null) {
            return -1;
        }
        return l(viewA);
    }

    public int G() {
        android.view.View viewA = a(e() - 1, -1, false, true);
        if (viewA == null) {
            return -1;
        }
        return l(viewA);
    }

    public int H() {
        return this.s;
    }

    protected boolean I() {
        return j() == 1;
    }

    public boolean J() {
        return this.z;
    }

    boolean K() {
        return this.u.d() == 0 && this.u.a() == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int a(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (this.s == 1) {
            return 0;
        }
        return c(i, vVar, a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int a(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return i(a0Var);
    }

    int a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.LinearLayoutManager.c cVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, boolean z) {
        int i = cVar.f187c;
        int i2 = cVar.g;
        if (i2 != Integer.MIN_VALUE) {
            if (i < 0) {
                cVar.g = i2 + i;
            }
            a(vVar, cVar);
        }
        int i3 = cVar.f187c + cVar.h;
        androidx.recyclerview.widget.LinearLayoutManager.b bVar = this.F;
        while (true) {
            if ((!cVar.m && i3 <= 0) || !cVar.a(a0Var)) {
                break;
            }
            bVar.a();
            a(vVar, a0Var, cVar, bVar);
            if (!bVar.f183b) {
                cVar.f186b += bVar.f182a * cVar.f;
                if (!bVar.f184c || cVar.l != null || !a0Var.d()) {
                    int i4 = cVar.f187c;
                    int i5 = bVar.f182a;
                    cVar.f187c = i4 - i5;
                    i3 -= i5;
                }
                int i6 = cVar.g;
                if (i6 != Integer.MIN_VALUE) {
                    int i7 = i6 + bVar.f182a;
                    cVar.g = i7;
                    int i8 = cVar.f187c;
                    if (i8 < 0) {
                        cVar.g = i7 + i8;
                    }
                    a(vVar, cVar);
                }
                if (z && bVar.d) {
                    break;
                }
            } else {
                break;
            }
        }
        return i - cVar.f187c;
    }

    android.view.View a(int i, int i2, boolean z, boolean z2) {
        E();
        return (this.s == 0 ? this.e : this.f).a(i, i2, z ? 24579 : 320, z2 ? 320 : 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public android.view.View a(android.view.View view, int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        int i2;
        R();
        if (e() == 0 || (i2 = i(i)) == Integer.MIN_VALUE) {
            return null;
        }
        E();
        a(i2, (int) (this.u.g() * 0.33333334f), false, a0Var);
        androidx.recyclerview.widget.LinearLayoutManager.c cVar = this.t;
        cVar.g = Integer.MIN_VALUE;
        cVar.f185a = false;
        a(vVar, cVar, a0Var, true);
        android.view.View viewO = i2 == -1 ? O() : N();
        android.view.View viewQ = i2 == -1 ? Q() : P();
        if (!viewQ.hasFocusable()) {
            return viewO;
        }
        if (viewO == null) {
            return null;
        }
        return viewQ;
    }

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
            if (iL >= 0 && iL < i3) {
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

    android.view.View a(boolean z, boolean z2) {
        int iE;
        int iE2;
        if (this.x) {
            iE = 0;
            iE2 = e();
        } else {
            iE = e() - 1;
            iE2 = -1;
        }
        return a(iE, iE2, z, z2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(int i, int i2, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.RecyclerView.o.c cVar) {
        if (this.s != 0) {
            i = i2;
        }
        if (e() == 0 || i == 0) {
            return;
        }
        E();
        a(i > 0 ? 1 : -1, java.lang.Math.abs(i), true, a0Var);
        a(a0Var, this.t, cVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(int i, androidx.recyclerview.widget.RecyclerView.o.c cVar) {
        boolean z;
        int i2;
        androidx.recyclerview.widget.LinearLayoutManager.d dVar = this.D;
        if (dVar == null || !dVar.a()) {
            R();
            z = this.x;
            i2 = this.A;
            if (i2 == -1) {
                i2 = z ? i - 1 : 0;
            }
        } else {
            androidx.recyclerview.widget.LinearLayoutManager.d dVar2 = this.D;
            z = dVar2.f190c;
            i2 = dVar2.f188a;
        }
        int i3 = z ? -1 : 1;
        for (int i4 = 0; i4 < this.G && i2 >= 0 && i2 < i; i4++) {
            cVar.a(i2, 0);
            i2 += i3;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(android.os.Parcelable parcelable) {
        if (parcelable instanceof androidx.recyclerview.widget.LinearLayoutManager.d) {
            this.D = (androidx.recyclerview.widget.LinearLayoutManager.d) parcelable;
            y();
        }
    }

    @Override // androidx.recyclerview.widget.f.i
    public void a(android.view.View view, android.view.View view2, int i, int i2) {
        int iD;
        a("Cannot drop a view during a scroll or layout calculation");
        E();
        R();
        int iL = l(view);
        int iL2 = l(view2);
        byte b2 = iL < iL2 ? (byte) 1 : (byte) -1;
        if (this.x) {
            if (b2 == 1) {
                f(iL2, this.u.b() - (this.u.d(view2) + this.u.b(view)));
                return;
            }
            iD = this.u.b() - this.u.a(view2);
        } else {
            if (b2 != -1) {
                f(iL2, this.u.a(view2) - this.u.b(view));
                return;
            }
            iD = this.u.d(view2);
        }
        f(iL2, iD);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.a(accessibilityEvent);
        if (e() > 0) {
            accessibilityEvent.setFromIndex(F());
            accessibilityEvent.setToIndex(G());
        }
    }

    void a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.c cVar, androidx.recyclerview.widget.RecyclerView.o.c cVar2) {
        int i = cVar.d;
        if (i < 0 || i >= a0Var.a()) {
            return;
        }
        cVar2.a(i, java.lang.Math.max(0, cVar.g));
    }

    protected void a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, int[] iArr) {
        int i;
        int iH = h(a0Var);
        if (this.t.f == -1) {
            i = 0;
        } else {
            i = iH;
            iH = 0;
        }
        iArr[0] = iH;
        iArr[1] = i;
    }

    void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.a aVar, int i) {
    }

    void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.LinearLayoutManager.c cVar, androidx.recyclerview.widget.LinearLayoutManager.b bVar) {
        int i;
        int i2;
        int i3;
        int iN;
        int iC;
        android.view.View viewA = cVar.a(vVar);
        if (viewA == null) {
            bVar.f183b = true;
            return;
        }
        androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) viewA.getLayoutParams();
        if (cVar.l == null) {
            if (this.x == (cVar.f == -1)) {
                b(viewA);
            } else {
                b(viewA, 0);
            }
        } else {
            if (this.x == (cVar.f == -1)) {
                a(viewA);
            } else {
                a(viewA, 0);
            }
        }
        a(viewA, 0, 0);
        bVar.f182a = this.u.b(viewA);
        if (this.s == 1) {
            if (I()) {
                iC = q() - o();
                iN = iC - this.u.c(viewA);
            } else {
                iN = n();
                iC = this.u.c(viewA) + iN;
            }
            int i4 = cVar.f;
            int i5 = cVar.f186b;
            if (i4 == -1) {
                i3 = i5;
                i2 = iC;
                i = i5 - bVar.f182a;
            } else {
                i = i5;
                i2 = iC;
                i3 = bVar.f182a + i5;
            }
        } else {
            int iP = p();
            int iC2 = this.u.c(viewA) + iP;
            int i6 = cVar.f;
            int i7 = cVar.f186b;
            if (i6 == -1) {
                i2 = i7;
                i = iP;
                i3 = iC2;
                iN = i7 - bVar.f182a;
            } else {
                i = iP;
                i2 = bVar.f182a + i7;
                i3 = iC2;
                iN = i7;
            }
        }
        a(viewA, iN, i, i2, i3);
        if (pVar.c() || pVar.b()) {
            bVar.f184c = true;
        }
        bVar.d = viewA.hasFocusable();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(java.lang.String str) {
        if (this.D == null) {
            super.a(str);
        }
    }

    public void a(boolean z) {
        a((java.lang.String) null);
        if (z == this.w) {
            return;
        }
        this.w = z;
        y();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean a() {
        return this.s == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int b(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (this.s == 0) {
            return 0;
        }
        return c(i, vVar, a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int b(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return j(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public android.view.View b(int i) {
        int iE = e();
        if (iE == 0) {
            return null;
        }
        int iL = i - l(c(0));
        if (iL >= 0 && iL < iE) {
            android.view.View viewC = c(iL);
            if (l(viewC) == i) {
                return viewC;
            }
        }
        return super.b(i);
    }

    android.view.View b(boolean z, boolean z2) {
        int iE;
        int iE2;
        if (this.x) {
            iE = e() - 1;
            iE2 = -1;
        } else {
            iE = 0;
            iE2 = e();
        }
        return a(iE, iE2, z, z2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void b(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.v vVar) {
        super.b(recyclerView, vVar);
        if (this.C) {
            b(vVar);
            vVar.a();
        }
    }

    public void b(boolean z) {
        a((java.lang.String) null);
        if (this.y == z) {
            return;
        }
        this.y = z;
        y();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean b() {
        return this.s == 1;
    }

    int c(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0 || i == 0) {
            return 0;
        }
        E();
        this.t.f185a = true;
        int i2 = i > 0 ? 1 : -1;
        int iAbs = java.lang.Math.abs(i);
        a(i2, iAbs, true, a0Var);
        androidx.recyclerview.widget.LinearLayoutManager.c cVar = this.t;
        int iA = cVar.g + a(vVar, cVar, a0Var, false);
        if (iA < 0) {
            return 0;
        }
        if (iAbs > iA) {
            i = i2 * iA;
        }
        this.u.a(-i);
        this.t.k = i;
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int c(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return k(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public androidx.recyclerview.widget.RecyclerView.p c() {
        return new androidx.recyclerview.widget.RecyclerView.p(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int d(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return i(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int e(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return j(a0Var);
    }

    android.view.View e(int i, int i2) {
        byte b2;
        int i3;
        int i4;
        E();
        if (i2 > i) {
            b2 = 1;
        } else {
            b2 = i2 < i ? (byte) -1 : (byte) 0;
        }
        if (b2 == 0) {
            return c(i);
        }
        if (this.u.d(c(i)) < this.u.f()) {
            i3 = 16644;
            i4 = 16388;
        } else {
            i3 = 4161;
            i4 = 4097;
        }
        return (this.s == 0 ? this.e : this.f).a(i, i2, i3, i4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void e(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        int i;
        int i2;
        int i3;
        int i4;
        int iA;
        int i5;
        android.view.View viewB;
        int iD;
        int iB;
        int i6 = -1;
        if (!(this.D == null && this.A == -1) && a0Var.a() == 0) {
            b(vVar);
            return;
        }
        androidx.recyclerview.widget.LinearLayoutManager.d dVar = this.D;
        if (dVar != null && dVar.a()) {
            this.A = this.D.f188a;
        }
        E();
        this.t.f185a = false;
        R();
        android.view.View viewG = g();
        if (!this.E.e || this.A != -1 || this.D != null) {
            this.E.b();
            androidx.recyclerview.widget.LinearLayoutManager.a aVar = this.E;
            aVar.d = this.x ^ this.y;
            b(vVar, a0Var, aVar);
            this.E.e = true;
        } else if (viewG != null && (this.u.d(viewG) >= this.u.b() || this.u.a(viewG) <= this.u.f())) {
            this.E.b(viewG, l(viewG));
        }
        androidx.recyclerview.widget.LinearLayoutManager.c cVar = this.t;
        cVar.f = cVar.k >= 0 ? 1 : -1;
        int[] iArr = this.H;
        iArr[0] = 0;
        iArr[1] = 0;
        a(a0Var, iArr);
        int iMax = java.lang.Math.max(0, this.H[0]) + this.u.f();
        int iMax2 = java.lang.Math.max(0, this.H[1]) + this.u.c();
        if (a0Var.d() && (i5 = this.A) != -1 && this.B != Integer.MIN_VALUE && (viewB = b(i5)) != null) {
            if (this.x) {
                iB = this.u.b() - this.u.a(viewB);
                iD = this.B;
            } else {
                iD = this.u.d(viewB) - this.u.f();
                iB = this.B;
            }
            int i7 = iB - iD;
            if (i7 > 0) {
                iMax += i7;
            } else {
                iMax2 -= i7;
            }
        }
        if (!this.E.d ? !this.x : this.x) {
            i6 = 1;
        }
        a(vVar, a0Var, this.E, i6);
        a(vVar);
        this.t.m = K();
        this.t.j = a0Var.d();
        this.t.i = 0;
        androidx.recyclerview.widget.LinearLayoutManager.a aVar2 = this.E;
        if (aVar2.d) {
            b(aVar2);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar2 = this.t;
            cVar2.h = iMax;
            a(vVar, cVar2, a0Var, false);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar3 = this.t;
            i2 = cVar3.f186b;
            int i8 = cVar3.d;
            int i9 = cVar3.f187c;
            if (i9 > 0) {
                iMax2 += i9;
            }
            a(this.E);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar4 = this.t;
            cVar4.h = iMax2;
            cVar4.d += cVar4.e;
            a(vVar, cVar4, a0Var, false);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar5 = this.t;
            i = cVar5.f186b;
            int i10 = cVar5.f187c;
            if (i10 > 0) {
                h(i8, i2);
                androidx.recyclerview.widget.LinearLayoutManager.c cVar6 = this.t;
                cVar6.h = i10;
                a(vVar, cVar6, a0Var, false);
                i2 = this.t.f186b;
            }
        } else {
            a(aVar2);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar7 = this.t;
            cVar7.h = iMax2;
            a(vVar, cVar7, a0Var, false);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar8 = this.t;
            i = cVar8.f186b;
            int i11 = cVar8.d;
            int i12 = cVar8.f187c;
            if (i12 > 0) {
                iMax += i12;
            }
            b(this.E);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar9 = this.t;
            cVar9.h = iMax;
            cVar9.d += cVar9.e;
            a(vVar, cVar9, a0Var, false);
            androidx.recyclerview.widget.LinearLayoutManager.c cVar10 = this.t;
            i2 = cVar10.f186b;
            int i13 = cVar10.f187c;
            if (i13 > 0) {
                g(i11, i);
                androidx.recyclerview.widget.LinearLayoutManager.c cVar11 = this.t;
                cVar11.h = i13;
                a(vVar, cVar11, a0Var, false);
                i = this.t.f186b;
            }
        }
        if (e() > 0) {
            if (this.x ^ this.y) {
                int iA2 = a(i, vVar, a0Var, true);
                i3 = i2 + iA2;
                i4 = i + iA2;
                iA = b(i3, vVar, a0Var, false);
            } else {
                int iB2 = b(i2, vVar, a0Var, true);
                i3 = i2 + iB2;
                i4 = i + iB2;
                iA = a(i4, vVar, a0Var, false);
            }
            i2 = i3 + iA;
            i = i4 + iA;
        }
        b(vVar, a0Var, i2, i);
        if (a0Var.d()) {
            this.E.b();
        } else {
            this.u.i();
        }
        this.v = this.y;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int f(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return k(a0Var);
    }

    public void f(int i, int i2) {
        this.A = i;
        this.B = i2;
        androidx.recyclerview.widget.LinearLayoutManager.d dVar = this.D;
        if (dVar != null) {
            dVar.b();
        }
        y();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void g(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        super.g(a0Var);
        this.D = null;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.E.b();
    }

    @java.lang.Deprecated
    protected int h(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (a0Var.c()) {
            return this.u.g();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void h(int i) {
        this.A = i;
        this.B = Integer.MIN_VALUE;
        androidx.recyclerview.widget.LinearLayoutManager.d dVar = this.D;
        if (dVar != null) {
            dVar.b();
        }
        y();
    }

    int i(int i) {
        if (i == 1) {
            return (this.s != 1 && I()) ? 1 : -1;
        }
        if (i == 2) {
            return (this.s != 1 && I()) ? -1 : 1;
        }
        if (i == 17) {
            return this.s == 0 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 33) {
            return this.s == 1 ? -1 : Integer.MIN_VALUE;
        }
        if (i != 66) {
            return (i == 130 && this.s == 1) ? 1 : Integer.MIN_VALUE;
        }
        return this.s == 0 ? 1 : Integer.MIN_VALUE;
    }

    public void j(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("invalid orientation:" + i);
        }
        a((java.lang.String) null);
        if (i != this.s || this.u == null) {
            androidx.recyclerview.widget.k kVarA = androidx.recyclerview.widget.k.a(this, i);
            this.u = kVarA;
            this.E.f179a = kVarA;
            this.s = i;
            y();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean u() {
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public android.os.Parcelable x() {
        if (this.D != null) {
            return new androidx.recyclerview.widget.LinearLayoutManager.d(this.D);
        }
        androidx.recyclerview.widget.LinearLayoutManager.d dVar = new androidx.recyclerview.widget.LinearLayoutManager.d();
        if (e() > 0) {
            E();
            boolean z = this.v ^ this.x;
            dVar.f190c = z;
            if (z) {
                android.view.View viewP = P();
                dVar.f189b = this.u.b() - this.u.a(viewP);
                dVar.f188a = l(viewP);
            } else {
                android.view.View viewQ = Q();
                dVar.f188a = l(viewQ);
                dVar.f189b = this.u.d(viewQ) - this.u.f();
            }
        } else {
            dVar.b();
        }
        return dVar;
    }
}
