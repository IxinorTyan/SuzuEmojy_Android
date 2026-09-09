package a.e.a;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    static final a.e.a.b.c f = new a.e.a.b.a();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.util.List<a.e.a.b.d> f129a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.util.List<a.e.a.c> f130b;
    private final android.util.SparseBooleanArray d = new android.util.SparseBooleanArray();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final java.util.Map<a.e.a.c, a.e.a.b.d> f131c = new a.a.a();
    private final a.e.a.b.d e = b();

    static class a implements a.e.a.b.c {
        a() {
        }

        private boolean a(float[] fArr) {
            return fArr[2] <= 0.05f;
        }

        private boolean b(float[] fArr) {
            return fArr[0] >= 10.0f && fArr[0] <= 37.0f && fArr[1] <= 0.82f;
        }

        private boolean c(float[] fArr) {
            return fArr[2] >= 0.95f;
        }

        @Override // a.e.a.b.c
        public boolean a(int i, float[] fArr) {
            return (c(fArr) || a(fArr) || b(fArr)) ? false : true;
        }
    }

    /* JADX INFO: renamed from: a.e.a.b$b, reason: collision with other inner class name */
    public static final class C0008b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final java.util.List<a.e.a.b.d> f132a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final android.graphics.Bitmap f133b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private final java.util.List<a.e.a.c> f134c = new java.util.ArrayList();
        private int d = 16;
        private int e = 12544;
        private int f = -1;
        private final java.util.List<a.e.a.b.c> g = new java.util.ArrayList();
        private android.graphics.Rect h;

        public C0008b(android.graphics.Bitmap bitmap) {
            if (bitmap == null || bitmap.isRecycled()) {
                throw new java.lang.IllegalArgumentException("Bitmap is not valid");
            }
            this.g.add(a.e.a.b.f);
            this.f133b = bitmap;
            this.f132a = null;
            this.f134c.add(a.e.a.c.e);
            this.f134c.add(a.e.a.c.f);
            this.f134c.add(a.e.a.c.g);
            this.f134c.add(a.e.a.c.h);
            this.f134c.add(a.e.a.c.i);
            this.f134c.add(a.e.a.c.j);
        }

        private int[] a(android.graphics.Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[width * height];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            android.graphics.Rect rect = this.h;
            if (rect == null) {
                return iArr;
            }
            int iWidth = rect.width();
            int iHeight = this.h.height();
            int[] iArr2 = new int[iWidth * iHeight];
            for (int i = 0; i < iHeight; i++) {
                android.graphics.Rect rect2 = this.h;
                java.lang.System.arraycopy(iArr, ((rect2.top + i) * width) + rect2.left, iArr2, i * iWidth, iWidth);
            }
            return iArr2;
        }

        private android.graphics.Bitmap b(android.graphics.Bitmap bitmap) {
            int iMax;
            int i;
            double dSqrt = -1.0d;
            if (this.e > 0) {
                int width = bitmap.getWidth() * bitmap.getHeight();
                int i2 = this.e;
                if (width > i2) {
                    dSqrt = java.lang.Math.sqrt(((double) i2) / ((double) width));
                }
            } else if (this.f > 0 && (iMax = java.lang.Math.max(bitmap.getWidth(), bitmap.getHeight())) > (i = this.f)) {
                dSqrt = ((double) i) / ((double) iMax);
            }
            return dSqrt <= 0.0d ? bitmap : android.graphics.Bitmap.createScaledBitmap(bitmap, (int) java.lang.Math.ceil(((double) bitmap.getWidth()) * dSqrt), (int) java.lang.Math.ceil(((double) bitmap.getHeight()) * dSqrt), false);
        }

        public a.e.a.b a() {
            java.util.List<a.e.a.b.d> listA;
            a.e.a.b.c[] cVarArr;
            android.graphics.Bitmap bitmap = this.f133b;
            if (bitmap != null) {
                android.graphics.Bitmap bitmapB = b(bitmap);
                android.graphics.Rect rect = this.h;
                if (bitmapB != this.f133b && rect != null) {
                    double width = ((double) bitmapB.getWidth()) / ((double) this.f133b.getWidth());
                    rect.left = (int) java.lang.Math.floor(((double) rect.left) * width);
                    rect.top = (int) java.lang.Math.floor(((double) rect.top) * width);
                    rect.right = java.lang.Math.min((int) java.lang.Math.ceil(((double) rect.right) * width), bitmapB.getWidth());
                    rect.bottom = java.lang.Math.min((int) java.lang.Math.ceil(((double) rect.bottom) * width), bitmapB.getHeight());
                }
                int[] iArrA = a(bitmapB);
                int i = this.d;
                if (this.g.isEmpty()) {
                    cVarArr = null;
                } else {
                    java.util.List<a.e.a.b.c> list = this.g;
                    cVarArr = (a.e.a.b.c[]) list.toArray(new a.e.a.b.c[list.size()]);
                }
                a.e.a.a aVar = new a.e.a.a(iArrA, i, cVarArr);
                if (bitmapB != this.f133b) {
                    bitmapB.recycle();
                }
                listA = aVar.a();
            } else {
                listA = this.f132a;
                if (listA == null) {
                    throw new java.lang.AssertionError();
                }
            }
            a.e.a.b bVar = new a.e.a.b(listA, this.f134c);
            bVar.a();
            return bVar;
        }
    }

    public interface c {
        boolean a(int i, float[] fArr);
    }

    public static final class d {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final int f135a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final int f136b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private final int f137c;
        private final int d;
        private final int e;
        private boolean f;
        private int g;
        private int h;
        private float[] i;

        public d(int i, int i2) {
            this.f135a = android.graphics.Color.red(i);
            this.f136b = android.graphics.Color.green(i);
            this.f137c = android.graphics.Color.blue(i);
            this.d = i;
            this.e = i2;
        }

        private void f() {
            int iD;
            if (this.f) {
                return;
            }
            int iA = a.c.b.a.a(-1, this.d, 4.5f);
            int iA2 = a.c.b.a.a(-1, this.d, 3.0f);
            if (iA == -1 || iA2 == -1) {
                int iA3 = a.c.b.a.a(-16777216, this.d, 4.5f);
                int iA4 = a.c.b.a.a(-16777216, this.d, 3.0f);
                if (iA3 == -1 || iA4 == -1) {
                    this.h = iA != -1 ? a.c.b.a.d(-1, iA) : a.c.b.a.d(-16777216, iA3);
                    this.g = iA2 != -1 ? a.c.b.a.d(-1, iA2) : a.c.b.a.d(-16777216, iA4);
                    this.f = true;
                    return;
                }
                this.h = a.c.b.a.d(-16777216, iA3);
                iD = a.c.b.a.d(-16777216, iA4);
            } else {
                this.h = a.c.b.a.d(-1, iA);
                iD = a.c.b.a.d(-1, iA2);
            }
            this.g = iD;
            this.f = true;
        }

        public int a() {
            f();
            return this.h;
        }

        public float[] b() {
            if (this.i == null) {
                this.i = new float[3];
            }
            a.c.b.a.a(this.f135a, this.f136b, this.f137c, this.i);
            return this.i;
        }

        public int c() {
            return this.e;
        }

        public int d() {
            return this.d;
        }

        public int e() {
            f();
            return this.g;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || a.e.a.b.d.class != obj.getClass()) {
                return false;
            }
            a.e.a.b.d dVar = (a.e.a.b.d) obj;
            return this.e == dVar.e && this.d == dVar.d;
        }

        public int hashCode() {
            return (this.d * 31) + this.e;
        }

        public java.lang.String toString() {
            return a.e.a.b.d.class.getSimpleName() + " [RGB: #" + java.lang.Integer.toHexString(d()) + "] [HSL: " + java.util.Arrays.toString(b()) + "] [Population: " + this.e + "] [Title Text: #" + java.lang.Integer.toHexString(e()) + "] [Body Text: #" + java.lang.Integer.toHexString(a()) + ']';
        }
    }

    b(java.util.List<a.e.a.b.d> list, java.util.List<a.e.a.c> list2) {
        this.f129a = list;
        this.f130b = list2;
    }

    private float a(a.e.a.b.d dVar, a.e.a.c cVar) {
        float[] fArrB = dVar.b();
        a.e.a.b.d dVar2 = this.e;
        return (cVar.g() > 0.0f ? cVar.g() * (1.0f - java.lang.Math.abs(fArrB[1] - cVar.i())) : 0.0f) + (cVar.a() > 0.0f ? cVar.a() * (1.0f - java.lang.Math.abs(fArrB[2] - cVar.h())) : 0.0f) + (cVar.f() > 0.0f ? cVar.f() * (dVar.c() / (dVar2 != null ? dVar2.c() : 1)) : 0.0f);
    }

    public static a.e.a.b.C0008b a(android.graphics.Bitmap bitmap) {
        return new a.e.a.b.C0008b(bitmap);
    }

    private a.e.a.b.d a(a.e.a.c cVar) {
        a.e.a.b.d dVarB = b(cVar);
        if (dVarB != null && cVar.j()) {
            this.d.append(dVarB.d(), true);
        }
        return dVarB;
    }

    private a.e.a.b.d b() {
        int size = this.f129a.size();
        int iC = Integer.MIN_VALUE;
        a.e.a.b.d dVar = null;
        for (int i = 0; i < size; i++) {
            a.e.a.b.d dVar2 = this.f129a.get(i);
            if (dVar2.c() > iC) {
                iC = dVar2.c();
                dVar = dVar2;
            }
        }
        return dVar;
    }

    private a.e.a.b.d b(a.e.a.c cVar) {
        int size = this.f129a.size();
        float f2 = 0.0f;
        a.e.a.b.d dVar = null;
        for (int i = 0; i < size; i++) {
            a.e.a.b.d dVar2 = this.f129a.get(i);
            if (b(dVar2, cVar)) {
                float fA = a(dVar2, cVar);
                if (dVar == null || fA > f2) {
                    dVar = dVar2;
                    f2 = fA;
                }
            }
        }
        return dVar;
    }

    private boolean b(a.e.a.b.d dVar, a.e.a.c cVar) {
        float[] fArrB = dVar.b();
        return fArrB[1] >= cVar.e() && fArrB[1] <= cVar.c() && fArrB[2] >= cVar.d() && fArrB[2] <= cVar.b() && !this.d.get(dVar.d());
    }

    public int a(int i) {
        a.e.a.b.d dVar = this.e;
        return dVar != null ? dVar.d() : i;
    }

    void a() {
        int size = this.f130b.size();
        for (int i = 0; i < size; i++) {
            a.e.a.c cVar = this.f130b.get(i);
            cVar.k();
            this.f131c.put(cVar, a(cVar));
        }
        this.d.clear();
    }
}
