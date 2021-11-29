package com.kabunx.erp.service.impl;

import com.kabunx.erp.domain.dto.SmsCodeDTO;
import com.kabunx.erp.service.SmsCodeService;
import org.springframework.stereotype.Service;

@Service
public class SmsCodeServiceImpl implements SmsCodeService {

    @Override
    public boolean send(SmsCodeDTO smsCodeDTO) {
        // 1、认证初始验证码是否正确
        // 发送短信验证码
        return false;
    }
}
