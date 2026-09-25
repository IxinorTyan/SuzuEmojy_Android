package com.yxcorp.gifshow.reminder.thirdpartyshare;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import c4n.d;
import com.kwai.feature.api.router.social.message.thirdpartysharebase.ThirdPartyShareContentType;
import com.kwai.feature.api.router.social.message.thirdpartysharebase.ThirdPartyShareParams;
import com.kwai.feature.api.social.message.imshare.model.IMShareOuterImageObject;
import com.kwai.feature.api.social.message.imshare.model.IMShareOuterVideoObject;
import com.kwai.feature.api.social.message.imshare.model.IMShareRecoRequest;
import com.kwai.feature.api.social.message.imshare.model.IMShareRequest;
import com.kwai.feature.api.social.message.imshare.model.IMShareTextObject;
import com.kwai.robust.PatchProxy;
import com.kwai.robust.PatchProxyResult;
import com.tbruyelle.rxpermissions2.f;
import com.yxcorp.gifshow.activity.GifshowActivity;
import com.yxcorp.gifshow.models.QMedia;
import com.yxcorp.gifshow.util.PermissionUtils;
import eql.r1;
import io.reactivex.internal.functions.Functions;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import l3n.t;
import rcc.i;
import vcn.g;

/* JADX INFO: compiled from: kSourceFile */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\kuaishou\classes19.dex */
public class c {
    public GifshowActivity a;
    public a b;
    public Intent c;

    /* JADX WARN: Code duplicated, block: B:29:0x0075  */
    public void a() {
        boolean zBooleanValue;
        ClipData.Item itemAt;
        boolean zEquals;
        boolean z;
        int iIntValue;
        boolean zBooleanValue2;
        boolean zBooleanValue3;
        if (PatchProxy.applyVoid(this, c.class, "2")) {
            return;
        }
        Intent intent = this.c;
        Object objApplyOneRefs = PatchProxy.applyOneRefs(intent, (Object) null, sfl.a.class, "4");
        if (objApplyOneRefs != PatchProxyResult.class) {
            zBooleanValue = ((Boolean) objApplyOneRefs).booleanValue();
        } else if ("android.intent.action.SEND".equals(intent.getAction()) && intent.getClipData() != null && intent.getClipData().getItemCount() > 0 && (itemAt = intent.getClipData().getItemAt(0)) != null && itemAt.getUri() != null) {
            String string = itemAt.getUri().toString();
            if (!TextUtils.isEmpty(string) && string.contains("baidu") && string.contains("input")) {
                zBooleanValue = true;
            } else {
                zBooleanValue = false;
            }
        } else {
            zBooleanValue = false;
        }
        if (!zBooleanValue) {
            Intent intent2 = this.c;
            Object objApplyOneRefs2 = PatchProxy.applyOneRefs(intent2, (Object) null, sfl.a.class, "5");
            if (objApplyOneRefs2 != PatchProxyResult.class) {
                zEquals = ((Boolean) objApplyOneRefs2).booleanValue();
            } else if (intent2 != null && intent2.getData() != null) {
                zEquals = "vnd.android.cursor.dir/vnd.android.document/directory".equals(intent2.getType());
            } else {
                zEquals = false;
            }
            if (!zEquals) {
                a aVar = this.b;
                Intent intent3 = this.c;
                Objects.requireNonNull(aVar);
                if (!PatchProxy.applyVoidOneRefs(intent3, aVar, a.class, "2")) {
                    if ("android.intent.action.SEND".equals(intent3.getAction())) {
                        aVar.l = true;
                        aVar.e = ThirdPartyShareContentType.fromMediaTypeText(intent3.getType());
                        String type = intent3.getType();
                        Object objApplyOneRefs3 = PatchProxy.applyOneRefs(type, (Object) null, sfl.a.class, "1");
                        if (objApplyOneRefs3 != PatchProxyResult.class) {
                            zBooleanValue3 = ((Boolean) objApplyOneRefs3).booleanValue();
                        } else {
                            ThirdPartyShareContentType thirdPartyShareContentTypeFromMediaTypeText = ThirdPartyShareContentType.fromMediaTypeText(type);
                            if (!ThirdPartyShareContentType.TEXT.equals(thirdPartyShareContentTypeFromMediaTypeText) && !ThirdPartyShareContentType.IMAGE.equals(thirdPartyShareContentTypeFromMediaTypeText) && !ThirdPartyShareContentType.Video.equals(thirdPartyShareContentTypeFromMediaTypeText)) {
                                zBooleanValue3 = false;
                            } else {
                                zBooleanValue3 = true;
                            }
                        }
                        if (zBooleanValue3) {
                            ThirdPartyShareContentType thirdPartyShareContentType = aVar.e;
                            if (thirdPartyShareContentType != ThirdPartyShareContentType.IMAGE && thirdPartyShareContentType != ThirdPartyShareContentType.Video) {
                                if (thirdPartyShareContentType == ThirdPartyShareContentType.TEXT) {
                                    aVar.f = intent3.getStringExtra("android.intent.extra.TEXT");
                                    aVar.i = true;
                                } else {
                                    aVar.j++;
                                }
                            } else {
                                aVar.a.add((Uri) intent3.getParcelableExtra("android.intent.extra.STREAM"));
                            }
                        } else {
                            aVar.j++;
                        }
                    } else if ("android.intent.action.SEND_MULTIPLE".equals(intent3.getAction())) {
                        aVar.l = false;
                        aVar.a.addAll(intent3.getParcelableArrayListExtra("android.intent.extra.STREAM"));
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < aVar.a.size(); i++) {
                            File fileA = aVar.a(aVar.a.get(i));
                            if (fileA != null) {
                                arrayList.add(fileA);
                            }
                        }
                        Object objApplyOneRefs4 = PatchProxy.applyOneRefs(arrayList, aVar, a.class, "4");
                        if (objApplyOneRefs4 != PatchProxyResult.class) {
                            iIntValue = ((Number) objApplyOneRefs4).intValue();
                        } else if (arrayList.size() <= 0) {
                            iIntValue = 0;
                        } else {
                            int i2 = 0;
                            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                File file = (File) arrayList.get(i3);
                                Object objApplyOneRefs5 = PatchProxy.applyOneRefs(file, (Object) null, sfl.a.class, "2");
                                if (objApplyOneRefs5 != PatchProxyResult.class) {
                                    zBooleanValue2 = ((Boolean) objApplyOneRefs5).booleanValue();
                                } else {
                                    ThirdPartyShareContentType thirdPartyShareContentTypeFromMediaFileName = ThirdPartyShareContentType.fromMediaFileName(file.getAbsolutePath());
                                    if (!ThirdPartyShareContentType.IMAGE.equals(thirdPartyShareContentTypeFromMediaFileName) && !ThirdPartyShareContentType.Video.equals(thirdPartyShareContentTypeFromMediaFileName)) {
                                        zBooleanValue2 = false;
                                    } else {
                                        zBooleanValue2 = true;
                                    }
                                }
                                if (!zBooleanValue2) {
                                    arrayList.remove(arrayList.get(i3));
                                    i2++;
                                }
                            }
                            iIntValue = i2;
                        }
                        aVar.j = iIntValue;
                        if (iIntValue > 0) {
                            i.b(2131887842, 2131834533);
                        }
                    }
                }
                if (TextUtils.isEmpty(this.b.f) && t.f(this.b.a)) {
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    this.a.finish();
                    i.e(2131887842, r1.q(2131834533), 1);
                    return;
                } else {
                    if (!PatchProxy.applyVoid(this, c.class, "3")) {
                        PermissionUtils.i(new f(this.a), this.a, "android.permission.WRITE_EXTERNAL_STORAGE", false).observeOn(gf8.f.g).observeOn(gf8.f.e).subscribe(new g() { // from class: sfl.c
                            public final void accept(Object obj) {
                                QMedia qMediaC;
                                com.yxcorp.gifshow.reminder.thirdpartyshare.c cVar = this.b;
                                Objects.requireNonNull(cVar);
                                if (((c3f.a) obj).b) {
                                    if (!PatchProxy.applyVoid(cVar, com.yxcorp.gifshow.reminder.thirdpartyshare.c.class, "4")) {
                                        com.yxcorp.gifshow.reminder.thirdpartyshare.a aVar2 = cVar.b;
                                        Objects.requireNonNull(aVar2);
                                        if (!PatchProxy.applyVoid(aVar2, com.yxcorp.gifshow.reminder.thirdpartyshare.a.class, "5")) {
                                            for (int i4 = 0; i4 < aVar2.a.size(); i4++) {
                                                ThirdPartyShareContentType thirdPartyShareContentTypeFromMediaFileName2 = ThirdPartyShareContentType.fromMediaFileName(aVar2.a(aVar2.a.get(i4)).getAbsolutePath());
                                                try {
                                                    ThirdPartyShareContentType thirdPartyShareContentType2 = ThirdPartyShareContentType.IMAGE;
                                                    if (thirdPartyShareContentTypeFromMediaFileName2 == thirdPartyShareContentType2) {
                                                        qMediaC = aVar2.b(aVar2.a.get(i4));
                                                    } else {
                                                        qMediaC = aVar2.c(aVar2.a.get(i4));
                                                    }
                                                    aVar2.k.add(qMediaC);
                                                    if (thirdPartyShareContentTypeFromMediaFileName2 == thirdPartyShareContentType2) {
                                                        aVar2.g = true;
                                                    } else {
                                                        aVar2.h = true;
                                                    }
                                                } catch (Exception e) {
                                                    zbl.a.f().e("ThirdPartyShareContentResolvedData", "convertUriToMediaList error", new Object[]{e.getMessage()});
                                                }
                                            }
                                        }
                                        if (TextUtils.isEmpty(cVar.b.f) && t.f(cVar.b.k)) {
                                            i.e(2131887844, r1.q(2131834533), 1);
                                            cVar.a.finish();
                                            return;
                                        }
                                        com.yxcorp.gifshow.reminder.thirdpartyshare.a aVar3 = cVar.b;
                                        ThirdPartyShareParams thirdPartyShareParams = new ThirdPartyShareParams(aVar3.e, aVar3.f, aVar3.k, aVar3.l, aVar3.j);
                                        com.yxcorp.gifshow.reminder.thirdpartyshare.a aVar4 = cVar.b;
                                        thirdPartyShareParams.mShareContentIncludeImage = aVar4.g;
                                        thirdPartyShareParams.mShareContentIncludeVideo = aVar4.h;
                                        thirdPartyShareParams.mShareContentIncludeText = aVar4.i;
                                        ArrayList arrayList2 = new ArrayList();
                                        if (!TextUtils.isEmpty(thirdPartyShareParams.mShareText)) {
                                            arrayList2.add(new IMShareTextObject(thirdPartyShareParams.mShareText));
                                        } else if (!t.f(thirdPartyShareParams.mShareMedias)) {
                                            for (QMedia qMedia : thirdPartyShareParams.mShareMedias) {
                                                if (qMedia.isImage()) {
                                                    arrayList2.add(new IMShareOuterImageObject(qMedia));
                                                } else if (qMedia.isVideo()) {
                                                    arrayList2.add(new IMShareOuterVideoObject(qMedia));
                                                }
                                            }
                                        } else {
                                            return;
                                        }
                                        IMShareRequest iMShareRequest = new IMShareRequest(String.valueOf(System.currentTimeMillis()), Collections.emptyList(), arrayList2);
                                        iMShareRequest.setIMShareBizSource("album_share");
                                        iMShareRequest.setEnableReplaceSendAWordShareButton(false);
                                        HashMap map = new HashMap();
                                        map.put("is_text", Integer.valueOf(thirdPartyShareParams.mShareContentIncludeText ? 1 : 0));
                                        map.put("is_photo", Integer.valueOf(thirdPartyShareParams.mShareContentIncludeVideo ? 1 : 0));
                                        map.put("is_image", Integer.valueOf(thirdPartyShareParams.mShareContentIncludeImage ? 1 : 0));
                                        iMShareRequest.setBizLogParams(map);
                                        iMShareRequest.setRecoRequest(new IMShareRecoRequest(String.valueOf(System.currentTimeMillis()), 0, "", (String) null, 4));
                                        GifshowActivity gifshowActivity = cVar.a;
                                        if (gifshowActivity instanceof GifshowActivity) {
                                            d.b(2030366997).MS0(cVar.a, iMShareRequest, thirdPartyShareParams);
                                            return;
                                        } else {
                                            gifshowActivity.finish();
                                            return;
                                        }
                                    }
                                    return;
                                }
                                i.c(2131887842, 2131834830, new Object[]{1});
                                cVar.a.finish();
                            }
                        }, Functions.e());
                        return;
                    }
                    return;
                }
            }
        }
        i.d(2131887844, r1.q(2131834533));
        this.a.finish();
    }

    public c(GifshowActivity gifshowActivity, Intent intent) {
        if (PatchProxy.applyVoidTwoRefsWithListener(gifshowActivity, intent, this, c.class, "1")) {
            return;
        }
        this.a = gifshowActivity;
        this.c = intent;
        this.b = new a(gifshowActivity);
    }
}
