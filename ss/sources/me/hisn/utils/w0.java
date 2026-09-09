package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class w0 {
    public void a(android.content.Context context, int i) {
        android.widget.Toast toast = new android.widget.Toast(context.getApplicationContext());
        android.widget.ImageView imageView = new android.widget.ImageView(context.getApplicationContext());
        imageView.setImageResource(i);
        toast.setView(imageView);
        toast.setDuration(1);
        toast.setGravity(17, 0, 0);
        toast.show();
    }
}
