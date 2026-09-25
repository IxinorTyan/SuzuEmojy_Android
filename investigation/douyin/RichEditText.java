package com.ss.android.ugc.aweme.im.sdk.chat.view;

import X.08Nz;
import X.0N0M;
import X.0N87;
import X.0Nik;
import X.0hLP;
import X.C22790pOj;
import X.C22800pOk;
import X.C22810pOl;
import X.InterfaceC22960pPa;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import com.GlobalProxyLancet;
import com.bytedance.android.replay.ReplayContext;
import com.bytedance.commerce.base.string.StringUtilKt;
import com.bytedance.ies.dmt.ui.widget.DmtEditText;
import com.bytedance.ies.im.core.api.ext.ExtensionsKt;
import com.bytedance.memoryx.StringBuilderCache;
import com.bytedance.qss.common.catcher.CatcherTrace;
import com.ss.android.ugc.aweme.emoji.interfaces.ISmallEmojiAdapter;
import com.ss.android.ugc.aweme.emoji.service.EmojiServiceProxy;
import com.ss.android.ugc.aweme.im.mob.message.MessageLogger;
import com.ss.android.ugc.aweme.im.sdk.chat.model.Range;
import com.ss.android.ugc.aweme.im.sdk.chat.model.RichTextInfo;
import com.ss.android.ugc.aweme.im.sdk.chat.rips.inputtips.idletips.smartassistant.model.ws.ComponentInfo;
import com.ss.android.ugc.aweme.im.sdk.chat.rips.inputtips.idletips.smartassistant.model.ws.RecommendQuickReply;
import com.ss.android.ugc.aweme.im.sdk.chat.rips.inputtips.idletips.smartassistant.model.ws.SuggestMsg;
import com.ss.android.ugc.aweme.im.sdk.chat.view.RichEditText;
import com.ss.android.ugc.aweme.im.sdk.group.view.AtCollectionType;
import com.ss.android.ugc.aweme.im.sdk.redpacket.ext.ExtentionsKt;
import com.ss.android.ugc.aweme.im.security.IMDebugUtils;
import com.ss.android.ugc.aweme.im.service.utils.IMLog;
import com.ss.android.ugc.aweme.im.widget.span.CenterImageSpan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.AFLambdaS391S0000000_31;
import kotlin.jvm.internal.ALambdaS114S0201000_19;
import kotlin.jvm.internal.ALambdaS206S1100000_19;
import kotlin.jvm.internal.ALambdaS290S0300000_19;
import kotlin.jvm.internal.ALambdaS551S0200000_19;
import kotlin.jvm.internal.ALambdaS563S0200000_31;
import kotlin.jvm.internal.ALambdaS94S1000000_31;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: loaded from: D:\Documents\try\in\investigation\douyin\classes26.dex */
public abstract class RichEditText extends DmtEditText {
    public View.OnKeyListener h;
    public final ISmallEmojiAdapter i;
    public Function0<Unit> j;
    public String k;
    public boolean l;

    public final InterfaceC22960pPa getMOnMentionInputListener() {
        return null;
    }

    public final void setMOnMentionInputListener(InterfaceC22960pPa interfaceC22960pPa) {
    }

    public final Function0<Unit> getHeightChangeListener() {
        return this.j;
    }

    public String getsetTextfrom() {
        return this.k;
    }

    public final C22800pOk getComponentInfoSpan() {
        Object[] spans;
        List list;
        Editable text = getText();
        C22800pOk c22800pOk = null;
        if (text != null && (spans = text.getSpans(0, text.length(), C22800pOk.class)) != null && (list = ArraysKt.toList(spans)) != null && (c22800pOk = (C22800pOk) CollectionsKt.firstOrNull(list)) != null) {
            c22800pOk.LIZLLL(new Range(text.getSpanStart(c22800pOk), text.getSpanEnd(c22800pOk)));
        }
        return c22800pOk;
    }

    public final 0Nik getFinalMentionSpans() {
        Object[] spans;
        List list;
        Editable text = getText();
        if (text == null || (spans = text.getSpans(0, text.length(), 0Nik.class)) == null || (list = ArraysKt.toList(spans)) == null || list.isEmpty()) {
            return null;
        }
        0Nik r3 = (0Nik) list.get(list.size() - 1);
        r3.LJII(new Range(text.getSpanStart(r3), text.getSpanEnd(r3)));
        return r3;
    }

    public final List<0Nik> getMentionSpans() {
        Object[] spans;
        Editable text = getText();
        List<0Nik> list = null;
        if (text != null && (spans = text.getSpans(0, text.length(), 0Nik.class)) != null && (list = ArraysKt.toList(spans)) != null) {
            for (0Nik r3 : list) {
                r3.LJII(new Range(text.getSpanStart(r3), text.getSpanEnd(r3)));
            }
        }
        return list;
    }

    public final C22810pOl getQuickReplySpan() {
        Object[] spans;
        List list;
        Editable text = getText();
        C22810pOl c22810pOl = null;
        if (text != null && (spans = text.getSpans(0, text.length(), C22810pOl.class)) != null && (list = ArraysKt.toList(spans)) != null && (c22810pOl = (C22810pOl) CollectionsKt.firstOrNull(list)) != null) {
            c22810pOl.LIZJ(new Range(text.getSpanStart(c22810pOl), text.getSpanEnd(c22810pOl)));
        }
        return c22810pOl;
    }

    public final C22790pOj getSuggestMsgSpan() {
        Object[] spans;
        List list;
        Editable text = getText();
        C22790pOj c22790pOj = null;
        if (text != null && (spans = text.getSpans(0, text.length(), C22790pOj.class)) != null && (list = ArraysKt.toList(spans)) != null && (c22790pOj = (C22790pOj) CollectionsKt.firstOrNull(list)) != null) {
            c22790pOj.LIZLLL(new Range(text.getSpanStart(c22790pOj), text.getSpanEnd(c22790pOj)));
        }
        return c22790pOj;
    }

    public final List<String> getMentionLabels() {
        List<0Nik> mentionSpans = getMentionSpans();
        if (mentionSpans != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : mentionSpans) {
                0Nik r2 = (0Nik) obj;
                if (r2.LJ() == 2 && StringUtilKt.isNotNullOrEmpty(r2.LIZIZ())) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String strLIZIZ = ((0Nik) it.next()).LIZIZ();
                if (strLIZIZ != null) {
                    arrayList2.add(strLIZIZ);
                }
            }
            return arrayList2;
        }
        return null;
    }

    public final List<String> getSingleMentionIds() {
        List<0Nik> mentionSpans = getMentionSpans();
        if (mentionSpans != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : mentionSpans) {
                0Nik r1 = (0Nik) obj;
                if (r1.LJ() == 0 && StringUtilKt.isNotNullOrEmpty(r1.LIZIZ())) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String strLIZIZ = ((0Nik) it.next()).LIZIZ();
                if (strLIZIZ != null) {
                    arrayList2.add(strLIZIZ);
                }
            }
            return arrayList2;
        }
        return null;
    }

    public final Pair<List<String>, List<String>> getSingleMentionIdsAndLabels() {
        List<0Nik> mentionSpans = getMentionSpans();
        if (mentionSpans != null && !mentionSpans.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (0Nik r0 : mentionSpans) {
                int iLJ = r0.LJ();
                String strLIZIZ = r0.LIZIZ();
                if (strLIZIZ == null) {
                    strLIZIZ = "";
                }
                if (strLIZIZ.length() != 0) {
                    if (iLJ != 0) {
                        if (iLJ == 2) {
                            arrayList2.add(strLIZIZ);
                        }
                    } else {
                        arrayList.add(strLIZIZ);
                    }
                }
            }
            return new Pair<>(arrayList, arrayList2);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002e  */
    /* JADX WARN: Code duplicated, block: B:16:0x0038 A[LOOP:0: B:14:0x0032->B:16:0x0038, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:23:0x0066  */
    /* JADX WARN: Code duplicated, block: B:40:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:43:0x00ad A[LOOP:2: B:41:0x00a7->B:43:0x00ad, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:50:0x00db  */
    /* JADX WARN: Code duplicated, block: B:77:0x0082 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean LJIILIIL() {
        0Nik r5;
        Editable text;
        Editable text2;
        List<C22800pOk> list;
        Range rangeLIZIZ;
        List<0Nik> list2;
        Iterator it;
        Object next;
        Range rangeLIZJ;
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionEnd != selectionStart) {
            return false;
        }
        this.l = true;
        Editable text3 = getText();
        C22800pOk c22800pOk = null;
        c22800pOk = null;
        Object obj = null;
        c22800pOk = null;
        c22800pOk = null;
        if (text3 != null) {
            if (text3.length() < selectionEnd) {
                Object[] spans = text3.getSpans(0, text3.length(), 0Nik.class);
                if (spans != null) {
                    list2 = ArraysKt.toList(spans);
                    if (list2 != null) {
                        for (0Nik r4 : list2) {
                            r4.LJII(new Range(text3.getSpanStart(r4), text3.getSpanEnd(r4)));
                        }
                        it = list2.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                next = it.next();
                                rangeLIZJ = ((0Nik) next).LIZJ();
                                if (rangeLIZJ == null && rangeLIZJ.contains(selectionStart, selectionEnd) && selectionEnd != rangeLIZJ.getFrom()) {
                                    break;
                                }
                            } else {
                                next = null;
                                break;
                            }
                        }
                        r5 = (0Nik) next;
                    }
                }
            } else {
                Object[] spans2 = text3.getSpans(0, selectionEnd, 0Nik.class);
                if (spans2 != null) {
                    list2 = ArraysKt.toList(spans2);
                    if (list2 != null) {
                        while (r5.hasNext()) {
                            r4.LJII(new Range(text3.getSpanStart(r4), text3.getSpanEnd(r4)));
                        }
                        it = list2.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                next = it.next();
                                rangeLIZJ = ((0Nik) next).LIZJ();
                                if (rangeLIZJ == null) {
                                }
                            } else {
                                next = null;
                                break;
                            }
                        }
                        r5 = (0Nik) next;
                    }
                }
            }
            r5 = null;
        } else {
            r5 = null;
        }
        Editable text4 = getText();
        if (text4 != null) {
            if (text4.length() < selectionEnd) {
                Object[] spans3 = text4.getSpans(0, text4.length(), C22800pOk.class);
                if (spans3 != null) {
                    list = ArraysKt.toList(spans3);
                    if (list != null) {
                        for (C22800pOk c22800pOk2 : list) {
                            c22800pOk2.LIZLLL(new Range(text4.getSpanStart(c22800pOk2), text4.getSpanEnd(c22800pOk2)));
                        }
                        for (Object obj2 : list) {
                            rangeLIZIZ = ((C22800pOk) obj2).LIZIZ();
                            if (rangeLIZIZ == null && rangeLIZIZ.contains(selectionStart, selectionEnd) && selectionEnd != rangeLIZIZ.getFrom()) {
                                obj = obj2;
                                break;
                            }
                        }
                        c22800pOk = (C22800pOk) obj;
                    }
                }
            } else {
                Object[] spans4 = text4.getSpans(0, selectionEnd, C22800pOk.class);
                if (spans4 != null) {
                    list = ArraysKt.toList(spans4);
                    if (list != null) {
                        while (r9.hasNext()) {
                            c22800pOk2.LIZLLL(new Range(text4.getSpanStart(c22800pOk2), text4.getSpanEnd(c22800pOk2)));
                        }
                        while (r4.hasNext()) {
                            rangeLIZIZ = ((C22800pOk) obj2).LIZIZ();
                            if (rangeLIZIZ == null) {
                            }
                        }
                        c22800pOk = (C22800pOk) obj;
                    }
                }
            }
        }
        if (r5 != null) {
            Range rangeLIZJ2 = r5.LIZJ();
            if (rangeLIZJ2 != null && (text2 = getText()) != null) {
                text2.delete(rangeLIZJ2.getFrom(), rangeLIZJ2.getTo());
            }
            this.l = false;
            return true;
        }
        if (c22800pOk != null) {
            Range rangeLIZIZ2 = c22800pOk.LIZIZ();
            if (rangeLIZIZ2 != null && (text = getText()) != null) {
                text.delete(rangeLIZIZ2.getFrom(), rangeLIZIZ2.getTo());
            }
            this.l = false;
            return true;
        }
        this.l = false;
        return false;
    }

    public final void setDeleting(boolean z) {
        this.l = z;
    }

    public final void setHeightChangeListener(Function0<Unit> function0) {
        this.j = function0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setOnKeyListener(View.OnKeyListener onKeyListener) {
        this.h = onKeyListener;
        super/*android.widget.EditText*/.setOnKeyListener(onKeyListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LIZJ(String str) {
        if (getEditableText() == null) {
            return;
        }
        if (str != null && str.length() != 0) {
            LJIIL("addInputText", new ALambdaS206S1100000_19(this, str, 5));
            return;
        }
        StringBuilder sb = StringBuilderCache.get();
        sb.append("content=");
        sb.append((Object) getText());
        IMLog.LIZJ(StringBuilderCache.release(sb));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LJIIJ(String str) {
        Intrinsics.checkNotNullParameter(str, "recallMsg");
        if (getEditableText() == null) {
            return;
        }
        int selectionStart = getSelectionStart();
        if (selectionStart < 0) {
            StringBuilder sb = StringBuilderCache.get();
            sb.append("addRecallText index invalid: ");
            sb.append(selectionStart);
            IMLog.e("RichEditText", StringBuilderCache.release(sb));
            return;
        }
        LJIIL("addRichTextInfoText", new ALambdaS114S0201000_19(this, selectionStart, new SpannableString(str), 6));
    }

    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        try {
            final InputConnection inputConnectionOnCreateInputConnection = super/*androidx.appcompat.widget.AppCompatEditText*/.onCreateInputConnection(editorInfo);
            Intrinsics.checkNotNull(inputConnectionOnCreateInputConnection);
            return new InputConnectionWrapper(this, inputConnectionOnCreateInputConnection, this) { // from class: X.0pPu
                public final RichEditText LIZ;
                public final /* synthetic */ RichEditText LIZIZ;

                @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
                public final boolean sendKeyEvent(KeyEvent keyEvent) {
                    Intrinsics.checkNotNullParameter(keyEvent, "event");
                    if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 67 && this.LIZ.LJIILIIL()) {
                        View.OnKeyListener onKeyListener = this.LIZIZ.h;
                        if (onKeyListener != null) {
                            onKeyListener.onKey(this.LIZ, keyEvent.getKeyCode(), keyEvent);
                        }
                    } else if (!super.sendKeyEvent(keyEvent)) {
                        return false;
                    }
                    return true;
                }

                @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
                public final boolean deleteSurroundingText(int i, int i2) {
                    if ((i != 1 || i2 != 0 || !this.LIZ.LJIILIIL()) && !super.deleteSurroundingText(i, i2)) {
                        return false;
                    }
                    return true;
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(inputConnectionOnCreateInputConnection, true);
                    Intrinsics.checkNotNullParameter(inputConnectionOnCreateInputConnection, "target");
                    Intrinsics.checkNotNullParameter(this, "editText");
                    this.LIZIZ = this;
                    this.LIZ = this;
                }
            };
        } catch (Exception e) {
            CatcherTrace.reportThrowable(e, "c.s.a.u.aw.i.sd.cha.v.ric1", "ocinpcon12", "java/lang/Exception", "1");
            IMDebugUtils.throwException$default(e, false, false, false, 12, (Object) null);
            return super/*androidx.appcompat.widget.AppCompatEditText*/.onCreateInputConnection(editorInfo);
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
                CatcherTrace.reportThrowable(e, "c.s.a.u.aw.i.sd.cha.v.ric1", "com_ss_android_ugc_aweme_im_sdk_chat_view_RichEditText_com_bytedance_android_replay_business_touch_TouchEventLancet_onTouchEvent", "java/lang/Exception", "1");
            }
            return zBooleanValue;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LIZIZ(String str) {
        Intrinsics.checkNotNullParameter(str, "emojiText");
        if (getEditableText() == null) {
            return;
        }
        int selectionStart = getSelectionStart();
        if (selectionStart < 0) {
            StringBuilder sb = StringBuilderCache.get();
            sb.append("addEmojiText index invalid: ");
            sb.append(selectionStart);
            IMLog.e("RichEditText", StringBuilderCache.release(sb));
            return;
        }
        SpannableString spannableString = new SpannableString(str);
        Drawable realDrawable = this.i.getRealDrawable(getContext(), str);
        if (realDrawable != null) {
            int lineHeight = getLineHeight();
            realDrawable.setBounds(0, 0, (int) ((lineHeight * ((realDrawable.getIntrinsicWidth() + 0.0f) / realDrawable.getIntrinsicHeight())) + 0.5f), lineHeight);
            GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString, new 08Nz(realDrawable, (Integer) null), 0, spannableString.length(), 33);
        }
        LJIIL("addEmojiText", new ALambdaS114S0201000_19(this, selectionStart, spannableString, 5));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LJIIIIZZ(RecommendQuickReply recommendQuickReply) {
        Intrinsics.checkNotNullParameter(recommendQuickReply, "quickReply");
        if (getEditableText() == null) {
            return;
        }
        String str = recommendQuickReply.content;
        if (str != null && str.length() != 0) {
            SpannableString spannableString = new SpannableString(str);
            GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString, new C22810pOl(recommendQuickReply), 0, spannableString.length(), 33);
            LJIIL("addQuickReply", new ALambdaS551S0200000_19(this, spannableString, 92));
        } else {
            StringBuilder sb = StringBuilderCache.get();
            sb.append("content=");
            sb.append(str);
            IMLog.LIZJ(StringBuilderCache.release(sb));
        }
    }

    public final void LJIILL(String str) {
        List<0Nik> mentionSpans;
        if (str != null && str.length() != 0 && (mentionSpans = getMentionSpans()) != null && !mentionSpans.isEmpty()) {
            Iterator it = CollectionsKt.reversed(SequencesKt.toList(SequencesKt.filterNotNull(SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence(mentionSpans), new ALambdaS94S1000000_31(str, 20)), AFLambdaS391S0000000_31.get$arr$(239))))).iterator();
            while (it.hasNext()) {
                LJIIL("removeMentionText", new ALambdaS563S0200000_31(this, (Range) it.next(), 91));
            }
        }
    }

    public final void LJIILJJIL(AtCollectionType atCollectionType) {
        Intrinsics.checkNotNullParameter(atCollectionType, "atCollectionType");
        List<0Nik> mentionSpans = getMentionSpans();
        if (mentionSpans != null && !mentionSpans.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : mentionSpans) {
                0Nik r1 = (0Nik) obj;
                if (r1.LJ() == 2 && StringsKt.equals$default(r1.LIZIZ(), String.valueOf(atCollectionType.getType()), false, 2, (Object) null)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Range rangeLIZJ = ((0Nik) it.next()).LIZJ();
                if (rangeLIZJ != null) {
                    arrayList2.add(rangeLIZJ);
                }
            }
            Iterator it2 = CollectionsKt.reversed(arrayList2).iterator();
            while (it2.hasNext()) {
                LJIIL("removeMentionText", new ALambdaS563S0200000_31(this, (Range) it2.next(), 92));
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RichEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LJIIZILJ(CharSequence charSequence, String str) {
        Intrinsics.checkNotNullParameter(str, "from");
        this.k = str;
        super/*android.widget.EditText*/.setText(charSequence);
        this.k = null;
    }

    public final boolean LJ(AtCollectionType atCollectionType, Map<String, String> map) {
        Intrinsics.checkNotNullParameter(atCollectionType, "atCollectionType");
        return LIZLLL(2, atCollectionType.getTitle(), map, null, String.valueOf(atCollectionType.getType()));
    }

    public final void LJIIL(String str, Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(str, "from");
        Intrinsics.checkNotNullParameter(function0, "block");
        this.k = str;
        function0.invoke();
        this.k = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onSelectionChanged(int i, int i2) {
        Range rangeLIZIZ;
        Object next;
        super/*android.widget.EditText*/.onSelectionChanged(i, i2);
        List<0Nik> mentionSpans = getMentionSpans();
        if (mentionSpans != null) {
            Iterator<T> it = mentionSpans.iterator();
            while (true) {
                if (it.hasNext()) {
                    next = it.next();
                    Range rangeLIZJ = ((0Nik) next).LIZJ();
                    if (rangeLIZJ != null && rangeLIZJ.isWrappedBy(i, i2)) {
                        break;
                    }
                } else {
                    next = null;
                    break;
                }
            }
            0Nik r1 = (0Nik) next;
            if (r1 != null) {
                LJIILLIIL(r1.LIZJ(), i, i2);
                return;
            }
        }
        C22800pOk componentInfoSpan = getComponentInfoSpan();
        if (componentInfoSpan != null && (rangeLIZIZ = componentInfoSpan.LIZIZ()) != null && rangeLIZIZ.isWrappedBy(i, i2)) {
            LJIILLIIL(componentInfoSpan.LIZIZ(), i, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LJIIJJI(SuggestMsg suggestMsg, Long l) {
        boolean z;
        String str;
        Intrinsics.checkNotNullParameter(suggestMsg, "suggestMsg");
        if (getEditableText() == null) {
            return;
        }
        String str2 = suggestMsg.content;
        if (str2 != null && str2.length() != 0) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            SpannableString spannableString = new SpannableString(str2);
            GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString, new C22790pOj(suggestMsg, l), 0, spannableString.length(), 33);
            LJIIL("addSuggestMsg", new ALambdaS551S0200000_19(this, spannableString, 93));
        }
        ComponentInfo componentInfo = suggestMsg.component_info;
        if (componentInfo != null && (str = componentInfo.content) != null && str.length() != 0) {
            SpannableString spannableString2 = new SpannableString(ExtentionsKt.getString(2131839992));
            C22800pOk c22800pOk = new C22800pOk(componentInfo);
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString2, C22800pOk.LIZJ(context), 0, spannableString2.length(), 33);
            GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString2, c22800pOk, 0, spannableString2.length(), 33);
            LJIIL("addSuggestMsg2", new ALambdaS551S0200000_19(this, spannableString2, 94));
        }
    }

    public final void LJIIIZ(CharSequence charSequence, ArrayList<RichTextInfo> arrayList) {
        int i;
        String str;
        Intrinsics.checkNotNullParameter(charSequence, "inputText");
        Intrinsics.checkNotNullParameter(arrayList, "richTextInfos");
        try {
            if (ExtensionsKt.isNonNullOrEmpty(arrayList)) {
                int size = arrayList.size();
                List list = StringsKt.toList(charSequence);
                String strRelease = "";
                int location = 0;
                int i2 = 0;
                while (location < list.size()) {
                    if (i2 < size && arrayList.get(i2).getInfoType() == 2) {
                        i2++;
                    } else if (i2 < size && location == arrayList.get(i2).getLocation()) {
                        if (StringUtilKt.isNotNullOrEmpty(strRelease)) {
                            LJIIJ(strRelease);
                            strRelease = "";
                        }
                        RichTextInfo richTextInfo = arrayList.get(i2);
                        Intrinsics.checkNotNullExpressionValue(richTextInfo, "get(...)");
                        RichTextInfo richTextInfo2 = richTextInfo;
                        if (richTextInfo2.getInfoType() != 1 && richTextInfo2.getInfoType() == 4) {
                            i = 2;
                        } else {
                            i = 0;
                        }
                        HashMap<String, String> info = richTextInfo2.getInfo();
                        String str2 = null;
                        if (info != null) {
                            str2 = info.get("uid");
                        }
                        if (str2 != null) {
                            HashMap<String, String> info2 = richTextInfo2.getInfo();
                            Intrinsics.checkNotNull(info2);
                            String str3 = info2.get("uid");
                            Intrinsics.checkNotNull(str3, "null cannot be cast to non-null type kotlin.String");
                            str = str3;
                        } else {
                            HashMap<String, String> info3 = richTextInfo2.getInfo();
                            if (info3 == null || info3.get("mention_label") == null) {
                                str = "";
                            } else {
                                HashMap<String, String> info4 = richTextInfo2.getInfo();
                                Intrinsics.checkNotNull(info4);
                                String str4 = info4.get("mention_label");
                                Intrinsics.checkNotNull(str4, "null cannot be cast to non-null type kotlin.String");
                                str = str4;
                            }
                        }
                        LIZLLL(i, StringsKt.trim(StringsKt.removePrefix(charSequence.subSequence(richTextInfo2.getLocation(), richTextInfo2.getLocation() + richTextInfo2.getLength()).toString(), "@")).toString(), null, null, str);
                        location = richTextInfo2.getLocation() + richTextInfo2.getLength();
                        i2++;
                    } else {
                        StringBuilder sb = StringBuilderCache.get();
                        sb.append(strRelease);
                        sb.append(((Character) list.get(location)).charValue());
                        strRelease = StringBuilderCache.release(sb);
                        location++;
                    }
                }
                if (StringUtilKt.isNotNullOrEmpty(strRelease)) {
                    LJIIJ(strRelease);
                }
            }
        } catch (Exception e) {
            CatcherTrace.reportThrowable(e, "c.s.a.u.aw.i.sd.cha.v.ric1", "aricteite", "java/lang/Exception", "1");
            IMLog.i("fail");
            IMDebugUtils.throwException$default(e, false, false, false, 12, (Object) null);
            int i3 = 0N87.LIZ;
            MessageLogger.LIZJ(e);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public RichEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.i = EmojiServiceProxy.get().getSmartAdapter();
        Context context2 = getContext();
        if (context2 != null && context2.getResources().getConfiguration().getLayoutDirection() == 1) {
            setTextAlignment(5);
            setGravity(getGravity() | 8388611);
        }
        addTextChangedListener(new TextWatcher() { // from class: X.0pOm
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                Intrinsics.checkNotNullParameter(editable, "editable");
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
                if (i4 == 1 && !TextUtils.isEmpty(charSequence) && '@' == charSequence.toString().charAt(i2)) {
                    this.d.getMOnMentionInputListener();
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void LJIILLIIL(Range range, int i, int i2) {
        if (range == null) {
            setSelection(i);
            return;
        }
        try {
            if (i == i2) {
                setSelection(range.getAnchorPosition(i));
                return;
            }
            if (i2 < range.getTo()) {
                setSelection(i, range.getTo());
            }
            if (i > range.getFrom()) {
                setSelection(range.getFrom(), i2);
            }
        } catch (Exception e) {
            CatcherTrace.reportThrowable(e, "c.s.a.u.aw.i.sd.cha.v.ric1", "serictesel3", "java/lang/Exception", "1");
            IMDebugUtils.throwException$default(e, false, false, false, 12, (Object) null);
        }
    }

    public final boolean LJFF(String str, String str2, Map<String, String> map, Map<String, String> map2) {
        Intrinsics.checkNotNullParameter(str, "nickname");
        Intrinsics.checkNotNullParameter(str2, "uid");
        return LIZLLL(0, str, map, map2, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean LIZLLL(int i, String str, Map map, Map map2, String str2) {
        boolean z;
        0Nik r7;
        int i2;
        if (getEditableText() == null) {
            return false;
        }
        Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = getSelectionStart();
        if (i == 0 && 0N0M.LJ(str2)) {
            z = true;
        } else {
            z = false;
        }
        StringBuilder sb = StringBuilderCache.get();
        sb.append("@");
        sb.append(str);
        sb.append(' ');
        SpannableString spannableString = new SpannableString(StringBuilderCache.release(sb));
        if (map != null) {
            r7 = new 0Nik(spannableString.toString(), str2, i, map);
        } else {
            r7 = new 0Nik(spannableString.toString(), str2, i);
        }
        if (map2 != null) {
            r7.j = map2;
        }
        if (z) {
            r7.LJIIIIZZ(1);
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            CenterImageSpan centerImageSpanLIZ = 0N0M.LIZ(context, GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_ActivityLancet_getColor2(getContext(), 2131100147), 12, true, false);
            if (centerImageSpanLIZ != null) {
                int length = spannableString.length() - 1;
                GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString, centerImageSpanLIZ, length, length + 1, 33);
            }
        }
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString, 0Nik.LJFF(context2), 0, spannableString.length(), 33);
        GlobalProxyLancet.com_ss_android_ugc_aweme_lancet_SetSpanLancet_setSpan(spannableString, r7, 0, spannableString.length(), 33);
        LJIIL("addMentionText", new ALambdaS290S0300000_19(this, intRef, spannableString, 12));
        Editable editableText = getEditableText();
        Intrinsics.checkNotNullExpressionValue(editableText, "getEditableText(...)");
        if (editableText.length() > 0 && 1 <= (i2 = intRef.element) && i2 <= getEditableText().length()) {
            Editable editableText2 = getEditableText();
            int i3 = intRef.element;
            if (TextUtils.equals(editableText2.subSequence(i3 - 1, i3), "@")) {
                LJIIL("addMentionText_delete", new ALambdaS551S0200000_19(this, intRef, 91));
            }
        }
        return true;
    }
}
