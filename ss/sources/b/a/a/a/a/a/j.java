package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    public static int a(int i, int i2, @org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.String str) {
        java.lang.String strA;
        if (i >= 0 && i < i2) {
            return i;
        }
        if (i < 0) {
            strA = b.a.a.a.a.a.k.a("%s (%s) must not be negative", "index", java.lang.Integer.valueOf(i));
        } else {
            if (i2 < 0) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder(26);
                sb.append("negative size: ");
                sb.append(i2);
                throw new java.lang.IllegalArgumentException(sb.toString());
            }
            strA = b.a.a.a.a.a.k.a("%s (%s) must be less than size (%s)", "index", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        throw new java.lang.IndexOutOfBoundsException(strA);
    }

    public static void a(int i, int i2, int i3) {
        java.lang.String strC;
        if (i < 0 || i2 < i || i2 > i3) {
            if (i < 0 || i > i3) {
                strC = c(i, i3, "start index");
            } else {
                strC = (i2 < 0 || i2 > i3) ? c(i2, i3, "end index") : b.a.a.a.a.a.k.a("end index (%s) must not be less than start index (%s)", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i));
            }
            throw new java.lang.IndexOutOfBoundsException(strC);
        }
    }

    public static int b(int i, int i2, @org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.String str) {
        if (i < 0 || i > i2) {
            throw new java.lang.IndexOutOfBoundsException(c(i, i2, "index"));
        }
        return i;
    }

    private static java.lang.String c(int i, int i2, @org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.String str) {
        if (i < 0) {
            return b.a.a.a.a.a.k.a("%s (%s) must not be negative", str, java.lang.Integer.valueOf(i));
        }
        if (i2 >= 0) {
            return b.a.a.a.a.a.k.a("%s (%s) must not be greater than size (%s)", str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(26);
        sb.append("negative size: ");
        sb.append(i2);
        throw new java.lang.IllegalArgumentException(sb.toString());
    }
}
