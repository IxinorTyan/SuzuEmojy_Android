package a.d.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class a implements android.os.Parcelable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final android.os.Parcelable f122a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final a.d.a.a f121b = new a.d.a.a.C0006a();
    public static final android.os.Parcelable.Creator<a.d.a.a> CREATOR = new a.d.a.a.b();

    /* JADX INFO: renamed from: a.d.a.a$a, reason: collision with other inner class name */
    static class C0006a extends a.d.a.a {
        C0006a() {
            super((a.d.a.a.C0006a) null);
        }
    }

    static class b implements android.os.Parcelable.ClassLoaderCreator<a.d.a.a> {
        b() {
        }

        @Override // android.os.Parcelable.Creator
        public a.d.a.a createFromParcel(android.os.Parcel parcel) {
            return createFromParcel(parcel, (java.lang.ClassLoader) null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public a.d.a.a createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            if (parcel.readParcelable(classLoader) == null) {
                return a.d.a.a.f121b;
            }
            throw new java.lang.IllegalStateException("superState must be null");
        }

        @Override // android.os.Parcelable.Creator
        public a.d.a.a[] newArray(int i) {
            return new a.d.a.a[i];
        }
    }

    private a() {
        this.f122a = null;
    }

    /* synthetic */ a(a.d.a.a.C0006a c0006a) {
        this();
    }

    protected a(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        android.os.Parcelable parcelable = parcel.readParcelable(classLoader);
        this.f122a = parcelable == null ? f121b : parcelable;
    }

    protected a(android.os.Parcelable parcelable) {
        if (parcelable == null) {
            throw new java.lang.IllegalArgumentException("superState must not be null");
        }
        this.f122a = parcelable == f121b ? null : parcelable;
    }

    public final android.os.Parcelable a() {
        return this.f122a;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.f122a, i);
    }
}
