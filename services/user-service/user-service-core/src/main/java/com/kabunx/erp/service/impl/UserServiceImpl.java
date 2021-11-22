package com.kabunx.erp.service.impl;

import com.kabunx.erp.domain.dto.UserFilterDto;
import com.kabunx.erp.dto.UserDto;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.User;
import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    AdminService adminService;

    @Resource
    MemberService memberService;

    @Resource
    UserMapper userMapper;

    @Override
    public UserVo create(UserDto userDto) {
        return null;
    }

    @Override
    public Object paginate(UserFilterDto<User> userFilter) {
        return userMapper.selectPage(userFilter.getPage(), userFilter.getQueryWrapper());
    }
}
