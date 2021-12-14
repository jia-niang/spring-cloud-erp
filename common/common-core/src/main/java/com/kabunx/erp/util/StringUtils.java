package com.kabunx.erp.util;

public class StringUtils {

    /**
     * 首字母大写
     */
    public static String capitalize(String name) {
        char[] cs = name.toCharArray();
        if (Character.isUpperCase(cs[0])) {
            return name;
        }
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 转为下划线
     */
    public static String snake(String name) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return name.replaceAll(regex, replacement).toLowerCase();
    }
}