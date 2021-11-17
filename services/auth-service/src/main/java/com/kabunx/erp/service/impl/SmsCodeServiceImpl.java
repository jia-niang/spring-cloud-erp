package com.kabunx.erp.service.impl;

import com.kabunx.erp.domain.dto.LoginKeyValueDTO;
import com.kabunx.erp.exception.AuthException;
import com.kabunx.erp.service.CaptchaService;
import com.kabunx.erp.service.SmsCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SmsCodeServiceImpl implements SmsCodeService {
    @Resource
    CaptchaService captchaService;

    @Override
    public boolean sendByLoginKeyValue(LoginKeyValueDTO loginKeyValueDTO) {
        // 1、认证初始验证码是否正确
        if (!captchaService.verifyKeyValue(loginKeyValueDTO)) {
            throw new AuthException("");
        }
        // 发送短信验证码
        return false;
    }
}
