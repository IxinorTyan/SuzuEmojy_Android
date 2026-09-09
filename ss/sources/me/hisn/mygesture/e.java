package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public abstract class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public android.content.Context f575a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public android.view.WindowManager f576b;

    public e(android.content.Context context, android.view.WindowManager windowManager) {
        this.f575a = context;
        this.f576b = windowManager;
    }

    public abstract void a();

    public abstract void a(int i, int i2, int i3);

    public abstract void a(int i, int i2, int i3, int i4);

    public abstract void b();
}
