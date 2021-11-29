package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.LoginCaptchaDTO;
import com.kabunx.erp.domain.dto.LoginSmsCodeDTO;
import com.kabunx.erp.domain.dto.LoginMiniAppDTO;
import com.kabunx.erp.domain.vo.AuthTokenVO;
import com.kabunx.erp.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    LoginService loginService;

    @PostMapping("/captcha")
    public AuthTokenVO loginByAccount(@RequestBody @Valid LoginCaptchaDTO loginCaptchaDTO) {
        return loginService.loginByCaptcha(loginCaptchaDTO);
    }

    @PostMapping("/sms-code")
    public AuthTokenVO loginBySmsCode(@RequestBody @Valid LoginSmsCodeDTO loginSmsCodeDTO) {
        return loginService.loginBySmsCode(loginSmsCodeDTO);
    }

    @PostMapping("/mini-app")
    public AuthTokenVO loginByMiniApp(@RequestBody @Valid LoginMiniAppDTO loginMiniAppDTO) {
        return loginService.loginByMiniApp(loginMiniAppDTO);
    }
}

