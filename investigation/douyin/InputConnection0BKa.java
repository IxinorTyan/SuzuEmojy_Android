package X;

import android.R;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import androidx.compose.ui.text.input.TextFieldValue;
import com.bytedance.memoryx.StringBuilderCache;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.ranges.RangesKt;

/* JADX INFO: renamed from: X.0BKa, reason: invalid class name and case insensitive filesystem */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\douyin\classes54.dex */
public final class InputConnectionC12790BKa implements InputConnection {
    public final InterfaceC12850BKg LIZ;
    public final boolean LIZIZ;
    public int LIZJ;
    public TextFieldValue LIZLLL;
    public int LJ;
    public boolean LJFF;
    public final List<InterfaceC12900BKl> LJI = new ArrayList();
    public boolean LJII = true;

    @Override // android.view.inputmethod.InputConnection
    public final Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean reportFullscreenMode(boolean z) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean endBatchEdit() {
        return LIZIZ();
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean beginBatchEdit() {
        boolean z = this.LJII;
        if (z) {
            this.LIZJ++;
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean finishComposingText() {
        boolean z = this.LJII;
        if (z) {
            LIZ(new C0BKQ());
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final void closeConnection() {
        ((ArrayList) this.LJI).clear();
        this.LIZJ = 0;
        this.LJII = false;
        this.LIZ.LIZIZ(this);
    }

    public final boolean LIZIZ() {
        int i = this.LIZJ - 1;
        this.LIZJ = i;
        if (i == 0 && (!this.LJI.isEmpty())) {
            this.LIZ.LIZJ(CollectionsKt.toMutableList(this.LJI));
            ((ArrayList) this.LJI).clear();
        }
        if (this.LIZJ > 0) {
            return true;
        }
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean clearMetaKeyStates(int i) {
        boolean z = this.LJII;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitCompletion(CompletionInfo completionInfo) {
        boolean z = this.LJII;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitCorrection(CorrectionInfo correctionInfo) {
        boolean z = this.LJII;
        if (z) {
            return this.LIZIZ;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean sendKeyEvent(KeyEvent keyEvent) {
        boolean z = this.LJII;
        if (z) {
            this.LIZ.onKeyEvent(keyEvent);
            return true;
        }
        return z;
    }

    public final void LIZ(InterfaceC12900BKl interfaceC12900BKl) {
        this.LIZJ++;
        try {
            ((ArrayList) this.LJI).add(interfaceC12900BKl);
        } finally {
            LIZIZ();
        }
    }

    public final void LIZJ(int i) {
        sendKeyEvent(new KeyEvent(0, i));
        sendKeyEvent(new KeyEvent(1, i));
    }

    @Override // android.view.inputmethod.InputConnection
    public final int getCursorCapsMode(int i) {
        TextFieldValue textFieldValue = this.LIZLLL;
        return TextUtils.getCapsMode(textFieldValue.LIZ.d, C0BIV.LJFF(textFieldValue.LIZIZ), i);
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getSelectedText(int i) {
        if (C0BIV.LIZIZ(this.LIZLLL.LIZIZ)) {
            return null;
        }
        return C0BIY.LIZ(this.LIZLLL).d;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean performContextMenuAction(int i) {
        boolean z = this.LJII;
        if (z) {
            z = false;
            switch (i) {
                case R.id.selectAll:
                    LIZ(new C0BKX(0, this.LIZLLL.LIZ.d.length()));
                    return false;
                case R.id.cut:
                    LIZJ(277);
                    return false;
                case R.id.copy:
                    LIZJ(278);
                    return false;
                case R.id.paste:
                    LIZJ(279);
                    return false;
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000a  */
    @Override // android.view.inputmethod.InputConnection
    public final boolean performEditorAction(int i) {
        int i2;
        boolean z = this.LJII;
        if (z) {
            z = true;
            if (i == 0) {
                i2 = 1;
            } else {
                switch (i) {
                    case 2:
                        i2 = 2;
                        break;
                    case 3:
                        i2 = 3;
                        break;
                    case 4:
                        i2 = 4;
                        break;
                    case 5:
                        i2 = 6;
                        break;
                    case 6:
                        i2 = 7;
                        break;
                    case 7:
                        i2 = 5;
                        break;
                    default:
                        i2 = 1;
                        break;
                }
            }
            this.LIZ.LIZLLL(i2);
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean requestCursorUpdates(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6 = this.LJII;
        if (z6) {
            boolean z7 = false;
            if ((i & 1) != 0) {
                z = true;
            } else {
                z = false;
            }
            if ((i & 2) != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 33) {
                if ((i & 16) != 0) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if ((i & 8) != 0) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                if ((i & 4) != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (i2 >= 34 && (i & 32) != 0) {
                    z7 = true;
                }
                if (!z4 && !z5 && !z3 && !z7) {
                    if (i2 >= 34) {
                        z4 = true;
                        z5 = true;
                        z3 = true;
                        z7 = true;
                    } else {
                        z7 = false;
                        z4 = true;
                        z5 = true;
                        z3 = true;
                    }
                }
            } else {
                z3 = false;
                z7 = false;
                z4 = true;
                z5 = true;
            }
            this.LIZ.LIZ(z, z2, z4, z5, z3, z7);
            return true;
        }
        return z6;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean performPrivateCommand(String str, Bundle bundle) {
        boolean z = this.LJII;
        if (z) {
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingText(int i, int i2) {
        boolean z = this.LJII;
        if (z) {
            LIZ(new C0BKY(i, i2));
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingTextInCodePoints(final int i, final int i2) {
        boolean z = this.LJII;
        if (z) {
            LIZ(new InterfaceC12900BKl(i, i2) { // from class: X.0BKZ
                public final int LIZ;
                public final int LIZIZ;

                public final int hashCode() {
                    return (this.LIZ * 31) + this.LIZIZ;
                }

                public final String toString() {
                    StringBuilder sb = StringBuilderCache.get();
                    sb.append("DeleteSurroundingTextInCodePointsCommand(lengthBeforeCursor=");
                    sb.append(this.LIZ);
                    sb.append(", lengthAfterCursor=");
                    sb.append(this.LIZIZ);
                    sb.append(')');
                    return StringBuilderCache.release(sb);
                }

                public final boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (!(obj instanceof C0BKZ)) {
                        return false;
                    }
                    C0BKZ c0bkz = (C0BKZ) obj;
                    if (this.LIZ == c0bkz.LIZ && this.LIZIZ == c0bkz.LIZIZ) {
                        return true;
                    }
                    return false;
                }

                @Override // X.InterfaceC12900BKl
                public final void LIZ(C0BKU c0bku) {
                    int i3 = this.LIZ;
                    int i4 = 0;
                    for (int i5 = 0; i5 < i3; i5++) {
                        i4++;
                        int i6 = c0bku.LIZIZ;
                        if (i6 > i4) {
                            char cLIZIZ = c0bku.LIZIZ((i6 - i4) - 1);
                            char cLIZIZ2 = c0bku.LIZIZ(c0bku.LIZIZ - i4);
                            if (Character.isHighSurrogate(cLIZIZ) && Character.isLowSurrogate(cLIZIZ2)) {
                                i4++;
                            }
                        }
                        if (i4 == c0bku.LIZIZ) {
                            break;
                        }
                    }
                    int i7 = this.LIZIZ;
                    int i8 = 0;
                    for (int i9 = 0; i9 < i7; i9++) {
                        i8++;
                        if (c0bku.LIZJ + i8 < c0bku.LIZLLL()) {
                            char cLIZIZ3 = c0bku.LIZIZ((c0bku.LIZJ + i8) - 1);
                            char cLIZIZ4 = c0bku.LIZIZ(c0bku.LIZJ + i8);
                            if (Character.isHighSurrogate(cLIZIZ3) && Character.isLowSurrogate(cLIZIZ4)) {
                                i8++;
                            }
                        }
                        if (c0bku.LIZJ + i8 == c0bku.LIZLLL()) {
                            break;
                        }
                    }
                    int i10 = c0bku.LIZJ;
                    c0bku.LIZ(i10, i8 + i10);
                    int i11 = c0bku.LIZIZ;
                    c0bku.LIZ(i11 - i4, i11);
                }

                {
                    this.LIZ = i;
                    this.LIZIZ = i2;
                    if (i >= 0 && i2 >= 0) {
                        return;
                    }
                    StringBuilder sb = StringBuilderCache.get();
                    sb.append("Expected lengthBeforeCursor and lengthAfterCursor to be non-negative, were ");
                    sb.append(i);
                    sb.append(" and ");
                    sb.append(i2);
                    sb.append(" respectively.");
                    String strRelease = StringBuilderCache.release(sb);
                    strRelease.toString();
                    throw new IllegalArgumentException(strRelease);
                }
            });
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getTextAfterCursor(int i, int i2) {
        return C0BIY.LIZIZ(this.LIZLLL, i).d;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getTextBeforeCursor(int i, int i2) {
        return C0BIY.LIZJ(this.LIZLLL, i).d;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setComposingRegion(final int i, final int i2) {
        boolean z = this.LJII;
        if (z) {
            LIZ(new InterfaceC12900BKl(i, i2) { // from class: X.0BKW
                public final int LIZ;
                public final int LIZIZ;

                public final int hashCode() {
                    return (this.LIZ * 31) + this.LIZIZ;
                }

                public final String toString() {
                    StringBuilder sb = StringBuilderCache.get();
                    sb.append("SetComposingRegionCommand(start=");
                    sb.append(this.LIZ);
                    sb.append(", end=");
                    sb.append(this.LIZIZ);
                    sb.append(')');
                    return StringBuilderCache.release(sb);
                }

                public final boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (!(obj instanceof C0BKW)) {
                        return false;
                    }
                    C0BKW c0bkw = (C0BKW) obj;
                    if (this.LIZ == c0bkw.LIZ && this.LIZIZ == c0bkw.LIZIZ) {
                        return true;
                    }
                    return false;
                }

                @Override // X.InterfaceC12900BKl
                public final void LIZ(C0BKU c0bku) {
                    if (c0bku.LIZLLL != -1) {
                        c0bku.LIZLLL = -1;
                        c0bku.LJ = -1;
                    }
                    int iCoerceIn = RangesKt.coerceIn(this.LIZ, 0, c0bku.LIZLLL());
                    int iCoerceIn2 = RangesKt.coerceIn(this.LIZIZ, 0, c0bku.LIZLLL());
                    if (iCoerceIn != iCoerceIn2) {
                        if (iCoerceIn < iCoerceIn2) {
                            c0bku.LJFF(iCoerceIn, iCoerceIn2);
                        } else {
                            c0bku.LJFF(iCoerceIn2, iCoerceIn);
                        }
                    }
                }

                {
                    this.LIZ = i;
                    this.LIZIZ = i2;
                }
            });
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setSelection(int i, int i2) {
        boolean z = this.LJII;
        if (z) {
            LIZ(new C0BKX(i, i2));
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitText(CharSequence charSequence, int i) {
        boolean z = this.LJII;
        if (z) {
            LIZ(new C0BKS(String.valueOf(charSequence), i));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        boolean z = true;
        int i2 = 0;
        if ((i & 1) == 0) {
            z = false;
        }
        this.LJFF = z;
        if (z) {
            if (extractedTextRequest != null) {
                i2 = extractedTextRequest.token;
            }
            this.LJ = i2;
        }
        return C12840BKf.LIZ(this.LIZLLL);
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setComposingText(CharSequence charSequence, int i) {
        boolean z = this.LJII;
        if (z) {
            LIZ(new C0BKT(String.valueOf(charSequence), i));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        boolean z = this.LJII;
        if (z) {
            return false;
        }
        return z;
    }

    public InputConnectionC12790BKa(TextFieldValue textFieldValue, C0BL2 c0bl2, boolean z) {
        this.LIZ = c0bl2;
        this.LIZIZ = z;
        this.LIZLLL = textFieldValue;
    }
}
