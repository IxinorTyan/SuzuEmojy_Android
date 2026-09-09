package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
final class v {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    static final com.android.billingclient.api.g f442a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    static final com.android.billingclient.api.g f443b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    static final com.android.billingclient.api.g f444c;
    static final com.android.billingclient.api.g d;
    static final com.android.billingclient.api.g e;
    static final com.android.billingclient.api.g f;
    static final com.android.billingclient.api.g g;
    static final com.android.billingclient.api.g h;
    static final com.android.billingclient.api.g i;
    static final com.android.billingclient.api.g j;
    static final com.android.billingclient.api.g k;
    static final com.android.billingclient.api.g l;
    static final com.android.billingclient.api.g m;
    static final com.android.billingclient.api.g n;
    static final com.android.billingclient.api.g o;

    static {
        com.android.billingclient.api.g.a aVarB = com.android.billingclient.api.g.b();
        aVarB.a(3);
        aVarB.a("Google Play In-app Billing API version is less than 3");
        f442a = aVarB.a();
        com.android.billingclient.api.g.a aVarB2 = com.android.billingclient.api.g.b();
        aVarB2.a(3);
        aVarB2.a("Google Play In-app Billing API version is less than 9");
        f443b = aVarB2.a();
        com.android.billingclient.api.g.a aVarB3 = com.android.billingclient.api.g.b();
        aVarB3.a(3);
        aVarB3.a("Billing service unavailable on device.");
        f444c = aVarB3.a();
        com.android.billingclient.api.g.a aVarB4 = com.android.billingclient.api.g.b();
        aVarB4.a(5);
        aVarB4.a("Client is already in the process of connecting to billing service.");
        d = aVarB4.a();
        com.android.billingclient.api.g.a aVarB5 = com.android.billingclient.api.g.b();
        aVarB5.a(3);
        aVarB5.a("Play Store version installed does not support cross selling products.");
        aVarB5.a();
        com.android.billingclient.api.g.a aVarB6 = com.android.billingclient.api.g.b();
        aVarB6.a(5);
        aVarB6.a("The list of SKUs can't be empty.");
        e = aVarB6.a();
        com.android.billingclient.api.g.a aVarB7 = com.android.billingclient.api.g.b();
        aVarB7.a(5);
        aVarB7.a("SKU type can't be empty.");
        f = aVarB7.a();
        com.android.billingclient.api.g.a aVarB8 = com.android.billingclient.api.g.b();
        aVarB8.a(-2);
        aVarB8.a("Client does not support extra params.");
        g = aVarB8.a();
        com.android.billingclient.api.g.a aVarB9 = com.android.billingclient.api.g.b();
        aVarB9.a(-2);
        aVarB9.a("Client does not support the feature.");
        h = aVarB9.a();
        com.android.billingclient.api.g.a aVarB10 = com.android.billingclient.api.g.b();
        aVarB10.a(-2);
        aVarB10.a("Client does not support get purchase history.");
        aVarB10.a();
        com.android.billingclient.api.g.a aVarB11 = com.android.billingclient.api.g.b();
        aVarB11.a(5);
        aVarB11.a("Invalid purchase token.");
        i = aVarB11.a();
        com.android.billingclient.api.g.a aVarB12 = com.android.billingclient.api.g.b();
        aVarB12.a(6);
        aVarB12.a("An internal error occurred.");
        j = aVarB12.a();
        com.android.billingclient.api.g.a aVarB13 = com.android.billingclient.api.g.b();
        aVarB13.a(4);
        aVarB13.a("Item is unavailable for purchase.");
        aVarB13.a();
        com.android.billingclient.api.g.a aVarB14 = com.android.billingclient.api.g.b();
        aVarB14.a(5);
        aVarB14.a("SKU can't be null.");
        aVarB14.a();
        com.android.billingclient.api.g.a aVarB15 = com.android.billingclient.api.g.b();
        aVarB15.a(5);
        aVarB15.a("SKU type can't be null.");
        aVarB15.a();
        com.android.billingclient.api.g.a aVarB16 = com.android.billingclient.api.g.b();
        aVarB16.a(0);
        k = aVarB16.a();
        com.android.billingclient.api.g.a aVarB17 = com.android.billingclient.api.g.b();
        aVarB17.a(-1);
        aVarB17.a("Service connection is disconnected.");
        l = aVarB17.a();
        com.android.billingclient.api.g.a aVarB18 = com.android.billingclient.api.g.b();
        aVarB18.a(-3);
        aVarB18.a("Timeout communicating with service.");
        m = aVarB18.a();
        com.android.billingclient.api.g.a aVarB19 = com.android.billingclient.api.g.b();
        aVarB19.a(-2);
        aVarB19.a("Client doesn't support subscriptions.");
        n = aVarB19.a();
        com.android.billingclient.api.g.a aVarB20 = com.android.billingclient.api.g.b();
        aVarB20.a(-2);
        aVarB20.a("Client doesn't support subscriptions update.");
        aVarB20.a();
        com.android.billingclient.api.g.a aVarB21 = com.android.billingclient.api.g.b();
        aVarB21.a(-2);
        aVarB21.a("Client doesn't support multi-item purchases.");
        o = aVarB21.a();
        com.android.billingclient.api.g.a aVarB22 = com.android.billingclient.api.g.b();
        aVarB22.a(5);
        aVarB22.a("Unknown feature");
        aVarB22.a();
    }
}
