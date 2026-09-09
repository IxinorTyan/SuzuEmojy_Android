package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
abstract class l<E> extends b.a.a.a.a.a.s<E> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final int f364a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f365b;

    protected l(int i, int i2) {
        b.a.a.a.a.a.j.b(i2, i, "index");
        this.f364a = i;
        this.f365b = i2;
    }

    protected abstract E a(int i);

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.f365b < this.f364a;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.f365b > 0;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final E next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        int i = this.f365b;
        this.f365b = i + 1;
        return a(i);
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.f365b;
    }

    @Override // java.util.ListIterator
    public final E previous() {
        if (!hasPrevious()) {
            throw new java.util.NoSuchElementException();
        }
        int i = this.f365b - 1;
        this.f365b = i;
        return a(i);
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.f365b - 1;
    }
}
