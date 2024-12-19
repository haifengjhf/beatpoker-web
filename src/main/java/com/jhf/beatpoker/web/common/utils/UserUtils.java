package com.jhf.beatpoker.web.common.utils;

import org.apache.tomcat.util.security.MD5Encoder;

import java.util.Base64;
import java.util.UUID;

public class UserUtils {
    private final static String SPLIT_TOKEN = "*_*";
    public final static int VERIFY_CODE_LENGTH = 6;
    public static String formatUserId(){
        String uuid = UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(uuid.getBytes()).toLowerCase();
    }

    public static String formatToken(String userId){
        String token = userId + SPLIT_TOKEN + System.currentTimeMillis();
        return MD5Encoder.encode(token.getBytes()).toLowerCase();
    }

    public static String formatVerifyCode(String userId){
        String verifyCode = userId + SPLIT_TOKEN + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(verifyCode.getBytes()).toLowerCase().substring(0,VERIFY_CODE_LENGTH);
    }
}
