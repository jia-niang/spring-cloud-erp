package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.SmsCodeDTO;
import com.kabunx.erp.service.SmsCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/sms-code")
public class SmsCodeController {

    @Resource
    SmsCodeService smsCodeService;

    @PostMapping("")
    public boolean send(@RequestBody @Valid SmsCodeDTO smsCodeDTO) {
        return smsCodeService.send(smsCodeDTO);
    }
}
