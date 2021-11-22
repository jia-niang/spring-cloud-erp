package com.kabunx.erp.api;

import com.kabunx.erp.api.fallback.UserFeignClientFallback;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.dto.UserDto;
import com.kabunx.erp.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "erp-user-service", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    @GetMapping("/users/{id}")
    JsonResponse<UserVo> show(@PathVariable("id") Integer id);

    @PostMapping("/users")
    JsonResponse<UserVo> create(@RequestBody UserDto userDTO);
}
