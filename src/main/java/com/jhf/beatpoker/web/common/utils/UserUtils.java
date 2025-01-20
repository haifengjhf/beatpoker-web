package com.jhf.beatpoker.web.common.utils;

import org.springframework.util.DigestUtils;

import java.util.Base64;

public class UserUtils {
    public final static String SPLITE = "*-*";
    public final static int VERIFY_CODE_LENGTH = 6;

    public static String formatToken(String userId){
        String token = userId + SPLITE + System.currentTimeMillis();
        return DigestUtils.md5DigestAsHex(token.getBytes()).toLowerCase();
    }

    public static String formatUserId(String emailAddress){
        return DigestUtils.md5DigestAsHex(emailAddress.getBytes());
    }

    public static String formatDbPassword(String emailAddress,String newPassword){
        String tmpEA = DigestUtils.md5DigestAsHex(emailAddress.getBytes());
        String tmpNP = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        String tmp = tmpEA.substring(0,tmpEA.length()/3) + tmpNP.substring(tmpNP.length()/3);
        return DigestUtils.md5DigestAsHex(tmp.getBytes());
    }

    public static String formatVerifyCode(String userId){
        String verifyCode = userId + SPLITE + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(verifyCode.getBytes()).toLowerCase().substring(0,VERIFY_CODE_LENGTH);
    }
}
