package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public class a extends androidx.constraintlayout.widget.b {
    private int g;
    private int h;
    private a.b.a.j.b i;

    public a(android.content.Context context) {
        super(context);
        super.setVisibility(8);
    }

    @Override // androidx.constraintlayout.widget.b
    protected void a(android.util.AttributeSet attributeSet) {
        super.a(attributeSet);
        this.i = new a.b.a.j.b();
        if (attributeSet != null) {
            android.content.res.TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, androidx.constraintlayout.widget.h.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_barrierDirection) {
                    setType(typedArrayObtainStyledAttributes.getInt(index, 0));
                } else if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_barrierAllowsGoneWidgets) {
                    this.i.c(typedArrayObtainStyledAttributes.getBoolean(index, true));
                }
            }
        }
        this.d = this.i;
        a();
    }

    public int getType() {
        return this.g;
    }

    public void setAllowsGoneWidget(boolean z) {
        this.i.c(z);
    }

    /* JADX WARN: Code duplicated, block: B:5:0x0010  */
    /* JADX WARN: Code duplicated, block: B:7:0x0015  */
    public void setType(int i) {
        this.g = i;
        this.h = i;
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            if (1 == getResources().getConfiguration().getLayoutDirection()) {
                int i2 = this.g;
                if (i2 == 5) {
                    this.h = 1;
                } else if (i2 == 6) {
                    this.h = 0;
                }
            } else {
                int i3 = this.g;
                if (i3 == 5) {
                    this.h = 0;
                } else if (i3 == 6) {
                    this.h = 1;
                }
            }
        } else if (i == 5) {
            this.h = 0;
        } else if (i == 6) {
            this.h = 1;
        }
        this.i.t(this.h);
    }
}
