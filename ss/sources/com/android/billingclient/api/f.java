package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f392a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.lang.String f393b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private java.lang.String f394c;
    private java.lang.String d;
    private int e = 0;
    private java.util.ArrayList<com.android.billingclient.api.k> f;
    private boolean g;

    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private java.lang.String f395a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private java.lang.String f396b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private java.lang.String f397c;
        private int d = 0;
        private java.util.ArrayList<com.android.billingclient.api.k> e;
        private boolean f;

        /* synthetic */ a(com.android.billingclient.api.t tVar) {
        }

        public com.android.billingclient.api.f.a a(com.android.billingclient.api.k kVar) {
            java.util.ArrayList<com.android.billingclient.api.k> arrayList = new java.util.ArrayList<>();
            arrayList.add(kVar);
            this.e = arrayList;
            return this;
        }

        public com.android.billingclient.api.f a() {
            java.util.ArrayList<com.android.billingclient.api.k> arrayList = this.e;
            if (arrayList == null || arrayList.isEmpty()) {
                throw new java.lang.IllegalArgumentException("SkuDetails must be provided.");
            }
            java.util.ArrayList<com.android.billingclient.api.k> arrayList2 = this.e;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                if (arrayList2.get(i) == null) {
                    throw new java.lang.IllegalArgumentException("SKU cannot be null.");
                }
                i = i2;
            }
            if (this.e.size() > 1) {
                com.android.billingclient.api.k kVar = this.e.get(0);
                java.lang.String strC = kVar.c();
                java.util.ArrayList<com.android.billingclient.api.k> arrayList3 = this.e;
                int size2 = arrayList3.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    com.android.billingclient.api.k kVar2 = arrayList3.get(i3);
                    if (!strC.equals("play_pass_subs") && !kVar2.c().equals("play_pass_subs") && !strC.equals(kVar2.c())) {
                        throw new java.lang.IllegalArgumentException("SKUs should have the same type.");
                    }
                }
                java.lang.String strF = kVar.f();
                java.util.ArrayList<com.android.billingclient.api.k> arrayList4 = this.e;
                int size3 = arrayList4.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    com.android.billingclient.api.k kVar3 = arrayList4.get(i4);
                    if (!strC.equals("play_pass_subs") && !kVar3.c().equals("play_pass_subs") && !strF.equals(kVar3.f())) {
                        throw new java.lang.IllegalArgumentException("All SKUs must have the same package name.");
                    }
                }
            }
            com.android.billingclient.api.f fVar = new com.android.billingclient.api.f(null);
            fVar.f392a = true ^ this.e.get(0).f().isEmpty();
            fVar.f393b = this.f395a;
            fVar.d = this.f397c;
            fVar.f394c = this.f396b;
            fVar.e = this.d;
            fVar.f = this.e;
            fVar.g = this.f;
            return fVar;
        }
    }

    /* synthetic */ f(com.android.billingclient.api.t tVar) {
    }

    public static com.android.billingclient.api.f.a h() {
        return new com.android.billingclient.api.f.a(null);
    }

    public boolean a() {
        return this.g;
    }

    public final int b() {
        return this.e;
    }

    public final java.lang.String c() {
        return this.f393b;
    }

    public final java.lang.String d() {
        return this.d;
    }

    public final java.lang.String e() {
        return this.f394c;
    }

    public final java.util.ArrayList<com.android.billingclient.api.k> f() {
        java.util.ArrayList<com.android.billingclient.api.k> arrayList = new java.util.ArrayList<>();
        arrayList.addAll(this.f);
        return arrayList;
    }

    final boolean g() {
        return (!this.g && this.f393b == null && this.d == null && this.e == 0 && !this.f392a) ? false : true;
    }
}
