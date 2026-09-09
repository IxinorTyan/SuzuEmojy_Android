package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class q extends a.b.a.j.f {
    protected java.util.ArrayList<a.b.a.j.f> k0 = new java.util.ArrayList<>();

    @Override // a.b.a.j.f
    public void D() {
        this.k0.clear();
        super.D();
    }

    @Override // a.b.a.j.f
    public void H() {
        super.H();
        java.util.ArrayList<a.b.a.j.f> arrayList = this.k0;
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            a.b.a.j.f fVar = this.k0.get(i);
            fVar.b(g(), h());
            if (!(fVar instanceof a.b.a.j.g)) {
                fVar.H();
            }
        }
    }

    public a.b.a.j.g J() {
        a.b.a.j.f fVarK = k();
        a.b.a.j.g gVar = this instanceof a.b.a.j.g ? (a.b.a.j.g) this : null;
        while (fVarK != null) {
            a.b.a.j.f fVarK2 = fVarK.k();
            if (fVarK instanceof a.b.a.j.g) {
                gVar = (a.b.a.j.g) fVarK;
            }
            fVarK = fVarK2;
        }
        return gVar;
    }

    public void K() {
        H();
        java.util.ArrayList<a.b.a.j.f> arrayList = this.k0;
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            a.b.a.j.f fVar = this.k0.get(i);
            if (fVar instanceof a.b.a.j.q) {
                ((a.b.a.j.q) fVar).K();
            }
        }
    }

    public void L() {
        this.k0.clear();
    }

    @Override // a.b.a.j.f
    public void a(a.b.a.c cVar) {
        super.a(cVar);
        int size = this.k0.size();
        for (int i = 0; i < size; i++) {
            this.k0.get(i).a(cVar);
        }
    }

    @Override // a.b.a.j.f
    public void b(int i, int i2) {
        super.b(i, i2);
        int size = this.k0.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.k0.get(i3).b(o(), p());
        }
    }

    public void b(a.b.a.j.f fVar) {
        this.k0.add(fVar);
        if (fVar.k() != null) {
            ((a.b.a.j.q) fVar.k()).c(fVar);
        }
        fVar.a((a.b.a.j.f) this);
    }

    public void c(a.b.a.j.f fVar) {
        this.k0.remove(fVar);
        fVar.a((a.b.a.j.f) null);
    }
}
