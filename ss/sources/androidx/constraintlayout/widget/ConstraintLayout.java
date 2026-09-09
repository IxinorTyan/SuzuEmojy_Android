package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintLayout extends android.view.ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    android.util.SparseArray<android.view.View> f141a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.util.ArrayList<androidx.constraintlayout.widget.b> f142b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final java.util.ArrayList<a.b.a.j.f> f143c;
    a.b.a.j.g d;
    private int e;
    private int f;
    private int g;
    private int h;
    private boolean i;
    private int j;
    private androidx.constraintlayout.widget.c k;
    private int l;
    private java.util.HashMap<java.lang.String, java.lang.Integer> m;
    private int n;
    private int o;
    private a.b.a.f p;

    public static class a extends android.view.ViewGroup.MarginLayoutParams {
        public float A;
        public java.lang.String B;
        int C;
        public float D;
        public float E;
        public int F;
        public int G;
        public int H;
        public int I;
        public int J;
        public int K;
        public int L;
        public int M;
        public float N;
        public float O;
        public int P;
        public int Q;
        public int R;
        public boolean S;
        public boolean T;
        boolean U;
        boolean V;
        boolean W;
        boolean X;
        boolean Y;
        boolean Z;

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f144a;
        int a0;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f145b;
        int b0;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public float f146c;
        int c0;
        public int d;
        int d0;
        public int e;
        int e0;
        public int f;
        int f0;
        public int g;
        float g0;
        public int h;
        int h0;
        public int i;
        int i0;
        public int j;
        float j0;
        public int k;
        a.b.a.j.f k0;
        public int l;
        public boolean l0;
        public int m;
        public int n;
        public float o;
        public int p;
        public int q;
        public int r;
        public int s;
        public int t;
        public int u;
        public int v;
        public int w;
        public int x;
        public int y;
        public float z;

        /* JADX INFO: renamed from: androidx.constraintlayout.widget.ConstraintLayout$a$a, reason: collision with other inner class name */
        private static class C0009a {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            public static final android.util.SparseIntArray f147a;

            static {
                android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                f147a = sparseIntArray;
                sparseIntArray.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintCircle, 2);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_android_orientation, 1);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                f147a.append(androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
            }
        }

        public a(int i, int i2) {
            super(i, i2);
            this.f144a = -1;
            this.f145b = -1;
            this.f146c = -1.0f;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = 0;
            this.o = 0.0f;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = -1;
            this.v = -1;
            this.w = -1;
            this.x = -1;
            this.y = -1;
            this.z = 0.5f;
            this.A = 0.5f;
            this.B = null;
            this.C = 1;
            this.D = -1.0f;
            this.E = -1.0f;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 1.0f;
            this.O = 1.0f;
            this.P = -1;
            this.Q = -1;
            this.R = -1;
            this.S = false;
            this.T = false;
            this.U = true;
            this.V = true;
            this.W = false;
            this.X = false;
            this.Y = false;
            this.Z = false;
            this.a0 = -1;
            this.b0 = -1;
            this.c0 = -1;
            this.d0 = -1;
            this.e0 = -1;
            this.f0 = -1;
            this.g0 = 0.5f;
            this.k0 = new a.b.a.j.f();
            this.l0 = false;
        }

        public a(android.content.Context context, android.util.AttributeSet attributeSet) {
            java.lang.String str;
            int i;
            super(context, attributeSet);
            this.f144a = -1;
            this.f145b = -1;
            this.f146c = -1.0f;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = 0;
            this.o = 0.0f;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = -1;
            this.v = -1;
            this.w = -1;
            this.x = -1;
            this.y = -1;
            this.z = 0.5f;
            this.A = 0.5f;
            this.B = null;
            this.C = 1;
            this.D = -1.0f;
            this.E = -1.0f;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 1.0f;
            this.O = 1.0f;
            this.P = -1;
            this.Q = -1;
            this.R = -1;
            this.S = false;
            this.T = false;
            this.U = true;
            this.V = true;
            this.W = false;
            this.X = false;
            this.Y = false;
            this.Z = false;
            this.a0 = -1;
            this.b0 = -1;
            this.c0 = -1;
            this.d0 = -1;
            this.e0 = -1;
            this.f0 = -1;
            this.g0 = 0.5f;
            this.k0 = new a.b.a.j.f();
            this.l0 = false;
            android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.constraintlayout.widget.h.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                int i3 = androidx.constraintlayout.widget.ConstraintLayout.a.C0009a.f147a.get(index);
                switch (i3) {
                    case 1:
                        this.R = typedArrayObtainStyledAttributes.getInt(index, this.R);
                        continue;
                        break;
                    case 2:
                        int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.m);
                        this.m = resourceId;
                        if (resourceId == -1) {
                            this.m = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 3:
                        this.n = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.n);
                        continue;
                        break;
                    case 4:
                        float f = typedArrayObtainStyledAttributes.getFloat(index, this.o) % 360.0f;
                        this.o = f;
                        if (f < 0.0f) {
                            this.o = (360.0f - f) % 360.0f;
                        } else {
                            continue;
                        }
                        break;
                    case 5:
                        this.f144a = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f144a);
                        continue;
                        break;
                    case 6:
                        this.f145b = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f145b);
                        continue;
                        break;
                    case 7:
                        this.f146c = typedArrayObtainStyledAttributes.getFloat(index, this.f146c);
                        continue;
                        break;
                    case 8:
                        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, this.d);
                        this.d = resourceId2;
                        if (resourceId2 == -1) {
                            this.d = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 9:
                        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(index, this.e);
                        this.e = resourceId3;
                        if (resourceId3 == -1) {
                            this.e = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 10:
                        int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(index, this.f);
                        this.f = resourceId4;
                        if (resourceId4 == -1) {
                            this.f = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 11:
                        int resourceId5 = typedArrayObtainStyledAttributes.getResourceId(index, this.g);
                        this.g = resourceId5;
                        if (resourceId5 == -1) {
                            this.g = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 12:
                        int resourceId6 = typedArrayObtainStyledAttributes.getResourceId(index, this.h);
                        this.h = resourceId6;
                        if (resourceId6 == -1) {
                            this.h = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 13:
                        int resourceId7 = typedArrayObtainStyledAttributes.getResourceId(index, this.i);
                        this.i = resourceId7;
                        if (resourceId7 == -1) {
                            this.i = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 14:
                        int resourceId8 = typedArrayObtainStyledAttributes.getResourceId(index, this.j);
                        this.j = resourceId8;
                        if (resourceId8 == -1) {
                            this.j = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 15:
                        int resourceId9 = typedArrayObtainStyledAttributes.getResourceId(index, this.k);
                        this.k = resourceId9;
                        if (resourceId9 == -1) {
                            this.k = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 16:
                        int resourceId10 = typedArrayObtainStyledAttributes.getResourceId(index, this.l);
                        this.l = resourceId10;
                        if (resourceId10 == -1) {
                            this.l = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 17:
                        int resourceId11 = typedArrayObtainStyledAttributes.getResourceId(index, this.p);
                        this.p = resourceId11;
                        if (resourceId11 == -1) {
                            this.p = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 18:
                        int resourceId12 = typedArrayObtainStyledAttributes.getResourceId(index, this.q);
                        this.q = resourceId12;
                        if (resourceId12 == -1) {
                            this.q = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 19:
                        int resourceId13 = typedArrayObtainStyledAttributes.getResourceId(index, this.r);
                        this.r = resourceId13;
                        if (resourceId13 == -1) {
                            this.r = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 20:
                        int resourceId14 = typedArrayObtainStyledAttributes.getResourceId(index, this.s);
                        this.s = resourceId14;
                        if (resourceId14 == -1) {
                            this.s = typedArrayObtainStyledAttributes.getInt(index, -1);
                        } else {
                            continue;
                        }
                        break;
                    case 21:
                        this.t = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.t);
                        continue;
                        break;
                    case 22:
                        this.u = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.u);
                        continue;
                        break;
                    case 23:
                        this.v = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.v);
                        continue;
                        break;
                    case 24:
                        this.w = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.w);
                        continue;
                        break;
                    case 25:
                        this.x = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.x);
                        continue;
                        break;
                    case 26:
                        this.y = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.y);
                        continue;
                        break;
                    case 27:
                        this.S = typedArrayObtainStyledAttributes.getBoolean(index, this.S);
                        continue;
                        break;
                    case 28:
                        this.T = typedArrayObtainStyledAttributes.getBoolean(index, this.T);
                        continue;
                        break;
                    case 29:
                        this.z = typedArrayObtainStyledAttributes.getFloat(index, this.z);
                        continue;
                        break;
                    case 30:
                        this.A = typedArrayObtainStyledAttributes.getFloat(index, this.A);
                        continue;
                        break;
                    case 31:
                        int i4 = typedArrayObtainStyledAttributes.getInt(index, 0);
                        this.H = i4;
                        if (i4 == 1) {
                            str = "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.";
                            break;
                        }
                        break;
                    case 32:
                        int i5 = typedArrayObtainStyledAttributes.getInt(index, 0);
                        this.I = i5;
                        if (i5 == 1) {
                            str = "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.";
                            break;
                        }
                        break;
                    case 33:
                        try {
                            this.J = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.J);
                            continue;
                        } catch (java.lang.Exception unused) {
                            if (typedArrayObtainStyledAttributes.getInt(index, this.J) == -2) {
                                this.J = -2;
                            }
                        }
                        break;
                    case 34:
                        try {
                            this.L = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.L);
                            continue;
                        } catch (java.lang.Exception unused2) {
                            if (typedArrayObtainStyledAttributes.getInt(index, this.L) == -2) {
                                this.L = -2;
                            }
                        }
                        break;
                    case 35:
                        this.N = java.lang.Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, this.N));
                        continue;
                        break;
                    case 36:
                        try {
                            this.K = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.K);
                            continue;
                        } catch (java.lang.Exception unused3) {
                            if (typedArrayObtainStyledAttributes.getInt(index, this.K) == -2) {
                                this.K = -2;
                            }
                        }
                        break;
                    case 37:
                        try {
                            this.M = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.M);
                            continue;
                        } catch (java.lang.Exception unused4) {
                            if (typedArrayObtainStyledAttributes.getInt(index, this.M) == -2) {
                                this.M = -2;
                            }
                        }
                        break;
                    case 38:
                        this.O = java.lang.Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, this.O));
                        continue;
                        break;
                    default:
                        switch (i3) {
                            case 44:
                                java.lang.String string = typedArrayObtainStyledAttributes.getString(index);
                                this.B = string;
                                this.C = -1;
                                if (string != null) {
                                    int length = string.length();
                                    int iIndexOf = this.B.indexOf(44);
                                    if (iIndexOf <= 0 || iIndexOf >= length - 1) {
                                        i = 0;
                                    } else {
                                        java.lang.String strSubstring = this.B.substring(0, iIndexOf);
                                        if (strSubstring.equalsIgnoreCase("W")) {
                                            this.C = 0;
                                        } else if (strSubstring.equalsIgnoreCase("H")) {
                                            this.C = 1;
                                        }
                                        i = iIndexOf + 1;
                                    }
                                    int iIndexOf2 = this.B.indexOf(58);
                                    if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                                        java.lang.String strSubstring2 = this.B.substring(i);
                                        if (strSubstring2.length() > 0) {
                                            java.lang.Float.parseFloat(strSubstring2);
                                        }
                                    } else {
                                        java.lang.String strSubstring3 = this.B.substring(i, iIndexOf2);
                                        java.lang.String strSubstring4 = this.B.substring(iIndexOf2 + 1);
                                        if (strSubstring3.length() > 0 && strSubstring4.length() > 0) {
                                            try {
                                                float f2 = java.lang.Float.parseFloat(strSubstring3);
                                                float f3 = java.lang.Float.parseFloat(strSubstring4);
                                                if (f2 > 0.0f && f3 > 0.0f) {
                                                    if (this.C == 1) {
                                                        java.lang.Math.abs(f3 / f2);
                                                    } else {
                                                        java.lang.Math.abs(f2 / f3);
                                                    }
                                                }
                                            } catch (java.lang.NumberFormatException unused5) {
                                            }
                                        }
                                    }
                                }
                                break;
                            case 45:
                                this.D = typedArrayObtainStyledAttributes.getFloat(index, this.D);
                                break;
                            case 46:
                                this.E = typedArrayObtainStyledAttributes.getFloat(index, this.E);
                                break;
                            case 47:
                                this.F = typedArrayObtainStyledAttributes.getInt(index, 0);
                                break;
                            case 48:
                                this.G = typedArrayObtainStyledAttributes.getInt(index, 0);
                                break;
                            case 49:
                                this.P = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.P);
                                break;
                            case 50:
                                this.Q = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.Q);
                                continue;
                        }
                        break;
                }
                android.util.Log.e("ConstraintLayout", str);
            }
            typedArrayObtainStyledAttributes.recycle();
            a();
        }

        public a(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f144a = -1;
            this.f145b = -1;
            this.f146c = -1.0f;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = 0;
            this.o = 0.0f;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = -1;
            this.v = -1;
            this.w = -1;
            this.x = -1;
            this.y = -1;
            this.z = 0.5f;
            this.A = 0.5f;
            this.B = null;
            this.C = 1;
            this.D = -1.0f;
            this.E = -1.0f;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 1.0f;
            this.O = 1.0f;
            this.P = -1;
            this.Q = -1;
            this.R = -1;
            this.S = false;
            this.T = false;
            this.U = true;
            this.V = true;
            this.W = false;
            this.X = false;
            this.Y = false;
            this.Z = false;
            this.a0 = -1;
            this.b0 = -1;
            this.c0 = -1;
            this.d0 = -1;
            this.e0 = -1;
            this.f0 = -1;
            this.g0 = 0.5f;
            this.k0 = new a.b.a.j.f();
            this.l0 = false;
        }

        public void a() {
            this.X = false;
            this.U = true;
            this.V = true;
            if (((android.view.ViewGroup.MarginLayoutParams) this).width == -2 && this.S) {
                this.U = false;
                this.H = 1;
            }
            if (((android.view.ViewGroup.MarginLayoutParams) this).height == -2 && this.T) {
                this.V = false;
                this.I = 1;
            }
            int i = ((android.view.ViewGroup.MarginLayoutParams) this).width;
            if (i == 0 || i == -1) {
                this.U = false;
                if (((android.view.ViewGroup.MarginLayoutParams) this).width == 0 && this.H == 1) {
                    ((android.view.ViewGroup.MarginLayoutParams) this).width = -2;
                    this.S = true;
                }
            }
            int i2 = ((android.view.ViewGroup.MarginLayoutParams) this).height;
            if (i2 == 0 || i2 == -1) {
                this.V = false;
                if (((android.view.ViewGroup.MarginLayoutParams) this).height == 0 && this.I == 1) {
                    ((android.view.ViewGroup.MarginLayoutParams) this).height = -2;
                    this.T = true;
                }
            }
            if (this.f146c == -1.0f && this.f144a == -1 && this.f145b == -1) {
                return;
            }
            this.X = true;
            this.U = true;
            this.V = true;
            if (!(this.k0 instanceof a.b.a.j.i)) {
                this.k0 = new a.b.a.j.i();
            }
            ((a.b.a.j.i) this.k0).v(this.R);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x004c  */
        /* JADX WARN: Code duplicated, block: B:19:0x0053  */
        /* JADX WARN: Code duplicated, block: B:22:0x005a  */
        /* JADX WARN: Code duplicated, block: B:25:0x0060  */
        /* JADX WARN: Code duplicated, block: B:28:0x0066  */
        /* JADX WARN: Code duplicated, block: B:35:0x007c  */
        /* JADX WARN: Code duplicated, block: B:36:0x0084  */
        /* JADX WARN: Code duplicated, block: B:38:0x0088  */
        /* JADX WARN: Code duplicated, block: B:40:0x008f  */
        /* JADX WARN: Code duplicated, block: B:42:0x0093  */
        /* JADX WARN: Code duplicated, block: B:74:0x00d8  */
        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        @android.annotation.TargetApi(17)
        public void resolveLayoutDirection(int i) {
            int i2;
            int i3;
            int i4;
            int i5;
            float f;
            int i6;
            int i7;
            int i8 = ((android.view.ViewGroup.MarginLayoutParams) this).leftMargin;
            int i9 = ((android.view.ViewGroup.MarginLayoutParams) this).rightMargin;
            super.resolveLayoutDirection(i);
            this.c0 = -1;
            this.d0 = -1;
            this.a0 = -1;
            this.b0 = -1;
            this.e0 = -1;
            this.f0 = -1;
            this.e0 = this.t;
            this.f0 = this.v;
            this.g0 = this.z;
            this.h0 = this.f144a;
            this.i0 = this.f145b;
            this.j0 = this.f146c;
            boolean z = false;
            if (1 == getLayoutDirection()) {
                int i10 = this.p;
                if (i10 != -1) {
                    this.c0 = i10;
                } else {
                    int i11 = this.q;
                    if (i11 != -1) {
                        this.d0 = i11;
                    } else {
                        i2 = this.r;
                        if (i2 != -1) {
                            this.b0 = i2;
                            z = true;
                        }
                        i3 = this.s;
                        if (i3 != -1) {
                            this.a0 = i3;
                            z = true;
                        }
                        i4 = this.x;
                        if (i4 != -1) {
                            this.f0 = i4;
                        }
                        i5 = this.y;
                        if (i5 != -1) {
                            this.e0 = i5;
                        }
                        if (z) {
                            this.g0 = 1.0f - this.z;
                        }
                        if (this.X && this.R == 1) {
                            f = this.f146c;
                            if (f != -1.0f) {
                                this.j0 = 1.0f - f;
                                this.h0 = -1;
                                this.i0 = -1;
                            } else {
                                i6 = this.f144a;
                                if (i6 != -1) {
                                    this.i0 = i6;
                                    this.h0 = -1;
                                } else {
                                    i7 = this.f145b;
                                    if (i7 != -1) {
                                        this.h0 = i7;
                                        this.i0 = -1;
                                    }
                                }
                                this.j0 = -1.0f;
                            }
                        }
                    }
                }
                z = true;
                i2 = this.r;
                if (i2 != -1) {
                    this.b0 = i2;
                    z = true;
                }
                i3 = this.s;
                if (i3 != -1) {
                    this.a0 = i3;
                    z = true;
                }
                i4 = this.x;
                if (i4 != -1) {
                    this.f0 = i4;
                }
                i5 = this.y;
                if (i5 != -1) {
                    this.e0 = i5;
                }
                if (z) {
                    this.g0 = 1.0f - this.z;
                }
                if (this.X) {
                    f = this.f146c;
                    if (f != -1.0f) {
                        this.j0 = 1.0f - f;
                        this.h0 = -1;
                        this.i0 = -1;
                    } else {
                        i6 = this.f144a;
                        if (i6 != -1) {
                            this.i0 = i6;
                            this.h0 = -1;
                        } else {
                            i7 = this.f145b;
                            if (i7 != -1) {
                                this.h0 = i7;
                                this.i0 = -1;
                            }
                        }
                        this.j0 = -1.0f;
                    }
                }
            } else {
                int i12 = this.p;
                if (i12 != -1) {
                    this.b0 = i12;
                }
                int i13 = this.q;
                if (i13 != -1) {
                    this.a0 = i13;
                }
                int i14 = this.r;
                if (i14 != -1) {
                    this.c0 = i14;
                }
                int i15 = this.s;
                if (i15 != -1) {
                    this.d0 = i15;
                }
                int i16 = this.x;
                if (i16 != -1) {
                    this.e0 = i16;
                }
                int i17 = this.y;
                if (i17 != -1) {
                    this.f0 = i17;
                }
            }
            if (this.r == -1 && this.s == -1 && this.q == -1 && this.p == -1) {
                int i18 = this.f;
                if (i18 != -1) {
                    this.c0 = i18;
                    if (((android.view.ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i9 > 0) {
                        ((android.view.ViewGroup.MarginLayoutParams) this).rightMargin = i9;
                    }
                } else {
                    int i19 = this.g;
                    if (i19 != -1) {
                        this.d0 = i19;
                        if (((android.view.ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i9 > 0) {
                            ((android.view.ViewGroup.MarginLayoutParams) this).rightMargin = i9;
                        }
                    }
                }
                int i20 = this.d;
                if (i20 != -1) {
                    this.a0 = i20;
                    if (((android.view.ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i8 <= 0) {
                        return;
                    }
                } else {
                    int i21 = this.e;
                    if (i21 == -1) {
                        return;
                    }
                    this.b0 = i21;
                    if (((android.view.ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i8 <= 0) {
                        return;
                    }
                }
                ((android.view.ViewGroup.MarginLayoutParams) this).leftMargin = i8;
            }
        }
    }

    public ConstraintLayout(android.content.Context context) {
        super(context);
        this.f141a = new android.util.SparseArray<>();
        this.f142b = new java.util.ArrayList<>(4);
        this.f143c = new java.util.ArrayList<>(100);
        this.d = new a.b.a.j.g();
        this.e = 0;
        this.f = 0;
        this.g = Integer.MAX_VALUE;
        this.h = Integer.MAX_VALUE;
        this.i = true;
        this.j = 7;
        this.k = null;
        this.l = -1;
        this.m = new java.util.HashMap<>();
        this.n = -1;
        this.o = -1;
        a((android.util.AttributeSet) null);
    }

    public ConstraintLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f141a = new android.util.SparseArray<>();
        this.f142b = new java.util.ArrayList<>(4);
        this.f143c = new java.util.ArrayList<>(100);
        this.d = new a.b.a.j.g();
        this.e = 0;
        this.f = 0;
        this.g = Integer.MAX_VALUE;
        this.h = Integer.MAX_VALUE;
        this.i = true;
        this.j = 7;
        this.k = null;
        this.l = -1;
        this.m = new java.util.HashMap<>();
        this.n = -1;
        this.o = -1;
        a(attributeSet);
    }

    public ConstraintLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f141a = new android.util.SparseArray<>();
        this.f142b = new java.util.ArrayList<>(4);
        this.f143c = new java.util.ArrayList<>(100);
        this.d = new a.b.a.j.g();
        this.e = 0;
        this.f = 0;
        this.g = Integer.MAX_VALUE;
        this.h = Integer.MAX_VALUE;
        this.i = true;
        this.j = 7;
        this.k = null;
        this.l = -1;
        this.m = new java.util.HashMap<>();
        this.n = -1;
        this.o = -1;
        a(attributeSet);
    }

    /* JADX WARN: Code duplicated, block: B:122:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:132:0x01f3  */
    /* JADX WARN: Code duplicated, block: B:138:0x0203  */
    /* JADX WARN: Code duplicated, block: B:140:0x0209  */
    /* JADX WARN: Code duplicated, block: B:141:0x0212 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:142:0x0214  */
    /* JADX WARN: Code duplicated, block: B:144:0x021a  */
    /* JADX WARN: Code duplicated, block: B:145:0x0224  */
    /* JADX WARN: Code duplicated, block: B:146:0x0227  */
    /* JADX WARN: Code duplicated, block: B:153:0x023d  */
    /* JADX WARN: Code duplicated, block: B:155:0x0243  */
    /* JADX WARN: Code duplicated, block: B:156:0x024b  */
    /* JADX WARN: Code duplicated, block: B:163:0x0262  */
    /* JADX WARN: Code duplicated, block: B:165:0x0268  */
    /* JADX WARN: Code duplicated, block: B:166:0x0271  */
    /* JADX WARN: Code duplicated, block: B:174:0x028b  */
    /* JADX WARN: Code duplicated, block: B:176:0x0291  */
    /* JADX WARN: Code duplicated, block: B:177:0x029b  */
    /* JADX WARN: Code duplicated, block: B:185:0x02b4  */
    /* JADX WARN: Code duplicated, block: B:209:0x0336  */
    /* JADX WARN: Code duplicated, block: B:211:0x033a  */
    /* JADX WARN: Code duplicated, block: B:212:0x0354  */
    /* JADX WARN: Code duplicated, block: B:213:0x035b  */
    /* JADX WARN: Code duplicated, block: B:217:0x0369  */
    /* JADX WARN: Code duplicated, block: B:219:0x036d  */
    /* JADX WARN: Code duplicated, block: B:220:0x0388  */
    /* JADX WARN: Code duplicated, block: B:221:0x0392  */
    /* JADX WARN: Code duplicated, block: B:224:0x03a1  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r26v0, types: [android.view.ViewGroup, androidx.constraintlayout.widget.ConstraintLayout] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v32 */
    /* JADX WARN: Type inference failed for: r3v38 */
    /* JADX WARN: Type inference failed for: r3v58 */
    private void a() {
        int i;
        int i2;
        int i3;
        int i4;
        float f;
        int i5;
        float f2;
        a.b.a.j.f fVarB;
        a.b.a.j.e.d dVar;
        a.b.a.j.e.d dVar2;
        int i6;
        a.b.a.j.f fVarB2;
        a.b.a.j.e.d dVar3;
        int i7;
        a.b.a.j.e.d dVar4;
        int i8;
        int i9;
        a.b.a.j.f fVarB3;
        a.b.a.j.e.d dVar5;
        a.b.a.j.e.d dVar6;
        int i10;
        int i11;
        int i12;
        int i13;
        a.b.a.j.f fVarB4;
        a.b.a.j.e.d dVar7;
        int i14;
        int i15;
        a.b.a.j.e.d dVar8;
        int i16;
        float f3;
        float f4;
        android.view.View view;
        a.b.a.j.f fVarB5;
        int i17;
        java.lang.String str;
        a.b.a.j.f fVarB6;
        int i18;
        boolean zIsInEditMode = isInEditMode();
        int childCount = getChildCount();
        ?? r3 = 0;
        if (zIsInEditMode) {
            for (int i19 = 0; i19 < childCount; i19++) {
                android.view.View childAt = getChildAt(i19);
                try {
                    java.lang.String resourceName = getResources().getResourceName(childAt.getId());
                    a(0, resourceName, java.lang.Integer.valueOf(childAt.getId()));
                    int iIndexOf = resourceName.indexOf(47);
                    if (iIndexOf != -1) {
                        resourceName = resourceName.substring(iIndexOf + 1);
                    }
                    b(childAt.getId()).a(resourceName);
                } catch (android.content.res.Resources.NotFoundException unused) {
                }
            }
        }
        for (int i20 = 0; i20 < childCount; i20++) {
            a.b.a.j.f fVarA = a(getChildAt(i20));
            if (fVarA != null) {
                fVarA.D();
            }
        }
        if (this.l != -1) {
            for (int i21 = 0; i21 < childCount; i21++) {
                android.view.View childAt2 = getChildAt(i21);
                if (childAt2.getId() == this.l && (childAt2 instanceof androidx.constraintlayout.widget.d)) {
                    this.k = ((androidx.constraintlayout.widget.d) childAt2).getConstraintSet();
                }
            }
        }
        androidx.constraintlayout.widget.c cVar = this.k;
        if (cVar != null) {
            cVar.a((androidx.constraintlayout.widget.ConstraintLayout) this);
        }
        this.d.L();
        int size = this.f142b.size();
        if (size > 0) {
            for (int i22 = 0; i22 < size; i22++) {
                this.f142b.get(i22).c(this);
            }
        }
        for (int i23 = 0; i23 < childCount; i23++) {
            android.view.View childAt3 = getChildAt(i23);
            if (childAt3 instanceof androidx.constraintlayout.widget.f) {
                ((androidx.constraintlayout.widget.f) childAt3).b(this);
            }
        }
        int i24 = 0;
        while (i24 < childCount) {
            android.view.View childAt4 = getChildAt(i24);
            a.b.a.j.f fVarA2 = a(childAt4);
            if (fVarA2 != null) {
                androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) childAt4.getLayoutParams();
                aVar.a();
                if (aVar.l0) {
                    aVar.l0 = r3;
                } else if (zIsInEditMode) {
                    try {
                        java.lang.String resourceName2 = getResources().getResourceName(childAt4.getId());
                        a(r3, resourceName2, java.lang.Integer.valueOf(childAt4.getId()));
                        b(childAt4.getId()).a(resourceName2.substring(resourceName2.indexOf("id/") + 3));
                    } catch (android.content.res.Resources.NotFoundException unused2) {
                    }
                }
                fVarA2.n(childAt4.getVisibility());
                if (aVar.Z) {
                    fVarA2.n(8);
                }
                fVarA2.a(childAt4);
                this.d.b(fVarA2);
                if (!aVar.V || !aVar.U) {
                    this.f143c.add(fVarA2);
                }
                if (aVar.X) {
                    a.b.a.j.i iVar = (a.b.a.j.i) fVarA2;
                    int i25 = aVar.h0;
                    int i26 = aVar.i0;
                    float f5 = aVar.j0;
                    if (android.os.Build.VERSION.SDK_INT < 17) {
                        i25 = aVar.f144a;
                        i26 = aVar.f145b;
                        f5 = aVar.f146c;
                    }
                    if (f5 != -1.0f) {
                        iVar.e(f5);
                    } else if (i25 != -1) {
                        iVar.t(i25);
                    } else if (i26 != -1) {
                        iVar.u(i26);
                    }
                } else if (aVar.d != -1 || aVar.e != -1 || aVar.f != -1 || aVar.g != -1 || aVar.q != -1 || aVar.p != -1 || aVar.r != -1 || aVar.s != -1 || aVar.h != -1 || aVar.i != -1 || aVar.j != -1 || aVar.k != -1 || aVar.l != -1 || aVar.P != -1 || aVar.Q != -1 || aVar.m != -1 || ((android.view.ViewGroup.MarginLayoutParams) aVar).width == -1 || ((android.view.ViewGroup.MarginLayoutParams) aVar).height == -1) {
                    int i27 = aVar.a0;
                    int i28 = aVar.b0;
                    int i29 = aVar.c0;
                    int i30 = aVar.d0;
                    int i31 = aVar.e0;
                    int i32 = aVar.f0;
                    float f6 = aVar.g0;
                    if (android.os.Build.VERSION.SDK_INT < 17) {
                        int i33 = aVar.d;
                        int i34 = aVar.e;
                        int i35 = aVar.f;
                        i30 = aVar.g;
                        i3 = aVar.t;
                        i2 = aVar.v;
                        f6 = aVar.z;
                        if (i33 == -1 && i34 == -1) {
                            int i36 = aVar.q;
                            if (i36 != -1) {
                                i18 = i34;
                                i27 = i36;
                            } else {
                                i18 = aVar.p;
                                if (i18 == -1) {
                                    i18 = i34;
                                }
                                i27 = i33;
                            }
                        } else {
                            i18 = i34;
                            i27 = i33;
                        }
                        if (i35 == -1 && i30 == -1) {
                            i = aVar.r;
                            if (i == -1) {
                                int i37 = aVar.s;
                                if (i37 != -1) {
                                    i3 = i3;
                                    i2 = i2;
                                    i28 = i18;
                                    f = f6;
                                    i4 = i37;
                                    i = i35;
                                } else {
                                    i = i35;
                                    i28 = i18;
                                }
                            } else {
                                i28 = i18;
                            }
                        } else {
                            i = i35;
                            i28 = i18;
                        }
                        i5 = aVar.m;
                        if (i5 != -1) {
                            fVarB6 = b(i5);
                            if (fVarB6 != null) {
                                fVarA2.a(fVarB6, aVar.o, aVar.n);
                            }
                        } else {
                            if (i27 != -1) {
                                fVarB = b(i27);
                                if (fVarB != null) {
                                    dVar2 = a.b.a.j.e.d.LEFT;
                                    dVar = dVar2;
                                    f2 = f;
                                    i6 = ((android.view.ViewGroup.MarginLayoutParams) aVar).leftMargin;
                                    fVarA2.a(dVar, fVarB, dVar2, i6, i3);
                                } else {
                                    f2 = f;
                                }
                            } else {
                                f2 = f;
                                if (i28 == -1 && (fVarB = b(i28)) != null) {
                                    dVar = a.b.a.j.e.d.LEFT;
                                    dVar2 = a.b.a.j.e.d.RIGHT;
                                    i6 = ((android.view.ViewGroup.MarginLayoutParams) aVar).leftMargin;
                                    fVarA2.a(dVar, fVarB, dVar2, i6, i3);
                                }
                            }
                            if (i != -1) {
                                fVarB2 = b(i);
                                if (fVarB2 != null) {
                                    dVar4 = a.b.a.j.e.d.RIGHT;
                                    dVar3 = a.b.a.j.e.d.LEFT;
                                    i7 = ((android.view.ViewGroup.MarginLayoutParams) aVar).rightMargin;
                                    fVarA2.a(dVar4, fVarB2, dVar3, i7, i2);
                                }
                            } else if (i4 == -1 && (fVarB2 = b(i4)) != null) {
                                dVar3 = a.b.a.j.e.d.RIGHT;
                                i7 = ((android.view.ViewGroup.MarginLayoutParams) aVar).rightMargin;
                                dVar4 = dVar3;
                                fVarA2.a(dVar4, fVarB2, dVar3, i7, i2);
                            }
                            i8 = aVar.h;
                            if (i8 != -1) {
                                fVarB3 = b(i8);
                                if (fVarB3 != null) {
                                    dVar6 = a.b.a.j.e.d.TOP;
                                    i10 = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
                                    i11 = aVar.u;
                                    dVar5 = dVar6;
                                    fVarA2.a(dVar5, fVarB3, dVar6, i10, i11);
                                }
                            } else {
                                i9 = aVar.i;
                                if (i9 == -1 && (fVarB3 = b(i9)) != null) {
                                    dVar5 = a.b.a.j.e.d.TOP;
                                    dVar6 = a.b.a.j.e.d.BOTTOM;
                                    i10 = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
                                    i11 = aVar.u;
                                    fVarA2.a(dVar5, fVarB3, dVar6, i10, i11);
                                }
                            }
                            i12 = aVar.j;
                            if (i12 != -1) {
                                fVarB4 = b(i12);
                                if (fVarB4 != null) {
                                    dVar8 = a.b.a.j.e.d.BOTTOM;
                                    dVar7 = a.b.a.j.e.d.TOP;
                                    i14 = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                                    i15 = aVar.w;
                                    fVarA2.a(dVar8, fVarB4, dVar7, i14, i15);
                                }
                            } else {
                                i13 = aVar.k;
                                if (i13 == -1 && (fVarB4 = b(i13)) != null) {
                                    dVar7 = a.b.a.j.e.d.BOTTOM;
                                    i14 = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                                    i15 = aVar.w;
                                    dVar8 = dVar7;
                                    fVarA2.a(dVar8, fVarB4, dVar7, i14, i15);
                                }
                            }
                            i16 = aVar.l;
                            if (i16 != -1) {
                                view = this.f141a.get(i16);
                                fVarB5 = b(aVar.l);
                                if (fVarB5 != null && view != null && (view.getLayoutParams() instanceof androidx.constraintlayout.widget.ConstraintLayout.a)) {
                                    androidx.constraintlayout.widget.ConstraintLayout.a aVar2 = (androidx.constraintlayout.widget.ConstraintLayout.a) view.getLayoutParams();
                                    aVar.W = true;
                                    aVar2.W = true;
                                    fVarA2.a(a.b.a.j.e.d.BASELINE).a(fVarB5.a(a.b.a.j.e.d.BASELINE), 0, -1, a.b.a.j.e.c.STRONG, 0, true);
                                    fVarA2.a(a.b.a.j.e.d.TOP).j();
                                    fVarA2.a(a.b.a.j.e.d.BOTTOM).j();
                                }
                            }
                            f3 = f2;
                            if (f3 >= 0.0f && f3 != 0.5f) {
                                fVarA2.a(f3);
                            }
                            f4 = aVar.A;
                            if (f4 >= 0.0f && f4 != 0.5f) {
                                fVarA2.c(f4);
                            }
                        }
                        if (zIsInEditMode && (aVar.P != -1 || aVar.Q != -1)) {
                            fVarA2.c(aVar.P, aVar.Q);
                        }
                        if (aVar.U) {
                            fVarA2.a(a.b.a.j.f.b.FIXED);
                            i17 = ((android.view.ViewGroup.MarginLayoutParams) aVar).width;
                        } else {
                            if (((android.view.ViewGroup.MarginLayoutParams) aVar).width == -1) {
                                fVarA2.a(a.b.a.j.f.b.MATCH_PARENT);
                                fVarA2.a(a.b.a.j.e.d.LEFT).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).leftMargin;
                                fVarA2.a(a.b.a.j.e.d.RIGHT).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).rightMargin;
                            } else {
                                fVarA2.a(a.b.a.j.f.b.MATCH_CONSTRAINT);
                                i17 = 0;
                            }
                            if (!aVar.V) {
                                r3 = 0;
                                fVarA2.b(a.b.a.j.f.b.FIXED);
                                fVarA2.g(((android.view.ViewGroup.MarginLayoutParams) aVar).height);
                            } else if (((android.view.ViewGroup.MarginLayoutParams) aVar).height == -1) {
                                fVarA2.b(a.b.a.j.f.b.MATCH_PARENT);
                                fVarA2.a(a.b.a.j.e.d.TOP).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
                                fVarA2.a(a.b.a.j.e.d.BOTTOM).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                                r3 = 0;
                            } else {
                                fVarA2.b(a.b.a.j.f.b.MATCH_CONSTRAINT);
                                r3 = 0;
                                fVarA2.g(0);
                            }
                            str = aVar.B;
                            if (str != null) {
                                fVarA2.b(str);
                            }
                            fVarA2.b(aVar.D);
                            fVarA2.d(aVar.E);
                            fVarA2.h(aVar.F);
                            fVarA2.m(aVar.G);
                            fVarA2.a(aVar.H, aVar.J, aVar.L, aVar.N);
                            fVarA2.b(aVar.I, aVar.K, aVar.M, aVar.O);
                        }
                        fVarA2.o(i17);
                        if (!aVar.V) {
                            r3 = 0;
                            fVarA2.b(a.b.a.j.f.b.FIXED);
                            fVarA2.g(((android.view.ViewGroup.MarginLayoutParams) aVar).height);
                        } else if (((android.view.ViewGroup.MarginLayoutParams) aVar).height == -1) {
                            fVarA2.b(a.b.a.j.f.b.MATCH_PARENT);
                            fVarA2.a(a.b.a.j.e.d.TOP).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
                            fVarA2.a(a.b.a.j.e.d.BOTTOM).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                            r3 = 0;
                        } else {
                            fVarA2.b(a.b.a.j.f.b.MATCH_CONSTRAINT);
                            r3 = 0;
                            fVarA2.g(0);
                        }
                        str = aVar.B;
                        if (str != null) {
                            fVarA2.b(str);
                        }
                        fVarA2.b(aVar.D);
                        fVarA2.d(aVar.E);
                        fVarA2.h(aVar.F);
                        fVarA2.m(aVar.G);
                        fVarA2.a(aVar.H, aVar.J, aVar.L, aVar.N);
                        fVarA2.b(aVar.I, aVar.K, aVar.M, aVar.O);
                    } else {
                        i = i29;
                        i2 = i32;
                        i3 = i31;
                    }
                    f = f6;
                    i4 = i30;
                    i5 = aVar.m;
                    if (i5 != -1) {
                        fVarB6 = b(i5);
                        if (fVarB6 != null) {
                            fVarA2.a(fVarB6, aVar.o, aVar.n);
                        }
                    } else {
                        if (i27 != -1) {
                            fVarB = b(i27);
                            if (fVarB != null) {
                                dVar2 = a.b.a.j.e.d.LEFT;
                                dVar = dVar2;
                                f2 = f;
                                i6 = ((android.view.ViewGroup.MarginLayoutParams) aVar).leftMargin;
                                fVarA2.a(dVar, fVarB, dVar2, i6, i3);
                            } else {
                                f2 = f;
                            }
                        } else {
                            f2 = f;
                            if (i28 == -1) {
                            }
                        }
                        if (i != -1) {
                            fVarB2 = b(i);
                            if (fVarB2 != null) {
                                dVar4 = a.b.a.j.e.d.RIGHT;
                                dVar3 = a.b.a.j.e.d.LEFT;
                                i7 = ((android.view.ViewGroup.MarginLayoutParams) aVar).rightMargin;
                                fVarA2.a(dVar4, fVarB2, dVar3, i7, i2);
                            }
                        } else if (i4 == -1) {
                        }
                        i8 = aVar.h;
                        if (i8 != -1) {
                            fVarB3 = b(i8);
                            if (fVarB3 != null) {
                                dVar6 = a.b.a.j.e.d.TOP;
                                i10 = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
                                i11 = aVar.u;
                                dVar5 = dVar6;
                                fVarA2.a(dVar5, fVarB3, dVar6, i10, i11);
                            }
                        } else {
                            i9 = aVar.i;
                            if (i9 == -1) {
                            }
                        }
                        i12 = aVar.j;
                        if (i12 != -1) {
                            fVarB4 = b(i12);
                            if (fVarB4 != null) {
                                dVar8 = a.b.a.j.e.d.BOTTOM;
                                dVar7 = a.b.a.j.e.d.TOP;
                                i14 = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                                i15 = aVar.w;
                                fVarA2.a(dVar8, fVarB4, dVar7, i14, i15);
                            }
                        } else {
                            i13 = aVar.k;
                            if (i13 == -1) {
                            }
                        }
                        i16 = aVar.l;
                        if (i16 != -1) {
                            view = this.f141a.get(i16);
                            fVarB5 = b(aVar.l);
                            if (fVarB5 != null) {
                                androidx.constraintlayout.widget.ConstraintLayout.a aVar3 = (androidx.constraintlayout.widget.ConstraintLayout.a) view.getLayoutParams();
                                aVar.W = true;
                                aVar3.W = true;
                                fVarA2.a(a.b.a.j.e.d.BASELINE).a(fVarB5.a(a.b.a.j.e.d.BASELINE), 0, -1, a.b.a.j.e.c.STRONG, 0, true);
                                fVarA2.a(a.b.a.j.e.d.TOP).j();
                                fVarA2.a(a.b.a.j.e.d.BOTTOM).j();
                            }
                        }
                        f3 = f2;
                        if (f3 >= 0.0f) {
                            fVarA2.a(f3);
                        }
                        f4 = aVar.A;
                        if (f4 >= 0.0f) {
                            fVarA2.c(f4);
                        }
                    }
                    if (zIsInEditMode) {
                        fVarA2.c(aVar.P, aVar.Q);
                    }
                    if (aVar.U) {
                        if (((android.view.ViewGroup.MarginLayoutParams) aVar).width == -1) {
                            fVarA2.a(a.b.a.j.f.b.MATCH_PARENT);
                            fVarA2.a(a.b.a.j.e.d.LEFT).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).leftMargin;
                            fVarA2.a(a.b.a.j.e.d.RIGHT).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).rightMargin;
                        } else {
                            fVarA2.a(a.b.a.j.f.b.MATCH_CONSTRAINT);
                            i17 = 0;
                        }
                        if (!aVar.V) {
                            r3 = 0;
                            fVarA2.b(a.b.a.j.f.b.FIXED);
                            fVarA2.g(((android.view.ViewGroup.MarginLayoutParams) aVar).height);
                        } else if (((android.view.ViewGroup.MarginLayoutParams) aVar).height == -1) {
                            fVarA2.b(a.b.a.j.f.b.MATCH_PARENT);
                            fVarA2.a(a.b.a.j.e.d.TOP).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
                            fVarA2.a(a.b.a.j.e.d.BOTTOM).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                            r3 = 0;
                        } else {
                            fVarA2.b(a.b.a.j.f.b.MATCH_CONSTRAINT);
                            r3 = 0;
                            fVarA2.g(0);
                        }
                        str = aVar.B;
                        if (str != null) {
                            fVarA2.b(str);
                        }
                        fVarA2.b(aVar.D);
                        fVarA2.d(aVar.E);
                        fVarA2.h(aVar.F);
                        fVarA2.m(aVar.G);
                        fVarA2.a(aVar.H, aVar.J, aVar.L, aVar.N);
                        fVarA2.b(aVar.I, aVar.K, aVar.M, aVar.O);
                    } else {
                        fVarA2.a(a.b.a.j.f.b.FIXED);
                        i17 = ((android.view.ViewGroup.MarginLayoutParams) aVar).width;
                    }
                    fVarA2.o(i17);
                    if (!aVar.V) {
                        r3 = 0;
                        fVarA2.b(a.b.a.j.f.b.FIXED);
                        fVarA2.g(((android.view.ViewGroup.MarginLayoutParams) aVar).height);
                    } else if (((android.view.ViewGroup.MarginLayoutParams) aVar).height == -1) {
                        fVarA2.b(a.b.a.j.f.b.MATCH_PARENT);
                        fVarA2.a(a.b.a.j.e.d.TOP).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).topMargin;
                        fVarA2.a(a.b.a.j.e.d.BOTTOM).e = ((android.view.ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                        r3 = 0;
                    } else {
                        fVarA2.b(a.b.a.j.f.b.MATCH_CONSTRAINT);
                        r3 = 0;
                        fVarA2.g(0);
                    }
                    str = aVar.B;
                    if (str != null) {
                        fVarA2.b(str);
                    }
                    fVarA2.b(aVar.D);
                    fVarA2.d(aVar.E);
                    fVarA2.h(aVar.F);
                    fVarA2.m(aVar.G);
                    fVarA2.a(aVar.H, aVar.J, aVar.L, aVar.N);
                    fVarA2.b(aVar.I, aVar.K, aVar.M, aVar.O);
                }
            }
            i24++;
            r3 = r3;
        }
    }

    private void a(int i, int i2) {
        boolean z;
        boolean z2;
        int baseline;
        int childMeasureSpec;
        int childMeasureSpec2;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            android.view.View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) childAt.getLayoutParams();
                a.b.a.j.f fVar = aVar.k0;
                if (!aVar.X && !aVar.Y) {
                    fVar.n(childAt.getVisibility());
                    int measuredWidth = ((android.view.ViewGroup.MarginLayoutParams) aVar).width;
                    int measuredHeight = ((android.view.ViewGroup.MarginLayoutParams) aVar).height;
                    boolean z3 = aVar.U;
                    if (z3 || aVar.V || (!z3 && aVar.H == 1) || ((android.view.ViewGroup.MarginLayoutParams) aVar).width == -1 || (!aVar.V && (aVar.I == 1 || ((android.view.ViewGroup.MarginLayoutParams) aVar).height == -1))) {
                        if (measuredWidth == 0) {
                            childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, -2);
                            z = true;
                        } else if (measuredWidth == -1) {
                            childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, -1);
                            z = false;
                        } else {
                            z = measuredWidth == -2;
                            childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, measuredWidth);
                        }
                        if (measuredHeight == 0) {
                            childMeasureSpec2 = android.view.ViewGroup.getChildMeasureSpec(i2, paddingTop, -2);
                            z2 = true;
                        } else if (measuredHeight == -1) {
                            childMeasureSpec2 = android.view.ViewGroup.getChildMeasureSpec(i2, paddingTop, -1);
                            z2 = false;
                        } else {
                            z2 = measuredHeight == -2;
                            childMeasureSpec2 = android.view.ViewGroup.getChildMeasureSpec(i2, paddingTop, measuredHeight);
                        }
                        childAt.measure(childMeasureSpec, childMeasureSpec2);
                        a.b.a.f fVar2 = this.p;
                        if (fVar2 != null) {
                            fVar2.f33a++;
                        }
                        fVar.b(measuredWidth == -2);
                        fVar.a(measuredHeight == -2);
                        measuredWidth = childAt.getMeasuredWidth();
                        measuredHeight = childAt.getMeasuredHeight();
                    } else {
                        z = false;
                        z2 = false;
                    }
                    fVar.o(measuredWidth);
                    fVar.g(measuredHeight);
                    if (z) {
                        fVar.q(measuredWidth);
                    }
                    if (z2) {
                        fVar.p(measuredHeight);
                    }
                    if (aVar.W && (baseline = childAt.getBaseline()) != -1) {
                        fVar.f(baseline);
                    }
                }
            }
        }
    }

    private void a(android.util.AttributeSet attributeSet) {
        this.d.a(this);
        this.f141a.put(getId(), this);
        this.k = null;
        if (attributeSet != null) {
            android.content.res.TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, androidx.constraintlayout.widget.h.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_android_minWidth) {
                    this.e = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.e);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_android_minHeight) {
                    this.f = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_android_maxWidth) {
                    this.g = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.g);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_android_maxHeight) {
                    this.h = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.h);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.j = typedArrayObtainStyledAttributes.getInt(index, this.j);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_constraintSet) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                    try {
                        androidx.constraintlayout.widget.c cVar = new androidx.constraintlayout.widget.c();
                        this.k = cVar;
                        cVar.a(getContext(), resourceId);
                    } catch (android.content.res.Resources.NotFoundException unused) {
                        this.k = null;
                    }
                    this.l = resourceId;
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        this.d.u(this.j);
    }

    private final a.b.a.j.f b(int i) {
        if (i == 0) {
            return this.d;
        }
        android.view.View viewFindViewById = this.f141a.get(i);
        if (viewFindViewById == null && (viewFindViewById = findViewById(i)) != null && viewFindViewById != this && viewFindViewById.getParent() == this) {
            onViewAdded(viewFindViewById);
        }
        if (viewFindViewById == this) {
            return this.d;
        }
        if (viewFindViewById == null) {
            return null;
        }
        return ((androidx.constraintlayout.widget.ConstraintLayout.a) viewFindViewById.getLayoutParams()).k0;
    }

    private void b() {
        int childCount = getChildCount();
        boolean z = false;
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).isLayoutRequested()) {
                z = true;
                break;
            }
        }
        if (z) {
            this.f143c.clear();
            a();
        }
    }

    /* JADX WARN: Code duplicated, block: B:163:0x02cf  */
    /* JADX WARN: Code duplicated, block: B:51:0x00d4  */
    private void b(int i, int i2) {
        long j;
        int i3;
        androidx.constraintlayout.widget.ConstraintLayout constraintLayout;
        int i4;
        int i5;
        long j2;
        boolean z;
        int childMeasureSpec;
        boolean z2;
        int childMeasureSpec2;
        int baseline;
        int i6;
        int baseline2;
        androidx.constraintlayout.widget.ConstraintLayout constraintLayout2 = this;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int childCount = getChildCount();
        int i7 = 0;
        while (true) {
            j = 1;
            i3 = 8;
            if (i7 >= childCount) {
                break;
            }
            android.view.View childAt = constraintLayout2.getChildAt(i7);
            if (childAt.getVisibility() == 8) {
                i6 = paddingTop;
            } else {
                androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) childAt.getLayoutParams();
                a.b.a.j.f fVar = aVar.k0;
                if (aVar.X || aVar.Y) {
                    i6 = paddingTop;
                } else {
                    fVar.n(childAt.getVisibility());
                    int i8 = ((android.view.ViewGroup.MarginLayoutParams) aVar).width;
                    int i9 = ((android.view.ViewGroup.MarginLayoutParams) aVar).height;
                    if (i8 == 0 || i9 == 0) {
                        i6 = paddingTop;
                        fVar.m().b();
                        fVar.l().b();
                    } else {
                        boolean z3 = i8 == -2;
                        int childMeasureSpec3 = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, i8);
                        boolean z4 = i9 == -2;
                        childAt.measure(childMeasureSpec3, android.view.ViewGroup.getChildMeasureSpec(i2, paddingTop, i9));
                        a.b.a.f fVar2 = constraintLayout2.p;
                        i6 = paddingTop;
                        if (fVar2 != null) {
                            fVar2.f33a++;
                        }
                        fVar.b(i8 == -2);
                        fVar.a(i9 == -2);
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight = childAt.getMeasuredHeight();
                        fVar.o(measuredWidth);
                        fVar.g(measuredHeight);
                        if (z3) {
                            fVar.q(measuredWidth);
                        }
                        if (z4) {
                            fVar.p(measuredHeight);
                        }
                        if (aVar.W && (baseline2 = childAt.getBaseline()) != -1) {
                            fVar.f(baseline2);
                        }
                        if (aVar.U && aVar.V) {
                            fVar.m().a(measuredWidth);
                            fVar.l().a(measuredHeight);
                        }
                    }
                }
            }
            i7++;
            paddingTop = i6;
        }
        int i10 = paddingTop;
        constraintLayout2.d.U();
        int i11 = 0;
        while (i11 < childCount) {
            android.view.View childAt2 = constraintLayout2.getChildAt(i11);
            if (childAt2.getVisibility() == i3) {
                constraintLayout = constraintLayout2;
                i4 = i11;
                i5 = childCount;
                j2 = j;
            } else {
                androidx.constraintlayout.widget.ConstraintLayout.a aVar2 = (androidx.constraintlayout.widget.ConstraintLayout.a) childAt2.getLayoutParams();
                a.b.a.j.f fVar3 = aVar2.k0;
                if (aVar2.X || aVar2.Y) {
                    constraintLayout = constraintLayout2;
                    i4 = i11;
                    i5 = childCount;
                    j2 = j;
                } else {
                    fVar3.n(childAt2.getVisibility());
                    int iF = ((android.view.ViewGroup.MarginLayoutParams) aVar2).width;
                    int iF2 = ((android.view.ViewGroup.MarginLayoutParams) aVar2).height;
                    if (iF == 0 || iF2 == 0) {
                        a.b.a.j.m mVarD = fVar3.a(a.b.a.j.e.d.LEFT).d();
                        a.b.a.j.m mVarD2 = fVar3.a(a.b.a.j.e.d.RIGHT).d();
                        boolean z5 = (fVar3.a(a.b.a.j.e.d.LEFT).g() == null || fVar3.a(a.b.a.j.e.d.RIGHT).g() == null) ? false : true;
                        a.b.a.j.m mVarD3 = fVar3.a(a.b.a.j.e.d.TOP).d();
                        a.b.a.j.m mVarD4 = fVar3.a(a.b.a.j.e.d.BOTTOM).d();
                        i5 = childCount;
                        boolean z6 = (fVar3.a(a.b.a.j.e.d.TOP).g() == null || fVar3.a(a.b.a.j.e.d.BOTTOM).g() == null) ? false : true;
                        if (iF == 0 && iF2 == 0 && z5 && z6) {
                            constraintLayout = constraintLayout2;
                            i4 = i11;
                            j2 = 1;
                        } else {
                            i4 = i11;
                            boolean z7 = constraintLayout2.d.j() != a.b.a.j.f.b.WRAP_CONTENT;
                            boolean z8 = constraintLayout2.d.q() != a.b.a.j.f.b.WRAP_CONTENT;
                            if (!z7) {
                                fVar3.m().b();
                            }
                            if (!z8) {
                                fVar3.l().b();
                            }
                            if (iF == 0) {
                                if (z7 && fVar3.C() && z5 && mVarD.c() && mVarD2.c()) {
                                    iF = (int) (mVarD2.f() - mVarD.f());
                                    fVar3.m().a(iF);
                                    childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, iF);
                                    z = false;
                                } else {
                                    childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, -2);
                                    z7 = false;
                                    z = true;
                                }
                            } else if (iF == -1) {
                                childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, -1);
                                z = false;
                            } else {
                                z = iF == -2;
                                childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i, paddingLeft, iF);
                            }
                            if (iF2 == 0) {
                                if (z8 && fVar3.B() && z6 && mVarD3.c() && mVarD4.c()) {
                                    iF2 = (int) (mVarD4.f() - mVarD3.f());
                                    fVar3.l().a(iF2);
                                    childMeasureSpec2 = android.view.ViewGroup.getChildMeasureSpec(i2, i10, iF2);
                                    z2 = false;
                                } else {
                                    childMeasureSpec2 = android.view.ViewGroup.getChildMeasureSpec(i2, i10, -2);
                                    z8 = false;
                                    z2 = true;
                                }
                            } else if (iF2 == -1) {
                                childMeasureSpec2 = android.view.ViewGroup.getChildMeasureSpec(i2, i10, -1);
                                z2 = false;
                            } else {
                                z2 = iF2 == -2;
                                childMeasureSpec2 = android.view.ViewGroup.getChildMeasureSpec(i2, i10, iF2);
                            }
                            childAt2.measure(childMeasureSpec, childMeasureSpec2);
                            constraintLayout = this;
                            a.b.a.f fVar4 = constraintLayout.p;
                            if (fVar4 != null) {
                                j2 = 1;
                                fVar4.f33a++;
                            } else {
                                j2 = 1;
                            }
                            fVar3.b(iF == -2);
                            fVar3.a(iF2 == -2);
                            int measuredWidth2 = childAt2.getMeasuredWidth();
                            int measuredHeight2 = childAt2.getMeasuredHeight();
                            fVar3.o(measuredWidth2);
                            fVar3.g(measuredHeight2);
                            if (z) {
                                fVar3.q(measuredWidth2);
                            }
                            if (z2) {
                                fVar3.p(measuredHeight2);
                            }
                            if (z7) {
                                fVar3.m().a(measuredWidth2);
                            } else {
                                fVar3.m().f();
                            }
                            if (z8) {
                                fVar3.l().a(measuredHeight2);
                            } else {
                                fVar3.l().f();
                            }
                            if (aVar2.W && (baseline = childAt2.getBaseline()) != -1) {
                                fVar3.f(baseline);
                            }
                        }
                    } else {
                        constraintLayout = constraintLayout2;
                        i4 = i11;
                        i5 = childCount;
                        j2 = j;
                    }
                }
            }
            i11 = i4 + 1;
            constraintLayout2 = constraintLayout;
            childCount = i5;
            j = j2;
            i3 = 8;
        }
    }

    private void c() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt instanceof androidx.constraintlayout.widget.f) {
                ((androidx.constraintlayout.widget.f) childAt).a(this);
            }
        }
        int size = this.f142b.size();
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                this.f142b.get(i2).b(this);
            }
        }
    }

    private void c(int i, int i2) {
        int iMin;
        a.b.a.j.f.b bVar;
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        a.b.a.j.f.b bVar2 = a.b.a.j.f.b.FIXED;
        getLayoutParams();
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                bVar = a.b.a.j.f.b.WRAP_CONTENT;
            } else if (mode != 1073741824) {
                bVar = bVar2;
            } else {
                iMin = java.lang.Math.min(this.g, size) - paddingLeft;
                bVar = bVar2;
            }
            iMin = 0;
        } else {
            iMin = size;
            bVar = a.b.a.j.f.b.WRAP_CONTENT;
        }
        if (mode2 != Integer.MIN_VALUE) {
            if (mode2 == 0) {
                bVar2 = a.b.a.j.f.b.WRAP_CONTENT;
            } else if (mode2 == 1073741824) {
                size2 = java.lang.Math.min(this.h, size2) - paddingTop;
            }
            size2 = 0;
        } else {
            bVar2 = a.b.a.j.f.b.WRAP_CONTENT;
        }
        this.d.l(0);
        this.d.k(0);
        this.d.a(bVar);
        this.d.o(iMin);
        this.d.b(bVar2);
        this.d.g(size2);
        this.d.l((this.e - getPaddingLeft()) - getPaddingRight());
        this.d.k((this.f - getPaddingTop()) - getPaddingBottom());
    }

    public final a.b.a.j.f a(android.view.View view) {
        if (view == this) {
            return this.d;
        }
        if (view == null) {
            return null;
        }
        return ((androidx.constraintlayout.widget.ConstraintLayout.a) view.getLayoutParams()).k0;
    }

    public android.view.View a(int i) {
        return this.f141a.get(i);
    }

    public java.lang.Object a(int i, java.lang.Object obj) {
        if (i != 0 || !(obj instanceof java.lang.String)) {
            return null;
        }
        java.lang.String str = (java.lang.String) obj;
        java.util.HashMap<java.lang.String, java.lang.Integer> map = this.m;
        if (map == null || !map.containsKey(str)) {
            return null;
        }
        return this.m.get(str);
    }

    public void a(int i, java.lang.Object obj, java.lang.Object obj2) {
        if (i == 0 && (obj instanceof java.lang.String) && (obj2 instanceof java.lang.Integer)) {
            if (this.m == null) {
                this.m = new java.util.HashMap<>();
            }
            java.lang.String strSubstring = (java.lang.String) obj;
            int iIndexOf = strSubstring.indexOf("/");
            if (iIndexOf != -1) {
                strSubstring = strSubstring.substring(iIndexOf + 1);
            }
            this.m.put(strSubstring, java.lang.Integer.valueOf(((java.lang.Integer) obj2).intValue()));
        }
    }

    protected void a(java.lang.String str) {
        this.d.K();
        a.b.a.f fVar = this.p;
        if (fVar != null) {
            fVar.f35c++;
        }
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (android.os.Build.VERSION.SDK_INT < 14) {
            onViewAdded(view);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof androidx.constraintlayout.widget.ConstraintLayout.a;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(android.graphics.Canvas canvas) {
        java.lang.Object tag;
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int childCount = getChildCount();
            float width = getWidth();
            float height = getHeight();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt.getVisibility() != 8 && (tag = childAt.getTag()) != null && (tag instanceof java.lang.String)) {
                    java.lang.String[] strArrSplit = ((java.lang.String) tag).split(",");
                    if (strArrSplit.length == 4) {
                        int i2 = java.lang.Integer.parseInt(strArrSplit[0]);
                        int i3 = java.lang.Integer.parseInt(strArrSplit[1]);
                        int i4 = java.lang.Integer.parseInt(strArrSplit[2]);
                        int i5 = (int) ((i2 / 1080.0f) * width);
                        int i6 = (int) ((i3 / 1920.0f) * height);
                        int i7 = (int) ((java.lang.Integer.parseInt(strArrSplit[3]) / 1920.0f) * height);
                        android.graphics.Paint paint = new android.graphics.Paint();
                        paint.setColor(-65536);
                        float f = i5;
                        float f2 = i6;
                        float f3 = i5 + ((int) ((i4 / 1080.0f) * width));
                        canvas.drawLine(f, f2, f3, f2, paint);
                        float f4 = i6 + i7;
                        canvas.drawLine(f3, f2, f3, f4, paint);
                        canvas.drawLine(f3, f4, f, f4, paint);
                        canvas.drawLine(f, f4, f, f2, paint);
                        paint.setColor(-16711936);
                        canvas.drawLine(f, f2, f3, f4, paint);
                        canvas.drawLine(f, f4, f3, f2, paint);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public androidx.constraintlayout.widget.ConstraintLayout.a generateDefaultLayoutParams() {
        return new androidx.constraintlayout.widget.ConstraintLayout.a(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new androidx.constraintlayout.widget.ConstraintLayout.a(layoutParams);
    }

    @Override // android.view.ViewGroup
    public androidx.constraintlayout.widget.ConstraintLayout.a generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new androidx.constraintlayout.widget.ConstraintLayout.a(getContext(), attributeSet);
    }

    public int getMaxHeight() {
        return this.h;
    }

    public int getMaxWidth() {
        return this.g;
    }

    public int getMinHeight() {
        return this.f;
    }

    public int getMinWidth() {
        return this.e;
    }

    public int getOptimizationLevel() {
        return this.d.M();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        android.view.View content;
        int childCount = getChildCount();
        boolean zIsInEditMode = isInEditMode();
        for (int i5 = 0; i5 < childCount; i5++) {
            android.view.View childAt = getChildAt(i5);
            androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) childAt.getLayoutParams();
            a.b.a.j.f fVar = aVar.k0;
            if ((childAt.getVisibility() != 8 || aVar.X || aVar.Y || zIsInEditMode) && !aVar.Z) {
                int iG = fVar.g();
                int iH = fVar.h();
                int iS = fVar.s() + iG;
                int i6 = fVar.i() + iH;
                childAt.layout(iG, iH, iS, i6);
                if ((childAt instanceof androidx.constraintlayout.widget.f) && (content = ((androidx.constraintlayout.widget.f) childAt).getContent()) != null) {
                    content.setVisibility(0);
                    content.layout(iG, iH, iS, i6);
                }
            }
        }
        int size = this.f142b.size();
        if (size > 0) {
            for (int i7 = 0; i7 < size; i7++) {
                this.f142b.get(i7).a(this);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:61:0x011d  */
    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        boolean z2;
        boolean z3;
        int i5;
        int i6;
        boolean z4;
        int i7;
        boolean z5;
        int baseline;
        java.lang.System.currentTimeMillis();
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        this.d.r(paddingLeft);
        this.d.s(paddingTop);
        this.d.j(this.g);
        this.d.i(this.h);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            this.d.c(getLayoutDirection() == 1);
        }
        c(i, i2);
        int iS = this.d.s();
        int i8 = this.d.i();
        if (this.i) {
            this.i = false;
            b();
            z = true;
        } else {
            z = false;
        }
        boolean z6 = (this.j & 8) == 8;
        if (z6) {
            this.d.T();
            this.d.f(iS, i8);
            b(i, i2);
        } else {
            a(i, i2);
        }
        c();
        if (getChildCount() > 0 && z) {
            a.b.a.j.a.a(this.d);
        }
        a.b.a.j.g gVar = this.d;
        if (gVar.x0) {
            if (gVar.y0 && mode == Integer.MIN_VALUE) {
                int i9 = gVar.A0;
                if (i9 < size) {
                    gVar.o(i9);
                }
                this.d.a(a.b.a.j.f.b.FIXED);
            }
            a.b.a.j.g gVar2 = this.d;
            if (gVar2.z0 && mode2 == Integer.MIN_VALUE) {
                int i10 = gVar2.B0;
                if (i10 < size2) {
                    gVar2.g(i10);
                }
                this.d.b(a.b.a.j.f.b.FIXED);
            }
        }
        if ((this.j & 32) == 32) {
            int iS2 = this.d.s();
            int i11 = this.d.i();
            if (this.n != iS2 && mode == 1073741824) {
                a.b.a.j.a.a(this.d.w0, 0, iS2);
            }
            if (this.o != i11 && mode2 == 1073741824) {
                a.b.a.j.a.a(this.d.w0, 1, i11);
            }
            a.b.a.j.g gVar3 = this.d;
            if (gVar3.y0 && gVar3.A0 > size) {
                a.b.a.j.a.a(gVar3.w0, 0, size);
            }
            a.b.a.j.g gVar4 = this.d;
            if (gVar4.z0 && gVar4.B0 > size2) {
                a.b.a.j.a.a(gVar4.w0, 1, size2);
            }
        }
        if (getChildCount() > 0) {
            a("First pass");
        }
        int size3 = this.f143c.size();
        int paddingBottom = paddingTop + getPaddingBottom();
        int paddingRight = paddingLeft + getPaddingRight();
        if (size3 > 0) {
            boolean z7 = this.d.j() == a.b.a.j.f.b.WRAP_CONTENT;
            boolean z8 = this.d.q() == a.b.a.j.f.b.WRAP_CONTENT;
            int iMax = java.lang.Math.max(this.d.s(), this.e);
            int iMax2 = java.lang.Math.max(this.d.i(), this.f);
            int i12 = 0;
            boolean z9 = false;
            int iCombineMeasuredStates = 0;
            while (i12 < size3) {
                a.b.a.j.f fVar = this.f143c.get(i12);
                int i13 = size3;
                android.view.View view = (android.view.View) fVar.e();
                if (view == null) {
                    i6 = iS;
                    i5 = i8;
                } else {
                    i5 = i8;
                    androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) view.getLayoutParams();
                    i6 = iS;
                    if (!aVar.Y && !aVar.X) {
                        z4 = z9;
                        if (view.getVisibility() != 8 && (!z6 || !fVar.m().c() || !fVar.l().c())) {
                            int i14 = ((android.view.ViewGroup.MarginLayoutParams) aVar).width;
                            int childMeasureSpec = (i14 == -2 && aVar.U) ? android.view.ViewGroup.getChildMeasureSpec(i, paddingRight, i14) : android.view.View.MeasureSpec.makeMeasureSpec(fVar.s(), 1073741824);
                            int i15 = ((android.view.ViewGroup.MarginLayoutParams) aVar).height;
                            view.measure(childMeasureSpec, (i15 == -2 && aVar.V) ? android.view.ViewGroup.getChildMeasureSpec(i2, paddingBottom, i15) : android.view.View.MeasureSpec.makeMeasureSpec(fVar.i(), 1073741824));
                            a.b.a.f fVar2 = this.p;
                            if (fVar2 != null) {
                                fVar2.f34b++;
                            }
                            int measuredWidth = view.getMeasuredWidth();
                            int measuredHeight = view.getMeasuredHeight();
                            if (measuredWidth != fVar.s()) {
                                fVar.o(measuredWidth);
                                if (z6) {
                                    fVar.m().a(measuredWidth);
                                }
                                if (z7 && fVar.n() > iMax) {
                                    iMax = java.lang.Math.max(iMax, fVar.n() + fVar.a(a.b.a.j.e.d.RIGHT).b());
                                }
                                z4 = true;
                            }
                            if (measuredHeight != fVar.i()) {
                                fVar.g(measuredHeight);
                                if (z6) {
                                    fVar.l().a(measuredHeight);
                                }
                                if (z8 && fVar.d() > iMax2) {
                                    iMax2 = java.lang.Math.max(iMax2, fVar.d() + fVar.a(a.b.a.j.e.d.BOTTOM).b());
                                }
                                i7 = iMax2;
                                z5 = true;
                            } else {
                                i7 = iMax2;
                                z5 = z4;
                            }
                            if (aVar.W && (baseline = view.getBaseline()) != -1 && baseline != fVar.c()) {
                                fVar.f(baseline);
                                z5 = true;
                            }
                            if (android.os.Build.VERSION.SDK_INT >= 11) {
                                iCombineMeasuredStates = android.view.ViewGroup.combineMeasuredStates(iCombineMeasuredStates, view.getMeasuredState());
                            }
                            z4 = z5;
                            iMax2 = i7;
                        }
                        i12++;
                        paddingBottom = paddingBottom;
                        iS = i6;
                        size3 = i13;
                        i8 = i5;
                        z9 = z4;
                    }
                    iCombineMeasuredStates = iCombineMeasuredStates;
                    i12++;
                    paddingBottom = paddingBottom;
                    iS = i6;
                    size3 = i13;
                    i8 = i5;
                    z9 = z4;
                }
                z4 = z9;
                iCombineMeasuredStates = iCombineMeasuredStates;
                i12++;
                paddingBottom = paddingBottom;
                iS = i6;
                size3 = i13;
                i8 = i5;
                z9 = z4;
            }
            int i16 = size3;
            int i17 = iS;
            int i18 = i8;
            boolean z10 = z9;
            i3 = paddingBottom;
            int i19 = iCombineMeasuredStates;
            if (z10) {
                this.d.o(i17);
                this.d.g(i18);
                if (z6) {
                    this.d.U();
                }
                a("2nd pass");
                if (this.d.s() < iMax) {
                    this.d.o(iMax);
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (this.d.i() < iMax2) {
                    this.d.g(iMax2);
                    z3 = true;
                } else {
                    z3 = z2;
                }
                if (z3) {
                    a("3rd pass");
                }
            }
            for (int i20 = 0; i20 < i16; i20++) {
                a.b.a.j.f fVar3 = this.f143c.get(i20);
                android.view.View view2 = (android.view.View) fVar3.e();
                if (view2 != null && (view2.getMeasuredWidth() != fVar3.s() || view2.getMeasuredHeight() != fVar3.i())) {
                    if (fVar3.r() != 8) {
                        view2.measure(android.view.View.MeasureSpec.makeMeasureSpec(fVar3.s(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(fVar3.i(), 1073741824));
                        a.b.a.f fVar4 = this.p;
                        if (fVar4 != null) {
                            fVar4.f34b++;
                        }
                    }
                }
            }
            i4 = i19;
        } else {
            i3 = paddingBottom;
            i4 = 0;
        }
        int iS3 = this.d.s() + paddingRight;
        int i21 = this.d.i() + i3;
        if (android.os.Build.VERSION.SDK_INT < 11) {
            setMeasuredDimension(iS3, i21);
            this.n = iS3;
            this.o = i21;
            return;
        }
        int iResolveSizeAndState = android.view.ViewGroup.resolveSizeAndState(iS3, i, i4);
        int iResolveSizeAndState2 = android.view.ViewGroup.resolveSizeAndState(i21, i2, i4 << 16) & 16777215;
        int iMin = java.lang.Math.min(this.g, iResolveSizeAndState & 16777215);
        int iMin2 = java.lang.Math.min(this.h, iResolveSizeAndState2);
        if (this.d.Q()) {
            iMin |= 16777216;
        }
        if (this.d.O()) {
            iMin2 |= 16777216;
        }
        setMeasuredDimension(iMin, iMin2);
        this.n = iMin;
        this.o = iMin2;
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        a.b.a.j.f fVarA = a(view);
        if ((view instanceof androidx.constraintlayout.widget.e) && !(fVarA instanceof a.b.a.j.i)) {
            androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) view.getLayoutParams();
            a.b.a.j.i iVar = new a.b.a.j.i();
            aVar.k0 = iVar;
            aVar.X = true;
            iVar.v(aVar.R);
        }
        if (view instanceof androidx.constraintlayout.widget.b) {
            androidx.constraintlayout.widget.b bVar = (androidx.constraintlayout.widget.b) view;
            bVar.a();
            ((androidx.constraintlayout.widget.ConstraintLayout.a) view.getLayoutParams()).Y = true;
            if (!this.f142b.contains(bVar)) {
                this.f142b.add(bVar);
            }
        }
        this.f141a.put(view.getId(), view);
        this.i = true;
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.f141a.remove(view.getId());
        a.b.a.j.f fVarA = a(view);
        this.d.c(fVarA);
        this.f142b.remove(view);
        this.f143c.remove(fVarA);
        this.i = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(android.view.View view) {
        super.removeView(view);
        if (android.os.Build.VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        this.i = true;
        this.n = -1;
        this.o = -1;
    }

    public void setConstraintSet(androidx.constraintlayout.widget.c cVar) {
        this.k = cVar;
    }

    @Override // android.view.View
    public void setId(int i) {
        this.f141a.remove(getId());
        super.setId(i);
        this.f141a.put(getId(), this);
    }

    public void setMaxHeight(int i) {
        if (i == this.h) {
            return;
        }
        this.h = i;
        requestLayout();
    }

    public void setMaxWidth(int i) {
        if (i == this.g) {
            return;
        }
        this.g = i;
        requestLayout();
    }

    public void setMinHeight(int i) {
        if (i == this.f) {
            return;
        }
        this.f = i;
        requestLayout();
    }

    public void setMinWidth(int i) {
        if (i == this.e) {
            return;
        }
        this.e = i;
        requestLayout();
    }

    public void setOptimizationLevel(int i) {
        this.d.u(i);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
