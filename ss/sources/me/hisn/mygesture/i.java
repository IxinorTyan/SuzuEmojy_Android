package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private androidx.viewpager.widget.ViewPager f590a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.view.View f591b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private java.util.List<me.hisn.mygesture.h> f592c;
    private int d = java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) / 2;

    class a implements androidx.viewpager.widget.ViewPager.k {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f593a;

        a(me.hisn.mygesture.i iVar, int i) {
            this.f593a = i;
        }

        @Override // androidx.viewpager.widget.ViewPager.k
        public void a(android.view.View view, float f) {
            float f2 = me.hisn.mygesture.P.d ? 3 : 4;
            float fAbs = (f2 - java.lang.Math.abs(f)) / f2;
            view.setScaleX(fAbs);
            view.setScaleY(fAbs);
            view.setAlpha(fAbs);
            float f3 = f2 * 2.0f;
            view.setTranslationX(((this.f593a / (-f3)) * f * java.lang.Math.abs(f)) + ((f * this.f593a) / f3));
        }
    }

    class b implements android.view.View.OnLongClickListener {
        b(me.hisn.mygesture.i iVar) {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            return true;
        }
    }

    private static class c extends androidx.viewpager.widget.a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        java.util.List<android.view.View> f594a;

        c(java.util.List<android.view.View> list) {
            this.f594a = list;
        }

        @Override // androidx.viewpager.widget.a
        public int a() {
            return this.f594a.size();
        }

        @Override // androidx.viewpager.widget.a
        public java.lang.Object a(android.view.ViewGroup viewGroup, int i) {
            viewGroup.addView(this.f594a.get(i));
            return this.f594a.get(i);
        }

        @Override // androidx.viewpager.widget.a
        public void a(android.view.ViewGroup viewGroup, int i, java.lang.Object obj) {
            viewGroup.removeView(this.f594a.get(i));
        }

        @Override // androidx.viewpager.widget.a
        public boolean a(android.view.View view, java.lang.Object obj) {
            return view == obj;
        }
    }

    private int a() {
        return this.f592c.size();
    }

    private int a(int i, int i2) {
        int i3 = me.hisn.mygesture.P.k0;
        int i4 = me.hisn.mygesture.P.l0;
        if (i3 <= i4) {
            i4 = me.hisn.mygesture.P.k0;
        } else if (i >= i4 / 2) {
            int i5 = me.hisn.mygesture.P.k0;
            int i6 = me.hisn.mygesture.P.l0;
            i2 -= i > i5 - (i6 / 2) ? me.hisn.mygesture.P.k0 - me.hisn.mygesture.P.l0 : i - (i6 / 2);
        }
        int iA = (i2 / (i4 / (a() + 4))) - 2;
        if (iA < 0) {
            return 0;
        }
        return iA > a() + (-1) ? a() - 1 : iA;
    }

    private android.view.View a(android.content.Context context, me.hisn.mygesture.h hVar, int i) {
        if (me.hisn.mygesture.P.d) {
            android.widget.ImageView imageView = new android.widget.ImageView(context);
            imageView.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
            imageView.setImageDrawable(hVar.f589c);
            return imageView;
        }
        android.view.View viewInflate = android.view.View.inflate(context, me.hisn.mygesture.R.layout.recent_item, null);
        android.widget.ImageView imageView2 = (android.widget.ImageView) viewInflate.findViewById(me.hisn.mygesture.R.id.app_icon);
        android.widget.TextView textView = (android.widget.TextView) viewInflate.findViewById(me.hisn.mygesture.R.id.app_label);
        androidx.constraintlayout.widget.ConstraintLayout constraintLayout = (androidx.constraintlayout.widget.ConstraintLayout) viewInflate.findViewById(me.hisn.mygesture.R.id.app_item_layout);
        android.graphics.drawable.Drawable drawable = context.getDrawable(me.hisn.mygesture.R.drawable.recent_item_bkg);
        if (drawable != null) {
            drawable.setColorFilter(hVar.d, android.graphics.PorterDuff.Mode.SRC);
        }
        constraintLayout.setBackground(drawable);
        imageView2.setImageDrawable(hVar.f589c);
        textView.setText(hVar.f588b);
        textView.setTextColor(hVar.e);
        textView.setTextSize(0, i / 7.5f);
        return viewInflate;
    }

    private void a(int i, boolean z) {
        if (this.f590a.getCurrentItem() != i) {
            this.f590a.a(i, z);
            if (z && this.f590a.getCurrentItem() == i) {
                me.hisn.mygesture.MAS.a(this.f590a, me.hisn.mygesture.P.g);
            }
        }
    }

    private int b(int i, int i2) {
        int i3 = me.hisn.mygesture.P.k0;
        int i4 = me.hisn.mygesture.P.l0;
        if (i3 < i4) {
            i4 = me.hisn.mygesture.P.k0;
            int i5 = me.hisn.mygesture.P.l0;
            if (i >= i5 / 2) {
                int i6 = me.hisn.mygesture.P.k0;
                i2 -= i > i5 - (i6 / 2) ? me.hisn.mygesture.P.l0 - me.hisn.mygesture.P.k0 : i - (i6 / 2);
            }
        }
        int iA = (i2 / (i4 / (a() + 4))) - 2;
        if (iA < 0) {
            return 0;
        }
        return iA > a() + (-1) ? a() - 1 : iA;
    }

    private void b() {
        java.lang.String str = this.f592c.get(me.hisn.mygesture.P.h0 ? (this.f592c.size() - 1) - this.f590a.getCurrentItem() : this.f590a.getCurrentItem()).f587a;
        if (str == null || str.equals(me.hisn.mygesture.MAS.e())) {
            return;
        }
        if (!"41422".equals(str)) {
            new me.hisn.utils.s().a(this.f590a.getContext().getApplicationContext(), me.hisn.utils.t.a(this.f590a.getContext().getApplicationContext(), str), new me.hisn.utils.z0().a(this.f590a), str);
            return;
        }
        this.f590a.setTag(java.lang.Integer.valueOf(me.hisn.mygesture.P.B));
        int[] iArr = new int[2];
        this.f590a.getLocationOnScreen(iArr);
        iArr[0] = iArr[0] + (this.f590a.getWidth() / 2);
        iArr[1] = iArr[1] + (this.f590a.getHeight() / 2);
        me.hisn.utils.s0.a(this.f590a.getContext(), 2, this.f590a, new me.hisn.utils.z0().a(this.f590a.getContext().getApplicationContext(), me.hisn.mygesture.R.anim.windows_in, me.hisn.mygesture.R.anim.windows_out), 0, iArr, (java.lang.String) null);
    }

    private void b(android.content.Context context) {
        if (context != null) {
            this.f592c = new java.util.ArrayList();
            me.hisn.mygesture.h hVar = new me.hisn.mygesture.h(context.getDrawable(me.hisn.mygesture.R.drawable.home_dark), "41422");
            hVar.f588b = context.getString(me.hisn.mygesture.R.string.home_text);
            hVar.e = context.getResources().getColor(me.hisn.mygesture.R.color.white);
            hVar.d = context.getResources().getColor(me.hisn.mygesture.R.color.dark_item);
            this.f592c.add(hVar);
            java.util.ArrayList<me.hisn.mygesture.h> arrayListA = me.hisn.utils.l0.a();
            if (arrayListA != null && arrayListA.size() > 0) {
                this.f592c.addAll(arrayListA);
            }
            java.util.List<me.hisn.mygesture.h> list = this.f592c;
            if (list == null || list.size() == 0) {
                new me.hisn.utils.b0().a(context, me.hisn.mygesture.R.string.no_recent_apps, 0);
                return;
            }
            android.view.View viewInflate = android.view.View.inflate(context, me.hisn.mygesture.R.layout.recent_page_view, null);
            this.f591b = viewInflate;
            this.f590a = (androidx.viewpager.widget.ViewPager) viewInflate.findViewById(me.hisn.mygesture.R.id.recent_view_pager);
            int iMin = java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) / 4;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (me.hisn.mygesture.P.h0) {
                for (int size = this.f592c.size() - 1; size >= 0; size--) {
                    arrayList.add(a(context, this.f592c.get(size), iMin));
                }
            } else {
                java.util.Iterator<me.hisn.mygesture.h> it = this.f592c.iterator();
                while (it.hasNext()) {
                    arrayList.add(a(context, it.next(), iMin));
                }
            }
            this.f590a.setAdapter(new me.hisn.mygesture.i.c(arrayList));
            this.f590a.setOffscreenPageLimit(3);
            this.f590a.a(false, (androidx.viewpager.widget.ViewPager.k) new me.hisn.mygesture.i.a(this, iMin));
            if (me.hisn.mygesture.P.g == 1) {
                this.f590a.setOnLongClickListener(new me.hisn.mygesture.i.b(this));
                this.f590a.setLongClickable(false);
            }
            android.view.WindowManager.LayoutParams layoutParamsA = new me.hisn.utils.v().a(false, 17, java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0), -2, 0, 0, me.hisn.mygesture.R.style.recent_anim, false);
            layoutParamsA.flags |= 2;
            layoutParamsA.dimAmount = 0.7f;
            this.f591b.setLayoutParams(layoutParamsA);
        }
    }

    public void a(int i, int i2, int i3, int i4, int i5) {
        boolean z = i5 == 3 || i5 == 4;
        if (java.lang.Math.abs(z ? i - i3 : i2 - i4) > this.d) {
            this.f591b.setAlpha(0.5f);
        } else {
            this.f591b.setAlpha(1.0f);
            a(z ? b(i4, i2) : a(i3, i), true);
        }
        if (me.hisn.mygesture.P.C == null) {
            me.hisn.mygesture.m.q = new me.hisn.mypanel.c().a();
        }
    }

    public void a(boolean z) {
        if (me.hisn.mygesture.MAS.k() != null) {
            try {
                me.hisn.mygesture.MAS.k().removeView(this.f591b);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        if (z && this.f591b.getAlpha() == 1.0f) {
            b();
        }
    }

    public boolean a(android.content.Context context) {
        b(context);
        try {
            if (me.hisn.mygesture.MAS.k() == null) {
                return false;
            }
            me.hisn.mygesture.MAS.k().addView(this.f591b, this.f591b.getLayoutParams());
            return true;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
