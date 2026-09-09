package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class Nex extends me.hisn.utils.e {

    class a implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: me.hisn.mygesture.Nex$a$a, reason: collision with other inner class name */
        class C0021a extends me.hisn.utils.z {

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            final /* synthetic */ android.widget.EditText f530b;

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            final /* synthetic */ android.view.View f531c;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C0021a(me.hisn.mygesture.Nex.a aVar, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, android.widget.EditText editText, android.view.View view) {
                super(context, str, str2, str3, str4, z, i);
                this.f530b = editText;
                this.f531c = view;
            }

            @Override // me.hisn.utils.z
            public void a(android.app.AlertDialog.Builder builder) {
                super.a(builder);
                builder.setView(this.f531c);
            }

            @Override // me.hisn.utils.z
            public void d() {
                android.text.Editable text = this.f530b.getText();
                me.hisn.mygesture.P.s.edit().putString("31416", text != null ? text.toString() : null).apply();
            }
        }

        a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            int id = view.getId();
            try {
                if (id == me.hisn.mygesture.R.id.breath_white_list_manager_btn) {
                    android.content.Intent intent = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.Aa.class);
                    intent.putExtra("31415", 4);
                    intent.putExtra("31417", me.hisn.mygesture.Nex.this.getString(me.hisn.mygesture.R.string.notification_white_title));
                    intent.putExtra("31416", me.hisn.mygesture.P.a0);
                    intent.putExtra("min_ram", me.hisn.mygesture.P.s.getBoolean("min_ram", false));
                    me.hisn.mygesture.Nex.this.startActivityForResult(intent, 81);
                } else {
                    if (id == me.hisn.mygesture.R.id.keywords_filter_btn) {
                        android.view.View viewInflate = android.view.View.inflate(view.getContext(), me.hisn.mygesture.R.layout.edit_view, null);
                        android.widget.EditText editText = (android.widget.EditText) viewInflate.findViewById(me.hisn.mygesture.R.id.edit_text_view);
                        java.lang.String string = me.hisn.mygesture.P.s.getString("31416", null);
                        if (string == null || string.isEmpty()) {
                            editText.setHint(me.hisn.mygesture.R.string.keywords_filter_tip);
                        } else {
                            editText.setText(string);
                        }
                        new me.hisn.mygesture.Nex.a.C0021a(this, view.getContext(), me.hisn.mygesture.Nex.this.getString(me.hisn.mygesture.R.string.keywords_filter), null, me.hisn.mygesture.Nex.this.getString(me.hisn.mygesture.R.string.save_text), me.hisn.mygesture.Nex.this.getString(me.hisn.mygesture.R.string.cancel_text), true, -2, editText, viewInflate);
                        return;
                    }
                    if (id != me.hisn.mygesture.R.id.notification_edge_setting_btn) {
                        return;
                    }
                    android.content.Intent intent2 = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.ColorPicker.class);
                    intent2.putExtra("edge_shadow_color", me.hisn.mygesture.P.Y);
                    intent2.putExtra("edge_shadow_width", me.hisn.mygesture.P.Z);
                    intent2.putExtra("title", me.hisn.mygesture.Nex.this.getString(me.hisn.mygesture.R.string.notification_edge_setting_text));
                    intent2.putExtra("screen_width", me.hisn.mygesture.P.k0);
                    intent2.putExtra("ram", me.hisn.mygesture.P.s.getBoolean("min_ram", false));
                    me.hisn.mygesture.Nex.this.startActivityForResult(intent2, 80);
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    class b implements android.widget.AdapterView.OnItemSelectedListener {
        b(me.hisn.mygesture.Nex nex) {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            java.lang.String str;
            if (adapterView.getId() != me.hisn.mygesture.R.id.breath_count_spinner) {
                str = null;
            } else {
                me.hisn.mygesture.P.X = i;
                str = "breath_count";
            }
            if (str != null) {
                me.hisn.mygesture.P.s.edit().putInt(str, i).apply();
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
        }
    }

    class c implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.Switch f532a;

        c(android.widget.Switch r2) {
            this.f532a = r2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            java.lang.String str;
            android.widget.Switch r0 = (android.widget.Switch) view;
            int id = view.getId();
            if (id == me.hisn.mygesture.R.id.notification_breath_switch) {
                if (r0.isChecked()) {
                    boolean zB = new me.hisn.utils.c0().b(view.getContext());
                    if (!zB || !me.hisn.mygesture.NotificationManagerService.a()) {
                        if (zB) {
                            new me.hisn.utils.b0().a(me.hisn.mygesture.Nex.this.getApplicationContext(), me.hisn.mygesture.R.string.reset_notification_access_tips, 0);
                        }
                        r0.setChecked(false);
                        new me.hisn.utils.c0().a(view.getContext());
                    }
                    java.lang.String str2 = me.hisn.mygesture.P.a0;
                    if (str2 == null || "".equals(str2)) {
                        r0.setChecked(false);
                        new me.hisn.utils.b0().a(me.hisn.mygesture.Nex.this.getApplicationContext(), me.hisn.mygesture.R.string.breath_setting_tip, 1);
                    }
                }
                boolean zIsChecked = r0.isChecked();
                me.hisn.mygesture.P.V = zIsChecked;
                this.f532a.setEnabled(zIsChecked);
                str = "is_show_notification_edge";
            } else if (id != me.hisn.mygesture.R.id.red_package_switch) {
                switch (id) {
                    case me.hisn.mygesture.R.id.breath_on_lock_screen_switch /* 2131230797 */:
                        if (r0.isChecked()) {
                            boolean zB2 = new me.hisn.utils.c0().b(view.getContext());
                            if (!zB2 || !me.hisn.mygesture.NotificationManagerService.a()) {
                                if (zB2) {
                                    new me.hisn.utils.b0().a(me.hisn.mygesture.Nex.this.getApplicationContext(), me.hisn.mygesture.R.string.reset_notification_access_tips, 0);
                                }
                                r0.setChecked(false);
                                new me.hisn.utils.c0().a(view.getContext());
                            }
                            java.lang.String str3 = me.hisn.mygesture.P.a0;
                            if (str3 == null || "".equals(str3)) {
                                r0.setChecked(false);
                                new me.hisn.utils.b0().a(me.hisn.mygesture.Nex.this.getApplicationContext(), me.hisn.mygesture.R.string.breath_setting_tip, 0);
                            }
                        }
                        me.hisn.mygesture.P.W = r0.isChecked();
                        str = "breath_on_lock_screen";
                        break;
                    case me.hisn.mygesture.R.id.breath_private_switch /* 2131230798 */:
                        me.hisn.mygesture.P.c0 = r0.isChecked();
                        str = "breath_private";
                        break;
                    case me.hisn.mygesture.R.id.breath_shadow_random_color_switch /* 2131230799 */:
                        me.hisn.mygesture.P.b0 = r0.isChecked();
                        str = "breath_shadow_random_color";
                        break;
                    default:
                        str = null;
                        break;
                }
            } else {
                me.hisn.mygesture.P.d0 = r0.isChecked();
                str = "red_package";
            }
            if (str != null) {
                me.hisn.mygesture.P.s.edit().putBoolean(str, r0.isChecked()).apply();
            }
        }
    }

    private void i() {
        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.notification_edge_setting_btn);
        android.widget.LinearLayout linearLayout2 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.breath_white_list_manager_btn);
        android.widget.LinearLayout linearLayout3 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.keywords_filter_btn);
        me.hisn.mygesture.Nex.a aVar = new me.hisn.mygesture.Nex.a();
        linearLayout.setOnClickListener(aVar);
        linearLayout2.setOnClickListener(aVar);
        linearLayout3.setOnClickListener(aVar);
    }

    private void j() {
        android.widget.Spinner spinner = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.breath_count_spinner);
        spinner.setSelection(me.hisn.mygesture.P.X);
        spinner.setOnItemSelectedListener(new me.hisn.mygesture.Nex.b(this));
    }

    private void k() {
        java.lang.String str;
        android.widget.Switch r0 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.notification_breath_switch);
        android.widget.Switch r1 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.red_package_switch);
        android.widget.Switch r2 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.breath_shadow_random_color_switch);
        android.widget.Switch r3 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.breath_on_lock_screen_switch);
        android.widget.Switch r4 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.breath_private_switch);
        r1.setEnabled(me.hisn.mygesture.P.V);
        boolean z = false;
        boolean booleanExtra = getIntent().getBooleanExtra("k", false);
        r3.setEnabled(booleanExtra);
        r4.setEnabled(booleanExtra);
        r1.setChecked(me.hisn.mygesture.P.d0);
        boolean z2 = new me.hisn.utils.c0().b(this) && me.hisn.mygesture.NotificationManagerService.a() && (str = me.hisn.mygesture.P.a0) != null && !"".equals(str);
        r0.setChecked(me.hisn.mygesture.P.V && z2);
        r2.setChecked(me.hisn.mygesture.P.b0);
        if (me.hisn.mygesture.P.W && z2) {
            z = true;
        }
        r3.setChecked(z);
        r4.setChecked(me.hisn.mygesture.P.c0);
        me.hisn.mygesture.Nex.c cVar = new me.hisn.mygesture.Nex.c(r1);
        r0.setOnClickListener(cVar);
        r1.setOnClickListener(cVar);
        r2.setOnClickListener(cVar);
        r3.setOnClickListener(cVar);
        r4.setOnClickListener(cVar);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        android.content.SharedPreferences.Editor editorPutString;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 80) {
                me.hisn.mygesture.P.Y = intent.getIntExtra("edge_shadow_color", me.hisn.mygesture.P.Y);
                me.hisn.mygesture.P.Z = intent.getIntExtra("edge_shadow_width", me.hisn.mygesture.P.Z);
                editorPutString = me.hisn.mygesture.P.s.edit().putInt("notification_edge_color", me.hisn.mygesture.P.Y).putInt("notification_edge_width", me.hisn.mygesture.P.Z);
            } else {
                if (i != 81) {
                    return;
                }
                java.lang.String stringExtra = intent.getStringExtra("31420");
                me.hisn.mygesture.P.a0 = stringExtra;
                if (stringExtra == null) {
                    me.hisn.mygesture.P.a0 = "";
                }
                editorPutString = me.hisn.mygesture.P.s.edit().putString("breath_white_list", me.hisn.mygesture.P.a0);
            }
            editorPutString.apply();
        }
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_nex);
        k();
        i();
        j();
    }
}
