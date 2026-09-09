package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public abstract class a0 {

    class a implements android.content.DialogInterface.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.util.List f728a;

        a(java.util.List list) {
            this.f728a = list;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(android.content.DialogInterface dialogInterface, int i) {
            me.hisn.utils.a0.this.a((java.lang.String) this.f728a.get(i), i);
            dialogInterface.dismiss();
        }
    }

    class b implements android.content.DialogInterface.OnClickListener {
        b() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(android.content.DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            me.hisn.utils.a0.this.c();
        }
    }

    class c implements android.content.DialogInterface.OnClickListener {
        c() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(android.content.DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            me.hisn.utils.a0.this.b();
        }
    }

    public void a(android.app.Activity activity, java.lang.String str, java.util.List<java.lang.String> list, java.lang.String str2, java.lang.String str3) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity, me.hisn.mygesture.R.style.DialogThemeDark);
        builder.setTitle(str);
        builder.setItems((java.lang.CharSequence[]) list.toArray(new java.lang.CharSequence[0]), new me.hisn.utils.a0.a(list));
        if (str2 != null) {
            builder.setPositiveButton(str2, new me.hisn.utils.a0.b());
        }
        if (str3 != null) {
            builder.setNegativeButton(str3, new me.hisn.utils.a0.c());
        }
        android.app.AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setCancelable(a());
        alertDialogCreate.show();
    }

    protected abstract void a(java.lang.String str, int i);

    protected abstract boolean a();

    protected void b() {
    }

    protected void c() {
    }
}
