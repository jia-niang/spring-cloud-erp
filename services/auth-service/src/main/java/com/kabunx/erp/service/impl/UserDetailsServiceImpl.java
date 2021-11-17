package com.kabunx.erp.service.impl;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.constant.AuthConstant;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.exception.AuthException;
import com.kabunx.erp.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl{ // implements UserDetailsService {
    @Resource
    UserFeignClient userFeignClient;

//    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JsonResponseBody<UserVO> response = userFeignClient.findByAccount(username);
        if (response.hasFallbackError()) {
            throw new AuthException(response.getMessage());
        }
        if (!response.isSuccess()) {
            throw new AuthException(AuthConstant.USERNAME_PASSWORD_ERROR);
        }
        return null;
    }
}
