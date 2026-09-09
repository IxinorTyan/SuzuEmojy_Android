package androidx.versionedparcelable;

/* JADX INFO: loaded from: classes.dex */
class b extends androidx.versionedparcelable.a {
    private final android.util.SparseIntArray d;
    private final android.os.Parcel e;
    private final int f;
    private final int g;
    private final java.lang.String h;
    private int i;
    private int j;
    private int k;

    b(android.os.Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "", new a.a.a(), new a.a.a(), new a.a.a());
    }

    private b(android.os.Parcel parcel, int i, int i2, java.lang.String str, a.a.a<java.lang.String, java.lang.reflect.Method> aVar, a.a.a<java.lang.String, java.lang.reflect.Method> aVar2, a.a.a<java.lang.String, java.lang.Class> aVar3) {
        super(aVar, aVar2, aVar3);
        this.d = new android.util.SparseIntArray();
        this.i = -1;
        this.j = 0;
        this.k = -1;
        this.e = parcel;
        this.f = i;
        this.g = i2;
        this.j = i;
        this.h = str;
    }

    @Override // androidx.versionedparcelable.a
    public void a() {
        int i = this.i;
        if (i >= 0) {
            int i2 = this.d.get(i);
            int iDataPosition = this.e.dataPosition();
            this.e.setDataPosition(i2);
            this.e.writeInt(iDataPosition - i2);
            this.e.setDataPosition(iDataPosition);
        }
    }

    @Override // androidx.versionedparcelable.a
    public void a(android.os.Parcelable parcelable) {
        this.e.writeParcelable(parcelable, 0);
    }

    @Override // androidx.versionedparcelable.a
    protected void a(java.lang.CharSequence charSequence) {
        android.text.TextUtils.writeToParcel(charSequence, this.e, 0);
    }

    @Override // androidx.versionedparcelable.a
    public void a(java.lang.String str) {
        this.e.writeString(str);
    }

    @Override // androidx.versionedparcelable.a
    public void a(boolean z) {
        this.e.writeInt(z ? 1 : 0);
    }

    @Override // androidx.versionedparcelable.a
    public void a(byte[] bArr) {
        if (bArr == null) {
            this.e.writeInt(-1);
        } else {
            this.e.writeInt(bArr.length);
            this.e.writeByteArray(bArr);
        }
    }

    @Override // androidx.versionedparcelable.a
    public boolean a(int i) {
        while (this.j < this.g) {
            int i2 = this.k;
            if (i2 == i) {
                return true;
            }
            if (java.lang.String.valueOf(i2).compareTo(java.lang.String.valueOf(i)) > 0) {
                return false;
            }
            this.e.setDataPosition(this.j);
            int i3 = this.e.readInt();
            this.k = this.e.readInt();
            this.j += i3;
        }
        return this.k == i;
    }

    @Override // androidx.versionedparcelable.a
    protected androidx.versionedparcelable.a b() {
        android.os.Parcel parcel = this.e;
        int iDataPosition = parcel.dataPosition();
        int i = this.j;
        if (i == this.f) {
            i = this.g;
        }
        return new androidx.versionedparcelable.b(parcel, iDataPosition, i, this.h + "  ", this.f344a, this.f345b, this.f346c);
    }

    @Override // androidx.versionedparcelable.a
    public void b(int i) {
        a();
        this.i = i;
        this.d.put(i, this.e.dataPosition());
        c(0);
        c(i);
    }

    @Override // androidx.versionedparcelable.a
    public void c(int i) {
        this.e.writeInt(i);
    }

    @Override // androidx.versionedparcelable.a
    public boolean d() {
        return this.e.readInt() != 0;
    }

    @Override // androidx.versionedparcelable.a
    public byte[] e() {
        int i = this.e.readInt();
        if (i < 0) {
            return null;
        }
        byte[] bArr = new byte[i];
        this.e.readByteArray(bArr);
        return bArr;
    }

    @Override // androidx.versionedparcelable.a
    protected java.lang.CharSequence f() {
        return (java.lang.CharSequence) android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this.e);
    }

    @Override // androidx.versionedparcelable.a
    public int g() {
        return this.e.readInt();
    }

    @Override // androidx.versionedparcelable.a
    public <T extends android.os.Parcelable> T h() {
        return (T) this.e.readParcelable(androidx.versionedparcelable.b.class.getClassLoader());
    }

    @Override // androidx.versionedparcelable.a
    public java.lang.String i() {
        return this.e.readString();
    }
}
