package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class Kp extends android.app.Activity {
    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
