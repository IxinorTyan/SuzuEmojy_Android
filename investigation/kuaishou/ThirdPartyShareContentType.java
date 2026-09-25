package com.kwai.feature.api.router.social.message.thirdpartysharebase;

import com.kwai.robust.PatchProxy;
import com.kwai.robust.PatchProxyResult;
import com.yxcorp.utility.TextUtils;
import y3n.b;

/* JADX INFO: compiled from: kSourceFile */
/* JADX INFO: loaded from: D:\Documents\try\in\investigation\kuaishou\classes4.dex */
public enum ThirdPartyShareContentType {
    Video("video/"),
    IMAGE("image/"),
    TEXT("text/"),
    UNKNOW("");

    public String type;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static ThirdPartyShareContentType[] valuesCustom() {
        Object objApply = PatchProxy.apply((Object) null, ThirdPartyShareContentType.class, "1");
        if (objApply != PatchProxyResult.class) {
            return (ThirdPartyShareContentType[]) objApply;
        }
        return (ThirdPartyShareContentType[]) values().clone();
    }

    public static ThirdPartyShareContentType valueOf(String str) {
        Object objApplyOneRefs = PatchProxy.applyOneRefs(str, (Object) null, ThirdPartyShareContentType.class, "2");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (ThirdPartyShareContentType) objApplyOneRefs;
        }
        return (ThirdPartyShareContentType) Enum.valueOf(ThirdPartyShareContentType.class, str);
    }

    public static ThirdPartyShareContentType fromMediaFileName(String str) {
        Object objApplyOneRefs = PatchProxy.applyOneRefs(str, (Object) null, ThirdPartyShareContentType.class, "5");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (ThirdPartyShareContentType) objApplyOneRefs;
        }
        if (TextUtils.x(str)) {
            return UNKNOW;
        }
        if (b.I().matcher(str).find()) {
            return Video;
        }
        if (b.F().matcher(str).find()) {
            return IMAGE;
        }
        return UNKNOW;
    }

    public static ThirdPartyShareContentType fromMediaTypeText(String str) {
        Object objApplyOneRefs = PatchProxy.applyOneRefs(str, (Object) null, ThirdPartyShareContentType.class, "4");
        if (objApplyOneRefs != PatchProxyResult.class) {
            return (ThirdPartyShareContentType) objApplyOneRefs;
        }
        if (TextUtils.x(str)) {
            return UNKNOW;
        }
        for (ThirdPartyShareContentType thirdPartyShareContentType : valuesCustom()) {
            if (str.startsWith(thirdPartyShareContentType.type)) {
                return thirdPartyShareContentType;
            }
        }
        return UNKNOW;
    }

    ThirdPartyShareContentType(String str) {
        if (PatchProxy.applyVoidObjectIntObject(ThirdPartyShareContentType.class, "3", this, str, i, str)) {
            return;
        }
        this.type = str;
    }
}
