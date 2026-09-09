package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class f {
    public static float j0 = 0.5f;
    protected a.b.a.j.e[] A;
    protected java.util.ArrayList<a.b.a.j.e> B;
    protected a.b.a.j.f.b[] C;
    a.b.a.j.f D;
    int E;
    int F;
    protected float G;
    protected int H;
    protected int I;
    protected int J;
    int K;
    int L;
    private int M;
    private int N;
    protected int O;
    protected int P;
    int Q;
    protected int R;
    protected int S;
    private int T;
    private int U;
    float V;
    float W;
    private java.lang.Object X;
    private int Y;
    private java.lang.String Z;
    private java.lang.String a0;
    boolean b0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    a.b.a.j.n f62c;
    boolean c0;
    a.b.a.j.n d;
    boolean d0;
    int e0;
    int f0;
    float[] g0;
    protected a.b.a.j.f[] h0;
    protected a.b.a.j.f[] i0;
    a.b.a.j.e z;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f60a = -1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f61b = -1;
    int e = 0;
    int f = 0;
    int[] g = new int[2];
    int h = 0;
    int i = 0;
    float j = 1.0f;
    int k = 0;
    int l = 0;
    float m = 1.0f;
    int n = -1;
    float o = 1.0f;
    a.b.a.j.h p = null;
    private int[] q = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    private float r = 0.0f;
    a.b.a.j.e s = new a.b.a.j.e(this, a.b.a.j.e.d.LEFT);
    a.b.a.j.e t = new a.b.a.j.e(this, a.b.a.j.e.d.TOP);
    a.b.a.j.e u = new a.b.a.j.e(this, a.b.a.j.e.d.RIGHT);
    a.b.a.j.e v = new a.b.a.j.e(this, a.b.a.j.e.d.BOTTOM);
    a.b.a.j.e w = new a.b.a.j.e(this, a.b.a.j.e.d.BASELINE);
    a.b.a.j.e x = new a.b.a.j.e(this, a.b.a.j.e.d.CENTER_X);
    a.b.a.j.e y = new a.b.a.j.e(this, a.b.a.j.e.d.CENTER_Y);

    static /* synthetic */ class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f63a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f64b;

        static {
            int[] iArr = new int[a.b.a.j.f.b.values().length];
            f64b = iArr;
            try {
                iArr[a.b.a.j.f.b.FIXED.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError unused) {
            }
            try {
                f64b[a.b.a.j.f.b.WRAP_CONTENT.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError unused2) {
            }
            try {
                f64b[a.b.a.j.f.b.MATCH_PARENT.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError unused3) {
            }
            try {
                f64b[a.b.a.j.f.b.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[a.b.a.j.e.d.values().length];
            f63a = iArr2;
            try {
                iArr2[a.b.a.j.e.d.LEFT.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError unused5) {
            }
            try {
                f63a[a.b.a.j.e.d.TOP.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError unused6) {
            }
            try {
                f63a[a.b.a.j.e.d.RIGHT.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError unused7) {
            }
            try {
                f63a[a.b.a.j.e.d.BOTTOM.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError unused8) {
            }
            try {
                f63a[a.b.a.j.e.d.BASELINE.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError unused9) {
            }
            try {
                f63a[a.b.a.j.e.d.CENTER.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError unused10) {
            }
            try {
                f63a[a.b.a.j.e.d.CENTER_X.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError unused11) {
            }
            try {
                f63a[a.b.a.j.e.d.CENTER_Y.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError unused12) {
            }
            try {
                f63a[a.b.a.j.e.d.NONE.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError unused13) {
            }
        }
    }

    public enum b {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public f() {
        a.b.a.j.e eVar = new a.b.a.j.e(this, a.b.a.j.e.d.CENTER);
        this.z = eVar;
        this.A = new a.b.a.j.e[]{this.s, this.u, this.t, this.v, this.w, eVar};
        this.B = new java.util.ArrayList<>();
        a.b.a.j.f.b bVar = a.b.a.j.f.b.FIXED;
        this.C = new a.b.a.j.f.b[]{bVar, bVar};
        this.D = null;
        this.E = 0;
        this.F = 0;
        this.G = 0.0f;
        this.H = -1;
        this.I = 0;
        this.J = 0;
        this.K = 0;
        this.L = 0;
        this.M = 0;
        this.N = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        float f = j0;
        this.V = f;
        this.W = f;
        this.Y = 0;
        this.Z = null;
        this.a0 = null;
        this.b0 = false;
        this.c0 = false;
        this.d0 = false;
        this.e0 = 0;
        this.f0 = 0;
        this.g0 = new float[]{-1.0f, -1.0f};
        this.h0 = new a.b.a.j.f[]{null, null};
        this.i0 = new a.b.a.j.f[]{null, null};
        J();
    }

    private void J() {
        this.B.add(this.s);
        this.B.add(this.t);
        this.B.add(this.u);
        this.B.add(this.v);
        this.B.add(this.x);
        this.B.add(this.y);
        this.B.add(this.z);
        this.B.add(this.w);
    }

    /* JADX WARN: Code duplicated, block: B:104:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:163:0x0282  */
    /* JADX WARN: Code duplicated, block: B:165:0x02b4 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:167:0x02bd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:168:0x02bf A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:170:0x02c7  */
    /* JADX WARN: Code duplicated, block: B:173:0x02d6  */
    /* JADX WARN: Code duplicated, block: B:175:0x02da A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:176:0x02dc A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:179:0x02e7 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:180:0x02e9 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:184:0x02f7  */
    /* JADX WARN: Code duplicated, block: B:98:0x01c2 A[ADDED_TO_REGION] */
    private void a(a.b.a.e eVar, boolean z, a.b.a.i iVar, a.b.a.i iVar2, a.b.a.j.f.b bVar, boolean z2, a.b.a.j.e eVar2, a.b.a.j.e eVar3, int i, int i2, int i3, int i4, float f, boolean z3, boolean z4, int i5, int i6, int i7, float f2, boolean z5) {
        boolean z6;
        int iMin;
        int i8;
        int i9;
        int i10;
        boolean z7;
        a.b.a.j.f fVar;
        a.b.a.i iVar3;
        a.b.a.j.e.d dVar;
        boolean z8;
        boolean z9;
        int i11;
        a.b.a.i iVar4;
        boolean z10;
        boolean z11;
        int i12;
        a.b.a.i iVar5;
        a.b.a.i iVar6;
        a.b.a.i iVar7;
        boolean z12;
        boolean z13;
        int i13;
        int i14;
        int i15;
        boolean z14;
        int i16;
        boolean z15;
        a.b.a.i iVarA = eVar.a(eVar2);
        a.b.a.i iVarA2 = eVar.a(eVar3);
        a.b.a.i iVarA3 = eVar.a(eVar2.g());
        a.b.a.i iVarA4 = eVar.a(eVar3.g());
        if (eVar.g && eVar2.d().f76b == 1 && eVar3.d().f76b == 1) {
            if (a.b.a.e.h() != null) {
                a.b.a.e.h().r++;
            }
            eVar2.d().a(eVar);
            eVar3.d().a(eVar);
            if (z4 || !z) {
                return;
            }
            eVar.b(iVar2, iVarA2, 0, 6);
            return;
        }
        if (a.b.a.e.h() != null) {
            a.b.a.e.h().z++;
        }
        boolean zI = eVar2.i();
        boolean zI2 = eVar3.i();
        boolean zI3 = this.z.i();
        int i17 = zI ? 1 : 0;
        if (zI2) {
            i17++;
        }
        if (zI3) {
            i17++;
        }
        int i18 = i17;
        int i19 = z3 ? 3 : i5;
        int i20 = a.b.a.j.f.a.f64b[bVar.ordinal()];
        boolean z16 = (i20 == 1 || i20 == 2 || i20 == 3 || i20 != 4 || i19 == 4) ? false : true;
        if (this.Y == 8) {
            iMin = 0;
            z6 = false;
        } else {
            z6 = z16;
            iMin = i2;
        }
        if (z5) {
            if (!zI && !zI2 && !zI3) {
                eVar.a(iVarA, i);
            } else if (zI && !zI2) {
                i8 = 6;
                eVar.a(iVarA, iVarA3, eVar2.b(), 6);
            }
            i8 = 6;
        } else {
            i8 = 6;
        }
        if (z6) {
            i9 = i6;
            i10 = i7;
            if (i9 == -2) {
                i9 = iMin;
            }
            if (i10 == -2) {
                i10 = iMin;
            }
            if (i9 > 0) {
                eVar.b(iVarA2, iVarA, i9, 6);
                iMin = java.lang.Math.max(iMin, i9);
            }
            if (i10 > 0) {
                eVar.c(iVarA2, iVarA, i10, 6);
                iMin = java.lang.Math.min(iMin, i10);
            }
            if (i19 == 1) {
                if (z) {
                    eVar.a(iVarA2, iVarA, iMin, 6);
                    z7 = z6;
                } else {
                    z7 = z6;
                    if (z4) {
                        eVar.a(iVarA2, iVarA, iMin, 4);
                    } else {
                        eVar.a(iVarA2, iVarA, iMin, 1);
                    }
                }
                z8 = z7;
                if (z8 || i18 == 2 || z3) {
                    z9 = z8;
                } else {
                    int iMax = java.lang.Math.max(i9, iMin);
                    if (i10 > 0) {
                        iMax = java.lang.Math.min(i10, iMax);
                    }
                    eVar.a(iVarA2, iVarA, iMax, 6);
                    z9 = false;
                }
            } else {
                z7 = z6;
                if (i19 == 2) {
                    if (eVar2.h() == a.b.a.j.e.d.TOP || eVar2.h() == a.b.a.j.e.d.BOTTOM) {
                        a.b.a.i iVarA5 = eVar.a(this.D.a(a.b.a.j.e.d.TOP));
                        fVar = this.D;
                        iVar3 = iVarA5;
                        dVar = a.b.a.j.e.d.BOTTOM;
                    } else {
                        a.b.a.i iVarA6 = eVar.a(this.D.a(a.b.a.j.e.d.LEFT));
                        fVar = this.D;
                        iVar3 = iVarA6;
                        dVar = a.b.a.j.e.d.RIGHT;
                    }
                    a.b.a.b bVarB = eVar.b();
                    iVarA3 = iVarA3;
                    iMin = iMin;
                    i19 = i19;
                    i18 = i18;
                    i10 = i10;
                    iVarA4 = iVarA4;
                    bVarB.a(iVarA2, iVarA, eVar.a(fVar.a(dVar)), iVar3, f2);
                    eVar.a(bVarB);
                    z8 = false;
                }
                if (z8) {
                    z9 = z8;
                } else {
                    z9 = z8;
                }
            }
            z8 = z7;
            if (z8) {
                z9 = z8;
            } else {
                z9 = z8;
            }
        } else {
            if (z2) {
                eVar.a(iVarA2, iVarA, 0, 3);
                if (i3 > 0) {
                    eVar.b(iVarA2, iVarA, i3, 6);
                }
                if (i4 < Integer.MAX_VALUE) {
                    eVar.c(iVarA2, iVarA, i4, 6);
                }
            } else {
                eVar.a(iVarA2, iVarA, iMin, i8);
            }
            i9 = i6;
            i19 = i19;
            i18 = i18;
            iVarA4 = iVarA4;
            iVarA3 = iVarA3;
            z9 = z6;
            i10 = i7;
        }
        if (!z5 || z4) {
            if (i18 >= 2 || !z) {
                return;
            }
            eVar.b(iVarA, iVar, 0, 6);
            eVar.b(iVar2, iVarA2, 0, 6);
            return;
        }
        if (zI || zI2 || zI3) {
            i11 = 0;
            if (!zI || zI2) {
                if (!zI && zI2) {
                    eVar.a(iVarA2, iVarA4, -eVar3.b(), 6);
                    if (z) {
                        eVar.b(iVarA, iVar, 0, 5);
                    }
                } else if (zI && zI2) {
                    a.b.a.i iVar8 = iVarA4;
                    if (z9) {
                        if (z && i3 == 0) {
                            eVar.b(iVarA2, iVarA, 0, 6);
                        }
                        if (i19 == 0) {
                            if (i10 > 0 || i9 > 0) {
                                i16 = 4;
                                z15 = true;
                            } else {
                                i16 = 6;
                                z15 = false;
                            }
                            iVar4 = iVarA3;
                            eVar.a(iVarA, iVar4, eVar2.b(), i16);
                            eVar.a(iVarA2, iVar8, -eVar3.b(), i16);
                            z10 = i10 > 0 || i9 > 0;
                            z11 = z15;
                            i12 = 5;
                        } else {
                            int i21 = i19;
                            iVar4 = iVarA3;
                            if (i21 == 1) {
                                z10 = true;
                                z11 = true;
                                i12 = 6;
                            } else {
                                if (i21 == 3) {
                                    int i22 = (z3 || this.n == -1 || i10 > 0) ? 4 : 6;
                                    eVar.a(iVarA, iVar4, eVar2.b(), i22);
                                    eVar.a(iVarA2, iVar8, -eVar3.b(), i22);
                                    z10 = true;
                                    z11 = true;
                                } else {
                                    z10 = false;
                                }
                                i12 = 5;
                                if (z10) {
                                    iVar6 = iVar8;
                                    iVar5 = iVar4;
                                    iVar7 = iVarA2;
                                    eVar.a(iVarA, iVar4, eVar2.b(), f, iVar8, iVarA2, eVar3.b(), i12);
                                    z14 = eVar2.d.f48b instanceof a.b.a.j.b;
                                    boolean z17 = eVar3.d.f48b instanceof a.b.a.j.b;
                                    if (!z14 && !z17) {
                                        z12 = z;
                                        i13 = 6;
                                        i14 = 5;
                                        z13 = true;
                                    } else if (z14 && z17) {
                                        z13 = z;
                                        i13 = 5;
                                        i14 = 6;
                                        z12 = true;
                                    }
                                    if (z11) {
                                        i13 = 6;
                                        i14 = 6;
                                    }
                                    if ((z9 && z12) || z11) {
                                        eVar.b(iVarA, iVar5, eVar2.b(), i14);
                                    }
                                    if ((z9 && z13) || z11) {
                                        eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                                    }
                                    i15 = 0;
                                    if (z) {
                                        eVar.b(iVarA, iVar, 0, 6);
                                    }
                                } else {
                                    iVar5 = iVar4;
                                    iVar6 = iVar8;
                                    iVar7 = iVarA2;
                                }
                                z12 = z;
                                z13 = z12;
                                i13 = 5;
                                i14 = 5;
                                if (z11) {
                                    i13 = 6;
                                    i14 = 6;
                                }
                                if (z9) {
                                    eVar.b(iVarA, iVar5, eVar2.b(), i14);
                                } else {
                                    eVar.b(iVarA, iVar5, eVar2.b(), i14);
                                }
                                if (z9) {
                                    eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                                } else {
                                    eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                                }
                                i15 = 0;
                                if (z) {
                                    eVar.b(iVarA, iVar, 0, 6);
                                }
                            }
                        }
                        if (z10) {
                            iVar6 = iVar8;
                            iVar5 = iVar4;
                            iVar7 = iVarA2;
                            eVar.a(iVarA, iVar4, eVar2.b(), f, iVar8, iVarA2, eVar3.b(), i12);
                            z14 = eVar2.d.f48b instanceof a.b.a.j.b;
                            boolean z18 = eVar3.d.f48b instanceof a.b.a.j.b;
                            if (!z14) {
                                if (z14) {
                                }
                            } else if (z14) {
                            }
                            if (z11) {
                                i13 = 6;
                                i14 = 6;
                            }
                            if (z9) {
                                eVar.b(iVarA, iVar5, eVar2.b(), i14);
                            } else {
                                eVar.b(iVarA, iVar5, eVar2.b(), i14);
                            }
                            if (z9) {
                                eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                            } else {
                                eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                            }
                            i15 = 0;
                            if (z) {
                                eVar.b(iVarA, iVar, 0, 6);
                            }
                        } else {
                            iVar5 = iVar4;
                            iVar6 = iVar8;
                            iVar7 = iVarA2;
                        }
                        z12 = z;
                        z13 = z12;
                        i13 = 5;
                        i14 = 5;
                        if (z11) {
                            i13 = 6;
                            i14 = 6;
                        }
                        if (z9) {
                            eVar.b(iVarA, iVar5, eVar2.b(), i14);
                        } else {
                            eVar.b(iVarA, iVar5, eVar2.b(), i14);
                        }
                        if (z9) {
                            eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                        } else {
                            eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                        }
                        i15 = 0;
                        if (z) {
                            eVar.b(iVarA, iVar, 0, 6);
                        }
                    } else {
                        iVar4 = iVarA3;
                        z10 = true;
                    }
                    z11 = false;
                    i12 = 5;
                    if (z10) {
                        iVar6 = iVar8;
                        iVar5 = iVar4;
                        iVar7 = iVarA2;
                        eVar.a(iVarA, iVar4, eVar2.b(), f, iVar8, iVarA2, eVar3.b(), i12);
                        z14 = eVar2.d.f48b instanceof a.b.a.j.b;
                        boolean z19 = eVar3.d.f48b instanceof a.b.a.j.b;
                        if (!z14) {
                            if (z14) {
                            }
                        } else if (z14) {
                        }
                        if (z11) {
                            i13 = 6;
                            i14 = 6;
                        }
                        if (z9) {
                            eVar.b(iVarA, iVar5, eVar2.b(), i14);
                        } else {
                            eVar.b(iVarA, iVar5, eVar2.b(), i14);
                        }
                        if (z9) {
                            eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                        } else {
                            eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                        }
                        i15 = 0;
                        if (z) {
                            eVar.b(iVarA, iVar, 0, 6);
                        }
                    } else {
                        iVar5 = iVar4;
                        iVar6 = iVar8;
                        iVar7 = iVarA2;
                    }
                    z12 = z;
                    z13 = z12;
                    i13 = 5;
                    i14 = 5;
                    if (z11) {
                        i13 = 6;
                        i14 = 6;
                    }
                    if (z9) {
                        eVar.b(iVarA, iVar5, eVar2.b(), i14);
                    } else {
                        eVar.b(iVarA, iVar5, eVar2.b(), i14);
                    }
                    if (z9) {
                        eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                    } else {
                        eVar.c(iVar7, iVar6, -eVar3.b(), i13);
                    }
                    i15 = 0;
                    if (z) {
                        eVar.b(iVarA, iVar, 0, 6);
                    }
                }
            } else if (z) {
                eVar.b(iVar2, iVarA2, i11, 5);
            }
            iVar7 = iVarA2;
            i15 = 0;
        } else {
            if (z) {
                i11 = 0;
                eVar.b(iVar2, iVarA2, i11, 5);
            }
            iVar7 = iVarA2;
            i15 = 0;
        }
        if (z) {
            eVar.b(iVar2, iVar7, i15, 6);
        }
    }

    private boolean t(int i) {
        int i2 = i * 2;
        a.b.a.j.e[] eVarArr = this.A;
        if (eVarArr[i2].d != null && eVarArr[i2].d.d != eVarArr[i2]) {
            int i3 = i2 + 1;
            if (eVarArr[i3].d != null && eVarArr[i3].d.d == eVarArr[i3]) {
                return true;
            }
        }
        return false;
    }

    public boolean A() {
        a.b.a.j.e eVar = this.t;
        a.b.a.j.e eVar2 = eVar.d;
        if (eVar2 != null && eVar2.d == eVar) {
            return true;
        }
        a.b.a.j.e eVar3 = this.v;
        a.b.a.j.e eVar4 = eVar3.d;
        return eVar4 != null && eVar4.d == eVar3;
    }

    public boolean B() {
        return this.f == 0 && this.G == 0.0f && this.k == 0 && this.l == 0 && this.C[1] == a.b.a.j.f.b.MATCH_CONSTRAINT;
    }

    public boolean C() {
        return this.e == 0 && this.G == 0.0f && this.h == 0 && this.i == 0 && this.C[0] == a.b.a.j.f.b.MATCH_CONSTRAINT;
    }

    public void D() {
        this.s.j();
        this.t.j();
        this.u.j();
        this.v.j();
        this.w.j();
        this.x.j();
        this.y.j();
        this.z.j();
        this.D = null;
        this.r = 0.0f;
        this.E = 0;
        this.F = 0;
        this.G = 0.0f;
        this.H = -1;
        this.I = 0;
        this.J = 0;
        this.M = 0;
        this.N = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.R = 0;
        this.S = 0;
        this.T = 0;
        this.U = 0;
        float f = j0;
        this.V = f;
        this.W = f;
        a.b.a.j.f.b[] bVarArr = this.C;
        a.b.a.j.f.b bVar = a.b.a.j.f.b.FIXED;
        bVarArr[0] = bVar;
        bVarArr[1] = bVar;
        this.X = null;
        this.Y = 0;
        this.a0 = null;
        this.e0 = 0;
        this.f0 = 0;
        float[] fArr = this.g0;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.f60a = -1;
        this.f61b = -1;
        int[] iArr = this.q;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.e = 0;
        this.f = 0;
        this.j = 1.0f;
        this.m = 1.0f;
        this.i = Integer.MAX_VALUE;
        this.l = Integer.MAX_VALUE;
        this.h = 0;
        this.k = 0;
        this.n = -1;
        this.o = 1.0f;
        a.b.a.j.n nVar = this.f62c;
        if (nVar != null) {
            nVar.d();
        }
        a.b.a.j.n nVar2 = this.d;
        if (nVar2 != null) {
            nVar2.d();
        }
        this.p = null;
        this.b0 = false;
        this.c0 = false;
        this.d0 = false;
    }

    public void E() {
        a.b.a.j.f fVarK = k();
        if (fVarK != null && (fVarK instanceof a.b.a.j.g) && ((a.b.a.j.g) k()).N()) {
            return;
        }
        int size = this.B.size();
        for (int i = 0; i < size; i++) {
            this.B.get(i).j();
        }
    }

    public void F() {
        for (int i = 0; i < 6; i++) {
            this.A[i].d().d();
        }
    }

    public void G() {
    }

    public void H() {
        int i = this.I;
        int i2 = this.J;
        this.M = i;
        this.N = i2;
    }

    public void I() {
        for (int i = 0; i < 6; i++) {
            this.A[i].d().g();
        }
    }

    public a.b.a.j.e a(a.b.a.j.e.d dVar) {
        switch (a.b.a.j.f.a.f63a[dVar.ordinal()]) {
            case 1:
                return this.s;
            case 2:
                return this.t;
            case 3:
                return this.u;
            case 4:
                return this.v;
            case 5:
                return this.w;
            case 6:
                return this.z;
            case 7:
                return this.x;
            case 8:
                return this.y;
            case 9:
                return null;
            default:
                throw new java.lang.AssertionError(dVar.name());
        }
    }

    public void a(float f) {
        this.V = f;
    }

    public void a(int i) {
        a.b.a.j.k.a(i, this);
    }

    public void a(int i, int i2) {
        this.I = i;
        int i3 = i2 - i;
        this.E = i3;
        int i4 = this.R;
        if (i3 < i4) {
            this.E = i4;
        }
    }

    public void a(int i, int i2, int i3) {
        if (i3 == 0) {
            a(i, i2);
        } else if (i3 == 1) {
            e(i, i2);
        }
        this.c0 = true;
    }

    public void a(int i, int i2, int i3, float f) {
        this.e = i;
        this.h = i2;
        this.i = i3;
        this.j = f;
        if (f >= 1.0f || i != 0) {
            return;
        }
        this.e = 2;
    }

    public void a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i3 - i;
        int i8 = i4 - i2;
        this.I = i;
        this.J = i2;
        if (this.Y == 8) {
            this.E = 0;
            this.F = 0;
            return;
        }
        if (this.C[0] == a.b.a.j.f.b.FIXED && i7 < (i6 = this.E)) {
            i7 = i6;
        }
        if (this.C[1] == a.b.a.j.f.b.FIXED && i8 < (i5 = this.F)) {
            i8 = i5;
        }
        this.E = i7;
        this.F = i8;
        int i9 = this.S;
        if (i8 < i9) {
            this.F = i9;
        }
        int i10 = this.E;
        int i11 = this.R;
        if (i10 < i11) {
            this.E = i11;
        }
        this.c0 = true;
    }

    public void a(a.b.a.c cVar) {
        this.s.a(cVar);
        this.t.a(cVar);
        this.u.a(cVar);
        this.v.a(cVar);
        this.w.a(cVar);
        this.z.a(cVar);
        this.x.a(cVar);
        this.y.a(cVar);
    }

    /* JADX WARN: Code duplicated, block: B:154:0x02ae  */
    /* JADX WARN: Code duplicated, block: B:155:0x02b7  */
    /* JADX WARN: Code duplicated, block: B:158:0x02bd  */
    /* JADX WARN: Code duplicated, block: B:159:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:162:0x02fc  */
    /* JADX WARN: Code duplicated, block: B:164:0x0306  */
    /* JADX WARN: Code duplicated, block: B:165:0x0311  */
    /* JADX WARN: Code duplicated, block: B:167:0x0320  */
    /* JADX WARN: Code duplicated, block: B:170:0x032a  */
    /* JADX WARN: Code duplicated, block: B:172:? A[RETURN, SYNTHETIC] */
    public void a(a.b.a.e eVar) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        a.b.a.i iVar;
        int i;
        int i2;
        int i3;
        boolean z5;
        a.b.a.e eVar2;
        boolean z6;
        a.b.a.i iVar2;
        a.b.a.j.f fVar;
        a.b.a.i iVarA;
        a.b.a.j.f fVar2;
        a.b.a.i iVarA2;
        a.b.a.i iVar3;
        a.b.a.j.f fVar3;
        int i4;
        int i5;
        a.b.a.i iVar4;
        a.b.a.i iVar5;
        a.b.a.i iVar6;
        a.b.a.i iVar7;
        int i6;
        int i7;
        int i8;
        boolean z7;
        boolean zA;
        a.b.a.i iVarA3 = eVar.a(this.s);
        a.b.a.i iVarA4 = eVar.a(this.u);
        a.b.a.i iVarA5 = eVar.a(this.t);
        a.b.a.i iVarA6 = eVar.a(this.v);
        a.b.a.i iVarA7 = eVar.a(this.w);
        a.b.a.j.f fVar4 = this.D;
        if (fVar4 != null) {
            z = fVar4 != null && fVar4.C[0] == a.b.a.j.f.b.WRAP_CONTENT;
            a.b.a.j.f fVar5 = this.D;
            boolean z8 = fVar5 != null && fVar5.C[1] == a.b.a.j.f.b.WRAP_CONTENT;
            if (t(0)) {
                ((a.b.a.j.g) this.D).a(this, 0);
                z7 = true;
            } else {
                z7 = z();
            }
            if (t(1)) {
                ((a.b.a.j.g) this.D).a(this, 1);
                zA = true;
            } else {
                zA = A();
            }
            if (z && this.Y != 8 && this.s.d == null && this.u.d == null) {
                eVar.b(eVar.a(this.D.u), iVarA4, 0, 1);
            }
            if (z8 && this.Y != 8 && this.t.d == null && this.v.d == null && this.w == null) {
                eVar.b(eVar.a(this.D.v), iVarA6, 0, 1);
            }
            z2 = z8;
            z3 = z7;
            z4 = zA;
        } else {
            z = false;
            z2 = false;
            z3 = false;
            z4 = false;
        }
        int i9 = this.E;
        int i10 = this.R;
        if (i9 < i10) {
            i9 = i10;
        }
        int i11 = this.F;
        int i12 = this.S;
        if (i11 < i12) {
            i11 = i12;
        }
        boolean z9 = this.C[0] != a.b.a.j.f.b.MATCH_CONSTRAINT;
        boolean z10 = this.C[1] != a.b.a.j.f.b.MATCH_CONSTRAINT;
        this.n = this.H;
        float f = this.G;
        this.o = f;
        int i13 = this.e;
        int i14 = this.f;
        if (f <= 0.0f || this.Y == 8) {
            iVar = iVarA7;
            i13 = i13;
            i = i9;
            i2 = i11;
            i3 = i14;
            z5 = false;
        } else {
            iVar = iVarA7;
            if (this.C[0] == a.b.a.j.f.b.MATCH_CONSTRAINT && i13 == 0) {
                i13 = 3;
            }
            if (this.C[1] == a.b.a.j.f.b.MATCH_CONSTRAINT && i14 == 0) {
                i14 = 3;
            }
            a.b.a.j.f.b[] bVarArr = this.C;
            a.b.a.j.f.b bVar = bVarArr[0];
            a.b.a.j.f.b bVar2 = a.b.a.j.f.b.MATCH_CONSTRAINT;
            if (bVar == bVar2 && bVarArr[1] == bVar2) {
                i8 = 3;
                if (i13 == 3 && i14 == 3) {
                    a(z, z2, z9, z10);
                }
                i = i9;
                i2 = i11;
                i3 = i14;
                z5 = true;
            } else {
                i8 = 3;
            }
            a.b.a.j.f.b[] bVarArr2 = this.C;
            a.b.a.j.f.b bVar3 = bVarArr2[0];
            a.b.a.j.f.b bVar4 = a.b.a.j.f.b.MATCH_CONSTRAINT;
            if (bVar3 == bVar4 && i13 == i8) {
                this.n = 0;
                i = (int) (this.o * this.F);
                if (bVarArr2[1] != bVar4) {
                    i2 = i11;
                    i3 = i14;
                    z5 = false;
                    i13 = 4;
                } else {
                    i2 = i11;
                    i3 = i14;
                    z5 = true;
                }
            } else if (this.C[1] == a.b.a.j.f.b.MATCH_CONSTRAINT && i14 == 3) {
                this.n = 1;
                if (this.H == -1) {
                    this.o = 1.0f / this.o;
                }
                i2 = (int) (this.o * this.E);
                i13 = i13;
                i = i9;
                if (this.C[0] != a.b.a.j.f.b.MATCH_CONSTRAINT) {
                    z5 = false;
                    i3 = 4;
                } else {
                    i3 = i14;
                    z5 = true;
                }
            } else {
                i = i9;
                i2 = i11;
                i3 = i14;
                z5 = true;
            }
        }
        int[] iArr = this.g;
        iArr[0] = i13;
        iArr[1] = i3;
        boolean z11 = z5 && ((i7 = this.n) == 0 || i7 == -1);
        boolean z12 = this.C[0] == a.b.a.j.f.b.WRAP_CONTENT && (this instanceof a.b.a.j.g);
        boolean z13 = !this.z.i();
        if (this.f60a != 2) {
            a.b.a.j.f fVar6 = this.D;
            a.b.a.i iVarA8 = fVar6 != null ? eVar.a(fVar6.u) : null;
            a.b.a.j.f fVar7 = this.D;
            a(eVar, z, fVar7 != null ? eVar.a(fVar7.s) : null, iVarA8, this.C[0], z12, this.s, this.u, this.I, i, this.R, this.q[0], this.V, z11, z3, i13, this.h, this.i, this.j, z13);
        }
        if (this.f61b == 2) {
            return;
        }
        boolean z14 = this.C[1] == a.b.a.j.f.b.WRAP_CONTENT && (this instanceof a.b.a.j.g);
        boolean z15 = z5 && ((i6 = this.n) == 1 || i6 == -1);
        if (this.Q > 0) {
            if (this.w.d().f76b == 1) {
                eVar2 = eVar;
                this.w.d().a(eVar2);
            } else {
                eVar2 = eVar;
                a.b.a.i iVar8 = iVar;
                iVar2 = iVarA5;
                eVar2.a(iVar8, iVar2, c(), 6);
                a.b.a.j.e eVar3 = this.w.d;
                if (eVar3 != null) {
                    eVar2.a(iVar8, eVar2.a(eVar3), 0, 6);
                    z6 = false;
                } else {
                    z6 = z13;
                }
            }
            fVar = this.D;
            if (fVar != null) {
                iVarA = eVar2.a(fVar.v);
            } else {
                iVarA = null;
            }
            fVar2 = this.D;
            if (fVar2 != null) {
                iVarA2 = eVar2.a(fVar2.t);
            } else {
                iVarA2 = null;
            }
            iVar3 = iVar2;
            a(eVar, z2, iVarA2, iVarA, this.C[1], z14, this.t, this.v, this.J, i2, this.S, this.q[1], this.W, z15, z4, i3, this.k, this.l, this.m, z6);
            if (z5) {
                i4 = 6;
                fVar3 = this;
                i5 = fVar3.n;
                float f2 = fVar3.o;
                if (i5 == 1) {
                    iVar4 = iVarA6;
                    iVar5 = iVar3;
                    iVar6 = iVarA4;
                    iVar7 = iVarA3;
                } else {
                    i4 = 6;
                    iVar4 = iVarA4;
                    iVar5 = iVarA3;
                    iVar6 = iVarA6;
                    iVar7 = iVar3;
                }
                eVar.a(iVar4, iVar5, iVar6, iVar7, f2, i4);
            } else {
                fVar3 = this;
            }
            if (fVar3.z.i()) {
                eVar.a(fVar3, fVar3.z.g().c(), (float) java.lang.Math.toRadians(fVar3.r + 90.0f), fVar3.z.b());
            }
        }
        eVar2 = eVar;
        iVar2 = iVarA5;
        z6 = z13;
        fVar = this.D;
        if (fVar != null) {
            iVarA = eVar2.a(fVar.v);
        } else {
            iVarA = null;
        }
        fVar2 = this.D;
        if (fVar2 != null) {
            iVarA2 = eVar2.a(fVar2.t);
        } else {
            iVarA2 = null;
        }
        iVar3 = iVar2;
        a(eVar, z2, iVarA2, iVarA, this.C[1], z14, this.t, this.v, this.J, i2, this.S, this.q[1], this.W, z15, z4, i3, this.k, this.l, this.m, z6);
        if (z5) {
            i4 = 6;
            fVar3 = this;
            i5 = fVar3.n;
            float f3 = fVar3.o;
            if (i5 == 1) {
                iVar4 = iVarA6;
                iVar5 = iVar3;
                iVar6 = iVarA4;
                iVar7 = iVarA3;
            } else {
                i4 = 6;
                iVar4 = iVarA4;
                iVar5 = iVarA3;
                iVar6 = iVarA6;
                iVar7 = iVar3;
            }
            eVar.a(iVar4, iVar5, iVar6, iVar7, f3, i4);
        } else {
            fVar3 = this;
        }
        if (fVar3.z.i()) {
            eVar.a(fVar3, fVar3.z.g().c(), (float) java.lang.Math.toRadians(fVar3.r + 90.0f), fVar3.z.b());
        }
    }

    public void a(a.b.a.j.e.d dVar, a.b.a.j.f fVar, a.b.a.j.e.d dVar2, int i, int i2) {
        a(dVar).a(fVar.a(dVar2), i, i2, a.b.a.j.e.c.STRONG, 0, true);
    }

    public void a(a.b.a.j.f.b bVar) {
        this.C[0] = bVar;
        if (bVar == a.b.a.j.f.b.WRAP_CONTENT) {
            o(this.T);
        }
    }

    public void a(a.b.a.j.f fVar) {
        this.D = fVar;
    }

    public void a(a.b.a.j.f fVar, float f, int i) {
        a.b.a.j.e.d dVar = a.b.a.j.e.d.CENTER;
        a(dVar, fVar, dVar, i, 0);
        this.r = f;
    }

    public void a(java.lang.Object obj) {
        this.X = obj;
    }

    public void a(java.lang.String str) {
        this.Z = str;
    }

    public void a(boolean z) {
    }

    public void a(boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.n == -1) {
            if (z3 && !z4) {
                this.n = 0;
            } else if (!z3 && z4) {
                this.n = 1;
                if (this.H == -1) {
                    this.o = 1.0f / this.o;
                }
            }
        }
        if (this.n == 0 && (!this.t.i() || !this.v.i())) {
            this.n = 1;
        } else if (this.n == 1 && (!this.s.i() || !this.u.i())) {
            this.n = 0;
        }
        if (this.n == -1 && (!this.t.i() || !this.v.i() || !this.s.i() || !this.u.i())) {
            if (this.t.i() && this.v.i()) {
                this.n = 0;
            } else if (this.s.i() && this.u.i()) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1) {
            if (z && !z2) {
                this.n = 0;
            } else if (!z && z2) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1) {
            if (this.h > 0 && this.k == 0) {
                this.n = 0;
            } else if (this.h == 0 && this.k > 0) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1 && z && z2) {
            this.o = 1.0f / this.o;
            this.n = 1;
        }
    }

    public boolean a() {
        return this.Y != 8;
    }

    public float b(int i) {
        if (i == 0) {
            return this.V;
        }
        if (i == 1) {
            return this.W;
        }
        return -1.0f;
    }

    public java.util.ArrayList<a.b.a.j.e> b() {
        return this.B;
    }

    public void b(float f) {
        this.g0[0] = f;
    }

    public void b(int i, int i2) {
        this.O = i;
        this.P = i2;
    }

    public void b(int i, int i2, int i3, float f) {
        this.f = i;
        this.k = i2;
        this.l = i3;
        this.m = f;
        if (f >= 1.0f || i != 0) {
            return;
        }
        this.f = 2;
    }

    public void b(a.b.a.e eVar) {
        eVar.a(this.s);
        eVar.a(this.t);
        eVar.a(this.u);
        eVar.a(this.v);
        if (this.Q > 0) {
            eVar.a(this.w);
        }
    }

    public void b(a.b.a.j.f.b bVar) {
        this.C[1] = bVar;
        if (bVar == a.b.a.j.f.b.WRAP_CONTENT) {
            g(this.U);
        }
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0084 A[PHI: r0
  0x0084: PHI (r0v2 int) = (r0v1 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int) binds: [B:45:0x0084, B:35:0x007d, B:23:0x004f, B:25:0x0055, B:27:0x0061, B:29:0x0065] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0084 -> B:39:0x0085). Please report as a decompilation issue!!! */
    public void b(java.lang.String str) {
        float fAbs;
        int i = 0;
        if (str == null || str.length() == 0) {
            this.G = 0.0f;
            return;
        }
        int i2 = -1;
        int length = str.length();
        int iIndexOf = str.indexOf(44);
        int i3 = 0;
        if (iIndexOf > 0 && iIndexOf < length - 1) {
            java.lang.String strSubstring = str.substring(0, iIndexOf);
            if (strSubstring.equalsIgnoreCase("W")) {
                i2 = 0;
            } else if (strSubstring.equalsIgnoreCase("H")) {
                i2 = 1;
            }
            i3 = iIndexOf + 1;
        }
        int iIndexOf2 = str.indexOf(58);
        try {
            if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                java.lang.String strSubstring2 = str.substring(i3);
                if (strSubstring2.length() > 0) {
                    fAbs = java.lang.Float.parseFloat(strSubstring2);
                } else {
                    fAbs = 0.0f;
                }
            } else {
                java.lang.String strSubstring3 = str.substring(i3, iIndexOf2);
                java.lang.String strSubstring4 = str.substring(iIndexOf2 + 1);
                if (strSubstring3.length() <= 0 || strSubstring4.length() <= 0) {
                    fAbs = 0.0f;
                } else {
                    float f = java.lang.Float.parseFloat(strSubstring3);
                    float f2 = java.lang.Float.parseFloat(strSubstring4);
                    if (f <= 0.0f || f2 <= 0.0f) {
                        fAbs = 0.0f;
                    } else {
                        fAbs = i2 == 1 ? java.lang.Math.abs(f2 / f) : java.lang.Math.abs(f / f2);
                    }
                }
            }
        } catch (java.lang.NumberFormatException unused) {
        }
        i = (fAbs > i ? 1 : (fAbs == i ? 0 : -1));
        if (i > 0) {
            this.G = fAbs;
            this.H = i2;
        }
    }

    public void b(boolean z) {
    }

    public int c() {
        return this.Q;
    }

    public a.b.a.j.f.b c(int i) {
        if (i == 0) {
            return j();
        }
        if (i == 1) {
            return q();
        }
        return null;
    }

    public void c(float f) {
        this.W = f;
    }

    public void c(int i, int i2) {
        this.I = i;
        this.J = i2;
    }

    public void c(a.b.a.e eVar) {
        int iB = eVar.b(this.s);
        int iB2 = eVar.b(this.t);
        int iB3 = eVar.b(this.u);
        int iB4 = eVar.b(this.v);
        int i = iB4 - iB2;
        if (iB3 - iB < 0 || i < 0 || iB == Integer.MIN_VALUE || iB == Integer.MAX_VALUE || iB2 == Integer.MIN_VALUE || iB2 == Integer.MAX_VALUE || iB3 == Integer.MIN_VALUE || iB3 == Integer.MAX_VALUE || iB4 == Integer.MIN_VALUE || iB4 == Integer.MAX_VALUE) {
            iB4 = 0;
            iB = 0;
            iB2 = 0;
            iB3 = 0;
        }
        a(iB, iB2, iB3, iB4);
    }

    public int d() {
        return w() + this.F;
    }

    public int d(int i) {
        if (i == 0) {
            return s();
        }
        if (i == 1) {
            return i();
        }
        return 0;
    }

    public void d(float f) {
        this.g0[1] = f;
    }

    void d(int i, int i2) {
        if (i2 == 0) {
            this.K = i;
        } else if (i2 == 1) {
            this.L = i;
        }
    }

    int e(int i) {
        if (i == 0) {
            return this.K;
        }
        if (i == 1) {
            return this.L;
        }
        return 0;
    }

    public java.lang.Object e() {
        return this.X;
    }

    public void e(int i, int i2) {
        this.J = i;
        int i3 = i2 - i;
        this.F = i3;
        int i4 = this.S;
        if (i3 < i4) {
            this.F = i4;
        }
    }

    public java.lang.String f() {
        return this.Z;
    }

    public void f(int i) {
        this.Q = i;
    }

    public int g() {
        return this.M + this.O;
    }

    public void g(int i) {
        this.F = i;
        int i2 = this.S;
        if (i < i2) {
            this.F = i2;
        }
    }

    public int h() {
        return this.N + this.P;
    }

    public void h(int i) {
        this.e0 = i;
    }

    public int i() {
        if (this.Y == 8) {
            return 0;
        }
        return this.F;
    }

    public void i(int i) {
        this.q[1] = i;
    }

    public a.b.a.j.f.b j() {
        return this.C[0];
    }

    public void j(int i) {
        this.q[0] = i;
    }

    public a.b.a.j.f k() {
        return this.D;
    }

    public void k(int i) {
        if (i < 0) {
            i = 0;
        }
        this.S = i;
    }

    public a.b.a.j.n l() {
        if (this.d == null) {
            this.d = new a.b.a.j.n();
        }
        return this.d;
    }

    public void l(int i) {
        if (i < 0) {
            i = 0;
        }
        this.R = i;
    }

    public a.b.a.j.n m() {
        if (this.f62c == null) {
            this.f62c = new a.b.a.j.n();
        }
        return this.f62c;
    }

    public void m(int i) {
        this.f0 = i;
    }

    public int n() {
        return v() + this.E;
    }

    public void n(int i) {
        this.Y = i;
    }

    protected int o() {
        return this.I + this.O;
    }

    public void o(int i) {
        this.E = i;
        int i2 = this.R;
        if (i < i2) {
            this.E = i2;
        }
    }

    protected int p() {
        return this.J + this.P;
    }

    public void p(int i) {
        this.U = i;
    }

    public a.b.a.j.f.b q() {
        return this.C[1];
    }

    public void q(int i) {
        this.T = i;
    }

    public int r() {
        return this.Y;
    }

    public void r(int i) {
        this.I = i;
    }

    public int s() {
        if (this.Y == 8) {
            return 0;
        }
        return this.E;
    }

    public void s(int i) {
        this.J = i;
    }

    public int t() {
        return this.U;
    }

    public java.lang.String toString() {
        java.lang.String str;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.String str2 = "";
        if (this.a0 != null) {
            str = "type: " + this.a0 + " ";
        } else {
            str = "";
        }
        sb.append(str);
        if (this.Z != null) {
            str2 = "id: " + this.Z + " ";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.I);
        sb.append(", ");
        sb.append(this.J);
        sb.append(") - (");
        sb.append(this.E);
        sb.append(" x ");
        sb.append(this.F);
        sb.append(") wrap: (");
        sb.append(this.T);
        sb.append(" x ");
        sb.append(this.U);
        sb.append(")");
        return sb.toString();
    }

    public int u() {
        return this.T;
    }

    public int v() {
        return this.I;
    }

    public int w() {
        return this.J;
    }

    public boolean x() {
        return this.Q > 0;
    }

    public boolean y() {
        return this.s.d().f76b == 1 && this.u.d().f76b == 1 && this.t.d().f76b == 1 && this.v.d().f76b == 1;
    }

    public boolean z() {
        a.b.a.j.e eVar = this.s;
        a.b.a.j.e eVar2 = eVar.d;
        if (eVar2 != null && eVar2.d == eVar) {
            return true;
        }
        a.b.a.j.e eVar3 = this.u;
        a.b.a.j.e eVar4 = eVar3.d;
        return eVar4 != null && eVar4.d == eVar3;
    }
}
