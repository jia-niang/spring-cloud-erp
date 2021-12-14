package com.kabunx.erp.controller;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.pagination.LengthPaginator;
import com.kabunx.erp.pagination.SimplePaginator;
import com.kabunx.erp.resource.LengthPaginatedResource;
import com.kabunx.erp.resource.SimplePaginatedResource;
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
    public LengthPaginatedResource<UserVO> paginate(@Valid UserQueryDTO userQueryDTO) {
        LengthPaginator<UserDO> paginator = userService.paginate(userQueryDTO);
        return LengthPaginatedResource.toResource(paginator, UserVO.class);
    }

    @Override
    public JsonResponse<UserVO> show(@PathVariable("id") Long id) {
        return JsonResponse.success(userService.findById(id));
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

    /**
     * 简单分页数据
     */
    @GetMapping("/users/simple")
    public SimplePaginatedResource<UserVO> simplePaginate(@Valid UserQueryDTO userQueryDTO) {
        SimplePaginator<UserDO> simplePaginator = userService.simplePaginate(userQueryDTO);
        return SimplePaginatedResource.toResource(simplePaginator, UserVO.class);
    }

    @GetMapping("/users/xml")
    public LengthPaginatedResource<UserVO> xmlPaginate(UserDTO userDTO) {
        return LengthPaginatedResource.toResource(
                userService.xmlPaginate(userDTO),
                UserVO.class
        );
    }

    @DeleteMapping("/users/{id}")
    public JsonResponse<Integer> create(@PathVariable("id") Long id) {
        return JsonResponse.success(userService.destroy(id));
    }
}
