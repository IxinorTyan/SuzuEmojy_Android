package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class r0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private long f836a;

    class a implements android.view.View.OnLayoutChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f837a;

        a(android.content.Context context) {
            this.f837a = context;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (me.hisn.mygesture.f.b(this.f837a.getApplicationContext())) {
                me.hisn.utils.r0.this.a(view);
            }
        }
    }

    class b implements android.view.View.OnTouchListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        java.util.List<android.graphics.Point> f839a;

        b() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            java.util.List<android.graphics.Point> list;
            android.graphics.Point point;
            int action = motionEvent.getAction();
            if (action != 0) {
                if (action == 1) {
                    long jCurrentTimeMillis = java.lang.System.currentTimeMillis() - me.hisn.utils.r0.this.f836a;
                    me.hisn.utils.r0.this.f836a = java.lang.System.currentTimeMillis();
                    if (jCurrentTimeMillis < 300) {
                        if (view.getBackground() != null) {
                            view.setBackground(null);
                        } else {
                            view.setBackgroundColor(-16777216);
                        }
                    } else if (me.hisn.utils.r0.this.a(this.f839a)) {
                        me.hisn.utils.r0.this.a(view);
                    }
                } else if (action == 2) {
                    list = this.f839a;
                    point = new android.graphics.Point((int) motionEvent.getX(), (int) motionEvent.getY());
                }
                return true;
            }
            java.util.List<android.graphics.Point> list2 = this.f839a;
            if (list2 == null) {
                this.f839a = new java.util.ArrayList();
            } else {
                list2.clear();
            }
            list = this.f839a;
            point = new android.graphics.Point((int) motionEvent.getX(), (int) motionEvent.getY());
            list.add(point);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.view.View view) {
        try {
            me.hisn.mygesture.MAS.k().removeView(view);
            new me.hisn.utils.b0().a(view.getContext().getApplicationContext(), me.hisn.mygesture.R.string.start_touch, 0);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(java.util.List<android.graphics.Point> list) {
        if (list.size() < 4) {
            return false;
        }
        android.graphics.Point point = list.get(0);
        android.graphics.Point point2 = point;
        android.graphics.Point point3 = point2;
        android.graphics.Point point4 = point3;
        for (android.graphics.Point point5 : list) {
            if (point5.x < point.x) {
                point = point5;
            }
            if (point5.x > point2.x) {
                point2 = point5;
            }
            if (point5.y < point3.y) {
                point3 = point5;
            }
            if (point5.y > point4.y) {
                point4 = point5;
            }
        }
        android.graphics.Point point6 = new android.graphics.Point((point.x + point2.x) / 2, (point.y + point2.y) / 2);
        android.graphics.Point point7 = new android.graphics.Point((point3.x + point4.x) / 2, (point3.y + point4.y) / 2);
        int i = point6.x;
        int i2 = point7.x;
        int i3 = point6.y;
        int i4 = point7.y;
        int i5 = ((i - i2) * (i - i2)) + ((i3 - i4) * (i3 - i4));
        int iMin = java.lang.Math.min(point2.x - point.x, point4.y - point3.y);
        int i6 = point3.x - point4.x;
        double d = ((double) iMin) * 0.3d;
        return ((double) java.lang.Math.abs(i6)) < d && ((double) java.lang.Math.abs(point.y - point2.y)) < d && java.lang.Math.sqrt((double) i5) < d;
    }

    private android.view.View b(android.content.Context context) {
        android.view.View view = new android.view.View(context.getApplicationContext());
        view.addOnLayoutChangeListener(new me.hisn.utils.r0.a(context));
        view.setOnTouchListener(new me.hisn.utils.r0.b());
        return view;
    }

    public void a(android.content.Context context) {
        android.view.WindowManager windowManagerK = me.hisn.mygesture.MAS.k();
        int iMax = java.lang.Math.max(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0) + 1000;
        try {
            windowManagerK.addView(b(context), new me.hisn.utils.v().a(true, 17, iMax, iMax, -300, -300, 0, false));
            new me.hisn.utils.b0().a(context.getApplicationContext(), me.hisn.mygesture.R.string.notouch_text, 0);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
