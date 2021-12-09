package com.kabunx.erp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.builder.QueryBuilder;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.exception.ExceptionEnum;
import com.kabunx.erp.exception.UserException;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.resource.PaginatedResource;
import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import com.kabunx.erp.wrapper.UserQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    AdminService adminService;

    @Resource
    MemberService memberService;

    @Resource
    UserMapper userMapper;

    @Override
    public UserVO findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public UserVO findByAccount(String account) {
        UserQueryWrapper<UserDO> wrapper = new UserQueryWrapper<>();
        wrapper.eqAccount(account);
        UserDO user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new UserException(ExceptionEnum.NOT_FOUND);
        }
        return Hydrate.map(user, UserVO.class);
    }

    @Override
    public UserVO findByPhone(String phone) {
        UserQueryWrapper<UserDO> wrapper = new UserQueryWrapper<>();
        wrapper.eqPhone(phone);
        UserDO user = userMapper.selectOne(wrapper);
        return Hydrate.map(user, UserVO.class);
    }

    @Override
    public UserVO create(UserFromDTO userFromDTO) {
        UserDO user = Hydrate.map(userFromDTO, UserDO.class);
        int count = userMapper.insert(user);
        log.info("{}", count);
        return Hydrate.map(user, UserVO.class);
    }

    @Override
    public Integer destroy(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public PaginatedResource<UserVO> paginate(UserQueryDTO userQueryDTO) {
        QueryBuilder<UserDO> builder = new QueryBuilder<>(userQueryDTO, new UserQueryWrapper<>());
        IPage<UserDO> page = userMapper.selectPage(builder.getQueryPage(), builder.getQueryWrapper());
        return PaginatedResource.toResource(page, UserVO.class);
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
