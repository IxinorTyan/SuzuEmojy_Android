package a.c.d;

/* JADX INFO: loaded from: classes.dex */
public class b<T> implements a.c.d.a<T> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.lang.Object[] f84a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f85b;

    public b(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("The max pool size must be > 0");
        }
        this.f84a = new java.lang.Object[i];
    }

    private boolean b(T t) {
        for (int i = 0; i < this.f85b; i++) {
            if (this.f84a[i] == t) {
                return true;
            }
        }
        return false;
    }

    @Override // a.c.d.a
    public T a() {
        int i = this.f85b;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        java.lang.Object[] objArr = this.f84a;
        T t = (T) objArr[i2];
        objArr[i2] = null;
        this.f85b = i - 1;
        return t;
    }

    @Override // a.c.d.a
    public boolean a(T t) {
        if (b(t)) {
            throw new java.lang.IllegalStateException("Already in the pool!");
        }
        int i = this.f85b;
        java.lang.Object[] objArr = this.f84a;
        if (i >= objArr.length) {
            return false;
        }
        objArr[i] = t;
        this.f85b = i + 1;
        return true;
    }
}
