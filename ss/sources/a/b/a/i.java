package a.b.a;

/* JADX INFO: loaded from: classes.dex */
public class i {
    private static int k = 1;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f38a;
    public float e;
    a.b.a.i.a g;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f39b = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    int f40c = -1;
    public int d = 0;
    float[] f = new float[7];
    a.b.a.b[] h = new a.b.a.b[8];
    int i = 0;
    public int j = 0;

    public enum a {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public i(a.b.a.i.a aVar, java.lang.String str) {
        this.g = aVar;
    }

    static void b() {
        k++;
    }

    public void a() {
        this.f38a = null;
        this.g = a.b.a.i.a.UNKNOWN;
        this.d = 0;
        this.f39b = -1;
        this.f40c = -1;
        this.e = 0.0f;
        this.i = 0;
        this.j = 0;
    }

    public final void a(a.b.a.b bVar) {
        int i = 0;
        while (true) {
            int i2 = this.i;
            if (i >= i2) {
                a.b.a.b[] bVarArr = this.h;
                if (i2 >= bVarArr.length) {
                    this.h = (a.b.a.b[]) java.util.Arrays.copyOf(bVarArr, bVarArr.length * 2);
                }
                a.b.a.b[] bVarArr2 = this.h;
                int i3 = this.i;
                bVarArr2[i3] = bVar;
                this.i = i3 + 1;
                return;
            }
            if (this.h[i] == bVar) {
                return;
            } else {
                i++;
            }
        }
    }

    public void a(a.b.a.i.a aVar, java.lang.String str) {
        this.g = aVar;
    }

    public final void b(a.b.a.b bVar) {
        int i = this.i;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.h[i2] == bVar) {
                for (int i3 = 0; i3 < (i - i2) - 1; i3++) {
                    a.b.a.b[] bVarArr = this.h;
                    int i4 = i2 + i3;
                    bVarArr[i4] = bVarArr[i4 + 1];
                }
                this.i--;
                return;
            }
        }
    }

    public final void c(a.b.a.b bVar) {
        int i = this.i;
        for (int i2 = 0; i2 < i; i2++) {
            a.b.a.b[] bVarArr = this.h;
            bVarArr[i2].d.a(bVarArr[i2], bVar, false);
        }
        this.i = 0;
    }

    public java.lang.String toString() {
        return "" + this.f38a;
    }
}
