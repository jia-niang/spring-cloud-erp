package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.CaptchaDTO;
import com.kabunx.erp.domain.vo.CaptchaVO;
import com.kabunx.erp.service.CaptchaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    CaptchaService captchaService;

    @PostMapping()
    public CaptchaVO image(@RequestBody @Valid CaptchaDTO captchaDTO) {
        return captchaService.generate(captchaDTO);
    }
}
