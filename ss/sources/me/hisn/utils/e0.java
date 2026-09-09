package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class e0 {
    public static java.lang.Object a(java.lang.String str) {
        java.lang.Object object = null;
        if (str == null) {
            return null;
        }
        try {
            java.io.ObjectInputStream objectInputStream = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(android.util.Base64.decode(str, 0)));
            object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return object;
        }
    }

    public static java.lang.String a(java.lang.Object obj) {
        java.lang.String strEncodeToString = null;
        if (!(obj instanceof java.io.Serializable)) {
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            strEncodeToString = android.util.Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            objectOutputStream.close();
            return strEncodeToString;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return strEncodeToString;
        }
    }
}
