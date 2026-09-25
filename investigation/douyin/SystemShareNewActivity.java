package com.ss.android.ugc.aweme.share.ui;

import X.0NOW;
import X.0gib;
import X.138m;
import X.13Tr;
import X.C08650KJj;
import X.C0KKB;
import X.C0KKO;
import X.C0KKQ;
import X.C0KLH;
import X.C0KNS;
import X.C0KXY;
import X.C0KZR;
import Y.ACListenerS46S0100000_12;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FixSpecialEffectControllerSetting;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.IgnoreAttachWindowSpecialEffectController;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.GlobalProxyLancet;
import com.bytedance.android.btm.api.BtmSDK;
import com.bytedance.common.utility.UIUtils;
import com.bytedance.dux.indicator.IndicatorExternalsKt;
import com.bytedance.dux.titlebar.listener.OnTitleBarClickListener;
import com.bytedance.ies.dmt.ui.widget.DmtTextView;
import com.bytedance.ies.ugc.aha.util.io.FileUtil;
import com.bytedance.ies.ugc.appcontext.ApplicationHolder;
import com.bytedance.ies.ugc.aweme.commercialize.splash.service.SplashAdServices;
import com.bytedance.lighten.loader.SmartImageView;
import com.bytedance.memoryx.StringBuilderCache;
import com.bytedance.qss.common.catcher.CatcherTrace;
import com.bytedance.squirtle.security.inlet.entry.ExportedComposeEntry;
import com.bytedance.sysoptimizer.EnterTransitionCrashOptimizer;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.ViewOffsetBehavior;
import com.gyf.barlibrary.ImmersionBar;
import com.ss.android.agilelogger.ALog;
import com.ss.android.ugc.aweme.app.event.EventMapBuilder;
import com.ss.android.ugc.aweme.base.FrescoHelper;
import com.ss.android.ugc.aweme.base.utils.ThreadUtils;
import com.ss.android.ugc.aweme.common.TrackerAttributionWrapper;
import com.ss.android.ugc.aweme.experiment.LocationStateOptAB;
import com.ss.android.ugc.aweme.im.IMProxy;
import com.ss.android.ugc.aweme.im.sdk.systemshare.SystemSearchShareFragment;
import com.ss.android.ugc.aweme.im.service.IIMService;
import com.ss.android.ugc.aweme.lancet.jarvis.UserActionLancet;
import com.ss.android.ugc.aweme.ml.api.MLCommonService;
import com.ss.android.ugc.aweme.openplatform.share.ShareCallbackMob;
import com.ss.android.ugc.aweme.openplatform.share.ShareDataWrapService;
import com.ss.android.ugc.aweme.openplatform.share.ShareSDKFrom;
import com.ss.android.ugc.aweme.openplatform.share.share.OpenShareResult;
import com.ss.android.ugc.aweme.openplatform.share.share.SharePipeline;
import com.ss.android.ugc.aweme.preinstall.PreinstallUtils;
import com.ss.android.ugc.aweme.share.OpenPlatformShareRealActivity;
import com.ss.android.ugc.aweme.share.ui.SystemShareNewActivity;
import com.ss.android.ugc.aweme.utils.LocationStateHelper;
import com.ss.android.ugc.bytex.async.stack.broken_chain.CallbackRunnable;
import com.ss.android.ugc.playerkit.exp.PlayerSettingCenter;
import com.ss.android.ugc.playerkit.utils.VideoBrightHelper;
import com.ss.aweme.paas.AwemePaasTargetUtilsKt;
import com.ss.aweme.paas.CallScope;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.internal.AFLambdaS311S0000000_12;
import kotlin.jvm.internal.ALambdaS860S0100000_12;
import kotlin.jvm.internal.ALambdaS915S0100000_12;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: D:\Documents\try\in\investigation\douyin\classes5.dex */
public final class SystemShareNewActivity extends OpenPlatformShareRealActivity implements 0NOW {
    public ShareDataWrapService n;
    public boolean p;
    public boolean q;
    public C0KZR r;
    public SmartImageView s;
    public RecyclerView t;
    public DmtTextView u;
    public final boolean o = C0KXY.LIZ();
    public final Lazy v = LazyKt__LazyJVMKt.lazy(AFLambdaS311S0000000_12.get$arr$(442));
    public final Lazy w = LazyKt__LazyJVMKt.lazy(AFLambdaS311S0000000_12.get$arr$(441));
    public final Lazy x = LazyKt__LazyJVMKt.lazy(AFLambdaS311S0000000_12.get$arr$(443));
    public final Lazy y = LazyKt__LazyJVMKt.lazy(new ALambdaS860S0100000_12(this, 242));

    public final void G3() {
        C0KXY.LJIIJ("takewords");
        u4("message");
    }

    public final String g2() {
        return ((C0KNS) this.x.getValue()).LJIIIIZZ;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onAttachedToWindow() {
        super/*android.app.Activity*/.onAttachedToWindow();
        ALog.d("OpenPlatform_SHARE_SystemShareNewActivity", "onAttachedToWindow");
    }

    public final void LJLIIL() {
        ((ShareCallbackMob) this.y.getValue()).LIZ(ShareCallbackMob.ShareResult.PUBLISH_SUCCESS, 20000, 20000, null, false, 1);
    }

    public final void U0() {
        getStatusView().setBackgroundResource(2131105175);
        getStatusView().setVisibility(8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ss.android.ugc.aweme.share.OpenPlatformShareRealActivity
    public final void onBackPressed() {
        super.onBackPressed();
        ((SharePipeline) this.v.getValue()).LJ(this, getShareData());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onResume() {
        BtmSDK.INSTANCE.getService().getActivityLifeCycleAopListener().onActivityPreResumeAop(this);
        super.onResume();
        if (this.q && !this.p) {
            s4();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ss.android.ugc.aweme.share.OpenPlatformShareRealActivity
    public final void onStart() {
        BtmSDK.INSTANCE.getService().getActivityLifeCycleAopListener().onActivityPreStartAop(this);
        super.onStart();
    }

    public final void W1() {
        C0KNS c0kns = new C0KNS(new Bundle());
        c0kns.LIZIZ = "";
        c0kns.LIZ = new C0KKB();
        new ShareCallbackMob(c0kns, getShareLaunchTime(), getShareLaunchTime(), this.o).LIZ(ShareCallbackMob.ShareResult.PUBLISHING_FAILED, 20001, 20001, null, false, 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ss.android.ugc.aweme.share.OpenPlatformShareRealActivity
    public final void onStop() {
        super.onStop();
        if (EnterTransitionCrashOptimizer.getContext() != null) {
            try {
                GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_DecorViewLancet_getDecorView(getWindow()).getViewTreeObserver().dispatchOnPreDraw();
            } catch (Throwable th) {
                CatcherTrace.reportThrowable(th, "c.s.a.u.aw.sha.ui.SystemShareNewActivity", "com_ss_android_ugc_aweme_share_ui_SystemShareNewActivity_com_bytedance_sysoptimizer_EnterTransitionLancet_onStop", "java/lang/Throwable", "1");
            }
        }
        try {
            GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_DecorViewLancet_getDecorView(getWindow()).getViewTreeObserver().dispatchOnPreDraw();
        } catch (Throwable th2) {
            CatcherTrace.reportThrowable(th2, "c.s.a.u.aw.sha.ui.SystemShareNewActivity", "com_ss_android_ugc_aweme_share_ui_SystemShareNewActivity_com_ss_android_ugc_aweme_lancet_ActivityEnterTransitionCoordinatorLancet_onStop", "java/lang/Throwable", "1");
        }
    }

    public final void s4() {
        IIMService iIMService = IMProxy.get();
        if (iIMService != null && !iIMService.isImReduction()) {
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            C0KZR c0kzr = this.r;
            if (c0kzr == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imSystemShareFragment");
                c0kzr = null;
            }
            fragmentTransactionBeginTransaction.add(2131378272, c0kzr);
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
            this.p = true;
            this.q = false;
            return;
        }
        U0();
    }

    public final void u() {
        C0KXY.LJIIJ("share");
        u4("message");
        ((ShareCallbackMob) this.y.getValue()).LIZLLL(ShareCallbackMob.TerminalPage.IM);
        ((ShareCallbackMob) this.y.getValue()).LIZJ();
    }

    @Override // com.ss.android.ugc.aweme.share.OpenPlatformShareRealActivity
    public final void r4() {
        getStatusView().setBackgroundResource(2131099720);
        Intent intent = getIntent();
        if (intent != null) {
            intent.putExtra("open_share_sdk_from", ShareSDKFrom.OPEN_SYSTEM_IM.getType());
        }
        getShareData();
        ((SharePipeline) this.v.getValue()).LIZLLL((C0KLH) this.w.getValue());
        ((SharePipeline) this.v.getValue()).LIZLLL(new C0KLH() { // from class: X.0KNC
            @Override // X.C0KLH, X.InterfaceC09540KMu
            public final void LJI(final C08650KJj c08650KJj) {
                Intrinsics.checkNotNullParameter(c08650KJj, "shareData");
                final SystemShareNewActivity systemShareNewActivity = this.LIZ;
                ThreadUtils.runOnUiThread(new Runnable() { // from class: X.0KZO
                    public CallbackRunnable _d_p;

                    /* JADX WARN: Code duplicated, block: B:49:0x01d7  */
                    @Override // java.lang.Runnable
                    public final void run() {
                        float fDip2Px;
                        final AppCompatActivity appCompatActivity = systemShareNewActivity;
                        C08650KJj c08650KJj2 = c08650KJj;
                        appCompatActivity.getClass();
                        LayoutInflater layoutInflaterFrom = LayoutInflater.from(appCompatActivity);
                        Object value = appCompatActivity.e.getValue();
                        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                        GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_ViewInflateLancet_inflate(layoutInflaterFrom, 2131568798, (ViewGroup) value, true);
                        SystemSearchShareFragment systemSearchShareFragmentLJLJLLL = IMProxy.get().getShareService().LJLJLLL();
                        if (systemSearchShareFragmentLJLJLLL != null) {
                            appCompatActivity.r = systemSearchShareFragmentLJLJLLL;
                        }
                        C0KZR c0kzr = appCompatActivity.r;
                        SimpleDraweeView simpleDraweeView = null;
                        C0KZR c0kzr2 = c0kzr;
                        if (c0kzr == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("imSystemShareFragment");
                            c0kzr2 = null;
                        }
                        c0kzr2.KX(appCompatActivity);
                        View viewFindViewById = appCompatActivity.findViewById(2131411831);
                        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
                        View view = (DmtTextView) viewFindViewById;
                        appCompatActivity.u = view;
                        if (view == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvSystemSharePublishGo");
                            view = null;
                        }
                        GlobalProxyLancet.com_ss_android_ugc_aweme_zzz_ZZZRESLancet_setOnClickListener_com_ss_android_ugc_aweme_lancet_jarvis_UserActionLancet_setOnClickListener_com_ss_android_ugc_aweme_lancet_ViewClickLancet_setOnClickListener_com_ss_android_ugc_aweme_lancet_BstHook_setOnClickListener_com_ss_android_ugc_aweme_lancet_AdViewClickLancet_setOnClickListener_com_bytedance_timon_scenecontext_lancet_SceneContextLancet$ViewLancet_setOnClickListener(view, new ACListenerS46S0100000_12(appCompatActivity, 19));
                        View view2 = appCompatActivity.u;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvSystemSharePublishGo");
                            view2 = null;
                        }
                        07ym.LIZJ(view2);
                        appCompatActivity.findViewById(2131394091).setOnTitleBarClickListener(new OnTitleBarClickListener() { // from class: X.0KZS
                            public final void onEndBtnClick(View view3) {
                            }

                            public final void onBackClick(View view3) {
                                appCompatActivity.onBackPressed();
                            }
                        });
                        SimpleDraweeView simpleDraweeViewFindViewById = appCompatActivity.findViewById(2131401085);
                        Intrinsics.checkNotNullExpressionValue(simpleDraweeViewFindViewById, "findViewById(...)");
                        SimpleDraweeView simpleDraweeView2 = simpleDraweeViewFindViewById;
                        if (simpleDraweeView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("sciSystemSharePublishAvatar");
                            simpleDraweeView2 = null;
                        }
                        FrescoHelper.LIZJ(simpleDraweeView2, 0veJ.LJ(0veJ.LIZJ()));
                        View viewFindViewById2 = appCompatActivity.findViewById(2131411832);
                        Intrinsics.checkNotNull(viewFindViewById2, "null cannot be cast to non-null type android.widget.TextView");
                        TextView textView = (TextView) viewFindViewById2;
                        CallScope callScope = new CallScope();
                        AwemePaasTargetUtilsKt.getCurrentAppTarget();
                        AwemePaasTargetUtilsKt.getCurrentAppTarget();
                        if (!callScope.getHasMatched()) {
                            callScope.getCallResult().value = appCompatActivity.getString(2131846817);
                        }
                        textView.setText((CharSequence) callScope.getCallResult().value);
                        if (c08650KJj2.LJ().size() == 1) {
                            C0KKO c0kko = c08650KJj2.LJ().get(0);
                            ((ViewStub) appCompatActivity.findViewById(2131414704)).inflate();
                            DraweeView draweeViewFindViewById = appCompatActivity.findViewById(2131403728);
                            Intrinsics.checkNotNullExpressionValue(draweeViewFindViewById, "findViewById(...)");
                            DraweeView draweeView = (SmartImageView) draweeViewFindViewById;
                            appCompatActivity.s = draweeView;
                            if (draweeView == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("sivSystemShareThing");
                                draweeView = null;
                            }
                            draweeView.getHierarchy().setRoundingParams(RoundingParams.fromCornersRadius(UIUtils.dip2Px(appCompatActivity, 12.0f)));
                            float fDip2Px2 = UIUtils.dip2Px(appCompatActivity, 266.0f);
                            float fDip2Px3 = UIUtils.dip2Px(appCompatActivity, 80.0f);
                            if (Intrinsics.areEqual(c0kko.LIZIZ, "video")) {
                                TextView textView2 = (TextView) appCompatActivity.findViewById(2131412101);
                                C0KKQ c0kkq = c0kko.LJ;
                                if (c0kkq != null) {
                                    int i = C0KZQ.g;
                                    textView2.setText(0H4Z.LIZIZ(c0kkq.LIZJ));
                                    textView2.setVisibility(0);
                                    fDip2Px = (UIUtils.dip2Px(appCompatActivity, 200.0f) * c0kkq.LIZ) / c0kkq.LIZIZ;
                                } else {
                                    fDip2Px = fDip2Px2;
                                }
                            } else {
                                C0KKS c0kks = c0kko.LJFF;
                                if (c0kks != null) {
                                    fDip2Px = (UIUtils.dip2Px(appCompatActivity, 200.0f) * c0kks.LIZ) / c0kks.LIZIZ;
                                } else {
                                    fDip2Px = fDip2Px2;
                                }
                            }
                            View view3 = appCompatActivity.s;
                            if (view3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("sivSystemShareThing");
                                view3 = null;
                            }
                            IndicatorExternalsKt.LIZ(view3, (int) Math.max(Math.min(fDip2Px, fDip2Px2), fDip2Px3));
                            SimpleDraweeView simpleDraweeView3 = appCompatActivity.s;
                            if (simpleDraweeView3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("sivSystemShareThing");
                            } else {
                                simpleDraweeView = simpleDraweeView3;
                            }
                            FrescoHelper.bindImage(simpleDraweeView, 0gib.LIZIZ(c0kko.LIZJ).toString());
                        } else if (c08650KJj2.LJ().size() > 1) {
                            final List<C0KKO> listLJ = c08650KJj2.LJ();
                            ((ViewStub) appCompatActivity.findViewById(2131414703)).inflate();
                            RecyclerView recyclerViewFindViewById = appCompatActivity.findViewById(2131400821);
                            Intrinsics.checkNotNullExpressionValue(recyclerViewFindViewById, "findViewById(...)");
                            RecyclerView recyclerView = recyclerViewFindViewById;
                            appCompatActivity.t = recyclerView;
                            if (recyclerView == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("rvSystemShareThing");
                                recyclerView = null;
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity, 0, false));
                            SimpleDraweeView simpleDraweeView4 = appCompatActivity.t;
                            if (simpleDraweeView4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("rvSystemShareThing");
                            } else {
                                simpleDraweeView = simpleDraweeView4;
                            }
                            simpleDraweeView.setAdapter(new RecyclerView.Adapter<C0KZQ>(listLJ) { // from class: X.0KZP
                                public final List<C0KKO> d;

                                public final int getItemCount() {
                                    return this.d.size();
                                }

                                {
                                    Intrinsics.checkNotNullParameter(listLJ, "mediaList");
                                    this.d = listLJ;
                                }

                                public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                                    Intrinsics.checkNotNullParameter(viewGroup, "parent");
                                    View viewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_inflate = GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_ViewInflateLancet_inflate(LayoutInflater.from(viewGroup.getContext()), 2131568789, viewGroup, false);
                                    Intrinsics.checkNotNull(viewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_inflate);
                                    return new C0KZQ(viewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_inflate);
                                }

                                public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                                    boolean z;
                                    float fDip2Px4;
                                    int i3;
                                    float fDip2Px5;
                                    float fDip2Px6;
                                    GenericDraweeHierarchy hierarchy;
                                    C0KZQ c0kzq = (C0KZQ) viewHolder;
                                    Intrinsics.checkNotNullParameter(c0kzq, "holder");
                                    C0KKO c0kko2 = this.d.get(i2);
                                    boolean z2 = true;
                                    if (c0kzq.getAdapterPosition() == 0) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    if (c0kzq.getAdapterPosition() != this.d.size() - 1) {
                                        z2 = false;
                                    }
                                    Intrinsics.checkNotNullParameter(c0kko2, "openMediaInfo");
                                    SimpleDraweeView simpleDraweeViewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById = GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById(c0kzq.d, 2131403728);
                                    float f = c0kzq.e;
                                    if (simpleDraweeViewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById != null && (hierarchy = simpleDraweeViewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById.getHierarchy()) != null) {
                                        hierarchy.setRoundingParams(RoundingParams.fromCornersRadius(UIUtils.dip2Px(c0kzq.d.getContext(), 12.0f)));
                                    }
                                    if (Intrinsics.areEqual(c0kko2.LIZIZ, "video")) {
                                        TextView textView3 = (TextView) GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById(c0kzq.d, 2131412101);
                                        C0KKQ c0kkq2 = c0kko2.LJ;
                                        if (c0kkq2 != null) {
                                            int i4 = C0KZQ.g;
                                            textView3.setText(0H4Z.LIZIZ(c0kkq2.LIZJ));
                                            textView3.setVisibility(0);
                                            fDip2Px4 = UIUtils.dip2Px(c0kzq.d.getContext(), 200.0f) * c0kkq2.LIZ;
                                            i3 = c0kkq2.LIZIZ;
                                            f = fDip2Px4 / i3;
                                        }
                                    } else {
                                        C0KKS c0kks2 = c0kko2.LJFF;
                                        if (c0kks2 != null) {
                                            fDip2Px4 = UIUtils.dip2Px(c0kzq.d.getContext(), 200.0f) * c0kks2.LIZ;
                                            i3 = c0kks2.LIZIZ;
                                            f = fDip2Px4 / i3;
                                        }
                                    }
                                    Intrinsics.checkNotNull(simpleDraweeViewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById);
                                    IndicatorExternalsKt.LIZ(simpleDraweeViewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById, (int) Math.max(Math.min(f, c0kzq.e), c0kzq.f));
                                    FrescoHelper.bindImage(simpleDraweeViewCom_ss_android_ugc_aweme_lancet_ViewInflateLancet_findViewById, 0gib.LIZIZ(c0kko2.LIZJ).toString());
                                    View view4 = c0kzq.d;
                                    ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(c0kzq.d.getLayoutParams());
                                    if (z) {
                                        fDip2Px5 = UIUtils.dip2Px(c0kzq.d.getContext(), 16.0f);
                                    } else {
                                        fDip2Px5 = UIUtils.dip2Px(c0kzq.d.getContext(), 6.0f);
                                    }
                                    marginLayoutParams.setMarginStart((int) fDip2Px5);
                                    if (z2) {
                                        fDip2Px6 = UIUtils.dip2Px(c0kzq.d.getContext(), 16.0f);
                                    } else {
                                        fDip2Px6 = UIUtils.dip2Px(c0kzq.d.getContext(), 6.0f);
                                    }
                                    marginLayoutParams.setMarginEnd((int) fDip2Px6);
                                    GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_ViewInflateLancet_setLayoutParams(view4, marginLayoutParams);
                                }
                            });
                        }
                        if (appCompatActivity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                            if (!appCompatActivity.p) {
                                appCompatActivity.s4();
                            }
                        } else {
                            appCompatActivity.q = true;
                            C08400KIk.LJ("OpenPlatform_SHARE_SystemShareNewActivity", "not add imFragment, wait resume");
                        }
                        TrackerAttributionWrapper.mobClickHelperOnEventV3("dysdk_system_share_panel_show", "com.ss.android.ugc.aweme.openplatform.manager.EventHelper", "com.ss.android.ugc.aweme.openplatform.manager.EventHelper", "systemSharePanelShow", "()V", "0");
                        ((C0KN4) systemShareNewActivity.w.getValue()).LIZ.remove("end");
                    }
                });
            }

            @Override // X.C0KLH, X.InterfaceC09540KMu
            public final void LJII(C08650KJj c08650KJj) {
                Intrinsics.checkNotNullParameter(c08650KJj, "shareData");
                this.LIZ.finish();
            }

            @Override // X.C0KLH, X.InterfaceC09540KMu
            public final void LJIIIIZZ(C08650KJj c08650KJj) {
                Intrinsics.checkNotNullParameter(c08650KJj, "shareData");
                this.LIZ.getStatusView().showLoading();
            }

            @Override // X.C0KLH, X.InterfaceC09540KMu
            public final void LJ(Activity activity, OpenShareResult openShareResult) {
                Intrinsics.checkNotNullParameter(openShareResult, "result");
                try {
                    this.LIZ.finish();
                } catch (Exception e) {
                    CatcherTrace.reportThrowable(e, "c.s.a.u.aw.sha.ui.SystemShareNewActivity$checkoutShareState$1", "onShareResult", "java/lang/Exception", "1");
                    C08400KIk.LIZLLL("OpenPlatform_SHARE_SystemShareNewActivity", e);
                }
            }
        });
        this.h.add(((SharePipeline) this.v.getValue()).LJIIJJI(this, getShareData(), false, new ALambdaS915S0100000_12(this, 967)));
        this.f = "friends";
    }

    public final void x0() {
        u4("search");
        View viewFindViewById = findViewById(2131362530);
        CoordinatorLayout.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams");
        final ViewOffsetBehavior behavior = layoutParams.getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            final int i = -viewFindViewById.getMeasuredHeight();
            final int topAndBottomOffset = behavior.getTopAndBottomOffset();
            float f = topAndBottomOffset / i;
            long j = (long) (300 * (1.0f - f));
            if (j <= 0) {
                return;
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, 1.0f);
            valueAnimatorOfFloat.setDuration(j);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: X.0KXH
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Intrinsics.checkNotNullParameter(valueAnimator, "it");
                    Object animatedValue = valueAnimator.getAnimatedValue();
                    Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
                    int iFloatValue = (int) (((Float) animatedValue).floatValue() * i);
                    if (topAndBottomOffset != iFloatValue) {
                        behavior.setTopAndBottomOffset(iFloatValue);
                    }
                }
            });
            valueAnimatorOfFloat.start();
        }
    }

    public final void LJZL() {
        C0KZR c0kzr;
        long length;
        C08650KJj shareData = getShareData();
        ArrayList arrayList = new ArrayList();
        shareData.LJ().size();
        Iterator<T> it = shareData.LJ().iterator();
        while (true) {
            c0kzr = null;
            if (!it.hasNext()) {
                break;
            }
            C0KKO c0kko = (C0KKO) it.next();
            if (Intrinsics.areEqual(c0kko.LIZIZ, "video") && c0kko.LJ != null) {
                C0KZR c0kzr2 = this.r;
                if (c0kzr2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imSystemShareFragment");
                } else {
                    c0kzr = c0kzr2;
                }
                C0KKQ c0kkq = c0kko.LJ;
                Intrinsics.checkNotNull(c0kkq);
                long j = c0kkq.LIZJ;
                FileUtil fileUtil = FileUtil.INSTANCE;
                String str = c0kko.LIZJ;
                fileUtil.getClass();
                File file = new File(str);
                if (file.isDirectory()) {
                    length = FileUtil.LJIILIIL(file);
                } else if (file.exists() && file.isFile()) {
                    length = file.length();
                } else {
                    length = -1;
                }
                if (c0kzr.FX(j, length)) {
                }
            }
            arrayList.add(new Pair(0gib.LIZIZ(c0kko.LIZJ).toString(), Boolean.valueOf(Intrinsics.areEqual(c0kko.LIZIZ, "video"))));
        }
        if (!arrayList.isEmpty()) {
            C0KZR c0kzr3 = this.r;
            if (c0kzr3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imSystemShareFragment");
            } else {
                c0kzr = c0kzr3;
            }
            c0kzr.GX(arrayList);
        }
    }

    public static void u4(String str) {
        EventMapBuilder eventMapBuilderNewBuilder = EventMapBuilder.newBuilder();
        eventMapBuilderNewBuilder.appendParam("click_type", str);
        TrackerAttributionWrapper.mobClickHelperOnEventV3("system_share_panel_click", eventMapBuilderNewBuilder.builder(), "com.ss.android.ugc.aweme.share.ui.SystemShareNewActivity", "com.ss.android.ugc.aweme.share.ui.SystemShareNewActivity", "logSystemSharePanelClick", "(Ljava/lang/String;)V", "0");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ss.android.ugc.aweme.share.OpenPlatformShareRealActivity
    public final void onCreate(Bundle bundle) {
        ExportedComposeEntry.onExportedActivityInlet(this);
        String name = SystemShareNewActivity.class.getName();
        List activityList = FixSpecialEffectControllerSetting.INSTANCE.getActivityList();
        if (activityList != null && !activityList.isEmpty() && activityList.contains(name)) {
            IgnoreAttachWindowSpecialEffectController.setController(getSupportFragmentManager());
        }
        ALog.d("OpenPlatform_SHARE_SystemShareNewActivity", "onCreate");
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(2131099935);
        }
        super.onCreate(bundle);
        ImmersionBar immersionBarWith = ImmersionBar.with(this);
        immersionBarWith.statusBarColor(2131099935);
        immersionBarWith.autoStatusBarDarkModeEnable(true);
        immersionBarWith.init();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onWindowFocusChanged(boolean z) {
        if (LocationStateOptAB.isCacheEnable()) {
            StringBuilder sb = StringBuilderCache.get();
            sb.append("window-focus: ");
            sb.append(z);
            LocationStateHelper.dispatchStateChanged(StringBuilderCache.release(sb));
        }
        if (PreinstallUtils.LIZIZ(ApplicationHolder.getApplication())) {
            super.onWindowFocusChanged(z);
            ALog.d("OpenPlatform_SHARE_SystemShareNewActivity", "onWindowFocusChanged");
            return;
        }
        if (PlayerSettingCenter.getHandleBrightOnWindowFocusChanged()) {
            VideoBrightHelper.getInstance().recoverSystemAdjust(this, z);
        }
        MLCommonService.instance().onWindowFocusChanged(this, z);
        SplashAdServices.getSplashServiceV2().onWindowFocusChanged(this, z);
        super.onWindowFocusChanged(z);
        ALog.d("OpenPlatform_SHARE_SystemShareNewActivity", "onWindowFocusChanged");
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        long interactionEventType = UserActionLancet.getInteractionEventType(i);
        if (138m.LIZIZ() && interactionEventType > 0 && 13Tr.LIZ) {
            138m.LJFF(interactionEventType, System.currentTimeMillis(), SystemShareNewActivity.class.getName(), (Map) null);
        }
        return super/*androidx.appcompat.app.AppCompatActivity*/.onKeyDown(i, keyEvent);
    }
}
