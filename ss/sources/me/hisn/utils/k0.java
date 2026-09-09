package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class k0 {
    public static int a(java.lang.String str, int i) {
        return me.hisn.mygesture.P.s.getInt(str, i);
    }

    public static java.lang.String a(java.lang.String str) {
        return me.hisn.mygesture.P.s.getString(str, null);
    }

    public static boolean a(java.lang.String str, boolean z) {
        return me.hisn.mygesture.P.s.getBoolean(str, z);
    }

    public static void b(java.lang.String str, int i) {
        me.hisn.mygesture.P.s.edit().putInt(str, i).apply();
    }

    public static void b(java.lang.String str, boolean z) {
        me.hisn.mygesture.P.s.edit().putBoolean(str, z).apply();
    }
}
