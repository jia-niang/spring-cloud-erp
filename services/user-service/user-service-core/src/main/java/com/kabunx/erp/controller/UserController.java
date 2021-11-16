package com.kabunx.erp.controller;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.dto.UserDTO;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class UserController implements UserFeignClient {

    @Resource
    UserService userService;

    @Override
    public JsonResponseBody<UserVO> findByAccount(@RequestParam("account") String account) {
        return null;
    }

    @Override
    public JsonResponseBody<UserVO> create(@RequestBody @Valid UserDTO userDTO) {
        userService.create(userDTO);
        return null;
    }
}
