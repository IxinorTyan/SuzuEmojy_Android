package a.c.e;

/* JADX INFO: loaded from: classes.dex */
public class p {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.lang.Object f108a;

    private p(java.lang.Object obj) {
        this.f108a = obj;
    }

    static a.c.e.p a(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        return new a.c.e.p(obj);
    }

    static java.lang.Object a(a.c.e.p pVar) {
        if (pVar == null) {
            return null;
        }
        return pVar.f108a;
    }

    public int a() {
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            return ((android.view.WindowInsets) this.f108a).getSystemWindowInsetBottom();
        }
        return 0;
    }

    public a.c.e.p a(int i, int i2, int i3, int i4) {
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            return new a.c.e.p(((android.view.WindowInsets) this.f108a).replaceSystemWindowInsets(i, i2, i3, i4));
        }
        return null;
    }

    public int b() {
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            return ((android.view.WindowInsets) this.f108a).getSystemWindowInsetLeft();
        }
        return 0;
    }

    public int c() {
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            return ((android.view.WindowInsets) this.f108a).getSystemWindowInsetRight();
        }
        return 0;
    }

    public int d() {
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            return ((android.view.WindowInsets) this.f108a).getSystemWindowInsetTop();
        }
        return 0;
    }

    public boolean e() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            return ((android.view.WindowInsets) this.f108a).isConsumed();
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || a.c.e.p.class != obj.getClass()) {
            return false;
        }
        java.lang.Object obj2 = this.f108a;
        java.lang.Object obj3 = ((a.c.e.p) obj).f108a;
        if (obj2 == null) {
            return obj3 == null;
        }
        return obj2.equals(obj3);
    }

    public int hashCode() {
        java.lang.Object obj = this.f108a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
