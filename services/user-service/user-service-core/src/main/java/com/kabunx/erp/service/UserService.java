package com.kabunx.erp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.vo.UserVO;

public interface UserService {

    UserVO findByPhone(String phone);

    UserVO create(UserFromDTO userFromDto);

    IPage<UserDO> paginate(UserQueryDTO<UserDO> userQuery);

    IPage<UserVO> simplePaginate(UserDTO userDTO);

    IPage<UserVO> xmlPaginate(UserDTO userDTO);
}
