package com.kabunx.erp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.mapper.MemberMapper;
import com.kabunx.erp.model.MemberDO;
import com.kabunx.erp.pagination.LengthPaginator;
import com.kabunx.erp.pagination.SimplePaginator;
import com.kabunx.erp.query.AutoBuilder;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.query.Builder;
import com.kabunx.erp.relation.HasOne;
import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import com.kabunx.erp.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        return Hydrate.map2Target(userMapper.findById(id), UserVO.class);
    }

    @Override
    public UserVO findByAccount(String account) {
        Builder<UserDO> builder = new Builder<>(userMapper);
        List<UserDO> users = builder.hasOne("member", (HasOne<MemberDO, UserDO> relation) -> {
                    relation.setRelated(memberMapper, "user_id");
                    relation.setFullback(UserDO::setMember);
                })
                .filter(w -> {
                    w.eq("account", account);
                })
                .with("member")
                .get();
        return Hydrate.map2Target(users, UserVO.class);
    }

    @Override
    public UserVO findByPhone(String phone) {
        UserWrapper wrapper = new UserWrapper();
        wrapper.eqPhone(phone);
        UserDO user = userMapper.selectOne(wrapper);
        return Hydrate.map2Target(user, UserVO.class);
    }

    @Override
    public UserVO create(UserFromDTO userFromDTO) {
        UserDO user = Hydrate.map2Target(userFromDTO, UserDO.class);
        int count = userMapper.insert(user);
        log.info("{}", count);
        return Hydrate.map2Target(user, UserVO.class);
    }

    @Override
    public Integer destroy(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public LengthPaginator<UserDO> paginate(UserQueryDTO userQueryDTO) {
        AutoBuilder<UserDO> builder = new AutoBuilder<>(
                userMapper, new UserWrapper(userQueryDTO)
        );
        return builder.paginate();
    }

    @Override
    public SimplePaginator<UserDO> simplePaginate(UserQueryDTO userQueryDTO) {
        AutoBuilder<UserDO> builder = new AutoBuilder<>(
                userMapper, new UserWrapper(userQueryDTO)
        );
        return builder.simplePaginate();
    }

    public IPage<UserDO> xmlPaginate(UserDTO userDTO) {
        Page<UserDO> page = new Page<>(1, 10);
        return userMapper.xmlSelectPage(page, userDTO);
    }
}
