package com.kabunx.erp.controller;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.dto.UserDto;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class UserController implements UserFeignClient {

    @Resource
    UserService userService;

    @Override
    public JsonResponse<UserVo> list(String account) {
        return null;
    }

    @Override
    public JsonResponse<UserVo> show(@PathVariable("id") Integer id) {
        return null;
    }

    @Override
    public JsonResponse<UserVo> create(@RequestBody @Valid UserDto userDTO) {
        return null;
    }
}
