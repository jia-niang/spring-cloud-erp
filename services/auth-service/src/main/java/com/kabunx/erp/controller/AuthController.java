package com.kabunx.erp.controller;

import com.kabunx.erp.domain.dto.LoginDTO;
import com.kabunx.erp.domain.dto.RegisterDTO;
import com.kabunx.erp.domain.vo.AuthTokenVO;
import com.kabunx.erp.vo.UserVO;
import com.kabunx.erp.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Resource
    AuthService authService;

    @PostMapping("/login")
    public AuthTokenVO login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/login/wechat")
    public AuthTokenVO wechatLogin(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/login/sms-code")
    public AuthTokenVO smsCodeLogin(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/login/qr-code")
    public AuthTokenVO qrCodeLogin(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    public UserVO register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
}
