package com.kabunx.erp.service.impl;

import com.kabunx.erp.mapper.AdminMapper;
import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.vo.AdminVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    @Override
    public AdminVO findByUserId(Integer userId) {
        return adminMapper.findByUserId(userId);
    }
}
