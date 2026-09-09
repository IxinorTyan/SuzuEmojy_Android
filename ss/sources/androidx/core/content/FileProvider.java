package androidx.core.content;

/* JADX INFO: loaded from: classes.dex */
public class FileProvider extends android.content.ContentProvider {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final java.lang.String[] f164b = {"_display_name", "_size"};

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private static final java.io.File f165c = new java.io.File("/");
    private static java.util.HashMap<java.lang.String, androidx.core.content.FileProvider.a> d = new java.util.HashMap<>();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private androidx.core.content.FileProvider.a f166a;

    interface a {
        java.io.File a(android.net.Uri uri);
    }

    static class b implements androidx.core.content.FileProvider.a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final java.util.HashMap<java.lang.String, java.io.File> f167a = new java.util.HashMap<>();

        b(java.lang.String str) {
        }

        @Override // androidx.core.content.FileProvider.a
        public java.io.File a(android.net.Uri uri) {
            java.lang.String encodedPath = uri.getEncodedPath();
            int iIndexOf = encodedPath.indexOf(47, 1);
            java.lang.String strDecode = android.net.Uri.decode(encodedPath.substring(1, iIndexOf));
            java.lang.String strDecode2 = android.net.Uri.decode(encodedPath.substring(iIndexOf + 1));
            java.io.File file = this.f167a.get(strDecode);
            if (file == null) {
                throw new java.lang.IllegalArgumentException("Unable to find configured root for " + uri);
            }
            java.io.File file2 = new java.io.File(file, strDecode2);
            try {
                java.io.File canonicalFile = file2.getCanonicalFile();
                if (canonicalFile.getPath().startsWith(file.getPath())) {
                    return canonicalFile;
                }
                throw new java.lang.SecurityException("Resolved path jumped beyond configured root");
            } catch (java.io.IOException unused) {
                throw new java.lang.IllegalArgumentException("Failed to resolve canonical path for " + file2);
            }
        }

        void a(java.lang.String str, java.io.File file) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("Name must not be empty");
            }
            try {
                this.f167a.put(str, file.getCanonicalFile());
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("Failed to resolve canonical path for " + file, e);
            }
        }
    }

    private static int a(java.lang.String str) {
        if ("r".equals(str)) {
            return 268435456;
        }
        if ("w".equals(str) || "wt".equals(str)) {
            return 738197504;
        }
        if ("wa".equals(str)) {
            return 704643072;
        }
        if ("rw".equals(str)) {
            return 939524096;
        }
        if ("rwt".equals(str)) {
            return 1006632960;
        }
        throw new java.lang.IllegalArgumentException("Invalid mode: " + str);
    }

    private static androidx.core.content.FileProvider.a a(android.content.Context context, java.lang.String str) {
        androidx.core.content.FileProvider.a aVarB;
        synchronized (d) {
            aVarB = d.get(str);
            if (aVarB == null) {
                try {
                    aVarB = b(context, str);
                    d.put(str, aVarB);
                } catch (java.io.IOException e) {
                    throw new java.lang.IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e);
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    throw new java.lang.IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
                }
            }
        }
        return aVarB;
    }

    private static java.io.File a(java.io.File file, java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            if (str != null) {
                file = new java.io.File(file, str);
            }
        }
        return file;
    }

    private static java.lang.Object[] a(java.lang.Object[] objArr, int i) {
        java.lang.Object[] objArr2 = new java.lang.Object[i];
        java.lang.System.arraycopy(objArr, 0, objArr2, 0, i);
        return objArr2;
    }

    private static java.lang.String[] a(java.lang.String[] strArr, int i) {
        java.lang.String[] strArr2 = new java.lang.String[i];
        java.lang.System.arraycopy(strArr, 0, strArr2, 0, i);
        return strArr2;
    }

    private static androidx.core.content.FileProvider.a b(android.content.Context context, java.lang.String str) {
        androidx.core.content.FileProvider.b bVar = new androidx.core.content.FileProvider.b(str);
        android.content.pm.ProviderInfo providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider(str, 128);
        if (providerInfoResolveContentProvider == null) {
            throw new java.lang.IllegalArgumentException("Couldn't find meta-data for provider with authority " + str);
        }
        android.content.res.XmlResourceParser xmlResourceParserLoadXmlMetaData = providerInfoResolveContentProvider.loadXmlMetaData(context.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
        if (xmlResourceParserLoadXmlMetaData == null) {
            throw new java.lang.IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        while (true) {
            int next = xmlResourceParserLoadXmlMetaData.next();
            if (next == 1) {
                return bVar;
            }
            if (next == 2) {
                java.lang.String name = xmlResourceParserLoadXmlMetaData.getName();
                java.io.File externalStorageDirectory = null;
                java.lang.String attributeValue = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, "name");
                java.lang.String attributeValue2 = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, "path");
                if ("root-path".equals(name)) {
                    externalStorageDirectory = f165c;
                } else if ("files-path".equals(name)) {
                    externalStorageDirectory = context.getFilesDir();
                } else if ("cache-path".equals(name)) {
                    externalStorageDirectory = context.getCacheDir();
                } else if ("external-path".equals(name)) {
                    externalStorageDirectory = android.os.Environment.getExternalStorageDirectory();
                } else if ("external-files-path".equals(name)) {
                    java.io.File[] fileArrB = androidx.core.content.a.b(context, null);
                    if (fileArrB.length > 0) {
                        externalStorageDirectory = fileArrB[0];
                    }
                } else if ("external-cache-path".equals(name)) {
                    java.io.File[] fileArrA = androidx.core.content.a.a(context);
                    if (fileArrA.length > 0) {
                        externalStorageDirectory = fileArrA[0];
                    }
                } else if (android.os.Build.VERSION.SDK_INT >= 21 && "external-media-path".equals(name)) {
                    java.io.File[] externalMediaDirs = context.getExternalMediaDirs();
                    if (externalMediaDirs.length > 0) {
                        externalStorageDirectory = externalMediaDirs[0];
                    }
                }
                if (externalStorageDirectory != null) {
                    bVar.a(attributeValue, a(externalStorageDirectory, attributeValue2));
                }
            }
        }
    }

    @Override // android.content.ContentProvider
    public void attachInfo(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new java.lang.SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new java.lang.SecurityException("Provider must grant uri permissions");
        }
        this.f166a = a(context, providerInfo.authority);
    }

    @Override // android.content.ContentProvider
    public int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        return this.f166a.a(uri).delete() ? 1 : 0;
    }

    @Override // android.content.ContentProvider
    public java.lang.String getType(android.net.Uri uri) {
        java.io.File fileA = this.f166a.a(uri);
        int iLastIndexOf = fileA.getName().lastIndexOf(46);
        if (iLastIndexOf < 0) {
            return "application/octet-stream";
        }
        java.lang.String mimeTypeFromExtension = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileA.getName().substring(iLastIndexOf + 1));
        return mimeTypeFromExtension != null ? mimeTypeFromExtension : "application/octet-stream";
    }

    @Override // android.content.ContentProvider
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) {
        throw new java.lang.UnsupportedOperationException("No external inserts");
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str) {
        return android.os.ParcelFileDescriptor.open(this.f166a.a(uri), a(str));
    }

    @Override // android.content.ContentProvider
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        int i;
        java.io.File fileA = this.f166a.a(uri);
        if (strArr == null) {
            strArr = f164b;
        }
        java.lang.String[] strArr3 = new java.lang.String[strArr.length];
        java.lang.Object[] objArr = new java.lang.Object[strArr.length];
        int i2 = 0;
        for (java.lang.String str3 : strArr) {
            if ("_display_name".equals(str3)) {
                strArr3[i2] = "_display_name";
                i = i2 + 1;
                objArr[i2] = fileA.getName();
            } else {
                if ("_size".equals(str3)) {
                    strArr3[i2] = "_size";
                    i = i2 + 1;
                    objArr[i2] = java.lang.Long.valueOf(fileA.length());
                }
            }
            i2 = i;
        }
        java.lang.String[] strArrA = a(strArr3, i2);
        java.lang.Object[] objArrA = a(objArr, i2);
        android.database.MatrixCursor matrixCursor = new android.database.MatrixCursor(strArrA, 1);
        matrixCursor.addRow(objArrA);
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("No external updates");
    }
}
