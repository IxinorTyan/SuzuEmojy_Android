package a.c.e.q;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static int d;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final android.view.accessibility.AccessibilityNodeInfo f112a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f113b = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int f114c = -1;

    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final java.lang.Object f115a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final java.lang.Class<? extends a.c.e.q.e.a> f116b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        protected final a.c.e.q.e f117c;

        static {
            new a.c.e.q.c.a(1, null);
            new a.c.e.q.c.a(2, null);
            new a.c.e.q.c.a(4, null);
            new a.c.e.q.c.a(8, null);
            new a.c.e.q.c.a(16, null);
            new a.c.e.q.c.a(32, null);
            new a.c.e.q.c.a(64, null);
            new a.c.e.q.c.a(128, null);
            new a.c.e.q.c.a(256, null, a.c.e.q.e.b.class);
            new a.c.e.q.c.a(512, null, a.c.e.q.e.b.class);
            new a.c.e.q.c.a(1024, null, a.c.e.q.e.c.class);
            new a.c.e.q.c.a(2048, null, a.c.e.q.e.c.class);
            new a.c.e.q.c.a(4096, null);
            new a.c.e.q.c.a(8192, null);
            new a.c.e.q.c.a(16384, null);
            new a.c.e.q.c.a(32768, null);
            new a.c.e.q.c.a(65536, null);
            new a.c.e.q.c.a(131072, null, a.c.e.q.e.g.class);
            new a.c.e.q.c.a(262144, null);
            new a.c.e.q.c.a(524288, null);
            new a.c.e.q.c.a(1048576, null);
            new a.c.e.q.c.a(2097152, null, a.c.e.q.e.h.class);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 23 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN : null, android.R.id.accessibilityActionShowOnScreen, null, null, null);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 23 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION : null, android.R.id.accessibilityActionScrollToPosition, null, null, a.c.e.q.e.C0005e.class);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 23 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP : null, android.R.id.accessibilityActionScrollUp, null, null, null);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 23 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT : null, android.R.id.accessibilityActionScrollLeft, null, null, null);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 23 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN : null, android.R.id.accessibilityActionScrollDown, null, null, null);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 23 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT : null, android.R.id.accessibilityActionScrollRight, null, null, null);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 23 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK : null, android.R.id.accessibilityActionContextClick, null, null, null);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 24 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS : null, android.R.id.accessibilityActionSetProgress, null, null, a.c.e.q.e.f.class);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 26 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW : null, android.R.id.accessibilityActionMoveWindow, null, null, a.c.e.q.e.d.class);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 28 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : null, android.R.id.accessibilityActionShowTooltip, null, null, null);
            new a.c.e.q.c.a(android.os.Build.VERSION.SDK_INT >= 28 ? android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP : null, android.R.id.accessibilityActionHideTooltip, null, null, null);
        }

        public a(int i, java.lang.CharSequence charSequence) {
            this(null, i, charSequence, null, null);
        }

        private a(int i, java.lang.CharSequence charSequence, java.lang.Class<? extends a.c.e.q.e.a> cls) {
            this(null, i, charSequence, null, cls);
        }

        a(java.lang.Object obj, int i, java.lang.CharSequence charSequence, a.c.e.q.e eVar, java.lang.Class<? extends a.c.e.q.e.a> cls) {
            this.f117c = eVar;
            if (android.os.Build.VERSION.SDK_INT >= 21 && obj == null) {
                obj = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(i, charSequence);
            }
            this.f115a = obj;
            this.f116b = cls;
        }

        public int a() {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                return ((android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction) this.f115a).getId();
            }
            return 0;
        }

        public boolean a(android.view.View view, android.os.Bundle bundle) {
            if (this.f117c == null) {
                return false;
            }
            a.c.e.q.e.a aVar = null;
            java.lang.Class<? extends a.c.e.q.e.a> cls = this.f116b;
            if (cls != null) {
                try {
                    a.c.e.q.e.a aVarNewInstance = cls.getDeclaredConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
                    try {
                        aVarNewInstance.a(bundle);
                        aVar = aVarNewInstance;
                    } catch (java.lang.Exception e) {
                        e = e;
                        aVar = aVarNewInstance;
                        java.lang.Class<? extends a.c.e.q.e.a> cls2 = this.f116b;
                        android.util.Log.e("A11yActionCompat", "Failed to execute command with argument class ViewCommandArgument: " + (cls2 == null ? "null" : cls2.getName()), e);
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                }
            }
            return this.f117c.a(view, aVar);
        }
    }

    public static class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final java.lang.Object f118a;

        b(java.lang.Object obj) {
            this.f118a = obj;
        }

        public static a.c.e.q.c.b a(int i, int i2, boolean z, int i3) {
            int i4 = android.os.Build.VERSION.SDK_INT;
            if (i4 >= 21) {
                return new a.c.e.q.c.b(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z, i3));
            }
            return i4 >= 19 ? new a.c.e.q.c.b(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z)) : new a.c.e.q.c.b(null);
        }
    }

    /* JADX INFO: renamed from: a.c.e.q.c$c, reason: collision with other inner class name */
    public static class C0004c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final java.lang.Object f119a;

        C0004c(java.lang.Object obj) {
            this.f119a = obj;
        }

        public static a.c.e.q.c.C0004c a(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            int i5 = android.os.Build.VERSION.SDK_INT;
            if (i5 >= 21) {
                return new a.c.e.q.c.C0004c(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2));
            }
            return i5 >= 19 ? new a.c.e.q.c.C0004c(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z)) : new a.c.e.q.c.C0004c(null);
        }
    }

    private c(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        this.f112a = accessibilityNodeInfo;
    }

    private int a(android.text.style.ClickableSpan clickableSpan, android.util.SparseArray<java.lang.ref.WeakReference<android.text.style.ClickableSpan>> sparseArray) {
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                if (clickableSpan.equals(sparseArray.valueAt(i).get())) {
                    return sparseArray.keyAt(i);
                }
            }
        }
        int i2 = d;
        d = i2 + 1;
        return i2;
    }

    public static a.c.e.q.c a(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        return new a.c.e.q.c(accessibilityNodeInfo);
    }

    private android.util.SparseArray<java.lang.ref.WeakReference<android.text.style.ClickableSpan>> a(android.view.View view) {
        android.util.SparseArray<java.lang.ref.WeakReference<android.text.style.ClickableSpan>> sparseArrayB = b(view);
        if (sparseArrayB != null) {
            return sparseArrayB;
        }
        android.util.SparseArray<java.lang.ref.WeakReference<android.text.style.ClickableSpan>> sparseArray = new android.util.SparseArray<>();
        view.setTag(a.c.a.tag_accessibility_clickable_spans, sparseArray);
        return sparseArray;
    }

    private java.util.List<java.lang.Integer> a(java.lang.String str) {
        if (android.os.Build.VERSION.SDK_INT < 19) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList<java.lang.Integer> integerArrayList = this.f112a.getExtras().getIntegerArrayList(str);
        if (integerArrayList != null) {
            return integerArrayList;
        }
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
        this.f112a.getExtras().putIntegerArrayList(str, arrayList);
        return arrayList;
    }

    private void a(int i, boolean z) {
        android.os.Bundle bundleD = d();
        if (bundleD != null) {
            int i2 = bundleD.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0) & (~i);
            if (!z) {
                i = 0;
            }
            bundleD.putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", i | i2);
        }
    }

    private void a(android.text.style.ClickableSpan clickableSpan, android.text.Spanned spanned, int i) {
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").add(java.lang.Integer.valueOf(spanned.getSpanStart(clickableSpan)));
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY").add(java.lang.Integer.valueOf(spanned.getSpanEnd(clickableSpan)));
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY").add(java.lang.Integer.valueOf(spanned.getSpanFlags(clickableSpan)));
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY").add(java.lang.Integer.valueOf(i));
    }

    private android.util.SparseArray<java.lang.ref.WeakReference<android.text.style.ClickableSpan>> b(android.view.View view) {
        return (android.util.SparseArray) view.getTag(a.c.a.tag_accessibility_clickable_spans);
    }

    private static java.lang.String b(int i) {
        if (i == 1) {
            return "ACTION_FOCUS";
        }
        if (i == 2) {
            return "ACTION_CLEAR_FOCUS";
        }
        switch (i) {
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case me.hisn.mygesture.g.ConstraintSet_layout_constraintTop_toTopOf /* 64 */:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }

    private void c(android.view.View view) {
        android.util.SparseArray<java.lang.ref.WeakReference<android.text.style.ClickableSpan>> sparseArrayB = b(view);
        if (sparseArrayB != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < sparseArrayB.size(); i++) {
                if (sparseArrayB.valueAt(i).get() == null) {
                    arrayList.add(java.lang.Integer.valueOf(i));
                }
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                sparseArrayB.remove(((java.lang.Integer) arrayList.get(i2)).intValue());
            }
        }
    }

    public static android.text.style.ClickableSpan[] c(java.lang.CharSequence charSequence) {
        if (charSequence instanceof android.text.Spanned) {
            return (android.text.style.ClickableSpan[]) ((android.text.Spanned) charSequence).getSpans(0, charSequence.length(), android.text.style.ClickableSpan.class);
        }
        return null;
    }

    private void s() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            this.f112a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
            this.f112a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
            this.f112a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
            this.f112a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
        }
    }

    private boolean t() {
        return !a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").isEmpty();
    }

    public int a() {
        return this.f112a.getActions();
    }

    public void a(int i) {
        this.f112a.addAction(i);
    }

    public void a(a.c.e.q.c.a aVar) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            this.f112a.addAction((android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction) aVar.f115a);
        }
    }

    public void a(android.graphics.Rect rect) {
        this.f112a.getBoundsInParent(rect);
    }

    public void a(java.lang.CharSequence charSequence) {
        this.f112a.setClassName(charSequence);
    }

    public void a(java.lang.CharSequence charSequence, android.view.View view) {
        int i = android.os.Build.VERSION.SDK_INT;
        if (i < 19 || i >= 26) {
            return;
        }
        s();
        c(view);
        android.text.style.ClickableSpan[] clickableSpanArrC = c(charSequence);
        if (clickableSpanArrC == null || clickableSpanArrC.length <= 0) {
            return;
        }
        d().putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY", a.c.a.accessibility_action_clickable_span);
        android.util.SparseArray<java.lang.ref.WeakReference<android.text.style.ClickableSpan>> sparseArrayA = a(view);
        for (int i2 = 0; clickableSpanArrC != null && i2 < clickableSpanArrC.length; i2++) {
            int iA = a(clickableSpanArrC[i2], sparseArrayA);
            sparseArrayA.put(iA, new java.lang.ref.WeakReference<>(clickableSpanArrC[i2]));
            a(clickableSpanArrC[i2], (android.text.Spanned) charSequence, iA);
        }
    }

    public void a(java.lang.Object obj) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            this.f112a.setCollectionInfo(obj == null ? null : (android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) ((a.c.e.q.c.b) obj).f118a);
        }
    }

    public void a(boolean z) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            this.f112a.setHeading(z);
        } else {
            a(2, z);
        }
    }

    public boolean a(int i, android.os.Bundle bundle) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            return this.f112a.performAction(i, bundle);
        }
        return false;
    }

    public java.lang.CharSequence b() {
        return this.f112a.getClassName();
    }

    public void b(android.graphics.Rect rect) {
        this.f112a.getBoundsInScreen(rect);
    }

    public void b(java.lang.CharSequence charSequence) {
        int i = android.os.Build.VERSION.SDK_INT;
        if (i >= 28) {
            this.f112a.setPaneTitle(charSequence);
        } else if (i >= 19) {
            this.f112a.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY", charSequence);
        }
    }

    public void b(java.lang.Object obj) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            this.f112a.setCollectionItemInfo(obj == null ? null : (android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) ((a.c.e.q.c.C0004c) obj).f119a);
        }
    }

    public void b(boolean z) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            this.f112a.setScreenReaderFocusable(z);
        } else {
            a(1, z);
        }
    }

    public java.lang.CharSequence c() {
        return this.f112a.getContentDescription();
    }

    public void c(boolean z) {
        this.f112a.setScrollable(z);
    }

    public android.os.Bundle d() {
        return android.os.Build.VERSION.SDK_INT >= 19 ? this.f112a.getExtras() : new android.os.Bundle();
    }

    public java.lang.CharSequence e() {
        return this.f112a.getPackageName();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || a.c.e.q.c.class != obj.getClass()) {
            return false;
        }
        a.c.e.q.c cVar = (a.c.e.q.c) obj;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = this.f112a;
        if (accessibilityNodeInfo == null) {
            if (cVar.f112a != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo.equals(cVar.f112a)) {
            return false;
        }
        return this.f114c == cVar.f114c && this.f113b == cVar.f113b;
    }

    public java.lang.CharSequence f() {
        if (!t()) {
            return this.f112a.getText();
        }
        java.util.List<java.lang.Integer> listA = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
        java.util.List<java.lang.Integer> listA2 = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
        java.util.List<java.lang.Integer> listA3 = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
        java.util.List<java.lang.Integer> listA4 = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
        android.text.SpannableString spannableString = new android.text.SpannableString(android.text.TextUtils.substring(this.f112a.getText(), 0, this.f112a.getText().length()));
        for (int i = 0; i < listA.size(); i++) {
            spannableString.setSpan(new a.c.e.q.a(listA4.get(i).intValue(), this, d().getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY")), listA.get(i).intValue(), listA2.get(i).intValue(), listA3.get(i).intValue());
        }
        return spannableString;
    }

    public java.lang.String g() {
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            return this.f112a.getViewIdResourceName();
        }
        return null;
    }

    public boolean h() {
        return this.f112a.isCheckable();
    }

    public int hashCode() {
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = this.f112a;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public boolean i() {
        return this.f112a.isChecked();
    }

    public boolean j() {
        return this.f112a.isClickable();
    }

    public boolean k() {
        return this.f112a.isEnabled();
    }

    public boolean l() {
        return this.f112a.isFocusable();
    }

    public boolean m() {
        return this.f112a.isFocused();
    }

    public boolean n() {
        return this.f112a.isLongClickable();
    }

    public boolean o() {
        return this.f112a.isPassword();
    }

    public boolean p() {
        return this.f112a.isScrollable();
    }

    public boolean q() {
        return this.f112a.isSelected();
    }

    public android.view.accessibility.AccessibilityNodeInfo r() {
        return this.f112a;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(super.toString());
        android.graphics.Rect rect = new android.graphics.Rect();
        a(rect);
        sb.append("; boundsInParent: " + rect);
        b(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ");
        sb.append(e());
        sb.append("; className: ");
        sb.append(b());
        sb.append("; text: ");
        sb.append(f());
        sb.append("; contentDescription: ");
        sb.append(c());
        sb.append("; viewId: ");
        sb.append(g());
        sb.append("; checkable: ");
        sb.append(h());
        sb.append("; checked: ");
        sb.append(i());
        sb.append("; focusable: ");
        sb.append(l());
        sb.append("; focused: ");
        sb.append(m());
        sb.append("; selected: ");
        sb.append(q());
        sb.append("; clickable: ");
        sb.append(j());
        sb.append("; longClickable: ");
        sb.append(n());
        sb.append("; enabled: ");
        sb.append(k());
        sb.append("; password: ");
        sb.append(o());
        sb.append("; scrollable: " + p());
        sb.append("; [");
        int iA = a();
        while (iA != 0) {
            int iNumberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(iA);
            iA &= ~iNumberOfTrailingZeros;
            sb.append(b(iNumberOfTrailingZeros));
            if (iA != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
