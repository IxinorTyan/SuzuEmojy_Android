package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
final class q<E> extends b.a.a.a.a.a.p<E> {
    static final b.a.a.a.a.a.p<java.lang.Object> d = new b.a.a.a.a.a.q(new java.lang.Object[0], 0);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final transient java.lang.Object[] f370c;

    q(java.lang.Object[] objArr, int i) {
        this.f370c = objArr;
    }

    @Override // b.a.a.a.a.a.m
    final int a() {
        return 0;
    }

    @Override // b.a.a.a.a.a.p, b.a.a.a.a.a.m
    final int a(java.lang.Object[] objArr, int i) {
        java.lang.System.arraycopy(this.f370c, 0, objArr, 0, 0);
        return 0;
    }

    @Override // b.a.a.a.a.a.m
    final int b() {
        return 0;
    }

    @Override // b.a.a.a.a.a.m
    final java.lang.Object[] c() {
        return this.f370c;
    }

    @Override // java.util.List
    public final E get(int i) {
        b.a.a.a.a.a.j.a(i, 0, "index");
        return (E) this.f370c[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return 0;
    }
}
