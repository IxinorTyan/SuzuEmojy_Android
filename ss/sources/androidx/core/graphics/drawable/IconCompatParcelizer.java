package androidx.core.graphics.drawable;

/* JADX INFO: loaded from: classes.dex */
public class IconCompatParcelizer {
    public static androidx.core.graphics.drawable.IconCompat read(androidx.versionedparcelable.a aVar) {
        androidx.core.graphics.drawable.IconCompat iconCompat = new androidx.core.graphics.drawable.IconCompat();
        iconCompat.f170a = aVar.a(iconCompat.f170a, 1);
        iconCompat.f172c = aVar.a(iconCompat.f172c, 2);
        iconCompat.d = aVar.a(iconCompat.d, 3);
        iconCompat.e = aVar.a(iconCompat.e, 4);
        iconCompat.f = aVar.a(iconCompat.f, 5);
        iconCompat.g = (android.content.res.ColorStateList) aVar.a(iconCompat.g, 6);
        iconCompat.i = aVar.a(iconCompat.i, 7);
        iconCompat.c();
        return iconCompat;
    }

    public static void write(androidx.core.graphics.drawable.IconCompat iconCompat, androidx.versionedparcelable.a aVar) {
        aVar.a(true, true);
        iconCompat.a(aVar.c());
        int i = iconCompat.f170a;
        if (-1 != i) {
            aVar.b(i, 1);
        }
        byte[] bArr = iconCompat.f172c;
        if (bArr != null) {
            aVar.b(bArr, 2);
        }
        android.os.Parcelable parcelable = iconCompat.d;
        if (parcelable != null) {
            aVar.b(parcelable, 3);
        }
        int i2 = iconCompat.e;
        if (i2 != 0) {
            aVar.b(i2, 4);
        }
        int i3 = iconCompat.f;
        if (i3 != 0) {
            aVar.b(i3, 5);
        }
        android.content.res.ColorStateList colorStateList = iconCompat.g;
        if (colorStateList != null) {
            aVar.b(colorStateList, 6);
        }
        java.lang.String str = iconCompat.i;
        if (str != null) {
            aVar.b(str, 7);
        }
    }
}
