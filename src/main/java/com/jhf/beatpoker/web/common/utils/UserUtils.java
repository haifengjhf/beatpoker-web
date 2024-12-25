package com.jhf.beatpoker.web.common.utils;

import org.springframework.util.DigestUtils;

import java.util.Base64;
import java.util.UUID;

public class UserUtils {
    private final static String SPLIT_TOKEN = "*-*";
    public final static int VERIFY_CODE_LENGTH = 6;

    public static String formatToken(String userId){
        String token = userId + SPLIT_TOKEN + System.currentTimeMillis();
        return DigestUtils.md5DigestAsHex(token.getBytes()).toLowerCase();
    }

    public static String formatUserId(String emailAddress){
        return DigestUtils.md5DigestAsHex(emailAddress.getBytes());
    }

    public static String formatDbPassword(String userId,String newPassword){
        String tmp = userId + SPLIT_TOKEN + newPassword;
        return DigestUtils.md5DigestAsHex(tmp.getBytes());
    }

    public static String formatVerifyCode(String userId){
        String verifyCode = userId + SPLIT_TOKEN + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(verifyCode.getBytes()).toLowerCase().substring(0,VERIFY_CODE_LENGTH);
    }
}
