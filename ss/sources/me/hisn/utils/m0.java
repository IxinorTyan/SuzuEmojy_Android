package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class m0 {
    public android.graphics.drawable.Drawable a(android.content.Context context, android.graphics.drawable.Drawable drawable) {
        androidx.core.graphics.drawable.b bVarA = androidx.core.graphics.drawable.c.a(context.getResources(), new me.hisn.utils.h().a(drawable));
        bVarA.a(drawable.getIntrinsicWidth() / 2.0f);
        return bVarA;
    }
}
