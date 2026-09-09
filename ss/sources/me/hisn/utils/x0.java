package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class x0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f881a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.media.AudioManager f882b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int f883c;
    private int d;
    private int e;

    public x0(android.content.Context context, int i, int i2) {
        a(context);
        this.f883c = i;
        this.d = i2;
        this.e = this.f882b.getStreamMaxVolume(i);
        this.f881a = a();
    }

    private int a() {
        return this.f882b.getStreamVolume(this.f883c);
    }

    private void a(int i) {
        b(this.f881a + i);
    }

    private void a(android.content.Context context) {
        if (this.f882b == null) {
            this.f882b = (android.media.AudioManager) context.getSystemService("audio");
        }
    }

    private int b() {
        return this.e;
    }

    private void b(int i) {
        if (i < 0) {
            i = 0;
        } else {
            int iB = b();
            if (i > iB) {
                i = iB;
            }
        }
        this.f882b.setStreamVolume(this.f883c, i, this.d);
    }

    public void a(int i, int i2, int i3, int i4, int i5) {
        a((int) (((((i == 3 || i == 4) ? i5 - i3 : i2 - i4) * 2.0f) / java.lang.Math.min(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0)) * this.e));
    }
}
