package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.lang.String f406a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.lang.String f407b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final org.json.JSONObject f408c;

    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final java.util.List<com.android.billingclient.api.h> f409a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final com.android.billingclient.api.g f410b;

        public a(com.android.billingclient.api.g gVar, java.util.List<com.android.billingclient.api.h> list) {
            this.f409a = list;
            this.f410b = gVar;
        }

        public com.android.billingclient.api.g a() {
            return this.f410b;
        }

        public java.util.List<com.android.billingclient.api.h> b() {
            return this.f409a;
        }
    }

    public h(java.lang.String str, java.lang.String str2) {
        this.f406a = str;
        this.f407b = str2;
        this.f408c = new org.json.JSONObject(this.f406a);
    }

    public java.lang.String a() {
        return this.f406a;
    }

    public int b() {
        return this.f408c.optInt("purchaseState", 1) != 4 ? 1 : 2;
    }

    public java.lang.String c() {
        org.json.JSONObject jSONObject = this.f408c;
        return jSONObject.optString("token", jSONObject.optString("purchaseToken"));
    }

    public java.lang.String d() {
        return this.f407b;
    }

    public java.util.ArrayList<java.lang.String> e() {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        if (this.f408c.has("productIds")) {
            org.json.JSONArray jSONArrayOptJSONArray = this.f408c.optJSONArray("productIds");
            if (jSONArrayOptJSONArray != null) {
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    arrayList.add(jSONArrayOptJSONArray.optString(i));
                }
            }
        } else if (this.f408c.has("productId")) {
            arrayList.add(this.f408c.optString("productId"));
        }
        return arrayList;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.billingclient.api.h)) {
            return false;
        }
        com.android.billingclient.api.h hVar = (com.android.billingclient.api.h) obj;
        return android.text.TextUtils.equals(this.f406a, hVar.a()) && android.text.TextUtils.equals(this.f407b, hVar.d());
    }

    public boolean f() {
        return this.f408c.optBoolean("acknowledged", true);
    }

    public int hashCode() {
        return this.f406a.hashCode();
    }

    public java.lang.String toString() {
        java.lang.String strValueOf = java.lang.String.valueOf(this.f406a);
        return strValueOf.length() != 0 ? "Purchase. Json: ".concat(strValueOf) : new java.lang.String("Purchase. Json: ");
    }
}
