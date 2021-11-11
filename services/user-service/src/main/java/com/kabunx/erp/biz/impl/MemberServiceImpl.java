package com.kabunx.erp.biz.impl;

import com.kabunx.erp.biz.MemberService;
import com.kabunx.erp.domain.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
