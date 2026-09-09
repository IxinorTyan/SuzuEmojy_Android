package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public class f extends android.view.View {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f158a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.view.View f159b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int f160c;

    public void a(androidx.constraintlayout.widget.ConstraintLayout constraintLayout) {
        if (this.f159b == null) {
            return;
        }
        androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) getLayoutParams();
        androidx.constraintlayout.widget.ConstraintLayout.a aVar2 = (androidx.constraintlayout.widget.ConstraintLayout.a) this.f159b.getLayoutParams();
        aVar2.k0.n(0);
        aVar.k0.o(aVar2.k0.s());
        aVar.k0.g(aVar2.k0.i());
        aVar2.k0.n(8);
    }

    public void b(androidx.constraintlayout.widget.ConstraintLayout constraintLayout) {
        if (this.f158a == -1 && !isInEditMode()) {
            setVisibility(this.f160c);
        }
        android.view.View viewFindViewById = constraintLayout.findViewById(this.f158a);
        this.f159b = viewFindViewById;
        if (viewFindViewById != null) {
            ((androidx.constraintlayout.widget.ConstraintLayout.a) viewFindViewById.getLayoutParams()).Z = true;
            this.f159b.setVisibility(0);
            setVisibility(0);
        }
    }

    public android.view.View getContent() {
        return this.f159b;
    }

    public int getEmptyVisibility() {
        return this.f160c;
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawRGB(223, 223, 223);
            android.graphics.Paint paint = new android.graphics.Paint();
            paint.setARGB(255, 210, 210, 210);
            paint.setTextAlign(android.graphics.Paint.Align.CENTER);
            paint.setTypeface(android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, 0));
            android.graphics.Rect rect = new android.graphics.Rect();
            canvas.getClipBounds(rect);
            paint.setTextSize(rect.height());
            int iHeight = rect.height();
            int iWidth = rect.width();
            paint.setTextAlign(android.graphics.Paint.Align.LEFT);
            paint.getTextBounds("?", 0, 1, rect);
            canvas.drawText("?", ((iWidth / 2.0f) - (rect.width() / 2.0f)) - rect.left, ((iHeight / 2.0f) + (rect.height() / 2.0f)) - rect.bottom, paint);
        }
    }

    public void setContentId(int i) {
        android.view.View viewFindViewById;
        if (this.f158a == i) {
            return;
        }
        android.view.View view = this.f159b;
        if (view != null) {
            view.setVisibility(0);
            ((androidx.constraintlayout.widget.ConstraintLayout.a) this.f159b.getLayoutParams()).Z = false;
            this.f159b = null;
        }
        this.f158a = i;
        if (i == -1 || (viewFindViewById = ((android.view.View) getParent()).findViewById(i)) == null) {
            return;
        }
        viewFindViewById.setVisibility(8);
    }

    public void setEmptyVisibility(int i) {
        this.f160c = i;
    }
}
