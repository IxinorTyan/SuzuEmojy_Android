package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
class a implements androidx.recyclerview.widget.j.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a.c.d.a<androidx.recyclerview.widget.a.b> f252a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    final java.util.ArrayList<androidx.recyclerview.widget.a.b> f253b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    final java.util.ArrayList<androidx.recyclerview.widget.a.b> f254c;
    final androidx.recyclerview.widget.a.InterfaceC0011a d;
    java.lang.Runnable e;
    final boolean f;
    final androidx.recyclerview.widget.j g;
    private int h;

    /* JADX INFO: renamed from: androidx.recyclerview.widget.a$a, reason: collision with other inner class name */
    interface InterfaceC0011a {
        androidx.recyclerview.widget.RecyclerView.d0 a(int i);

        void a(int i, int i2);

        void a(int i, int i2, java.lang.Object obj);

        void a(androidx.recyclerview.widget.a.b bVar);

        void b(int i, int i2);

        void b(androidx.recyclerview.widget.a.b bVar);

        void c(int i, int i2);

        void d(int i, int i2);
    }

    static class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f255a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f256b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        java.lang.Object f257c;
        int d;

        b(int i, int i2, int i3, java.lang.Object obj) {
            this.f255a = i;
            this.f256b = i2;
            this.d = i3;
            this.f257c = obj;
        }

        java.lang.String a() {
            int i = this.f255a;
            if (i == 1) {
                return "add";
            }
            if (i == 2) {
                return "rm";
            }
            if (i != 4) {
                return i != 8 ? "??" : "mv";
            }
            return "up";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || androidx.recyclerview.widget.a.b.class != obj.getClass()) {
                return false;
            }
            androidx.recyclerview.widget.a.b bVar = (androidx.recyclerview.widget.a.b) obj;
            int i = this.f255a;
            if (i != bVar.f255a) {
                return false;
            }
            if (i == 8 && java.lang.Math.abs(this.d - this.f256b) == 1 && this.d == bVar.f256b && this.f256b == bVar.d) {
                return true;
            }
            if (this.d != bVar.d || this.f256b != bVar.f256b) {
                return false;
            }
            java.lang.Object obj2 = this.f257c;
            java.lang.Object obj3 = bVar.f257c;
            if (obj2 != null) {
                if (!obj2.equals(obj3)) {
                    return false;
                }
            } else if (obj3 != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.f255a * 31) + this.f256b) * 31) + this.d;
        }

        public java.lang.String toString() {
            return java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + "[" + a() + ",s:" + this.f256b + "c:" + this.d + ",p:" + this.f257c + "]";
        }
    }

    a(androidx.recyclerview.widget.a.InterfaceC0011a interfaceC0011a) {
        this(interfaceC0011a, false);
    }

    a(androidx.recyclerview.widget.a.InterfaceC0011a interfaceC0011a, boolean z) {
        this.f252a = new a.c.d.b(30);
        this.f253b = new java.util.ArrayList<>();
        this.f254c = new java.util.ArrayList<>();
        this.h = 0;
        this.d = interfaceC0011a;
        this.f = z;
        this.g = new androidx.recyclerview.widget.j(this);
    }

    /* JADX WARN: Code duplicated, block: B:56:0x00a6  */
    private int b(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        for (int size = this.f254c.size() - 1; size >= 0; size--) {
            androidx.recyclerview.widget.a.b bVar = this.f254c.get(size);
            int i7 = bVar.f255a;
            if (i7 == 8) {
                int i8 = bVar.f256b;
                int i9 = bVar.d;
                if (i8 >= i9) {
                    i9 = i8;
                    i8 = i9;
                }
                if (i < i8 || i > i9) {
                    int i10 = bVar.f256b;
                    if (i < i10) {
                        if (i2 == 1) {
                            bVar.f256b = i10 + 1;
                            i4 = bVar.d + 1;
                        } else if (i2 == 2) {
                            bVar.f256b = i10 - 1;
                            i4 = bVar.d - 1;
                        }
                        bVar.d = i4;
                    }
                } else {
                    int i11 = bVar.f256b;
                    if (i8 == i11) {
                        if (i2 == 1) {
                            i6 = bVar.d + 1;
                        } else {
                            if (i2 == 2) {
                                i6 = bVar.d - 1;
                            }
                            i++;
                        }
                        bVar.d = i6;
                        i++;
                    } else {
                        if (i2 == 1) {
                            i5 = i11 + 1;
                        } else {
                            if (i2 == 2) {
                                i5 = i11 - 1;
                            }
                            i--;
                        }
                        bVar.f256b = i5;
                        i--;
                    }
                }
            } else {
                int i12 = bVar.f256b;
                if (i12 > i) {
                    if (i2 == 1) {
                        i3 = i12 + 1;
                    } else if (i2 == 2) {
                        i3 = i12 - 1;
                    }
                    bVar.f256b = i3;
                } else if (i7 == 1) {
                    i -= bVar.d;
                } else if (i7 == 2) {
                    i += bVar.d;
                }
            }
        }
        for (int size2 = this.f254c.size() - 1; size2 >= 0; size2--) {
            androidx.recyclerview.widget.a.b bVar2 = this.f254c.get(size2);
            if (bVar2.f255a == 8) {
                int i13 = bVar2.d;
                if (i13 == bVar2.f256b || i13 < 0) {
                    this.f254c.remove(size2);
                    a(bVar2);
                }
            } else if (bVar2.d <= 0) {
                this.f254c.remove(size2);
                a(bVar2);
            }
        }
        return i;
    }

    private void b(androidx.recyclerview.widget.a.b bVar) {
        g(bVar);
    }

    private void c(androidx.recyclerview.widget.a.b bVar) {
        g(bVar);
    }

    private void d(androidx.recyclerview.widget.a.b bVar) {
        boolean z;
        byte b2;
        int i = bVar.f256b;
        int i2 = bVar.d + i;
        byte b3 = -1;
        int i3 = i;
        int i4 = 0;
        while (i3 < i2) {
            if (this.d.a(i3) != null || d(i3)) {
                if (b3 == 0) {
                    f(a(2, i, i4, null));
                    z = true;
                } else {
                    z = false;
                }
                b2 = 1;
            } else {
                if (b3 == 1) {
                    g(a(2, i, i4, null));
                    z = true;
                } else {
                    z = false;
                }
                b2 = 0;
            }
            if (z) {
                i3 -= i4;
                i2 -= i4;
                i4 = 1;
            } else {
                i4++;
            }
            i3++;
            b3 = b2;
        }
        if (i4 != bVar.d) {
            a(bVar);
            bVar = a(2, i, i4, null);
        }
        if (b3 == 0) {
            f(bVar);
        } else {
            g(bVar);
        }
    }

    private boolean d(int i) {
        int size = this.f254c.size();
        for (int i2 = 0; i2 < size; i2++) {
            androidx.recyclerview.widget.a.b bVar = this.f254c.get(i2);
            int i3 = bVar.f255a;
            if (i3 == 8) {
                if (a(bVar.d, i2 + 1) == i) {
                    return true;
                }
            } else if (i3 == 1) {
                int i4 = bVar.f256b;
                int i5 = bVar.d + i4;
                while (i4 < i5) {
                    if (a(i4, i2 + 1) == i) {
                        return true;
                    }
                    i4++;
                }
            } else {
                continue;
            }
        }
        return false;
    }

    private void e(androidx.recyclerview.widget.a.b bVar) {
        int i = bVar.f256b;
        int i2 = bVar.d + i;
        int i3 = i;
        byte b2 = -1;
        int i4 = 0;
        while (i < i2) {
            if (this.d.a(i) != null || d(i)) {
                if (b2 == 0) {
                    f(a(4, i3, i4, bVar.f257c));
                    i3 = i;
                    i4 = 0;
                }
                b2 = 1;
            } else {
                if (b2 == 1) {
                    g(a(4, i3, i4, bVar.f257c));
                    i3 = i;
                    i4 = 0;
                }
                b2 = 0;
            }
            i4++;
            i++;
        }
        if (i4 != bVar.d) {
            java.lang.Object obj = bVar.f257c;
            a(bVar);
            bVar = a(4, i3, i4, obj);
        }
        if (b2 == 0) {
            f(bVar);
        } else {
            g(bVar);
        }
    }

    private void f(androidx.recyclerview.widget.a.b bVar) {
        int i;
        int i2 = bVar.f255a;
        if (i2 == 1 || i2 == 8) {
            throw new java.lang.IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int iB = b(bVar.f256b, i2);
        int i3 = bVar.f256b;
        int i4 = bVar.f255a;
        if (i4 == 2) {
            i = 0;
        } else {
            if (i4 != 4) {
                throw new java.lang.IllegalArgumentException("op should be remove or update." + bVar);
            }
            i = 1;
        }
        int i5 = 1;
        for (int i6 = 1; i6 < bVar.d; i6++) {
            int iB2 = b(bVar.f256b + (i * i6), bVar.f255a);
            int i7 = bVar.f255a;
            if (i7 == 2 ? iB2 == iB : i7 == 4 && iB2 == iB + 1) {
                i5++;
            } else {
                androidx.recyclerview.widget.a.b bVarA = a(bVar.f255a, iB, i5, bVar.f257c);
                a(bVarA, i3);
                a(bVarA);
                if (bVar.f255a == 4) {
                    i3 += i5;
                }
                iB = iB2;
                i5 = 1;
            }
        }
        java.lang.Object obj = bVar.f257c;
        a(bVar);
        if (i5 > 0) {
            androidx.recyclerview.widget.a.b bVarA2 = a(bVar.f255a, iB, i5, obj);
            a(bVarA2, i3);
            a(bVarA2);
        }
    }

    private void g(androidx.recyclerview.widget.a.b bVar) {
        this.f254c.add(bVar);
        int i = bVar.f255a;
        if (i == 1) {
            this.d.a(bVar.f256b, bVar.d);
            return;
        }
        if (i == 2) {
            this.d.d(bVar.f256b, bVar.d);
            return;
        }
        if (i == 4) {
            this.d.a(bVar.f256b, bVar.d, bVar.f257c);
        } else {
            if (i == 8) {
                this.d.b(bVar.f256b, bVar.d);
                return;
            }
            throw new java.lang.IllegalArgumentException("Unknown update op type for " + bVar);
        }
    }

    public int a(int i) {
        int size = this.f253b.size();
        for (int i2 = 0; i2 < size; i2++) {
            androidx.recyclerview.widget.a.b bVar = this.f253b.get(i2);
            int i3 = bVar.f255a;
            if (i3 != 1) {
                if (i3 == 2) {
                    int i4 = bVar.f256b;
                    if (i4 <= i) {
                        int i5 = bVar.d;
                        if (i4 + i5 > i) {
                            return -1;
                        }
                        i -= i5;
                    } else {
                        continue;
                    }
                } else if (i3 == 8) {
                    int i6 = bVar.f256b;
                    if (i6 == i) {
                        i = bVar.d;
                    } else {
                        if (i6 < i) {
                            i--;
                        }
                        if (bVar.d <= i) {
                            i++;
                        }
                    }
                }
            } else if (bVar.f256b <= i) {
                i += bVar.d;
            }
        }
        return i;
    }

    int a(int i, int i2) {
        int size = this.f254c.size();
        while (i2 < size) {
            androidx.recyclerview.widget.a.b bVar = this.f254c.get(i2);
            int i3 = bVar.f255a;
            if (i3 == 8) {
                int i4 = bVar.f256b;
                if (i4 == i) {
                    i = bVar.d;
                } else {
                    if (i4 < i) {
                        i--;
                    }
                    if (bVar.d <= i) {
                        i++;
                    }
                }
            } else {
                int i5 = bVar.f256b;
                if (i5 > i) {
                    continue;
                } else if (i3 == 2) {
                    int i6 = bVar.d;
                    if (i < i5 + i6) {
                        return -1;
                    }
                    i -= i6;
                } else if (i3 == 1) {
                    i += bVar.d;
                }
            }
            i2++;
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.j.a
    public androidx.recyclerview.widget.a.b a(int i, int i2, int i3, java.lang.Object obj) {
        androidx.recyclerview.widget.a.b bVarA = this.f252a.a();
        if (bVarA == null) {
            return new androidx.recyclerview.widget.a.b(i, i2, i3, obj);
        }
        bVarA.f255a = i;
        bVarA.f256b = i2;
        bVarA.d = i3;
        bVarA.f257c = obj;
        return bVarA;
    }

    void a() {
        int size = this.f254c.size();
        for (int i = 0; i < size; i++) {
            this.d.a(this.f254c.get(i));
        }
        a(this.f254c);
        this.h = 0;
    }

    @Override // androidx.recyclerview.widget.j.a
    public void a(androidx.recyclerview.widget.a.b bVar) {
        if (this.f) {
            return;
        }
        bVar.f257c = null;
        this.f252a.a(bVar);
    }

    void a(androidx.recyclerview.widget.a.b bVar, int i) {
        this.d.b(bVar);
        int i2 = bVar.f255a;
        if (i2 == 2) {
            this.d.c(i, bVar.d);
        } else {
            if (i2 != 4) {
                throw new java.lang.IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            }
            this.d.a(i, bVar.d, bVar.f257c);
        }
    }

    void a(java.util.List<androidx.recyclerview.widget.a.b> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            a(list.get(i));
        }
        list.clear();
    }

    boolean a(int i, int i2, int i3) {
        if (i == i2) {
            return false;
        }
        if (i3 != 1) {
            throw new java.lang.IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.f253b.add(a(8, i, i2, null));
        this.h |= 8;
        return this.f253b.size() == 1;
    }

    int b(int i) {
        return a(i, 0);
    }

    void b() {
        a();
        int size = this.f253b.size();
        for (int i = 0; i < size; i++) {
            androidx.recyclerview.widget.a.b bVar = this.f253b.get(i);
            int i2 = bVar.f255a;
            if (i2 == 1) {
                this.d.a(bVar);
                this.d.a(bVar.f256b, bVar.d);
            } else if (i2 == 2) {
                this.d.a(bVar);
                this.d.c(bVar.f256b, bVar.d);
            } else if (i2 == 4) {
                this.d.a(bVar);
                this.d.a(bVar.f256b, bVar.d, bVar.f257c);
            } else if (i2 == 8) {
                this.d.a(bVar);
                this.d.b(bVar.f256b, bVar.d);
            }
            java.lang.Runnable runnable = this.e;
            if (runnable != null) {
                runnable.run();
            }
        }
        a(this.f253b);
        this.h = 0;
    }

    boolean c() {
        return this.f253b.size() > 0;
    }

    boolean c(int i) {
        return (i & this.h) != 0;
    }

    boolean d() {
        return (this.f254c.isEmpty() || this.f253b.isEmpty()) ? false : true;
    }

    void e() {
        this.g.a(this.f253b);
        int size = this.f253b.size();
        for (int i = 0; i < size; i++) {
            androidx.recyclerview.widget.a.b bVar = this.f253b.get(i);
            int i2 = bVar.f255a;
            if (i2 == 1) {
                b(bVar);
            } else if (i2 == 2) {
                d(bVar);
            } else if (i2 == 4) {
                e(bVar);
            } else if (i2 == 8) {
                c(bVar);
            }
            java.lang.Runnable runnable = this.e;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.f253b.clear();
    }

    void f() {
        a(this.f253b);
        a(this.f254c);
        this.h = 0;
    }
}
