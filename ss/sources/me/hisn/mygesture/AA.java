package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class AA extends android.app.Activity {
    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        if (me.hisn.mygesture.MAS.t()) {
            new me.hisn.utils.b0().a(getApplicationContext(), me.hisn.mygesture.R.string.running, 0);
        }
        finish();
    }
}
