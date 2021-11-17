package com.kabunx.erp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @GetMapping("/info")
    public String info() {
        return "123123";
    }

    @PostMapping("/logout")
    public boolean logout() {
        return true;
    }
}
