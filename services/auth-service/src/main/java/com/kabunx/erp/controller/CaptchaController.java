package com.kabunx.erp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @PostMapping("/login-key")
    public String loginKey() {
        return "12312";
    }
}
