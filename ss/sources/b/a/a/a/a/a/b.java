package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public final class b extends b.a.a.a.a.a.e implements b.a.a.a.a.a.d {
    b(android.os.IBinder iBinder) {
        super(iBinder, "com.android.vending.billing.IInAppBillingService");
    }

    @Override // b.a.a.a.a.a.d
    public final int a(int i, java.lang.String str, java.lang.String str2) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(i);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        android.os.Parcel parcelA2 = a(1, parcelA);
        int i2 = parcelA2.readInt();
        parcelA2.recycle();
        return i2;
    }

    @Override // b.a.a.a.a.a.d
    public final android.os.Bundle a(int i, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(9);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        b.a.a.a.a.a.g.a(parcelA, bundle);
        android.os.Parcel parcelA2 = a(902, parcelA);
        android.os.Bundle bundle2 = (android.os.Bundle) b.a.a.a.a.a.g.a(parcelA2, android.os.Bundle.CREATOR);
        parcelA2.recycle();
        return bundle2;
    }

    @Override // b.a.a.a.a.a.d
    public final android.os.Bundle a(int i, java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.os.Bundle bundle2) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(10);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        b.a.a.a.a.a.g.a(parcelA, bundle);
        b.a.a.a.a.a.g.a(parcelA, bundle2);
        android.os.Parcel parcelA2 = a(901, parcelA);
        android.os.Bundle bundle3 = (android.os.Bundle) b.a.a.a.a.a.g.a(parcelA2, android.os.Bundle.CREATOR);
        parcelA2.recycle();
        return bundle3;
    }

    @Override // b.a.a.a.a.a.d
    public final android.os.Bundle a(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(3);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        parcelA.writeString(str3);
        android.os.Parcel parcelA2 = a(4, parcelA);
        android.os.Bundle bundle = (android.os.Bundle) b.a.a.a.a.a.g.a(parcelA2, android.os.Bundle.CREATOR);
        parcelA2.recycle();
        return bundle;
    }

    @Override // b.a.a.a.a.a.d
    public final android.os.Bundle a(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(9);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        parcelA.writeString(str3);
        b.a.a.a.a.a.g.a(parcelA, bundle);
        android.os.Parcel parcelA2 = a(11, parcelA);
        android.os.Bundle bundle2 = (android.os.Bundle) b.a.a.a.a.a.g.a(parcelA2, android.os.Bundle.CREATOR);
        parcelA2.recycle();
        return bundle2;
    }

    @Override // b.a.a.a.a.a.d
    public final android.os.Bundle a(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(3);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        parcelA.writeString(str3);
        parcelA.writeString(null);
        android.os.Parcel parcelA2 = a(3, parcelA);
        android.os.Bundle bundle = (android.os.Bundle) b.a.a.a.a.a.g.a(parcelA2, android.os.Bundle.CREATOR);
        parcelA2.recycle();
        return bundle;
    }

    @Override // b.a.a.a.a.a.d
    public final android.os.Bundle a(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.os.Bundle bundle) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(i);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        parcelA.writeString(str3);
        parcelA.writeString(null);
        b.a.a.a.a.a.g.a(parcelA, bundle);
        android.os.Parcel parcelA2 = a(8, parcelA);
        android.os.Bundle bundle2 = (android.os.Bundle) b.a.a.a.a.a.g.a(parcelA2, android.os.Bundle.CREATOR);
        parcelA2.recycle();
        return bundle2;
    }

    @Override // b.a.a.a.a.a.d
    public final android.os.Bundle b(int i, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        android.os.Parcel parcelA = a();
        parcelA.writeInt(3);
        parcelA.writeString(str);
        parcelA.writeString(str2);
        b.a.a.a.a.a.g.a(parcelA, bundle);
        android.os.Parcel parcelA2 = a(2, parcelA);
        android.os.Bundle bundle2 = (android.os.Bundle) b.a.a.a.a.a.g.a(parcelA2, android.os.Bundle.CREATOR);
        parcelA2.recycle();
        return bundle2;
    }
}
