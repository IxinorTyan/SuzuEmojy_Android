package a.c.e;

/* JADX INFO: loaded from: classes.dex */
public class m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static java.lang.reflect.Field f99a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static boolean f100b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private static java.lang.reflect.Field f101c;
    private static boolean d;
    private static java.lang.reflect.Field e;
    private static boolean f;

    static class a implements android.view.View.OnApplyWindowInsetsListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ a.c.e.l f102a;

        a(a.c.e.l lVar) {
            this.f102a = lVar;
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        public android.view.WindowInsets onApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets) {
            return (android.view.WindowInsets) a.c.e.p.a(this.f102a.a(view, a.c.e.p.a(windowInsets)));
        }
    }

    static class b extends a.c.e.m.f<java.lang.Boolean> {
        b(int i, java.lang.Class cls, int i2) {
            super(i, cls, i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // a.c.e.m.f
        public java.lang.Boolean a(android.view.View view) {
            return java.lang.Boolean.valueOf(view.isScreenReaderFocusable());
        }
    }

    static class c extends a.c.e.m.f<java.lang.CharSequence> {
        c(int i, java.lang.Class cls, int i2, int i3) {
            super(i, cls, i2, i3);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // a.c.e.m.f
        public java.lang.CharSequence a(android.view.View view) {
            return view.getAccessibilityPaneTitle();
        }
    }

    static class d extends a.c.e.m.f<java.lang.Boolean> {
        d(int i, java.lang.Class cls, int i2) {
            super(i, cls, i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // a.c.e.m.f
        public java.lang.Boolean a(android.view.View view) {
            return java.lang.Boolean.valueOf(view.isAccessibilityHeading());
        }
    }

    static class e implements android.view.ViewTreeObserver.OnGlobalLayoutListener, android.view.View.OnAttachStateChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private java.util.WeakHashMap<android.view.View, java.lang.Boolean> f103a = new java.util.WeakHashMap<>();

        e() {
        }

        private void a(android.view.View view) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        private void a(android.view.View view, boolean z) {
            boolean z2 = view.getVisibility() == 0;
            if (z != z2) {
                if (z2) {
                    a.c.e.m.a(view, 16);
                }
                this.f103a.put(view, java.lang.Boolean.valueOf(z2));
            }
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            for (java.util.Map.Entry<android.view.View, java.lang.Boolean> entry : this.f103a.entrySet()) {
                a(entry.getKey(), entry.getValue().booleanValue());
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
            a(view);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
        }
    }

    static abstract class f<T> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final int f104a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final java.lang.Class<T> f105b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private final int f106c;

        f(int i, java.lang.Class<T> cls, int i2) {
            this(i, cls, 0, i2);
        }

        f(int i, java.lang.Class<T> cls, int i2, int i3) {
            this.f104a = i;
            this.f105b = cls;
            this.f106c = i3;
        }

        private boolean a() {
            return android.os.Build.VERSION.SDK_INT >= 19;
        }

        private boolean b() {
            return android.os.Build.VERSION.SDK_INT >= this.f106c;
        }

        abstract T a(android.view.View view);

        T b(android.view.View view) {
            if (b()) {
                return a(view);
            }
            if (!a()) {
                return null;
            }
            T t = (T) view.getTag(this.f104a);
            if (this.f105b.isInstance(t)) {
                return t;
            }
            return null;
        }
    }

    static {
        new java.util.concurrent.atomic.AtomicInteger(1);
        f = false;
        new a.c.e.m.e();
    }

    public static a.c.e.a a(android.view.View view) {
        android.view.View.AccessibilityDelegate accessibilityDelegateB = b(view);
        if (accessibilityDelegateB == null) {
            return null;
        }
        return accessibilityDelegateB instanceof a.c.e.a.C0002a ? ((a.c.e.a.C0002a) accessibilityDelegateB).f89a : new a.c.e.a(accessibilityDelegateB);
    }

    private static a.c.e.m.f<java.lang.Boolean> a() {
        return new a.c.e.m.d(a.c.a.tag_accessibility_heading, java.lang.Boolean.class, 28);
    }

    public static a.c.e.p a(android.view.View view, a.c.e.p pVar) {
        if (android.os.Build.VERSION.SDK_INT < 21) {
            return pVar;
        }
        android.view.WindowInsets windowInsets = (android.view.WindowInsets) a.c.e.p.a(pVar);
        android.view.WindowInsets windowInsetsDispatchApplyWindowInsets = view.dispatchApplyWindowInsets(windowInsets);
        if (!windowInsetsDispatchApplyWindowInsets.equals(windowInsets)) {
            windowInsets = new android.view.WindowInsets(windowInsetsDispatchApplyWindowInsets);
        }
        return a.c.e.p.a(windowInsets);
    }

    public static void a(android.view.View view, float f2) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            view.setElevation(f2);
        }
    }

    static void a(android.view.View view, int i) {
        if (((android.view.accessibility.AccessibilityManager) view.getContext().getSystemService("accessibility")).isEnabled()) {
            boolean z = d(view) != null;
            if (c(view) != 0 || (z && view.getVisibility() == 0)) {
                android.view.accessibility.AccessibilityEvent accessibilityEventObtain = android.view.accessibility.AccessibilityEvent.obtain();
                accessibilityEventObtain.setEventType(z ? 32 : 2048);
                accessibilityEventObtain.setContentChangeTypes(i);
                view.sendAccessibilityEventUnchecked(accessibilityEventObtain);
                return;
            }
            if (view.getParent() != null) {
                try {
                    view.getParent().notifySubtreeAccessibilityStateChanged(view, view, i);
                } catch (java.lang.AbstractMethodError e2) {
                    android.util.Log.e("ViewCompat", view.getParent().getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                }
            }
        }
    }

    public static void a(android.view.View view, a.c.e.a aVar) {
        if (aVar == null && (b(view) instanceof a.c.e.a.C0002a)) {
            aVar = new a.c.e.a();
        }
        view.setAccessibilityDelegate(aVar == null ? null : aVar.a());
    }

    public static void a(android.view.View view, a.c.e.l lVar) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            if (lVar == null) {
                view.setOnApplyWindowInsetsListener(null);
            } else {
                view.setOnApplyWindowInsetsListener(new a.c.e.m.a(lVar));
            }
        }
    }

    public static void a(android.view.View view, java.lang.Runnable runnable) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, android.animation.ValueAnimator.getFrameDelay());
        }
    }

    public static void a(android.view.View view, java.lang.Runnable runnable, long j) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            view.postOnAnimationDelayed(runnable, j);
        } else {
            view.postDelayed(runnable, android.animation.ValueAnimator.getFrameDelay() + j);
        }
    }

    private static a.c.e.m.f<java.lang.CharSequence> b() {
        return new a.c.e.m.c(a.c.a.tag_accessibility_pane_title, java.lang.CharSequence.class, 8, 28);
    }

    public static a.c.e.p b(android.view.View view, a.c.e.p pVar) {
        if (android.os.Build.VERSION.SDK_INT < 21) {
            return pVar;
        }
        android.view.WindowInsets windowInsets = (android.view.WindowInsets) a.c.e.p.a(pVar);
        android.view.WindowInsets windowInsetsOnApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        if (!windowInsetsOnApplyWindowInsets.equals(windowInsets)) {
            windowInsets = new android.view.WindowInsets(windowInsetsOnApplyWindowInsets);
        }
        return a.c.e.p.a(windowInsets);
    }

    private static android.view.View.AccessibilityDelegate b(android.view.View view) {
        if (f) {
            return null;
        }
        if (e == null) {
            try {
                java.lang.reflect.Field declaredField = android.view.View.class.getDeclaredField("mAccessibilityDelegate");
                e = declaredField;
                declaredField.setAccessible(true);
            } catch (java.lang.Throwable unused) {
                f = true;
                return null;
            }
        }
        try {
            java.lang.Object obj = e.get(view);
            if (obj instanceof android.view.View.AccessibilityDelegate) {
                return (android.view.View.AccessibilityDelegate) obj;
            }
            return null;
        } catch (java.lang.Throwable unused2) {
            f = true;
            return null;
        }
    }

    public static void b(android.view.View view, int i) {
        int i2 = android.os.Build.VERSION.SDK_INT;
        if (i2 < 19) {
            if (i2 < 16) {
                return;
            }
            if (i == 4) {
                i = 2;
            }
        }
        view.setImportantForAccessibility(i);
    }

    public static int c(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            return view.getAccessibilityLiveRegion();
        }
        return 0;
    }

    private static a.c.e.m.f<java.lang.Boolean> c() {
        return new a.c.e.m.b(a.c.a.tag_screen_reader_focusable, java.lang.Boolean.class, 28);
    }

    public static void c(android.view.View view, int i) {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            view.setImportantForAutofill(i);
        }
    }

    public static java.lang.CharSequence d(android.view.View view) {
        return b().b(view);
    }

    public static android.view.Display e(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            return view.getDisplay();
        }
        if (n(view)) {
            return ((android.view.WindowManager) view.getContext().getSystemService("window")).getDefaultDisplay();
        }
        return null;
    }

    public static float f(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            return view.getElevation();
        }
        return 0.0f;
    }

    public static int g(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            return view.getImportantForAccessibility();
        }
        return 0;
    }

    @android.annotation.SuppressLint({"InlinedApi"})
    public static int h(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            return view.getImportantForAutofill();
        }
        return 0;
    }

    public static int i(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            return view.getLayoutDirection();
        }
        return 0;
    }

    public static int j(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            return view.getMinimumHeight();
        }
        if (!d) {
            try {
                java.lang.reflect.Field declaredField = android.view.View.class.getDeclaredField("mMinHeight");
                f101c = declaredField;
                declaredField.setAccessible(true);
            } catch (java.lang.NoSuchFieldException unused) {
            }
            d = true;
        }
        java.lang.reflect.Field field = f101c;
        if (field == null) {
            return 0;
        }
        try {
            return ((java.lang.Integer) field.get(view)).intValue();
        } catch (java.lang.Exception unused2) {
            return 0;
        }
    }

    public static int k(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            return view.getMinimumWidth();
        }
        if (!f100b) {
            try {
                java.lang.reflect.Field declaredField = android.view.View.class.getDeclaredField("mMinWidth");
                f99a = declaredField;
                declaredField.setAccessible(true);
            } catch (java.lang.NoSuchFieldException unused) {
            }
            f100b = true;
        }
        java.lang.reflect.Field field = f99a;
        if (field == null) {
            return 0;
        }
        try {
            return ((java.lang.Integer) field.get(view)).intValue();
        } catch (java.lang.Exception unused2) {
            return 0;
        }
    }

    public static boolean l(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            return view.hasTransientState();
        }
        return false;
    }

    public static boolean m(android.view.View view) {
        java.lang.Boolean boolB = a().b(view);
        if (boolB == null) {
            return false;
        }
        return boolB.booleanValue();
    }

    public static boolean n(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            return view.isAttachedToWindow();
        }
        return view.getWindowToken() != null;
    }

    public static boolean o(android.view.View view) {
        java.lang.Boolean boolB = c().b(view);
        if (boolB == null) {
            return false;
        }
        return boolB.booleanValue();
    }

    public static void p(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation();
        } else {
            view.postInvalidate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void q(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            view.stopNestedScroll();
        } else if (view instanceof a.c.e.g) {
            ((a.c.e.g) view).stopNestedScroll();
        }
    }
}
