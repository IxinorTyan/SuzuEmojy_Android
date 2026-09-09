package androidx.viewpager.widget;

/* JADX INFO: loaded from: classes.dex */
public class ViewPager extends android.view.ViewGroup {
    static final int[] f0 = {android.R.attr.layout_gravity};
    private static final java.util.Comparator<androidx.viewpager.widget.ViewPager.f> g0 = new androidx.viewpager.widget.ViewPager.a();
    private static final android.view.animation.Interpolator h0 = new androidx.viewpager.widget.ViewPager.b();
    private static final androidx.viewpager.widget.ViewPager.n i0 = new androidx.viewpager.widget.ViewPager.n();
    private int A;
    private int B;
    private float C;
    private float D;
    private float E;
    private float F;
    private int G;
    private android.view.VelocityTracker H;
    private int I;
    private int J;
    private int K;
    private int L;
    private boolean M;
    private android.widget.EdgeEffect N;
    private android.widget.EdgeEffect O;
    private boolean P;
    private boolean Q;
    private int R;
    private java.util.List<androidx.viewpager.widget.ViewPager.j> S;
    private androidx.viewpager.widget.ViewPager.j T;
    private androidx.viewpager.widget.ViewPager.j U;
    private java.util.List<androidx.viewpager.widget.ViewPager.i> V;
    private androidx.viewpager.widget.ViewPager.k W;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f347a;
    private int a0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.util.ArrayList<androidx.viewpager.widget.ViewPager.f> f348b;
    private int b0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final androidx.viewpager.widget.ViewPager.f f349c;
    private java.util.ArrayList<android.view.View> c0;
    private final android.graphics.Rect d;
    private final java.lang.Runnable d0;
    androidx.viewpager.widget.a e;
    private int e0;
    int f;
    private int g;
    private android.os.Parcelable h;
    private java.lang.ClassLoader i;
    private android.widget.Scroller j;
    private boolean k;
    private androidx.viewpager.widget.ViewPager.l l;
    private int m;
    private android.graphics.drawable.Drawable n;
    private int o;
    private int p;
    private float q;
    private float r;
    private int s;
    private boolean t;
    private boolean u;
    private boolean v;
    private int w;
    private boolean x;
    private boolean y;
    private int z;

    static class a implements java.util.Comparator<androidx.viewpager.widget.ViewPager.f> {
        a() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(androidx.viewpager.widget.ViewPager.f fVar, androidx.viewpager.widget.ViewPager.f fVar2) {
            return fVar.f354b - fVar2.f354b;
        }
    }

    static class b implements android.view.animation.Interpolator {
        b() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    }

    class c implements java.lang.Runnable {
        c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            androidx.viewpager.widget.ViewPager.this.setScrollState(0);
            androidx.viewpager.widget.ViewPager.this.e();
        }
    }

    class d implements a.c.e.l {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final android.graphics.Rect f351a = new android.graphics.Rect();

        d() {
        }

        @Override // a.c.e.l
        public a.c.e.p a(android.view.View view, a.c.e.p pVar) {
            a.c.e.p pVarB = a.c.e.m.b(view, pVar);
            if (pVarB.e()) {
                return pVarB;
            }
            android.graphics.Rect rect = this.f351a;
            rect.left = pVarB.b();
            rect.top = pVarB.d();
            rect.right = pVarB.c();
            rect.bottom = pVarB.a();
            int childCount = androidx.viewpager.widget.ViewPager.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                a.c.e.p pVarA = a.c.e.m.a(androidx.viewpager.widget.ViewPager.this.getChildAt(i), pVarB);
                rect.left = java.lang.Math.min(pVarA.b(), rect.left);
                rect.top = java.lang.Math.min(pVarA.d(), rect.top);
                rect.right = java.lang.Math.min(pVarA.c(), rect.right);
                rect.bottom = java.lang.Math.min(pVarA.a(), rect.bottom);
            }
            return pVarB.a(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
    @java.lang.annotation.Inherited
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface e {
    }

    static class f {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        java.lang.Object f353a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f354b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        boolean f355c;
        float d;
        float e;

        f() {
        }
    }

    public static class g extends android.view.ViewGroup.LayoutParams {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public boolean f356a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f357b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        float f358c;
        boolean d;
        int e;
        int f;

        public g() {
            super(-1, -1);
            this.f358c = 0.0f;
        }

        public g(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f358c = 0.0f;
            android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.viewpager.widget.ViewPager.f0);
            this.f357b = typedArrayObtainStyledAttributes.getInteger(0, 48);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    class h extends a.c.e.a {
        h() {
        }

        private boolean b() {
            androidx.viewpager.widget.a aVar = androidx.viewpager.widget.ViewPager.this.e;
            return aVar != null && aVar.a() > 1;
        }

        @Override // a.c.e.a
        public void a(android.view.View view, a.c.e.q.c cVar) {
            super.a(view, cVar);
            cVar.a((java.lang.CharSequence) androidx.viewpager.widget.ViewPager.class.getName());
            cVar.c(b());
            if (androidx.viewpager.widget.ViewPager.this.canScrollHorizontally(1)) {
                cVar.a(4096);
            }
            if (androidx.viewpager.widget.ViewPager.this.canScrollHorizontally(-1)) {
                cVar.a(8192);
            }
        }

        @Override // a.c.e.a
        public boolean a(android.view.View view, int i, android.os.Bundle bundle) {
            androidx.viewpager.widget.ViewPager viewPager;
            int i2;
            if (super.a(view, i, bundle)) {
                return true;
            }
            if (i != 4096) {
                if (i != 8192 || !androidx.viewpager.widget.ViewPager.this.canScrollHorizontally(-1)) {
                    return false;
                }
                viewPager = androidx.viewpager.widget.ViewPager.this;
                i2 = viewPager.f - 1;
            } else {
                if (!androidx.viewpager.widget.ViewPager.this.canScrollHorizontally(1)) {
                    return false;
                }
                viewPager = androidx.viewpager.widget.ViewPager.this;
                i2 = viewPager.f + 1;
            }
            viewPager.setCurrentItem(i2);
            return true;
        }

        @Override // a.c.e.a
        public void b(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            androidx.viewpager.widget.a aVar;
            super.b(view, accessibilityEvent);
            accessibilityEvent.setClassName(androidx.viewpager.widget.ViewPager.class.getName());
            accessibilityEvent.setScrollable(b());
            if (accessibilityEvent.getEventType() != 4096 || (aVar = androidx.viewpager.widget.ViewPager.this.e) == null) {
                return;
            }
            accessibilityEvent.setItemCount(aVar.a());
            accessibilityEvent.setFromIndex(androidx.viewpager.widget.ViewPager.this.f);
            accessibilityEvent.setToIndex(androidx.viewpager.widget.ViewPager.this.f);
        }
    }

    public interface i {
        void a(androidx.viewpager.widget.ViewPager viewPager, androidx.viewpager.widget.a aVar, androidx.viewpager.widget.a aVar2);
    }

    public interface j {
        void a(int i);

        void a(int i, float f, int i2);

        void b(int i);
    }

    public interface k {
        void a(android.view.View view, float f);
    }

    private class l extends android.database.DataSetObserver {
        l() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            androidx.viewpager.widget.ViewPager.this.a();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            androidx.viewpager.widget.ViewPager.this.a();
        }
    }

    public static class m extends a.d.a.a {
        public static final android.os.Parcelable.Creator<androidx.viewpager.widget.ViewPager.m> CREATOR = new androidx.viewpager.widget.ViewPager.m.a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        int f360c;
        android.os.Parcelable d;
        java.lang.ClassLoader e;

        static class a implements android.os.Parcelable.ClassLoaderCreator<androidx.viewpager.widget.ViewPager.m> {
            a() {
            }

            @Override // android.os.Parcelable.Creator
            public androidx.viewpager.widget.ViewPager.m createFromParcel(android.os.Parcel parcel) {
                return new androidx.viewpager.widget.ViewPager.m(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public androidx.viewpager.widget.ViewPager.m createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
                return new androidx.viewpager.widget.ViewPager.m(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public androidx.viewpager.widget.ViewPager.m[] newArray(int i) {
                return new androidx.viewpager.widget.ViewPager.m[i];
            }
        }

        m(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? androidx.viewpager.widget.ViewPager.m.class.getClassLoader() : classLoader;
            this.f360c = parcel.readInt();
            this.d = parcel.readParcelable(classLoader);
            this.e = classLoader;
        }

        public m(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        public java.lang.String toString() {
            return "FragmentPager.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " position=" + this.f360c + "}";
        }

        @Override // a.d.a.a, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f360c);
            parcel.writeParcelable(this.d, i);
        }
    }

    static class n implements java.util.Comparator<android.view.View> {
        n() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(android.view.View view, android.view.View view2) {
            androidx.viewpager.widget.ViewPager.g gVar = (androidx.viewpager.widget.ViewPager.g) view.getLayoutParams();
            androidx.viewpager.widget.ViewPager.g gVar2 = (androidx.viewpager.widget.ViewPager.g) view2.getLayoutParams();
            boolean z = gVar.f356a;
            if (z != gVar2.f356a) {
                return z ? 1 : -1;
            }
            return gVar.e - gVar2.e;
        }
    }

    public ViewPager(android.content.Context context) {
        super(context);
        this.f348b = new java.util.ArrayList<>();
        this.f349c = new androidx.viewpager.widget.ViewPager.f();
        this.d = new android.graphics.Rect();
        this.g = -1;
        this.h = null;
        this.i = null;
        this.q = -3.4028235E38f;
        this.r = Float.MAX_VALUE;
        this.w = 1;
        this.G = -1;
        this.P = true;
        this.d0 = new androidx.viewpager.widget.ViewPager.c();
        this.e0 = 0;
        b();
    }

    public ViewPager(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f348b = new java.util.ArrayList<>();
        this.f349c = new androidx.viewpager.widget.ViewPager.f();
        this.d = new android.graphics.Rect();
        this.g = -1;
        this.h = null;
        this.i = null;
        this.q = -3.4028235E38f;
        this.r = Float.MAX_VALUE;
        this.w = 1;
        this.G = -1;
        this.P = true;
        this.d0 = new androidx.viewpager.widget.ViewPager.c();
        this.e0 = 0;
        b();
    }

    private int a(int i2, float f2, int i3, int i4) {
        if (java.lang.Math.abs(i4) <= this.K || java.lang.Math.abs(i3) <= this.I) {
            i2 += (int) (f2 + (i2 >= this.f ? 0.4f : 0.6f));
        } else if (i3 <= 0) {
            i2++;
        }
        if (this.f348b.size() <= 0) {
            return i2;
        }
        androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(0);
        java.util.ArrayList<androidx.viewpager.widget.ViewPager.f> arrayList = this.f348b;
        return java.lang.Math.max(fVar.f354b, java.lang.Math.min(i2, arrayList.get(arrayList.size() - 1).f354b));
    }

    private android.graphics.Rect a(android.graphics.Rect rect, android.view.View view) {
        if (rect == null) {
            rect = new android.graphics.Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        android.view.ViewParent parent = view.getParent();
        while ((parent instanceof android.view.ViewGroup) && parent != this) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) parent;
            rect.left += viewGroup.getLeft();
            rect.right += viewGroup.getRight();
            rect.top += viewGroup.getTop();
            rect.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect;
    }

    private void a(int i2, int i3, int i4, int i5) {
        int iMin;
        if (i3 <= 0 || this.f348b.isEmpty()) {
            androidx.viewpager.widget.ViewPager.f fVarB = b(this.f);
            iMin = (int) ((fVarB != null ? java.lang.Math.min(fVarB.e, this.r) : 0.0f) * ((i2 - getPaddingLeft()) - getPaddingRight()));
            if (iMin == getScrollX()) {
                return;
            } else {
                a(false);
            }
        } else if (!this.j.isFinished()) {
            this.j.setFinalX(getCurrentItem() * getClientWidth());
            return;
        } else {
            iMin = (int) ((getScrollX() / (((i3 - getPaddingLeft()) - getPaddingRight()) + i5)) * (((i2 - getPaddingLeft()) - getPaddingRight()) + i4));
        }
        scrollTo(iMin, getScrollY());
    }

    private void a(int i2, boolean z, int i3, boolean z2) {
        androidx.viewpager.widget.ViewPager.f fVarB = b(i2);
        int clientWidth = fVarB != null ? (int) (getClientWidth() * java.lang.Math.max(this.q, java.lang.Math.min(fVarB.e, this.r))) : 0;
        if (z) {
            a(clientWidth, 0, i3);
            if (z2) {
                d(i2);
                return;
            }
            return;
        }
        if (z2) {
            d(i2);
        }
        a(false);
        scrollTo(clientWidth, 0);
        f(clientWidth);
    }

    private void a(android.view.MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.G) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.C = motionEvent.getX(i2);
            this.G = motionEvent.getPointerId(i2);
            android.view.VelocityTracker velocityTracker = this.H;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private void a(androidx.viewpager.widget.ViewPager.f fVar, int i2, androidx.viewpager.widget.ViewPager.f fVar2) {
        int i3;
        int i4;
        androidx.viewpager.widget.ViewPager.f fVar3;
        androidx.viewpager.widget.ViewPager.f fVar4;
        int iA = this.e.a();
        int clientWidth = getClientWidth();
        float f2 = clientWidth > 0 ? this.m / clientWidth : 0.0f;
        if (fVar2 != null) {
            int i5 = fVar2.f354b;
            int i6 = fVar.f354b;
            if (i5 < i6) {
                int i7 = 0;
                float fA = fVar2.e + fVar2.d + f2;
                while (true) {
                    i5++;
                    if (i5 > fVar.f354b || i7 >= this.f348b.size()) {
                        break;
                    }
                    while (true) {
                        fVar4 = this.f348b.get(i7);
                        if (i5 <= fVar4.f354b || i7 >= this.f348b.size() - 1) {
                            break;
                        } else {
                            i7++;
                        }
                    }
                    while (i5 < fVar4.f354b) {
                        fA += this.e.a(i5) + f2;
                        i5++;
                    }
                    fVar4.e = fA;
                    fA += fVar4.d + f2;
                }
            } else if (i5 > i6) {
                int size = this.f348b.size() - 1;
                float fA2 = fVar2.e;
                while (true) {
                    i5--;
                    if (i5 < fVar.f354b || size < 0) {
                        break;
                    }
                    while (true) {
                        fVar3 = this.f348b.get(size);
                        if (i5 >= fVar3.f354b || size <= 0) {
                            break;
                        } else {
                            size--;
                        }
                    }
                    while (i5 > fVar3.f354b) {
                        fA2 -= this.e.a(i5) + f2;
                        i5--;
                    }
                    fA2 -= fVar3.d + f2;
                    fVar3.e = fA2;
                }
            }
        }
        int size2 = this.f348b.size();
        float fA3 = fVar.e;
        int i8 = fVar.f354b;
        int i9 = i8 - 1;
        this.q = i8 == 0 ? fA3 : -3.4028235E38f;
        int i10 = iA - 1;
        this.r = fVar.f354b == i10 ? (fVar.e + fVar.d) - 1.0f : Float.MAX_VALUE;
        int i11 = i2 - 1;
        while (i11 >= 0) {
            androidx.viewpager.widget.ViewPager.f fVar5 = this.f348b.get(i11);
            while (true) {
                i4 = fVar5.f354b;
                if (i9 <= i4) {
                    break;
                }
                fA3 -= this.e.a(i9) + f2;
                i9--;
            }
            fA3 -= fVar5.d + f2;
            fVar5.e = fA3;
            if (i4 == 0) {
                this.q = fA3;
            }
            i11--;
            i9--;
        }
        float fA4 = fVar.e + fVar.d + f2;
        int i12 = fVar.f354b + 1;
        int i13 = i2 + 1;
        while (i13 < size2) {
            androidx.viewpager.widget.ViewPager.f fVar6 = this.f348b.get(i13);
            while (true) {
                i3 = fVar6.f354b;
                if (i12 >= i3) {
                    break;
                }
                fA4 += this.e.a(i12) + f2;
                i12++;
            }
            if (i3 == i10) {
                this.r = (fVar6.d + fA4) - 1.0f;
            }
            fVar6.e = fA4;
            fA4 += fVar6.d + f2;
            i13++;
            i12++;
        }
    }

    private void a(boolean z) {
        boolean z2 = this.e0 == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            if (!this.j.isFinished()) {
                this.j.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.j.getCurrX();
                int currY = this.j.getCurrY();
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        f(currX);
                    }
                }
            }
        }
        this.v = false;
        for (int i2 = 0; i2 < this.f348b.size(); i2++) {
            androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(i2);
            if (fVar.f355c) {
                fVar.f355c = false;
                z2 = true;
            }
        }
        if (z2) {
            if (z) {
                a.c.e.m.a(this, this.d0);
            } else {
                this.d0.run();
            }
        }
    }

    private boolean a(float f2, float f3) {
        return (f2 < ((float) this.A) && f3 > 0.0f) || (f2 > ((float) (getWidth() - this.A)) && f3 < 0.0f);
    }

    private void b(int i2, float f2, int i3) {
        androidx.viewpager.widget.ViewPager.j jVar = this.T;
        if (jVar != null) {
            jVar.a(i2, f2, i3);
        }
        java.util.List<androidx.viewpager.widget.ViewPager.j> list = this.S;
        if (list != null) {
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                androidx.viewpager.widget.ViewPager.j jVar2 = this.S.get(i4);
                if (jVar2 != null) {
                    jVar2.a(i2, f2, i3);
                }
            }
        }
        androidx.viewpager.widget.ViewPager.j jVar3 = this.U;
        if (jVar3 != null) {
            jVar3.a(i2, f2, i3);
        }
    }

    private void b(boolean z) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).setLayerType(z ? this.a0 : 0, null);
        }
    }

    private boolean b(float f2) {
        boolean z;
        boolean z2;
        float f3 = this.C - f2;
        this.C = f2;
        float scrollX = getScrollX() + f3;
        float clientWidth = getClientWidth();
        float f4 = this.q * clientWidth;
        float f5 = this.r * clientWidth;
        boolean z3 = false;
        androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(0);
        java.util.ArrayList<androidx.viewpager.widget.ViewPager.f> arrayList = this.f348b;
        androidx.viewpager.widget.ViewPager.f fVar2 = arrayList.get(arrayList.size() - 1);
        if (fVar.f354b != 0) {
            f4 = fVar.e * clientWidth;
            z = false;
        } else {
            z = true;
        }
        if (fVar2.f354b != this.e.a() - 1) {
            f5 = fVar2.e * clientWidth;
            z2 = false;
        } else {
            z2 = true;
        }
        if (scrollX < f4) {
            if (z) {
                this.N.onPull(java.lang.Math.abs(f4 - scrollX) / clientWidth);
                z3 = true;
            }
            scrollX = f4;
        } else if (scrollX > f5) {
            if (z2) {
                this.O.onPull(java.lang.Math.abs(scrollX - f5) / clientWidth);
                z3 = true;
            }
            scrollX = f5;
        }
        int i2 = (int) scrollX;
        this.C += scrollX - i2;
        scrollTo(i2, getScrollY());
        f(i2);
        return z3;
    }

    private void c(boolean z) {
        android.view.ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private static boolean c(android.view.View view) {
        return view.getClass().getAnnotation(androidx.viewpager.widget.ViewPager.e.class) != null;
    }

    private void d(int i2) {
        androidx.viewpager.widget.ViewPager.j jVar = this.T;
        if (jVar != null) {
            jVar.a(i2);
        }
        java.util.List<androidx.viewpager.widget.ViewPager.j> list = this.S;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                androidx.viewpager.widget.ViewPager.j jVar2 = this.S.get(i3);
                if (jVar2 != null) {
                    jVar2.a(i2);
                }
            }
        }
        androidx.viewpager.widget.ViewPager.j jVar3 = this.U;
        if (jVar3 != null) {
            jVar3.a(i2);
        }
    }

    private void e(int i2) {
        androidx.viewpager.widget.ViewPager.j jVar = this.T;
        if (jVar != null) {
            jVar.b(i2);
        }
        java.util.List<androidx.viewpager.widget.ViewPager.j> list = this.S;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                androidx.viewpager.widget.ViewPager.j jVar2 = this.S.get(i3);
                if (jVar2 != null) {
                    jVar2.b(i2);
                }
            }
        }
        androidx.viewpager.widget.ViewPager.j jVar3 = this.U;
        if (jVar3 != null) {
            jVar3.b(i2);
        }
    }

    private void f() {
        this.x = false;
        this.y = false;
        android.view.VelocityTracker velocityTracker = this.H;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.H = null;
        }
    }

    private boolean f(int i2) {
        if (this.f348b.size() == 0) {
            if (this.P) {
                return false;
            }
            this.Q = false;
            a(0, 0.0f, 0);
            if (this.Q) {
                return false;
            }
            throw new java.lang.IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        androidx.viewpager.widget.ViewPager.f fVarG = g();
        int clientWidth = getClientWidth();
        int i3 = this.m;
        int i4 = clientWidth + i3;
        float f2 = clientWidth;
        int i5 = fVarG.f354b;
        float f3 = ((i2 / f2) - fVarG.e) / (fVarG.d + (i3 / f2));
        this.Q = false;
        a(i5, f3, (int) (i4 * f3));
        if (this.Q) {
            return true;
        }
        throw new java.lang.IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    private androidx.viewpager.widget.ViewPager.f g() {
        int i2;
        int clientWidth = getClientWidth();
        float f2 = 0.0f;
        float scrollX = clientWidth > 0 ? getScrollX() / clientWidth : 0.0f;
        float f3 = clientWidth > 0 ? this.m / clientWidth : 0.0f;
        androidx.viewpager.widget.ViewPager.f fVar = null;
        float f4 = 0.0f;
        int i3 = -1;
        int i4 = 0;
        boolean z = true;
        while (i4 < this.f348b.size()) {
            androidx.viewpager.widget.ViewPager.f fVar2 = this.f348b.get(i4);
            if (!z && fVar2.f354b != (i2 = i3 + 1)) {
                fVar2 = this.f349c;
                fVar2.e = f2 + f4 + f3;
                fVar2.f354b = i2;
                fVar2.d = this.e.a(i2);
                i4--;
            }
            f2 = fVar2.e;
            float f5 = fVar2.d + f2 + f3;
            if (!z && scrollX < f2) {
                return fVar;
            }
            if (scrollX < f5 || i4 == this.f348b.size() - 1) {
                return fVar2;
            }
            i3 = fVar2.f354b;
            f4 = fVar2.d;
            i4++;
            fVar = fVar2;
            z = false;
        }
        return fVar;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private void h() {
        int i2 = 0;
        while (i2 < getChildCount()) {
            if (!((androidx.viewpager.widget.ViewPager.g) getChildAt(i2).getLayoutParams()).f356a) {
                removeViewAt(i2);
                i2--;
            }
            i2++;
        }
    }

    private boolean i() {
        this.G = -1;
        f();
        this.N.onRelease();
        this.O.onRelease();
        return this.N.isFinished() || this.O.isFinished();
    }

    private void j() {
        if (this.b0 != 0) {
            java.util.ArrayList<android.view.View> arrayList = this.c0;
            if (arrayList == null) {
                this.c0 = new java.util.ArrayList<>();
            } else {
                arrayList.clear();
            }
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                this.c0.add(getChildAt(i2));
            }
            java.util.Collections.sort(this.c0, i0);
        }
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.u != z) {
            this.u = z;
        }
    }

    float a(float f2) {
        return (float) java.lang.Math.sin((f2 - 0.5f) * 0.47123894f);
    }

    androidx.viewpager.widget.ViewPager.f a(int i2, int i3) {
        androidx.viewpager.widget.ViewPager.f fVar = new androidx.viewpager.widget.ViewPager.f();
        fVar.f354b = i2;
        fVar.f353a = this.e.a(this, i2);
        fVar.d = this.e.a(i2);
        if (i3 < 0 || i3 >= this.f348b.size()) {
            this.f348b.add(fVar);
        } else {
            this.f348b.add(i3, fVar);
        }
        return fVar;
    }

    androidx.viewpager.widget.ViewPager.f a(android.view.View view) {
        while (true) {
            java.lang.Object parent = view.getParent();
            if (parent == this) {
                return b(view);
            }
            if (parent == null || !(parent instanceof android.view.View)) {
                return null;
            }
            view = (android.view.View) parent;
        }
    }

    void a() {
        int iA = this.e.a();
        this.f347a = iA;
        boolean z = this.f348b.size() < (this.w * 2) + 1 && this.f348b.size() < iA;
        int iMax = this.f;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < this.f348b.size()) {
            androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(i2);
            int iA2 = this.e.a(fVar.f353a);
            if (iA2 != -1) {
                if (iA2 == -2) {
                    this.f348b.remove(i2);
                    i2--;
                    if (!z2) {
                        this.e.b((android.view.ViewGroup) this);
                        z2 = true;
                    }
                    this.e.a((android.view.ViewGroup) this, fVar.f354b, fVar.f353a);
                    int i3 = this.f;
                    if (i3 == fVar.f354b) {
                        iMax = java.lang.Math.max(0, java.lang.Math.min(i3, iA - 1));
                    }
                } else {
                    int i4 = fVar.f354b;
                    if (i4 != iA2) {
                        if (i4 == this.f) {
                            iMax = iA2;
                        }
                        fVar.f354b = iA2;
                    }
                }
                z = true;
            }
            i2++;
        }
        if (z2) {
            this.e.a((android.view.ViewGroup) this);
        }
        java.util.Collections.sort(this.f348b, g0);
        if (z) {
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                androidx.viewpager.widget.ViewPager.g gVar = (androidx.viewpager.widget.ViewPager.g) getChildAt(i5).getLayoutParams();
                if (!gVar.f356a) {
                    gVar.f358c = 0.0f;
                }
            }
            a(iMax, false, true);
            requestLayout();
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0064  */
    protected void a(int i2, float f2, int i3) {
        int iMax;
        int width;
        int left;
        if (this.R > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width2 = getWidth();
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                android.view.View childAt = getChildAt(i4);
                androidx.viewpager.widget.ViewPager.g gVar = (androidx.viewpager.widget.ViewPager.g) childAt.getLayoutParams();
                if (gVar.f356a) {
                    int i5 = gVar.f357b & 7;
                    if (i5 != 1) {
                        if (i5 == 3) {
                            width = childAt.getWidth() + paddingLeft;
                        } else if (i5 != 5) {
                            width = paddingLeft;
                        } else {
                            iMax = (width2 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        left = (paddingLeft + scrollX) - childAt.getLeft();
                        if (left != 0) {
                            childAt.offsetLeftAndRight(left);
                        }
                        paddingLeft = width;
                    } else {
                        iMax = java.lang.Math.max((width2 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i6 = iMax;
                    width = paddingLeft;
                    paddingLeft = i6;
                    left = (paddingLeft + scrollX) - childAt.getLeft();
                    if (left != 0) {
                        childAt.offsetLeftAndRight(left);
                    }
                    paddingLeft = width;
                }
            }
        }
        b(i2, f2, i3);
        if (this.W != null) {
            int scrollX2 = getScrollX();
            int childCount2 = getChildCount();
            for (int i7 = 0; i7 < childCount2; i7++) {
                android.view.View childAt2 = getChildAt(i7);
                if (!((androidx.viewpager.widget.ViewPager.g) childAt2.getLayoutParams()).f356a) {
                    this.W.a(childAt2, (childAt2.getLeft() - scrollX2) / getClientWidth());
                }
            }
        }
        this.Q = true;
    }

    void a(int i2, int i3, int i4) {
        int scrollX;
        int iAbs;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        android.widget.Scroller scroller = this.j;
        if ((scroller == null || scroller.isFinished()) ? false : true) {
            scrollX = this.k ? this.j.getCurrX() : this.j.getStartX();
            this.j.abortAnimation();
            setScrollingCacheEnabled(false);
        } else {
            scrollX = getScrollX();
        }
        int i5 = scrollX;
        int scrollY = getScrollY();
        int i6 = i2 - i5;
        int i7 = i3 - scrollY;
        if (i6 == 0 && i7 == 0) {
            a(false);
            e();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int clientWidth = getClientWidth();
        int i8 = clientWidth / 2;
        float f2 = clientWidth;
        float f3 = i8;
        float fA = f3 + (a(java.lang.Math.min(1.0f, (java.lang.Math.abs(i6) * 1.0f) / f2)) * f3);
        int iAbs2 = java.lang.Math.abs(i4);
        if (iAbs2 > 0) {
            iAbs = java.lang.Math.round(java.lang.Math.abs(fA / iAbs2) * 1000.0f) * 4;
        } else {
            iAbs = (int) (((java.lang.Math.abs(i6) / ((f2 * this.e.a(this.f)) + this.m)) + 1.0f) * 100.0f);
        }
        int iMin = java.lang.Math.min(iAbs, 600);
        this.k = false;
        this.j.startScroll(i5, scrollY, i6, i7, iMin);
        a.c.e.m.p(this);
    }

    public void a(int i2, boolean z) {
        this.v = false;
        a(i2, z, false);
    }

    void a(int i2, boolean z, boolean z2) {
        a(i2, z, z2, 0);
    }

    void a(int i2, boolean z, boolean z2, int i3) {
        androidx.viewpager.widget.a aVar = this.e;
        if (aVar == null || aVar.a() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!z2 && this.f == i2 && this.f348b.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 >= this.e.a()) {
            i2 = this.e.a() - 1;
        }
        int i4 = this.w;
        int i5 = this.f;
        if (i2 > i5 + i4 || i2 < i5 - i4) {
            for (int i6 = 0; i6 < this.f348b.size(); i6++) {
                this.f348b.get(i6).f355c = true;
            }
        }
        boolean z3 = this.f != i2;
        if (!this.P) {
            c(i2);
            a(i2, z, i3, z3);
        } else {
            this.f = i2;
            if (z3) {
                d(i2);
            }
            requestLayout();
        }
    }

    public void a(androidx.viewpager.widget.ViewPager.j jVar) {
        if (this.S == null) {
            this.S = new java.util.ArrayList();
        }
        this.S.add(jVar);
    }

    public void a(boolean z, androidx.viewpager.widget.ViewPager.k kVar) {
        a(z, kVar, 2);
    }

    public void a(boolean z, androidx.viewpager.widget.ViewPager.k kVar, int i2) {
        boolean z2 = kVar != null;
        boolean z3 = z2 != (this.W != null);
        this.W = kVar;
        setChildrenDrawingOrderEnabled(z2);
        if (z2) {
            this.b0 = z ? 2 : 1;
            this.a0 = i2;
        } else {
            this.b0 = 0;
        }
        if (z3) {
            e();
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0094  */
    public boolean a(int i2) {
        boolean z;
        boolean zD;
        android.view.View viewFindFocus = findFocus();
        boolean zC = false;
        if (viewFindFocus == this) {
            viewFindFocus = null;
        } else if (viewFindFocus != null) {
            android.view.ViewParent parent = viewFindFocus.getParent();
            while (true) {
                if (!(parent instanceof android.view.ViewGroup)) {
                    z = false;
                    break;
                }
                if (parent == this) {
                    z = true;
                    break;
                }
                parent = parent.getParent();
            }
            if (!z) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(viewFindFocus.getClass().getSimpleName());
                for (android.view.ViewParent parent2 = viewFindFocus.getParent(); parent2 instanceof android.view.ViewGroup; parent2 = parent2.getParent()) {
                    sb.append(" => ");
                    sb.append(parent2.getClass().getSimpleName());
                }
                android.util.Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                viewFindFocus = null;
            }
        }
        android.view.View viewFindNextFocus = android.view.FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i2);
        if (viewFindNextFocus == null || viewFindNextFocus == viewFindFocus) {
            if (i2 == 17 || i2 == 1) {
                zC = c();
            } else if (i2 == 66 || i2 == 2) {
                zC = d();
            }
        } else if (i2 == 17) {
            int i3 = a(this.d, viewFindNextFocus).left;
            int i4 = a(this.d, viewFindFocus).left;
            if (viewFindFocus == null || i3 < i4) {
                zD = viewFindNextFocus.requestFocus();
            } else {
                zD = c();
            }
            zC = zD;
        } else if (i2 == 66) {
            int i5 = a(this.d, viewFindNextFocus).left;
            int i6 = a(this.d, viewFindFocus).left;
            if (viewFindFocus == null || i5 > i6) {
                zD = viewFindNextFocus.requestFocus();
            } else {
                zD = d();
            }
            zC = zD;
        }
        if (zC) {
            playSoundEffect(android.view.SoundEffectConstants.getContantForFocusDirection(i2));
        }
        return zC;
    }

    public boolean a(android.view.KeyEvent keyEvent) {
        int i2;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 21) {
                if (keyCode != 22) {
                    if (keyCode == 61) {
                        if (keyEvent.hasNoModifiers()) {
                            return a(2);
                        }
                        if (keyEvent.hasModifiers(1)) {
                            return a(1);
                        }
                    }
                } else {
                    if (keyEvent.hasModifiers(2)) {
                        return d();
                    }
                    i2 = 66;
                }
            } else {
                if (keyEvent.hasModifiers(2)) {
                    return c();
                }
                i2 = 17;
            }
            return a(i2);
        }
        return false;
    }

    protected boolean a(android.view.View view, boolean z, int i2, int i3, int i4) {
        int i5;
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                android.view.View childAt = viewGroup.getChildAt(childCount);
                int i6 = i3 + scrollX;
                if (i6 >= childAt.getLeft() && i6 < childAt.getRight() && (i5 = i4 + scrollY) >= childAt.getTop() && i5 < childAt.getBottom() && a(childAt, true, i2, i6 - childAt.getLeft(), i5 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z && view.canScrollHorizontally(-i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(java.util.ArrayList<android.view.View> arrayList, int i2, int i3) {
        androidx.viewpager.widget.ViewPager.f fVarB;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                android.view.View childAt = getChildAt(i4);
                if (childAt.getVisibility() == 0 && (fVarB = b(childAt)) != null && fVarB.f354b == this.f) {
                    childAt.addFocusables(arrayList, i2, i3);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if (((i3 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) || arrayList == null) {
                return;
            }
            arrayList.add(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(java.util.ArrayList<android.view.View> arrayList) {
        androidx.viewpager.widget.ViewPager.f fVarB;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (fVarB = b(childAt)) != null && fVarB.f354b == this.f) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i2, android.view.ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        androidx.viewpager.widget.ViewPager.g gVar = (androidx.viewpager.widget.ViewPager.g) layoutParams;
        boolean zC = gVar.f356a | c(view);
        gVar.f356a = zC;
        if (!this.t) {
            super.addView(view, i2, layoutParams);
        } else {
            if (gVar != null && zC) {
                throw new java.lang.IllegalStateException("Cannot add pager decor view during layout");
            }
            gVar.d = true;
            addViewInLayout(view, i2, layoutParams);
        }
    }

    androidx.viewpager.widget.ViewPager.f b(int i2) {
        for (int i3 = 0; i3 < this.f348b.size(); i3++) {
            androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(i3);
            if (fVar.f354b == i2) {
                return fVar;
            }
        }
        return null;
    }

    androidx.viewpager.widget.ViewPager.f b(android.view.View view) {
        for (int i2 = 0; i2 < this.f348b.size(); i2++) {
            androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(i2);
            if (this.e.a(view, fVar.f353a)) {
                return fVar;
            }
        }
        return null;
    }

    void b() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        android.content.Context context = getContext();
        this.j = new android.widget.Scroller(context, h0);
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.B = viewConfiguration.getScaledPagingTouchSlop();
        this.I = (int) (400.0f * f2);
        this.J = viewConfiguration.getScaledMaximumFlingVelocity();
        this.N = new android.widget.EdgeEffect(context);
        this.O = new android.widget.EdgeEffect(context);
        this.K = (int) (25.0f * f2);
        this.L = (int) (2.0f * f2);
        this.z = (int) (f2 * 16.0f);
        a.c.e.m.a(this, new androidx.viewpager.widget.ViewPager.h());
        if (a.c.e.m.g(this) == 0) {
            a.c.e.m.b(this, 1);
        }
        a.c.e.m.a(this, new androidx.viewpager.widget.ViewPager.d());
    }

    /* JADX WARN: Code duplicated, block: B:63:0x00de A[PHI: r7 r10 r15
  0x00de: PHI (r7v6 int) = (r7v5 int), (r7v4 int), (r7v10 int) binds: [B:62:0x00dc, B:59:0x00ce, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
  0x00de: PHI (r10v9 int) = (r10v1 int), (r10v8 int), (r10v13 int) binds: [B:62:0x00dc, B:59:0x00ce, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
  0x00de: PHI (r15v7 float) = (r15v5 float), (r15v6 float), (r15v4 float) binds: [B:62:0x00dc, B:59:0x00ce, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:64:0x00e7 A[PHI: r7 r10 r15
  0x00e7: PHI (r7v9 int) = (r7v5 int), (r7v4 int), (r7v10 int) binds: [B:62:0x00dc, B:59:0x00ce, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
  0x00e7: PHI (r10v12 int) = (r10v1 int), (r10v8 int), (r10v13 int) binds: [B:62:0x00dc, B:59:0x00ce, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
  0x00e7: PHI (r15v10 float) = (r15v5 float), (r15v6 float), (r15v4 float) binds: [B:62:0x00dc, B:59:0x00ce, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]] */
    void c(int i2) {
        androidx.viewpager.widget.ViewPager.f fVarB;
        java.lang.String hexString;
        androidx.viewpager.widget.ViewPager.f fVarA;
        androidx.viewpager.widget.ViewPager.f fVarB2;
        androidx.viewpager.widget.ViewPager.f fVar;
        int i3 = this.f;
        if (i3 != i2) {
            fVarB = b(i3);
            this.f = i2;
        } else {
            fVarB = null;
        }
        if (this.e == null) {
            j();
            return;
        }
        if (this.v) {
            j();
            return;
        }
        if (getWindowToken() == null) {
            return;
        }
        this.e.b((android.view.ViewGroup) this);
        int i4 = this.w;
        int iMax = java.lang.Math.max(0, this.f - i4);
        int iA = this.e.a();
        int iMin = java.lang.Math.min(iA - 1, this.f + i4);
        if (iA != this.f347a) {
            try {
                hexString = getResources().getResourceName(getId());
            } catch (android.content.res.Resources.NotFoundException unused) {
                hexString = java.lang.Integer.toHexString(getId());
            }
            throw new java.lang.IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.f347a + ", found: " + iA + " Pager id: " + hexString + " Pager class: " + androidx.viewpager.widget.ViewPager.class + " Problematic adapter: " + this.e.getClass());
        }
        int i5 = 0;
        while (true) {
            if (i5 < this.f348b.size()) {
                fVarA = this.f348b.get(i5);
                int i6 = fVarA.f354b;
                int i7 = this.f;
                if (i6 >= i7) {
                    if (i6 != i7) {
                        break;
                    } else {
                        break;
                    }
                }
                i5++;
            }
            fVarA = null;
            break;
        }
        if (fVarA == null && iA > 0) {
            fVarA = a(this.f, i5);
        }
        if (fVarA != null) {
            int i8 = i5 - 1;
            androidx.viewpager.widget.ViewPager.f fVar2 = i8 >= 0 ? this.f348b.get(i8) : null;
            int clientWidth = getClientWidth();
            float paddingLeft = clientWidth <= 0 ? 0.0f : (2.0f - fVarA.d) + (getPaddingLeft() / clientWidth);
            float f2 = 0.0f;
            for (int i9 = this.f - 1; i9 >= 0; i9--) {
                if (f2 < paddingLeft || i9 >= iMax) {
                    if (fVar2 == null || i9 != fVar2.f354b) {
                        f2 += a(i9, i8 + 1).d;
                        i5++;
                        if (i8 >= 0) {
                            fVar = this.f348b.get(i8);
                        } else {
                            fVar = null;
                        }
                    } else {
                        f2 += fVar2.d;
                        i8--;
                        if (i8 >= 0) {
                            fVar = this.f348b.get(i8);
                        } else {
                            fVar = null;
                        }
                    }
                    fVar2 = fVar;
                } else {
                    if (fVar2 == null) {
                        break;
                    }
                    if (i9 == fVar2.f354b && !fVar2.f355c) {
                        this.f348b.remove(i8);
                        this.e.a((android.view.ViewGroup) this, i9, fVar2.f353a);
                        i8--;
                        i5--;
                        if (i8 >= 0) {
                            fVar = this.f348b.get(i8);
                        } else {
                            fVar = null;
                        }
                        fVar2 = fVar;
                    }
                }
            }
            float f3 = fVarA.d;
            int i10 = i5 + 1;
            if (f3 < 2.0f) {
                androidx.viewpager.widget.ViewPager.f fVar3 = i10 < this.f348b.size() ? this.f348b.get(i10) : null;
                float paddingRight = clientWidth <= 0 ? 0.0f : (getPaddingRight() / clientWidth) + 2.0f;
                int i11 = this.f;
                while (true) {
                    i11++;
                    if (i11 >= iA) {
                        break;
                    }
                    if (f3 >= paddingRight && i11 > iMin) {
                        if (fVar3 == null) {
                            break;
                        }
                        if (i11 == fVar3.f354b && !fVar3.f355c) {
                            this.f348b.remove(i10);
                            this.e.a((android.view.ViewGroup) this, i11, fVar3.f353a);
                            if (i10 < this.f348b.size()) {
                            }
                        }
                    } else if (fVar3 == null || i11 != fVar3.f354b) {
                        androidx.viewpager.widget.ViewPager.f fVarA2 = a(i11, i10);
                        i10++;
                        f3 += fVarA2.d;
                        fVar3 = i10 < this.f348b.size() ? this.f348b.get(i10) : null;
                    } else {
                        f3 += fVar3.d;
                        i10++;
                        if (i10 < this.f348b.size()) {
                        }
                    }
                }
            }
            a(fVarA, i5, fVarB);
            this.e.b(this, this.f, fVarA.f353a);
        }
        this.e.a((android.view.ViewGroup) this);
        int childCount = getChildCount();
        for (int i12 = 0; i12 < childCount; i12++) {
            android.view.View childAt = getChildAt(i12);
            androidx.viewpager.widget.ViewPager.g gVar = (androidx.viewpager.widget.ViewPager.g) childAt.getLayoutParams();
            gVar.f = i12;
            if (!gVar.f356a && gVar.f358c == 0.0f && (fVarB2 = b(childAt)) != null) {
                gVar.f358c = fVarB2.d;
                gVar.e = fVarB2.f354b;
            }
        }
        j();
        if (hasFocus()) {
            android.view.View viewFindFocus = findFocus();
            androidx.viewpager.widget.ViewPager.f fVarA3 = viewFindFocus != null ? a(viewFindFocus) : null;
            if (fVarA3 == null || fVarA3.f354b != this.f) {
                for (int i13 = 0; i13 < getChildCount(); i13++) {
                    android.view.View childAt2 = getChildAt(i13);
                    androidx.viewpager.widget.ViewPager.f fVarB3 = b(childAt2);
                    if (fVarB3 != null && fVarB3.f354b == this.f && childAt2.requestFocus(2)) {
                        return;
                    }
                }
            }
        }
    }

    boolean c() {
        int i2 = this.f;
        if (i2 <= 0) {
            return false;
        }
        a(i2 - 1, true);
        return true;
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i2) {
        if (this.e == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        if (i2 < 0) {
            return scrollX > ((int) (((float) clientWidth) * this.q));
        }
        return i2 > 0 && scrollX < ((int) (((float) clientWidth) * this.r));
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof androidx.viewpager.widget.ViewPager.g) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void computeScroll() {
        this.k = true;
        if (this.j.isFinished() || !this.j.computeScrollOffset()) {
            a(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.j.getCurrX();
        int currY = this.j.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
            if (!f(currX)) {
                this.j.abortAnimation();
                scrollTo(0, currY);
            }
        }
        a.c.e.m.p(this);
    }

    boolean d() {
        androidx.viewpager.widget.a aVar = this.e;
        if (aVar == null || this.f >= aVar.a() - 1) {
            return false;
        }
        a(this.f + 1, true);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || a(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        androidx.viewpager.widget.ViewPager.f fVarB;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (fVarB = b(childAt)) != null && fVarB.f354b == this.f && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        androidx.viewpager.widget.a aVar;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean zDraw = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (aVar = this.e) != null && aVar.a() > 1)) {
            if (!this.N.isFinished()) {
                int iSave = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((-height) + getPaddingTop(), this.q * width);
                this.N.setSize(height, width);
                zDraw = false | this.N.draw(canvas);
                canvas.restoreToCount(iSave);
            }
            if (!this.O.isFinished()) {
                int iSave2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.r + 1.0f)) * width2);
                this.O.setSize(height2, width2);
                zDraw |= this.O.draw(canvas);
                canvas.restoreToCount(iSave2);
            }
        } else {
            this.N.finish();
            this.O.finish();
        }
        if (zDraw) {
            a.c.e.m.p(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        android.graphics.drawable.Drawable drawable = this.n;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    void e() {
        c(this.f);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new androidx.viewpager.widget.ViewPager.g();
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new androidx.viewpager.widget.ViewPager.g(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    public androidx.viewpager.widget.a getAdapter() {
        return this.e;
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i2, int i3) {
        if (this.b0 == 2) {
            i3 = (i2 - 1) - i3;
        }
        return ((androidx.viewpager.widget.ViewPager.g) this.c0.get(i3).getLayoutParams()).f;
    }

    public int getCurrentItem() {
        return this.f;
    }

    public int getOffscreenPageLimit() {
        return this.w;
    }

    public int getPageMargin() {
        return this.m;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.P = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.d0);
        android.widget.Scroller scroller = this.j;
        if (scroller != null && !scroller.isFinished()) {
            this.j.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        float f2;
        super.onDraw(canvas);
        if (this.m <= 0 || this.n == null || this.f348b.size() <= 0 || this.e == null) {
            return;
        }
        int scrollX = getScrollX();
        int width = getWidth();
        float f3 = width;
        float f4 = this.m / f3;
        int i2 = 0;
        androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(0);
        float f5 = fVar.e;
        int size = this.f348b.size();
        int i3 = fVar.f354b;
        int i4 = this.f348b.get(size - 1).f354b;
        while (i3 < i4) {
            while (i3 > fVar.f354b && i2 < size) {
                i2++;
                fVar = this.f348b.get(i2);
            }
            if (i3 == fVar.f354b) {
                float f6 = fVar.e;
                float f7 = fVar.d;
                f2 = (f6 + f7) * f3;
                f5 = f6 + f7 + f4;
            } else {
                float fA = this.e.a(i3);
                f2 = (f5 + fA) * f3;
                f5 += fA + f4;
            }
            if (this.m + f2 > scrollX) {
                this.n.setBounds(java.lang.Math.round(f2), this.o, java.lang.Math.round(this.m + f2), this.p);
                this.n.draw(canvas);
            }
            if (f2 > scrollX + width) {
                return;
            }
            i3++;
            f4 = f4;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            i();
            return false;
        }
        if (action != 0) {
            if (this.x) {
                return true;
            }
            if (this.y) {
                return false;
            }
        }
        if (action == 0) {
            float x = motionEvent.getX();
            this.E = x;
            this.C = x;
            float y = motionEvent.getY();
            this.F = y;
            this.D = y;
            this.G = motionEvent.getPointerId(0);
            this.y = false;
            this.k = true;
            this.j.computeScrollOffset();
            if (this.e0 != 2 || java.lang.Math.abs(this.j.getFinalX() - this.j.getCurrX()) <= this.L) {
                a(false);
                this.x = false;
            } else {
                this.j.abortAnimation();
                this.v = false;
                e();
                this.x = true;
                c(true);
                setScrollState(1);
            }
        } else if (action == 2) {
            int i2 = this.G;
            if (i2 != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i2);
                float x2 = motionEvent.getX(iFindPointerIndex);
                float f2 = x2 - this.C;
                float fAbs = java.lang.Math.abs(f2);
                float y2 = motionEvent.getY(iFindPointerIndex);
                float fAbs2 = java.lang.Math.abs(y2 - this.F);
                if (f2 != 0.0f && !a(this.C, f2) && a(this, false, (int) f2, (int) x2, (int) y2)) {
                    this.C = x2;
                    this.D = y2;
                    this.y = true;
                    return false;
                }
                if (fAbs > this.B && fAbs * 0.5f > fAbs2) {
                    this.x = true;
                    c(true);
                    setScrollState(1);
                    float f3 = this.E;
                    float f4 = this.B;
                    this.C = f2 > 0.0f ? f3 + f4 : f3 - f4;
                    this.D = y2;
                    setScrollingCacheEnabled(true);
                } else if (fAbs2 > this.B) {
                    this.y = true;
                }
                if (this.x && b(x2)) {
                    a.c.e.m.p(this);
                }
            }
        } else if (action == 6) {
            a(motionEvent);
        }
        if (this.H == null) {
            this.H = android.view.VelocityTracker.obtain();
        }
        this.H.addMovement(motionEvent);
        return this.x;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0071  */
    /* JADX WARN: Code duplicated, block: B:24:0x0075  */
    /* JADX WARN: Code duplicated, block: B:26:0x0079  */
    /* JADX WARN: Code duplicated, block: B:27:0x007b  */
    /* JADX WARN: Code duplicated, block: B:28:0x0088  */
    /* JADX WARN: Code duplicated, block: B:29:0x008e  */
    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        boolean z2;
        androidx.viewpager.widget.ViewPager.f fVarB;
        int iMax;
        int measuredWidth;
        int iMax2;
        int measuredHeight;
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            android.view.View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                androidx.viewpager.widget.ViewPager.g gVar = (androidx.viewpager.widget.ViewPager.g) childAt.getLayoutParams();
                if (gVar.f356a) {
                    int i10 = gVar.f357b;
                    int i11 = i10 & 7;
                    int i12 = i10 & 112;
                    if (i11 != 1) {
                        if (i11 == 3) {
                            measuredWidth = childAt.getMeasuredWidth() + paddingLeft;
                        } else if (i11 != 5) {
                            measuredWidth = paddingLeft;
                        } else {
                            iMax = (i6 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        if (i12 != 16) {
                            if (i12 != 48) {
                                measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                            } else if (i12 != 80) {
                                measuredHeight = paddingTop;
                            } else {
                                iMax2 = (i7 - paddingBottom) - childAt.getMeasuredHeight();
                                paddingBottom += childAt.getMeasuredHeight();
                            }
                            int i13 = paddingLeft + scrollX;
                            childAt.layout(i13, paddingTop, childAt.getMeasuredWidth() + i13, paddingTop + childAt.getMeasuredHeight());
                            i8++;
                            paddingTop = measuredHeight;
                            paddingLeft = measuredWidth;
                        } else {
                            iMax2 = java.lang.Math.max((i7 - childAt.getMeasuredHeight()) / 2, paddingTop);
                        }
                        int i14 = iMax2;
                        measuredHeight = paddingTop;
                        paddingTop = i14;
                        int i15 = paddingLeft + scrollX;
                        childAt.layout(i15, paddingTop, childAt.getMeasuredWidth() + i15, paddingTop + childAt.getMeasuredHeight());
                        i8++;
                        paddingTop = measuredHeight;
                        paddingLeft = measuredWidth;
                    } else {
                        iMax = java.lang.Math.max((i6 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i16 = iMax;
                    measuredWidth = paddingLeft;
                    paddingLeft = i16;
                    if (i12 != 16) {
                        if (i12 != 48) {
                            measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                        } else if (i12 != 80) {
                            measuredHeight = paddingTop;
                        } else {
                            iMax2 = (i7 - paddingBottom) - childAt.getMeasuredHeight();
                            paddingBottom += childAt.getMeasuredHeight();
                        }
                        int i17 = paddingLeft + scrollX;
                        childAt.layout(i17, paddingTop, childAt.getMeasuredWidth() + i17, paddingTop + childAt.getMeasuredHeight());
                        i8++;
                        paddingTop = measuredHeight;
                        paddingLeft = measuredWidth;
                    } else {
                        iMax2 = java.lang.Math.max((i7 - childAt.getMeasuredHeight()) / 2, paddingTop);
                    }
                    int i18 = iMax2;
                    measuredHeight = paddingTop;
                    paddingTop = i18;
                    int i19 = paddingLeft + scrollX;
                    childAt.layout(i19, paddingTop, childAt.getMeasuredWidth() + i19, paddingTop + childAt.getMeasuredHeight());
                    i8++;
                    paddingTop = measuredHeight;
                    paddingLeft = measuredWidth;
                }
            }
        }
        int i20 = (i6 - paddingLeft) - paddingRight;
        for (int i21 = 0; i21 < childCount; i21++) {
            android.view.View childAt2 = getChildAt(i21);
            if (childAt2.getVisibility() != 8) {
                androidx.viewpager.widget.ViewPager.g gVar2 = (androidx.viewpager.widget.ViewPager.g) childAt2.getLayoutParams();
                if (!gVar2.f356a && (fVarB = b(childAt2)) != null) {
                    float f2 = i20;
                    int i22 = ((int) (fVarB.e * f2)) + paddingLeft;
                    if (gVar2.d) {
                        gVar2.d = false;
                        childAt2.measure(android.view.View.MeasureSpec.makeMeasureSpec((int) (f2 * gVar2.f358c), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec((i7 - paddingTop) - paddingBottom, 1073741824));
                    }
                    childAt2.layout(i22, paddingTop, childAt2.getMeasuredWidth() + i22, childAt2.getMeasuredHeight() + paddingTop);
                }
            }
        }
        this.o = paddingTop;
        this.p = i7 - paddingBottom;
        this.R = i8;
        if (this.P) {
            z2 = false;
            a(this.f, false, 0, false);
        } else {
            z2 = false;
        }
        this.P = z2;
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0082 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x0085  */
    /* JADX WARN: Code duplicated, block: B:36:0x0089  */
    /* JADX WARN: Code duplicated, block: B:39:0x008e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x0091  */
    /* JADX WARN: Code duplicated, block: B:42:0x0093  */
    /* JADX WARN: Code duplicated, block: B:45:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:46:0x00a8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:66:0x00af A[SYNTHETIC] */
    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        androidx.viewpager.widget.ViewPager.g gVar;
        androidx.viewpager.widget.ViewPager.g gVar2;
        int i4;
        int i5;
        int i6;
        setMeasuredDimension(android.view.ViewGroup.getDefaultSize(0, i2), android.view.ViewGroup.getDefaultSize(0, i3));
        int measuredWidth = getMeasuredWidth();
        this.A = java.lang.Math.min(measuredWidth / 10, this.z);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i7 = 0;
        while (true) {
            boolean z = true;
            int i8 = 1073741824;
            if (i7 >= childCount) {
                break;
            }
            android.view.View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8 && (gVar2 = (androidx.viewpager.widget.ViewPager.g) childAt.getLayoutParams()) != null && gVar2.f356a) {
                int i9 = gVar2.f357b;
                int i10 = i9 & 7;
                int i11 = i9 & 112;
                boolean z2 = i11 == 48 || i11 == 80;
                if (i10 != 3 && i10 != 5) {
                    z = false;
                }
                int i12 = Integer.MIN_VALUE;
                if (z2) {
                    i12 = 1073741824;
                } else {
                    i4 = z ? 1073741824 : Integer.MIN_VALUE;
                    i5 = ((android.view.ViewGroup.LayoutParams) gVar2).width;
                    if (i5 != -2) {
                        if (i5 == -1) {
                            i5 = paddingLeft;
                        }
                        i12 = 1073741824;
                    } else {
                        i5 = paddingLeft;
                    }
                    i6 = ((android.view.ViewGroup.LayoutParams) gVar2).height;
                    if (i6 != -2) {
                        i6 = measuredHeight;
                        i8 = i4;
                    } else if (i6 == -1) {
                        i6 = measuredHeight;
                    }
                    childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec(i5, i12), android.view.View.MeasureSpec.makeMeasureSpec(i6, i8));
                    if (z2) {
                        measuredHeight -= childAt.getMeasuredHeight();
                    } else if (z) {
                        paddingLeft -= childAt.getMeasuredWidth();
                    }
                }
                i5 = ((android.view.ViewGroup.LayoutParams) gVar2).width;
                if (i5 != -2) {
                    if (i5 == -1) {
                        i5 = paddingLeft;
                    }
                    i12 = 1073741824;
                } else {
                    i5 = paddingLeft;
                }
                i6 = ((android.view.ViewGroup.LayoutParams) gVar2).height;
                if (i6 != -2) {
                    i6 = measuredHeight;
                    i8 = i4;
                } else if (i6 == -1) {
                    i6 = measuredHeight;
                }
                childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec(i5, i12), android.view.View.MeasureSpec.makeMeasureSpec(i6, i8));
                if (z2) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i7++;
        }
        android.view.View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        this.s = android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.t = true;
        e();
        this.t = false;
        int childCount2 = getChildCount();
        for (int i13 = 0; i13 < childCount2; i13++) {
            android.view.View childAt2 = getChildAt(i13);
            if (childAt2.getVisibility() != 8 && ((gVar = (androidx.viewpager.widget.ViewPager.g) childAt2.getLayoutParams()) == null || !gVar.f356a)) {
                childAt2.measure(android.view.View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * gVar.f358c), 1073741824), this.s);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i2, android.graphics.Rect rect) {
        int i3;
        int i4;
        androidx.viewpager.widget.ViewPager.f fVarB;
        int childCount = getChildCount();
        int i5 = -1;
        if ((i2 & 2) != 0) {
            i5 = childCount;
            i3 = 0;
            i4 = 1;
        } else {
            i3 = childCount - 1;
            i4 = -1;
        }
        while (i3 != i5) {
            android.view.View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 0 && (fVarB = b(childAt)) != null && fVarB.f354b == this.f && childAt.requestFocus(i2, rect)) {
                return true;
            }
            i3 += i4;
        }
        return false;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (!(parcelable instanceof androidx.viewpager.widget.ViewPager.m)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        androidx.viewpager.widget.ViewPager.m mVar = (androidx.viewpager.widget.ViewPager.m) parcelable;
        super.onRestoreInstanceState(mVar.a());
        androidx.viewpager.widget.a aVar = this.e;
        if (aVar != null) {
            aVar.a(mVar.d, mVar.e);
            a(mVar.f360c, false, true);
        } else {
            this.g = mVar.f360c;
            this.h = mVar.d;
            this.i = mVar.e;
        }
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        androidx.viewpager.widget.ViewPager.m mVar = new androidx.viewpager.widget.ViewPager.m(super.onSaveInstanceState());
        mVar.f360c = this.f;
        androidx.viewpager.widget.a aVar = this.e;
        if (aVar != null) {
            mVar.d = aVar.b();
        }
        return mVar;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            int i6 = this.m;
            a(i2, i4, i6, i6);
        }
    }

    /* JADX WARN: Code duplicated, block: B:61:0x0151  */
    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        androidx.viewpager.widget.a aVar;
        int pointerId;
        if (this.M) {
            return true;
        }
        boolean zI = false;
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || (aVar = this.e) == null || aVar.a() == 0) {
            return false;
        }
        if (this.H == null) {
            this.H = android.view.VelocityTracker.obtain();
        }
        this.H.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (!this.x) {
                        int iFindPointerIndex = motionEvent.findPointerIndex(this.G);
                        if (iFindPointerIndex == -1) {
                            zI = i();
                        } else {
                            float x = motionEvent.getX(iFindPointerIndex);
                            float fAbs = java.lang.Math.abs(x - this.C);
                            float y = motionEvent.getY(iFindPointerIndex);
                            float fAbs2 = java.lang.Math.abs(y - this.D);
                            if (fAbs > this.B && fAbs > fAbs2) {
                                this.x = true;
                                c(true);
                                float f2 = this.E;
                                this.C = x - f2 > 0.0f ? f2 + this.B : f2 - this.B;
                                this.D = y;
                                setScrollState(1);
                                setScrollingCacheEnabled(true);
                                android.view.ViewParent parent = getParent();
                                if (parent != null) {
                                    parent.requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        }
                    }
                    if (this.x) {
                        zI = false | b(motionEvent.getX(motionEvent.findPointerIndex(this.G)));
                    }
                } else if (action != 3) {
                    if (action == 5) {
                        int actionIndex = motionEvent.getActionIndex();
                        this.C = motionEvent.getX(actionIndex);
                        pointerId = motionEvent.getPointerId(actionIndex);
                    } else if (action == 6) {
                        a(motionEvent);
                        this.C = motionEvent.getX(motionEvent.findPointerIndex(this.G));
                    }
                } else if (this.x) {
                    a(this.f, true, 0, false);
                    zI = i();
                }
            } else if (this.x) {
                android.view.VelocityTracker velocityTracker = this.H;
                velocityTracker.computeCurrentVelocity(1000, this.J);
                int xVelocity = (int) velocityTracker.getXVelocity(this.G);
                this.v = true;
                int clientWidth = getClientWidth();
                int scrollX = getScrollX();
                androidx.viewpager.widget.ViewPager.f fVarG = g();
                float f3 = clientWidth;
                a(a(fVarG.f354b, ((scrollX / f3) - fVarG.e) / (fVarG.d + (this.m / f3)), xVelocity, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.G)) - this.E)), true, true, xVelocity);
                zI = i();
            }
            if (zI) {
                a.c.e.m.p(this);
            }
            return true;
        }
        this.j.abortAnimation();
        this.v = false;
        e();
        float x2 = motionEvent.getX();
        this.E = x2;
        this.C = x2;
        float y2 = motionEvent.getY();
        this.F = y2;
        this.D = y2;
        pointerId = motionEvent.getPointerId(0);
        this.G = pointerId;
        if (zI) {
            a.c.e.m.p(this);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(android.view.View view) {
        if (this.t) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public void setAdapter(androidx.viewpager.widget.a aVar) {
        androidx.viewpager.widget.a aVar2 = this.e;
        if (aVar2 != null) {
            aVar2.a((android.database.DataSetObserver) null);
            this.e.b((android.view.ViewGroup) this);
            for (int i2 = 0; i2 < this.f348b.size(); i2++) {
                androidx.viewpager.widget.ViewPager.f fVar = this.f348b.get(i2);
                this.e.a((android.view.ViewGroup) this, fVar.f354b, fVar.f353a);
            }
            this.e.a((android.view.ViewGroup) this);
            this.f348b.clear();
            h();
            this.f = 0;
            scrollTo(0, 0);
        }
        androidx.viewpager.widget.a aVar3 = this.e;
        this.e = aVar;
        this.f347a = 0;
        if (aVar != null) {
            if (this.l == null) {
                this.l = new androidx.viewpager.widget.ViewPager.l();
            }
            this.e.a((android.database.DataSetObserver) this.l);
            this.v = false;
            boolean z = this.P;
            this.P = true;
            this.f347a = this.e.a();
            if (this.g >= 0) {
                this.e.a(this.h, this.i);
                a(this.g, false, true);
                this.g = -1;
                this.h = null;
                this.i = null;
            } else if (z) {
                requestLayout();
            } else {
                e();
            }
        }
        java.util.List<androidx.viewpager.widget.ViewPager.i> list = this.V;
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = this.V.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.V.get(i3).a(this, aVar3, aVar);
        }
    }

    public void setCurrentItem(int i2) {
        this.v = false;
        a(i2, !this.P, false);
    }

    public void setOffscreenPageLimit(int i2) {
        if (i2 < 1) {
            android.util.Log.w("ViewPager", "Requested offscreen page limit " + i2 + " too small; defaulting to 1");
            i2 = 1;
        }
        if (i2 != this.w) {
            this.w = i2;
            e();
        }
    }

    @java.lang.Deprecated
    public void setOnPageChangeListener(androidx.viewpager.widget.ViewPager.j jVar) {
        this.T = jVar;
    }

    public void setPageMargin(int i2) {
        int i3 = this.m;
        this.m = i2;
        int width = getWidth();
        a(width, width, i2, i3);
        requestLayout();
    }

    public void setPageMarginDrawable(int i2) {
        setPageMarginDrawable(androidx.core.content.a.a(getContext(), i2));
    }

    public void setPageMarginDrawable(android.graphics.drawable.Drawable drawable) {
        this.n = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    void setScrollState(int i2) {
        if (this.e0 == i2) {
            return;
        }
        this.e0 = i2;
        if (this.W != null) {
            b(i2 != 0);
        }
        e(i2);
    }

    @Override // android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.n;
    }
}
