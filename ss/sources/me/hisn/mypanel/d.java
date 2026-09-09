package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public abstract class d {
    public android.widget.PopupWindow a(android.view.View view, int i, int i2, boolean z) {
        android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(view, i, i2, z);
        popupWindow.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        a(view, popupWindow);
        return popupWindow;
    }

    protected abstract void a(android.view.View view, android.widget.PopupWindow popupWindow);
}
