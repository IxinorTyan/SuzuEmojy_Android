package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class MAS extends android.accessibilityservice.AccessibilityService {
    private static me.hisn.mygesture.MAS m;
    private static android.os.Vibrator n;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.view.WindowManager f513a = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.util.List<android.view.View> f514b = new java.util.ArrayList();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private me.hisn.mygesture.a f515c;
    private me.hisn.mygesture.MAS.i d;
    private java.lang.String e;
    private long f;
    private int g;
    private int h;
    private int i;
    private int j;
    private android.view.View k;
    private android.view.View l;

    class a implements java.lang.Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            java.lang.String strE = me.hisn.mygesture.MAS.e();
            if (strE.isEmpty() || strE.equals(me.hisn.mygesture.MAS.this.e) || "com.android.systemui".equals(strE)) {
                return;
            }
            me.hisn.mygesture.MAS.this.e = strE;
            int iA = me.hisn.utils.l0.a(me.hisn.mygesture.MAS.this.getApplicationContext(), strE + "");
            me.hisn.mygesture.MAS.d(iA);
            if (me.hisn.mygesture.P.q) {
                me.hisn.mygesture.MAS.a(strE, iA);
            }
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f517a;

        b(android.widget.ImageView imageView) {
            this.f517a = imageView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f517a.setColorFilter((android.graphics.ColorFilter) null);
            this.f517a.setImageDrawable(me.hisn.utils.o.b(me.hisn.mygesture.MAS.m.getApplicationContext()));
            this.f517a.invalidate();
        }
    }

    class c implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f518a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f519b;

        c(int i, android.widget.ImageView imageView) {
            this.f518a = i;
            this.f519b = imageView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f519b.setColorFilter(this.f518a, android.graphics.PorterDuff.Mode.SRC_ATOP);
            this.f519b.invalidate();
        }
    }

    class d implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f520a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.graphics.drawable.Drawable f521b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f522c;

        d(int i, android.graphics.drawable.Drawable drawable, android.widget.ImageView imageView) {
            this.f520a = i;
            this.f521b = drawable;
            this.f522c = imageView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f521b.setColorFilter(android.graphics.Color.argb(android.graphics.Color.alpha(me.hisn.mygesture.P.n), android.graphics.Color.red(this.f520a), android.graphics.Color.green(this.f520a), android.graphics.Color.blue(this.f520a)), android.graphics.PorterDuff.Mode.SRC);
            this.f522c.invalidate();
        }
    }

    class e implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f523a;

        e(android.view.View view) {
            this.f523a = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f523a.setAlpha(0.3f);
        }
    }

    class f implements android.view.ViewTreeObserver.OnGlobalLayoutListener {
        f() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            me.hisn.mygesture.MAS mas = me.hisn.mygesture.MAS.this;
            if (mas.a(mas.k)) {
                if (me.hisn.mygesture.MAS.this.d != null) {
                    me.hisn.mygesture.MAS.this.d.b();
                }
                me.hisn.mygesture.MAS.this.n();
                me.hisn.mygesture.MAS.this.v();
            }
        }
    }

    class g implements android.view.ViewTreeObserver.OnGlobalLayoutListener {
        g() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            me.hisn.mygesture.MAS mas = me.hisn.mygesture.MAS.this;
            if (mas.a(mas.l)) {
                if (me.hisn.mygesture.MAS.this.d != null) {
                    me.hisn.mygesture.MAS.this.d.b();
                }
                me.hisn.mygesture.MAS.this.n();
                me.hisn.mygesture.MAS.this.v();
            }
        }
    }

    class h implements java.lang.Runnable {
        h(int i) {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.MAS.this.m();
        }
    }

    public interface i {
        void a();

        void b();
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0052  */
    public static void a(int i2, int i3, int i4, int i5, int i6) {
        me.hisn.mygesture.MAS mas;
        if (!me.hisn.mygesture.P.m0 || (mas = m) == null) {
            return;
        }
        for (android.view.View view : mas.f514b) {
            if (i2 == ((java.lang.Integer) view.getTag()).intValue()) {
                android.view.WindowManager.LayoutParams layoutParams = (android.view.WindowManager.LayoutParams) view.getLayoutParams();
                if (i2 == me.hisn.mygesture.P.f536c) {
                    if (i3 > -1) {
                        layoutParams.width = i3;
                    }
                    if (i4 > -1) {
                        layoutParams.height = i4;
                    }
                    if (i5 != -1) {
                        layoutParams.x = i5;
                    }
                    if (i6 != -1) {
                        layoutParams.y = i6;
                    }
                } else {
                    if (i3 > -1) {
                        layoutParams.width = i3;
                    }
                    if (i4 > -1) {
                        layoutParams.height = i4;
                    }
                    if ((i2 == me.hisn.mygesture.P.L || i2 == me.hisn.mygesture.P.R) && i6 != -1) {
                        layoutParams.y = i6;
                    }
                }
                try {
                    m.f513a.updateViewLayout(view, layoutParams);
                } catch (java.lang.Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void a(android.view.View view, int i2) {
        if (i2 < 0) {
            if (n != null) {
                if (android.os.Build.VERSION.SDK_INT >= 26) {
                    n.vibrate(android.os.VibrationEffect.createOneShot(i2 * (-1), -1));
                    return;
                } else {
                    n.vibrate(i2 * (-1));
                    return;
                }
            }
            return;
        }
        if (i2 == 1) {
            view.performLongClick();
            return;
        }
        if (i2 == 3) {
            view.performHapticFeedback(0, 2);
        } else if (i2 == 4) {
            view.performHapticFeedback(1, 2);
        } else {
            if (i2 != 5) {
                return;
            }
            view.performHapticFeedback(3, 2);
        }
    }

    public static synchronized void a(java.lang.String str, int i2) {
        if (me.hisn.mygesture.P.j && me.hisn.mygesture.P.v && m != null) {
            if (i2 == 0) {
                if (str == null) {
                    str = e();
                }
                i2 = me.hisn.utils.k.a(m.getApplicationContext().getPackageManager(), str);
            }
            if (i2 != 0 && m.f514b != null) {
                for (android.view.View view : m.f514b) {
                    if (me.hisn.mygesture.P.B == ((java.lang.Integer) view.getTag()).intValue()) {
                        android.widget.ImageView imageView = (android.widget.ImageView) view;
                        android.graphics.drawable.Drawable drawable = imageView.getDrawable();
                        if (drawable != null) {
                            imageView.post(new me.hisn.mygesture.MAS.d(i2, drawable, imageView));
                        }
                        return;
                    }
                }
            }
        }
    }

    public static void a(me.hisn.mygesture.MAS.i iVar) {
        me.hisn.mygesture.MAS mas = m;
        if (mas != null) {
            mas.d = iVar;
        }
    }

    public static synchronized void a(me.hisn.mygesture.a.h hVar) {
        if (m != null && m.f515c != null) {
            m.f515c.a(hVar);
        }
    }

    static void a(boolean z) {
        me.hisn.mygesture.MAS mas;
        if (!me.hisn.mygesture.P.m0 || (mas = m) == null) {
            return;
        }
        for (android.view.View view : mas.f514b) {
            if (((java.lang.Integer) view.getTag()).intValue() != me.hisn.mygesture.P.f536c) {
                if (z) {
                    view.setBackgroundColor(-2013228155);
                } else {
                    view.setBackground(null);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:18:0x004d A[PHI: r3
  0x004d: PHI (r3v6 boolean) = (r3v3 boolean), (r3v10 boolean) binds: [B:16:0x0048, B:9:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    public boolean a(android.view.View view) {
        boolean z;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = me.hisn.mygesture.P.k0;
        b(false);
        boolean z2 = true;
        if (view == this.k) {
            if (this.g != iArr[1]) {
                this.g = iArr[1];
                z = true;
            } else {
                z = false;
            }
            int height = (me.hisn.mygesture.P.l0 - iArr[1]) - view.getHeight();
            if (this.j != height) {
                this.j = height;
            } else {
                z2 = z;
            }
        } else {
            if (this.h != iArr[0]) {
                this.h = iArr[0];
                z = true;
            } else {
                z = false;
            }
            int width = (me.hisn.mygesture.P.k0 - iArr[0]) - view.getWidth();
            if (this.i != width) {
                this.i = width;
            } else {
                z2 = z;
            }
        }
        if (i2 == me.hisn.mygesture.P.k0) {
            return z2;
        }
        view.postDelayed(new me.hisn.mygesture.MAS.h(i2), 500L);
        return false;
    }

    private void b() {
        if (this.k == null) {
            this.k = new android.view.View(m.getApplicationContext());
            this.l = new android.view.View(m.getApplicationContext());
            this.k.getViewTreeObserver().addOnGlobalLayoutListener(new me.hisn.mygesture.MAS.f());
            this.l.getViewTreeObserver().addOnGlobalLayoutListener(new me.hisn.mygesture.MAS.g());
            try {
                this.f513a.addView(this.k, new me.hisn.utils.v().a(false, 8388659, 1, -1, 0, 0, 0, false));
                this.f513a.addView(this.l, new me.hisn.utils.v().a(false, 8388659, -1, 1, 0, 0, 0, false));
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void b(int i2) {
        me.hisn.mygesture.MAS mas = m;
        if (mas != null) {
            mas.performGlobalAction(i2);
        }
    }

    public static void b(android.view.View view) {
        try {
            if (((java.lang.Integer) view.getTag()).intValue() == me.hisn.mygesture.P.f536c) {
                view.setAlpha(0.0f);
                view.postDelayed(new me.hisn.mygesture.MAS.e(view), 1000L);
            } else if (m != null && m.f515c != null && me.hisn.mygesture.P.w > 0) {
                m.f515c.a(false);
            }
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }

    private void b(boolean z) {
        if (this.f513a == null) {
            android.view.WindowManager windowManager = (android.view.WindowManager) getSystemService("window");
            this.f513a = windowManager;
            this.f515c = new me.hisn.mygesture.a(windowManager);
        }
        android.graphics.Point point = new android.graphics.Point();
        this.f513a.getDefaultDisplay().getRealSize(point);
        me.hisn.mygesture.P.k0 = point.x;
        me.hisn.mygesture.P.l0 = point.y;
        if (z) {
            c(-1);
        }
    }

    private void c() {
        if (me.hisn.mygesture.P.m0) {
            p();
        }
        b(true);
        b();
        java.util.List<android.view.View> listA = this.f515c.a(getApplicationContext());
        this.f514b = listA;
        if (listA.size() == 0) {
            return;
        }
        for (android.view.View view : this.f514b) {
            try {
                this.f513a.addView(view, view.getLayoutParams());
                me.hisn.mygesture.P.m0 = true;
            } catch (java.lang.Exception e2) {
                me.hisn.mygesture.P.m0 = false;
                e2.printStackTrace();
            }
        }
        q();
    }

    static void c(int i2) {
        if (i2 == -1) {
            i2 = me.hisn.mygesture.P.s.getInt("sensitivity_size", 3);
        }
        me.hisn.mygesture.P.n0 = java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) / ((i2 * 2) + 3);
    }

    private void c(boolean z) {
        if (z) {
            new me.hisn.utils.d0(getApplicationContext()).a(1, getString(me.hisn.mygesture.R.string.gesture_closed), getString(me.hisn.mygesture.R.string.notification_gesture_closed), new android.content.Intent(getApplicationContext(), (java.lang.Class<?>) me.hisn.mygesture.AA.class), false);
            return;
        }
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getSystemService("notification");
        if (notificationManager != null) {
            notificationManager.cancel(1);
        }
    }

    public static android.view.View d() {
        me.hisn.mygesture.MAS mas = m;
        if (mas != null) {
            return mas.k;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void d(int i2) {
        if (me.hisn.mygesture.P.s.getBoolean("f41433", false) && i2 != 0 && m != null && !me.hisn.utils.o.a(m.getApplicationContext())) {
            for (android.view.View view : m.f514b) {
                if (me.hisn.mygesture.P.f536c == ((java.lang.Integer) view.getTag()).intValue()) {
                    android.widget.ImageView imageView = (android.widget.ImageView) view;
                    imageView.post(new me.hisn.mygesture.MAS.c(i2, imageView));
                    return;
                }
            }
        }
    }

    public static void d(boolean z) {
        m.p();
        if (z) {
            m.c(true);
            new me.hisn.utils.b0().a(m.getApplicationContext(), me.hisn.mygesture.R.string.gestrue_closed, 0);
        }
    }

    public static java.lang.String e() {
        android.view.accessibility.AccessibilityNodeInfo rootInActiveWindow;
        try {
            rootInActiveWindow = m.getRootInActiveWindow();
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
            rootInActiveWindow = null;
        }
        if (rootInActiveWindow == null) {
            return "";
        }
        return ((java.lang.Object) rootInActiveWindow.getPackageName()) + "";
    }

    public static android.view.accessibility.AccessibilityNodeInfo f() {
        try {
            return m.getRootInActiveWindow();
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static int g() {
        me.hisn.mygesture.MAS mas = m;
        if (mas == null) {
            return 0;
        }
        return mas.j;
    }

    public static int h() {
        me.hisn.mygesture.MAS mas = m;
        if (mas == null) {
            return 0;
        }
        return mas.h;
    }

    public static int i() {
        me.hisn.mygesture.MAS mas = m;
        if (mas == null) {
            return 0;
        }
        return mas.i;
    }

    public static int j() {
        me.hisn.mygesture.MAS mas = m;
        if (mas == null) {
            return 0;
        }
        return mas.g;
    }

    public static android.view.WindowManager k() {
        me.hisn.mygesture.MAS mas = m;
        if (mas != null) {
            return mas.f513a;
        }
        return null;
    }

    public static boolean l() {
        me.hisn.mygesture.MAS mas = m;
        return (mas == null || mas.getServiceInfo() == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        me.hisn.mygesture.MAS.i iVar = this.d;
        if (iVar != null) {
            iVar.a();
        }
        if (me.hisn.mygesture.P.x != 2) {
            v();
        } else if (me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0) {
            p();
            c(true);
        } else {
            c();
            c(false);
        }
        n();
        int i2 = me.hisn.mygesture.P.s.getInt("41423", 0);
        if (i2 <= 0 || android.os.Build.VERSION.SDK_INT > 29) {
            return;
        }
        if (me.hisn.mygesture.P.k0 <= me.hisn.mygesture.P.l0) {
            new me.hisn.utils.d().d(getApplicationContext());
        } else if (i2 == 1) {
            new me.hisn.utils.d().b(getApplicationContext());
        } else {
            new me.hisn.utils.d().e(getApplicationContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        if (me.hisn.mygesture.P.w > 0) {
            this.f515c.a(false);
        }
    }

    static void o() {
        m.p();
    }

    private void p() {
        if (me.hisn.mygesture.P.m0) {
            java.util.Iterator<android.view.View> it = this.f514b.iterator();
            while (it.hasNext()) {
                try {
                    this.f513a.removeView(it.next());
                    me.hisn.mygesture.P.m0 = false;
                } catch (java.lang.Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        q();
    }

    private void q() {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            try {
                android.service.quicksettings.TileService.requestListeningState(getApplicationContext(), new android.content.ComponentName(getPackageName(), me.hisn.utils.TS.class.getName()));
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void r() {
        me.hisn.mygesture.a aVar;
        me.hisn.mygesture.MAS mas = m;
        if (mas == null || (aVar = mas.f515c) == null) {
            return;
        }
        aVar.h = null;
    }

    public static void s() {
        me.hisn.mygesture.MAS mas;
        if (!me.hisn.mygesture.P.s.getBoolean("f41433", false) || (mas = m) == null) {
            return;
        }
        for (android.view.View view : mas.f514b) {
            if (me.hisn.mygesture.P.f536c == ((java.lang.Integer) view.getTag()).intValue()) {
                android.widget.ImageView imageView = (android.widget.ImageView) view;
                imageView.post(new me.hisn.mygesture.MAS.b(imageView));
                return;
            }
        }
    }

    public static boolean t() {
        me.hisn.mygesture.MAS mas = m;
        if (mas == null || !new me.hisn.utils.f0(mas.getApplicationContext()).a()) {
            return false;
        }
        m.c();
        return me.hisn.mygesture.P.m0;
    }

    private void u() {
        m = null;
        this.k = null;
        this.l = null;
        p();
        java.lang.System.exit(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void v() {
        java.util.List<android.view.View> listA = this.f515c.a(getApplicationContext());
        this.f514b = listA;
        for (android.view.View view : listA) {
            try {
                this.f513a.updateViewLayout(view, view.getLayoutParams());
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (android.os.Build.VERSION.SDK_INT < 28) {
            long eventTime = accessibilityEvent.getEventTime();
            if (eventTime - this.f < 300) {
                return;
            } else {
                this.f = eventTime;
            }
        } else if (accessibilityEvent.getWindowChanges() != 1) {
            return;
        }
        new java.lang.Thread(new me.hisn.mygesture.MAS.a()).start();
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onInterrupt() {
    }

    @Override // android.accessibilityservice.AccessibilityService
    protected void onServiceConnected() {
        super.onServiceConnected();
        m = this;
        n = (android.os.Vibrator) getSystemService("vibrator");
        if (t()) {
            new me.hisn.utils.b0().a(getApplicationContext(), me.hisn.mygesture.R.string.running, 0);
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        u();
        return super.onUnbind(intent);
    }
}
