package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public final class k {
    public static java.lang.String a(@javax.annotation.CheckForNull java.lang.String str, @javax.annotation.CheckForNull java.lang.Object... objArr) {
        int length;
        int length2;
        int iIndexOf;
        java.lang.String string;
        int i = 0;
        int i2 = 0;
        while (true) {
            length = objArr.length;
            if (i2 >= length) {
                break;
            }
            java.lang.Object obj = objArr[i2];
            if (obj == null) {
                string = "null";
            } else {
                try {
                    string = obj.toString();
                } catch (java.lang.Exception e) {
                    java.lang.String name = obj.getClass().getName();
                    java.lang.String hexString = java.lang.Integer.toHexString(java.lang.System.identityHashCode(obj));
                    java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(name).length() + 1 + java.lang.String.valueOf(hexString).length());
                    sb.append(name);
                    sb.append('@');
                    sb.append(hexString);
                    java.lang.String string2 = sb.toString();
                    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("com.google.common.base.Strings");
                    java.util.logging.Level level = java.util.logging.Level.WARNING;
                    java.lang.String strValueOf = java.lang.String.valueOf(string2);
                    logger.logp(level, "com.google.common.base.Strings", "lenientToString", strValueOf.length() != 0 ? "Exception during lenientFormat for ".concat(strValueOf) : new java.lang.String("Exception during lenientFormat for "), (java.lang.Throwable) e);
                    java.lang.String name2 = e.getClass().getName();
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder(java.lang.String.valueOf(string2).length() + 9 + java.lang.String.valueOf(name2).length());
                    sb2.append("<");
                    sb2.append(string2);
                    sb2.append(" threw ");
                    sb2.append(name2);
                    sb2.append(">");
                    string = sb2.toString();
                }
            }
            objArr[i2] = string;
            i2++;
        }
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder(str.length() + (length * 16));
        int i3 = 0;
        while (true) {
            length2 = objArr.length;
            if (i >= length2 || (iIndexOf = str.indexOf("%s", i3)) == -1) {
                break;
            }
            sb3.append((java.lang.CharSequence) str, i3, iIndexOf);
            sb3.append(objArr[i]);
            i3 = iIndexOf + 2;
            i++;
        }
        sb3.append((java.lang.CharSequence) str, i3, str.length());
        if (i < length2) {
            sb3.append(" [");
            sb3.append(objArr[i]);
            for (int i4 = i + 1; i4 < objArr.length; i4++) {
                sb3.append(", ");
                sb3.append(objArr[i4]);
            }
            sb3.append(']');
        }
        return sb3.toString();
    }
}
