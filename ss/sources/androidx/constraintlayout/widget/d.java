package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public class d extends android.view.ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    androidx.constraintlayout.widget.c f157a;

    public static class a extends androidx.constraintlayout.widget.ConstraintLayout.a {
        public float m0;
        public boolean n0;
        public float o0;
        public float p0;
        public float q0;
        public float r0;
        public float s0;
        public float t0;
        public float u0;
        public float v0;
        public float w0;
        public float x0;
        public float y0;

        public a(int i, int i2) {
            super(i, i2);
            this.m0 = 1.0f;
            this.n0 = false;
            this.o0 = 0.0f;
            this.p0 = 0.0f;
            this.q0 = 0.0f;
            this.r0 = 0.0f;
            this.s0 = 1.0f;
            this.t0 = 1.0f;
            this.u0 = 0.0f;
            this.v0 = 0.0f;
            this.w0 = 0.0f;
            this.x0 = 0.0f;
            this.y0 = 0.0f;
        }

        public a(android.content.Context context, android.util.AttributeSet attributeSet) {
            float f;
            super(context, attributeSet);
            this.m0 = 1.0f;
            this.n0 = false;
            this.o0 = 0.0f;
            this.p0 = 0.0f;
            this.q0 = 0.0f;
            this.r0 = 0.0f;
            this.s0 = 1.0f;
            this.t0 = 1.0f;
            this.u0 = 0.0f;
            this.v0 = 0.0f;
            this.w0 = 0.0f;
            this.x0 = 0.0f;
            this.y0 = 0.0f;
            android.content.res.TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.constraintlayout.widget.h.ConstraintSet);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_alpha) {
                    this.m0 = typedArrayObtainStyledAttributes.getFloat(index, this.m0);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_elevation) {
                    this.o0 = typedArrayObtainStyledAttributes.getFloat(index, this.o0);
                    this.n0 = true;
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_rotationX) {
                    this.q0 = typedArrayObtainStyledAttributes.getFloat(index, this.q0);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_rotationY) {
                    this.r0 = typedArrayObtainStyledAttributes.getFloat(index, this.r0);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_rotation) {
                    this.p0 = typedArrayObtainStyledAttributes.getFloat(index, this.p0);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_scaleX) {
                    this.s0 = typedArrayObtainStyledAttributes.getFloat(index, this.s0);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_scaleY) {
                    this.t0 = typedArrayObtainStyledAttributes.getFloat(index, this.t0);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_transformPivotX) {
                    this.u0 = typedArrayObtainStyledAttributes.getFloat(index, this.u0);
                } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_transformPivotY) {
                    this.v0 = typedArrayObtainStyledAttributes.getFloat(index, this.v0);
                } else {
                    if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_translationX) {
                        f = this.w0;
                    } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_translationY) {
                        this.x0 = typedArrayObtainStyledAttributes.getFloat(index, this.x0);
                    } else if (index == androidx.constraintlayout.widget.h.ConstraintSet_android_translationZ) {
                        f = this.y0;
                    }
                    this.w0 = typedArrayObtainStyledAttributes.getFloat(index, f);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public androidx.constraintlayout.widget.d.a generateDefaultLayoutParams() {
        return new androidx.constraintlayout.widget.d.a(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new androidx.constraintlayout.widget.ConstraintLayout.a(layoutParams);
    }

    @Override // android.view.ViewGroup
    public androidx.constraintlayout.widget.d.a generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new androidx.constraintlayout.widget.d.a(getContext(), attributeSet);
    }

    public androidx.constraintlayout.widget.c getConstraintSet() {
        if (this.f157a == null) {
            this.f157a = new androidx.constraintlayout.widget.c();
        }
        this.f157a.a(this);
        return this.f157a;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
