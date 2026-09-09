package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
final class s implements android.content.ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.lang.Object f439a = new java.lang.Object();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f440b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private com.android.billingclient.api.e f441c;
    final /* synthetic */ com.android.billingclient.api.d d;

    /* synthetic */ s(com.android.billingclient.api.d dVar, com.android.billingclient.api.e eVar, com.android.billingclient.api.o0 o0Var) {
        this.d = dVar;
        this.f441c = eVar;
    }

    private final void a(com.android.billingclient.api.g gVar) {
        synchronized (this.f439a) {
            com.android.billingclient.api.e eVar = this.f441c;
            if (eVar != null) {
                eVar.a(gVar);
            }
        }
    }

    public final /* synthetic */ java.lang.Object a() {
        int iA;
        synchronized (this.f439a) {
            if (this.f440b) {
                return null;
            }
            int i = 3;
            try {
                java.lang.String packageName = this.d.e.getPackageName();
                int i2 = 16;
                iA = 3;
                while (true) {
                    if (i2 < 3) {
                        i2 = 0;
                        break;
                    }
                    try {
                        iA = this.d.f.a(i2, packageName, "subs");
                        if (iA == 0) {
                            break;
                        }
                        i2--;
                    } catch (java.lang.Exception unused) {
                        i = iA;
                        b.a.a.a.a.a.a.b("BillingClient", "Exception while checking if billing is supported; try to reconnect");
                        this.d.f387a = 0;
                        this.d.f = null;
                        iA = i;
                    }
                }
                boolean z = true;
                this.d.i = i2 >= 5;
                this.d.h = i2 >= 3;
                if (i2 < 3) {
                    b.a.a.a.a.a.a.a("BillingClient", "In-app billing API does not support subscription on this device.");
                }
                for (int i3 = 16; i3 >= 3; i3--) {
                    iA = this.d.f.a(i3, packageName, "inapp");
                    if (iA == 0) {
                        this.d.j = i3;
                        break;
                    }
                }
                com.android.billingclient.api.d dVar = this.d;
                dVar.r = dVar.j >= 16;
                com.android.billingclient.api.d dVar2 = this.d;
                dVar2.q = dVar2.j >= 15;
                com.android.billingclient.api.d dVar3 = this.d;
                dVar3.p = dVar3.j >= 14;
                com.android.billingclient.api.d dVar4 = this.d;
                dVar4.o = dVar4.j >= 12;
                com.android.billingclient.api.d dVar5 = this.d;
                dVar5.n = dVar5.j >= 10;
                com.android.billingclient.api.d dVar6 = this.d;
                dVar6.m = dVar6.j >= 9;
                com.android.billingclient.api.d dVar7 = this.d;
                dVar7.l = dVar7.j >= 8;
                com.android.billingclient.api.d dVar8 = this.d;
                if (dVar8.j < 6) {
                    z = false;
                }
                dVar8.k = z;
                if (this.d.j < 3) {
                    b.a.a.a.a.a.a.b("BillingClient", "In-app billing API version 3 is not supported on this device.");
                }
                if (iA == 0) {
                    this.d.f387a = 2;
                } else {
                    this.d.f387a = 0;
                    this.d.f = null;
                }
            } catch (java.lang.Exception unused2) {
            }
            a(iA == 0 ? com.android.billingclient.api.v.k : com.android.billingclient.api.v.f442a);
            return null;
        }
    }

    public final /* synthetic */ void b() {
        this.d.f387a = 0;
        this.d.f = null;
        a(com.android.billingclient.api.v.m);
    }

    final void c() {
        synchronized (this.f439a) {
            this.f441c = null;
            this.f440b = true;
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        b.a.a.a.a.a.a.a("BillingClient", "Billing service connected.");
        this.d.f = b.a.a.a.a.a.c.a(iBinder);
        if (this.d.a(new java.util.concurrent.Callable() { // from class: com.android.billingclient.api.r
            @Override // java.util.concurrent.Callable
            public final java.lang.Object call() {
                this.f438a.a();
                return null;
            }
        }, 30000L, new java.lang.Runnable() { // from class: com.android.billingclient.api.q
            @Override // java.lang.Runnable
            public final void run() {
                this.f437a.b();
            }
        }, this.d.c()) == null) {
            a(this.d.d());
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(android.content.ComponentName componentName) {
        b.a.a.a.a.a.a.b("BillingClient", "Billing service disconnected.");
        this.d.f = null;
        this.d.f387a = 0;
        synchronized (this.f439a) {
            com.android.billingclient.api.e eVar = this.f441c;
            if (eVar != null) {
                eVar.a();
            }
        }
    }
}
