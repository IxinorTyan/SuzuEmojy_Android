package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class d0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.content.Context f735a;

    public d0(android.content.Context context) {
        this.f735a = context;
    }

    public android.app.Notification a(int i, java.lang.String str, java.lang.String str2, android.content.Intent intent, boolean z) {
        android.app.PendingIntent activity;
        android.app.Notification.Builder priority;
        android.content.Context context;
        int i2;
        if (intent != null) {
            intent.addFlags(32768);
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                context = this.f735a;
                i2 = 201326592;
            } else {
                context = this.f735a;
                i2 = 134217728;
            }
            activity = android.app.PendingIntent.getActivity(context, i, intent, i2);
        } else {
            activity = null;
        }
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.f735a.getSystemService("notification");
        int i3 = android.os.Build.VERSION.SDK_INT;
        int i4 = me.hisn.mygesture.R.drawable.ic_null;
        if (i3 >= 26) {
            int i5 = me.hisn.mygesture.R.string.fore_service;
            android.content.Context context2 = this.f735a;
            java.lang.String string = z ? context2.getString(me.hisn.mygesture.R.string.fore_service) : context2.getPackageName();
            android.content.Context context3 = this.f735a;
            if (!z) {
                i5 = me.hisn.mygesture.R.string.app_name;
            }
            android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(string, context3.getString(i5), 2);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            android.app.Notification.Builder contentText = new android.app.Notification.Builder(this.f735a, string).setContentTitle(str).setContentText(str2);
            if (!z) {
                i4 = me.hisn.mygesture.R.drawable.ic_glogo_mini;
            }
            priority = contentText.setSmallIcon(i4).setColor(this.f735a.getColor(me.hisn.mygesture.R.color.green)).setContentIntent(activity);
        } else {
            android.app.Notification.Builder contentText2 = new android.app.Notification.Builder(this.f735a).setContentTitle(str).setContentText(str2);
            if (!z) {
                i4 = me.hisn.mygesture.R.drawable.ic_glogo_mini;
            }
            priority = contentText2.setSmallIcon(i4).setColor(-16739451).setContentIntent(activity).setPriority(-1);
        }
        android.app.Notification notificationBuild = priority.setAutoCancel(true).build();
        if (!z && notificationManager != null) {
            notificationBuild.flags |= 32;
            notificationManager.notify(i, notificationBuild);
        }
        return notificationBuild;
    }
}
