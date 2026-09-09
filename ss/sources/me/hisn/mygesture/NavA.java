package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class NavA extends me.hisn.utils.e {

    class a implements android.widget.AdapterView.OnItemSelectedListener {
        a(me.hisn.mygesture.NavA navA) {
        }

        private void a(android.view.View view, int i) {
            java.lang.String str;
            switch (view.getId()) {
                case me.hisn.mygesture.R.id.when_landscape_spinner /* 2131231082 */:
                    str = "41423";
                    break;
                case me.hisn.mygesture.R.id.when_lock_spinner /* 2131231083 */:
                    str = "41424";
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
            a(adapterView, i);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.widget.Spinner f527a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.AdapterView.OnItemSelectedListener f528b;

        b(me.hisn.mygesture.NavA navA, android.widget.Spinner spinner, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
            this.f527a = spinner;
            this.f528b = onItemSelectedListener;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f527a.setOnItemSelectedListener(this.f528b);
        }
    }

    private void a(android.widget.Spinner spinner, int i, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
        spinner.setSelection(i);
        spinner.postDelayed(new me.hisn.mygesture.NavA.b(this, spinner, onItemSelectedListener), 300L);
    }

    private void i() {
        android.widget.Spinner spinner = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.when_lock_spinner);
        android.widget.Spinner spinner2 = (android.widget.Spinner) findViewById(me.hisn.mygesture.R.id.when_landscape_spinner);
        me.hisn.mygesture.NavA.a aVar = new me.hisn.mygesture.NavA.a(this);
        a(spinner, me.hisn.mygesture.P.s.getInt("41424", 0), aVar);
        a(spinner2, me.hisn.mygesture.P.s.getInt("41423", 0), aVar);
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_nav);
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
