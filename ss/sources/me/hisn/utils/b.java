package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String[] f732a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int[] f733b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final android.content.Context f734c;

    public b(android.content.Context context) {
        this.f734c = context;
        b();
    }

    private void b() {
        this.f732a = this.f734c.getResources().getStringArray(me.hisn.mygesture.R.array.side_gesture);
        this.f733b = new int[]{0, me.hisn.mygesture.R.drawable.action_back, me.hisn.mygesture.R.drawable.action_home, me.hisn.mygesture.R.drawable.action_recent, me.hisn.mygesture.R.drawable.action_notification, me.hisn.mygesture.R.drawable.action_quick_setting, me.hisn.mygesture.R.drawable.action_power_menu, me.hisn.mygesture.R.drawable.action_split, me.hisn.mygesture.R.drawable.action_screen_lock, me.hisn.mygesture.R.drawable.ic_capture_btn, me.hisn.mygesture.R.drawable.action_close_gesture, me.hisn.mygesture.R.drawable.action_panel, me.hisn.mygesture.R.drawable.action_prev_app, me.hisn.mygesture.R.drawable.ic_music_prev, me.hisn.mygesture.R.drawable.ic_play, me.hisn.mygesture.R.drawable.ic_music_next, me.hisn.mygesture.R.drawable.ic_bluetooth_btn, me.hisn.mygesture.R.drawable.ic_wifi, me.hisn.mygesture.R.drawable.ic_notouch, me.hisn.mygesture.R.drawable.ic_capture_btn, me.hisn.mygesture.R.drawable.ic_light, me.hisn.mygesture.R.drawable.action_keyboard, me.hisn.mygesture.R.drawable.action_assistant, me.hisn.mygesture.R.drawable.ic_wechat_scan, me.hisn.mygesture.R.drawable.ic_pay_code, me.hisn.mygesture.R.drawable.ic_ali_scan, me.hisn.mygesture.R.drawable.ic_apps_drawer, me.hisn.mygesture.R.drawable.action_prev_app, me.hisn.mygesture.R.drawable.ic_network_panel7, me.hisn.mygesture.R.drawable.ic_volume_panel7, me.hisn.mygesture.R.drawable.ic_picture_in_picture_alt_black_24dp, me.hisn.mygesture.R.drawable.ic_eye, me.hisn.mygesture.R.drawable.ic_volume_up_black_24dp, me.hisn.mygesture.R.drawable.ic_brightness_7_black_24dp, me.hisn.mygesture.R.drawable.ic_keep_screen_on, me.hisn.mygesture.R.drawable.ic_sniper, me.hisn.mygesture.R.drawable.ic_swicther, me.hisn.mygesture.R.drawable.ic_four_in_one, me.hisn.mygesture.R.drawable.ic_swicther, me.hisn.mygesture.R.drawable.ic_three_in_one};
    }

    public static boolean b(int i) {
        return (i == 0 || i == 11 || i == 37 || i == 39 || i == 32 || i == 33) ? false : true;
    }

    public static boolean c(int i) {
        return (i == 32 || i == 33 || i == 36 || i == 39) ? false : true;
    }

    public static boolean d(int i) {
        return (i == 32 || i == 33 || i == 36 || i == 39) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0048  */
    private boolean e(int i) {
        boolean z = true;
        if (i == 8 || i == 9) {
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                z = false;
            }
        } else if (i != 20) {
            if (i != 35) {
                if (i != 28 && i != 29) {
                    return false;
                }
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    z = false;
                }
            } else if (android.os.Build.VERSION.SDK_INT >= 24 && new me.hisn.utils.d().c(this.f734c.getApplicationContext()) >= 7) {
                z = false;
            }
        } else if (android.os.Build.VERSION.SDK_INT >= 23) {
            z = false;
        }
        return z;
    }

    public static boolean f(int i) {
        if (i == 11 || i == 12 || i == 26) {
            return true;
        }
        switch (i) {
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                return true;
            default:
                return false;
        }
    }

    public int a(int i) {
        int[] iArr = this.f733b;
        if (i < iArr.length) {
            return iArr[i];
        }
        return -1;
    }

    public java.util.List<me.hisn.mypanel.f> a() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int length = this.f732a.length;
        for (int i = 0; i < length; i++) {
            if (!e(i)) {
                me.hisn.mypanel.f fVar = new me.hisn.mypanel.f();
                fVar.f = 2;
                fVar.f691b = this.f732a[i];
                int[] iArr = this.f733b;
                if (iArr[i] != 0) {
                    android.graphics.drawable.Drawable drawable = this.f734c.getDrawable(iArr[i]);
                    fVar.f690a = drawable;
                    if (android.os.Build.VERSION.SDK_INT >= 23 && drawable != null) {
                        drawable.setTint(this.f734c.getColor(me.hisn.mygesture.R.color.colorPrimary));
                    }
                }
                fVar.e = i;
                arrayList.add(fVar);
            }
        }
        return arrayList;
    }
}
