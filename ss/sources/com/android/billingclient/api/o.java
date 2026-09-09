package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
final class o implements java.util.concurrent.Callable<java.lang.Void> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ java.lang.String f432a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final /* synthetic */ com.android.billingclient.api.i f433b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final /* synthetic */ com.android.billingclient.api.d f434c;

    o(com.android.billingclient.api.d dVar, java.lang.String str, com.android.billingclient.api.i iVar) {
        this.f434c = dVar;
        this.f432a = str;
        this.f433b = iVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ java.lang.Void call() {
        com.android.billingclient.api.h.a aVarA = com.android.billingclient.api.d.a(this.f434c, this.f432a);
        if (aVarA.b() != null) {
            this.f433b.a(aVarA.a(), aVarA.b());
            return null;
        }
        this.f433b.a(aVarA.a(), b.a.a.a.a.a.p.d());
        return null;
    }
}
