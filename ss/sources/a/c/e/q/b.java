package a.c.e.q;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static int a(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            return accessibilityEvent.getContentChangeTypes();
        }
        return 0;
    }

    public static void a(android.view.accessibility.AccessibilityEvent accessibilityEvent, int i) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            accessibilityEvent.setContentChangeTypes(i);
        }
    }
}
