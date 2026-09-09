package a.a;

/* JADX INFO: loaded from: classes.dex */
public class e<K, V> {
    static java.lang.Object[] d;
    static int e;
    static java.lang.Object[] f;
    static int g;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int[] f18a = a.a.b.f0a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    java.lang.Object[] f19b = a.a.b.f2c;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    int f20c = 0;

    private static int a(int[] iArr, int i, int i2) {
        try {
            return a.a.b.a(iArr, i, i2);
        } catch (java.lang.ArrayIndexOutOfBoundsException unused) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    private static void a(int[] iArr, java.lang.Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (a.a.a.class) {
                if (g < 10) {
                    objArr[0] = f;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    f = objArr;
                    g++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (a.a.a.class) {
                if (e < 10) {
                    objArr[0] = d;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    d = objArr;
                    e++;
                }
            }
        }
    }

    private void e(int i) {
        if (i == 8) {
            synchronized (a.a.a.class) {
                if (f != null) {
                    java.lang.Object[] objArr = f;
                    this.f19b = objArr;
                    f = (java.lang.Object[]) objArr[0];
                    this.f18a = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    g--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (a.a.a.class) {
                if (d != null) {
                    java.lang.Object[] objArr2 = d;
                    this.f19b = objArr2;
                    d = (java.lang.Object[]) objArr2[0];
                    this.f18a = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    e--;
                    return;
                }
            }
        }
        this.f18a = new int[i];
        this.f19b = new java.lang.Object[i << 1];
    }

    int a() {
        int i = this.f20c;
        if (i == 0) {
            return -1;
        }
        int iA = a(this.f18a, i, 0);
        if (iA < 0 || this.f19b[iA << 1] == null) {
            return iA;
        }
        int i2 = iA + 1;
        while (i2 < i && this.f18a[i2] == 0) {
            if (this.f19b[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = iA - 1; i3 >= 0 && this.f18a[i3] == 0; i3--) {
            if (this.f19b[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    public int a(java.lang.Object obj) {
        return obj == null ? a() : a(obj, obj.hashCode());
    }

    int a(java.lang.Object obj, int i) {
        int i2 = this.f20c;
        if (i2 == 0) {
            return -1;
        }
        int iA = a(this.f18a, i2, i);
        if (iA < 0 || obj.equals(this.f19b[iA << 1])) {
            return iA;
        }
        int i3 = iA + 1;
        while (i3 < i2 && this.f18a[i3] == i) {
            if (obj.equals(this.f19b[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = iA - 1; i4 >= 0 && this.f18a[i4] == i; i4--) {
            if (obj.equals(this.f19b[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    public V a(int i, V v) {
        int i2 = (i << 1) + 1;
        java.lang.Object[] objArr = this.f19b;
        V v2 = (V) objArr[i2];
        objArr[i2] = v;
        return v2;
    }

    public void a(int i) {
        int i2 = this.f20c;
        int[] iArr = this.f18a;
        if (iArr.length < i) {
            java.lang.Object[] objArr = this.f19b;
            e(i);
            if (this.f20c > 0) {
                java.lang.System.arraycopy(iArr, 0, this.f18a, 0, i2);
                java.lang.System.arraycopy(objArr, 0, this.f19b, 0, i2 << 1);
            }
            a(iArr, objArr, i2);
        }
        if (this.f20c != i2) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    int b(java.lang.Object obj) {
        int i = this.f20c * 2;
        java.lang.Object[] objArr = this.f19b;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    public K b(int i) {
        return (K) this.f19b[i << 1];
    }

    public V c(int i) {
        java.lang.Object[] objArr = this.f19b;
        int i2 = i << 1;
        V v = (V) objArr[i2 + 1];
        int i3 = this.f20c;
        int i4 = 0;
        if (i3 <= 1) {
            a(this.f18a, objArr, i3);
            this.f18a = a.a.b.f0a;
            this.f19b = a.a.b.f2c;
        } else {
            int i5 = i3 - 1;
            int[] iArr = this.f18a;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                if (i < i5) {
                    int[] iArr2 = this.f18a;
                    int i6 = i + 1;
                    int i7 = i5 - i;
                    java.lang.System.arraycopy(iArr2, i6, iArr2, i, i7);
                    java.lang.Object[] objArr2 = this.f19b;
                    java.lang.System.arraycopy(objArr2, i6 << 1, objArr2, i2, i7 << 1);
                }
                java.lang.Object[] objArr3 = this.f19b;
                int i8 = i5 << 1;
                objArr3[i8] = null;
                objArr3[i8 + 1] = null;
            } else {
                int i9 = i3 > 8 ? i3 + (i3 >> 1) : 8;
                int[] iArr3 = this.f18a;
                java.lang.Object[] objArr4 = this.f19b;
                e(i9);
                if (i3 != this.f20c) {
                    throw new java.util.ConcurrentModificationException();
                }
                if (i > 0) {
                    java.lang.System.arraycopy(iArr3, 0, this.f18a, 0, i);
                    java.lang.System.arraycopy(objArr4, 0, this.f19b, 0, i2);
                }
                if (i < i5) {
                    int i10 = i + 1;
                    int i11 = i5 - i;
                    java.lang.System.arraycopy(iArr3, i10, this.f18a, i, i11);
                    java.lang.System.arraycopy(objArr4, i10 << 1, this.f19b, i2, i11 << 1);
                }
            }
            i4 = i5;
        }
        if (i3 != this.f20c) {
            throw new java.util.ConcurrentModificationException();
        }
        this.f20c = i4;
        return v;
    }

    public void clear() {
        int i = this.f20c;
        if (i > 0) {
            int[] iArr = this.f18a;
            java.lang.Object[] objArr = this.f19b;
            this.f18a = a.a.b.f0a;
            this.f19b = a.a.b.f2c;
            this.f20c = 0;
            a(iArr, objArr, i);
        }
        if (this.f20c > 0) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    public boolean containsKey(java.lang.Object obj) {
        return a(obj) >= 0;
    }

    public boolean containsValue(java.lang.Object obj) {
        return b(obj) >= 0;
    }

    public V d(int i) {
        return (V) this.f19b[(i << 1) + 1];
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof a.a.e) {
            a.a.e eVar = (a.a.e) obj;
            if (size() != eVar.size()) {
                return false;
            }
            for (int i = 0; i < this.f20c; i++) {
                try {
                    K kB = b(i);
                    V vD = d(i);
                    java.lang.Object obj2 = eVar.get(kB);
                    if (vD == null) {
                        if (obj2 != null || !eVar.containsKey(kB)) {
                            return false;
                        }
                    } else if (!vD.equals(obj2)) {
                        return false;
                    }
                } catch (java.lang.ClassCastException | java.lang.NullPointerException unused) {
                    return false;
                }
            }
            return true;
        }
        if (obj instanceof java.util.Map) {
            java.util.Map map = (java.util.Map) obj;
            if (size() != map.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.f20c; i2++) {
                try {
                    K kB2 = b(i2);
                    V vD2 = d(i2);
                    java.lang.Object obj3 = map.get(kB2);
                    if (vD2 == null) {
                        if (obj3 != null || !map.containsKey(kB2)) {
                            return false;
                        }
                    } else if (!vD2.equals(obj3)) {
                        return false;
                    }
                } catch (java.lang.ClassCastException | java.lang.NullPointerException unused2) {
                }
            }
            return true;
        }
        return false;
    }

    public V get(java.lang.Object obj) {
        int iA = a(obj);
        if (iA >= 0) {
            return (V) this.f19b[(iA << 1) + 1];
        }
        return null;
    }

    public int hashCode() {
        int[] iArr = this.f18a;
        java.lang.Object[] objArr = this.f19b;
        int i = this.f20c;
        int i2 = 1;
        int i3 = 0;
        int iHashCode = 0;
        while (i3 < i) {
            java.lang.Object obj = objArr[i2];
            iHashCode += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return iHashCode;
    }

    public boolean isEmpty() {
        return this.f20c <= 0;
    }

    public V put(K k, V v) {
        int i;
        int iA;
        int i2 = this.f20c;
        if (k == null) {
            iA = a();
            i = 0;
        } else {
            int iHashCode = k.hashCode();
            i = iHashCode;
            iA = a(k, iHashCode);
        }
        if (iA >= 0) {
            int i3 = (iA << 1) + 1;
            java.lang.Object[] objArr = this.f19b;
            V v2 = (V) objArr[i3];
            objArr[i3] = v;
            return v2;
        }
        int i4 = ~iA;
        if (i2 >= this.f18a.length) {
            int i5 = 4;
            if (i2 >= 8) {
                i5 = (i2 >> 1) + i2;
            } else if (i2 >= 4) {
                i5 = 8;
            }
            int[] iArr = this.f18a;
            java.lang.Object[] objArr2 = this.f19b;
            e(i5);
            if (i2 != this.f20c) {
                throw new java.util.ConcurrentModificationException();
            }
            int[] iArr2 = this.f18a;
            if (iArr2.length > 0) {
                java.lang.System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                java.lang.System.arraycopy(objArr2, 0, this.f19b, 0, objArr2.length);
            }
            a(iArr, objArr2, i2);
        }
        if (i4 < i2) {
            int[] iArr3 = this.f18a;
            int i6 = i4 + 1;
            java.lang.System.arraycopy(iArr3, i4, iArr3, i6, i2 - i4);
            java.lang.Object[] objArr3 = this.f19b;
            java.lang.System.arraycopy(objArr3, i4 << 1, objArr3, i6 << 1, (this.f20c - i4) << 1);
        }
        int i7 = this.f20c;
        if (i2 == i7) {
            int[] iArr4 = this.f18a;
            if (i4 < iArr4.length) {
                iArr4[i4] = i;
                java.lang.Object[] objArr4 = this.f19b;
                int i8 = i4 << 1;
                objArr4[i8] = k;
                objArr4[i8 + 1] = v;
                this.f20c = i7 + 1;
                return null;
            }
        }
        throw new java.util.ConcurrentModificationException();
    }

    public V remove(java.lang.Object obj) {
        int iA = a(obj);
        if (iA >= 0) {
            return c(iA);
        }
        return null;
    }

    public int size() {
        return this.f20c;
    }

    public java.lang.String toString() {
        if (isEmpty()) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.f20c * 28);
        sb.append('{');
        for (int i = 0; i < this.f20c; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K kB = b(i);
            if (kB != this) {
                sb.append(kB);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V vD = d(i);
            if (vD != this) {
                sb.append(vD);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
