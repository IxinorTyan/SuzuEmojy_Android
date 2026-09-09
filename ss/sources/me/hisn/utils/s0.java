package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class s0 {

    class a implements me.hisn.mygesture.a.h {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.x0 f841a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f842b;

        a(me.hisn.utils.x0 x0Var, int i) {
            this.f841a = x0Var;
            this.f842b = i;
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view, int i, int i2, int i3, int i4) {
            this.f841a.a(this.f842b, i, i2, i3, i4);
        }

        @Override // me.hisn.mygesture.a.h
        public void b(android.view.View view, int i, int i2, int i3, int i4) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        }
    }

    class b extends me.hisn.utils.j {
        b() {
        }

        @Override // me.hisn.utils.j
        public void b() {
        }
    }

    class c implements me.hisn.mygesture.a.h {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.j f843a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.content.Context f844b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f845c;

        c(me.hisn.utils.j jVar, android.content.Context context, int i) {
            this.f843a = jVar;
            this.f844b = context;
            this.f845c = i;
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view, int i, int i2, int i3, int i4) {
            this.f843a.a(this.f844b.getApplicationContext(), this.f845c, i, i2, i3, i4);
        }

        @Override // me.hisn.mygesture.a.h
        public void b(android.view.View view, int i, int i2, int i3, int i4) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        }
    }

    class d implements me.hisn.mygesture.a.h {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.mypanel.g f846a;

        d(me.hisn.mypanel.g gVar) {
            this.f846a = gVar;
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
            this.f846a.a(false);
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view, int i, int i2, int i3, int i4) {
            this.f846a.a(i3, i4, i, i2);
        }

        @Override // me.hisn.mygesture.a.h
        public void b(android.view.View view, int i, int i2, int i3, int i4) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
            this.f846a.a(true);
        }
    }

    class e implements me.hisn.mygesture.a.h {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.mygesture.i f847a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f848b;

        e(me.hisn.mygesture.i iVar, int i) {
            this.f847a = iVar;
            this.f848b = i;
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
            this.f847a.a(false);
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view, int i, int i2, int i3, int i4) {
            this.f847a.a(i, i2, i3, i4, this.f848b);
        }

        @Override // me.hisn.mygesture.a.h
        public void b(android.view.View view, int i, int i2, int i3, int i4) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
            this.f847a.a(true);
        }
    }

    class f implements me.hisn.mygesture.a.h {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.p f849a;

        f(me.hisn.utils.p pVar) {
            this.f849a = pVar;
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
            this.f849a.a(0, 0, 0, 0, false);
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view, int i, int i2, int i3, int i4) {
            this.f849a.a(i, i2, i3, i4);
        }

        @Override // me.hisn.mygesture.a.h
        public void b(android.view.View view, int i, int i2, int i3, int i4) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
            this.f849a.a(i, i2, i3, i4, true);
        }
    }

    class g implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f850a;

        g(int i) {
            this.f850a = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.MAS.b(this.f850a);
        }
    }

    class h implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f851a;

        h(int i) {
            this.f851a = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.MAS.b(this.f851a);
        }
    }

    class i implements me.hisn.mypanel.CaptureService.c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f852a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f853b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f854c;
        final /* synthetic */ int[] d;
        final /* synthetic */ java.lang.String e;

        i(android.content.Context context, android.view.View view, int i, int[] iArr, java.lang.String str) {
            this.f852a = context;
            this.f853b = view;
            this.f854c = i;
            this.d = iArr;
            this.e = str;
        }

        @Override // me.hisn.mypanel.CaptureService.c
        public void a(android.graphics.Bitmap bitmap) {
            me.hisn.utils.s0.a(this.f852a, 11, this.f853b, (android.os.Bundle) null, this.f854c, this.d, this.e);
        }
    }

    class j implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f855a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.content.Context f856b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f857c;
        final /* synthetic */ int d;

        class a extends me.hisn.utils.r {
            a(me.hisn.utils.s0.j jVar) {
            }

            @Override // me.hisn.utils.g0
            public void c(android.view.View view) {
                if (me.hisn.mygesture.MAS.k() != null) {
                    try {
                        me.hisn.mygesture.MAS.k().removeView(view);
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        class b extends me.hisn.utils.w {
            b(me.hisn.utils.s0.j jVar) {
            }

            @Override // me.hisn.utils.g0
            public void c(android.view.View view) {
                if (me.hisn.mygesture.MAS.k() != null) {
                    try {
                        me.hisn.mygesture.MAS.k().removeView(view);
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        class c implements me.hisn.mypanel.CaptureService.c {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.View f858a;

            class a implements java.lang.Runnable {

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                final /* synthetic */ android.widget.ImageView f860a;

                /* JADX INFO: renamed from: b, reason: collision with root package name */
                final /* synthetic */ android.graphics.Bitmap f861b;

                a(android.widget.ImageView imageView, android.graphics.Bitmap bitmap) {
                    this.f860a = imageView;
                    this.f861b = bitmap;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.f860a.setBackground(new me.hisn.utils.i().a(me.hisn.utils.s0.j.this.f856b, this.f861b, 10, true, false));
                }
            }

            c(android.view.View view) {
                this.f858a = view;
            }

            @Override // me.hisn.mypanel.CaptureService.c
            public void a(android.graphics.Bitmap bitmap) {
                android.widget.ImageView imageView = (android.widget.ImageView) this.f858a.findViewById(me.hisn.mygesture.R.id.blur_back);
                imageView.post(new me.hisn.utils.s0.j.c.a(imageView, bitmap));
            }
        }

        class d implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.View f863a;

            d(me.hisn.utils.s0.j jVar, android.view.View view) {
                this.f863a = view;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (me.hisn.mygesture.MAS.k() != null) {
                    try {
                        me.hisn.mygesture.MAS.k().addView(this.f863a, this.f863a.getLayoutParams());
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        j(int i, android.content.Context context, int i2, int i3) {
            this.f855a = i;
            this.f856b = context;
            this.f857c = i2;
            this.d = i3;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.View viewA = (this.f855a == 1 ? new me.hisn.utils.s0.j.a(this) : new me.hisn.utils.s0.j.b(this)).a(this.f856b, this.f857c);
            if (this.d == 4) {
                me.hisn.mypanel.CaptureService.a(this.f856b, new me.hisn.utils.s0.j.c(viewA), new android.graphics.Point(me.hisn.mygesture.P.k0 / 12, me.hisn.mygesture.P.l0 / 12));
            }
            if (me.hisn.mygesture.MAS.d() != null) {
                me.hisn.mygesture.MAS.d().post(new me.hisn.utils.s0.j.d(this, viewA));
            }
        }
    }

    class k implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f864a;

        k(android.content.Context context) {
            this.f864a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.mypanel.h(this.f864a.getApplicationContext()).a(-1, true);
        }
    }

    class l implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f865a;

        l(android.content.Context context) {
            this.f865a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.mypanel.h(this.f865a.getApplicationContext()).b(-1, true);
        }
    }

    class m implements me.hisn.mypanel.CaptureService.c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f866a;

        class a extends me.hisn.mypanel.b {
            a(me.hisn.utils.s0.m mVar) {
            }

            @Override // me.hisn.mypanel.b
            public void a(android.view.View view) {
                ((android.view.WindowManager) java.util.Objects.requireNonNull(me.hisn.mygesture.MAS.k())).removeView(view);
            }
        }

        class b implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.View f867a;

            b(me.hisn.utils.s0.m mVar, android.view.View view) {
                this.f867a = view;
            }

            @Override // java.lang.Runnable
            public void run() {
                android.view.WindowManager windowManager = (android.view.WindowManager) java.util.Objects.requireNonNull(me.hisn.mygesture.MAS.k());
                android.view.View view = this.f867a;
                windowManager.addView(view, view.getLayoutParams());
            }
        }

        m(android.content.Context context) {
            this.f866a = context;
        }

        @Override // me.hisn.mypanel.CaptureService.c
        public void a(android.graphics.Bitmap bitmap) {
            if (bitmap != null) {
                android.view.View viewA = new me.hisn.utils.s0.m.a(this).a(bitmap, this.f866a);
                if (me.hisn.mygesture.MAS.d() != null) {
                    me.hisn.mygesture.MAS.d().post(new me.hisn.utils.s0.m.b(this, viewA));
                }
            }
        }
    }

    class n implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f868a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f869b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f870c;

        class a extends me.hisn.appdrawer.a {
            a(me.hisn.utils.s0.n nVar) {
            }

            @Override // me.hisn.appdrawer.a
            public void a(android.view.View view) {
                if (me.hisn.mygesture.MAS.k() != null) {
                    me.hisn.mygesture.MAS.k().removeView(view);
                }
            }
        }

        class b implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.View f871a;

            b(me.hisn.utils.s0.n nVar, android.view.View view) {
                this.f871a = view;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (me.hisn.mygesture.MAS.k() != null) {
                    android.view.WindowManager windowManagerK = me.hisn.mygesture.MAS.k();
                    android.view.View view = this.f871a;
                    windowManagerK.addView(view, view.getLayoutParams());
                }
            }
        }

        n(android.content.Context context, int i, int i2) {
            this.f868a = context;
            this.f869b = i;
            this.f870c = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.View viewA = new me.hisn.utils.s0.n.a(this).a(this.f868a, this.f869b, this.f870c);
            if (me.hisn.mygesture.MAS.d() != null) {
                me.hisn.mygesture.MAS.d().post(new me.hisn.utils.s0.n.b(this, viewA));
            }
        }
    }

    class o implements java.lang.Runnable {
        o() {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.MAS.b(3);
        }
    }

    public static void a(android.content.Context context, int i2, android.view.View view, android.os.Bundle bundle, int i3, int[] iArr, java.lang.String str) {
        android.content.Context applicationContext;
        int i4;
        java.lang.Thread thread;
        me.hisn.utils.s sVar;
        android.content.Intent intent;
        me.hisn.utils.s sVar2;
        android.content.Intent intent2;
        me.hisn.mygesture.a.h aVar;
        if (i2 == 0) {
        }
        int iIntValue = view.getTag() != null ? ((java.lang.Integer) view.getTag()).intValue() : me.hisn.mygesture.P.f536c;
        switch (i2) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                new java.lang.Thread(new me.hisn.utils.s0.g(i2)).start();
                break;
            case 2:
                if (!me.hisn.mygesture.P.e0) {
                    android.content.Intent intent3 = new android.content.Intent("android.intent.action.MAIN");
                    intent3.addCategory("android.intent.category.HOME");
                    new me.hisn.utils.s().a(view.getContext(), intent3, bundle, null);
                } else {
                    me.hisn.mygesture.MAS.b(i2);
                }
                break;
            case 9:
                me.hisn.mygesture.MAS.b(view);
                if (me.hisn.mygesture.MAS.d() != null) {
                    me.hisn.mygesture.MAS.d().postDelayed(new me.hisn.utils.s0.h(i2), 300L);
                }
                break;
            case 10:
                me.hisn.mygesture.MAS.b(view);
                me.hisn.mygesture.MAS.d(true);
                break;
            case 11:
                android.content.SharedPreferences sharedPreferences = context.getSharedPreferences("my_panel", 0);
                int i5 = sharedPreferences.getInt("background_type", 0);
                int i6 = sharedPreferences.getInt("41420", 0);
                if (i5 != 4 || me.hisn.mypanel.CaptureService.a(context, new me.hisn.utils.s0.i(context, view, i3, iArr, str))) {
                    new java.lang.Thread(new me.hisn.utils.s0.j(i6, context, i3, i5)).start();
                }
                break;
            case 12:
                android.content.Intent intentB = me.hisn.utils.l0.b(context.getApplicationContext(), me.hisn.mygesture.MAS.e());
                if (intentB == null) {
                    new me.hisn.utils.b0().a(context.getApplicationContext(), me.hisn.mygesture.R.string.no_prev_app, 0);
                } else {
                    new me.hisn.utils.s().a(view.getContext(), intentB, bundle, ((android.content.ComponentName) java.util.Objects.requireNonNull(intentB.getComponent())).getPackageName());
                }
                break;
            case 13:
                applicationContext = context.getApplicationContext();
                i4 = 82;
                me.hisn.utils.y.a(applicationContext, i4);
                break;
            case 14:
                applicationContext = context.getApplicationContext();
                i4 = 81;
                me.hisn.utils.y.a(applicationContext, i4);
                break;
            case 15:
                applicationContext = context.getApplicationContext();
                i4 = 80;
                me.hisn.utils.y.a(applicationContext, i4);
                break;
            case 16:
                thread = new java.lang.Thread(new me.hisn.utils.s0.k(context));
                thread.start();
                break;
            case 17:
                if (android.os.Build.VERSION.SDK_INT < 29) {
                    thread = new java.lang.Thread(new me.hisn.utils.s0.l(context));
                    thread.start();
                } else {
                    me.hisn.utils.d dVar = new me.hisn.utils.d();
                    if (dVar.c(context.getApplicationContext()) >= 6) {
                        dVar.f(context);
                    } else {
                        try {
                            context.startActivity(new android.content.Intent("android.settings.panel.action.WIFI").addFlags(268435456));
                        } catch (java.lang.Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                }
                break;
            case 18:
                new me.hisn.utils.r0().a(context);
                break;
            case 19:
                me.hisn.mygesture.MAS.b(view);
                me.hisn.mypanel.CaptureService.a(context, new me.hisn.utils.s0.m(context), new android.graphics.Point(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0));
                break;
            case 20:
                new me.hisn.utils.n().a(context);
                break;
            case 21:
                sVar = new me.hisn.utils.s();
                intent = new android.content.Intent(context, (java.lang.Class<?>) me.hisn.utils.InputMethodPicker.class);
                sVar.a(context, intent, null, null);
                break;
            case 22:
                sVar = new me.hisn.utils.s();
                intent = new android.content.Intent("android.intent.action.ASSIST");
                sVar.a(context, intent, null, null);
                break;
            case 23:
                new me.hisn.utils.i0().c(context);
                break;
            case 24:
                new me.hisn.utils.i0().a(context);
                break;
            case 25:
                new me.hisn.utils.i0().b(context);
                break;
            case 26:
                thread = new java.lang.Thread(new me.hisn.utils.s0.n(context, iIntValue, i3));
                thread.start();
                break;
            case 27:
                me.hisn.mygesture.MAS.b(3);
                view.postDelayed(new me.hisn.utils.s0.o(), 300L);
                break;
            case 28:
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    sVar2 = new me.hisn.utils.s();
                    intent2 = new android.content.Intent("android.settings.panel.action.INTERNET_CONNECTIVITY");
                    sVar2.a(context, intent2, bundle, null);
                }
                break;
            case 29:
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    sVar2 = new me.hisn.utils.s();
                    intent2 = new android.content.Intent("android.settings.panel.action.VOLUME");
                    sVar2.a(context, intent2, bundle, null);
                }
                break;
            case 30:
                new me.hisn.utils.y0().a(context, me.hisn.mygesture.MAS.e(), true);
                break;
            case 31:
                new me.hisn.utils.m().a(context.getApplicationContext());
                break;
            case 32:
                aVar = new me.hisn.utils.s0.a(new me.hisn.utils.x0(context.getApplicationContext(), 3, 1), i3);
                me.hisn.mygesture.MAS.a(aVar);
                break;
            case 33:
                me.hisn.utils.s0.b bVar = new me.hisn.utils.s0.b();
                bVar.c(context.getApplicationContext());
                new me.hisn.utils.w0().a(context, me.hisn.mygesture.R.drawable.brightness_view);
                me.hisn.mygesture.MAS.a(new me.hisn.utils.s0.c(bVar, context, i3));
                break;
            case 34:
                new me.hisn.utils.u().a();
                break;
            case 35:
                me.hisn.mypanel.g gVar = new me.hisn.mypanel.g();
                gVar.a(view.getContext(), iArr);
                me.hisn.mygesture.MAS.a(new me.hisn.utils.s0.d(gVar));
                break;
            case 36:
                me.hisn.mygesture.i iVar = new me.hisn.mygesture.i();
                iVar.a(context);
                aVar = new me.hisn.utils.s0.e(iVar, i3);
                me.hisn.mygesture.MAS.a(aVar);
                break;
            case 37:
                me.hisn.utils.p pVar = new me.hisn.utils.p();
                me.hisn.mygesture.MAS.a(new me.hisn.utils.s0.f(pVar));
                pVar.a(context, iArr[0], iArr[1], i3);
                break;
            case 38:
                new me.hisn.utils.q0().a(i3, view, iIntValue);
                break;
            case 39:
                if (str != null) {
                    new me.hisn.utils.v0(view.getContext().getApplicationContext(), str, iIntValue).a(iArr[0], iArr[1], 0, 0);
                }
                break;
        }
    }

    public static void a(android.content.Context context, java.lang.String str, android.view.View view, android.os.Bundle bundle, android.content.SharedPreferences sharedPreferences, int i2, int[] iArr) {
        int i3 = sharedPreferences.getInt(str + "_k", 0);
        if (i3 == 0) {
        }
        if (a(context, i3)) {
            new me.hisn.utils.b0().a(context.getApplicationContext(), me.hisn.mygesture.R.string.please_unlock);
            return;
        }
        switch (i3) {
            case 1068078049:
                java.lang.String string = sharedPreferences.getString(str + "_p", "");
                android.content.ComponentName componentName = new android.content.ComponentName(string, sharedPreferences.getString(str + "_c", ""));
                android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setComponent(componentName);
                intent.addFlags(1048576);
                if ("me.hisn.letterslauncher".equals(string)) {
                    bundle = new me.hisn.utils.z0().a(context.getApplicationContext(), me.hisn.mygesture.R.anim.windows_in, me.hisn.mygesture.R.anim.windows_out);
                }
                new me.hisn.utils.s().a(context, intent, bundle, string);
                if (me.hisn.mygesture.P.C == null) {
                    new me.hisn.mypanel.c().a();
                }
                break;
            case 1068078050:
                byte[] bArrDecode = android.util.Base64.decode(sharedPreferences.getString(str + "_s", ""), 0);
                android.os.Bundle bundle2 = new android.os.Bundle();
                android.os.Parcel parcelObtain = android.os.Parcel.obtain();
                parcelObtain.unmarshall(bArrDecode, 0, bArrDecode.length);
                parcelObtain.setDataPosition(0);
                bundle2.readFromParcel(parcelObtain);
                new me.hisn.utils.s().a(context, (android.content.Intent) bundle2.get("android.intent.extra.shortcut.INTENT"), bundle, null);
                break;
            default:
                a(context, i3, view, bundle, i2, iArr, str);
                break;
        }
    }

    private static boolean a(android.content.Context context, int i2) {
        return me.hisn.utils.b.f(i2) && me.hisn.utils.C.CP(context, new me.hisn.utils.p0().b(context), 0) < 0;
    }
}
