package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final androidx.recyclerview.widget.o.b f333a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    androidx.recyclerview.widget.o.a f334b = new androidx.recyclerview.widget.o.a();

    static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f335a = 0;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f336b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f337c;
        int d;
        int e;

        a() {
        }

        int a(int i, int i2) {
            if (i > i2) {
                return 1;
            }
            return i == i2 ? 2 : 4;
        }

        void a(int i) {
            this.f335a = i | this.f335a;
        }

        void a(int i, int i2, int i3, int i4) {
            this.f336b = i;
            this.f337c = i2;
            this.d = i3;
            this.e = i4;
        }

        boolean a() {
            int i = this.f335a;
            if ((i & 7) != 0 && (i & (a(this.d, this.f336b) << 0)) == 0) {
                return false;
            }
            int i2 = this.f335a;
            if ((i2 & 112) != 0 && (i2 & (a(this.d, this.f337c) << 4)) == 0) {
                return false;
            }
            int i3 = this.f335a;
            if ((i3 & 1792) != 0 && (i3 & (a(this.e, this.f336b) << 8)) == 0) {
                return false;
            }
            int i4 = this.f335a;
            return (i4 & 28672) == 0 || (i4 & (a(this.e, this.f337c) << 12)) != 0;
        }

        void b() {
            this.f335a = 0;
        }
    }

    interface b {
        int a();

        int a(android.view.View view);

        android.view.View a(int i);

        int b();

        int b(android.view.View view);
    }

    o(androidx.recyclerview.widget.o.b bVar) {
        this.f333a = bVar;
    }

    android.view.View a(int i, int i2, int i3, int i4) {
        int iB = this.f333a.b();
        int iA = this.f333a.a();
        int i5 = i2 > i ? 1 : -1;
        android.view.View view = null;
        while (i != i2) {
            android.view.View viewA = this.f333a.a(i);
            this.f334b.a(iB, iA, this.f333a.a(viewA), this.f333a.b(viewA));
            if (i3 != 0) {
                this.f334b.b();
                this.f334b.a(i3);
                if (this.f334b.a()) {
                    return viewA;
                }
            }
            if (i4 != 0) {
                this.f334b.b();
                this.f334b.a(i4);
                if (this.f334b.a()) {
                    view = viewA;
                }
            }
            i += i5;
        }
        return view;
    }

    boolean a(android.view.View view, int i) {
        this.f334b.a(this.f333a.b(), this.f333a.a(), this.f333a.a(view), this.f333a.b(view));
        if (i == 0) {
            return false;
        }
        this.f334b.b();
        this.f334b.a(i);
        return this.f334b.a();
    }
}
