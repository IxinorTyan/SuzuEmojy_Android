package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class CaptureService extends android.app.Service {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static android.content.Intent f634a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static android.graphics.Point f635b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private static me.hisn.mypanel.CaptureService.c f636c;

    class a implements java.lang.Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mypanel.CaptureService.this.a(me.hisn.mypanel.CaptureService.f634a);
        }
    }

    class b extends me.hisn.mypanel.a {
        b() {
        }

        @Override // me.hisn.mypanel.a
        public void a() {
            a((android.graphics.Bitmap) null);
        }

        @Override // me.hisn.mypanel.a
        public void a(android.graphics.Bitmap bitmap) {
            if (bitmap != null && me.hisn.mypanel.CaptureService.f636c != null) {
                me.hisn.mypanel.CaptureService.f636c.a(bitmap);
            }
            me.hisn.mypanel.CaptureService.a((me.hisn.mypanel.CaptureService.c) null);
            me.hisn.mypanel.CaptureService.b((android.graphics.Point) null);
            me.hisn.mypanel.CaptureService.this.stopSelf();
        }
    }

    public interface c {
        void a(android.graphics.Bitmap bitmap);
    }

    private static void a(android.content.Context context) {
        context.startActivity(new android.content.Intent(context, (java.lang.Class<?>) me.hisn.mypanel.CapturePA.class).addFlags(268435456));
    }

    public static void a(android.content.Context context, android.content.Intent intent) {
        f634a = intent;
        if (intent == null) {
            a((me.hisn.mypanel.CaptureService.c) null);
            b((android.graphics.Point) null);
            return;
        }
        me.hisn.mypanel.CaptureService.c cVar = f636c;
        if (cVar != null) {
            android.graphics.Point point = f635b;
            if (point != null) {
                a(context, cVar, point);
            } else {
                cVar.a(null);
            }
        }
    }

    public static void a(android.content.Context context, me.hisn.mypanel.CaptureService.c cVar, android.graphics.Point point) {
        a(cVar);
        b(point);
        if (f634a == null) {
            a(context);
        } else {
            if (cVar == null || point == null) {
                return;
            }
            b(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.content.Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            c();
        }
        new me.hisn.mypanel.CaptureService.b().a(this, intent, f635b);
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            stopForeground(true);
        }
    }

    public static void a(me.hisn.mypanel.CaptureService.c cVar) {
        f636c = cVar;
    }

    public static boolean a(android.content.Context context, me.hisn.mypanel.CaptureService.c cVar) {
        if (f634a == null) {
            a(context, cVar, f635b);
        }
        return f634a != null;
    }

    private static void b(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent(context, (java.lang.Class<?>) me.hisn.mypanel.CaptureService.class);
        if (android.os.Build.VERSION.SDK_INT < 29) {
            context.startService(intent);
            return;
        }
        try {
            context.startForegroundService(intent);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(android.graphics.Point point) {
        f635b = point;
    }

    private void c() {
        startForeground((int) java.lang.System.currentTimeMillis(), new me.hisn.utils.d0(this).a(0, getString(me.hisn.mygesture.R.string.app_name), null, null, true));
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(android.content.Intent intent, int i, int i2) {
        if (f634a != null) {
            new java.lang.Thread(new me.hisn.mypanel.CaptureService.a()).start();
        } else {
            stopSelf();
        }
        return super.onStartCommand(intent, i, i2);
    }
}
