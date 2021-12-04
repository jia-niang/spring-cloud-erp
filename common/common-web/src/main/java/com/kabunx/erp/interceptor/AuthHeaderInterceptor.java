package com.kabunx.erp.interceptor;

import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.entity.CurrentUser;
import com.kabunx.erp.util.ThreadLocalUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 提取头信息中的用户信息
 */
public class AuthHeaderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(SecurityConstant.USER_ID_HEADER);
        String userType = request.getHeader(SecurityConstant.USER_TYPE_HEADER);
        if (userId != null && userType != null) {
            CurrentUser user = new CurrentUser();
            user.setId(Long.parseLong(userId));
            user.setType(userType);
            ThreadLocalUtils.setCurrentUser(user);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.removeCurrentUser();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
