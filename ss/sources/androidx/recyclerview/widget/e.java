package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
final class e implements java.lang.Runnable {
    static final java.lang.ThreadLocal<androidx.recyclerview.widget.e> e = new java.lang.ThreadLocal<>();
    static java.util.Comparator<androidx.recyclerview.widget.e.c> f = new androidx.recyclerview.widget.e.a();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    long f299b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    long f300c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    java.util.ArrayList<androidx.recyclerview.widget.RecyclerView> f298a = new java.util.ArrayList<>();
    private java.util.ArrayList<androidx.recyclerview.widget.e.c> d = new java.util.ArrayList<>();

    static class a implements java.util.Comparator<androidx.recyclerview.widget.e.c> {
        a() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(androidx.recyclerview.widget.e.c cVar, androidx.recyclerview.widget.e.c cVar2) {
            if ((cVar.d == null) != (cVar2.d == null)) {
                return cVar.d == null ? 1 : -1;
            }
            boolean z = cVar.f304a;
            if (z != cVar2.f304a) {
                return z ? -1 : 1;
            }
            int i = cVar2.f305b - cVar.f305b;
            if (i != 0) {
                return i;
            }
            int i2 = cVar.f306c - cVar2.f306c;
            if (i2 != 0) {
                return i2;
            }
            return 0;
        }
    }

    @android.annotation.SuppressLint({"VisibleForTests"})
    static class b implements androidx.recyclerview.widget.RecyclerView.o.c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f301a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f302b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int[] f303c;
        int d;

        b() {
        }

        void a() {
            int[] iArr = this.f303c;
            if (iArr != null) {
                java.util.Arrays.fill(iArr, -1);
            }
            this.d = 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.o.c
        public void a(int i, int i2) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Layout positions must be non-negative");
            }
            if (i2 < 0) {
                throw new java.lang.IllegalArgumentException("Pixel distance must be non-negative");
            }
            int i3 = this.d * 2;
            int[] iArr = this.f303c;
            if (iArr == null) {
                int[] iArr2 = new int[4];
                this.f303c = iArr2;
                java.util.Arrays.fill(iArr2, -1);
            } else if (i3 >= iArr.length) {
                int[] iArr3 = new int[i3 * 2];
                this.f303c = iArr3;
                java.lang.System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            }
            int[] iArr4 = this.f303c;
            iArr4[i3] = i;
            iArr4[i3 + 1] = i2;
            this.d++;
        }

        void a(androidx.recyclerview.widget.RecyclerView recyclerView, boolean z) {
            this.d = 0;
            int[] iArr = this.f303c;
            if (iArr != null) {
                java.util.Arrays.fill(iArr, -1);
            }
            androidx.recyclerview.widget.RecyclerView.o oVar = recyclerView.m;
            if (recyclerView.l == null || oVar == null || !oVar.v()) {
                return;
            }
            if (z) {
                if (!recyclerView.d.c()) {
                    oVar.a(recyclerView.l.a(), this);
                }
            } else if (!recyclerView.j()) {
                oVar.a(this.f301a, this.f302b, recyclerView.h0, this);
            }
            int i = this.d;
            if (i > oVar.m) {
                oVar.m = i;
                oVar.n = z;
                recyclerView.f192b.j();
            }
        }

        boolean a(int i) {
            if (this.f303c != null) {
                int i2 = this.d * 2;
                for (int i3 = 0; i3 < i2; i3 += 2) {
                    if (this.f303c[i3] == i) {
                        return true;
                    }
                }
            }
            return false;
        }

        void b(int i, int i2) {
            this.f301a = i;
            this.f302b = i2;
        }
    }

    static class c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public boolean f304a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f305b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f306c;
        public androidx.recyclerview.widget.RecyclerView d;
        public int e;

        c() {
        }

        public void a() {
            this.f304a = false;
            this.f305b = 0;
            this.f306c = 0;
            this.d = null;
            this.e = 0;
        }
    }

    e() {
    }

    private androidx.recyclerview.widget.RecyclerView.d0 a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, long j) {
        if (a(recyclerView, i)) {
            return null;
        }
        androidx.recyclerview.widget.RecyclerView.v vVar = recyclerView.f192b;
        try {
            recyclerView.q();
            androidx.recyclerview.widget.RecyclerView.d0 d0VarA = vVar.a(i, false, j);
            if (d0VarA != null) {
                if (!d0VarA.n() || d0VarA.o()) {
                    vVar.a(d0VarA, false);
                } else {
                    vVar.b(d0VarA.f203a);
                }
            }
            return d0VarA;
        } finally {
            recyclerView.a(false);
        }
    }

    private void a() {
        androidx.recyclerview.widget.e.c cVar;
        int size = this.f298a.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            androidx.recyclerview.widget.RecyclerView recyclerView = this.f298a.get(i2);
            if (recyclerView.getWindowVisibility() == 0) {
                recyclerView.g0.a(recyclerView, false);
                i += recyclerView.g0.d;
            }
        }
        this.d.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            androidx.recyclerview.widget.RecyclerView recyclerView2 = this.f298a.get(i4);
            if (recyclerView2.getWindowVisibility() == 0) {
                androidx.recyclerview.widget.e.b bVar = recyclerView2.g0;
                int iAbs = java.lang.Math.abs(bVar.f301a) + java.lang.Math.abs(bVar.f302b);
                for (int i5 = 0; i5 < bVar.d * 2; i5 += 2) {
                    if (i3 >= this.d.size()) {
                        cVar = new androidx.recyclerview.widget.e.c();
                        this.d.add(cVar);
                    } else {
                        cVar = this.d.get(i3);
                    }
                    int i6 = bVar.f303c[i5 + 1];
                    cVar.f304a = i6 <= iAbs;
                    cVar.f305b = iAbs;
                    cVar.f306c = i6;
                    cVar.d = recyclerView2;
                    cVar.e = bVar.f303c[i5];
                    i3++;
                }
            }
        }
        java.util.Collections.sort(this.d, f);
    }

    private void a(androidx.recyclerview.widget.RecyclerView recyclerView, long j) {
        if (recyclerView == null) {
            return;
        }
        if (recyclerView.D && recyclerView.e.b() != 0) {
            recyclerView.t();
        }
        androidx.recyclerview.widget.e.b bVar = recyclerView.g0;
        bVar.a(recyclerView, true);
        if (bVar.d != 0) {
            try {
                a.c.c.a.a("RV Nested Prefetch");
                recyclerView.h0.a(recyclerView.l);
                for (int i = 0; i < bVar.d * 2; i += 2) {
                    a(recyclerView, bVar.f303c[i], j);
                }
                a.c.c.a.a();
            } catch (java.lang.Throwable th) {
                a.c.c.a.a();
                throw th;
            }
        }
    }

    private void a(androidx.recyclerview.widget.e.c cVar, long j) {
        androidx.recyclerview.widget.RecyclerView.d0 d0VarA = a(cVar.d, cVar.e, cVar.f304a ? Long.MAX_VALUE : j);
        if (d0VarA == null || d0VarA.f204b == null || !d0VarA.n() || d0VarA.o()) {
            return;
        }
        a(d0VarA.f204b.get(), j);
    }

    static boolean a(androidx.recyclerview.widget.RecyclerView recyclerView, int i) {
        int iB = recyclerView.e.b();
        for (int i2 = 0; i2 < iB; i2++) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarK = androidx.recyclerview.widget.RecyclerView.k(recyclerView.e.d(i2));
            if (d0VarK.f205c == i && !d0VarK.o()) {
                return true;
            }
        }
        return false;
    }

    private void b(long j) {
        for (int i = 0; i < this.d.size(); i++) {
            androidx.recyclerview.widget.e.c cVar = this.d.get(i);
            if (cVar.d == null) {
                return;
            }
            a(cVar, j);
            cVar.a();
        }
    }

    void a(long j) {
        a();
        b(j);
    }

    public void a(androidx.recyclerview.widget.RecyclerView recyclerView) {
        this.f298a.add(recyclerView);
    }

    void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow() && this.f299b == 0) {
            this.f299b = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.g0.b(i, i2);
    }

    public void b(androidx.recyclerview.widget.RecyclerView recyclerView) {
        this.f298a.remove(recyclerView);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a.c.c.a.a("RV Prefetch");
            if (!this.f298a.isEmpty()) {
                int size = this.f298a.size();
                long jMax = 0;
                for (int i = 0; i < size; i++) {
                    androidx.recyclerview.widget.RecyclerView recyclerView = this.f298a.get(i);
                    if (recyclerView.getWindowVisibility() == 0) {
                        jMax = java.lang.Math.max(recyclerView.getDrawingTime(), jMax);
                    }
                }
                if (jMax != 0) {
                    a(java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(jMax) + this.f300c);
                }
            }
        } finally {
            this.f299b = 0L;
            a.c.c.a.a();
        }
    }
}
