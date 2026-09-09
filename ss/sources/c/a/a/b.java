package c.a.a;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.content.Context f374a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private java.util.ArrayList<java.lang.String> f375b = null;

    public b(android.content.Context context) {
        this.f374a = context;
    }

    public java.util.List<java.lang.String> a(boolean z) {
        if (this.f375b == null || z) {
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
            this.f375b = arrayList;
            arrayList.add("none");
            for (android.content.pm.ResolveInfo resolveInfo : this.f374a.getPackageManager().queryIntentActivities(new android.content.Intent("com.novalauncher.THEME"), 128)) {
                if (!this.f375b.contains(resolveInfo.activityInfo.packageName)) {
                    this.f375b.add(resolveInfo.activityInfo.packageName);
                }
            }
        }
        return this.f375b;
    }
}
