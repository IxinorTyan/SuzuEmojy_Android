package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class BackupRestoreA extends android.app.Activity {

    class a extends me.hisn.utils.z {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f696b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, android.app.Activity activity) {
            super(context, str, str2, str3, str4, z, i);
            this.f696b = activity;
        }

        @Override // me.hisn.utils.z
        public void d() {
            android.content.Intent intent = new android.content.Intent(this.f696b.getApplicationContext(), (java.lang.Class<?>) me.hisn.utils.BackupRestoreA.class);
            intent.putExtra("31415", 81);
            this.f696b.startActivity(intent);
        }
    }

    class b extends me.hisn.utils.z {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.app.Activity f697b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, android.app.Activity activity) {
            super(context, str, str2, str3, str4, z, i);
            this.f697b = activity;
        }

        @Override // me.hisn.utils.z
        public void d() {
            android.content.Intent intent = new android.content.Intent(this.f697b.getApplicationContext(), (java.lang.Class<?>) me.hisn.utils.BackupRestoreA.class);
            intent.putExtra("31415", 80);
            this.f697b.startActivity(intent);
        }
    }

    class c implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.net.Uri f698a;

        class a implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ boolean f700a;

            a(boolean z) {
                this.f700a = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                new me.hisn.utils.b0().a(me.hisn.utils.BackupRestoreA.this.getApplicationContext(), this.f700a ? me.hisn.mygesture.R.string.restore_succeed : me.hisn.mygesture.R.string.restore_failed);
            }
        }

        c(android.net.Uri uri) {
            this.f698a = uri;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.utils.BackupRestoreA.this.runOnUiThread(new me.hisn.utils.BackupRestoreA.c.a(new me.hisn.utils.f().b(me.hisn.utils.BackupRestoreA.this.getApplicationContext(), this.f698a)));
        }
    }

    class d implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.net.Uri f702a;

        class a implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ boolean f704a;

            a(boolean z) {
                this.f704a = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                new me.hisn.utils.b0().a(me.hisn.utils.BackupRestoreA.this.getApplicationContext(), this.f704a ? me.hisn.mygesture.R.string.backup_succeed : me.hisn.mygesture.R.string.backup_failed);
            }
        }

        d(android.net.Uri uri) {
            this.f702a = uri;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.utils.BackupRestoreA.this.runOnUiThread(new me.hisn.utils.BackupRestoreA.d.a(new me.hisn.utils.f().a(me.hisn.utils.BackupRestoreA.this.getApplicationContext(), this.f702a)));
        }
    }

    public static void a(android.app.Activity activity) {
        new me.hisn.utils.BackupRestoreA.a(activity, null, activity.getString(me.hisn.mygesture.R.string.save_backup_file), activity.getString(android.R.string.ok), activity.getString(android.R.string.cancel), true, -2, activity);
    }

    private void a(android.app.Activity activity, int i) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyMMddHHmmss", java.util.Locale.CHINA);
        android.content.Intent intent = new android.content.Intent("android.intent.action.CREATE_DOCUMENT");
        intent.putExtra("android.intent.extra.TITLE", getString(me.hisn.mygesture.R.string.app_name) + simpleDateFormat.format(new java.util.Date()) + ".backup");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("application/*");
        activity.startActivityForResult(intent, i);
    }

    public static void b(android.app.Activity activity) {
        new me.hisn.utils.BackupRestoreA.b(activity, null, activity.getString(me.hisn.mygesture.R.string.pick_backup_file), activity.getString(android.R.string.ok), activity.getString(android.R.string.cancel), true, -2, activity);
    }

    private void b(android.app.Activity activity, int i) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        activity.startActivityForResult(intent, i);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        android.net.Uri data;
        java.lang.Thread thread;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && (data = intent.getData()) != null) {
            if (i == 80) {
                thread = new java.lang.Thread(new me.hisn.utils.BackupRestoreA.c(data));
            } else if (i == 81) {
                thread = new java.lang.Thread(new me.hisn.utils.BackupRestoreA.d(data));
            }
            thread.start();
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        setTheme(android.R.style.Theme.Material.Dialog.NoActionBar);
        super.onCreate(bundle);
        int intExtra = getIntent().getIntExtra("31415", 0);
        if (intExtra == 81) {
            a(this, 81);
        } else if (intExtra == 80) {
            b(this, 80);
        }
    }
}
