package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class g extends a.b.a.j.q {
    private a.b.a.j.p n0;
    int o0;
    int p0;
    int q0;
    int r0;
    private boolean l0 = false;
    protected a.b.a.e m0 = new a.b.a.e();
    int s0 = 0;
    int t0 = 0;
    a.b.a.j.d[] u0 = new a.b.a.j.d[4];
    a.b.a.j.d[] v0 = new a.b.a.j.d[4];
    public java.util.List<a.b.a.j.h> w0 = new java.util.ArrayList();
    public boolean x0 = false;
    public boolean y0 = false;
    public boolean z0 = false;
    public int A0 = 0;
    public int B0 = 0;
    private int C0 = 7;
    public boolean D0 = false;
    private boolean E0 = false;
    private boolean F0 = false;

    private void V() {
        this.s0 = 0;
        this.t0 = 0;
    }

    private void d(a.b.a.j.f fVar) {
        int i = this.s0 + 1;
        a.b.a.j.d[] dVarArr = this.v0;
        if (i >= dVarArr.length) {
            this.v0 = (a.b.a.j.d[]) java.util.Arrays.copyOf(dVarArr, dVarArr.length * 2);
        }
        this.v0[this.s0] = new a.b.a.j.d(fVar, 0, P());
        this.s0++;
    }

    private void e(a.b.a.j.f fVar) {
        int i = this.t0 + 1;
        a.b.a.j.d[] dVarArr = this.u0;
        if (i >= dVarArr.length) {
            this.u0 = (a.b.a.j.d[]) java.util.Arrays.copyOf(dVarArr, dVarArr.length * 2);
        }
        this.u0[this.t0] = new a.b.a.j.d(fVar, 1, P());
        this.t0++;
    }

    @Override // a.b.a.j.q, a.b.a.j.f
    public void D() {
        this.m0.f();
        this.o0 = 0;
        this.q0 = 0;
        this.p0 = 0;
        this.r0 = 0;
        this.w0.clear();
        this.D0 = false;
        super.D();
    }

    /* JADX WARN: Code duplicated, block: B:107:0x024c  */
    /* JADX WARN: Code duplicated, block: B:110:0x025f  */
    /* JADX WARN: Code duplicated, block: B:113:0x027c  */
    /* JADX WARN: Code duplicated, block: B:114:0x0289  */
    /* JADX WARN: Code duplicated, block: B:116:0x028e  */
    /* JADX WARN: Code duplicated, block: B:118:0x0297 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:124:0x02b5 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:128:0x02cb A[PHI: r0 r9
  0x02cb: PHI (r0v34 boolean) = (r0v33 boolean), (r0v36 boolean), (r0v36 boolean), (r0v36 boolean) binds: [B:115:0x028c, B:123:0x02b3, B:124:0x02b5, B:126:0x02bb] A[DONT_GENERATE, DONT_INLINE]
  0x02cb: PHI (r9v11 boolean) = (r9v10 boolean), (r9v12 boolean), (r9v12 boolean), (r9v12 boolean) binds: [B:115:0x028c, B:123:0x02b3, B:124:0x02b5, B:126:0x02bb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:163:0x0189 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:0x0182  */
    /* JADX WARN: Code duplicated, block: B:75:0x018b  */
    /* JADX WARN: Code duplicated, block: B:77:0x0193  */
    /* JADX WARN: Code duplicated, block: B:89:0x01d6  */
    /* JADX WARN: Type inference failed for: r8v23 */
    /* JADX WARN: Type inference failed for: r8v24, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v28 */
    @Override // a.b.a.j.q
    public void K() {
        boolean z;
        int i;
        int i2;
        a.b.a.j.f fVar;
        char c2;
        int i3;
        boolean z2;
        int iMax;
        int iMax2;
        ?? r8;
        boolean z3;
        int i4 = this.I;
        int i5 = this.J;
        int iMax3 = java.lang.Math.max(0, s());
        int iMax4 = java.lang.Math.max(0, i());
        this.E0 = false;
        this.F0 = false;
        if (this.D != null) {
            if (this.n0 == null) {
                this.n0 = new a.b.a.j.p(this);
            }
            this.n0.b(this);
            r(this.o0);
            s(this.p0);
            E();
            a(this.m0.d());
        } else {
            this.I = 0;
            this.J = 0;
        }
        int i6 = 32;
        if (this.C0 != 0) {
            if (!t(8)) {
                S();
            }
            if (!t(32)) {
                R();
            }
            this.m0.g = true;
        } else {
            this.m0.g = false;
        }
        a.b.a.j.f.b[] bVarArr = this.C;
        a.b.a.j.f.b bVar = bVarArr[1];
        a.b.a.j.f.b bVar2 = bVarArr[0];
        V();
        if (this.w0.size() == 0) {
            this.w0.clear();
            this.w0.add(0, new a.b.a.j.h(this.k0));
        }
        int size = this.w0.size();
        java.util.ArrayList<a.b.a.j.f> arrayList = this.k0;
        boolean z4 = j() == a.b.a.j.f.b.WRAP_CONTENT || q() == a.b.a.j.f.b.WRAP_CONTENT;
        boolean z5 = false;
        int i7 = 0;
        while (i7 < size && !this.D0) {
            if (this.w0.get(i7).d) {
                i = size;
            } else {
                if (t(i6)) {
                    this.k0 = (java.util.ArrayList) ((j() == a.b.a.j.f.b.FIXED && q() == a.b.a.j.f.b.FIXED) ? this.w0.get(i7).a() : this.w0.get(i7).f68a);
                }
                V();
                int size2 = this.k0.size();
                for (int i8 = 0; i8 < size2; i8++) {
                    a.b.a.j.f fVar2 = this.k0.get(i8);
                    if (fVar2 instanceof a.b.a.j.q) {
                        ((a.b.a.j.q) fVar2).K();
                    }
                }
                boolean z6 = z5;
                int i9 = 0;
                boolean zD = true;
                while (true) {
                    z = z6;
                    if (!zD) {
                        break;
                    }
                    int i10 = i9 + 1;
                    try {
                        this.m0.f();
                        V();
                        b(this.m0);
                        int i11 = 0;
                        while (i11 < size2) {
                            boolean z7 = zD;
                            try {
                                this.k0.get(i11).b(this.m0);
                                i11++;
                                zD = z7;
                            } catch (java.lang.Exception e) {
                                e = e;
                                zD = z7;
                                e.printStackTrace();
                                java.lang.System.out.println("EXCEPTION : " + e);
                                zD = zD;
                                if (zD) {
                                    a(this.m0, a.b.a.j.k.f72a);
                                } else {
                                    c(this.m0);
                                    i2 = 0;
                                    while (true) {
                                        if (i2 < size2) {
                                            fVar = this.k0.get(i2);
                                            if (fVar.C[0] != a.b.a.j.f.b.MATCH_CONSTRAINT) {
                                            }
                                            if (fVar.C[1] != a.b.a.j.f.b.MATCH_CONSTRAINT) {
                                            }
                                            i2++;
                                        }
                                    }
                                    if (z4) {
                                        i3 = i10;
                                        z2 = false;
                                    } else {
                                        i3 = i10;
                                        z2 = false;
                                    }
                                    iMax = java.lang.Math.max(this.R, s());
                                    if (iMax > s()) {
                                        o(iMax);
                                        this.C[0] = a.b.a.j.f.b.FIXED;
                                        z2 = true;
                                        z = true;
                                    }
                                    iMax2 = java.lang.Math.max(this.S, i());
                                    if (iMax2 > i()) {
                                        g(iMax2);
                                        r8 = 1;
                                        this.C[1] = a.b.a.j.f.b.FIXED;
                                        z2 = true;
                                        z3 = true;
                                    } else {
                                        r8 = 1;
                                        z3 = z;
                                    }
                                    if (z3) {
                                        zD = z2;
                                        z6 = z3;
                                    } else {
                                        if (this.C[0] == a.b.a.j.f.b.WRAP_CONTENT) {
                                            this.E0 = r8;
                                            this.C[0] = a.b.a.j.f.b.FIXED;
                                            o(iMax3);
                                            z2 = true;
                                            z3 = true;
                                        }
                                        if (this.C[r8] == a.b.a.j.f.b.WRAP_CONTENT) {
                                            zD = z2;
                                            z6 = z3;
                                        } else {
                                            zD = z2;
                                            z6 = z3;
                                        }
                                    }
                                    i9 = i3;
                                    size = size;
                                }
                                c2 = 2;
                                if (z4) {
                                    i3 = i10;
                                    z2 = false;
                                } else {
                                    i3 = i10;
                                    z2 = false;
                                }
                                iMax = java.lang.Math.max(this.R, s());
                                if (iMax > s()) {
                                    o(iMax);
                                    this.C[0] = a.b.a.j.f.b.FIXED;
                                    z2 = true;
                                    z = true;
                                }
                                iMax2 = java.lang.Math.max(this.S, i());
                                if (iMax2 > i()) {
                                    g(iMax2);
                                    r8 = 1;
                                    this.C[1] = a.b.a.j.f.b.FIXED;
                                    z2 = true;
                                    z3 = true;
                                } else {
                                    r8 = 1;
                                    z3 = z;
                                }
                                if (z3) {
                                    zD = z2;
                                    z6 = z3;
                                } else {
                                    if (this.C[0] == a.b.a.j.f.b.WRAP_CONTENT) {
                                        this.E0 = r8;
                                        this.C[0] = a.b.a.j.f.b.FIXED;
                                        o(iMax3);
                                        z2 = true;
                                        z3 = true;
                                    }
                                    if (this.C[r8] == a.b.a.j.f.b.WRAP_CONTENT) {
                                        zD = z2;
                                        z6 = z3;
                                    } else {
                                        zD = z2;
                                        z6 = z3;
                                    }
                                }
                                i9 = i3;
                                size = size;
                            }
                        }
                        zD = d(this.m0);
                        if (zD) {
                            try {
                                this.m0.e();
                            } catch (java.lang.Exception e2) {
                                e = e2;
                                e.printStackTrace();
                                java.lang.System.out.println("EXCEPTION : " + e);
                                zD = zD;
                            }
                        }
                    } catch (java.lang.Exception e3) {
                        e = e3;
                    }
                    if (zD) {
                        a(this.m0, a.b.a.j.k.f72a);
                    } else {
                        c(this.m0);
                        i2 = 0;
                        while (true) {
                            if (i2 < size2) {
                                fVar = this.k0.get(i2);
                                if (fVar.C[0] != a.b.a.j.f.b.MATCH_CONSTRAINT && fVar.s() < fVar.u()) {
                                    a.b.a.j.k.f72a[2] = true;
                                } else {
                                    if (fVar.C[1] != a.b.a.j.f.b.MATCH_CONSTRAINT && fVar.i() < fVar.t()) {
                                        c2 = 2;
                                        a.b.a.j.k.f72a[2] = true;
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                        if (z4 || i10 >= 8 || !a.b.a.j.k.f72a[c2]) {
                            i3 = i10;
                            z2 = false;
                        } else {
                            int i12 = 0;
                            int iMax5 = 0;
                            int iMax6 = 0;
                            while (i12 < size2) {
                                a.b.a.j.f fVar3 = this.k0.get(i12);
                                iMax5 = java.lang.Math.max(iMax5, fVar3.I + fVar3.s());
                                iMax6 = java.lang.Math.max(iMax6, fVar3.J + fVar3.i());
                                i12++;
                                i10 = i10;
                            }
                            i3 = i10;
                            int iMax7 = java.lang.Math.max(this.R, iMax5);
                            int iMax8 = java.lang.Math.max(this.S, iMax6);
                            if (bVar2 != a.b.a.j.f.b.WRAP_CONTENT || s() >= iMax7) {
                                z2 = false;
                            } else {
                                o(iMax7);
                                this.C[0] = a.b.a.j.f.b.WRAP_CONTENT;
                                z2 = true;
                                z = true;
                            }
                            if (bVar == a.b.a.j.f.b.WRAP_CONTENT && i() < iMax8) {
                                g(iMax8);
                                this.C[1] = a.b.a.j.f.b.WRAP_CONTENT;
                                z2 = true;
                                z = true;
                            }
                        }
                        iMax = java.lang.Math.max(this.R, s());
                        if (iMax > s()) {
                            o(iMax);
                            this.C[0] = a.b.a.j.f.b.FIXED;
                            z2 = true;
                            z = true;
                        }
                        iMax2 = java.lang.Math.max(this.S, i());
                        if (iMax2 > i()) {
                            g(iMax2);
                            r8 = 1;
                            this.C[1] = a.b.a.j.f.b.FIXED;
                            z2 = true;
                            z3 = true;
                        } else {
                            r8 = 1;
                            z3 = z;
                        }
                        if (z3) {
                            zD = z2;
                            z6 = z3;
                        } else {
                            if (this.C[0] == a.b.a.j.f.b.WRAP_CONTENT && iMax3 > 0 && s() > iMax3) {
                                this.E0 = r8;
                                this.C[0] = a.b.a.j.f.b.FIXED;
                                o(iMax3);
                                z2 = true;
                                z3 = true;
                            }
                            if (this.C[r8] == a.b.a.j.f.b.WRAP_CONTENT || iMax4 <= 0 || i() <= iMax4) {
                                zD = z2;
                                z6 = z3;
                            } else {
                                this.F0 = r8;
                                this.C[r8] = a.b.a.j.f.b.FIXED;
                                g(iMax4);
                                z6 = true;
                                zD = true;
                            }
                        }
                        i9 = i3;
                        size = size;
                    }
                    c2 = 2;
                    if (z4) {
                        i3 = i10;
                        z2 = false;
                    } else {
                        i3 = i10;
                        z2 = false;
                    }
                    iMax = java.lang.Math.max(this.R, s());
                    if (iMax > s()) {
                        o(iMax);
                        this.C[0] = a.b.a.j.f.b.FIXED;
                        z2 = true;
                        z = true;
                    }
                    iMax2 = java.lang.Math.max(this.S, i());
                    if (iMax2 > i()) {
                        g(iMax2);
                        r8 = 1;
                        this.C[1] = a.b.a.j.f.b.FIXED;
                        z2 = true;
                        z3 = true;
                    } else {
                        r8 = 1;
                        z3 = z;
                    }
                    if (z3) {
                        zD = z2;
                        z6 = z3;
                    } else {
                        if (this.C[0] == a.b.a.j.f.b.WRAP_CONTENT) {
                            this.E0 = r8;
                            this.C[0] = a.b.a.j.f.b.FIXED;
                            o(iMax3);
                            z2 = true;
                            z3 = true;
                        }
                        if (this.C[r8] == a.b.a.j.f.b.WRAP_CONTENT) {
                            zD = z2;
                            z6 = z3;
                        } else {
                            zD = z2;
                            z6 = z3;
                        }
                    }
                    i9 = i3;
                    size = size;
                }
                i = size;
                this.w0.get(i7).b();
                z5 = z;
            }
            i7++;
            size = i;
            i6 = 32;
        }
        this.k0 = arrayList;
        if (this.D != null) {
            int iMax9 = java.lang.Math.max(this.R, s());
            int iMax10 = java.lang.Math.max(this.S, i());
            this.n0.a(this);
            o(iMax9 + this.o0 + this.q0);
            g(iMax10 + this.p0 + this.r0);
        } else {
            this.I = i4;
            this.J = i5;
        }
        if (z5) {
            a.b.a.j.f.b[] bVarArr2 = this.C;
            bVarArr2[0] = bVar2;
            bVarArr2[1] = bVar;
        }
        a(this.m0.d());
        if (this == J()) {
            H();
        }
    }

    public int M() {
        return this.C0;
    }

    public boolean N() {
        return false;
    }

    public boolean O() {
        return this.F0;
    }

    public boolean P() {
        return this.l0;
    }

    public boolean Q() {
        return this.E0;
    }

    public void R() {
        if (!t(8)) {
            a(this.C0);
        }
        U();
    }

    public void S() {
        int size = this.k0.size();
        F();
        for (int i = 0; i < size; i++) {
            this.k0.get(i).F();
        }
    }

    public void T() {
        S();
        a(this.C0);
    }

    public void U() {
        a.b.a.j.m mVarD = a(a.b.a.j.e.d.LEFT).d();
        a.b.a.j.m mVarD2 = a(a.b.a.j.e.d.TOP).d();
        mVarD.a((a.b.a.j.m) null, 0.0f);
        mVarD2.a((a.b.a.j.m) null, 0.0f);
    }

    @Override // a.b.a.j.f
    public void a(int i) {
        super.a(i);
        int size = this.k0.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.k0.get(i2).a(i);
        }
    }

    public void a(a.b.a.e eVar, boolean[] zArr) {
        zArr[2] = false;
        c(eVar);
        int size = this.k0.size();
        for (int i = 0; i < size; i++) {
            a.b.a.j.f fVar = this.k0.get(i);
            fVar.c(eVar);
            if (fVar.C[0] == a.b.a.j.f.b.MATCH_CONSTRAINT && fVar.s() < fVar.u()) {
                zArr[2] = true;
            }
            if (fVar.C[1] == a.b.a.j.f.b.MATCH_CONSTRAINT && fVar.i() < fVar.t()) {
                zArr[2] = true;
            }
        }
    }

    void a(a.b.a.j.f fVar, int i) {
        if (i == 0) {
            d(fVar);
        } else if (i == 1) {
            e(fVar);
        }
    }

    public void c(boolean z) {
        this.l0 = z;
    }

    public boolean d(a.b.a.e eVar) {
        a(eVar);
        int size = this.k0.size();
        for (int i = 0; i < size; i++) {
            a.b.a.j.f fVar = this.k0.get(i);
            if (fVar instanceof a.b.a.j.g) {
                a.b.a.j.f.b[] bVarArr = fVar.C;
                a.b.a.j.f.b bVar = bVarArr[0];
                a.b.a.j.f.b bVar2 = bVarArr[1];
                if (bVar == a.b.a.j.f.b.WRAP_CONTENT) {
                    fVar.a(a.b.a.j.f.b.FIXED);
                }
                if (bVar2 == a.b.a.j.f.b.WRAP_CONTENT) {
                    fVar.b(a.b.a.j.f.b.FIXED);
                }
                fVar.a(eVar);
                if (bVar == a.b.a.j.f.b.WRAP_CONTENT) {
                    fVar.a(bVar);
                }
                if (bVar2 == a.b.a.j.f.b.WRAP_CONTENT) {
                    fVar.b(bVar2);
                }
            } else {
                a.b.a.j.k.a(this, eVar, fVar);
                fVar.a(eVar);
            }
        }
        if (this.s0 > 0) {
            a.b.a.j.c.a(this, eVar, 0);
        }
        if (this.t0 > 0) {
            a.b.a.j.c.a(this, eVar, 1);
        }
        return true;
    }

    public void f(int i, int i2) {
        a.b.a.j.n nVar;
        a.b.a.j.n nVar2;
        if (this.C[0] != a.b.a.j.f.b.WRAP_CONTENT && (nVar2 = this.f62c) != null) {
            nVar2.a(i);
        }
        if (this.C[1] == a.b.a.j.f.b.WRAP_CONTENT || (nVar = this.d) == null) {
            return;
        }
        nVar.a(i2);
    }

    public boolean t(int i) {
        return (this.C0 & i) == i;
    }

    public void u(int i) {
        this.C0 = i;
    }
}
