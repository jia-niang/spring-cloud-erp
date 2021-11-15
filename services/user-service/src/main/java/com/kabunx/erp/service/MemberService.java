package com.kabunx.erp.service;

import com.kabunx.erp.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService {
    UserDto loadUserByUsername(String username) throws UsernameNotFoundException;
}
