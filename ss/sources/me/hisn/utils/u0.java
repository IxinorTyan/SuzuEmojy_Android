package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class u0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static java.lang.String f873a = "mg_theme";

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static java.lang.String f874b = "theme_type";

    public static void a(android.content.Context context, int i) {
        context.getSharedPreferences(f873a, 0).edit().putInt(f874b, i).apply();
    }

    public static boolean a(android.content.Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static int b(android.content.Context context) {
        int iC = c(context);
        if (iC != 1) {
            return (iC == 2 && a(context)) ? me.hisn.mygesture.R.style.AppThemeDark : me.hisn.mygesture.R.style.AppTheme;
        }
        return me.hisn.mygesture.R.style.AppThemeDark;
    }

    public static int c(android.content.Context context) {
        return context.getSharedPreferences(f873a, 0).getInt(f874b, 0);
    }
}
