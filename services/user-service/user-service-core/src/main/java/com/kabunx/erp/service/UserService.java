package com.kabunx.erp.service;

import com.kabunx.erp.dto.UserDTO;
import com.kabunx.erp.vo.UserVO;

public interface UserService {

    UserVO create(UserDTO userDTO);
}
