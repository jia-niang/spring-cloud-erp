package com.kabunx.erp.service.impl;

import com.kabunx.erp.service.LoginService;
import com.kabunx.erp.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountLoginServiceImpl implements LoginService {
    @Resource
    JwtUtil jwt;
}
