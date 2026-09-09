package me.hisn.utils;

/* JADX INFO: loaded from: classes.dex */
public class TS extends android.service.quicksettings.TileService {
    private void a() {
        android.service.quicksettings.Tile qsTile = getQsTile();
        qsTile.setState(me.hisn.mygesture.P.m0 ? 2 : 1);
        qsTile.updateTile();
    }

    @Override // android.service.quicksettings.TileService, android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return super.onBind(intent);
    }

    @Override // android.service.quicksettings.TileService
    public void onClick() {
        super.onClick();
        if (!me.hisn.mygesture.MAS.l()) {
            new me.hisn.utils.a(this).b();
        } else if (me.hisn.mygesture.P.m0) {
            me.hisn.mygesture.MAS.d(false);
        } else {
            me.hisn.mygesture.MAS.t();
        }
    }

    @Override // android.service.quicksettings.TileService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.service.quicksettings.TileService
    public void onStartListening() {
        super.onStartListening();
        a();
    }

    @Override // android.service.quicksettings.TileService
    public void onStopListening() {
        super.onStopListening();
    }

    @Override // android.service.quicksettings.TileService
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override // android.service.quicksettings.TileService
    public void onTileRemoved() {
        super.onTileRemoved();
    }
}
