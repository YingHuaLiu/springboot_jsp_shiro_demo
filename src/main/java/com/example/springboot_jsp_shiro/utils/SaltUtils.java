package com.example.springboot_jsp_shiro.utils;

import java.util.Random;

public class SaltUtils {
    /**
     * 生成salt
     * @param n
     * @return
     */
    public static String getSalt(int n) {
        char[] chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }
}
