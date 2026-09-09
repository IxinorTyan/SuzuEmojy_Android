package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class NotificationManagerService extends android.service.notification.NotificationListenerService {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static me.hisn.mygesture.NotificationManagerService f534b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private long f535a;

    private android.graphics.drawable.Drawable a(java.lang.String str) {
        try {
            return getPackageManager().getApplicationIcon(str);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean a() {
        return f534b != null;
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        f534b = null;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
        super.onListenerConnected();
        f534b = this;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        f534b = null;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(android.service.notification.StatusBarNotification statusBarNotification) {
        java.lang.String str;
        android.app.Notification notification;
        super.onNotificationPosted(statusBarNotification);
        this.f535a = java.lang.System.currentTimeMillis();
        boolean zB = me.hisn.mygesture.f.b(getApplicationContext());
        if (zB) {
            if (!me.hisn.mygesture.P.W) {
                return;
            }
        } else if (!me.hisn.mygesture.P.V) {
            return;
        }
        java.lang.String packageName = statusBarNotification.getPackageName();
        if ("android".equals(packageName) || (str = me.hisn.mygesture.P.a0) == null || !str.contains(packageName) || (notification = statusBarNotification.getNotification()) == null || notification.contentIntent == null) {
            return;
        }
        android.os.Bundle bundle = statusBarNotification.getNotification().extras;
        java.lang.String string = bundle.getString("android.text");
        java.lang.String string2 = bundle.getString("android.title");
        if (string == null) {
            string = "";
        }
        if (string2 == null) {
            string2 = "";
        }
        java.lang.String str2 = string + string2;
        if (str2.isEmpty()) {
            return;
        }
        android.graphics.drawable.Drawable drawableA = null;
        java.lang.String string3 = me.hisn.mygesture.P.s.getString("31416", null);
        if (string3 != null) {
            for (java.lang.String str3 : string3.split(string3.contains(",") ? "," : "，")) {
                if (str2.contains(str3)) {
                    return;
                }
            }
        }
        if (me.hisn.mygesture.P.o0) {
            me.hisn.mygesture.P.o0 = false;
        }
        me.hisn.mygesture.P.p0 = notification.contentIntent;
        java.lang.String str4 = string2 + "\n\n" + string;
        if (zB) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                android.graphics.drawable.Icon largeIcon = notification.getLargeIcon();
                drawableA = largeIcon != null ? largeIcon.loadDrawable(getApplicationContext()) : a(packageName);
            } else {
                drawableA = a(packageName);
            }
        }
        if (!me.hisn.mygesture.P.d0 || !"com.tencent.mm".equals(packageName) || !str4.contains("[微信红包]")) {
            me.hisn.mygesture.f.a(getApplicationContext(), me.hisn.mygesture.MAS.k(), drawableA, str4);
            return;
        }
        try {
            me.hisn.mygesture.P.p0.send();
        } catch (android.app.PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
        if (java.lang.System.currentTimeMillis() - this.f535a > 500) {
            me.hisn.mygesture.P.o0 = false;
            me.hisn.mygesture.P.p0 = null;
        }
    }
}
