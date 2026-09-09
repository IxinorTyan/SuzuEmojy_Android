package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
final class c0 extends android.content.BroadcastReceiver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final com.android.billingclient.api.j f384a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f385b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final /* synthetic */ com.android.billingclient.api.d0 f386c;

    /* synthetic */ c0(com.android.billingclient.api.d0 d0Var, com.android.billingclient.api.j jVar, com.android.billingclient.api.b0 b0Var) {
        this.f386c = d0Var;
        this.f384a = jVar;
    }

    public final void a(android.content.Context context) {
        if (!this.f385b) {
            b.a.a.a.a.a.a.b("BillingBroadcastManager", "Receiver is not registered.");
        } else {
            context.unregisterReceiver(this.f386c.f391b);
            this.f385b = false;
        }
    }

    public final void a(android.content.Context context, android.content.IntentFilter intentFilter) {
        if (this.f385b) {
            return;
        }
        context.registerReceiver(this.f386c.f391b, intentFilter);
        this.f385b = true;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(android.content.Context context, android.content.Intent intent) {
        this.f384a.a(b.a.a.a.a.a.a.a(intent, "BillingBroadcastManager"), b.a.a.a.a.a.a.a(intent.getExtras()));
    }
}
