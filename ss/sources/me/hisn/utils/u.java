package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class u {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static int f872a;

    public void a() {
        a(f872a == 0);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0026  */
    public void a(boolean z) {
        android.view.View viewD;
        int i;
        android.app.NotificationManager notificationManager;
        android.view.WindowManager windowManagerK = me.hisn.mygesture.MAS.k();
        if (windowManagerK == null || (viewD = me.hisn.mygesture.MAS.d()) == null) {
            return;
        }
        android.view.WindowManager.LayoutParams layoutParams = (android.view.WindowManager.LayoutParams) viewD.getLayoutParams();
        boolean z2 = true;
        int i2 = layoutParams.flags;
        int i3 = i2 & 128;
        if (z) {
            if (i3 == 0) {
                i = i2 | 128;
                layoutParams.flags = i;
            } else {
                z2 = false;
            }
        } else if (i3 != 0) {
            i = i2 & (-129);
            layoutParams.flags = i;
        } else {
            z2 = false;
        }
        if (z2) {
            try {
                windowManagerK.updateViewLayout(viewD, layoutParams);
                int i4 = me.hisn.mygesture.R.string.keep_screen_on_on;
                if (z) {
                    f872a = (int) (java.lang.System.currentTimeMillis() / 1000);
                    android.content.Intent intent = new android.content.Intent(viewD.getContext(), (java.lang.Class<?>) me.hisn.utils.RemoteA.class);
                    intent.putExtra("31415", 82);
                    new me.hisn.utils.d0(viewD.getContext()).a(f872a, viewD.getContext().getString(me.hisn.mygesture.R.string.keep_screen_on_on), viewD.getContext().getString(me.hisn.mygesture.R.string.click_to_cancel), intent, false);
                } else {
                    if (f872a > 0 && (notificationManager = (android.app.NotificationManager) viewD.getContext().getSystemService("notification")) != null) {
                        try {
                            notificationManager.cancel(f872a);
                            f872a = 0;
                        } catch (java.lang.Exception e) {
                            e.printStackTrace();
                        }
                    }
                    i4 = me.hisn.mygesture.R.string.keep_screen_on_canceled;
                }
                new me.hisn.utils.b0().a(viewD.getContext().getApplicationContext(), i4, 0);
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
