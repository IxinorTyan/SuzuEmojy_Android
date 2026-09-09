package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class c extends b.a.a.a.a.a.f implements b.a.a.a.a.a.d {
    public static b.a.a.a.a.a.d a(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        android.os.IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.vending.billing.IInAppBillingService");
        return iInterfaceQueryLocalInterface instanceof b.a.a.a.a.a.d ? (b.a.a.a.a.a.d) iInterfaceQueryLocalInterface : new b.a.a.a.a.a.b(iBinder);
    }
}
