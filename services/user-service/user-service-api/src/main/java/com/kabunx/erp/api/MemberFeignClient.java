package com.kabunx.erp.api;

import com.kabunx.erp.api.fallback.MemberFeignClientFallback;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.vo.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "erp-user-service", fallback = MemberFeignClientFallback.class)
public interface MemberFeignClient {

    @GetMapping("/members/{id}")
    JsonResponse<MemberVo> findById(@PathVariable("id") Integer id);
}
