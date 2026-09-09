package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class m {

    class a extends me.hisn.utils.j {
        a(me.hisn.utils.m mVar) {
        }

        @Override // me.hisn.utils.j
        public void b() {
        }
    }

    public void a(android.content.Context context) {
        a(context, ((me.hisn.mygesture.P) context.getApplicationContext()).f537a == null);
    }

    public void a(android.content.Context context, boolean z) {
        me.hisn.mygesture.P p = (me.hisn.mygesture.P) context.getApplicationContext();
        me.hisn.utils.m.a aVar = new me.hisn.utils.m.a(this);
        int iB = -1;
        if (!z) {
            android.view.View view = p.f537a;
            if (view != null) {
                int iIntValue = ((java.lang.Integer) view.getTag()).intValue();
                if (iIntValue == -1) {
                    aVar.b(context, 1);
                } else {
                    aVar.a(context, iIntValue);
                }
                try {
                    me.hisn.mygesture.MAS.k().removeView(p.f537a);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
                android.app.NotificationManager notificationManager = (android.app.NotificationManager) p.f537a.getContext().getSystemService("notification");
                if (notificationManager != null) {
                    try {
                        notificationManager.cancel(1429217280);
                    } catch (java.lang.Exception e2) {
                        e2.printStackTrace();
                    }
                }
                p.f537a = null;
                return;
            }
            return;
        }
        if (p.f537a == null) {
            android.view.View view2 = new android.view.View(context.getApplicationContext());
            p.f537a = view2;
            view2.setBackgroundColor(1429217280);
            if (aVar.a(context) == 1) {
                aVar.b(context, 0);
            } else {
                iB = aVar.b(context);
            }
            aVar.a(context, 0);
            p.f537a.setTag(java.lang.Integer.valueOf(iB));
            int iMax = java.lang.Math.max(me.hisn.mygesture.P.l0, me.hisn.mygesture.P.k0) + 600;
            try {
                me.hisn.mygesture.MAS.k().addView(p.f537a, new me.hisn.utils.v().a(false, 17, iMax, iMax, 0, 0, 0, false));
                android.content.Intent intent = new android.content.Intent(context, (java.lang.Class<?>) me.hisn.utils.RemoteA.class);
                intent.putExtra("31415", 83);
                new me.hisn.utils.d0(context).a(1429217280, p.getString(me.hisn.mygesture.R.string.eyes_care_on), context.getString(me.hisn.mygesture.R.string.click_to_cancel), intent, false);
            } catch (java.lang.Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
