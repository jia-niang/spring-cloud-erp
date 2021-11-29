package com.kabunx.erp.controller;

import com.kabunx.erp.domain.vo.CaptchaVO;
import com.kabunx.erp.service.CaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    CaptchaService captchaService;

    @GetMapping("/image/{type}")
    public CaptchaVO image(@PathVariable("type") @NotEmpty String type) {
        return captchaService.generate(type);
    }
}
