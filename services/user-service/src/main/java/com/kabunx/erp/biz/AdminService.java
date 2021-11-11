package com.kabunx.erp.biz;

import com.kabunx.erp.domain.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AdminService {
    UserDto loadUserByUsername(String username) throws UsernameNotFoundException;
}
