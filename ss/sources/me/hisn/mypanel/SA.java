package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class SA extends me.hisn.utils.e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f648a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f649b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private boolean f650c;
    private boolean d;
    private boolean e;
    private boolean f;
    private int g;
    private int h = 55;
    private android.content.SharedPreferences i;

    class a implements android.widget.AdapterView.OnItemSelectedListener {

        /* JADX INFO: renamed from: me.hisn.mypanel.SA$a$a, reason: collision with other inner class name */
        class C0028a extends me.hisn.utils.z {
            C0028a(me.hisn.mypanel.SA.a aVar, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
                super(context, str, str2, str3, str4, z, i);
            }

            @Override // me.hisn.utils.z
            public void d() {
            }
        }

        class b extends me.hisn.utils.z {
            b(me.hisn.mypanel.SA.a aVar, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
                super(context, str, str2, str3, str4, z, i);
            }

            @Override // me.hisn.utils.z
            public void d() {
            }
        }

        a() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            android.content.SharedPreferences.Editor editorEdit;
            java.lang.String str;
            if (adapterView.getId() == me.hisn.mygesture.R.id.panel_background_spinner) {
                me.hisn.mypanel.SA.this.i.edit().putInt("background_type", i).apply();
                if (i == 2 || i == 3) {
                    new me.hisn.utils.j0().a(me.hisn.mypanel.SA.this, new java.lang.String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 11);
                    return;
                }
                if (i == 4) {
                    me.hisn.mypanel.SA sa = me.hisn.mypanel.SA.this;
                    new me.hisn.mypanel.SA.a.C0028a(this, sa, null, sa.getString(me.hisn.mygesture.R.string.blur_back_tips), me.hisn.mypanel.SA.this.getString(me.hisn.mygesture.R.string.i_know), null, true, -2);
                    return;
                } else {
                    if (i == 5) {
                        me.hisn.mypanel.SA sa2 = me.hisn.mypanel.SA.this;
                        new me.hisn.mypanel.SA.a.b(this, sa2, null, sa2.getString(me.hisn.mygesture.R.string.blur_android12), me.hisn.mypanel.SA.this.getString(me.hisn.mygesture.R.string.i_know), null, true, -2);
                        return;
                    }
                    return;
                }
            }
            if (adapterView.getId() == me.hisn.mygesture.R.id.panel_size_spinner) {
                editorEdit = me.hisn.mypanel.SA.this.i.edit();
                str = "panel_size";
            } else if (adapterView.getId() == me.hisn.mygesture.R.id.panel_anim_spinner) {
                editorEdit = me.hisn.mypanel.SA.this.i.edit();
                str = "panel_anim_type";
            } else if (adapterView.getId() == me.hisn.mygesture.R.id.panel_alpha_spinner) {
                editorEdit = me.hisn.mypanel.SA.this.i.edit();
                str = "panel_back_alpha";
            } else if (adapterView.getId() == me.hisn.mygesture.R.id.panel_theme_spinner) {
                editorEdit = me.hisn.mypanel.SA.this.i.edit();
                str = "panel_theme_type";
            } else {
                if (adapterView.getId() != me.hisn.mygesture.R.id.panel_layout_spinner) {
                    return;
                }
                editorEdit = me.hisn.mypanel.SA.this.i.edit();
                str = "41420";
            }
            editorEdit.putInt(str, i).apply();
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.Spinner f652a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.AdapterView.OnItemSelectedListener f653b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.Spinner f654c;
        final /* synthetic */ android.widget.Spinner d;
        final /* synthetic */ android.widget.Spinner e;
        final /* synthetic */ android.widget.Spinner f;
        final /* synthetic */ android.widget.Spinner g;

        b(me.hisn.mypanel.SA sa, android.widget.Spinner spinner, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener, android.widget.Spinner spinner2, android.widget.Spinner spinner3, android.widget.Spinner spinner4, android.widget.Spinner spinner5, android.widget.Spinner spinner6) {
            this.f652a = spinner;
            this.f653b = onItemSelectedListener;
            this.f654c = spinner2;
            this.d = spinner3;
            this.e = spinner4;
            this.f = spinner5;
            this.g = spinner6;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f652a.setOnItemSelectedListener(this.f653b);
            this.f654c.setOnItemSelectedListener(this.f653b);
            this.d.setOnItemSelectedListener(this.f653b);
            this.e.setOnItemSelectedListener(this.f653b);
            this.f.setOnItemSelectedListener(this.f653b);
            this.g.setOnItemSelectedListener(this.f653b);
        }
    }

    class c implements android.view.View.OnClickListener {

        class a extends me.hisn.utils.z {

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            final /* synthetic */ android.widget.Switch f656b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, android.widget.Switch r18) {
                super(context, str, str2, str3, str4, z, i);
                this.f656b = r18;
            }

            @Override // me.hisn.utils.z
            public void d() {
                this.f656b.setChecked(true);
                me.hisn.mypanel.SA.this.i.edit().putBoolean("tint_panel_icons", true).apply();
            }
        }

        c() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            java.lang.String str;
            android.widget.Switch r9 = (android.widget.Switch) view;
            boolean zIsChecked = r9.isChecked();
            switch (view.getId()) {
                case me.hisn.mygesture.R.id.panel_align_center_switch /* 2131230961 */:
                    str = "panel_show_in_center";
                    break;
                case me.hisn.mygesture.R.id.panel_auto_exit_switch /* 2131230964 */:
                    str = "panel_auto_exit";
                    break;
                case me.hisn.mygesture.R.id.panel_show_on_lock_switch /* 2131230969 */:
                    str = "panel_show_on_lock";
                    break;
                case me.hisn.mygesture.R.id.panel_tint_icon_switch /* 2131230972 */:
                    if (!zIsChecked) {
                        str = "tint_panel_icons";
                    } else {
                        r9.setChecked(false);
                        new me.hisn.mypanel.SA.c.a(view.getContext(), null, me.hisn.mypanel.SA.this.getString(me.hisn.mygesture.R.string.white_panel_tips), me.hisn.mypanel.SA.this.getString(me.hisn.mygesture.R.string.yes_text), null, true, -2, r9);
                        str = null;
                    }
                    break;
                case me.hisn.mygesture.R.id.show_system_volume_panel_switch /* 2131231029 */:
                    str = "show_system_volume_panel";
                    break;
                case me.hisn.mygesture.R.id.zoom_seek_bar_switch /* 2131231086 */:
                    str = "zoom_in_seek_bar";
                    break;
                default:
                    str = null;
                    break;
            }
            if (str != null) {
                me.hisn.mypanel.SA.this.i.edit().putBoolean(str, zIsChecked).apply();
            }
        }
    }

    class d implements android.view.View.OnClickListener {
        d() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.content.Intent intent = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.ColorPicker.class);
            intent.putExtra("edge_shadow_color", me.hisn.mypanel.SA.this.g);
            intent.putExtra("title", me.hisn.mypanel.SA.this.getString(me.hisn.mygesture.R.string.panel_back_color));
            intent.putExtra("screen_width", me.hisn.mygesture.P.k0);
            try {
                me.hisn.mypanel.SA.this.startActivityForResult(intent, me.hisn.mypanel.SA.this.h);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void i() {
        ((android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.background_color_btn)).setOnClickListener(new me.hisn.mypanel.SA.d());
    }

    private void j() {
        android.content.SharedPreferences sharedPreferences = getSharedPreferences("my_panel", 0);
        this.i = sharedPreferences;
        this.f648a = sharedPreferences.getBoolean("panel_show_in_center", false);
        this.f649b = this.i.getBoolean("show_system_volume_panel", false);
        this.g = this.i.getInt("panel_background_color", me.hisn.mygesture.P.Y);
        this.f650c = this.i.getBoolean("panel_show_on_lock", false);
        this.d = this.i.getBoolean("panel_auto_exit", true);
        this.e = this.i.getBoolean("tint_panel_icons", false);
        this.f = this.i.getBoolean("zoom_in_seek_bar", false);
    }

    private void k() {
        android.widget.Spinner spinner = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.panel_background_spinner);
        android.widget.Spinner spinner2 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.panel_size_spinner);
        android.widget.Spinner spinner3 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.panel_anim_spinner);
        android.widget.Spinner spinner4 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.panel_alpha_spinner);
        android.widget.Spinner spinner5 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.panel_theme_spinner);
        android.widget.Spinner spinner6 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.panel_layout_spinner);
        spinner2.setSelection(this.i.getInt("panel_size", 2));
        int i = this.i.getInt("background_type", 0);
        spinner.setSelection(i);
        if (i == 2 || i == 3) {
            new me.hisn.utils.j0().a(this, new java.lang.String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 11);
        }
        spinner3.setSelection(this.i.getInt("panel_anim_type", 2));
        spinner4.setSelection(this.i.getInt("panel_back_alpha", 1));
        spinner5.setSelection(this.i.getInt("panel_theme_type", 1));
        spinner6.setSelection(this.i.getInt("41420", 0));
        spinner.postDelayed(new me.hisn.mypanel.SA.b(this, spinner, new me.hisn.mypanel.SA.a(), spinner2, spinner3, spinner4, spinner5, spinner6), 500L);
    }

    private void l() {
        android.widget.Switch r0 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.panel_align_center_switch);
        android.widget.Switch r1 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.show_system_volume_panel_switch);
        android.widget.Switch r2 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.panel_show_on_lock_switch);
        android.widget.Switch r3 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.panel_auto_exit_switch);
        android.widget.Switch r4 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.zoom_seek_bar_switch);
        r4.setChecked(this.f);
        r0.setChecked(this.f648a);
        r1.setChecked(this.f649b);
        r2.setChecked(this.f650c);
        r3.setChecked(this.d);
        android.widget.Switch r5 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.panel_tint_icon_switch);
        r5.setChecked(this.e);
        me.hisn.mypanel.SA.c cVar = new me.hisn.mypanel.SA.c();
        r0.setOnClickListener(cVar);
        r1.setOnClickListener(cVar);
        r2.setOnClickListener(cVar);
        r3.setOnClickListener(cVar);
        r5.setOnClickListener(cVar);
        r4.setOnClickListener(cVar);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == this.h) {
            this.g = intent.getIntExtra("edge_shadow_color", this.g);
            this.i.edit().putInt("panel_background_color", this.g).apply();
        }
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_s);
        j();
        l();
        k();
        i();
    }
}
