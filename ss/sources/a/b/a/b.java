package a.b.a;

/* JADX INFO: loaded from: classes.dex */
public class b implements a.b.a.e.a {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    boolean f26c;
    public final a.b.a.a d;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    a.b.a.i f24a = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    float f25b = 0.0f;
    boolean e = false;

    public b(a.b.a.c cVar) {
        this.d = new a.b.a.a(this, cVar);
    }

    public a.b.a.b a(float f, float f2, float f3, a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, a.b.a.i iVar4) {
        this.f25b = 0.0f;
        if (f2 == 0.0f || f == f3) {
            this.d.a(iVar, 1.0f);
            this.d.a(iVar2, -1.0f);
            this.d.a(iVar4, 1.0f);
            this.d.a(iVar3, -1.0f);
        } else if (f == 0.0f) {
            this.d.a(iVar, 1.0f);
            this.d.a(iVar2, -1.0f);
        } else if (f3 == 0.0f) {
            this.d.a(iVar3, 1.0f);
            this.d.a(iVar4, -1.0f);
        } else {
            float f4 = (f / f2) / (f3 / f2);
            this.d.a(iVar, 1.0f);
            this.d.a(iVar2, -1.0f);
            this.d.a(iVar4, f4);
            this.d.a(iVar3, -f4);
        }
        return this;
    }

    public a.b.a.b a(a.b.a.e eVar, int i) {
        this.d.a(eVar.a(i, "ep"), 1.0f);
        this.d.a(eVar.a(i, "em"), -1.0f);
        return this;
    }

    a.b.a.b a(a.b.a.i iVar, int i) {
        this.d.a(iVar, i);
        return this;
    }

    public a.b.a.b a(a.b.a.i iVar, a.b.a.i iVar2, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.f25b = i;
        }
        if (z) {
            this.d.a(iVar, 1.0f);
            this.d.a(iVar2, -1.0f);
        } else {
            this.d.a(iVar, -1.0f);
            this.d.a(iVar2, 1.0f);
        }
        return this;
    }

    a.b.a.b a(a.b.a.i iVar, a.b.a.i iVar2, int i, float f, a.b.a.i iVar3, a.b.a.i iVar4, int i2) {
        float f2;
        if (iVar2 == iVar3) {
            this.d.a(iVar, 1.0f);
            this.d.a(iVar4, 1.0f);
            this.d.a(iVar2, -2.0f);
            return this;
        }
        if (f == 0.5f) {
            this.d.a(iVar, 1.0f);
            this.d.a(iVar2, -1.0f);
            this.d.a(iVar3, -1.0f);
            this.d.a(iVar4, 1.0f);
            if (i > 0 || i2 > 0) {
                f2 = (-i) + i2;
                this.f25b = f2;
            }
        } else {
            if (f <= 0.0f) {
                this.d.a(iVar, -1.0f);
                this.d.a(iVar2, 1.0f);
                f2 = i;
            } else if (f >= 1.0f) {
                this.d.a(iVar3, -1.0f);
                this.d.a(iVar4, 1.0f);
                f2 = i2;
            } else {
                float f3 = 1.0f - f;
                this.d.a(iVar, f3 * 1.0f);
                this.d.a(iVar2, f3 * (-1.0f));
                this.d.a(iVar3, (-1.0f) * f);
                this.d.a(iVar4, 1.0f * f);
                if (i > 0 || i2 > 0) {
                    f2 = ((-i) * f3) + (i2 * f);
                }
            }
            this.f25b = f2;
        }
        return this;
    }

    a.b.a.b a(a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, float f) {
        this.d.a(iVar, -1.0f);
        this.d.a(iVar2, 1.0f - f);
        this.d.a(iVar3, f);
        return this;
    }

    public a.b.a.b a(a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.f25b = i;
        }
        if (z) {
            this.d.a(iVar, 1.0f);
            this.d.a(iVar2, -1.0f);
            this.d.a(iVar3, -1.0f);
        } else {
            this.d.a(iVar, -1.0f);
            this.d.a(iVar2, 1.0f);
            this.d.a(iVar3, 1.0f);
        }
        return this;
    }

    public a.b.a.b a(a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, a.b.a.i iVar4, float f) {
        this.d.a(iVar, -1.0f);
        this.d.a(iVar2, 1.0f);
        this.d.a(iVar3, f);
        this.d.a(iVar4, -f);
        return this;
    }

    @Override // a.b.a.e.a
    public a.b.a.i a(a.b.a.e eVar, boolean[] zArr) {
        return this.d.a(zArr, (a.b.a.i) null);
    }

    void a() {
        float f = this.f25b;
        if (f < 0.0f) {
            this.f25b = f * (-1.0f);
            this.d.b();
        }
    }

    @Override // a.b.a.e.a
    public void a(a.b.a.e.a aVar) {
        if (!(aVar instanceof a.b.a.b)) {
            return;
        }
        a.b.a.b bVar = (a.b.a.b) aVar;
        this.f24a = null;
        this.d.a();
        int i = 0;
        while (true) {
            a.b.a.a aVar2 = bVar.d;
            if (i >= aVar2.f21a) {
                return;
            }
            this.d.a(aVar2.a(i), bVar.d.b(i), true);
            i++;
        }
    }

    @Override // a.b.a.e.a
    public void a(a.b.a.i iVar) {
        int i = iVar.d;
        float f = 1.0f;
        if (i != 1) {
            if (i == 2) {
                f = 1000.0f;
            } else if (i == 3) {
                f = 1000000.0f;
            } else if (i == 4) {
                f = 1.0E9f;
            } else if (i == 5) {
                f = 1.0E12f;
            }
        }
        this.d.a(iVar, f);
    }

    boolean a(a.b.a.e eVar) {
        boolean z;
        a.b.a.i iVarA = this.d.a(eVar);
        if (iVarA == null) {
            z = true;
        } else {
            d(iVarA);
            z = false;
        }
        if (this.d.f21a == 0) {
            this.e = true;
        }
        return z;
    }

    a.b.a.b b(a.b.a.i iVar, int i) {
        this.f24a = iVar;
        float f = i;
        iVar.e = f;
        this.f25b = f;
        this.e = true;
        return this;
    }

    public a.b.a.b b(a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.f25b = i;
        }
        if (z) {
            this.d.a(iVar, 1.0f);
            this.d.a(iVar2, -1.0f);
            this.d.a(iVar3, 1.0f);
        } else {
            this.d.a(iVar, -1.0f);
            this.d.a(iVar2, 1.0f);
            this.d.a(iVar3, -1.0f);
        }
        return this;
    }

    public a.b.a.b b(a.b.a.i iVar, a.b.a.i iVar2, a.b.a.i iVar3, a.b.a.i iVar4, float f) {
        this.d.a(iVar3, 0.5f);
        this.d.a(iVar4, 0.5f);
        this.d.a(iVar, -0.5f);
        this.d.a(iVar2, -0.5f);
        this.f25b = -f;
        return this;
    }

    boolean b() {
        a.b.a.i iVar = this.f24a;
        return iVar != null && (iVar.g == a.b.a.i.a.UNRESTRICTED || this.f25b >= 0.0f);
    }

    boolean b(a.b.a.i iVar) {
        return this.d.a(iVar);
    }

    public a.b.a.b c(a.b.a.i iVar, int i) {
        a.b.a.a aVar;
        float f;
        if (i < 0) {
            this.f25b = i * (-1);
            aVar = this.d;
            f = 1.0f;
        } else {
            this.f25b = i;
            aVar = this.d;
            f = -1.0f;
        }
        aVar.a(iVar, f);
        return this;
    }

    a.b.a.i c(a.b.a.i iVar) {
        return this.d.a((boolean[]) null, iVar);
    }

    public boolean c() {
        return this.f24a == null && this.f25b == 0.0f && this.d.f21a == 0;
    }

    @Override // a.b.a.e.a
    public void clear() {
        this.d.a();
        this.f24a = null;
        this.f25b = 0.0f;
    }

    public void d() {
        this.f24a = null;
        this.d.a();
        this.f25b = 0.0f;
        this.e = false;
    }

    void d(a.b.a.i iVar) {
        a.b.a.i iVar2 = this.f24a;
        if (iVar2 != null) {
            this.d.a(iVar2, -1.0f);
            this.f24a = null;
        }
        float fA = this.d.a(iVar, true) * (-1.0f);
        this.f24a = iVar;
        if (fA == 1.0f) {
            return;
        }
        this.f25b /= fA;
        this.d.a(fA);
    }

    /*  JADX ERROR: ConcurrentModificationException in pass: ConstructorVisitor
        java.util.ConcurrentModificationException
        	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
        	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
        	at jadx.core.dex.visitors.ConstructorVisitor.insertPhiInsn(ConstructorVisitor.java:139)
        	at jadx.core.dex.visitors.ConstructorVisitor.processInvoke(ConstructorVisitor.java:91)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:56)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    java.lang.String e() {
        /*
            Method dump skipped, instruction units count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: a.b.a.b.e():java.lang.String");
    }

    @Override // a.b.a.e.a
    public a.b.a.i getKey() {
        return this.f24a;
    }

    public java.lang.String toString() {
        return e();
    }
}
