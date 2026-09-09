package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class y0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static int f884a;

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ boolean f885a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.content.Context f886b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.content.Intent f887c;
        final /* synthetic */ android.app.ActivityOptions d;

        a(me.hisn.utils.y0 y0Var, boolean z, android.content.Context context, android.content.Intent intent, android.app.ActivityOptions activityOptions) {
            this.f885a = z;
            this.f886b = context;
            this.f887c = intent;
            this.d = activityOptions;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.f885a) {
                android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
                intent.addFlags(270532608);
                intent.addFlags(65536);
                intent.addCategory("android.intent.category.HOME");
                try {
                    this.f886b.startActivity(intent, null);
                    java.lang.Thread.sleep(300L);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
            if (me.hisn.utils.y0.f884a > 0) {
                this.f887c.setPackage(null);
                int unused = me.hisn.utils.y0.f884a = 0;
            } else {
                me.hisn.utils.y0.b();
            }
            this.f887c.addFlags(402722816);
            this.f887c.addCategory("android.intent.category.DEFAULT");
            try {
                this.f886b.startActivity(this.f887c, this.d.toBundle());
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ int b() {
        int i = f884a;
        f884a = i + 1;
        return i;
    }

    public void a(android.content.Context context, java.lang.String str, boolean z) {
        if (android.os.Build.VERSION.SDK_INT < 24) {
            return;
        }
        android.app.ActivityOptions activityOptionsMakeBasic = android.app.ActivityOptions.makeBasic();
        try {
            android.app.ActivityOptions.class.getMethod("setLaunchWindowingMode", java.lang.Integer.TYPE).invoke(activityOptionsMakeBasic, 5);
        } catch (java.lang.IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (java.lang.reflect.InvocationTargetException e3) {
            e3.printStackTrace();
        }
        int iMin = java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) - 100;
        activityOptionsMakeBasic.setLaunchBounds(new android.graphics.Rect((me.hisn.mygesture.P.k0 - iMin) / 2, (me.hisn.mygesture.P.l0 - iMin) / 2, (me.hisn.mygesture.P.k0 + iMin) / 2, (me.hisn.mygesture.P.l0 + iMin) / 2));
        android.content.Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            new java.lang.Thread(new me.hisn.utils.y0.a(this, z, context, launchIntentForPackage, activityOptionsMakeBasic)).start();
        }
    }
}
