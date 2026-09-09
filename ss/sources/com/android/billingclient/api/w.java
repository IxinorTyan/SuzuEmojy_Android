package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
final class w {
    static com.android.billingclient.api.g a(android.os.Bundle bundle, java.lang.String str, java.lang.String str2) {
        java.lang.String str3;
        com.android.billingclient.api.g gVar = com.android.billingclient.api.v.j;
        if (bundle == null) {
            str3 = java.lang.String.format("%s got null owned items list", str2);
        } else {
            int iA = b.a.a.a.a.a.a.a(bundle, "BillingClient");
            java.lang.String strB = b.a.a.a.a.a.a.b(bundle, "BillingClient");
            com.android.billingclient.api.g.a aVarB = com.android.billingclient.api.g.b();
            aVarB.a(iA);
            aVarB.a(strB);
            com.android.billingclient.api.g gVarA = aVarB.a();
            if (iA != 0) {
                b.a.a.a.a.a.a.b("BillingClient", java.lang.String.format("%s failed. Response code: %s", str2, java.lang.Integer.valueOf(iA)));
                return gVarA;
            }
            if (bundle.containsKey("INAPP_PURCHASE_ITEM_LIST") && bundle.containsKey("INAPP_PURCHASE_DATA_LIST") && bundle.containsKey("INAPP_DATA_SIGNATURE_LIST")) {
                java.util.ArrayList<java.lang.String> stringArrayList = bundle.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                java.util.ArrayList<java.lang.String> stringArrayList2 = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                java.util.ArrayList<java.lang.String> stringArrayList3 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                if (stringArrayList == null) {
                    str3 = java.lang.String.format("Bundle returned from %s contains null SKUs list.", str2);
                } else if (stringArrayList2 == null) {
                    str3 = java.lang.String.format("Bundle returned from %s contains null purchases list.", str2);
                } else {
                    if (stringArrayList3 != null) {
                        return com.android.billingclient.api.v.k;
                    }
                    str3 = java.lang.String.format("Bundle returned from %s contains null signatures list.", str2);
                }
            } else {
                str3 = java.lang.String.format("Bundle returned from %s doesn't contain required fields.", str2);
            }
        }
        b.a.a.a.a.a.a.b("BillingClient", str3);
        return gVar;
    }
}
