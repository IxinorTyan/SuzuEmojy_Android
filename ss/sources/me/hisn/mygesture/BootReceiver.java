package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class BootReceiver extends android.content.BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            try {
                context.startService(new android.content.Intent(context, (java.lang.Class<?>) me.hisn.mygesture.MAS.class));
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }
}
