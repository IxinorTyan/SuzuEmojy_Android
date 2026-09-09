package a.b.a;

/* JADX INFO: loaded from: classes.dex */
class h<T> implements a.b.a.g<T> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.lang.Object[] f36a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f37b;

    h(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("The max pool size must be > 0");
        }
        this.f36a = new java.lang.Object[i];
    }

    @Override // a.b.a.g
    public T a() {
        int i = this.f37b;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        java.lang.Object[] objArr = this.f36a;
        T t = (T) objArr[i2];
        objArr[i2] = null;
        this.f37b = i - 1;
        return t;
    }

    @Override // a.b.a.g
    public void a(T[] tArr, int i) {
        if (i > tArr.length) {
            i = tArr.length;
        }
        for (int i2 = 0; i2 < i; i2++) {
            T t = tArr[i2];
            int i3 = this.f37b;
            java.lang.Object[] objArr = this.f36a;
            if (i3 < objArr.length) {
                objArr[i3] = t;
                this.f37b = i3 + 1;
            }
        }
    }

    @Override // a.b.a.g
    public boolean a(T t) {
        int i = this.f37b;
        java.lang.Object[] objArr = this.f36a;
        if (i >= objArr.length) {
            return false;
        }
        objArr[i] = t;
        this.f37b = i + 1;
        return true;
    }
}
