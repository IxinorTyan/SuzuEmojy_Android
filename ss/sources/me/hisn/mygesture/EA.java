package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class EA extends me.hisn.utils.e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private me.hisn.utils.f0 f465a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private me.hisn.utils.a f466b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.widget.TextView f467c;
    private android.widget.TextView d;
    private android.widget.Switch e;
    private java.lang.String[] f;
    private int[] g;

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.lang.String f468a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ java.lang.String f469b;

        /* JADX INFO: renamed from: me.hisn.mygesture.EA$a$a, reason: collision with other inner class name */
        class C0020a extends me.hisn.utils.z {

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            int f471b;

            C0020a(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
                super(context, str, str2, str3, str4, z, i);
                this.f471b = 0;
            }

            @Override // me.hisn.utils.z
            protected boolean b() {
                return false;
            }

            @Override // me.hisn.utils.z
            public void c() {
                super.c();
                a();
                me.hisn.mygesture.EA.this.finish();
            }

            @Override // me.hisn.utils.z
            public void d() {
                int i = this.f471b;
                if (i < 1) {
                    this.f471b = i + 1;
                    new me.hisn.utils.b0().a(me.hisn.mygesture.EA.this.getApplicationContext(), me.hisn.mygesture.R.string.check_again);
                } else {
                    a();
                    me.hisn.utils.k0.b("314262", true);
                    me.hisn.mygesture.EA.this.recreate();
                }
            }
        }

        a(java.lang.String str, java.lang.String str2) {
            this.f468a = str;
            this.f469b = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            int height = (int) (((double) me.hisn.mygesture.EA.this.getWindow().getDecorView().getHeight()) * 0.75d);
            me.hisn.mygesture.EA ea = me.hisn.mygesture.EA.this;
            new me.hisn.mygesture.EA.a.C0020a(ea, this.f468a, this.f469b, ea.getString(me.hisn.mygesture.R.string.accept), me.hisn.mygesture.EA.this.getString(me.hisn.mygesture.R.string.refuse), false, height);
        }
    }

    class b implements java.lang.Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (me.hisn.mygesture.EA.this.f465a.a()) {
                me.hisn.mygesture.EA.this.f467c.setText(me.hisn.mygesture.R.string.state_opened);
            } else {
                me.hisn.mygesture.EA.this.f467c.setText(me.hisn.mygesture.R.string.to_open_text);
            }
            if (me.hisn.mygesture.EA.this.f466b.a()) {
                me.hisn.mygesture.EA.this.d.setText(me.hisn.mygesture.R.string.state_opened);
            } else {
                me.hisn.mygesture.EA.this.d.setText(me.hisn.mygesture.R.string.to_open_text);
            }
        }
    }

    class c implements android.view.View.OnClickListener {
        c() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (me.hisn.mygesture.EA.this.e.isChecked()) {
                me.hisn.mygesture.EA.this.b(true);
            } else if (me.hisn.mygesture.MAS.l()) {
                me.hisn.mygesture.MAS.o();
            }
            me.hisn.mygesture.EA.this.e.setChecked(me.hisn.mygesture.EA.this.o());
        }
    }

    class d implements java.lang.Runnable {
        d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.EA.this.e.setChecked(me.hisn.mygesture.EA.this.o());
        }
    }

    class e extends me.hisn.utils.z {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ boolean f476b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e(me.hisn.mygesture.EA ea, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, boolean z2) {
            super(context, str, str2, str3, str4, z, i);
            this.f476b = z2;
        }

        @Override // me.hisn.utils.z
        public void d() {
            if (this.f476b) {
                me.hisn.mygesture.P.s.edit().putBoolean("is_first_run2", false).apply();
            }
        }
    }

    class f implements java.lang.Runnable {
        f() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (me.hisn.mygesture.P.D && me.hisn.mygesture.P.s.getBoolean("is_first_run2", true)) {
                me.hisn.mygesture.EA.this.c(true);
            }
        }
    }

    class g implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.Switch f478a;

        class a implements java.lang.Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.mygesture.EA.this.b(false);
            }
        }

        class b extends me.hisn.utils.z {
            b(me.hisn.mygesture.EA.g gVar, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
                super(context, str, str2, str3, str4, z, i);
            }

            @Override // me.hisn.utils.z
            public void d() {
            }
        }

        g(android.widget.Switch r2) {
            this.f478a = r2;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            java.lang.String str;
            android.widget.Switch r0 = (android.widget.Switch) view;
            switch (view.getId()) {
                case me.hisn.mygesture.R.id.auto_hide_trigger_zone_switch /* 2131230773 */:
                    me.hisn.mygesture.P.y = r0.isChecked();
                    str = "41421";
                    break;
                case me.hisn.mygesture.R.id.bottom_side_switch /* 2131230792 */:
                    me.hisn.mygesture.P.v = r0.isChecked();
                    me.hisn.mygesture.EA.this.b(false);
                    me.hisn.mygesture.EA.this.a(view);
                    str = "bottom_side_enabled";
                    break;
                case me.hisn.mygesture.R.id.compat_home_switch /* 2131230822 */:
                    me.hisn.mygesture.P.e0 = r0.isChecked();
                    str = "compat_home";
                    break;
                case me.hisn.mygesture.R.id.disable_gesture_on_lock_switch /* 2131230830 */:
                    str = "disable_gesture_on_lock";
                    break;
                case me.hisn.mygesture.R.id.edge_visible_switch /* 2131230848 */:
                    boolean zIsChecked = r0.isChecked();
                    me.hisn.mygesture.P.q0 = zIsChecked;
                    me.hisn.mygesture.MAS.a(zIsChecked);
                    str = null;
                    break;
                case me.hisn.mygesture.R.id.f_draggable_switch /* 2131230856 */:
                    if (r0.isChecked()) {
                        me.hisn.mygesture.EA ea = me.hisn.mygesture.EA.this;
                        new me.hisn.mygesture.EA.g.b(this, ea, null, ea.getString(me.hisn.mygesture.R.string.float_ball_draggable_tip), me.hisn.mygesture.EA.this.getString(android.R.string.ok), null, true, -2);
                    }
                    str = "f41439";
                    break;
                case me.hisn.mygesture.R.id.float_ball_switch /* 2131230868 */:
                    view.postDelayed(new me.hisn.mygesture.EA.g.a(), 500L);
                    str = "f41433";
                    break;
                case me.hisn.mygesture.R.id.hands_up_slow_no_up_switch /* 2131230884 */:
                    me.hisn.mygesture.P.E = r0.isChecked();
                    str = "no_up_for_long";
                    break;
                case me.hisn.mygesture.R.id.hands_up_switch /* 2131230885 */:
                    boolean zIsChecked2 = r0.isChecked();
                    me.hisn.mygesture.P.D = zIsChecked2;
                    if (zIsChecked2) {
                        me.hisn.mygesture.EA.this.c(false);
                    }
                    this.f478a.setEnabled(me.hisn.mygesture.P.D);
                    str = "hands_up_mode";
                    break;
                case me.hisn.mygesture.R.id.hide_from_recents_switch /* 2131230887 */:
                    me.hisn.mygesture.EA.this.a(r0.isChecked());
                    str = "hide_from_recents";
                    break;
                case me.hisn.mygesture.R.id.hide_when_soft_input /* 2131230889 */:
                    me.hisn.mygesture.P.z = r0.isChecked();
                    me.hisn.mygesture.EA.this.b(false);
                    str = "hide_when_soft_input";
                    break;
                case me.hisn.mygesture.R.id.left_side_switch /* 2131230912 */:
                    me.hisn.mygesture.P.t = r0.isChecked();
                    me.hisn.mygesture.EA.this.b(false);
                    me.hisn.mygesture.EA.this.a(view);
                    str = "left_side_enabled";
                    break;
                case me.hisn.mygesture.R.id.optimize_ram_use_switch /* 2131230943 */:
                    str = "min_ram";
                    break;
                case me.hisn.mygesture.R.id.right_side_switch /* 2131230998 */:
                    me.hisn.mygesture.P.u = r0.isChecked();
                    me.hisn.mygesture.EA.this.b(false);
                    me.hisn.mygesture.EA.this.a(view);
                    str = "right_side_enabled";
                    break;
                default:
                    str = null;
                    break;
            }
            if (str != null) {
                me.hisn.mygesture.P.s.edit().putBoolean(str, r0.isChecked()).apply();
            }
        }
    }

    class h implements java.lang.Runnable {
        h() {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.MAS.a(false);
            me.hisn.utils.C.BI(me.hisn.mygesture.EA.this, 82);
        }
    }

    class i implements android.widget.AdapterView.OnItemSelectedListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f482a;

        i(int i) {
            this.f482a = i;
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            java.lang.String str;
            java.lang.String str2;
            boolean z;
            java.lang.String str3 = null;
            switch (adapterView.getId()) {
                case me.hisn.mygesture.R.id.bottom_split_spinner /* 2131230793 */:
                    me.hisn.mygesture.P.f0 = i;
                    me.hisn.mygesture.EA.this.l();
                    str2 = "bottom_split_count";
                    str = str2;
                    z = false;
                    break;
                case me.hisn.mygesture.R.id.edge_anim_spinner /* 2131230835 */:
                    me.hisn.mygesture.P.w = i;
                    z = i > 2;
                    me.hisn.mygesture.MAS.r();
                    if (i == 10) {
                        me.hisn.mygesture.EA.this.q();
                    }
                    str = "edge_anim_type";
                    break;
                case me.hisn.mygesture.R.id.landscape_gesture_spinner /* 2131230905 */:
                    me.hisn.mygesture.P.x = i;
                    str2 = "31424";
                    str = str2;
                    z = false;
                    break;
                case me.hisn.mygesture.R.id.theme_type_spinner /* 2131231053 */:
                    if (this.f482a != i) {
                        me.hisn.utils.u0.a(me.hisn.mygesture.EA.this.getApplicationContext(), i);
                        me.hisn.mygesture.EA.this.p();
                        me.hisn.mygesture.EA.this.recreate();
                        break;
                    }
                default:
                    str = null;
                    z = false;
                    break;
            }
            if (!z || me.hisn.utils.C.CP(me.hisn.mygesture.EA.this.getApplicationContext(), new me.hisn.utils.p0().b(me.hisn.mygesture.EA.this.getApplicationContext()), 0) >= 0) {
                str3 = str;
            } else {
                ((android.widget.Spinner) adapterView).setSelection(0);
            }
            if (str3 != null) {
                me.hisn.mygesture.P.s.edit().putInt(str3, adapterView.getSelectedItemPosition()).apply();
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
        }
    }

    class j implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.Spinner f484a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.AdapterView.OnItemSelectedListener f485b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.Spinner f486c;
        final /* synthetic */ android.widget.Spinner d;
        final /* synthetic */ android.widget.Spinner e;

        j(me.hisn.mygesture.EA ea, android.widget.Spinner spinner, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener, android.widget.Spinner spinner2, android.widget.Spinner spinner3, android.widget.Spinner spinner4) {
            this.f484a = spinner;
            this.f485b = onItemSelectedListener;
            this.f486c = spinner2;
            this.d = spinner3;
            this.e = spinner4;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f484a.setOnItemSelectedListener(this.f485b);
            this.f486c.setOnItemSelectedListener(this.f485b);
            this.d.setOnItemSelectedListener(this.f485b);
            this.e.setOnItemSelectedListener(this.f485b);
        }
    }

    class k extends me.hisn.utils.z {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f487b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        k(me.hisn.mygesture.EA ea, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, android.view.View view) {
            super(context, str, str2, str3, str4, z, i);
            this.f487b = view;
        }

        @Override // me.hisn.utils.z
        public void a(android.app.AlertDialog.Builder builder) {
            super.a(builder);
            builder.setView(this.f487b);
        }

        @Override // me.hisn.utils.z
        public void d() {
        }
    }

    class l implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.z f488a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.Switch f489b;

        l(me.hisn.utils.z zVar, android.widget.Switch r3) {
            this.f488a = zVar;
            this.f489b = r3;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            int id = view.getId();
            if (id != me.hisn.mygesture.R.id.custom_feedback_iv) {
                if (id != me.hisn.mygesture.R.id.feedback_rotate_sw) {
                    return;
                }
                me.hisn.mygesture.P.s.edit().putBoolean("31422", this.f489b.isChecked()).apply();
                me.hisn.mygesture.MAS.r();
                return;
            }
            this.f488a.a();
            android.content.Intent intent = new android.content.Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            me.hisn.mygesture.EA.this.startActivityForResult(intent, 85);
        }
    }

    class m implements android.view.View.OnClickListener {
        m() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.content.Intent intent = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mypanel.Aa.class);
            intent.putExtra("31415", me.hisn.utils.C.CP(me.hisn.mygesture.EA.this.getApplicationContext(), new me.hisn.utils.p0().b(me.hisn.mygesture.EA.this.getApplicationContext()), 0) >= 0 ? 129 : 1);
            intent.putExtra("min_ram", me.hisn.mygesture.P.s.getBoolean("min_ram", false));
            try {
                android.widget.TextView textView = (android.widget.TextView) ((android.widget.LinearLayout) view.getParent()).getChildAt(0);
                if (textView != null) {
                    intent.putExtra("31417", textView.getText());
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            try {
                me.hisn.mygesture.EA.this.startActivityForResult(intent, view.getId());
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    class n implements android.view.View.OnClickListener {
        n() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            new me.hisn.utils.b0().a(me.hisn.mygesture.EA.this.getApplicationContext(), me.hisn.mygesture.R.string.need_actived);
        }
    }

    class o implements java.lang.Runnable {
        o(me.hisn.mygesture.EA ea) {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mygesture.MAS.s();
        }
    }

    class p extends me.hisn.utils.z {
        p(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
            super(context, str, str2, str3, str4, z, i);
        }

        @Override // me.hisn.utils.z
        public void d() {
            me.hisn.mygesture.EA.this.f466b.b();
        }
    }

    class q implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.RelativeLayout.LayoutParams f494a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.RelativeLayout f495b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.TextView f496c;

        q(android.widget.RelativeLayout.LayoutParams layoutParams, android.widget.RelativeLayout relativeLayout, android.widget.TextView textView) {
            this.f494a = layoutParams;
            this.f495b = relativeLayout;
            this.f496c = textView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f494a.leftMargin = (this.f495b.getWidth() - this.f496c.getWidth()) / 2;
            this.f496c.setLayoutParams(this.f494a);
            this.f496c.startAnimation(android.view.animation.AnimationUtils.loadAnimation(me.hisn.mygesture.EA.this.getApplicationContext(), me.hisn.mygesture.R.anim.logo_in_anim));
            this.f496c.setAlpha(1.0f);
        }
    }

    class r implements android.view.View.OnScrollChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f497a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.TextView f498b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.RelativeLayout.LayoutParams f499c;
        final /* synthetic */ android.widget.RelativeLayout d;
        final /* synthetic */ android.widget.TextView e;
        final /* synthetic */ android.widget.ImageView f;

        r(me.hisn.mygesture.EA ea, int i, android.widget.TextView textView, android.widget.RelativeLayout.LayoutParams layoutParams, android.widget.RelativeLayout relativeLayout, android.widget.TextView textView2, android.widget.ImageView imageView) {
            this.f497a = i;
            this.f498b = textView;
            this.f499c = layoutParams;
            this.d = relativeLayout;
            this.e = textView2;
            this.f = imageView;
        }

        @Override // android.view.View.OnScrollChangeListener
        public void onScrollChange(android.view.View view, int i, int i2, int i3, int i4) {
            float f = i2 / this.f497a;
            if (f > 1.0f) {
                this.f498b.setVisibility(0);
                return;
            }
            this.f498b.setVisibility(8);
            float f2 = 1.0f - f;
            this.f499c.leftMargin = (int) (((this.d.getWidth() - this.e.getWidth()) / 2) * f2);
            this.e.setLayoutParams(this.f499c);
            this.e.invalidate();
            this.f.setAlpha(f2);
        }
    }

    class s implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.LinearLayout f500a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.LinearLayout f501b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f502c;
        final /* synthetic */ android.widget.LinearLayout d;
        final /* synthetic */ android.widget.LinearLayout e;
        final /* synthetic */ android.widget.ImageView f;
        final /* synthetic */ android.widget.LinearLayout g;
        final /* synthetic */ android.widget.LinearLayout h;
        final /* synthetic */ android.widget.ImageView i;
        final /* synthetic */ android.widget.LinearLayout j;
        final /* synthetic */ android.widget.LinearLayout k;
        final /* synthetic */ android.widget.ImageView l;

        s(me.hisn.mygesture.EA ea, android.widget.LinearLayout linearLayout, android.widget.LinearLayout linearLayout2, android.widget.ImageView imageView, android.widget.LinearLayout linearLayout3, android.widget.LinearLayout linearLayout4, android.widget.ImageView imageView2, android.widget.LinearLayout linearLayout5, android.widget.LinearLayout linearLayout6, android.widget.ImageView imageView3, android.widget.LinearLayout linearLayout7, android.widget.LinearLayout linearLayout8, android.widget.ImageView imageView4) {
            this.f500a = linearLayout;
            this.f501b = linearLayout2;
            this.f502c = imageView;
            this.d = linearLayout3;
            this.e = linearLayout4;
            this.f = imageView2;
            this.g = linearLayout5;
            this.h = linearLayout6;
            this.i = imageView3;
            this.j = linearLayout7;
            this.k = linearLayout8;
            this.l = imageView4;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.widget.LinearLayout linearLayout;
            android.widget.LinearLayout linearLayout2;
            android.widget.ImageView imageView;
            android.widget.ImageView imageView2 = null;
            if (view == this.f500a) {
                linearLayout2 = this.f501b;
                imageView = this.f502c;
            } else if (view == this.d) {
                linearLayout2 = this.e;
                imageView = this.f;
            } else {
                if (view != this.g) {
                    if (view == this.j) {
                        linearLayout2 = this.k;
                        imageView = this.l;
                    } else {
                        linearLayout = null;
                    }
                    if (imageView2 != null || linearLayout == null) {
                    }
                    int i = 0;
                    if (linearLayout.getVisibility() == 8) {
                        linearLayout.setVisibility(0);
                        i = 180;
                    } else {
                        linearLayout.setVisibility(8);
                    }
                    imageView2.animate().rotation(i).setDuration(200L).start();
                    return;
                }
                linearLayout2 = this.h;
                imageView = this.i;
            }
            android.widget.LinearLayout linearLayout3 = linearLayout2;
            imageView2 = imageView;
            linearLayout = linearLayout3;
            if (imageView2 != null) {
            }
        }
    }

    class t implements android.widget.SeekBar.OnSeekBarChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.SeekBar f503a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.TextView f504b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.SeekBar f505c;
        final /* synthetic */ android.widget.TextView d;
        final /* synthetic */ android.widget.SeekBar e;
        final /* synthetic */ android.widget.SeekBar f;
        final /* synthetic */ android.widget.SeekBar g;

        t(android.widget.SeekBar seekBar, android.widget.TextView textView, android.widget.SeekBar seekBar2, android.widget.TextView textView2, android.widget.SeekBar seekBar3, android.widget.SeekBar seekBar4, android.widget.SeekBar seekBar5) {
            this.f503a = seekBar;
            this.f504b = textView;
            this.f505c = seekBar2;
            this.d = textView2;
            this.e = seekBar3;
            this.f = seekBar4;
            this.g = seekBar5;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to find 'out' block for switch in B:11:0x0044. Please report as an issue. */
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
            int iB;
            int i2;
            int iC;
            int i3;
            android.widget.TextView textView;
            java.lang.StringBuilder sb;
            if (seekBar == this.f503a) {
                textView = this.f504b;
                sb = new java.lang.StringBuilder();
                sb.append(me.hisn.mygesture.EA.this.getString(me.hisn.mygesture.R.string.long_press_time));
                i = (i + 2) * 50;
            } else {
                if (seekBar != this.f505c) {
                    int i4 = 0;
                    int i5 = -1;
                    switch (seekBar.getId()) {
                        case me.hisn.mygesture.R.id.bottom_side_height_seekbar /* 2131230791 */:
                            i4 = me.hisn.mygesture.P.B;
                            i2 = (i * me.hisn.mygesture.P.k0) / 100;
                            iB = -1;
                            i3 = -1;
                            break;
                        case me.hisn.mygesture.R.id.float_ball_size_seekbar /* 2131230867 */:
                            if (i > 0) {
                                int iA = me.hisn.mygesture.a.a(i);
                                i4 = me.hisn.mygesture.P.f536c;
                                i2 = iA;
                                i5 = i2;
                                iB = -1;
                                i3 = -1;
                            }
                            iB = -1;
                            i2 = -1;
                            i3 = -1;
                            break;
                        case me.hisn.mygesture.R.id.float_ball_x_seekbar /* 2131230870 */:
                            iB = me.hisn.mygesture.a.b(i) - me.hisn.mygesture.MAS.h();
                            i4 = me.hisn.mygesture.P.f536c;
                            i2 = -1;
                            i3 = -1;
                            break;
                        case me.hisn.mygesture.R.id.float_ball_y_seekbar /* 2131230871 */:
                            iC = me.hisn.mygesture.a.c(i) - me.hisn.mygesture.MAS.j();
                            i4 = me.hisn.mygesture.P.f536c;
                            i3 = iC;
                            iB = -1;
                            i2 = -1;
                            break;
                        case me.hisn.mygesture.R.id.left_side_height_seekbar /* 2131230910 */:
                            i4 = me.hisn.mygesture.P.L;
                            i2 = (i * me.hisn.mygesture.P.l0) / 10;
                            iB = -1;
                            i3 = -1;
                            break;
                        case me.hisn.mygesture.R.id.left_side_position_seekbar /* 2131230911 */:
                            i4 = me.hisn.mygesture.P.L;
                            iC = (me.hisn.mygesture.P.l0 * i) / 10;
                            i3 = iC;
                            iB = -1;
                            i2 = -1;
                            break;
                        case me.hisn.mygesture.R.id.left_side_width_seekbar /* 2131230913 */:
                            i4 = me.hisn.mygesture.P.L;
                            i5 = (i * me.hisn.mygesture.P.k0) / 100;
                            iB = -1;
                            i2 = -1;
                            i3 = -1;
                            break;
                        case me.hisn.mygesture.R.id.right_side_height_seekbar /* 2131230996 */:
                            i4 = me.hisn.mygesture.P.R;
                            i2 = (i * me.hisn.mygesture.P.l0) / 10;
                            iB = -1;
                            i3 = -1;
                            break;
                        case me.hisn.mygesture.R.id.right_side_position_seekbar /* 2131230997 */:
                            i4 = me.hisn.mygesture.P.R;
                            iC = (me.hisn.mygesture.P.l0 * i) / 10;
                            i3 = iC;
                            iB = -1;
                            i2 = -1;
                            break;
                        case me.hisn.mygesture.R.id.right_side_width_seekbar /* 2131230999 */:
                            i4 = me.hisn.mygesture.P.R;
                            i5 = (i * me.hisn.mygesture.P.k0) / 100;
                            iB = -1;
                            i2 = -1;
                            i3 = -1;
                            break;
                        default:
                            iB = -1;
                            i2 = -1;
                            i3 = -1;
                            break;
                    }
                    if (i4 > 0) {
                        me.hisn.mygesture.MAS.a(i4, i5, i2, iB, i3);
                        return;
                    }
                    return;
                }
                textView = this.d;
                sb = new java.lang.StringBuilder();
                sb.append(me.hisn.mygesture.EA.this.getString(me.hisn.mygesture.R.string.sensitivity_text));
            }
            sb.append(i);
            textView.setText(sb.toString());
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
            if (seekBar == this.f503a || seekBar == this.f505c || seekBar == this.e || seekBar == this.f || seekBar == this.g) {
                return;
            }
            me.hisn.mygesture.MAS.a(true);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
            java.lang.String str;
            int progress = seekBar.getProgress();
            if (seekBar != this.f503a) {
                if (seekBar != this.f505c) {
                    if (seekBar != this.f) {
                        if (seekBar != this.g) {
                            if (seekBar != this.e) {
                                if (!me.hisn.mygesture.P.q0) {
                                    me.hisn.mygesture.MAS.a(false);
                                }
                                switch (seekBar.getId()) {
                                    case me.hisn.mygesture.R.id.bottom_side_height_seekbar /* 2131230791 */:
                                        me.hisn.mygesture.P.S = progress;
                                        if (me.hisn.mygesture.P.j) {
                                            me.hisn.mygesture.EA.this.b(false);
                                        }
                                        str = "bottom_edge_height";
                                        break;
                                    case me.hisn.mygesture.R.id.left_side_height_seekbar /* 2131230910 */:
                                        me.hisn.mygesture.P.I = progress;
                                        str = "left_edge_height";
                                        break;
                                    case me.hisn.mygesture.R.id.left_side_position_seekbar /* 2131230911 */:
                                        me.hisn.mygesture.P.K = progress;
                                        str = "left_edge_position";
                                        break;
                                    case me.hisn.mygesture.R.id.left_side_width_seekbar /* 2131230913 */:
                                        me.hisn.mygesture.P.J = progress;
                                        str = "left_edge_width";
                                        break;
                                    case me.hisn.mygesture.R.id.right_side_height_seekbar /* 2131230996 */:
                                        me.hisn.mygesture.P.M = progress;
                                        str = "right_edge_height";
                                        break;
                                    case me.hisn.mygesture.R.id.right_side_position_seekbar /* 2131230997 */:
                                        me.hisn.mygesture.P.Q = progress;
                                        str = "right_edge_position";
                                        break;
                                    case me.hisn.mygesture.R.id.right_side_width_seekbar /* 2131230999 */:
                                        me.hisn.mygesture.P.O = progress;
                                        str = "right_edge_width";
                                        break;
                                    default:
                                        str = null;
                                        break;
                                }
                            } else {
                                str = "f41436";
                            }
                        } else {
                            str = "f41435";
                        }
                    } else {
                        str = "f41434";
                    }
                } else {
                    me.hisn.mygesture.MAS.c(progress);
                    str = "sensitivity_size";
                }
            } else {
                progress += 2;
                me.hisn.mygesture.P.r = progress;
                str = "long_press_time";
            }
            if (str != null) {
                me.hisn.mygesture.P.s.edit().putInt(str, progress).apply();
            }
        }
    }

    class u implements android.view.View.OnLongClickListener {

        class a implements java.lang.Runnable {
            a(me.hisn.mygesture.EA.u uVar) {
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.mygesture.MAS.s();
            }
        }

        u() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            me.hisn.utils.o.d(me.hisn.mygesture.EA.this.getApplicationContext());
            view.postDelayed(new me.hisn.mygesture.EA.u.a(this), 1000L);
            return true;
        }
    }

    class v implements android.view.View.OnClickListener {

        class a extends me.hisn.utils.z {
            a(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
                super(context, str, str2, str3, str4, z, i);
            }

            @Override // me.hisn.utils.z
            public void c() {
                super.c();
                me.hisn.utils.k0.b("314262", false);
                me.hisn.mygesture.EA.this.finish();
            }

            @Override // me.hisn.utils.z
            public void d() {
            }
        }

        v() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.mygesture.EA ea;
            android.content.Intent intent;
            switch (view.getId()) {
                case me.hisn.mygesture.R.id.crash_log_btn /* 2131230824 */:
                    java.lang.String string = me.hisn.mygesture.EA.this.getSharedPreferences("31410", 0).getString("31420", null);
                    if (string != null) {
                        me.hisn.mygesture.EA.this.a(view.getContext(), string);
                        return;
                    } else {
                        new me.hisn.utils.b0().a(me.hisn.mygesture.EA.this.getApplicationContext(), me.hisn.mygesture.R.string.no_crash_log, 0);
                        return;
                    }
                case me.hisn.mygesture.R.id.f_custom_img /* 2131230854 */:
                    android.content.Intent intent2 = new android.content.Intent("android.intent.action.GET_CONTENT");
                    intent2.setType("image/*");
                    me.hisn.mygesture.EA.this.startActivityForResult(intent2, 86);
                    return;
                case me.hisn.mygesture.R.id.guide_btn /* 2131230882 */:
                    me.hisn.mygesture.EA ea2 = me.hisn.mygesture.EA.this;
                    ea2.a(ea2.getString(me.hisn.mygesture.R.string.user_guide));
                    new me.hisn.utils.b0().a(me.hisn.mygesture.EA.this.getApplicationContext(), me.hisn.mygesture.R.string.cannot_open_tip);
                    return;
                case me.hisn.mygesture.R.id.nav_bar_control_btn /* 2131230931 */:
                    ea = me.hisn.mygesture.EA.this;
                    intent = new android.content.Intent(me.hisn.mygesture.EA.this.getApplicationContext(), (java.lang.Class<?>) me.hisn.mygesture.NavA.class);
                    break;
                case me.hisn.mygesture.R.id.notification_extension_btn /* 2131230938 */:
                    android.content.Intent intent3 = new android.content.Intent(view.getContext(), (java.lang.Class<?>) me.hisn.mygesture.Nex.class);
                    intent3.putExtra("k", me.hisn.utils.C.CP(me.hisn.mygesture.EA.this.getApplicationContext(), new me.hisn.utils.p0().b(me.hisn.mygesture.EA.this.getApplicationContext()), 0) >= 0);
                    try {
                        me.hisn.mygesture.EA.this.startActivity(intent3);
                        return;
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case me.hisn.mygesture.R.id.privacy_btn /* 2131230981 */:
                    int height = (int) (((double) me.hisn.mygesture.EA.this.getWindow().getDecorView().getHeight()) * 0.75d);
                    me.hisn.mygesture.EA ea3 = me.hisn.mygesture.EA.this;
                    new me.hisn.mygesture.EA.v.a(ea3, ea3.getString(me.hisn.mygesture.R.string.privacy), me.hisn.mygesture.EA.this.getString(me.hisn.mygesture.R.string.privacy_policy), me.hisn.mygesture.EA.this.getString(me.hisn.mygesture.R.string.accept), me.hisn.mygesture.EA.this.getString(me.hisn.mygesture.R.string.refuse), false, height);
                    return;
                case me.hisn.mygesture.R.id.s_panel_btn /* 2131231014 */:
                    me.hisn.utils.C.BI(me.hisn.mygesture.EA.this, 81);
                    return;
                case me.hisn.mygesture.R.id.vibrate_settings_btn /* 2131231073 */:
                    ea = me.hisn.mygesture.EA.this;
                    intent = new android.content.Intent(me.hisn.mygesture.EA.this.getApplicationContext(), (java.lang.Class<?>) me.hisn.mygesture.VibrateA.class);
                    break;
                default:
                    return;
            }
            ea.startActivity(intent);
        }
    }

    class w implements android.view.View.OnLongClickListener {
        w(me.hisn.mygesture.EA ea) {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            android.widget.Toast.makeText(view.getContext(), java.lang.String.format(java.util.Locale.getDefault(), "Last touch at %s", new java.text.SimpleDateFormat("MM-dd HH:mm", java.util.Locale.getDefault()).format(new java.util.Date(me.hisn.mygesture.P.t0))), 1).show();
            return true;
        }
    }

    class x implements android.view.View.OnLongClickListener {
        x() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            java.util.ArrayList arrayList = null;
            try {
                arrayList = (java.util.ArrayList) me.hisn.utils.e0.a(me.hisn.mygesture.EA.this.getSharedPreferences("31410", 0).getString("31419", null));
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            if (arrayList != null) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(android.os.Build.BRAND);
                sb.append(" ");
                sb.append(android.os.Build.MODEL);
                sb.append("\n");
                sb.append(android.os.Build.VERSION.SDK_INT);
                sb.append("\n");
                me.hisn.mygesture.EA ea = me.hisn.mygesture.EA.this;
                sb.append(ea.a(ea.getApplicationContext()));
                sb.append("\n\n");
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    sb.append((java.lang.String) arrayList.get(size));
                    sb.append("\n\n");
                }
                me.hisn.mygesture.EA.this.a(view.getContext(), sb.toString());
            }
            return true;
        }
    }

    class y extends me.hisn.utils.z {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ java.lang.String f510b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        y(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, java.lang.String str5) {
            super(context, str, str2, str3, str4, z, i);
            this.f510b = str5;
        }

        @Override // me.hisn.utils.z
        public void d() {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) me.hisn.mygesture.EA.this.getSystemService("clipboard");
            android.content.ClipData clipDataNewPlainText = android.content.ClipData.newPlainText("crash", this.f510b);
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(clipDataNewPlainText);
            }
        }
    }

    class z implements android.view.View.OnClickListener {
        z() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (view.getId() == me.hisn.mygesture.R.id.overlay_permission_state) {
                me.hisn.mygesture.EA.this.f465a.b();
            } else {
                me.hisn.mygesture.EA.this.f466b.b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String a(android.content.Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private void a() {
        new me.hisn.utils.g().a((android.app.Activity) this, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.content.Context context, java.lang.String str) {
        new me.hisn.mygesture.EA.y(context, getString(me.hisn.mygesture.R.string.crash_info), str, getString(me.hisn.mygesture.R.string.copy_text), getString(me.hisn.mygesture.R.string.cancel_text), true, -2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.view.View view) {
        if (me.hisn.mygesture.P.q0) {
            return;
        }
        me.hisn.mygesture.MAS.a(true);
        view.postDelayed(new me.hisn.mygesture.EA.h(), 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(java.lang.String str) {
        new me.hisn.utils.s().a(getApplicationContext(), new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse(str)), null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) {
        java.util.List<android.app.ActivityManager.AppTask> appTasks = ((android.app.ActivityManager) getSystemService("activity")).getAppTasks();
        if (appTasks == null || appTasks.size() <= 0) {
            return;
        }
        appTasks.get(0).setExcludeFromRecents(z2);
    }

    private void a(int[] iArr) {
        if (me.hisn.utils.C.CP(this, new me.hisn.utils.p0().b(getApplicationContext()), 0) < 0) {
            for (int i2 : iArr) {
                android.widget.TextView textView = (android.widget.TextView) findViewById(i2);
                textView.setOnClickListener(new me.hisn.mygesture.EA.n());
                textView.setTextColor(getResources().getColor(me.hisn.mygesture.R.color.dark_grey));
            }
        }
    }

    private void b() {
        android.content.Intent intent = new android.content.Intent(this, (java.lang.Class<?>) me.hisn.mygesture.j.class);
        intent.putExtra("from_flag", me.hisn.utils.C.CP(getApplicationContext(), new me.hisn.utils.p0().b(getApplicationContext()), 0) >= 0 ? 1 : 0);
        startActivityForResult(intent, 80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:24:0x007c  */
    /* JADX WARN: Code duplicated, block: B:29:? A[RETURN, SYNTHETIC] */
    public void b(boolean z2) {
        int i2;
        if (me.hisn.mygesture.MAS.t()) {
            if (z2) {
                new me.hisn.utils.b0().a(getApplicationContext(), me.hisn.mygesture.R.string.running, 0);
                return;
            }
            return;
        }
        if (z2) {
            java.lang.String string = null;
            if (!me.hisn.mygesture.P.t && !me.hisn.mygesture.P.v && !me.hisn.mygesture.P.u) {
                i2 = me.hisn.mygesture.R.string.all_edge_disable_tips;
            } else {
                if (this.f465a.a()) {
                    if (this.f466b.a()) {
                        new me.hisn.mygesture.EA.p(this, null, "\n" + getString(me.hisn.mygesture.R.string.nomas_tips), getString(me.hisn.mygesture.R.string.yes_text), getString(me.hisn.mygesture.R.string.cancel_text), true, -2);
                    } else {
                        i2 = me.hisn.mygesture.R.string.accessibility_permission;
                    }
                    if (string != null) {
                        new me.hisn.utils.b0().a(getApplicationContext(), string, 1);
                    }
                }
                i2 = me.hisn.mygesture.R.string.need_overlay;
            }
            string = getString(i2);
            if (string != null) {
                new me.hisn.utils.b0().a(getApplicationContext(), string, 1);
            }
        }
    }

    private void c() {
        if (this.e == null) {
            android.widget.Switch r0 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.my_gesture_switch);
            this.e = r0;
            r0.setOnClickListener(new me.hisn.mygesture.EA.c());
        }
        this.e.postDelayed(new me.hisn.mygesture.EA.d(), 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(boolean z2) {
        new me.hisn.mygesture.EA.e(this, this, null, getString(me.hisn.mygesture.R.string.hands_up_tips), getString(me.hisn.mygesture.R.string.i_know), "", true, -2, z2);
    }

    private void d() {
        if (this.f465a == null) {
            this.f465a = new me.hisn.utils.f0(this);
            this.f466b = new me.hisn.utils.a(getApplicationContext());
            this.f467c = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.overlay_permission_state);
            this.d = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.accessibility_permission_state);
            me.hisn.mygesture.EA.z zVar = new me.hisn.mygesture.EA.z();
            this.f467c.setOnClickListener(zVar);
            this.d.setOnClickListener(zVar);
        }
        this.f467c.postDelayed(new me.hisn.mygesture.EA.b(), 300L);
    }

    private void e() {
        m();
    }

    private void g() {
        k();
    }

    private void i() {
        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.left_area_toggle_bar);
        android.widget.LinearLayout linearLayout2 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.right_area_toggle_bar);
        android.widget.LinearLayout linearLayout3 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.bottom_area_toggle_bar);
        android.widget.LinearLayout linearLayout4 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.float_ball_toggle_bar);
        me.hisn.mygesture.EA.s sVar = new me.hisn.mygesture.EA.s(this, linearLayout, (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.left_side_custom), (android.widget.ImageView) findViewById(me.hisn.mygesture.R.id.left_expend_btn), linearLayout2, (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.right_side_custom), (android.widget.ImageView) findViewById(me.hisn.mygesture.R.id.right_expend_btn), linearLayout3, (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.bottom_side_custom), (android.widget.ImageView) findViewById(me.hisn.mygesture.R.id.bottom_expend_btn), linearLayout4, (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.float_ball_custom), (android.widget.ImageView) findViewById(me.hisn.mygesture.R.id.float_ball_expend_btn));
        linearLayout.setOnClickListener(sVar);
        linearLayout2.setOnClickListener(sVar);
        linearLayout3.setOnClickListener(sVar);
        linearLayout4.setOnClickListener(sVar);
    }

    private void j() {
        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.guide_btn);
        android.widget.LinearLayout linearLayout2 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.crash_log_btn);
        android.widget.LinearLayout linearLayout3 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.s_panel_btn);
        android.widget.LinearLayout linearLayout4 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.privacy_btn);
        android.widget.LinearLayout linearLayout5 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.gesture_blacklist_btn);
        android.widget.LinearLayout linearLayout6 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.notification_extension_btn);
        android.widget.LinearLayout linearLayout7 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.vibrate_settings_btn);
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.f_custom_img);
        textView.setOnLongClickListener(new me.hisn.mygesture.EA.u());
        android.widget.LinearLayout linearLayout8 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.nav_bar_control_layout);
        if (new me.hisn.utils.d().c(getApplicationContext()) < 5 || android.os.Build.VERSION.SDK_INT > 29) {
            linearLayout8.setVisibility(8);
        } else {
            linearLayout8 = (android.widget.LinearLayout) findViewById(me.hisn.mygesture.R.id.nav_bar_control_btn);
        }
        me.hisn.mygesture.EA.v vVar = new me.hisn.mygesture.EA.v();
        linearLayout.setOnClickListener(vVar);
        linearLayout2.setOnClickListener(vVar);
        linearLayout4.setOnClickListener(vVar);
        linearLayout3.setOnClickListener(vVar);
        linearLayout5.setOnClickListener(vVar);
        linearLayout6.setOnClickListener(vVar);
        linearLayout7.setOnClickListener(vVar);
        linearLayout8.setOnClickListener(vVar);
        textView.setOnClickListener(vVar);
        android.widget.TextView textView2 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.version_name);
        java.lang.String string = getString(me.hisn.mygesture.R.string.app_name);
        textView2.setText(java.lang.String.format("%s %s", string.substring(0, java.lang.Math.min(string.length(), 9)), a((android.content.Context) this)));
        android.widget.TextView textView3 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.count_tv);
        textView3.setText(java.lang.String.format(getString(me.hisn.mygesture.R.string.count_format), java.lang.Integer.valueOf((int) ((((java.lang.System.currentTimeMillis() - me.hisn.mygesture.P.u0) / 1000) / 60) / 60)), java.lang.Integer.valueOf(me.hisn.mygesture.P.s0)));
        textView3.setOnLongClickListener(new me.hisn.mygesture.EA.w(this));
        linearLayout2.setOnLongClickListener(new me.hisn.mygesture.EA.x());
    }

    private void k() {
        android.widget.SeekBar seekBar = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.sensitivity_size);
        int i2 = me.hisn.mygesture.P.s.getInt("sensitivity_size", 3);
        seekBar.setProgress(i2);
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.sensitivity_size_tv);
        textView.setText(getString(me.hisn.mygesture.R.string.sensitivity_text) + i2);
        android.widget.SeekBar seekBar2 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.left_side_height_seekbar);
        android.widget.SeekBar seekBar3 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.left_side_width_seekbar);
        android.widget.SeekBar seekBar4 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.left_side_position_seekbar);
        android.widget.SeekBar seekBar5 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.right_side_height_seekbar);
        android.widget.SeekBar seekBar6 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.right_side_width_seekbar);
        android.widget.SeekBar seekBar7 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.right_side_position_seekbar);
        android.widget.SeekBar seekBar8 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.bottom_side_height_seekbar);
        android.widget.SeekBar seekBar9 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.float_ball_x_seekbar);
        android.widget.SeekBar seekBar10 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.float_ball_y_seekbar);
        android.widget.SeekBar seekBar11 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.float_ball_size_seekbar);
        android.widget.SeekBar seekBar12 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.long_press_time_seek);
        android.widget.TextView textView2 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.long_press_time_text);
        seekBar2.setProgress(me.hisn.mygesture.P.I);
        seekBar3.setProgress(me.hisn.mygesture.P.J);
        seekBar4.setProgress(me.hisn.mygesture.P.K);
        seekBar5.setProgress(me.hisn.mygesture.P.M);
        seekBar6.setProgress(me.hisn.mygesture.P.O);
        seekBar7.setProgress(me.hisn.mygesture.P.Q);
        seekBar8.setProgress(me.hisn.mygesture.P.S);
        seekBar9.setProgress(me.hisn.mygesture.P.s.getInt("f41434", 0));
        seekBar10.setProgress(me.hisn.mygesture.P.s.getInt("f41435", me.hisn.mygesture.P.l0 / 2));
        seekBar11.setProgress(me.hisn.mygesture.P.s.getInt("f41436", 50));
        seekBar12.setProgress(me.hisn.mygesture.P.r);
        textView2.setText(getString(me.hisn.mygesture.R.string.long_press_time) + (me.hisn.mygesture.P.r * 50));
        me.hisn.mygesture.EA.t tVar = new me.hisn.mygesture.EA.t(seekBar12, textView2, seekBar, textView, seekBar11, seekBar9, seekBar10);
        seekBar2.setOnSeekBarChangeListener(tVar);
        seekBar3.setOnSeekBarChangeListener(tVar);
        seekBar4.setOnSeekBarChangeListener(tVar);
        seekBar5.setOnSeekBarChangeListener(tVar);
        seekBar6.setOnSeekBarChangeListener(tVar);
        seekBar7.setOnSeekBarChangeListener(tVar);
        seekBar8.setOnSeekBarChangeListener(tVar);
        seekBar12.setOnSeekBarChangeListener(tVar);
        seekBar.setOnSeekBarChangeListener(tVar);
        seekBar11.setOnSeekBarChangeListener(tVar);
        seekBar9.setOnSeekBarChangeListener(tVar);
        seekBar10.setOnSeekBarChangeListener(tVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        this.g = new int[]{me.hisn.mygesture.R.id.l_r_key, me.hisn.mygesture.R.id.l_u_key, me.hisn.mygesture.R.id.l_d_key, me.hisn.mygesture.R.id.s_l_r_key, me.hisn.mygesture.R.id.s_l_u_key, me.hisn.mygesture.R.id.s_l_d_key, me.hisn.mygesture.R.id.r_l_key, me.hisn.mygesture.R.id.r_u_key, me.hisn.mygesture.R.id.r_d_key, me.hisn.mygesture.R.id.s_r_l_key, me.hisn.mygesture.R.id.s_r_u_key, me.hisn.mygesture.R.id.s_r_d_key, me.hisn.mygesture.R.id.b1_u_key, me.hisn.mygesture.R.id.b2_u_key, me.hisn.mygesture.R.id.b3_u_key, me.hisn.mygesture.R.id.s_b1_u_key, me.hisn.mygesture.R.id.s_b2_u_key, me.hisn.mygesture.R.id.s_b3_u_key, me.hisn.mygesture.R.id.b_l_key, me.hisn.mygesture.R.id.b_r_key, me.hisn.mygesture.R.id.s_b_l_key, me.hisn.mygesture.R.id.s_b_r_key, me.hisn.mygesture.R.id.f_l_key, me.hisn.mygesture.R.id.f_r_key, me.hisn.mygesture.R.id.f_u_key, me.hisn.mygesture.R.id.f_d_key, me.hisn.mygesture.R.id.s_f_l_key, me.hisn.mygesture.R.id.s_f_r_key, me.hisn.mygesture.R.id.s_f_u_key, me.hisn.mygesture.R.id.s_f_d_key, me.hisn.mygesture.R.id.f_click_key, me.hisn.mygesture.R.id.f_long_click_key};
        this.f = new java.lang.String[]{"left_edge_swipe_right_key", "left_edge_swipe_up_key", "left_edge_swipe_down_key", "slow_left_edge_swipe_right_key", "slow_left_edge_swipe_up_key", "slow_left_edge_swipe_down_key", "right_edge_swipe_left_key", "right_edge_swipe_up_key", "right_edge_swipe_down_key", "slow_right_edge_swipe_left_key", "slow_right_edge_swipe_up_key", "slow_right_edge_swipe_down_key", "left_of_bottom_edge_swipe_up_key", "center_of_bottom_edge_swipe_up_key", "right_of_bottom_edge_swipe_up_key", "slow_left_of_bottom_edge_swipe_up_key", "slow_center_of_bottom_edge_swipe_up_key", "slow_right_of_bottom_edge_swipe_up_key", "b31415", "b31416", "b31418", "b31417", "f41425", "f41426", "f41427", "f41428", "f41429", "f41430", "f41432", "f41431", "f41437", "f41438"};
        me.hisn.mygesture.EA.m mVar = new me.hisn.mygesture.EA.m();
        int length = this.g.length;
        for (int i2 = 0; i2 < length; i2++) {
            android.widget.TextView textView = (android.widget.TextView) findViewById(this.g[i2]);
            textView.setOnClickListener(mVar);
            textView.setText(me.hisn.mygesture.P.s.getString(this.f[i2] + "_l", getString(me.hisn.mygesture.R.string.select_text)));
            if (i2 > 11 && i2 < 18) {
                android.view.View view = (android.view.View) textView.getParent();
                if (i2 % 3 > me.hisn.mygesture.P.f0) {
                    view.setVisibility(8);
                } else {
                    view.setVisibility(0);
                }
            }
        }
        a(new int[]{me.hisn.mygesture.R.id.s_f_l_key, me.hisn.mygesture.R.id.s_f_r_key, me.hisn.mygesture.R.id.s_f_u_key, me.hisn.mygesture.R.id.s_f_d_key, me.hisn.mygesture.R.id.f_click_key, me.hisn.mygesture.R.id.f_long_click_key});
    }

    private void m() {
        android.widget.Spinner spinner = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.edge_anim_spinner);
        int length = getResources().getStringArray(me.hisn.mygesture.R.array.edge_anim).length;
        int i2 = me.hisn.mygesture.P.w;
        if (i2 <= length - 1) {
            spinner.setSelection(i2);
        }
        android.widget.Spinner spinner2 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.bottom_split_spinner);
        spinner2.setSelection(me.hisn.mygesture.P.f0);
        android.widget.Spinner spinner3 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.theme_type_spinner);
        int iC = me.hisn.utils.u0.c(getApplicationContext());
        spinner3.setSelection(iC);
        android.widget.Spinner spinner4 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.landscape_gesture_spinner);
        spinner4.setSelection(me.hisn.mygesture.P.x);
        spinner.postDelayed(new me.hisn.mygesture.EA.j(this, spinner, new me.hisn.mygesture.EA.i(iC), spinner2, spinner3, spinner4), 500L);
    }

    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    private void n() {
        android.widget.Switch r1 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.hide_when_soft_input);
        android.widget.Switch r2 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.compat_home_switch);
        r2.setChecked(me.hisn.mygesture.P.e0);
        android.widget.Switch r3 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.hands_up_switch);
        android.widget.Switch r4 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.hands_up_slow_no_up_switch);
        r4.setEnabled(me.hisn.mygesture.P.D);
        r3.post(new me.hisn.mygesture.EA.f());
        android.widget.Switch r5 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.edge_visible_switch);
        r5.setChecked(me.hisn.mygesture.P.q0);
        android.widget.Switch r6 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.optimize_ram_use_switch);
        r6.setChecked(me.hisn.mygesture.P.s.getBoolean("min_ram", false));
        android.widget.Switch r7 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.disable_gesture_on_lock_switch);
        r7.setChecked(me.hisn.mygesture.P.s.getBoolean("disable_gesture_on_lock", false));
        r1.setChecked(me.hisn.mygesture.P.z);
        r3.setChecked(me.hisn.mygesture.P.D);
        r4.setChecked(me.hisn.mygesture.P.E);
        android.widget.Switch r9 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.hide_from_recents_switch);
        r9.setChecked(me.hisn.mygesture.P.s.getBoolean("hide_from_recents", false));
        android.widget.Switch r10 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.left_side_switch);
        android.widget.Switch r11 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.right_side_switch);
        android.widget.Switch r12 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.bottom_side_switch);
        android.widget.Switch r13 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.float_ball_switch);
        android.widget.Switch r14 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.f_draggable_switch);
        android.widget.Switch r15 = (android.widget.Switch) findViewById(me.hisn.mygesture.R.id.auto_hide_trigger_zone_switch);
        r15.setChecked(me.hisn.mygesture.P.y);
        r10.setChecked(me.hisn.mygesture.P.t);
        r11.setChecked(me.hisn.mygesture.P.u);
        r12.setChecked(me.hisn.mygesture.P.v);
        r13.setChecked(me.hisn.mygesture.P.s.getBoolean("f41433", false));
        r14.setChecked(me.hisn.utils.k0.a("f41439", false));
        me.hisn.mygesture.EA.g gVar = new me.hisn.mygesture.EA.g(r4);
        r1.setOnClickListener(gVar);
        r3.setOnClickListener(gVar);
        r4.setOnClickListener(gVar);
        r2.setOnClickListener(gVar);
        r10.setOnClickListener(gVar);
        r11.setOnClickListener(gVar);
        r12.setOnClickListener(gVar);
        r13.setOnClickListener(gVar);
        r14.setOnClickListener(gVar);
        r5.setOnClickListener(gVar);
        r9.setOnClickListener(gVar);
        r6.setOnClickListener(gVar);
        r7.setOnClickListener(gVar);
        r15.setOnClickListener(gVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean o() {
        if (me.hisn.mygesture.P.m0) {
            return android.os.Build.VERSION.SDK_INT >= 22 ? me.hisn.mygesture.MAS.l() : this.f465a.a();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void p() {
        android.content.Intent intent = new android.content.Intent(this, (java.lang.Class<?>) me.hisn.utils.Ks.class);
        intent.addFlags(32768);
        try {
            startActivity(intent);
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void q() {
        android.view.View viewInflate = android.view.View.inflate(getApplicationContext(), me.hisn.mygesture.R.layout.custom_feedback_view, null);
        android.widget.ImageView imageView = (android.widget.ImageView) viewInflate.findViewById(me.hisn.mygesture.R.id.custom_feedback_iv);
        imageView.setImageDrawable(me.hisn.mygesture.d.a(getApplicationContext()));
        me.hisn.mygesture.EA.k kVar = new me.hisn.mygesture.EA.k(this, this, getString(me.hisn.mygesture.R.string.custom_image), null, null, null, true, -2, viewInflate);
        android.widget.Switch r0 = (android.widget.Switch) viewInflate.findViewById(me.hisn.mygesture.R.id.feedback_rotate_sw);
        r0.setChecked(me.hisn.mygesture.P.s.getBoolean("31422", true));
        me.hisn.mygesture.EA.l lVar = new me.hisn.mygesture.EA.l(kVar, r0);
        imageView.setOnClickListener(lVar);
        r0.setOnClickListener(lVar);
    }

    @Override // me.hisn.utils.e
    public void h() {
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.big_title_bar);
        android.widget.RelativeLayout relativeLayout = (android.widget.RelativeLayout) findViewById(me.hisn.mygesture.R.id.logo_layout);
        android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) textView.getLayoutParams();
        android.widget.ImageView imageView = (android.widget.ImageView) findViewById(me.hisn.mygesture.R.id.logo_view);
        textView.post(new me.hisn.mygesture.EA.q(layoutParams, relativeLayout, textView));
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            android.widget.TextView textView2 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.title_bar);
            findViewById(me.hisn.mygesture.R.id.scroll_layout).setOnScrollChangeListener(new me.hisn.mygesture.EA.r(this, new me.hisn.utils.l().a(getApplicationContext(), 200.0f), textView2, layoutParams, relativeLayout, textView, imageView));
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i2, int i3, android.content.Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 == 80) {
            if (i3 == -1) {
                if (me.hisn.utils.C.CP(this, new me.hisn.utils.p0().b(getApplicationContext()), 0) >= 0) {
                    new me.hisn.utils.b0().a(getApplicationContext(), me.hisn.mygesture.R.string.active_tip, 0);
                    return;
                } else {
                    me.hisn.mygesture.P.A = true;
                    return;
                }
            }
            return;
        }
        if (i2 == 81) {
            return;
        }
        try {
            if (i2 == 86) {
                if (i3 != -1 || intent.getData() == null) {
                    return;
                }
                android.graphics.Bitmap bitmapDecodeStream = android.graphics.BitmapFactory.decodeStream(getContentResolver().openInputStream(intent.getData()));
                if (bitmapDecodeStream != null) {
                    me.hisn.utils.o.a(getApplicationContext(), bitmapDecodeStream);
                    new android.os.Handler(getMainLooper()).postDelayed(new me.hisn.mygesture.EA.o(this), 1000L);
                }
            } else {
                if (i2 != 85) {
                    if (i3 == -1) {
                        int[] iArr = this.g;
                        int[] iArrCopyOf = java.util.Arrays.copyOf(iArr, iArr.length + 1);
                        iArrCopyOf[this.g.length] = i2;
                        int iGP = me.hisn.utils.C.GP(this, iArrCopyOf);
                        if (iGP != -1) {
                            android.widget.TextView textView = (android.widget.TextView) findViewById(i2);
                            java.lang.String str = this.f[iGP];
                            textView.setText(me.hisn.utils.h0.a(getApplicationContext(), intent, str, me.hisn.mygesture.P.s, false));
                            if (me.hisn.utils.k0.a(str + "_k", 0) == 39) {
                                android.content.Intent intent2 = new android.content.Intent(getApplicationContext(), (java.lang.Class<?>) me.hisn.utils.ThreeInOneManagerA.class);
                                intent2.putExtra("31415", str);
                                startActivity(intent2);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (i3 != -1 || intent.getData() == null) {
                    return;
                }
                android.graphics.Bitmap bitmapDecodeStream2 = android.graphics.BitmapFactory.decodeStream(getContentResolver().openInputStream(intent.getData()));
                if (bitmapDecodeStream2 != null) {
                    me.hisn.mygesture.d.a(getApplicationContext(), bitmapDecodeStream2);
                }
            }
        } catch (java.io.FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        p();
        super.onBackPressed();
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.ea);
        j();
        me.hisn.utils.C.CI(this, 36);
        n();
        i();
        me.hisn.utils.C.CI(this, 34);
        l();
        a(me.hisn.mygesture.P.s.getBoolean("hide_from_recents", false));
        if (!me.hisn.utils.k0.a("314262", false)) {
            findViewById(me.hisn.mygesture.R.id.logo_view).post(new me.hisn.mygesture.EA.a(getString(me.hisn.mygesture.R.string.privacy), getString(me.hisn.mygesture.R.string.privacy_policy)));
        } else if (me.hisn.mygesture.P.s.getBoolean("is_first_run", true)) {
            startActivity(new android.content.Intent(this, (java.lang.Class<?>) me.hisn.mygesture.Guide.class));
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        me.hisn.utils.C.BI(this, 83);
        me.hisn.utils.C.BI(this, 82);
    }
}
