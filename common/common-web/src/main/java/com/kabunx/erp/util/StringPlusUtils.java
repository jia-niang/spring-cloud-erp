package com.kabunx.erp.util;

import com.kabunx.erp.CommonCoreVersion;
import com.kabunx.erp.constant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 字符串处理工具
 */
public class StringPlusUtils extends StringUtils {

    /**
     * 默认分隔符 ,
     */
    public static final long SEPARATOR = CommonCoreVersion.SERIAL_VERSION_UID;

    public static final Integer CUT_LENGTH = 10;

    /**
     * 首字母大写
     */
    public static String toFirstUpper(String name) {
        char[] cs = name.toCharArray();
        if (Character.isUpperCase(cs[0])) {
            return name;
        }
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static String toFirstLower(String name) {
        char[] cs = name.toCharArray();
        if (Character.isLowerCase(cs[0])) {
            return name;
        }
        cs[0] += 32;
        return String.valueOf(cs);
    }

    public static boolean isNotEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 裁剪字符串，显示前部分+...
     */
    public static String cut(String input) {
        return cut(input, CUT_LENGTH);
    }

    /**
     * 裁剪字符串，显示前部分+...
     */
    public static String cut(String input, int cutLength) {
        return substring(input, 0, cutLength);
    }

    /**
     * 将list拼接成string，默认分隔符:,
     */
    public static String join(Iterable<?> stringList) {
        return join(stringList, SEPARATOR);
    }

    /**
     * 将list拼接成string，默认分隔符:,
     */
    public static String join(String[] stringArray) {
        return join(stringArray, SEPARATOR);
    }

    /**
     * 按,拆分字符串
     */
    public static String[] split(String joinedStr) {
        if (joinedStr == null) {
            return null;
        }
        return joinedStr.split(GlobalConstant.SEPARATOR);
    }

    /**
     * 转换为String数组（避免转型异常）
     */
    public static String[] toStringArray(List<String> stringList) {
        return stringList.toArray(new String[0]);
    }

    /**
     * 按,拆分字符串并转换为 List<String>
     */
    public static List<String> splitToList(String joinedStr) {
        if (joinedStr == null) {
            return null;
        }
        return Arrays.asList(joinedStr.split(GlobalConstant.SEPARATOR));
    }

    /**
     * 转换成蛇形命名（用于Java属性转换为数据库列名）
     */
    public static String[] toSnakeCase(String[] camelCaseStrArray) {
        if (camelCaseStrArray == null) {
            return null;
        }
        String[] snakeCaseArray = new String[camelCaseStrArray.length];
        for (int i = 0; i < camelCaseStrArray.length; i++) {
            snakeCaseArray[i] = toSnakeCase(camelCaseStrArray[i]);
        }
        return snakeCaseArray;
    }

    /**
     * 转换成蛇形命名（用于Java属性转换为数据库列名）
     */
    public static List<String> toSnakeCase(List<String> camelCaseStrArray) {
        if (camelCaseStrArray == null) {
            return null;
        }
        List<String> snakeCaseList = new ArrayList<>(camelCaseStrArray.size());
        for (String camelCaseStr : camelCaseStrArray) {
            snakeCaseList.add(toSnakeCase(camelCaseStr));
        }
        return snakeCaseList;
    }

    /**
     * 转换成小写蛇形命名（用于Java属性转换为小写数据库列名）
     */
    public static String toSnakeCase(String camelCaseStr) {
        if (ObjectUtils.isEmpty(camelCaseStr)) {
            return null;
        }
        // 全小写
        if (camelCaseStr.toLowerCase().equals(camelCaseStr)) {
            return camelCaseStr;
        }
        // 全大写直接return小写
        if (camelCaseStr.toUpperCase().equals(camelCaseStr)) {
            return camelCaseStr.toLowerCase();
        }
        // 大小写混合，则遇“大写”转换为“_小写”
        char[] chars = camelCaseStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                if (sb.length() > 0) {
                    sb.append(GlobalConstant.SEPARATOR_UNDERSCORE);
                }
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 转换成首字母小写的驼峰命名（用于数据库列名转换为Java属性）
     */
    public static String toLowerCaseCamel(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return null;
        }
        // 不包含_
        if (!str.contains(GlobalConstant.SEPARATOR_UNDERSCORE)) {
            // 全大写直接return小写
            if (str.toUpperCase().equals(str)) {
                return str.toLowerCase();
            } else { // 其他return 首字母小写
                return toFirstLower(str);
            }
        }
        // 包含_
        StringBuilder stringBuilder = null;
        String[] words = str.split(GlobalConstant.SEPARATOR_UNDERSCORE);
        for (String word : words) {
            if (ObjectUtils.isEmpty(word)) {
                continue;
            }
            if (stringBuilder == null) {
                stringBuilder = new StringBuilder(word.toLowerCase());
            } else {
                stringBuilder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
            }
        }
        if (str.endsWith(GlobalConstant.SEPARATOR_UNDERSCORE) && stringBuilder != null) {
            stringBuilder.append(GlobalConstant.SEPARATOR_UNDERSCORE);
        }
        return stringBuilder != null ? stringBuilder.toString() : null;
    }

    /**
     * 转换为Long类型（判空，避免NPE）
     */
    public static Long toLong(String strValue) {
        return toLong(strValue, null);
    }

    /**
     * 转换为Long类型（判空，避免NPE）
     */
    public static Long toLong(String strValue, Long defaultLong) {
        if (ObjectUtils.isEmpty(strValue)) {
            return defaultLong;
        }
        return Long.parseLong(strValue);
    }

    /**
     * 转换为Integer类型(判空，避免NPE)
     */
    public static Integer toInt(String strValue) {
        return toInt(strValue, null);
    }

    /**
     * 转换为Integer类型(判空，避免NPE)
     */
    public static Integer toInt(String strValue, Integer defaultInt) {
        if (ObjectUtils.isEmpty(strValue)) {
            return defaultInt;
        }
        return Integer.parseInt(strValue);
    }

    /**
     * 字符串转换为boolean
     */
    public static boolean toBoolean(String strValue) {
        return toBoolean(strValue, false);
    }

    /**
     * 字符串转换为boolean
     */
    public static boolean toBoolean(String strValue, boolean defaultBoolean) {
        if (ObjectUtils.notEmpty(strValue)) {
            return ObjectUtils.isTrue(strValue);
        }
        return defaultBoolean;
    }

    /**
     * 将多个空格替换为一个
     */
    public static String removeDuplicateBlank(String input) {
        if (ObjectUtils.isEmpty(input)) {
            return input;
        }
        return input.trim().replaceAll(" +", " ");
    }

    /**
     * 获得随机串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 将object转换为字符串
     */
    public static String valueOf(Object object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object);
    }

    /**
     * 字符串转换，如果是null则返回空字符串
     */
    public static String defaultValueOf(Object object) {
        if (object == null) {
            return EMPTY;
        }
        return String.valueOf(object);
    }

    /**
     * 字符串转换，如果是null，则使用默认字符串代替
     */
    public static String defaultValueOf(Object object, String defaultStr) {
        if (object == null) {
            return defaultStr;
        }
        return String.valueOf(object);
    }

    /**
     * 生成指定位数的数字/验证码
     */
    private static final String NUMBER_SET = "12345678901";

    private static final Random random = new Random();

    public static String newRandomNum(int length) {
        StringBuilder sb = new StringBuilder();
        sb.append(NUMBER_SET.charAt(random.nextInt(9)));
        for (int i = 1; i < length; i++) {
            sb.append(NUMBER_SET.charAt(random.nextInt(10)));
        }
        return sb.toString();
    }


    /**
     * 移除转义符
     */
    public static String removeEsc(String columnName) {
        if (ObjectUtils.isEmpty(columnName)) {
            return columnName;
        }
        if (startsWithAny(columnName, "`", "\"", "[")) {
            return substring(columnName, 1, columnName.length() - 1);
        }
        return columnName;
    }

    /**
     * 替换指定字符串的指定区间内字符为固定字符
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @param replacedChar 被替换的字符
     * @return 替换后的字符串
     */
    public static String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (isEmpty(str)) {
            return valueOf(str);
        }
        final int strLength = str.length();
        if (startInclude > strLength) {
            return valueOf(str);
        }
        if (endExclude > strLength) {
            endExclude = strLength;
        }
        if (startInclude > endExclude) {
            // 如果起始位置大于结束位置，不替换
            return valueOf(str);
        }

        final char[] chars = new char[strLength];
        for (int i = 0; i < strLength; i++) {
            if (i >= startInclude && i < endExclude) {
                chars[i] = replacedChar;
            } else {
                chars[i] = str.charAt(i);
            }
        }
        return new String(chars);
    }
}
