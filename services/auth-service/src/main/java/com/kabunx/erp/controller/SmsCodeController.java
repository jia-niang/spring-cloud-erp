package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.SmsCodeDTO;
import com.kabunx.erp.service.SmsCodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/sms-code")
public class SmsCodeController {

    @Resource
    SmsCodeService smsCodeService;

    @PostMapping()
    public String generate(@RequestBody @Valid SmsCodeDTO smsCodeDTO) {
        return smsCodeService.generateAndSend(smsCodeDTO);
    }
}
