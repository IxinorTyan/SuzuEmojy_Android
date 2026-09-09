package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
class b {
    b() {
    }

    void a(android.content.Context context, java.lang.String str) {
        android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService("clipboard");
        android.content.ClipData clipDataNewPlainText = android.content.ClipData.newPlainText("crash", str);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipDataNewPlainText);
        }
    }
}
