package com.yxcorp.gifshow.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import com.kwai.library.widget.edittext.SafeEditText;
import com.kwai.robust.PatchProxy;
import com.kwai.robust.PatchProxyResult;
import com.yxcorp.image.network.NetworkRequestInfo;
import com.yxcorp.utility.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import mzi.r2;

/* JADX INFO: compiled from: kSourceFile */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\kuaishou\classes20.dex */
public class EmojiEditText extends SafeEditText {
    public boolean i;
    public KSTextDisplayHandler j;
    public List<c> k;
    public List<e> l;
    public List<d> m;
    public final b n;
    public d0 o;

    /* JADX INFO: compiled from: kSourceFile */
    public interface c {
        boolean a(View view, int i, KeyEvent keyEvent);
    }

    /* JADX INFO: compiled from: kSourceFile */
    public interface d {
        void a(EmojiEditText emojiEditText);
    }

    /* JADX INFO: compiled from: kSourceFile */
    public interface e {
        void a(int i, int i2);
    }

    public KSTextDisplayHandler getKSTextDisplayHandler() {
        return this.j;
    }

    public boolean k() {
        return this.i;
    }

    /* JADX INFO: compiled from: kSourceFile */
    public static class b implements View.OnKeyListener {
        public final List<View.OnKeyListener> b;

        public b() {
            if (PatchProxy.applyVoid(this, b.class, "1")) {
                return;
            }
            this.b = new ArrayList();
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Object objApplyObjectIntObject = PatchProxy.applyObjectIntObject(b.class, "4", this, view, i, keyEvent);
            if (objApplyObjectIntObject != PatchProxyResult.class) {
                return ((Boolean) objApplyObjectIntObject).booleanValue();
            }
            Iterator<View.OnKeyListener> it = this.b.iterator();
            while (true) {
                boolean z = false;
                while (it.hasNext()) {
                    if (it.next().onKey(view, i, keyEvent) || z) {
                        z = true;
                    }
                }
                return z;
            }
        }
    }

    @z0.a
    public final List<c> getOnKeyPreImeListeners() {
        Object objApply = PatchProxy.apply(this, EmojiEditText.class, "23");
        if (objApply != PatchProxyResult.class) {
            return (List) objApply;
        }
        if (this.k == null) {
            this.k = new ArrayList();
        }
        return this.k;
    }

    @z0.a
    public final List<d> getOnPasteListeners() {
        Object objApply = PatchProxy.apply(this, EmojiEditText.class, "25");
        if (objApply != PatchProxyResult.class) {
            return (List) objApply;
        }
        if (this.m == null) {
            this.m = new ArrayList();
        }
        return this.m;
    }

    @z0.a
    public final List<e> getOnSelectionChangedListeners() {
        Object objApply = PatchProxy.apply(this, EmojiEditText.class, "24");
        if (objApply != PatchProxyResult.class) {
            return (List) objApply;
        }
        if (this.l == null) {
            this.l = new ArrayList();
        }
        return this.l;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean performLongClick() {
        Object objApply = PatchProxy.apply(this, EmojiEditText.class, "15");
        if (objApply != PatchProxyResult.class) {
            return ((Boolean) objApply).booleanValue();
        }
        try {
            return super/*android.widget.TextView*/.performLongClick();
        } catch (Throwable th) {
            if (tsf.b.a != 0) {
                th.printStackTrace();
                return false;
            }
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void g() {
        KSTextDisplayHandler kSTextDisplayHandler;
        if (PatchProxy.applyVoid(this, EmojiEditText.class, "4")) {
            return;
        }
        KSTextDisplayHandler kSTextDisplayHandler2 = new KSTextDisplayHandler(this);
        this.j = kSTextDisplayHandler2;
        kSTextDisplayHandler2.r = false;
        addTextChangedListener(new a());
        if (getText() != null && getText().length() > 0 && (kSTextDisplayHandler = this.j) != null) {
            kSTextDisplayHandler.b(getText());
        }
        setOnKeyListener(this.n);
    }

    /* JADX INFO: compiled from: kSourceFile */
    public class a implements TextWatcher {
        public int b = 0;
        public int c = 0;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public a() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            KSTextDisplayHandler kSTextDisplayHandler;
            if (!PatchProxy.applyVoidOneRefs(editable, this, a.class, "1") && (kSTextDisplayHandler = EmojiEditText.this.j) != null) {
                kSTextDisplayHandler.b(editable);
            }
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            this.b = i;
            this.c = i3;
        }
    }

    public void e(c cVar) {
        if (PatchProxy.applyVoidOneRefs(cVar, this, EmojiEditText.class, "6")) {
            return;
        }
        getOnKeyPreImeListeners().add(cVar);
    }

    public void f(e eVar) {
        if (PatchProxy.applyVoidOneRefs(eVar, this, EmojiEditText.class, "10")) {
            return;
        }
        getOnSelectionChangedListeners().add(eVar);
    }

    public void h(CharSequence charSequence) {
        if (PatchProxy.applyVoidOneRefs(charSequence, this, EmojiEditText.class, "18")) {
            return;
        }
        j(charSequence, Boolean.FALSE);
    }

    public void m(c cVar) {
        if (PatchProxy.applyVoidOneRefs(cVar, this, EmojiEditText.class, "7")) {
            return;
        }
        getOnKeyPreImeListeners().remove(cVar);
    }

    public void n(e eVar) {
        if (PatchProxy.applyVoidOneRefs(eVar, this, EmojiEditText.class, "11")) {
            return;
        }
        getOnSelectionChangedListeners().remove(eVar);
    }

    public void setKSTextDisplayHandler(KSTextDisplayHandler kSTextDisplayHandler) {
        this.j = kSTextDisplayHandler;
    }

    public void setOnDeleteKeyListener(d0.a aVar) {
        if (PatchProxy.applyVoidOneRefs(aVar, this, EmojiEditText.class, "27")) {
            return;
        }
        this.o.a = aVar;
    }

    public void setPasted(boolean z) {
        this.i = z;
    }

    public EmojiEditText(Context context) {
        super(context);
        if (PatchProxy.applyVoidOneRefs(context, this, EmojiEditText.class, "3")) {
            return;
        }
        this.n = new b();
        this.o = new d0(null, true);
        g();
    }

    public void d(View.OnKeyListener onKeyListener) {
        if (PatchProxy.applyVoidOneRefs(onKeyListener, this, EmojiEditText.class, "28")) {
            return;
        }
        b bVar = this.n;
        Objects.requireNonNull(bVar);
        if (!PatchProxy.applyVoidOneRefs(onKeyListener, bVar, b.class, "2")) {
            bVar.b.add(onKeyListener);
        }
    }

    public void l(View.OnKeyListener onKeyListener) {
        if (PatchProxy.applyVoidOneRefs(onKeyListener, this, EmojiEditText.class, "29")) {
            return;
        }
        b bVar = this.n;
        Objects.requireNonNull(bVar);
        if (!PatchProxy.applyVoidOneRefs(onKeyListener, bVar, b.class, "3")) {
            bVar.b.remove(onKeyListener);
        }
    }

    public void o(String str) {
        Editable text;
        if (!PatchProxy.applyVoidOneRefs(str, this, EmojiEditText.class, "5") && (text = getText()) != null && str != null) {
            text.replace(0, text.length(), str);
            this.j.b(text);
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        Object objApplyOneRefs = PatchProxy.applyOneRefs(editorInfo, this, EmojiEditText.class, "26");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (InputConnection) objApplyOneRefs;
        }
        d0 d0Var = this.o;
        if (d0Var != null && d0Var.a != null) {
            d0Var.setTarget(super/*androidx.appcompat.widget.AppCompatEditText*/.onCreateInputConnection(editorInfo));
            return this.o;
        }
        return super/*androidx.appcompat.widget.AppCompatEditText*/.onCreateInputConnection(editorInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setHintWithEmoji(CharSequence charSequence) {
        if (PatchProxy.applyVoidOneRefs(charSequence, this, EmojiEditText.class, "21")) {
            return;
        }
        if (TextUtils.isEmpty(charSequence)) {
            setHint(charSequence);
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        this.j.b(spannableStringBuilder);
        setHint(spannableStringBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean onTextContextMenuItem(int i) {
        Object objApplyInt = PatchProxy.applyInt(EmojiEditText.class, "20", this, i);
        if (objApplyInt != PatchProxyResult.class) {
            return ((Boolean) objApplyInt).booleanValue();
        }
        if (i == 16908322) {
            Iterator<d> it = getOnPasteListeners().iterator();
            while (it.hasNext()) {
                it.next().a(this);
            }
            this.i = true;
        }
        try {
            return super/*android.widget.TextView*/.onTextContextMenuItem(i);
        } catch (Exception unused) {
            return false;
        }
    }

    public EmojiEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (PatchProxy.applyVoidTwoRefs(context, attributeSet, this, EmojiEditText.class, "2")) {
            return;
        }
        this.n = new b();
        this.o = new d0(null, true);
        g();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onMeasure(int i, int i2) {
        if (PatchProxy.applyVoidIntInt(EmojiEditText.class, "14", this, i, i2)) {
            return;
        }
        try {
            super/*android.widget.TextView*/.onMeasure(i, i2);
        } catch (IndexOutOfBoundsException unused) {
            setText(getText().toString());
            try {
                super/*android.widget.TextView*/.onMeasure(i, i2);
            } catch (IndexOutOfBoundsException unused2) {
                setText(NetworkRequestInfo.S_UNSET);
                super/*android.widget.TextView*/.onMeasure(i, i2);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void j(CharSequence charSequence, Boolean bool) {
        if (PatchProxy.applyVoidTwoRefs(charSequence, bool, this, EmojiEditText.class, "19") || TextUtils.isEmpty(charSequence)) {
            return;
        }
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionEnd < 0) {
            selectionEnd = selectionStart;
        } else if (selectionEnd < selectionStart) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        try {
            if (!hasFocus() && selectionStart == 0 && selectionEnd == 0) {
                append(charSequence);
            } else if (bool.booleanValue() && getText() != null) {
                getText().insert(selectionStart, charSequence);
            } else {
                getText().replace(selectionStart, selectionEnd, charSequence);
            }
        } catch (Throwable th) {
            r2.P("insertText", Log.f(th));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        boolean z;
        Object objApplyIntObject = PatchProxy.applyIntObject(EmojiEditText.class, "9", this, i, keyEvent);
        if (objApplyIntObject != PatchProxyResult.class) {
            return ((Boolean) objApplyIntObject).booleanValue();
        }
        Iterator<c> it = getOnKeyPreImeListeners().iterator();
        loop0: while (true) {
            z = false;
            while (true) {
                if (!it.hasNext()) {
                    break loop0;
                }
                if (it.next().a(this, i, keyEvent) || z) {
                    z = true;
                }
            }
        }
        if (!z && !super/*android.widget.TextView*/.onKeyPreIme(i, keyEvent)) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onSelectionChanged(int i, int i2) {
        if (PatchProxy.applyVoidIntInt(EmojiEditText.class, "13", this, i, i2)) {
            return;
        }
        KSTextDisplayHandler kSTextDisplayHandler = this.j;
        if (kSTextDisplayHandler != null && kSTextDisplayHandler.i) {
            return;
        }
        super/*android.widget.TextView*/.onSelectionChanged(i, i2);
        List<e> onSelectionChangedListeners = getOnSelectionChangedListeners();
        if (!onSelectionChangedListeners.isEmpty()) {
            for (e eVar : onSelectionChangedListeners) {
                if (eVar != null) {
                    eVar.a(i, i2);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        if (PatchProxy.applyVoidTwoRefs(charSequence, bufferType, this, EmojiEditText.class, "16")) {
            return;
        }
        KSTextDisplayHandler kSTextDisplayHandler = this.j;
        if (kSTextDisplayHandler != null) {
            Objects.requireNonNull(kSTextDisplayHandler);
            if (!PatchProxy.applyVoid(kSTextDisplayHandler, KSTextDisplayHandler.class, "20")) {
                o1 o1Var = kSTextDisplayHandler.t;
                Objects.requireNonNull(o1Var);
                if (!PatchProxy.applyVoid(o1Var, o1.class, "8")) {
                    o1Var.a();
                }
            }
        }
        try {
            super/*android.widget.EditText*/.setText(charSequence, bufferType);
        } catch (Throwable th) {
            r2.P("settext", Log.f(th));
        }
    }

    public EmojiEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (PatchProxy.applyVoidObjectObjectInt(EmojiEditText.class, "1", this, context, attributeSet, i)) {
            return;
        }
        this.n = new b();
        this.o = new d0(null, true);
        g();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void append(CharSequence charSequence, int i, int i2) {
        if (PatchProxy.applyVoidObjectIntInt(EmojiEditText.class, "17", this, charSequence, i, i2)) {
            return;
        }
        try {
            super/*android.widget.TextView*/.append(charSequence, i, i2);
        } catch (Throwable th) {
            r2.P("appendText", Log.f(th));
        }
    }

    /* JADX INFO: compiled from: kSourceFile */
    public static class f implements InputFilter {
        @Override // android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            Object objApply;
            if (PatchProxy.isSupport(f.class) && (objApply = PatchProxy.apply(new Object[]{charSequence, Integer.valueOf(i), Integer.valueOf(i2), spanned, Integer.valueOf(i3), Integer.valueOf(i4)}, this, f.class, "1")) != PatchProxyResult.class) {
                return (CharSequence) objApply;
            }
            for (int i5 = i; i5 < i2; i5++) {
                if (65283 == charSequence.charAt(i5)) {
                    char[] cArr = new char[i2 - i];
                    TextUtils.getChars(charSequence, i, i2, cArr, 0);
                    String strReplace = new String(cArr).replace((char) 65283, '#');
                    if (charSequence instanceof Spanned) {
                        SpannableString spannableString = new SpannableString(strReplace);
                        TextUtils.copySpansFrom((Spanned) charSequence, i, i2, null, spannableString, 0);
                        return spannableString;
                    }
                    return strReplace;
                }
            }
            return null;
        }
    }
}
