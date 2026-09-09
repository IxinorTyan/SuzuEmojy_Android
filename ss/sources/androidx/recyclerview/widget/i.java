package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class i {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    int f327b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    int f328c;
    int d;
    int e;
    boolean h;
    boolean i;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    boolean f326a = true;
    int f = 0;
    int g = 0;

    i() {
    }

    android.view.View a(androidx.recyclerview.widget.RecyclerView.v vVar) {
        android.view.View viewD = vVar.d(this.f328c);
        this.f328c += this.d;
        return viewD;
    }

    boolean a(androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
        int i = this.f328c;
        return i >= 0 && i < a0Var.a();
    }

    public java.lang.String toString() {
        return "LayoutState{mAvailable=" + this.f327b + ", mCurrentPosition=" + this.f328c + ", mItemDirection=" + this.d + ", mLayoutDirection=" + this.e + ", mStartLine=" + this.f + ", mEndLine=" + this.g + '}';
    }
}
