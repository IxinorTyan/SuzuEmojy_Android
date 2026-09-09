package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: me.hisn.mypanel.a$a, reason: collision with other inner class name */
    class C0029a implements android.media.ImageReader.OnImageAvailableListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.media.ImageReader f662a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.hardware.display.VirtualDisplay f663b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.media.projection.MediaProjection f664c;

        /* JADX INFO: renamed from: me.hisn.mypanel.a$a$a, reason: collision with other inner class name */
        class RunnableC0030a implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.media.ImageReader f665a;

            RunnableC0030a(android.media.ImageReader imageReader) {
                this.f665a = imageReader;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    try {
                        me.hisn.mypanel.a.this.a(me.hisn.mypanel.a.this.a(this.f665a.acquireLatestImage()));
                    } catch (java.lang.Exception e) {
                        me.hisn.mypanel.a.this.a();
                        e.printStackTrace();
                    }
                } finally {
                    me.hisn.mypanel.a.C0029a.this.f663b.release();
                    me.hisn.mypanel.a.C0029a.this.f664c.stop();
                    this.f665a.close();
                }
            }
        }

        C0029a(android.media.ImageReader imageReader, android.hardware.display.VirtualDisplay virtualDisplay, android.media.projection.MediaProjection mediaProjection) {
            this.f662a = imageReader;
            this.f663b = virtualDisplay;
            this.f664c = mediaProjection;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(android.media.ImageReader imageReader) {
            this.f662a.setOnImageAvailableListener(null, null);
            new java.lang.Thread(new me.hisn.mypanel.a.C0029a.RunnableC0030a(imageReader)).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.graphics.Bitmap a(android.media.Image image) {
        android.graphics.Bitmap bitmapCreateBitmap = null;
        if (image != null) {
            try {
                try {
                    int width = image.getWidth();
                    int height = image.getHeight();
                    android.media.Image.Plane[] planes = image.getPlanes();
                    java.nio.ByteBuffer buffer = planes[0].getBuffer();
                    int pixelStride = planes[0].getPixelStride();
                    android.graphics.Bitmap bitmapCreateBitmap2 = android.graphics.Bitmap.createBitmap(((planes[0].getRowStride() - (pixelStride * width)) / pixelStride) + width, height, android.graphics.Bitmap.Config.ARGB_8888);
                    bitmapCreateBitmap2.copyPixelsFromBuffer(buffer);
                    bitmapCreateBitmap = android.graphics.Bitmap.createBitmap(bitmapCreateBitmap2, 0, 0, width, height);
                    buffer.clear();
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            } finally {
                image.close();
            }
        }
        return bitmapCreateBitmap;
    }

    public abstract void a();

    public synchronized void a(android.content.Context context, android.content.Intent intent, android.graphics.Point point) {
        android.media.projection.MediaProjectionManager mediaProjectionManager = (android.media.projection.MediaProjectionManager) context.getSystemService("media_projection");
        if (mediaProjectionManager == null) {
            a();
            return;
        }
        try {
            android.media.projection.MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(-1, intent);
            android.media.ImageReader imageReaderNewInstance = android.media.ImageReader.newInstance(point.x < me.hisn.mygesture.P.k0 ? point.x - 1 : point.x, point.y < me.hisn.mygesture.P.l0 ? point.y - 1 : point.y, 1, 1);
            android.hardware.display.VirtualDisplay virtualDisplayCreateVirtualDisplay = mediaProjection.createVirtualDisplay("mg_scr", point.x, point.y, android.content.res.Resources.getSystem().getDisplayMetrics().densityDpi, 16, imageReaderNewInstance.getSurface(), null, null);
            android.os.Looper.prepare();
            imageReaderNewInstance.setOnImageAvailableListener(new me.hisn.mypanel.a.C0029a(imageReaderNewInstance, virtualDisplayCreateVirtualDisplay, mediaProjection), null);
            android.os.Looper.loop();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void a(android.graphics.Bitmap bitmap);
}
