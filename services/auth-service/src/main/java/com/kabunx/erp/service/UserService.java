package com.kabunx.erp.service;

import com.kabunx.erp.vo.UserVO;

public interface UserService {
    UserVO loadByAccount(String account);

    UserVO loadByPhone(String phone);
}
