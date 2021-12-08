package com.kabunx.erp.util;

public class StringUtils {

    /**
     * 首字母大写
     */
    public static String capitalize(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
