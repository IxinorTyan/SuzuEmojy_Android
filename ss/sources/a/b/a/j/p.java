package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class p {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f77a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f78b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int f79c;
    private int d;
    private java.util.ArrayList<a.b.a.j.p.a> e = new java.util.ArrayList<>();

    static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private a.b.a.j.e f80a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private a.b.a.j.e f81b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private int f82c;
        private a.b.a.j.e.c d;
        private int e;

        public a(a.b.a.j.e eVar) {
            this.f80a = eVar;
            this.f81b = eVar.g();
            this.f82c = eVar.b();
            this.d = eVar.f();
            this.e = eVar.a();
        }

        public void a(a.b.a.j.f fVar) {
            fVar.a(this.f80a.h()).a(this.f81b, this.f82c, this.d, this.e);
        }

        public void b(a.b.a.j.f fVar) {
            int iA;
            a.b.a.j.e eVarA = fVar.a(this.f80a.h());
            this.f80a = eVarA;
            if (eVarA != null) {
                this.f81b = eVarA.g();
                this.f82c = this.f80a.b();
                this.d = this.f80a.f();
                iA = this.f80a.a();
            } else {
                this.f81b = null;
                iA = 0;
                this.f82c = 0;
                this.d = a.b.a.j.e.c.STRONG;
            }
            this.e = iA;
        }
    }

    public p(a.b.a.j.f fVar) {
        this.f77a = fVar.v();
        this.f78b = fVar.w();
        this.f79c = fVar.s();
        this.d = fVar.i();
        java.util.ArrayList<a.b.a.j.e> arrayListB = fVar.b();
        int size = arrayListB.size();
        for (int i = 0; i < size; i++) {
            this.e.add(new a.b.a.j.p.a(arrayListB.get(i)));
        }
    }

    public void a(a.b.a.j.f fVar) {
        fVar.r(this.f77a);
        fVar.s(this.f78b);
        fVar.o(this.f79c);
        fVar.g(this.d);
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            this.e.get(i).a(fVar);
        }
    }

    public void b(a.b.a.j.f fVar) {
        this.f77a = fVar.v();
        this.f78b = fVar.w();
        this.f79c = fVar.s();
        this.d = fVar.i();
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            this.e.get(i).b(fVar);
        }
    }
}
