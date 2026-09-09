package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class j extends a.b.a.j.f {
    protected a.b.a.j.f[] k0 = new a.b.a.j.f[4];
    protected int l0 = 0;

    public void J() {
        this.l0 = 0;
    }

    public void b(a.b.a.j.f fVar) {
        int i = this.l0 + 1;
        a.b.a.j.f[] fVarArr = this.k0;
        if (i > fVarArr.length) {
            this.k0 = (a.b.a.j.f[]) java.util.Arrays.copyOf(fVarArr, fVarArr.length * 2);
        }
        a.b.a.j.f[] fVarArr2 = this.k0;
        int i2 = this.l0;
        fVarArr2[i2] = fVar;
        this.l0 = i2 + 1;
    }
}
