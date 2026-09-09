package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int f361a = java.lang.Runtime.getRuntime().availableProcessors();

    public static int a(android.os.Bundle bundle, java.lang.String str) {
        if (bundle == null) {
            b(str, "Unexpected null bundle received!");
            return 6;
        }
        java.lang.Object obj = bundle.get("RESPONSE_CODE");
        if (obj == null) {
            a(str, "getResponseCodeFromBundle() got null response code, assuming OK");
            return 0;
        }
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        java.lang.String strValueOf = java.lang.String.valueOf(obj.getClass().getName());
        b(str, strValueOf.length() != 0 ? "Unexpected type for bundle response code: ".concat(strValueOf) : new java.lang.String("Unexpected type for bundle response code: "));
        return 6;
    }

    public static android.os.Bundle a(int i, boolean z, java.lang.String str, java.lang.String str2, java.util.ArrayList<com.android.billingclient.api.a0> arrayList) {
        android.os.Bundle bundle = new android.os.Bundle();
        if (i >= 9) {
            bundle.putString("playBillingLibraryVersion", str);
        }
        if (i >= 9 && z) {
            bundle.putBoolean("enablePendingPurchases", true);
        }
        if (i >= 14) {
            java.util.ArrayList<java.lang.String> arrayList2 = new java.util.ArrayList<>();
            int size = arrayList.size();
            boolean z2 = false;
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.get(i2);
                arrayList2.add(null);
                z2 |= !android.text.TextUtils.isEmpty(null);
            }
            if (z2) {
                bundle.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList2);
            }
        }
        return bundle;
    }

    public static android.os.Bundle a(com.android.billingclient.api.a aVar, java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("playBillingLibraryVersion", str);
        return bundle;
    }

    public static android.os.Bundle a(com.android.billingclient.api.f fVar, boolean z, boolean z2, java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("playBillingLibraryVersion", str);
        if (fVar.b() != 0) {
            bundle.putInt("prorationMode", fVar.b());
        }
        if (!android.text.TextUtils.isEmpty(fVar.c())) {
            bundle.putString("accountId", fVar.c());
        }
        if (!android.text.TextUtils.isEmpty(fVar.d())) {
            bundle.putString("obfuscatedProfileId", fVar.d());
        }
        if (fVar.a()) {
            bundle.putBoolean("vr", true);
        }
        if (!android.text.TextUtils.isEmpty(null)) {
            bundle.putStringArrayList("skusToReplace", new java.util.ArrayList<>(java.util.Arrays.asList(null)));
        }
        if (!android.text.TextUtils.isEmpty(fVar.e())) {
            bundle.putString("oldSkuPurchaseToken", fVar.e());
        }
        if (!android.text.TextUtils.isEmpty(null)) {
            bundle.putString("oldSkuPurchaseId", null);
        }
        if (!android.text.TextUtils.isEmpty(null)) {
            bundle.putString("paymentsPurchaseParams", null);
        }
        if (z && z2) {
            bundle.putBoolean("enablePendingPurchases", true);
        }
        return bundle;
    }

    public static android.os.Bundle a(boolean z, boolean z2, java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("playBillingLibraryVersion", str);
        if (z && z2) {
            bundle.putBoolean("enablePendingPurchases", true);
        }
        return bundle;
    }

    public static com.android.billingclient.api.g a(android.content.Intent intent, java.lang.String str) {
        if (intent != null) {
            com.android.billingclient.api.g.a aVarB = com.android.billingclient.api.g.b();
            aVarB.a(a(intent.getExtras(), str));
            aVarB.a(b(intent.getExtras(), str));
            return aVarB.a();
        }
        b("BillingHelper", "Got null intent!");
        com.android.billingclient.api.g.a aVarB2 = com.android.billingclient.api.g.b();
        aVarB2.a(6);
        aVarB2.a("An internal error occurred.");
        return aVarB2.a();
    }

    public static java.util.List<com.android.billingclient.api.h> a(android.os.Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        java.util.ArrayList<java.lang.String> stringArrayList = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
        java.util.ArrayList<java.lang.String> stringArrayList2 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (stringArrayList == null || stringArrayList2 == null) {
            b("BillingHelper", "Couldn't find purchase lists, trying to find single data.");
            com.android.billingclient.api.h hVarC = c(bundle.getString("INAPP_PURCHASE_DATA"), bundle.getString("INAPP_DATA_SIGNATURE"));
            if (hVarC == null) {
                b("BillingHelper", "Couldn't find single purchase data as well.");
                return null;
            }
            arrayList.add(hVarC);
        } else {
            for (int i = 0; i < stringArrayList.size() && i < stringArrayList2.size(); i++) {
                com.android.billingclient.api.h hVarC2 = c(stringArrayList.get(i), stringArrayList2.get(i));
                if (hVarC2 != null) {
                    arrayList.add(hVarC2);
                }
            }
        }
        return arrayList;
    }

    public static void a(java.lang.String str, java.lang.String str2) {
        if (android.util.Log.isLoggable(str, 2)) {
            android.util.Log.v(str, str2);
        }
    }

    public static java.lang.String b(android.os.Bundle bundle, java.lang.String str) {
        if (bundle == null) {
            b(str, "Unexpected null bundle received!");
            return "";
        }
        java.lang.Object obj = bundle.get("DEBUG_MESSAGE");
        if (obj == null) {
            a(str, "getDebugMessageFromBundle() got null response code, assuming OK");
            return "";
        }
        if (obj instanceof java.lang.String) {
            return (java.lang.String) obj;
        }
        java.lang.String strValueOf = java.lang.String.valueOf(obj.getClass().getName());
        b(str, strValueOf.length() != 0 ? "Unexpected type for debug message: ".concat(strValueOf) : new java.lang.String("Unexpected type for debug message: "));
        return "";
    }

    public static void b(java.lang.String str, java.lang.String str2) {
        if (android.util.Log.isLoggable(str, 5)) {
            android.util.Log.w(str, str2);
        }
    }

    private static com.android.billingclient.api.h c(java.lang.String str, java.lang.String str2) {
        if (str == null || str2 == null) {
            b("BillingHelper", "Received a bad purchase data.");
            return null;
        }
        try {
            return new com.android.billingclient.api.h(str, str2);
        } catch (org.json.JSONException e) {
            java.lang.String strValueOf = java.lang.String.valueOf(e);
            java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf).length() + 47);
            sb.append("Got JSONException while parsing purchase data: ");
            sb.append(strValueOf);
            b("BillingHelper", sb.toString());
            return null;
        }
    }
}
