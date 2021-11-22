package com.kabunx.erp.service;

import com.kabunx.erp.dto.UserDto;
import com.kabunx.erp.vo.UserVo;

public interface UserService {

    UserVo create(UserDto userDTO);
}
