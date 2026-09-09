package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected a.b.a.j.f f44a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    protected a.b.a.j.f f45b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    protected a.b.a.j.f f46c;
    protected a.b.a.j.f d;
    protected a.b.a.j.f e;
    protected a.b.a.j.f f;
    protected a.b.a.j.f g;
    protected java.util.ArrayList<a.b.a.j.f> h;
    protected int i;
    protected int j;
    protected float k = 0.0f;
    private int l;
    private boolean m;
    protected boolean n;
    protected boolean o;
    protected boolean p;
    private boolean q;

    public d(a.b.a.j.f fVar, int i, boolean z) {
        this.m = false;
        this.f44a = fVar;
        this.l = i;
        this.m = z;
    }

    private static boolean a(a.b.a.j.f fVar, int i) {
        if (fVar.r() != 8 && fVar.C[i] == a.b.a.j.f.b.MATCH_CONSTRAINT) {
            int[] iArr = fVar.g;
            if (iArr[i] == 0 || iArr[i] == 3) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        int i = this.l * 2;
        a.b.a.j.f fVar = this.f44a;
        boolean z = false;
        a.b.a.j.f fVar2 = fVar;
        boolean z2 = false;
        while (!z2) {
            this.i++;
            a.b.a.j.f[] fVarArr = fVar.i0;
            int i2 = this.l;
            a.b.a.j.f fVar3 = null;
            fVarArr[i2] = null;
            fVar.h0[i2] = null;
            if (fVar.r() != 8) {
                if (this.f45b == null) {
                    this.f45b = fVar;
                }
                this.d = fVar;
                a.b.a.j.f.b[] bVarArr = fVar.C;
                int i3 = this.l;
                if (bVarArr[i3] == a.b.a.j.f.b.MATCH_CONSTRAINT) {
                    int[] iArr = fVar.g;
                    if (iArr[i3] == 0 || iArr[i3] == 3 || iArr[i3] == 2) {
                        this.j++;
                        float[] fArr = fVar.g0;
                        int i4 = this.l;
                        float f = fArr[i4];
                        if (f > 0.0f) {
                            this.k += fArr[i4];
                        }
                        if (a(fVar, this.l)) {
                            if (f < 0.0f) {
                                this.n = true;
                            } else {
                                this.o = true;
                            }
                            if (this.h == null) {
                                this.h = new java.util.ArrayList<>();
                            }
                            this.h.add(fVar);
                        }
                        if (this.f == null) {
                            this.f = fVar;
                        }
                        a.b.a.j.f fVar4 = this.g;
                        if (fVar4 != null) {
                            fVar4.h0[this.l] = fVar;
                        }
                        this.g = fVar;
                    }
                }
            }
            if (fVar2 != fVar) {
                fVar2.i0[this.l] = fVar;
            }
            a.b.a.j.e eVar = fVar.A[i + 1].d;
            if (eVar != null) {
                a.b.a.j.f fVar5 = eVar.f48b;
                a.b.a.j.e[] eVarArr = fVar5.A;
                if (eVarArr[i].d != null && eVarArr[i].d.f48b == fVar) {
                    fVar3 = fVar5;
                }
            }
            if (fVar3 == null) {
                fVar3 = fVar;
                z2 = true;
            }
            fVar2 = fVar;
            fVar = fVar3;
        }
        this.f46c = fVar;
        if (this.l == 0 && this.m) {
            this.e = fVar;
        } else {
            this.e = this.f44a;
        }
        if (this.o && this.n) {
            z = true;
        }
        this.p = z;
    }

    public void a() {
        if (!this.q) {
            b();
        }
        this.q = true;
    }
}
