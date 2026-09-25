package com.yxcorp.gifshow.reminder.thirdpartyshare;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import c4n.d;
import com.kwai.component.uiconfig.childlock.util.ChildLockUtil;
import com.kwai.feature.api.social.login.model.LoginParams;
import com.kwai.framework.hack.resource.ResourcesManager;
import com.kwai.framework.model.user.QCurrentUser;
import com.kwai.plugin.dva.feature.core.loader.AssetManagerHook;
import com.kwai.robust.PatchProxy;
import com.kwai.robust.PatchProxyResult;
import com.yxcorp.gifshow.activity.GifshowActivity;
import com.yxcorp.gifshow.reminder.thirdpartyshare.ThirdPartyShareFriendsActivity;
import com.yxcorp.gifshow.util.PermissionUtils;
import io.reactivex.internal.functions.Functions;
import java.util.Objects;
import l3n.h;
import rcc.i;
import vcn.g;

/* JADX INFO: compiled from: kSourceFile */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\kuaishou\classes19.dex */
public class ThirdPartyShareFriendsActivity extends GifshowActivity {
    public static final /* synthetic */ int c = 0;

    /* JADX WARN: Multi-variable type inference failed */
    public Resources getResources() {
        Object objApply = PatchProxy.apply(this, ThirdPartyShareFriendsActivity.class, "2");
        if (objApply != PatchProxyResult.class) {
            return (Resources) objApply;
        }
        ResourcesManager.loadResources(this, super/*android.view.ContextThemeWrapper*/.getResources());
        AssetManagerHook.loadSplitResourcesIfResourceOpening(this, super/*android.view.ContextThemeWrapper*/.getResources());
        return super/*android.view.ContextThemeWrapper*/.getResources();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        if (PatchProxy.applyVoidOneRefs(bundle, this, ThirdPartyShareFriendsActivity.class, "1")) {
            return;
        }
        super.onCreate(bundle);
        h.g(this, 0);
        if (com.kwai.sdk.switchconfig.a.E().getBooleanValue("shareFromSystemPhotoAlbumToMessage", true) && !ChildLockUtil.b()) {
            PermissionUtils.g(this, "android.permission.READ_EXTERNAL_STORAGE").subscribe(new g() { // from class: com.yxcorp.gifshow.reminder.thirdpartyshare.b
                /* JADX WARN: Type inference failed for: r0v0, types: [android.app.Activity, android.content.Context, com.yxcorp.gifshow.activity.GifshowActivity, com.yxcorp.gifshow.reminder.thirdpartyshare.ThirdPartyShareFriendsActivity, java.lang.Object] */
                public final void accept(Object obj) {
                    final ?? r0 = this.b;
                    int i = ThirdPartyShareFriendsActivity.c;
                    Objects.requireNonNull(r0);
                    if (((c3f.a) obj).b) {
                        if (!QCurrentUser.ME.isLogined()) {
                            LoginParams.a aVar = new LoginParams.a();
                            aVar.d(0);
                            d.b(-1712118428).qI0((Context) r0, 0, aVar.a(), new zdm.a() { // from class: sfl.b
                                public final void onActivityCallback(int i2, int i3, Intent intent) {
                                    GifshowActivity gifshowActivity = r0;
                                    int i4 = ThirdPartyShareFriendsActivity.c;
                                    Objects.requireNonNull(gifshowActivity);
                                    if (QCurrentUser.me().isLogined()) {
                                        gifshowActivity.setEnableFinishTransition(false);
                                        new com.yxcorp.gifshow.reminder.thirdpartyshare.c(gifshowActivity, gifshowActivity.getIntent()).a();
                                    } else {
                                        gifshowActivity.finish();
                                    }
                                }
                            });
                            return;
                        }
                        r0.setEnableFinishTransition(false);
                        new c(r0, r0.getIntent()).a();
                        return;
                    }
                    i.b(2131887842, 2131834968);
                    r0.finish();
                }
            }, Functions.e());
        } else {
            i.b(2131887844, 2131834513);
            finish();
        }
    }
}
