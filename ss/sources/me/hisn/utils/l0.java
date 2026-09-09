package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class l0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static java.util.ArrayList<me.hisn.mygesture.h> f803a;

    /* JADX WARN: Code duplicated, block: B:27:0x0053 A[Catch: all -> 0x00f1, TryCatch #0 {, blocks: (B:6:0x0006, B:9:0x0010, B:11:0x0014, B:12:0x0017, B:16:0x0021, B:20:0x0030, B:22:0x0044, B:27:0x0053, B:30:0x0061, B:32:0x0069, B:34:0x0079, B:37:0x0082, B:42:0x0093, B:43:0x00ae, B:45:0x00c3, B:46:0x00da, B:48:0x00e9, B:40:0x008e, B:25:0x004e), top: B:57:0x0006, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0060  */
    /* JADX WARN: Code duplicated, block: B:32:0x0069 A[Catch: all -> 0x00f1, TryCatch #0 {, blocks: (B:6:0x0006, B:9:0x0010, B:11:0x0014, B:12:0x0017, B:16:0x0021, B:20:0x0030, B:22:0x0044, B:27:0x0053, B:30:0x0061, B:32:0x0069, B:34:0x0079, B:37:0x0082, B:42:0x0093, B:43:0x00ae, B:45:0x00c3, B:46:0x00da, B:48:0x00e9, B:40:0x008e, B:25:0x004e), top: B:57:0x0006, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x007f A[LOOP:0: B:30:0x0061->B:36:0x007f, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:42:0x0093 A[Catch: all -> 0x00f1, TryCatch #0 {, blocks: (B:6:0x0006, B:9:0x0010, B:11:0x0014, B:12:0x0017, B:16:0x0021, B:20:0x0030, B:22:0x0044, B:27:0x0053, B:30:0x0061, B:32:0x0069, B:34:0x0079, B:37:0x0082, B:42:0x0093, B:43:0x00ae, B:45:0x00c3, B:46:0x00da, B:48:0x00e9, B:40:0x008e, B:25:0x004e), top: B:57:0x0006, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x00c3 A[Catch: all -> 0x00f1, TryCatch #0 {, blocks: (B:6:0x0006, B:9:0x0010, B:11:0x0014, B:12:0x0017, B:16:0x0021, B:20:0x0030, B:22:0x0044, B:27:0x0053, B:30:0x0061, B:32:0x0069, B:34:0x0079, B:37:0x0082, B:42:0x0093, B:43:0x00ae, B:45:0x00c3, B:46:0x00da, B:48:0x00e9, B:40:0x008e, B:25:0x004e), top: B:57:0x0006, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x00e9 A[Catch: all -> 0x00f1, TRY_LEAVE, TryCatch #0 {, blocks: (B:6:0x0006, B:9:0x0010, B:11:0x0014, B:12:0x0017, B:16:0x0021, B:20:0x0030, B:22:0x0044, B:27:0x0053, B:30:0x0061, B:32:0x0069, B:34:0x0079, B:37:0x0082, B:42:0x0093, B:43:0x00ae, B:45:0x00c3, B:46:0x00da, B:48:0x00e9, B:40:0x008e, B:25:0x004e), top: B:57:0x0006, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x0079 A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:42:0x0093, please report this as an issue */
    public static synchronized int a(android.content.Context context, java.lang.String str) {
        me.hisn.mygesture.h hVar;
        int i;
        int i2 = 0;
        if (str != null) {
            if (!"".equals(str)) {
                if (me.hisn.mygesture.P.g0 == null) {
                    a(context);
                }
                if (me.hisn.mygesture.P.g0.contains(str)) {
                    return 0;
                }
                b();
                if (context.getPackageManager().getLaunchIntentForPackage(str) == null) {
                    return 0;
                }
                android.content.pm.ApplicationInfo applicationInfo = null;
                android.graphics.drawable.Drawable drawableA = new c.a.a.a(context, me.hisn.mygesture.P.s.getString("drawer_icon_pack", null)).a(str, null, null);
                if (drawableA != null) {
                    if (drawableA != null) {
                        hVar = new me.hisn.mygesture.h(drawableA, str);
                        if (f803a.size() > 0) {
                            for (i = 0; i < f803a.size(); i++) {
                                if (f803a.get(i).f587a.equals(str)) {
                                    f803a.remove(i);
                                    break;
                                }
                            }
                        }
                        applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
                        if (applicationInfo != null) {
                            hVar.f588b = ((java.lang.Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
                        }
                        int[] iArrA = me.hisn.utils.k.a(hVar.f589c);
                        hVar.d = iArrA[0];
                        hVar.e = iArrA[1];
                        int i3 = iArrA[0];
                        if (me.hisn.mygesture.P.i0) {
                            me.hisn.utils.x xVar = new me.hisn.utils.x();
                            int intrinsicWidth = hVar.f589c.getIntrinsicWidth();
                            xVar.a(context, 0, intrinsicWidth, intrinsicWidth);
                            hVar.f589c = xVar.a(context, hVar.f589c, -1);
                        }
                        f803a.add(hVar);
                        if (f803a.size() > 8) {
                            f803a.remove(0);
                        }
                        i2 = i3;
                    }
                    return i2;
                }
                try {
                    drawableA = context.getPackageManager().getApplicationIcon(str);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (drawableA != null) {
                    hVar = new me.hisn.mygesture.h(drawableA, str);
                    if (f803a.size() > 0) {
                        while (i < f803a.size()) {
                            if (f803a.get(i).f587a.equals(str)) {
                                f803a.remove(i);
                                break;
                            }
                        }
                    }
                    try {
                        applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                        e2.printStackTrace();
                    }
                    if (applicationInfo != null) {
                        hVar.f588b = ((java.lang.Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
                    }
                    int[] iArrA2 = me.hisn.utils.k.a(hVar.f589c);
                    hVar.d = iArrA2[0];
                    hVar.e = iArrA2[1];
                    int i4 = iArrA2[0];
                    if (me.hisn.mygesture.P.i0) {
                        me.hisn.utils.x xVar2 = new me.hisn.utils.x();
                        int intrinsicWidth2 = hVar.f589c.getIntrinsicWidth();
                        xVar2.a(context, 0, intrinsicWidth2, intrinsicWidth2);
                        hVar.f589c = xVar2.a(context, hVar.f589c, -1);
                    }
                    f803a.add(hVar);
                    if (f803a.size() > 8) {
                        f803a.remove(0);
                    }
                    i2 = i4;
                }
                return i2;
                throw th;
            }
        }
        return 0;
    }

    public static java.util.ArrayList<me.hisn.mygesture.h> a() {
        return f803a;
    }

    public static me.hisn.mygesture.h a(java.lang.String str) {
        java.util.ArrayList<me.hisn.mygesture.h> arrayList = f803a;
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        for (int size = f803a.size() - 1; size >= 0; size--) {
            me.hisn.mygesture.h hVar = f803a.get(size);
            java.lang.String str2 = hVar.f587a;
            if (str2 != null && !str2.equals(str)) {
                return hVar;
            }
        }
        return null;
    }

    public static void a(android.content.Context context) {
        if (me.hisn.mygesture.P.g0 == null) {
            java.lang.String string = me.hisn.mygesture.P.s.getString("switch_black", null);
            me.hisn.mygesture.P.g0 = string;
            if (string == null) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("com.android.systemui&");
                android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                java.util.Iterator<android.content.pm.ResolveInfo> it = context.getPackageManager().queryIntentActivities(intent, 0).iterator();
                while (it.hasNext()) {
                    sb.append(it.next().activityInfo.packageName);
                    sb.append("&");
                }
                me.hisn.mygesture.P.g0 = sb.toString();
                me.hisn.mygesture.P.s.edit().putString("switch_black", me.hisn.mygesture.P.g0).apply();
            }
        }
    }

    public static android.content.Intent b(android.content.Context context, java.lang.String str) {
        me.hisn.mygesture.h hVarA = a(str);
        if (hVarA != null) {
            return me.hisn.utils.t.a(context, hVarA.f587a);
        }
        return null;
    }

    private static void b() {
        if (f803a == null) {
            f803a = new java.util.ArrayList<>();
        }
    }

    public static void b(java.lang.String str) {
        java.util.ArrayList<me.hisn.mygesture.h> arrayList = f803a;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        for (me.hisn.mygesture.h hVar : new java.util.ArrayList(f803a)) {
            if (str.contains(hVar.f587a)) {
                f803a.remove(hVar);
            }
        }
    }
}
