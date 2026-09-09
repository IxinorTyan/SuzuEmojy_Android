package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class p<E> extends b.a.a.a.a.a.m<E> implements java.util.List<E>, java.util.RandomAccess {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final b.a.a.a.a.a.s<java.lang.Object> f369b = new b.a.a.a.a.a.n(b.a.a.a.a.a.q.d, 0);

    p() {
    }

    public static <E> b.a.a.a.a.a.p<E> d() {
        return (b.a.a.a.a.a.p<E>) b.a.a.a.a.a.q.d;
    }

    @Override // b.a.a.a.a.a.m
    int a(java.lang.Object[] objArr, int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i2] = get(i2);
        }
        return size;
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: a */
    public b.a.a.a.a.a.p<E> subList(int i, int i2) {
        b.a.a.a.a.a.j.a(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        return i3 == 0 ? (b.a.a.a.a.a.p<E>) b.a.a.a.a.a.q.d : new b.a.a.a.a.a.o(this, i, i3);
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final b.a.a.a.a.a.s<E> listIterator(int i) {
        b.a.a.a.a.a.j.b(i, size(), "index");
        return isEmpty() ? (b.a.a.a.a.a.s<E>) f369b : new b.a.a.a.a.a.n(this, i);
    }

    @Override // java.util.List
    @java.lang.Deprecated
    public final void add(int i, E e) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.List
    @java.lang.Deprecated
    public final boolean addAll(int i, java.util.Collection<? extends E> collection) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof java.util.List) {
            java.util.List list = (java.util.List) obj;
            int size = size();
            if (size == list.size()) {
                if (list instanceof java.util.RandomAccess) {
                    for (int i = 0; i < size; i++) {
                        if (b.a.a.a.a.a.i.a(get(i), list.get(i))) {
                        }
                    }
                    return true;
                }
                java.util.Iterator<E> it = iterator();
                java.util.Iterator<E> it2 = list.iterator();
                while (it.hasNext()) {
                    if (it2.hasNext() && b.a.a.a.a.a.i.a(it.next(), it2.next())) {
                    }
                }
                if (!it2.hasNext()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        int size = size();
        int iHashCode = 1;
        for (int i = 0; i < size; i++) {
            iHashCode = (iHashCode * 31) + get(i).hashCode();
        }
        return iHashCode;
    }

    @Override // java.util.List
    public final int indexOf(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object obj) {
        if (obj == null) {
            return -1;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (obj.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final /* bridge */ /* synthetic */ java.util.Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final int lastIndexOf(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object obj) {
        if (obj == null) {
            return -1;
        }
        for (int size = size() - 1; size >= 0; size--) {
            if (obj.equals(get(size))) {
                return size;
            }
        }
        return -1;
    }

    @Override // java.util.List
    public final /* bridge */ /* synthetic */ java.util.ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    @java.lang.Deprecated
    public final E remove(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.List
    @java.lang.Deprecated
    public final E set(int i, E e) {
        throw new java.lang.UnsupportedOperationException();
    }
}
