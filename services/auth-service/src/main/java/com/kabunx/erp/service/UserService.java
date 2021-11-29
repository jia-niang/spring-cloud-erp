package com.kabunx.erp.service;

import com.kabunx.erp.vo.UserVO;

public interface UserService {
    UserVO loadByUsername(String username);
}
