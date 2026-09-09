package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class z0 {
    public android.os.Bundle a(int i, int i2, android.view.View view) {
        int i3;
        if (i == 2) {
            i3 = me.hisn.mygesture.P.F;
            if (i3 == 1) {
                return a(view.getContext().getApplicationContext(), me.hisn.mygesture.R.anim.windows_in, me.hisn.mygesture.R.anim.windows_out);
            }
            if (i3 <= 1) {
                return null;
            }
        } else if (i != 12) {
            switch (i) {
                case 1068078049:
                case 1068078050:
                    i3 = me.hisn.mygesture.P.G;
                    break;
                default:
                    return a(view);
            }
        } else {
            i3 = me.hisn.mygesture.P.H;
        }
        return b(i3, i2, view);
    }

    public android.os.Bundle a(android.content.Context context, int i, int i2) {
        return android.app.ActivityOptions.makeCustomAnimation(context, i, i2).toBundle();
    }

    public android.os.Bundle a(android.view.View view) {
        return android.app.ActivityOptions.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, view.getWidth(), view.getHeight()).toBundle();
    }

    public android.os.Bundle b(int i, int i2, android.view.View view) {
        int i3;
        if (i == 0) {
            return null;
        }
        if (i == 1) {
            return new me.hisn.utils.z0().a(view);
        }
        int i4 = 0;
        if (i2 == 1) {
            i4 = i == 2 ? me.hisn.mygesture.R.anim.prev_in : me.hisn.mygesture.R.anim.prev_in2;
            i3 = i == 2 ? me.hisn.mygesture.R.anim.prev_out : me.hisn.mygesture.R.anim.prev_out2;
        } else if (i2 == 2) {
            i4 = i == 2 ? me.hisn.mygesture.R.anim.prev_right_in : me.hisn.mygesture.R.anim.prev_right_in2;
            i3 = i == 2 ? me.hisn.mygesture.R.anim.prev_right_out : me.hisn.mygesture.R.anim.prev_right_out2;
        } else if (i2 == 3) {
            i4 = i == 2 ? me.hisn.mygesture.R.anim.up_in : me.hisn.mygesture.R.anim.up_in2;
            i3 = i == 2 ? me.hisn.mygesture.R.anim.up_out : me.hisn.mygesture.R.anim.up_out2;
        } else if (i2 != 4) {
            i3 = 0;
        } else {
            i4 = i == 2 ? me.hisn.mygesture.R.anim.down_in : me.hisn.mygesture.R.anim.down_in2;
            i3 = i == 2 ? me.hisn.mygesture.R.anim.down_out : me.hisn.mygesture.R.anim.down_out2;
        }
        return a(view.getContext().getApplicationContext(), i4, i3);
    }
}
