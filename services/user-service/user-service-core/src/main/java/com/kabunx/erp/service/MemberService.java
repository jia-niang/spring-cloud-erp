package com.kabunx.erp.service;

import com.kabunx.erp.vo.MemberVO;

public interface MemberService {
    MemberVO findById(Integer id);

    MemberVO findByIdWithUser(Integer id);
}
