package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public abstract class c {

    public static final class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private volatile boolean f381a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final android.content.Context f382b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private volatile com.android.billingclient.api.j f383c;

        /* synthetic */ a(android.content.Context context, com.android.billingclient.api.e0 e0Var) {
            this.f382b = context;
        }

        public com.android.billingclient.api.c.a a(com.android.billingclient.api.j jVar) {
            this.f383c = jVar;
            return this;
        }

        public com.android.billingclient.api.c a() {
            if (this.f382b == null) {
                throw new java.lang.IllegalArgumentException("Please provide a valid Context.");
            }
            if (this.f383c == null) {
                throw new java.lang.IllegalArgumentException("Please provide a valid listener for purchases updates.");
            }
            if (this.f381a) {
                return new com.android.billingclient.api.d(null, this.f381a, this.f382b, this.f383c);
            }
            throw new java.lang.IllegalArgumentException("Support for pending purchases must be enabled. Enable this by calling 'enablePendingPurchases()' on BillingClientBuilder.");
        }

        public com.android.billingclient.api.c.a b() {
            this.f381a = true;
            return this;
        }
    }

    public static com.android.billingclient.api.c.a a(android.content.Context context) {
        return new com.android.billingclient.api.c.a(context, null);
    }

    public abstract com.android.billingclient.api.g a(android.app.Activity activity, com.android.billingclient.api.f fVar);

    public abstract void a();

    public abstract void a(com.android.billingclient.api.a aVar, com.android.billingclient.api.b bVar);

    public abstract void a(com.android.billingclient.api.e eVar);

    public abstract void a(com.android.billingclient.api.l lVar, com.android.billingclient.api.m mVar);

    public abstract void a(java.lang.String str, com.android.billingclient.api.i iVar);

    public abstract boolean b();
}
