package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
public class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    static boolean[] f72a = new boolean[3];

    /* JADX WARN: Code duplicated, block: B:57:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:58:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:59:0x00f8 A[PHI: r7
  0x00f8: PHI (r7v38 int) = (r7v31 int), (r7v39 int), (r7v39 int) binds: [B:58:0x00f4, B:33:0x0080, B:27:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:73:0x012e  */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01c4, code lost:
    
        if (r6 != false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01c6, code lost:
    
        r4.a(r2, 1, r17.l());
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01cf, code lost:
    
        r4.a(r2, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01e0, code lost:
    
        if (r6 != false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void a(int r16, a.b.a.j.f r17) {
        /*
            Method dump skipped, instruction units count: 810
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: a.b.a.j.k.a(int, a.b.a.j.f):void");
    }

    static void a(a.b.a.j.f fVar, int i, int i2) {
        int i3 = i * 2;
        int i4 = i3 + 1;
        fVar.A[i3].d().f = fVar.k().s.d();
        fVar.A[i3].d().g = i2;
        fVar.A[i3].d().f76b = 1;
        fVar.A[i4].d().f = fVar.A[i3].d();
        fVar.A[i4].d().g = fVar.d(i);
        fVar.A[i4].d().f76b = 1;
    }

    static void a(a.b.a.j.g gVar, a.b.a.e eVar, a.b.a.j.f fVar) {
        if (gVar.C[0] != a.b.a.j.f.b.WRAP_CONTENT && fVar.C[0] == a.b.a.j.f.b.MATCH_PARENT) {
            int i = fVar.s.e;
            int iS = gVar.s() - fVar.u.e;
            a.b.a.j.e eVar2 = fVar.s;
            eVar2.i = eVar.a(eVar2);
            a.b.a.j.e eVar3 = fVar.u;
            eVar3.i = eVar.a(eVar3);
            eVar.a(fVar.s.i, i);
            eVar.a(fVar.u.i, iS);
            fVar.f60a = 2;
            fVar.a(i, iS);
        }
        if (gVar.C[1] == a.b.a.j.f.b.WRAP_CONTENT || fVar.C[1] != a.b.a.j.f.b.MATCH_PARENT) {
            return;
        }
        int i2 = fVar.t.e;
        int i3 = gVar.i() - fVar.v.e;
        a.b.a.j.e eVar4 = fVar.t;
        eVar4.i = eVar.a(eVar4);
        a.b.a.j.e eVar5 = fVar.v;
        eVar5.i = eVar.a(eVar5);
        eVar.a(fVar.t.i, i2);
        eVar.a(fVar.v.i, i3);
        if (fVar.Q > 0 || fVar.r() == 8) {
            a.b.a.j.e eVar6 = fVar.w;
            eVar6.i = eVar.a(eVar6);
            eVar.a(fVar.w.i, fVar.Q + i2);
        }
        fVar.f61b = 2;
        fVar.e(i2, i3);
    }

    private static boolean a(a.b.a.j.f fVar, int i) {
        a.b.a.j.f.b[] bVarArr = fVar.C;
        if (bVarArr[i] != a.b.a.j.f.b.MATCH_CONSTRAINT) {
            return false;
        }
        if (fVar.G != 0.0f) {
            a.b.a.j.f.b bVar = bVarArr[i != 0 ? (char) 0 : (char) 1];
            a.b.a.j.f.b bVar2 = a.b.a.j.f.b.MATCH_CONSTRAINT;
            return false;
        }
        if (i == 0) {
            if (fVar.e != 0 || fVar.h != 0 || fVar.i != 0) {
                return false;
            }
        } else if (fVar.f != 0 || fVar.k != 0 || fVar.l != 0) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0034 A[PHI: r11 r12
  0x0034: PHI (r11v18 boolean) = (r11v2 boolean), (r11v21 boolean) binds: [B:25:0x0048, B:13:0x0032] A[DONT_GENERATE, DONT_INLINE]
  0x0034: PHI (r12v9 boolean) = (r12v2 boolean), (r12v12 boolean) binds: [B:25:0x0048, B:13:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:15:0x0036 A[PHI: r11 r12
  0x0036: PHI (r11v4 boolean) = (r11v2 boolean), (r11v21 boolean) binds: [B:25:0x0048, B:13:0x0032] A[DONT_GENERATE, DONT_INLINE]
  0x0036: PHI (r12v4 boolean) = (r12v2 boolean), (r12v12 boolean) binds: [B:25:0x0048, B:13:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:76:0x0101  */
    static boolean a(a.b.a.j.g gVar, a.b.a.e eVar, int i, int i2, a.b.a.j.d dVar) {
        boolean z;
        boolean z2;
        boolean z3;
        a.b.a.j.m mVar;
        float fB;
        float f;
        a.b.a.j.f fVar;
        boolean z4;
        a.b.a.j.f fVar2 = dVar.f44a;
        a.b.a.j.f fVar3 = dVar.f46c;
        a.b.a.j.f fVar4 = dVar.f45b;
        a.b.a.j.f fVar5 = dVar.d;
        a.b.a.j.f fVar6 = dVar.e;
        float f2 = dVar.k;
        a.b.a.j.f fVar7 = dVar.f;
        a.b.a.j.f fVar8 = dVar.g;
        a.b.a.j.f.b bVar = gVar.C[i];
        a.b.a.j.f.b bVar2 = a.b.a.j.f.b.WRAP_CONTENT;
        if (i == 0) {
            z = fVar6.e0 == 0;
            z2 = fVar6.e0 == 1;
            if (fVar6.e0 == 2) {
                z3 = true;
            } else {
                z3 = false;
            }
        } else {
            z = fVar6.f0 == 0;
            z2 = fVar6.f0 == 1;
            if (fVar6.f0 == 2) {
                z3 = true;
            } else {
                z3 = false;
            }
        }
        a.b.a.j.f fVar9 = fVar2;
        int i3 = 0;
        boolean z5 = false;
        int i4 = 0;
        float fS = 0.0f;
        float fB2 = 0.0f;
        while (!z5) {
            if (fVar9.r() != 8) {
                i4++;
                fS += i == 0 ? fVar9.s() : fVar9.i();
                if (fVar9 != fVar4) {
                    fS += fVar9.A[i2].b();
                }
                if (fVar9 != fVar5) {
                    fS += fVar9.A[i2 + 1].b();
                }
                fB2 = fB2 + fVar9.A[i2].b() + fVar9.A[i2 + 1].b();
            }
            a.b.a.j.e eVar2 = fVar9.A[i2];
            if (fVar9.r() != 8 && fVar9.C[i] == a.b.a.j.f.b.MATCH_CONSTRAINT) {
                i3++;
                if (i != 0) {
                    z4 = false;
                    if (fVar9.f != 0) {
                        return false;
                    }
                    if (fVar9.k != 0 || fVar9.l != 0) {
                    }
                    return z4;
                }
                if (fVar9.e != 0) {
                    return false;
                }
                z4 = false;
                if (fVar9.h != 0 || fVar9.i != 0) {
                    return false;
                }
                if (fVar9.G != 0.0f) {
                    return z4;
                }
            }
            a.b.a.j.e eVar3 = fVar9.A[i2 + 1].d;
            if (eVar3 != null) {
                a.b.a.j.f fVar10 = eVar3.f48b;
                a.b.a.j.e[] eVarArr = fVar10.A;
                if (eVarArr[i2].d == null || eVarArr[i2].d.f48b != fVar9) {
                    fVar = null;
                } else {
                    fVar = fVar10;
                }
            } else {
                fVar = null;
            }
            if (fVar != null) {
                fVar9 = fVar;
            } else {
                z5 = true;
            }
        }
        a.b.a.j.m mVarD = fVar2.A[i2].d();
        int i5 = i2 + 1;
        a.b.a.j.m mVarD2 = fVar3.A[i5].d();
        a.b.a.j.m mVar2 = mVarD.d;
        if (mVar2 == null || (mVar = mVarD2.d) == null || mVar2.f76b != 1 || mVar.f76b != 1) {
            return false;
        }
        if (i3 > 0 && i3 != i4) {
            return false;
        }
        if (z3 || z || z2) {
            fB = fVar4 != null ? fVar4.A[i2].b() : 0.0f;
            if (fVar5 != null) {
                fB += fVar5.A[i5].b();
            }
        } else {
            fB = 0.0f;
        }
        float f3 = mVarD.d.g;
        float f4 = mVarD2.d.g;
        float f5 = (f3 < f4 ? f4 - f3 : f3 - f4) - fS;
        if (i3 > 0 && i3 == i4) {
            if (fVar9.k() != null && fVar9.k().C[i] == a.b.a.j.f.b.WRAP_CONTENT) {
                return false;
            }
            float f6 = (f5 + fS) - fB2;
            float fB3 = f3;
            a.b.a.j.f fVar11 = fVar2;
            while (fVar11 != null) {
                a.b.a.f fVar12 = a.b.a.e.q;
                if (fVar12 != null) {
                    fVar12.z--;
                    fVar12.r++;
                    fVar12.x++;
                }
                a.b.a.j.f fVar13 = fVar11.i0[i];
                if (fVar13 != null || fVar11 == fVar3) {
                    float f7 = f6 / i3;
                    if (f2 > 0.0f) {
                        float[] fArr = fVar11.g0;
                        if (fArr[i] == -1.0f) {
                            f = 0.0f;
                        } else {
                            f7 = (fArr[i] * f6) / f2;
                            f = f7;
                        }
                    } else {
                        f = f7;
                    }
                    if (fVar11.r() == 8) {
                        f = 0.0f;
                    }
                    float fB4 = fB3 + fVar11.A[i2].b();
                    fVar11.A[i2].d().a(mVarD.f, fB4);
                    float f8 = fB4 + f;
                    fVar11.A[i5].d().a(mVarD.f, f8);
                    fVar11.A[i2].d().a(eVar);
                    fVar11.A[i5].d().a(eVar);
                    fB3 = f8 + fVar11.A[i5].b();
                }
                fVar11 = fVar13;
            }
            return true;
        }
        if (f5 < 0.0f) {
            z3 = true;
            z = false;
            z2 = false;
        }
        if (z3) {
            a.b.a.j.f fVar14 = fVar2;
            float fB5 = f3 + ((f5 - fB) * fVar14.b(i));
            while (true) {
                a.b.a.j.f fVar15 = fVar14;
                if (fVar15 == null) {
                    return true;
                }
                a.b.a.f fVar16 = a.b.a.e.q;
                if (fVar16 != null) {
                    fVar16.z--;
                    fVar16.r++;
                    fVar16.x++;
                }
                fVar14 = fVar15.i0[i];
                if (fVar14 != null || fVar15 == fVar3) {
                    int iS = i == 0 ? fVar15.s() : fVar15.i();
                    float fB6 = fB5 + fVar15.A[i2].b();
                    fVar15.A[i2].d().a(mVarD.f, fB6);
                    float f9 = fB6 + iS;
                    fVar15.A[i5].d().a(mVarD.f, f9);
                    fVar15.A[i2].d().a(eVar);
                    fVar15.A[i5].d().a(eVar);
                    fB5 = f9 + fVar15.A[i5].b();
                }
            }
        } else {
            a.b.a.j.f fVar17 = fVar2;
            if (!z && !z2) {
                return true;
            }
            if (z || z2) {
                f5 -= fB;
            }
            float f10 = f5 / (i4 + 1);
            if (z2) {
                f10 = f5 / (i4 > 1 ? i4 - 1 : 2.0f);
            }
            float fB7 = fVar17.r() != 8 ? f3 + f10 : f3;
            if (z2 && i4 > 1) {
                fB7 = fVar4.A[i2].b() + f3;
            }
            if (z && fVar4 != null) {
                fB7 += fVar4.A[i2].b();
            }
            while (true) {
                a.b.a.j.f fVar18 = fVar17;
                if (fVar18 == null) {
                    return true;
                }
                a.b.a.f fVar19 = a.b.a.e.q;
                if (fVar19 != null) {
                    fVar19.z--;
                    fVar19.r++;
                    fVar19.x++;
                }
                fVar17 = fVar18.i0[i];
                if (fVar17 != null || fVar18 == fVar3) {
                    float fS2 = i == 0 ? fVar18.s() : fVar18.i();
                    if (fVar18 != fVar4) {
                        fB7 += fVar18.A[i2].b();
                    }
                    fVar18.A[i2].d().a(mVarD.f, fB7);
                    fVar18.A[i5].d().a(mVarD.f, fB7 + fS2);
                    fVar18.A[i2].d().a(eVar);
                    fVar18.A[i5].d().a(eVar);
                    fB7 += fS2 + fVar18.A[i5].b();
                    if (fVar17 != null) {
                        if (fVar17.r() != 8) {
                            fB7 += f10;
                        }
                    }
                }
            }
        }
    }
}
