package com.kabunx.erp.service;

import com.kabunx.erp.vo.AdminVO;

public interface AdminService {

    AdminVO findByUserId(Long userId);
}
