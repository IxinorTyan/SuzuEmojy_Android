package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public class e implements android.os.IInterface {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final android.os.IBinder f362a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.lang.String f363b = "com.android.vending.billing.IInAppBillingService";

    protected e(android.os.IBinder iBinder, java.lang.String str) {
        this.f362a = iBinder;
    }

    protected final android.os.Parcel a() {
        android.os.Parcel parcelObtain = android.os.Parcel.obtain();
        parcelObtain.writeInterfaceToken(this.f363b);
        return parcelObtain;
    }

    protected final android.os.Parcel a(int i, android.os.Parcel parcel) {
        android.os.Parcel parcelObtain = android.os.Parcel.obtain();
        try {
            try {
                this.f362a.transact(i, parcel, parcelObtain, 0);
                parcelObtain.readException();
                parcel.recycle();
                return parcelObtain;
            } catch (java.lang.RuntimeException e) {
                parcelObtain.recycle();
                throw e;
            }
        } catch (java.lang.Throwable th) {
            parcel.recycle();
            throw th;
        }
    }

    @Override // android.os.IInterface
    public final android.os.IBinder asBinder() {
        return this.f362a;
    }
}
