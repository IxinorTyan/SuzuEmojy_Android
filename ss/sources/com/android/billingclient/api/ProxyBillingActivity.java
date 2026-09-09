package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
public class ProxyBillingActivity extends android.app.Activity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.os.ResultReceiver f376a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f377b;

    private final android.content.Intent a() {
        android.content.Intent intent = new android.content.Intent("com.android.vending.billing.PURCHASES_UPDATED");
        intent.setPackage(getApplicationContext().getPackageName());
        return intent;
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100) {
            int iA = b.a.a.a.a.a.a.a(intent, "ProxyBillingActivity").a();
            if (i2 != -1) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder(85);
                sb.append("Activity finished with resultCode ");
                sb.append(i2);
                sb.append(" and billing's responseCode: ");
                sb.append(iA);
                b.a.a.a.a.a.a.b("ProxyBillingActivity", sb.toString());
            } else if (iA != 0) {
                i2 = -1;
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder(85);
                sb2.append("Activity finished with resultCode ");
                sb2.append(i2);
                sb2.append(" and billing's responseCode: ");
                sb2.append(iA);
                b.a.a.a.a.a.a.b("ProxyBillingActivity", sb2.toString());
            } else {
                iA = 0;
            }
            android.os.ResultReceiver resultReceiver = this.f376a;
            if (resultReceiver != null) {
                resultReceiver.send(iA, intent == null ? null : intent.getExtras());
            } else {
                android.content.Intent intentA = a();
                if (intent != null) {
                    if (intent.getExtras() != null) {
                        intentA.putExtras(intent.getExtras());
                    } else {
                        b.a.a.a.a.a.a.b("ProxyBillingActivity", "Got null bundle!");
                        intentA.putExtra("RESPONSE_CODE", 6);
                        intentA.putExtra("DEBUG_MESSAGE", "An internal error occurred.");
                    }
                }
                sendBroadcast(intentA);
            }
        } else {
            java.lang.StringBuilder sb3 = new java.lang.StringBuilder(69);
            sb3.append("Got onActivityResult with wrong requestCode: ");
            sb3.append(i);
            sb3.append("; skipping...");
            b.a.a.a.a.a.a.b("ProxyBillingActivity", sb3.toString());
        }
        this.f377b = false;
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        android.app.PendingIntent pendingIntent;
        super.onCreate(bundle);
        if (bundle != null) {
            b.a.a.a.a.a.a.a("ProxyBillingActivity", "Launching Play Store billing flow from savedInstanceState");
            this.f377b = bundle.getBoolean("send_cancelled_broadcast_if_finished", false);
            if (bundle.containsKey("result_receiver")) {
                this.f376a = (android.os.ResultReceiver) bundle.getParcelable("result_receiver");
                return;
            }
            return;
        }
        b.a.a.a.a.a.a.a("ProxyBillingActivity", "Launching Play Store billing flow");
        if (getIntent().hasExtra("BUY_INTENT")) {
            pendingIntent = (android.app.PendingIntent) getIntent().getParcelableExtra("BUY_INTENT");
        } else if (getIntent().hasExtra("SUBS_MANAGEMENT_INTENT")) {
            pendingIntent = (android.app.PendingIntent) getIntent().getParcelableExtra("SUBS_MANAGEMENT_INTENT");
            this.f376a = (android.os.ResultReceiver) getIntent().getParcelableExtra("result_receiver");
        } else {
            pendingIntent = null;
        }
        try {
            this.f377b = true;
            startIntentSenderForResult(pendingIntent.getIntentSender(), 100, new android.content.Intent(), 0, 0, 0);
        } catch (android.content.IntentSender.SendIntentException e) {
            java.lang.String strValueOf = java.lang.String.valueOf(e);
            java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.valueOf(strValueOf).length() + 53);
            sb.append("Got exception while trying to start a purchase flow: ");
            sb.append(strValueOf);
            b.a.a.a.a.a.a.b("ProxyBillingActivity", sb.toString());
            android.os.ResultReceiver resultReceiver = this.f376a;
            if (resultReceiver != null) {
                resultReceiver.send(6, null);
            } else {
                android.content.Intent intentA = a();
                intentA.putExtra("RESPONSE_CODE", 6);
                intentA.putExtra("DEBUG_MESSAGE", "An internal error occurred.");
                sendBroadcast(intentA);
            }
            this.f377b = false;
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() && this.f377b) {
            android.content.Intent intentA = a();
            intentA.putExtra("RESPONSE_CODE", 1);
            intentA.putExtra("DEBUG_MESSAGE", "Billing dialog closed.");
            sendBroadcast(intentA);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        android.os.ResultReceiver resultReceiver = this.f376a;
        if (resultReceiver != null) {
            bundle.putParcelable("result_receiver", resultReceiver);
        }
        bundle.putBoolean("send_cancelled_broadcast_if_finished", this.f377b);
    }
}
