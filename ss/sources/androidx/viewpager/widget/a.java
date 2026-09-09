package androidx.viewpager.widget;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    public a() {
        new android.database.DataSetObservable();
    }

    public float a(int i) {
        return 1.0f;
    }

    public abstract int a();

    public int a(java.lang.Object obj) {
        return -1;
    }

    public abstract java.lang.Object a(android.view.ViewGroup viewGroup, int i);

    void a(android.database.DataSetObserver dataSetObserver) {
        synchronized (this) {
        }
    }

    public void a(android.os.Parcelable parcelable, java.lang.ClassLoader classLoader) {
    }

    @java.lang.Deprecated
    public void a(android.view.View view) {
    }

    @java.lang.Deprecated
    public void a(android.view.View view, int i, java.lang.Object obj) {
    }

    public void a(android.view.ViewGroup viewGroup) {
        a((android.view.View) viewGroup);
    }

    public abstract void a(android.view.ViewGroup viewGroup, int i, java.lang.Object obj);

    public abstract boolean a(android.view.View view, java.lang.Object obj);

    public android.os.Parcelable b() {
        return null;
    }

    @java.lang.Deprecated
    public void b(android.view.View view) {
    }

    public void b(android.view.ViewGroup viewGroup) {
        b((android.view.View) viewGroup);
    }

    public void b(android.view.ViewGroup viewGroup, int i, java.lang.Object obj) {
        a((android.view.View) viewGroup, i, obj);
    }
}
