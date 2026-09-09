package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class PLA extends android.app.Activity {

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f645a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.graphics.Bitmap f646b;

        /* JADX INFO: renamed from: me.hisn.mypanel.PLA$a$a, reason: collision with other inner class name */
        class RunnableC0027a implements java.lang.Runnable {
            RunnableC0027a(me.hisn.mypanel.PLA.a aVar) {
            }

            @Override // java.lang.Runnable
            public void run() {
            }
        }

        a(int i, android.graphics.Bitmap bitmap) {
            this.f645a = i;
            this.f646b = bitmap;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.mypanel.c().a(this.f646b, me.hisn.mypanel.PLA.this.getExternalFilesDir(null) + "/" + this.f645a + ".png", new me.hisn.utils.l().a(me.hisn.mypanel.PLA.this.getApplicationContext(), 40.0f), true);
            me.hisn.mypanel.PLA.this.runOnUiThread(new me.hisn.mypanel.PLA.a.RunnableC0027a(this));
        }
    }

    private android.graphics.Bitmap a(android.net.Uri uri) {
        try {
            return android.graphics.BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private android.graphics.drawable.Drawable a(android.os.Bundle bundle) {
        android.content.pm.PackageManager packageManager = getPackageManager();
        android.content.Intent.ShortcutIconResource shortcutIconResource = (android.content.Intent.ShortcutIconResource) bundle.get("android.intent.extra.shortcut.ICON_RESOURCE");
        android.graphics.drawable.Drawable drawable = null;
        if (shortcutIconResource == null) {
            return null;
        }
        try {
            android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(shortcutIconResource.packageName);
            drawable = resourcesForApplication.getDrawable(resourcesForApplication.getIdentifier(shortcutIconResource.resourceName, "drawable", shortcutIconResource.packageName));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return drawable == null ? me.hisn.mypanel.f.a(this, shortcutIconResource.packageName) : drawable;
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        android.graphics.Bitmap bitmapA;
        int intExtra;
        android.graphics.drawable.Drawable drawable;
        android.graphics.drawable.Drawable activityIcon;
        me.hisn.mypanel.c cVar;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            int intExtra2 = intent.getIntExtra("31418", -1);
            if (intExtra2 != -1) {
                me.hisn.utils.h0.a(getApplicationContext(), intent, i + "", getSharedPreferences("my_panel", 0), false);
                bitmapA = null;
                if (intExtra2 == 1) {
                    android.os.Bundle bundleExtra = intent.getBundleExtra("31419");
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) bundleExtra.getParcelable("android.intent.extra.shortcut.ICON");
                    if (bitmap == null) {
                        activityIcon = a(bundleExtra);
                        cVar = new me.hisn.mypanel.c();
                        bitmapA = cVar.a(activityIcon);
                    } else {
                        bitmapA = bitmap;
                    }
                } else if (intExtra2 == 0) {
                    java.lang.String stringExtra = intent.getStringExtra("31421");
                    java.lang.String stringExtra2 = intent.getStringExtra("31422");
                    if (stringExtra == null || stringExtra2 == null) {
                        activityIcon = null;
                    } else {
                        try {
                            activityIcon = getPackageManager().getActivityIcon(new android.content.ComponentName(stringExtra, stringExtra2));
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            activityIcon = null;
                        }
                    }
                    if (activityIcon != null) {
                        cVar = new me.hisn.mypanel.c();
                        bitmapA = cVar.a(activityIcon);
                    }
                } else if (intExtra2 == 2 && (intExtra = intent.getIntExtra("31424", 0)) != 0 && (drawable = getDrawable(new me.hisn.utils.b(this).a(intExtra))) != null) {
                    bitmapA = new me.hisn.mypanel.c().a(drawable);
                }
            } else {
                bitmapA = a(intent.getData());
            }
            if (bitmapA != null) {
                new java.lang.Thread(new me.hisn.mypanel.PLA.a(i, bitmapA)).start();
            }
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        android.content.Intent intent;
        super.onCreate(bundle);
        int intExtra = getIntent().getIntExtra("31415", 1068078049);
        int intExtra2 = getIntent().getIntExtra("31416", 81);
        if (intExtra2 == 81) {
            intent = new android.content.Intent(this, (java.lang.Class<?>) me.hisn.mypanel.Aa.class);
            intent.putExtra("31415", 2);
        } else if (intExtra2 == 80) {
            intent = new android.content.Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
        } else {
            intent = null;
        }
        if (intent != null) {
            try {
                startActivityForResult(intent, intExtra);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }
}
