package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class q0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.view.View f828a;

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f829a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f830b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f831c;

        /* JADX INFO: renamed from: me.hisn.utils.q0$a$a, reason: collision with other inner class name */
        class RunnableC0039a implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.WindowManager.LayoutParams f832a;

            RunnableC0039a(android.view.WindowManager.LayoutParams layoutParams) {
                this.f832a = layoutParams;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (me.hisn.mygesture.MAS.k() != null) {
                    me.hisn.mygesture.MAS.k().addView(me.hisn.utils.q0.this.f828a, this.f832a);
                }
            }
        }

        a(android.view.View view, int i, int i2) {
            this.f829a = view;
            this.f830b = i;
            this.f831c = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.utils.q0 q0Var = me.hisn.utils.q0.this;
            q0Var.f828a = q0Var.a(this.f829a.getContext().getApplicationContext(), this.f830b);
            this.f829a.post(new me.hisn.utils.q0.a.RunnableC0039a(me.hisn.utils.q0.this.d(this.f831c)));
        }
    }

    class b implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f834a;

        b(android.content.Context context) {
            this.f834a = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (view.getTag() != null) {
                java.lang.String str = (java.lang.String) view.getTag();
                me.hisn.utils.s sVar = new me.hisn.utils.s();
                android.content.Context context = this.f834a;
                sVar.a(context, me.hisn.utils.t.a(context, str), null, str);
            }
            me.hisn.utils.q0.this.a();
        }
    }

    private static int a(int i) {
        if (i == 1) {
            return me.hisn.mygesture.R.style.switcher_anim_left;
        }
        if (i == 2) {
            return me.hisn.mygesture.R.style.switcher_anim_right;
        }
        if (i == 3) {
            return me.hisn.mygesture.R.style.switcher_anim_up;
        }
        if (i != 4) {
            return 0;
        }
        return me.hisn.mygesture.R.style.switcher_anim_down;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.View a(android.content.Context context, int i) {
        int iMin = java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) / 8;
        int i2 = iMin / 4;
        android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(context);
        android.widget.GridLayout gridLayout = new android.widget.GridLayout(context);
        linearLayout.addView(gridLayout);
        linearLayout.setGravity(b(i));
        android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) gridLayout.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.setMargins(i2, i2, i2, i2);
        gridLayout.setLayoutParams(layoutParams);
        gridLayout.setBackgroundResource(me.hisn.utils.u0.a(context) ? me.hisn.mygesture.R.drawable.round_shape_trans_dark : me.hisn.mygesture.R.drawable.round_shape_trans);
        java.util.ArrayList<me.hisn.mygesture.h> arrayListA = me.hisn.utils.l0.a();
        int iC = c(i);
        gridLayout.setOrientation(iC);
        if (iC == 0) {
            gridLayout.setColumnCount(4);
        } else {
            gridLayout.setRowCount(4);
        }
        android.view.View.OnClickListener bVar = new me.hisn.utils.q0.b(context);
        linearLayout.setOnClickListener(bVar);
        gridLayout.setPadding(i2, i2, i2, i2);
        gridLayout.setElevation(10.0f);
        if (arrayListA != null) {
            if (me.hisn.mygesture.P.h0) {
                for (int size = arrayListA.size() - 1; size >= 0; size--) {
                    gridLayout.addView(a(context, iMin, i2, arrayListA.get(size), bVar));
                }
            } else {
                java.util.Iterator<me.hisn.mygesture.h> it = arrayListA.iterator();
                while (it.hasNext()) {
                    gridLayout.addView(a(context, iMin, i2, it.next(), bVar));
                }
            }
        }
        return linearLayout;
    }

    private android.view.View a(android.content.Context context, int i, int i2, me.hisn.mygesture.h hVar, android.view.View.OnClickListener onClickListener) {
        android.widget.ImageView imageView = new android.widget.ImageView(context);
        imageView.setImageDrawable(hVar.f589c);
        android.widget.GridLayout.LayoutParams layoutParams = new android.widget.GridLayout.LayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        layoutParams.setMargins(i2, i2, i2, i2);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundResource(me.hisn.mygesture.R.drawable.wave_bkg);
        imageView.setTag(hVar.f587a);
        imageView.setOnClickListener(onClickListener);
        return imageView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (me.hisn.mygesture.MAS.k() != null) {
            me.hisn.mygesture.MAS.k().removeView(this.f828a);
        }
    }

    private int b(int i) {
        if (i == me.hisn.mygesture.P.L) {
            return 8388627;
        }
        if (i == me.hisn.mygesture.P.R) {
            return 8388629;
        }
        return i == me.hisn.mygesture.P.B ? 81 : 17;
    }

    private int c(int i) {
        return (i == me.hisn.mygesture.P.L || i == me.hisn.mygesture.P.R) ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.WindowManager.LayoutParams d(int i) {
        return new me.hisn.utils.v().a(true, 17, -1, -1, 0, 0, a(i), false);
    }

    public void a(int i, android.view.View view, int i2) {
        new java.lang.Thread(new me.hisn.utils.q0.a(view, i2, i)).start();
    }
}
