package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public abstract class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f667a = null;

    class a implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f668a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ me.hisn.utils.DrawPadView f669b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.content.Context f670c;
        final /* synthetic */ android.graphics.Bitmap d;
        final /* synthetic */ android.widget.LinearLayout e;

        /* JADX INFO: renamed from: me.hisn.mypanel.b$a$a, reason: collision with other inner class name */
        class RunnableC0031a implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.net.Uri f671a;

            /* JADX INFO: renamed from: me.hisn.mypanel.b$a$a$a, reason: collision with other inner class name */
            class RunnableC0032a implements java.lang.Runnable {
                RunnableC0032a() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    me.hisn.mypanel.b.a aVar = me.hisn.mypanel.b.a.this;
                    me.hisn.mypanel.b.this.b(aVar.f668a);
                }
            }

            RunnableC0031a(android.net.Uri uri) {
                this.f671a = uri;
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean zB = me.hisn.mypanel.b.a.this.f669b.b();
                android.net.Uri uri = this.f671a;
                if (zB) {
                    me.hisn.mypanel.b.a.this.f670c.getContentResolver().delete(this.f671a, null, null);
                    me.hisn.mypanel.c cVar = new me.hisn.mypanel.c();
                    me.hisn.mypanel.b.a aVar = me.hisn.mypanel.b.a.this;
                    android.graphics.Bitmap bitmap = aVar.d;
                    cVar.a(bitmap, cVar.a(aVar.f669b));
                    uri = android.net.Uri.parse(cVar.a(me.hisn.mypanel.b.a.this.f670c.getContentResolver(), bitmap, me.hisn.mypanel.b.a.this.f670c.getString(me.hisn.mygesture.R.string.app_name), me.hisn.mypanel.b.a.this.f670c.getString(me.hisn.mygesture.R.string.app_name)));
                }
                try {
                    me.hisn.mypanel.b.a.this.f670c.startActivity(me.hisn.mypanel.b.this.a(me.hisn.mypanel.b.a.this.f670c, uri));
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
                me.hisn.mypanel.b.a.this.f668a.post(new me.hisn.mypanel.b.a.RunnableC0031a.RunnableC0032a());
            }
        }

        /* JADX INFO: renamed from: me.hisn.mypanel.b$a$b, reason: collision with other inner class name */
        class RunnableC0033b implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.View f674a;

            /* JADX INFO: renamed from: me.hisn.mypanel.b$a$b$a, reason: collision with other inner class name */
            class RunnableC0034a implements java.lang.Runnable {

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                final /* synthetic */ java.lang.String f676a;

                RunnableC0034a(java.lang.String str) {
                    this.f676a = str;
                }

                @Override // java.lang.Runnable
                public void run() {
                    me.hisn.mypanel.b.a aVar = me.hisn.mypanel.b.a.this;
                    me.hisn.mypanel.b.this.b(aVar.f668a);
                    new me.hisn.utils.b0().a(me.hisn.mypanel.b.a.this.f670c.getApplicationContext(), me.hisn.mypanel.b.a.this.f670c.getString(me.hisn.mygesture.R.string.saved_shell_sceen_tip) + this.f676a, 0);
                    me.hisn.mypanel.b.a aVar2 = me.hisn.mypanel.b.a.this;
                    me.hisn.mypanel.b.this.a(aVar2.f670c.getApplicationContext(), new java.io.File(this.f676a));
                }
            }

            RunnableC0033b(android.view.View view) {
                this.f674a = view;
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.mypanel.c cVar = new me.hisn.mypanel.c();
                java.lang.String strA = cVar.a(me.hisn.mypanel.b.a.this.f670c.getApplicationContext().getContentResolver(), cVar.a(me.hisn.mypanel.b.a.this.f668a), me.hisn.mypanel.b.a.this.f670c.getString(me.hisn.mygesture.R.string.app_name), me.hisn.mypanel.b.a.this.f670c.getString(me.hisn.mygesture.R.string.app_name));
                me.hisn.mypanel.b.a aVar = me.hisn.mypanel.b.a.this;
                java.lang.String strB = me.hisn.mypanel.b.this.b(aVar.f670c, android.net.Uri.parse(strA));
                if (strB != null) {
                    this.f674a.post(new me.hisn.mypanel.b.a.RunnableC0033b.RunnableC0034a(strB));
                }
            }
        }

        a(android.view.View view, me.hisn.utils.DrawPadView drawPadView, android.content.Context context, android.graphics.Bitmap bitmap, android.widget.LinearLayout linearLayout) {
            this.f668a = view;
            this.f669b = drawPadView;
            this.f670c = context;
            this.d = bitmap;
            this.e = linearLayout;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (view.getId() != me.hisn.mygesture.R.id.parent_layout) {
                if (me.hisn.mypanel.b.this.f667a != null) {
                    android.net.Uri uri = android.net.Uri.parse(me.hisn.mypanel.b.this.f667a);
                    switch (view.getId()) {
                        case me.hisn.mygesture.R.id.delete_btn /* 2131230826 */:
                            if (this.f669b.b()) {
                                this.f669b.a();
                                return;
                            }
                            this.f670c.getContentResolver().delete(uri, null, null);
                            me.hisn.mypanel.b.this.b(this.f668a);
                            new me.hisn.utils.b0().a(this.f670c.getApplicationContext(), me.hisn.mygesture.R.string.delete_screen_picture_tip, 0);
                            return;
                        case me.hisn.mygesture.R.id.save_shell_btn /* 2131231019 */:
                            this.e.setVisibility(8);
                            new java.lang.Thread(new me.hisn.mypanel.b.a.RunnableC0033b(view)).start();
                            return;
                        case me.hisn.mygesture.R.id.share_btn /* 2131231027 */:
                            new java.lang.Thread(new me.hisn.mypanel.b.a.RunnableC0031a(uri)).start();
                            return;
                        case me.hisn.mygesture.R.id.view_btn /* 2131231076 */:
                            android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
                            intent.setType("image/*");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.addFlags(268435520);
                            intent.setData(uri);
                            try {
                                this.f670c.startActivity(intent);
                            } catch (java.lang.Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            return;
                    }
                } else {
                    return;
                }
            }
            me.hisn.mypanel.b.this.b(this.f668a);
        }
    }

    /* JADX INFO: renamed from: me.hisn.mypanel.b$b, reason: collision with other inner class name */
    class RunnableC0035b implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.graphics.Bitmap f678a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f679b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.ImageView f680c;
        final /* synthetic */ android.widget.ImageView d;
        final /* synthetic */ android.widget.ImageView e;
        final /* synthetic */ android.widget.ImageView f;

        /* JADX INFO: renamed from: me.hisn.mypanel.b$b$a */
        class a implements java.lang.Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.mypanel.b.RunnableC0035b.this.f680c.setColorFilter(-12303292);
                me.hisn.mypanel.b.RunnableC0035b.this.d.setColorFilter(-12303292);
                me.hisn.mypanel.b.RunnableC0035b.this.e.setColorFilter(-12303292);
                me.hisn.mypanel.b.RunnableC0035b.this.f.setColorFilter(-12303292);
            }
        }

        RunnableC0035b(android.graphics.Bitmap bitmap, android.view.View view, android.widget.ImageView imageView, android.widget.ImageView imageView2, android.widget.ImageView imageView3, android.widget.ImageView imageView4) {
            this.f678a = bitmap;
            this.f679b = view;
            this.f680c = imageView;
            this.d = imageView2;
            this.e = imageView3;
            this.f = imageView4;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (me.hisn.mypanel.b.this.a(this.f678a)) {
                this.f679b.post(new me.hisn.mypanel.b.RunnableC0035b.a());
            }
        }
    }

    class c implements me.hisn.mygesture.MAS.i {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f682a;

        c(android.view.View view) {
            this.f682a = view;
        }

        @Override // me.hisn.mygesture.MAS.i
        public void a() {
            me.hisn.mypanel.b.this.b(this.f682a);
        }

        @Override // me.hisn.mygesture.MAS.i
        public void b() {
            try {
                ((android.view.WindowManager) java.util.Objects.requireNonNull(me.hisn.mygesture.MAS.k())).updateViewLayout(this.f682a, me.hisn.mypanel.b.this.a());
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    class d implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.content.Context f684a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.graphics.Bitmap f685b;

        d(android.content.Context context, android.graphics.Bitmap bitmap) {
            this.f684a = context;
            this.f685b = bitmap;
        }

        @Override // java.lang.Runnable
        public void run() {
            me.hisn.mypanel.b.this.f667a = new me.hisn.mypanel.c().a(this.f684a.getContentResolver(), this.f685b, this.f684a.getString(me.hisn.mygesture.R.string.app_name), this.f684a.getString(me.hisn.mygesture.R.string.app_name));
        }
    }

    class e implements android.media.MediaScannerConnection.OnScanCompletedListener {
        e(me.hisn.mypanel.b bVar) {
        }

        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
        public void onScanCompleted(java.lang.String str, android.net.Uri uri) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Intent a(android.content.Context context, android.net.Uri uri) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.SEND");
        intent.setType("image/*");
        intent.addFlags(268435520);
        intent.putExtra("android.intent.extra.STREAM", uri);
        android.content.Intent intentCreateChooser = android.content.Intent.createChooser(intent, context.getString(me.hisn.mygesture.R.string.share_text));
        intentCreateChooser.addFlags(268435456);
        return intentCreateChooser;
    }

    private android.graphics.Bitmap a(android.view.View view, android.graphics.Bitmap bitmap) {
        ((android.widget.ImageView) view.findViewById(me.hisn.mygesture.R.id.capture_img)).setImageBitmap(android.graphics.Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth() * 3) / 4, (bitmap.getHeight() * 3) / 4, true));
        android.view.View viewFindViewById = view.findViewById(me.hisn.mygesture.R.id.parent_layout);
        android.graphics.Bitmap bitmapA = new me.hisn.utils.i().a(bitmap, 10, true, true);
        viewFindViewById.setBackground(new android.graphics.drawable.BitmapDrawable(view.getContext().getResources(), bitmapA));
        viewFindViewById.setAlpha(1.0f);
        c(view);
        return bitmapA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.WindowManager.LayoutParams a() {
        return new me.hisn.utils.v().a(true, 8388659, me.hisn.mygesture.P.k0, me.hisn.mygesture.P.l0, 0 - me.hisn.mygesture.MAS.h(), 0 - me.hisn.mygesture.MAS.j(), me.hisn.mygesture.R.style.capture_anim, false);
    }

    private void a(android.content.Context context, android.graphics.Bitmap bitmap) {
        new java.lang.Thread(new me.hisn.mypanel.b.d(context, bitmap)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.content.Context context, java.io.File file) {
        android.media.MediaScannerConnection.scanFile(context, new java.lang.String[]{file.getAbsolutePath()}, null, new me.hisn.mypanel.b.e(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(android.graphics.Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = 0;
        int iRed = 0;
        for (int i2 = width / 4; i2 < (width * 3) / 4; i2++) {
            for (int i3 = (height * 16) / 18; i3 < (height * 17) / 18; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                iRed += (int) ((((double) android.graphics.Color.red(pixel)) * 0.299d) + (((double) android.graphics.Color.green(pixel)) * 0.587d) + (((double) android.graphics.Color.blue(pixel)) * 0.114d));
                i++;
            }
        }
        return i != 0 && iRed / i > 192;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String b(android.content.Context context, android.net.Uri uri) {
        if (uri != null) {
            java.lang.String[] strArr = {"_data"};
            android.database.Cursor cursorQuery = context.getContentResolver().query(uri, strArr, null, null, null);
            if (cursorQuery != null) {
                cursorQuery.moveToFirst();
                java.lang.String string = cursorQuery.getString(cursorQuery.getColumnIndex(strArr[0]));
                cursorQuery.close();
                return string;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(android.view.View view) {
        me.hisn.mygesture.MAS.a((me.hisn.mygesture.MAS.i) null);
        a(view);
    }

    private void c(android.view.View view) {
        me.hisn.mygesture.MAS.a(new me.hisn.mypanel.b.c(view));
    }

    public android.view.View a(android.graphics.Bitmap bitmap, android.content.Context context) {
        a(context, bitmap);
        android.view.View viewInflate = android.view.View.inflate(context, me.hisn.mygesture.P.k0 > me.hisn.mygesture.P.l0 ? me.hisn.mygesture.R.layout.activity_capture_land : me.hisn.mygesture.R.layout.activity_capture, null);
        android.widget.ImageView imageView = (android.widget.ImageView) viewInflate.findViewById(me.hisn.mygesture.R.id.view_btn);
        android.widget.ImageView imageView2 = (android.widget.ImageView) viewInflate.findViewById(me.hisn.mygesture.R.id.share_btn);
        android.widget.ImageView imageView3 = (android.widget.ImageView) viewInflate.findViewById(me.hisn.mygesture.R.id.delete_btn);
        android.widget.ImageView imageView4 = (android.widget.ImageView) viewInflate.findViewById(me.hisn.mygesture.R.id.save_shell_btn);
        android.view.View viewFindViewById = viewInflate.findViewById(me.hisn.mygesture.R.id.parent_layout);
        me.hisn.mypanel.b.a aVar = new me.hisn.mypanel.b.a(viewInflate, (me.hisn.utils.DrawPadView) viewInflate.findViewById(me.hisn.mygesture.R.id.draw_view), context, bitmap, (android.widget.LinearLayout) viewInflate.findViewById(me.hisn.mygesture.R.id.tool_layout));
        imageView.setOnClickListener(aVar);
        imageView2.setOnClickListener(aVar);
        imageView3.setOnClickListener(aVar);
        viewFindViewById.setOnClickListener(aVar);
        imageView4.setOnClickListener(aVar);
        android.graphics.Bitmap bitmapA = a(viewInflate, bitmap);
        viewInflate.setLayoutParams(a());
        new java.lang.Thread(new me.hisn.mypanel.b.RunnableC0035b(bitmapA, viewInflate, imageView4, imageView, imageView2, imageView3)).start();
        return viewInflate;
    }

    public abstract void a(android.view.View view);
}
