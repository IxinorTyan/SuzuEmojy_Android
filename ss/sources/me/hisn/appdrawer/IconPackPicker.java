package me.hisn.appdrawer;

/* JADX INFO: loaded from: classes.dex */
public class IconPackPicker extends me.hisn.utils.e implements android.view.View.OnClickListener {
    private android.view.View a(java.lang.String str, java.lang.String str2, android.graphics.drawable.Drawable drawable) {
        android.view.View viewInflate = android.view.View.inflate(this, me.hisn.mygesture.R.layout.pkg_item, null);
        android.widget.ImageView imageView = (android.widget.ImageView) viewInflate.findViewById(me.hisn.mygesture.R.id.pkg_icon);
        android.widget.TextView textView = (android.widget.TextView) viewInflate.findViewById(me.hisn.mygesture.R.id.pkg_app_name);
        android.widget.RelativeLayout relativeLayout = (android.widget.RelativeLayout) viewInflate.findViewById(me.hisn.mygesture.R.id.list_item_layout);
        relativeLayout.setTag(str);
        relativeLayout.setOnClickListener(this);
        imageView.setImageDrawable(drawable);
        textView.setText(str2);
        return viewInflate;
    }

    private void i() {
        android.view.View viewA;
        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.icon_pack_layout);
        java.util.List<java.lang.String> listA = new c.a.a.b(getApplicationContext()).a(true);
        android.content.pm.PackageManager packageManager = getPackageManager();
        for (java.lang.String str : listA) {
            android.content.pm.ApplicationInfo applicationInfo = null;
            if ("none".equals(str)) {
                viewA = a(null, "None", packageManager.getDefaultActivityIcon());
            } else {
                try {
                    applicationInfo = packageManager.getApplicationInfo(str, 0);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (applicationInfo != null) {
                    viewA = a(str, ((java.lang.Object) applicationInfo.loadLabel(packageManager)) + "", applicationInfo.loadIcon(packageManager));
                }
            }
            linearLayout.addView(viewA);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        java.lang.String str;
        if (view.getTag() != null) {
            str = view.getTag() + "";
        } else {
            str = null;
        }
        me.hisn.mygesture.P.s.edit().putString("drawer_icon_pack", str).apply();
        finish();
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_icon_pack_picker);
        i();
    }
}
