package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class o {

    class a implements me.hisn.mygesture.a.h {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f804a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f805b;

        a(int i, int i2) {
            this.f804a = i;
            this.f805b = i2;
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view) {
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        }

        @Override // me.hisn.mygesture.a.h
        public void a(android.view.View view, int i, int i2, int i3, int i4) {
            me.hisn.mygesture.MAS.a(me.hisn.mygesture.P.f536c, -1, -1, this.f804a + (i - i3), this.f805b + (i2 - i4));
        }

        @Override // me.hisn.mygesture.a.h
        public void b(android.view.View view, int i, int i2, int i3, int i4) {
            int iH = (int) ((((this.f804a + (i - i3)) + me.hisn.mygesture.MAS.h()) / me.hisn.mygesture.P.k0) * 1000.0f);
            int iJ = (int) ((((this.f805b + (i2 - i4)) + me.hisn.mygesture.MAS.j()) / me.hisn.mygesture.P.l0) * 1000.0f);
            me.hisn.utils.k0.b("f41434", iH);
            me.hisn.utils.k0.b("f41435", iJ);
            me.hisn.mygesture.MAS.a((me.hisn.mygesture.a.h) null);
        }
    }

    class b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.graphics.Bitmap f806a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.content.Context f807b;

        b(android.graphics.Bitmap bitmap, android.content.Context context) {
            this.f806a = bitmap;
            this.f807b = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            new me.hisn.mypanel.c().a(this.f806a, me.hisn.utils.o.c(this.f807b), java.lang.Math.min(me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0) / 6, true);
        }
    }

    public static void a(android.content.Context context, android.graphics.Bitmap bitmap) {
        new java.lang.Thread(new me.hisn.utils.o.b(bitmap, context)).start();
    }

    public static void a(android.view.View view) {
        me.hisn.mygesture.MAS.a(new me.hisn.utils.o.a(me.hisn.mygesture.a.b(me.hisn.mygesture.P.s.getInt("f41434", 0)) - me.hisn.mygesture.MAS.h(), me.hisn.mygesture.a.c(me.hisn.mygesture.P.s.getInt("f41435", me.hisn.mygesture.P.l0 / 2)) - me.hisn.mygesture.MAS.j()));
    }

    public static boolean a(android.content.Context context) {
        return new java.io.File(c(context)).exists();
    }

    public static android.graphics.drawable.Drawable b(android.content.Context context) {
        java.io.File file = new java.io.File(c(context));
        android.graphics.drawable.Drawable drawableCreateFromPath = file.exists() ? android.graphics.drawable.Drawable.createFromPath(file.getPath()) : null;
        return drawableCreateFromPath == null ? context.getDrawable(me.hisn.mygesture.R.drawable.ic_rings2) : drawableCreateFromPath;
    }

    public static java.lang.String c(android.content.Context context) {
        return context.getExternalFilesDir(null) + "/31425.png";
    }

    public static void d(android.content.Context context) {
        java.io.File file = new java.io.File(c(context));
        if (file.exists()) {
            file.delete();
        }
    }
}
