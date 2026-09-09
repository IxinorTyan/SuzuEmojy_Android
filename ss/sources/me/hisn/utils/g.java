package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.android.billingclient.api.c f741a;

    class a implements com.android.billingclient.api.j {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f742a;

        a(android.app.Activity activity) {
            this.f742a = activity;
        }

        @Override // com.android.billingclient.api.j
        public void a(com.android.billingclient.api.g gVar, java.util.List<com.android.billingclient.api.h> list) {
            if ((list == null || gVar.a() != 0) && gVar.a() != 7) {
                me.hisn.utils.g.this.f741a.a();
            } else {
                me.hisn.utils.g.this.a(this.f742a, true, false);
            }
        }
    }

    class b implements com.android.billingclient.api.e {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f744a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ boolean f745b;

        b(android.app.Activity activity, boolean z) {
            this.f744a = activity;
            this.f745b = z;
        }

        @Override // com.android.billingclient.api.e
        public void a() {
            me.hisn.utils.g gVar = me.hisn.utils.g.this;
            android.app.Activity activity = this.f744a;
            gVar.a(activity, activity.getString(me.hisn.mygesture.R.string.error_try_again));
        }

        @Override // com.android.billingclient.api.e
        public void a(com.android.billingclient.api.g gVar) {
            me.hisn.utils.g gVar2 = me.hisn.utils.g.this;
            android.app.Activity activity = this.f744a;
            boolean z = this.f745b;
            gVar2.a(activity, z, z);
        }
    }

    class c implements com.android.billingclient.api.i {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ boolean f747a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ boolean f748b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f749c;

        class a implements com.android.billingclient.api.b {
            a() {
            }

            @Override // com.android.billingclient.api.b
            public void a(com.android.billingclient.api.g gVar) {
                me.hisn.utils.g.c cVar = me.hisn.utils.g.c.this;
                if (cVar.f747a) {
                    return;
                }
                me.hisn.utils.g.this.f741a.a();
            }
        }

        class b implements java.lang.Runnable {
            b() {
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.utils.g.c.this.f749c.recreate();
            }
        }

        c(boolean z, boolean z2, android.app.Activity activity) {
            this.f747a = z;
            this.f748b = z2;
            this.f749c = activity;
        }

        @Override // com.android.billingclient.api.i
        public void a(com.android.billingclient.api.g gVar, java.util.List<com.android.billingclient.api.h> list) {
            boolean z = !this.f747a;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (gVar.a() != 0) {
                if (this.f748b) {
                    me.hisn.utils.g gVar2 = me.hisn.utils.g.this;
                    android.app.Activity activity = this.f749c;
                    gVar2.a(activity, activity.getString(me.hisn.mygesture.R.string.error_try_again));
                }
            } else if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    com.android.billingclient.api.h hVar = list.get(i);
                    if (hVar.b() == 1) {
                        arrayList.add(hVar.e().get(0));
                        if (!hVar.f()) {
                            com.android.billingclient.api.a.C0016a c0016aB = com.android.billingclient.api.a.b();
                            c0016aB.a(hVar.c());
                            me.hisn.utils.g.this.f741a.a(c0016aB.a(), new me.hisn.utils.g.c.a());
                            z = false;
                        }
                    }
                }
            }
            if (me.hisn.utils.C.PA(this.f749c.getApplicationContext(), (java.lang.String[]) arrayList.toArray(new java.lang.String[0]), null)) {
                if (this.f748b) {
                    me.hisn.utils.g gVar3 = me.hisn.utils.g.this;
                    android.app.Activity activity2 = this.f749c;
                    gVar3.a(activity2, activity2.getString(me.hisn.mygesture.R.string.actived_pro));
                    me.hisn.utils.g.this.f741a.a();
                    this.f749c.runOnUiThread(new me.hisn.utils.g.c.b());
                }
            } else if (this.f747a) {
                me.hisn.utils.g.this.a(this.f749c);
            } else if (this.f748b) {
                me.hisn.utils.g gVar4 = me.hisn.utils.g.this;
                android.app.Activity activity3 = this.f749c;
                gVar4.a(activity3, activity3.getString(me.hisn.mygesture.R.string.locked_text));
            }
            if (z) {
                me.hisn.utils.g.this.f741a.a();
            }
        }
    }

    class d implements com.android.billingclient.api.m {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f752a;

        d(android.app.Activity activity) {
            this.f752a = activity;
        }

        @Override // com.android.billingclient.api.m
        public void a(com.android.billingclient.api.g gVar, java.util.List<com.android.billingclient.api.k> list) {
            if (list == null || list.size() != 1) {
                return;
            }
            com.android.billingclient.api.f.a aVarH = com.android.billingclient.api.f.h();
            aVarH.a(list.get(0));
            if (me.hisn.utils.g.this.f741a.a(this.f752a, aVarH.a()).a() != 0) {
                me.hisn.utils.g gVar2 = me.hisn.utils.g.this;
                android.app.Activity activity = this.f752a;
                gVar2.a(activity, activity.getString(me.hisn.mygesture.R.string.error_try_again));
            }
        }
    }

    class e implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f754a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ java.lang.String f755b;

        e(me.hisn.utils.g gVar, android.app.Activity activity, java.lang.String str) {
            this.f754a = activity;
            this.f755b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.utils.b0().a(this.f754a.getApplicationContext(), this.f755b, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.app.Activity activity) {
        if (a((android.content.Context) activity)) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(me.hisn.utils.C.GS(activity.getApplicationContext()));
        com.android.billingclient.api.l.a aVarC = com.android.billingclient.api.l.c();
        aVarC.a(arrayList);
        aVarC.a("inapp");
        this.f741a.a(aVarC.a(), new me.hisn.utils.g.d(activity));
    }

    private void a(android.app.Activity activity, com.android.billingclient.api.e eVar) {
        me.hisn.utils.g.a aVar = new me.hisn.utils.g.a(activity);
        com.android.billingclient.api.c.a aVarA = com.android.billingclient.api.c.a(activity);
        aVarA.a(aVar);
        aVarA.b();
        com.android.billingclient.api.c cVarA = aVarA.a();
        this.f741a = cVarA;
        cVarA.a(eVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.app.Activity activity, java.lang.String str) {
        activity.runOnUiThread(new me.hisn.utils.g.e(this, activity, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.app.Activity activity, boolean z, boolean z2) {
        this.f741a.a("inapp", new me.hisn.utils.g.c(z2, z, activity));
    }

    private boolean a(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setType("application/vnd.luckypatcher.archive");
        return context.getPackageManager().queryIntentActivities(intent, 0).size() > 0;
    }

    public synchronized void a(android.app.Activity activity, boolean z) {
        if (z) {
            a(activity, activity.getString(me.hisn.mygesture.R.string.checking_text));
        }
        if (this.f741a == null) {
            a(activity, new me.hisn.utils.g.b(activity, z));
        } else if (this.f741a.b()) {
            a(activity, z, z);
        }
    }
}
