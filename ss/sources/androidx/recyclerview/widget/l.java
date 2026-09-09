package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public class l extends a.c.e.a {
    final androidx.recyclerview.widget.RecyclerView d;
    private final androidx.recyclerview.widget.l.a e;

    public static class a extends a.c.e.a {
        final androidx.recyclerview.widget.l d;
        private java.util.Map<android.view.View, a.c.e.a> e = new java.util.WeakHashMap();

        public a(androidx.recyclerview.widget.l lVar) {
            this.d = lVar;
        }

        @Override // a.c.e.a
        public a.c.e.q.d a(android.view.View view) {
            a.c.e.a aVar = this.e.get(view);
            return aVar != null ? aVar.a(view) : super.a(view);
        }

        @Override // a.c.e.a
        public void a(android.view.View view, int i) {
            a.c.e.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.a(view, i);
            } else {
                super.a(view, i);
            }
        }

        @Override // a.c.e.a
        public void a(android.view.View view, a.c.e.q.c cVar) {
            if (!this.d.c() && this.d.d.getLayoutManager() != null) {
                this.d.d.getLayoutManager().a(view, cVar);
                a.c.e.a aVar = this.e.get(view);
                if (aVar != null) {
                    aVar.a(view, cVar);
                    return;
                }
            }
            super.a(view, cVar);
        }

        @Override // a.c.e.a
        public boolean a(android.view.View view, int i, android.os.Bundle bundle) {
            if (this.d.c() || this.d.d.getLayoutManager() == null) {
                return super.a(view, i, bundle);
            }
            a.c.e.a aVar = this.e.get(view);
            if (aVar != null) {
                if (aVar.a(view, i, bundle)) {
                    return true;
                }
            } else if (super.a(view, i, bundle)) {
                return true;
            }
            return this.d.d.getLayoutManager().a(view, i, bundle);
        }

        @Override // a.c.e.a
        public boolean a(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            a.c.e.a aVar = this.e.get(view);
            return aVar != null ? aVar.a(view, accessibilityEvent) : super.a(view, accessibilityEvent);
        }

        @Override // a.c.e.a
        public boolean a(android.view.ViewGroup viewGroup, android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            a.c.e.a aVar = this.e.get(viewGroup);
            return aVar != null ? aVar.a(viewGroup, view, accessibilityEvent) : super.a(viewGroup, view, accessibilityEvent);
        }

        @Override // a.c.e.a
        public void b(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            a.c.e.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.b(view, accessibilityEvent);
            } else {
                super.b(view, accessibilityEvent);
            }
        }

        a.c.e.a c(android.view.View view) {
            return this.e.remove(view);
        }

        @Override // a.c.e.a
        public void c(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            a.c.e.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.c(view, accessibilityEvent);
            } else {
                super.c(view, accessibilityEvent);
            }
        }

        void d(android.view.View view) {
            a.c.e.a aVarA = a.c.e.m.a(view);
            if (aVarA == null || aVarA == this) {
                return;
            }
            this.e.put(view, aVarA);
        }

        @Override // a.c.e.a
        public void d(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            a.c.e.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.d(view, accessibilityEvent);
            } else {
                super.d(view, accessibilityEvent);
            }
        }
    }

    public l(androidx.recyclerview.widget.RecyclerView recyclerView) {
        this.d = recyclerView;
        a.c.e.a aVarB = b();
        this.e = (aVarB == null || !(aVarB instanceof androidx.recyclerview.widget.l.a)) ? new androidx.recyclerview.widget.l.a(this) : (androidx.recyclerview.widget.l.a) aVarB;
    }

    @Override // a.c.e.a
    public void a(android.view.View view, a.c.e.q.c cVar) {
        super.a(view, cVar);
        if (c() || this.d.getLayoutManager() == null) {
            return;
        }
        this.d.getLayoutManager().a(cVar);
    }

    @Override // a.c.e.a
    public boolean a(android.view.View view, int i, android.os.Bundle bundle) {
        if (super.a(view, i, bundle)) {
            return true;
        }
        if (c() || this.d.getLayoutManager() == null) {
            return false;
        }
        return this.d.getLayoutManager().a(i, bundle);
    }

    public a.c.e.a b() {
        return this.e;
    }

    @Override // a.c.e.a
    public void b(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.b(view, accessibilityEvent);
        if (!(view instanceof androidx.recyclerview.widget.RecyclerView) || c()) {
            return;
        }
        androidx.recyclerview.widget.RecyclerView recyclerView = (androidx.recyclerview.widget.RecyclerView) view;
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().a(accessibilityEvent);
        }
    }

    boolean c() {
        return this.d.j();
    }
}
