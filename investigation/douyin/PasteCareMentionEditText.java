package com.ss.android.ugc.aweme.im.business.awemereplypage.view;

import X.0hLP;
import X.C0UFP;
import X.C23160UsW;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.bytedance.android.replay.ReplayContext;
import com.bytedance.qss.common.catcher.CatcherTrace;
import com.ss.android.ugc.aweme.framework.analysis.CrashlyticsWrapper;
import com.ss.android.ugc.aweme.views.mention.MentionEditText;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: D:\Documents\try\in\investigation\douyin\classes13.dex */
public final class PasteCareMentionEditText extends MentionEditText {
    public final void setOnPasteListener(C0UFP c0ufp) {
    }

    public final boolean onTextContextMenuItem(int i) {
        return super/*com.bytedance.ies.dmt.ui.widget.DmtEditText*/.onTextContextMenuItem(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setImeOptions(int i) {
        super/*android.widget.EditText*/.setImeOptions(i | 33554432);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent;
        try {
            zOnTouchEvent = super.onTouchEvent(motionEvent);
        } catch (IllegalArgumentException e) {
            CatcherTrace.reportThrowable(e, "c.s.a.u.aw.i.bu.awemereplypage.v.pas6", "com_ss_android_ugc_aweme_im_business_awemereplypage_view_PasteCareMentionEditText__onTouchEvent$___twin___", "java/lang/IllegalArgumentException", "1");
            CrashlyticsWrapper.catchException(e);
            zOnTouchEvent = false;
        } catch (IndexOutOfBoundsException e2) {
            CatcherTrace.reportThrowable(e2, "c.s.a.u.aw.i.bu.awemereplypage.v.pas6", "com_ss_android_ugc_aweme_im_business_awemereplypage_view_PasteCareMentionEditText__onTouchEvent$___twin___", "java/lang/IndexOutOfBoundsException", "1");
            CrashlyticsWrapper.catchException(e2);
            zOnTouchEvent = false;
        }
        Boolean boolValueOf = Boolean.valueOf(zOnTouchEvent);
        if (boolValueOf != null) {
            boolean zBooleanValue = boolValueOf.booleanValue();
            try {
                if (ReplayContext.LJFF.LIZIZ && 0hLP.LIZLLL.LIZIZ()) {
                    Intrinsics.checkNotNullExpressionValue(this, "This.get()");
                    v3.LIZJ(zBooleanValue, motionEvent, this);
                    return zBooleanValue;
                }
            } catch (Exception e3) {
                CatcherTrace.reportThrowable(e3, "c.s.a.u.aw.i.bu.awemereplypage.v.pas6", "com_ss_android_ugc_aweme_im_business_awemereplypage_view_PasteCareMentionEditText_com_bytedance_android_replay_business_touch_TouchEventLancet_onTouchEvent", "java/lang/Exception", "1");
            }
            return zBooleanValue;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PasteCareMentionEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        C23160UsW.LIZ(this);
    }
}
