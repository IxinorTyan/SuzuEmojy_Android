package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public abstract class j {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f798b = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f797a = 0;

    private boolean c(android.content.Context context, int i) {
        return a(context, this.f797a + i);
    }

    private void d(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent(context.getApplicationContext(), (java.lang.Class<?>) me.hisn.utils.PermissionA.class);
        intent.putExtra("31415", 80);
        intent.addFlags(268435456);
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        b();
    }

    public int a() {
        if (this.f798b == 0) {
            try {
                android.content.res.Resources system = android.content.res.Resources.getSystem();
                int identifier = system.getIdentifier("config_screenBrightnessSettingMaximum", "integer", "android");
                if (identifier != 0) {
                    this.f798b = system.getInteger(identifier);
                }
            } catch (java.lang.Exception unused) {
                this.f798b = 255;
            }
        }
        return this.f798b;
    }

    public int a(android.content.Context context) {
        try {
            return android.provider.Settings.System.getInt(context.getContentResolver(), "screen_brightness_mode");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void a(android.content.Context context, int i, int i2, int i3, int i4, int i5) {
        c(context, (int) (((((i == 3 || i == 4) ? i5 - i3 : i2 - i4) * 2.0f) / java.lang.Math.min(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0)) * this.f798b));
    }

    public synchronized boolean a(android.content.Context context, int i) {
        boolean z;
        z = false;
        if (i < 0) {
            i = 0;
        } else {
            try {
                if (i > a()) {
                    i = a();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (android.os.Build.VERSION.SDK_INT < 23 || android.provider.Settings.System.canWrite(context)) {
            try {
                android.provider.Settings.System.putInt(context.getContentResolver(), "screen_brightness", i);
                z = true;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        } else {
            d(context);
        }
        return z;
    }

    public int b(android.content.Context context) {
        try {
            return android.provider.Settings.System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (android.provider.Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public abstract void b();

    public boolean b(android.content.Context context, int i) {
        if (android.os.Build.VERSION.SDK_INT < 23 || android.provider.Settings.System.canWrite(context)) {
            try {
                android.provider.Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", i);
                return true;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        } else {
            d(context);
        }
        return false;
    }

    public void c(android.content.Context context) {
        this.f797a = b(context);
        this.f798b = a();
        b(context, 0);
    }
}
