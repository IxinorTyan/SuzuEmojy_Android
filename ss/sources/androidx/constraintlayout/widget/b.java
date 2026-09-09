package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public abstract class b extends android.view.View {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected int[] f148a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    protected int f149b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    protected android.content.Context f150c;
    protected a.b.a.j.j d;
    protected boolean e;
    private java.lang.String f;

    public b(android.content.Context context) {
        super(context);
        this.f148a = new int[32];
        this.e = false;
        this.f150c = context;
        a((android.util.AttributeSet) null);
    }

    private void a(java.lang.String str) {
        int iIntValue;
        java.lang.Object objA;
        if (str == null || this.f150c == null) {
            return;
        }
        java.lang.String strTrim = str.trim();
        try {
            iIntValue = androidx.constraintlayout.widget.g.class.getField(strTrim).getInt(null);
        } catch (java.lang.Exception unused) {
            iIntValue = 0;
        }
        if (iIntValue == 0) {
            iIntValue = this.f150c.getResources().getIdentifier(strTrim, "id", this.f150c.getPackageName());
        }
        if (iIntValue == 0 && isInEditMode() && (getParent() instanceof androidx.constraintlayout.widget.ConstraintLayout) && (objA = ((androidx.constraintlayout.widget.ConstraintLayout) getParent()).a(0, strTrim)) != null && (objA instanceof java.lang.Integer)) {
            iIntValue = ((java.lang.Integer) objA).intValue();
        }
        if (iIntValue != 0) {
            setTag(iIntValue, null);
            return;
        }
        android.util.Log.w("ConstraintHelper", "Could not find id of \"" + strTrim + "\"");
    }

    private void setIds(java.lang.String str) {
        if (str == null) {
            return;
        }
        int i = 0;
        while (true) {
            int iIndexOf = str.indexOf(44, i);
            if (iIndexOf == -1) {
                a(str.substring(i));
                return;
            } else {
                a(str.substring(i, iIndexOf));
                i = iIndexOf + 1;
            }
        }
    }

    public void a() {
        if (this.d == null) {
            return;
        }
        android.view.ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof androidx.constraintlayout.widget.ConstraintLayout.a) {
            ((androidx.constraintlayout.widget.ConstraintLayout.a) layoutParams).k0 = this.d;
        }
    }

    protected void a(android.util.AttributeSet attributeSet) {
        if (attributeSet != null) {
            android.content.res.TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, androidx.constraintlayout.widget.h.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == androidx.constraintlayout.widget.h.ConstraintLayout_Layout_constraint_referenced_ids) {
                    java.lang.String string = typedArrayObtainStyledAttributes.getString(index);
                    this.f = string;
                    setIds(string);
                }
            }
        }
    }

    public void a(androidx.constraintlayout.widget.ConstraintLayout constraintLayout) {
    }

    public void b(androidx.constraintlayout.widget.ConstraintLayout constraintLayout) {
    }

    public void c(androidx.constraintlayout.widget.ConstraintLayout constraintLayout) {
        if (isInEditMode()) {
            setIds(this.f);
        }
        a.b.a.j.j jVar = this.d;
        if (jVar == null) {
            return;
        }
        jVar.J();
        for (int i = 0; i < this.f149b; i++) {
            android.view.View viewA = constraintLayout.a(this.f148a[i]);
            if (viewA != null) {
                this.d.b(constraintLayout.a(viewA));
            }
        }
    }

    public int[] getReferencedIds() {
        return java.util.Arrays.copyOf(this.f148a, this.f149b);
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.e) {
            super.onMeasure(i, i2);
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    public void setReferencedIds(int[] iArr) {
        this.f149b = 0;
        for (int i : iArr) {
            setTag(i, null);
        }
    }

    @Override // android.view.View
    public void setTag(int i, java.lang.Object obj) {
        int i2 = this.f149b + 1;
        int[] iArr = this.f148a;
        if (i2 > iArr.length) {
            this.f148a = java.util.Arrays.copyOf(iArr, iArr.length * 2);
        }
        int[] iArr2 = this.f148a;
        int i3 = this.f149b;
        iArr2[i3] = i;
        this.f149b = i3 + 1;
    }
}
