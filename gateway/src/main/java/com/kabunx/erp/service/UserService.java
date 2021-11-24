package com.kabunx.erp.service;

import com.kabunx.erp.vo.MemberVO;

public interface UserService {
    MemberVO findAndValidateMember(String authToken);
}
