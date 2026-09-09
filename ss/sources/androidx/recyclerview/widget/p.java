package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class p {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final a.a.e<androidx.recyclerview.widget.RecyclerView.d0, androidx.recyclerview.widget.p.a> f338a = new a.a.e<>();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final a.a.c<androidx.recyclerview.widget.RecyclerView.d0> f339b = new a.a.c<>();

    static class a {
        static a.c.d.a<androidx.recyclerview.widget.p.a> d = new a.c.d.b(20);

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f340a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        androidx.recyclerview.widget.RecyclerView.l.c f341b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        androidx.recyclerview.widget.RecyclerView.l.c f342c;

        private a() {
        }

        static void a() {
            while (d.a() != null) {
            }
        }

        static void a(androidx.recyclerview.widget.p.a aVar) {
            aVar.f340a = 0;
            aVar.f341b = null;
            aVar.f342c = null;
            d.a(aVar);
        }

        static androidx.recyclerview.widget.p.a b() {
            androidx.recyclerview.widget.p.a aVarA = d.a();
            return aVarA == null ? new androidx.recyclerview.widget.p.a() : aVarA;
        }
    }

    interface b {
        void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var);

        void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2);

        void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2);

        void c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar, androidx.recyclerview.widget.RecyclerView.l.c cVar2);
    }

    p() {
    }

    private androidx.recyclerview.widget.RecyclerView.l.c a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, int i) {
        androidx.recyclerview.widget.p.a aVarD;
        androidx.recyclerview.widget.RecyclerView.l.c cVar;
        int iA = this.f338a.a(d0Var);
        if (iA >= 0 && (aVarD = this.f338a.d(iA)) != null) {
            int i2 = aVarD.f340a;
            if ((i2 & i) != 0) {
                aVarD.f340a = (~i) & i2;
                if (i == 4) {
                    cVar = aVarD.f341b;
                } else {
                    if (i != 8) {
                        throw new java.lang.IllegalArgumentException("Must provide flag PRE or POST");
                    }
                    cVar = aVarD.f342c;
                }
                if ((aVarD.f340a & 12) == 0) {
                    this.f338a.c(iA);
                    androidx.recyclerview.widget.p.a.a(aVarD);
                }
                return cVar;
            }
        }
        return null;
    }

    androidx.recyclerview.widget.RecyclerView.d0 a(long j) {
        return this.f339b.a(j);
    }

    void a() {
        this.f338a.clear();
        this.f339b.a();
    }

    void a(long j, androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        this.f339b.b(j, d0Var);
    }

    void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        androidx.recyclerview.widget.p.a aVarB = this.f338a.get(d0Var);
        if (aVarB == null) {
            aVarB = androidx.recyclerview.widget.p.a.b();
            this.f338a.put(d0Var, aVarB);
        }
        aVarB.f340a |= 1;
    }

    void a(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar) {
        androidx.recyclerview.widget.p.a aVarB = this.f338a.get(d0Var);
        if (aVarB == null) {
            aVarB = androidx.recyclerview.widget.p.a.b();
            this.f338a.put(d0Var, aVarB);
        }
        aVarB.f340a |= 2;
        aVarB.f341b = cVar;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x003a  */
    /* JADX WARN: Code duplicated, block: B:6:0x0021  */
    void a(androidx.recyclerview.widget.p.b bVar) {
        androidx.recyclerview.widget.RecyclerView.l.c cVar;
        androidx.recyclerview.widget.RecyclerView.l.c cVar2;
        for (int size = this.f338a.size() - 1; size >= 0; size--) {
            androidx.recyclerview.widget.RecyclerView.d0 d0VarB = this.f338a.b(size);
            androidx.recyclerview.widget.p.a aVarC = this.f338a.c(size);
            int i = aVarC.f340a;
            if ((i & 3) != 3) {
                if ((i & 1) != 0) {
                    cVar = aVarC.f341b;
                    if (cVar == null) {
                        bVar.a(d0VarB);
                    } else {
                        cVar2 = aVarC.f342c;
                    }
                } else if ((i & 14) == 14) {
                    bVar.c(d0VarB, aVarC.f341b, aVarC.f342c);
                } else if ((i & 12) == 12) {
                    bVar.b(d0VarB, aVarC.f341b, aVarC.f342c);
                } else if ((i & 4) != 0) {
                    cVar = aVarC.f341b;
                    cVar2 = null;
                } else if ((i & 8) != 0) {
                    bVar.c(d0VarB, aVarC.f341b, aVarC.f342c);
                }
                bVar.a(d0VarB, cVar, cVar2);
            } else {
                bVar.a(d0VarB);
            }
            androidx.recyclerview.widget.p.a.a(aVarC);
        }
    }

    void b() {
        androidx.recyclerview.widget.p.a.a();
    }

    void b(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar) {
        androidx.recyclerview.widget.p.a aVarB = this.f338a.get(d0Var);
        if (aVarB == null) {
            aVarB = androidx.recyclerview.widget.p.a.b();
            this.f338a.put(d0Var, aVarB);
        }
        aVarB.f342c = cVar;
        aVarB.f340a |= 8;
    }

    boolean b(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        androidx.recyclerview.widget.p.a aVar = this.f338a.get(d0Var);
        return (aVar == null || (aVar.f340a & 1) == 0) ? false : true;
    }

    void c(androidx.recyclerview.widget.RecyclerView.d0 d0Var, androidx.recyclerview.widget.RecyclerView.l.c cVar) {
        androidx.recyclerview.widget.p.a aVarB = this.f338a.get(d0Var);
        if (aVarB == null) {
            aVarB = androidx.recyclerview.widget.p.a.b();
            this.f338a.put(d0Var, aVarB);
        }
        aVarB.f341b = cVar;
        aVarB.f340a |= 4;
    }

    boolean c(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        androidx.recyclerview.widget.p.a aVar = this.f338a.get(d0Var);
        return (aVar == null || (aVar.f340a & 4) == 0) ? false : true;
    }

    public void d(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        g(d0Var);
    }

    androidx.recyclerview.widget.RecyclerView.l.c e(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        return a(d0Var, 8);
    }

    androidx.recyclerview.widget.RecyclerView.l.c f(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        return a(d0Var, 4);
    }

    void g(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        androidx.recyclerview.widget.p.a aVar = this.f338a.get(d0Var);
        if (aVar == null) {
            return;
        }
        aVar.f340a &= -2;
    }

    void h(androidx.recyclerview.widget.RecyclerView.d0 d0Var) {
        for (int iB = this.f339b.b() - 1; iB >= 0; iB--) {
            if (d0Var == this.f339b.c(iB)) {
                this.f339b.b(iB);
                break;
            }
        }
        androidx.recyclerview.widget.p.a aVarRemove = this.f338a.remove(d0Var);
        if (aVarRemove != null) {
            androidx.recyclerview.widget.p.a.a(aVarRemove);
        }
    }
}
