package a.b.a.j;

/* JADX INFO: loaded from: classes.dex */
class c {
    static void a(a.b.a.j.g gVar, a.b.a.e eVar, int i) {
        int i2;
        int i3;
        a.b.a.j.d[] dVarArr;
        if (i == 0) {
            int i4 = gVar.s0;
            dVarArr = gVar.v0;
            i3 = i4;
            i2 = 0;
        } else {
            i2 = 2;
            i3 = gVar.t0;
            dVarArr = gVar.u0;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            a.b.a.j.d dVar = dVarArr[i5];
            dVar.a();
            if (!gVar.t(4) || !a.b.a.j.k.a(gVar, eVar, i, i2, dVar)) {
                a(gVar, eVar, i, i2, dVar);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:156:0x02aa  */
    /* JADX WARN: Code duplicated, block: B:172:0x02ef  */
    /* JADX WARN: Code duplicated, block: B:173:0x0301  */
    /* JADX WARN: Code duplicated, block: B:175:0x0309  */
    /* JADX WARN: Code duplicated, block: B:176:0x030c  */
    /* JADX WARN: Code duplicated, block: B:179:0x0316  */
    /* JADX WARN: Code duplicated, block: B:181:0x031d  */
    /* JADX WARN: Code duplicated, block: B:238:0x03ed  */
    /* JADX WARN: Code duplicated, block: B:241:0x03f6  */
    /* JADX WARN: Code duplicated, block: B:244:0x0402  */
    /* JADX WARN: Code duplicated, block: B:245:0x0405  */
    /* JADX WARN: Code duplicated, block: B:251:0x0423  */
    /* JADX WARN: Code duplicated, block: B:29:0x004a A[PHI: r8 r14
  0x004a: PHI (r8v4 boolean) = (r8v2 boolean), (r8v46 boolean) binds: [B:28:0x0048, B:17:0x0035] A[DONT_GENERATE, DONT_INLINE]
  0x004a: PHI (r14v4 boolean) = (r14v2 boolean), (r14v30 boolean) binds: [B:28:0x0048, B:17:0x0035] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:30:0x004c A[PHI: r8 r14
  0x004c: PHI (r8v43 boolean) = (r8v2 boolean), (r8v46 boolean) binds: [B:28:0x0048, B:17:0x0035] A[DONT_GENERATE, DONT_INLINE]
  0x004c: PHI (r14v27 boolean) = (r14v2 boolean), (r14v30 boolean) binds: [B:28:0x0048, B:17:0x0035] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:85:0x0150  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r38v0, types: [a.b.a.e] */
    /* JADX WARN: Type inference failed for: r5v27 */
    /* JADX WARN: Type inference failed for: r5v28, types: [a.b.a.i] */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2, types: [a.b.a.j.f] */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v32 */
    /* JADX WARN: Type inference failed for: r7v33 */
    static void a(a.b.a.j.g gVar, a.b.a.e eVar, int i, int i2, a.b.a.j.d dVar) {
        boolean z;
        boolean z2;
        boolean z3;
        int i3;
        int i4;
        a.b.a.j.e eVar2;
        a.b.a.i iVar;
        a.b.a.j.e eVar3;
        a.b.a.i iVar2;
        int i5;
        a.b.a.j.e eVar4;
        int iB;
        int i6;
        int iB2;
        a.b.a.j.e eVar5;
        a.b.a.i iVar3;
        a.b.a.i iVar4;
        ?? r5;
        int size;
        int i7;
        int i8;
        a.b.a.j.f fVar = dVar.f44a;
        a.b.a.j.f fVar2 = dVar.f46c;
        a.b.a.j.f fVar3 = dVar.f45b;
        a.b.a.j.f fVar4 = dVar.d;
        a.b.a.j.f fVar5 = dVar.e;
        float f = dVar.k;
        a.b.a.j.f fVar6 = dVar.f;
        a.b.a.j.f fVar7 = dVar.g;
        boolean z4 = gVar.C[i] == a.b.a.j.f.b.WRAP_CONTENT;
        if (i == 0) {
            z = fVar5.e0 == 0;
            z2 = fVar5.e0 == 1;
            if (fVar5.e0 == 2) {
                z3 = true;
            } else {
                z3 = false;
            }
        } else {
            z = fVar5.f0 == 0;
            z2 = fVar5.f0 == 1;
            if (fVar5.f0 == 2) {
                z3 = true;
            } else {
                z3 = false;
            }
        }
        ?? r7 = fVar;
        boolean z5 = z2;
        boolean z6 = z;
        boolean z7 = false;
        while (true) {
            java.lang.Object obj = null;
            if (z7) {
                break;
            }
            a.b.a.j.e eVar6 = r7.A[i2];
            int i9 = (z4 || z3) ? 1 : 4;
            int iB3 = eVar6.b();
            float f2 = f;
            a.b.a.j.e eVar7 = eVar6.d;
            if (eVar7 != null && r7 != fVar) {
                iB3 += eVar7.b();
            }
            int i10 = iB3;
            if (!z3 || r7 == fVar || r7 == fVar3) {
                i7 = (z6 && z4) ? 4 : i9;
            } else {
                i7 = 6;
            }
            a.b.a.j.e eVar8 = eVar6.d;
            if (eVar8 != null) {
                if (r7 == fVar3) {
                    eVar.b(eVar6.i, eVar8.i, i10, 5);
                } else {
                    eVar.b(eVar6.i, eVar8.i, i10, 6);
                }
                eVar.a(eVar6.i, eVar6.d.i, i10, i7);
            } else {
                fVar5 = fVar5;
                z6 = z6;
            }
            if (z4) {
                if (r7.r() == 8 || r7.C[i] != a.b.a.j.f.b.MATCH_CONSTRAINT) {
                    i8 = 0;
                } else {
                    a.b.a.j.e[] eVarArr = r7.A;
                    i8 = 0;
                    eVar.b(eVarArr[i2 + 1].i, eVarArr[i2].i, 0, 5);
                }
                eVar.b(r7.A[i2].i, gVar.A[i2].i, i8, 6);
            }
            a.b.a.j.e eVar9 = r7.A[i2 + 1].d;
            if (eVar9 != null) {
                a.b.a.j.f fVar8 = eVar9.f48b;
                a.b.a.j.e[] eVarArr2 = fVar8.A;
                if (eVarArr2[i2].d != null && eVarArr2[i2].d.f48b == r7) {
                    obj = fVar8;
                }
            }
            if (obj != null) {
                r7 = obj;
                z7 = z7;
            } else {
                z7 = true;
            }
            z5 = z5;
            f = f2;
            z6 = z6;
            fVar5 = fVar5;
            r7 = r7;
        }
        a.b.a.j.f fVar9 = fVar5;
        float f3 = f;
        boolean z8 = z6;
        boolean z9 = z5;
        if (fVar4 != null) {
            a.b.a.j.e[] eVarArr3 = fVar2.A;
            int i11 = i2 + 1;
            if (eVarArr3[i11].d != null) {
                a.b.a.j.e eVar10 = fVar4.A[i11];
                eVar.c(eVar10.i, eVarArr3[i11].d.i, -eVar10.b(), 5);
            }
        }
        if (z4) {
            int i12 = i2 + 1;
            a.b.a.i iVar5 = gVar.A[i12].i;
            a.b.a.j.e[] eVarArr4 = fVar2.A;
            eVar.b(iVar5, eVarArr4[i12].i, eVarArr4[i12].b(), 6);
        }
        java.util.ArrayList<a.b.a.j.f> arrayList = dVar.h;
        if (arrayList != null && (size = arrayList.size()) > 1) {
            float f4 = (!dVar.n || dVar.p) ? f3 : dVar.j;
            float f5 = 0.0f;
            a.b.a.j.f fVar10 = null;
            int i13 = 0;
            float f6 = 0.0f;
            while (i13 < size) {
                a.b.a.j.f fVar11 = arrayList.get(i13);
                float f7 = fVar11.g0[i];
                if (f7 < f5) {
                    if (dVar.p) {
                        a.b.a.j.e[] eVarArr5 = fVar11.A;
                        eVar.a(eVarArr5[i2 + 1].i, eVarArr5[i2].i, 0, 4);
                    } else {
                        f7 = 1.0f;
                        f5 = 0.0f;
                    }
                    arrayList = arrayList;
                    size = size;
                    i13++;
                    size = size;
                    arrayList = arrayList;
                    f5 = 0.0f;
                }
                if (f7 == f5) {
                    a.b.a.j.e[] eVarArr6 = fVar11.A;
                    eVar.a(eVarArr6[i2 + 1].i, eVarArr6[i2].i, 0, 6);
                    arrayList = arrayList;
                    size = size;
                } else {
                    if (fVar10 != null) {
                        a.b.a.j.e[] eVarArr7 = fVar10.A;
                        a.b.a.i iVar6 = eVarArr7[i2].i;
                        int i14 = i2 + 1;
                        a.b.a.i iVar7 = eVarArr7[i14].i;
                        a.b.a.j.e[] eVarArr8 = fVar11.A;
                        a.b.a.i iVar8 = eVarArr8[i2].i;
                        a.b.a.i iVar9 = eVarArr8[i14].i;
                        a.b.a.b bVarB = eVar.b();
                        bVarB.a(f6, f4, f7, iVar6, iVar7, iVar8, iVar9);
                        eVar.a(bVarB);
                    }
                    f6 = f7;
                    fVar10 = fVar11;
                }
                i13++;
                size = size;
                arrayList = arrayList;
                f5 = 0.0f;
            }
        }
        if (fVar3 != null && (fVar3 == fVar4 || z3)) {
            a.b.a.j.e[] eVarArr9 = fVar.A;
            a.b.a.j.e eVar11 = eVarArr9[i2];
            int i15 = i2 + 1;
            a.b.a.j.e eVar12 = fVar2.A[i15];
            a.b.a.i iVar10 = eVarArr9[i2].d != null ? eVarArr9[i2].d.i : null;
            a.b.a.j.e[] eVarArr10 = fVar2.A;
            a.b.a.i iVar11 = eVarArr10[i15].d != null ? eVarArr10[i15].d.i : null;
            if (fVar3 == fVar4) {
                a.b.a.j.e[] eVarArr11 = fVar3.A;
                eVar11 = eVarArr11[i2];
                eVar12 = eVarArr11[i15];
            }
            if (iVar10 != null && iVar11 != null) {
                eVar.a(eVar11.i, iVar10, eVar11.b(), i == 0 ? fVar9.V : fVar9.W, iVar11, eVar12.i, eVar12.b(), 5);
            }
        } else if (!z8 || fVar3 == null) {
            int i16 = 8;
            if (z9 && fVar3 != null) {
                int i17 = dVar.j;
                boolean z10 = i17 > 0 && dVar.i == i17;
                a.b.a.j.f fVar12 = fVar3;
                a.b.a.j.f fVar13 = fVar12;
                while (fVar12 != null) {
                    a.b.a.j.f fVar14 = fVar12.i0[i];
                    while (fVar14 != null && fVar14.r() == i16) {
                        fVar14 = fVar14.i0[i];
                    }
                    if (fVar12 == fVar3 || fVar12 == fVar4 || fVar14 == null) {
                        fVar13 = fVar13;
                        i4 = 8;
                    } else {
                        a.b.a.j.f fVar15 = fVar14 == fVar4 ? null : fVar14;
                        a.b.a.j.e eVar13 = fVar12.A[i2];
                        a.b.a.i iVar12 = eVar13.i;
                        a.b.a.j.e eVar14 = eVar13.d;
                        if (eVar14 != null) {
                            a.b.a.i iVar13 = eVar14.i;
                        }
                        int i18 = i2 + 1;
                        a.b.a.i iVar14 = fVar13.A[i18].i;
                        int iB4 = eVar13.b();
                        int iB5 = fVar12.A[i18].b();
                        if (fVar15 != null) {
                            eVar2 = fVar15.A[i2];
                            iVar = eVar2.i;
                            eVar3 = eVar2.d;
                            if (eVar3 == null) {
                                iVar2 = null;
                            }
                            if (eVar2 != null) {
                                iB5 += eVar2.b();
                            }
                            int i19 = iB5;
                            if (fVar13 != null) {
                                iB4 += fVar13.A[i18].b();
                            }
                            int i20 = iB4;
                            if (z10) {
                                i5 = 6;
                            } else {
                                i5 = 4;
                            }
                            if (iVar12 != null || iVar14 == null || iVar == null || iVar2 == null) {
                                i4 = 8;
                            } else {
                                i4 = 8;
                                eVar.a(iVar12, iVar14, i20, 0.5f, iVar, iVar2, i19, i5);
                            }
                            fVar14 = fVar15;
                        } else {
                            eVar2 = fVar12.A[i18].d;
                            iVar = eVar2 != null ? eVar2.i : null;
                            eVar3 = fVar12.A[i18];
                        }
                        iVar2 = eVar3.i;
                        if (eVar2 != null) {
                            iB5 += eVar2.b();
                        }
                        int i110 = iB5;
                        if (fVar13 != null) {
                            iB4 += fVar13.A[i18].b();
                        }
                        int i21 = iB4;
                        if (z10) {
                            i5 = 6;
                        } else {
                            i5 = 4;
                        }
                        if (iVar12 != null) {
                            i4 = 8;
                        } else {
                            i4 = 8;
                        }
                        fVar14 = fVar15;
                    }
                    if (fVar12.r() == i4) {
                        fVar12 = fVar13;
                    }
                    fVar13 = fVar12;
                    i16 = 8;
                    fVar12 = fVar14;
                }
                a.b.a.j.e eVar15 = fVar3.A[i2];
                a.b.a.j.e eVar16 = fVar.A[i2].d;
                int i22 = i2 + 1;
                a.b.a.j.e eVar17 = fVar4.A[i22];
                a.b.a.j.e eVar18 = fVar2.A[i22].d;
                if (eVar16 == null) {
                    i3 = 5;
                } else if (fVar3 != fVar4) {
                    i3 = 5;
                    eVar.a(eVar15.i, eVar16.i, eVar15.b(), 5);
                } else {
                    i3 = 5;
                    if (eVar18 != null) {
                        eVar.a(eVar15.i, eVar16.i, eVar15.b(), 0.5f, eVar17.i, eVar18.i, eVar17.b(), 5);
                    }
                }
                if (eVar18 != null && fVar3 != fVar4) {
                    eVar.a(eVar17.i, eVar18.i, -eVar17.b(), i3);
                }
            }
        } else {
            int i23 = dVar.j;
            boolean z11 = i23 > 0 && dVar.i == i23;
            a.b.a.j.f fVar16 = fVar3;
            a.b.a.j.f fVar17 = fVar16;
            while (fVar16 != null) {
                a.b.a.j.f fVar18 = fVar16.i0[i];
                while (fVar18 != null && fVar18.r() == 8) {
                    fVar18 = fVar18.i0[i];
                }
                if (fVar18 != null || fVar16 == fVar4) {
                    a.b.a.j.e eVar19 = fVar16.A[i2];
                    a.b.a.i iVar15 = eVar19.i;
                    a.b.a.j.e eVar20 = eVar19.d;
                    a.b.a.i iVar16 = eVar20 != null ? eVar20.i : null;
                    if (fVar17 != fVar16) {
                        eVar4 = fVar17.A[i2 + 1];
                    } else {
                        if (fVar16 == fVar3 && fVar17 == fVar16) {
                            a.b.a.j.e[] eVarArr12 = fVar.A;
                            if (eVarArr12[i2].d != null) {
                                eVar4 = eVarArr12[i2].d;
                            } else {
                                iVar16 = null;
                            }
                        }
                        iB = eVar19.b();
                        i6 = i2 + 1;
                        iB2 = fVar16.A[i6].b();
                        if (fVar18 != null) {
                            eVar5 = fVar18.A[i2];
                            a.b.a.i iVar17 = eVar5.i;
                            iVar4 = fVar16.A[i6].i;
                            iVar3 = iVar17;
                        } else {
                            eVar5 = fVar2.A[i6].d;
                            if (eVar5 != null) {
                                iVar3 = eVar5.i;
                            } else {
                                iVar3 = null;
                            }
                            iVar4 = fVar16.A[i6].i;
                        }
                        if (eVar5 != null) {
                            iB2 += eVar5.b();
                        }
                        if (fVar17 != null) {
                            iB += fVar17.A[i6].b();
                        }
                        if (iVar15 == null && iVar16 != null && iVar3 != null && iVar4 != null) {
                            if (fVar16 == fVar3) {
                                iB = fVar3.A[i2].b();
                            }
                            eVar.a(iVar15, iVar16, iB, 0.5f, iVar3, iVar4, fVar16 == fVar4 ? fVar4.A[i6].b() : iB2, z11 ? 6 : 4);
                        }
                    }
                    iVar16 = eVar4.i;
                    iB = eVar19.b();
                    i6 = i2 + 1;
                    iB2 = fVar16.A[i6].b();
                    if (fVar18 != null) {
                        eVar5 = fVar18.A[i2];
                        a.b.a.i iVar18 = eVar5.i;
                        iVar4 = fVar16.A[i6].i;
                        iVar3 = iVar18;
                    } else {
                        eVar5 = fVar2.A[i6].d;
                        if (eVar5 != null) {
                            iVar3 = eVar5.i;
                        } else {
                            iVar3 = null;
                        }
                        iVar4 = fVar16.A[i6].i;
                    }
                    if (eVar5 != null) {
                        iB2 += eVar5.b();
                    }
                    if (fVar17 != null) {
                        iB += fVar17.A[i6].b();
                    }
                    if (iVar15 == null) {
                    }
                }
                if (fVar16.r() != 8) {
                    fVar17 = fVar16;
                }
                fVar16 = fVar18;
            }
        }
        if ((z8 || z9) && fVar3 != null) {
            a.b.a.j.e eVar21 = fVar3.A[i2];
            int i24 = i2 + 1;
            a.b.a.j.e eVar22 = fVar4.A[i24];
            a.b.a.j.e eVar23 = eVar21.d;
            a.b.a.i iVar19 = eVar23 != null ? eVar23.i : null;
            a.b.a.j.e eVar24 = eVar22.d;
            a.b.a.i iVar20 = eVar24 != null ? eVar24.i : null;
            if (fVar2 != fVar4) {
                a.b.a.j.e eVar25 = fVar2.A[i24].d;
                r5 = eVar25 != null ? eVar25.i : null;
            } else {
                r5 = iVar20;
            }
            if (fVar3 == fVar4) {
                a.b.a.j.e[] eVarArr13 = fVar3.A;
                a.b.a.j.e eVar26 = eVarArr13[i2];
                eVar22 = eVarArr13[i24];
                eVar21 = eVar26;
            }
            if (iVar19 == null || r5 == 0) {
                return;
            }
            int iB6 = eVar21.b();
            if (fVar4 != null) {
                fVar2 = fVar4;
            }
            eVar.a(eVar21.i, iVar19, iB6, 0.5f, r5, eVar22.i, fVar2.A[i24].b(), 5);
        }
    }
}
