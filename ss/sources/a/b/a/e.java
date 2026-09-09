package a.b.a;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static int p = 1000;
    public static a.b.a.f q;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private a.b.a.e.a f32c;
    a.b.a.b[] f;
    final a.b.a.c l;
    private final a.b.a.e.a o;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int f30a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.util.HashMap<java.lang.String, a.b.a.i> f31b = null;
    private int d = 32;
    private int e = 32;
    public boolean g = false;
    private boolean[] h = new boolean[32];
    int i = 1;
    int j = 0;
    private int k = 32;
    private a.b.a.i[] m = new a.b.a.i[p];
    private int n = 0;

    interface a {
        a.b.a.i a(a.b.a.e eVar, boolean[] zArr);

        void a(a.b.a.e.a aVar);

        void a(a.b.a.i iVar);

        void clear();

        a.b.a.i getKey();
    }

    public e() {
        this.f = null;
        this.f = new a.b.a.b[32];
        j();
        a.b.a.c cVar = new a.b.a.c();
        this.l = cVar;
        this.f32c = new a.b.a.d(cVar);
        this.o = new a.b.a.b(this.l);
    }

    private final int a(a.b.a.e.a aVar, boolean z) {
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.h++;
        }
        for (int i = 0; i < this.i; i++) {
            this.h[i] = false;
        }
        boolean z2 = false;
        int i2 = 0;
        while (!z2) {
            a.b.a.f fVar2 = q;
            if (fVar2 != null) {
                fVar2.i++;
            }
            i2++;
            if (i2 >= this.i * 2) {
                return i2;
            }
            if (aVar.getKey() != null) {
                this.h[aVar.getKey().f39b] = true;
            }
            a.b.a.i iVarA = aVar.a(this, this.h);
            if (iVarA != null) {
                boolean[] zArr = this.h;
                int i3 = iVarA.f39b;
                if (zArr[i3]) {
                    return i2;
                }
                zArr[i3] = true;
            }
            if (iVarA != null) {
                float f = Float.MAX_VALUE;
                int i4 = -1;
                for (int i5 = 0; i5 < this.j; i5++) {
                    a.b.a.b bVar = this.f[i5];
                    if (bVar.f24a.g != a.b.a.i.a.UNRESTRICTED && !bVar.e && bVar.b(iVarA)) {
                        float fB = bVar.d.b(iVarA);
                        if (fB < 0.0f) {
                            float f2 = (-bVar.f25b) / fB;
                            if (f2 < f) {
                                i4 = i5;
                                f = f2;
                            }
                        }
                    }
                }
                if (i4 > -1) {
                    a.b.a.b bVar2 = this.f[i4];
                    bVar2.f24a.f40c = -1;
                    a.b.a.f fVar3 = q;
                    if (fVar3 != null) {
                        fVar3.j++;
                    }
                    bVar2.d(iVarA);
                    a.b.a.i iVar = bVar2.f24a;
                    iVar.f40c = i4;
                    iVar.c(bVar2);
                }
            }
            z2 = true;
        }
        return i2;
    }

    public static a.b.a.b a(a.b.a.e eVar, a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, float f, boolean z) {
        a.b.a.b bVarB = eVar.b();
        if (z) {
            eVar.b(bVarB);
        }
        bVarB.a(iVar, iVar2, iVar3, f);
        return bVarB;
    }

    private a.b.a.i a(a.b.a.i.a aVar, java.lang.String str) {
        a.b.a.i iVarA = this.l.f28b.a();
        if (iVarA == null) {
            iVarA = new a.b.a.i(aVar, str);
        } else {
            iVarA.a();
        }
        iVarA.a(aVar, str);
        int i = this.n;
        int i2 = p;
        if (i >= i2) {
            int i3 = i2 * 2;
            p = i3;
            this.m = (a.b.a.i[]) java.util.Arrays.copyOf(this.m, i3);
        }
        a.b.a.i[] iVarArr = this.m;
        int i4 = this.n;
        this.n = i4 + 1;
        iVarArr[i4] = iVarA;
        return iVarA;
    }

    private int b(a.b.a.e.a aVar) {
        float f;
        boolean z;
        int i = 0;
        while (true) {
            f = 0.0f;
            if (i >= this.j) {
                z = false;
                break;
            }
            a.b.a.b[] bVarArr = this.f;
            if (bVarArr[i].f24a.g != a.b.a.i.a.UNRESTRICTED && bVarArr[i].f25b < 0.0f) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            return 0;
        }
        boolean z2 = false;
        int i2 = 0;
        while (!z2) {
            a.b.a.f fVar = q;
            if (fVar != null) {
                fVar.k++;
            }
            i2++;
            float f2 = Float.MAX_VALUE;
            int i3 = 0;
            int i4 = -1;
            int i5 = -1;
            int i6 = 0;
            while (i3 < this.j) {
                a.b.a.b bVar = this.f[i3];
                if (bVar.f24a.g != a.b.a.i.a.UNRESTRICTED && !bVar.e && bVar.f25b < f) {
                    int i7 = 1;
                    while (i7 < this.i) {
                        a.b.a.i iVar = this.l.f29c[i7];
                        float fB = bVar.d.b(iVar);
                        if (fB > f) {
                            for (int i8 = 0; i8 < 7; i8++) {
                                float f3 = iVar.f[i8] / fB;
                                if ((f3 < f2 && i8 == i6) || i8 > i6) {
                                    i5 = i7;
                                    i6 = i8;
                                    f2 = f3;
                                    i4 = i3;
                                }
                            }
                        }
                        i7++;
                        f = 0.0f;
                    }
                }
                i3++;
                f = 0.0f;
            }
            if (i4 != -1) {
                a.b.a.b bVar2 = this.f[i4];
                bVar2.f24a.f40c = -1;
                a.b.a.f fVar2 = q;
                if (fVar2 != null) {
                    fVar2.j++;
                }
                bVar2.d(this.l.f29c[i5]);
                a.b.a.i iVar2 = bVar2.f24a;
                iVar2.f40c = i4;
                iVar2.c(bVar2);
            } else {
                z2 = true;
            }
            if (i2 > this.i / 2) {
                z2 = true;
            }
            f = 0.0f;
        }
        return i2;
    }

    private void b(a.b.a.b bVar) {
        bVar.a(this, 0);
    }

    private final void c(a.b.a.b bVar) {
        a.b.a.b[] bVarArr = this.f;
        int i = this.j;
        if (bVarArr[i] != null) {
            this.l.f27a.a(bVarArr[i]);
        }
        a.b.a.b[] bVarArr2 = this.f;
        int i2 = this.j;
        bVarArr2[i2] = bVar;
        a.b.a.i iVar = bVar.f24a;
        iVar.f40c = i2;
        this.j = i2 + 1;
        iVar.c(bVar);
    }

    private final void d(a.b.a.b bVar) {
        if (this.j > 0) {
            bVar.d.a(bVar, this.f);
            if (bVar.d.f21a == 0) {
                bVar.e = true;
            }
        }
    }

    private void g() {
        for (int i = 0; i < this.j; i++) {
            a.b.a.b bVar = this.f[i];
            bVar.f24a.e = bVar.f25b;
        }
    }

    public static a.b.a.f h() {
        return q;
    }

    private void i() {
        int i = this.d * 2;
        this.d = i;
        this.f = (a.b.a.b[]) java.util.Arrays.copyOf(this.f, i);
        a.b.a.c cVar = this.l;
        cVar.f29c = (a.b.a.i[]) java.util.Arrays.copyOf(cVar.f29c, this.d);
        int i2 = this.d;
        this.h = new boolean[i2];
        this.e = i2;
        this.k = i2;
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.d++;
            fVar.o = java.lang.Math.max(fVar.o, i2);
            a.b.a.f fVar2 = q;
            fVar2.A = fVar2.o;
        }
    }

    private void j() {
        int i = 0;
        while (true) {
            a.b.a.b[] bVarArr = this.f;
            if (i >= bVarArr.length) {
                return;
            }
            a.b.a.b bVar = bVarArr[i];
            if (bVar != null) {
                this.l.f27a.a(bVar);
            }
            this.f[i] = null;
            i++;
        }
    }

    public a.b.a.b a(a.b.a.i iVar, a.b.a.i iVar2, int i, int i2) {
        a.b.a.b bVarB = b();
        bVarB.a(iVar, iVar2, i);
        if (i2 != 6) {
            bVarB.a(this, i2);
        }
        a(bVarB);
        return bVarB;
    }

    public a.b.a.i a() {
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.n++;
        }
        if (this.i + 1 >= this.e) {
            i();
        }
        a.b.a.i iVarA = a(a.b.a.i.a.SLACK, (java.lang.String) null);
        int i = this.f30a + 1;
        this.f30a = i;
        this.i++;
        iVarA.f39b = i;
        this.l.f29c[i] = iVarA;
        return iVarA;
    }

    public a.b.a.i a(int i, java.lang.String str) {
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.l++;
        }
        if (this.i + 1 >= this.e) {
            i();
        }
        a.b.a.i iVarA = a(a.b.a.i.a.ERROR, str);
        int i2 = this.f30a + 1;
        this.f30a = i2;
        this.i++;
        iVarA.f39b = i2;
        iVarA.d = i;
        this.l.f29c[i2] = iVarA;
        this.f32c.a(iVarA);
        return iVarA;
    }

    public a.b.a.i a(java.lang.Object obj) {
        a.b.a.i iVarE = null;
        if (obj == null) {
            return null;
        }
        if (this.i + 1 >= this.e) {
            i();
        }
        if (obj instanceof a.b.a.j.e) {
            a.b.a.j.e eVar = (a.b.a.j.e) obj;
            iVarE = eVar.e();
            if (iVarE == null) {
                eVar.a(this.l);
                iVarE = eVar.e();
            }
            int i = iVarE.f39b;
            if (i == -1 || i > this.f30a || this.l.f29c[i] == null) {
                if (iVarE.f39b != -1) {
                    iVarE.a();
                }
                int i2 = this.f30a + 1;
                this.f30a = i2;
                this.i++;
                iVarE.f39b = i2;
                iVarE.g = a.b.a.i.a.UNRESTRICTED;
                this.l.f29c[i2] = iVarE;
            }
        }
        return iVarE;
    }

    public void a(a.b.a.b bVar) {
        a.b.a.i iVarC;
        if (bVar == null) {
            return;
        }
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.f++;
            if (bVar.e) {
                fVar.g++;
            }
        }
        boolean z = true;
        if (this.j + 1 >= this.k || this.i + 1 >= this.e) {
            i();
        }
        boolean z2 = false;
        if (!bVar.e) {
            d(bVar);
            if (bVar.c()) {
                return;
            }
            bVar.a();
            if (bVar.a(this)) {
                a.b.a.i iVarA = a();
                bVar.f24a = iVarA;
                c(bVar);
                this.o.a(bVar);
                a(this.o, true);
                if (iVarA.f40c == -1) {
                    if (bVar.f24a == iVarA && (iVarC = bVar.c(iVarA)) != null) {
                        a.b.a.f fVar2 = q;
                        if (fVar2 != null) {
                            fVar2.j++;
                        }
                        bVar.d(iVarC);
                    }
                    if (!bVar.e) {
                        bVar.f24a.c(bVar);
                    }
                    this.j--;
                }
            } else {
                z = false;
            }
            if (!bVar.b()) {
                return;
            } else {
                z2 = z;
            }
        }
        if (z2) {
            return;
        }
        c(bVar);
    }

    void a(a.b.a.b bVar, int i, int i2) {
        bVar.a(a(i2, (java.lang.String) null), i);
    }

    void a(a.b.a.e.a aVar) {
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.s++;
            fVar.t = java.lang.Math.max(fVar.t, this.i);
            a.b.a.f fVar2 = q;
            fVar2.u = java.lang.Math.max(fVar2.u, this.j);
        }
        d((a.b.a.b) aVar);
        b(aVar);
        a(aVar, false);
        g();
    }

    public void a(a.b.a.i iVar, int i) {
        a.b.a.b bVarB;
        int i2 = iVar.f40c;
        if (i2 != -1) {
            a.b.a.b bVar = this.f[i2];
            if (!bVar.e) {
                if (bVar.d.f21a == 0) {
                    bVar.e = true;
                } else {
                    bVarB = b();
                    bVarB.c(iVar, i);
                }
            }
            bVar.f25b = i;
            return;
        }
        bVarB = b();
        bVarB.b(iVar, i);
        a(bVarB);
    }

    public void a(a.b.a.i iVar, a.b.a.i iVar2, int i, float f, a.b.a.i iVar3, a.b.a.i iVar4, int i2, int i3) {
        a.b.a.b bVarB = b();
        bVarB.a(iVar, iVar2, i, f, iVar3, iVar4, i2);
        if (i3 != 6) {
            bVarB.a(this, i3);
        }
        a(bVarB);
    }

    public void a(a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, a.b.a.i iVar4, float f, int i) {
        a.b.a.b bVarB = b();
        bVarB.a(iVar, iVar2, iVar3, iVar4, f);
        if (i != 6) {
            bVarB.a(this, i);
        }
        a(bVarB);
    }

    public void a(a.b.a.i iVar, a.b.a.i iVar2, boolean z) {
        a.b.a.b bVarB = b();
        a.b.a.i iVarC = c();
        iVarC.d = 0;
        bVarB.a(iVar, iVar2, iVarC, 0);
        if (z) {
            a(bVarB, (int) (bVarB.d.b(iVarC) * (-1.0f)), 1);
        }
        a(bVarB);
    }

    public void a(a.b.a.j.f fVar, a.b.a.j.f fVar2, float f, int i) {
        a.b.a.i iVarA = a(fVar.a(a.b.a.j.e.d.LEFT));
        a.b.a.i iVarA2 = a(fVar.a(a.b.a.j.e.d.TOP));
        a.b.a.i iVarA3 = a(fVar.a(a.b.a.j.e.d.RIGHT));
        a.b.a.i iVarA4 = a(fVar.a(a.b.a.j.e.d.BOTTOM));
        a.b.a.i iVarA5 = a(fVar2.a(a.b.a.j.e.d.LEFT));
        a.b.a.i iVarA6 = a(fVar2.a(a.b.a.j.e.d.TOP));
        a.b.a.i iVarA7 = a(fVar2.a(a.b.a.j.e.d.RIGHT));
        a.b.a.i iVarA8 = a(fVar2.a(a.b.a.j.e.d.BOTTOM));
        a.b.a.b bVarB = b();
        double d = f;
        double d2 = i;
        bVarB.b(iVarA2, iVarA4, iVarA6, iVarA8, (float) (java.lang.Math.sin(d) * d2));
        a(bVarB);
        a.b.a.b bVarB2 = b();
        bVarB2.b(iVarA, iVarA3, iVarA5, iVarA7, (float) (java.lang.Math.cos(d) * d2));
        a(bVarB2);
    }

    public int b(java.lang.Object obj) {
        a.b.a.i iVarE = ((a.b.a.j.e) obj).e();
        if (iVarE != null) {
            return (int) (iVarE.e + 0.5f);
        }
        return 0;
    }

    public a.b.a.b b() {
        a.b.a.b bVarA = this.l.f27a.a();
        if (bVarA == null) {
            bVarA = new a.b.a.b(this.l);
        } else {
            bVarA.d();
        }
        a.b.a.i.b();
        return bVarA;
    }

    public void b(a.b.a.i iVar, a.b.a.i iVar2, int i, int i2) {
        a.b.a.b bVarB = b();
        a.b.a.i iVarC = c();
        iVarC.d = 0;
        bVarB.a(iVar, iVar2, iVarC, i);
        if (i2 != 6) {
            a(bVarB, (int) (bVarB.d.b(iVarC) * (-1.0f)), i2);
        }
        a(bVarB);
    }

    public void b(a.b.a.i iVar, a.b.a.i iVar2, boolean z) {
        a.b.a.b bVarB = b();
        a.b.a.i iVarC = c();
        iVarC.d = 0;
        bVarB.b(iVar, iVar2, iVarC, 0);
        if (z) {
            a(bVarB, (int) (bVarB.d.b(iVarC) * (-1.0f)), 1);
        }
        a(bVarB);
    }

    public a.b.a.i c() {
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.m++;
        }
        if (this.i + 1 >= this.e) {
            i();
        }
        a.b.a.i iVarA = a(a.b.a.i.a.SLACK, (java.lang.String) null);
        int i = this.f30a + 1;
        this.f30a = i;
        this.i++;
        iVarA.f39b = i;
        this.l.f29c[i] = iVarA;
        return iVarA;
    }

    public void c(a.b.a.i iVar, a.b.a.i iVar2, int i, int i2) {
        a.b.a.b bVarB = b();
        a.b.a.i iVarC = c();
        iVarC.d = 0;
        bVarB.b(iVar, iVar2, iVarC, i);
        if (i2 != 6) {
            a(bVarB, (int) (bVarB.d.b(iVarC) * (-1.0f)), i2);
        }
        a(bVarB);
    }

    public a.b.a.c d() {
        return this.l;
    }

    public void e() {
        a.b.a.f fVar = q;
        if (fVar != null) {
            fVar.e++;
        }
        if (this.g) {
            a.b.a.f fVar2 = q;
            if (fVar2 != null) {
                fVar2.q++;
            }
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= this.j) {
                    z = true;
                    break;
                } else if (!this.f[i].e) {
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                a.b.a.f fVar3 = q;
                if (fVar3 != null) {
                    fVar3.p++;
                }
                g();
                return;
            }
        }
        a(this.f32c);
    }

    public void f() {
        a.b.a.c cVar;
        int i = 0;
        while (true) {
            cVar = this.l;
            a.b.a.i[] iVarArr = cVar.f29c;
            if (i >= iVarArr.length) {
                break;
            }
            a.b.a.i iVar = iVarArr[i];
            if (iVar != null) {
                iVar.a();
            }
            i++;
        }
        cVar.f28b.a(this.m, this.n);
        this.n = 0;
        java.util.Arrays.fill(this.l.f29c, (java.lang.Object) null);
        java.util.HashMap<java.lang.String, a.b.a.i> map = this.f31b;
        if (map != null) {
            map.clear();
        }
        this.f30a = 0;
        this.f32c.clear();
        this.i = 1;
        for (int i2 = 0; i2 < this.j; i2++) {
            this.f[i2].f26c = false;
        }
        j();
        this.j = 0;
    }
}
