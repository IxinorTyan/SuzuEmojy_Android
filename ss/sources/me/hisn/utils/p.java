package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class p {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.view.View f808a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f809b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.content.Context f810c;
    private android.view.View d;
    private android.view.View e;
    private android.view.View f;
    private android.view.View g;
    private int[] h;
    private int i = 0;
    private int j;

    class a implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f811a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f812b;

        a(int i, int i2) {
            this.f811a = i;
            this.f812b = i2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.utils.p.this.i = view.getId();
            me.hisn.utils.p pVar = me.hisn.utils.p.this;
            int i = this.f811a;
            int i2 = this.f812b;
            pVar.a(i, i2, i, i2, true);
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        }
    }

    class b implements android.view.View.OnLongClickListener {
        b(me.hisn.utils.p pVar) {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            return true;
        }
    }

    private int a(int i, int i2) {
        int[] iArr = this.h;
        int i3 = i - iArr[0];
        int i4 = i2 - iArr[1];
        if (java.lang.Math.abs(i3) <= this.j / 6 && java.lang.Math.abs(i4) <= this.j / 6) {
            return 0;
        }
        if (java.lang.Math.abs(i3) * 1.2f > java.lang.Math.abs(i4)) {
            return i3 > 0 ? me.hisn.mygesture.R.id.back_btn : me.hisn.mygesture.R.id.prev_btn;
        }
        return i4 < 0 ? me.hisn.mygesture.R.id.recent_btn : me.hisn.mygesture.R.id.home_btn;
    }

    private android.view.View b(android.content.Context context, int i, int i2, int i3) {
        android.view.View viewInflate = android.view.View.inflate(context, me.hisn.mygesture.R.layout.four_in_one, null);
        int[] iArrC = c(context, i, i2, i3);
        viewInflate.setLayoutParams(new me.hisn.utils.v().a(me.hisn.mygesture.P.D, 8388659, -2, -2, iArrC[0] - me.hisn.mygesture.MAS.h(), iArrC[1] - me.hisn.mygesture.MAS.j(), me.hisn.mygesture.R.style.four_in_one_anim_left, false));
        return viewInflate;
    }

    private int[] c(android.content.Context context, int i, int i2, int i3) {
        int[] iArr = new int[2];
        int i4 = this.j;
        int i5 = i4 / 30;
        if (i3 == 1) {
            iArr[0] = i + i5;
            int i6 = (i4 / 2) + i2;
            int i7 = me.hisn.mygesture.P.l0;
            iArr[1] = i6 > i7 ? i7 - i4 : i2 - (i4 / 2);
        } else if (i3 == 2) {
            iArr[0] = (i - i4) - i5;
            int i8 = (i4 / 2) + i2;
            int i9 = me.hisn.mygesture.P.l0;
            iArr[1] = i8 > i9 ? i9 - i4 : i2 - (i4 / 2);
        } else if (i3 == 3) {
            if (i - (i4 / 2) < 0) {
                iArr[0] = i5;
            } else {
                int i10 = (i4 / 2) + i;
                int i11 = me.hisn.mygesture.P.k0;
                if (i10 > i11) {
                    iArr[0] = i11 - i4;
                } else {
                    iArr[0] = i - (i4 / 2);
                }
            }
            iArr[1] = (i2 - this.j) - i5;
        } else if (i3 == 4) {
            if (i - (i4 / 2) < 0) {
                iArr[0] = i5;
            } else {
                int i12 = (i4 / 2) + i;
                int i13 = me.hisn.mygesture.P.k0;
                if (i12 > i13) {
                    iArr[0] = i13 - i4;
                } else {
                    iArr[0] = i - (i4 / 2);
                }
            }
            int i14 = this.j;
            int i15 = i2 + i14;
            int i16 = me.hisn.mygesture.P.l0;
            if (i15 > i16) {
                iArr[1] = (i16 - i14) - i5;
            } else {
                iArr[1] = i2;
            }
        }
        this.h = new int[]{i + (i / 2), iArr[1] + (i / 2)};
        int i17 = iArr[0];
        int i18 = this.j;
        return iArr;
    }

    public void a(int i, int i2, int i3, int i4) {
        int iA = a(i, i2);
        int i5 = this.i;
        if (iA == i5 || iA <= 0) {
            return;
        }
        if (i5 > 0) {
            android.view.View viewFindViewById = this.f808a.findViewById(i5);
            viewFindViewById.setScaleY(1.0f);
            viewFindViewById.setScaleX(1.0f);
            viewFindViewById.setAlpha(0.5f);
        }
        this.i = iA;
        android.view.View viewFindViewById2 = this.f808a.findViewById(iA);
        viewFindViewById2.setScaleX(1.25f);
        viewFindViewById2.setScaleY(1.25f);
        viewFindViewById2.setAlpha(1.0f);
        me.hisn.mygesture.MAS.a(this.f808a, me.hisn.mygesture.P.g);
    }

    public void a(int i, int i2, int i3, int i4, boolean z) {
        if (z) {
            int[] iArr = {i3, i4};
            switch (this.i) {
                case me.hisn.mygesture.R.id.back_btn /* 2131230779 */:
                    me.hisn.utils.s0.a(this.f810c, 1, this.f808a, (android.os.Bundle) null, this.f809b, iArr, (java.lang.String) null);
                    break;
                case me.hisn.mygesture.R.id.home_btn /* 2131230890 */:
                    me.hisn.utils.s0.a(this.f810c, 2, this.f808a, new me.hisn.utils.z0().a(2, 4, this.e), 4, iArr, (java.lang.String) null);
                    break;
                case me.hisn.mygesture.R.id.prev_btn /* 2131230980 */:
                    if (this.g.getTag() != null) {
                        java.lang.String str = this.g.getTag() + "";
                        new me.hisn.utils.s().a(this.f.getContext().getApplicationContext(), me.hisn.utils.t.a(this.f.getContext().getApplicationContext(), str), null, str);
                    }
                    break;
                case me.hisn.mygesture.R.id.recent_btn /* 2131230986 */:
                    me.hisn.utils.s0.a(this.f810c, 3, me.hisn.mygesture.MAS.d(), (android.os.Bundle) null, 3, iArr, (java.lang.String) null);
                    break;
            }
        }
        if (me.hisn.mygesture.MAS.k() != null) {
            try {
                me.hisn.mygesture.MAS.k().removeView(this.f808a);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void a(android.content.Context context, int i, int i2, int i3) {
        this.f809b = i3;
        this.f810c = context;
        this.j = (int) context.getResources().getDimension(me.hisn.mygesture.R.dimen.four_in_one_height);
        android.view.View viewB = b(context, i, i2, i3);
        this.f808a = viewB;
        this.d = viewB.findViewById(me.hisn.mygesture.R.id.back_btn);
        this.e = this.f808a.findViewById(me.hisn.mygesture.R.id.home_btn);
        this.f = this.f808a.findViewById(me.hisn.mygesture.R.id.recent_btn);
        this.g = this.f808a.findViewById(me.hisn.mygesture.R.id.prev_btn);
        if (me.hisn.mygesture.P.D) {
            me.hisn.utils.p.a aVar = new me.hisn.utils.p.a(i, i2);
            this.d.setOnClickListener(aVar);
            this.e.setOnClickListener(aVar);
            this.f.setOnClickListener(aVar);
            this.g.setOnClickListener(aVar);
        }
        if (me.hisn.mygesture.P.g == 1) {
            this.f808a.setOnLongClickListener(new me.hisn.utils.p.b(this));
            this.f808a.setLongClickable(false);
        }
        me.hisn.mygesture.h hVarA = me.hisn.utils.l0.a(me.hisn.mygesture.MAS.e());
        if (hVarA != null) {
            android.graphics.drawable.Drawable drawableA = hVarA.f589c;
            if (!me.hisn.mygesture.P.i0) {
                me.hisn.utils.x xVar = new me.hisn.utils.x();
                int intrinsicWidth = drawableA.getIntrinsicWidth();
                xVar.a(context, 0, intrinsicWidth, intrinsicWidth);
                drawableA = new me.hisn.utils.x().a(context, drawableA, -1);
            }
            ((android.widget.ImageView) this.g).setImageDrawable(drawableA);
            this.g.setTag(hVarA.f587a);
        }
        if (me.hisn.mygesture.MAS.k() != null) {
            try {
                me.hisn.mygesture.MAS.k().addView(this.f808a, this.f808a.getLayoutParams());
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }
}
