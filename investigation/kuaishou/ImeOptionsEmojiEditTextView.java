package com.yxcorp.gifshow.message.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import com.kwai.framework.model.user.QCurrentUser;
import com.kwai.robust.PatchProxy;
import com.kwai.robust.PatchProxyResult;
import com.yxcorp.gifshow.widget.EmojiEditText;

/* JADX INFO: compiled from: kSourceFile */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\kuaishou\classes17.dex */
public class ImeOptionsEmojiEditTextView extends EmojiEditText {
    /* JADX WARN: Multi-variable type inference failed */
    public final void p() {
        if (!PatchProxy.applyVoid(this, ImeOptionsEmojiEditTextView.class, "4") && Build.VERSION.SDK_INT >= 28) {
            setFallbackLineSpacing(false);
        }
    }

    /* JADX INFO: compiled from: kSourceFile */
    public static class a extends InputConnectionWrapper {
        public a(InputConnection inputConnection) {
            super(inputConnection, true);
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean deleteSurroundingText(int i, int i2) {
            Object objApplyIntInt = PatchProxy.applyIntInt(a.class, "1", this, i, i2);
            if (objApplyIntInt != PatchProxyResult.class) {
                return ((Boolean) objApplyIntInt).booleanValue();
            }
            try {
                if (i >= 0 && i2 >= 0) {
                    CharSequence textBeforeCursor = getTextBeforeCursor(1000, 0);
                    CharSequence textAfterCursor = getTextAfterCursor(1000, 0);
                    if (textBeforeCursor != null) {
                        i = Math.min(i, textBeforeCursor.length());
                    }
                    if (textAfterCursor != null) {
                        i2 = Math.min(i2, textAfterCursor.length());
                    }
                    return super.deleteSurroundingText(i, i2);
                }
                nej.j.a("deleteSurroundingText: invalid parameters beforeLength=" + i + ", afterLength=" + i2);
                return false;
            } catch (Exception e) {
                nej.j.a("deleteSurroundingText exception: " + e.getMessage());
                return false;
            }
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
            Object objApplyIntInt = PatchProxy.applyIntInt(a.class, "2", this, i, i2);
            if (objApplyIntInt != PatchProxyResult.class) {
                return ((Boolean) objApplyIntInt).booleanValue();
            }
            try {
                if (i >= 0 && i2 >= 0) {
                    return super.deleteSurroundingTextInCodePoints(i, i2);
                }
                nej.j.a("deleteSurroundingTextInCodePoints: invalid parameters beforeLength=" + i + ", afterLength=" + i2);
                return false;
            } catch (Exception e) {
                nej.j.a("deleteSurroundingTextInCodePoints exception: " + e.getMessage());
                return false;
            }
        }
    }

    public ImeOptionsEmojiEditTextView(Context context) {
        super(context);
        if (PatchProxy.applyVoidOneRefs(context, this, ImeOptionsEmojiEditTextView.class, "3")) {
            return;
        }
        p();
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection;
        Object objApplyOneRefs = PatchProxy.applyOneRefs(editorInfo, this, ImeOptionsEmojiEditTextView.class, "5");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (InputConnection) objApplyOneRefs;
        }
        if (prd.g.m1() && QCurrentUser.ME.getOpenReturnKeySendMsgSwitchValue() == 1) {
            inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (inputConnectionOnCreateInputConnection == null) {
                return null;
            }
            editorInfo.imeOptions &= -1073741825;
        } else {
            inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        }
        if (inputConnectionOnCreateInputConnection == null) {
            return null;
        }
        return new a(inputConnectionOnCreateInputConnection);
    }

    public ImeOptionsEmojiEditTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (PatchProxy.applyVoidTwoRefs(context, attributeSet, this, ImeOptionsEmojiEditTextView.class, "2")) {
            return;
        }
        p();
    }

    public ImeOptionsEmojiEditTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (PatchProxy.applyVoidObjectObjectInt(ImeOptionsEmojiEditTextView.class, "1", this, context, attributeSet, i)) {
            return;
        }
        p();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        if (PatchProxy.isSupport(ImeOptionsEmojiEditTextView.class) && PatchProxy.applyVoidThreeRefs(Boolean.valueOf(z), Integer.valueOf(i), rect, this, ImeOptionsEmojiEditTextView.class, "6")) {
            return;
        }
        super/*android.widget.TextView*/.onFocusChanged(z, i, rect);
        nej.j.a("ImeOptionsEmojiEditTextView onFocusChanged , focused is " + z);
    }
}
