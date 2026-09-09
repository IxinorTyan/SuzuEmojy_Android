package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public abstract class w extends me.hisn.utils.g0 {
    @Override // me.hisn.utils.g0
    protected int a(int i, int i2) {
        return i - i2;
    }

    @Override // me.hisn.utils.g0
    protected int a(android.view.View view) {
        return view.getPaddingRight();
    }

    @Override // me.hisn.utils.g0
    protected int a(android.view.View view, android.view.MotionEvent motionEvent) {
        return (int) motionEvent.getX();
    }

    @Override // me.hisn.utils.g0
    protected void a(android.view.View view, float f) {
        int width = view.getWidth();
        if (width == 0) {
            width = new me.hisn.utils.l().a(view.getContext(), d() ? 193.45454f : 227.27272f);
        }
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        view.setPadding(0, 0, (int) (width * (1.0f - f)), 0);
    }

    @Override // me.hisn.utils.g0
    protected int[] a() {
        return new int[]{me.hisn.mygesture.R.id.music_layout, me.hisn.mygesture.R.id.search_layout, me.hisn.mygesture.R.id.brightness_bar, me.hisn.mygesture.R.id.volume_bar, me.hisn.mygesture.R.id.auto_brightness_btn, me.hisn.mygesture.R.id.volume_off_btn, me.hisn.mygesture.R.id.button2, me.hisn.mygesture.R.id.button4};
    }

    @Override // me.hisn.utils.g0
    protected int b() {
        return d() ? me.hisn.mygesture.R.layout.cc3_land : me.hisn.mygesture.R.layout.cc3;
    }

    @Override // me.hisn.utils.g0
    protected int b(android.view.View view) {
        return view.getWidth();
    }

    @Override // me.hisn.utils.g0
    protected int[] c() {
        return new int[]{me.hisn.mygesture.R.id.panel_1, me.hisn.mygesture.R.id.panel_2, me.hisn.mygesture.R.id.panel_3, me.hisn.mygesture.R.id.panel_4, me.hisn.mygesture.R.id.panel_5, me.hisn.mygesture.R.id.panel_6, me.hisn.mygesture.R.id.panel_7, me.hisn.mygesture.R.id.panel_8, me.hisn.mygesture.R.id.panel_9, me.hisn.mygesture.R.id.panel_10, me.hisn.mygesture.R.id.panel_11, me.hisn.mygesture.R.id.panel_12, me.hisn.mygesture.R.id.panel_13};
    }

    @Override // me.hisn.utils.g0
    protected boolean e() {
        return false;
    }
}
