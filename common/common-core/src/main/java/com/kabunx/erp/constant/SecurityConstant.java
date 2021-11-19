package com.kabunx.erp.constant;

public interface SecurityConstant {
    /**
     * 后台client_id
     */
    String ADMIN_CLIENT_ID = "erp-admin";

    /**
     * 微信小程序client_id
     */
    String WECHAT_CLIENT_ID = "erp-wechat";

    /**
     * 前台client_id
     */
    String APP_CLIENT_ID = "erp-app";

    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/admin/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "security:resource:roles";

    /**
     * 认证信息Http请求头
     */
    String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 令牌前缀
     */
    String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    /**
     * 自定义TOKEN分隔符
     */
    String AUTHORIZATION_CUSTOM_TOKEN_SPLIT = "|";

    /**
     * 授权令牌
     */
    String AUTHORIZATION_ACCESS_TYPE = "ACCESS";

    /**
     * 刷新令牌
     */
    String AUTHORIZATION_REFRESH_TYPE = "REFRESH";

    String AUTHORIZATION_ERROR = "用户认证失败";

    /**
     * 存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * 存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 解析后的用户类型传递的头名称
     */
    String USER_TYPE_HEADER = "erp-user-type";

    /**
     * 解析后的用户唯一识别ID传递的头名称
     */
    String USER_ID_HEADER = "erp-user-id";
}
