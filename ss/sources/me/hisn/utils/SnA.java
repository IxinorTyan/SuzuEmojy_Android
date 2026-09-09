package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class SnA extends android.app.Activity {
    private void a() {
        java.lang.String string = getIntent().getIntExtra("sg_int", 0) == new me.hisn.utils.n0().a(getApplicationContext()).hashCode() ? me.hisn.mygesture.P.s.getString("si", "") : "";
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("sn", string);
        setResult(-1, intent);
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        a();
        finish();
    }
}
