package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class o0 {
    public static android.graphics.Bitmap a(android.content.Context context, android.os.Bundle bundle) {
        android.graphics.Bitmap bitmap = (android.graphics.Bitmap) bundle.getParcelable("android.intent.extra.shortcut.ICON");
        if (bitmap != null) {
            return bitmap;
        }
        android.content.Intent.ShortcutIconResource shortcutIconResource = (android.content.Intent.ShortcutIconResource) bundle.get("android.intent.extra.shortcut.ICON_RESOURCE");
        android.graphics.drawable.Drawable drawable = null;
        if (shortcutIconResource == null) {
            return null;
        }
        try {
            android.content.res.Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(shortcutIconResource.packageName);
            drawable = resourcesForApplication.getDrawable(resourcesForApplication.getIdentifier(shortcutIconResource.resourceName, "drawable", shortcutIconResource.packageName));
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return new me.hisn.mypanel.c().a(drawable);
    }
}
