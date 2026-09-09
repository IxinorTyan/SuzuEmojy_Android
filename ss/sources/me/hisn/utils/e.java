package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class e extends me.hisn.utils.t0 {

    class a implements android.view.View.OnClickListener {
        a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.utils.e.this.onBackPressed();
        }
    }

    class b implements android.view.View.OnScrollChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f737a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.widget.TextView f738b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ float f739c;
        final /* synthetic */ float d;
        final /* synthetic */ android.widget.TextView e;

        b(me.hisn.utils.e eVar, int i, android.widget.TextView textView, float f, float f2, android.widget.TextView textView2) {
            this.f737a = i;
            this.f738b = textView;
            this.f739c = f;
            this.d = f2;
            this.e = textView2;
        }

        @Override // android.view.View.OnScrollChangeListener
        public void onScrollChange(android.view.View view, int i, int i2, int i3, int i4) {
            android.widget.TextView textView;
            float f = i2 / this.f737a;
            float f2 = 1.0f;
            if (f <= 1.0f) {
                this.f738b.setTextSize(0, this.f739c - (f * this.d));
                textView = this.e;
                f2 = 0.0f;
            } else {
                textView = this.e;
            }
            textView.setAlpha(f2);
        }
    }

    public void h() {
        android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.big_title_bar);
        android.widget.TextView textView2 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.title_bar);
        me.hisn.utils.e.a aVar = new me.hisn.utils.e.a();
        textView2.setOnClickListener(aVar);
        textView.setOnClickListener(aVar);
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            float textSize = textView2.getTextSize();
            float textSize2 = textView.getTextSize();
            int iA = new me.hisn.utils.l().a(getApplicationContext(), 200.0f);
            findViewById(me.hisn.mygesture.R.id.scroll_layout).setOnScrollChangeListener(new me.hisn.utils.e.b(this, iA, textView, textSize2, textSize2 - textSize, textView2));
        }
    }

    @Override // me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    protected void onPostCreate(android.os.Bundle bundle) {
        super.onPostCreate(bundle);
        h();
    }
}
