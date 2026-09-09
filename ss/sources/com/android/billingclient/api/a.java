package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f378a;

    /* JADX INFO: renamed from: com.android.billingclient.api.a$a, reason: collision with other inner class name */
    public static final class C0016a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private java.lang.String f379a;

        /* synthetic */ C0016a(com.android.billingclient.api.n nVar) {
        }

        public com.android.billingclient.api.a.C0016a a(java.lang.String str) {
            this.f379a = str;
            return this;
        }

        public com.android.billingclient.api.a a() {
            if (this.f379a == null) {
                throw new java.lang.IllegalArgumentException("Purchase token must be set");
            }
            com.android.billingclient.api.a aVar = new com.android.billingclient.api.a(null);
            aVar.f378a = this.f379a;
            return aVar;
        }
    }

    /* synthetic */ a(com.android.billingclient.api.n nVar) {
    }

    public static com.android.billingclient.api.a.C0016a b() {
        return new com.android.billingclient.api.a.C0016a(null);
    }

    public java.lang.String a() {
        return this.f378a;
    }
}
