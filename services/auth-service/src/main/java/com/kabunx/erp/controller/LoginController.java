package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.AccountLoginDTO;
import com.kabunx.erp.domain.dto.SmsCodeLoginDTO;
import com.kabunx.erp.domain.dto.MiniAppLoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/account")
    public AccountLoginDTO loginByAccount(@RequestBody @Valid AccountLoginDTO accountLoginDTO) {
        return accountLoginDTO;
    }

    @PostMapping("/sms-code")
    public SmsCodeLoginDTO loginBySmsCode(@RequestBody @Valid SmsCodeLoginDTO codeLoginDTO) {
        return codeLoginDTO;
    }

    @PostMapping("/mini-app")
    public MiniAppLoginDTO loginByMiniApp(@RequestBody @Valid MiniAppLoginDTO miniAppLoginDTO) {
        return miniAppLoginDTO;
    }
}

