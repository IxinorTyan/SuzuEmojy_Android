package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class v {
    /* JADX WARN: Code duplicated, block: B:14:0x0027  */
    public android.view.WindowManager.LayoutParams a(boolean z, int i, int i2, int i3, int i4, int i5, int i6, boolean z2) {
        int i7;
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams();
        layoutParams.type = android.os.Build.VERSION.SDK_INT >= 22 ? 2032 : 2002;
        layoutParams.format = -3;
        if (z) {
            layoutParams.flags = 552;
            i7 = z2 ? 552 | 131072 : 536;
            if (i6 != 0) {
                layoutParams.windowAnimations = i6;
            }
            layoutParams.gravity = i;
            layoutParams.x = i4;
            layoutParams.y = i5;
            layoutParams.width = i2;
            layoutParams.height = i3;
            return layoutParams;
        }
        layoutParams.flags = i7;
        if (i6 != 0) {
            layoutParams.windowAnimations = i6;
        }
        layoutParams.gravity = i;
        layoutParams.x = i4;
        layoutParams.y = i5;
        layoutParams.width = i2;
        layoutParams.height = i3;
        return layoutParams;
    }
}
