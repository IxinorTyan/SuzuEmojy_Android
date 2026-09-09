package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final int[] f801a = {-1762269, -1499549, -6543440, -10011977, -12627531, -11110404, -16537100, -16728876, -16738680, -14312668, -7617718, -3285959, -5317, -16121, -26624, -43230};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final int[] f802b = {15014947, 15277667, 10233776, 6765239, 4149685, 5666812, 240116, 48340, 38536, 2464548, 9159498, 13491257, 16771899, 16761095, 16750592, 16733986};

    public static int a() {
        return f801a[(int) (java.lang.Math.random() * ((double) f801a.length))];
    }

    private static int a(int i) {
        return android.graphics.Color.rgb((int) java.lang.Math.floor(((double) ((i >> 16) & 255)) * 0.9d), (int) java.lang.Math.floor(((double) ((i >> 8) & 255)) * 0.9d), (int) java.lang.Math.floor(((double) (i & 255)) * 0.9d));
    }

    public static int a(android.content.pm.PackageManager packageManager, java.lang.String str) {
        int i = 0;
        if (packageManager.getLaunchIntentForPackage(str) != null) {
            try {
                android.graphics.drawable.Drawable applicationIcon = packageManager.getApplicationIcon(str);
                android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
                android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
                applicationIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                applicationIcon.draw(canvas);
                int iA = a.e.a.b.a(bitmapCreateBitmap).a().a(0);
                if (iA == 0) {
                    try {
                        iA = android.graphics.Bitmap.createScaledBitmap(bitmapCreateBitmap, 2, 2, false).getPixel(0, 0);
                    } catch (java.lang.Exception e) {
                        e = e;
                        i = iA;
                        e.printStackTrace();
                    }
                }
                i = iA;
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        }
        return i != 0 ? a(i) : i;
    }

    public static int[] a(int i, int i2) {
        int iAlpha = android.graphics.Color.alpha(i);
        int iRed = android.graphics.Color.red(i);
        int iRed2 = android.graphics.Color.red(i2);
        int iGreen = android.graphics.Color.green(i);
        int iGreen2 = android.graphics.Color.green(i2);
        int iBlue = android.graphics.Color.blue(i);
        int i3 = (iRed2 - iRed) / 5;
        int i4 = (iGreen2 - iGreen) / 5;
        int iBlue2 = (android.graphics.Color.blue(i2) - iBlue) / 5;
        int i5 = iRed + i3;
        int i6 = iGreen + i4;
        int i7 = iBlue + iBlue2;
        int i8 = i5 + i3;
        int i9 = i6 + i4;
        int i10 = i7 + iBlue2;
        int i11 = i8 + i3;
        int i12 = i9 + i4;
        int i13 = i10 + iBlue2;
        return new int[]{i, android.graphics.Color.argb(iAlpha, i5, i6, i7), android.graphics.Color.argb(iAlpha, i8, i9, i10), android.graphics.Color.argb(iAlpha, i11, i12, i13), android.graphics.Color.argb(iAlpha, i11 + i3, i12 + i4, i13 + iBlue2)};
    }

    public static int[] a(android.graphics.drawable.Drawable drawable) {
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        int[] iArr = {a.e.a.b.a(bitmapCreateBitmap).a().a(0), 0};
        if (iArr[0] == 0) {
            iArr[0] = -1;
            iArr[1] = -12303292;
        } else {
            iArr[0] = a(iArr[0]);
            if (b(iArr[0])) {
                iArr[1] = -12303292;
            } else {
                iArr[1] = -1;
            }
        }
        return iArr;
    }

    public static int b() {
        return f802b[(int) (java.lang.Math.random() * ((double) f802b.length))];
    }

    public static boolean b(int i) {
        return 1.0d - ((((((double) android.graphics.Color.red(i)) * 0.299d) + (((double) android.graphics.Color.green(i)) * 0.587d)) + (((double) android.graphics.Color.blue(i)) * 0.114d)) / 255.0d) < 0.25d;
    }
}
