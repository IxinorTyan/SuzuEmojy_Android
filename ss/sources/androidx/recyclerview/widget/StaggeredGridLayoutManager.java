package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public class StaggeredGridLayoutManager extends androidx.recyclerview.widget.RecyclerView.o {
    private java.util.BitSet B;
    private boolean G;
    private boolean H;
    private androidx.recyclerview.widget.StaggeredGridLayoutManager.e I;
    private int J;
    private int[] O;
    androidx.recyclerview.widget.StaggeredGridLayoutManager.f[] t;
    androidx.recyclerview.widget.k u;
    androidx.recyclerview.widget.k v;
    private int w;
    private int x;
    private final androidx.recyclerview.widget.i y;
    private int s = -1;
    boolean z = false;
    boolean A = false;
    int C = -1;
    int D = Integer.MIN_VALUE;
    androidx.recyclerview.widget.StaggeredGridLayoutManager.d E = new androidx.recyclerview.widget.StaggeredGridLayoutManager.d();
    private int F = 2;
    private final android.graphics.Rect K = new android.graphics.Rect();
    private final androidx.recyclerview.widget.StaggeredGridLayoutManager.b L = new androidx.recyclerview.widget.StaggeredGridLayoutManager.b();
    private boolean M = false;
    private boolean N = true;
    private final java.lang.Runnable P = new androidx.recyclerview.widget.StaggeredGridLayoutManager.a();

    class a implements java.lang.Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.this.F();
        }
    }

    class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f238a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f239b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        boolean f240c;
        boolean d;
        boolean e;
        int[] f;

        b() {
            b();
        }

        void a() {
            this.f239b = this.f240c ? androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b() : androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.f();
        }

        void a(int i) {
            this.f239b = this.f240c ? androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b() - i : androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.f() + i;
        }

        void a(androidx.recyclerview.widget.StaggeredGridLayoutManager.f[] fVarArr) {
            int length = fVarArr.length;
            int[] iArr = this.f;
            if (iArr == null || iArr.length < length) {
                this.f = new int[androidx.recyclerview.widget.StaggeredGridLayoutManager.this.t.length];
            }
            for (int i = 0; i < length; i++) {
                this.f[i] = fVarArr[i].b(Integer.MIN_VALUE);
            }
        }

        void b() {
            this.f238a = -1;
            this.f239b = Integer.MIN_VALUE;
            this.f240c = false;
            this.d = false;
            this.e = false;
            int[] iArr = this.f;
            if (iArr != null) {
                java.util.Arrays.fill(iArr, -1);
            }
        }
    }

    public static class c extends androidx.recyclerview.widget.RecyclerView.p {
        androidx.recyclerview.widget.StaggeredGridLayoutManager.f e;
        boolean f;

        public c(int i, int i2) {
            super(i, i2);
        }

        public c(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public c(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public c(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public final int e() {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar = this.e;
            if (fVar == null) {
                return -1;
            }
            return fVar.e;
        }

        public boolean f() {
            return this.f;
        }
    }

    static class d {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int[] f241a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> f242b;

        @android.annotation.SuppressLint({"BanParcelableUsage"})
        static class a implements android.os.Parcelable {
            public static final android.os.Parcelable.Creator<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> CREATOR = new androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a.C0010a();

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            int f243a;

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            int f244b;

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            int[] f245c;
            boolean d;

            /* JADX INFO: renamed from: androidx.recyclerview.widget.StaggeredGridLayoutManager$d$a$a, reason: collision with other inner class name */
            static class C0010a implements android.os.Parcelable.Creator<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> {
                C0010a() {
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a createFromParcel(android.os.Parcel parcel) {
                    return new androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a[] newArray(int i) {
                    return new androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a[i];
                }
            }

            a() {
            }

            a(android.os.Parcel parcel) {
                this.f243a = parcel.readInt();
                this.f244b = parcel.readInt();
                this.d = parcel.readInt() == 1;
                int i = parcel.readInt();
                if (i > 0) {
                    int[] iArr = new int[i];
                    this.f245c = iArr;
                    parcel.readIntArray(iArr);
                }
            }

            int a(int i) {
                int[] iArr = this.f245c;
                if (iArr == null) {
                    return 0;
                }
                return iArr[i];
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public java.lang.String toString() {
                return "FullSpanItem{mPosition=" + this.f243a + ", mGapDir=" + this.f244b + ", mHasUnwantedGapAfter=" + this.d + ", mGapPerSpan=" + java.util.Arrays.toString(this.f245c) + '}';
            }

            @Override // android.os.Parcelable
            public void writeToParcel(android.os.Parcel parcel, int i) {
                parcel.writeInt(this.f243a);
                parcel.writeInt(this.f244b);
                parcel.writeInt(this.d ? 1 : 0);
                int[] iArr = this.f245c;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(iArr.length);
                    parcel.writeIntArray(this.f245c);
                }
            }
        }

        d() {
        }

        private void c(int i, int i2) {
            java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> list = this.f242b;
            if (list == null) {
                return;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar = this.f242b.get(size);
                int i3 = aVar.f243a;
                if (i3 >= i) {
                    aVar.f243a = i3 + i2;
                }
            }
        }

        private void d(int i, int i2) {
            java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> list = this.f242b;
            if (list == null) {
                return;
            }
            int i3 = i + i2;
            for (int size = list.size() - 1; size >= 0; size--) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar = this.f242b.get(size);
                int i4 = aVar.f243a;
                if (i4 >= i) {
                    if (i4 < i3) {
                        this.f242b.remove(size);
                    } else {
                        aVar.f243a = i4 - i2;
                    }
                }
            }
        }

        private int g(int i) {
            if (this.f242b == null) {
                return -1;
            }
            androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarC = c(i);
            if (aVarC != null) {
                this.f242b.remove(aVarC);
            }
            int size = this.f242b.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                }
                if (this.f242b.get(i2).f243a >= i) {
                    break;
                }
                i2++;
            }
            if (i2 == -1) {
                return -1;
            }
            androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar = this.f242b.get(i2);
            this.f242b.remove(i2);
            return aVar.f243a;
        }

        public androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a a(int i, int i2, int i3, boolean z) {
            java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> list = this.f242b;
            if (list == null) {
                return null;
            }
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar = this.f242b.get(i4);
                int i5 = aVar.f243a;
                if (i5 >= i2) {
                    return null;
                }
                if (i5 >= i && (i3 == 0 || aVar.f244b == i3 || (z && aVar.d))) {
                    return aVar;
                }
            }
            return null;
        }

        void a() {
            int[] iArr = this.f241a;
            if (iArr != null) {
                java.util.Arrays.fill(iArr, -1);
            }
            this.f242b = null;
        }

        void a(int i) {
            int[] iArr = this.f241a;
            if (iArr == null) {
                int[] iArr2 = new int[java.lang.Math.max(i, 10) + 1];
                this.f241a = iArr2;
                java.util.Arrays.fill(iArr2, -1);
            } else if (i >= iArr.length) {
                int[] iArr3 = new int[f(i)];
                this.f241a = iArr3;
                java.lang.System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                int[] iArr4 = this.f241a;
                java.util.Arrays.fill(iArr4, iArr.length, iArr4.length, -1);
            }
        }

        void a(int i, int i2) {
            int[] iArr = this.f241a;
            if (iArr == null || i >= iArr.length) {
                return;
            }
            int i3 = i + i2;
            a(i3);
            int[] iArr2 = this.f241a;
            java.lang.System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
            java.util.Arrays.fill(this.f241a, i, i3, -1);
            c(i, i2);
        }

        void a(int i, androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar) {
            a(i);
            this.f241a[i] = fVar.e;
        }

        public void a(androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar) {
            if (this.f242b == null) {
                this.f242b = new java.util.ArrayList();
            }
            int size = this.f242b.size();
            for (int i = 0; i < size; i++) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar2 = this.f242b.get(i);
                if (aVar2.f243a == aVar.f243a) {
                    this.f242b.remove(i);
                }
                if (aVar2.f243a >= aVar.f243a) {
                    this.f242b.add(i, aVar);
                    return;
                }
            }
            this.f242b.add(aVar);
        }

        int b(int i) {
            java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> list = this.f242b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (this.f242b.get(size).f243a >= i) {
                        this.f242b.remove(size);
                    }
                }
            }
            return e(i);
        }

        void b(int i, int i2) {
            int[] iArr = this.f241a;
            if (iArr == null || i >= iArr.length) {
                return;
            }
            int i3 = i + i2;
            a(i3);
            int[] iArr2 = this.f241a;
            java.lang.System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
            int[] iArr3 = this.f241a;
            java.util.Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
            d(i, i2);
        }

        public androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a c(int i) {
            java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> list = this.f242b;
            if (list == null) {
                return null;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar = this.f242b.get(size);
                if (aVar.f243a == i) {
                    return aVar;
                }
            }
            return null;
        }

        int d(int i) {
            int[] iArr = this.f241a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            return iArr[i];
        }

        int e(int i) {
            int[] iArr = this.f241a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            int iG = g(i);
            if (iG == -1) {
                int[] iArr2 = this.f241a;
                java.util.Arrays.fill(iArr2, i, iArr2.length, -1);
                return this.f241a.length;
            }
            int i2 = iG + 1;
            java.util.Arrays.fill(this.f241a, i, i2, -1);
            return i2;
        }

        int f(int i) {
            int length = this.f241a.length;
            while (length <= i) {
                length *= 2;
            }
            return length;
        }
    }

    @android.annotation.SuppressLint({"BanParcelableUsage"})
    public static class e implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<androidx.recyclerview.widget.StaggeredGridLayoutManager.e> CREATOR = new androidx.recyclerview.widget.StaggeredGridLayoutManager.e.a();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f246a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f247b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f248c;
        int[] d;
        int e;
        int[] f;
        java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a> g;
        boolean h;
        boolean i;
        boolean j;

        static class a implements android.os.Parcelable.Creator<androidx.recyclerview.widget.StaggeredGridLayoutManager.e> {
            a() {
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public androidx.recyclerview.widget.StaggeredGridLayoutManager.e createFromParcel(android.os.Parcel parcel) {
                return new androidx.recyclerview.widget.StaggeredGridLayoutManager.e(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public androidx.recyclerview.widget.StaggeredGridLayoutManager.e[] newArray(int i) {
                return new androidx.recyclerview.widget.StaggeredGridLayoutManager.e[i];
            }
        }

        public e() {
        }

        e(android.os.Parcel parcel) {
            this.f246a = parcel.readInt();
            this.f247b = parcel.readInt();
            int i = parcel.readInt();
            this.f248c = i;
            if (i > 0) {
                int[] iArr = new int[i];
                this.d = iArr;
                parcel.readIntArray(iArr);
            }
            int i2 = parcel.readInt();
            this.e = i2;
            if (i2 > 0) {
                int[] iArr2 = new int[i2];
                this.f = iArr2;
                parcel.readIntArray(iArr2);
            }
            this.h = parcel.readInt() == 1;
            this.i = parcel.readInt() == 1;
            this.j = parcel.readInt() == 1;
            this.g = parcel.readArrayList(androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a.class.getClassLoader());
        }

        public e(androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar) {
            this.f248c = eVar.f248c;
            this.f246a = eVar.f246a;
            this.f247b = eVar.f247b;
            this.d = eVar.d;
            this.e = eVar.e;
            this.f = eVar.f;
            this.h = eVar.h;
            this.i = eVar.i;
            this.j = eVar.j;
            this.g = eVar.g;
        }

        void a() {
            this.d = null;
            this.f248c = 0;
            this.f246a = -1;
            this.f247b = -1;
        }

        void b() {
            this.d = null;
            this.f248c = 0;
            this.e = 0;
            this.f = null;
            this.g = null;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.f246a);
            parcel.writeInt(this.f247b);
            parcel.writeInt(this.f248c);
            if (this.f248c > 0) {
                parcel.writeIntArray(this.d);
            }
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeList(this.g);
        }
    }

    class f {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        java.util.ArrayList<android.view.View> f249a = new java.util.ArrayList<>();

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f250b = Integer.MIN_VALUE;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f251c = Integer.MIN_VALUE;
        int d = 0;
        final int e;

        f(int i) {
            this.e = i;
        }

        int a(int i) {
            int i2 = this.f251c;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.f249a.size() == 0) {
                return i;
            }
            a();
            return this.f251c;
        }

        int a(int i, int i2, boolean z) {
            return a(i, i2, false, false, z);
        }

        int a(int i, int i2, boolean z, boolean z2, boolean z3) {
            int iF = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.f();
            int iB = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                android.view.View view = this.f249a.get(i);
                int iD = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.d(view);
                int iA = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.a(view);
                boolean z4 = false;
                boolean z5 = !z3 ? iD >= iB : iD > iB;
                if (!z3 ? iA > iF : iA >= iF) {
                    z4 = true;
                }
                if (z5 && z4) {
                    if (z && z2) {
                        if (iD >= iF && iA <= iB) {
                            return androidx.recyclerview.widget.StaggeredGridLayoutManager.this.l(view);
                        }
                    } else if (z2 || iD < iF || iA > iB) {
                        return androidx.recyclerview.widget.StaggeredGridLayoutManager.this.l(view);
                    }
                }
                i += i3;
            }
            return -1;
        }

        public android.view.View a(int i, int i2) {
            android.view.View view = null;
            if (i2 != -1) {
                int size = this.f249a.size() - 1;
                while (size >= 0) {
                    android.view.View view2 = this.f249a.get(size);
                    androidx.recyclerview.widget.StaggeredGridLayoutManager staggeredGridLayoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager.z && staggeredGridLayoutManager.l(view2) >= i) {
                        break;
                    }
                    androidx.recyclerview.widget.StaggeredGridLayoutManager staggeredGridLayoutManager2 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager2.z && staggeredGridLayoutManager2.l(view2) <= i) || !view2.hasFocusable()) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.f249a.size();
                int i3 = 0;
                while (i3 < size2) {
                    android.view.View view3 = this.f249a.get(i3);
                    androidx.recyclerview.widget.StaggeredGridLayoutManager staggeredGridLayoutManager3 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager3.z && staggeredGridLayoutManager3.l(view3) <= i) {
                        break;
                    }
                    androidx.recyclerview.widget.StaggeredGridLayoutManager staggeredGridLayoutManager4 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager4.z && staggeredGridLayoutManager4.l(view3) >= i) || !view3.hasFocusable()) {
                        break;
                    }
                    i3++;
                    view = view3;
                }
            }
            return view;
        }

        void a() {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarC;
            java.util.ArrayList<android.view.View> arrayList = this.f249a;
            android.view.View view = arrayList.get(arrayList.size() - 1);
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVarB = b(view);
            this.f251c = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.a(view);
            if (cVarB.f && (aVarC = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.E.c(cVarB.a())) != null && aVarC.f244b == 1) {
                this.f251c += aVarC.a(this.e);
            }
        }

        void a(android.view.View view) {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVarB = b(view);
            cVarB.e = this;
            this.f249a.add(view);
            this.f251c = Integer.MIN_VALUE;
            if (this.f249a.size() == 1) {
                this.f250b = Integer.MIN_VALUE;
            }
            if (cVarB.c() || cVarB.b()) {
                this.d += androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b(view);
            }
        }

        void a(boolean z, int i) {
            int iA = z ? a(Integer.MIN_VALUE) : b(Integer.MIN_VALUE);
            c();
            if (iA == Integer.MIN_VALUE) {
                return;
            }
            if (!z || iA >= androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b()) {
                if (z || iA <= androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.f()) {
                    if (i != Integer.MIN_VALUE) {
                        iA += i;
                    }
                    this.f251c = iA;
                    this.f250b = iA;
                }
            }
        }

        int b(int i) {
            int i2 = this.f250b;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.f249a.size() == 0) {
                return i;
            }
            b();
            return this.f250b;
        }

        androidx.recyclerview.widget.StaggeredGridLayoutManager.c b(android.view.View view) {
            return (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) view.getLayoutParams();
        }

        void b() {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarC;
            android.view.View view = this.f249a.get(0);
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVarB = b(view);
            this.f250b = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.d(view);
            if (cVarB.f && (aVarC = androidx.recyclerview.widget.StaggeredGridLayoutManager.this.E.c(cVarB.a())) != null && aVarC.f244b == -1) {
                this.f250b -= aVarC.a(this.e);
            }
        }

        void c() {
            this.f249a.clear();
            i();
            this.d = 0;
        }

        void c(int i) {
            int i2 = this.f250b;
            if (i2 != Integer.MIN_VALUE) {
                this.f250b = i2 + i;
            }
            int i3 = this.f251c;
            if (i3 != Integer.MIN_VALUE) {
                this.f251c = i3 + i;
            }
        }

        void c(android.view.View view) {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVarB = b(view);
            cVarB.e = this;
            this.f249a.add(0, view);
            this.f250b = Integer.MIN_VALUE;
            if (this.f249a.size() == 1) {
                this.f251c = Integer.MIN_VALUE;
            }
            if (cVarB.c() || cVarB.b()) {
                this.d += androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b(view);
            }
        }

        public int d() {
            int size;
            int size2;
            if (androidx.recyclerview.widget.StaggeredGridLayoutManager.this.z) {
                size = this.f249a.size() - 1;
                size2 = -1;
            } else {
                size = 0;
                size2 = this.f249a.size();
            }
            return a(size, size2, true);
        }

        void d(int i) {
            this.f250b = i;
            this.f251c = i;
        }

        public int e() {
            int size;
            int size2;
            if (androidx.recyclerview.widget.StaggeredGridLayoutManager.this.z) {
                size = 0;
                size2 = this.f249a.size();
            } else {
                size = this.f249a.size() - 1;
                size2 = -1;
            }
            return a(size, size2, true);
        }

        public int f() {
            return this.d;
        }

        int g() {
            int i = this.f251c;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            a();
            return this.f251c;
        }

        int h() {
            int i = this.f250b;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            b();
            return this.f250b;
        }

        void i() {
            this.f250b = Integer.MIN_VALUE;
            this.f251c = Integer.MIN_VALUE;
        }

        void j() {
            int size = this.f249a.size();
            android.view.View viewRemove = this.f249a.remove(size - 1);
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVarB = b(viewRemove);
            cVarB.e = null;
            if (cVarB.c() || cVarB.b()) {
                this.d -= androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b(viewRemove);
            }
            if (size == 1) {
                this.f250b = Integer.MIN_VALUE;
            }
            this.f251c = Integer.MIN_VALUE;
        }

        void k() {
            android.view.View viewRemove = this.f249a.remove(0);
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVarB = b(viewRemove);
            cVarB.e = null;
            if (this.f249a.size() == 0) {
                this.f251c = Integer.MIN_VALUE;
            }
            if (cVarB.c() || cVarB.b()) {
                this.d -= androidx.recyclerview.widget.StaggeredGridLayoutManager.this.u.b(viewRemove);
            }
            this.f250b = Integer.MIN_VALUE;
        }
    }

    public StaggeredGridLayoutManager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        androidx.recyclerview.widget.RecyclerView.o.d dVarA = androidx.recyclerview.widget.RecyclerView.o.a(context, attributeSet, i, i2);
        i(dVarA.f221a);
        j(dVarA.f222b);
        c(dVarA.f223c);
        this.y = new androidx.recyclerview.widget.i();
        M();
    }

    private void M() {
        this.u = androidx.recyclerview.widget.k.a(this, this.w);
        this.v = androidx.recyclerview.widget.k.a(this, 1 - this.w);
    }

    private void N() {
        if (this.v.d() == 1073741824) {
            return;
        }
        float fMax = 0.0f;
        int iE = e();
        for (int i = 0; i < iE; i++) {
            android.view.View viewC = c(i);
            float fB = this.v.b(viewC);
            if (fB >= fMax) {
                if (((androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewC.getLayoutParams()).f()) {
                    fB = (fB * 1.0f) / this.s;
                }
                fMax = java.lang.Math.max(fMax, fB);
            }
        }
        int i2 = this.x;
        int iRound = java.lang.Math.round(fMax * this.s);
        if (this.v.d() == Integer.MIN_VALUE) {
            iRound = java.lang.Math.min(iRound, this.v.g());
        }
        k(iRound);
        if (this.x == i2) {
            return;
        }
        for (int i3 = 0; i3 < iE; i3++) {
            android.view.View viewC2 = c(i3);
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewC2.getLayoutParams();
            if (!cVar.f) {
                if (L() && this.w == 1) {
                    int i4 = this.s;
                    int i5 = cVar.e.e;
                    viewC2.offsetLeftAndRight(((-((i4 - 1) - i5)) * this.x) - ((-((i4 - 1) - i5)) * i2));
                } else {
                    int i6 = cVar.e.e;
                    int i7 = this.w;
                    int i8 = (this.x * i6) - (i6 * i2);
                    if (i7 == 1) {
                        viewC2.offsetLeftAndRight(i8);
                    } else {
                        viewC2.offsetTopAndBottom(i8);
                    }
                }
            }
        }
    }

    private void O() {
        this.A = (this.w == 1 || !L()) ? this.z : !this.z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v7 */
    private int a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.i iVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        int i;
        int iR;
        androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVarA;
        int iB;
        int i2;
        int iB2;
        int iB3;
        int i3;
        int i4;
        ?? r9 = 0;
        this.B.set(0, this.s, true);
        if (this.y.i) {
            i = iVar.e == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            i = iVar.e == 1 ? iVar.g + iVar.f327b : iVar.f - iVar.f327b;
        }
        e(iVar.e, i);
        int iB4 = this.A ? this.u.b() : this.u.f();
        boolean z = false;
        while (iVar.a(a0Var) && (this.y.i || !this.B.isEmpty())) {
            android.view.View viewA = iVar.a(vVar);
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewA.getLayoutParams();
            int iA = cVar.a();
            int iD = this.E.d(iA);
            boolean z2 = iD == -1;
            if (z2) {
                fVarA = cVar.f ? this.t[r9] : a(iVar);
                this.E.a(iA, fVarA);
            } else {
                fVarA = this.t[iD];
            }
            androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar = fVarA;
            cVar.e = fVar;
            if (iVar.e == 1) {
                b(viewA);
            } else {
                b(viewA, (int) r9);
            }
            a(viewA, cVar, (boolean) r9);
            if (iVar.e == 1) {
                int iR2 = cVar.f ? r(iB4) : fVar.a(iB4);
                int iB5 = this.u.b(viewA) + iR2;
                if (z2 && cVar.f) {
                    androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarN = n(iR2);
                    aVarN.f244b = -1;
                    aVarN.f243a = iA;
                    this.E.a(aVarN);
                }
                i2 = iB5;
                iB = iR2;
            } else {
                int iU = cVar.f ? u(iB4) : fVar.b(iB4);
                iB = iU - this.u.b(viewA);
                if (z2 && cVar.f) {
                    androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarO = o(iU);
                    aVarO.f244b = 1;
                    aVarO.f243a = iA;
                    this.E.a(aVarO);
                }
                i2 = iU;
            }
            if (cVar.f && iVar.d == -1) {
                if (z2) {
                    this.M = true;
                } else {
                    if (!(iVar.e == 1 ? D() : E())) {
                        androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarC = this.E.c(iA);
                        if (aVarC != null) {
                            aVarC.d = true;
                        }
                        this.M = true;
                    }
                }
            }
            a(viewA, cVar, iVar);
            if (L() && this.w == 1) {
                int iB6 = cVar.f ? this.v.b() : this.v.b() - (((this.s - 1) - fVar.e) * this.x);
                iB3 = iB6;
                iB2 = iB6 - this.v.b(viewA);
            } else {
                int iF = cVar.f ? this.v.f() : (fVar.e * this.x) + this.v.f();
                iB2 = iF;
                iB3 = this.v.b(viewA) + iF;
            }
            if (this.w == 1) {
                i3 = iB2;
                iB2 = iB;
                i4 = iB3;
            } else {
                i3 = iB;
                i4 = i2;
                i2 = iB3;
            }
            a(viewA, i3, iB2, i4, i2);
            if (cVar.f) {
                e(this.y.e, i);
            } else {
                a(fVar, this.y.e, i);
            }
            a(vVar, this.y);
            if (this.y.h && viewA.hasFocusable()) {
                if (cVar.f) {
                    this.B.clear();
                } else {
                    this.B.set(fVar.e, false);
                }
            }
            z = true;
            r9 = 0;
        }
        if (!z) {
            a(vVar, this.y);
        }
        if (this.y.e == -1) {
            iR = this.u.f() - u(this.u.f());
        } else {
            iR = r(this.u.b()) - this.u.b();
        }
        if (iR > 0) {
            return java.lang.Math.min(iVar.f327b, iR);
        }
        return 0;
    }

    private androidx.recyclerview.widget.StaggeredGridLayoutManager.f a(androidx.recyclerview.widget.i iVar) {
        int i;
        int i2;
        int i3 = -1;
        if (v(iVar.e)) {
            i = this.s - 1;
            i2 = -1;
        } else {
            i = 0;
            i3 = this.s;
            i2 = 1;
        }
        androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar = null;
        if (iVar.e == 1) {
            int i4 = Integer.MAX_VALUE;
            int iF = this.u.f();
            while (i != i3) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar2 = this.t[i];
                int iA = fVar2.a(iF);
                if (iA < i4) {
                    fVar = fVar2;
                    i4 = iA;
                }
                i += i2;
            }
            return fVar;
        }
        int i5 = Integer.MIN_VALUE;
        int iB = this.u.b();
        while (i != i3) {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar3 = this.t[i];
            int iB2 = fVar3.b(iB);
            if (iB2 > i5) {
                fVar = fVar3;
                i5 = iB2;
            }
            i += i2;
        }
        return fVar;
    }

    private void a(android.view.View view, int i, int i2, boolean z) {
        a(view, this.K);
        androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) view.getLayoutParams();
        int i3 = ((android.view.ViewGroup.MarginLayoutParams) cVar).leftMargin;
        android.graphics.Rect rect = this.K;
        int iC = c(i, i3 + rect.left, ((android.view.ViewGroup.MarginLayoutParams) cVar).rightMargin + rect.right);
        int i4 = ((android.view.ViewGroup.MarginLayoutParams) cVar).topMargin;
        android.graphics.Rect rect2 = this.K;
        int iC2 = c(i2, i4 + rect2.top, ((android.view.ViewGroup.MarginLayoutParams) cVar).bottomMargin + rect2.bottom);
        if (z ? b(view, iC, iC2, cVar) : a(view, iC, iC2, cVar)) {
            view.measure(iC, iC2);
        }
    }

    private void a(android.view.View view, androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar, androidx.recyclerview.widget.i iVar) {
        if (iVar.e == 1) {
            if (cVar.f) {
                p(view);
                return;
            } else {
                cVar.e.a(view);
                return;
            }
        }
        if (cVar.f) {
            q(view);
        } else {
            cVar.e.c(view);
        }
    }

    private void a(android.view.View view, androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar, boolean z) {
        int iA;
        int iA2;
        if (!cVar.f) {
            if (this.w == 1) {
                iA = androidx.recyclerview.widget.RecyclerView.o.a(this.x, r(), 0, ((android.view.ViewGroup.MarginLayoutParams) cVar).width, false);
            } else {
                iA = androidx.recyclerview.widget.RecyclerView.o.a(q(), r(), n() + o(), ((android.view.ViewGroup.MarginLayoutParams) cVar).width, true);
                iA2 = androidx.recyclerview.widget.RecyclerView.o.a(this.x, i(), 0, ((android.view.ViewGroup.MarginLayoutParams) cVar).height, false);
            }
            a(view, iA, iA2, z);
        }
        if (this.w != 1) {
            a(view, androidx.recyclerview.widget.RecyclerView.o.a(q(), r(), n() + o(), ((android.view.ViewGroup.MarginLayoutParams) cVar).width, true), this.J, z);
            return;
        }
        iA = this.J;
        iA2 = androidx.recyclerview.widget.RecyclerView.o.a(h(), i(), p() + m(), ((android.view.ViewGroup.MarginLayoutParams) cVar).height, true);
        a(view, iA, iA2, z);
    }

    private void a(androidx.recyclerview.widget.RecyclerView.v vVar, int i) {
        for (int iE = e() - 1; iE >= 0; iE--) {
            android.view.View viewC = c(iE);
            if (this.u.d(viewC) < i || this.u.f(viewC) < i) {
                return;
            }
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewC.getLayoutParams();
            if (cVar.f) {
                for (int i2 = 0; i2 < this.s; i2++) {
                    if (this.t[i2].f249a.size() == 1) {
                        return;
                    }
                }
                for (int i3 = 0; i3 < this.s; i3++) {
                    this.t[i3].j();
                }
            } else if (cVar.e.f249a.size() == 1) {
                return;
            } else {
                cVar.e.j();
            }
            a(viewC, vVar);
        }
    }

    private void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, boolean z) {
        int iB;
        int iR = r(Integer.MIN_VALUE);
        if (iR != Integer.MIN_VALUE && (iB = this.u.b() - iR) > 0) {
            int i = iB - (-c(-iB, vVar, a0Var));
            if (!z || i <= 0) {
                return;
            }
            this.u.a(i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0010, code lost:
    
        if (r4.e == (-1)) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(androidx.recyclerview.widget.RecyclerView.v r3, androidx.recyclerview.widget.i r4) {
        /*
            r2 = this;
            boolean r0 = r4.f326a
            if (r0 == 0) goto L4d
            boolean r0 = r4.i
            if (r0 == 0) goto L9
            goto L4d
        L9:
            int r0 = r4.f327b
            r1 = -1
            if (r0 != 0) goto L1e
            int r0 = r4.e
            if (r0 != r1) goto L18
        L12:
            int r4 = r4.g
        L14:
            r2.a(r3, r4)
            goto L4d
        L18:
            int r4 = r4.f
        L1a:
            r2.b(r3, r4)
            goto L4d
        L1e:
            int r0 = r4.e
            if (r0 != r1) goto L37
            int r0 = r4.f
            int r1 = r2.s(r0)
            int r0 = r0 - r1
            if (r0 >= 0) goto L2c
            goto L12
        L2c:
            int r1 = r4.g
            int r4 = r4.f327b
            int r4 = java.lang.Math.min(r0, r4)
            int r4 = r1 - r4
            goto L14
        L37:
            int r0 = r4.g
            int r0 = r2.t(r0)
            int r1 = r4.g
            int r0 = r0 - r1
            if (r0 >= 0) goto L43
            goto L18
        L43:
            int r1 = r4.f
            int r4 = r4.f327b
            int r4 = java.lang.Math.min(r0, r4)
            int r4 = r4 + r1
            goto L1a
        L4d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.a(androidx.recyclerview.widget.RecyclerView$v, androidx.recyclerview.widget.i):void");
    }

    private void a(androidx.recyclerview.widget.StaggeredGridLayoutManager.b bVar) {
        boolean z;
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar = this.I;
        int i = eVar.f248c;
        if (i > 0) {
            if (i == this.s) {
                for (int i2 = 0; i2 < this.s; i2++) {
                    this.t[i2].c();
                    androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar2 = this.I;
                    int iB = eVar2.d[i2];
                    if (iB != Integer.MIN_VALUE) {
                        iB += eVar2.i ? this.u.b() : this.u.f();
                    }
                    this.t[i2].d(iB);
                }
            } else {
                eVar.b();
                androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar3 = this.I;
                eVar3.f246a = eVar3.f247b;
            }
        }
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar4 = this.I;
        this.H = eVar4.j;
        c(eVar4.h);
        O();
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar5 = this.I;
        int i3 = eVar5.f246a;
        if (i3 != -1) {
            this.C = i3;
            z = eVar5.i;
        } else {
            z = this.A;
        }
        bVar.f240c = z;
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar6 = this.I;
        if (eVar6.e > 1) {
            androidx.recyclerview.widget.StaggeredGridLayoutManager.d dVar = this.E;
            dVar.f241a = eVar6.f;
            dVar.f242b = eVar6.g;
        }
    }

    private void a(androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar, int i, int i2) {
        int iF = fVar.f();
        if (i == -1) {
            if (fVar.h() + iF > i2) {
                return;
            }
        } else if (fVar.g() - iF < i2) {
            return;
        }
        this.B.set(fVar.e, false);
    }

    private boolean a(androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar) {
        if (this.A) {
            if (fVar.g() < this.u.b()) {
                java.util.ArrayList<android.view.View> arrayList = fVar.f249a;
                return !fVar.b(arrayList.get(arrayList.size() - 1)).f;
            }
        } else if (fVar.h() > this.u.f()) {
            return !fVar.b(fVar.f249a.get(0)).f;
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0025  */
    /* JADX WARN: Code duplicated, block: B:17:0x0028 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:19:0x002b  */
    /* JADX WARN: Code duplicated, block: B:20:0x0036  */
    /* JADX WARN: Code duplicated, block: B:21:0x003c  */
    /* JADX WARN: Code duplicated, block: B:23:0x0043 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x0044  */
    /* JADX WARN: Code duplicated, block: B:26:0x0048  */
    /* JADX WARN: Code duplicated, block: B:27:0x004d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0053  */
    /* JADX WARN: Code duplicated, block: B:31:? A[RETURN, SYNTHETIC] */
    private void b(int i, int i2, int i3) {
        int i4;
        int i5;
        int I;
        int I2 = this.A ? I() : H();
        if (i3 == 8) {
            if (i < i2) {
                i4 = i2 + 1;
            } else {
                i4 = i + 1;
                i5 = i2;
            }
            this.E.e(i5);
            if (i3 != 1) {
                this.E.a(i, i2);
            } else if (i3 != 2) {
                this.E.b(i, i2);
            } else if (i3 == 8) {
                this.E.b(i, 1);
                this.E.a(i2, 1);
            }
            if (i4 <= I2) {
                return;
            }
            if (this.A) {
                I = H();
            } else {
                I = I();
            }
            if (i5 <= I) {
                y();
            }
        }
        i4 = i + i2;
        i5 = i;
        this.E.e(i5);
        if (i3 != 1) {
            this.E.a(i, i2);
        } else if (i3 != 2) {
            this.E.b(i, i2);
        } else if (i3 == 8) {
            this.E.b(i, 1);
            this.E.a(i2, 1);
        }
        if (i4 <= I2) {
            return;
        }
        if (this.A) {
            I = H();
        } else {
            I = I();
        }
        if (i5 <= I) {
            y();
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0036  */
    /* JADX WARN: Code duplicated, block: B:18:0x004d  */
    private void b(int i, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        int iG;
        int iG2;
        int iB;
        androidx.recyclerview.widget.i iVar = this.y;
        boolean z = false;
        iVar.f327b = 0;
        iVar.f328c = i;
        if (w() && (iB = a0Var.b()) != -1) {
            if (this.A == (iB < i)) {
                iG = this.u.g();
            } else {
                iG2 = this.u.g();
                iG = 0;
            }
            if (f()) {
                this.y.f = this.u.f() - iG2;
                this.y.g = this.u.b() + iG;
            } else {
                this.y.g = this.u.a() + iG;
                this.y.f = -iG2;
            }
            androidx.recyclerview.widget.i iVar2 = this.y;
            iVar2.h = false;
            iVar2.f326a = true;
            if (this.u.d() == 0 && this.u.a() == 0) {
                z = true;
            }
            iVar2.i = z;
        }
        iG = 0;
        iG2 = 0;
        if (f()) {
            this.y.f = this.u.f() - iG2;
            this.y.g = this.u.b() + iG;
        } else {
            this.y.g = this.u.a() + iG;
            this.y.f = -iG2;
        }
        androidx.recyclerview.widget.i iVar3 = this.y;
        iVar3.h = false;
        iVar3.f326a = true;
        if (this.u.d() == 0) {
            z = true;
        }
        iVar3.i = z;
    }

    private void b(androidx.recyclerview.widget.RecyclerView.v vVar, int i) {
        while (e() > 0) {
            android.view.View viewC = c(0);
            if (this.u.a(viewC) > i || this.u.e(viewC) > i) {
                return;
            }
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewC.getLayoutParams();
            if (cVar.f) {
                for (int i2 = 0; i2 < this.s; i2++) {
                    if (this.t[i2].f249a.size() == 1) {
                        return;
                    }
                }
                for (int i3 = 0; i3 < this.s; i3++) {
                    this.t[i3].k();
                }
            } else if (cVar.e.f249a.size() == 1) {
                return;
            } else {
                cVar.e.k();
            }
            a(viewC, vVar);
        }
    }

    private void b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, boolean z) {
        int iF;
        int iU = u(Integer.MAX_VALUE);
        if (iU != Integer.MAX_VALUE && (iF = iU - this.u.f()) > 0) {
            int iC = iF - c(iF, vVar, a0Var);
            if (!z || iC <= 0) {
                return;
            }
            this.u.a(-iC);
        }
    }

    private int c(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = android.view.View.MeasureSpec.getMode(i);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, (android.view.View.MeasureSpec.getSize(i) - i2) - i3), mode) : i;
    }

    /* JADX WARN: Code duplicated, block: B:89:0x014e  */
    private void c(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, boolean z) {
        boolean z2;
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar;
        androidx.recyclerview.widget.StaggeredGridLayoutManager.b bVar = this.L;
        if (!(this.I == null && this.C == -1) && a0Var.a() == 0) {
            b(vVar);
            bVar.b();
            return;
        }
        boolean z3 = (bVar.e && this.C == -1 && this.I == null) ? false : true;
        if (z3) {
            bVar.b();
            if (this.I != null) {
                a(bVar);
            } else {
                O();
                bVar.f240c = this.A;
            }
            b(a0Var, bVar);
            bVar.e = true;
        }
        if (this.I == null && this.C == -1 && (bVar.f240c != this.G || L() != this.H)) {
            this.E.a();
            bVar.d = true;
        }
        if (e() > 0 && ((eVar = this.I) == null || eVar.f248c < 1)) {
            if (bVar.d) {
                for (int i = 0; i < this.s; i++) {
                    this.t[i].c();
                    int i2 = bVar.f239b;
                    if (i2 != Integer.MIN_VALUE) {
                        this.t[i].d(i2);
                    }
                }
            } else if (z3 || this.L.f == null) {
                for (int i3 = 0; i3 < this.s; i3++) {
                    this.t[i3].a(this.A, bVar.f239b);
                }
                this.L.a(this.t);
            } else {
                for (int i4 = 0; i4 < this.s; i4++) {
                    androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar = this.t[i4];
                    fVar.c();
                    fVar.d(this.L.f[i4]);
                }
            }
        }
        a(vVar);
        this.y.f326a = false;
        this.M = false;
        k(this.v.g());
        b(bVar.f238a, a0Var);
        if (bVar.f240c) {
            w(-1);
            a(vVar, this.y, a0Var);
            w(1);
        } else {
            w(1);
            a(vVar, this.y, a0Var);
            w(-1);
        }
        androidx.recyclerview.widget.i iVar = this.y;
        iVar.f328c = bVar.f238a + iVar.d;
        a(vVar, iVar, a0Var);
        N();
        if (e() > 0) {
            if (this.A) {
                a(vVar, a0Var, true);
                b(vVar, a0Var, false);
            } else {
                b(vVar, a0Var, true);
                a(vVar, a0Var, false);
            }
        }
        if (z && !a0Var.d()) {
            if (this.F != 0 && e() > 0 && (this.M || J() != null)) {
                a(this.P);
                z2 = F();
            }
        }
        if (a0Var.d()) {
            this.L.b();
        }
        this.G = bVar.f240c;
        this.H = L();
        if (z2) {
            this.L.b();
            c(vVar, a0Var, false);
        }
    }

    private boolean c(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.StaggeredGridLayoutManager.b bVar) {
        boolean z = this.G;
        int iA = a0Var.a();
        bVar.f238a = z ? q(iA) : p(iA);
        bVar.f239b = Integer.MIN_VALUE;
        return true;
    }

    private void e(int i, int i2) {
        for (int i3 = 0; i3 < this.s; i3++) {
            if (!this.t[i3].f249a.isEmpty()) {
                a(this.t[i3], i, i2);
            }
        }
    }

    private int h(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0) {
            return 0;
        }
        return androidx.recyclerview.widget.m.a(a0Var, this.u, b(!this.N), a(!this.N), this, this.N);
    }

    private int i(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0) {
            return 0;
        }
        return androidx.recyclerview.widget.m.a(a0Var, this.u, b(!this.N), a(!this.N), this, this.N, this.A);
    }

    private int j(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0) {
            return 0;
        }
        return androidx.recyclerview.widget.m.b(a0Var, this.u, b(!this.N), a(!this.N), this, this.N);
    }

    private int l(int i) {
        if (e() == 0) {
            return this.A ? 1 : -1;
        }
        return (i < H()) != this.A ? -1 : 1;
    }

    private int m(int i) {
        if (i == 1) {
            return (this.w != 1 && L()) ? 1 : -1;
        }
        if (i == 2) {
            return (this.w != 1 && L()) ? -1 : 1;
        }
        if (i == 17) {
            return this.w == 0 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 33) {
            return this.w == 1 ? -1 : Integer.MIN_VALUE;
        }
        if (i != 66) {
            return (i == 130 && this.w == 1) ? 1 : Integer.MIN_VALUE;
        }
        return this.w == 0 ? 1 : Integer.MIN_VALUE;
    }

    private androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a n(int i) {
        androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar = new androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a();
        aVar.f245c = new int[this.s];
        for (int i2 = 0; i2 < this.s; i2++) {
            aVar.f245c[i2] = i - this.t[i2].a(i);
        }
        return aVar;
    }

    private androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a o(int i) {
        androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVar = new androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a();
        aVar.f245c = new int[this.s];
        for (int i2 = 0; i2 < this.s; i2++) {
            aVar.f245c[i2] = this.t[i2].b(i) - i;
        }
        return aVar;
    }

    private int p(int i) {
        int iE = e();
        for (int i2 = 0; i2 < iE; i2++) {
            int iL = l(c(i2));
            if (iL >= 0 && iL < i) {
                return iL;
            }
        }
        return 0;
    }

    private void p(android.view.View view) {
        for (int i = this.s - 1; i >= 0; i--) {
            this.t[i].a(view);
        }
    }

    private int q(int i) {
        for (int iE = e() - 1; iE >= 0; iE--) {
            int iL = l(c(iE));
            if (iL >= 0 && iL < i) {
                return iL;
            }
        }
        return 0;
    }

    private void q(android.view.View view) {
        for (int i = this.s - 1; i >= 0; i--) {
            this.t[i].c(view);
        }
    }

    private int r(int i) {
        int iA = this.t[0].a(i);
        for (int i2 = 1; i2 < this.s; i2++) {
            int iA2 = this.t[i2].a(i);
            if (iA2 > iA) {
                iA = iA2;
            }
        }
        return iA;
    }

    private int s(int i) {
        int iB = this.t[0].b(i);
        for (int i2 = 1; i2 < this.s; i2++) {
            int iB2 = this.t[i2].b(i);
            if (iB2 > iB) {
                iB = iB2;
            }
        }
        return iB;
    }

    private int t(int i) {
        int iA = this.t[0].a(i);
        for (int i2 = 1; i2 < this.s; i2++) {
            int iA2 = this.t[i2].a(i);
            if (iA2 < iA) {
                iA = iA2;
            }
        }
        return iA;
    }

    private int u(int i) {
        int iB = this.t[0].b(i);
        for (int i2 = 1; i2 < this.s; i2++) {
            int iB2 = this.t[i2].b(i);
            if (iB2 < iB) {
                iB = iB2;
            }
        }
        return iB;
    }

    private boolean v(int i) {
        if (this.w == 0) {
            return (i == -1) != this.A;
        }
        return ((i == -1) == this.A) == L();
    }

    private void w(int i) {
        androidx.recyclerview.widget.i iVar = this.y;
        iVar.e = i;
        iVar.d = this.A != (i == -1) ? -1 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean C() {
        return this.I == null;
    }

    boolean D() {
        int iA = this.t[0].a(Integer.MIN_VALUE);
        for (int i = 1; i < this.s; i++) {
            if (this.t[i].a(Integer.MIN_VALUE) != iA) {
                return false;
            }
        }
        return true;
    }

    boolean E() {
        int iB = this.t[0].b(Integer.MIN_VALUE);
        for (int i = 1; i < this.s; i++) {
            if (this.t[i].b(Integer.MIN_VALUE) != iB) {
                return false;
            }
        }
        return true;
    }

    boolean F() {
        int iH;
        int I;
        if (e() == 0 || this.F == 0 || !t()) {
            return false;
        }
        if (this.A) {
            iH = I();
            I = H();
        } else {
            iH = H();
            I = I();
        }
        if (iH == 0 && J() != null) {
            this.E.a();
        } else {
            if (!this.M) {
                return false;
            }
            int i = this.A ? -1 : 1;
            int i2 = I + 1;
            androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarA = this.E.a(iH, i2, i, true);
            if (aVarA == null) {
                this.M = false;
                this.E.b(i2);
                return false;
            }
            androidx.recyclerview.widget.StaggeredGridLayoutManager.d.a aVarA2 = this.E.a(iH, aVarA.f243a, i * (-1), true);
            if (aVarA2 == null) {
                this.E.b(aVarA.f243a);
            } else {
                this.E.b(aVarA2.f243a + 1);
            }
        }
        z();
        y();
        return true;
    }

    int G() {
        android.view.View viewA = this.A ? a(true) : b(true);
        if (viewA == null) {
            return -1;
        }
        return l(viewA);
    }

    int H() {
        if (e() == 0) {
            return 0;
        }
        return l(c(0));
    }

    int I() {
        int iE = e();
        if (iE == 0) {
            return 0;
        }
        return l(c(iE - 1));
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0088  */
    /* JADX WARN: Code duplicated, block: B:38:0x008a  */
    android.view.View J() {
        int i;
        int i2;
        boolean z;
        int iE = e() - 1;
        java.util.BitSet bitSet = new java.util.BitSet(this.s);
        bitSet.set(0, this.s, true);
        byte b2 = (this.w == 1 && L()) ? (byte) 1 : (byte) -1;
        if (this.A) {
            i = -1;
        } else {
            i = iE + 1;
            iE = 0;
        }
        int i3 = iE < i ? 1 : -1;
        while (iE != i) {
            android.view.View viewC = c(iE);
            androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewC.getLayoutParams();
            if (bitSet.get(cVar.e.e)) {
                if (a(cVar.e)) {
                    return viewC;
                }
                bitSet.clear(cVar.e.e);
            }
            if (!cVar.f && (i2 = iE + i3) != i) {
                android.view.View viewC2 = c(i2);
                if (this.A) {
                    int iA = this.u.a(viewC);
                    int iA2 = this.u.a(viewC2);
                    if (iA < iA2) {
                        return viewC;
                    }
                    if (iA == iA2) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    int iD = this.u.d(viewC);
                    int iD2 = this.u.d(viewC2);
                    if (iD > iD2) {
                        return viewC;
                    }
                    if (iD == iD2) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    if ((cVar.e.e - ((androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewC2.getLayoutParams()).e.e < 0) != (b2 < 0)) {
                        return viewC;
                    }
                } else {
                    continue;
                }
            }
            iE += i3;
        }
        return null;
    }

    public void K() {
        this.E.a();
        y();
    }

    boolean L() {
        return j() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int a(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return c(i, vVar, a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int a(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return h(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.w == 1 ? this.s : super.a(vVar, a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public android.view.View a(android.view.View view, int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        android.view.View viewC;
        android.view.View viewA;
        if (e() == 0 || (viewC = c(view)) == null) {
            return null;
        }
        O();
        int iM = m(i);
        if (iM == Integer.MIN_VALUE) {
            return null;
        }
        androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) viewC.getLayoutParams();
        boolean z = cVar.f;
        androidx.recyclerview.widget.StaggeredGridLayoutManager.f fVar = cVar.e;
        int I = iM == 1 ? I() : H();
        b(I, a0Var);
        w(iM);
        androidx.recyclerview.widget.i iVar = this.y;
        iVar.f328c = iVar.d + I;
        iVar.f327b = (int) (this.u.g() * 0.33333334f);
        androidx.recyclerview.widget.i iVar2 = this.y;
        iVar2.h = true;
        iVar2.f326a = false;
        a(vVar, iVar2, a0Var);
        this.G = this.A;
        if (!z && (viewA = fVar.a(I, iM)) != null && viewA != viewC) {
            return viewA;
        }
        if (v(iM)) {
            for (int i2 = this.s - 1; i2 >= 0; i2--) {
                android.view.View viewA2 = this.t[i2].a(I, iM);
                if (viewA2 != null && viewA2 != viewC) {
                    return viewA2;
                }
            }
        } else {
            for (int i3 = 0; i3 < this.s; i3++) {
                android.view.View viewA3 = this.t[i3].a(I, iM);
                if (viewA3 != null && viewA3 != viewC) {
                    return viewA3;
                }
            }
        }
        boolean z2 = (this.z ^ true) == (iM == -1);
        if (!z) {
            android.view.View viewB = b(z2 ? fVar.d() : fVar.e());
            if (viewB != null && viewB != viewC) {
                return viewB;
            }
        }
        if (v(iM)) {
            for (int i4 = this.s - 1; i4 >= 0; i4--) {
                if (i4 != fVar.e) {
                    androidx.recyclerview.widget.StaggeredGridLayoutManager.f[] fVarArr = this.t;
                    android.view.View viewB2 = b(z2 ? fVarArr[i4].d() : fVarArr[i4].e());
                    if (viewB2 != null && viewB2 != viewC) {
                        return viewB2;
                    }
                }
            }
        } else {
            for (int i5 = 0; i5 < this.s; i5++) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.f[] fVarArr2 = this.t;
                android.view.View viewB3 = b(z2 ? fVarArr2[i5].d() : fVarArr2[i5].e());
                if (viewB3 != null && viewB3 != viewC) {
                    return viewB3;
                }
            }
        }
        return null;
    }

    android.view.View a(boolean z) {
        int iF = this.u.f();
        int iB = this.u.b();
        android.view.View view = null;
        for (int iE = e() - 1; iE >= 0; iE--) {
            android.view.View viewC = c(iE);
            int iD = this.u.d(viewC);
            int iA = this.u.a(viewC);
            if (iA > iF && iD < iB) {
                if (iA <= iB || !z) {
                    return viewC;
                }
                if (view == null) {
                    view = viewC;
                }
            }
        }
        return view;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public androidx.recyclerview.widget.RecyclerView.p a(android.content.Context context, android.util.AttributeSet attributeSet) {
        return new androidx.recyclerview.widget.StaggeredGridLayoutManager.c(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public androidx.recyclerview.widget.RecyclerView.p a(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.view.ViewGroup.MarginLayoutParams ? new androidx.recyclerview.widget.StaggeredGridLayoutManager.c((android.view.ViewGroup.MarginLayoutParams) layoutParams) : new androidx.recyclerview.widget.StaggeredGridLayoutManager.c(layoutParams);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(int i, int i2, androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.RecyclerView.o.c cVar) {
        int iA;
        int iB;
        if (this.w != 0) {
            i = i2;
        }
        if (e() == 0 || i == 0) {
            return;
        }
        a(i, a0Var);
        int[] iArr = this.O;
        if (iArr == null || iArr.length < this.s) {
            this.O = new int[this.s];
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.s; i4++) {
            androidx.recyclerview.widget.i iVar = this.y;
            if (iVar.d == -1) {
                iA = iVar.f;
                iB = this.t[i4].b(iA);
            } else {
                iA = this.t[i4].a(iVar.g);
                iB = this.y.g;
            }
            int i5 = iA - iB;
            if (i5 >= 0) {
                this.O[i3] = i5;
                i3++;
            }
        }
        java.util.Arrays.sort(this.O, 0, i3);
        for (int i6 = 0; i6 < i3 && this.y.a(a0Var); i6++) {
            cVar.a(this.y.f328c, this.O[i6]);
            androidx.recyclerview.widget.i iVar2 = this.y;
            iVar2.f328c += iVar2.d;
        }
    }

    void a(int i, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        int iH;
        int i2;
        if (i > 0) {
            iH = I();
            i2 = 1;
        } else {
            iH = H();
            i2 = -1;
        }
        this.y.f326a = true;
        b(iH, a0Var);
        w(i2);
        androidx.recyclerview.widget.i iVar = this.y;
        iVar.f328c = iH + iVar.d;
        iVar.f327b = java.lang.Math.abs(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(android.graphics.Rect rect, int i, int i2) {
        int iA;
        int iA2;
        int iN = n() + o();
        int iP = p() + m();
        if (this.w == 1) {
            iA2 = androidx.recyclerview.widget.RecyclerView.o.a(i2, rect.height() + iP, k());
            iA = androidx.recyclerview.widget.RecyclerView.o.a(i, (this.x * this.s) + iN, l());
        } else {
            iA = androidx.recyclerview.widget.RecyclerView.o.a(i, rect.width() + iN, l());
            iA2 = androidx.recyclerview.widget.RecyclerView.o.a(i2, (this.x * this.s) + iP, k());
        }
        c(iA, iA2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(android.os.Parcelable parcelable) {
        if (parcelable instanceof androidx.recyclerview.widget.StaggeredGridLayoutManager.e) {
            this.I = (androidx.recyclerview.widget.StaggeredGridLayoutManager.e) parcelable;
            y();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.a(accessibilityEvent);
        if (e() > 0) {
            android.view.View viewB = b(false);
            android.view.View viewA = a(false);
            if (viewB == null || viewA == null) {
                return;
            }
            int iL = l(viewB);
            int iL2 = l(viewA);
            if (iL < iL2) {
                accessibilityEvent.setFromIndex(iL);
                accessibilityEvent.setToIndex(iL2);
            } else {
                accessibilityEvent.setFromIndex(iL2);
                accessibilityEvent.setToIndex(iL);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var, android.view.View view, a.c.e.q.c cVar) {
        int iE;
        int i;
        int iE2;
        int i2;
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof androidx.recyclerview.widget.StaggeredGridLayoutManager.c)) {
            super.a(view, cVar);
            return;
        }
        androidx.recyclerview.widget.StaggeredGridLayoutManager.c cVar2 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) layoutParams;
        if (this.w == 0) {
            iE = cVar2.e();
            i = cVar2.f ? this.s : 1;
            iE2 = -1;
            i2 = -1;
        } else {
            iE = -1;
            i = -1;
            iE2 = cVar2.e();
            i2 = cVar2.f ? this.s : 1;
        }
        cVar.b(a.c.e.q.c.C0004c.a(iE, i, iE2, i2, false, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        b(i, i2, 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2, int i3) {
        b(i, i2, 8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2, java.lang.Object obj) {
        b(i, i2, 4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void a(java.lang.String str) {
        if (this.I == null) {
            super.a(str);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean a() {
        return this.w == 0;
    }

    boolean a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.StaggeredGridLayoutManager.b bVar) {
        int i;
        int iF;
        int iD;
        if (!a0Var.d() && (i = this.C) != -1) {
            if (i >= 0 && i < a0Var.a()) {
                androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar = this.I;
                if (eVar == null || eVar.f246a == -1 || eVar.f248c < 1) {
                    android.view.View viewB = b(this.C);
                    if (viewB != null) {
                        bVar.f238a = this.A ? I() : H();
                        if (this.D != Integer.MIN_VALUE) {
                            if (bVar.f240c) {
                                iF = this.u.b() - this.D;
                                iD = this.u.a(viewB);
                            } else {
                                iF = this.u.f() + this.D;
                                iD = this.u.d(viewB);
                            }
                            bVar.f239b = iF - iD;
                            return true;
                        }
                        if (this.u.b(viewB) > this.u.g()) {
                            bVar.f239b = bVar.f240c ? this.u.b() : this.u.f();
                            return true;
                        }
                        int iD2 = this.u.d(viewB) - this.u.f();
                        if (iD2 < 0) {
                            bVar.f239b = -iD2;
                            return true;
                        }
                        int iB = this.u.b() - this.u.a(viewB);
                        if (iB < 0) {
                            bVar.f239b = iB;
                            return true;
                        }
                        bVar.f239b = Integer.MIN_VALUE;
                    } else {
                        int i2 = this.C;
                        bVar.f238a = i2;
                        int i3 = this.D;
                        if (i3 == Integer.MIN_VALUE) {
                            bVar.f240c = l(i2) == 1;
                            bVar.a();
                        } else {
                            bVar.a(i3);
                        }
                        bVar.d = true;
                    }
                } else {
                    bVar.f239b = Integer.MIN_VALUE;
                    bVar.f238a = this.C;
                }
                return true;
            }
            this.C = -1;
            this.D = Integer.MIN_VALUE;
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean a(androidx.recyclerview.widget.RecyclerView.p pVar) {
        return pVar instanceof androidx.recyclerview.widget.StaggeredGridLayoutManager.c;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int b(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return c(i, vVar, a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int b(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return i(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int b(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return this.w == 0 ? this.s : super.b(vVar, a0Var);
    }

    android.view.View b(boolean z) {
        int iF = this.u.f();
        int iB = this.u.b();
        int iE = e();
        android.view.View view = null;
        for (int i = 0; i < iE; i++) {
            android.view.View viewC = c(i);
            int iD = this.u.d(viewC);
            if (this.u.a(viewC) > iF && iD < iB) {
                if (iD >= iF || !z) {
                    return viewC;
                }
                if (view == null) {
                    view = viewC;
                }
            }
        }
        return view;
    }

    void b(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.StaggeredGridLayoutManager.b bVar) {
        if (a(a0Var, bVar) || c(a0Var, bVar)) {
            return;
        }
        bVar.a();
        bVar.f238a = 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void b(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
        b(i, i2, 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void b(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.v vVar) {
        super.b(recyclerView, vVar);
        a(this.P);
        for (int i = 0; i < this.s; i++) {
            this.t[i].c();
        }
        recyclerView.requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean b() {
        return this.w == 1;
    }

    int c(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        if (e() == 0 || i == 0) {
            return 0;
        }
        a(i, a0Var);
        int iA = a(vVar, this.y, a0Var);
        if (this.y.f327b >= iA) {
            i = i < 0 ? -iA : iA;
        }
        this.u.a(-i);
        this.G = this.A;
        androidx.recyclerview.widget.i iVar = this.y;
        iVar.f327b = 0;
        a(vVar, iVar);
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int c(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return j(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public androidx.recyclerview.widget.RecyclerView.p c() {
        return this.w == 0 ? new androidx.recyclerview.widget.StaggeredGridLayoutManager.c(-2, -1) : new androidx.recyclerview.widget.StaggeredGridLayoutManager.c(-1, -2);
    }

    public void c(boolean z) {
        a((java.lang.String) null);
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar = this.I;
        if (eVar != null && eVar.h != z) {
            eVar.h = z;
        }
        this.z = z;
        y();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int d(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return h(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void d(int i) {
        super.d(i);
        for (int i2 = 0; i2 < this.s; i2++) {
            this.t[i2].c(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void d(androidx.recyclerview.widget.RecyclerView recyclerView) {
        this.E.a();
        y();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int e(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return i(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void e(int i) {
        super.e(i);
        for (int i2 = 0; i2 < this.s; i2++) {
            this.t[i2].c(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void e(androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        c(vVar, a0Var, true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public int f(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        return j(a0Var);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void f(int i) {
        if (i == 0) {
            F();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void g(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        super.g(a0Var);
        this.C = -1;
        this.D = Integer.MIN_VALUE;
        this.I = null;
        this.L.b();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public void h(int i) {
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar = this.I;
        if (eVar != null && eVar.f246a != i) {
            eVar.a();
        }
        this.C = i;
        this.D = Integer.MIN_VALUE;
        y();
    }

    public void i(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("invalid orientation.");
        }
        a((java.lang.String) null);
        if (i == this.w) {
            return;
        }
        this.w = i;
        androidx.recyclerview.widget.k kVar = this.u;
        this.u = this.v;
        this.v = kVar;
        y();
    }

    public void j(int i) {
        a((java.lang.String) null);
        if (i != this.s) {
            K();
            this.s = i;
            this.B = new java.util.BitSet(this.s);
            this.t = new androidx.recyclerview.widget.StaggeredGridLayoutManager.f[this.s];
            for (int i2 = 0; i2 < this.s; i2++) {
                this.t[i2] = new androidx.recyclerview.widget.StaggeredGridLayoutManager.f(i2);
            }
            y();
        }
    }

    void k(int i) {
        this.x = i / this.s;
        this.J = android.view.View.MeasureSpec.makeMeasureSpec(i, this.v.d());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public boolean u() {
        return this.F != 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.o
    public android.os.Parcelable x() {
        int iB;
        int iF;
        int[] iArr;
        if (this.I != null) {
            return new androidx.recyclerview.widget.StaggeredGridLayoutManager.e(this.I);
        }
        androidx.recyclerview.widget.StaggeredGridLayoutManager.e eVar = new androidx.recyclerview.widget.StaggeredGridLayoutManager.e();
        eVar.h = this.z;
        eVar.i = this.G;
        eVar.j = this.H;
        androidx.recyclerview.widget.StaggeredGridLayoutManager.d dVar = this.E;
        if (dVar == null || (iArr = dVar.f241a) == null) {
            eVar.e = 0;
        } else {
            eVar.f = iArr;
            eVar.e = iArr.length;
            eVar.g = dVar.f242b;
        }
        if (e() > 0) {
            eVar.f246a = this.G ? I() : H();
            eVar.f247b = G();
            int i = this.s;
            eVar.f248c = i;
            eVar.d = new int[i];
            for (int i2 = 0; i2 < this.s; i2++) {
                if (this.G) {
                    iB = this.t[i2].a(Integer.MIN_VALUE);
                    if (iB != Integer.MIN_VALUE) {
                        iF = this.u.b();
                        iB -= iF;
                    }
                } else {
                    iB = this.t[i2].b(Integer.MIN_VALUE);
                    if (iB != Integer.MIN_VALUE) {
                        iF = this.u.f();
                        iB -= iF;
                    }
                }
                eVar.d[i2] = iB;
            }
        } else {
            eVar.f246a = -1;
            eVar.f247b = -1;
            eVar.f248c = 0;
        }
        return eVar;
    }
}
