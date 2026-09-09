package a.c.e;

/* JADX INFO: loaded from: classes.dex */
public final class o {
    public static void a(android.view.ViewParent viewParent, android.view.View view, int i) {
        if (viewParent instanceof a.c.e.i) {
            ((a.c.e.i) viewParent).a(view, i);
            return;
        }
        if (i == 0) {
            if (android.os.Build.VERSION.SDK_INT < 21) {
                if (viewParent instanceof a.c.e.k) {
                    ((a.c.e.k) viewParent).onStopNestedScroll(view);
                    return;
                }
                return;
            }
            try {
                viewParent.onStopNestedScroll(view);
            } catch (java.lang.AbstractMethodError e) {
                android.util.Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onStopNestedScroll", e);
            }
        }
    }

    public static void a(android.view.ViewParent viewParent, android.view.View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (viewParent instanceof a.c.e.j) {
            ((a.c.e.j) viewParent).a(view, i, i2, i3, i4, i5, iArr);
            return;
        }
        iArr[0] = iArr[0] + i3;
        iArr[1] = iArr[1] + i4;
        if (viewParent instanceof a.c.e.i) {
            ((a.c.e.i) viewParent).a(view, i, i2, i3, i4, i5);
            return;
        }
        if (i5 == 0) {
            if (android.os.Build.VERSION.SDK_INT < 21) {
                if (viewParent instanceof a.c.e.k) {
                    ((a.c.e.k) viewParent).onNestedScroll(view, i, i2, i3, i4);
                    return;
                }
                return;
            }
            try {
                viewParent.onNestedScroll(view, i, i2, i3, i4);
            } catch (java.lang.AbstractMethodError e) {
                android.util.Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedScroll", e);
            }
        }
    }

    public static void a(android.view.ViewParent viewParent, android.view.View view, int i, int i2, int[] iArr, int i3) {
        if (viewParent instanceof a.c.e.i) {
            ((a.c.e.i) viewParent).a(view, i, i2, iArr, i3);
            return;
        }
        if (i3 == 0) {
            if (android.os.Build.VERSION.SDK_INT < 21) {
                if (viewParent instanceof a.c.e.k) {
                    ((a.c.e.k) viewParent).onNestedPreScroll(view, i, i2, iArr);
                    return;
                }
                return;
            }
            try {
                viewParent.onNestedPreScroll(view, i, i2, iArr);
            } catch (java.lang.AbstractMethodError e) {
                android.util.Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedPreScroll", e);
            }
        }
    }

    public static void a(android.view.ViewParent viewParent, android.view.View view, android.view.View view2, int i, int i2) {
        if (viewParent instanceof a.c.e.i) {
            ((a.c.e.i) viewParent).a(view, view2, i, i2);
            return;
        }
        if (i2 == 0) {
            if (android.os.Build.VERSION.SDK_INT < 21) {
                if (viewParent instanceof a.c.e.k) {
                    ((a.c.e.k) viewParent).onNestedScrollAccepted(view, view2, i);
                    return;
                }
                return;
            }
            try {
                viewParent.onNestedScrollAccepted(view, view2, i);
            } catch (java.lang.AbstractMethodError e) {
                android.util.Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedScrollAccepted", e);
            }
        }
    }

    public static boolean a(android.view.ViewParent viewParent, android.view.View view, float f, float f2) {
        if (android.os.Build.VERSION.SDK_INT < 21) {
            if (viewParent instanceof a.c.e.k) {
                return ((a.c.e.k) viewParent).onNestedPreFling(view, f, f2);
            }
            return false;
        }
        try {
            return viewParent.onNestedPreFling(view, f, f2);
        } catch (java.lang.AbstractMethodError e) {
            android.util.Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedPreFling", e);
            return false;
        }
    }

    public static boolean a(android.view.ViewParent viewParent, android.view.View view, float f, float f2, boolean z) {
        if (android.os.Build.VERSION.SDK_INT < 21) {
            if (viewParent instanceof a.c.e.k) {
                return ((a.c.e.k) viewParent).onNestedFling(view, f, f2, z);
            }
            return false;
        }
        try {
            return viewParent.onNestedFling(view, f, f2, z);
        } catch (java.lang.AbstractMethodError e) {
            android.util.Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onNestedFling", e);
            return false;
        }
    }

    public static boolean b(android.view.ViewParent viewParent, android.view.View view, android.view.View view2, int i, int i2) {
        if (viewParent instanceof a.c.e.i) {
            return ((a.c.e.i) viewParent).b(view, view2, i, i2);
        }
        if (i2 != 0) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT < 21) {
            if (viewParent instanceof a.c.e.k) {
                return ((a.c.e.k) viewParent).onStartNestedScroll(view, view2, i);
            }
            return false;
        }
        try {
            return viewParent.onStartNestedScroll(view, view2, i);
        } catch (java.lang.AbstractMethodError e) {
            android.util.Log.e("ViewParentCompat", "ViewParent " + viewParent + " does not implement interface method onStartNestedScroll", e);
            return false;
        }
    }
}
