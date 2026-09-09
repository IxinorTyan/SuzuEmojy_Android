package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class ThreeInOneManagerA extends me.hisn.utils.t0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f722a = null;

    class a extends me.hisn.utils.a0 {
        a() {
        }

        @Override // me.hisn.utils.a0
        protected void a(java.lang.String str, int i) {
            me.hisn.utils.ThreeInOneManagerA.this.a(i + 80);
        }

        @Override // me.hisn.utils.a0
        protected boolean a() {
            return false;
        }

        @Override // me.hisn.utils.a0
        protected void c() {
            super.c();
            me.hisn.utils.ThreeInOneManagerA.this.finish();
        }
    }

    public static android.graphics.Bitmap a(android.content.Context context, android.graphics.Bitmap bitmap) {
        int iA = new me.hisn.utils.l().a(context, 30.0f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        return android.graphics.Bitmap.createScaledBitmap(bitmap, iA, (int) (height * (iA / width)), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), (java.lang.Class<?>) me.hisn.mypanel.Aa.class);
        intent.putExtra("31415", 32);
        intent.putExtra("31417", getString(me.hisn.mygesture.R.string.three_in_one) + " " + ((i - 80) + 1));
        startActivityForResult(intent, i);
    }

    private void h() {
        java.lang.String strA = me.hisn.utils.k0.a(this.f722a + "31427_l");
        java.lang.String strA2 = me.hisn.utils.k0.a(this.f722a + "31428_l");
        java.lang.String strA3 = me.hisn.utils.k0.a(this.f722a + "31429_l");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("1: ");
        if (strA == null) {
            strA = "";
        }
        sb.append(strA);
        arrayList.add(sb.toString());
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("2: ");
        if (strA2 == null) {
            strA2 = "";
        }
        sb2.append(strA2);
        arrayList.add(sb2.toString());
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        sb3.append("3: ");
        if (strA3 == null) {
            strA3 = "";
        }
        sb3.append(strA3);
        arrayList.add(sb3.toString());
        new me.hisn.utils.ThreeInOneManagerA.a().a(this, getString(me.hisn.mygesture.R.string.three_in_one_settings), arrayList, getString(me.hisn.mygesture.R.string.yes_text), null);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        java.lang.StringBuilder sb;
        java.lang.String str;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            int i3 = i - 80;
            java.lang.String string = null;
            if (i3 == 0) {
                sb = new java.lang.StringBuilder();
                sb.append(this.f722a);
                str = "31427";
            } else if (i3 == 1) {
                sb = new java.lang.StringBuilder();
                sb.append(this.f722a);
                str = "31428";
            } else {
                if (i3 == 2) {
                    sb = new java.lang.StringBuilder();
                    sb.append(this.f722a);
                    str = "31429";
                }
                me.hisn.utils.h0.a(getApplicationContext(), intent, string, me.hisn.mygesture.P.s, true);
            }
            sb.append(str);
            string = sb.toString();
            me.hisn.utils.h0.a(getApplicationContext(), intent, string, me.hisn.mygesture.P.s, true);
        }
        h();
    }

    @Override // me.hisn.utils.t0, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        java.lang.String stringExtra = getIntent().getStringExtra("31415");
        this.f722a = stringExtra;
        if (stringExtra == null) {
            finish();
        } else {
            h();
        }
    }
}
