package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class y {
    public static java.lang.String a(android.content.Context context) {
        java.lang.String string = android.provider.Settings.Secure.getString(context.getContentResolver(), "media_button_receiver");
        if (android.text.TextUtils.isEmpty(string)) {
            return null;
        }
        int iIndexOf = string.indexOf("{");
        int iIndexOf2 = string.indexOf("}");
        if (iIndexOf2 < 0) {
            iIndexOf2 = string.length();
        }
        java.lang.String strSubstring = string.substring(iIndexOf + 1, iIndexOf2);
        int iIndexOf3 = strSubstring.indexOf("/");
        if (iIndexOf3 <= 0) {
            return null;
        }
        java.lang.String strSubstring2 = strSubstring.substring(0, iIndexOf3);
        if (context.getPackageManager().getLaunchIntentForPackage(strSubstring2) == null) {
            return null;
        }
        return strSubstring2;
    }

    public static boolean a(android.content.Context context, int i) {
        int i2;
        try {
            android.media.AudioManager audioManager = (android.media.AudioManager) context.getSystemService("audio");
            if (audioManager == null) {
                return false;
            }
            switch (i) {
                case 80:
                    i2 = 87;
                    break;
                case 81:
                    i2 = 85;
                    break;
                case 82:
                    i2 = 88;
                    break;
                default:
                    i2 = 0;
                    break;
            }
            if (i2 == 0) {
                return false;
            }
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(0, i2));
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(1, i2));
            return true;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
