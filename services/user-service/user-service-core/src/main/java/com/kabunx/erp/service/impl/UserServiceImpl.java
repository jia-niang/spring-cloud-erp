package com.kabunx.erp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import com.kabunx.erp.wrapper.UserWrapper;
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
    public UserVO findByPhone(String phone) {
        UserWrapper<UserDO> wrapper = new UserWrapper<>();
        wrapper.eqPhone(phone);
        UserDO user = userMapper.selectOne(wrapper);
        return Hydrate.map(user, UserVO.class);
    }

    @Override
    public UserVO create(UserFromDTO userFromDto) {
        return Hydrate.map(userFromDto, UserVO.class);
    }

    @Override
    public IPage<UserDO> paginate(UserQueryDTO<UserDO> userQuery) {
        return userMapper.selectPage(userQuery.getPage(), userQuery.getWrapper());
    }

    @Override
    public IPage<UserVO> simplePaginate(UserDTO userDTO) {
        Page<UserVO> page = new Page<>(1, 10);
        return userMapper.simpleSelectPage(page, userDTO);
    }

    public IPage<UserVO> xmlPaginate(UserDTO userDTO) {
        Page<UserVO> page = new Page<>(1, 10);
        return userMapper.xmlSelectPage(page, userDTO);
    }
}
