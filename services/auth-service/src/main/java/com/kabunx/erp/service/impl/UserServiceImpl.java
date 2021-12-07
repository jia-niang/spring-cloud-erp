package com.kabunx.erp.service.impl;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserFeignClient userFeignClient;

    public UserVO loadByAccount(String account) {
        JsonResponse<UserVO> response = userFeignClient.account(account);
        if (response.unavailable()) {
            return null;
        }
        return response.getData();
    }

    @Override
    public UserVO loadByPhone(String phone) {
        JsonResponse<UserVO> response = userFeignClient.phone(phone);
        if (response.unavailable()) {
            return null;
        }
        return response.getData();
    }

    public UserVO loadUserById(Long id) {
        JsonResponse<UserVO> response = userFeignClient.show(id);
        if (response.unavailable()) {
            return null;
        }
        return response.getData();
    }

}
