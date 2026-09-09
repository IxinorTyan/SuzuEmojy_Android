package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final int[] f151b = {0, 4, 8};

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private static android.util.SparseIntArray f152c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.util.HashMap<java.lang.Integer, androidx.constraintlayout.widget.c.b> f153a = new java.util.HashMap<>();

    private static class b {
        public int A;
        public int B;
        public int C;
        public int D;
        public int E;
        public int F;
        public int G;
        public int H;
        public int I;
        public int J;
        public int K;
        public int L;
        public int M;
        public int N;
        public int O;
        public int P;
        public float Q;
        public float R;
        public int S;
        public int T;
        public float U;
        public boolean V;
        public float W;
        public float X;
        public float Y;
        public float Z;

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        boolean f154a;
        public float a0;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f155b;
        public float b0;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f156c;
        public float c0;
        int d;
        public float d0;
        public int e;
        public float e0;
        public int f;
        public float f0;
        public float g;
        public float g0;
        public int h;
        public boolean h0;
        public int i;
        public boolean i0;
        public int j;
        public int j0;
        public int k;
        public int k0;
        public int l;
        public int l0;
        public int m;
        public int m0;
        public int n;
        public int n0;
        public int o;
        public int o0;
        public int p;
        public float p0;
        public int q;
        public float q0;
        public int r;
        public boolean r0;
        public int s;
        public int s0;
        public int t;
        public int t0;
        public float u;
        public int[] u0;
        public float v;
        public java.lang.String v0;
        public java.lang.String w;
        public int x;
        public int y;
        public float z;

        private b() {
            this.f154a = false;
            this.e = -1;
            this.f = -1;
            this.g = -1.0f;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = -1;
            this.o = -1;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = 0.5f;
            this.v = 0.5f;
            this.w = null;
            this.x = -1;
            this.y = 0;
            this.z = 0.0f;
            this.A = -1;
            this.B = -1;
            this.C = -1;
            this.D = -1;
            this.E = -1;
            this.F = -1;
            this.G = -1;
            this.H = -1;
            this.I = -1;
            this.J = 0;
            this.K = -1;
            this.L = -1;
            this.M = -1;
            this.N = -1;
            this.O = -1;
            this.P = -1;
            this.Q = 0.0f;
            this.R = 0.0f;
            this.S = 0;
            this.T = 0;
            this.U = 1.0f;
            this.V = false;
            this.W = 0.0f;
            this.X = 0.0f;
            this.Y = 0.0f;
            this.Z = 0.0f;
            this.a0 = 1.0f;
            this.b0 = 1.0f;
            this.c0 = Float.NaN;
            this.d0 = Float.NaN;
            this.e0 = 0.0f;
            this.f0 = 0.0f;
            this.g0 = 0.0f;
            this.h0 = false;
            this.i0 = false;
            this.j0 = 0;
            this.k0 = 0;
            this.l0 = -1;
            this.m0 = -1;
            this.n0 = -1;
            this.o0 = -1;
            this.p0 = 1.0f;
            this.q0 = 1.0f;
            this.r0 = false;
            this.s0 = -1;
            this.t0 = -1;
        }

        private void a(int i, androidx.constraintlayout.widget.ConstraintLayout.a aVar) {
            this.d = i;
            this.h = aVar.d;
            this.i = aVar.e;
            this.j = aVar.f;
            this.k = aVar.g;
            this.l = aVar.h;
            this.m = aVar.i;
            this.n = aVar.j;
            this.o = aVar.k;
            this.p = aVar.l;
            this.q = aVar.p;
            this.r = aVar.q;
            this.s = aVar.r;
            this.t = aVar.s;
            this.u = aVar.z;
            this.v = aVar.A;
            this.w = aVar.B;
            this.x = aVar.m;
            this.y = aVar.n;
            this.z = aVar.o;
            this.A = aVar.P;
            this.B = aVar.Q;
            this.C = aVar.R;
            this.g = aVar.f146c;
            this.e = aVar.f144a;
            this.f = aVar.f145b;
            this.f155b = ((android.view.ViewGroup.MarginLayoutParams) aVar).width;
            this.f156c = ((android.view.ViewGroup.MarginLayoutParams) aVar).height;
            this.D = ((android.view.ViewGroup.MarginLayoutParams) aVar).leftMargin;
            this.E = ((android.view.ViewGroup.MarginLayoutParams) aVar).rightMargin;
            this.F = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
            this.G = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
            this.Q = aVar.E;
            this.R = aVar.D;
            this.T = aVar.G;
            this.S = aVar.F;
            boolean z = aVar.S;
            this.h0 = z;
            this.i0 = aVar.T;
            this.j0 = aVar.H;
            this.k0 = aVar.I;
            this.h0 = z;
            this.l0 = aVar.L;
            this.m0 = aVar.M;
            this.n0 = aVar.J;
            this.o0 = aVar.K;
            this.p0 = aVar.N;
            this.q0 = aVar.O;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                this.H = aVar.getMarginEnd();
                this.I = aVar.getMarginStart();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, androidx.constraintlayout.widget.d.a aVar) {
            a(i, (androidx.constraintlayout.widget.ConstraintLayout.a) aVar);
            this.U = aVar.m0;
            this.X = aVar.p0;
            this.Y = aVar.q0;
            this.Z = aVar.r0;
            this.a0 = aVar.s0;
            this.b0 = aVar.t0;
            this.c0 = aVar.u0;
            this.d0 = aVar.v0;
            this.e0 = aVar.w0;
            this.f0 = aVar.x0;
            this.g0 = aVar.y0;
            this.W = aVar.o0;
            this.V = aVar.n0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(androidx.constraintlayout.widget.b bVar, int i, androidx.constraintlayout.widget.d.a aVar) {
            a(i, aVar);
            if (bVar instanceof androidx.constraintlayout.widget.a) {
                this.t0 = 1;
                androidx.constraintlayout.widget.a aVar2 = (androidx.constraintlayout.widget.a) bVar;
                this.s0 = aVar2.getType();
                this.u0 = aVar2.getReferencedIds();
            }
        }

        public void a(androidx.constraintlayout.widget.ConstraintLayout.a aVar) {
            aVar.d = this.h;
            aVar.e = this.i;
            aVar.f = this.j;
            aVar.g = this.k;
            aVar.h = this.l;
            aVar.i = this.m;
            aVar.j = this.n;
            aVar.k = this.o;
            aVar.l = this.p;
            aVar.p = this.q;
            aVar.q = this.r;
            aVar.r = this.s;
            aVar.s = this.t;
            ((android.view.ViewGroup.MarginLayoutParams) aVar).leftMargin = this.D;
            ((android.view.ViewGroup.MarginLayoutParams) aVar).rightMargin = this.E;
            ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin = this.F;
            ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin = this.G;
            aVar.x = this.P;
            aVar.y = this.O;
            aVar.z = this.u;
            aVar.A = this.v;
            aVar.m = this.x;
            aVar.n = this.y;
            aVar.o = this.z;
            aVar.B = this.w;
            aVar.P = this.A;
            aVar.Q = this.B;
            aVar.E = this.Q;
            aVar.D = this.R;
            aVar.G = this.T;
            aVar.F = this.S;
            aVar.S = this.h0;
            aVar.T = this.i0;
            aVar.H = this.j0;
            aVar.I = this.k0;
            aVar.L = this.l0;
            aVar.M = this.m0;
            aVar.J = this.n0;
            aVar.K = this.o0;
            aVar.N = this.p0;
            aVar.O = this.q0;
            aVar.R = this.C;
            aVar.f146c = this.g;
            aVar.f144a = this.e;
            aVar.f145b = this.f;
            ((android.view.ViewGroup.MarginLayoutParams) aVar).width = this.f155b;
            ((android.view.ViewGroup.MarginLayoutParams) aVar).height = this.f156c;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                aVar.setMarginStart(this.I);
                aVar.setMarginEnd(this.H);
            }
            aVar.a();
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public androidx.constraintlayout.widget.c.b m1clone() {
            androidx.constraintlayout.widget.c.b bVar = new androidx.constraintlayout.widget.c.b();
            bVar.f154a = this.f154a;
            bVar.f155b = this.f155b;
            bVar.f156c = this.f156c;
            bVar.e = this.e;
            bVar.f = this.f;
            bVar.g = this.g;
            bVar.h = this.h;
            bVar.i = this.i;
            bVar.j = this.j;
            bVar.k = this.k;
            bVar.l = this.l;
            bVar.m = this.m;
            bVar.n = this.n;
            bVar.o = this.o;
            bVar.p = this.p;
            bVar.q = this.q;
            bVar.r = this.r;
            bVar.s = this.s;
            bVar.t = this.t;
            bVar.u = this.u;
            bVar.v = this.v;
            bVar.w = this.w;
            bVar.A = this.A;
            bVar.B = this.B;
            bVar.u = this.u;
            bVar.u = this.u;
            bVar.u = this.u;
            bVar.u = this.u;
            bVar.u = this.u;
            bVar.C = this.C;
            bVar.D = this.D;
            bVar.E = this.E;
            bVar.F = this.F;
            bVar.G = this.G;
            bVar.H = this.H;
            bVar.I = this.I;
            bVar.J = this.J;
            bVar.K = this.K;
            bVar.L = this.L;
            bVar.M = this.M;
            bVar.N = this.N;
            bVar.O = this.O;
            bVar.P = this.P;
            bVar.Q = this.Q;
            bVar.R = this.R;
            bVar.S = this.S;
            bVar.T = this.T;
            bVar.U = this.U;
            bVar.V = this.V;
            bVar.W = this.W;
            bVar.X = this.X;
            bVar.Y = this.Y;
            bVar.Z = this.Z;
            bVar.a0 = this.a0;
            bVar.b0 = this.b0;
            bVar.c0 = this.c0;
            bVar.d0 = this.d0;
            bVar.e0 = this.e0;
            bVar.f0 = this.f0;
            bVar.g0 = this.g0;
            bVar.h0 = this.h0;
            bVar.i0 = this.i0;
            bVar.j0 = this.j0;
            bVar.k0 = this.k0;
            bVar.l0 = this.l0;
            bVar.m0 = this.m0;
            bVar.n0 = this.n0;
            bVar.o0 = this.o0;
            bVar.p0 = this.p0;
            bVar.q0 = this.q0;
            bVar.s0 = this.s0;
            bVar.t0 = this.t0;
            int[] iArr = this.u0;
            if (iArr != null) {
                bVar.u0 = java.util.Arrays.copyOf(iArr, iArr.length);
            }
            bVar.x = this.x;
            bVar.y = this.y;
            bVar.z = this.z;
            bVar.r0 = this.r0;
            return bVar;
        }
    }

    static {
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        f152c = sparseIntArray;
        sparseIntArray.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintLeft_toLeftOf, 25);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintLeft_toRightOf, 26);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintRight_toLeftOf, 29);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintRight_toRightOf, 30);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintTop_toTopOf, 36);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintTop_toBottomOf, 35);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintBottom_toTopOf, 4);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintBottom_toBottomOf, 3);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintBaseline_toBaselineOf, 1);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_editor_absoluteX, 6);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_editor_absoluteY, 7);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintGuide_begin, 17);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintGuide_end, 18);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintGuide_percent, 19);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_orientation, 27);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintStart_toEndOf, 32);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintStart_toStartOf, 33);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintEnd_toStartOf, 10);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintEnd_toEndOf, 9);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_goneMarginLeft, 13);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_goneMarginTop, 16);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_goneMarginRight, 14);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_goneMarginBottom, 11);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_goneMarginStart, 15);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_goneMarginEnd, 12);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintVertical_weight, 40);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintHorizontal_weight, 39);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintHorizontal_chainStyle, 41);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintVertical_chainStyle, 42);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintHorizontal_bias, 20);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintVertical_bias, 37);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintDimensionRatio, 5);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintLeft_creator, 75);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintTop_creator, 75);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintRight_creator, 75);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintBottom_creator, 75);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintBaseline_creator, 75);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_marginLeft, 24);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_marginRight, 28);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_marginStart, 31);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_marginEnd, 8);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_marginTop, 34);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_marginBottom, 2);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_width, 23);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_layout_height, 21);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_visibility, 22);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_alpha, 43);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_elevation, 44);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_rotationX, 45);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_rotationY, 46);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_rotation, 60);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_scaleX, 47);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_scaleY, 48);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_transformPivotX, 49);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_transformPivotY, 50);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_translationX, 51);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_translationY, 52);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_translationZ, 53);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintWidth_default, 54);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintHeight_default, 55);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintWidth_max, 56);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintHeight_max, 57);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintWidth_min, 58);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintHeight_min, 59);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintCircle, 61);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintCircleRadius, 62);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintCircleAngle, 63);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_android_id, 38);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintWidth_percent, 69);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_layout_constraintHeight_percent, 70);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_chainUseRtl, 71);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_barrierDirection, 72);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_constraint_referenced_ids, 73);
        f152c.append(androidx.constraintlayout.widget.h.ConstraintSet_barrierAllowsGoneWidgets, 74);
    }

    private static int a(android.content.res.TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    private androidx.constraintlayout.widget.c.b a(android.content.Context context, android.util.AttributeSet attributeSet) {
        androidx.constraintlayout.widget.c.b bVar = new androidx.constraintlayout.widget.c.b();
        android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.constraintlayout.widget.h.ConstraintSet);
        a(bVar, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        return bVar;
    }

    private void a(androidx.constraintlayout.widget.c.b bVar, android.content.res.TypedArray typedArray) {
        java.lang.StringBuilder sb;
        java.lang.String str;
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            int i2 = f152c.get(index);
            switch (i2) {
                case 1:
                    bVar.p = a(typedArray, index, bVar.p);
                    break;
                case 2:
                    bVar.G = typedArray.getDimensionPixelSize(index, bVar.G);
                    break;
                case 3:
                    bVar.o = a(typedArray, index, bVar.o);
                    break;
                case 4:
                    bVar.n = a(typedArray, index, bVar.n);
                    break;
                case 5:
                    bVar.w = typedArray.getString(index);
                    break;
                case 6:
                    bVar.A = typedArray.getDimensionPixelOffset(index, bVar.A);
                    break;
                case 7:
                    bVar.B = typedArray.getDimensionPixelOffset(index, bVar.B);
                    break;
                case 8:
                    bVar.H = typedArray.getDimensionPixelSize(index, bVar.H);
                    break;
                case 9:
                    bVar.t = a(typedArray, index, bVar.t);
                    break;
                case 10:
                    bVar.s = a(typedArray, index, bVar.s);
                    break;
                case 11:
                    bVar.N = typedArray.getDimensionPixelSize(index, bVar.N);
                    break;
                case 12:
                    bVar.O = typedArray.getDimensionPixelSize(index, bVar.O);
                    break;
                case 13:
                    bVar.K = typedArray.getDimensionPixelSize(index, bVar.K);
                    break;
                case 14:
                    bVar.M = typedArray.getDimensionPixelSize(index, bVar.M);
                    break;
                case 15:
                    bVar.P = typedArray.getDimensionPixelSize(index, bVar.P);
                    break;
                case 16:
                    bVar.L = typedArray.getDimensionPixelSize(index, bVar.L);
                    break;
                case 17:
                    bVar.e = typedArray.getDimensionPixelOffset(index, bVar.e);
                    break;
                case 18:
                    bVar.f = typedArray.getDimensionPixelOffset(index, bVar.f);
                    break;
                case 19:
                    bVar.g = typedArray.getFloat(index, bVar.g);
                    break;
                case 20:
                    bVar.u = typedArray.getFloat(index, bVar.u);
                    break;
                case 21:
                    bVar.f156c = typedArray.getLayoutDimension(index, bVar.f156c);
                    break;
                case 22:
                    int i3 = typedArray.getInt(index, bVar.J);
                    bVar.J = i3;
                    bVar.J = f151b[i3];
                    break;
                case 23:
                    bVar.f155b = typedArray.getLayoutDimension(index, bVar.f155b);
                    break;
                case 24:
                    bVar.D = typedArray.getDimensionPixelSize(index, bVar.D);
                    break;
                case 25:
                    bVar.h = a(typedArray, index, bVar.h);
                    break;
                case 26:
                    bVar.i = a(typedArray, index, bVar.i);
                    break;
                case 27:
                    bVar.C = typedArray.getInt(index, bVar.C);
                    break;
                case 28:
                    bVar.E = typedArray.getDimensionPixelSize(index, bVar.E);
                    break;
                case 29:
                    bVar.j = a(typedArray, index, bVar.j);
                    break;
                case 30:
                    bVar.k = a(typedArray, index, bVar.k);
                    break;
                case 31:
                    bVar.I = typedArray.getDimensionPixelSize(index, bVar.I);
                    break;
                case 32:
                    bVar.q = a(typedArray, index, bVar.q);
                    break;
                case 33:
                    bVar.r = a(typedArray, index, bVar.r);
                    break;
                case 34:
                    bVar.F = typedArray.getDimensionPixelSize(index, bVar.F);
                    break;
                case 35:
                    bVar.m = a(typedArray, index, bVar.m);
                    break;
                case 36:
                    bVar.l = a(typedArray, index, bVar.l);
                    break;
                case 37:
                    bVar.v = typedArray.getFloat(index, bVar.v);
                    break;
                case 38:
                    bVar.d = typedArray.getResourceId(index, bVar.d);
                    break;
                case 39:
                    bVar.R = typedArray.getFloat(index, bVar.R);
                    break;
                case 40:
                    bVar.Q = typedArray.getFloat(index, bVar.Q);
                    break;
                case 41:
                    bVar.S = typedArray.getInt(index, bVar.S);
                    break;
                case 42:
                    bVar.T = typedArray.getInt(index, bVar.T);
                    break;
                case 43:
                    bVar.U = typedArray.getFloat(index, bVar.U);
                    break;
                case 44:
                    bVar.V = true;
                    bVar.W = typedArray.getDimension(index, bVar.W);
                    break;
                case 45:
                    bVar.Y = typedArray.getFloat(index, bVar.Y);
                    break;
                case 46:
                    bVar.Z = typedArray.getFloat(index, bVar.Z);
                    break;
                case 47:
                    bVar.a0 = typedArray.getFloat(index, bVar.a0);
                    break;
                case 48:
                    bVar.b0 = typedArray.getFloat(index, bVar.b0);
                    break;
                case 49:
                    bVar.c0 = typedArray.getFloat(index, bVar.c0);
                    break;
                case 50:
                    bVar.d0 = typedArray.getFloat(index, bVar.d0);
                    break;
                case 51:
                    bVar.e0 = typedArray.getDimension(index, bVar.e0);
                    break;
                case 52:
                    bVar.f0 = typedArray.getDimension(index, bVar.f0);
                    break;
                case 53:
                    bVar.g0 = typedArray.getDimension(index, bVar.g0);
                    break;
                default:
                    switch (i2) {
                        case me.hisn.mygesture.g.ConstraintSet_layout_constraintStart_toEndOf /* 60 */:
                            bVar.X = typedArray.getFloat(index, bVar.X);
                            break;
                        case me.hisn.mygesture.g.ConstraintSet_layout_constraintStart_toStartOf /* 61 */:
                            bVar.x = a(typedArray, index, bVar.x);
                            break;
                        case me.hisn.mygesture.g.ConstraintSet_layout_constraintTop_creator /* 62 */:
                            bVar.y = typedArray.getDimensionPixelSize(index, bVar.y);
                            break;
                        case me.hisn.mygesture.g.ConstraintSet_layout_constraintTop_toBottomOf /* 63 */:
                            bVar.z = typedArray.getFloat(index, bVar.z);
                            break;
                        default:
                            switch (i2) {
                                case me.hisn.mygesture.g.ConstraintSet_layout_constraintWidth_max /* 69 */:
                                    bVar.p0 = typedArray.getFloat(index, 1.0f);
                                    continue;
                                case me.hisn.mygesture.g.ConstraintSet_layout_constraintWidth_min /* 70 */:
                                    bVar.q0 = typedArray.getFloat(index, 1.0f);
                                    continue;
                                case me.hisn.mygesture.g.ConstraintSet_layout_constraintWidth_percent /* 71 */:
                                    android.util.Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                                    continue;
                                case me.hisn.mygesture.g.ConstraintSet_layout_editor_absoluteX /* 72 */:
                                    bVar.s0 = typedArray.getInt(index, bVar.s0);
                                    continue;
                                case me.hisn.mygesture.g.ConstraintSet_layout_editor_absoluteY /* 73 */:
                                    bVar.v0 = typedArray.getString(index);
                                    continue;
                                case me.hisn.mygesture.g.ConstraintSet_layout_goneMarginBottom /* 74 */:
                                    bVar.r0 = typedArray.getBoolean(index, bVar.r0);
                                    continue;
                                case me.hisn.mygesture.g.ConstraintSet_layout_goneMarginEnd /* 75 */:
                                    sb = new java.lang.StringBuilder();
                                    str = "unused attribute 0x";
                                    break;
                                default:
                                    sb = new java.lang.StringBuilder();
                                    str = "Unknown attribute 0x";
                                    break;
                            }
                            sb.append(str);
                            sb.append(java.lang.Integer.toHexString(index));
                            sb.append("   ");
                            sb.append(f152c.get(index));
                            android.util.Log.w("ConstraintSet", sb.toString());
                            break;
                    }
                    break;
            }
        }
    }

    private int[] a(android.view.View view, java.lang.String str) {
        int iIntValue;
        java.lang.Object objA;
        java.lang.String[] strArrSplit = str.split(",");
        android.content.Context context = view.getContext();
        int[] iArr = new int[strArrSplit.length];
        int i = 0;
        int i2 = 0;
        while (i < strArrSplit.length) {
            java.lang.String strTrim = strArrSplit[i].trim();
            try {
                iIntValue = androidx.constraintlayout.widget.g.class.getField(strTrim).getInt(null);
            } catch (java.lang.Exception unused) {
                iIntValue = 0;
            }
            if (iIntValue == 0) {
                iIntValue = context.getResources().getIdentifier(strTrim, "id", context.getPackageName());
            }
            if (iIntValue == 0 && view.isInEditMode() && (view.getParent() instanceof androidx.constraintlayout.widget.ConstraintLayout) && (objA = ((androidx.constraintlayout.widget.ConstraintLayout) view.getParent()).a(0, strTrim)) != null && (objA instanceof java.lang.Integer)) {
                iIntValue = ((java.lang.Integer) objA).intValue();
            }
            iArr[i2] = iIntValue;
            i++;
            i2++;
        }
        return i2 != strArrSplit.length ? java.util.Arrays.copyOf(iArr, i2) : iArr;
    }

    public void a(android.content.Context context, int i) {
        android.content.res.XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    java.lang.String name = xml.getName();
                    androidx.constraintlayout.widget.c.b bVarA = a(context, android.util.Xml.asAttributeSet(xml));
                    if (name.equalsIgnoreCase("Guideline")) {
                        bVarA.f154a = true;
                    }
                    this.f153a.put(java.lang.Integer.valueOf(bVarA.d), bVarA);
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    void a(androidx.constraintlayout.widget.ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        java.util.HashSet<java.lang.Integer> hashSet = new java.util.HashSet(this.f153a.keySet());
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (id == -1) {
                throw new java.lang.RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (this.f153a.containsKey(java.lang.Integer.valueOf(id))) {
                hashSet.remove(java.lang.Integer.valueOf(id));
                androidx.constraintlayout.widget.c.b bVar = this.f153a.get(java.lang.Integer.valueOf(id));
                if (childAt instanceof androidx.constraintlayout.widget.a) {
                    bVar.t0 = 1;
                }
                int i2 = bVar.t0;
                if (i2 != -1 && i2 == 1) {
                    androidx.constraintlayout.widget.a aVar = (androidx.constraintlayout.widget.a) childAt;
                    aVar.setId(id);
                    aVar.setType(bVar.s0);
                    aVar.setAllowsGoneWidget(bVar.r0);
                    int[] iArr = bVar.u0;
                    if (iArr != null) {
                        aVar.setReferencedIds(iArr);
                    } else {
                        java.lang.String str = bVar.v0;
                        if (str != null) {
                            int[] iArrA = a(aVar, str);
                            bVar.u0 = iArrA;
                            aVar.setReferencedIds(iArrA);
                        }
                    }
                }
                androidx.constraintlayout.widget.ConstraintLayout.a aVar2 = (androidx.constraintlayout.widget.ConstraintLayout.a) childAt.getLayoutParams();
                bVar.a(aVar2);
                childAt.setLayoutParams(aVar2);
                childAt.setVisibility(bVar.J);
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    childAt.setAlpha(bVar.U);
                    childAt.setRotation(bVar.X);
                    childAt.setRotationX(bVar.Y);
                    childAt.setRotationY(bVar.Z);
                    childAt.setScaleX(bVar.a0);
                    childAt.setScaleY(bVar.b0);
                    if (!java.lang.Float.isNaN(bVar.c0)) {
                        childAt.setPivotX(bVar.c0);
                    }
                    if (!java.lang.Float.isNaN(bVar.d0)) {
                        childAt.setPivotY(bVar.d0);
                    }
                    childAt.setTranslationX(bVar.e0);
                    childAt.setTranslationY(bVar.f0);
                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        childAt.setTranslationZ(bVar.g0);
                        if (bVar.V) {
                            childAt.setElevation(bVar.W);
                        }
                    }
                }
            }
        }
        for (java.lang.Integer num : hashSet) {
            androidx.constraintlayout.widget.c.b bVar2 = this.f153a.get(num);
            int i3 = bVar2.t0;
            if (i3 != -1 && i3 == 1) {
                androidx.constraintlayout.widget.a aVar3 = new androidx.constraintlayout.widget.a(constraintLayout.getContext());
                aVar3.setId(num.intValue());
                int[] iArr2 = bVar2.u0;
                if (iArr2 != null) {
                    aVar3.setReferencedIds(iArr2);
                } else {
                    java.lang.String str2 = bVar2.v0;
                    if (str2 != null) {
                        int[] iArrA2 = a(aVar3, str2);
                        bVar2.u0 = iArrA2;
                        aVar3.setReferencedIds(iArrA2);
                    }
                }
                aVar3.setType(bVar2.s0);
                androidx.constraintlayout.widget.ConstraintLayout.a aVarGenerateDefaultLayoutParams = constraintLayout.generateDefaultLayoutParams();
                aVar3.a();
                bVar2.a(aVarGenerateDefaultLayoutParams);
                constraintLayout.addView(aVar3, aVarGenerateDefaultLayoutParams);
            }
            if (bVar2.f154a) {
                android.view.View eVar = new androidx.constraintlayout.widget.e(constraintLayout.getContext());
                eVar.setId(num.intValue());
                androidx.constraintlayout.widget.ConstraintLayout.a aVarGenerateDefaultLayoutParams2 = constraintLayout.generateDefaultLayoutParams();
                bVar2.a(aVarGenerateDefaultLayoutParams2);
                constraintLayout.addView(eVar, aVarGenerateDefaultLayoutParams2);
            }
        }
    }

    public void a(androidx.constraintlayout.widget.d dVar) {
        int childCount = dVar.getChildCount();
        this.f153a.clear();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = dVar.getChildAt(i);
            androidx.constraintlayout.widget.d.a aVar = (androidx.constraintlayout.widget.d.a) childAt.getLayoutParams();
            int id = childAt.getId();
            if (id == -1) {
                throw new java.lang.RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.f153a.containsKey(java.lang.Integer.valueOf(id))) {
                this.f153a.put(java.lang.Integer.valueOf(id), new androidx.constraintlayout.widget.c.b());
            }
            androidx.constraintlayout.widget.c.b bVar = this.f153a.get(java.lang.Integer.valueOf(id));
            if (childAt instanceof androidx.constraintlayout.widget.b) {
                bVar.a((androidx.constraintlayout.widget.b) childAt, id, aVar);
            }
            bVar.a(id, aVar);
        }
    }
}
