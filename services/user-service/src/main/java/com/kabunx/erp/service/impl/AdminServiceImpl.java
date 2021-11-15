package com.kabunx.erp.service.impl;

import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
