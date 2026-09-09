package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public abstract class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected final androidx.recyclerview.widget.RecyclerView.o f330a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f331b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final android.graphics.Rect f332c;

    static class a extends androidx.recyclerview.widget.k {
        a(androidx.recyclerview.widget.RecyclerView.o oVar) {
            super(oVar, null);
        }

        @Override // androidx.recyclerview.widget.k
        public int a() {
            return this.f330a.q();
        }

        @Override // androidx.recyclerview.widget.k
        public int a(android.view.View view) {
            return this.f330a.i(view) + ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).rightMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public void a(int i) {
            this.f330a.d(i);
        }

        @Override // androidx.recyclerview.widget.k
        public int b() {
            return this.f330a.q() - this.f330a.o();
        }

        @Override // androidx.recyclerview.widget.k
        public int b(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            return this.f330a.h(view) + ((android.view.ViewGroup.MarginLayoutParams) pVar).leftMargin + ((android.view.ViewGroup.MarginLayoutParams) pVar).rightMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public int c() {
            return this.f330a.o();
        }

        @Override // androidx.recyclerview.widget.k
        public int c(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            return this.f330a.g(view) + ((android.view.ViewGroup.MarginLayoutParams) pVar).topMargin + ((android.view.ViewGroup.MarginLayoutParams) pVar).bottomMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public int d() {
            return this.f330a.r();
        }

        @Override // androidx.recyclerview.widget.k
        public int d(android.view.View view) {
            return this.f330a.f(view) - ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).leftMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public int e() {
            return this.f330a.i();
        }

        @Override // androidx.recyclerview.widget.k
        public int e(android.view.View view) {
            this.f330a.a(view, true, this.f332c);
            return this.f332c.right;
        }

        @Override // androidx.recyclerview.widget.k
        public int f() {
            return this.f330a.n();
        }

        @Override // androidx.recyclerview.widget.k
        public int f(android.view.View view) {
            this.f330a.a(view, true, this.f332c);
            return this.f332c.left;
        }

        @Override // androidx.recyclerview.widget.k
        public int g() {
            return (this.f330a.q() - this.f330a.n()) - this.f330a.o();
        }
    }

    static class b extends androidx.recyclerview.widget.k {
        b(androidx.recyclerview.widget.RecyclerView.o oVar) {
            super(oVar, null);
        }

        @Override // androidx.recyclerview.widget.k
        public int a() {
            return this.f330a.h();
        }

        @Override // androidx.recyclerview.widget.k
        public int a(android.view.View view) {
            return this.f330a.e(view) + ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).bottomMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public void a(int i) {
            this.f330a.e(i);
        }

        @Override // androidx.recyclerview.widget.k
        public int b() {
            return this.f330a.h() - this.f330a.m();
        }

        @Override // androidx.recyclerview.widget.k
        public int b(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            return this.f330a.g(view) + ((android.view.ViewGroup.MarginLayoutParams) pVar).topMargin + ((android.view.ViewGroup.MarginLayoutParams) pVar).bottomMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public int c() {
            return this.f330a.m();
        }

        @Override // androidx.recyclerview.widget.k
        public int c(android.view.View view) {
            androidx.recyclerview.widget.RecyclerView.p pVar = (androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams();
            return this.f330a.h(view) + ((android.view.ViewGroup.MarginLayoutParams) pVar).leftMargin + ((android.view.ViewGroup.MarginLayoutParams) pVar).rightMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public int d() {
            return this.f330a.i();
        }

        @Override // androidx.recyclerview.widget.k
        public int d(android.view.View view) {
            return this.f330a.j(view) - ((android.view.ViewGroup.MarginLayoutParams) ((androidx.recyclerview.widget.RecyclerView.p) view.getLayoutParams())).topMargin;
        }

        @Override // androidx.recyclerview.widget.k
        public int e() {
            return this.f330a.r();
        }

        @Override // androidx.recyclerview.widget.k
        public int e(android.view.View view) {
            this.f330a.a(view, true, this.f332c);
            return this.f332c.bottom;
        }

        @Override // androidx.recyclerview.widget.k
        public int f() {
            return this.f330a.p();
        }

        @Override // androidx.recyclerview.widget.k
        public int f(android.view.View view) {
            this.f330a.a(view, true, this.f332c);
            return this.f332c.top;
        }

        @Override // androidx.recyclerview.widget.k
        public int g() {
            return (this.f330a.h() - this.f330a.p()) - this.f330a.m();
        }
    }

    private k(androidx.recyclerview.widget.RecyclerView.o oVar) {
        this.f331b = Integer.MIN_VALUE;
        this.f332c = new android.graphics.Rect();
        this.f330a = oVar;
    }

    /* synthetic */ k(androidx.recyclerview.widget.RecyclerView.o oVar, androidx.recyclerview.widget.k.a aVar) {
        this(oVar);
    }

    public static androidx.recyclerview.widget.k a(androidx.recyclerview.widget.RecyclerView.o oVar) {
        return new androidx.recyclerview.widget.k.a(oVar);
    }

    public static androidx.recyclerview.widget.k a(androidx.recyclerview.widget.RecyclerView.o oVar, int i) {
        if (i == 0) {
            return a(oVar);
        }
        if (i == 1) {
            return b(oVar);
        }
        throw new java.lang.IllegalArgumentException("invalid orientation");
    }

    public static androidx.recyclerview.widget.k b(androidx.recyclerview.widget.RecyclerView.o oVar) {
        return new androidx.recyclerview.widget.k.b(oVar);
    }

    public abstract int a();

    public abstract int a(android.view.View view);

    public abstract void a(int i);

    public abstract int b();

    public abstract int b(android.view.View view);

    public abstract int c();

    public abstract int c(android.view.View view);

    public abstract int d();

    public abstract int d(android.view.View view);

    public abstract int e();

    public abstract int e(android.view.View view);

    public abstract int f();

    public abstract int f(android.view.View view);

    public abstract int g();

    public int h() {
        if (Integer.MIN_VALUE == this.f331b) {
            return 0;
        }
        return g() - this.f331b;
    }

    public void i() {
        this.f331b = g();
    }
}
