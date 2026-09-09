package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
class d extends com.android.billingclient.api.c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private volatile int f387a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.lang.String f388b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final android.os.Handler f389c;
    private volatile com.android.billingclient.api.d0 d;
    private android.content.Context e;
    private volatile b.a.a.a.a.a.d f;
    private volatile com.android.billingclient.api.s g;
    private boolean h;
    private boolean i;
    private int j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private java.util.concurrent.ExecutorService t;

    private d(android.content.Context context, boolean z, com.android.billingclient.api.j jVar, java.lang.String str, java.lang.String str2) {
        this.f387a = 0;
        this.f389c = new android.os.Handler(android.os.Looper.getMainLooper());
        this.j = 0;
        this.f388b = str;
        a(context, jVar, z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    d(java.lang.String str, boolean z, android.content.Context context, com.android.billingclient.api.j jVar) {
        java.lang.String str2;
        try {
            str2 = (java.lang.String) java.lang.Class.forName("com.android.billingclient.ktx.BuildConfig").getField("VERSION_NAME").get(null);
        } catch (java.lang.Exception unused) {
            str2 = "4.0.0";
        }
        this(context, z, jVar, str2, null);
    }

    static /* synthetic */ com.android.billingclient.api.h.a a(com.android.billingclient.api.d dVar, java.lang.String str) {
        java.lang.String strValueOf = java.lang.String.valueOf(str);
        b.a.a.a.a.a.a.a("BillingClient", strValueOf.length() != 0 ? "Querying owned items, item type: ".concat(strValueOf) : new java.lang.String("Querying owned items, item type: "));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.os.Bundle bundleA = b.a.a.a.a.a.a.a(dVar.m, dVar.s, dVar.f388b);
        java.lang.String string = null;
        do {
            try {
                android.os.Bundle bundleA2 = dVar.m ? dVar.f.a(9, dVar.e.getPackageName(), str, string, bundleA) : dVar.f.a(3, dVar.e.getPackageName(), str, string);
                com.android.billingclient.api.g gVarA = com.android.billingclient.api.w.a(bundleA2, "BillingClient", "getPurchase()");
                if (gVarA != com.android.billingclient.api.v.k) {
                    return new com.android.billingclient.api.h.a(gVarA, null);
                }
                java.util.ArrayList<java.lang.String> stringArrayList = bundleA2.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                java.util.ArrayList<java.lang.String> stringArrayList2 = bundleA2.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                java.util.ArrayList<java.lang.String> stringArrayList3 = bundleA2.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    java.lang.String str2 = stringArrayList2.get(i);
                    java.lang.String str3 = stringArrayList3.get(i);
                    java.lang.String strValueOf2 = java.lang.String.valueOf(stringArrayList.get(i));
                    b.a.a.a.a.a.a.a("BillingClient", strValueOf2.length() != 0 ? "Sku is owned: ".concat(strValueOf2) : new java.lang.String("Sku is owned: "));
                    try {
                        com.android.billingclient.api.h hVar = new com.android.billingclient.api.h(str2, str3);
                        if (android.text.TextUtils.isEmpty(hVar.c())) {
                            b.a.a.a.a.a.a.b("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(hVar);
                    } catch (org.json.JSONException e) {
                        java.lang.String strValueOf3 = java.lang.String.valueOf(e);
                        java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf3).length() + 48);
                        sb.append("Got an exception trying to decode the purchase: ");
                        sb.append(strValueOf3);
                        b.a.a.a.a.a.a.b("BillingClient", sb.toString());
                        return new com.android.billingclient.api.h.a(com.android.billingclient.api.v.j, null);
                    }
                }
                string = bundleA2.getString("INAPP_CONTINUATION_TOKEN");
                java.lang.String strValueOf4 = java.lang.String.valueOf(string);
                b.a.a.a.a.a.a.a("BillingClient", strValueOf4.length() != 0 ? "Continuation token: ".concat(strValueOf4) : new java.lang.String("Continuation token: "));
            } catch (java.lang.Exception e2) {
                java.lang.String strValueOf5 = java.lang.String.valueOf(e2);
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf5).length() + 57);
                sb2.append("Got exception trying to get purchases: ");
                sb2.append(strValueOf5);
                sb2.append("; try to reconnect");
                b.a.a.a.a.a.a.b("BillingClient", sb2.toString());
                return new com.android.billingclient.api.h.a(com.android.billingclient.api.v.l, null);
            }
        } while (!android.text.TextUtils.isEmpty(string));
        return new com.android.billingclient.api.h.a(com.android.billingclient.api.v.k, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T> java.util.concurrent.Future<T> a(java.util.concurrent.Callable<T> callable, long j, final java.lang.Runnable runnable, android.os.Handler handler) {
        long j2 = (long) (j * 0.95d);
        if (this.t == null) {
            this.t = java.util.concurrent.Executors.newFixedThreadPool(b.a.a.a.a.a.a.f361a, new com.android.billingclient.api.p(this));
        }
        try {
            final java.util.concurrent.Future<T> futureSubmit = this.t.submit(callable);
            handler.postDelayed(new java.lang.Runnable() { // from class: com.android.billingclient.api.m0
                @Override // java.lang.Runnable
                public final void run() {
                    java.util.concurrent.Future future = futureSubmit;
                    java.lang.Runnable runnable2 = runnable;
                    if (future.isDone() || future.isCancelled()) {
                        return;
                    }
                    future.cancel(true);
                    b.a.a.a.a.a.a.b("BillingClient", "Async task is taking too long, cancel it!");
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }, j2);
            return futureSubmit;
        } catch (java.lang.Exception e) {
            java.lang.String strValueOf = java.lang.String.valueOf(e);
            java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf).length() + 28);
            sb.append("Async task throws exception ");
            sb.append(strValueOf);
            b.a.a.a.a.a.a.b("BillingClient", sb.toString());
            return null;
        }
    }

    private void a(android.content.Context context, com.android.billingclient.api.j jVar, boolean z) {
        android.content.Context applicationContext = context.getApplicationContext();
        this.e = applicationContext;
        this.d = new com.android.billingclient.api.d0(applicationContext, jVar);
        this.s = z;
    }

    private final com.android.billingclient.api.g b(final com.android.billingclient.api.g gVar) {
        if (java.lang.Thread.interrupted()) {
            return gVar;
        }
        this.f389c.post(new java.lang.Runnable() { // from class: com.android.billingclient.api.j0
            @Override // java.lang.Runnable
            public final void run() {
                this.f417a.a(gVar);
            }
        });
        return gVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final android.os.Handler c() {
        return android.os.Looper.myLooper() == null ? this.f389c : new android.os.Handler(android.os.Looper.myLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final com.android.billingclient.api.g d() {
        return (this.f387a == 0 || this.f387a == 3) ? com.android.billingclient.api.v.l : com.android.billingclient.api.v.j;
    }

    public final /* synthetic */ android.os.Bundle a(int i, com.android.billingclient.api.k kVar, java.lang.String str, com.android.billingclient.api.f fVar, android.os.Bundle bundle) {
        return this.f.a(i, this.e.getPackageName(), kVar.b(), str, null, bundle);
    }

    public final /* synthetic */ android.os.Bundle a(com.android.billingclient.api.k kVar, java.lang.String str) {
        return this.f.a(3, this.e.getPackageName(), kVar.b(), str, (java.lang.String) null);
    }

    @Override // com.android.billingclient.api.c
    public final com.android.billingclient.api.g a(android.app.Activity activity, final com.android.billingclient.api.f fVar) {
        java.lang.String str;
        java.util.concurrent.Callable callable;
        long j;
        java.lang.Runnable runnable;
        android.os.Handler handler;
        com.android.billingclient.api.g gVar;
        boolean z;
        final int i;
        java.lang.String strOptString;
        java.lang.String str2 = "BUY_INTENT";
        if (b()) {
            java.util.ArrayList<com.android.billingclient.api.k> arrayListF = fVar.f();
            final com.android.billingclient.api.k kVar = arrayListF.get(0);
            final java.lang.String strC = kVar.c();
            if (strC.equals("subs") && !this.h) {
                b.a.a.a.a.a.a.b("BillingClient", "Current client doesn't support subscriptions.");
                gVar = com.android.billingclient.api.v.n;
            } else if (fVar.g() && !this.k) {
                b.a.a.a.a.a.a.b("BillingClient", "Current client doesn't support extra params for buy intent.");
                gVar = com.android.billingclient.api.v.g;
            } else if (arrayListF.size() <= 1 || this.r) {
                java.lang.String str3 = "";
                for (int i2 = 0; i2 < arrayListF.size(); i2++) {
                    java.lang.String strValueOf = java.lang.String.valueOf(str3);
                    java.lang.String strValueOf2 = java.lang.String.valueOf(arrayListF.get(i2));
                    java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf).length() + java.lang.String.valueOf(strValueOf2).length());
                    sb.append(strValueOf);
                    sb.append(strValueOf2);
                    java.lang.String string = sb.toString();
                    if (i2 < arrayListF.size() - 1) {
                        string = java.lang.String.valueOf(string).concat(", ");
                    }
                    str3 = string;
                }
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder(java.lang.String.valueOf(str3).length() + 41 + java.lang.String.valueOf(strC).length());
                sb2.append("Constructing buy intent for ");
                sb2.append(str3);
                sb2.append(", item type: ");
                sb2.append(strC);
                b.a.a.a.a.a.a.a("BillingClient", sb2.toString());
                if (this.k) {
                    final android.os.Bundle bundleA = b.a.a.a.a.a.a.a(fVar, this.m, this.s, this.f388b);
                    java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
                    java.util.ArrayList<java.lang.String> arrayList2 = new java.util.ArrayList<>();
                    java.util.ArrayList<java.lang.String> arrayList3 = new java.util.ArrayList<>();
                    java.util.ArrayList<java.lang.Integer> arrayList4 = new java.util.ArrayList<>();
                    java.util.ArrayList<java.lang.String> arrayList5 = new java.util.ArrayList<>();
                    int size = arrayListF.size();
                    int i3 = 0;
                    boolean z2 = false;
                    boolean z3 = false;
                    boolean z4 = false;
                    boolean z5 = false;
                    while (i3 < size) {
                        com.android.billingclient.api.k kVar2 = arrayListF.get(i3);
                        if (!kVar2.h().isEmpty()) {
                            arrayList.add(kVar2.h());
                        }
                        java.lang.String str4 = str2;
                        try {
                            strOptString = new org.json.JSONObject(kVar2.a()).optString("offer_id_token");
                        } catch (org.json.JSONException unused) {
                            strOptString = "";
                        }
                        java.lang.String strE = kVar2.e();
                        int iD = kVar2.d();
                        java.lang.String strG = kVar2.g();
                        arrayList2.add(strOptString);
                        z2 |= !android.text.TextUtils.isEmpty(strOptString);
                        arrayList3.add(strE);
                        z3 |= !android.text.TextUtils.isEmpty(strE);
                        arrayList4.add(java.lang.Integer.valueOf(iD));
                        z4 |= iD != 0;
                        z5 |= !android.text.TextUtils.isEmpty(strG);
                        arrayList5.add(strG);
                        i3++;
                        size = size;
                        str2 = str4;
                    }
                    str = str2;
                    if (!arrayList.isEmpty()) {
                        bundleA.putStringArrayList("skuDetailsTokens", arrayList);
                    }
                    if (z2) {
                        if (this.p) {
                            bundleA.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList2);
                        } else {
                            gVar = com.android.billingclient.api.v.h;
                        }
                    }
                    if (z3) {
                        bundleA.putStringArrayList("SKU_OFFER_ID_LIST", arrayList3);
                    }
                    if (z4) {
                        bundleA.putIntegerArrayList("SKU_OFFER_TYPE_LIST", arrayList4);
                    }
                    if (z5) {
                        bundleA.putStringArrayList("SKU_SERIALIZED_DOCID_LIST", arrayList5);
                    }
                    if (android.text.TextUtils.isEmpty(kVar.f())) {
                        z = false;
                    } else {
                        bundleA.putString("skuPackageName", kVar.f());
                        z = true;
                    }
                    if (!android.text.TextUtils.isEmpty(null)) {
                        bundleA.putString("accountName", null);
                    }
                    if (arrayListF.size() > 1) {
                        java.util.ArrayList<java.lang.String> arrayList6 = new java.util.ArrayList<>(arrayListF.size() - 1);
                        java.util.ArrayList<java.lang.String> arrayList7 = new java.util.ArrayList<>(arrayListF.size() - 1);
                        for (int i4 = 1; i4 < arrayListF.size(); i4++) {
                            arrayList6.add(arrayListF.get(i4).b());
                            arrayList7.add(arrayListF.get(i4).c());
                        }
                        bundleA.putStringArrayList("additionalSkus", arrayList6);
                        bundleA.putStringArrayList("additionalSkuTypes", arrayList7);
                    }
                    if (!android.text.TextUtils.isEmpty(activity.getIntent().getStringExtra("PROXY_PACKAGE"))) {
                        java.lang.String stringExtra = activity.getIntent().getStringExtra("PROXY_PACKAGE");
                        bundleA.putString("proxyPackage", stringExtra);
                        try {
                            bundleA.putString("proxyPackageVersion", this.e.getPackageManager().getPackageInfo(stringExtra, 0).versionName);
                        } catch (android.content.pm.PackageManager.NameNotFoundException unused2) {
                            bundleA.putString("proxyPackageVersion", "package not found");
                        }
                    }
                    if (this.q && z) {
                        i = 15;
                    } else if (this.m) {
                        i = 9;
                    } else {
                        i = fVar.a() ? 7 : 6;
                    }
                    java.util.concurrent.Callable callable2 = new java.util.concurrent.Callable() { // from class: com.android.billingclient.api.n0
                        @Override // java.util.concurrent.Callable
                        public final java.lang.Object call() {
                            return this.f429a.a(i, kVar, strC, fVar, bundleA);
                        }
                    };
                    j = 5000;
                    runnable = null;
                    handler = this.f389c;
                    callable = callable2;
                } else {
                    str = "BUY_INTENT";
                    callable = new java.util.concurrent.Callable() { // from class: com.android.billingclient.api.h0
                        @Override // java.util.concurrent.Callable
                        public final java.lang.Object call() {
                            return this.f411a.a(kVar, strC);
                        }
                    };
                    j = 5000;
                    runnable = null;
                    handler = this.f389c;
                }
                try {
                    android.os.Bundle bundle = (android.os.Bundle) a(callable, j, runnable, handler).get(5000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                    int iA = b.a.a.a.a.a.a.a(bundle, "BillingClient");
                    java.lang.String strB = b.a.a.a.a.a.a.b(bundle, "BillingClient");
                    if (iA == 0) {
                        android.content.Intent intent = new android.content.Intent(activity, (java.lang.Class<?>) com.android.billingclient.api.ProxyBillingActivity.class);
                        java.lang.String str5 = str;
                        intent.putExtra(str5, (android.app.PendingIntent) bundle.getParcelable(str5));
                        activity.startActivity(intent);
                        return com.android.billingclient.api.v.k;
                    }
                    java.lang.StringBuilder sb3 = new java.lang.StringBuilder(52);
                    sb3.append("Unable to buy item, Error response code: ");
                    sb3.append(iA);
                    b.a.a.a.a.a.a.b("BillingClient", sb3.toString());
                    com.android.billingclient.api.g.a aVarB = com.android.billingclient.api.g.b();
                    aVarB.a(iA);
                    aVarB.a(strB);
                    com.android.billingclient.api.g gVarA = aVarB.a();
                    b(gVarA);
                    return gVarA;
                } catch (java.util.concurrent.CancellationException | java.util.concurrent.TimeoutException unused3) {
                    java.lang.String str6 = str3;
                    java.lang.StringBuilder sb4 = new java.lang.StringBuilder(java.lang.String.valueOf(str6).length() + 68);
                    sb4.append("Time out while launching billing flow: ; for sku: ");
                    sb4.append(str6);
                    sb4.append("; try to reconnect");
                    b.a.a.a.a.a.a.b("BillingClient", sb4.toString());
                    gVar = com.android.billingclient.api.v.m;
                } catch (java.lang.Exception unused4) {
                    java.lang.StringBuilder sb5 = new java.lang.StringBuilder(java.lang.String.valueOf(str3).length() + 69);
                    sb5.append("Exception while launching billing flow: ; for sku: ");
                    sb5.append(str3);
                    sb5.append("; try to reconnect");
                    b.a.a.a.a.a.a.b("BillingClient", sb5.toString());
                }
            } else {
                b.a.a.a.a.a.a.b("BillingClient", "Current client doesn't support multi-item purchases.");
                gVar = com.android.billingclient.api.v.o;
            }
        } else {
            gVar = com.android.billingclient.api.v.l;
        }
        b(gVar);
        return gVar;
    }

    public final /* synthetic */ java.lang.Object a(java.lang.String str, java.util.List list, java.lang.String str2, com.android.billingclient.api.m mVar) {
        java.lang.String strB;
        int i;
        java.lang.String str3;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = list.size();
        int i2 = 0;
        while (true) {
            strB = "Item is unavailable for purchase.";
            if (i2 >= size) {
                strB = "";
                i = 0;
                break;
            }
            int i3 = i2 + 20;
            java.util.ArrayList arrayList2 = new java.util.ArrayList(list.subList(i2, i3 > size ? size : i3));
            java.util.ArrayList<java.lang.String> arrayList3 = new java.util.ArrayList<>();
            int size2 = arrayList2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                arrayList3.add(((com.android.billingclient.api.a0) arrayList2.get(i4)).a());
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putStringArrayList("ITEM_ID_LIST", arrayList3);
            bundle.putString("playBillingLibraryVersion", this.f388b);
            try {
                android.os.Bundle bundleA = this.n ? this.f.a(10, this.e.getPackageName(), str, bundle, b.a.a.a.a.a.a.a(this.j, this.s, this.f388b, null, arrayList2)) : this.f.b(3, this.e.getPackageName(), str, bundle);
                if (bundleA != null) {
                    if (bundleA.containsKey("DETAILS_LIST")) {
                        java.util.ArrayList<java.lang.String> stringArrayList = bundleA.getStringArrayList("DETAILS_LIST");
                        if (stringArrayList == null) {
                            str3 = "querySkuDetailsAsync got null response list";
                        } else {
                            for (int i5 = 0; i5 < stringArrayList.size(); i5++) {
                                try {
                                    com.android.billingclient.api.k kVar = new com.android.billingclient.api.k(stringArrayList.get(i5));
                                    java.lang.String strValueOf = java.lang.String.valueOf(kVar);
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf).length() + 17);
                                    sb.append("Got sku details: ");
                                    sb.append(strValueOf);
                                    b.a.a.a.a.a.a.a("BillingClient", sb.toString());
                                    arrayList.add(kVar);
                                } catch (org.json.JSONException unused) {
                                    b.a.a.a.a.a.a.b("BillingClient", "Got a JSON exception trying to decode SkuDetails.");
                                    strB = "Error trying to decode SkuDetails.";
                                    arrayList = null;
                                }
                            }
                            i2 = i3;
                        }
                    } else {
                        int iA = b.a.a.a.a.a.a.a(bundleA, "BillingClient");
                        strB = b.a.a.a.a.a.a.b(bundleA, "BillingClient");
                        if (iA != 0) {
                            java.lang.StringBuilder sb2 = new java.lang.StringBuilder(50);
                            sb2.append("getSkuDetails() failed. Response code: ");
                            sb2.append(iA);
                            b.a.a.a.a.a.a.b("BillingClient", sb2.toString());
                            i = iA;
                            break;
                        }
                        b.a.a.a.a.a.a.b("BillingClient", "getSkuDetails() returned a bundle with neither an error nor a detail list.");
                    }
                    i = 6;
                    break;
                }
                str3 = "querySkuDetailsAsync got null sku details list";
                b.a.a.a.a.a.a.b("BillingClient", str3);
                arrayList = null;
                i = 4;
                break;
            } catch (java.lang.Exception e) {
                java.lang.String strValueOf2 = java.lang.String.valueOf(e);
                java.lang.StringBuilder sb3 = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf2).length() + 63);
                sb3.append("querySkuDetailsAsync got a remote exception (try to reconnect).");
                sb3.append(strValueOf2);
                b.a.a.a.a.a.a.b("BillingClient", sb3.toString());
                i = -1;
                strB = "Service connection is disconnected.";
                arrayList = null;
            }
        }
        com.android.billingclient.api.g.a aVarB = com.android.billingclient.api.g.b();
        aVarB.a(i);
        aVarB.a(strB);
        mVar.a(aVarB.a(), arrayList);
        return null;
    }

    @Override // com.android.billingclient.api.c
    public final void a() {
        try {
            this.d.b();
            if (this.g != null) {
                this.g.c();
            }
            if (this.g != null && this.f != null) {
                b.a.a.a.a.a.a.a("BillingClient", "Unbinding from service.");
                this.e.unbindService(this.g);
                this.g = null;
            }
            this.f = null;
            java.util.concurrent.ExecutorService executorService = this.t;
            if (executorService != null) {
                executorService.shutdownNow();
                this.t = null;
            }
        } catch (java.lang.Exception e) {
            java.lang.String strValueOf = java.lang.String.valueOf(e);
            java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf).length() + 48);
            sb.append("There was an exception while ending connection: ");
            sb.append(strValueOf);
            b.a.a.a.a.a.a.b("BillingClient", sb.toString());
        } finally {
            this.f387a = 3;
        }
    }

    @Override // com.android.billingclient.api.c
    public final void a(final com.android.billingclient.api.a aVar, final com.android.billingclient.api.b bVar) {
        com.android.billingclient.api.g gVarD;
        if (!b()) {
            gVarD = com.android.billingclient.api.v.l;
        } else if (android.text.TextUtils.isEmpty(aVar.a())) {
            b.a.a.a.a.a.a.b("BillingClient", "Please provide a valid purchase token.");
            gVarD = com.android.billingclient.api.v.i;
        } else if (!this.m) {
            gVarD = com.android.billingclient.api.v.f443b;
        } else if (a(new java.util.concurrent.Callable() { // from class: com.android.billingclient.api.g0
            @Override // java.util.concurrent.Callable
            public final java.lang.Object call() {
                this.f403a.b(aVar, bVar);
                return null;
            }
        }, 30000L, new java.lang.Runnable() { // from class: com.android.billingclient.api.f0
            @Override // java.lang.Runnable
            public final void run() {
                bVar.a(com.android.billingclient.api.v.m);
            }
        }, c()) != null) {
            return;
        } else {
            gVarD = d();
        }
        bVar.a(gVarD);
    }

    @Override // com.android.billingclient.api.c
    public final void a(com.android.billingclient.api.e eVar) {
        android.content.pm.ServiceInfo serviceInfo;
        java.lang.String str;
        if (b()) {
            b.a.a.a.a.a.a.a("BillingClient", "Service connection is valid. No need to re-initialize.");
            eVar.a(com.android.billingclient.api.v.k);
            return;
        }
        if (this.f387a == 1) {
            b.a.a.a.a.a.a.b("BillingClient", "Client is already in the process of connecting to billing service.");
            eVar.a(com.android.billingclient.api.v.d);
            return;
        }
        if (this.f387a == 3) {
            b.a.a.a.a.a.a.b("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            eVar.a(com.android.billingclient.api.v.l);
            return;
        }
        this.f387a = 1;
        this.d.c();
        b.a.a.a.a.a.a.a("BillingClient", "Starting in-app billing setup.");
        this.g = new com.android.billingclient.api.s(this, eVar, null);
        android.content.Intent intent = new android.content.Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        java.util.List<android.content.pm.ResolveInfo> listQueryIntentServices = this.e.getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices != null && !listQueryIntentServices.isEmpty() && (serviceInfo = listQueryIntentServices.get(0).serviceInfo) != null) {
            java.lang.String str2 = serviceInfo.packageName;
            java.lang.String str3 = serviceInfo.name;
            if (!"com.android.vending".equals(str2) || str3 == null) {
                str = "The device doesn't have valid Play Store.";
            } else {
                android.content.ComponentName componentName = new android.content.ComponentName(str2, str3);
                android.content.Intent intent2 = new android.content.Intent(intent);
                intent2.setComponent(componentName);
                intent2.putExtra("playBillingLibraryVersion", this.f388b);
                if (this.e.bindService(intent2, this.g, 1)) {
                    b.a.a.a.a.a.a.a("BillingClient", "Service was bonded successfully.");
                    return;
                }
                str = "Connection to Billing service is blocked.";
            }
            b.a.a.a.a.a.a.b("BillingClient", str);
        }
        this.f387a = 0;
        b.a.a.a.a.a.a.a("BillingClient", "Billing service unavailable on device.");
        eVar.a(com.android.billingclient.api.v.f444c);
    }

    public final /* synthetic */ void a(com.android.billingclient.api.g gVar) {
        this.d.a().a(gVar, null);
    }

    @Override // com.android.billingclient.api.c
    public final void a(com.android.billingclient.api.l lVar, final com.android.billingclient.api.m mVar) {
        com.android.billingclient.api.g gVarD;
        if (b()) {
            final java.lang.String strA = lVar.a();
            java.util.List<java.lang.String> listB = lVar.b();
            if (android.text.TextUtils.isEmpty(strA)) {
                b.a.a.a.a.a.a.b("BillingClient", "Please fix the input params. SKU type can't be empty.");
                gVarD = com.android.billingclient.api.v.f;
            } else if (listB != null) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str : listB) {
                    com.android.billingclient.api.z zVar = new com.android.billingclient.api.z(null);
                    zVar.a(str);
                    arrayList.add(zVar.a());
                }
                final java.lang.String str2 = null;
                if (a(new java.util.concurrent.Callable(strA, arrayList, str2, mVar) { // from class: com.android.billingclient.api.i0

                    /* JADX INFO: renamed from: b, reason: collision with root package name */
                    public final /* synthetic */ java.lang.String f415b;

                    /* JADX INFO: renamed from: c, reason: collision with root package name */
                    public final /* synthetic */ java.util.List f416c;
                    public final /* synthetic */ com.android.billingclient.api.m d;

                    {
                        this.d = mVar;
                    }

                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        this.f414a.a(this.f415b, this.f416c, (java.lang.String) null, this.d);
                        return null;
                    }
                }, 30000L, new java.lang.Runnable() { // from class: com.android.billingclient.api.l0
                    @Override // java.lang.Runnable
                    public final void run() {
                        mVar.a(com.android.billingclient.api.v.m, null);
                    }
                }, c()) != null) {
                    return;
                } else {
                    gVarD = d();
                }
            } else {
                b.a.a.a.a.a.a.b("BillingClient", "Please fix the input params. The list of SKUs can't be empty - set SKU list or SkuWithOffer list.");
                gVarD = com.android.billingclient.api.v.e;
            }
        } else {
            gVarD = com.android.billingclient.api.v.l;
        }
        mVar.a(gVarD, null);
    }

    @Override // com.android.billingclient.api.c
    public void a(java.lang.String str, final com.android.billingclient.api.i iVar) {
        com.android.billingclient.api.g gVarD;
        if (!b()) {
            gVarD = com.android.billingclient.api.v.l;
        } else if (android.text.TextUtils.isEmpty(str)) {
            b.a.a.a.a.a.a.b("BillingClient", "Please provide a valid SKU type.");
            gVarD = com.android.billingclient.api.v.f;
        } else if (a(new com.android.billingclient.api.o(this, str, iVar), 30000L, new java.lang.Runnable() { // from class: com.android.billingclient.api.k0
            @Override // java.lang.Runnable
            public final void run() {
                iVar.a(com.android.billingclient.api.v.m, b.a.a.a.a.a.p.d());
            }
        }, c()) != null) {
            return;
        } else {
            gVarD = d();
        }
        iVar.a(gVarD, b.a.a.a.a.a.p.d());
    }

    public final /* synthetic */ java.lang.Object b(com.android.billingclient.api.a aVar, com.android.billingclient.api.b bVar) {
        com.android.billingclient.api.g gVarA;
        try {
            android.os.Bundle bundleA = this.f.a(9, this.e.getPackageName(), aVar.a(), b.a.a.a.a.a.a.a(aVar, this.f388b));
            int iA = b.a.a.a.a.a.a.a(bundleA, "BillingClient");
            java.lang.String strB = b.a.a.a.a.a.a.b(bundleA, "BillingClient");
            com.android.billingclient.api.g.a aVarB = com.android.billingclient.api.g.b();
            aVarB.a(iA);
            aVarB.a(strB);
            gVarA = aVarB.a();
        } catch (java.lang.Exception e) {
            java.lang.String strValueOf = java.lang.String.valueOf(e);
            java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf).length() + 32);
            sb.append("Error acknowledge purchase; ex: ");
            sb.append(strValueOf);
            b.a.a.a.a.a.a.b("BillingClient", sb.toString());
            gVarA = com.android.billingclient.api.v.l;
        }
        bVar.a(gVarA);
        return null;
    }

    @Override // com.android.billingclient.api.c
    public final boolean b() {
        return (this.f387a != 2 || this.f == null || this.g == null) ? false : true;
    }
}
