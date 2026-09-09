package androidx.core.graphics.drawable;

/* JADX INFO: loaded from: classes.dex */
public class IconCompat extends androidx.versionedparcelable.CustomVersionedParcelable {
    static final android.graphics.PorterDuff.Mode j = android.graphics.PorterDuff.Mode.SRC_IN;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    java.lang.Object f171b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f170a = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public byte[] f172c = null;
    public android.os.Parcelable d = null;
    public int e = 0;
    public int f = 0;
    public android.content.res.ColorStateList g = null;
    android.graphics.PorterDuff.Mode h = j;
    public java.lang.String i = null;

    private static int a(android.graphics.drawable.Icon icon) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            return icon.getResId();
        }
        try {
            return ((java.lang.Integer) icon.getClass().getMethod("getResId", new java.lang.Class[0]).invoke(icon, new java.lang.Object[0])).intValue();
        } catch (java.lang.IllegalAccessException e) {
            android.util.Log.e("IconCompat", "Unable to get icon resource", e);
            return 0;
        } catch (java.lang.NoSuchMethodException e2) {
            android.util.Log.e("IconCompat", "Unable to get icon resource", e2);
            return 0;
        } catch (java.lang.reflect.InvocationTargetException e3) {
            android.util.Log.e("IconCompat", "Unable to get icon resource", e3);
            return 0;
        }
    }

    private static java.lang.String a(int i) {
        if (i == 1) {
            return "BITMAP";
        }
        if (i == 2) {
            return "RESOURCE";
        }
        if (i == 3) {
            return "DATA";
        }
        if (i != 4) {
            return i != 5 ? "UNKNOWN" : "BITMAP_MASKABLE";
        }
        return "URI";
    }

    private static java.lang.String b(android.graphics.drawable.Icon icon) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            return icon.getResPackage();
        }
        try {
            return (java.lang.String) icon.getClass().getMethod("getResPackage", new java.lang.Class[0]).invoke(icon, new java.lang.Object[0]);
        } catch (java.lang.IllegalAccessException e) {
            android.util.Log.e("IconCompat", "Unable to get icon package", e);
            return null;
        } catch (java.lang.NoSuchMethodException e2) {
            android.util.Log.e("IconCompat", "Unable to get icon package", e2);
            return null;
        } catch (java.lang.reflect.InvocationTargetException e3) {
            android.util.Log.e("IconCompat", "Unable to get icon package", e3);
            return null;
        }
    }

    public int a() {
        if (this.f170a == -1 && android.os.Build.VERSION.SDK_INT >= 23) {
            return a((android.graphics.drawable.Icon) this.f171b);
        }
        if (this.f170a == 2) {
            return this.e;
        }
        throw new java.lang.IllegalStateException("called getResId() on " + this);
    }

    public void a(boolean z) {
        this.i = this.h.name();
        int i = this.f170a;
        if (i != -1) {
            if (i != 1) {
                if (i == 2) {
                    this.f172c = ((java.lang.String) this.f171b).getBytes(java.nio.charset.Charset.forName("UTF-16"));
                    return;
                }
                if (i == 3) {
                    this.f172c = (byte[]) this.f171b;
                    return;
                } else if (i == 4) {
                    this.f172c = this.f171b.toString().getBytes(java.nio.charset.Charset.forName("UTF-16"));
                    return;
                } else if (i != 5) {
                    return;
                }
            }
            if (z) {
                android.graphics.Bitmap bitmap = (android.graphics.Bitmap) this.f171b;
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                this.f172c = byteArrayOutputStream.toByteArray();
                return;
            }
        } else if (z) {
            throw new java.lang.IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
        }
        this.d = (android.os.Parcelable) this.f171b;
    }

    public java.lang.String b() {
        if (this.f170a == -1 && android.os.Build.VERSION.SDK_INT >= 23) {
            return b((android.graphics.drawable.Icon) this.f171b);
        }
        if (this.f170a == 2) {
            return ((java.lang.String) this.f171b).split(":", -1)[0];
        }
        throw new java.lang.IllegalStateException("called getResPackage() on " + this);
    }

    public void c() {
        android.os.Parcelable parcelable;
        this.h = android.graphics.PorterDuff.Mode.valueOf(this.i);
        int i = this.f170a;
        if (i != -1) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        this.f171b = this.f172c;
                        return;
                    } else if (i != 4) {
                        if (i != 5) {
                            return;
                        }
                    }
                }
                this.f171b = new java.lang.String(this.f172c, java.nio.charset.Charset.forName("UTF-16"));
                return;
            }
            parcelable = this.d;
            if (parcelable == null) {
                byte[] bArr = this.f172c;
                this.f171b = bArr;
                this.f170a = 3;
                this.e = 0;
                this.f = bArr.length;
                return;
            }
        } else {
            parcelable = this.d;
            if (parcelable == null) {
                throw new java.lang.IllegalArgumentException("Invalid icon");
            }
        }
        this.f171b = parcelable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
    
        if (r1 != 5) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r4 = this;
            int r0 = r4.f170a
            r1 = -1
            if (r0 != r1) goto Lc
            java.lang.Object r0 = r4.f171b
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        Lc:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Icon(typ="
            r0.<init>(r1)
            int r1 = r4.f170a
            java.lang.String r1 = a(r1)
            r0.append(r1)
            int r1 = r4.f170a
            r2 = 1
            if (r1 == r2) goto L77
            r3 = 2
            if (r1 == r3) goto L4f
            r2 = 3
            if (r1 == r2) goto L39
            r2 = 4
            if (r1 == r2) goto L2e
            r2 = 5
            if (r1 == r2) goto L77
            goto L97
        L2e:
            java.lang.String r1 = " uri="
            r0.append(r1)
            java.lang.Object r1 = r4.f171b
            r0.append(r1)
            goto L97
        L39:
            java.lang.String r1 = " len="
            r0.append(r1)
            int r1 = r4.e
            r0.append(r1)
            int r1 = r4.f
            if (r1 == 0) goto L97
            java.lang.String r1 = " off="
            r0.append(r1)
            int r1 = r4.f
            goto L94
        L4f:
            java.lang.String r1 = " pkg="
            r0.append(r1)
            java.lang.String r1 = r4.b()
            r0.append(r1)
            java.lang.String r1 = " id="
            r0.append(r1)
            java.lang.Object[] r1 = new java.lang.Object[r2]
            r2 = 0
            int r3 = r4.a()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r2] = r3
            java.lang.String r2 = "0x%08x"
            java.lang.String r1 = java.lang.String.format(r2, r1)
            r0.append(r1)
            goto L97
        L77:
            java.lang.String r1 = " size="
            r0.append(r1)
            java.lang.Object r1 = r4.f171b
            android.graphics.Bitmap r1 = (android.graphics.Bitmap) r1
            int r1 = r1.getWidth()
            r0.append(r1)
            java.lang.String r1 = "x"
            r0.append(r1)
            java.lang.Object r1 = r4.f171b
            android.graphics.Bitmap r1 = (android.graphics.Bitmap) r1
            int r1 = r1.getHeight()
        L94:
            r0.append(r1)
        L97:
            android.content.res.ColorStateList r1 = r4.g
            if (r1 == 0) goto La5
            java.lang.String r1 = " tint="
            r0.append(r1)
            android.content.res.ColorStateList r1 = r4.g
            r0.append(r1)
        La5:
            android.graphics.PorterDuff$Mode r1 = r4.h
            android.graphics.PorterDuff$Mode r2 = androidx.core.graphics.drawable.IconCompat.j
            if (r1 == r2) goto Lb5
            java.lang.String r1 = " mode="
            r0.append(r1)
            android.graphics.PorterDuff$Mode r1 = r4.h
            r0.append(r1)
        Lb5:
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.drawable.IconCompat.toString():java.lang.String");
    }
}
