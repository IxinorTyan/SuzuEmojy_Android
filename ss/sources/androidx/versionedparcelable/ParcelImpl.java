package androidx.versionedparcelable;

/* JADX INFO: loaded from: classes.dex */
@android.annotation.SuppressLint({"BanParcelableUsage"})
public class ParcelImpl implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<androidx.versionedparcelable.ParcelImpl> CREATOR = new androidx.versionedparcelable.ParcelImpl.a();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final androidx.versionedparcelable.c f343a;

    static class a implements android.os.Parcelable.Creator<androidx.versionedparcelable.ParcelImpl> {
        a() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public androidx.versionedparcelable.ParcelImpl createFromParcel(android.os.Parcel parcel) {
            return new androidx.versionedparcelable.ParcelImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public androidx.versionedparcelable.ParcelImpl[] newArray(int i) {
            return new androidx.versionedparcelable.ParcelImpl[i];
        }
    }

    protected ParcelImpl(android.os.Parcel parcel) {
        this.f343a = new androidx.versionedparcelable.b(parcel).j();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        new androidx.versionedparcelable.b(parcel).a(this.f343a);
    }
}
