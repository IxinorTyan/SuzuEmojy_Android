package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public abstract class n extends androidx.recyclerview.widget.RecyclerView.l {
    boolean g = true;

    public final void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, boolean z) {
        c(d0Var, z);
        b(d0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        return !this.g || d0Var.o();
    }

    public abstract boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, int i2, int i3, int i4);

    public abstract boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2, int i, int i2, int i3, int i4);

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
        int i;
        int i2;
        int i3 = cVar.f213a;
        int i4 = cVar.f214b;
        if (d0Var2.y()) {
            int i5 = cVar.f213a;
            i2 = cVar.f214b;
            i = i5;
        } else {
            i = cVar2.f213a;
            i2 = cVar2.f214b;
        }
        return a(d0Var, d0Var2, i3, i4, i, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
        return (cVar == null || (cVar.f213a == cVar2.f213a && cVar.f214b == cVar2.f214b)) ? f(d0Var) : a(d0Var, cVar.f213a, cVar.f214b, cVar2.f213a, cVar2.f214b);
    }

    public final void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, boolean z) {
        d(d0Var, z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
        int i = cVar.f213a;
        int i2 = cVar.f214b;
        android.view.View view = d0Var.f203a;
        int left = cVar2 == null ? view.getLeft() : cVar2.f213a;
        int top = cVar2 == null ? view.getTop() : cVar2.f214b;
        if (d0Var.q() || (i == left && i2 == top)) {
            return g(d0Var);
        }
        view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
        return a(d0Var, i, i2, left, top);
    }

    public void c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, boolean z) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2) {
        if (cVar.f213a != cVar2.f213a || cVar.f214b != cVar2.f214b) {
            return a(d0Var, cVar.f213a, cVar.f214b, cVar2.f213a, cVar2.f214b);
        }
        j(d0Var);
        return false;
    }

    public void d(androidx.recyclerview.widget.RecyclerView.d0 d0Var, boolean z) {
    }

    public abstract boolean f(androidx.recyclerview.widget.RecyclerView.d0 d0Var);

    public abstract boolean g(androidx.recyclerview.widget.RecyclerView.d0 d0Var);

    public final void h(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        n(d0Var);
        b(d0Var);
    }

    public final void i(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        o(d0Var);
    }

    public final void j(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        p(d0Var);
        b(d0Var);
    }

    public final void k(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        q(d0Var);
    }

    public final void l(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        r(d0Var);
        b(d0Var);
    }

    public final void m(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        s(d0Var);
    }

    public void n(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
    }

    public void o(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
    }

    public void p(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
    }

    public void q(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
    }

    public void r(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
    }

    public void s(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
    }
}
