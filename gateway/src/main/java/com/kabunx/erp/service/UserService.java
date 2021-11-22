package com.kabunx.erp.service;

import com.kabunx.erp.vo.MemberVo;
import com.kabunx.erp.vo.UserTokenVo;

public interface UserService {
    MemberVo findAndValidateByToken(String authToken);
}
