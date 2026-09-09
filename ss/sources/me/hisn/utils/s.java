package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class s {
    private void a(android.content.Context context, java.lang.String str, android.os.Bundle bundle) {
        if (str != null) {
            new me.hisn.utils.q().a(context, str, bundle);
        }
    }

    public void a(android.content.Context context, android.app.PendingIntent pendingIntent, android.os.Bundle bundle) {
        if (pendingIntent != null) {
            try {
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    pendingIntent.send(context, 11, null, null, null, null, bundle);
                } else {
                    pendingIntent.send();
                }
            } catch (android.app.PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    public void a(android.content.Context context, android.content.Intent intent, android.os.Bundle bundle, java.lang.String str) {
        if (intent == null) {
            if (str != null) {
                a(context, str, bundle);
                return;
            }
            return;
        }
        intent.addFlags(270532608);
        if (android.os.Build.VERSION.SDK_INT > 22) {
            try {
                android.app.PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 1140850688).send(context, 11, null, null, null, null, bundle);
                return;
            } catch (android.app.PendingIntent.CanceledException e) {
                a(context, str, bundle);
                e.printStackTrace();
                return;
            }
        }
        try {
            context.startActivity(intent, bundle);
        } catch (java.lang.Exception e2) {
            a(context, str, bundle);
            e2.printStackTrace();
        }
    }
}
