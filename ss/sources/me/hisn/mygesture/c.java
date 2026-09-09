package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class c implements java.lang.Thread.UncaughtExceptionHandler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.content.Context f566a;

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.lang.Throwable f567a;

        a(java.lang.Throwable th) {
            this.f567a = th;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.os.Looper.prepare();
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", java.util.Locale.CHINA);
            java.util.Date date = new java.util.Date(java.lang.System.currentTimeMillis());
            android.content.SharedPreferences sharedPreferences = me.hisn.mygesture.c.this.f566a.getSharedPreferences("31410", 0);
            java.lang.String strA = me.hisn.mygesture.c.this.a(this.f567a);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(simpleDateFormat.format(date));
            sb.append("\n");
            sb.append(android.os.Build.BRAND);
            sb.append(":");
            sb.append(android.os.Build.MODEL);
            sb.append("\n");
            sb.append(android.os.Build.VERSION.RELEASE);
            sb.append("(");
            sb.append(android.os.Build.VERSION.SDK_INT);
            sb.append(")\n");
            me.hisn.mygesture.c cVar = me.hisn.mygesture.c.this;
            sb.append(cVar.a(cVar.f566a));
            sb.append("\n");
            sb.append(me.hisn.mygesture.P.N != null);
            sb.append("\n");
            java.lang.String string = sb.toString();
            sharedPreferences.edit().putString("31420", string + strA).apply();
            new me.hisn.mygesture.b().a(me.hisn.mygesture.c.this.f566a, string + strA);
            new me.hisn.utils.b0().a(me.hisn.mygesture.c.this.f566a, me.hisn.mygesture.R.string.crash_restart_text, 0);
            android.os.Looper.loop();
        }
    }

    c(android.content.Context context) {
        this.f566a = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String a(android.content.Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String a(java.lang.Throwable th) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("\nMessage:\n");
        sb.append(th.getMessage());
        sb.append("\n");
        sb.append("\nTrace:\n");
        for (java.lang.StackTraceElement stackTraceElement : th.getStackTrace()) {
            sb.append(stackTraceElement.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    void a() {
        java.lang.Thread.getDefaultUncaughtExceptionHandler();
        java.lang.Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(java.lang.Thread thread, java.lang.Throwable th) {
        new java.lang.Thread(new me.hisn.mygesture.c.a(th)).start();
        android.os.SystemClock.sleep(1200L);
        java.lang.System.exit(1);
    }
}
