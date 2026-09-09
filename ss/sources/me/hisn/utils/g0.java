package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public abstract class g0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f756a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.view.View f757b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int f758c;
    private int d;
    private int e;
    private int f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private android.content.SharedPreferences k;
    private java.lang.Runnable l = null;
    private android.animation.ValueAnimator m = null;
    private android.media.AudioManager n;
    private int o;
    private int p;
    private int q;
    private android.view.View.OnClickListener r;
    private android.view.View.OnLongClickListener s;
    private boolean t;

    class a implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f759a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.j f760b;

        a(android.widget.ImageView imageView, me.hisn.utils.j jVar) {
            this.f759a = imageView;
            this.f760b = jVar;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.widget.ImageView imageView;
            int i;
            if (view != this.f759a) {
                me.hisn.utils.g0.this.a((android.widget.ImageView) view, true);
                return;
            }
            if (this.f760b.a(view.getContext()) == 0) {
                if (!this.f760b.b(view.getContext(), 1)) {
                    return;
                }
                imageView = (android.widget.ImageView) view;
                i = me.hisn.mygesture.R.drawable.ic_brightness_auto_black_24dp;
            } else {
                if (!this.f760b.b(view.getContext(), 0)) {
                    return;
                }
                imageView = (android.widget.ImageView) view;
                i = me.hisn.mygesture.R.drawable.ic_brightness_7_black_24dp;
            }
            imageView.setImageResource(i);
        }
    }

    class b implements android.view.View.OnTouchListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f762a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        float f763b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f764c = 0;
        final /* synthetic */ me.hisn.utils.HiImageView d;
        final /* synthetic */ me.hisn.utils.j e;
        final /* synthetic */ android.widget.ImageView f;

        b(me.hisn.utils.HiImageView hiImageView, me.hisn.utils.j jVar, android.widget.ImageView imageView) {
            this.d = hiImageView;
            this.e = jVar;
            this.f = imageView;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.f762a = me.hisn.utils.g0.this.a(view, motionEvent);
                if (view == this.d) {
                    if (!this.e.b(view.getContext(), 0)) {
                        return true;
                    }
                    this.f.setImageResource(me.hisn.mygesture.R.drawable.ic_brightness_7_black_24dp);
                    me.hisn.utils.g0 g0Var = me.hisn.utils.g0.this;
                    g0Var.a(g0Var.f757b, true);
                }
                this.f764c = me.hisn.utils.g0.this.a(view);
            } else if (action == 2) {
                float fA = 1.0f - ((me.hisn.utils.g0.this.a(this.f762a, me.hisn.utils.g0.this.a(view, motionEvent)) + this.f764c) / me.hisn.utils.g0.this.b(view));
                this.f763b = fA;
                if (fA < 0.0f) {
                    this.f763b = 0.0f;
                } else if (fA > 1.0f) {
                    this.f763b = 1.0f;
                }
                if (view == this.d) {
                    me.hisn.utils.g0.this.a(view, this.f763b);
                    view.invalidate();
                    this.e.a(view.getContext(), (int) (this.f763b * me.hisn.utils.g0.this.q));
                } else {
                    int i = (int) (this.f763b * me.hisn.utils.g0.this.o);
                    float f = -1.0f;
                    if (me.hisn.mygesture.P.h == 0) {
                        f = this.f763b;
                    } else if (i != me.hisn.utils.g0.this.f()) {
                        f = i / me.hisn.utils.g0.this.o;
                        me.hisn.mygesture.MAS.a(view, me.hisn.mygesture.P.h);
                    }
                    if (f >= 0.0f) {
                        me.hisn.utils.g0.this.a(view, f);
                        view.invalidate();
                        me.hisn.utils.g0.this.b(i);
                    }
                }
            } else if (view == this.d) {
                me.hisn.utils.g0 g0Var2 = me.hisn.utils.g0.this;
                g0Var2.a(g0Var2.f757b, false);
            }
            return true;
        }
    }

    class c implements android.view.View.OnLongClickListener {
        c(me.hisn.utils.g0 g0Var) {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            return true;
        }
    }

    class d implements android.view.View.OnClickListener {
        d() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.content.Intent intentA = me.hisn.utils.t.a(view.getContext(), "com.hisnstudio.quicksearch");
            if (intentA == null) {
                intentA = new android.content.Intent("android.intent.action.WEB_SEARCH");
            }
            new me.hisn.utils.s().a(view.getContext().getApplicationContext(), intentA, null, null);
            me.hisn.utils.g0.this.a(true);
        }
    }

    class e implements android.view.animation.Animation.AnimationListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f766a;

        e(android.widget.ImageView imageView) {
            this.f766a = imageView;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(android.view.animation.Animation animation) {
            this.f766a.setImageDrawable(null);
            me.hisn.utils.g0 g0Var = me.hisn.utils.g0.this;
            g0Var.c(g0Var.f757b);
            me.hisn.utils.g0.this.f757b = null;
            me.hisn.utils.g0.this.t = false;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(android.view.animation.Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(android.view.animation.Animation animation) {
        }
    }

    class f implements android.view.View.OnAttachStateChangeListener {
        f() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
            me.hisn.utils.g0.this.i();
            me.hisn.utils.g0.this.b(true);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
        }
    }

    class g implements me.hisn.mygesture.MAS.i {
        g() {
        }

        @Override // me.hisn.mygesture.MAS.i
        public void a() {
            me.hisn.utils.g0 g0Var = me.hisn.utils.g0.this;
            g0Var.c(g0Var.f757b);
        }

        @Override // me.hisn.mygesture.MAS.i
        public void b() {
            try {
                ((android.view.WindowManager) java.util.Objects.requireNonNull(me.hisn.mygesture.MAS.k())).updateViewLayout(me.hisn.utils.g0.this.f757b, me.hisn.utils.g0.this.g());
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    class h implements android.view.View.OnTouchListener {
        h() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && !me.hisn.utils.g0.this.t) {
                me.hisn.utils.g0.this.a(true);
            }
            return true;
        }
    }

    class i implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.lang.String f771a;

        i(java.lang.String str) {
            this.f771a = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            new me.hisn.utils.s().a(view.getContext().getApplicationContext(), me.hisn.utils.t.a(view.getContext(), this.f771a), new me.hisn.utils.z0().a(view), this.f771a);
            me.hisn.utils.g0 g0Var = me.hisn.utils.g0.this;
            g0Var.c(g0Var.f757b);
        }
    }

    class j implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f773a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f774b;

        j(android.widget.ImageView imageView, android.view.View view) {
            this.f773a = imageView;
            this.f774b = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.utils.g0 g0Var = me.hisn.utils.g0.this;
            g0Var.a(this.f773a, this.f774b, g0Var.n.isMusicActive(), me.hisn.utils.y.a(this.f774b.getContext()));
            this.f774b.postDelayed(me.hisn.utils.g0.this.l, 1500L);
        }
    }

    class k implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f776a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f777b;

        k(android.widget.ImageView imageView, android.view.View view) {
            this.f776a = imageView;
            this.f777b = view;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            int i;
            switch (view.getId()) {
                case me.hisn.mygesture.R.id.music_next_btn /* 2131230927 */:
                    i = 80;
                    break;
                case me.hisn.mygesture.R.id.music_play_btn /* 2131230928 */:
                    i = 81;
                    break;
                case me.hisn.mygesture.R.id.music_prev_btn /* 2131230929 */:
                    i = 82;
                    break;
                default:
                    i = 0;
                    break;
            }
            if (i != 0) {
                view.removeCallbacks(me.hisn.utils.g0.this.l);
                view.postDelayed(me.hisn.utils.g0.this.l, 1500L);
                if (i == 81) {
                    me.hisn.utils.g0 g0Var = me.hisn.utils.g0.this;
                    g0Var.a(this.f776a, this.f777b, !g0Var.n.isMusicActive(), me.hisn.utils.y.a(view.getContext()));
                }
                me.hisn.utils.y.a(me.hisn.utils.g0.this.f757b.getContext().getApplicationContext(), i);
            }
        }
    }

    class l implements android.animation.ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f779a;

        l(me.hisn.utils.g0 g0Var, android.view.View view) {
            this.f779a = view;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            this.f779a.setRotation(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    class m extends me.hisn.utils.j {
        m() {
        }

        @Override // me.hisn.utils.j
        public void b() {
            me.hisn.utils.g0.this.a(false);
        }
    }

    class n implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.HiImageView f781a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ float f782b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.HiImageView f783c;
        final /* synthetic */ float d;

        class a implements android.animation.ValueAnimator.AnimatorUpdateListener {
            a() {
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                float fFloatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
                me.hisn.utils.g0.n nVar = me.hisn.utils.g0.n.this;
                me.hisn.utils.g0.this.a(nVar.f781a, nVar.f782b * fFloatValue);
                me.hisn.utils.g0.n nVar2 = me.hisn.utils.g0.n.this;
                me.hisn.utils.g0.this.a(nVar2.f783c, nVar2.d * fFloatValue);
                if (fFloatValue == 1.0f) {
                    me.hisn.utils.g0.n.this.f781a.setClickable(true);
                    me.hisn.utils.g0.n.this.f781a.setLongClickable(true);
                    me.hisn.utils.g0.n.this.f783c.setClickable(true);
                }
            }
        }

        n(me.hisn.utils.HiImageView hiImageView, float f, me.hisn.utils.HiImageView hiImageView2, float f2) {
            this.f781a = hiImageView;
            this.f782b = f;
            this.f783c = hiImageView2;
            this.d = f2;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.animation.ValueAnimator valueAnimatorOfFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(400L);
            valueAnimatorOfFloat.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(this.f781a.getContext().getApplicationContext(), android.R.anim.decelerate_interpolator));
            valueAnimatorOfFloat.addUpdateListener(new me.hisn.utils.g0.n.a());
            valueAnimatorOfFloat.start();
            this.f781a.setClickable(false);
            this.f783c.setClickable(false);
            this.f781a.setLongClickable(false);
            this.f781a.setImageDrawable(new android.graphics.drawable.ColorDrawable(-1));
            this.f783c.setImageDrawable(new android.graphics.drawable.ColorDrawable(-1));
        }
    }

    private class o implements android.view.View.OnLongClickListener {

        class a extends me.hisn.mypanel.d {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.widget.ImageView f786a;

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            final /* synthetic */ int f787b;

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            final /* synthetic */ android.view.View f788c;

            /* JADX INFO: renamed from: me.hisn.utils.g0$o$a$a, reason: collision with other inner class name */
            class ViewOnClickListenerC0037a implements android.view.View.OnClickListener {

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                final /* synthetic */ android.widget.PopupWindow f789a;

                /* JADX INFO: renamed from: me.hisn.utils.g0$o$a$a$a, reason: collision with other inner class name */
                class RunnableC0038a implements java.lang.Runnable {

                    /* JADX INFO: renamed from: a, reason: collision with root package name */
                    final /* synthetic */ java.lang.String f791a;

                    RunnableC0038a(java.lang.String str) {
                        this.f791a = str;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        new java.io.File(me.hisn.utils.g0.o.a.this.f788c.getContext().getExternalFilesDir(null) + "/" + this.f791a + ".png").delete();
                    }
                }

                ViewOnClickListenerC0037a(android.widget.PopupWindow popupWindow) {
                    this.f789a = popupWindow;
                }

                private void a() {
                    me.hisn.utils.g0.o.a.this.f786a.setImageResource(me.hisn.mygesture.R.drawable.ic_add_black_24dp);
                    me.hisn.utils.g0.this.k.edit().remove(me.hisn.utils.g0.o.a.this.f787b + "_k").remove(me.hisn.utils.g0.o.a.this.f787b + "_f").remove(me.hisn.utils.g0.o.a.this.f787b + "_c").remove(me.hisn.utils.g0.o.a.this.f787b + "_p").remove(me.hisn.utils.g0.o.a.this.f787b + "_s").apply();
                    new java.lang.Thread(new me.hisn.utils.g0.o.a.ViewOnClickListenerC0037a.RunnableC0038a(me.hisn.utils.g0.o.a.this.f787b + "")).start();
                }

                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    this.f789a.dismiss();
                    if (view.getId() == me.hisn.mygesture.R.id.delete_btn) {
                        a();
                        return;
                    }
                    android.content.Intent intent = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.PLA.class);
                    intent.putExtra("31416", 80);
                    intent.putExtra("31415", ((java.lang.Integer) me.hisn.utils.g0.o.a.this.f786a.getTag()).intValue());
                    intent.addFlags(268435456);
                    view.getContext().startActivity(intent);
                    me.hisn.utils.g0.this.a(true);
                }
            }

            a(android.widget.ImageView imageView, int i, android.view.View view) {
                this.f786a = imageView;
                this.f787b = i;
                this.f788c = view;
            }

            @Override // me.hisn.mypanel.d
            protected void a(android.view.View view, android.widget.PopupWindow popupWindow) {
                android.widget.TextView textView = (android.widget.TextView) view.findViewById(me.hisn.mygesture.R.id.delete_btn);
                android.widget.TextView textView2 = (android.widget.TextView) view.findViewById(me.hisn.mygesture.R.id.change_icon_btn);
                me.hisn.utils.g0.o.a.ViewOnClickListenerC0037a viewOnClickListenerC0037a = new me.hisn.utils.g0.o.a.ViewOnClickListenerC0037a(popupWindow);
                textView.setOnClickListener(viewOnClickListenerC0037a);
                textView2.setOnClickListener(viewOnClickListenerC0037a);
            }
        }

        private o() {
        }

        /* synthetic */ o(me.hisn.utils.g0 g0Var, me.hisn.utils.g0.f fVar) {
            this();
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            android.widget.ImageView imageView = (android.widget.ImageView) view;
            if (me.hisn.utils.g0.this.k.getInt(imageView.getTag() + "_k", 0) == 0) {
                return true;
            }
            int iIntValue = ((java.lang.Integer) view.getTag()).intValue();
            android.view.View viewInflate = android.view.LayoutInflater.from(view.getContext()).inflate(me.hisn.utils.g0.this.m() ? me.hisn.mygesture.R.layout.cc_menu_dark : me.hisn.mygesture.R.layout.cc_menu, (android.view.ViewGroup) null, false);
            android.widget.PopupWindow popupWindowA = new me.hisn.utils.g0.o.a(imageView, iIntValue, view).a(viewInflate, -2, -2, true);
            viewInflate.measure(0, 0);
            int measuredWidth = viewInflate.getMeasuredWidth();
            int measuredHeight = viewInflate.getMeasuredHeight();
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int width = (me.hisn.mygesture.P.k0 - iArr[0]) - (view.getWidth() / 2);
            int height = (me.hisn.mygesture.P.l0 - iArr[1]) - (view.getHeight() / 2);
            int width2 = view.getWidth() / 2;
            int height2 = 0 - (view.getHeight() / 2);
            if (height < measuredHeight) {
                height2 -= measuredHeight;
            }
            if (width < measuredWidth) {
                width2 -= measuredWidth;
            }
            popupWindowA.showAsDropDown(view, width2, height2);
            return true;
        }
    }

    private class p implements android.view.View.OnClickListener {
        private p() {
        }

        /* synthetic */ p(me.hisn.utils.g0 g0Var, me.hisn.utils.g0.f fVar) {
            this();
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.utils.g0.this.d(view);
        }
    }

    private android.graphics.drawable.Drawable a(android.content.Context context, java.lang.String str) {
        java.io.File file = new java.io.File(context.getExternalFilesDir(null) + "/" + str + ".png");
        if (!file.exists()) {
            return null;
        }
        if (me.hisn.mygesture.P.N == null) {
            new me.hisn.mypanel.c().a();
        }
        return new android.graphics.drawable.BitmapDrawable(context.getResources(), android.graphics.BitmapFactory.decodeFile(file.getPath()));
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0042  */
    /* JADX WARN: Code duplicated, block: B:33:0x0053  */
    /* JADX WARN: Code duplicated, block: B:34:0x0062  */
    /* JADX WARN: Code duplicated, block: B:36:? A[RETURN, SYNTHETIC] */
    private android.view.animation.Animation a(android.content.Context context, int i2, boolean z, boolean z2) {
        float f2;
        float f3;
        android.view.animation.TranslateAnimation translateAnimation;
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 == 3) {
                    f3 = z ? 0.7f : 0.3f;
                } else if (i2 != 4) {
                    f2 = 0.0f;
                } else {
                    f3 = z ? -0.7f : -0.3f;
                }
                f2 = 0.0f;
                if (z) {
                    translateAnimation = new android.view.animation.TranslateAnimation(1, f2, 1, 0.0f, 1, f3, 1, 0.0f);
                    if (z2) {
                        return translateAnimation;
                    }
                    translateAnimation.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator));
                    return translateAnimation;
                }
                android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(false);
                android.view.animation.TranslateAnimation translateAnimation2 = new android.view.animation.TranslateAnimation(1, 0.0f, 1, f2, 1, 0.0f, 1, f3);
                android.view.animation.AlphaAnimation alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
                animationSet.addAnimation(translateAnimation2);
                animationSet.addAnimation(alphaAnimation);
                return animationSet;
            }
            f2 = z ? 0.7f : 0.3f;
        } else {
            f2 = z ? -0.7f : -0.3f;
        }
        f3 = 0.0f;
        if (z) {
            translateAnimation = new android.view.animation.TranslateAnimation(1, f2, 1, 0.0f, 1, f3, 1, 0.0f);
            if (z2) {
                return translateAnimation;
            }
            translateAnimation.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator));
            return translateAnimation;
        }
        android.view.animation.AnimationSet animationSet2 = new android.view.animation.AnimationSet(false);
        android.view.animation.TranslateAnimation translateAnimation3 = new android.view.animation.TranslateAnimation(1, 0.0f, 1, f2, 1, 0.0f, 1, f3);
        android.view.animation.AlphaAnimation alphaAnimation2 = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
        animationSet2.addAnimation(translateAnimation3);
        animationSet2.addAnimation(alphaAnimation2);
        return animationSet2;
    }

    private void a(int i2) {
        android.content.Intent intent = new android.content.Intent(this.f757b.getContext().getApplicationContext(), (java.lang.Class<?>) me.hisn.utils.PermissionA.class);
        intent.putExtra("31415", i2);
        intent.addFlags(268435456);
        this.f757b.getContext().getApplicationContext().startActivity(intent);
        a(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.view.View view, boolean z) {
        if (!this.j || view == null) {
            return;
        }
        float f2 = z ? 0.0f : 1.0f;
        int[] iArr = {me.hisn.mygesture.R.id.button2, me.hisn.mygesture.R.id.button4, me.hisn.mygesture.R.id.blur_back, me.hisn.mygesture.R.id.music_layout, me.hisn.mygesture.R.id.search_layout, me.hisn.mygesture.R.id.volume_off_btn, me.hisn.mygesture.R.id.auto_brightness_btn, me.hisn.mygesture.R.id.volume_layout};
        for (int i2 = 0; i2 < 8; i2++) {
            view.findViewById(iArr[i2]).setAlpha(f2);
        }
        for (int i3 : c()) {
            view.findViewById(i3).setAlpha(f2);
        }
        if (!e()) {
            view.findViewById(me.hisn.mygesture.R.id.grid_layout).setBackgroundResource(z ? 0 : me.hisn.mygesture.R.drawable.round_shape_gray);
            return;
        }
        android.view.View viewFindViewById = view.findViewById(me.hisn.mygesture.R.id.brightness_layout);
        float f3 = z ? 1.5f : 1.0f;
        android.view.View viewFindViewById2 = view.findViewById(me.hisn.mygesture.R.id.blur_back);
        if (viewFindViewById2.getWidth() > viewFindViewById2.getHeight()) {
            viewFindViewById.setPivotY(viewFindViewById.getHeight() + 15.0f);
        }
        viewFindViewById.setScaleX(f3);
        viewFindViewById.setScaleY(f3);
    }

    private void a(android.view.View view, int[] iArr) {
        if (iArr.length > 0) {
            int i2 = m() ? me.hisn.mygesture.R.drawable.cc_bkg_dark : me.hisn.mygesture.R.drawable.cc_bkg_light;
            for (int i3 : iArr) {
                android.graphics.drawable.Drawable drawable = view.getContext().getApplicationContext().getDrawable(i2);
                if (drawable != null) {
                    drawable.setAlpha((int) ((10.0f - (this.e * 1.5f)) * 25.5f));
                }
                view.findViewById(i3).setBackground(drawable);
            }
        }
    }

    private void a(android.view.View view, int[] iArr, boolean z) {
        int i2 = m() ? -1 : -14540254;
        for (int i3 : iArr) {
            if (i3 != me.hisn.mygesture.R.id.panel_9) {
                android.view.View viewFindViewById = view.findViewById(i3);
                if (z) {
                    ((android.widget.TextView) viewFindViewById).setTextColor(i2);
                } else {
                    ((android.widget.ImageView) viewFindViewById).setColorFilter(i2);
                }
            }
        }
    }

    private void a(android.widget.ImageView imageView) {
        android.graphics.drawable.Drawable drawableB;
        int i2 = this.f758c;
        if (i2 == 0) {
            imageView.setBackgroundResource(me.hisn.mygesture.R.color.trans_black);
            return;
        }
        if (i2 == 1) {
            imageView.setBackgroundColor(this.k.getInt("panel_background_color", -16711681));
            return;
        }
        if (i2 == 2) {
            drawableB = b(imageView.getContext());
        } else {
            if (i2 != 3) {
                if (i2 == 5 && android.os.Build.VERSION.SDK_INT >= 31) {
                    android.view.WindowManager.LayoutParams layoutParams = (android.view.WindowManager.LayoutParams) this.f757b.getLayoutParams();
                    layoutParams.flags |= 4;
                    layoutParams.setBlurBehindRadius(me.hisn.mygesture.P.k0 / 10);
                    this.f757b.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            drawableB = b(imageView.getContext());
            if (drawableB == null) {
                return;
            }
            android.graphics.drawable.Drawable drawableA = new me.hisn.utils.i().a(imageView.getContext().getApplicationContext(), ((android.graphics.drawable.BitmapDrawable) drawableB).getBitmap(), 10, true, true);
            if (drawableA != null) {
                drawableB = drawableA;
            }
        }
        imageView.setImageDrawable(drawableB);
    }

    private void a(android.widget.ImageView imageView, android.graphics.drawable.Drawable drawable, int i2, int i3) {
        android.graphics.drawable.Drawable drawableA = a(imageView.getContext(), imageView.getTag() + "");
        if (drawableA != null) {
            if (i3 != me.hisn.mygesture.R.id.panel_9) {
                imageView.setImageDrawable(drawableA);
                int iB = b(imageView.getContext().getApplicationContext(), i2);
                if (iB != 0) {
                    imageView.setColorFilter(iB);
                    return;
                }
                return;
            }
            drawable = new me.hisn.utils.m0().a(imageView.getContext().getApplicationContext(), drawableA);
        }
        imageView.setImageDrawable(drawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.widget.ImageView imageView, android.view.View view, boolean z, java.lang.String str) {
        if (str == null) {
            return;
        }
        a((android.widget.ImageView) view.findViewById(me.hisn.mygesture.R.id.panel_9), str);
        boolean z2 = false;
        android.animation.ValueAnimator valueAnimator = this.m;
        if (z) {
            if (valueAnimator == null) {
                android.animation.ValueAnimator valueAnimatorOfFloat = android.animation.ValueAnimator.ofFloat(0.0f, 360.0f);
                this.m = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(10000L);
                this.m.setRepeatCount(-1);
                this.m.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(view.getContext(), android.R.interpolator.linear));
                this.m.addUpdateListener(new me.hisn.utils.g0.l(this, view));
            }
            if (!this.m.isRunning()) {
                this.m.start();
                z2 = true;
            }
        } else if (valueAnimator != null && valueAnimator.isRunning()) {
            this.m.cancel();
            if (view.getRotation() > 0.0f) {
                view.animate().setDuration(500L).rotation(0.0f).start();
            }
            z2 = true;
        }
        if (z2) {
            imageView.setImageResource(z ? me.hisn.mygesture.R.drawable.v_play_pause : me.hisn.mygesture.R.drawable.v_pause_play);
            java.lang.Object drawable = imageView.getDrawable();
            if (drawable instanceof android.graphics.drawable.Animatable) {
                ((android.graphics.drawable.Animatable) drawable).start();
            }
        }
    }

    private void a(android.widget.ImageView imageView, java.lang.String str) {
        android.content.pm.PackageManager packageManager = imageView.getContext().getPackageManager();
        android.graphics.drawable.Drawable bitmapDrawable = null;
        if (str != null) {
            android.graphics.Bitmap bitmapA = new me.hisn.utils.C0040c().a(str);
            bitmapDrawable = bitmapA != null ? new android.graphics.drawable.BitmapDrawable(imageView.getContext().getResources(), bitmapA) : null;
            if (bitmapDrawable == null) {
                try {
                    bitmapDrawable = packageManager.getApplicationInfo(str, 128).loadIcon(packageManager);
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (bitmapDrawable != null) {
            imageView.setImageDrawable(new me.hisn.utils.m0().a(imageView.getContext().getApplicationContext(), bitmapDrawable));
            imageView.setOnClickListener(new me.hisn.utils.g0.i(str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.widget.ImageView imageView, boolean z) {
        int[] iArr = {me.hisn.mygesture.R.drawable.ic_notifications_black_24dp, me.hisn.mygesture.R.drawable.ic_notifications_off_black_24dp, me.hisn.mygesture.R.drawable.ic_vibration_black_24dp};
        int[] iArr2 = {2, 0, 1};
        int ringerMode = this.n.getRingerMode();
        int i2 = 1;
        if (ringerMode != 0) {
            i2 = ringerMode != 1 ? 0 : 2;
        }
        if (z && imageView != null) {
            if (a(imageView.getContext())) {
                int i3 = i2 + 1;
                int i4 = i3 <= 2 ? i3 : 0;
                this.n.setRingerMode(iArr2[i4]);
                i2 = i4;
            } else {
                a(81);
            }
        }
        imageView.setImageResource(iArr[i2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (!z || this.d == 0 || this.f757b == null) {
            c(this.f757b);
            this.f757b = null;
        } else {
            this.t = true;
            b(false);
            android.widget.ImageView imageView = (android.widget.ImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.blur_back);
            android.view.animation.Animation animationLoadAnimation = android.view.animation.AnimationUtils.loadAnimation(this.f757b.getContext(), me.hisn.mygesture.R.anim.panel_exit);
            animationLoadAnimation.setAnimationListener(new me.hisn.utils.g0.e(imageView));
            imageView.startAnimation(animationLoadAnimation);
        }
        me.hisn.mygesture.MAS.a((me.hisn.mygesture.MAS.i) null);
    }

    private void a(int[] iArr) {
        me.hisn.utils.g0.f fVar = null;
        if (this.r == null) {
            this.r = new me.hisn.utils.g0.p(this, fVar);
        }
        if (this.s == null) {
            this.s = new me.hisn.utils.g0.o(this, fVar);
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            android.widget.ImageView imageView = (android.widget.ImageView) this.f757b.findViewById(iArr[i2]);
            imageView.setTag(java.lang.Integer.valueOf(1068078049 + i2));
            imageView.setOnClickListener(this.r);
            imageView.setOnLongClickListener(this.s);
            int i3 = this.k.getInt(imageView.getTag() + "_k", 0);
            if (i3 != 0) {
                a(imageView, (android.graphics.drawable.Drawable) null, i3, iArr[i2]);
            }
        }
    }

    private boolean a(android.content.Context context) {
        return android.os.Build.VERSION.SDK_INT < 24 || ((android.app.NotificationManager) context.getSystemService("notification")).isNotificationPolicyAccessGranted();
    }

    private int b(android.content.Context context, int i2) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int color = context.getColor(me.hisn.mygesture.R.color.panel_color1);
            if (i2 != 16) {
                if (i2 != 17) {
                    if (i2 != 31) {
                        if (i2 == 34 && me.hisn.utils.u.f872a == 0) {
                            return color;
                        }
                    } else if (((me.hisn.mygesture.P) context).f537a == null) {
                        return color;
                    }
                } else if (!new me.hisn.mypanel.h(context).b()) {
                    return color;
                }
            } else if (!new me.hisn.mypanel.h(context).a()) {
                return color;
            }
        }
        return 0;
    }

    private android.graphics.drawable.Drawable b(android.content.Context context) {
        android.app.WallpaperManager wallpaperManager = (android.app.WallpaperManager) context.getSystemService("wallpaper");
        if (wallpaperManager == null) {
            return null;
        }
        try {
            if (androidx.core.content.a.a(context, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                return wallpaperManager.getDrawable();
            }
            return null;
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /*  JADX ERROR: ConcurrentModificationException in pass: ConstructorVisitor
        java.util.ConcurrentModificationException
        	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
        	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
        	at jadx.core.dex.visitors.ConstructorVisitor.insertPhiInsn(ConstructorVisitor.java:139)
        	at jadx.core.dex.visitors.ConstructorVisitor.processInvoke(ConstructorVisitor.java:91)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:56)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    private android.view.animation.Animation b(
    /*  JADX ERROR: ConcurrentModificationException in pass: ConstructorVisitor
        java.util.ConcurrentModificationException
        	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
        	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
        	at jadx.core.dex.visitors.ConstructorVisitor.insertPhiInsn(ConstructorVisitor.java:139)
        	at jadx.core.dex.visitors.ConstructorVisitor.processInvoke(ConstructorVisitor.java:91)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:56)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r15v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:215)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:150)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:415)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:345)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:299)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2) {
        this.n.setStreamVolume(this.p, i2, this.h ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z) {
        android.view.animation.Animation animationB;
        android.view.View view = this.f757b;
        if (view == null) {
            return;
        }
        androidx.constraintlayout.widget.ConstraintLayout constraintLayout = (androidx.constraintlayout.widget.ConstraintLayout) view.findViewById(me.hisn.mygesture.R.id.grid_layout);
        int i2 = this.d;
        if (i2 == 1) {
            animationB = b(this.f757b.getContext(), this.f756a, z, false);
        } else if (i2 == 2) {
            animationB = b(this.f757b.getContext(), this.f756a, z, true);
        } else if (i2 != 3) {
            animationB = i2 != 4 ? null : a(this.f757b.getContext(), this.f756a, z, true);
        } else {
            animationB = a(this.f757b.getContext(), this.f756a, z, false);
        }
        if (animationB != null) {
            animationB.setDuration(z ? 300L : 200L);
            animationB.setFillAfter(true);
            constraintLayout.startAnimation(animationB);
        } else if (this.d == 5) {
            int childCount = constraintLayout.getChildCount();
            if (!z) {
                constraintLayout.setBackground(null);
            }
            for (int i3 = 0; i3 < childCount; i3++) {
                android.view.View childAt = constraintLayout.getChildAt(i3);
                if (childAt.getVisibility() != 8) {
                    childAt.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this.f757b.getContext(), z ? me.hisn.mygesture.R.anim.item_zoom_in : me.hisn.mygesture.R.anim.item_zoom_out));
                }
            }
        }
    }

    private void c(android.content.Context context) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences("my_panel", 0);
        this.k = sharedPreferences;
        this.g = sharedPreferences.getBoolean("panel_show_in_center", false);
        this.f758c = this.k.getInt("background_type", 0);
        this.h = this.k.getBoolean("show_system_volume_panel", false);
        this.d = this.k.getInt("panel_anim_type", 2);
        this.k.getBoolean("panel_show_on_lock", false);
        this.i = this.k.getBoolean("tint_panel_icons", false);
        this.e = this.k.getInt("panel_back_alpha", 1);
        this.j = this.k.getBoolean("zoom_in_seek_bar", false);
        this.f = this.k.getInt("panel_theme_type", 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(android.view.View view) {
        android.widget.ImageView imageView = (android.widget.ImageView) view;
        int i2 = this.k.getInt(imageView.getTag() + "_k", 0);
        if (i2 == 0) {
            android.content.Intent intent = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.PLA.class);
            intent.putExtra("31416", 81);
            intent.putExtra("31415", ((java.lang.Integer) imageView.getTag()).intValue());
            intent.addFlags(268435456);
            view.getContext().startActivity(intent);
        } else {
            android.os.Bundle bundleA = null;
            if (i2 == 19 || i2 == 9) {
                this.f757b.findViewById(me.hisn.mygesture.R.id.panel_parent).setAlpha(0.0f);
            } else {
                bundleA = new me.hisn.utils.z0().a(view);
            }
            android.os.Bundle bundle = bundleA;
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            iArr[0] = iArr[0] + (view.getWidth() / 2);
            iArr[1] = iArr[1] + (view.getHeight() / 2);
            try {
                me.hisn.utils.s0.a(view.getContext().getApplicationContext(), imageView.getTag() + "", view, bundle, this.k, 3, iArr);
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
        a(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int f() {
        return this.n.getStreamVolume(this.p);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.WindowManager.LayoutParams g() {
        return new me.hisn.utils.v().a(true, 8388659, me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0, 0 - me.hisn.mygesture.MAS.h(), 0 - me.hisn.mygesture.MAS.j(), me.hisn.mygesture.R.style.panel_anim, false);
    }

    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    private void h() {
        android.widget.ImageView imageView = (android.widget.ImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.blur_back);
        a(imageView);
        imageView.setOnTouchListener(new me.hisn.utils.g0.h());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        android.widget.ImageView imageView = (android.widget.ImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.music_prev_btn);
        android.widget.ImageView imageView2 = (android.widget.ImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.music_play_btn);
        android.widget.ImageView imageView3 = (android.widget.ImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.music_next_btn);
        android.view.View viewFindViewById = this.f757b.findViewById(me.hisn.mygesture.R.id.album_bkg);
        this.l = new me.hisn.utils.g0.j(imageView2, viewFindViewById);
        if (this.n.isMusicActive()) {
            viewFindViewById.post(this.l);
        }
        me.hisn.utils.g0.k kVar = new me.hisn.utils.g0.k(imageView2, viewFindViewById);
        imageView.setOnClickListener(kVar);
        imageView2.setOnClickListener(kVar);
        imageView3.setOnClickListener(kVar);
    }

    private void j() {
        a(c());
    }

    private void k() {
        ((android.widget.LinearLayout) this.f757b.findViewById(me.hisn.mygesture.R.id.search_layout)).setOnClickListener(new me.hisn.utils.g0.d());
    }

    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    private void l() {
        this.n = (android.media.AudioManager) this.f757b.getContext().getSystemService("audio");
        this.p = 3;
        android.widget.ImageView imageView = (android.widget.ImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.volume_off_btn);
        android.widget.ImageView imageView2 = (android.widget.ImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.auto_brightness_btn);
        me.hisn.utils.HiImageView hiImageView = (me.hisn.utils.HiImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.volume_bar);
        me.hisn.utils.HiImageView hiImageView2 = (me.hisn.utils.HiImageView) this.f757b.findViewById(me.hisn.mygesture.R.id.brightness_bar);
        me.hisn.utils.g0.m mVar = new me.hisn.utils.g0.m();
        a(imageView, false);
        imageView2.setImageResource(mVar.a(this.f757b.getContext()) == 1 ? me.hisn.mygesture.R.drawable.ic_brightness_auto_black_24dp : me.hisn.mygesture.R.drawable.ic_brightness_7_black_24dp);
        this.o = this.n.getStreamMaxVolume(this.p);
        float f2 = f() / this.o;
        this.q = mVar.a();
        hiImageView.postDelayed(new me.hisn.utils.g0.n(hiImageView, f2, hiImageView2, mVar.b(hiImageView2.getContext()) / this.q), 100L);
        me.hisn.utils.g0.a aVar = new me.hisn.utils.g0.a(imageView2, mVar);
        imageView.setOnClickListener(aVar);
        imageView2.setOnClickListener(aVar);
        me.hisn.utils.g0.b bVar = new me.hisn.utils.g0.b(hiImageView2, mVar, imageView2);
        hiImageView.setOnTouchListener(bVar);
        hiImageView2.setOnTouchListener(bVar);
        hiImageView.setOnLongClickListener(new me.hisn.utils.g0.c(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean m() {
        int i2 = this.f;
        if (i2 == 0) {
            return false;
        }
        if (i2 == 1) {
            return true;
        }
        return me.hisn.utils.u0.a(this.f757b.getContext().getApplicationContext());
    }

    private void n() {
        a(this.f757b, a());
        int[] iArr = {me.hisn.mygesture.R.id.music_next_btn, me.hisn.mygesture.R.id.music_prev_btn, me.hisn.mygesture.R.id.music_play_btn, me.hisn.mygesture.R.id.search_icon, me.hisn.mygesture.R.id.auto_brightness_btn, me.hisn.mygesture.R.id.volume_off_btn, me.hisn.mygesture.R.id.volume_bar, me.hisn.mygesture.R.id.brightness_bar};
        if (this.i) {
            a(this.f757b, c(), false);
        }
        a(this.f757b, iArr, false);
        a(this.f757b, new int[]{me.hisn.mygesture.R.id.search_tv}, true);
    }

    private void o() {
        me.hisn.mygesture.MAS.a(new me.hisn.utils.g0.g());
    }

    private void p() {
        androidx.constraintlayout.widget.ConstraintLayout constraintLayout = (androidx.constraintlayout.widget.ConstraintLayout) this.f757b.findViewById(me.hisn.mygesture.R.id.grid_layout);
        if (this.g) {
            android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) constraintLayout.getLayoutParams();
            layoutParams.removeRule(12);
            layoutParams.addRule(13);
            constraintLayout.setLayoutParams(layoutParams);
        }
        int i2 = this.k.getInt("panel_size", 2);
        if (i2 != 2) {
            float f2 = 1.2f - (i2 * 0.1f);
            if (!d() && !this.g) {
                constraintLayout.setPivotX(new me.hisn.utils.l().a(this.f757b.getContext().getApplicationContext(), 150.0f));
                constraintLayout.setPivotY(new me.hisn.utils.l().a(this.f757b.getContext().getApplicationContext(), 378.0f));
            }
            constraintLayout.setScaleX(f2);
            constraintLayout.setScaleY(f2);
        }
    }

    protected abstract int a(int i2, int i3);

    protected abstract int a(android.view.View view);

    protected abstract int a(android.view.View view, android.view.MotionEvent motionEvent);

    public android.view.View a(android.content.Context context, int i2) {
        this.f756a = i2;
        if (this.f757b == null) {
            android.view.View viewInflate = android.view.View.inflate(context.getApplicationContext(), b(), null);
            this.f757b = viewInflate;
            viewInflate.addOnAttachStateChangeListener(new me.hisn.utils.g0.f());
            this.f757b.setLayoutParams(g());
            c(context);
            p();
            n();
            j();
            k();
            h();
            l();
            o();
        }
        return this.f757b;
    }

    protected abstract void a(android.view.View view, float f2);

    protected abstract int[] a();

    protected abstract int b();

    protected abstract int b(android.view.View view);

    public abstract void c(android.view.View view);

    protected abstract int[] c();

    protected boolean d() {
        return me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0;
    }

    protected abstract boolean e();
}
