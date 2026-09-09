package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class m {
    static int a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.k kVar, android.view.View view, android.view.View view2, androidx.recyclerview.widget.RecyclerView.o oVar, boolean z) {
        if (oVar.e() == 0 || a0Var.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return java.lang.Math.abs(oVar.l(view) - oVar.l(view2)) + 1;
        }
        return java.lang.Math.min(kVar.g(), kVar.a(view2) - kVar.d(view));
    }

    static int a(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.k kVar, android.view.View view, android.view.View view2, androidx.recyclerview.widget.RecyclerView.o oVar, boolean z, boolean z2) {
        if (oVar.e() == 0 || a0Var.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        int iMax = z2 ? java.lang.Math.max(0, (a0Var.a() - java.lang.Math.max(oVar.l(view), oVar.l(view2))) - 1) : java.lang.Math.max(0, java.lang.Math.min(oVar.l(view), oVar.l(view2)));
        if (z) {
            return java.lang.Math.round((iMax * (java.lang.Math.abs(kVar.a(view2) - kVar.d(view)) / (java.lang.Math.abs(oVar.l(view) - oVar.l(view2)) + 1))) + (kVar.f() - kVar.d(view)));
        }
        return iMax;
    }

    static int b(androidx.recyclerview.widget.RecyclerView.a0 a0Var, androidx.recyclerview.widget.k kVar, android.view.View view, android.view.View view2, androidx.recyclerview.widget.RecyclerView.o oVar, boolean z) {
        if (oVar.e() == 0 || a0Var.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return a0Var.a();
        }
        return (int) (((kVar.a(view2) - kVar.d(view)) / (java.lang.Math.abs(oVar.l(view) - oVar.l(view2)) + 1)) * a0Var.a());
    }
}
