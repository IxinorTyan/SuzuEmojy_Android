package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
final class n<E> extends b.a.a.a.a.a.l<E> {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final b.a.a.a.a.a.p<E> f367c;

    n(b.a.a.a.a.a.p<E> pVar, int i) {
        super(pVar.size(), i);
        this.f367c = pVar;
    }

    @Override // b.a.a.a.a.a.l
    protected final E a(int i) {
        return this.f367c.get(i);
    }
}
