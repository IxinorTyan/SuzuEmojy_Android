package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class Aa extends me.hisn.utils.e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.util.List<me.hisn.mypanel.f> f624a = new java.util.ArrayList();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.util.List<me.hisn.mypanel.f> f625b = new java.util.ArrayList();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private android.widget.ImageView f626c;
    private android.content.pm.PackageManager d;
    private int e;
    private boolean f;
    private androidx.viewpager.widget.ViewPager g;
    private android.widget.TextView h;
    private android.widget.TextView i;
    private android.widget.TextView j;
    private android.widget.TextView k;

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: me.hisn.mypanel.Aa$a$a, reason: collision with other inner class name */
        class RunnableC0026a implements java.lang.Runnable {
            RunnableC0026a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.mypanel.Aa.this.m();
                me.hisn.mypanel.Aa.this.o();
                me.hisn.mypanel.Aa.this.i();
            }
        }

        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mypanel.Aa.this.j();
            me.hisn.mypanel.Aa.this.runOnUiThread(new me.hisn.mypanel.Aa.a.RunnableC0026a());
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f629a;

        b(android.view.View view) {
            this.f629a = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            int height = this.f629a.getHeight();
            android.view.View viewFindViewById = me.hisn.mypanel.Aa.this.findViewById(me.hisn.mygesture.R.id.list_view_layout);
            android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
            layoutParams.height = height - new me.hisn.utils.l().a(me.hisn.mypanel.Aa.this.getApplicationContext(), 110.0f);
            viewFindViewById.setLayoutParams(layoutParams);
        }
    }

    class c implements androidx.viewpager.widget.ViewPager.j {
        c() {
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0012 A[PHI: r2
  0x0012: PHI (r2v4 me.hisn.mypanel.Aa) = (r2v1 me.hisn.mypanel.Aa), (r2v6 me.hisn.mypanel.Aa) binds: [B:16:0x0036, B:5:0x0009] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // androidx.viewpager.widget.ViewPager.j
        public void a(int i) {
            me.hisn.mypanel.Aa aa;
            android.widget.TextView textView;
            if (i == 1) {
                aa = me.hisn.mypanel.Aa.this;
                if (aa.f) {
                    textView = me.hisn.mypanel.Aa.this.i;
                } else {
                    textView = me.hisn.mypanel.Aa.this.h;
                }
            } else if (i == 2) {
                aa = me.hisn.mypanel.Aa.this;
                textView = aa.i;
            } else if (i == 3) {
                aa = me.hisn.mypanel.Aa.this;
                textView = aa.j;
            } else {
                aa = me.hisn.mypanel.Aa.this;
                if (aa.f) {
                    textView = me.hisn.mypanel.Aa.this.h;
                } else {
                    textView = me.hisn.mypanel.Aa.this.k;
                }
            }
            aa.a(textView);
        }

        @Override // androidx.viewpager.widget.ViewPager.j
        public void a(int i, float f, int i2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.j
        public void b(int i) {
        }
    }

    class d implements android.view.View.OnClickListener {
        d() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            androidx.viewpager.widget.ViewPager viewPager;
            int i;
            me.hisn.mypanel.Aa.this.a(view);
            if (view == me.hisn.mypanel.Aa.this.h) {
                viewPager = me.hisn.mypanel.Aa.this.g;
                i = !me.hisn.mypanel.Aa.this.f ? 1 : 0;
            } else if (view == me.hisn.mypanel.Aa.this.i) {
                viewPager = me.hisn.mypanel.Aa.this.g;
                i = me.hisn.mypanel.Aa.this.f ? 1 : 2;
            } else if (view == me.hisn.mypanel.Aa.this.j) {
                viewPager = me.hisn.mypanel.Aa.this.g;
                i = 3;
            } else {
                viewPager = me.hisn.mypanel.Aa.this.g;
                i = 0;
            }
            viewPager.a(i, true);
        }
    }

    private class e implements java.util.Comparator<me.hisn.mypanel.f> {
        private e(me.hisn.mypanel.Aa aa) {
        }

        /* synthetic */ e(me.hisn.mypanel.Aa aa, me.hisn.mypanel.Aa.a aVar) {
            this(aa);
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(me.hisn.mypanel.f fVar, me.hisn.mypanel.f fVar2) {
            return java.text.Collator.getInstance(java.util.Locale.CHINA).compare(fVar.f691b, fVar2.f691b);
        }
    }

    static class f extends androidx.recyclerview.widget.LinearLayoutManager {
        android.widget.ScrollView I;

        public f(android.content.Context context, android.view.View view) {
            super(context);
            this.I = (android.widget.ScrollView) view;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
        public int b(int i, androidx.recyclerview.widget.RecyclerView.v vVar, androidx.recyclerview.widget.RecyclerView.a0 a0Var) {
            if (i > 3) {
                this.I.scrollBy(0, i / 3);
            }
            return super.b(i, vVar, a0Var);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.o
        public boolean b() {
            return super.b();
        }
    }

    private class g extends androidx.viewpager.widget.a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        java.util.List<android.view.View> f633a;

        g(me.hisn.mypanel.Aa aa, java.util.List<android.view.View> list) {
            this.f633a = list;
        }

        @Override // androidx.viewpager.widget.a
        public int a() {
            return this.f633a.size();
        }

        @Override // androidx.viewpager.widget.a
        public java.lang.Object a(android.view.ViewGroup viewGroup, int i) {
            viewGroup.addView(this.f633a.get(i));
            return this.f633a.get(i);
        }

        @Override // androidx.viewpager.widget.a
        public void a(android.view.ViewGroup viewGroup, int i, java.lang.Object obj) {
            viewGroup.removeView(this.f633a.get(i));
        }

        @Override // androidx.viewpager.widget.a
        public boolean a(android.view.View view, java.lang.Object obj) {
            return view == obj;
        }
    }

    private androidx.recyclerview.widget.RecyclerView a(java.util.List<me.hisn.mypanel.f> list) {
        androidx.recyclerview.widget.RecyclerView recyclerView = (androidx.recyclerview.widget.RecyclerView) android.view.LayoutInflater.from(this).inflate(me.hisn.mygesture.R.layout.my_recycler_view, (android.view.ViewGroup) null);
        recyclerView.setLayoutManager(new me.hisn.mypanel.Aa.f(this, findViewById(me.hisn.mygesture.R.id.scroll_layout)));
        recyclerView.setAdapter(new me.hisn.mypanel.e(list, this, this.e));
        return recyclerView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.view.View view) {
        java.util.ArrayList<android.view.View> arrayList = new java.util.ArrayList();
        arrayList.add(this.h);
        arrayList.add(this.i);
        arrayList.add(this.j);
        arrayList.add(this.k);
        for (android.view.View view2 : arrayList) {
            if (view2 == view) {
                view2.setBackgroundResource(me.hisn.mygesture.R.drawable.tab_bkg_selected);
            } else {
                view2.setBackground(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        this.f626c.clearAnimation();
        this.f626c.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        for (android.content.pm.ResolveInfo resolveInfo : this.d.queryIntentActivities(intent, 0)) {
            me.hisn.mypanel.f fVar = new me.hisn.mypanel.f();
            fVar.f691b = ((java.lang.Object) resolveInfo.loadLabel(this.d)) + "";
            fVar.f692c = resolveInfo.activityInfo.packageName;
            fVar.f690a = resolveInfo.loadIcon(this.d);
            android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
            fVar.d = activityInfo.name;
            fVar.f = 0;
            fVar.e = 1068078049;
            ((activityInfo.applicationInfo.flags & 1) <= 0 ? this.f625b : this.f624a).add(fVar);
        }
        me.hisn.mypanel.Aa.a aVar = null;
        java.util.Collections.sort(this.f625b, new me.hisn.mypanel.Aa.e(this, aVar));
        java.util.Collections.sort(this.f624a, new me.hisn.mypanel.Aa.e(this, aVar));
    }

    private void k() {
        android.view.View viewFindViewById = findViewById(me.hisn.mygesture.R.id.scroll_layout);
        viewFindViewById.post(new me.hisn.mypanel.Aa.b(viewFindViewById));
    }

    private void l() {
        this.f626c = (android.widget.ImageView) findViewById(me.hisn.mygesture.R.id.loading_img);
        this.f626c.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, me.hisn.mygesture.R.anim.rote_animation));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        this.g = (androidx.viewpager.widget.ViewPager) findViewById(me.hisn.mygesture.R.id.add_item_pager);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (!this.f) {
            arrayList.add(a(new me.hisn.utils.b(this).a()));
        }
        arrayList.add(a(this.f625b));
        arrayList.add(a(this.f624a));
        if (!this.f) {
            arrayList.add(a(n()));
        }
        this.g.a(new me.hisn.mypanel.Aa.c());
        this.g.setAdapter(new me.hisn.mypanel.Aa.g(this, arrayList));
    }

    private java.util.List<me.hisn.mypanel.f> n() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.clear();
        for (android.content.pm.ResolveInfo resolveInfo : this.d.queryIntentActivities(new android.content.Intent("android.intent.action.CREATE_SHORTCUT"), 131072)) {
            me.hisn.mypanel.f fVar = new me.hisn.mypanel.f();
            fVar.f690a = resolveInfo.activityInfo.loadIcon(this.d);
            fVar.f691b = ((java.lang.Object) resolveInfo.activityInfo.loadLabel(this.d)) + "";
            android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
            fVar.f692c = activityInfo.packageName;
            fVar.d = activityInfo.name;
            fVar.f = 1;
            fVar.e = 1068078050;
            arrayList.add(fVar);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void o() {
        this.h = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.third_party_apps);
        this.i = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.system_apps);
        this.j = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.shortcuts_btn);
        this.k = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.operation_btn);
        if (!this.f) {
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            a(this.k);
        }
        me.hisn.mypanel.Aa.d dVar = new me.hisn.mypanel.Aa.d();
        this.h.setOnClickListener(dVar);
        this.i.setOnClickListener(dVar);
        this.j.setOnClickListener(dVar);
        this.k.setOnClickListener(dVar);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        android.os.Bundle extras;
        super.onActivityResult(i, i2, intent);
        if (i == 85 && i2 == -1 && (extras = intent.getExtras()) != null) {
            android.content.Intent intent2 = new android.content.Intent();
            intent2.putExtra("31418", 1);
            intent2.putExtra("31419", extras);
            setResult(-1, intent2);
            finish();
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!this.f) {
            super.onBackPressed();
            return;
        }
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("31420", me.hisn.mypanel.e.f);
        setResult(-1, intent);
        finish();
    }

    @Override // me.hisn.utils.e, me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_apps);
        int intExtra = getIntent().getIntExtra("31415", 1);
        this.e = intExtra;
        boolean z = (intExtra & 12) != 0;
        this.f = z;
        if (z) {
            java.lang.String stringExtra = getIntent().getStringExtra("31416");
            me.hisn.mypanel.e.f = stringExtra;
            if (stringExtra == null) {
                me.hisn.mypanel.e.f = "";
            }
            new me.hisn.utils.b0().a(getApplicationContext(), me.hisn.mygesture.R.string.save_app_tip, 0);
        }
        java.lang.String stringExtra2 = getIntent().getStringExtra("31417");
        if (stringExtra2 != null) {
            android.widget.TextView textView = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.title_bar);
            android.widget.TextView textView2 = (android.widget.TextView) findViewById(me.hisn.mygesture.R.id.big_title_bar);
            textView.setText(stringExtra2);
            textView2.setText(stringExtra2);
        }
        this.d = getPackageManager();
        l();
        k();
        new java.lang.Thread(new me.hisn.mypanel.Aa.a()).start();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(android.view.MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
