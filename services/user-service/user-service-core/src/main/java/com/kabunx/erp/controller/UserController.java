package com.kabunx.erp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class UserController implements UserFeignClient {

    @Resource
    UserService userService;

    @Override
    public JsonResponse<UserVO> show(@PathVariable("id") Integer id) {
        return null;
    }

    @Override
    public JsonResponse<UserVO> phone(String phone) {
        return JsonResponse.success(userService.findByPhone(phone));
    }

    @Override
    public JsonResponse<UserVO> account(String account) {
        return JsonResponse.success(userService.findByAccount(account));
    }

    @Override
    public JsonResponse<UserVO> create(@RequestBody @Valid UserFromDTO userFromDTO) {
        return JsonResponse.success(userService.create(userFromDTO));
    }

    @GetMapping("/users")
    public IPage<UserDO> paginate(@RequestParam(required = false) UserQueryDTO<UserDO> userQueryDTO) {
        return userService.paginate(userQueryDTO);
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

    @DeleteMapping("/users/{id}")
    public JsonResponse<Integer> create(@PathVariable("id") Long id) {
        return JsonResponse.success(userService.destroy(id));
    }
}
