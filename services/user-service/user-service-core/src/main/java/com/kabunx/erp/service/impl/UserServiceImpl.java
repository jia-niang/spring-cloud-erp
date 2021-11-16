package com.kabunx.erp.service.impl;

import com.kabunx.erp.dto.UserDTO;
import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    AdminService adminService;

    @Resource
    MemberService memberService;

    @Override
    public UserVO create(UserDTO userDTO) {
        return null;
    }
}
