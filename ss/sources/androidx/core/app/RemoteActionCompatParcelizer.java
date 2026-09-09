package androidx.core.app;

/* JADX INFO: loaded from: classes.dex */
public class RemoteActionCompatParcelizer {
    public static androidx.core.app.RemoteActionCompat read(androidx.versionedparcelable.a aVar) {
        androidx.core.app.RemoteActionCompat remoteActionCompat = new androidx.core.app.RemoteActionCompat();
        remoteActionCompat.f161a = (androidx.core.graphics.drawable.IconCompat) aVar.a(remoteActionCompat.f161a, 1);
        remoteActionCompat.f162b = aVar.a(remoteActionCompat.f162b, 2);
        remoteActionCompat.f163c = aVar.a(remoteActionCompat.f163c, 3);
        remoteActionCompat.d = (android.app.PendingIntent) aVar.a(remoteActionCompat.d, 4);
        remoteActionCompat.e = aVar.a(remoteActionCompat.e, 5);
        remoteActionCompat.f = aVar.a(remoteActionCompat.f, 6);
        return remoteActionCompat;
    }

    public static void write(androidx.core.app.RemoteActionCompat remoteActionCompat, androidx.versionedparcelable.a aVar) {
        aVar.a(false, false);
        aVar.b(remoteActionCompat.f161a, 1);
        aVar.b(remoteActionCompat.f162b, 2);
        aVar.b(remoteActionCompat.f163c, 3);
        aVar.b(remoteActionCompat.d, 4);
        aVar.b(remoteActionCompat.e, 5);
        aVar.b(remoteActionCompat.f, 6);
    }
}
