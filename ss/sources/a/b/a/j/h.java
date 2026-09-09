package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public java.util.List<a.b.a.j.f> f68a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    int f69b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    int f70c;
    public boolean d;
    public final int[] e;
    java.util.List<a.b.a.j.f> f;
    java.util.List<a.b.a.j.f> g;
    java.util.HashSet<a.b.a.j.f> h;
    java.util.HashSet<a.b.a.j.f> i;
    java.util.List<a.b.a.j.f> j;
    java.util.List<a.b.a.j.f> k;

    h(java.util.List<a.b.a.j.f> list) {
        this.f69b = -1;
        this.f70c = -1;
        this.d = false;
        this.e = new int[]{-1, -1};
        this.f = new java.util.ArrayList();
        this.g = new java.util.ArrayList();
        this.h = new java.util.HashSet<>();
        this.i = new java.util.HashSet<>();
        this.j = new java.util.ArrayList();
        this.k = new java.util.ArrayList();
        this.f68a = list;
    }

    h(java.util.List<a.b.a.j.f> list, boolean z) {
        this.f69b = -1;
        this.f70c = -1;
        this.d = false;
        this.e = new int[]{-1, -1};
        this.f = new java.util.ArrayList();
        this.g = new java.util.ArrayList();
        this.h = new java.util.HashSet<>();
        this.i = new java.util.HashSet<>();
        this.j = new java.util.ArrayList();
        this.k = new java.util.ArrayList();
        this.f68a = list;
        this.d = z;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0043  */
    private void a(a.b.a.j.f fVar) {
        int iS;
        if (!fVar.b0 || fVar.y()) {
            return;
        }
        boolean z = fVar.u.d != null;
        a.b.a.j.e eVar = (z ? fVar.u : fVar.s).d;
        if (eVar != null) {
            a.b.a.j.f fVar2 = eVar.f48b;
            if (!fVar2.c0) {
                a(fVar2);
            }
            a.b.a.j.e.d dVar = eVar.f49c;
            if (dVar == a.b.a.j.e.d.RIGHT) {
                a.b.a.j.f fVar3 = eVar.f48b;
                iS = fVar3.I + fVar3.s();
            } else if (dVar == a.b.a.j.e.d.LEFT) {
                iS = eVar.f48b.I;
            } else {
                iS = 0;
            }
        } else {
            iS = 0;
        }
        int iB = z ? iS - fVar.u.b() : iS + fVar.s.b() + fVar.s();
        fVar.a(iB - fVar.s(), iB);
        a.b.a.j.e eVar2 = fVar.w.d;
        if (eVar2 != null) {
            a.b.a.j.f fVar4 = eVar2.f48b;
            if (!fVar4.c0) {
                a(fVar4);
            }
            a.b.a.j.f fVar5 = eVar2.f48b;
            int i = (fVar5.J + fVar5.Q) - fVar.Q;
            fVar.e(i, fVar.F + i);
            fVar.c0 = true;
            return;
        }
        boolean z2 = fVar.v.d != null;
        a.b.a.j.e eVar3 = (z2 ? fVar.v : fVar.t).d;
        if (eVar3 != null) {
            a.b.a.j.f fVar6 = eVar3.f48b;
            if (!fVar6.c0) {
                a(fVar6);
            }
            a.b.a.j.e.d dVar2 = eVar3.f49c;
            if (dVar2 == a.b.a.j.e.d.BOTTOM) {
                a.b.a.j.f fVar7 = eVar3.f48b;
                iB = fVar7.J + fVar7.i();
            } else if (dVar2 == a.b.a.j.e.d.TOP) {
                iB = eVar3.f48b.J;
            }
        }
        int iB2 = z2 ? iB - fVar.v.b() : iB + fVar.t.b() + fVar.i();
        fVar.e(iB2 - fVar.i(), iB2);
        fVar.c0 = true;
    }

    private void a(java.util.ArrayList<a.b.a.j.f> arrayList, a.b.a.j.f fVar) {
        if (fVar.d0) {
            return;
        }
        arrayList.add(fVar);
        fVar.d0 = true;
        if (fVar.y()) {
            return;
        }
        if (fVar instanceof a.b.a.j.j) {
            a.b.a.j.j jVar = (a.b.a.j.j) fVar;
            int i = jVar.l0;
            for (int i2 = 0; i2 < i; i2++) {
                a(arrayList, jVar.k0[i2]);
            }
        }
        int length = fVar.A.length;
        for (int i3 = 0; i3 < length; i3++) {
            a.b.a.j.e eVar = fVar.A[i3].d;
            if (eVar != null) {
                a.b.a.j.f fVar2 = eVar.f48b;
                if (eVar != null && fVar2 != fVar.k()) {
                    a(arrayList, fVar2);
                }
            }
        }
    }

    java.util.List<a.b.a.j.f> a() {
        if (!this.j.isEmpty()) {
            return this.j;
        }
        int size = this.f68a.size();
        for (int i = 0; i < size; i++) {
            a.b.a.j.f fVar = this.f68a.get(i);
            if (!fVar.b0) {
                a((java.util.ArrayList<a.b.a.j.f>) this.j, fVar);
            }
        }
        this.k.clear();
        this.k.addAll(this.f68a);
        this.k.removeAll(this.j);
        return this.j;
    }

    public java.util.List<a.b.a.j.f> a(int i) {
        if (i == 0) {
            return this.f;
        }
        if (i == 1) {
            return this.g;
        }
        return null;
    }

    void a(a.b.a.j.f fVar, int i) {
        java.util.HashSet<a.b.a.j.f> hashSet;
        if (i == 0) {
            hashSet = this.h;
        } else if (i != 1) {
            return;
        } else {
            hashSet = this.i;
        }
        hashSet.add(fVar);
    }

    java.util.Set<a.b.a.j.f> b(int i) {
        if (i == 0) {
            return this.h;
        }
        if (i == 1) {
            return this.i;
        }
        return null;
    }

    void b() {
        int size = this.k.size();
        for (int i = 0; i < size; i++) {
            a(this.k.get(i));
        }
    }
}
