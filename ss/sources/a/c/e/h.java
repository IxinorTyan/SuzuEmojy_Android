package a.c.e;

/* JADX INFO: loaded from: classes.dex */
public class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.view.ViewParent f96a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.view.ViewParent f97b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final android.view.View f98c;
    private boolean d;
    private int[] e;

    public h(android.view.View view) {
        this.f98c = view;
    }

    private void a(int i, android.view.ViewParent viewParent) {
        if (i == 0) {
            this.f96a = viewParent;
        } else {
            if (i != 1) {
                return;
            }
            this.f97b = viewParent;
        }
    }

    private boolean b(int i, int i2, int i3, int i4, int[] iArr, int i5, int[] iArr2) {
        android.view.ViewParent viewParentD;
        int i6;
        int i7;
        int[] iArr3;
        if (!b() || (viewParentD = d(i5)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
            return false;
        }
        if (iArr != null) {
            this.f98c.getLocationInWindow(iArr);
            i6 = iArr[0];
            i7 = iArr[1];
        } else {
            i6 = 0;
            i7 = 0;
        }
        if (iArr2 == null) {
            int[] iArrD = d();
            iArrD[0] = 0;
            iArrD[1] = 0;
            iArr3 = iArrD;
        } else {
            iArr3 = iArr2;
        }
        a.c.e.o.a(viewParentD, this.f98c, i, i2, i3, i4, i5, iArr3);
        if (iArr != null) {
            this.f98c.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - i6;
            iArr[1] = iArr[1] - i7;
        }
        return true;
    }

    private android.view.ViewParent d(int i) {
        if (i == 0) {
            return this.f96a;
        }
        if (i != 1) {
            return null;
        }
        return this.f97b;
    }

    private int[] d() {
        if (this.e == null) {
            this.e = new int[2];
        }
        return this.e;
    }

    public void a(int i, int i2, int i3, int i4, int[] iArr, int i5, int[] iArr2) {
        b(i, i2, i3, i4, iArr, i5, iArr2);
    }

    public void a(boolean z) {
        if (this.d) {
            a.c.e.m.q(this.f98c);
        }
        this.d = z;
    }

    public boolean a() {
        return a(0);
    }

    public boolean a(float f, float f2) {
        android.view.ViewParent viewParentD;
        if (!b() || (viewParentD = d(0)) == null) {
            return false;
        }
        return a.c.e.o.a(viewParentD, this.f98c, f, f2);
    }

    public boolean a(float f, float f2, boolean z) {
        android.view.ViewParent viewParentD;
        if (!b() || (viewParentD = d(0)) == null) {
            return false;
        }
        return a.c.e.o.a(viewParentD, this.f98c, f, f2, z);
    }

    public boolean a(int i) {
        return d(i) != null;
    }

    public boolean a(int i, int i2) {
        if (a(i2)) {
            return true;
        }
        if (!b()) {
            return false;
        }
        android.view.View view = this.f98c;
        for (android.view.ViewParent parent = this.f98c.getParent(); parent != null; parent = parent.getParent()) {
            if (a.c.e.o.b(parent, view, this.f98c, i, i2)) {
                a(i2, parent);
                a.c.e.o.a(parent, view, this.f98c, i, i2);
                return true;
            }
            if (parent instanceof android.view.View) {
                view = (android.view.View) parent;
            }
        }
        return false;
    }

    public boolean a(int i, int i2, int i3, int i4, int[] iArr) {
        return b(i, i2, i3, i4, iArr, 0, null);
    }

    public boolean a(int i, int i2, int[] iArr, int[] iArr2) {
        return a(i, i2, iArr, iArr2, 0);
    }

    public boolean a(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        android.view.ViewParent viewParentD;
        int i4;
        int i5;
        if (!b() || (viewParentD = d(i3)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0) {
            if (iArr2 == null) {
                return false;
            }
            iArr2[0] = 0;
            iArr2[1] = 0;
            return false;
        }
        if (iArr2 != null) {
            this.f98c.getLocationInWindow(iArr2);
            i4 = iArr2[0];
            i5 = iArr2[1];
        } else {
            i4 = 0;
            i5 = 0;
        }
        if (iArr == null) {
            iArr = d();
        }
        iArr[0] = 0;
        iArr[1] = 0;
        a.c.e.o.a(viewParentD, this.f98c, i, i2, iArr, i3);
        if (iArr2 != null) {
            this.f98c.getLocationInWindow(iArr2);
            iArr2[0] = iArr2[0] - i4;
            iArr2[1] = iArr2[1] - i5;
        }
        return (iArr[0] == 0 && iArr[1] == 0) ? false : true;
    }

    public boolean b() {
        return this.d;
    }

    public boolean b(int i) {
        return a(i, 0);
    }

    public void c() {
        c(0);
    }

    public void c(int i) {
        android.view.ViewParent viewParentD = d(i);
        if (viewParentD != null) {
            a.c.e.o.a(viewParentD, this.f98c, i);
            a(i, (android.view.ViewParent) null);
        }
    }
}
