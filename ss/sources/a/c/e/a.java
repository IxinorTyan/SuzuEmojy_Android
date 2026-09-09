package a.c.e;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private static final android.view.View.AccessibilityDelegate f86c = new android.view.View.AccessibilityDelegate();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final android.view.View.AccessibilityDelegate f87a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final android.view.View.AccessibilityDelegate f88b;

    /* JADX INFO: renamed from: a.c.e.a$a, reason: collision with other inner class name */
    static final class C0002a extends android.view.View.AccessibilityDelegate {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final a.c.e.a f89a;

        C0002a(a.c.e.a aVar) {
            this.f89a = aVar;
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean dispatchPopulateAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            return this.f89a.a(view, accessibilityEvent);
        }

        @Override // android.view.View.AccessibilityDelegate
        public android.view.accessibility.AccessibilityNodeProvider getAccessibilityNodeProvider(android.view.View view) {
            a.c.e.q.d dVarA = this.f89a.a(view);
            if (dVarA != null) {
                return (android.view.accessibility.AccessibilityNodeProvider) dVarA.a();
            }
            return null;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            this.f89a.b(view, accessibilityEvent);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            a.c.e.q.c cVarA = a.c.e.q.c.a(accessibilityNodeInfo);
            cVarA.b(a.c.e.m.o(view));
            cVarA.a(a.c.e.m.m(view));
            cVarA.b(a.c.e.m.d(view));
            this.f89a.a(view, cVarA);
            cVarA.a(accessibilityNodeInfo.getText(), view);
            java.util.List<a.c.e.q.c.a> listB = a.c.e.a.b(view);
            for (int i = 0; i < listB.size(); i++) {
                cVarA.a(listB.get(i));
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onPopulateAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            this.f89a.c(view, accessibilityEvent);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean onRequestSendAccessibilityEvent(android.view.ViewGroup viewGroup, android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            return this.f89a.a(viewGroup, view, accessibilityEvent);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
            return this.f89a.a(view, i, bundle);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void sendAccessibilityEvent(android.view.View view, int i) {
            this.f89a.a(view, i);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void sendAccessibilityEventUnchecked(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            this.f89a.d(view, accessibilityEvent);
        }
    }

    public a() {
        this(f86c);
    }

    public a(android.view.View.AccessibilityDelegate accessibilityDelegate) {
        this.f87a = accessibilityDelegate;
        this.f88b = new a.c.e.a.C0002a(this);
    }

    private boolean a(int i, android.view.View view) {
        java.lang.ref.WeakReference weakReference;
        android.util.SparseArray sparseArray = (android.util.SparseArray) view.getTag(a.c.a.tag_accessibility_clickable_spans);
        if (sparseArray == null || (weakReference = (java.lang.ref.WeakReference) sparseArray.get(i)) == null) {
            return false;
        }
        android.text.style.ClickableSpan clickableSpan = (android.text.style.ClickableSpan) weakReference.get();
        if (!a(clickableSpan, view)) {
            return false;
        }
        clickableSpan.onClick(view);
        return true;
    }

    private boolean a(android.text.style.ClickableSpan clickableSpan, android.view.View view) {
        if (clickableSpan != null) {
            android.text.style.ClickableSpan[] clickableSpanArrC = a.c.e.q.c.c(view.createAccessibilityNodeInfo().getText());
            for (int i = 0; clickableSpanArrC != null && i < clickableSpanArrC.length; i++) {
                if (clickableSpan.equals(clickableSpanArrC[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    static java.util.List<a.c.e.q.c.a> b(android.view.View view) {
        java.util.List<a.c.e.q.c.a> list = (java.util.List) view.getTag(a.c.a.tag_accessibility_actions);
        return list == null ? java.util.Collections.emptyList() : list;
    }

    public a.c.e.q.d a(android.view.View view) {
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider;
        if (android.os.Build.VERSION.SDK_INT < 16 || (accessibilityNodeProvider = this.f87a.getAccessibilityNodeProvider(view)) == null) {
            return null;
        }
        return new a.c.e.q.d(accessibilityNodeProvider);
    }

    android.view.View.AccessibilityDelegate a() {
        return this.f88b;
    }

    public void a(android.view.View view, int i) {
        this.f87a.sendAccessibilityEvent(view, i);
    }

    public void a(android.view.View view, a.c.e.q.c cVar) {
        this.f87a.onInitializeAccessibilityNodeInfo(view, cVar.r());
    }

    public boolean a(android.view.View view, int i, android.os.Bundle bundle) {
        java.util.List<a.c.e.q.c.a> listB = b(view);
        boolean zPerformAccessibilityAction = false;
        for (int i2 = 0; i2 < listB.size(); i2++) {
            a.c.e.q.c.a aVar = listB.get(i2);
            if (aVar.a() == i) {
                zPerformAccessibilityAction = aVar.a(view, bundle);
                break;
            }
        }
        if (!zPerformAccessibilityAction && android.os.Build.VERSION.SDK_INT >= 16) {
            zPerformAccessibilityAction = this.f87a.performAccessibilityAction(view, i, bundle);
        }
        return (zPerformAccessibilityAction || i != a.c.a.accessibility_action_clickable_span) ? zPerformAccessibilityAction : a(bundle.getInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", -1), view);
    }

    public boolean a(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return this.f87a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public boolean a(android.view.ViewGroup viewGroup, android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return this.f87a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    public void b(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        this.f87a.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public void c(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        this.f87a.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public void d(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        this.f87a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }
}
