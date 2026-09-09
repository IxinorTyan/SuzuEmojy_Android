package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    java.util.HashSet<a.b.a.j.o> f75a = new java.util.HashSet<>(2);

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    int f76b = 0;

    public void a() {
        this.f76b = 1;
        java.util.Iterator<a.b.a.j.o> it = this.f75a.iterator();
        while (it.hasNext()) {
            it.next().e();
        }
    }

    public void a(a.b.a.j.o oVar) {
        this.f75a.add(oVar);
    }

    public void b() {
        this.f76b = 0;
        java.util.Iterator<a.b.a.j.o> it = this.f75a.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    public boolean c() {
        return this.f76b == 1;
    }

    public void d() {
        this.f76b = 0;
        this.f75a.clear();
    }

    public void e() {
    }
}
