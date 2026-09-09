package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final a.b.a.j.f f48b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final a.b.a.j.e.d f49c;
    a.b.a.j.e d;
    private int h;
    a.b.a.i i;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a.b.a.j.m f47a = new a.b.a.j.m(this);
    public int e = 0;
    int f = -1;
    private a.b.a.j.e.c g = a.b.a.j.e.c.NONE;

    static /* synthetic */ class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f50a;

        static {
            int[] iArr = new int[a.b.a.j.e.d.values().length];
            f50a = iArr;
            try {
                iArr[a.b.a.j.e.d.CENTER.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError unused) {
            }
            try {
                f50a[a.b.a.j.e.d.LEFT.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError unused2) {
            }
            try {
                f50a[a.b.a.j.e.d.RIGHT.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError unused3) {
            }
            try {
                f50a[a.b.a.j.e.d.TOP.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError unused4) {
            }
            try {
                f50a[a.b.a.j.e.d.BOTTOM.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError unused5) {
            }
            try {
                f50a[a.b.a.j.e.d.BASELINE.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError unused6) {
            }
            try {
                f50a[a.b.a.j.e.d.CENTER_X.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError unused7) {
            }
            try {
                f50a[a.b.a.j.e.d.CENTER_Y.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError unused8) {
            }
            try {
                f50a[a.b.a.j.e.d.NONE.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError unused9) {
            }
        }
    }

    public enum b {
        RELAXED,
        STRICT
    }

    public enum c {
        NONE,
        STRONG,
        WEAK
    }

    public enum d {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
    }

    public e(a.b.a.j.f fVar, a.b.a.j.e.d dVar) {
        a.b.a.j.e.b bVar = a.b.a.j.e.b.RELAXED;
        this.h = 0;
        this.f48b = fVar;
        this.f49c = dVar;
    }

    public int a() {
        return this.h;
    }

    public void a(a.b.a.c cVar) {
        a.b.a.i iVar = this.i;
        if (iVar == null) {
            this.i = new a.b.a.i(a.b.a.i.a.UNRESTRICTED, null);
        } else {
            iVar.a();
        }
    }

    public boolean a(a.b.a.j.e eVar) {
        if (eVar == null) {
            return false;
        }
        a.b.a.j.e.d dVarH = eVar.h();
        a.b.a.j.e.d dVar = this.f49c;
        if (dVarH == dVar) {
            return dVar != a.b.a.j.e.d.BASELINE || (eVar.c().x() && c().x());
        }
        switch (a.b.a.j.e.a.f50a[dVar.ordinal()]) {
            case 1:
                return (dVarH == a.b.a.j.e.d.BASELINE || dVarH == a.b.a.j.e.d.CENTER_X || dVarH == a.b.a.j.e.d.CENTER_Y) ? false : true;
            case 2:
            case 3:
                boolean z = dVarH == a.b.a.j.e.d.LEFT || dVarH == a.b.a.j.e.d.RIGHT;
                if (eVar.c() instanceof a.b.a.j.i) {
                    return z || dVarH == a.b.a.j.e.d.CENTER_X;
                }
                return z;
            case 4:
            case 5:
                boolean z2 = dVarH == a.b.a.j.e.d.TOP || dVarH == a.b.a.j.e.d.BOTTOM;
                if (eVar.c() instanceof a.b.a.j.i) {
                    return z2 || dVarH == a.b.a.j.e.d.CENTER_Y;
                }
                return z2;
            case 6:
            case 7:
            case 8:
            case 9:
                return false;
            default:
                throw new java.lang.AssertionError(this.f49c.name());
        }
    }

    public boolean a(a.b.a.j.e eVar, int i, int i2, a.b.a.j.e.c cVar, int i3, boolean z) {
        if (eVar == null) {
            this.d = null;
            this.e = 0;
            this.f = -1;
            this.g = a.b.a.j.e.c.NONE;
            this.h = 2;
            return true;
        }
        if (!z && !a(eVar)) {
            return false;
        }
        this.d = eVar;
        if (i > 0) {
            this.e = i;
        } else {
            this.e = 0;
        }
        this.f = i2;
        this.g = cVar;
        this.h = i3;
        return true;
    }

    public boolean a(a.b.a.j.e eVar, int i, a.b.a.j.e.c cVar, int i2) {
        return a(eVar, i, -1, cVar, i2, false);
    }

    public int b() {
        a.b.a.j.e eVar;
        if (this.f48b.r() == 8) {
            return 0;
        }
        return (this.f <= -1 || (eVar = this.d) == null || eVar.f48b.r() != 8) ? this.e : this.f;
    }

    public a.b.a.j.f c() {
        return this.f48b;
    }

    public a.b.a.j.m d() {
        return this.f47a;
    }

    public a.b.a.i e() {
        return this.i;
    }

    public a.b.a.j.e.c f() {
        return this.g;
    }

    public a.b.a.j.e g() {
        return this.d;
    }

    public a.b.a.j.e.d h() {
        return this.f49c;
    }

    public boolean i() {
        return this.d != null;
    }

    public void j() {
        this.d = null;
        this.e = 0;
        this.f = -1;
        this.g = a.b.a.j.e.c.STRONG;
        this.h = 0;
        a.b.a.j.e.b bVar = a.b.a.j.e.b.RELAXED;
        this.f47a.d();
    }

    public java.lang.String toString() {
        return this.f48b.f() + ":" + this.f49c.toString();
    }
}
