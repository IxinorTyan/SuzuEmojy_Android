package com.yxcorp.gifshow.reminder.thirdpartyshare;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import com.kwai.feature.api.router.social.message.thirdpartysharebase.ThirdPartyShareContentType;
import com.kwai.privacykit.interceptor.MediaInterceptor;
import com.kwai.robust.PatchProxy;
import com.kwai.robust.PatchProxyResult;
import com.yxcorp.gifshow.models.QMedia;
import com.yxcorp.utility.TextUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: kSourceFile */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\kuaishou\classes19.dex */
public class a {
    public final List<Uri> a;
    public final Activity b;
    public final String[] c;
    public final String[] d;
    public ThirdPartyShareContentType e;
    public String f;
    public boolean g;
    public boolean h;
    public boolean i;
    public int j;
    public List<QMedia> k;
    public boolean l;

    public a(Activity activity) {
        if (PatchProxy.applyVoidOneRefs(activity, this, a.class, "1")) {
            return;
        }
        this.a = new ArrayList();
        this.c = new String[]{"_id", "_data", "duration", "date_added", "date_modified", "_size", "width", "height", "datetaken", "orientation"};
        this.d = new String[]{"_id", "_data", "date_added", "datetaken", "date_modified", "width", "height", "orientation", "_size"};
        this.e = ThirdPartyShareContentType.UNKNOW;
        this.j = 0;
        this.k = new ArrayList();
        this.b = activity;
    }

    public final File a(Uri uri) {
        String strD;
        boolean zBooleanValue;
        Object objApplyOneRefs = PatchProxy.applyOneRefs(uri, this, a.class, "3");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (File) objApplyOneRefs;
        }
        try {
            try {
                strD = g4n.b.a(this.b.getContentResolver(), uri);
            } catch (SecurityException e) {
                zbl.a.f().e("ThirdPartyShareContentResolvedData", "resolve share content error", e);
                strD = null;
            }
            Object objApplyOneRefs2 = PatchProxy.applyOneRefs(strD, (Object) null, sfl.a.class, "3");
            if (objApplyOneRefs2 != PatchProxyResult.class) {
                zBooleanValue = ((Boolean) objApplyOneRefs2).booleanValue();
            } else if (strD != null && strD.startsWith("/data") && strD.contains(rx9.a.b().getPackageName())) {
                zBooleanValue = true;
            } else {
                zBooleanValue = false;
            }
            if (!zBooleanValue) {
                strD = com.kuaishou.gifshow.files.a.d(this.b.getContentResolver(), uri);
            }
        } catch (Exception e2) {
            zbl.a.f().e("ThirdPartyShareContentResolvedData", "getContentUriPath failed", new Object[]{e2.getMessage()});
            strD = null;
        }
        if (!TextUtils.x(strD)) {
            zbl.a.f().i("ThirdPartyShareContentResolvedData", "resolve share content path succeed, filePath: ", new Object[]{strD});
            String strDecode = Uri.decode(strD);
            if (strDecode != null && !strDecode.contains("..")) {
                return new File(strDecode);
            }
        }
        return null;
    }

    public final QMedia b(Uri uri) throws FileNotFoundException {
        int i;
        Object objApplyOneRefs = PatchProxy.applyOneRefs(uri, this, a.class, "6");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (QMedia) objApplyOneRefs;
        }
        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(this.b.getContentResolver().openInputStream(uri));
        String absolutePath = a(uri).getAbsolutePath();
        Bitmap.Config config = bitmapDecodeStream.getConfig();
        if (config == Bitmap.Config.ARGB_8888) {
            i = 4;
        } else if (config == Bitmap.Config.RGB_565 || config == Bitmap.Config.ARGB_4444) {
            i = 2;
        } else {
            Bitmap.Config config2 = Bitmap.Config.ALPHA_8;
            i = 1;
        }
        QMedia qMedia = new QMedia(0L, absolutePath, 0L, i, 0L, 0L, 0);
        qMedia.mWidth = bitmapDecodeStream.getWidth();
        qMedia.mHeight = bitmapDecodeStream.getHeight();
        return qMedia;
    }

    public final QMedia c(Uri uri) throws IOException {
        Object objApplyOneRefs = PatchProxy.applyOneRefs(uri, this, a.class, "7");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (QMedia) objApplyOneRefs;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(a(uri).getAbsolutePath());
        String strExtractMetadata = MediaInterceptor.extractMetadata(mediaMetadataRetriever, 18, "dqn0zzdqsr/ijhtjpy/tfojpegs0ujjterbtu{tjbtf0UjjteRbtu{TjbtfEppugovSgtqmxffEcuc");
        String strExtractMetadata2 = MediaInterceptor.extractMetadata(mediaMetadataRetriever, 19, "dqn0zzdqsr/ijhtjpy/tfojpegs0ujjterbtu{tjbtf0UjjteRbtu{TjbtfEppugovSgtqmxffEcuc");
        String strExtractMetadata3 = MediaInterceptor.extractMetadata(mediaMetadataRetriever, 9, "dqn0zzdqsr/ijhtjpy/tfojpegs0ujjterbtu{tjbtf0UjjteRbtu{TjbtfEppugovSgtqmxffEcuc");
        String strExtractMetadata4 = MediaInterceptor.extractMetadata(mediaMetadataRetriever, 24, "dqn0zzdqsr/ijhtjpy/tfojpegs0ujjterbtu{tjbtf0UjjteRbtu{TjbtfEppugovSgtqmxffEcuc");
        if (!strExtractMetadata4.equals("90") && !strExtractMetadata4.equals("270")) {
            strExtractMetadata2 = strExtractMetadata;
            strExtractMetadata = strExtractMetadata2;
        }
        mediaMetadataRetriever.release();
        QMedia qMedia = new QMedia(0L, a(uri).getAbsolutePath(), Long.parseLong(strExtractMetadata3), 0L, 1);
        qMedia.mHeight = Integer.parseInt(strExtractMetadata);
        qMedia.mWidth = Integer.parseInt(strExtractMetadata2);
        return qMedia;
    }
}
