package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class i extends a.b.a.j.f {
    protected float k0 = -1.0f;
    protected int l0 = -1;
    protected int m0 = -1;
    private a.b.a.j.e n0 = this.t;
    private int o0 = 0;
    private boolean p0 = false;

    static /* synthetic */ class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f71a;

        static {
            int[] iArr = new int[a.b.a.j.e.d.values().length];
            f71a = iArr;
            try {
                iArr[a.b.a.j.e.d.LEFT.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError unused) {
            }
            try {
                f71a[a.b.a.j.e.d.RIGHT.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError unused2) {
            }
            try {
                f71a[a.b.a.j.e.d.TOP.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError unused3) {
            }
            try {
                f71a[a.b.a.j.e.d.BOTTOM.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError unused4) {
            }
            try {
                f71a[a.b.a.j.e.d.BASELINE.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError unused5) {
            }
            try {
                f71a[a.b.a.j.e.d.CENTER.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError unused6) {
            }
            try {
                f71a[a.b.a.j.e.d.CENTER_X.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError unused7) {
            }
            try {
                f71a[a.b.a.j.e.d.CENTER_Y.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError unused8) {
            }
            try {
                f71a[a.b.a.j.e.d.NONE.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError unused9) {
            }
        }
    }

    public i() {
        new a.b.a.j.l();
        this.B.clear();
        this.B.add(this.n0);
        int length = this.A.length;
        for (int i = 0; i < length; i++) {
            this.A[i] = this.n0;
        }
    }

    public int J() {
        return this.o0;
    }

    @Override // a.b.a.j.f
    public a.b.a.j.e a(a.b.a.j.e.d dVar) {
        switch (a.b.a.j.i.a.f71a[dVar.ordinal()]) {
            case 1:
            case 2:
                if (this.o0 == 1) {
                    return this.n0;
                }
                break;
            case 3:
            case 4:
                if (this.o0 == 0) {
                    return this.n0;
                }
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return null;
        }
        throw new java.lang.AssertionError(dVar.name());
    }

    @Override // a.b.a.j.f
    public void a(int i) {
        int i2;
        a.b.a.j.m mVarD;
        a.b.a.j.e eVar;
        a.b.a.j.m mVarD2;
        a.b.a.j.e eVar2;
        a.b.a.j.e eVar3;
        a.b.a.j.m mVarD3;
        int i3;
        a.b.a.j.f fVarK = k();
        if (fVarK == null) {
            return;
        }
        if (J() == 1) {
            this.t.d().a(1, fVarK.t.d(), 0);
            this.v.d().a(1, fVarK.t.d(), 0);
            if (this.l0 != -1) {
                this.s.d().a(1, fVarK.s.d(), this.l0);
                mVarD2 = this.u.d();
                eVar3 = fVarK.s;
                mVarD3 = eVar3.d();
                i3 = this.l0;
            } else {
                if (this.m0 == -1) {
                    if (this.k0 == -1.0f || fVarK.j() != a.b.a.j.f.b.FIXED) {
                        return;
                    }
                    i2 = (int) (fVarK.E * this.k0);
                    this.s.d().a(1, fVarK.s.d(), i2);
                    mVarD = this.u.d();
                    eVar = fVarK.s;
                    mVarD.a(1, eVar.d(), i2);
                    return;
                }
                this.s.d().a(1, fVarK.u.d(), -this.m0);
                mVarD2 = this.u.d();
                eVar2 = fVarK.u;
                mVarD3 = eVar2.d();
                i3 = -this.m0;
            }
        } else {
            this.s.d().a(1, fVarK.s.d(), 0);
            this.u.d().a(1, fVarK.s.d(), 0);
            if (this.l0 != -1) {
                this.t.d().a(1, fVarK.t.d(), this.l0);
                mVarD2 = this.v.d();
                eVar3 = fVarK.t;
                mVarD3 = eVar3.d();
                i3 = this.l0;
            } else {
                if (this.m0 == -1) {
                    if (this.k0 == -1.0f || fVarK.q() != a.b.a.j.f.b.FIXED) {
                        return;
                    }
                    i2 = (int) (fVarK.F * this.k0);
                    this.t.d().a(1, fVarK.t.d(), i2);
                    mVarD = this.v.d();
                    eVar = fVarK.t;
                    mVarD.a(1, eVar.d(), i2);
                    return;
                }
                this.t.d().a(1, fVarK.v.d(), -this.m0);
                mVarD2 = this.v.d();
                eVar2 = fVarK.v;
                mVarD3 = eVar2.d();
                i3 = -this.m0;
            }
        }
        mVarD2.a(1, mVarD3, i3);
    }

    @Override // a.b.a.j.f
    public void a(a.b.a.e eVar) {
        a.b.a.j.g gVar = (a.b.a.j.g) k();
        if (gVar == null) {
            return;
        }
        a.b.a.j.e eVarA = gVar.a(a.b.a.j.e.d.LEFT);
        a.b.a.j.e eVarA2 = gVar.a(a.b.a.j.e.d.RIGHT);
        a.b.a.j.f fVar = this.D;
        boolean z = fVar != null && fVar.C[0] == a.b.a.j.f.b.WRAP_CONTENT;
        if (this.o0 == 0) {
            eVarA = gVar.a(a.b.a.j.e.d.TOP);
            eVarA2 = gVar.a(a.b.a.j.e.d.BOTTOM);
            a.b.a.j.f fVar2 = this.D;
            z = fVar2 != null && fVar2.C[1] == a.b.a.j.f.b.WRAP_CONTENT;
        }
        if (this.l0 != -1) {
            a.b.a.i iVarA = eVar.a(this.n0);
            eVar.a(iVarA, eVar.a(eVarA), this.l0, 6);
            if (z) {
                eVar.b(eVar.a(eVarA2), iVarA, 0, 5);
                return;
            }
            return;
        }
        if (this.m0 == -1) {
            if (this.k0 != -1.0f) {
                eVar.a(a.b.a.e.a(eVar, eVar.a(this.n0), eVar.a(eVarA), eVar.a(eVarA2), this.k0, this.p0));
                return;
            }
            return;
        }
        a.b.a.i iVarA2 = eVar.a(this.n0);
        a.b.a.i iVarA3 = eVar.a(eVarA2);
        eVar.a(iVarA2, iVarA3, -this.m0, 6);
        if (z) {
            eVar.b(iVarA2, eVar.a(eVarA), 0, 5);
            eVar.b(iVarA3, iVarA2, 0, 5);
        }
    }

    @Override // a.b.a.j.f
    public boolean a() {
        return true;
    }

    @Override // a.b.a.j.f
    public java.util.ArrayList<a.b.a.j.e> b() {
        return this.B;
    }

    @Override // a.b.a.j.f
    public void c(a.b.a.e eVar) {
        if (k() == null) {
            return;
        }
        int iB = eVar.b(this.n0);
        if (this.o0 == 1) {
            r(iB);
            s(0);
            g(k().i());
            o(0);
            return;
        }
        r(0);
        s(iB);
        o(k().s());
        g(0);
    }

    public void e(float f) {
        if (f > -1.0f) {
            this.k0 = f;
            this.l0 = -1;
            this.m0 = -1;
        }
    }

    public void t(int i) {
        if (i > -1) {
            this.k0 = -1.0f;
            this.l0 = i;
            this.m0 = -1;
        }
    }

    public void u(int i) {
        if (i > -1) {
            this.k0 = -1.0f;
            this.l0 = -1;
            this.m0 = i;
        }
    }

    public void v(int i) {
        if (this.o0 == i) {
            return;
        }
        this.o0 = i;
        this.B.clear();
        this.n0 = this.o0 == 1 ? this.s : this.t;
        this.B.add(this.n0);
        int length = this.A.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.A[i2] = this.n0;
        }
    }
}
