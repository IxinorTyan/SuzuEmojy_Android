package a.a;

/* JADX INFO: loaded from: classes.dex */
public class a<K, V> extends a.a.e<K, V> implements java.util.Map<K, V> {
    a.a.d<K, V> h;

    /* JADX INFO: renamed from: a.a.a$a, reason: collision with other inner class name */
    class C0000a extends a.a.d<K, V> {
        C0000a() {
        }

        @Override // a.a.d
        protected int a(java.lang.Object obj) {
            return a.a.a.this.a(obj);
        }

        @Override // a.a.d
        protected java.lang.Object a(int i, int i2) {
            return a.a.a.this.f19b[(i << 1) + i2];
        }

        @Override // a.a.d
        protected V a(int i, V v) {
            return a.a.a.this.a(i, v);
        }

        @Override // a.a.d
        protected void a() {
            a.a.a.this.clear();
        }

        @Override // a.a.d
        protected void a(int i) {
            a.a.a.this.c(i);
        }

        @Override // a.a.d
        protected void a(K k, V v) {
            a.a.a.this.put(k, v);
        }

        @Override // a.a.d
        protected int b(java.lang.Object obj) {
            return a.a.a.this.b(obj);
        }

        @Override // a.a.d
        protected java.util.Map<K, V> b() {
            return a.a.a.this;
        }

        @Override // a.a.d
        protected int c() {
            return a.a.a.this.f20c;
        }
    }

    private a.a.d<K, V> b() {
        if (this.h == null) {
            this.h = new a.a.a.C0000a();
        }
        return this.h;
    }

    @Override // java.util.Map
    public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
        return b().d();
    }

    @Override // java.util.Map
    public java.util.Set<K> keySet() {
        return b().e();
    }

    @Override // java.util.Map
    public void putAll(java.util.Map<? extends K, ? extends V> map) {
        a(this.f20c + map.size());
        for (java.util.Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public java.util.Collection<V> values() {
        return b().f();
    }
}
