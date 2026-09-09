package a.a;

/* JADX INFO: loaded from: classes.dex */
public class c<E> implements java.lang.Cloneable {
    private static final java.lang.Object e = new java.lang.Object();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f3a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private long[] f4b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private java.lang.Object[] f5c;
    private int d;

    public c() {
        this(10);
    }

    public c(int i) {
        this.f3a = false;
        if (i == 0) {
            this.f4b = a.a.b.f1b;
            this.f5c = a.a.b.f2c;
        } else {
            int iB = a.a.b.b(i);
            this.f4b = new long[iB];
            this.f5c = new java.lang.Object[iB];
        }
        this.d = 0;
    }

    private void c() {
        int i = this.d;
        long[] jArr = this.f4b;
        java.lang.Object[] objArr = this.f5c;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            java.lang.Object obj = objArr[i3];
            if (obj != e) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f3a = false;
        this.d = i2;
    }

    public long a(int i) {
        if (this.f3a) {
            c();
        }
        return this.f4b[i];
    }

    public E a(long j) {
        return a(j, null);
    }

    public E a(long j, E e2) {
        int iA = a.a.b.a(this.f4b, this.d, j);
        if (iA >= 0) {
            java.lang.Object[] objArr = this.f5c;
            if (objArr[iA] != e) {
                return (E) objArr[iA];
            }
        }
        return e2;
    }

    public void a() {
        int i = this.d;
        java.lang.Object[] objArr = this.f5c;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.d = 0;
        this.f3a = false;
    }

    public int b() {
        if (this.f3a) {
            c();
        }
        return this.d;
    }

    public void b(int i) {
        java.lang.Object[] objArr = this.f5c;
        java.lang.Object obj = objArr[i];
        java.lang.Object obj2 = e;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.f3a = true;
        }
    }

    public void b(long j, E e2) {
        int iA = a.a.b.a(this.f4b, this.d, j);
        if (iA >= 0) {
            this.f5c[iA] = e2;
            return;
        }
        int i = ~iA;
        if (i < this.d) {
            java.lang.Object[] objArr = this.f5c;
            if (objArr[i] == e) {
                this.f4b[i] = j;
                objArr[i] = e2;
                return;
            }
        }
        if (this.f3a && this.d >= this.f4b.length) {
            c();
            i = ~a.a.b.a(this.f4b, this.d, j);
        }
        int i2 = this.d;
        if (i2 >= this.f4b.length) {
            int iB = a.a.b.b(i2 + 1);
            long[] jArr = new long[iB];
            java.lang.Object[] objArr2 = new java.lang.Object[iB];
            long[] jArr2 = this.f4b;
            java.lang.System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            java.lang.Object[] objArr3 = this.f5c;
            java.lang.System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f4b = jArr;
            this.f5c = objArr2;
        }
        int i3 = this.d;
        if (i3 - i != 0) {
            long[] jArr3 = this.f4b;
            int i4 = i + 1;
            java.lang.System.arraycopy(jArr3, i, jArr3, i4, i3 - i);
            java.lang.Object[] objArr4 = this.f5c;
            java.lang.System.arraycopy(objArr4, i, objArr4, i4, this.d - i);
        }
        this.f4b[i] = j;
        this.f5c[i] = e2;
        this.d++;
    }

    public E c(int i) {
        if (this.f3a) {
            c();
        }
        return (E) this.f5c[i];
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public a.a.c<E> m0clone() {
        try {
            a.a.c<E> cVar = (a.a.c) super.clone();
            cVar.f4b = (long[]) this.f4b.clone();
            cVar.f5c = (java.lang.Object[]) this.f5c.clone();
            return cVar;
        } catch (java.lang.CloneNotSupportedException e2) {
            throw new java.lang.AssertionError(e2);
        }
    }

    public java.lang.String toString() {
        if (b() <= 0) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.d * 28);
        sb.append('{');
        for (int i = 0; i < this.d; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a(i));
            sb.append('=');
            E eC = c(i);
            if (eC != this) {
                sb.append(eC);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
