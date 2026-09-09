package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class ColorPicker extends me.hisn.utils.t0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.widget.TextView f639a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private android.widget.TextView f640b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.widget.TextView f641c;
    private android.widget.TextView d;
    private android.widget.TextView e;
    private android.widget.TextView f;
    private int g;
    private int h;
    private int i;
    private int j;
    private android.view.View k;
    private android.widget.RelativeLayout.LayoutParams l;
    private int m;
    private int n;
    private int o;
    private int p;

    class a implements android.view.View.OnClickListener {
        a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.mypanel.ColorPicker.this.onBackPressed();
        }
    }

    class b implements android.view.View.OnClickListener {
        b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (view.getId() == me.hisn.mygesture.R.id.color_save_btn) {
                me.hisn.mypanel.ColorPicker.this.l();
            }
            me.hisn.mypanel.ColorPicker.this.finish();
        }
    }

    private class c implements android.widget.SeekBar.OnSeekBarChangeListener {
        private c() {
        }

        /* synthetic */ c(me.hisn.mypanel.ColorPicker colorPicker, me.hisn.mypanel.ColorPicker.a aVar) {
            this();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
            java.lang.String hexString;
            if (seekBar.getId() == me.hisn.mygesture.R.id.edge_shadow_width_seek) {
                me.hisn.mypanel.ColorPicker.this.e.setText(java.lang.Integer.toString(i));
                me.hisn.mypanel.ColorPicker.this.h = i;
                me.hisn.mypanel.ColorPicker.this.b(i);
                return;
            }
            if (seekBar.getId() == me.hisn.mygesture.R.id.touch_bar_height_seek) {
                me.hisn.mypanel.ColorPicker.this.f.setText(java.lang.Integer.toString(i));
                me.hisn.mypanel.ColorPicker.this.i = i;
                return;
            }
            if (i < 16) {
                hexString = "0" + java.lang.Integer.toHexString(i);
            } else {
                hexString = java.lang.Integer.toHexString(i);
            }
            switch (seekBar.getId()) {
                case me.hisn.mygesture.R.id.edge_shadow_alpha_seek /* 2131230836 */:
                    me.hisn.mypanel.ColorPicker.this.d.setText(hexString);
                    me.hisn.mypanel.ColorPicker.this.p = i;
                    break;
                case me.hisn.mygesture.R.id.edge_shadow_blue_seek /* 2131230838 */:
                    me.hisn.mypanel.ColorPicker.this.f641c.setText(hexString);
                    me.hisn.mypanel.ColorPicker.this.o = i;
                    break;
                case me.hisn.mygesture.R.id.edge_shadow_green_seek /* 2131230840 */:
                    me.hisn.mypanel.ColorPicker.this.f640b.setText(hexString);
                    me.hisn.mypanel.ColorPicker.this.n = i;
                    break;
                case me.hisn.mygesture.R.id.edge_shadow_red_seek /* 2131230843 */:
                    me.hisn.mypanel.ColorPicker.this.f639a.setText(hexString);
                    me.hisn.mypanel.ColorPicker.this.m = i;
                    break;
            }
            me.hisn.mypanel.ColorPicker colorPicker = me.hisn.mypanel.ColorPicker.this;
            colorPicker.a(android.graphics.Color.argb(colorPicker.p, me.hisn.mypanel.ColorPicker.this.m, me.hisn.mypanel.ColorPicker.this.n, me.hisn.mypanel.ColorPicker.this.o));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        android.graphics.drawable.Drawable background = this.k.getBackground();
        background.setTint(i);
        this.k.setBackground(background);
        ((android.widget.ImageView) findViewById(me.hisn.mygesture.R.id.color_view)).setColorFilter(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i) {
        android.widget.RelativeLayout.LayoutParams layoutParams = this.l;
        layoutParams.width = (this.j * i) / 100;
        layoutParams.height = -1;
        this.k.setLayoutParams(layoutParams);
        this.k.requestLayout();
    }

    private void h() {
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.color_set_cancel_btn);
        android.widget.TextView textView2 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.color_save_btn);
        me.hisn.mypanel.ColorPicker.b bVar = new me.hisn.mypanel.ColorPicker.b();
        textView.setOnClickListener(bVar);
        textView2.setOnClickListener(bVar);
    }

    private void i() {
        this.m = android.graphics.Color.red(this.g);
        this.n = android.graphics.Color.green(this.g);
        this.o = android.graphics.Color.blue(this.g);
        this.p = android.graphics.Color.alpha(this.g);
    }

    private void j() {
        this.g = getIntent().getIntExtra("edge_shadow_color", 0);
        this.h = getIntent().getIntExtra("edge_shadow_width", 5);
        this.j = getIntent().getIntExtra("screen_width", 1080);
        this.i = getIntent().getIntExtra("touch_bar_height", 16);
        java.lang.String stringExtra = getIntent().getStringExtra("title");
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.title_text);
        textView.setText(stringExtra);
        textView.setOnClickListener(new me.hisn.mypanel.ColorPicker.a());
        if (getString(me.hisn.mygesture.R.string.panel_back_color).equals(stringExtra)) {
            findViewById(me.hisn.mygesture.R.id.edge_shadow_img).setVisibility(8);
            findViewById(me.hisn.mygesture.R.id.width_layout).setVisibility(8);
        }
        if (getString(me.hisn.mygesture.R.string.touch_bar_appearance).equals(stringExtra)) {
            findViewById(me.hisn.mygesture.R.id.edge_shadow_img).setVisibility(8);
            findViewById(me.hisn.mygesture.R.id.height_layout).setVisibility(0);
        }
        if (this.h < 0) {
            ((android.view.View) findViewById(me.hisn.mygesture.R.id.edge_shadow_width_seek).getParent()).setVisibility(8);
        }
    }

    private void k() {
        java.lang.String hexString;
        java.lang.String hexString2;
        java.lang.String hexString3;
        java.lang.String hexString4;
        android.view.View viewFindViewById = findViewById(me.hisn.mygesture.R.id.edge_shadow_img);
        this.k = viewFindViewById;
        this.l = (android.widget.RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
        b(this.h);
        a(this.g);
        this.f639a = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.edge_shadow_red_value);
        this.f640b = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.edge_shadow_green_value);
        this.f641c = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.edge_shadow_blue_value);
        this.d = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.edge_shadow_alpha_value);
        this.e = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.edge_shadow_width_value);
        this.f = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.touch_bar_height_value);
        android.widget.SeekBar seekBar = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.edge_shadow_red_seek);
        android.widget.SeekBar seekBar2 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.edge_shadow_green_seek);
        android.widget.SeekBar seekBar3 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.edge_shadow_blue_seek);
        android.widget.SeekBar seekBar4 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.edge_shadow_alpha_seek);
        android.widget.SeekBar seekBar5 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.edge_shadow_width_seek);
        android.widget.SeekBar seekBar6 = (android.widget.SeekBar) findViewById(me.hisn.mygesture.R.id.touch_bar_height_seek);
        seekBar6.setMax(getIntent().getIntExtra("max_height", 30));
        android.widget.TextView textView = this.f639a;
        int i = this.m;
        if (i < 16) {
            hexString = "0" + java.lang.Integer.toHexString(this.m);
        } else {
            hexString = java.lang.Integer.toHexString(i);
        }
        textView.setText(hexString);
        android.widget.TextView textView2 = this.f640b;
        int i2 = this.n;
        if (i2 < 16) {
            hexString2 = "0" + java.lang.Integer.toHexString(this.n);
        } else {
            hexString2 = java.lang.Integer.toHexString(i2);
        }
        textView2.setText(hexString2);
        android.widget.TextView textView3 = this.f641c;
        int i3 = this.o;
        if (i3 < 16) {
            hexString3 = "0" + java.lang.Integer.toHexString(this.o);
        } else {
            hexString3 = java.lang.Integer.toHexString(i3);
        }
        textView3.setText(hexString3);
        android.widget.TextView textView4 = this.d;
        int i4 = this.p;
        if (i4 < 16) {
            hexString4 = "0" + java.lang.Integer.toHexString(this.p);
        } else {
            hexString4 = java.lang.Integer.toHexString(i4);
        }
        textView4.setText(hexString4);
        this.e.setText(this.h + "");
        this.f.setText(this.i + "");
        seekBar.setProgress(this.m);
        seekBar2.setProgress(this.n);
        seekBar3.setProgress(this.o);
        seekBar4.setProgress(this.p);
        seekBar5.setProgress(this.h);
        seekBar6.setProgress(this.i);
        me.hisn.mypanel.ColorPicker.c cVar = new me.hisn.mypanel.ColorPicker.c(this, null);
        seekBar.setOnSeekBarChangeListener(cVar);
        seekBar2.setOnSeekBarChangeListener(cVar);
        seekBar3.setOnSeekBarChangeListener(cVar);
        seekBar4.setOnSeekBarChangeListener(cVar);
        seekBar5.setOnSeekBarChangeListener(cVar);
        seekBar6.setOnSeekBarChangeListener(cVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        this.g = android.graphics.Color.argb(this.p, this.m, this.n, this.o);
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("edge_shadow_color", this.g);
        intent.putExtra("edge_shadow_width", this.h);
        intent.putExtra("touch_bar_height", this.i);
        setResult(-1, intent);
        if (android.graphics.Color.alpha(this.g) < 48) {
            new me.hisn.utils.b0().a(getApplicationContext(), me.hisn.mygesture.R.string.low_alpha_tip, 0);
        }
    }

    @Override // me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_color_picker);
        j();
        i();
        k();
        h();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}
