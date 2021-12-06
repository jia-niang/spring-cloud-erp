package com.kabunx.erp.api;

import com.kabunx.erp.api.fallback.AdminFeignClientFallback;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.vo.AdminVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "erp-user-service",
        contextId = "admin",
        fallback = AdminFeignClientFallback.class,
        path = "/user"
)
public interface AdminFeignClient {

    @GetMapping("/admins/{id}")
    JsonResponse<AdminVO> findById(@PathVariable("id") Long id);
}
