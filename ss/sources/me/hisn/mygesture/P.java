package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class P extends android.app.Application {
    public static boolean A = false;
    public static int B = 0;
    public static java.lang.String C = null;
    public static boolean D = false;
    public static boolean E = false;
    public static int F = 0;
    public static int G = 0;
    public static int H = 0;
    static int I = 0;
    static int J = 0;
    static int K = 0;
    public static int L = 0;
    static int M = 0;
    public static java.lang.String N = null;
    static int O = 0;
    public static final boolean P = false;
    static int Q = 0;
    public static int R = 0;
    static int S = 0;
    static int T = -16777216;
    static int U = 5;
    static boolean V = false;
    public static boolean W = false;
    static int X = 0;
    public static int Y = -16739451;
    static int Z = 5;
    public static java.lang.String a0 = "";
    static boolean b0 = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static int f536c = 110;
    static boolean c0 = false;
    public static boolean d = false;
    static boolean d0 = false;
    public static int e = 0;
    public static boolean e0 = false;
    public static int f = 0;
    static int f0 = 0;
    public static int g = 0;
    public static java.lang.String g0 = null;
    public static int h = 0;
    public static boolean h0 = false;
    public static int i = 0;
    public static boolean i0 = false;
    public static boolean j = false;
    public static boolean j0 = false;
    public static boolean k = false;
    public static int k0 = 0;
    public static int l = 16;
    public static int l0 = 0;
    public static int m = 22;
    public static boolean m0 = false;
    public static int n = -10066330;
    public static int n0 = 0;
    public static int o = -301989888;
    static boolean o0;
    public static java.lang.String p;
    static android.app.PendingIntent p0;
    public static boolean q;
    static boolean q0;
    static int r;
    static boolean r0;
    public static android.content.SharedPreferences s;
    public static int s0;
    static boolean t;
    public static long t0;
    static boolean u;
    public static long u0;
    static boolean v;
    static int w;
    public static int x;
    public static boolean y;
    public static boolean z;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public android.view.View f537a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private me.hisn.mygesture.k f538b;

    class a implements java.lang.Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.utils.C.CP(me.hisn.mygesture.P.this.getApplicationContext(), new me.hisn.utils.p0().b(me.hisn.mygesture.P.this.getApplicationContext()), 0);
            try {
                java.lang.Thread.sleep(500L);
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
            new me.hisn.utils.d().a(me.hisn.mygesture.P.this.getApplicationContext());
        }
    }

    private java.lang.String a(android.content.Context context, int i2) {
        java.util.List<android.app.ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((android.app.ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
            return null;
        }
        for (android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == i2) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    private void b() {
        w = s.getInt("edge_anim_type", 2);
        t = s.getBoolean("left_side_enabled", true);
        u = s.getBoolean("right_side_enabled", true);
        v = s.getBoolean("bottom_side_enabled", true);
        x = s.getInt("31424", 0);
        z = s.getBoolean("hide_when_soft_input", false);
        D = s.getBoolean("hands_up_mode", false);
        E = s.getBoolean("no_up_for_long", true);
        d0 = s.getBoolean("red_package", false);
        e0 = s.getBoolean("compat_home", false);
        e = s.getInt("41416", 0);
        f = s.getInt("41417", 0);
        g = s.getInt("41418", 0);
        h = s.getInt("41419", 0);
        i = s.getInt("41420", 0);
        V = s.getBoolean("is_show_notification_edge", false);
        f0 = s.getInt("bottom_split_count", 1);
        I = s.getInt("left_edge_height", 6);
        J = s.getInt("left_edge_width", 4);
        K = s.getInt("left_edge_position", 1);
        M = s.getInt("right_edge_height", 6);
        O = s.getInt("right_edge_width", 4);
        Q = s.getInt("right_edge_position", 1);
        S = s.getInt("bottom_edge_height", 4);
        int i2 = s.getInt("long_press_time", 6);
        r = i2;
        if (i2 < 2) {
            r = 2;
        }
        y = s.getBoolean("41421", true);
    }

    private void c() {
        if (N == null) {
            if (w > 2) {
                w = 1;
                return;
            }
            return;
        }
        T = s.getInt("edge_shadow_color", -16777216);
        U = s.getInt("edge_shadow_width", 5);
        Y = s.getInt("notification_edge_color", -16739451);
        Z = s.getInt("notification_edge_width", 5);
        a0 = s.getString("breath_white_list", "");
        b0 = s.getBoolean("breath_shadow_random_color", false);
        W = s.getBoolean("breath_on_lock_screen", false);
        c0 = s.getBoolean("breath_private", false);
        h0 = s.getBoolean("reversed_order_for_switch_list", false);
        i0 = s.getBoolean("31417", false);
        d = s.getBoolean("31418", false);
        F = s.getInt("home_anim", 0);
        G = s.getInt("open_app_anim", 0);
        H = s.getInt("prev_app_anim", 0);
        X = s.getInt("breath_count", 0);
        j = s.getBoolean("show_touch_bar", false);
        k = s.getBoolean("hide_touch_bar_when_land", false);
        n = s.getInt("touch_bar_color", -1717986919);
        m = s.getInt("touch_bar_width", 22);
        l = s.getInt("touch_bar_height", 16);
        q = s.getBoolean("colorful_touch_bar", false);
    }

    public void a() {
        b();
        c();
    }

    public void a(android.content.Context context) {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        me.hisn.mygesture.k kVar = new me.hisn.mygesture.k();
        this.f538b = kVar;
        try {
            context.registerReceiver(kVar, intentFilter);
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        new me.hisn.mygesture.c(this).a();
        if (getPackageName().equals(a(this, android.os.Process.myPid()))) {
            me.hisn.utils.C.I(this);
            a();
            a(this);
            new java.lang.Thread(new me.hisn.mygesture.P.a()).start();
            u0 = java.lang.System.currentTimeMillis();
        }
    }
}
