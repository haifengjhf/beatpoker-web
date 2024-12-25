package com.jhf.beatpoker.web.common.utils;

import java.security.SecureRandom;

public class RandomPasswordGenerator {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
//    private static final String SPECIAL_CHARS = "!@#$%^&*()";
//    private static final String ALL_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;
    private static final String ALL_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER;

    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALL_CHARS.length());
            sb.append(ALL_CHARS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
