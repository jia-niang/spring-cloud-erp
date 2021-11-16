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
     * JWT认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * JWT授权令牌
     */
    String JWT_ACCESS_TYPE = "ACCESS";

    /**
     * JWT刷新令牌
     */
    String JWT_REFRESH_TYPE = "REFRESH";

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 解析后的用户信息传递的Header Key
     */
    String USER_ID_HEADER = "security-user-id";
}
