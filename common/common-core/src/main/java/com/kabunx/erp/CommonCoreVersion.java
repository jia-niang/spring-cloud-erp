package com.kabunx.erp;

/**
 * 通用核心声明
 */
public final class CommonCoreVersion {
    public static final long SERIAL_VERSION_UID = 650L;

    public static final String MIN_COMMON_VERSION = getCommonVersion();

    // TODO 获取yml文件的配置信息
    private static String getCommonVersion() {
        return "1.0-SNAPSHOT";
    }
}
