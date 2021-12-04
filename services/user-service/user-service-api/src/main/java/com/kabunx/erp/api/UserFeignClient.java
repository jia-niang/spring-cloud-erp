package com.kabunx.erp.api;

import com.kabunx.erp.api.fallback.UserFeignClientFallback;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "erp-user-service", contextId = "userFeignClient", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    @GetMapping("/users/{id}")
    JsonResponse<UserVO> show(@PathVariable("id") Integer id);

    @GetMapping("/users/phone/{phone}")
    JsonResponse<UserVO> phone(@PathVariable("phone") String phone);

    @GetMapping("/users/account/{account}")
    JsonResponse<UserVO> account(@PathVariable("account") String account);

    @PostMapping("/users")
    JsonResponse<UserVO> create(@RequestBody UserFromDTO userFromDTO);
}
