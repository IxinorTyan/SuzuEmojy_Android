package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class i {
    public android.graphics.Bitmap a(android.graphics.Bitmap bitmap, int i, boolean z, boolean z2) {
        if (!z) {
            bitmap = bitmap.copy(bitmap.getConfig(), true);
        }
        if (z2) {
            bitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.1f), (int) (bitmap.getHeight() * 0.1f), false);
        }
        if (i == 1) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        android.graphics.Bitmap bitmap2 = bitmap;
        bitmap2.getPixels(iArr, 0, width, 0, 0, width, height);
        me.hisn.utils.C.GBP(iArr, width, height, i);
        bitmap2.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public android.graphics.drawable.Drawable a(android.content.Context context, android.graphics.Bitmap bitmap, int i, boolean z, boolean z2) {
        android.graphics.Bitmap bitmapA = a(bitmap, i, z, z2);
        if (bitmapA != null) {
            return new android.graphics.drawable.BitmapDrawable(context.getResources(), bitmapA);
        }
        return null;
    }
}
