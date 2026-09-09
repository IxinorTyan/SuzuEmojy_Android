package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public class c extends androidx.recyclerview.widget.n {
    private static android.animation.TimeInterpolator s;
    private java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> h = new java.util.ArrayList<>();
    private java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> i = new java.util.ArrayList<>();
    private java.util.ArrayList<androidx.recyclerview.widget.c.j> j = new java.util.ArrayList<>();
    private java.util.ArrayList<androidx.recyclerview.widget.c.i> k = new java.util.ArrayList<>();
    java.util.ArrayList<java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0>> l = new java.util.ArrayList<>();
    java.util.ArrayList<java.util.ArrayList<androidx.recyclerview.widget.c.j>> m = new java.util.ArrayList<>();
    java.util.ArrayList<java.util.ArrayList<androidx.recyclerview.widget.c.i>> n = new java.util.ArrayList<>();
    java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> o = new java.util.ArrayList<>();
    java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> p = new java.util.ArrayList<>();
    java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> q = new java.util.ArrayList<>();
    java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> r = new java.util.ArrayList<>();

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.util.ArrayList f263a;

        a(java.util.ArrayList arrayList) {
            this.f263a = arrayList;
        }

        @Override // java.lang.Runnable
        public void run() {
            for (androidx.recyclerview.widget.c.j jVar : this.f263a) {
                androidx.recyclerview.widget.c.this.b(jVar.f287a, jVar.f288b, jVar.f289c, jVar.d, jVar.e);
            }
            this.f263a.clear();
            androidx.recyclerview.widget.c.this.m.remove(this.f263a);
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.util.ArrayList f265a;

        b(java.util.ArrayList arrayList) {
            this.f265a = arrayList;
        }

        @Override // java.lang.Runnable
        public void run() {
            java.util.Iterator it = this.f265a.iterator();
            while (it.hasNext()) {
                androidx.recyclerview.widget.c.this.a((androidx.recyclerview.widget.c.i) it.next());
            }
            this.f265a.clear();
            androidx.recyclerview.widget.c.this.n.remove(this.f265a);
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.c$c, reason: collision with other inner class name */
    class RunnableC0013c implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.util.ArrayList f267a;

        RunnableC0013c(java.util.ArrayList arrayList) {
            this.f267a = arrayList;
        }

        @Override // java.lang.Runnable
        public void run() {
            java.util.Iterator it = this.f267a.iterator();
            while (it.hasNext()) {
                androidx.recyclerview.widget.c.this.t((androidx.recyclerview.widget.RecyclerView.d0) it.next());
            }
            this.f267a.clear();
            androidx.recyclerview.widget.c.this.l.remove(this.f267a);
        }
    }

    class d extends android.animation.AnimatorListenerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.RecyclerView.d0 f269a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.ViewPropertyAnimator f270b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.View f271c;

        d(androidx.recyclerview.widget.RecyclerView.d0 d0Var, android.view.ViewPropertyAnimator viewPropertyAnimator, android.view.View view) {
            this.f269a = d0Var;
            this.f270b = viewPropertyAnimator;
            this.f271c = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            this.f270b.setListener(null);
            this.f271c.setAlpha(1.0f);
            androidx.recyclerview.widget.c.this.l(this.f269a);
            androidx.recyclerview.widget.c.this.q.remove(this.f269a);
            androidx.recyclerview.widget.c.this.j();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            androidx.recyclerview.widget.c.this.m(this.f269a);
        }
    }

    class e extends android.animation.AnimatorListenerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.RecyclerView.d0 f272a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f273b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.ViewPropertyAnimator f274c;

        e(androidx.recyclerview.widget.RecyclerView.d0 d0Var, android.view.View view, android.view.ViewPropertyAnimator viewPropertyAnimator) {
            this.f272a = d0Var;
            this.f273b = view;
            this.f274c = viewPropertyAnimator;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            this.f273b.setAlpha(1.0f);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            this.f274c.setListener(null);
            androidx.recyclerview.widget.c.this.h(this.f272a);
            androidx.recyclerview.widget.c.this.o.remove(this.f272a);
            androidx.recyclerview.widget.c.this.j();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            androidx.recyclerview.widget.c.this.i(this.f272a);
        }
    }

    class f extends android.animation.AnimatorListenerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.RecyclerView.d0 f275a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f276b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.View f277c;
        final /* synthetic */ int d;
        final /* synthetic */ android.view.ViewPropertyAnimator e;

        f(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, android.view.View view, int i2, android.view.ViewPropertyAnimator viewPropertyAnimator) {
            this.f275a = d0Var;
            this.f276b = i;
            this.f277c = view;
            this.d = i2;
            this.e = viewPropertyAnimator;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            if (this.f276b != 0) {
                this.f277c.setTranslationX(0.0f);
            }
            if (this.d != 0) {
                this.f277c.setTranslationY(0.0f);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            this.e.setListener(null);
            androidx.recyclerview.widget.c.this.j(this.f275a);
            androidx.recyclerview.widget.c.this.p.remove(this.f275a);
            androidx.recyclerview.widget.c.this.j();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            androidx.recyclerview.widget.c.this.k(this.f275a);
        }
    }

    class g extends android.animation.AnimatorListenerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.c.i f278a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.ViewPropertyAnimator f279b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.View f280c;

        g(androidx.recyclerview.widget.c.i iVar, android.view.ViewPropertyAnimator viewPropertyAnimator, android.view.View view) {
            this.f278a = iVar;
            this.f279b = viewPropertyAnimator;
            this.f280c = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            this.f279b.setListener(null);
            this.f280c.setAlpha(1.0f);
            this.f280c.setTranslationX(0.0f);
            this.f280c.setTranslationY(0.0f);
            androidx.recyclerview.widget.c.this.a(this.f278a.f284a, true);
            androidx.recyclerview.widget.c.this.r.remove(this.f278a.f284a);
            androidx.recyclerview.widget.c.this.j();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            androidx.recyclerview.widget.c.this.b(this.f278a.f284a, true);
        }
    }

    class h extends android.animation.AnimatorListenerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.c.i f281a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.ViewPropertyAnimator f282b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.View f283c;

        h(androidx.recyclerview.widget.c.i iVar, android.view.ViewPropertyAnimator viewPropertyAnimator, android.view.View view) {
            this.f281a = iVar;
            this.f282b = viewPropertyAnimator;
            this.f283c = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            this.f282b.setListener(null);
            this.f283c.setAlpha(1.0f);
            this.f283c.setTranslationX(0.0f);
            this.f283c.setTranslationY(0.0f);
            androidx.recyclerview.widget.c.this.a(this.f281a.f285b, false);
            androidx.recyclerview.widget.c.this.r.remove(this.f281a.f285b);
            androidx.recyclerview.widget.c.this.j();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            androidx.recyclerview.widget.c.this.b(this.f281a.f285b, false);
        }
    }

    private static class i {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public androidx.recyclerview.widget.RecyclerView.d0 f284a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public androidx.recyclerview.widget.RecyclerView.d0 f285b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f286c;
        public int d;
        public int e;
        public int f;

        private i(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2) {
            this.f284a = d0Var;
            this.f285b = d0Var2;
        }

        i(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2, int i, int i2, int i3, int i4) {
            this(d0Var, d0Var2);
            this.f286c = i;
            this.d = i2;
            this.e = i3;
            this.f = i4;
        }

        public java.lang.String toString() {
            return "ChangeInfo{oldHolder=" + this.f284a + ", newHolder=" + this.f285b + ", fromX=" + this.f286c + ", fromY=" + this.d + ", toX=" + this.e + ", toY=" + this.f + '}';
        }
    }

    private static class j {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public androidx.recyclerview.widget.RecyclerView.d0 f287a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f288b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f289c;
        public int d;
        public int e;

        j(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i, int i2, int i3, int i4) {
            this.f287a = d0Var;
            this.f288b = i;
            this.f289c = i2;
            this.d = i3;
            this.e = i4;
        }
    }

    private void a(java.util.List<androidx.recyclerview.widget.c.i> list, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        for (int size = list.size() - 1; size >= 0; size--) {
            androidx.recyclerview.widget.c.i iVar = list.get(size);
            if (a(iVar, d0Var) && iVar.f284a == null && iVar.f285b == null) {
                list.remove(iVar);
            }
        }
    }

    private boolean a(androidx.recyclerview.widget.c.i iVar, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        boolean z = false;
        if (iVar.f285b == d0Var) {
            iVar.f285b = null;
        } else {
            if (iVar.f284a != d0Var) {
                return false;
            }
            iVar.f284a = null;
            z = true;
        }
        d0Var.f203a.setAlpha(1.0f);
        d0Var.f203a.setTranslationX(0.0f);
        d0Var.f203a.setTranslationY(0.0f);
        a(d0Var, z);
        return true;
    }

    private void b(androidx.recyclerview.widget.c.i iVar) {
        androidx.recyclerview.widget.RecyclerView.d0 d0Var = iVar.f284a;
        if (d0Var != null) {
            a(iVar, d0Var);
        }
        androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = iVar.f285b;
        if (d0Var2 != null) {
            a(iVar, d0Var2);
        }
    }

    private void u(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        android.view.View view = d0Var.f203a;
        android.view.ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        this.q.add(d0Var);
        viewPropertyAnimatorAnimate.setDuration(f()).alpha(0.0f).setListener(new androidx.recyclerview.widget.c.d(d0Var, viewPropertyAnimatorAnimate, view)).start();
    }

    private void v(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        if (s == null) {
            s = new android.animation.ValueAnimator().getInterpolator();
        }
        d0Var.f203a.animate().setInterpolator(s);
        c(d0Var);
    }

    void a(androidx.recyclerview.widget.c.i iVar) {
        androidx.recyclerview.widget.RecyclerView.d0 d0Var = iVar.f284a;
        android.view.View view = d0Var == null ? null : d0Var.f203a;
        androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = iVar.f285b;
        android.view.View view2 = d0Var2 != null ? d0Var2.f203a : null;
        if (view != null) {
            android.view.ViewPropertyAnimator duration = view.animate().setDuration(d());
            this.r.add(iVar.f284a);
            duration.translationX(iVar.e - iVar.f286c);
            duration.translationY(iVar.f - iVar.d);
            duration.alpha(0.0f).setListener(new androidx.recyclerview.widget.c.g(iVar, duration, view)).start();
        }
        if (view2 != null) {
            android.view.ViewPropertyAnimator viewPropertyAnimatorAnimate = view2.animate();
            this.r.add(iVar.f285b);
            viewPropertyAnimatorAnimate.translationX(0.0f).translationY(0.0f).setDuration(d()).alpha(1.0f).setListener(new androidx.recyclerview.widget.c.h(iVar, viewPropertyAnimatorAnimate, view2)).start();
        }
    }

    void a(java.util.List<androidx.recyclerview.widget.RecyclerView.d0> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).f203a.animate().cancel();
        }
    }

    @Override // androidx.recyclerview.widget.n
    public boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i2, int i3, int i4, int i5) {
        android.view.View view = d0Var.f203a;
        int translationX = i2 + ((int) view.getTranslationX());
        int translationY = i3 + ((int) d0Var.f203a.getTranslationY());
        v(d0Var);
        int i6 = i4 - translationX;
        int i7 = i5 - translationY;
        if (i6 == 0 && i7 == 0) {
            j(d0Var);
            return false;
        }
        if (i6 != 0) {
            view.setTranslationX(-i6);
        }
        if (i7 != 0) {
            view.setTranslationY(-i7);
        }
        this.j.add(new androidx.recyclerview.widget.c.j(d0Var, translationX, translationY, i4, i5));
        return true;
    }

    @Override // androidx.recyclerview.widget.n
    public boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2, int i2, int i3, int i4, int i5) {
        if (d0Var == d0Var2) {
            return a(d0Var, i2, i3, i4, i5);
        }
        float translationX = d0Var.f203a.getTranslationX();
        float translationY = d0Var.f203a.getTranslationY();
        float alpha = d0Var.f203a.getAlpha();
        v(d0Var);
        int i6 = (int) ((i4 - i2) - translationX);
        int i7 = (int) ((i5 - i3) - translationY);
        d0Var.f203a.setTranslationX(translationX);
        d0Var.f203a.setTranslationY(translationY);
        d0Var.f203a.setAlpha(alpha);
        if (d0Var2 != null) {
            v(d0Var2);
            d0Var2.f203a.setTranslationX(-i6);
            d0Var2.f203a.setTranslationY(-i7);
            d0Var2.f203a.setAlpha(0.0f);
        }
        this.k.add(new androidx.recyclerview.widget.c.i(d0Var, d0Var2, i2, i3, i4, i5));
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, java.util.List<java.lang.Object> list) {
        return !list.isEmpty() || super.a(d0Var, list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public void b() {
        int size = this.j.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            androidx.recyclerview.widget.c.j jVar = this.j.get(size);
            android.view.View view = jVar.f287a.f203a;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            j(jVar.f287a);
            this.j.remove(size);
        }
        for (int size2 = this.h.size() - 1; size2 >= 0; size2--) {
            l(this.h.get(size2));
            this.h.remove(size2);
        }
        int size3 = this.i.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            androidx.recyclerview.widget.RecyclerView.d0 d0Var = this.i.get(size3);
            d0Var.f203a.setAlpha(1.0f);
            h(d0Var);
            this.i.remove(size3);
        }
        for (int size4 = this.k.size() - 1; size4 >= 0; size4--) {
            b(this.k.get(size4));
        }
        this.k.clear();
        if (g()) {
            for (int size5 = this.m.size() - 1; size5 >= 0; size5--) {
                java.util.ArrayList<androidx.recyclerview.widget.c.j> arrayList = this.m.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    androidx.recyclerview.widget.c.j jVar2 = arrayList.get(size6);
                    android.view.View view2 = jVar2.f287a.f203a;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    j(jVar2.f287a);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.m.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.l.size() - 1; size7 >= 0; size7--) {
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList2 = this.l.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    androidx.recyclerview.widget.RecyclerView.d0 d0Var2 = arrayList2.get(size8);
                    d0Var2.f203a.setAlpha(1.0f);
                    h(d0Var2);
                    arrayList2.remove(size8);
                    if (arrayList2.isEmpty()) {
                        this.l.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.n.size() - 1; size9 >= 0; size9--) {
                java.util.ArrayList<androidx.recyclerview.widget.c.i> arrayList3 = this.n.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    b(arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.n.remove(arrayList3);
                    }
                }
            }
            a(this.q);
            a(this.p);
            a(this.o);
            a(this.r);
            a();
        }
    }

    void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i2, int i3, int i4, int i5) {
        android.view.View view = d0Var.f203a;
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        if (i6 != 0) {
            view.animate().translationX(0.0f);
        }
        if (i7 != 0) {
            view.animate().translationY(0.0f);
        }
        android.view.ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        this.p.add(d0Var);
        viewPropertyAnimatorAnimate.setDuration(e()).setListener(new androidx.recyclerview.widget.c.f(d0Var, i6, view, i7, viewPropertyAnimatorAnimate)).start();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public void c(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        android.view.View view = d0Var.f203a;
        view.animate().cancel();
        int size = this.j.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            if (this.j.get(size).f287a == d0Var) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                j(d0Var);
                this.j.remove(size);
            }
        }
        a(this.k, d0Var);
        if (this.h.remove(d0Var)) {
            view.setAlpha(1.0f);
            l(d0Var);
        }
        if (this.i.remove(d0Var)) {
            view.setAlpha(1.0f);
            h(d0Var);
        }
        for (int size2 = this.n.size() - 1; size2 >= 0; size2--) {
            java.util.ArrayList<androidx.recyclerview.widget.c.i> arrayList = this.n.get(size2);
            a(arrayList, d0Var);
            if (arrayList.isEmpty()) {
                this.n.remove(size2);
            }
        }
        for (int size3 = this.m.size() - 1; size3 >= 0; size3--) {
            java.util.ArrayList<androidx.recyclerview.widget.c.j> arrayList2 = this.m.get(size3);
            for (int size4 = arrayList2.size() - 1; size4 >= 0; size4--) {
                if (arrayList2.get(size4).f287a == d0Var) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    j(d0Var);
                    arrayList2.remove(size4);
                    if (!arrayList2.isEmpty()) {
                        break;
                    }
                    this.m.remove(size3);
                    break;
                }
            }
        }
        for (int size5 = this.l.size() - 1; size5 >= 0; size5--) {
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList3 = this.l.get(size5);
            if (arrayList3.remove(d0Var)) {
                view.setAlpha(1.0f);
                h(d0Var);
                if (arrayList3.isEmpty()) {
                    this.l.remove(size5);
                }
            }
        }
        this.q.remove(d0Var);
        this.o.remove(d0Var);
        this.r.remove(d0Var);
        this.p.remove(d0Var);
        j();
    }

    @Override // androidx.recyclerview.widget.n
    public boolean f(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        v(d0Var);
        d0Var.f203a.setAlpha(0.0f);
        this.i.add(d0Var);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean g() {
        return (this.i.isEmpty() && this.k.isEmpty() && this.j.isEmpty() && this.h.isEmpty() && this.p.isEmpty() && this.q.isEmpty() && this.o.isEmpty() && this.r.isEmpty() && this.m.isEmpty() && this.l.isEmpty() && this.n.isEmpty()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.n
    public boolean g(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        v(d0Var);
        this.h.add(d0Var);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public void i() {
        boolean z = !this.h.isEmpty();
        boolean z2 = !this.j.isEmpty();
        boolean z3 = !this.k.isEmpty();
        boolean z4 = !this.i.isEmpty();
        if (z || z2 || z4 || z3) {
            java.util.Iterator<androidx.recyclerview.widget.RecyclerView.d0> it = this.h.iterator();
            while (it.hasNext()) {
                u(it.next());
            }
            this.h.clear();
            if (z2) {
                java.util.ArrayList<androidx.recyclerview.widget.c.j> arrayList = new java.util.ArrayList<>();
                arrayList.addAll(this.j);
                this.m.add(arrayList);
                this.j.clear();
                androidx.recyclerview.widget.c.a aVar = new androidx.recyclerview.widget.c.a(arrayList);
                if (z) {
                    a.c.e.m.a(arrayList.get(0).f287a.f203a, aVar, f());
                } else {
                    aVar.run();
                }
            }
            if (z3) {
                java.util.ArrayList<androidx.recyclerview.widget.c.i> arrayList2 = new java.util.ArrayList<>();
                arrayList2.addAll(this.k);
                this.n.add(arrayList2);
                this.k.clear();
                androidx.recyclerview.widget.c.b bVar = new androidx.recyclerview.widget.c.b(arrayList2);
                if (z) {
                    a.c.e.m.a(arrayList2.get(0).f284a.f203a, bVar, f());
                } else {
                    bVar.run();
                }
            }
            if (z4) {
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView.d0> arrayList3 = new java.util.ArrayList<>();
                arrayList3.addAll(this.i);
                this.l.add(arrayList3);
                this.i.clear();
                androidx.recyclerview.widget.c.RunnableC0013c runnableC0013c = new androidx.recyclerview.widget.c.RunnableC0013c(arrayList3);
                if (z || z2 || z3) {
                    a.c.e.m.a(arrayList3.get(0).f203a, runnableC0013c, (z ? f() : 0L) + java.lang.Math.max(z2 ? e() : 0L, z3 ? d() : 0L));
                } else {
                    runnableC0013c.run();
                }
            }
        }
    }

    void j() {
        if (g()) {
            return;
        }
        a();
    }

    void t(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        android.view.View view = d0Var.f203a;
        android.view.ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        this.o.add(d0Var);
        viewPropertyAnimatorAnimate.alpha(1.0f).setDuration(c()).setListener(new androidx.recyclerview.widget.c.e(d0Var, view, viewPropertyAnimatorAnimate)).start();
    }
}
