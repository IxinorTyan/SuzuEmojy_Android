package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
class n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    android.graphics.Paint f621a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f622b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    int f623c;

    n() {
        a();
    }

    private void a() {
        android.graphics.Paint paint = new android.graphics.Paint();
        this.f621a = paint;
        paint.setAntiAlias(true);
        this.f621a.setColor(me.hisn.mygesture.P.w == 3 ? me.hisn.mygesture.P.T : me.hisn.utils.k.a());
        this.f621a.setStyle(android.graphics.Paint.Style.STROKE);
        this.f621a.setStrokeWidth(this.f622b);
        this.f621a.setAlpha(255);
        this.f623c = 0;
        this.f622b = 0;
    }
}
