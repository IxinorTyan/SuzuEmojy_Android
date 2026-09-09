package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class SameSelectedSpinner extends android.widget.Spinner {
    public SameSelectedSpinner(android.content.Context context) {
        super(context);
    }

    public SameSelectedSpinner(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SameSelectedSpinner(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.AbsSpinner, android.widget.AdapterView
    public void setSelection(int i) {
        boolean z = i == getSelectedItemPosition();
        super.setSelection(i);
        if (!z || getOnItemSelectedListener() == null) {
            return;
        }
        getOnItemSelectedListener().onItemSelected(this, getSelectedView(), i, getSelectedItemId());
    }

    @Override // android.widget.AbsSpinner
    public void setSelection(int i, boolean z) {
        boolean z2 = i == getSelectedItemPosition();
        super.setSelection(i, z);
        if (!z2 || getOnItemSelectedListener() == null) {
            return;
        }
        getOnItemSelectedListener().onItemSelected(this, getSelectedView(), i, getSelectedItemId());
    }
}
