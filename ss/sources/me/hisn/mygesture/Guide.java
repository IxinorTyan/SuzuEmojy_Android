package me.hisn.mygesture;

/* JADX INFO: loaded from: classes.dex */
public class Guide extends android.app.Activity {
    private void a() {
        if (me.hisn.mygesture.P.s.getBoolean("is_first_run", true)) {
            me.hisn.mygesture.P.s.edit().putString("left_edge_swipe_right_key_l", getString(me.hisn.mygesture.R.string.back_text)).putInt("left_edge_swipe_right_key_k", 1).apply();
            me.hisn.mygesture.P.s.edit().putString("right_edge_swipe_left_key_l", getString(me.hisn.mygesture.R.string.back_text)).putInt("right_edge_swipe_left_key_k", 1).apply();
            me.hisn.mygesture.P.s.edit().putString("left_of_bottom_edge_swipe_up_key_l", getString(me.hisn.mygesture.R.string.home_text)).putInt("left_of_bottom_edge_swipe_up_key_k", 2).apply();
            me.hisn.mygesture.P.s.edit().putString("center_of_bottom_edge_swipe_up_key_l", getString(me.hisn.mygesture.R.string.recent_text)).putInt("center_of_bottom_edge_swipe_up_key_k", 3).apply();
            me.hisn.mygesture.P.s.edit().putString("left_edge_swipe_down_key_l", getString(me.hisn.mygesture.R.string.notifications)).putInt("left_edge_swipe_down_key_k", 4).apply();
            me.hisn.mygesture.P.s.edit().putString("right_edge_swipe_down_key_l", getString(me.hisn.mygesture.R.string.notifications)).putInt("right_edge_swipe_down_key_k", 4).apply();
            me.hisn.mygesture.P.s.edit().putBoolean("is_first_run2", true).putBoolean("is_first_run", false).apply();
        }
    }

    public void iKnow(android.view.View view) {
        startActivity(new android.content.Intent(this, (java.lang.Class<?>) me.hisn.mygesture.EA.class));
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(me.hisn.mygesture.R.layout.activity_guide);
        a();
    }
}
