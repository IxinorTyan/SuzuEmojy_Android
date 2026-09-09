package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class CapturePA extends android.app.Activity {
    private void a() {
        android.media.projection.MediaProjectionManager mediaProjectionManager = (android.media.projection.MediaProjectionManager) getApplicationContext().getSystemService("media_projection");
        if (mediaProjectionManager != null) {
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 80);
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 80) {
            me.hisn.mypanel.CaptureService.a(getApplicationContext(), intent);
        } else {
            me.hisn.mypanel.CaptureService.a(getApplicationContext(), (android.content.Intent) null);
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        overridePendingTransition(0, 0);
        super.onCreate(bundle);
        a();
    }
}
