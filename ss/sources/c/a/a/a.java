package c.a.a;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private java.lang.String f371a;
    private int d;
    private android.content.Context f;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f372b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    private java.util.HashMap<java.lang.String, java.lang.String> f373c = new java.util.HashMap<>();
    private android.content.res.Resources e = null;

    public a(android.content.Context context, java.lang.String str) {
        this.f = context;
        this.f371a = str;
    }

    private android.graphics.drawable.Drawable a(java.lang.String str) {
        int identifier = this.e.getIdentifier(str, "drawable", this.f371a);
        if (identifier > 0) {
            return this.e.getDrawable(identifier);
        }
        return null;
    }

    private void a() {
        org.xmlpull.v1.XmlPullParser xmlPullParserNewPullParser;
        org.xmlpull.v1.XmlPullParser xml;
        try {
            try {
                android.content.res.Resources resourcesForApplication = this.f.getPackageManager().getResourcesForApplication(this.f371a);
                this.e = resourcesForApplication;
                int identifier = resourcesForApplication.getIdentifier("appfilter", "xml", this.f371a);
                if (identifier > 0) {
                    xml = this.e.getXml(identifier);
                } else {
                    try {
                        java.io.InputStream inputStreamOpen = this.e.getAssets().open("appfilter.xml");
                        org.xmlpull.v1.XmlPullParserFactory xmlPullParserFactoryNewInstance = org.xmlpull.v1.XmlPullParserFactory.newInstance();
                        xmlPullParserFactoryNewInstance.setNamespaceAware(true);
                        xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
                        try {
                            xmlPullParserNewPullParser.setInput(inputStreamOpen, "utf-8");
                        } catch (java.io.IOException e) {
                            e = e;
                            e.printStackTrace();
                        } catch (org.xmlpull.v1.XmlPullParserException e2) {
                            e = e2;
                            e.printStackTrace();
                        }
                    } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e3) {
                        e = e3;
                        xmlPullParserNewPullParser = null;
                    }
                    xml = xmlPullParserNewPullParser;
                }
                if (xml != null) {
                    for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                        if (eventType == 2 && xml.getName().equals("item")) {
                            java.lang.String attributeValue = null;
                            java.lang.String attributeValue2 = null;
                            for (int i = 0; i < xml.getAttributeCount(); i++) {
                                if (xml.getAttributeName(i).equals("component")) {
                                    attributeValue = xml.getAttributeValue(i);
                                } else if (xml.getAttributeName(i).equals("drawable")) {
                                    attributeValue2 = xml.getAttributeValue(i);
                                }
                            }
                            if (!this.f373c.containsKey(attributeValue)) {
                                this.f373c.put(attributeValue, attributeValue2);
                                this.d++;
                            }
                        }
                    }
                }
            } catch (java.io.IOException e4) {
                e = e4;
                e.printStackTrace();
            } catch (org.xmlpull.v1.XmlPullParserException e5) {
                e = e5;
                e.printStackTrace();
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e6) {
            e = e6;
            e.printStackTrace();
        }
        this.f372b = true;
    }

    public android.graphics.drawable.Drawable a(java.lang.String str, java.lang.String str2, android.graphics.drawable.Drawable drawable) {
        int iIndexOf;
        int iIndexOf2;
        java.lang.String string = null;
        if (this.f371a == null) {
            return null;
        }
        if (!this.f372b) {
            a();
        }
        if (str2 == null) {
            android.content.Intent launchIntentForPackage = this.f.getPackageManager().getLaunchIntentForPackage(str);
            if (launchIntentForPackage != null) {
                android.content.ComponentName component = launchIntentForPackage.getComponent();
                if (component == null) {
                    return null;
                }
                string = component.toString();
            }
        } else {
            string = new android.content.ComponentName(str, str2).toString();
        }
        java.lang.String str3 = this.f373c.get(string);
        if (str3 != null) {
            return a(str3);
        }
        if (string != null && (iIndexOf2 = string.indexOf("}", (iIndexOf = string.indexOf("{") + 1))) > iIndexOf) {
            java.lang.String strReplace = string.substring(iIndexOf, iIndexOf2).toLowerCase(java.util.Locale.getDefault()).replace(".", "_").replace("/", "_");
            android.content.res.Resources resources = this.e;
            if (resources != null && resources.getIdentifier(strReplace, "drawable", str) > 0) {
                return a(strReplace);
            }
        }
        return drawable;
    }
}
