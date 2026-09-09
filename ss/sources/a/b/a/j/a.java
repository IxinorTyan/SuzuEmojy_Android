package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static int a(a.b.a.j.f fVar) {
        if (fVar.j() == a.b.a.j.f.b.MATCH_CONSTRAINT) {
            int i = (int) (fVar.H == 0 ? fVar.i() * fVar.G : fVar.i() / fVar.G);
            fVar.o(i);
            return i;
        }
        if (fVar.q() != a.b.a.j.f.b.MATCH_CONSTRAINT) {
            return -1;
        }
        int iS = (int) (fVar.H == 1 ? fVar.s() * fVar.G : fVar.s() / fVar.G);
        fVar.g(iS);
        return iS;
    }

    private static int a(a.b.a.j.f fVar, int i) {
        a.b.a.j.e eVar;
        int i2 = i * 2;
        a.b.a.j.e[] eVarArr = fVar.A;
        a.b.a.j.e eVar2 = eVarArr[i2];
        a.b.a.j.e eVar3 = eVarArr[i2 + 1];
        a.b.a.j.e eVar4 = eVar2.d;
        if (eVar4 == null) {
            return 0;
        }
        a.b.a.j.f fVar2 = eVar4.f48b;
        a.b.a.j.f fVar3 = fVar.D;
        if (fVar2 != fVar3 || (eVar = eVar3.d) == null || eVar.f48b != fVar3) {
            return 0;
        }
        return (int) ((((fVar3.d(i) - eVar2.b()) - eVar3.b()) - fVar.d(i)) * (i == 0 ? fVar.V : fVar.W));
    }

    private static int a(a.b.a.j.f fVar, int i, boolean z, int i2) {
        int i3;
        int iC;
        int i4;
        int i5;
        int i6;
        int iS;
        int i7;
        int i8;
        int iMax = 0;
        if (!fVar.b0) {
            return 0;
        }
        boolean z2 = fVar.w.d != null && i == 1;
        if (z) {
            i3 = fVar.c();
            iC = fVar.i() - fVar.c();
            i5 = i * 2;
            i4 = i5 + 1;
        } else {
            i3 = fVar.i() - fVar.c();
            iC = fVar.c();
            i4 = i * 2;
            i5 = i4 + 1;
        }
        a.b.a.j.e[] eVarArr = fVar.A;
        if (eVarArr[i4].d == null || eVarArr[i5].d != null) {
            i6 = 1;
        } else {
            i6 = -1;
            int i9 = i4;
            i4 = i5;
            i5 = i9;
        }
        int i10 = z2 ? i2 - i3 : i2;
        int iB = (fVar.A[i5].b() * i6) + a(fVar, i);
        int i11 = i10 + iB;
        int iS2 = (i == 0 ? fVar.s() : fVar.i()) * i6;
        java.util.Iterator<a.b.a.j.o> it = fVar.A[i5].d().f75a.iterator();
        while (it.hasNext()) {
            iMax = java.lang.Math.max(iMax, a(((a.b.a.j.m) it.next()).f73c.f48b, i, z, i11));
        }
        int iMax2 = 0;
        for (java.util.Iterator<a.b.a.j.o> it2 = fVar.A[i4].d().f75a.iterator(); it2.hasNext(); it2 = it2) {
            iMax2 = java.lang.Math.max(iMax2, a(((a.b.a.j.m) it2.next()).f73c.f48b, i, z, iS2 + i11));
        }
        if (z2) {
            iMax -= i3;
            iS = iMax2 + iC;
        } else {
            iS = iMax2 + ((i == 0 ? fVar.s() : fVar.i()) * i6);
        }
        int i12 = 1;
        if (i == 1) {
            java.util.Iterator<a.b.a.j.o> it3 = fVar.w.d().f75a.iterator();
            int iMax3 = 0;
            while (it3.hasNext()) {
                java.util.Iterator<a.b.a.j.o> it4 = it3;
                a.b.a.j.m mVar = (a.b.a.j.m) it3.next();
                iMax3 = i6 == i12 ? java.lang.Math.max(iMax3, a(mVar.f73c.f48b, i, z, i3 + i11)) : java.lang.Math.max(iMax3, a(mVar.f73c.f48b, i, z, (iC * i6) + i11));
                it3 = it4;
                i4 = i4;
                i12 = 1;
            }
            i7 = i4;
            int i13 = iMax3;
            i8 = (fVar.w.d().f75a.size() <= 0 || z2) ? i13 : i6 == 1 ? i13 + i3 : i13 - iC;
        } else {
            i7 = i4;
            i8 = 0;
        }
        int iMax4 = iB + java.lang.Math.max(iMax, java.lang.Math.max(iS, i8));
        int i14 = iS2 + i11;
        if (i6 == -1) {
            i14 = i11;
            i11 = i14;
        }
        if (z) {
            a.b.a.j.k.a(fVar, i, i11);
            fVar.a(i11, i14, i);
        } else {
            fVar.p.a(fVar, i);
            fVar.d(i11, i);
        }
        if (fVar.c(i) == a.b.a.j.f.b.MATCH_CONSTRAINT && fVar.G != 0.0f) {
            fVar.p.a(fVar, i);
        }
        a.b.a.j.e[] eVarArr2 = fVar.A;
        if (eVarArr2[i5].d != null && eVarArr2[i7].d != null) {
            a.b.a.j.f fVarK = fVar.k();
            a.b.a.j.e[] eVarArr3 = fVar.A;
            if (eVarArr3[i5].d.f48b == fVarK && eVarArr3[i7].d.f48b == fVarK) {
                fVar.p.a(fVar, i);
            }
        }
        return iMax4;
    }

    private static int a(a.b.a.j.h hVar, int i) {
        int i2 = i * 2;
        java.util.List<a.b.a.j.f> listA = hVar.a(i);
        int size = listA.size();
        int iMax = 0;
        for (int i3 = 0; i3 < size; i3++) {
            a.b.a.j.f fVar = listA.get(i3);
            a.b.a.j.e[] eVarArr = fVar.A;
            int i4 = i2 + 1;
            iMax = java.lang.Math.max(iMax, a(fVar, i, eVarArr[i4].d == null || !(eVarArr[i2].d == null || eVarArr[i4].d == null), 0));
        }
        hVar.e[i] = iMax;
        return iMax;
    }

    private static void a(a.b.a.j.e eVar) {
        a.b.a.j.m mVarD = eVar.d();
        a.b.a.j.e eVar2 = eVar.d;
        if (eVar2 == null || eVar2.d == eVar) {
            return;
        }
        eVar2.d().a(mVarD);
    }

    private static void a(a.b.a.j.f fVar, int i, int i2) {
        int i3 = i * 2;
        a.b.a.j.e[] eVarArr = fVar.A;
        a.b.a.j.e eVar = eVarArr[i3];
        a.b.a.j.e eVar2 = eVarArr[i3 + 1];
        if ((eVar.d == null || eVar2.d == null) ? false : true) {
            a.b.a.j.k.a(fVar, i, a(fVar, i) + eVar.b());
            return;
        }
        if (fVar.G == 0.0f || fVar.c(i) != a.b.a.j.f.b.MATCH_CONSTRAINT) {
            int iE = i2 - fVar.e(i);
            int iD = iE - fVar.d(i);
            fVar.a(iD, iE, i);
            a.b.a.j.k.a(fVar, i, iD);
            return;
        }
        int iA = a(fVar);
        int i4 = (int) fVar.A[i3].d().g;
        eVar2.d().f = eVar.d();
        eVar2.d().g = iA;
        eVar2.d().f76b = 1;
        fVar.a(i4, i4 + iA, i);
    }

    public static void a(a.b.a.j.g gVar) {
        if ((gVar.M() & 32) != 32) {
            b(gVar);
            return;
        }
        gVar.D0 = true;
        gVar.x0 = false;
        gVar.y0 = false;
        gVar.z0 = false;
        java.util.ArrayList<a.b.a.j.f> arrayList = gVar.k0;
        java.util.List<a.b.a.j.h> list = gVar.w0;
        boolean z = gVar.j() == a.b.a.j.f.b.WRAP_CONTENT;
        boolean z2 = gVar.q() == a.b.a.j.f.b.WRAP_CONTENT;
        boolean z3 = z || z2;
        list.clear();
        for (a.b.a.j.f fVar : arrayList) {
            fVar.p = null;
            fVar.d0 = false;
            fVar.F();
        }
        for (a.b.a.j.f fVar2 : arrayList) {
            if (fVar2.p == null && !a(fVar2, list, z3)) {
                b(gVar);
                gVar.D0 = false;
                return;
            }
        }
        int iMax = 0;
        int iMax2 = 0;
        for (a.b.a.j.h hVar : list) {
            iMax = java.lang.Math.max(iMax, a(hVar, 0));
            iMax2 = java.lang.Math.max(iMax2, a(hVar, 1));
        }
        if (z) {
            gVar.a(a.b.a.j.f.b.FIXED);
            gVar.o(iMax);
            gVar.x0 = true;
            gVar.y0 = true;
            gVar.A0 = iMax;
        }
        if (z2) {
            gVar.b(a.b.a.j.f.b.FIXED);
            gVar.g(iMax2);
            gVar.x0 = true;
            gVar.z0 = true;
            gVar.B0 = iMax2;
        }
        a(list, 0, gVar.s());
        a(list, 1, gVar.i());
    }

    private static void a(a.b.a.j.g gVar, a.b.a.j.f fVar, a.b.a.j.h hVar) {
        hVar.d = false;
        gVar.D0 = false;
        fVar.b0 = false;
    }

    public static void a(java.util.List<a.b.a.j.h> list, int i, int i2) {
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            for (a.b.a.j.f fVar : list.get(i3).b(i)) {
                if (fVar.b0) {
                    a(fVar, i, i2);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:123:0x0183  */
    /* JADX WARN: Code duplicated, block: B:92:0x012a  */
    private static boolean a(a.b.a.j.f fVar, a.b.a.j.h hVar, java.util.List<a.b.a.j.h> list, boolean z) {
        a.b.a.j.e eVar;
        a.b.a.j.e eVar2;
        a.b.a.j.e eVar3;
        a.b.a.j.e eVar4;
        a.b.a.j.e eVar5;
        a.b.a.j.e eVar6;
        if (fVar == null) {
            return true;
        }
        fVar.c0 = false;
        a.b.a.j.g gVar = (a.b.a.j.g) fVar.k();
        a.b.a.j.h hVar2 = fVar.p;
        if (hVar2 != null) {
            if (hVar2 != hVar) {
                hVar.f68a.addAll(hVar2.f68a);
                hVar.f.addAll(fVar.p.f);
                hVar.g.addAll(fVar.p.g);
                if (!fVar.p.d) {
                    hVar.d = false;
                }
                list.remove(fVar.p);
                java.util.Iterator<a.b.a.j.f> it = fVar.p.f68a.iterator();
                while (it.hasNext()) {
                    it.next().p = hVar;
                }
            }
            return true;
        }
        fVar.b0 = true;
        hVar.f68a.add(fVar);
        fVar.p = hVar;
        if (fVar.s.d == null && fVar.u.d == null && fVar.t.d == null && fVar.v.d == null && fVar.w.d == null && fVar.z.d == null) {
            a(gVar, fVar, hVar);
            if (z) {
                return false;
            }
        }
        if (fVar.t.d != null && fVar.v.d != null) {
            gVar.q();
            a.b.a.j.f.b bVar = a.b.a.j.f.b.WRAP_CONTENT;
            if (z) {
                a(gVar, fVar, hVar);
                return false;
            }
            if (fVar.t.d.f48b != fVar.k() || fVar.v.d.f48b != fVar.k()) {
                a(gVar, fVar, hVar);
            }
        }
        if (fVar.s.d != null && fVar.u.d != null) {
            gVar.j();
            a.b.a.j.f.b bVar2 = a.b.a.j.f.b.WRAP_CONTENT;
            if (z) {
                a(gVar, fVar, hVar);
                return false;
            }
            if (fVar.s.d.f48b != fVar.k() || fVar.u.d.f48b != fVar.k()) {
                a(gVar, fVar, hVar);
            }
        }
        if (((fVar.j() == a.b.a.j.f.b.MATCH_CONSTRAINT) ^ (fVar.q() == a.b.a.j.f.b.MATCH_CONSTRAINT)) && fVar.G != 0.0f) {
            a(fVar);
        } else if (fVar.j() == a.b.a.j.f.b.MATCH_CONSTRAINT || fVar.q() == a.b.a.j.f.b.MATCH_CONSTRAINT) {
            a(gVar, fVar, hVar);
            if (z) {
                return false;
            }
        }
        if ((fVar.s.d != null || fVar.u.d != null) && (((eVar = fVar.s.d) == null || eVar.f48b != fVar.D || fVar.u.d != null) && ((eVar2 = fVar.u.d) == null || eVar2.f48b != fVar.D || fVar.s.d != null))) {
            a.b.a.j.e eVar7 = fVar.s.d;
            if (eVar7 != null) {
                a.b.a.j.f fVar2 = eVar7.f48b;
                a.b.a.j.f fVar3 = fVar.D;
                if (fVar2 == fVar3 && (eVar3 = fVar.u.d) != null && eVar3.f48b == fVar3) {
                    if (fVar.z.d == null && !(fVar instanceof a.b.a.j.i) && !(fVar instanceof a.b.a.j.j)) {
                        hVar.f.add(fVar);
                    }
                }
            }
        } else if (fVar.z.d == null) {
            hVar.f.add(fVar);
        }
        if ((fVar.t.d != null || fVar.v.d != null) && (((eVar4 = fVar.t.d) == null || eVar4.f48b != fVar.D || fVar.v.d != null) && ((eVar5 = fVar.v.d) == null || eVar5.f48b != fVar.D || fVar.t.d != null))) {
            a.b.a.j.e eVar8 = fVar.t.d;
            if (eVar8 != null) {
                a.b.a.j.f fVar4 = eVar8.f48b;
                a.b.a.j.f fVar5 = fVar.D;
                if (fVar4 == fVar5 && (eVar6 = fVar.v.d) != null && eVar6.f48b == fVar5) {
                    if (fVar.z.d == null && fVar.w.d == null && !(fVar instanceof a.b.a.j.i) && !(fVar instanceof a.b.a.j.j)) {
                        hVar.g.add(fVar);
                    }
                }
            }
        } else if (fVar.z.d == null) {
            hVar.g.add(fVar);
        }
        if (fVar instanceof a.b.a.j.j) {
            a(gVar, fVar, hVar);
            if (z) {
                return false;
            }
            a.b.a.j.j jVar = (a.b.a.j.j) fVar;
            for (int i = 0; i < jVar.l0; i++) {
                if (!a(jVar.k0[i], hVar, list, z)) {
                    return false;
                }
            }
        }
        int length = fVar.A.length;
        for (int i2 = 0; i2 < length; i2++) {
            a.b.a.j.e eVar9 = fVar.A[i2];
            a.b.a.j.e eVar10 = eVar9.d;
            if (eVar10 != null && eVar10.f48b != fVar.k()) {
                if (eVar9.f49c == a.b.a.j.e.d.CENTER) {
                    a(gVar, fVar, hVar);
                    if (z) {
                        return false;
                    }
                } else {
                    a(eVar9);
                }
                if (!a(eVar9.d.f48b, hVar, list, z)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean a(a.b.a.j.f fVar, java.util.List<a.b.a.j.h> list, boolean z) {
        a.b.a.j.h hVar = new a.b.a.j.h(new java.util.ArrayList(), true);
        list.add(hVar);
        return a(fVar, hVar, list, z);
    }

    private static void b(a.b.a.j.g gVar) {
        gVar.w0.clear();
        gVar.w0.add(0, new a.b.a.j.h(gVar.k0));
    }
}
