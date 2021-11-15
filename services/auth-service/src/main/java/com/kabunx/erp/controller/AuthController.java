package com.kabunx.erp.controller;

import com.kabunx.erp.domain.AuthRequest;
import com.kabunx.erp.domain.AuthResponse;
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

    @PostMapping(value = "/register")
    public AuthResponse register(@RequestBody AuthRequest authRequest) {
        return authService.register(authRequest);
    }
}
