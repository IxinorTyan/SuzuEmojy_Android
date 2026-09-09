package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final androidx.recyclerview.widget.b.InterfaceC0012b f258a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final androidx.recyclerview.widget.b.a f259b = new androidx.recyclerview.widget.b.a();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final java.util.List<android.view.View> f260c = new java.util.ArrayList();

    static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        long f261a = 0;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        androidx.recyclerview.widget.b.a f262b;

        a() {
        }

        private void b() {
            if (this.f262b == null) {
                this.f262b = new androidx.recyclerview.widget.b.a();
            }
        }

        void a() {
            this.f261a = 0L;
            androidx.recyclerview.widget.b.a aVar = this.f262b;
            if (aVar != null) {
                aVar.a();
            }
        }

        void a(int i) {
            if (i < 64) {
                this.f261a &= ~(1 << i);
                return;
            }
            androidx.recyclerview.widget.b.a aVar = this.f262b;
            if (aVar != null) {
                aVar.a(i - 64);
            }
        }

        void a(int i, boolean z) {
            if (i >= 64) {
                b();
                this.f262b.a(i - 64, z);
                return;
            }
            boolean z2 = (this.f261a & Long.MIN_VALUE) != 0;
            long j = (1 << i) - 1;
            long j2 = this.f261a;
            this.f261a = ((j2 & (~j)) << 1) | (j2 & j);
            if (z) {
                e(i);
            } else {
                a(i);
            }
            if (z2 || this.f262b != null) {
                b();
                this.f262b.a(0, z2);
            }
        }

        int b(int i) {
            androidx.recyclerview.widget.b.a aVar = this.f262b;
            if (aVar == null) {
                return i >= 64 ? java.lang.Long.bitCount(this.f261a) : java.lang.Long.bitCount(this.f261a & ((1 << i) - 1));
            }
            return i < 64 ? java.lang.Long.bitCount(this.f261a & ((1 << i) - 1)) : aVar.b(i - 64) + java.lang.Long.bitCount(this.f261a);
        }

        boolean c(int i) {
            if (i < 64) {
                return (this.f261a & (1 << i)) != 0;
            }
            b();
            return this.f262b.c(i - 64);
        }

        boolean d(int i) {
            if (i >= 64) {
                b();
                return this.f262b.d(i - 64);
            }
            long j = 1 << i;
            boolean z = (this.f261a & j) != 0;
            long j2 = this.f261a & (~j);
            this.f261a = j2;
            long j3 = j - 1;
            this.f261a = (j2 & j3) | java.lang.Long.rotateRight((~j3) & j2, 1);
            androidx.recyclerview.widget.b.a aVar = this.f262b;
            if (aVar != null) {
                if (aVar.c(0)) {
                    e(63);
                }
                this.f262b.d(0);
            }
            return z;
        }

        void e(int i) {
            if (i < 64) {
                this.f261a |= 1 << i;
            } else {
                b();
                this.f262b.e(i - 64);
            }
        }

        public java.lang.String toString() {
            if (this.f262b == null) {
                return java.lang.Long.toBinaryString(this.f261a);
            }
            return this.f262b.toString() + "xx" + java.lang.Long.toBinaryString(this.f261a);
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.b$b, reason: collision with other inner class name */
    interface InterfaceC0012b {
        int a();

        android.view.View a(int i);

        void a(android.view.View view);

        void a(android.view.View view, int i);

        void a(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams);

        int b(android.view.View view);

        void b();

        void b(int i);

        void c(int i);

        void c(android.view.View view);

        androidx.recyclerview.widget.RecyclerView.d0 d(android.view.View view);
    }

    b(androidx.recyclerview.widget.b.InterfaceC0012b interfaceC0012b) {
        this.f258a = interfaceC0012b;
    }

    private int f(int i) {
        if (i < 0) {
            return -1;
        }
        int iA = this.f258a.a();
        int i2 = i;
        while (i2 < iA) {
            int iB = i - (i2 - this.f259b.b(i2));
            if (iB == 0) {
                while (this.f259b.c(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += iB;
        }
        return -1;
    }

    private void g(android.view.View view) {
        this.f260c.add(view);
        this.f258a.c(view);
    }

    private boolean h(android.view.View view) {
        if (!this.f260c.remove(view)) {
            return false;
        }
        this.f258a.a(view);
        return true;
    }

    int a() {
        return this.f258a.a() - this.f260c.size();
    }

    void a(int i) {
        int iF = f(i);
        this.f259b.d(iF);
        this.f258a.b(iF);
    }

    void a(android.view.View view) {
        int iB = this.f258a.b(view);
        if (iB >= 0) {
            this.f259b.e(iB);
            g(view);
        } else {
            throw new java.lang.IllegalArgumentException("view is not a child, cannot hide " + view);
        }
    }

    void a(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams, boolean z) {
        int iA = i < 0 ? this.f258a.a() : f(i);
        this.f259b.a(iA, z);
        if (z) {
            g(view);
        }
        this.f258a.a(view, iA, layoutParams);
    }

    void a(android.view.View view, int i, boolean z) {
        int iA = i < 0 ? this.f258a.a() : f(i);
        this.f259b.a(iA, z);
        if (z) {
            g(view);
        }
        this.f258a.a(view, iA);
    }

    void a(android.view.View view, boolean z) {
        a(view, -1, z);
    }

    int b() {
        return this.f258a.a();
    }

    int b(android.view.View view) {
        int iB = this.f258a.b(view);
        if (iB == -1 || this.f259b.c(iB)) {
            return -1;
        }
        return iB - this.f259b.b(iB);
    }

    android.view.View b(int i) {
        int size = this.f260c.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.View view = this.f260c.get(i2);
            androidx.recyclerview.widget.RecyclerView.d0 d0VarD = this.f258a.d(view);
            if (d0VarD.i() == i && !d0VarD.o() && !d0VarD.q()) {
                return view;
            }
        }
        return null;
    }

    android.view.View c(int i) {
        return this.f258a.a(f(i));
    }

    void c() {
        this.f259b.a();
        for (int size = this.f260c.size() - 1; size >= 0; size--) {
            this.f258a.a(this.f260c.get(size));
            this.f260c.remove(size);
        }
        this.f258a.b();
    }

    boolean c(android.view.View view) {
        return this.f260c.contains(view);
    }

    android.view.View d(int i) {
        return this.f258a.a(i);
    }

    void d(android.view.View view) {
        int iB = this.f258a.b(view);
        if (iB < 0) {
            return;
        }
        if (this.f259b.d(iB)) {
            h(view);
        }
        this.f258a.c(iB);
    }

    void e(int i) {
        int iF = f(i);
        android.view.View viewA = this.f258a.a(iF);
        if (viewA == null) {
            return;
        }
        if (this.f259b.d(iF)) {
            h(viewA);
        }
        this.f258a.c(iF);
    }

    boolean e(android.view.View view) {
        int iB = this.f258a.b(view);
        if (iB == -1) {
            h(view);
            return true;
        }
        if (!this.f259b.c(iB)) {
            return false;
        }
        this.f259b.d(iB);
        h(view);
        this.f258a.c(iB);
        return true;
    }

    void f(android.view.View view) {
        int iB = this.f258a.b(view);
        if (iB < 0) {
            throw new java.lang.IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        if (this.f259b.c(iB)) {
            this.f259b.a(iB);
            h(view);
        } else {
            throw new java.lang.RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }

    public java.lang.String toString() {
        return this.f259b.toString() + ", hidden list:" + this.f260c.size();
    }
}
