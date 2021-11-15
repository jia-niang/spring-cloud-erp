package com.kabunx.erp.service.impl;

import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
