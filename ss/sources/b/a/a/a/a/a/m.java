package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class m<E> extends java.util.AbstractCollection<E> implements java.io.Serializable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final java.lang.Object[] f366a = new java.lang.Object[0];

    m() {
    }

    abstract int a();

    abstract int a(java.lang.Object[] objArr, int i);

    @Override // java.util.AbstractCollection, java.util.Collection
    @java.lang.Deprecated
    public final boolean add(E e) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @java.lang.Deprecated
    public final boolean addAll(java.util.Collection<? extends E> collection) {
        throw new java.lang.UnsupportedOperationException();
    }

    abstract int b();

    @org.checkerframework.checker.nullness.compatqual.NullableDecl
    abstract java.lang.Object[] c();

    @Override // java.util.AbstractCollection, java.util.Collection
    @java.lang.Deprecated
    public final void clear() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @java.lang.Deprecated
    public final boolean remove(java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @java.lang.Deprecated
    public final boolean removeAll(java.util.Collection<?> collection) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @java.lang.Deprecated
    public final boolean retainAll(java.util.Collection<?> collection) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final java.lang.Object[] toArray() {
        return toArray(f366a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        if (tArr == null) {
            throw null;
        }
        int size = size();
        int length = tArr.length;
        if (length < size) {
            java.lang.Object[] objArrC = c();
            if (objArrC != null) {
                return (T[]) java.util.Arrays.copyOfRange(objArrC, b(), a(), tArr.getClass());
            }
            tArr = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance(tArr.getClass().getComponentType(), size));
        } else if (length > size) {
            tArr[size] = null;
        }
        a(tArr, 0);
        return tArr;
    }
}
