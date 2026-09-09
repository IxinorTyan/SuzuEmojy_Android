package a.e.a;

/* JADX INFO: loaded from: classes.dex */
final class a {
    private static final java.util.Comparator<a.e.a.a.b> f = new a.e.a.a.C0007a();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final int[] f123a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final int[] f124b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final java.util.List<a.e.a.b.d> f125c;
    final a.e.a.b.c[] d;
    private final float[] e = new float[3];

    /* JADX INFO: renamed from: a.e.a.a$a, reason: collision with other inner class name */
    static class C0007a implements java.util.Comparator<a.e.a.a.b> {
        C0007a() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(a.e.a.a.b bVar, a.e.a.a.b bVar2) {
            return bVar2.g() - bVar.g();
        }
    }

    private class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private int f126a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private int f127b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private int f128c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;

        b(int i, int i2) {
            this.f126a = i;
            this.f127b = i2;
            c();
        }

        final boolean a() {
            return e() > 1;
        }

        final int b() {
            int iF = f();
            a.e.a.a aVar = a.e.a.a.this;
            int[] iArr = aVar.f123a;
            int[] iArr2 = aVar.f124b;
            a.e.a.a.a(iArr, iF, this.f126a, this.f127b);
            java.util.Arrays.sort(iArr, this.f126a, this.f127b + 1);
            a.e.a.a.a(iArr, iF, this.f126a, this.f127b);
            int i = this.f128c / 2;
            int i2 = this.f126a;
            int i3 = 0;
            while (true) {
                int i4 = this.f127b;
                if (i2 > i4) {
                    return this.f126a;
                }
                i3 += iArr2[iArr[i2]];
                if (i3 >= i) {
                    return java.lang.Math.min(i4 - 1, i2);
                }
                i2++;
            }
        }

        final void c() {
            a.e.a.a aVar = a.e.a.a.this;
            int[] iArr = aVar.f123a;
            int[] iArr2 = aVar.f124b;
            int i = Integer.MAX_VALUE;
            int i2 = Integer.MAX_VALUE;
            int i3 = Integer.MAX_VALUE;
            int i4 = Integer.MIN_VALUE;
            int i5 = Integer.MIN_VALUE;
            int i6 = Integer.MIN_VALUE;
            int i7 = 0;
            for (int i8 = this.f126a; i8 <= this.f127b; i8++) {
                int i9 = iArr[i8];
                i7 += iArr2[i9];
                int iF = a.e.a.a.f(i9);
                int iE = a.e.a.a.e(i9);
                int iD = a.e.a.a.d(i9);
                if (iF > i4) {
                    i4 = iF;
                }
                if (iF < i) {
                    i = iF;
                }
                if (iE > i5) {
                    i5 = iE;
                }
                if (iE < i2) {
                    i2 = iE;
                }
                if (iD > i6) {
                    i6 = iD;
                }
                if (iD < i3) {
                    i3 = iD;
                }
            }
            this.d = i;
            this.e = i4;
            this.f = i2;
            this.g = i5;
            this.h = i3;
            this.i = i6;
            this.f128c = i7;
        }

        final a.e.a.b.d d() {
            a.e.a.a aVar = a.e.a.a.this;
            int[] iArr = aVar.f123a;
            int[] iArr2 = aVar.f124b;
            int iF = 0;
            int i = 0;
            int iE = 0;
            int iD = 0;
            for (int i2 = this.f126a; i2 <= this.f127b; i2++) {
                int i3 = iArr[i2];
                int i4 = iArr2[i3];
                i += i4;
                iF += a.e.a.a.f(i3) * i4;
                iE += a.e.a.a.e(i3) * i4;
                iD += i4 * a.e.a.a.d(i3);
            }
            float f = i;
            return new a.e.a.b.d(a.e.a.a.a(java.lang.Math.round(iF / f), java.lang.Math.round(iE / f), java.lang.Math.round(iD / f)), i);
        }

        final int e() {
            return (this.f127b + 1) - this.f126a;
        }

        final int f() {
            int i = this.e - this.d;
            int i2 = this.g - this.f;
            int i3 = this.i - this.h;
            if (i < i2 || i < i3) {
                return (i2 < i || i2 < i3) ? -1 : -2;
            }
            return -3;
        }

        final int g() {
            return ((this.e - this.d) + 1) * ((this.g - this.f) + 1) * ((this.i - this.h) + 1);
        }

        final a.e.a.a.b h() {
            if (!a()) {
                throw new java.lang.IllegalStateException("Can not split a box with only 1 color");
            }
            int iB = b();
            a.e.a.a.b bVar = a.e.a.a.this.new b(iB + 1, this.f127b);
            this.f127b = iB;
            c();
            return bVar;
        }
    }

    a(int[] iArr, int i, a.e.a.b.c[] cVarArr) {
        this.d = cVarArr;
        int[] iArr2 = new int[32768];
        this.f124b = iArr2;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int iB = b(iArr[i2]);
            iArr[i2] = iB;
            iArr2[iB] = iArr2[iB] + 1;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < 32768; i4++) {
            if (iArr2[i4] > 0 && g(i4)) {
                iArr2[i4] = 0;
            }
            if (iArr2[i4] > 0) {
                i3++;
            }
        }
        int[] iArr3 = new int[i3];
        this.f123a = iArr3;
        int i5 = 0;
        for (int i6 = 0; i6 < 32768; i6++) {
            if (iArr2[i6] > 0) {
                iArr3[i5] = i6;
                i5++;
            }
        }
        if (i3 > i) {
            this.f125c = c(i);
            return;
        }
        this.f125c = new java.util.ArrayList();
        for (int i7 = 0; i7 < i3; i7++) {
            int i8 = iArr3[i7];
            this.f125c.add(new a.e.a.b.d(a(i8), iArr2[i8]));
        }
    }

    private static int a(int i) {
        return a(f(i), e(i), d(i));
    }

    static int a(int i, int i2, int i3) {
        return android.graphics.Color.rgb(b(i, 5, 8), b(i2, 5, 8), b(i3, 5, 8));
    }

    private java.util.List<a.e.a.b.d> a(java.util.Collection<a.e.a.a.b> collection) {
        java.util.ArrayList arrayList = new java.util.ArrayList(collection.size());
        java.util.Iterator<a.e.a.a.b> it = collection.iterator();
        while (it.hasNext()) {
            a.e.a.b.d dVarD = it.next().d();
            if (!a(dVarD)) {
                arrayList.add(dVarD);
            }
        }
        return arrayList;
    }

    private void a(java.util.PriorityQueue<a.e.a.a.b> priorityQueue, int i) {
        a.e.a.a.b bVarPoll;
        while (priorityQueue.size() < i && (bVarPoll = priorityQueue.poll()) != null && bVarPoll.a()) {
            priorityQueue.offer(bVarPoll.h());
            priorityQueue.offer(bVarPoll);
        }
    }

    static void a(int[] iArr, int i, int i2, int i3) {
        if (i == -2) {
            while (i2 <= i3) {
                int i4 = iArr[i2];
                iArr[i2] = d(i4) | (e(i4) << 10) | (f(i4) << 5);
                i2++;
            }
            return;
        }
        if (i != -1) {
            return;
        }
        while (i2 <= i3) {
            int i5 = iArr[i2];
            iArr[i2] = f(i5) | (d(i5) << 10) | (e(i5) << 5);
            i2++;
        }
    }

    private boolean a(int i, float[] fArr) {
        a.e.a.b.c[] cVarArr = this.d;
        if (cVarArr != null && cVarArr.length > 0) {
            int length = cVarArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (!this.d[i2].a(i, fArr)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(a.e.a.b.d dVar) {
        return a(dVar.d(), dVar.b());
    }

    private static int b(int i) {
        return b(android.graphics.Color.blue(i), 8, 5) | (b(android.graphics.Color.red(i), 8, 5) << 10) | (b(android.graphics.Color.green(i), 8, 5) << 5);
    }

    private static int b(int i, int i2, int i3) {
        return (i3 > i2 ? i << (i3 - i2) : i >> (i2 - i3)) & ((1 << i3) - 1);
    }

    private java.util.List<a.e.a.b.d> c(int i) {
        java.util.PriorityQueue<a.e.a.a.b> priorityQueue = new java.util.PriorityQueue<>(i, f);
        priorityQueue.offer(new a.e.a.a.b(0, this.f123a.length - 1));
        a(priorityQueue, i);
        return a(priorityQueue);
    }

    static int d(int i) {
        return i & 31;
    }

    static int e(int i) {
        return (i >> 5) & 31;
    }

    static int f(int i) {
        return (i >> 10) & 31;
    }

    private boolean g(int i) {
        int iA = a(i);
        a.c.b.a.a(iA, this.e);
        return a(iA, this.e);
    }

    java.util.List<a.e.a.b.d> a() {
        return this.f125c;
    }
}
