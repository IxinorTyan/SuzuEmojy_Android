package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class f0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.content.Context f740a;

    public f0(android.content.Context context) {
        this.f740a = context;
    }

    public boolean a() {
        return android.os.Build.VERSION.SDK_INT < 23 || android.provider.Settings.canDrawOverlays(this.f740a.getApplicationContext());
    }

    public void b() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            android.content.Intent intent = new android.content.Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.addFlags(268435456);
            try {
                this.f740a.getApplicationContext().startActivity(intent);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }
}
