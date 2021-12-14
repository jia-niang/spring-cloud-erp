package com.kabunx.erp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.pagination.LengthPaginator;
import com.kabunx.erp.pagination.SimplePaginator;
import com.kabunx.erp.vo.UserVO;

public interface UserService {

    UserVO findById(Long id);

    UserVO findByAccount(String account);

    UserVO findByPhone(String phone);

    UserVO create(UserFromDTO userFromDto);

    Integer destroy(Long id);

    LengthPaginator<UserDO> paginate(UserQueryDTO userQueryDTO);

    SimplePaginator<UserDO> simplePaginate(UserQueryDTO userQueryDTO);

    IPage<UserDO> xmlPaginate(UserDTO userDTO);
}
