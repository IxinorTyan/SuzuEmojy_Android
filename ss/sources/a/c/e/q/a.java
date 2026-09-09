package a.c.e.q;

/* JADX INFO: loaded from: classes.dex */
public final class a extends android.text.style.ClickableSpan {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final int f109a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final a.c.e.q.c f110b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final int f111c;

    public a(int i, a.c.e.q.c cVar, int i2) {
        this.f109a = i;
        this.f110b = cVar;
        this.f111c = i2;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(android.view.View view) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", this.f109a);
        this.f110b.a(this.f111c, bundle);
    }
}
