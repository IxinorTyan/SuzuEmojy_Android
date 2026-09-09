package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private java.util.HashMap<java.lang.String, java.lang.String> a(android.content.Context context, boolean z) {
        java.io.File[] fileArrListFiles;
        java.util.HashMap<java.lang.String, java.lang.String> map = null;
        java.io.File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null && (fileArrListFiles = externalFilesDir.listFiles()) != null && fileArrListFiles.length > 0) {
            me.hisn.mypanel.c cVar = new me.hisn.mypanel.c();
            for (java.io.File file : fileArrListFiles) {
                if (file.getName().contains("png")) {
                    java.lang.String strA = cVar.a(android.graphics.BitmapFactory.decodeFile(file.getPath()));
                    if (map == null) {
                        map = new java.util.HashMap<>();
                    }
                    map.put(file.getName(), strA);
                }
            }
        }
        return map;
    }

    private boolean a(android.content.Context context, java.util.HashMap<java.lang.String, java.lang.String> map) {
        if (map == null || map.size() <= 0) {
            return false;
        }
        java.io.File externalFilesDir = context.getExternalFilesDir(null);
        me.hisn.mypanel.c cVar = new me.hisn.mypanel.c();
        for (java.lang.String str : map.keySet()) {
            cVar.a(cVar.a(map.get(str)), externalFilesDir + "/" + str, 0, false);
        }
        return true;
    }

    private boolean a(android.content.Context context, java.util.HashMap map, java.lang.String str) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        int size = map.size();
        java.lang.Object[] objArr = new java.lang.Object[size];
        if (size <= 0) {
            return false;
        }
        map.keySet().toArray(objArr);
        android.content.SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        for (int i = 0; i < size; i++) {
            java.lang.Object obj = objArr[i];
            java.lang.Object obj2 = map.get(obj);
            if (obj2 != null) {
                if (java.lang.Boolean.class.getName().equals(obj2.getClass().getName())) {
                    editorEdit.putBoolean((java.lang.String) obj, ((java.lang.Boolean) obj2).booleanValue());
                } else if (java.lang.String.class.getName().equals(obj2.getClass().getName())) {
                    editorEdit.putString((java.lang.String) obj, (java.lang.String) obj2);
                } else if (java.lang.Integer.class.getName().equals(obj2.getClass().getName())) {
                    editorEdit.putInt((java.lang.String) obj, ((java.lang.Integer) obj2).intValue());
                }
            }
        }
        editorEdit.apply();
        return true;
    }

    public boolean a(android.content.Context context, android.net.Uri uri) {
        boolean z;
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences("my_gesture", 0);
        android.content.SharedPreferences sharedPreferences2 = context.getSharedPreferences("my_panel", 0);
        java.util.Map<java.lang.String, ?> all = sharedPreferences.getAll();
        java.util.Map<java.lang.String, ?> all2 = sharedPreferences2.getAll();
        android.os.Bundle bundle = new android.os.Bundle();
        if (all == null || all.size() <= 0) {
            z = false;
        } else {
            java.util.HashMap map = new java.util.HashMap(all);
            map.remove("si");
            bundle.putSerializable("my_gesture", map);
            z = true;
        }
        if (all2 != null && all2.size() > 0) {
            bundle.putSerializable("my_panel", new java.util.HashMap(all2));
            z = true;
        }
        java.util.HashMap<java.lang.String, java.lang.String> mapA = a(context, true);
        if (mapA != null) {
            bundle.putSerializable("icons", mapA);
            z = true;
        }
        if (!z) {
            return false;
        }
        android.os.Parcel parcelObtain = android.os.Parcel.obtain();
        bundle.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        java.lang.String strEncodeToString = android.util.Base64.encodeToString(bArrMarshall, 0, bArrMarshall.length, 0);
        try {
            java.io.OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uri);
            if (outputStreamOpenOutputStream == null) {
                return false;
            }
            outputStreamOpenOutputStream.write(strEncodeToString.getBytes());
            outputStreamOpenOutputStream.flush();
            outputStreamOpenOutputStream.close();
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean b(android.content.Context context, android.net.Uri uri) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(context.getContentResolver().openInputStream(uri)));
            while (true) {
                java.lang.String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            bufferedReader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        boolean zA = false;
        if (sb.length() <= 0) {
            return false;
        }
        try {
            byte[] bArrDecode = android.util.Base64.decode(sb.toString(), 0);
            android.os.Bundle bundle = new android.os.Bundle();
            android.os.Parcel parcelObtain = android.os.Parcel.obtain();
            parcelObtain.unmarshall(bArrDecode, 0, bArrDecode.length);
            parcelObtain.setDataPosition(0);
            bundle.readFromParcel(parcelObtain);
            java.util.HashMap map = (java.util.HashMap) bundle.get("my_gesture");
            java.util.HashMap map2 = (java.util.HashMap) bundle.get("my_panel");
            java.util.HashMap<java.lang.String, java.lang.String> map3 = (java.util.HashMap) bundle.get("icons");
            if (map3 != null) {
                a(context, map3);
            }
            if (map != null && map.size() > 0) {
                zA = a(context, map, "my_gesture");
            }
            return (map2 == null || map2.size() <= 0) ? zA : a(context, map2, "my_panel");
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
            return zA;
        }
    }
}
