package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class f extends me.hisn.mygesture.e {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.view.View f577c;
    private boolean d;
    private android.view.WindowManager.LayoutParams e;

    class a implements android.view.View.OnClickListener {
        a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.mygesture.P.o0 = false;
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f578a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.WindowManager f579b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f580c;
        final /* synthetic */ android.widget.TextView d;

        b(android.content.Context context, android.view.WindowManager windowManager, android.widget.ImageView imageView, android.widget.TextView textView) {
            this.f578a = context;
            this.f579b = windowManager;
            this.f580c = imageView;
            this.d = textView;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.f.b(this.f578a, this.f579b, true, this.f580c, this.d);
        }
    }

    class c implements android.animation.ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f581a = 0;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.animation.ValueAnimator f582b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.graphics.drawable.Drawable f583c;
        final /* synthetic */ android.graphics.drawable.Drawable d;
        final /* synthetic */ android.view.View e;
        final /* synthetic */ android.view.View f;
        final /* synthetic */ boolean g;
        final /* synthetic */ android.view.View h;
        final /* synthetic */ android.view.View i;
        final /* synthetic */ android.content.Context j;

        c(android.animation.ValueAnimator valueAnimator, android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.view.View view, android.view.View view2, boolean z, android.view.View view3, android.view.View view4, android.content.Context context) {
            this.f582b = valueAnimator;
            this.f583c = drawable;
            this.d = drawable2;
            this.e = view;
            this.f = view2;
            this.g = z;
            this.h = view3;
            this.i = view4;
            this.j = context;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            int iIntValue = ((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue();
            int i = this.f581a + 1;
            this.f581a = i;
            if (i % 2 == 0) {
                if (me.hisn.mygesture.P.o0) {
                    this.f583c.setTint(iIntValue);
                    this.d.setTint(iIntValue);
                    this.e.setBackground(this.f583c);
                    this.f.setBackground(this.d);
                } else {
                    this.f582b.cancel();
                }
                if (this.g && this.h.getAlpha() < 0.9f) {
                    float fAlpha = android.graphics.Color.alpha(iIntValue) / 255.0f;
                    this.h.setAlpha(fAlpha);
                    android.view.View view = this.i;
                    if (view != null) {
                        view.setAlpha(fAlpha);
                    }
                }
            }
            if (this.g && this.f581a % 20 == 0) {
                if (!me.hisn.mygesture.f.b(this.j)) {
                    new me.hisn.utils.s().a(this.j, me.hisn.mygesture.P.p0, new me.hisn.utils.z0().a(this.h));
                    me.hisn.mygesture.P.o0 = false;
                    me.hisn.mygesture.P.p0 = null;
                } else if (me.hisn.mygesture.f.c(this.j)) {
                    return;
                }
                this.f582b.cancel();
            }
        }
    }

    class d extends android.animation.AnimatorListenerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.WindowManager f584a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f585b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.View f586c;
        final /* synthetic */ boolean d;
        final /* synthetic */ android.view.View e;
        final /* synthetic */ android.view.View f;

        d(android.view.WindowManager windowManager, android.view.View view, android.view.View view2, boolean z, android.view.View view3, android.view.View view4) {
            this.f584a = windowManager;
            this.f585b = view;
            this.f586c = view2;
            this.d = z;
            this.e = view3;
            this.f = view4;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            super.onAnimationEnd(animator);
            me.hisn.mygesture.P.o0 = false;
            try {
                this.f584a.removeView(this.f585b);
                this.f584a.removeView(this.f586c);
                if (this.d) {
                    this.f584a.removeView(this.e);
                    if (me.hisn.mygesture.P.c0) {
                        return;
                    }
                    this.f584a.removeView(this.f);
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public f(android.content.Context context, android.view.WindowManager windowManager) {
        super(context, windowManager);
    }

    static void a(android.content.Context context, android.view.WindowManager windowManager, android.graphics.drawable.Drawable drawable, java.lang.String str) {
        if (me.hisn.mygesture.P.o0 || windowManager == null) {
            return;
        }
        me.hisn.mygesture.P.o0 = true;
        android.widget.TextView textView = null;
        if (!(me.hisn.mygesture.P.W && b(context))) {
            b(context, windowManager, false, null, null);
            return;
        }
        int i = me.hisn.mygesture.P.k0 / 6;
        android.widget.ImageView imageView = new android.widget.ImageView(context);
        imageView.setAlpha(0.0f);
        imageView.setBackgroundResource(me.hisn.mygesture.R.drawable.circle_shap);
        int i2 = i / 20;
        imageView.setPadding(i2, i2, i2, i2);
        imageView.setImageDrawable(new me.hisn.utils.m0().a(context, drawable));
        imageView.setOnClickListener(new me.hisn.mygesture.f.a());
        android.view.WindowManager.LayoutParams layoutParamsA = new me.hisn.utils.v().a(true, 17, i, i, 0, -200, me.hisn.mygesture.R.style.breath_anim, false);
        layoutParamsA.flags |= 2;
        layoutParamsA.dimAmount = 1.0f;
        try {
            windowManager.addView(imageView, layoutParamsA);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        if (!me.hisn.mygesture.P.c0) {
            textView = new android.widget.TextView(context);
            textView.setAlpha(0.0f);
            textView.setGravity(1);
            textView.setText(str);
            textView.setEllipsize(android.text.TextUtils.TruncateAt.END);
            textView.setLines(5);
            textView.setTextColor(-3355444);
            try {
                windowManager.addView(textView, new me.hisn.utils.v().a(false, 17, (int) (((double) me.hisn.mygesture.P.k0) * 0.7d), me.hisn.mygesture.P.k0 / 3, 0, me.hisn.mygesture.P.k0 / 5, me.hisn.mygesture.R.style.breath_anim, false));
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
        imageView.postDelayed(new me.hisn.mygesture.f.b(context, windowManager, imageView, textView), 800L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(android.content.Context context, android.view.WindowManager windowManager, boolean z, android.view.View view, android.view.View view2) {
        int iMin = (java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) * me.hisn.mygesture.P.Z) / 100;
        android.view.View view3 = new android.view.View(context);
        android.view.View view4 = new android.view.View(context);
        android.graphics.drawable.Drawable drawable = context.getResources().getDrawable(me.hisn.mygesture.R.mipmap.left_light, null);
        android.graphics.drawable.Drawable drawable2 = context.getResources().getDrawable(me.hisn.mygesture.R.mipmap.right_light, null);
        android.view.WindowManager.LayoutParams layoutParamsA = new me.hisn.utils.v().a(false, 8388659, iMin, -1, 0 - me.hisn.mygesture.MAS.h(), 0, me.hisn.mygesture.R.style.breath_anim, false);
        android.view.ViewGroup.LayoutParams layoutParamsA2 = new me.hisn.utils.v().a(false, 8388659, iMin, -1, (me.hisn.mygesture.P.k0 - iMin) - me.hisn.mygesture.MAS.h(), 0, me.hisn.mygesture.R.style.breath_anim, false);
        if (z) {
            layoutParamsA.flags |= 2097152;
        }
        try {
            windowManager.addView(view3, layoutParamsA);
            windowManager.addView(view4, layoutParamsA2);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        int[] iArr = new int[2];
        iArr[0] = me.hisn.mygesture.P.b0 ? me.hisn.utils.k.b() : 16777215;
        iArr[1] = me.hisn.mygesture.P.b0 ? me.hisn.utils.k.a() : me.hisn.mygesture.P.Y;
        android.animation.ValueAnimator valueAnimatorOfArgb = android.animation.ValueAnimator.ofArgb(iArr);
        valueAnimatorOfArgb.setDuration(1500L);
        valueAnimatorOfArgb.setRepeatCount(z ? -1 : ((me.hisn.mygesture.P.X + 1) * 2) - 1);
        valueAnimatorOfArgb.setRepeatMode(2);
        valueAnimatorOfArgb.addUpdateListener(new me.hisn.mygesture.f.c(valueAnimatorOfArgb, drawable, drawable2, view3, view4, z, view, view2, context));
        valueAnimatorOfArgb.addListener(new me.hisn.mygesture.f.d(windowManager, view3, view4, z, view, view2));
        valueAnimatorOfArgb.start();
    }

    public static boolean b(android.content.Context context) {
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) context.getSystemService("keyguard");
        if (keyguardManager != null) {
            return android.os.Build.VERSION.SDK_INT >= 22 ? keyguardManager.isDeviceLocked() : keyguardManager.inKeyguardRestrictedInputMode();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(android.content.Context context) {
        android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService("power");
        if (powerManager != null) {
            return powerManager.isInteractive();
        }
        return false;
    }

    @Override // me.hisn.mygesture.e
    public void a() {
        c();
    }

    @Override // me.hisn.mygesture.e
    public void a(int i, int i2, int i3) {
        a(this.f575a, this.f576b, i3);
    }

    @Override // me.hisn.mygesture.e
    public void a(int i, int i2, int i3, int i4) {
        if (this.d) {
            this.f577c.setAlpha(i / (java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) / 2.0f));
        }
    }

    void a(android.content.Context context, android.view.WindowManager windowManager, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int iG;
        int i6;
        int iH;
        int i7;
        if (this.d) {
            return;
        }
        int iMin = (java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) * me.hisn.mygesture.P.U) / 100;
        if (this.f577c == null) {
            this.f577c = new android.view.View(context);
            this.f576b = windowManager;
            d();
        }
        if (i == me.hisn.mygesture.P.L) {
            i2 = me.hisn.mygesture.R.mipmap.left_light;
            i3 = me.hisn.mygesture.P.l0;
            i4 = (me.hisn.mygesture.MAS.h() < 0 ? 8388613 : 8388611) | 48;
            if (me.hisn.mygesture.MAS.h() >= 0) {
                i7 = me.hisn.mygesture.MAS.h();
                i5 = 0 - i7;
                iG = 0;
            } else {
                i6 = me.hisn.mygesture.P.k0 - iMin;
                iH = me.hisn.mygesture.MAS.i();
                i5 = i6 - iH;
                iG = 0;
            }
        } else if (i == me.hisn.mygesture.P.R) {
            i2 = me.hisn.mygesture.R.mipmap.right_light;
            i3 = me.hisn.mygesture.P.l0;
            i4 = (me.hisn.mygesture.MAS.i() >= 0 ? 8388613 : 8388611) | 48;
            if (me.hisn.mygesture.MAS.i() >= 0) {
                i7 = me.hisn.mygesture.MAS.i();
                i5 = 0 - i7;
                iG = 0;
            } else {
                i6 = me.hisn.mygesture.P.k0 - iMin;
                iH = me.hisn.mygesture.MAS.h();
                i5 = i6 - iH;
                iG = 0;
            }
        } else if (i == me.hisn.mygesture.P.B) {
            i2 = me.hisn.mygesture.R.mipmap.bottom_light;
            int i8 = me.hisn.mygesture.P.k0;
            i4 = (me.hisn.mygesture.MAS.g() >= 0 ? 80 : 48) | 17;
            iG = me.hisn.mygesture.MAS.g() >= 0 ? 0 - me.hisn.mygesture.MAS.g() : (me.hisn.mygesture.P.l0 - iMin) - me.hisn.mygesture.MAS.j();
            i5 = 0;
            iMin = i8;
            i3 = iMin;
        } else {
            i2 = 0;
            i3 = 0;
            iMin = 0;
            i4 = 0;
            i5 = 0;
            iG = 0;
        }
        android.view.WindowManager.LayoutParams layoutParams = this.e;
        layoutParams.gravity = i4;
        layoutParams.width = iMin;
        layoutParams.height = i3;
        layoutParams.x = i5;
        layoutParams.y = iG;
        android.graphics.drawable.Drawable drawable = context.getResources().getDrawable(i2, null);
        drawable.setTint(me.hisn.mygesture.P.w == 2 ? me.hisn.utils.k.a() : me.hisn.mygesture.P.T);
        this.f577c.setBackground(drawable);
        if (me.hisn.mygesture.P.C == null && me.hisn.mygesture.m.q != null) {
            me.hisn.mygesture.m.q = new me.hisn.mypanel.c().a();
        }
        try {
            this.f577c.setAlpha(0.0f);
            this.f577c.setVisibility(0);
            this.f576b.addView(this.f577c, this.e);
            this.d = true;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @Override // me.hisn.mygesture.e
    public void b() {
        if (this.d) {
            try {
                this.f577c.setVisibility(8);
                this.f576b.removeView(this.f577c);
                this.d = false;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    void c() {
        if (this.d) {
            try {
                this.f576b.removeView(this.f577c);
                this.d = false;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    void d() {
        this.e = new me.hisn.utils.v().a(false, 0, 0, 0, 0, 0, me.hisn.mygesture.R.style.light_anim, false);
    }
}
