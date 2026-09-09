package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
final class o extends b.a.a.a.a.a.p {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final transient int f368c;
    final transient int d;
    final /* synthetic */ b.a.a.a.a.a.p e;

    o(b.a.a.a.a.a.p pVar, int i, int i2) {
        this.e = pVar;
        this.f368c = i;
        this.d = i2;
    }

    @Override // b.a.a.a.a.a.m
    final int a() {
        return this.e.b() + this.f368c + this.d;
    }

    @Override // b.a.a.a.a.a.p, java.util.List
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final b.a.a.a.a.a.p subList(int i, int i2) {
        b.a.a.a.a.a.j.a(i, i2, this.d);
        b.a.a.a.a.a.p pVar = this.e;
        int i3 = this.f368c;
        return pVar.subList(i + i3, i2 + i3);
    }

    @Override // b.a.a.a.a.a.m
    final int b() {
        return this.e.b() + this.f368c;
    }

    @Override // b.a.a.a.a.a.m
    final java.lang.Object[] c() {
        return this.e.c();
    }

    @Override // java.util.List
    public final java.lang.Object get(int i) {
        b.a.a.a.a.a.j.a(i, this.d, "index");
        return this.e.get(i + this.f368c);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.d;
    }
}
