package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class v0 implements me.hisn.mygesture.a.h {
    private static final int[] h = {me.hisn.mygesture.R.id.tri_in_one_item_1, me.hisn.mygesture.R.id.tri_in_one_item_2, me.hisn.mygesture.R.id.tri_in_one_item_3};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.view.View f875a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final android.content.Context f876b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final java.lang.String f877c;
    private final int d;
    android.widget.ImageView e;
    android.widget.ImageView f;
    android.widget.ImageView g;

    public v0(android.content.Context context, java.lang.String str, int i) {
        this.f877c = str;
        this.f876b = context;
        this.d = i;
    }

    private android.graphics.Point a(android.content.Context context, int i) {
        me.hisn.utils.l lVar = new me.hisn.utils.l();
        return i == me.hisn.mygesture.P.B ? new android.graphics.Point(lVar.a(context, 90), lVar.a(context, 60)) : new android.graphics.Point(lVar.a(context, 60), lVar.a(context, 90));
    }

    private android.graphics.drawable.Drawable a(android.content.Context context, java.lang.String str) {
        return c(context, b(context, str));
    }

    private android.view.WindowManager.LayoutParams a(int i, int i2, int i3) {
        int iH;
        int iJ;
        int i4;
        int i5;
        android.graphics.Point pointA = a(this.f875a.getContext(), i3);
        if (i3 == me.hisn.mygesture.P.L) {
            iH = 5 - me.hisn.mygesture.MAS.h();
            iJ = (i2 - (pointA.y / 2)) - me.hisn.mygesture.MAS.j();
            i4 = 8388659;
            i5 = me.hisn.mygesture.R.style.three_in_one_left_anim;
        } else if (i3 == me.hisn.mygesture.P.R) {
            iH = ((me.hisn.mygesture.P.k0 - me.hisn.mygesture.MAS.h()) - pointA.x) - 5;
            iJ = (i2 - (pointA.y / 2)) - me.hisn.mygesture.MAS.j();
            i4 = 8388659;
            i5 = me.hisn.mygesture.R.style.three_in_one_right_anim;
        } else if (i3 == me.hisn.mygesture.P.B) {
            iH = (i - me.hisn.mygesture.MAS.h()) - (pointA.x / 2);
            iJ = 5 - me.hisn.mygesture.MAS.g();
            i4 = 8388691;
            i5 = me.hisn.mygesture.R.style.three_in_one_bottom_anim;
        } else {
            iH = (i - me.hisn.mygesture.MAS.h()) - (pointA.x / 2);
            iJ = (i2 - (pointA.y / 2)) - me.hisn.mygesture.MAS.j();
            i4 = 8388659;
            i5 = me.hisn.mygesture.R.style.four_in_one_anim_left;
        }
        return new me.hisn.utils.v().a(false, i4, -2, -2, iH, iJ, i5, false);
    }

    private void a(int i) {
        if (i < 0) {
            return;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.f875a.findViewById(me.hisn.mygesture.R.id.tri_in_one_parent);
        int i2 = h[i];
        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
            android.view.View childAt = viewGroup.getChildAt(i3);
            childAt.setAlpha(childAt.getId() == i2 ? 1.0f : 0.3f);
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x005e  */
    /* JADX WARN: Code duplicated, block: B:16:0x0075  */
    /* JADX WARN: Code duplicated, block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:14:0x005e, please report this as an issue */
    private void a(int i, int i2) {
        java.lang.StringBuilder sb;
        java.lang.String str;
        java.lang.String string;
        int iA;
        if (i < 0) {
            return;
        }
        int[] iArr = new int[2];
        this.f875a.getLocationInWindow(iArr);
        iArr[0] = iArr[0] + (this.f875a.getWidth() / 2);
        iArr[1] = iArr[1] + (this.f875a.getHeight() / 2);
        if (i == 0) {
            sb = new java.lang.StringBuilder();
            sb.append(this.f877c);
            str = "31427";
        } else {
            if (i != 1) {
                if (i != 2) {
                    string = null;
                } else {
                    sb = new java.lang.StringBuilder();
                    sb.append(this.f877c);
                    str = "31429";
                }
                if (string != null) {
                    iA = me.hisn.utils.k0.a(string + "_k", 0);
                    if (iA > 0) {
                        me.hisn.utils.s0.a(this.f876b, string, this.f875a, new me.hisn.utils.z0().a(iA, i2, this.f875a), me.hisn.mygesture.P.s, i2, iArr);
                    }
                }
            }
            sb = new java.lang.StringBuilder();
            sb.append(this.f877c);
            str = "31428";
        }
        sb.append(str);
        string = sb.toString();
        if (string != null) {
            iA = me.hisn.utils.k0.a(string + "_k", 0);
            if (iA > 0) {
                me.hisn.utils.s0.a(this.f876b, string, this.f875a, new me.hisn.utils.z0().a(iA, i2, this.f875a), me.hisn.mygesture.P.s, i2, iArr);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:4:0x0035  */
    private void a(android.view.View view, int i) {
        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) view.findViewById(me.hisn.mygesture.R.id.tri_in_one_parent);
        android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) this.e.getLayoutParams();
        android.widget.LinearLayout.LayoutParams layoutParams2 = (android.widget.LinearLayout.LayoutParams) this.f.getLayoutParams();
        android.widget.LinearLayout.LayoutParams layoutParams3 = (android.widget.LinearLayout.LayoutParams) this.g.getLayoutParams();
        int iA = new me.hisn.utils.l().a(view.getContext(), 30.0f);
        if (i == me.hisn.mygesture.P.L) {
            linearLayout.setOrientation(1);
            layoutParams2.leftMargin = iA;
        } else if (i == me.hisn.mygesture.P.R) {
            linearLayout.setOrientation(1);
            layoutParams.leftMargin = iA;
            layoutParams3.leftMargin = iA;
        } else if (i == me.hisn.mygesture.P.B) {
            linearLayout.setOrientation(0);
            layoutParams.topMargin = iA;
            layoutParams3.topMargin = iA;
        } else {
            linearLayout.setOrientation(1);
            layoutParams2.leftMargin = iA;
        }
        this.e.setLayoutParams(layoutParams);
        this.f.setLayoutParams(layoutParams2);
        this.g.setLayoutParams(layoutParams3);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0069  */
    private int[] a(float f, float f2) {
        float f3 = this.d == me.hisn.mygesture.P.B ? 1.5f : 1.0f;
        float f4 = this.d == me.hisn.mygesture.P.B ? 1.0f : 1.5f;
        int i = 3;
        int i2 = -1;
        if (java.lang.Math.abs(f) > me.hisn.mygesture.P.n0 / 2.0f || java.lang.Math.abs(f2) > me.hisn.mygesture.P.n0 / 2.0f) {
            if (java.lang.Math.abs(f2) * f3 > java.lang.Math.abs(f) * f4) {
                if (f2 > 0.0f) {
                    i2 = this.d != me.hisn.mygesture.P.B ? 2 : -1;
                    i = 4;
                } else {
                    i2 = this.d == me.hisn.mygesture.P.B ? 1 : 0;
                }
            } else if (f > 0.0f) {
                int i3 = this.d;
                if (i3 == me.hisn.mygesture.P.L) {
                    i2 = 1;
                } else if (i3 != me.hisn.mygesture.P.R) {
                    if (i3 == me.hisn.mygesture.P.B) {
                        i2 = 2;
                    } else {
                        i2 = 1;
                    }
                }
                i = 1;
            } else {
                int i4 = this.d;
                if (i4 != me.hisn.mygesture.P.L) {
                    if (i4 == me.hisn.mygesture.P.R) {
                        i2 = 1;
                    } else if (i4 == me.hisn.mygesture.P.B) {
                        i2 = 0;
                    }
                }
                i = 2;
            }
        }
        return new int[]{i2, i};
    }

    private java.lang.String b(android.content.Context context, java.lang.String str) {
        java.lang.String strA = me.hisn.utils.k0.a(str + "_l");
        int iA = me.hisn.utils.k0.a(str + "_k", 0);
        java.lang.String strA2 = me.hisn.utils.k0.a(str + "_p");
        java.lang.String strA3 = me.hisn.utils.k0.a(str + "_c");
        java.lang.String strA4 = me.hisn.utils.k0.a(str + "_s");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append((strA + iA + strA2 + strA3 + strA4).hashCode());
        sb.append("");
        return context.getExternalFilesDir(null) + "/action_icons/" + sb.toString() + ".png";
    }

    private void b(android.view.View view) {
        this.e = (android.widget.ImageView) view.findViewById(me.hisn.mygesture.R.id.tri_in_one_item_1);
        this.f = (android.widget.ImageView) view.findViewById(me.hisn.mygesture.R.id.tri_in_one_item_2);
        this.g = (android.widget.ImageView) view.findViewById(me.hisn.mygesture.R.id.tri_in_one_item_3);
        this.e.setImageDrawable(a(this.f876b, this.f877c + "31427"));
        this.f.setImageDrawable(a(this.f876b, this.f877c + "31428"));
        this.g.setImageDrawable(a(this.f876b, this.f877c + "31429"));
    }

    private android.graphics.drawable.Drawable c(android.content.Context context, java.lang.String str) {
        java.io.File file = new java.io.File(str);
        if (file.exists()) {
            return new android.graphics.drawable.BitmapDrawable(context.getResources(), android.graphics.BitmapFactory.decodeFile(file.getPath()));
        }
        return null;
    }

    public void a(int i, int i2, int i3, int i4) {
        android.view.View viewInflate = android.view.View.inflate(this.f876b, me.hisn.mygesture.R.layout.three_in_one, null);
        this.f875a = viewInflate;
        b(viewInflate);
        a(this.f875a, this.d);
        try {
            me.hisn.mygesture.MAS.k().addView(this.f875a, a(i, i2, this.d));
            me.hisn.mygesture.MAS.a(this);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void a(int i, int i2, int i3, int i4, long j) {
        a(a(i3 - i, i4 - i2)[0]);
    }

    public void a(int i, int i2, int i3, int i4, boolean z) {
        if (this.f875a != null) {
            if (!z) {
                int[] iArrA = a(i3 - i, i4 - i2);
                a(iArrA[0], iArrA[1]);
            }
            try {
                me.hisn.mygesture.MAS.k().removeView(this.f875a);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // me.hisn.mygesture.a.h
    public void a(android.view.View view) {
        me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        a(0, 0, 0, 0, true);
    }

    @Override // me.hisn.mygesture.a.h
    public void a(android.view.View view, int i, int i2, int i3, int i4) {
        a(i3, i4, i, i2, 0L);
    }

    @Override // me.hisn.mygesture.a.h
    public void b(android.view.View view, int i, int i2, int i3, int i4) {
        me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        a(i3, i4, i, i2, false);
    }
}
