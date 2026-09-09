package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class Ta extends android.app.Activity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f659a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.lang.String f660b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private java.lang.String f661c;

    public android.graphics.Bitmap a() {
        android.media.session.MediaController next;
        android.media.session.PlaybackState playbackState;
        android.graphics.Bitmap bitmap = null;
        this.f660b = null;
        this.f659a = false;
        try {
            if (me.hisn.mygesture.NotificationManagerService.f534b == null) {
                return null;
            }
            java.util.List<android.media.session.MediaController> activeSessions = ((android.media.session.MediaSessionManager) me.hisn.mygesture.NotificationManagerService.f534b.getSystemService("media_session")).getActiveSessions(new android.content.ComponentName(me.hisn.mygesture.NotificationManagerService.f534b.getPackageName(), me.hisn.mygesture.NotificationManagerService.f534b.getClass().getName()));
            if (activeSessions.size() == 0) {
                return null;
            }
            java.util.Iterator<android.media.session.MediaController> it = activeSessions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (next != null && (playbackState = next.getPlaybackState()) != null && 3 == playbackState.getState()) {
                    this.f659a = true;
                    break;
                }
            }
            if (next == null) {
                next = activeSessions.get(0);
            }
            this.f660b = next.getPackageName();
            android.media.MediaMetadata metadata = next.getMetadata();
            if (metadata != null) {
                this.f661c = metadata.getString("android.media.metadata.TITLE");
                bitmap = metadata.getBitmap("android.media.metadata.DISPLAY_ICON");
                if (bitmap == null && (bitmap = metadata.getBitmap("android.media.metadata.ART")) == null) {
                    bitmap = metadata.getBitmap("android.media.metadata.ALBUM_ART");
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return bitmap != null ? android.graphics.Bitmap.createScaledBitmap(bitmap, 100, 100, false) : bitmap;
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.graphics.Bitmap bitmapA = a();
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("album", bitmapA);
        intent.putExtra("playing", this.f659a);
        intent.putExtra("playing_pkg", this.f660b);
        intent.putExtra("track_title", this.f661c);
        setResult(-1, intent);
        finish();
    }
}
