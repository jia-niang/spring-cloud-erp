package com.kabunx.erp.biz;

import com.kabunx.erp.domain.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService {
    UserDto loadUserByUsername(String username) throws UsernameNotFoundException;
}
