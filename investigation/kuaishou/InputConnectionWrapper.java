package com.yxcorp.gifshow.widget;

import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import com.kwai.robust.PatchProxy;
import com.kwai.robust.PatchProxyResult;

/* JADX INFO: compiled from: kSourceFile */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\kuaishou\classes20.dex */
public class d0 extends InputConnectionWrapper {
    public a a;

    /* JADX INFO: compiled from: kSourceFile */
    public interface a {
        boolean qi(int i, int i2);
    }

    public d0(InputConnection inputConnection, boolean z) {
        super(null, z);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        Object objApplyIntInt = PatchProxy.applyIntInt(d0.class, "1", this, i, i2);
        if (objApplyIntInt != PatchProxyResult.class) {
            return ((Boolean) objApplyIntInt).booleanValue();
        }
        a aVar = this.a;
        if (aVar != null && aVar.qi(i, i2)) {
            return true;
        }
        return super.deleteSurroundingText(i, i2);
    }
}
