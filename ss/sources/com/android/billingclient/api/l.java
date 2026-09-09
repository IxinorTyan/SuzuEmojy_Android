package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public class l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f422a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.util.List<java.lang.String> f423b;

    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private java.lang.String f424a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private java.util.List<java.lang.String> f425b;

        /* synthetic */ a(com.android.billingclient.api.x xVar) {
        }

        public com.android.billingclient.api.l.a a(java.lang.String str) {
            this.f424a = str;
            return this;
        }

        public com.android.billingclient.api.l.a a(java.util.List<java.lang.String> list) {
            this.f425b = new java.util.ArrayList(list);
            return this;
        }

        public com.android.billingclient.api.l a() {
            if (this.f424a == null) {
                throw new java.lang.IllegalArgumentException("SKU type must be set");
            }
            if (this.f425b == null) {
                throw new java.lang.IllegalArgumentException("SKU list or SkuWithOffer list must be set");
            }
            com.android.billingclient.api.l lVar = new com.android.billingclient.api.l();
            lVar.f422a = this.f424a;
            lVar.f423b = this.f425b;
            return lVar;
        }
    }

    public static com.android.billingclient.api.l.a c() {
        return new com.android.billingclient.api.l.a(null);
    }

    public java.lang.String a() {
        return this.f422a;
    }

    public java.util.List<java.lang.String> b() {
        return this.f423b;
    }
}
