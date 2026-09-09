package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public class e extends android.view.View {
    public e(android.content.Context context) {
        super(context);
        super.setVisibility(8);
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    public void setGuidelineBegin(int i) {
        androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) getLayoutParams();
        aVar.f144a = i;
        setLayoutParams(aVar);
    }

    public void setGuidelineEnd(int i) {
        androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) getLayoutParams();
        aVar.f145b = i;
        setLayoutParams(aVar);
    }

    public void setGuidelinePercent(float f) {
        androidx.constraintlayout.widget.ConstraintLayout.a aVar = (androidx.constraintlayout.widget.ConstraintLayout.a) getLayoutParams();
        aVar.f146c = f;
        setLayoutParams(aVar);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
    }
}
