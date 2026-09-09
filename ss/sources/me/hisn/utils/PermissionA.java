package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class PermissionA extends android.app.Activity implements android.view.View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f717a;

    private void a() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            android.content.Intent intent = new android.content.Intent("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS");
            intent.addFlags(268435456);
            try {
                startActivity(intent);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void b() {
        new me.hisn.utils.j0().a(this);
    }

    private void c() {
        int i;
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.tip_tv);
        findViewById(me.hisn.mygesture.R.id.cancel_btn).setOnClickListener(this);
        findViewById(me.hisn.mygesture.R.id.grant_btn).setOnClickListener(this);
        int i2 = this.f717a;
        if (i2 != 80) {
            i = i2 != 81 ? 0 : me.hisn.mygesture.R.string.need_disturb_permission;
        } else {
            i = me.hisn.mygesture.R.string.need_write_settings_permission_text;
        }
        if (i != 0) {
            textView.setText(i);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        int id = view.getId();
        if (id != me.hisn.mygesture.R.id.cancel_btn) {
            if (id != me.hisn.mygesture.R.id.grant_btn) {
                return;
            }
            int i = this.f717a;
            if (i == 80) {
                b();
            } else if (i == 81) {
                a();
            }
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_permission);
        this.f717a = getIntent().getIntExtra("31415", 80);
        c();
    }
}
