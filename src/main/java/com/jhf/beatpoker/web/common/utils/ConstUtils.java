package com.jhf.beatpoker.web.common.utils;

import com.jhf.beatpoker.web.common.response.EnumStatusCode;

public class ConstUtils {
    public final static int ACCOUNT_STATUS_NORMAL = 0;
    public final static int ACCOUNT_STATUS_ABNORMAL = 1;
    public final static int ACCOUNT_STATUS_TO_BE_ACTIVATED = 2;//待激活

    public final static int TYPE_INCREASE = 1;
    public final static int TYPE_DECREASE = 2;

    public static EnumStatusCode countToCode(int count){
        return count <= 0 ? EnumStatusCode.FAILED:EnumStatusCode.SUCCESS;
    }

    public static EnumStatusCode countToCode(int count,EnumStatusCode failed,EnumStatusCode success){
        return count <= 0 ? failed:success;
    }
}
