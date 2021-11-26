package com.kabunx.erp.controller;

import com.kabunx.erp.service.impl.UserDetailsServiceImpl;
import com.kabunx.erp.vo.UserVO;;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class AuthController {

    @Resource
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/info/{id}")
    public UserVO info(@PathVariable("id") Integer id) {
        return userDetailsService.loadUserById(id);
    }

    @PostMapping("/logout")
    public String logout() {
        return "name";
    }
}
