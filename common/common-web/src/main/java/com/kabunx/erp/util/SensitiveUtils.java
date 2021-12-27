package com.kabunx.erp.util;

public class SensitiveUtils {
    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String fullName) {
        if (StringPlusUtils.isBlank(fullName)) {
            return "";
        }
        final String name = StringPlusUtils.left(fullName, 1);
        return StringPlusUtils.rightPad(name, StringPlusUtils.length(fullName), "*");
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String familyName, final String givenName) {
        if (StringPlusUtils.isBlank(familyName) || StringPlusUtils.isBlank(givenName)) {
            return "";
        }
        return chineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：420**********5762>
     */
    public static String idCardNum(final String id) {
        if (StringPlusUtils.isBlank(id)) {
            return "";
        }
        return StringPlusUtils.left(id, 3).concat(
                StringPlusUtils.removeStart(
                        StringPlusUtils.leftPad(StringPlusUtils.right(id, 4), StringPlusUtils.length(id), "*"),
                        "***"
                )
        );
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     */
    public static String fixedPhone(final String num) {
        if (StringPlusUtils.isBlank(num)) {
            return "";
        }
        return StringPlusUtils.leftPad(StringPlusUtils.right(num, 4), StringPlusUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String mobilePhone(final String num) {
        if (StringPlusUtils.isBlank(num)) {
            return "";
        }
        return StringPlusUtils.left(num, 3).concat(StringPlusUtils
                .removeStart(StringPlusUtils.leftPad(StringPlusUtils.right(num, 4), StringPlusUtils.length(num), "*"),
                        "***"));

    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param sensitiveSize 敏感信息长度
     */
    public static String address(final String address, final int sensitiveSize) {
        if (StringPlusUtils.isBlank(address)) {
            return "";
        }
        final int length = StringPlusUtils.length(address);
        return StringPlusUtils.rightPad(StringPlusUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     */
    public static String email(final String email) {
        if (StringPlusUtils.isBlank(email)) {
            return "";
        }
        final int index = StringPlusUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return StringPlusUtils.rightPad(StringPlusUtils.left(email, 1), index, "*")
                    .concat(StringPlusUtils.mid(email, index, StringPlusUtils.length(email)));
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     */
    public static String bankCard(final String cardNum) {
        if (StringPlusUtils.isBlank(cardNum)) {
            return "";
        }
        return StringPlusUtils.left(cardNum, 6).concat(
                StringPlusUtils.removeStart(
                        StringPlusUtils.leftPad(StringPlusUtils.right(cardNum, 4), StringPlusUtils.length(cardNum), "*"),
                        "******"
                )
        );
    }
}