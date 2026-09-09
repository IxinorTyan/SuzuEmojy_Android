package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.lang.String f419a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final org.json.JSONObject f420b;

    public k(java.lang.String str) {
        this.f419a = str;
        org.json.JSONObject jSONObject = new org.json.JSONObject(this.f419a);
        this.f420b = jSONObject;
        if (android.text.TextUtils.isEmpty(jSONObject.optString("productId"))) {
            throw new java.lang.IllegalArgumentException("SKU cannot be empty.");
        }
        if (android.text.TextUtils.isEmpty(this.f420b.optString("type"))) {
            throw new java.lang.IllegalArgumentException("SkuType cannot be empty.");
        }
    }

    public java.lang.String a() {
        return this.f419a;
    }

    public java.lang.String b() {
        return this.f420b.optString("productId");
    }

    public java.lang.String c() {
        return this.f420b.optString("type");
    }

    public int d() {
        return this.f420b.optInt("offer_type");
    }

    public java.lang.String e() {
        return this.f420b.optString("offer_id");
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof com.android.billingclient.api.k) {
            return android.text.TextUtils.equals(this.f419a, ((com.android.billingclient.api.k) obj).f419a);
        }
        return false;
    }

    public final java.lang.String f() {
        return this.f420b.optString("packageName");
    }

    public java.lang.String g() {
        return this.f420b.optString("serializedDocid");
    }

    final java.lang.String h() {
        return this.f420b.optString("skuDetailsToken");
    }

    public int hashCode() {
        return this.f419a.hashCode();
    }

    public java.lang.String toString() {
        java.lang.String strValueOf = java.lang.String.valueOf(this.f419a);
        return strValueOf.length() != 0 ? "SkuDetails: ".concat(strValueOf) : new java.lang.String("SkuDetails: ");
    }
}
