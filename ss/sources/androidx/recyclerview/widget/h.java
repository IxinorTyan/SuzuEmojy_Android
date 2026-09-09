package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class h implements androidx.recyclerview.widget.g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    static final androidx.recyclerview.widget.g f325a = new androidx.recyclerview.widget.h();

    h() {
    }

    private static float a(androidx.recyclerview.widget.RecyclerView recyclerView, android.view.View view) {
        int childCount = recyclerView.getChildCount();
        float f = 0.0f;
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = recyclerView.getChildAt(i);
            if (childAt != view) {
                float f2 = a.c.e.m.f(childAt);
                if (f2 > f) {
                    f = f2;
                }
            }
        }
        return f;
    }

    @Override // androidx.recyclerview.widget.g
    public void a(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, android.view.View view, float f, float f2, int i, boolean z) {
    }

    @Override // androidx.recyclerview.widget.g
    public void a(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            java.lang.Object tag = view.getTag(a.f.c.item_touch_helper_previous_elevation);
            if (tag instanceof java.lang.Float) {
                a.c.e.m.a(view, ((java.lang.Float) tag).floatValue());
            }
            view.setTag(a.f.c.item_touch_helper_previous_elevation, null);
        }
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }

    @Override // androidx.recyclerview.widget.g
    public void b(android.graphics.Canvas canvas, androidx.recyclerview.widget.RecyclerView recyclerView, android.view.View view, float f, float f2, int i, boolean z) {
        if (android.os.Build.VERSION.SDK_INT >= 21 && z && view.getTag(a.f.c.item_touch_helper_previous_elevation) == null) {
            java.lang.Float fValueOf = java.lang.Float.valueOf(a.c.e.m.f(view));
            a.c.e.m.a(view, a(recyclerView, view) + 1.0f);
            view.setTag(a.f.c.item_touch_helper_previous_elevation, fValueOf);
        }
        view.setTranslationX(f);
        view.setTranslationY(f2);
    }

    @Override // androidx.recyclerview.widget.g
    public void b(android.view.View view) {
    }
}
