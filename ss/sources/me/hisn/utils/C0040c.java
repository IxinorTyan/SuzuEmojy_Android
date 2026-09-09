package me.hisn.utils;

/* JADX INFO: renamed from: me.hisn.utils.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0040c {
    public android.graphics.Bitmap a(java.lang.String str) {
        android.media.session.MediaController next;
        android.media.MediaMetadata metadata;
        android.graphics.Bitmap bitmap = null;
        try {
            if (me.hisn.mygesture.NotificationManagerService.f534b == null) {
                return null;
            }
            android.content.ComponentName componentName = new android.content.ComponentName(me.hisn.mygesture.NotificationManagerService.f534b.getPackageName(), me.hisn.mygesture.NotificationManagerService.f534b.getClass().getName());
            android.media.session.MediaSessionManager mediaSessionManager = (android.media.session.MediaSessionManager) me.hisn.mygesture.NotificationManagerService.f534b.getSystemService("media_session");
            if (mediaSessionManager != null) {
                java.util.List<android.media.session.MediaController> activeSessions = mediaSessionManager.getActiveSessions(componentName);
                if (activeSessions.size() > 0) {
                    java.util.Iterator<android.media.session.MediaController> it = activeSessions.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (next != null) {
                            if (str != null && str.equals(next.getPackageName())) {
                                break;
                            }
                            try {
                                if (next.getPlaybackState().getState() == 3) {
                                    break;
                                }
                            } catch (java.lang.Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (next != null && (metadata = next.getMetadata()) != null) {
                        metadata.getString("android.media.metadata.TITLE");
                        bitmap = metadata.getBitmap("android.media.metadata.DISPLAY_ICON");
                        if (bitmap == null && (bitmap = metadata.getBitmap("android.media.metadata.ART")) == null) {
                            bitmap = metadata.getBitmap("android.media.metadata.ALBUM_ART");
                        }
                    }
                }
            }
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
        return bitmap != null ? android.graphics.Bitmap.createScaledBitmap(bitmap, 100, 100, false) : bitmap;
    }
}
