package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.content.Context f727a;

    public a(android.content.Context context) {
        this.f727a = context;
    }

    public boolean a() {
        java.lang.String string = android.provider.Settings.Secure.getString(this.f727a.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            return false;
        }
        java.lang.String str = this.f727a.getPackageName() + "/" + me.hisn.mygesture.MAS.class.getCanonicalName();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(this.f727a.getPackageName());
        sb.append("/.");
        sb.append(me.hisn.mygesture.MAS.class.getSimpleName());
        return string.contains(str) || string.contains(sb.toString());
    }

    public void b() {
        try {
            android.content.Intent intent = new android.content.Intent("android.settings.ACCESSIBILITY_SETTINGS");
            intent.addFlags(272629760);
            this.f727a.startActivity(intent);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
