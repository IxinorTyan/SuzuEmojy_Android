package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class n extends a.b.a.j.o {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    float f74c = 0.0f;

    public void a(int i) {
        if (this.f76b == 0 || this.f74c != i) {
            this.f74c = i;
            if (this.f76b == 1) {
                b();
            }
            a();
        }
    }

    @Override // a.b.a.j.o
    public void d() {
        super.d();
        this.f74c = 0.0f;
    }

    public void f() {
        this.f76b = 2;
    }
}
