package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class j extends me.hisn.utils.e {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private static boolean f595c = false;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.widget.EditText f596a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.view.View f597b;

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f598a;

        /* JADX INFO: renamed from: me.hisn.mygesture.j$a$a, reason: collision with other inner class name */
        class C0025a extends me.hisn.utils.z {

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            final /* synthetic */ int f600b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C0025a(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, int i2) {
                super(context, str, str2, str3, str4, z, i);
                this.f600b = i2;
            }

            @Override // me.hisn.utils.z
            public void d() {
                android.net.Uri uri;
                android.content.ComponentName componentName;
                android.content.Intent intent = new android.content.Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.addFlags(64);
                intent.setPackage("com.tencent.mm");
                android.content.pm.PackageManager packageManager = me.hisn.mygesture.j.this.getPackageManager();
                boolean z = false;
                java.util.Iterator<android.content.pm.ResolveInfo> it = packageManager.queryIntentActivities(intent, 0).iterator();
                while (true) {
                    uri = null;
                    if (!it.hasNext()) {
                        componentName = null;
                        break;
                    }
                    android.content.pm.ResolveInfo next = it.next();
                    java.lang.String string = next.loadLabel(packageManager).toString();
                    if (string.contains("收藏") || string.contains("Favorites")) {
                        android.content.pm.ActivityInfo activityInfo = next.activityInfo;
                        componentName = new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
                        break;
                    }
                }
                if (componentName != null) {
                    me.hisn.mypanel.c cVar = new me.hisn.mypanel.c();
                    java.lang.String strA = cVar.a(me.hisn.mygesture.j.this.getContentResolver(), cVar.a(me.hisn.mygesture.j.a.this.f598a), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.app_name), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.backup_text));
                    if (strA != null) {
                        try {
                            uri = android.net.Uri.parse(strA);
                        } catch (java.lang.Exception e) {
                            e.printStackTrace();
                        }
                        if (uri != null) {
                            intent.setComponent(componentName);
                            intent.putExtra("android.intent.extra.STREAM", uri);
                            try {
                                me.hisn.mygesture.j.this.startActivityForResult(intent, 89);
                                z = true;
                            } catch (java.lang.Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
                if (!z) {
                    new me.hisn.utils.b0().a(me.hisn.mygesture.j.this.getApplicationContext(), me.hisn.mygesture.R.string.backup_key_failed_tip, 1);
                } else {
                    me.hisn.mygesture.P.s.edit().putInt("backup_key_tips_count", this.f600b + 1).apply();
                    boolean unused = me.hisn.mygesture.j.f595c = true;
                }
            }
        }

        a(android.view.View view) {
            this.f598a = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = me.hisn.mygesture.P.s.getInt("backup_key_tips_count", 0);
            if (i >= 2 || me.hisn.mygesture.j.f595c || !new me.hisn.utils.j0().a(me.hisn.mygesture.j.this, new java.lang.String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 86)) {
                return;
            }
            me.hisn.mygesture.j jVar = me.hisn.mygesture.j.this;
            new me.hisn.mygesture.j.a.C0025a(jVar, jVar.getString(me.hisn.mygesture.R.string.attention), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.backup_tip), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.backup_text), i > 0 ? me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.cancel_text) : null, false, -2, i);
        }
    }

    class b implements android.view.View.OnClickListener {
        b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            new me.hisn.utils.b0().a(me.hisn.mygesture.j.this.getApplicationContext(), me.hisn.mygesture.R.string.useless_text, 0);
        }
    }

    class c implements android.widget.AdapterView.OnItemSelectedListener {
        c(me.hisn.mygesture.j jVar) {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            java.lang.String str;
            switch (adapterView.getId()) {
                case me.hisn.mygesture.R.id.bottom_swipe_left_spinner /* 2131230794 */:
                    str = "b31415";
                    break;
                case me.hisn.mygesture.R.id.bottom_swipe_right_spinner /* 2131230795 */:
                    str = "b31416";
                    break;
                case me.hisn.mygesture.R.id.drawer_folder_bkg_spinner /* 2131230832 */:
                    str = "31430";
                    break;
                case me.hisn.mygesture.R.id.go_home_anim_spinner /* 2131230876 */:
                    me.hisn.mygesture.P.F = i;
                    str = "home_anim";
                    break;
                case me.hisn.mygesture.R.id.go_prev_app_anim_spinner /* 2131230877 */:
                    me.hisn.mygesture.P.H = i;
                    str = "prev_app_anim";
                    break;
                case me.hisn.mygesture.R.id.open_app_anim_spinner /* 2131230941 */:
                    me.hisn.mygesture.P.G = i;
                    str = "open_app_anim";
                    break;
                default:
                    str = null;
                    break;
            }
            if (str != null) {
                me.hisn.mygesture.P.s.edit().putInt(str, i).apply();
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
        }
    }

    class d implements android.view.View.OnLongClickListener {
        d() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            me.hisn.mygesture.j.this.j();
            return true;
        }
    }

    class e implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ boolean f604a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.TextView f605b;

        class a extends me.hisn.utils.z {

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            final /* synthetic */ me.hisn.utils.p0 f607b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, me.hisn.utils.p0 p0Var) {
                super(context, str, str2, str3, str4, z, i);
                this.f607b = p0Var;
            }

            @Override // me.hisn.utils.z
            public void d() {
                this.f607b.b(me.hisn.mygesture.j.this, 90);
            }
        }

        e(boolean z, android.widget.TextView textView) {
            this.f604a = z;
            this.f605b = textView;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.content.SharedPreferences.Editor editorEdit;
            boolean zIsChecked;
            java.lang.String str;
            android.content.SharedPreferences.Editor editorPutBoolean;
            me.hisn.utils.b0 b0Var;
            android.content.Context applicationContext;
            int i;
            android.content.SharedPreferences.Editor editorEdit2;
            boolean z;
            java.lang.String str2;
            me.hisn.mygesture.j jVar;
            java.lang.String string;
            int i2;
            android.content.SharedPreferences.Editor editorEdit3;
            boolean z2;
            java.lang.String str3;
            try {
                switch (view.getId()) {
                    case me.hisn.mygesture.R.id.app_drawer_blur_behind_switch /* 2131230763 */:
                        if (android.os.Build.VERSION.SDK_INT >= 31) {
                            me.hisn.mygesture.P.s.edit().putBoolean("d31424", ((android.widget.Switch) view).isChecked()).apply();
                        }
                        if (((android.widget.Switch) view).isChecked()) {
                            android.widget.Toast.makeText(me.hisn.mygesture.j.this.getApplicationContext(), me.hisn.mygesture.R.string.blur_android12, 0).show();
                        }
                        break;
                    case me.hisn.mygesture.R.id.app_drawer_long_click_switch /* 2131230764 */:
                        editorEdit = me.hisn.mygesture.P.s.edit();
                        zIsChecked = ((android.widget.Switch) view).isChecked();
                        str = "d31423";
                        editorPutBoolean = editorEdit.putBoolean(str, zIsChecked);
                        editorPutBoolean.apply();
                        break;
                    case me.hisn.mygesture.R.id.app_drawer_show_label_switch /* 2131230765 */:
                        zIsChecked = ((android.widget.Switch) view).isChecked();
                        editorEdit = me.hisn.mygesture.P.s.edit();
                        str = "show_app_label";
                        editorPutBoolean = editorEdit.putBoolean(str, zIsChecked);
                        editorPutBoolean.apply();
                        break;
                    case me.hisn.mygesture.R.id.apps_drawer_icon_pack_btn /* 2131230769 */:
                        me.hisn.mygesture.j.this.startActivity(new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.appdrawer.IconPackPicker.class));
                        me.hisn.appdrawer.a.k = null;
                        break;
                    case me.hisn.mygesture.R.id.apps_drawer_list_btn /* 2131230770 */:
                        java.lang.String string2 = me.hisn.mygesture.P.s.getString("drawer_apps", "");
                        android.content.Intent intent = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.Aa.class);
                        intent.putExtra("31415", 8);
                        intent.putExtra("31416", string2);
                        intent.putExtra("31417", me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.apps_drawer_text));
                        intent.putExtra("min_ram", this.f604a);
                        me.hisn.mygesture.j.this.startActivityForResult(intent, 85);
                        break;
                    case me.hisn.mygesture.R.id.backup_settings_btn /* 2131230781 */:
                        me.hisn.utils.BackupRestoreA.a(me.hisn.mygesture.j.this);
                        break;
                    case me.hisn.mygesture.R.id.bottom_show_touch_bar_switch /* 2131230789 */:
                        if (me.hisn.mygesture.P.v) {
                            me.hisn.mygesture.P.j = ((android.widget.Switch) view).isChecked();
                            editorEdit2 = me.hisn.mygesture.P.s.edit();
                            z = me.hisn.mygesture.P.j;
                            str2 = "show_touch_bar";
                            editorEdit2.putBoolean(str2, z).apply();
                            me.hisn.mygesture.MAS.t();
                        } else {
                            b0Var = new me.hisn.utils.b0();
                            applicationContext = me.hisn.mygesture.j.this.getApplicationContext();
                            i = me.hisn.mygesture.R.string.require_bottom_edge_enabled;
                            b0Var.a(applicationContext, i, 0);
                        }
                        break;
                    case me.hisn.mygesture.R.id.click_touch_bar_key /* 2131230817 */:
                        jVar = me.hisn.mygesture.j.this;
                        string = jVar.getString(me.hisn.mygesture.R.string.click_touch_bar);
                        i2 = 87;
                        jVar.a(string, i2);
                        break;
                    case me.hisn.mygesture.R.id.colorful_touch_bar_switch /* 2131230821 */:
                        me.hisn.mygesture.P.q = ((android.widget.Switch) view).isChecked();
                        editorEdit2 = me.hisn.mygesture.P.s.edit();
                        z = me.hisn.mygesture.P.q;
                        str2 = "colorful_touch_bar";
                        editorEdit2.putBoolean(str2, z).apply();
                        me.hisn.mygesture.MAS.t();
                        break;
                    case me.hisn.mygesture.R.id.edge_shadow_setting_btn /* 2131230845 */:
                        android.content.Intent intent2 = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.ColorPicker.class);
                        intent2.putExtra("edge_shadow_color", me.hisn.mygesture.P.T);
                        intent2.putExtra("edge_shadow_width", me.hisn.mygesture.P.U);
                        intent2.putExtra("title", me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.edge_shadow_setting));
                        intent2.putExtra("screen_width", me.hisn.mygesture.P.k0);
                        intent2.putExtra("ram", this.f604a);
                        me.hisn.mygesture.j.this.startActivityForResult(intent2, 80);
                        break;
                    case me.hisn.mygesture.R.id.force_round_icon_switch /* 2131230872 */:
                        me.hisn.mygesture.P.i0 = ((android.widget.Switch) view).isChecked();
                        me.hisn.mygesture.P.s.edit().putBoolean("31417", me.hisn.mygesture.P.i0).apply();
                        me.hisn.appdrawer.a.k = null;
                        break;
                    case me.hisn.mygesture.R.id.hide_touch_bar_when_land_switch /* 2131230888 */:
                        me.hisn.mygesture.P.k = ((android.widget.Switch) view).isChecked();
                        editorEdit3 = me.hisn.mygesture.P.s.edit();
                        z2 = me.hisn.mygesture.P.k;
                        str3 = "hide_touch_bar_when_land";
                        editorPutBoolean = editorEdit3.putBoolean(str3, z2);
                        editorPutBoolean.apply();
                        break;
                    case me.hisn.mygesture.R.id.long_click_touch_bar_key /* 2131230921 */:
                        jVar = me.hisn.mygesture.j.this;
                        string = jVar.getString(me.hisn.mygesture.R.string.long_click_touch_bar);
                        i2 = 88;
                        jVar.a(string, i2);
                        break;
                    case me.hisn.mygesture.R.id.panel_settings_btn /* 2131230968 */:
                        me.hisn.utils.C.CP(me.hisn.mygesture.j.this, new me.hisn.utils.p0().b(me.hisn.mygesture.j.this.getApplicationContext()), 85);
                        break;
                    case me.hisn.mygesture.R.id.pro_guide_btn /* 2131230982 */:
                        me.hisn.mygesture.j jVar2 = me.hisn.mygesture.j.this;
                        jVar2.a(jVar2.getString(me.hisn.mygesture.R.string.pro_guide));
                        new me.hisn.utils.b0().a(me.hisn.mygesture.j.this.getApplicationContext(), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.cannot_open_tip), 1);
                        break;
                    case me.hisn.mygesture.R.id.restore_settings_btn /* 2131230989 */:
                        me.hisn.utils.BackupRestoreA.b(me.hisn.mygesture.j.this);
                        break;
                    case me.hisn.mygesture.R.id.save_btn /* 2131231018 */:
                        java.lang.CharSequence text = this.f605b.getText();
                        if (text == null || text.toString().length() < 5) {
                            b0Var = new me.hisn.utils.b0();
                            applicationContext = me.hisn.mygesture.j.this.getApplicationContext();
                            i = me.hisn.mygesture.R.string.read_sn_tip;
                            b0Var.a(applicationContext, i, 0);
                        } else {
                            java.lang.String string3 = me.hisn.mygesture.j.this.f596a.getText().toString();
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            if (string3.length() > 0) {
                                int length = string3.length();
                                for (int i3 = 0; i3 < length; i3++) {
                                    char cCharAt = string3.charAt(i3);
                                    if (java.lang.Character.isDigit(cCharAt)) {
                                        sb.append(cCharAt);
                                    }
                                }
                                me.hisn.utils.C.PA(me.hisn.mygesture.j.this.getApplicationContext(), new me.hisn.utils.p0().b(me.hisn.mygesture.j.this.getApplicationContext()), sb.toString());
                                me.hisn.mygesture.P.A = false;
                                me.hisn.mygesture.j.this.setResult(-1);
                                me.hisn.mygesture.j.this.finish();
                            }
                        }
                        break;
                    case me.hisn.mygesture.R.id.sn_text /* 2131231031 */:
                        me.hisn.utils.p0 p0Var = new me.hisn.utils.p0();
                        java.lang.String strA = p0Var.a(me.hisn.mygesture.j.this.getApplicationContext(), 0);
                        if (android.os.Build.VERSION.SDK_INT > 28) {
                            me.hisn.mygesture.j.this.j();
                            break;
                        } else if ("".equals(strA) && android.os.Build.VERSION.SDK_INT >= 23) {
                            new me.hisn.mygesture.j.e.a(view.getContext(), null, me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.need_state_p), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.go_grant_text), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.cancel_text), true, -2, p0Var);
                            break;
                        }
                        break;
                    case me.hisn.mygesture.R.id.switch_black_list_btn /* 2131231038 */:
                        android.content.Intent intent3 = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.Aa.class);
                        me.hisn.utils.l0.a(view.getContext().getApplicationContext());
                        intent3.putExtra("31415", 4);
                        intent3.putExtra("31417", me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.switch_black_title));
                        intent3.putExtra("31416", me.hisn.mygesture.P.g0);
                        intent3.putExtra("min_ram", this.f604a);
                        me.hisn.mygesture.j.this.startActivityForResult(intent3, 84);
                        break;
                    case me.hisn.mygesture.R.id.switch_icon_only_switch /* 2131231039 */:
                        me.hisn.mygesture.P.d = ((android.widget.Switch) view).isChecked();
                        editorEdit3 = me.hisn.mygesture.P.s.edit();
                        z2 = me.hisn.mygesture.P.d;
                        str3 = "31418";
                        editorPutBoolean = editorEdit3.putBoolean(str3, z2);
                        editorPutBoolean.apply();
                        break;
                    case me.hisn.mygesture.R.id.switch_order_switch /* 2131231040 */:
                        me.hisn.mygesture.P.h0 = ((android.widget.Switch) view).isChecked();
                        editorEdit3 = me.hisn.mygesture.P.s.edit();
                        z2 = me.hisn.mygesture.P.h0;
                        str3 = "reversed_order_for_switch_list";
                        editorPutBoolean = editorEdit3.putBoolean(str3, z2);
                        editorPutBoolean.apply();
                        break;
                    case me.hisn.mygesture.R.id.touch_bar_appearance_tuner_btn /* 2131231063 */:
                        android.content.Intent intent4 = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.ColorPicker.class);
                        intent4.putExtra("edge_shadow_color", me.hisn.mygesture.P.n);
                        intent4.putExtra("edge_shadow_width", me.hisn.mygesture.P.m);
                        intent4.putExtra("title", me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.touch_bar_appearance));
                        intent4.putExtra("screen_width", me.hisn.mygesture.P.k0);
                        int iMin = (me.hisn.mygesture.P.S * java.lang.Math.min(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0)) / 200;
                        if (me.hisn.mygesture.P.l > iMin) {
                            me.hisn.mygesture.P.l = iMin;
                        }
                        intent4.putExtra("max_height", iMin);
                        intent4.putExtra("touch_bar_height", me.hisn.mygesture.P.l);
                        intent4.putExtra("ram", this.f604a);
                        me.hisn.mygesture.j.this.startActivityForResult(intent4, 81);
                        break;
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    class f implements android.view.View.OnLongClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.lang.String f609a;

        f(java.lang.String str) {
            this.f609a = str;
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) me.hisn.mygesture.j.this.getSystemService("clipboard");
            if (clipboardManager == null) {
                return true;
            }
            clipboardManager.setPrimaryClip(android.content.ClipData.newPlainText("sn", "<" + this.f609a + ">"));
            new me.hisn.utils.b0().a(me.hisn.mygesture.j.this.getApplicationContext(), me.hisn.mygesture.j.this.getString(me.hisn.mygesture.R.string.copyed_sn) + "<" + this.f609a + ">", 0);
            return true;
        }
    }

    class g extends me.hisn.utils.z {
        g(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
            super(context, str, str2, str3, str4, z, i);
        }

        @Override // me.hisn.utils.z
        public void d() {
            try {
                me.hisn.mygesture.j.this.startActivity(me.hisn.mygesture.j.this.getPackageManager().getLaunchIntentForPackage("com.tencent.mm"));
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a() {
        new me.hisn.utils.g().a((android.app.Activity) this, true);
    }

    private void a(android.widget.TextView textView) {
        java.lang.String str;
        java.lang.String[] strArrB = new me.hisn.utils.p0().b(getApplicationContext());
        int iCP = me.hisn.utils.C.CP(getApplicationContext(), strArrB, 0);
        if (iCP >= 0) {
            str = strArrB[iCP];
        } else {
            str = strArrB.length > 0 ? strArrB[strArrB.length - 1] : "";
        }
        if ("".equals(str)) {
            return;
        }
        textView.setText(java.lang.String.format("%s%s", "<" + str + ">", getString(me.hisn.mygesture.R.string.click_to_copy)));
        textView.setOnLongClickListener(new me.hisn.mygesture.j.f(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(java.lang.String str) {
        try {
            startActivity(new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse(str)));
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(java.lang.String str, int i) {
        android.content.Intent intent = new android.content.Intent(this, (java.lang.Class<?>) me.hisn.mypanel.Aa.class);
        intent.putExtra("31415", 16);
        intent.putExtra("31417", str);
        intent.putExtra("min_ram", me.hisn.mygesture.P.s.getBoolean("min_ram", false));
        try {
            startActivityForResult(intent, i);
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }

    private boolean a(android.view.View view, java.lang.Object obj) {
        android.view.View.OnClickListener bVar;
        boolean z = me.hisn.utils.C.CP(getApplicationContext(), new me.hisn.utils.p0().b(getApplicationContext()), 0) >= 0;
        if (z) {
            if (view instanceof android.widget.Spinner) {
                view.setClickable(true);
                ((android.view.View) view.getParent()).setAlpha(1.0f);
                ((android.widget.Spinner) view).setOnItemSelectedListener((android.widget.AdapterView.OnItemSelectedListener) obj);
            } else {
                bVar = (android.view.View.OnClickListener) obj;
            }
            return z;
        }
        if (!(view instanceof android.widget.LinearLayout)) {
            view.setEnabled(false);
            view.setClickable(false);
            view = (android.view.View) view.getParent();
        }
        view.setAlpha(0.7f);
        bVar = new me.hisn.mygesture.j.b();
        view.setOnClickListener(bVar);
        return z;
    }

    private void b() {
        android.view.View view = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.switch_black_list_btn);
        android.view.View view2 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.backup_settings_btn);
        android.view.View view3 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.restore_settings_btn);
        android.widget.Switch r4 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.switch_order_switch);
        android.widget.Switch r5 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.force_round_icon_switch);
        android.widget.Switch r6 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.switch_icon_only_switch);
        android.widget.Switch r7 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.bottom_show_touch_bar_switch);
        r7.setChecked(me.hisn.mygesture.P.j);
        android.widget.Switch r8 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.hide_touch_bar_when_land_switch);
        r8.setChecked(me.hisn.mygesture.P.k);
        android.view.View view4 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.touch_bar_appearance_tuner_btn);
        android.widget.Switch r10 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.colorful_touch_bar_switch);
        r10.setChecked(me.hisn.mygesture.P.q);
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.click_touch_bar_key);
        android.widget.TextView textView2 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.long_click_touch_bar_key);
        textView.setText(me.hisn.mygesture.P.s.getString("touch_bar_click_l", getString(me.hisn.mygesture.R.string.select_text)));
        textView2.setText(me.hisn.mygesture.P.s.getString("touch_bar_long_click_l", getString(me.hisn.mygesture.R.string.select_text)));
        android.view.View view5 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.panel_settings_btn);
        android.view.View view6 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.edge_shadow_setting_btn);
        android.view.View view7 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.apps_drawer_list_btn);
        android.view.View view8 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.apps_drawer_icon_pack_btn);
        android.widget.Switch r11 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.app_drawer_show_label_switch);
        android.widget.Switch r15 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.app_drawer_blur_behind_switch);
        android.widget.Switch r1 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.app_drawer_long_click_switch);
        r11.setChecked(me.hisn.mygesture.P.s.getBoolean("show_app_label", true));
        r1.setChecked(me.hisn.mygesture.P.s.getBoolean("d31423", false));
        if (android.os.Build.VERSION.SDK_INT >= 31) {
            r15.setChecked(me.hisn.mygesture.P.s.getBoolean("d31424", false));
        }
        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.pro_guide_btn);
        android.widget.TextView textView3 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.save_btn);
        android.widget.TextView textView4 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.sn_text);
        android.widget.TextView textView5 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.get_pro_tv);
        findViewById(me.hisn.mygesture.R.id.imei_title_tv).setOnLongClickListener(new me.hisn.mygesture.j.d());
        a(textView4);
        r4.setChecked(me.hisn.mygesture.P.h0);
        r5.setChecked(me.hisn.mygesture.P.i0);
        r6.setChecked(me.hisn.mygesture.P.d);
        android.view.View.OnClickListener eVar = new me.hisn.mygesture.j.e(me.hisn.mygesture.P.s.getBoolean("min_ram", false), textView4);
        a(view5, eVar);
        a(view6, eVar);
        a(r7, eVar);
        a(r8, eVar);
        a(view4, eVar);
        a(textView, eVar);
        a(textView2, eVar);
        a(view2, eVar);
        a(view3, eVar);
        a(view, eVar);
        a(r4, eVar);
        a(r5, eVar);
        a(r6, eVar);
        a(view7, eVar);
        a(view8, eVar);
        a(r11, eVar);
        a(r15, eVar);
        a(r1, eVar);
        a(r10, eVar);
        a(findViewById(me.hisn.mygesture.R.id.go_home_anim_spinner), (java.lang.Object) null);
        a(findViewById(me.hisn.mygesture.R.id.go_prev_app_anim_spinner), (java.lang.Object) null);
        a(findViewById(me.hisn.mygesture.R.id.open_app_anim_spinner), (java.lang.Object) null);
        a(findViewById(me.hisn.mygesture.R.id.bottom_swipe_left_spinner), (java.lang.Object) null);
        a(findViewById(me.hisn.mygesture.R.id.bottom_swipe_right_spinner), (java.lang.Object) null);
        if (!a(r10, eVar) && me.hisn.mygesture.P.A) {
            textView5.setText(me.hisn.mygesture.R.string.cannot_active_pro);
            textView5.setTextColor(-65536);
        }
        linearLayout.setOnClickListener(eVar);
        textView3.setOnClickListener(eVar);
        textView4.setOnClickListener(eVar);
    }

    private void c() {
    }

    private void d() {
        android.view.View viewFindViewById = findViewById(me.hisn.mygesture.R.id.active_layout);
        viewFindViewById.setVisibility(0);
        if (getIntent().getIntExtra("from_flag", 0) == 1) {
            viewFindViewById.postDelayed(new me.hisn.mygesture.j.a(viewFindViewById), 500L);
        }
    }

    private void e() {
        android.widget.Spinner spinner = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.go_home_anim_spinner);
        android.widget.Spinner spinner2 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.go_prev_app_anim_spinner);
        android.widget.Spinner spinner3 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.open_app_anim_spinner);
        android.widget.Spinner spinner4 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.drawer_folder_bkg_spinner);
        android.widget.Spinner spinner5 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.bottom_swipe_left_spinner);
        android.widget.Spinner spinner6 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.bottom_swipe_right_spinner);
        spinner5.setSelection(me.hisn.mygesture.P.s.getInt("b31415", 0));
        spinner6.setSelection(me.hisn.mygesture.P.s.getInt("b31416", 0));
        spinner4.setSelection(me.hisn.mygesture.P.s.getInt("31430", 0));
        spinner.setSelection(me.hisn.mygesture.P.F);
        spinner2.setSelection(me.hisn.mygesture.P.H);
        spinner3.setSelection(me.hisn.mygesture.P.G);
        java.lang.Object cVar = new me.hisn.mygesture.j.c(this);
        a(spinner, cVar);
        a(spinner2, cVar);
        a(spinner3, cVar);
        a(spinner4, cVar);
        a(spinner5, cVar);
        a(spinner6, cVar);
    }

    private void f() {
        try {
            startActivity(new android.content.Intent(getApplicationContext(), (java.lang.Class<?>) me.hisn.mypanel.SA.class));
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        if (!me.hisn.mygesture.P.m0) {
            new me.hisn.utils.b0().a(getApplicationContext(), me.hisn.mygesture.R.string.enable_gesture_first, 0);
        } else if (this.f597b == null) {
            this.f597b = new me.hisn.utils.p0().a((android.app.Activity) this, 90);
        }
    }

    private void k() {
        android.widget.EditText editText = (android.widget.EditText) findViewById(me.hisn.mygesture.R.id.key_text);
        this.f596a = editText;
        editText.setText(me.hisn.mygesture.P.N);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        android.content.SharedPreferences.Editor editorPutInt;
        android.content.SharedPreferences.Editor editorEdit;
        java.lang.String str;
        java.lang.String str2;
        java.lang.String strA;
        int i3;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            switch (i) {
                case 80:
                    me.hisn.mygesture.P.T = intent.getIntExtra("edge_shadow_color", me.hisn.mygesture.P.T);
                    me.hisn.mygesture.P.U = intent.getIntExtra("edge_shadow_width", me.hisn.mygesture.P.U);
                    editorPutInt = me.hisn.mygesture.P.s.edit().putInt("edge_shadow_color", me.hisn.mygesture.P.T).putInt("edge_shadow_width", me.hisn.mygesture.P.U);
                    editorPutInt.apply();
                    break;
                case 81:
                    me.hisn.mygesture.P.n = intent.getIntExtra("edge_shadow_color", me.hisn.mygesture.P.n);
                    int intExtra = intent.getIntExtra("edge_shadow_width", me.hisn.mygesture.P.Z);
                    me.hisn.mygesture.P.m = intExtra;
                    if (intExtra < 1) {
                        me.hisn.mygesture.P.m = 1;
                    }
                    int intExtra2 = intent.getIntExtra("touch_bar_height", me.hisn.mygesture.P.l);
                    me.hisn.mygesture.P.l = intExtra2;
                    if (intExtra2 < 1) {
                        me.hisn.mygesture.P.l = 1;
                    }
                    me.hisn.mygesture.P.s.edit().putInt("touch_bar_width", me.hisn.mygesture.P.m).putInt("touch_bar_height", me.hisn.mygesture.P.l).putInt("touch_bar_color", me.hisn.mygesture.P.n).apply();
                    me.hisn.mygesture.MAS.t();
                    break;
                case 82:
                    me.hisn.mygesture.P.o = intent.getIntExtra("edge_shadow_color", me.hisn.mygesture.P.o);
                    editorPutInt = me.hisn.mygesture.P.s.edit().putInt("touch_bar_color2", me.hisn.mygesture.P.o);
                    editorPutInt.apply();
                    break;
                case 83:
                    java.lang.String stringExtra = intent.getStringExtra("31420");
                    me.hisn.mygesture.P.p = stringExtra;
                    if (stringExtra == null) {
                        me.hisn.mygesture.P.p = "";
                    }
                    editorEdit = me.hisn.mygesture.P.s.edit();
                    str = me.hisn.mygesture.P.p;
                    str2 = "touch_bar_color2_apps";
                    editorPutInt = editorEdit.putString(str2, str);
                    editorPutInt.apply();
                    break;
                case 84:
                    java.lang.String stringExtra2 = intent.getStringExtra("31420");
                    me.hisn.mygesture.P.g0 = stringExtra2;
                    if (stringExtra2 != null && !stringExtra2.contains("&")) {
                        me.hisn.mygesture.P.g0 = null;
                    }
                    me.hisn.utils.l0.b(me.hisn.mygesture.P.g0);
                    editorEdit = me.hisn.mygesture.P.s.edit();
                    str = me.hisn.mygesture.P.g0;
                    str2 = "switch_black";
                    editorPutInt = editorEdit.putString(str2, str);
                    editorPutInt.apply();
                    break;
                case 85:
                    me.hisn.mygesture.P.s.edit().putString("drawer_apps", intent.getStringExtra("31420")).apply();
                    me.hisn.appdrawer.a.k = null;
                    break;
                case 87:
                    strA = me.hisn.utils.h0.a(getApplicationContext(), intent, "touch_bar_click", me.hisn.mygesture.P.s, false);
                    i3 = me.hisn.mygesture.R.id.click_touch_bar_key;
                    ((android.widget.TextView) findViewById(i3)).setText(strA);
                    break;
                case 88:
                    strA = me.hisn.utils.h0.a(getApplicationContext(), intent, "touch_bar_long_click", me.hisn.mygesture.P.s, false);
                    i3 = me.hisn.mygesture.R.id.long_click_touch_bar_key;
                    ((android.widget.TextView) findViewById(i3)).setText(strA);
                    break;
            }
        }
        if (i == 89) {
            new me.hisn.mygesture.j.g(this, null, getString(me.hisn.mygesture.R.string.backup_to_wechat_tip), getString(me.hisn.mygesture.R.string.yes_text), getString(me.hisn.mygesture.R.string.cancel_text), true, -2);
        }
        if (i == 91) {
            me.hisn.utils.C.BI(this, 82);
        }
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_j);
        k();
        me.hisn.utils.C.BI(this, 81);
        me.hisn.utils.C.BI(this, 83);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // me.hisn.utils.e, android.app.Activity
    protected void onPostCreate(android.os.Bundle bundle) {
        super.onPostCreate(bundle);
        if (me.hisn.utils.C.CP(this, new me.hisn.utils.p0().b(getApplicationContext()), 84) >= 0) {
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, java.lang.String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 86) {
            recreate();
        }
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        if (this.f597b != null) {
            try {
                if (me.hisn.mygesture.MAS.k() != null) {
                    me.hisn.mygesture.MAS.k().removeView(this.f597b);
                }
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
            this.f597b = null;
            k();
            a((android.widget.TextView) findViewById(me.hisn.mygesture.R.id.sn_text));
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }
}
