package a.b.a;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final a.b.a.b f22b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final a.b.a.c f23c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int f21a = 0;
    private int d = 8;
    private a.b.a.i e = null;
    private int[] f = new int[8];
    private int[] g = new int[8];
    private float[] h = new float[8];
    private int i = -1;
    private int j = -1;
    private boolean k = false;

    a(a.b.a.b bVar, a.b.a.c cVar) {
        this.f22b = bVar;
        this.f23c = cVar;
    }

    private boolean a(a.b.a.i iVar, a.b.a.e eVar) {
        return iVar.j <= 1;
    }

    public final float a(a.b.a.i iVar, boolean z) {
        if (this.e == iVar) {
            this.e = null;
        }
        int i = this.i;
        if (i == -1) {
            return 0.0f;
        }
        int i2 = 0;
        int i3 = -1;
        while (i != -1 && i2 < this.f21a) {
            if (this.f[i] == iVar.f39b) {
                if (i == this.i) {
                    this.i = this.g[i];
                } else {
                    int[] iArr = this.g;
                    iArr[i3] = iArr[i];
                }
                if (z) {
                    iVar.b(this.f22b);
                }
                iVar.j--;
                this.f21a--;
                this.f[i] = -1;
                if (this.k) {
                    this.j = i;
                }
                return this.h[i];
            }
            i2++;
            i3 = i;
            i = this.g[i];
        }
        return 0.0f;
    }

    final a.b.a.i a(int i) {
        int i2 = this.i;
        for (int i3 = 0; i2 != -1 && i3 < this.f21a; i3++) {
            if (i3 == i) {
                return this.f23c.f29c[this.f[i2]];
            }
            i2 = this.g[i2];
        }
        return null;
    }

    a.b.a.i a(a.b.a.e eVar) {
        int i = this.i;
        a.b.a.i iVar = null;
        a.b.a.i iVar2 = null;
        boolean zA = false;
        boolean zA2 = false;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            float[] fArr = this.h;
            float f3 = fArr[i];
            a.b.a.i iVar3 = this.f23c.f29c[this.f[i]];
            if (f3 < 0.0f) {
                if (f3 > -0.001f) {
                    fArr[i] = 0.0f;
                    iVar3.b(this.f22b);
                    f3 = 0.0f;
                }
            } else if (f3 < 0.001f) {
                fArr[i] = 0.0f;
                iVar3.b(this.f22b);
                f3 = 0.0f;
            }
            if (f3 != 0.0f) {
                if (iVar3.g == a.b.a.i.a.UNRESTRICTED) {
                    if (iVar2 == null || f > f3) {
                        zA = a(iVar3, eVar);
                        f = f3;
                        iVar2 = iVar3;
                    } else if (!zA && a(iVar3, eVar)) {
                        f = f3;
                        iVar2 = iVar3;
                        zA = true;
                    }
                } else if (iVar2 == null && f3 < 0.0f) {
                    if (iVar == null || f2 > f3) {
                        zA2 = a(iVar3, eVar);
                        f2 = f3;
                        iVar = iVar3;
                    } else if (!zA2 && a(iVar3, eVar)) {
                        f2 = f3;
                        iVar = iVar3;
                        zA2 = true;
                    }
                }
            }
            i = this.g[i];
        }
        return iVar2 != null ? iVar2 : iVar;
    }

    a.b.a.i a(boolean[] zArr, a.b.a.i iVar) {
        a.b.a.i.a aVar;
        int i = this.i;
        a.b.a.i iVar2 = null;
        float f = 0.0f;
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            if (this.h[i] < 0.0f) {
                a.b.a.i iVar3 = this.f23c.f29c[this.f[i]];
                if ((zArr == null || !zArr[iVar3.f39b]) && iVar3 != iVar && ((aVar = iVar3.g) == a.b.a.i.a.SLACK || aVar == a.b.a.i.a.ERROR)) {
                    float f2 = this.h[i];
                    if (f2 < f) {
                        iVar2 = iVar3;
                        f = f2;
                    }
                }
            }
            i = this.g[i];
        }
        return iVar2;
    }

    public final void a() {
        int i = this.i;
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            a.b.a.i iVar = this.f23c.f29c[this.f[i]];
            if (iVar != null) {
                iVar.b(this.f22b);
            }
            i = this.g[i];
        }
        this.i = -1;
        this.j = -1;
        this.k = false;
        this.f21a = 0;
    }

    void a(float f) {
        int i = this.i;
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            float[] fArr = this.h;
            fArr[i] = fArr[i] / f;
            i = this.g[i];
        }
    }

    final void a(a.b.a.b bVar, a.b.a.b bVar2, boolean z) {
        int i = this.i;
        while (true) {
            for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
                int i3 = this.f[i];
                a.b.a.i iVar = bVar2.f24a;
                if (i3 == iVar.f39b) {
                    float f = this.h[i];
                    a(iVar, z);
                    a.b.a.a aVar = bVar2.d;
                    int i4 = aVar.i;
                    for (int i5 = 0; i4 != -1 && i5 < aVar.f21a; i5++) {
                        a(this.f23c.f29c[aVar.f[i4]], aVar.h[i4] * f, z);
                        i4 = aVar.g[i4];
                    }
                    bVar.f25b += bVar2.f25b * f;
                    if (z) {
                        bVar2.f24a.b(bVar);
                    }
                    i = this.i;
                } else {
                    i = this.g[i];
                }
            }
            return;
        }
    }

    void a(a.b.a.b bVar, a.b.a.b[] bVarArr) {
        int i = this.i;
        while (true) {
            for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
                a.b.a.i iVar = this.f23c.f29c[this.f[i]];
                if (iVar.f40c != -1) {
                    float f = this.h[i];
                    a(iVar, true);
                    a.b.a.b bVar2 = bVarArr[iVar.f40c];
                    if (!bVar2.e) {
                        a.b.a.a aVar = bVar2.d;
                        int i3 = aVar.i;
                        for (int i4 = 0; i3 != -1 && i4 < aVar.f21a; i4++) {
                            a(this.f23c.f29c[aVar.f[i3]], aVar.h[i3] * f, true);
                            i3 = aVar.g[i3];
                        }
                    }
                    bVar.f25b += bVar2.f25b * f;
                    bVar2.f24a.b(bVar);
                    i = this.i;
                } else {
                    i = this.g[i];
                }
            }
            return;
        }
    }

    public final void a(a.b.a.i iVar, float f) {
        if (f == 0.0f) {
            a(iVar, true);
            return;
        }
        int i = this.i;
        if (i == -1) {
            this.i = 0;
            this.h[0] = f;
            this.f[0] = iVar.f39b;
            this.g[0] = -1;
            iVar.j++;
            iVar.a(this.f22b);
            this.f21a++;
            if (this.k) {
                return;
            }
            int i2 = this.j + 1;
            this.j = i2;
            int[] iArr = this.f;
            if (i2 >= iArr.length) {
                this.k = true;
                this.j = iArr.length - 1;
                return;
            }
            return;
        }
        int i3 = -1;
        for (int i4 = 0; i != -1 && i4 < this.f21a; i4++) {
            int[] iArr2 = this.f;
            int i5 = iArr2[i];
            int i6 = iVar.f39b;
            if (i5 == i6) {
                this.h[i] = f;
                return;
            }
            if (iArr2[i] < i6) {
                i3 = i;
            }
            i = this.g[i];
        }
        int length = this.j;
        int i7 = length + 1;
        if (this.k) {
            int[] iArr3 = this.f;
            if (iArr3[length] != -1) {
                length = iArr3.length;
            }
        } else {
            length = i7;
        }
        int[] iArr4 = this.f;
        if (length >= iArr4.length && this.f21a < iArr4.length) {
            int i8 = 0;
            while (true) {
                int[] iArr5 = this.f;
                if (i8 >= iArr5.length) {
                    break;
                }
                if (iArr5[i8] == -1) {
                    length = i8;
                    break;
                }
                i8++;
            }
        }
        int[] iArr6 = this.f;
        if (length >= iArr6.length) {
            length = iArr6.length;
            int i9 = this.d * 2;
            this.d = i9;
            this.k = false;
            this.j = length - 1;
            this.h = java.util.Arrays.copyOf(this.h, i9);
            this.f = java.util.Arrays.copyOf(this.f, this.d);
            this.g = java.util.Arrays.copyOf(this.g, this.d);
        }
        this.f[length] = iVar.f39b;
        this.h[length] = f;
        int[] iArr7 = this.g;
        if (i3 != -1) {
            iArr7[length] = iArr7[i3];
            iArr7[i3] = length;
        } else {
            iArr7[length] = this.i;
            this.i = length;
        }
        iVar.j++;
        iVar.a(this.f22b);
        this.f21a++;
        if (!this.k) {
            this.j++;
        }
        if (this.f21a >= this.f.length) {
            this.k = true;
        }
        int i10 = this.j;
        int[] iArr8 = this.f;
        if (i10 >= iArr8.length) {
            this.k = true;
            this.j = iArr8.length - 1;
        }
    }

    final void a(a.b.a.i iVar, float f, boolean z) {
        if (f == 0.0f) {
            return;
        }
        int i = this.i;
        if (i == -1) {
            this.i = 0;
            this.h[0] = f;
            this.f[0] = iVar.f39b;
            this.g[0] = -1;
            iVar.j++;
            iVar.a(this.f22b);
            this.f21a++;
            if (this.k) {
                return;
            }
            int i2 = this.j + 1;
            this.j = i2;
            int[] iArr = this.f;
            if (i2 >= iArr.length) {
                this.k = true;
                this.j = iArr.length - 1;
                return;
            }
            return;
        }
        int i3 = -1;
        for (int i4 = 0; i != -1 && i4 < this.f21a; i4++) {
            int[] iArr2 = this.f;
            int i5 = iArr2[i];
            int i6 = iVar.f39b;
            if (i5 == i6) {
                float[] fArr = this.h;
                fArr[i] = fArr[i] + f;
                if (fArr[i] == 0.0f) {
                    if (i == this.i) {
                        this.i = this.g[i];
                    } else {
                        int[] iArr3 = this.g;
                        iArr3[i3] = iArr3[i];
                    }
                    if (z) {
                        iVar.b(this.f22b);
                    }
                    if (this.k) {
                        this.j = i;
                    }
                    iVar.j--;
                    this.f21a--;
                    return;
                }
                return;
            }
            if (iArr2[i] < i6) {
                i3 = i;
            }
            i = this.g[i];
        }
        int length = this.j;
        int i7 = length + 1;
        if (this.k) {
            int[] iArr4 = this.f;
            if (iArr4[length] != -1) {
                length = iArr4.length;
            }
        } else {
            length = i7;
        }
        int[] iArr5 = this.f;
        if (length >= iArr5.length && this.f21a < iArr5.length) {
            int i8 = 0;
            while (true) {
                int[] iArr6 = this.f;
                if (i8 >= iArr6.length) {
                    break;
                }
                if (iArr6[i8] == -1) {
                    length = i8;
                    break;
                }
                i8++;
            }
        }
        int[] iArr7 = this.f;
        if (length >= iArr7.length) {
            length = iArr7.length;
            int i9 = this.d * 2;
            this.d = i9;
            this.k = false;
            this.j = length - 1;
            this.h = java.util.Arrays.copyOf(this.h, i9);
            this.f = java.util.Arrays.copyOf(this.f, this.d);
            this.g = java.util.Arrays.copyOf(this.g, this.d);
        }
        this.f[length] = iVar.f39b;
        this.h[length] = f;
        int[] iArr8 = this.g;
        if (i3 != -1) {
            iArr8[length] = iArr8[i3];
            iArr8[i3] = length;
        } else {
            iArr8[length] = this.i;
            this.i = length;
        }
        iVar.j++;
        iVar.a(this.f22b);
        this.f21a++;
        if (!this.k) {
            this.j++;
        }
        int i10 = this.j;
        int[] iArr9 = this.f;
        if (i10 >= iArr9.length) {
            this.k = true;
            this.j = iArr9.length - 1;
        }
    }

    final boolean a(a.b.a.i iVar) {
        int i = this.i;
        if (i == -1) {
            return false;
        }
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            if (this.f[i] == iVar.f39b) {
                return true;
            }
            i = this.g[i];
        }
        return false;
    }

    final float b(int i) {
        int i2 = this.i;
        for (int i3 = 0; i2 != -1 && i3 < this.f21a; i3++) {
            if (i3 == i) {
                return this.h[i2];
            }
            i2 = this.g[i2];
        }
        return 0.0f;
    }

    public final float b(a.b.a.i iVar) {
        int i = this.i;
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            if (this.f[i] == iVar.f39b) {
                return this.h[i];
            }
            i = this.g[i];
        }
        return 0.0f;
    }

    void b() {
        int i = this.i;
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            float[] fArr = this.h;
            fArr[i] = fArr[i] * (-1.0f);
            i = this.g[i];
        }
    }

    public java.lang.String toString() {
        int i = this.i;
        java.lang.String str = "";
        for (int i2 = 0; i != -1 && i2 < this.f21a; i2++) {
            str = ((str + " -> ") + this.h[i] + " : ") + this.f23c.f29c[this.f[i]];
            i = this.g[i];
        }
        return str;
    }
}
