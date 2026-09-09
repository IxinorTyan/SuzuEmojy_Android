package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class h0 {

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f794a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.graphics.Bitmap f795b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ java.lang.String f796c;

        a(android.content.Context context, android.graphics.Bitmap bitmap, java.lang.String str) {
            this.f794a = context;
            this.f795b = bitmap;
            this.f796c = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.utils.h0.b(this.f794a, this.f795b, this.f796c);
        }
    }

    public static java.lang.String a(android.content.Context context, android.content.Intent intent, java.lang.String str, android.content.SharedPreferences sharedPreferences, boolean z) {
        java.lang.String stringExtra;
        java.lang.String stringExtra2;
        java.lang.String stringExtra3;
        android.graphics.Bitmap bitmapA;
        int intExtra;
        me.hisn.utils.x xVar;
        android.graphics.Bitmap bitmapA2;
        int i;
        java.lang.String str2 = null;
        if (intent == null) {
            return null;
        }
        int intExtra2 = intent.getIntExtra("31418", -1);
        if (intExtra2 == 1) {
            android.os.Bundle bundleExtra = intent.getBundleExtra("31419");
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("android.intent.extra.shortcut.INTENT", bundleExtra.getParcelable("android.intent.extra.shortcut.INTENT"));
            android.os.Parcel parcelObtain = android.os.Parcel.obtain();
            bundle.writeToParcel(parcelObtain, 0);
            byte[] bArrMarshall = parcelObtain.marshall();
            java.lang.String strEncodeToString = android.util.Base64.encodeToString(bArrMarshall, 0, bArrMarshall.length, 0);
            stringExtra2 = bundleExtra.getString("android.intent.extra.shortcut.NAME");
            bitmapA = z ? me.hisn.utils.o0.a(context, bundleExtra) : null;
            stringExtra3 = null;
            intExtra = 1068078050;
            str2 = strEncodeToString;
            stringExtra = null;
        } else if (intExtra2 == 0) {
            stringExtra = intent.getStringExtra("31421");
            stringExtra3 = intent.getStringExtra("31422");
            java.lang.String stringExtra4 = intent.getStringExtra("31423");
            bitmapA = z ? (android.graphics.Bitmap) intent.getParcelableExtra("31426") : null;
            stringExtra2 = stringExtra4;
            intExtra = 1068078049;
        } else if (intExtra2 == 2) {
            intExtra = intent.getIntExtra("31424", 0);
            stringExtra2 = intent.getStringExtra("31425");
            if (z) {
                bitmapA = (android.graphics.Bitmap) intent.getParcelableExtra("31426");
                stringExtra = null;
            } else {
                bitmapA = null;
                stringExtra = null;
            }
            stringExtra3 = stringExtra;
        } else {
            stringExtra = null;
            stringExtra2 = null;
            stringExtra3 = null;
            bitmapA = null;
            intExtra = 0;
        }
        sharedPreferences.edit().putString(str + "_s", str2).putInt(str + "_f", intExtra2).putInt(str + "_k", intExtra).putString(str + "_p", stringExtra).putString(str + "_c", stringExtra3).putString(str + "_l", stringExtra2).apply();
        if (bitmapA != null) {
            int dimension = (int) context.getResources().getDimension(me.hisn.mygesture.R.dimen.three_in_one_item_size);
            android.graphics.drawable.Drawable drawable = context.getDrawable(me.hisn.mygesture.R.drawable.circle_mask);
            if (intExtra2 == 0 || intExtra2 == 1) {
                xVar = new me.hisn.utils.x();
                bitmapA2 = new me.hisn.mypanel.c().a(drawable);
                i = 2;
            } else {
                if (drawable != null) {
                    int i2 = me.hisn.mygesture.R.color.green;
                    if (str.endsWith("31427")) {
                        i2 = me.hisn.mygesture.R.color.g_blue;
                    } else if (str.endsWith("31428")) {
                        i2 = me.hisn.mygesture.R.color.g_green;
                    } else if (str.endsWith("31429")) {
                        i2 = me.hisn.mygesture.R.color.g_yellow;
                    }
                    drawable.setColorFilter(context.getResources().getColor(i2), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                xVar = new me.hisn.utils.x();
                bitmapA2 = new me.hisn.mypanel.c().a(drawable);
                i = (-dimension) / 8;
            }
            android.graphics.Bitmap bitmapA3 = xVar.a(bitmapA, bitmapA2, dimension, dimension, i);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append((stringExtra2 + intExtra + stringExtra + stringExtra3 + str2).hashCode());
            sb.append("");
            new java.lang.Thread(new me.hisn.utils.h0.a(context, bitmapA3, sb.toString())).start();
        }
        return stringExtra2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(android.content.Context context, android.graphics.Bitmap bitmap, java.lang.String str) {
        java.io.File file = new java.io.File(context.getExternalFilesDir(null) + "/action_icons/" + str + ".png");
        if (file.exists()) {
            file.delete();
        } else {
            file.mkdirs();
        }
        new me.hisn.mypanel.c().a(bitmap, file.getAbsolutePath(), 0, false);
    }
}
