package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class n {
    public void a(android.content.Context context) {
        android.hardware.camera2.CameraManager cameraManager = (android.hardware.camera2.CameraManager) context.getApplicationContext().getSystemService("camera");
        if (android.os.Build.VERSION.SDK_INT < 23 || cameraManager == null) {
            return;
        }
        try {
            try {
                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], !me.hisn.mygesture.P.j0);
                me.hisn.mygesture.P.j0 = me.hisn.mygesture.P.j0 ? false : true;
            } catch (android.hardware.camera2.CameraAccessException e) {
                e.printStackTrace();
            }
        } catch (java.lang.IllegalArgumentException e2) {
            e2.printStackTrace();
        }
    }
}
