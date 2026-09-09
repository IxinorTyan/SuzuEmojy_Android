package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class q {
    public void a(android.content.Context context, java.lang.String str, android.os.Bundle bundle) {
        android.content.Intent intent = new android.content.Intent("me.hisn.appcage.release");
        intent.putExtra("PACKAGE_NAME", str);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        try {
            context.startActivity(intent, bundle);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
