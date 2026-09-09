package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class WaveImageView extends android.widget.ImageView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.graphics.Paint f724a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private long f725b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private int[] f726c;
    private me.hisn.utils.WaveImageView.a d;

    public interface a {
        void a(android.content.Context context, int[] iArr);
    }

    public WaveImageView(android.content.Context context) {
        super(context);
        this.f724a = null;
        this.f725b = 0L;
        this.f726c = null;
        this.d = null;
        a(context);
    }

    public WaveImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f724a = null;
        this.f725b = 0L;
        this.f726c = null;
        this.d = null;
        a(context);
    }

    public WaveImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f724a = null;
        this.f725b = 0L;
        this.f726c = null;
        this.d = null;
        a(context);
    }

    private void a(android.content.Context context) {
        android.graphics.Paint paint = new android.graphics.Paint();
        this.f724a = paint;
        paint.setAntiAlias(true);
        this.f724a.setColor(858993459);
    }

    public int[] getPath() {
        return this.f726c;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        int[] iArr = this.f726c;
        if (iArr != null) {
            canvas.drawCircle(iArr[0], iArr[1], 50, this.f724a);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        me.hisn.utils.WaveImageView.a aVar;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.f726c = new int[5];
            this.f725b = java.lang.System.currentTimeMillis();
            int[] iArr = this.f726c;
            int x = (int) motionEvent.getX();
            iArr[0] = x;
            iArr[2] = x;
            int[] iArr2 = this.f726c;
            int y = (int) motionEvent.getY();
            iArr2[1] = y;
            iArr2[3] = y;
            if (motionEvent.getY() >= 100.0f) {
                invalidate();
            }
        } else if (action == 1) {
            this.f726c[4] = (int) (java.lang.System.currentTimeMillis() - this.f725b);
            me.hisn.utils.WaveImageView.a aVar2 = this.d;
            if (aVar2 != null) {
                aVar2.a(getContext(), this.f726c);
            }
        } else if (action == 2) {
            int x2 = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            if (x2 > 0 && y2 > 0) {
                int[] iArr3 = this.f726c;
                iArr3[2] = x2;
                iArr3[3] = y2;
            }
        } else if (action == 4 && (aVar = this.d) != null) {
            aVar.a(getContext(), null);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setOnUpListener(me.hisn.utils.WaveImageView.a aVar) {
        this.d = aVar;
    }
}
