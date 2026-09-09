package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public final class z {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f445a;

    /* synthetic */ z(com.android.billingclient.api.y yVar) {
    }

    public final com.android.billingclient.api.a0 a() {
        if (android.text.TextUtils.isEmpty(this.f445a)) {
            throw new java.lang.IllegalArgumentException("SKU must be set.");
        }
        return new com.android.billingclient.api.a0(this.f445a, null, null);
    }

    public final com.android.billingclient.api.z a(java.lang.String str) {
        this.f445a = str;
        return this;
    }
}
