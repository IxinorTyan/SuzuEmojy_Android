package b.a.a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    static {
        b.a.a.a.a.a.g.class.getClassLoader();
    }

    private g() {
    }

    public static <T extends android.os.Parcelable> T a(android.os.Parcel parcel, android.os.Parcelable.Creator<T> creator) {
        if (parcel.readInt() == 0) {
            return null;
        }
        return creator.createFromParcel(parcel);
    }

    public static void a(android.os.Parcel parcel, android.os.Parcelable parcelable) {
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 0);
    }
}
