package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.LoginKeyValueDTO;
import com.kabunx.erp.service.SmsCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/sms-code")
public class SmsCodeController {

    @Resource
    SmsCodeService smsCodeService;

    @PostMapping("/login")
    public boolean login(@RequestBody @Valid LoginKeyValueDTO loginKeyValueDTO) {
        return smsCodeService.sendByLoginKeyValue(loginKeyValueDTO);
    }
}
