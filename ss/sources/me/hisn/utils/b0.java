package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class b0 {
    private void b(android.content.Context context, java.lang.String str, int i) {
        if (str != null) {
            android.widget.Toast.makeText(context, str, i).show();
        }
    }

    public void a(android.content.Context context, int i) {
        a(context, i, 0);
    }

    public void a(android.content.Context context, int i, int i2) {
        b(context, context.getString(i), i2);
    }

    public void a(android.content.Context context, java.lang.String str, int i) {
        b(context, str, i);
    }
}
