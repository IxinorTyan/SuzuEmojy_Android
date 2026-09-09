package a.e.a;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public static final a.e.a.c e;
    public static final a.e.a.c f;
    public static final a.e.a.c g;
    public static final a.e.a.c h;
    public static final a.e.a.c i;
    public static final a.e.a.c j;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final float[] f138a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final float[] f139b = new float[3];

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final float[] f140c = new float[3];
    boolean d = true;

    static {
        a.e.a.c cVar = new a.e.a.c();
        e = cVar;
        b(cVar);
        e(e);
        a.e.a.c cVar2 = new a.e.a.c();
        f = cVar2;
        d(cVar2);
        e(f);
        a.e.a.c cVar3 = new a.e.a.c();
        g = cVar3;
        a(cVar3);
        e(g);
        a.e.a.c cVar4 = new a.e.a.c();
        h = cVar4;
        b(cVar4);
        c(h);
        a.e.a.c cVar5 = new a.e.a.c();
        i = cVar5;
        d(cVar5);
        c(i);
        a.e.a.c cVar6 = new a.e.a.c();
        j = cVar6;
        a(cVar6);
        c(j);
    }

    c() {
        float[] fArr = new float[3];
        this.f138a = fArr;
        a(fArr);
        a(this.f139b);
        l();
    }

    private static void a(a.e.a.c cVar) {
        float[] fArr = cVar.f139b;
        fArr[1] = 0.26f;
        fArr[2] = 0.45f;
    }

    private static void a(float[] fArr) {
        fArr[0] = 0.0f;
        fArr[1] = 0.5f;
        fArr[2] = 1.0f;
    }

    private static void b(a.e.a.c cVar) {
        float[] fArr = cVar.f139b;
        fArr[0] = 0.55f;
        fArr[1] = 0.74f;
    }

    private static void c(a.e.a.c cVar) {
        float[] fArr = cVar.f138a;
        fArr[1] = 0.3f;
        fArr[2] = 0.4f;
    }

    private static void d(a.e.a.c cVar) {
        float[] fArr = cVar.f139b;
        fArr[0] = 0.3f;
        fArr[1] = 0.5f;
        fArr[2] = 0.7f;
    }

    private static void e(a.e.a.c cVar) {
        float[] fArr = cVar.f138a;
        fArr[0] = 0.35f;
        fArr[1] = 1.0f;
    }

    private void l() {
        float[] fArr = this.f140c;
        fArr[0] = 0.24f;
        fArr[1] = 0.52f;
        fArr[2] = 0.24f;
    }

    public float a() {
        return this.f140c[1];
    }

    public float b() {
        return this.f139b[2];
    }

    public float c() {
        return this.f138a[2];
    }

    public float d() {
        return this.f139b[0];
    }

    public float e() {
        return this.f138a[0];
    }

    public float f() {
        return this.f140c[2];
    }

    public float g() {
        return this.f140c[0];
    }

    public float h() {
        return this.f139b[1];
    }

    public float i() {
        return this.f138a[1];
    }

    public boolean j() {
        return this.d;
    }

    void k() {
        int length = this.f140c.length;
        float f2 = 0.0f;
        for (int i2 = 0; i2 < length; i2++) {
            float f3 = this.f140c[i2];
            if (f3 > 0.0f) {
                f2 += f3;
            }
        }
        if (f2 != 0.0f) {
            int length2 = this.f140c.length;
            for (int i3 = 0; i3 < length2; i3++) {
                float[] fArr = this.f140c;
                if (fArr[i3] > 0.0f) {
                    fArr[i3] = fArr[i3] / f2;
                }
            }
        }
    }
}
