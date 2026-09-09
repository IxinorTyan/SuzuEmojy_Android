package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public abstract class r extends me.hisn.utils.g0 {
    @Override // me.hisn.utils.g0
    protected int a(int i, int i2) {
        return i2 - i;
    }

    @Override // me.hisn.utils.g0
    protected int a(android.view.View view) {
        return view.getPaddingTop();
    }

    @Override // me.hisn.utils.g0
    protected int a(android.view.View view, android.view.MotionEvent motionEvent) {
        return (int) motionEvent.getY();
    }

    @Override // me.hisn.utils.g0
    protected void a(android.view.View view, float f) {
        int height = view.getHeight();
        if (height == 0) {
            height = new me.hisn.utils.l().a(view.getContext(), d() ? 140.5f : 142.0f);
        }
        int i = (int) (height * (1.0f - f));
        if (i < 0) {
            i = 0;
        }
        if (i <= height) {
            height = i;
        }
        view.setPadding(0, height, 0, 0);
    }

    @Override // me.hisn.utils.g0
    protected int[] a() {
        return d() ? new int[]{me.hisn.mygesture.R.id.music_layout, me.hisn.mygesture.R.id.search_layout, me.hisn.mygesture.R.id.brightness_bar, me.hisn.mygesture.R.id.volume_bar, me.hisn.mygesture.R.id.auto_brightness_btn, me.hisn.mygesture.R.id.volume_off_btn, me.hisn.mygesture.R.id.panel_5, me.hisn.mygesture.R.id.panel_6, me.hisn.mygesture.R.id.button2, me.hisn.mygesture.R.id.button4} : new int[]{me.hisn.mygesture.R.id.music_layout, me.hisn.mygesture.R.id.search_layout, me.hisn.mygesture.R.id.brightness_bar, me.hisn.mygesture.R.id.volume_bar, me.hisn.mygesture.R.id.auto_brightness_btn, me.hisn.mygesture.R.id.volume_off_btn, me.hisn.mygesture.R.id.panel_5, me.hisn.mygesture.R.id.panel_6, me.hisn.mygesture.R.id.panel_1, me.hisn.mygesture.R.id.panel_2, me.hisn.mygesture.R.id.panel_3, me.hisn.mygesture.R.id.panel_4, me.hisn.mygesture.R.id.panel_7, me.hisn.mygesture.R.id.panel_8};
    }

    @Override // me.hisn.utils.g0
    protected int b() {
        return d() ? me.hisn.mygesture.R.layout.cc2_land : me.hisn.mygesture.R.layout.cc2;
    }

    @Override // me.hisn.utils.g0
    protected int b(android.view.View view) {
        return view.getHeight();
    }

    @Override // me.hisn.utils.g0
    protected int[] c() {
        return new int[]{me.hisn.mygesture.R.id.panel_1, me.hisn.mygesture.R.id.panel_2, me.hisn.mygesture.R.id.panel_3, me.hisn.mygesture.R.id.panel_4, me.hisn.mygesture.R.id.panel_5, me.hisn.mygesture.R.id.panel_6, me.hisn.mygesture.R.id.panel_7, me.hisn.mygesture.R.id.panel_8, me.hisn.mygesture.R.id.panel_9};
    }

    @Override // me.hisn.utils.g0
    protected boolean e() {
        return true;
    }
}
