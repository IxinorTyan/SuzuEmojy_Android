package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class h {
    public android.graphics.Bitmap a(android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        try {
            return ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        }
    }
}
