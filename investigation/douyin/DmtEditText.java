package com.bytedance.ies.dmt.ui.widget;

import X.0SHA;
import X.0hLP;
import X.0ilF;
import X.0qu6;
import X.C11201Eed;
import X.C11221Eef;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import com.bytedance.android.replay.ReplayContext;
import com.bytedance.dux.api.IDuxEmojiService;
import com.bytedance.dux.utils.EmojiEditTextHelper;
import com.bytedance.dux.utils.EmojiLoadCallbackImpl;
import com.bytedance.qss.common.catcher.CatcherTrace;
import com.ss.android.ugc.aweme.publish.EditorAnrCrashFixSwitch;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: D:\Documents\try\in\investigation\douyin\classes46.dex */
public class DmtEditText extends AppCompatEditText {
    public static 0SHA g;
    public EmojiEditTextHelper d;
    public 0ilF e;
    public final ArrayList<View.OnFocusChangeListener> f;

    public DmtEditText() {
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private EmojiEditTextHelper getEmojiViewHelper() {
        if (this.d == null) {
            this.d = new EmojiEditTextHelper(this);
        }
        return this.d;
    }

    public void setTextContextMenuListener(0ilF r1) {
        this.e = r1;
    }

    public void setFontType(String str) {
        synchronized (0qu6.LIZJ()) {
        }
    }

    public boolean onTextContextMenuItem(int i) {
        0ilF r0;
        boolean zOnTextContextMenuItem = super.onTextContextMenuItem(i);
        if (i == 16908322 && (r0 = this.e) != null) {
            r0.LIZ(zOnTextContextMenuItem);
        }
        return zOnTextContextMenuItem;
    }

    public final boolean onDragEvent(DragEvent dragEvent) throws Exception {
        try {
            return super.onDragEvent(dragEvent);
        } catch (Exception e) {
            CatcherTrace.reportThrowable(e, "c.b.ie.dm.ui.w.dmt13", "odra2e", "java/lang/Exception", "1");
            if (g != null) {
                g.getClass();
                Intrinsics.checkNotNull(e);
                if (EditorAnrCrashFixSwitch.LIZ().getTextEditorDragCrashOpt()) {
                    return false;
                }
                throw e;
            }
            throw e;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Boolean boolValueOf = Boolean.valueOf(super.onTouchEvent(motionEvent));
        if (boolValueOf != null) {
            boolean zBooleanValue = boolValueOf.booleanValue();
            try {
                if (ReplayContext.LJFF.LIZIZ && 0hLP.LIZLLL.LIZIZ()) {
                    v3.LIZJ(zBooleanValue, motionEvent, this);
                    return zBooleanValue;
                }
            } catch (Exception e) {
                CatcherTrace.reportThrowable(e, "c.b.ie.dm.ui.w.dmt13", "com_bytedance_ies_dmt_ui_widget_DmtEditText_com_bytedance_android_replay_business_touch_TouchEventLancet_onTouchEvent", "java/lang/Exception", "1");
            }
            return zBooleanValue;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
    }

    public void setEnableParseEmoji(boolean z) {
        IDuxEmojiService iDuxEmojiServiceLIZ;
        IDuxEmojiService iDuxEmojiServiceLIZ2;
        EmojiEditTextHelper emojiViewHelper = getEmojiViewHelper();
        if (emojiViewHelper.LIZIZ != z) {
            EmojiLoadCallbackImpl emojiLoadCallbackImpl = emojiViewHelper.LIZJ;
            if (emojiLoadCallbackImpl != null && (iDuxEmojiServiceLIZ2 = emojiViewHelper.LIZ()) != null) {
                iDuxEmojiServiceLIZ2.LIZJ(emojiLoadCallbackImpl);
            }
            emojiViewHelper.LIZIZ = z;
            if (z && (iDuxEmojiServiceLIZ = emojiViewHelper.LIZ()) != null && iDuxEmojiServiceLIZ.LJ() && emojiViewHelper.LIZ.isAttachedToWindow()) {
                Editable editableText = emojiViewHelper.LIZ.getEditableText();
                int selectionStart = Selection.getSelectionStart(editableText);
                int selectionEnd = Selection.getSelectionEnd(editableText);
                IDuxEmojiService iDuxEmojiServiceLIZ3 = emojiViewHelper.LIZ();
                if (iDuxEmojiServiceLIZ3 != null) {
                    iDuxEmojiServiceLIZ3.LIZ(emojiViewHelper.LIZ, editableText);
                }
                Intrinsics.checkNotNull(editableText);
                C11221Eef.LIZ(editableText, selectionStart, selectionEnd);
            }
        }
    }

    public DmtEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f = new ArrayList<>();
        LIZ(attributeSet, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LIZ(AttributeSet attributeSet, int i) {
        Editable.Factory factoryLJFF;
        0qu6.LIZJ().LIZIZ(this, attributeSet);
        EmojiEditTextHelper emojiViewHelper = getEmojiViewHelper();
        if (attributeSet != null) {
            if (((Boolean) emojiViewHelper.LJ.getValue()).booleanValue()) {
                emojiViewHelper.LIZ.addTextChangedListener((C11201Eed) emojiViewHelper.LJFF.getValue());
                IDuxEmojiService iDuxEmojiServiceLIZ = emojiViewHelper.LIZ();
                if (iDuxEmojiServiceLIZ != null && (factoryLJFF = iDuxEmojiServiceLIZ.LJFF()) != null) {
                    emojiViewHelper.LIZ.setEditableFactory(factoryLJFF);
                    return;
                }
                return;
            }
            return;
        }
        emojiViewHelper.getClass();
    }

    public DmtEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f = new ArrayList<>();
        LIZ(attributeSet, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        super/*android.widget.EditText*/.onFocusChanged(z, i, rect);
        Iterator<View.OnFocusChangeListener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onFocusChange(this, z);
        }
    }
}
