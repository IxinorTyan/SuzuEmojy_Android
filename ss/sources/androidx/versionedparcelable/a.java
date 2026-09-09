package androidx.versionedparcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected final a.a.a<java.lang.String, java.lang.reflect.Method> f344a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    protected final a.a.a<java.lang.String, java.lang.reflect.Method> f345b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    protected final a.a.a<java.lang.String, java.lang.Class> f346c;

    public a(a.a.a<java.lang.String, java.lang.reflect.Method> aVar, a.a.a<java.lang.String, java.lang.reflect.Method> aVar2, a.a.a<java.lang.String, java.lang.Class> aVar3) {
        this.f344a = aVar;
        this.f345b = aVar2;
        this.f346c = aVar3;
    }

    private java.lang.Class a(java.lang.Class<? extends androidx.versionedparcelable.c> cls) throws java.lang.ClassNotFoundException {
        java.lang.Class cls2 = this.f346c.get(cls.getName());
        if (cls2 != null) {
            return cls2;
        }
        java.lang.Class<?> cls3 = java.lang.Class.forName(java.lang.String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
        this.f346c.put(cls.getName(), cls3);
        return cls3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private java.lang.reflect.Method b(java.lang.Class cls) throws java.lang.NoSuchMethodException, java.lang.ClassNotFoundException {
        java.lang.reflect.Method method = this.f345b.get(cls.getName());
        if (method != null) {
            return method;
        }
        java.lang.Class clsA = a((java.lang.Class<? extends androidx.versionedparcelable.c>) cls);
        java.lang.System.currentTimeMillis();
        java.lang.reflect.Method declaredMethod = clsA.getDeclaredMethod("write", cls, androidx.versionedparcelable.a.class);
        this.f345b.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    private java.lang.reflect.Method b(java.lang.String str) throws java.lang.NoSuchMethodException {
        java.lang.reflect.Method method = this.f344a.get(str);
        if (method != null) {
            return method;
        }
        java.lang.System.currentTimeMillis();
        java.lang.reflect.Method declaredMethod = java.lang.Class.forName(str, true, androidx.versionedparcelable.a.class.getClassLoader()).getDeclaredMethod("read", androidx.versionedparcelable.a.class);
        this.f344a.put(str, declaredMethod);
        return declaredMethod;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void b(androidx.versionedparcelable.c cVar) {
        try {
            a(a((java.lang.Class<? extends androidx.versionedparcelable.c>) cVar.getClass()).getName());
        } catch (java.lang.ClassNotFoundException e) {
            throw new java.lang.RuntimeException(cVar.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    public int a(int i, int i2) {
        return !a(i2) ? i : g();
    }

    public <T extends android.os.Parcelable> T a(T t, int i) {
        return !a(i) ? t : (T) h();
    }

    public <T extends androidx.versionedparcelable.c> T a(T t, int i) {
        return !a(i) ? t : (T) j();
    }

    protected <T extends androidx.versionedparcelable.c> T a(java.lang.String str, androidx.versionedparcelable.a aVar) {
        try {
            return (T) b(str).invoke(null, aVar);
        } catch (java.lang.ClassNotFoundException e) {
            throw new java.lang.RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
        } catch (java.lang.IllegalAccessException e2) {
            throw new java.lang.RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (java.lang.NoSuchMethodException e3) {
            throw new java.lang.RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (java.lang.reflect.InvocationTargetException e4) {
            if (e4.getCause() instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) e4.getCause());
            }
            throw new java.lang.RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
        }
    }

    public java.lang.CharSequence a(java.lang.CharSequence charSequence, int i) {
        return !a(i) ? charSequence : f();
    }

    public java.lang.String a(java.lang.String str, int i) {
        return !a(i) ? str : i();
    }

    protected abstract void a();

    protected abstract void a(android.os.Parcelable parcelable);

    protected void a(androidx.versionedparcelable.c cVar) {
        if (cVar == null) {
            a((java.lang.String) null);
            return;
        }
        b(cVar);
        androidx.versionedparcelable.a aVarB = b();
        a(cVar, aVarB);
        aVarB.a();
    }

    protected <T extends androidx.versionedparcelable.c> void a(T t, androidx.versionedparcelable.a aVar) {
        try {
            b(t.getClass()).invoke(null, t, aVar);
        } catch (java.lang.ClassNotFoundException e) {
            throw new java.lang.RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
        } catch (java.lang.IllegalAccessException e2) {
            throw new java.lang.RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (java.lang.NoSuchMethodException e3) {
            throw new java.lang.RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (java.lang.reflect.InvocationTargetException e4) {
            if (!(e4.getCause() instanceof java.lang.RuntimeException)) {
                throw new java.lang.RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
            }
            throw ((java.lang.RuntimeException) e4.getCause());
        }
    }

    protected abstract void a(java.lang.CharSequence charSequence);

    protected abstract void a(java.lang.String str);

    protected abstract void a(boolean z);

    public void a(boolean z, boolean z2) {
    }

    protected abstract void a(byte[] bArr);

    protected abstract boolean a(int i);

    public boolean a(boolean z, int i) {
        return !a(i) ? z : d();
    }

    public byte[] a(byte[] bArr, int i) {
        return !a(i) ? bArr : e();
    }

    protected abstract androidx.versionedparcelable.a b();

    protected abstract void b(int i);

    public void b(int i, int i2) {
        b(i2);
        c(i);
    }

    public void b(android.os.Parcelable parcelable, int i) {
        b(i);
        a(parcelable);
    }

    public void b(androidx.versionedparcelable.c cVar, int i) {
        b(i);
        a(cVar);
    }

    public void b(java.lang.CharSequence charSequence, int i) {
        b(i);
        a(charSequence);
    }

    public void b(java.lang.String str, int i) {
        b(i);
        a(str);
    }

    public void b(boolean z, int i) {
        b(i);
        a(z);
    }

    public void b(byte[] bArr, int i) {
        b(i);
        a(bArr);
    }

    protected abstract void c(int i);

    public boolean c() {
        return false;
    }

    protected abstract boolean d();

    protected abstract byte[] e();

    protected abstract java.lang.CharSequence f();

    protected abstract int g();

    protected abstract <T extends android.os.Parcelable> T h();

    protected abstract java.lang.String i();

    protected <T extends androidx.versionedparcelable.c> T j() {
        java.lang.String strI = i();
        if (strI == null) {
            return null;
        }
        return (T) a(strI, b());
    }
}
