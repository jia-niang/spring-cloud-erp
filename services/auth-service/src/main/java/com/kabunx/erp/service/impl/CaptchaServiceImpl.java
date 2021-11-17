package com.kabunx.erp.service.impl;

import com.kabunx.erp.domain.dto.LoginKeyValueDTO;
import com.kabunx.erp.service.CaptchaService;
import com.kabunx.erp.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Resource
    RedisService redisService;

    public boolean verifyKeyValue(LoginKeyValueDTO loginKeyValueDTO) {
        redisService.get(loginKeyValueDTO.getCaptchaKey());
        return true;
    }
}
