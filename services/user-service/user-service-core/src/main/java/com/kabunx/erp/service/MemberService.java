package com.kabunx.erp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kabunx.erp.vo.MemberVo;

public interface MemberService {
    MemberVo findById(Integer id);
}
