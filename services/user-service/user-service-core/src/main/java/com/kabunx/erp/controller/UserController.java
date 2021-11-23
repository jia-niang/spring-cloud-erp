package com.kabunx.erp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.domain.dto.UserFilterDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.model.User;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class UserController implements UserFeignClient {

    @Resource
    UserService userService;

    @GetMapping("/users")
    public IPage<User> paginate(@RequestBody UserFilterDTO<User> filterDto) {
        return userService.paginate(filterDto);
    }

    @Override
    public JsonResponse<UserVO> show(@PathVariable("id") Integer id) {
        return null;
    }

    @Override
    public JsonResponse<UserVO> create(@RequestBody @Valid UserFromDTO userFromDTO) {
        return JsonResponse.success(userService.create(userFromDTO));
    }
}
