package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class b extends a.b.a.j.j {
    private int m0 = 0;
    private java.util.ArrayList<a.b.a.j.m> n0 = new java.util.ArrayList<>(4);
    private boolean o0 = true;

    @Override // a.b.a.j.f
    public void F() {
        super.F();
        this.n0.clear();
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0030  */
    /* JADX WARN: Code duplicated, block: B:19:0x003d  */
    /* JADX WARN: Code duplicated, block: B:26:0x004b  */
    /* JADX WARN: Code duplicated, block: B:28:0x0051 A[PHI: r9
  0x0051: PHI (r9v4 float) = (r9v3 float), (r9v5 float) binds: [B:27:0x004f, B:24:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:32:0x005e  */
    /* JADX WARN: Code duplicated, block: B:35:0x0074 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:36:0x0076 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x0078 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:38:0x007a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:39:0x007b  */
    /* JADX WARN: Code duplicated, block: B:40:0x007e  */
    /* JADX WARN: Code duplicated, block: B:41:0x0081  */
    /* JADX WARN: Code duplicated, block: B:42:0x0084  */
    /* JADX WARN: Code duplicated, block: B:45:0x003c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:0x0055 A[SYNTHETIC] */
    @Override // a.b.a.j.f
    public void G() {
        a.b.a.j.e eVar;
        a.b.a.j.m mVarD;
        int size;
        a.b.a.j.m mVar;
        int i;
        int i2;
        a.b.a.j.e eVar2;
        a.b.a.j.m mVar2;
        int i3;
        float f;
        a.b.a.j.e eVar3;
        int i4 = this.m0;
        float f2 = Float.MAX_VALUE;
        if (i4 != 0) {
            if (i4 == 1) {
                eVar3 = this.u;
            } else if (i4 == 2) {
                eVar = this.t;
            } else if (i4 != 3) {
                return;
            } else {
                eVar3 = this.v;
            }
            mVarD = eVar3.d();
            f2 = 0.0f;
            size = this.n0.size();
            mVar = null;
            for (i = 0; i < size; i++) {
                mVar2 = this.n0.get(i);
                if (mVar2.f76b != 1) {
                    return;
                }
                i3 = this.m0;
                if (i3 != 0 || i3 == 2) {
                    f = mVar2.g;
                    if (f < f2) {
                        mVar = mVar2.f;
                        f2 = f;
                    }
                } else {
                    f = mVar2.g;
                    if (f > f2) {
                        mVar = mVar2.f;
                        f2 = f;
                    }
                }
            }
            if (a.b.a.e.h() != null) {
                a.b.a.e.h().y++;
            }
            mVarD.f = mVar;
            mVarD.g = f2;
            mVarD.a();
            i2 = this.m0;
            if (i2 != 0) {
                eVar2 = this.u;
            } else if (i2 != 1) {
                eVar2 = this.s;
            } else if (i2 != 2) {
                eVar2 = this.v;
            } else if (i2 != 3) {
                return;
            } else {
                eVar2 = this.t;
            }
            eVar2.d().a(mVar, f2);
        }
        eVar = this.s;
        mVarD = eVar.d();
        size = this.n0.size();
        mVar = null;
        while (i < size) {
            mVar2 = this.n0.get(i);
            if (mVar2.f76b != 1) {
                return;
            }
            i3 = this.m0;
            if (i3 != 0) {
                f = mVar2.g;
                if (f < f2) {
                    mVar = mVar2.f;
                    f2 = f;
                }
            } else {
                f = mVar2.g;
                if (f < f2) {
                    mVar = mVar2.f;
                    f2 = f;
                }
            }
        }
        if (a.b.a.e.h() != null) {
            a.b.a.e.h().y++;
        }
        mVarD.f = mVar;
        mVarD.g = f2;
        mVarD.a();
        i2 = this.m0;
        if (i2 != 0) {
            eVar2 = this.u;
        } else if (i2 != 1) {
            eVar2 = this.s;
        } else if (i2 != 2) {
            eVar2 = this.v;
        } else if (i2 != 3) {
            return;
        } else {
            eVar2 = this.t;
        }
        eVar2.d().a(mVar, f2);
    }

    /* JADX WARN: Code duplicated, block: B:44:0x008c  */
    /* JADX WARN: Code duplicated, block: B:49:0x0094 A[SYNTHETIC] */
    @Override // a.b.a.j.f
    public void a(int i) {
        a.b.a.j.e eVar;
        a.b.a.j.e eVar2;
        a.b.a.j.e eVar3;
        a.b.a.j.m mVarD;
        a.b.a.j.f fVar = this.D;
        if (fVar != null && ((a.b.a.j.g) fVar).t(2)) {
            int i2 = this.m0;
            if (i2 == 0) {
                eVar = this.s;
            } else if (i2 == 1) {
                eVar = this.u;
            } else if (i2 == 2) {
                eVar = this.t;
            } else if (i2 != 3) {
                return;
            } else {
                eVar = this.v;
            }
            a.b.a.j.m mVarD2 = eVar.d();
            mVarD2.b(5);
            int i3 = this.m0;
            if (i3 == 0 || i3 == 1) {
                this.t.d().a((a.b.a.j.m) null, 0.0f);
                eVar2 = this.v;
            } else {
                this.s.d().a((a.b.a.j.m) null, 0.0f);
                eVar2 = this.u;
            }
            eVar2.d().a((a.b.a.j.m) null, 0.0f);
            this.n0.clear();
            for (int i4 = 0; i4 < this.l0; i4++) {
                a.b.a.j.f fVar2 = this.k0[i4];
                if (this.o0 || fVar2.a()) {
                    int i5 = this.m0;
                    if (i5 == 0) {
                        eVar3 = fVar2.s;
                    } else if (i5 == 1) {
                        eVar3 = fVar2.u;
                    } else if (i5 != 2) {
                        if (i5 != 3) {
                            mVarD = null;
                        } else {
                            eVar3 = fVar2.v;
                        }
                        if (mVarD != null) {
                            this.n0.add(mVarD);
                            mVarD.a(mVarD2);
                        }
                    } else {
                        eVar3 = fVar2.t;
                    }
                    mVarD = eVar3.d();
                    if (mVarD != null) {
                        this.n0.add(mVarD);
                        mVarD.a(mVarD2);
                    }
                }
            }
        }
    }

    @Override // a.b.a.j.f
    public void a(a.b.a.e eVar) {
        a.b.a.j.e[] eVarArr;
        boolean z;
        a.b.a.i iVar;
        a.b.a.j.e eVar2;
        int i;
        int i2;
        a.b.a.j.e[] eVarArr2 = this.A;
        eVarArr2[0] = this.s;
        eVarArr2[2] = this.t;
        eVarArr2[1] = this.u;
        eVarArr2[3] = this.v;
        int i3 = 0;
        while (true) {
            eVarArr = this.A;
            if (i3 >= eVarArr.length) {
                break;
            }
            eVarArr[i3].i = eVar.a(eVarArr[i3]);
            i3++;
        }
        int i4 = this.m0;
        if (i4 < 0 || i4 >= 4) {
            return;
        }
        a.b.a.j.e eVar3 = eVarArr[i4];
        int i5 = 0;
        while (true) {
            if (i5 >= this.l0) {
                z = false;
                break;
            }
            a.b.a.j.f fVar = this.k0[i5];
            if ((this.o0 || fVar.a()) && ((((i = this.m0) == 0 || i == 1) && fVar.j() == a.b.a.j.f.b.MATCH_CONSTRAINT) || (((i2 = this.m0) == 2 || i2 == 3) && fVar.q() == a.b.a.j.f.b.MATCH_CONSTRAINT))) {
                z = true;
                break;
            }
            i5++;
        }
        int i6 = this.m0;
        if (i6 == 0 || i6 == 1 ? k().j() == a.b.a.j.f.b.WRAP_CONTENT : k().q() == a.b.a.j.f.b.WRAP_CONTENT) {
            z = false;
        }
        for (int i7 = 0; i7 < this.l0; i7++) {
            a.b.a.j.f fVar2 = this.k0[i7];
            if (this.o0 || fVar2.a()) {
                a.b.a.i iVarA = eVar.a(fVar2.A[this.m0]);
                a.b.a.j.e[] eVarArr3 = fVar2.A;
                int i8 = this.m0;
                eVarArr3[i8].i = iVarA;
                if (i8 == 0 || i8 == 2) {
                    eVar.b(eVar3.i, iVarA, z);
                } else {
                    eVar.a(eVar3.i, iVarA, z);
                }
            }
        }
        int i9 = this.m0;
        if (i9 == 0) {
            eVar.a(this.u.i, this.s.i, 0, 6);
            if (z) {
                return;
            }
            iVar = this.s.i;
            eVar2 = this.D.u;
        } else if (i9 == 1) {
            eVar.a(this.s.i, this.u.i, 0, 6);
            if (z) {
                return;
            }
            iVar = this.s.i;
            eVar2 = this.D.s;
        } else if (i9 == 2) {
            eVar.a(this.v.i, this.t.i, 0, 6);
            if (z) {
                return;
            }
            iVar = this.t.i;
            eVar2 = this.D.v;
        } else {
            if (i9 != 3) {
                return;
            }
            eVar.a(this.t.i, this.v.i, 0, 6);
            if (z) {
                return;
            }
            iVar = this.t.i;
            eVar2 = this.D.t;
        }
        eVar.a(iVar, eVar2.i, 0, 5);
    }

    @Override // a.b.a.j.f
    public boolean a() {
        return true;
    }

    public void c(boolean z) {
        this.o0 = z;
    }

    public void t(int i) {
        this.m0 = i;
    }
}
