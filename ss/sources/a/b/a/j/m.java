package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class m extends a.b.a.j.o {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    a.b.a.j.e f73c;
    a.b.a.j.m d;
    float e;
    a.b.a.j.m f;
    float g;
    private a.b.a.j.m i;
    int h = 0;
    private a.b.a.j.n j = null;
    private int k = 1;
    private a.b.a.j.n l = null;
    private int m = 1;

    public m(a.b.a.j.e eVar) {
        this.f73c = eVar;
    }

    java.lang.String a(int i) {
        if (i == 1) {
            return "DIRECT";
        }
        if (i == 2) {
            return "CENTER";
        }
        if (i == 3) {
            return "MATCH";
        }
        if (i == 4) {
            return "CHAIN";
        }
        return i == 5 ? "BARRIER" : "UNCONNECTED";
    }

    public void a(int i, a.b.a.j.m mVar, int i2) {
        this.h = i;
        this.d = mVar;
        this.e = i2;
        mVar.a(this);
    }

    void a(a.b.a.e eVar) {
        a.b.a.i iVarE = this.f73c.e();
        a.b.a.j.m mVar = this.f;
        if (mVar == null) {
            eVar.a(iVarE, (int) (this.g + 0.5f));
        } else {
            eVar.a(iVarE, eVar.a(mVar.f73c), (int) (this.g + 0.5f), 6);
        }
    }

    public void a(a.b.a.j.m mVar, float f) {
        if (this.f76b == 0 || !(this.f == mVar || this.g == f)) {
            this.f = mVar;
            this.g = f;
            if (this.f76b == 1) {
                b();
            }
            a();
        }
    }

    public void a(a.b.a.j.m mVar, int i) {
        this.d = mVar;
        this.e = i;
        mVar.a(this);
    }

    public void a(a.b.a.j.m mVar, int i, a.b.a.j.n nVar) {
        this.d = mVar;
        mVar.a(this);
        this.j = nVar;
        this.k = i;
        nVar.a(this);
    }

    public void b(int i) {
        this.h = i;
    }

    public void b(a.b.a.j.m mVar, float f) {
        this.i = mVar;
    }

    public void b(a.b.a.j.m mVar, int i, a.b.a.j.n nVar) {
        this.i = mVar;
        this.l = nVar;
        this.m = i;
    }

    @Override // a.b.a.j.o
    public void d() {
        super.d();
        this.d = null;
        this.e = 0.0f;
        this.j = null;
        this.k = 1;
        this.l = null;
        this.m = 1;
        this.f = null;
        this.g = 0.0f;
        this.i = null;
        this.h = 0;
    }

    @Override // a.b.a.j.o
    public void e() {
        a.b.a.j.m mVar;
        a.b.a.j.m mVar2;
        a.b.a.j.m mVar3;
        a.b.a.j.m mVar4;
        a.b.a.j.m mVar5;
        a.b.a.j.m mVar6;
        float f;
        a.b.a.j.m mVar7;
        float fS;
        float f2;
        a.b.a.j.m mVar8;
        float f3;
        boolean z = true;
        if (this.f76b == 1 || this.h == 4) {
            return;
        }
        a.b.a.j.n nVar = this.j;
        if (nVar != null) {
            if (nVar.f76b != 1) {
                return;
            } else {
                this.e = this.k * nVar.f74c;
            }
        }
        a.b.a.j.n nVar2 = this.l;
        if (nVar2 != null) {
            if (nVar2.f76b != 1) {
                return;
            } else {
                float f4 = nVar2.f74c;
            }
        }
        if (this.h == 1 && ((mVar8 = this.d) == null || mVar8.f76b == 1)) {
            a.b.a.j.m mVar9 = this.d;
            if (mVar9 == null) {
                this.f = this;
                f3 = this.e;
            } else {
                this.f = mVar9.f;
                f3 = mVar9.g + this.e;
            }
            this.g = f3;
            a();
            return;
        }
        if (this.h == 2 && (mVar4 = this.d) != null && mVar4.f76b == 1 && (mVar5 = this.i) != null && (mVar6 = mVar5.d) != null && mVar6.f76b == 1) {
            if (a.b.a.e.h() != null) {
                a.b.a.e.h().v++;
            }
            this.f = this.d.f;
            a.b.a.j.m mVar10 = this.i;
            mVar10.f = mVar10.d.f;
            a.b.a.j.e.d dVar = this.f73c.f49c;
            int i = 0;
            if (dVar != a.b.a.j.e.d.RIGHT && dVar != a.b.a.j.e.d.BOTTOM) {
                z = false;
            }
            if (z) {
                f = this.d.g;
                mVar7 = this.i.d;
            } else {
                f = this.i.d.g;
                mVar7 = this.d;
            }
            float f5 = f - mVar7.g;
            a.b.a.j.e eVar = this.f73c;
            a.b.a.j.e.d dVar2 = eVar.f49c;
            if (dVar2 == a.b.a.j.e.d.LEFT || dVar2 == a.b.a.j.e.d.RIGHT) {
                fS = f5 - this.f73c.f48b.s();
                f2 = this.f73c.f48b.V;
            } else {
                fS = f5 - eVar.f48b.i();
                f2 = this.f73c.f48b.W;
            }
            int iB = this.f73c.b();
            int iB2 = this.i.f73c.b();
            if (this.f73c.g() == this.i.f73c.g()) {
                f2 = 0.5f;
                iB2 = 0;
            } else {
                i = iB;
            }
            float f6 = i;
            float f7 = iB2;
            float f8 = (fS - f6) - f7;
            if (z) {
                a.b.a.j.m mVar11 = this.i;
                mVar11.g = mVar11.d.g + f7 + (f8 * f2);
                this.g = (this.d.g - f6) - (f8 * (1.0f - f2));
            } else {
                this.g = this.d.g + f6 + (f8 * f2);
                a.b.a.j.m mVar12 = this.i;
                mVar12.g = (mVar12.d.g - f7) - (f8 * (1.0f - f2));
            }
        } else {
            if (this.h != 3 || (mVar = this.d) == null || mVar.f76b != 1 || (mVar2 = this.i) == null || (mVar3 = mVar2.d) == null || mVar3.f76b != 1) {
                if (this.h == 5) {
                    this.f73c.f48b.G();
                    return;
                }
                return;
            }
            if (a.b.a.e.h() != null) {
                a.b.a.e.h().w++;
            }
            a.b.a.j.m mVar13 = this.d;
            this.f = mVar13.f;
            a.b.a.j.m mVar14 = this.i;
            a.b.a.j.m mVar15 = mVar14.d;
            mVar14.f = mVar15.f;
            this.g = mVar13.g + this.e;
            mVar14.g = mVar15.g + mVar14.e;
        }
        a();
        this.i.a();
    }

    public float f() {
        return this.g;
    }

    public void g() {
        a.b.a.j.e eVarG = this.f73c.g();
        if (eVarG == null) {
            return;
        }
        if (eVarG.g() == this.f73c) {
            this.h = 4;
            eVarG.d().h = 4;
        }
        int iB = this.f73c.b();
        a.b.a.j.e.d dVar = this.f73c.f49c;
        if (dVar == a.b.a.j.e.d.RIGHT || dVar == a.b.a.j.e.d.BOTTOM) {
            iB = -iB;
        }
        a(eVarG.d(), iB);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb;
        java.lang.String str;
        if (this.f76b != 1) {
            sb = new java.lang.StringBuilder();
            sb.append("{ ");
            sb.append(this.f73c);
            str = " UNRESOLVED} type: ";
        } else if (this.f == this) {
            sb = new java.lang.StringBuilder();
            sb.append("[");
            sb.append(this.f73c);
            sb.append(", RESOLVED: ");
            sb.append(this.g);
            str = "]  type: ";
        } else {
            sb = new java.lang.StringBuilder();
            sb.append("[");
            sb.append(this.f73c);
            sb.append(", RESOLVED: ");
            sb.append(this.f);
            sb.append(":");
            sb.append(this.g);
            str = "] type: ";
        }
        sb.append(str);
        sb.append(a(this.h));
        return sb.toString();
    }
}
