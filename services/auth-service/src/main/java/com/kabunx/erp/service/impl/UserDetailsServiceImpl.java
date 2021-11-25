package com.kabunx.erp.service.impl;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl {
    @Resource
    UserFeignClient userFeignClient;

    public UserVO loadUserByUsername(String username) {
        return null;
    }
}
