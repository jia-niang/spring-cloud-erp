package com.kabunx.erp.service.impl;

import com.kabunx.erp.converter.ObjectConverter;
import com.kabunx.erp.domain.dto.UserFilterDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.User;
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

    @Resource
    UserMapper userMapper;

    @Override
    public UserVO create(UserFromDTO userFromDto) {
        return ObjectConverter.map(userFromDto, UserVO.class);
    }

    @Override
    public Object paginate(UserFilterDTO<User> userFilter) {
        return userMapper.selectPage(userFilter.getPage(), userFilter.getQueryWrapper());
    }
}
