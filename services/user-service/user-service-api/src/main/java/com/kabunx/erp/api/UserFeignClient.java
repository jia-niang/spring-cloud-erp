package com.kabunx.erp.api;

import com.kabunx.erp.api.fallback.UserFeignClientFallback;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.dto.UserDTO;
import com.kabunx.erp.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "erp-user-service", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    @GetMapping("users")
    JsonResponseBody<UserVO> findByAccount(@RequestParam("account") String account);

    @PostMapping("users")
    JsonResponseBody<UserVO> create(@RequestBody UserDTO userDTO);

    @GetMapping("users/{id}")
    JsonResponseBody<UserVO> show(@PathVariable("id") String id);
}
