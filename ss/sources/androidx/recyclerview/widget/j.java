package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final androidx.recyclerview.widget.j.a f329a;

    interface a {
        androidx.recyclerview.widget.a.b a(int i, int i2, int i3, java.lang.Object obj);

        void a(androidx.recyclerview.widget.a.b bVar);
    }

    j(androidx.recyclerview.widget.j.a aVar) {
        this.f329a = aVar;
    }

    private void a(java.util.List<androidx.recyclerview.widget.a.b> list, int i, int i2) {
        androidx.recyclerview.widget.a.b bVar = list.get(i);
        androidx.recyclerview.widget.a.b bVar2 = list.get(i2);
        int i3 = bVar2.f255a;
        if (i3 == 1) {
            c(list, i, bVar, i2, bVar2);
        } else if (i3 == 2) {
            a(list, i, bVar, i2, bVar2);
        } else {
            if (i3 != 4) {
                return;
            }
            b(list, i, bVar, i2, bVar2);
        }
    }

    private int b(java.util.List<androidx.recyclerview.widget.a.b> list) {
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).f255a != 8) {
                z = true;
            } else if (z) {
                return size;
            }
        }
        return -1;
    }

    private void c(java.util.List<androidx.recyclerview.widget.a.b> list, int i, androidx.recyclerview.widget.a.b bVar, int i2, androidx.recyclerview.widget.a.b bVar2) {
        int i3 = bVar.d < bVar2.f256b ? -1 : 0;
        if (bVar.f256b < bVar2.f256b) {
            i3++;
        }
        int i4 = bVar2.f256b;
        int i5 = bVar.f256b;
        if (i4 <= i5) {
            bVar.f256b = i5 + bVar2.d;
        }
        int i6 = bVar2.f256b;
        int i7 = bVar.d;
        if (i6 <= i7) {
            bVar.d = i7 + bVar2.d;
        }
        bVar2.f256b += i3;
        list.set(i, bVar2);
        list.set(i2, bVar);
    }

    void a(java.util.List<androidx.recyclerview.widget.a.b> list) {
        while (true) {
            int iB = b(list);
            if (iB == -1) {
                return;
            } else {
                a(list, iB, iB + 1);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x00ca A[PHI: r0
  0x00ca: PHI (r0v12 int) = (r0v6 int), (r0v16 int) binds: [B:59:0x00c8, B:46:0x009e] A[DONT_GENERATE, DONT_INLINE]] */
    void a(java.util.List<androidx.recyclerview.widget.a.b> list, int i, androidx.recyclerview.widget.a.b bVar, int i2, androidx.recyclerview.widget.a.b bVar2) {
        boolean z;
        int i3;
        int i4 = bVar.f256b;
        int i5 = bVar.d;
        boolean z2 = false;
        int i6 = bVar2.f256b;
        if (i4 < i5) {
            if (i6 == i4 && bVar2.d == i5 - i4) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
        } else if (i6 == i5 + 1 && bVar2.d == i4 - i5) {
            z = true;
            z2 = true;
        } else {
            z = true;
        }
        int i7 = bVar.d;
        int i8 = bVar2.f256b;
        if (i7 < i8) {
            bVar2.f256b = i8 - 1;
        } else {
            int i9 = bVar2.d;
            if (i7 < i8 + i9) {
                bVar2.d = i9 - 1;
                bVar.f255a = 2;
                bVar.d = 1;
                if (bVar2.d == 0) {
                    list.remove(i2);
                    this.f329a.a(bVar2);
                    return;
                }
                return;
            }
        }
        int i10 = bVar.f256b;
        int i11 = bVar2.f256b;
        androidx.recyclerview.widget.a.b bVarA = null;
        if (i10 <= i11) {
            bVar2.f256b = i11 + 1;
        } else {
            int i12 = bVar2.d;
            if (i10 < i11 + i12) {
                bVarA = this.f329a.a(2, i10 + 1, (i11 + i12) - i10, null);
                bVar2.d = bVar.f256b - bVar2.f256b;
            }
        }
        if (z2) {
            list.set(i, bVar2);
            list.remove(i2);
            this.f329a.a(bVar);
            return;
        }
        if (z) {
            if (bVarA != null) {
                int i13 = bVar.f256b;
                if (i13 > bVarA.f256b) {
                    bVar.f256b = i13 - bVarA.d;
                }
                int i14 = bVar.d;
                if (i14 > bVarA.f256b) {
                    bVar.d = i14 - bVarA.d;
                }
            }
            int i15 = bVar.f256b;
            if (i15 > bVar2.f256b) {
                bVar.f256b = i15 - bVar2.d;
            }
            i3 = bVar.d;
            if (i3 > bVar2.f256b) {
                bVar.d = i3 - bVar2.d;
            }
        } else {
            if (bVarA != null) {
                int i16 = bVar.f256b;
                if (i16 >= bVarA.f256b) {
                    bVar.f256b = i16 - bVarA.d;
                }
                int i17 = bVar.d;
                if (i17 >= bVarA.f256b) {
                    bVar.d = i17 - bVarA.d;
                }
            }
            int i18 = bVar.f256b;
            if (i18 >= bVar2.f256b) {
                bVar.f256b = i18 - bVar2.d;
            }
            i3 = bVar.d;
            if (i3 >= bVar2.f256b) {
                bVar.d = i3 - bVar2.d;
            }
        }
        list.set(i, bVar2);
        if (bVar.f256b != bVar.d) {
            list.set(i2, bVar);
        } else {
            list.remove(i2);
        }
        if (bVarA != null) {
            list.add(i, bVarA);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0027  */
    /* JADX WARN: Code duplicated, block: B:12:0x002b  */
    /* JADX WARN: Code duplicated, block: B:14:0x0031  */
    /* JADX WARN: Code duplicated, block: B:17:0x0048  */
    /* JADX WARN: Code duplicated, block: B:18:0x004c  */
    /* JADX WARN: Code duplicated, block: B:20:0x0056  */
    /* JADX WARN: Code duplicated, block: B:22:0x005b  */
    /* JADX WARN: Code duplicated, block: B:24:? A[RETURN, SYNTHETIC] */
    void b(java.util.List<androidx.recyclerview.widget.a.b> list, int i, androidx.recyclerview.widget.a.b bVar, int i2, androidx.recyclerview.widget.a.b bVar2) {
        androidx.recyclerview.widget.a.b bVarA;
        int i3;
        int i4;
        int i5;
        int i6 = bVar.d;
        int i7 = bVar2.f256b;
        androidx.recyclerview.widget.a.b bVarA2 = null;
        if (i6 >= i7) {
            int i8 = bVar2.d;
            if (i6 < i7 + i8) {
                bVar2.d = i8 - 1;
                bVarA = this.f329a.a(4, bVar.f256b, 1, bVar2.f257c);
            }
            i3 = bVar.f256b;
            i4 = bVar2.f256b;
            if (i3 <= i4) {
                bVar2.f256b = i4 + 1;
            } else {
                i5 = bVar2.d;
                if (i3 < i4 + i5) {
                    int i9 = (i4 + i5) - i3;
                    bVarA2 = this.f329a.a(4, i3 + 1, i9, bVar2.f257c);
                    bVar2.d -= i9;
                }
            }
            list.set(i2, bVar);
            if (bVar2.d > 0) {
                list.set(i, bVar2);
            } else {
                list.remove(i);
                this.f329a.a(bVar2);
            }
            if (bVarA != null) {
                list.add(i, bVarA);
            }
            if (bVarA2 != null) {
                list.add(i, bVarA2);
            }
        }
        bVar2.f256b = i7 - 1;
        bVarA = null;
        i3 = bVar.f256b;
        i4 = bVar2.f256b;
        if (i3 <= i4) {
            bVar2.f256b = i4 + 1;
        } else {
            i5 = bVar2.d;
            if (i3 < i4 + i5) {
                int i10 = (i4 + i5) - i3;
                bVarA2 = this.f329a.a(4, i3 + 1, i10, bVar2.f257c);
                bVar2.d -= i10;
            }
        }
        list.set(i2, bVar);
        if (bVar2.d > 0) {
            list.set(i, bVar2);
        } else {
            list.remove(i);
            this.f329a.a(bVar2);
        }
        if (bVarA != null) {
            list.add(i, bVarA);
        }
        if (bVarA2 != null) {
            list.add(i, bVarA2);
        }
    }
}
