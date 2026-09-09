package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.view.View f693a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.view.WindowManager.LayoutParams f694b;

    private android.view.View a(android.content.Context context) {
        android.widget.ImageView imageView = new android.widget.ImageView(context);
        imageView.setImageResource(me.hisn.mygesture.R.drawable.ic_sniper);
        return imageView;
    }

    private android.view.WindowManager.LayoutParams a(int[] iArr) {
        return new me.hisn.utils.v().a(false, 8388659, 100, 100, iArr[0], java.lang.Math.max(iArr[1] - ((me.hisn.mygesture.P.l0 * 3) / 5), 0), me.hisn.mygesture.R.style.mouse_anim, false);
    }

    public void a(int i, int i2, int i3, int i4) {
        if (me.hisn.mygesture.MAS.k() != null) {
            android.view.WindowManager.LayoutParams layoutParams = this.f694b;
            int i5 = (int) (i + ((i3 - i) * 1.5f));
            layoutParams.x = i5;
            layoutParams.y = i4 - ((me.hisn.mygesture.P.l0 * 3) / 5);
            if (i5 < 0) {
                layoutParams.x = 0;
            }
            android.view.WindowManager.LayoutParams layoutParams2 = this.f694b;
            int i6 = layoutParams2.x;
            int i7 = me.hisn.mygesture.P.k0;
            if (i6 > i7 - 100) {
                layoutParams2.x = i7 - 100;
            }
            if (this.f694b.y < 0 - me.hisn.mygesture.MAS.j()) {
                this.f694b.y = 0 - me.hisn.mygesture.MAS.j();
            }
            android.view.WindowManager.LayoutParams layoutParams3 = this.f694b;
            int i8 = layoutParams3.y;
            int i9 = me.hisn.mygesture.P.l0;
            if (i8 > i9 - 100) {
                layoutParams3.y = i9 - 100;
            }
            me.hisn.mygesture.MAS.k().updateViewLayout(this.f693a, this.f694b);
        }
    }

    public void a(android.content.Context context, int[] iArr) {
        if (me.hisn.mygesture.MAS.k() != null) {
            this.f693a = a(context);
            this.f694b = a(iArr);
            try {
                me.hisn.mygesture.MAS.k().addView(this.f693a, this.f694b);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void a(boolean z) {
        if (z) {
            int[] iArr = new int[2];
            this.f693a.getLocationOnScreen(iArr);
            int[] iArr2 = new int[5];
            iArr2[0] = iArr[0] + 50;
            iArr2[1] = iArr[1] + 50;
            if (iArr2[0] > 0 && iArr2[0] < me.hisn.mygesture.P.k0 && iArr2[1] > 0 && iArr2[1] < me.hisn.mygesture.P.l0) {
                iArr2[2] = iArr2[0];
                iArr2[3] = iArr2[1];
                iArr2[4] = 50;
                new me.hisn.utils.d().a(this.f693a.getContext(), iArr2);
            }
        } else {
            ((android.widget.ImageView) this.f693a).setImageDrawable(null);
        }
        if (me.hisn.mygesture.MAS.k() != null) {
            me.hisn.mygesture.MAS.k().removeView(this.f693a);
        }
    }
}
