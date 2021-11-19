package com.kabunx.erp.controller;

import com.kabunx.erp.config.NacosConfig;
import com.kabunx.erp.domain.dto.LoginKeyValueDTO;
import com.kabunx.erp.service.SmsCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/sms-code")
public class SmsCodeController {

    @PostMapping("/login")
    public boolean login(
            @RequestBody @Valid LoginKeyValueDTO loginKeyValueDTO,
            SmsCodeService smsCodeService
    ) {
        return smsCodeService.sendByLoginKeyValue(loginKeyValueDTO);
    }

    @GetMapping("/config")
    public NacosConfig config(NacosConfig nacosConfig) {
        return nacosConfig;
    }

    @GetMapping("/string")
    public String string() {
        return "123123";
    }
}
