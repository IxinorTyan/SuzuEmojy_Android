package me.hisn.appdrawer;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    public static java.util.List<me.hisn.appdrawer.b> k;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f446a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f447b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private boolean f448c;
    private boolean d;
    private int e;
    private java.lang.String f;
    private int g;
    private android.view.View h;
    private int i;
    private boolean j;

    /* JADX INFO: renamed from: me.hisn.appdrawer.a$a, reason: collision with other inner class name */
    class RunnableC0017a implements java.lang.Runnable {
        RunnableC0017a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.appdrawer.a aVar = me.hisn.appdrawer.a.this;
            aVar.f446a = aVar.a();
            me.hisn.mygesture.P.s.edit().putString("drawer_apps", me.hisn.appdrawer.a.this.f446a).apply();
        }
    }

    class b implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f450a;

        b(android.view.View view) {
            this.f450a = view;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.appdrawer.a.this.b(this.f450a);
        }
    }

    class c implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.RecyclerView f452a;

        c(me.hisn.appdrawer.a aVar, androidx.recyclerview.widget.RecyclerView recyclerView) {
            this.f452a = recyclerView;
        }

        @Override // java.lang.Runnable
        public void run() {
            int height = this.f452a.getHeight();
            int iMax = (int) (java.lang.Math.max(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) * 0.618f);
            if (height > iMax) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) this.f452a.getLayoutParams();
                layoutParams.height = iMax;
                this.f452a.setLayoutParams(layoutParams);
            }
        }
    }

    class d implements android.view.View.OnAttachStateChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ androidx.recyclerview.widget.RecyclerView f453a;

        /* JADX INFO: renamed from: me.hisn.appdrawer.a$d$a, reason: collision with other inner class name */
        class RunnableC0018a implements java.lang.Runnable {
            RunnableC0018a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.appdrawer.a.d dVar = me.hisn.appdrawer.a.d.this;
                android.view.animation.Animation animationA = me.hisn.appdrawer.a.this.a(dVar.f453a.getContext(), me.hisn.appdrawer.a.this.i, true, true);
                animationA.setDuration(300L);
                animationA.setFillAfter(true);
                me.hisn.appdrawer.a.d.this.f453a.startAnimation(animationA);
                me.hisn.appdrawer.a.d.this.f453a.setAlpha(1.0f);
                if (me.hisn.mygesture.P.s.getBoolean("d31423", false)) {
                    return;
                }
                new androidx.recyclerview.widget.f(me.hisn.appdrawer.a.this.new f()).a(me.hisn.appdrawer.a.d.this.f453a);
            }
        }

        d(androidx.recyclerview.widget.RecyclerView recyclerView) {
            this.f453a = recyclerView;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
            view.post(new me.hisn.appdrawer.a.d.RunnableC0018a());
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
        }
    }

    private class e extends androidx.recyclerview.widget.RecyclerView.g {

        /* JADX INFO: renamed from: me.hisn.appdrawer.a$e$a, reason: collision with other inner class name */
        class ViewOnClickListenerC0019a implements android.view.View.OnClickListener {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ me.hisn.appdrawer.a.e.d f457a;

            ViewOnClickListenerC0019a(me.hisn.appdrawer.a.e.d dVar) {
                this.f457a = dVar;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                me.hisn.appdrawer.b bVar = me.hisn.appdrawer.a.k.get(this.f457a.f());
                if (bVar.f462a != null) {
                    android.content.ComponentName componentName = new android.content.ComponentName(bVar.f462a, bVar.f463b);
                    android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
                    intent.addCategory("android.intent.category.LAUNCHER");
                    intent.setComponent(componentName);
                    new me.hisn.utils.s().a(view.getContext().getApplicationContext(), intent, new me.hisn.utils.z0().a(view), bVar.f462a);
                }
                me.hisn.appdrawer.a aVar = me.hisn.appdrawer.a.this;
                aVar.b(aVar.h);
            }
        }

        class b implements android.view.View.OnLongClickListener {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ me.hisn.appdrawer.a.e.d f459a;

            b(me.hisn.appdrawer.a.e.d dVar) {
                this.f459a = dVar;
            }

            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(android.view.View view) {
                me.hisn.appdrawer.b bVar = me.hisn.appdrawer.a.k.get(this.f459a.f());
                if (bVar.f462a != null) {
                    new me.hisn.utils.y0().a(view.getContext(), bVar.f462a, false);
                }
                me.hisn.appdrawer.a aVar = me.hisn.appdrawer.a.this;
                aVar.b(aVar.h);
                return true;
            }
        }

        class c implements android.view.View.OnClickListener {
            c() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                me.hisn.appdrawer.a aVar = me.hisn.appdrawer.a.this;
                aVar.b(aVar.h);
            }
        }

        private class d extends androidx.recyclerview.widget.RecyclerView.d0 {
            android.widget.ImageView t;
            android.widget.TextView u;
            android.widget.LinearLayout v;

            public d(me.hisn.appdrawer.a.e eVar, android.view.View view, int i) {
                super(view);
                this.v = (android.widget.LinearLayout) view.findViewById(me.hisn.mygesture.R.id.app_item_layout);
                if (i != 0) {
                    this.t = (android.widget.ImageView) view.findViewById(me.hisn.mygesture.R.id.app_icon);
                    this.u = (android.widget.TextView) view.findViewById(me.hisn.mygesture.R.id.app_label);
                    if (me.hisn.appdrawer.a.this.f447b) {
                        return;
                    }
                    this.u.setVisibility(8);
                }
            }
        }

        public e() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.g
        public int a() {
            return me.hisn.appdrawer.a.k.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.g
        public int b(int i) {
            return 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.g
        public androidx.recyclerview.widget.RecyclerView.d0 b(android.view.ViewGroup viewGroup, int i) {
            me.hisn.appdrawer.a.e.d dVar = new me.hisn.appdrawer.a.e.d(this, android.view.LayoutInflater.from(viewGroup.getContext()).inflate(i == 0 ? me.hisn.mygesture.R.layout.drawer_header : me.hisn.mygesture.R.layout.drawer_app_item, viewGroup, false), i);
            android.widget.LinearLayout linearLayout = dVar.v;
            if (i != 0) {
                linearLayout.setOnClickListener(new me.hisn.appdrawer.a.e.ViewOnClickListenerC0019a(dVar));
                if (me.hisn.mygesture.P.s.getBoolean("d31423", false)) {
                    dVar.v.setOnLongClickListener(new me.hisn.appdrawer.a.e.b(dVar));
                }
            } else {
                linearLayout.setOnClickListener(new me.hisn.appdrawer.a.e.c());
            }
            return dVar;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.g
        public void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i) {
            android.widget.TextView textView;
            int i2;
            float f;
            if (b(i) != 0) {
                me.hisn.appdrawer.b bVar = me.hisn.appdrawer.a.k.get(i);
                me.hisn.appdrawer.a.e.d dVar = (me.hisn.appdrawer.a.e.d) d0Var;
                if (me.hisn.appdrawer.a.this.f447b) {
                    dVar.u.setText(bVar.f464c);
                    if (me.hisn.appdrawer.a.this.e == 1) {
                        dVar.u.setTextColor(-7829368);
                        textView = dVar.u;
                        i2 = 0;
                        f = 0.0f;
                    } else {
                        dVar.u.setTextColor(-1);
                        textView = dVar.u;
                        i2 = -16777216;
                        f = 1.0f;
                    }
                    textView.setShadowLayer(f, f, f, i2);
                }
                if (me.hisn.mygesture.P.C == null) {
                    new me.hisn.mypanel.c().a();
                }
                dVar.t.setImageDrawable(bVar.d);
            }
        }
    }

    private class f extends androidx.recyclerview.widget.f.AbstractC0015f {
        public f() {
        }

        private void e(int i, int i2) {
            if (i >= i2) {
                while (i > i2) {
                    java.util.Collections.swap(me.hisn.appdrawer.a.k, i, i - 1);
                    i--;
                }
            } else {
                while (i < i2) {
                    int i3 = i + 1;
                    java.util.Collections.swap(me.hisn.appdrawer.a.k, i, i3);
                    i = i3;
                }
            }
        }

        @Override // androidx.recyclerview.widget.f.AbstractC0015f
        public void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i) {
            super.a(d0Var, i);
        }

        @Override // androidx.recyclerview.widget.f.AbstractC0015f
        public void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i) {
        }

        @Override // androidx.recyclerview.widget.f.AbstractC0015f
        public boolean b() {
            return false;
        }

        @Override // androidx.recyclerview.widget.f.AbstractC0015f
        public boolean b(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.d0 d0Var2) {
            int iF = d0Var.f();
            int iF2 = d0Var2.f();
            if (iF != 0 && iF2 != 0 && iF != me.hisn.appdrawer.a.k.size() - 1 && iF2 != me.hisn.appdrawer.a.k.size() - 1) {
                e(iF, iF2);
                ((androidx.recyclerview.widget.RecyclerView.g) java.util.Objects.requireNonNull(recyclerView.getAdapter())).a(iF, iF2);
                me.hisn.appdrawer.a.this.j = true;
            }
            return true;
        }

        @Override // androidx.recyclerview.widget.f.AbstractC0015f
        public int c(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
            return androidx.recyclerview.widget.f.AbstractC0015f.d(15, 0);
        }
    }

    private static int a(int i) {
        if (i == 1) {
            return me.hisn.mygesture.R.style.drawer_anim_left;
        }
        if (i == 2) {
            return me.hisn.mygesture.R.style.drawer_anim_right;
        }
        if (i == 3) {
            return me.hisn.mygesture.R.style.drawer_anim_up;
        }
        if (i != 4) {
            return 0;
        }
        return me.hisn.mygesture.R.style.drawer_anim_down;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:17:0x0034  */
    /* JADX WARN: Code duplicated, block: B:19:0x0048  */
    /* JADX WARN: Code duplicated, block: B:21:0x0059  */
    /* JADX WARN: Code duplicated, block: B:23:0x006a  */
    public android.view.animation.Animation a(android.content.Context context, int i, boolean z, boolean z2) {
        float f2;
        float f3;
        android.view.animation.AnimationSet animationSet;
        android.view.animation.TranslateAnimation translateAnimation;
        android.view.animation.AlphaAnimation alphaAnimation;
        if (i == 1) {
            f2 = -0.7f;
        } else {
            if (i != 2) {
                if (i != 3) {
                    f2 = 0.0f;
                    if (i == 4) {
                        f3 = -0.7f;
                    }
                } else {
                    f2 = 0.0f;
                    f3 = 0.7f;
                }
                if (z) {
                    animationSet = new android.view.animation.AnimationSet(false);
                    translateAnimation = new android.view.animation.TranslateAnimation(1, f2, 1, 0.0f, 1, f3, 1, 0.0f);
                    if (z2) {
                        translateAnimation.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator));
                    }
                    alphaAnimation = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
                } else {
                    animationSet = new android.view.animation.AnimationSet(false);
                    translateAnimation = new android.view.animation.TranslateAnimation(1, 0.0f, 1, f2, 1, 0.0f, 1, f3);
                    if (z2) {
                        translateAnimation.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator));
                    }
                    alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
                }
                animationSet.addAnimation(translateAnimation);
                animationSet.addAnimation(alphaAnimation);
                return animationSet;
            }
            f2 = 0.7f;
        }
        f3 = 0.0f;
        if (z) {
            animationSet = new android.view.animation.AnimationSet(false);
            translateAnimation = new android.view.animation.TranslateAnimation(1, f2, 1, 0.0f, 1, f3, 1, 0.0f);
            if (z2) {
                translateAnimation.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator));
            }
            alphaAnimation = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
        } else {
            animationSet = new android.view.animation.AnimationSet(false);
            translateAnimation = new android.view.animation.TranslateAnimation(1, 0.0f, 1, f2, 1, 0.0f, 1, f3);
            if (z2) {
                translateAnimation.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator));
            }
            alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
        }
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized java.lang.String a() {
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder();
        for (me.hisn.appdrawer.b bVar : k) {
            if (bVar.f462a != null) {
                sb.append(bVar.f462a);
                sb.append("/");
                sb.append(bVar.f463b);
                sb.append("&");
            }
        }
        return sb.toString();
    }

    private java.util.List<me.hisn.appdrawer.b> a(android.content.Context context, java.lang.String str) {
        java.lang.String strSubstring;
        java.lang.String className;
        android.content.pm.ActivityInfo activityInfo;
        java.lang.String str2 = null;
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String[] strArrSplit = str.split("&");
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        c.a.a.a aVar = new c.a.a.a(context, this.f);
        me.hisn.utils.x xVar = new me.hisn.utils.x();
        int i = 0;
        xVar.a(context, 0, 192, 192);
        int length = strArrSplit.length;
        boolean z = false;
        int i2 = 0;
        while (true) {
            boolean z2 = true;
            if (i2 >= length) {
                break;
            }
            java.lang.String str3 = strArrSplit[i2];
            if (str3.contains("/")) {
                int iIndexOf = str3.indexOf(47);
                strSubstring = str3.substring(i, iIndexOf);
                className = str3.substring(iIndexOf + 1);
                z2 = z;
            } else {
                android.content.Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str3);
                if (launchIntentForPackage != null) {
                    strSubstring = str3;
                    className = ((android.content.ComponentName) java.util.Objects.requireNonNull(launchIntentForPackage.getComponent())).getClassName();
                } else {
                    z2 = z;
                    strSubstring = str3;
                    className = str2;
                }
            }
            if (className != null) {
                try {
                    activityInfo = packageManager.getActivityInfo(new android.content.ComponentName(strSubstring, className), 8192);
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                    activityInfo = str2;
                }
                if (activityInfo != null) {
                    me.hisn.appdrawer.b bVar = new me.hisn.appdrawer.b();
                    bVar.f462a = strSubstring;
                    bVar.f463b = className;
                    bVar.f464c = ((java.lang.Object) activityInfo.loadLabel(packageManager)) + "";
                    str2 = null;
                    android.graphics.drawable.Drawable drawableA = aVar.a(strSubstring, className, null);
                    bVar.d = drawableA;
                    if (drawableA == null) {
                        bVar.d = activityInfo.loadIcon(packageManager);
                    }
                    if (this.f448c) {
                        bVar.d = xVar.a(context, bVar.d, -1);
                    }
                    arrayList.add(bVar);
                }
            }
            i2++;
            z = z2;
            i = 0;
        }
        if (z) {
            this.j = true;
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0052  */
    private void a(android.view.View view, int i) {
        int i2;
        me.hisn.appdrawer.a.e eVar = new me.hisn.appdrawer.a.e();
        androidx.recyclerview.widget.RecyclerView recyclerView = (androidx.recyclerview.widget.RecyclerView) view.findViewById(me.hisn.mygesture.R.id.drawer_recyclerView);
        recyclerView.setFadingEdgeLength((int) view.getContext().getResources().getDimension(me.hisn.mygesture.R.dimen.drawer_folder_bkg_radius));
        recyclerView.setVerticalFadingEdgeEnabled(true);
        int i3 = this.e;
        if (i3 != 1) {
            if (i3 == 2) {
                i2 = me.hisn.mygesture.R.drawable.drawer_folder_bkg_dark;
            } else {
                recyclerView.setBackgroundColor(0);
            }
            recyclerView.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(view.getContext(), i, 1, false));
            recyclerView.setAdapter(eVar);
            recyclerView.setAlpha(0.0f);
            if (this.e > 0) {
                recyclerView.post(new me.hisn.appdrawer.a.c(this, recyclerView));
            }
            view.addOnAttachStateChangeListener(new me.hisn.appdrawer.a.d(recyclerView));
        }
        i2 = me.hisn.mygesture.R.drawable.drawer_folder_bkg;
        recyclerView.setBackgroundResource(i2);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(view.getContext(), i, 1, false));
        recyclerView.setAdapter(eVar);
        recyclerView.setAlpha(0.0f);
        if (this.e > 0) {
            recyclerView.post(new me.hisn.appdrawer.a.c(this, recyclerView));
        }
        view.addOnAttachStateChangeListener(new me.hisn.appdrawer.a.d(recyclerView));
    }

    private android.view.WindowManager.LayoutParams b(int i) {
        android.view.WindowManager.LayoutParams layoutParamsA = new me.hisn.utils.v().a(true, 17, -1, -1, 0, 0, a(i), false);
        if (!this.d || android.os.Build.VERSION.SDK_INT < 31) {
            layoutParamsA.flags |= 2;
            layoutParamsA.dimAmount = 0.7f;
        } else {
            layoutParamsA.flags |= 4;
            layoutParamsA.setBlurBehindRadius(me.hisn.mygesture.P.k0 / 10);
        }
        return layoutParamsA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(android.view.View view) {
        a(view);
        if (this.j) {
            new java.lang.Thread(new me.hisn.appdrawer.a.RunnableC0017a()).start();
        }
    }

    private void c(android.view.View view) {
        int i;
        this.j = false;
        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) view.findViewById(me.hisn.mygesture.R.id.drawer_layout);
        linearLayout.setOnClickListener(new me.hisn.appdrawer.a.b(view));
        if (android.text.TextUtils.isEmpty(this.f446a)) {
            view.findViewById(me.hisn.mygesture.R.id.empty_tips_tv).setVisibility(0);
            return;
        }
        int i2 = 3;
        int i3 = me.hisn.mygesture.P.L;
        int i4 = this.g;
        if (i3 == i4) {
            i = 8388627;
        } else if (me.hisn.mygesture.P.R == i4) {
            i = 8388629;
        } else {
            i2 = 4;
            i = 17;
        }
        linearLayout.setGravity(i);
        if (k == null) {
            k = a(view.getContext(), this.f446a);
        }
        a(view, i2);
    }

    public android.view.View a(android.content.Context context, int i, int i2) {
        this.f446a = me.hisn.mygesture.P.s.getString("drawer_apps", "");
        this.f447b = me.hisn.mygesture.P.s.getBoolean("show_app_label", true);
        this.d = me.hisn.mygesture.P.s.getBoolean("d31424", false);
        this.f448c = me.hisn.mygesture.P.i0;
        this.g = i;
        this.i = i2;
        this.f = me.hisn.mygesture.P.s.getString("drawer_icon_pack", null);
        this.e = me.hisn.mygesture.P.s.getInt("31430", 0);
        android.view.View viewInflate = android.view.View.inflate(context, me.hisn.mygesture.R.layout.activity_app_drawer, null);
        this.h = viewInflate;
        c(viewInflate);
        this.h.setLayoutParams(b(i2));
        return this.h;
    }

    public abstract void a(android.view.View view);
}
