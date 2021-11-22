package com.kabunx.erp.service;

import com.kabunx.erp.domain.dto.UserFilterDto;
import com.kabunx.erp.dto.UserDto;
import com.kabunx.erp.model.User;
import com.kabunx.erp.vo.UserVo;

public interface UserService {

    UserVo create(UserDto userDto);

    Object paginate(UserFilterDto<User> userFilter);

}
