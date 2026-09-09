package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class VibrateA extends me.hisn.utils.e {

    class a implements android.widget.AdapterView.OnItemSelectedListener {

        /* JADX INFO: renamed from: me.hisn.mygesture.VibrateA$a$a, reason: collision with other inner class name */
        class C0022a implements android.widget.SeekBar.OnSeekBarChangeListener {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.widget.TextView f541a;

            C0022a(me.hisn.mygesture.VibrateA.a aVar, android.widget.TextView textView) {
                this.f541a = textView;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
                this.f541a.setText(i + "");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
            }
        }

        class b extends me.hisn.utils.z {

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            final /* synthetic */ android.widget.SeekBar f542b;

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            final /* synthetic */ android.view.View f543c;
            final /* synthetic */ android.view.View d;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            b(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i, android.widget.SeekBar seekBar, android.view.View view, android.view.View view2) {
                super(context, str, str2, str3, str4, z, i);
                this.f542b = seekBar;
                this.f543c = view;
                this.d = view2;
            }

            @Override // me.hisn.utils.z
            public void a(android.app.AlertDialog.Builder builder) {
                super.a(builder);
                builder.setView(this.d);
            }

            @Override // me.hisn.utils.z
            public void d() {
                if (this.f542b.getProgress() == 0) {
                    ((android.widget.Spinner) this.f543c).setSelection(0);
                } else {
                    me.hisn.mygesture.VibrateA.a.this.a(this.f543c, this.f542b.getProgress() * (-1));
                }
            }
        }

        a() {
        }

        private void a(android.view.View view) {
            android.view.View viewInflate = android.view.View.inflate(view.getContext(), me.hisn.mygesture.R.layout.vibrate_setting, null);
            android.widget.TextView textView = (android.widget.TextView) viewInflate.findViewById(me.hisn.mygesture.R.id.vibrator_strength_tv);
            android.widget.SeekBar seekBar = (android.widget.SeekBar) viewInflate.findViewById(me.hisn.mygesture.R.id.vibrator_strength_seek_bar);
            seekBar.setOnSeekBarChangeListener(new me.hisn.mygesture.VibrateA.a.C0022a(this, textView));
            new me.hisn.mygesture.VibrateA.a.b(view.getContext(), me.hisn.mygesture.VibrateA.this.getString(me.hisn.mygesture.R.string.vibrator_strength_text), null, me.hisn.mygesture.VibrateA.this.getString(me.hisn.mygesture.R.string.yes_text), null, false, -2, seekBar, view, viewInflate);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(android.view.View view, int i) {
            java.lang.String str;
            switch (view.getId()) {
                case me.hisn.mygesture.R.id.long_press_vibrate_spinner /* 2131230924 */:
                    me.hisn.mygesture.P.f = i;
                    str = "41417";
                    break;
                case me.hisn.mygesture.R.id.panel_volume_tuner_spinner /* 2131230973 */:
                    me.hisn.mygesture.P.h = i;
                    str = "41419";
                    break;
                case me.hisn.mygesture.R.id.slide_switch_vibrate_spinner /* 2131231030 */:
                    me.hisn.mygesture.P.g = i;
                    str = "41418";
                    break;
                case me.hisn.mygesture.R.id.trigger_vibrate_spinner /* 2131231071 */:
                    me.hisn.mygesture.P.e = i;
                    str = "41416";
                    break;
                case me.hisn.mygesture.R.id.when_landscape_spinner /* 2131231082 */:
                    me.hisn.mygesture.P.i = i;
                    str = "41420";
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
        public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            if (i == 2) {
                a(adapterView);
            } else {
                a(adapterView, i);
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.Spinner f544a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.AdapterView.OnItemSelectedListener f545b;

        b(me.hisn.mygesture.VibrateA vibrateA, android.widget.Spinner spinner, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
            this.f544a = spinner;
            this.f545b = onItemSelectedListener;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f544a.setOnItemSelectedListener(this.f545b);
        }
    }

    private void a(android.widget.Spinner spinner, int i, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
        if (i < 0) {
            i = 2;
        }
        spinner.setSelection(i);
        spinner.postDelayed(new me.hisn.mygesture.VibrateA.b(this, spinner, onItemSelectedListener), 300L);
    }

    private void i() {
        android.widget.Spinner spinner = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.trigger_vibrate_spinner);
        android.widget.Spinner spinner2 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.long_press_vibrate_spinner);
        android.widget.Spinner spinner3 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.slide_switch_vibrate_spinner);
        android.widget.Spinner spinner4 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.panel_volume_tuner_spinner);
        android.widget.Spinner spinner5 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.when_landscape_spinner);
        me.hisn.mygesture.VibrateA.a aVar = new me.hisn.mygesture.VibrateA.a();
        a(spinner, me.hisn.mygesture.P.e, aVar);
        a(spinner2, me.hisn.mygesture.P.f, aVar);
        a(spinner3, me.hisn.mygesture.P.g, aVar);
        a(spinner4, me.hisn.mygesture.P.h, aVar);
        a(spinner5, me.hisn.mygesture.P.i, aVar);
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_vibrate);
        i();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            return;
        }
        finish();
    }
}
