package a.c.b;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final java.lang.ThreadLocal<double[]> f83a = new java.lang.ThreadLocal<>();

    public static double a(int i) {
        double[] dArrA = a();
        a(i, dArrA);
        return dArrA[1] / 100.0d;
    }

    public static double a(int i, int i2) {
        if (android.graphics.Color.alpha(i2) != 255) {
            throw new java.lang.IllegalArgumentException("background can not be translucent: #" + java.lang.Integer.toHexString(i2));
        }
        if (android.graphics.Color.alpha(i) < 255) {
            i = c(i, i2);
        }
        double dA = a(i) + 0.05d;
        double dA2 = a(i2) + 0.05d;
        return java.lang.Math.max(dA, dA2) / java.lang.Math.min(dA, dA2);
    }

    private static float a(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        return f > f3 ? f3 : f;
    }

    public static int a(int i, int i2, float f) {
        int i3 = 255;
        if (android.graphics.Color.alpha(i2) != 255) {
            throw new java.lang.IllegalArgumentException("background can not be translucent: #" + java.lang.Integer.toHexString(i2));
        }
        double d = f;
        if (a(d(i, 255), i2) < d) {
            return -1;
        }
        int i4 = 0;
        for (int i5 = 0; i5 <= 10 && i3 - i4 > 1; i5++) {
            int i6 = (i4 + i3) / 2;
            if (a(d(i, i6), i2) < d) {
                i4 = i6;
            } else {
                i3 = i6;
            }
        }
        return i3;
    }

    private static int a(int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            return 0;
        }
        return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
    }

    public static void a(int i, int i2, int i3, double[] dArr) {
        if (dArr.length != 3) {
            throw new java.lang.IllegalArgumentException("outXyz must have a length of 3.");
        }
        double d = ((double) i) / 255.0d;
        double dPow = d < 0.04045d ? d / 12.92d : java.lang.Math.pow((d + 0.055d) / 1.055d, 2.4d);
        double d2 = ((double) i2) / 255.0d;
        double dPow2 = d2 < 0.04045d ? d2 / 12.92d : java.lang.Math.pow((d2 + 0.055d) / 1.055d, 2.4d);
        double d3 = ((double) i3) / 255.0d;
        double dPow3 = d3 < 0.04045d ? d3 / 12.92d : java.lang.Math.pow((d3 + 0.055d) / 1.055d, 2.4d);
        dArr[0] = ((0.4124d * dPow) + (0.3576d * dPow2) + (0.1805d * dPow3)) * 100.0d;
        dArr[1] = ((0.2126d * dPow) + (0.7152d * dPow2) + (0.0722d * dPow3)) * 100.0d;
        dArr[2] = ((dPow * 0.0193d) + (dPow2 * 0.1192d) + (dPow3 * 0.9505d)) * 100.0d;
    }

    public static void a(int i, int i2, int i3, float[] fArr) {
        float f;
        float fAbs;
        float f2 = i / 255.0f;
        float f3 = i2 / 255.0f;
        float f4 = i3 / 255.0f;
        float fMax = java.lang.Math.max(f2, java.lang.Math.max(f3, f4));
        float fMin = java.lang.Math.min(f2, java.lang.Math.min(f3, f4));
        float f5 = fMax - fMin;
        float f6 = (fMax + fMin) / 2.0f;
        if (fMax == fMin) {
            f = 0.0f;
            fAbs = 0.0f;
        } else {
            if (fMax == f2) {
                f = ((f3 - f4) / f5) % 6.0f;
            } else {
                f = fMax == f3 ? ((f4 - f2) / f5) + 2.0f : 4.0f + ((f2 - f3) / f5);
            }
            fAbs = f5 / (1.0f - java.lang.Math.abs((2.0f * f6) - 1.0f));
        }
        float f7 = (f * 60.0f) % 360.0f;
        if (f7 < 0.0f) {
            f7 += 360.0f;
        }
        fArr[0] = a(f7, 0.0f, 360.0f);
        fArr[1] = a(fAbs, 0.0f, 1.0f);
        fArr[2] = a(f6, 0.0f, 1.0f);
    }

    public static void a(int i, double[] dArr) {
        a(android.graphics.Color.red(i), android.graphics.Color.green(i), android.graphics.Color.blue(i), dArr);
    }

    public static void a(int i, float[] fArr) {
        a(android.graphics.Color.red(i), android.graphics.Color.green(i), android.graphics.Color.blue(i), fArr);
    }

    private static double[] a() {
        double[] dArr = f83a.get();
        if (dArr != null) {
            return dArr;
        }
        double[] dArr2 = new double[3];
        f83a.set(dArr2);
        return dArr2;
    }

    private static int b(int i, int i2) {
        return 255 - (((255 - i2) * (255 - i)) / 255);
    }

    public static int c(int i, int i2) {
        int iAlpha = android.graphics.Color.alpha(i2);
        int iAlpha2 = android.graphics.Color.alpha(i);
        int iB = b(iAlpha2, iAlpha);
        return android.graphics.Color.argb(iB, a(android.graphics.Color.red(i), iAlpha2, android.graphics.Color.red(i2), iAlpha, iB), a(android.graphics.Color.green(i), iAlpha2, android.graphics.Color.green(i2), iAlpha, iB), a(android.graphics.Color.blue(i), iAlpha2, android.graphics.Color.blue(i2), iAlpha, iB));
    }

    public static int d(int i, int i2) {
        if (i2 < 0 || i2 > 255) {
            throw new java.lang.IllegalArgumentException("alpha must be between 0 and 255.");
        }
        return (i & 16777215) | (i2 << 24);
    }
}
