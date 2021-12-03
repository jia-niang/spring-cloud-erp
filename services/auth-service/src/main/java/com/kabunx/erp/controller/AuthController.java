package com.kabunx.erp.controller;

import com.kabunx.erp.service.impl.UserServiceImpl;
import com.kabunx.erp.vo.UserVO;;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthController {

    @Resource
    UserServiceImpl userDetailsService;

    @GetMapping("/info")
    public UserVO info() {
        return userDetailsService.loadUserById(12);
    }

    @PostMapping("/logout")
    public String logout() {
        return "name";
    }
}
