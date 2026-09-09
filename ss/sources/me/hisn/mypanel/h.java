package me.hisn.mypanel;

/* JADX INFO: loaded from: classes.dex */
public class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.content.Context f695a;

    public h(android.content.Context context) {
        this.f695a = context;
    }

    public int a(int i, boolean z) {
        android.bluetooth.BluetoothAdapter adapter;
        me.hisn.utils.b0 b0Var;
        android.content.Context applicationContext;
        int i2;
        int i3;
        android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) this.f695a.getSystemService("bluetooth");
        int i4 = -1;
        if (bluetoothManager != null && (adapter = bluetoothManager.getAdapter()) != null) {
            try {
                if (i == -1) {
                    if (adapter.isEnabled()) {
                        adapter.disable();
                        i3 = 0;
                    } else {
                        adapter.enable();
                        i3 = 1;
                    }
                    i4 = i3;
                } else if (i == 0) {
                    if (adapter.isEnabled()) {
                        adapter.disable();
                    }
                    i4 = 0;
                } else if (i == 1) {
                    if (!adapter.isEnabled()) {
                        adapter.enable();
                    }
                    i4 = 1;
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            if (z) {
                android.os.Looper.prepare();
                if (i4 == 1) {
                    b0Var = new me.hisn.utils.b0();
                    applicationContext = this.f695a.getApplicationContext();
                    i2 = me.hisn.mygesture.R.string.bluetooth_on;
                } else {
                    if (i4 == 0) {
                        b0Var = new me.hisn.utils.b0();
                        applicationContext = this.f695a.getApplicationContext();
                        i2 = me.hisn.mygesture.R.string.bluetooth_off;
                    }
                    android.os.Looper.loop();
                }
                b0Var.a(applicationContext, i2, 0);
                android.os.Looper.loop();
            }
        }
        return i4;
    }

    public boolean a() {
        android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) this.f695a.getSystemService("bluetooth");
        if (bluetoothManager == null) {
            return false;
        }
        try {
            return bluetoothManager.getAdapter().isEnabled();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int b(int i, boolean z) {
        me.hisn.utils.b0 b0Var;
        android.content.Context applicationContext;
        int i2;
        android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) this.f695a.getApplicationContext().getSystemService("wifi");
        int i3 = -1;
        if (wifiManager != null) {
            int wifiState = wifiManager.getWifiState();
            if (i != -1) {
                try {
                    if (i != 0) {
                        if (i == 1 && wifiState != 3 && wifiState != 2) {
                            wifiManager.setWifiEnabled(true);
                            i3 = 1;
                        }
                    } else if (wifiState != 1 && wifiState != 0) {
                        wifiManager.setWifiEnabled(false);
                        i3 = 0;
                    }
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (wifiState == 1 || wifiState == 0) {
                        wifiManager.setWifiEnabled(true);
                        i3 = 1;
                    } else if (wifiState == 3 || wifiState == 2) {
                        wifiManager.setWifiEnabled(false);
                        i3 = 0;
                    }
                } catch (android.content.res.Resources.NotFoundException e2) {
                    e2.printStackTrace();
                }
            }
            if (z) {
                android.os.Looper.prepare();
                if (i3 == 1) {
                    b0Var = new me.hisn.utils.b0();
                    applicationContext = this.f695a.getApplicationContext();
                    i2 = me.hisn.mygesture.R.string.wifi_on;
                } else {
                    if (i3 == 0) {
                        b0Var = new me.hisn.utils.b0();
                        applicationContext = this.f695a.getApplicationContext();
                        i2 = me.hisn.mygesture.R.string.wifi_off;
                    }
                    android.os.Looper.loop();
                }
                b0Var.a(applicationContext, i2, 0);
                android.os.Looper.loop();
            }
        }
        return i3;
    }

    public boolean b() {
        android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) this.f695a.getApplicationContext().getSystemService("wifi");
        if (wifiManager != null) {
            try {
                return wifiManager.getWifiState() == 3;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
