package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class j0 {

    class a extends me.hisn.utils.z {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f799b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ java.lang.String[] f800c;
        final /* synthetic */ int d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(me.hisn.utils.j0 j0Var, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, android.app.Activity activity, java.lang.String[] strArr, int i2) {
            super(context, str, str2, str3, str4, z, i);
            this.f799b = activity;
            this.f800c = strArr;
            this.d = i2;
        }

        @Override // me.hisn.utils.z
        public void d() {
            this.f799b.requestPermissions(this.f800c, this.d);
        }
    }

    public void a(android.app.Activity activity) {
        if (android.os.Build.VERSION.SDK_INT < 23 || android.provider.Settings.System.canWrite(activity)) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(android.net.Uri.parse("package:" + activity.getPackageName()));
        intent.addFlags(268435456);
        activity.startActivityForResult(intent, 0);
    }

    public boolean a(android.app.Activity activity, java.lang.String[] strArr, int i) {
        if (android.os.Build.VERSION.SDK_INT < 23 || a(strArr, activity.getApplicationContext())) {
            return true;
        }
        new me.hisn.utils.j0.a(this, activity, null, activity.getString(me.hisn.mygesture.R.string.need_write_storage_permission), activity.getString(me.hisn.mygesture.R.string.yes_text), activity.getString(me.hisn.mygesture.R.string.cancel_text), true, -2, activity, strArr, i);
        return false;
    }

    public boolean a(java.lang.String[] strArr, android.content.Context context) {
        if (android.os.Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (java.lang.String str : strArr) {
            if (context.checkSelfPermission(str) != 0) {
                return false;
            }
        }
        return true;
    }
}
