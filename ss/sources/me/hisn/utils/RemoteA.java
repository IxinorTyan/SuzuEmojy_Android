package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class RemoteA extends android.app.Activity {

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f718a;

        a(int i) {
            this.f718a = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.mypanel.h(me.hisn.utils.RemoteA.this.getApplicationContext()).a(this.f718a, true);
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f720a;

        b(int i) {
            this.f720a = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.mypanel.h(me.hisn.utils.RemoteA.this.getApplicationContext()).b(this.f720a, true);
        }
    }

    private void a() {
        android.app.NotificationManager notificationManager;
        int i;
        switch (getIntent().getIntExtra("31415", 0)) {
            case me.hisn.mygesture.g.ConstraintSet_layout_goneMarginTop /* 79 */:
                try {
                    ((android.app.AlarmManager) getSystemService("alarm")).cancel((android.app.PendingIntent) getIntent().getParcelableExtra("31417"));
                    return;
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 80:
                new java.lang.Thread(new me.hisn.utils.RemoteA.b(getIntent().getIntExtra("31416", -1))).start();
                notificationManager = (android.app.NotificationManager) getSystemService("notification");
                i = 1001;
                break;
            case 81:
                new java.lang.Thread(new me.hisn.utils.RemoteA.a(getIntent().getIntExtra("31416", -1))).start();
                notificationManager = (android.app.NotificationManager) getSystemService("notification");
                i = 1002;
                break;
            case 82:
                new me.hisn.utils.u().a(false);
                return;
            case 83:
                new me.hisn.utils.m().a(getApplicationContext(), false);
                return;
            default:
                return;
        }
        notificationManager.cancel(i);
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        a();
        finish();
    }
}
