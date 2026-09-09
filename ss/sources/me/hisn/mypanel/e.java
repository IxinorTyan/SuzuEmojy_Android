package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class e extends androidx.recyclerview.widget.RecyclerView.g<me.hisn.mypanel.e.b> {
    static java.lang.String f;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private final java.util.List<me.hisn.mypanel.f> f687c;
    private final android.app.Activity d;
    private final int e;

    class a implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ me.hisn.mypanel.e.b f688a;

        a(me.hisn.mypanel.e.b bVar) {
            this.f688a = bVar;
        }

        /* JADX WARN: Code duplicated, block: B:18:0x0062  */
        /* JADX WARN: Code duplicated, block: B:46:? A[RETURN, SYNTHETIC] */
        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.content.Intent intent;
            java.lang.String str;
            java.lang.String strReplace;
            android.app.Activity activity;
            int i;
            if (view.getAlpha() < 1.0f) {
                java.lang.String string = null;
                if ((2 & me.hisn.mypanel.e.this.e) != 0) {
                    activity = me.hisn.mypanel.e.this.d;
                    i = me.hisn.mygesture.R.string.can_not_add_to_panel;
                } else if ((me.hisn.mypanel.e.this.e & 16) != 0) {
                    activity = me.hisn.mypanel.e.this.d;
                    i = me.hisn.mygesture.R.string.cannotaddtotouchbar_tips;
                } else {
                    if ((me.hisn.mypanel.e.this.e & 32) == 0) {
                        if ((me.hisn.mypanel.e.this.e & 128) == 0) {
                            activity = me.hisn.mypanel.e.this.d;
                            i = me.hisn.mygesture.R.string.need_unlock_pro_first;
                        }
                        if (string != null) {
                            new me.hisn.utils.b0().a(me.hisn.mypanel.e.this.d.getApplicationContext(), string, 0);
                            return;
                        }
                        return;
                    }
                    activity = me.hisn.mypanel.e.this.d;
                    i = me.hisn.mygesture.R.string.can_not_add_this;
                }
                string = activity.getString(i);
                if (string != null) {
                    new me.hisn.utils.b0().a(me.hisn.mypanel.e.this.d.getApplicationContext(), string, 0);
                    return;
                }
                return;
            }
            me.hisn.mypanel.f fVar = (me.hisn.mypanel.f) me.hisn.mypanel.e.this.f687c.get(this.f688a.f());
            if ((me.hisn.mypanel.e.this.e & 12) != 0) {
                boolean zIsChecked = this.f688a.w.isChecked();
                if ((me.hisn.mypanel.e.this.e & 8) != 0) {
                    str = fVar.f692c + "/" + fVar.d;
                } else {
                    str = fVar.f692c;
                }
                if (zIsChecked) {
                    strReplace = me.hisn.mypanel.e.f.replace(str + "&", "");
                } else {
                    strReplace = me.hisn.mypanel.e.f + str + "&";
                }
                me.hisn.mypanel.e.f = strReplace;
                this.f688a.w.setChecked(!zIsChecked);
                return;
            }
            int i2 = fVar.f;
            if (i2 == 0) {
                intent = new android.content.Intent();
                intent.putExtra("31421", fVar.f692c);
                intent.putExtra("31422", fVar.d);
                intent.putExtra("31418", 0);
                intent.putExtra("31423", fVar.f691b);
                intent.putExtra("31424", fVar.e);
            } else {
                if (i2 == 1) {
                    android.content.Intent intent2 = new android.content.Intent("android.intent.action.CREATE_SHORTCUT");
                    intent2.setComponent(new android.content.ComponentName(fVar.f692c, fVar.d));
                    try {
                        me.hisn.mypanel.e.this.d.startActivityForResult(intent2, 85);
                        return;
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                        new me.hisn.utils.b0().a(me.hisn.mypanel.e.this.d.getApplicationContext(), me.hisn.mygesture.R.string.limited_tips, 0);
                        return;
                    }
                }
                if (i2 != 2) {
                    return;
                }
                intent = new android.content.Intent();
                intent.putExtra("31418", fVar.f);
                intent.putExtra("31424", fVar.e);
                intent.putExtra("31425", fVar.f691b);
            }
            me.hisn.mypanel.e.this.a(intent, fVar);
            me.hisn.mypanel.e.this.d.setResult(-1, intent);
            me.hisn.mypanel.e.this.d.finish();
        }
    }

    class b extends androidx.recyclerview.widget.RecyclerView.d0 {
        android.widget.ImageView t;
        android.widget.TextView u;
        android.widget.RelativeLayout v;
        android.widget.CheckBox w;

        public b(me.hisn.mypanel.e eVar, android.view.View view) {
            super(view);
            this.t = (android.widget.ImageView) view.findViewById(me.hisn.mygesture.R.id.pkg_icon);
            this.u = (android.widget.TextView) view.findViewById(me.hisn.mygesture.R.id.pkg_app_name);
            this.v = (android.widget.RelativeLayout) view.findViewById(me.hisn.mygesture.R.id.list_item_layout);
            if ((eVar.e & 12) != 0) {
                android.widget.CheckBox checkBox = (android.widget.CheckBox) view.findViewById(me.hisn.mygesture.R.id.item_check_box);
                this.w = checkBox;
                checkBox.setVisibility(0);
            }
        }
    }

    e(java.util.List<me.hisn.mypanel.f> list, android.app.Activity activity, int i) {
        this.f687c = list;
        this.d = activity;
        this.e = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(android.content.Intent intent, me.hisn.mypanel.f fVar) {
        android.graphics.drawable.Drawable drawable;
        if ((this.e & 32) != 0) {
            if (fVar.f == 2 && (drawable = fVar.f690a) != null) {
                drawable.setColorFilter(-1, android.graphics.PorterDuff.Mode.SRC_IN);
            }
            android.graphics.Bitmap bitmapA = new me.hisn.mypanel.c().a(fVar.f690a);
            if (bitmapA != null) {
                intent.putExtra("31426", me.hisn.utils.ThreeInOneManagerA.a(this.d.getApplicationContext(), bitmapA));
            }
        }
    }

    private void a(me.hisn.mypanel.e.b bVar) {
        bVar.v.setOnClickListener(new me.hisn.mypanel.e.a(bVar));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.g
    public int a() {
        return this.f687c.size();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0069 A[PHI: r6
  0x0069: PHI (r6v5 android.widget.RelativeLayout) = 
  (r6v1 android.widget.RelativeLayout)
  (r6v2 android.widget.RelativeLayout)
  (r6v3 android.widget.RelativeLayout)
  (r6v6 android.widget.RelativeLayout)
 binds: [B:42:0x00a5, B:35:0x0092, B:28:0x007f, B:19:0x0066] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.recyclerview.widget.RecyclerView.g
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(me.hisn.mypanel.e.b bVar, int i) {
        android.widget.RelativeLayout relativeLayout;
        android.widget.CheckBox checkBox;
        java.lang.String str;
        java.lang.StringBuilder sb;
        java.lang.String str2;
        me.hisn.mypanel.f fVar = this.f687c.get(i);
        try {
            bVar.t.setImageDrawable(fVar.f690a);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        bVar.u.setText(fVar.f691b);
        int i2 = this.e;
        if ((i2 & 4) != 0) {
            checkBox = bVar.w;
            str = f;
            sb = new java.lang.StringBuilder();
            str2 = fVar.f692c;
        } else {
            if ((i2 & 8) == 0) {
                float f2 = 1.0f;
                if ((i2 & 2) != 0) {
                    if (fVar.f != 2) {
                        return;
                    }
                    relativeLayout = bVar.v;
                    if (!me.hisn.utils.b.b(fVar.e)) {
                        f2 = 0.5f;
                    }
                } else if ((i2 & 16) != 0) {
                    if (fVar.f != 2) {
                        return;
                    }
                    relativeLayout = bVar.v;
                    if (!me.hisn.utils.b.d(fVar.e)) {
                        f2 = 0.5f;
                    }
                } else if ((i2 & 32) != 0) {
                    if (fVar.f != 2) {
                        return;
                    }
                    relativeLayout = bVar.v;
                    if (!me.hisn.utils.b.c(fVar.e)) {
                        f2 = 0.5f;
                    }
                } else {
                    if ((i2 & 128) != 0) {
                        return;
                    }
                    int i3 = fVar.f;
                    relativeLayout = bVar.v;
                    if (i3 != 2) {
                        relativeLayout.setAlpha(0.5f);
                        return;
                    } else if (me.hisn.utils.b.f(fVar.e)) {
                        f2 = 0.5f;
                    }
                }
                relativeLayout.setAlpha(f2);
                return;
            }
            checkBox = bVar.w;
            str = f;
            sb = new java.lang.StringBuilder();
            str2 = fVar.d;
        }
        sb.append(str2);
        sb.append("&");
        checkBox.setChecked(str.contains(sb.toString()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.g
    public me.hisn.mypanel.e.b b(android.view.ViewGroup viewGroup, int i) {
        me.hisn.mypanel.e.b bVar = new me.hisn.mypanel.e.b(this, android.view.LayoutInflater.from(viewGroup.getContext()).inflate(me.hisn.mygesture.R.layout.pkg_item, viewGroup, false));
        a(bVar);
        return bVar;
    }
}
