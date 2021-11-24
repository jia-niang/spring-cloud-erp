package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.LoginAccountDTO;
import com.kabunx.erp.domain.dto.LoginSmsCodeDTO;
import com.kabunx.erp.domain.dto.LoginMiniAppDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/account")
    public LoginAccountDTO loginByAccount(@RequestBody @Valid LoginAccountDTO loginAccountDTO) {
        return loginAccountDTO;
    }

    @PostMapping("/sms-code")
    public LoginSmsCodeDTO loginBySmsCode(@RequestBody @Valid LoginSmsCodeDTO loginSmsCodeDTO) {
        return loginSmsCodeDTO;
    }

    @PostMapping("/mini-app")
    public LoginMiniAppDTO loginByMiniApp(@RequestBody @Valid LoginMiniAppDTO loginMiniAppDTO) {
        return loginMiniAppDTO;
    }
}

