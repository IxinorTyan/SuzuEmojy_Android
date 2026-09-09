package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class p0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.util.List<java.lang.String> f814a = new java.util.ArrayList();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.util.List<java.lang.String> f815b = new java.util.ArrayList();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private java.lang.Runnable f816c = null;

    class a implements android.view.View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ java.lang.String f817a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ android.view.View f818b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.widget.TextView f819c;
        final /* synthetic */ android.app.Activity d;
        final /* synthetic */ android.widget.TextView e;
        final /* synthetic */ android.view.WindowManager f;

        a(java.lang.String str, android.view.View view, android.widget.TextView textView, android.app.Activity activity, android.widget.TextView textView2, android.view.WindowManager windowManager) {
            this.f817a = str;
            this.f818b = view;
            this.f819c = textView;
            this.d = activity;
            this.e = textView2;
            this.f = windowManager;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.view.WindowManager windowManager;
            int id = view.getId();
            if (id == me.hisn.mygesture.R.id.exit_btn) {
                me.hisn.utils.p0.this.c(view.getContext());
                windowManager = this.f;
                if (windowManager == null) {
                    return;
                }
            } else {
                if (id == me.hisn.mygesture.R.id.fetch_btn) {
                    java.util.HashMap mapA = me.hisn.utils.p0.this.a(view.getContext().getApplicationContext(), this.f817a);
                    if (mapA == null || mapA.size() <= 0) {
                        return;
                    }
                    android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) this.f818b.findViewById(me.hisn.mygesture.R.id.fetch_view_layout);
                    for (java.lang.String str : mapA.keySet()) {
                        android.widget.TextView textView = new android.widget.TextView(view.getContext());
                        textView.setTextColor(-1);
                        textView.setBackgroundResource(me.hisn.mygesture.R.drawable.item_trans_selector);
                        textView.setPadding(50, 50, 50, 50);
                        java.lang.String str2 = (java.lang.String) mapA.get(str);
                        textView.setText(java.lang.String.format("%s：%s", str, str2));
                        textView.setOnClickListener(this);
                        textView.setTag(str2);
                        linearLayout.addView(textView);
                    }
                    me.hisn.mygesture.MAS.b(1);
                    this.f819c.setText(this.d.getString(me.hisn.mygesture.R.string.fetch_imei_tips2));
                    this.e.setVisibility(8);
                    return;
                }
                if (view.getTag() == null) {
                    return;
                }
                me.hisn.mygesture.P.s.edit().putString("si", me.hisn.utils.C.GES(view.getTag().toString() + "&" + android.os.Build.TIME, 1)).apply();
                me.hisn.utils.p0.this.c(view.getContext());
                windowManager = this.f;
                if (windowManager == null) {
                    return;
                }
            }
            windowManager.removeView(this.f818b);
        }
    }

    class b implements android.view.View.OnTouchListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f820a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ java.lang.String f821b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.View.OnClickListener f822c;
        final /* synthetic */ android.content.Intent d;

        class a implements java.lang.Runnable {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            final /* synthetic */ android.view.accessibility.AccessibilityNodeInfo f823a;

            a(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
                this.f823a = accessibilityNodeInfo;
            }

            @Override // java.lang.Runnable
            public void run() {
                me.hisn.utils.p0.b bVar = me.hisn.utils.p0.b.this;
                me.hisn.utils.p0.this.a(this.f823a, bVar.f820a, bVar.f821b, bVar.f822c, bVar.d);
            }
        }

        b(android.view.View view, java.lang.String str, android.view.View.OnClickListener onClickListener, android.content.Intent intent) {
            this.f820a = view;
            this.f821b = str;
            this.f822c = onClickListener;
            this.d = intent;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfoF;
            if (motionEvent.getAction() != 4 || (accessibilityNodeInfoF = me.hisn.mygesture.MAS.f()) == null) {
                return false;
            }
            new java.lang.Thread(new me.hisn.utils.p0.b.a(accessibilityNodeInfoF)).start();
            return false;
        }
    }

    class c implements java.lang.Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ android.view.View f825a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ java.lang.String f826b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        final /* synthetic */ android.view.View.OnClickListener f827c;

        c(android.view.View view, java.lang.String str, android.view.View.OnClickListener onClickListener) {
            this.f825a = view;
            this.f826b = str;
            this.f827c = onClickListener;
        }

        @Override // java.lang.Runnable
        public void run() {
            java.util.HashMap mapA = me.hisn.utils.p0.this.a(this.f825a.getContext().getApplicationContext(), this.f826b);
            if (mapA == null || mapA.size() <= 0) {
                return;
            }
            android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) this.f825a.findViewById(me.hisn.mygesture.R.id.fetch_view_layout);
            for (java.lang.String str : mapA.keySet()) {
                android.widget.TextView textView = new android.widget.TextView(this.f825a.getContext());
                textView.setTextColor(-1);
                textView.setBackgroundResource(me.hisn.mygesture.R.drawable.item_trans_selector);
                textView.setPadding(50, 50, 50, 50);
                java.lang.String str2 = (java.lang.String) mapA.get(str);
                textView.setText(java.lang.String.format("%s：%s", str, str2));
                textView.setOnClickListener(this.f827c);
                textView.setTag(str2);
                linearLayout.addView(textView);
            }
            me.hisn.mygesture.MAS.b(1);
            ((android.widget.TextView) this.f825a.findViewById(me.hisn.mygesture.R.id.fetch_tips)).setText(this.f825a.getContext().getString(me.hisn.mygesture.R.string.fetch_imei_tips2));
            this.f825a.setOnTouchListener(null);
        }
    }

    private java.lang.String a(java.util.List<java.lang.String> list) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("\n");
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public java.util.HashMap<java.lang.String, java.lang.String> a(android.content.Context context, java.lang.String str) {
        java.lang.String strB;
        java.lang.String str2;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfoF = me.hisn.mygesture.MAS.f();
        if (accessibilityNodeInfoF == null) {
            strB = b("nodeInfo null");
            str2 = "79";
        } else {
            this.f814a.clear();
            java.lang.String str3 = ((java.lang.Object) accessibilityNodeInfoF.getPackageName()) + "";
            if (b(context, str3)) {
                a(accessibilityNodeInfoF);
                java.lang.String strA = a(this.f814a);
                if (this.f814a.size() < 3) {
                    strB = b(strA);
                    str2 = "83";
                } else {
                    java.lang.String str4 = this.f814a.get(0);
                    if (java.util.regex.Pattern.compile("IMEI|MEID|识别码|身份码|Emode|id|^Device|^设备", 2).matcher(str4).find()) {
                        java.util.HashSet hashSet = new java.util.HashSet();
                        hashSet.add(2524);
                        hashSet.add(988430);
                        hashSet.add(30661432);
                        hashSet.add(java.lang.Integer.valueOf(context.getString(android.R.string.ok).hashCode()));
                        java.util.List<java.lang.String> list = this.f814a;
                        java.lang.String str5 = list.get(list.size() - 1);
                        java.util.List<java.lang.String> list2 = this.f814a;
                        java.lang.String str6 = list2.get(list2.size() - 2);
                        if (hashSet.contains(java.lang.Integer.valueOf(str5.hashCode())) || hashSet.contains(java.lang.Integer.valueOf(str6.hashCode()))) {
                            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("编辑|EDIT|删除|DELETE|复制|COPY|拨打|CALL|发送|SEND|加入|添加|联系人|contact", 2).matcher(strA);
                            if (!matcher.find()) {
                                java.util.ArrayList arrayList = new java.util.ArrayList();
                                for (int i = 1; i < this.f814a.size() - 1; i++) {
                                    java.lang.String strA2 = a(this.f814a.get(i));
                                    if (strA2 != null) {
                                        arrayList.add(strA2);
                                    }
                                }
                                java.util.HashMap<java.lang.String, java.lang.String> map = new java.util.HashMap<>();
                                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                    map.put("ID" + i2, arrayList.get(i2));
                                }
                                if (map.size() == 0) {
                                    a("87", context, b(strA), false);
                                }
                                return map;
                            }
                            strB = b(strA + ":" + matcher.group());
                            str2 = "84";
                        } else {
                            str2 = "86," + str5;
                        }
                    } else {
                        str2 = "85:" + str4;
                    }
                    strB = b(strA);
                }
            } else {
                strB = b(android.os.Build.BRAND + "," + android.os.Build.VERSION.SDK_INT + "," + str3);
                str2 = "81";
            }
        }
        a(str2, context, strB, false);
        return null;
    }

    private void a(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.getChildCount() != 0) {
            for (int i = 0; i < accessibilityNodeInfo.getChildCount(); i++) {
                if (accessibilityNodeInfo.getChild(i) != null) {
                    a(accessibilityNodeInfo.getChild(i));
                }
            }
            return;
        }
        if (accessibilityNodeInfo.getText() == null || accessibilityNodeInfo.isEditable()) {
            return;
        }
        java.lang.String string = accessibilityNodeInfo.getText().toString();
        if (!string.contains("\n")) {
            this.f814a.add(string);
        } else {
            this.f814a.addAll(java.util.Arrays.asList(string.split("\n")));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.View view, java.lang.String str, android.view.View.OnClickListener onClickListener, android.content.Intent intent) {
        java.lang.String str2 = ((java.lang.Object) accessibilityNodeInfo.getPackageName()) + "";
        if (b(view.getContext(), str2)) {
            this.f815b.clear();
            b(accessibilityNodeInfo);
            if (d(view.getContext())) {
                if (this.f816c == null) {
                    this.f816c = new me.hisn.utils.p0.c(view, str, onClickListener);
                } else {
                    view.removeCallbacks(this.f816c);
                }
                view.postDelayed(this.f816c, 1000L);
                return;
            }
            return;
        }
        a("81", view.getContext().getApplicationContext(), b("touching pkg:" + str2 + "  dialer:" + str), false);
        try {
            view.getContext().startActivity(intent);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private void a(java.lang.String str, android.content.Context context, java.lang.String str2, boolean z) {
        java.lang.String string;
        if (z) {
            new me.hisn.utils.b0().a(context.getApplicationContext(), "请按顶上提示条操作读取" + str, 0);
        }
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences("31410", 0);
        java.util.ArrayList arrayList = null;
        try {
            string = sharedPreferences.getString("31419", null);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            string = null;
        }
        try {
            arrayList = (java.util.ArrayList) me.hisn.utils.e0.a(string);
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
        if (arrayList == null) {
            arrayList = new java.util.ArrayList();
        }
        while (arrayList.size() >= 30) {
            arrayList.remove(0);
        }
        arrayList.add(new java.text.SimpleDateFormat("MM-dd-hh-mm-ss", java.util.Locale.getDefault()).format(new java.util.Date()) + "_error:" + str + "\n" + str2);
        java.lang.String strA = me.hisn.utils.e0.a(arrayList);
        if (strA != null) {
            sharedPreferences.edit().putString("31419", strA).apply();
        }
    }

    private java.lang.String b(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                sb.append((char) (str.charAt(i) + 256));
            }
        }
        return sb.toString();
    }

    private void b(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return;
        }
        if (accessibilityNodeInfo.getChildCount() != 0) {
            for (int i = 0; i < accessibilityNodeInfo.getChildCount(); i++) {
                if (accessibilityNodeInfo.getChild(i) != null) {
                    b(accessibilityNodeInfo.getChild(i));
                }
            }
            return;
        }
        if (accessibilityNodeInfo.isEditable()) {
            try {
                accessibilityNodeInfo.refresh();
                accessibilityNodeInfo.performAction(1);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            if (accessibilityNodeInfo.getText() != null) {
                this.f815b.add(accessibilityNodeInfo.getText().toString().replace(" ", ""));
            }
        }
    }

    private boolean b(android.content.Context context, java.lang.String str) {
        boolean z;
        try {
            z = (context.getPackageManager().getApplicationInfo(str, 128).flags & 1) > 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        java.util.Iterator<android.content.pm.ResolveInfo> it = context.getPackageManager().queryIntentActivities(intent, 128).iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().activityInfo.packageName)) {
                return false;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent(context, (java.lang.Class<?>) me.hisn.mygesture.j.class);
        intent.addFlags(805306368);
        try {
            context.startActivity(intent);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private boolean c(java.lang.String str) {
        return a(str) != null;
    }

    private boolean d(android.content.Context context) {
        if (this.f815b.size() == 0) {
            a("80", context.getApplicationContext(), b("dialing:None"), false);
            return false;
        }
        java.util.Iterator<java.lang.String> it = this.f815b.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            int iIndexOf = next.indexOf(",");
            if (iIndexOf > 0) {
                next = next.substring(0, iIndexOf);
            }
            if (next.hashCode() == 1286399 || next.hashCode() == 1868778778 || next.hashCode() == 13109886 || next.hashCode() == 794913537) {
                return true;
            }
            a("80", context.getApplicationContext(), b("dialing:" + next), false);
        }
        return false;
    }

    public android.view.View a(android.app.Activity activity, int i) {
        java.lang.String str;
        if (me.hisn.mygesture.MAS.l()) {
            android.content.Intent intent = new android.content.Intent("android.intent.action.DIAL");
            intent.addFlags(268468224);
            java.util.Iterator<android.content.pm.ResolveInfo> it = activity.getApplicationContext().getPackageManager().queryIntentActivities(intent, 128).iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = null;
                    break;
                }
                android.content.pm.ActivityInfo activityInfo = it.next().activityInfo;
                if ((activityInfo.applicationInfo.flags & 1) > 0) {
                    java.lang.String str2 = activityInfo.packageName;
                    intent.setPackage(str2);
                    str = str2;
                    break;
                }
            }
            try {
                activity.startActivity(intent);
                new me.hisn.utils.b0().a(activity.getApplicationContext(), "请按顶上提示条操作读取", 0);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            android.view.WindowManager windowManagerK = me.hisn.mygesture.MAS.k();
            android.view.View viewInflate = android.view.View.inflate(activity.getApplicationContext(), me.hisn.mygesture.R.layout.fetch_view, null);
            android.widget.TextView textView = (android.widget.TextView) viewInflate.findViewById(me.hisn.mygesture.R.id.fetch_tips);
            textView.setText(me.hisn.mygesture.R.string.dialer_enter_tips_simple);
            android.widget.TextView textView2 = (android.widget.TextView) viewInflate.findViewById(me.hisn.mygesture.R.id.fetch_btn);
            textView2.setVisibility(8);
            android.widget.TextView textView3 = (android.widget.TextView) viewInflate.findViewById(me.hisn.mygesture.R.id.exit_btn);
            me.hisn.utils.p0.a aVar = new me.hisn.utils.p0.a(str, viewInflate, textView, activity, textView2, windowManagerK);
            textView2.setOnClickListener(aVar);
            textView3.setOnClickListener(aVar);
            viewInflate.setOnTouchListener(new me.hisn.utils.p0.b(viewInflate, str, aVar, intent));
            if (windowManagerK != null) {
                android.view.WindowManager.LayoutParams layoutParamsA = new me.hisn.utils.v().a(true, 49, -1, -2, 0, 0, 0, false);
                layoutParamsA.flags |= 262144;
                windowManagerK.addView(viewInflate, layoutParamsA);
                return viewInflate;
            }
        }
        return null;
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    public java.lang.String a(android.content.Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return "";
            }
            int i = android.os.Build.VERSION.SDK_INT;
            if (i >= 26 && i <= 28) {
                return android.os.Build.getSerial();
            }
        }
        return android.os.Build.SERIAL;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x006a A[PHI: r9
  0x006a: PHI (r9v5 int) = (r9v0 int), (r9v2 int), (r9v0 int), (r9v0 int), (r9v0 int), (r9v0 int) binds: [B:31:0x0051, B:41:0x0068, B:28:0x004a, B:5:0x0010, B:7:0x0016, B:17:0x0031] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:53:0x00b7  */
    @android.annotation.SuppressLint({"MissingPermission"})
    public java.lang.String a(android.content.Context context, int i) {
        java.lang.String deviceId;
        int i2 = android.os.Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            if (i2 > 28 || context.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                deviceId = "";
            } else {
                android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    try {
                        deviceId = i < 2 ? telephonyManager.getImei(i) : telephonyManager.getMeid();
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                        deviceId = "";
                    }
                } else {
                    deviceId = "";
                }
                if (deviceId == null) {
                    deviceId = "";
                }
            }
        } else if (i2 < 23) {
            android.telephony.TelephonyManager telephonyManager2 = (android.telephony.TelephonyManager) context.getSystemService("phone");
            if (telephonyManager2 != null) {
                try {
                    deviceId = telephonyManager2.getDeviceId();
                } catch (java.lang.Exception e2) {
                    e2.printStackTrace();
                    deviceId = "";
                }
            } else {
                deviceId = "";
            }
            if (deviceId == null) {
                deviceId = "";
            }
        } else if (context.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            deviceId = "";
        } else {
            android.telephony.TelephonyManager telephonyManager3 = (android.telephony.TelephonyManager) context.getSystemService("phone");
            if (telephonyManager3 != null) {
                if (i == 2) {
                    i = 0;
                }
                try {
                    deviceId = telephonyManager3.getDeviceId(i);
                } catch (java.lang.Exception e3) {
                    e3.printStackTrace();
                    deviceId = "";
                }
            } else {
                deviceId = "";
            }
            if (deviceId == null) {
                deviceId = "";
            }
        }
        if ("".equals(deviceId) || i == 2) {
            java.lang.String string = me.hisn.mygesture.P.s.getString("si", "");
            if (string == null || string.length() <= 3) {
                deviceId = "";
            } else {
                java.lang.String strGES = me.hisn.utils.C.GES(string, 0);
                if (strGES.contains("&" + android.os.Build.TIME)) {
                    deviceId = strGES.replace("&" + android.os.Build.TIME, "");
                } else {
                    deviceId = "";
                }
            }
        }
        return !c(deviceId) ? "" : deviceId;
    }

    public java.lang.String a(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(?<=[^0-9a-z]|^)(?:99|86|35)[0-9]{12,15}|(?:a0|a1|66)[0-9a-z]{12,15}|00[1-9][0-9]{11,14}|000[1-9][0-9]{10,13}(?=[^0-9a-z]|$)", 2).matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public void b(android.app.Activity activity, int i) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            activity.requestPermissions(new java.lang.String[]{"android.permission.READ_PHONE_STATE"}, i);
        }
    }

    public java.lang.String[] b(android.content.Context context) {
        java.lang.String[] strArr = {a(context), a(context, 0), a(context, 1), a(context, 2)};
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < 4; i++) {
            java.lang.String str = strArr[i];
            if (!"unknown".equals(str) && !android.text.TextUtils.isEmpty(str)) {
                arrayList.add(str);
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }
}
