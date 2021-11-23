package com.kabunx.erp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserFilterDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.model.User;
import com.kabunx.erp.vo.UserVO;

public interface UserService {

    UserVO create(UserFromDTO userFromDto);

    IPage<User> paginate(UserFilterDTO<User> userFilter);

    IPage<UserVO> simplePaginate(UserDTO userDTO);

    IPage<UserVO> xmlPaginate(UserDTO userDTO);
}
