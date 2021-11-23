package com.kabunx.erp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.domain.dto.UserDTO;
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

    /**
     * @param userDTO 直接接受前端传参
     * @return 分页数据
     */
    @GetMapping("/users/simple")
    public IPage<UserVO> simplePaginate(UserDTO userDTO) {
        return userService.simplePaginate(userDTO);
    }

    @GetMapping("/users/xml")
    public IPage<UserVO> xmlPaginate(UserDTO userDTO) {
        return userService.xmlPaginate(userDTO);
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
