package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class c0 {
    public boolean a(android.content.Context context) {
        new me.hisn.utils.b0().a(context.getApplicationContext(), me.hisn.mygesture.R.string.notification_permission_tip, 0);
        try {
            try {
                android.content.Intent intent = new android.content.Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.addFlags(268435456);
                context.startActivity(intent);
                return true;
            } catch (android.content.ActivityNotFoundException unused) {
                android.content.Intent intent2 = new android.content.Intent();
                intent2.addFlags(268435456);
                intent2.setComponent(new android.content.ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity"));
                intent2.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                context.startActivity(intent2);
                return true;
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean b(android.content.Context context) {
        java.lang.String packageName = context.getApplicationContext().getPackageName();
        java.lang.String string = android.provider.Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "enabled_notification_listeners");
        if (string != null) {
            return string.contains(packageName);
        }
        return false;
    }
}
