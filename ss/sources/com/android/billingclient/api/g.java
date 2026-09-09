package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public final class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f399a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.lang.String f400b;

    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private int f401a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private java.lang.String f402b = "";

        /* synthetic */ a(com.android.billingclient.api.u uVar) {
        }

        public com.android.billingclient.api.g.a a(int i) {
            this.f401a = i;
            return this;
        }

        public com.android.billingclient.api.g.a a(java.lang.String str) {
            this.f402b = str;
            return this;
        }

        public com.android.billingclient.api.g a() {
            com.android.billingclient.api.g gVar = new com.android.billingclient.api.g();
            gVar.f399a = this.f401a;
            gVar.f400b = this.f402b;
            return gVar;
        }
    }

    public static com.android.billingclient.api.g.a b() {
        return new com.android.billingclient.api.g.a(null);
    }

    public int a() {
        return this.f399a;
    }
}
