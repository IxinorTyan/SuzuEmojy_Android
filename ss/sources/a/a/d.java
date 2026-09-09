package a.a;

/* JADX INFO: loaded from: classes.dex */
abstract class d<K, V> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    a.a.d<K, V>.b f6a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    a.a.d<K, V>.c f7b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    a.a.d<K, V>.e f8c;

    final class a<T> implements java.util.Iterator<T> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final int f9a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f10b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f11c;
        boolean d = false;

        a(int i) {
            this.f9a = i;
            this.f10b = a.a.d.this.c();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f11c < this.f10b;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T t = (T) a.a.d.this.a(this.f11c, this.f9a);
            this.f11c++;
            this.d = true;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.d) {
                throw new java.lang.IllegalStateException();
            }
            int i = this.f11c - 1;
            this.f11c = i;
            this.f10b--;
            this.d = false;
            a.a.d.this.a(i);
        }
    }

    final class b implements java.util.Set<java.util.Map.Entry<K, V>> {
        b() {
        }

        public boolean a(java.util.Map.Entry<K, V> entry) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public /* bridge */ /* synthetic */ boolean add(java.lang.Object obj) {
            a((java.util.Map.Entry) obj);
            throw null;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(java.util.Collection<? extends java.util.Map.Entry<K, V>> collection) {
            int iC = a.a.d.this.c();
            for (java.util.Map.Entry<K, V> entry : collection) {
                a.a.d.this.a(entry.getKey(), entry.getValue());
            }
            return iC != a.a.d.this.c();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            a.a.d.this.a();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(java.lang.Object obj) {
            if (!(obj instanceof java.util.Map.Entry)) {
                return false;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry) obj;
            int iA = a.a.d.this.a(entry.getKey());
            if (iA < 0) {
                return false;
            }
            return a.a.b.a(a.a.d.this.a(iA, 1), entry.getValue());
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(java.util.Collection<?> collection) {
            java.util.Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(java.lang.Object obj) {
            return a.a.d.a((java.util.Set) this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int iC = a.a.d.this.c() - 1; iC >= 0; iC--) {
                java.lang.Object objA = a.a.d.this.a(iC, 0);
                java.lang.Object objA2 = a.a.d.this.a(iC, 1);
                iHashCode += (objA == null ? 0 : objA.hashCode()) ^ (objA2 == null ? 0 : objA2.hashCode());
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return a.a.d.this.c() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
            return new a.a.d.C0001d();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(java.lang.Object obj) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(java.util.Collection<?> collection) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(java.util.Collection<?> collection) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return a.a.d.this.c();
        }

        @Override // java.util.Set, java.util.Collection
        public java.lang.Object[] toArray() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    final class c implements java.util.Set<K> {
        c() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(java.util.Collection<? extends K> collection) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            a.a.d.this.a();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(java.lang.Object obj) {
            return a.a.d.this.a(obj) >= 0;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(java.util.Collection<?> collection) {
            return a.a.d.a((java.util.Map) a.a.d.this.b(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(java.lang.Object obj) {
            return a.a.d.a((java.util.Set) this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int iC = a.a.d.this.c() - 1; iC >= 0; iC--) {
                java.lang.Object objA = a.a.d.this.a(iC, 0);
                iHashCode += objA == null ? 0 : objA.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return a.a.d.this.c() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public java.util.Iterator<K> iterator() {
            return new a.a.d.a(0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(java.lang.Object obj) {
            int iA = a.a.d.this.a(obj);
            if (iA < 0) {
                return false;
            }
            a.a.d.this.a(iA);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(java.util.Collection<?> collection) {
            return a.a.d.b(a.a.d.this.b(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(java.util.Collection<?> collection) {
            return a.a.d.c(a.a.d.this.b(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return a.a.d.this.c();
        }

        @Override // java.util.Set, java.util.Collection
        public java.lang.Object[] toArray() {
            return a.a.d.this.b(0);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) a.a.d.this.a(tArr, 0);
        }
    }

    /* JADX INFO: renamed from: a.a.d$d, reason: collision with other inner class name */
    final class C0001d implements java.util.Iterator<java.util.Map.Entry<K, V>>, java.util.Map.Entry<K, V> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f14a;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        boolean f16c = false;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f15b = -1;

        C0001d() {
            this.f14a = a.a.d.this.c() - 1;
        }

        @Override // java.util.Map.Entry
        public boolean equals(java.lang.Object obj) {
            if (!this.f16c) {
                throw new java.lang.IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(obj instanceof java.util.Map.Entry)) {
                return false;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry) obj;
            return a.a.b.a(entry.getKey(), a.a.d.this.a(this.f15b, 0)) && a.a.b.a(entry.getValue(), a.a.d.this.a(this.f15b, 1));
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.f16c) {
                return (K) a.a.d.this.a(this.f15b, 0);
            }
            throw new java.lang.IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.f16c) {
                return (V) a.a.d.this.a(this.f15b, 1);
            }
            throw new java.lang.IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f15b < this.f14a;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (!this.f16c) {
                throw new java.lang.IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            java.lang.Object objA = a.a.d.this.a(this.f15b, 0);
            java.lang.Object objA2 = a.a.d.this.a(this.f15b, 1);
            return (objA == null ? 0 : objA.hashCode()) ^ (objA2 != null ? objA2.hashCode() : 0);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ java.lang.Object next() {
            next();
            return this;
        }

        @Override // java.util.Iterator
        public java.util.Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            this.f15b++;
            this.f16c = true;
            return this;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.f16c) {
                throw new java.lang.IllegalStateException();
            }
            a.a.d.this.a(this.f15b);
            this.f15b--;
            this.f14a--;
            this.f16c = false;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (this.f16c) {
                return (V) a.a.d.this.a(this.f15b, v);
            }
            throw new java.lang.IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public java.lang.String toString() {
            return getKey() + "=" + getValue();
        }
    }

    final class e implements java.util.Collection<V> {
        e() {
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(java.util.Collection<? extends V> collection) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            a.a.d.this.a();
        }

        @Override // java.util.Collection
        public boolean contains(java.lang.Object obj) {
            return a.a.d.this.b(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(java.util.Collection<?> collection) {
            java.util.Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return a.a.d.this.c() == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public java.util.Iterator<V> iterator() {
            return new a.a.d.a(1);
        }

        @Override // java.util.Collection
        public boolean remove(java.lang.Object obj) {
            int iB = a.a.d.this.b(obj);
            if (iB < 0) {
                return false;
            }
            a.a.d.this.a(iB);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(java.util.Collection<?> collection) {
            int iC = a.a.d.this.c();
            int i = 0;
            boolean z = false;
            while (i < iC) {
                if (collection.contains(a.a.d.this.a(i, 1))) {
                    a.a.d.this.a(i);
                    i--;
                    iC--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(java.util.Collection<?> collection) {
            int iC = a.a.d.this.c();
            int i = 0;
            boolean z = false;
            while (i < iC) {
                if (!collection.contains(a.a.d.this.a(i, 1))) {
                    a.a.d.this.a(i);
                    i--;
                    iC--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return a.a.d.this.c();
        }

        @Override // java.util.Collection
        public java.lang.Object[] toArray() {
            return a.a.d.this.b(1);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) a.a.d.this.a(tArr, 1);
        }
    }

    d() {
    }

    public static <K, V> boolean a(java.util.Map<K, V> map, java.util.Collection<?> collection) {
        java.util.Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!map.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean a(java.util.Set<T> set, java.lang.Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof java.util.Set) {
            java.util.Set set2 = (java.util.Set) obj;
            try {
                return set.size() == set2.size() && set.containsAll(set2);
            } catch (java.lang.ClassCastException | java.lang.NullPointerException unused) {
            }
        }
        return false;
    }

    public static <K, V> boolean b(java.util.Map<K, V> map, java.util.Collection<?> collection) {
        int size = map.size();
        java.util.Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            map.remove(it.next());
        }
        return size != map.size();
    }

    public static <K, V> boolean c(java.util.Map<K, V> map, java.util.Collection<?> collection) {
        int size = map.size();
        java.util.Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    protected abstract int a(java.lang.Object obj);

    protected abstract java.lang.Object a(int i, int i2);

    protected abstract V a(int i, V v);

    protected abstract void a();

    protected abstract void a(int i);

    protected abstract void a(K k, V v);

    public <T> T[] a(T[] tArr, int i) {
        int iC = c();
        if (tArr.length < iC) {
            tArr = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance(tArr.getClass().getComponentType(), iC));
        }
        for (int i2 = 0; i2 < iC; i2++) {
            tArr[i2] = a(i2, i);
        }
        if (tArr.length > iC) {
            tArr[iC] = null;
        }
        return tArr;
    }

    protected abstract int b(java.lang.Object obj);

    protected abstract java.util.Map<K, V> b();

    public java.lang.Object[] b(int i) {
        int iC = c();
        java.lang.Object[] objArr = new java.lang.Object[iC];
        for (int i2 = 0; i2 < iC; i2++) {
            objArr[i2] = a(i2, i);
        }
        return objArr;
    }

    protected abstract int c();

    public java.util.Set<java.util.Map.Entry<K, V>> d() {
        if (this.f6a == null) {
            this.f6a = new a.a.d.b();
        }
        return this.f6a;
    }

    public java.util.Set<K> e() {
        if (this.f7b == null) {
            this.f7b = new a.a.d.c();
        }
        return this.f7b;
    }

    public java.util.Collection<V> f() {
        if (this.f8c == null) {
            this.f8c = new a.a.d.e();
        }
        return this.f8c;
    }
}
