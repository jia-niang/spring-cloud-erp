package com.kabunx.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.mapper.MemberMapper;
import com.kabunx.erp.query.AutoBuilder;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.query.Builder;
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

    @Resource
    MemberMapper memberMapper;

    @Override
    public UserVO findById(Long id) {
        UserQueryWrapper<UserDO> wrapper = new UserQueryWrapper<>();
        wrapper.eq("sex", 1);
        UserDO user = userMapper.firstOrFail(wrapper);
        return userMapper.findById(id);
    }

    @Override
    public UserVO findByAccount(String account) {
        Builder<UserDO> builder = new Builder<>(userMapper);
        builder.withOne(memberMapper, "user_id", "id");
        UserDO user1 = builder.where("account", account).firstOrFail();

        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        UserDO user2 = userMapper.firstOrFail(wrapper);
        return Hydrate.map(user2, UserVO.class);
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
        AutoBuilder<UserDO> builder = new AutoBuilder<>(userQueryDTO, new UserQueryWrapper<>());
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
