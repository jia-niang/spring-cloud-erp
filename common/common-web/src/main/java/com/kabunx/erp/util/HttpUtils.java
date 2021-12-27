package com.kabunx.erp.util;

import com.kabunx.erp.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求相关工具类
 */
@Slf4j
public class HttpUtils {
    private static final String USER_AGENT_FLAG = "user-agent";
    private static final String[] HEADER_IP_KEYWORDS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "X-Real-IP"
    };

    /***
     * 构建请求参数Map
     */
    public static Map<String, Object> buildParamsMap(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        if (!paramNames.hasMoreElements()) {
            return Collections.emptyMap();
        }
        Map<String, Object> result = new HashMap<>();
        try {
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String[] values = request.getParameterValues(paramName);
                if (ObjectUtils.notEmpty(values)) {
                    if (values.length == 1) {
                        if (ObjectUtils.notEmpty(values[0])) {
                            String paramValue = java.net.URLDecoder.decode(values[0], GlobalConstant.CHARSET_UTF8);
                            result.put(paramName, paramValue);
                        }
                    } else {
                        String[] valueArray = new String[values.length];
                        for (int i = 0; i < values.length; i++) {
                            valueArray[i] = java.net.URLDecoder.decode(values[i], GlobalConstant.CHARSET_UTF8);
                        }
                        result.put(paramName, valueArray);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("构建请求参数异常", e);
        }
        return result;
    }

    /***
     * 获取user-agent
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(USER_AGENT_FLAG);
    }

    /***
     * 获取客户ip地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        for (String header : HEADER_IP_KEYWORDS) {
            String ipAddresses = request.getHeader(header);
            if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
                continue;
            }
            if (ObjectUtils.notEmpty(ipAddresses)) {
                return ipAddresses.split(GlobalConstant.SEPARATOR)[0];
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 构建TrustManager
     */
    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }
}
