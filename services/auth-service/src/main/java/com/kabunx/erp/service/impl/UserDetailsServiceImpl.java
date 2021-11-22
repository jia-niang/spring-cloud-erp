package com.kabunx.erp.service.impl;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.constant.AuthConstant;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.exception.AuthException;
import com.kabunx.erp.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl {
    @Resource
    UserFeignClient userFeignClient;

    public UserVo loadUserByUsername(String username) {
        JsonResponse<UserVo> response = userFeignClient.list(username);
        if (response.hasFallbackError()) {
            throw new AuthException(response.getMessage());
        }
        if (!response.isSuccess()) {
            throw new AuthException(AuthConstant.USERNAME_PASSWORD_ERROR);
        }
        return null;
    }
}
