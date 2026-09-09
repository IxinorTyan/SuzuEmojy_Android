package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class InputMethodPicker extends android.app.Activity {

    class a implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.inputmethod.InputMethodManager f714a;

        /* JADX INFO: renamed from: me.hisn.utils.InputMethodPicker$a$a, reason: collision with other inner class name */
        class RunnableC0036a implements java.lang.Runnable {
            RunnableC0036a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.utils.InputMethodPicker.a.this.f714a.showInputMethodPicker();
                me.hisn.utils.InputMethodPicker.this.finish();
                me.hisn.utils.InputMethodPicker.this.overridePendingTransition(0, 0);
            }
        }

        a(android.view.inputmethod.InputMethodManager inputMethodManager) {
            this.f714a = inputMethodManager;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                java.lang.Thread.sleep(100L);
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
            me.hisn.utils.InputMethodPicker.this.runOnUiThread(new me.hisn.utils.InputMethodPicker.a.RunnableC0036a());
        }
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        overridePendingTransition(0, 0);
        super.onCreate(bundle);
        new java.lang.Thread(new me.hisn.utils.InputMethodPicker.a((android.view.inputmethod.InputMethodManager) getApplicationContext().getSystemService("input_method"))).start();
    }
}
