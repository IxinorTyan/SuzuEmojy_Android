package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
final class d0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final android.content.Context f390a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final com.android.billingclient.api.c0 f391b;

    d0(android.content.Context context, com.android.billingclient.api.j jVar) {
        this.f390a = context;
        this.f391b = new com.android.billingclient.api.c0(this, jVar, null);
    }

    final com.android.billingclient.api.j a() {
        return this.f391b.f384a;
    }

    final void b() {
        this.f391b.a(this.f390a);
    }

    final void c() {
        this.f391b.a(this.f390a, new android.content.IntentFilter("com.android.vending.billing.PURCHASES_UPDATED"));
    }
}
