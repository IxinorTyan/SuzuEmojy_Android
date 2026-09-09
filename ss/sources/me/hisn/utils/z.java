package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public abstract class z implements android.content.DialogInterface.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final android.app.Dialog f888a;

    class a implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.app.AlertDialog f889a;

        a(android.app.AlertDialog alertDialog) {
            this.f889a = alertDialog;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.utils.z.this.d();
            if (me.hisn.utils.z.this.b()) {
                this.f889a.dismiss();
            }
        }
    }

    class b implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.app.AlertDialog f891a;

        b(android.app.AlertDialog alertDialog) {
            this.f891a = alertDialog;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            me.hisn.utils.z.this.c();
            if (me.hisn.utils.z.this.b()) {
                this.f891a.dismiss();
            }
        }
    }

    public z(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
        this.f888a = a(context, str, str2, str3, str4, z, i);
    }

    private android.app.Dialog a(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, int i) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context, me.hisn.mygesture.R.style.DialogThemeDark);
        a(builder);
        if (str != null) {
            builder.setTitle(str);
        }
        builder.setMessage(str2);
        if (str3 != null) {
            builder.setPositiveButton(str3, (android.content.DialogInterface.OnClickListener) null);
        }
        if (str4 != null) {
            builder.setNegativeButton(str4, (android.content.DialogInterface.OnClickListener) null);
        }
        builder.setCancelable(z);
        android.app.AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.show();
        if (alertDialogCreate.getWindow() != null) {
            android.view.WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
            attributes.height = i;
            alertDialogCreate.getWindow().setAttributes(attributes);
        }
        if (str3 != null) {
            try {
                alertDialogCreate.getButton(-1).setOnClickListener(new me.hisn.utils.z.a(alertDialogCreate));
            } catch (java.lang.Exception e) {
                e.printStackTrace();
                builder.setPositiveButton(str3, this);
            }
        }
        if (str4 != null) {
            try {
                alertDialogCreate.getButton(-2).setOnClickListener(new me.hisn.utils.z.b(alertDialogCreate));
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
                builder.setNegativeButton(str4, this);
            }
        }
        return alertDialogCreate;
    }

    public void a() {
        this.f888a.dismiss();
    }

    public void a(android.app.AlertDialog.Builder builder) {
    }

    protected boolean b() {
        return true;
    }

    public void c() {
    }

    public abstract void d();

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        if (i == -2) {
            c();
        } else if (i == -1) {
            d();
        }
        if (b()) {
            dialogInterface.dismiss();
        }
    }
}
