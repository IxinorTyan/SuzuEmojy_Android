package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class c {
    public android.graphics.Bitmap a() {
        int i = android.os.Build.VERSION.SDK_INT < 26 ? 2 : 3;
        if (android.os.Build.VERSION.SDK_INT < 23) {
            i = 1;
        }
        int iRandom = (int) (java.lang.Math.random() * java.lang.Math.random() * 10000.0d * ((double) i));
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(iRandom, iRandom, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas();
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setColor(-16776961);
        canvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
        return bitmapCreateBitmap;
    }

    public android.graphics.Bitmap a(android.graphics.Bitmap bitmap, android.graphics.Bitmap bitmap2) {
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmap);
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setAntiAlias(true);
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        float width = bitmap.getWidth() / bitmap2.getWidth();
        matrix.setScale(width, width);
        canvas.drawBitmap(bitmap2, matrix, paint);
        return bitmap;
    }

    public android.graphics.Bitmap a(android.graphics.drawable.Drawable drawable) {
        if (drawable == null || drawable.getIntrinsicWidth() == 0) {
            return null;
        }
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public android.graphics.Bitmap a(android.view.View view) {
        android.graphics.Bitmap bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(view.getWidth(), view.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmapCreateBitmap);
        android.graphics.drawable.Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        }
        view.draw(canvas);
        return bitmapCreateBitmap;
    }

    public android.graphics.Bitmap a(java.lang.String str) {
        if (str == null) {
            return null;
        }
        byte[] bArrDecode = android.util.Base64.decode(str, 0);
        return android.graphics.BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
    }

    public java.lang.String a(android.content.ContentResolver contentResolver, android.graphics.Bitmap bitmap, java.lang.String str, java.lang.String str2) {
        android.net.Uri uriInsert;
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("title", str);
        contentValues.put("description", str2);
        contentValues.put("datetaken", java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
        contentValues.put("mime_type", "image/jpeg");
        try {
            uriInsert = contentResolver.insert(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                if (bitmap != null) {
                    java.io.OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                    try {
                        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, outputStreamOpenOutputStream);
                        outputStreamOpenOutputStream.close();
                    } catch (java.lang.Throwable th) {
                        outputStreamOpenOutputStream.close();
                        throw th;
                    }
                } else {
                    contentResolver.delete(uriInsert, null, null);
                    uriInsert = null;
                }
            } catch (java.lang.Exception unused) {
                if (uriInsert != null) {
                    contentResolver.delete(uriInsert, null, null);
                    uriInsert = null;
                }
            }
        } catch (java.lang.Exception unused2) {
            uriInsert = null;
        }
        if (uriInsert != null) {
            return uriInsert.toString();
        }
        return null;
    }

    public java.lang.String a(android.graphics.Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return android.util.Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
    }

    public void a(android.graphics.Bitmap bitmap, java.lang.String str, int i, boolean z) {
        int i2;
        int i3;
        if (z) {
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int iMin = java.lang.Math.min(width, height);
            if (iMin >= i) {
                float f = i / iMin;
                matrix.postScale(f, f);
                if (height > width) {
                    i3 = (height - width) / 2;
                    i2 = 0;
                } else {
                    i2 = (width - height) / 2;
                    i3 = 0;
                }
                bitmap = android.graphics.Bitmap.createBitmap(bitmap, i2, i3, width - (i2 * 2), height - (i3 * 2), matrix, true);
            }
        }
        java.io.File file = new java.io.File(str);
        if (file.exists()) {
            file.delete();
        }
        try {
            java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(file));
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
