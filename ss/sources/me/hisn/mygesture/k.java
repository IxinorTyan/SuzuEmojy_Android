package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class k extends android.content.BroadcastReceiver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.util.List<java.lang.Runnable> f612a;

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if (intent.getAction() == null) {
            return;
        }
        boolean z = me.hisn.mygesture.P.s.getBoolean("disable_gesture_on_lock", false);
        int i = me.hisn.mygesture.P.s.getInt("41424", 0);
        java.lang.String action = intent.getAction();
        byte b2 = -1;
        int iHashCode = action.hashCode();
        if (iHashCode != -2128145023) {
            if (iHashCode == 823795052 && action.equals("android.intent.action.USER_PRESENT")) {
                b2 = 1;
            }
        } else if (action.equals("android.intent.action.SCREEN_OFF")) {
            b2 = 0;
        }
        if (b2 != 0) {
            if (b2 != 1) {
                return;
            }
            if (i > 0 && android.os.Build.VERSION.SDK_INT <= 29) {
                new me.hisn.utils.d().d(context.getApplicationContext());
            }
            if (z) {
                me.hisn.mygesture.MAS.t();
                return;
            }
            return;
        }
        if (me.hisn.mygesture.f.b(context)) {
            if (z) {
                me.hisn.mygesture.MAS.o();
            }
            if (android.os.Build.VERSION.SDK_INT <= 29) {
                if (i == 1) {
                    new me.hisn.utils.d().b(context.getApplicationContext());
                } else if (i == 2) {
                    new me.hisn.utils.d().e(context.getApplicationContext());
                }
            }
            java.util.List<java.lang.Runnable> list = this.f612a;
            if (list != null && list.size() > 0) {
                java.util.Iterator<java.lang.Runnable> it = this.f612a.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
            }
        }
        if (me.hisn.utils.u.f872a > 0) {
            new me.hisn.utils.u().a(false);
        }
    }
}
